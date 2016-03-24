package io.padarom.migration.tests;

import io.padarom.migration.MigrationInterface;
import io.padarom.migration.Migrator;
import io.padarom.migration.repository.DatabaseMigrationRepository;
import io.padarom.migration.repository.MigrationRepositoryInterface;
import io.padarom.migration.schema.Schema;
import org.junit.*;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;

public class MainTests {

    private Connection connection;
    private Migrator migrator;

    @Before
    public void setUp() throws SQLException {
        // Just use a Memory Database, don't save it to disk
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");

        this.migrator = new Migrator(connection, "io.padarom.migration.tests.migrations");
    }

    @Test
    public void itCreatesAMigrationTable() throws SQLException {
        MigrationRepositoryInterface repository = new DatabaseMigrationRepository(connection, "migrations");
        repository.createRepository();

        assertTrue(Schema.hasTable("migrations"));
    }

    @Test
    public void itSortsMigrationsByTheirPriority() {
        List<MigrationInterface> list =  migrator.getMigrationList();

        String[] migrationNames = new String[] { "CreateTestsTable", "CreateUsersTable" };
        int counter = 0;

        // The migration list has to contain 2 entries
        assertEquals(list.size(), 2);

        for (MigrationInterface element : list) {
            assertEquals(migrationNames[counter++], element.getClass().getSimpleName());
        }
    }

    @Test
    public void itCanCreateTables() throws SQLException {
        Schema.create("test_table", table -> {
            table.increments("id");
            table.string("name", 35).nullable().defaultsTo("peter");
        });

        assertTrue(Schema.hasTable("test_table"));
    }

}

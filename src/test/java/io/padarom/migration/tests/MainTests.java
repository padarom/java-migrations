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

    private MigrationRepositoryInterface migrationRepository;
    private Connection connection;
    private Migrator migrator;

    @Before
    public void setUp() throws SQLException {
        // Just use a Memory Database, don't save it to disk
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");

        this.migrationRepository = new DatabaseMigrationRepository(connection, "migrations");
        this.migrator = new Migrator(migrationRepository, connection, "io.padarom.migration.tests.migrations");
    }

    @Test
    public void itCreatesAMigrationTable() throws SQLException {
        migrationRepository.createRepository();

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
    public void itCanCreateTables() throws Exception {
        Schema.create("test_table", table -> {
            table.increments("id");
            table.string("name", 35).nullable().defaultsTo("peter");
            table.enumeration("value_list", new String[] {"a", "b", "c"});
            table.bool("active").defaultsTo("1");
        });

        assertTrue(Schema.hasTable("test_table"));
    }

    @Test
    public void itRunsAllMigrations() throws Exception {
        assertEquals(migrationRepository.getRan().size(), 0);

        migrator.runAllMigrations();

        assertEquals(migrationRepository.getRan().size(), 2);
    }

}

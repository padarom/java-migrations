package io.padarom.migration.tests;

import io.padarom.migration.MigrationInterface;
import io.padarom.migration.Migrator;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class MainTests {

    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        // Just use a Memory Database, don't save it to disk
        this.connection = DriverManager.getConnection("jdbc:sqlite::memory:");
    }

    @Test
    public void itSortsMigrationsByTheirPriority() {
        Migrator migrator = new Migrator(this.connection, "io.padarom.migration.tests.migrations");
        List<MigrationInterface> list =  migrator.getMigrationList();

        String[] migrationNames = new String[] { "CreateTestsTable", "CreateUsersTable" };
        int counter = 0;

        // The migration list has to contain 2 entries
        assertEquals(list.size(), 2);

        for (MigrationInterface element : list) {
            assertEquals(migrationNames[counter++], element.getClass().getSimpleName());
        }
    }

}

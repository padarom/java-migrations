package io.padarom.migration.tests;

import io.padarom.migration.MigrationInterface;
import io.padarom.migration.Migrator;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MainTests {

    @Test
    public void itSortsMigrationsByTheirPriority() {
        Migrator migrator = new Migrator("");
        List<MigrationInterface> list =  migrator.getMigrationList("io.padarom.migration.tests.migrations");

        String[] migrationNames = new String[] { "CreateTestsTable", "CreateUsersTable" };
        int counter = 0;

        // The migration list has to contain 2 entries
        assertEquals(list.size(), 2);

        for (MigrationInterface element : list) {
            assertEquals(migrationNames[counter++], element.getClass().getSimpleName());
        }
    }

}

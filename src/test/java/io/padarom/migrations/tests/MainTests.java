package io.padarom.migrations.tests;

import io.padarom.migrations.MigrationInterface;
import io.padarom.migrations.Migrator;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MainTests {

    @Test
    public void testTests() {
        assertEquals(7, 7);
    }

    @Test
    public void itSortsMigrationsByTheirPriority() {
        Migrator migrator = new Migrator("");
        List<MigrationInterface> list =  migrator.getMigrationList("io.padarom.migrations.tests.migration");

        String[] migrationNames = new String[] { "CreateTestsTable", "CreateUsersTable" };
        int counter = 0;

        for (MigrationInterface element : list) {
            assertEquals(migrationNames[counter++], element.getClass().getSimpleName());
        }
    }
}

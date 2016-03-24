package io.padarom.migration.tests.migrations;

import io.padarom.migration.Migration;
import io.padarom.migration.MigrationInterface;
import io.padarom.migration.schema.Schema;

@Migration (
        priority = 10
)
public class CreateTestsTable implements MigrationInterface {
    public void up() {
        Schema.create("tests", table -> {
            table.increments("id");
            table.string("description");

            table.timestamps();
        });
    }

    public void down() {
        Schema.drop("tests");
    }
}

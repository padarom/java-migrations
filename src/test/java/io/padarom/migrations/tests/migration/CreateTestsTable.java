package io.padarom.migrations.tests.migration;

import io.padarom.migrations.Migration;
import io.padarom.migrations.MigrationInterface;
import io.padarom.migrations.schema.Schema;

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

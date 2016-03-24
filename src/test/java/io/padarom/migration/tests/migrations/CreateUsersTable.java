package io.padarom.migration.tests.migrations;

import io.padarom.migration.Migration;
import io.padarom.migration.MigrationInterface;
import io.padarom.migration.schema.Schema;

@Migration(
        priority = 20
)
public class CreateUsersTable implements MigrationInterface {
    public void up() {
        Schema.create("users", table -> {
            table.increments("id");
            table.string("name");
            table.string("email");
            table.string("password");
            table.string("reset_token").nullable();

            table.timestamps();
        });
    }

    public void down() {
        Schema.drop("users");
    }
}

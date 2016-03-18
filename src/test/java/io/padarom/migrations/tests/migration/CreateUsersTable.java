package io.padarom.migrations.tests.migration;

import io.padarom.migrations.Migration;
import io.padarom.migrations.MigrationInterface;
import io.padarom.migrations.schema.Schema;

@Migration
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

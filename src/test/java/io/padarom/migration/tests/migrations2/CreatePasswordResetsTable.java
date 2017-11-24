package io.padarom.migration.tests.migrations2;

import io.padarom.migration.Migration;
import io.padarom.migration.MigrationInterface;
import io.padarom.migration.schema.ForeignKeyConstraint;
import io.padarom.migration.schema.Schema;

import static io.padarom.migration.schema.ForeignKeyConstraint.ConstraintType.CASCADE;

@Migration(
        priority = 21
)
public class CreatePasswordResetsTable implements MigrationInterface {
    public void up() throws Exception {
        Schema.create("password_resets", table -> {
            table.increments("id");
            table.integer("user_id").unsigned();
            table.string("reset_token").nullable();

            table.timestamps();

            table.foreign("user_id", "id", "users").onDelete("cascade");
        });
    }

    public void down() {
        Schema.drop("password_resets");
    }
}

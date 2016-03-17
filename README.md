# Migrations

Java Migrations for Java 8. _This package is a work in progress._

## Example
```java
package io.padarom.migrations.example;

import io.padarom.migrations.MigrationInterface;
import io.padarom.migrations.Migration;
import io.padarom.migrations.schema.Schema;

@Migration
public class CreateUsersTable implements MigrationInterface {

    @Override
    public void up() {
        Schema.create("users", table -> {
            table.increments("id");
            table.string("name");
            table.string("password");
            table.timestamps();
        });
    }

    @Override
    public void down() {
        Schema.drop("users");
    }
}
```
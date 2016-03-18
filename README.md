# Migrations

Java Migrations for Java 8. _This package is a work in progress._

## About this project
I make a living in the world of PHP and personally love the PHP web framework [Laravel](https://laravel.com/). One of the many great features it offers are [Database Migrations](https://laravel.com/docs/5.2/migrations). Their description of it is as follows:
> Migrations are like version control for your database, allowing a team to easily modify and share the application's database schema. Migrations are typically paired with Laravel's schema builder to easily build your application's database schema.

For my Java projects I more and more need to use databases, so I thought it would make sense to have a similar tool at hand that allows you to easily create and then run migrations without too much boilerplate code. The other migration tools I've found for Java didn't quite fit my needs, so I decided to write my own, heavily inspired by Laravel's migration syntax.

## Example Migration
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
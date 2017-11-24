# Migrations

Java Migrations for Java 8. _This package is a work in progress._

## About this project
I make a living in the world of PHP and love working with the PHP web framework [Laravel](https://laravel.com/). One of the many great features it offers are [Database Migrations](https://laravel.com/docs/5.2/migrations). Their description of it is as follows:
> Migrations are like version control for your database, allowing a team to easily modify and share the application's database schema. Migrations are typically paired with Laravel's schema builder to easily build your application's database schema.

For my Java projects I more and more need to use databases, so I thought it would make sense to have a similar tool at hand that allows me to easily create and then run migrations without too much boilerplate code. The other migration tools I've found for Java didn't quite fit my needs, so I decided to write my own, heavily inspired by Laravel's migration and schema builder syntax.

### Roadmap
- Migrations
  - [x] Run migrations within package
  - [ ] Run single migrations
  - [ ] Rollback all migrations
  - [x] Rollback batch of migrations
  - [x] Rollback individual migrations
  - [x] Create Tables
  - [x] Drop Tables
  - [ ] Modify existing tables
  - [ ] Rename tables
- Schema Builder
  - [x] Custom columns
  - [ ] Foreign keys
  - [ ] Custom indices
  - [ ] Pre-defined column definitions for ease of use

## Why should you use this?
- No need to learn library specific markup for XML, YAML or JSON definitions
- No need to manually write SQL statements
- Create your database schema using simple, nonverbose Java

## What this isn't
Keep in mind that this is not an ORM and not meant to ever become one. There are quasi standards for Java ORMs out there already, so this library focuses solely on setting up your database.

## How to use
### Define your migration
```java
package path.to.your.migrations;

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

You can define the order in which multiple migrations should run by specifying a priority (defaults to `100`):
```java
@Migration (priority = 10)
public class CreateFirstTable implements MigrationInterface {
   // ...
}

@Migration (priority = 20)
public class CreateSecondTable implements MigrationInterface {
    // ...
}
```

### Call the Migrator
```java
java.sql.Connection databaseConnection = somehowGetYourDatabaseConnection();

Migrator migrator = new Migrator(databaseConnection, "path.to.your.migrations");
migrator.runAllMigrations();
```

In this example the migrator automatically creates a `DatabaseMigrationRepository`, which stores a list of all previously ran migrations within the database, in the table _migrations_. If you don't want this, you can pass in your own `MigrationRepositoryInterface` to the migrator's constructor:

```java
DatabaseMigrationRepository repository = new DatabaseMigrationRepository(databaseConnection, "custom_migrations_table");
Migrator migrator = new Migrator(repository, databaseConnection, "path.to.your.migrations");
```

By doing this you can store the information of which migrations have already run however you'd like.

### SQL Flavors
This library works with JDBC. This library uses instances of `java.sql.Connection` to interact with the database. It will automatically determine the flavor of your DBMS by analyzing the driver's class name. Currently supported, and planned for future support are:

- [x] SQLite (`org.sqlite.JDBC`)
- [ ] MySQL (`com.mysql.jdbc.Driver`)
- [ ] PostgreSQL (`org.postgresql.Driver`)
- [ ] SqlServer (`com.microsoft.sqlserver.jdbc.SQLServerDriver`)

# Migrations

Java Migrations for Java 8. _This package is a work in progress._

## About this project
I make a living in the world of PHP and personally love the PHP web framework [Laravel](https://laravel.com/). One of the many great features it offers are [Database Migrations](https://laravel.com/docs/5.2/migrations). Their description of it is as follows:
> Migrations are like version control for your database, allowing a team to easily modify and share the application's database schema. Migrations are typically paired with Laravel's schema builder to easily build your application's database schema.

For my Java projects I more and more need to use databases, so I thought it would make sense to have a similar tool at hand that allows you to easily create and then run migrations without too much boilerplate code. The other migration tools I've found for Java didn't quite fit my needs, so I decided to write my own, heavily inspired by Laravel's migration syntax.

## Why this?

- No need to learn library specific markup for XML, YAML or JSON definitions
- No need to manually write SQL
- Create the schema using simple, nonverbose Java

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

If not otherwise specified the Migrator will use a `DatabaseMigrationRepository` with the table name `migrations`. What this means is, that the list of the ran migrations and batches will be stored inside a database table with the name _migrations_. You can override this table name, or use your own MigrationRepository (implementing the `MigrationRepositoryInterface`) and pass it to the Migrator constructor as the first parameter:

```java
DatabaseMigrationRepository repository = new DatabaseMigrationRepository(databaseConnection, "custom_migrations_table");
Migrator migrator = new Migrator(repository, databaseConnection, "path.to.your.migrations");
```

### SQL Flavors
This library is working with JDBC. All built classes use instances of `java.sql.Connection` to interact with the database. It will automatically determine the flavor of your DBMS by analyzing the driver's class name. Currently supported are:

- [x] SQLite (`org.sqlite.JDBC`)
- [ ] MySQL (`com.mysql.jdbc.Driver`)
- [ ] PostgreSQL (`org.postgresql.Driver`)
- [ ] SqlServer (`com.microsoft.sqlserver.jdbc.SQLServerDriver`)

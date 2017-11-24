package io.padarom.migration.tests;

import io.padarom.migration.MigrationInterface;
import io.padarom.migration.Migrator;
import io.padarom.migration.repository.DatabaseMigrationRepository;
import io.padarom.migration.repository.MigrationRepositoryInterface;
import io.padarom.migration.schema.Schema;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ForeignKeyTests {

    private MigrationRepositoryInterface migrationRepository;
    private Connection connection;
    private Migrator migrator;

    @Before
    public void setUp() throws SQLException {
        // Just use a Memory Database, don't save it to disk
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");

        this.migrationRepository = new DatabaseMigrationRepository(connection, "migrations");
        this.migrator = new Migrator(migrationRepository, connection, "io.padarom.migration.tests.migrations2");
    }

}

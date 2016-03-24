package io.padarom.migration.repository;

import io.padarom.migration.Migration;
import io.padarom.migration.schema.Schema;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMigrationRepository implements MigrationRepositoryInterface {
    private Connection connection;

    private String table;

    public DatabaseMigrationRepository(Connection connection, String table) {
        this.connection = connection;
        this.table = table;
    }

    @Override
    public List<String> getRan() {
        return new ArrayList<>();
    }

    @Override
    public List<String> getLast() {
        return null;
    }

    @Override
    public void log(String migration, int batch) {

    }

    @Override
    public void delete(Migration migration) {

    }

    @Override
    public int getNextBatchNumber() {
        return 0;
    }

    @Override
    public void createRepository() {
        try {
            Schema.create(this.table, table -> {
                table.string("migration");
                table.integer("batch");
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean repositoryExists() {
        try {
            return Schema.hasTable(this.table);
        } catch (SQLException e) {
            // Silently discard the error
            return false;
        }
    }

    public void table() throws SQLException {
        Statement statement = this.connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM " + this.table);
    }

    public Connection getConnection() {
        return this.connection;
    }
}

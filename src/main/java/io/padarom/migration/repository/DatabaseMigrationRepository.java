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
    public List<String> getRan() throws SQLException {
        List<String> ranMigrations = new ArrayList<>();

        if (! repositoryExists()) {
            return ranMigrations;
        }

        Statement statement = this.connection.createStatement();
        ResultSet results = statement.executeQuery("select migration from " + this.table + " order by batch asc, migration asc");


        while (results.next()) {
            ranMigrations.add(results.getString("migration"));
        }

        return ranMigrations;
    }

    @Override
    public List<String> getLast() {
        return null;
    }

    @Override
    public void log(String migration, int batch) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into " + table + " values(?, ?)");
            statement.setString(1, migration);
            statement.setInt(2, batch);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Migration migration) {
        try {
            PreparedStatement statement = connection.prepareStatement("delete from " + table + " where migration = ?");
            statement.setString(1, migration.getClass().getSimpleName());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getNextBatchNumber() {
        return getLastBatchNumber() + 1;
    }

    public int getLastBatchNumber() {
        try {
            Statement statement = null;
            statement = this.connection.createStatement();
            ResultSet results = statement.executeQuery("select max(batch) from " + this.table);

            return results.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void createRepository() {
        try {
            Schema.create(this.table, table -> {
                table.string("migration");
                table.integer("batch");
            });
        } catch (Exception e) {
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

    public Connection getConnection() {
        return this.connection;
    }
}

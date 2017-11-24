package io.padarom.migration.repository;

import io.padarom.migration.Migration;
import io.padarom.migration.MigrationInterface;
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
        try {
            Statement statement = this.connection.createStatement();
            ResultSet results = statement.executeQuery("select migration from " + this.table + " order by batch asc, migration asc");

            return createMigrationList(results);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getMigrations(int step) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet results = statement.executeQuery("select migration from " + this.table + " where batch >= 1 order by batch desc, priority desc limit " + step);

            return createMigrationList(results);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getLast() {
        try {
            PreparedStatement statement = this.connection.prepareStatement("select migration from " + this.table + " where batch = ? order by priority desc");
            statement.setInt(1, getLastBatchNumber());
            ResultSet results = statement.executeQuery();

            return createMigrationList(results);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Returns a list of migrations1 from the provided result set
     *
     * @param results List of migrations1 as returned by a query
     * @return
     * @throws SQLException
     */
    protected List<String> createMigrationList(ResultSet results) throws SQLException {
        List<String> migrations = new ArrayList<>();

        while (results.next()) {
            migrations.add(results.getString("migration"));
        }

        return migrations;
    }

    @Override
    public void log(String migration, int batch, int priority) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into " + table + " values(?, ?, ?)");
            statement.setString(1, migration);
            statement.setInt(2, batch);
            statement.setInt(3, priority);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(MigrationInterface migration) {
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
                table.integer("priority");
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

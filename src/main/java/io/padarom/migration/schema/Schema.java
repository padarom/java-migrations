package io.padarom.migration.schema;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public final class Schema {
    private static Connection connection = null;

    protected Schema() {}

    public static void table(String table, Consumer<Blueprint> lambda) {

    }

    public static boolean hasTable(String table) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, "%", null);

        while (resultSet.next()) {
            if (resultSet.getString(3).equals(table)) {
                return true;
            }
        }

        return false;
    }

    public static void create(String table, Consumer<Blueprint> lambda) throws Exception {
        Blueprint blueprint = Schema.createBlueprint(table);

        blueprint.create();
        lambda.accept(blueprint);

        Schema.build(blueprint);
    }

    public static void drop(String table) {

    }

    public static void dropIfExists(String table) {

    }

    public static void rename(String from, String to) {

    }

    private static void build(Blueprint blueprint) throws Exception {
        blueprint.build(Schema.connection); // Connection übergeben
    }

    private static Blueprint createBlueprint(String table, Consumer<Blueprint> lambda) {
        return new Blueprint(table, lambda);
    }

    private static Blueprint createBlueprint(String table) {
        return new Blueprint(table, null);
    }

    public static void setConnection(Connection connection) {
        Schema.connection = connection;
    }

    public static Connection getConnection() {
        return Schema.connection;
    }
}

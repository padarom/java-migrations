package io.padarom.migration.schema.grammars;

import io.padarom.migration.schema.Blueprint;
import io.padarom.migration.schema.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLiteGrammar {
    public static String compileCreate(Blueprint blueprint, Map<String, String> command) {
        String columns = String.join(", ", getColumns(blueprint));

        String sql = blueprint.temporary ? "create temporary" : "create";
        sql += " table " + blueprint.table + " (" + columns;

        return sql + ")";
    }

    public static List<String> getColumns(Blueprint blueprint) {
        List<String> columns = new ArrayList<>();

        for (Column column : blueprint.columns) {
            columns.add(column.getName() + " " + getType(column));
        }

        return columns;
    }

    public static String getType(Column column) {
        String type = getTypeName(column);

        if (column.hasAttribute("length")) {
            type += "(" + column.getAttribute("length") + ")";
        }

        if (column.hasAttribute("autoIncrement") && column.getAttribute("autoIncrement").equals("true")) {
            type += " primary key";
        }

        if (! column.hasAttribute("nullable")) {
            type += " not null";
        } else {
            type += " null";
        }

        if (column.hasAttribute("default")) {
            type += " default " + getDefault(column);
        }

        return type;
    }

    public static String getDefault(Column column) {
        return "'" + column.getAttribute("default") + "'";
    }

    public static String getTypeName(Column column) {
        switch (column.getType().toLowerCase()) {
            case "string":
            case "char":
            case "uuid":
                return "varchar";
            case "enum":
                return "varchar(255)";
            case "binary":
                return "blob";
            case "timestamp":
            case "time":
                return "datetime";
            case "date":
                return "date";
            case "text":
            case "mediumtext":
            case "longtext":
            case "json":
                return "text";
            case "integer":
            case "biginteger":
            case "mediuminteger":
            case "tinyinteger":
            case "smallinteger":
                return "integer";
            case "float":
            case "double":
                return "float";
            case "decimal":
                return "numeric";
            case "boolean":
                return "tinyint(1)";
        }

        return column.getType().toLowerCase();
    }
}

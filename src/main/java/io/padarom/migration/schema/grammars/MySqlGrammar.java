package io.padarom.migration.schema.grammars;

import io.padarom.migration.schema.Blueprint;
import io.padarom.migration.schema.Column;
import io.padarom.migration.schema.grammars.type.MySqlTypeResolver;
import io.padarom.migration.schema.grammars.type.TypeResolverInterface;

import java.util.Map;

public class MySqlGrammar extends Grammar {
    public String compileCreate(Blueprint blueprint, Map<String, String> command) {
        String columns = String.join(", ", getColumns(blueprint));

        String sql = blueprint.temporary ? "create temporary" : "create";
        sql += " table " + wrap(blueprint.table) + " (" + columns;

        return sql + ")";
    }

    public String getType(Column column) {
        String type = this.getTypeName(column);

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

    public String getDefault(Column column) {
        return "'" + column.getAttribute("default") + "'";
    }

    public TypeResolverInterface getTypeResolver() {
        return new MySqlTypeResolver();
    }
}

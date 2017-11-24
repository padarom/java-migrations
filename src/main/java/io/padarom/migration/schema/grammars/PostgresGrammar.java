package io.padarom.migration.schema.grammars;

import io.padarom.migration.schema.Blueprint;
import io.padarom.migration.schema.Column;
import io.padarom.migration.schema.grammars.type.PostgreSQLTypeResolver;
import io.padarom.migration.schema.grammars.type.TypeResolverInterface;

import java.util.Map;

public class PostgresGrammar extends Grammar {
    public String getType(Column column) {
        String type = this.getTypeName(column);

        return type;
    }

    public TypeResolverInterface getTypeResolver() {
        return new PostgreSQLTypeResolver();
    }

    @Override
    public String compileCreate(Blueprint blueprint, Map<String, String> command) {
        String columns = String.join(", ", getColumns(blueprint));

        String sql = blueprint.temporary ? "create temporary" : "create";
        sql += " table " + wrap(blueprint.table) + " (" + columns;

        return sql + ")";
    }
}

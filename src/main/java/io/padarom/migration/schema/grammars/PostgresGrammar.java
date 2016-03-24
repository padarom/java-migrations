package io.padarom.migration.schema.grammars;

import io.padarom.migration.schema.Column;
import io.padarom.migration.schema.grammars.type.SQLiteTypeResolver;
import io.padarom.migration.schema.grammars.type.TypeResolverInterface;

public class PostgresGrammar extends Grammar {
    public String getType(Column column) {
        String type = this.getTypeName(column);

        return type;
    }

    public TypeResolverInterface getTypeResolver() {
        return new SQLiteTypeResolver();
    }
}

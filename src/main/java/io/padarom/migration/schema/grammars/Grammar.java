package io.padarom.migration.schema.grammars;

import io.padarom.migration.schema.Blueprint;
import io.padarom.migration.schema.Column;
import io.padarom.migration.schema.grammars.type.TypeResolverInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class Grammar {
    public abstract TypeResolverInterface getTypeResolver();

    public abstract String getType(Column column);

    public String getTypeName(Column column) {
        try {
            TypeResolverInterface typeResolver = getTypeResolver();
            Method method = typeResolver.getClass().getMethod(column.getType() + "Type");

            Object value = method.invoke(typeResolver);
            return (String) value;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return "";
    }

    public List<String> getColumns(Blueprint blueprint) {
        List<String> columns = new ArrayList<>();

        for (Column column : blueprint.columns) {
            columns.add(wrap(column.getName()) + " " + getType(column));
        }

        return columns;
    }

    public String wrap(String toWrap) {
        return "\"" + toWrap + "\"";
    }
}

package io.padarom.migrations;

import org.reflections.Reflections;

import java.util.Set;

public class Migrator {
    public Migrator() {
        Reflections reflections = new Reflections("io.padarom.minecraft.database.migrations");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Migration.class);
    }
}

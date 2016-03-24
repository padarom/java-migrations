package io.padarom.migrations;

import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Migrator {
    private String basename;

    public Migrator(String basename) {
        this.basename = basename;
    }

    /**
     * Get a list of migrations in a given package sorted by their priority
     *
     * @param packageName The name of the package the migrations are defined in
     * @return Sorted list of migrations
     */
    public List<MigrationInterface> getMigrationList(String packageName) {
        // Get a Set of all classes in the package annotated with "Migration"
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotatedSet = reflections.getTypesAnnotatedWith(Migration.class);

        // Convert the Set to a List
        List<Class<?>> annotatedList = new ArrayList<>();
        annotatedList.addAll(annotatedSet);

        // Sort List by the Migration's priority
        annotatedList.sort((o1, o2) -> {
            Migration m1 = o1.getAnnotation(Migration.class);
            Migration m2 = o2.getAnnotation(Migration.class);

            if (m1.priority() > m2.priority()) {
                return 1;
            }
            else if (m1.priority() < m2.priority()) {
                return -1;
            }

            return 0;
        });

        // Create a list of migrations
        List<MigrationInterface> migrationList = new ArrayList<>();
        for (Class<?> element : annotatedList) {
            try {

                MigrationInterface migration = (MigrationInterface) element.newInstance();
                migrationList.add(migration);

            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return migrationList;
    }
}

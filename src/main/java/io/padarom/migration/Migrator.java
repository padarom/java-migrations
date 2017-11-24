package io.padarom.migration;

import io.padarom.migration.repository.DatabaseMigrationRepository;
import io.padarom.migration.repository.MigrationRepositoryInterface;
import io.padarom.migration.schema.Schema;
import org.reflections.Reflections;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Migrator {
    private MigrationRepositoryInterface migrationRepository;
    private Connection connection;
    private String basename;

    public Migrator(MigrationRepositoryInterface repositoryInterface, Connection connection, String basename) {
        this.migrationRepository = repositoryInterface;
        this.connection = connection;
        this.basename = basename;

        // Prepare the connection for the schema
        Schema.setConnection(connection);
    }

    public Migrator(Connection connection, String basename) {
        this(new DatabaseMigrationRepository(connection, "migrations1"), connection, basename);
    }

    public void runAllMigrations() throws Exception {
        if (! migrationRepository.repositoryExists()) {
            migrationRepository.createRepository();
        }

        int nextBatchNumber = migrationRepository.getNextBatchNumber();
        List<MigrationInterface> migrationList = getMigrationList();
        for (MigrationInterface migration : migrationList) {
            int priority = migration.getClass().getAnnotation(Migration.class).priority();

            migrationRepository.log(migration.getClass().getSimpleName(), nextBatchNumber, priority);
            migration.up();
        }
    }

    /**
     * Get a list of migrations1 in a given package sorted by their priority
     *
     * @param packageName The name of the package the migrations1 are defined in
     * @return Sorted list of migrations1
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

            return m1.priority() > m2.priority() ? 1 : -1;
        });

        // Create a list of migrations1
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

    public List<MigrationInterface> getMigrationList() {
        return getMigrationList(basename);
    }

    public void rollback() {
        rollback(basename);
    }

    public void rollback(int step) {
        rollback(step, basename);
    }

    public void rollback(String packageName) {
        rollbackMigrations(migrationRepository.getLast(), packageName);
    }

    public void rollback(int step, String packageName) {
        rollbackMigrations(migrationRepository.getMigrations(step), packageName);
    }

    protected void rollbackMigrations(List<String> migrations, String packageName)
    {
        for (String migrationName : migrations) {
            try {
                Class migrationClass = Class.forName(packageName + "." + migrationName);
                MigrationInterface migration = (MigrationInterface) migrationClass.newInstance();

                migrationRepository.delete(migration);
                migration.down();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

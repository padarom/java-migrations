package io.padarom.migration.repository;

import io.padarom.migration.Migration;
import io.padarom.migration.MigrationInterface;

import java.sql.SQLException;
import java.util.List;

public interface MigrationRepositoryInterface {
    /**
     * Get the ran migrations1.
     *
     * @return
     */
    List<String> getRan();

    /**
     * Get a list of migrations1.
     *
     * @param step
     * @return
     */
    List<String> getMigrations(int step);

    /**
     * Get the last migration batch.
     *
     * @return
     */
    List<String> getLast();

    /**
     * Log that a migration was run.
     *
     * @param migration
     * @param batch
     * @param priority
     */
    void log(String migration, int batch, int priority);

    /**
     * Remove a migration from the log
     *
     * @param migration
     */
    void delete(MigrationInterface migration);

    /**
     * Get the next migration batch number.
     *
     * @return
     */
    int getNextBatchNumber();

    /**
     * Create the migration repository data store.
     */
    void createRepository();

    /**
     * Determine if the migration repository exists.
     *
     * @return
     */
    boolean repositoryExists();
}

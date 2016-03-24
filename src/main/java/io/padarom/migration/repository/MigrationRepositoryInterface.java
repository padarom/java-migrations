package io.padarom.migration.repository;

import io.padarom.migration.Migration;

import java.sql.SQLException;
import java.util.List;

public interface MigrationRepositoryInterface {
    /**
     * Get the ran migrations.
     *
     * @return
     */
    List<String> getRan() throws SQLException;

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
     */
    void log(String migration, int batch);

    /**
     * Remove a migration from the log
     *
     * @param migration
     */
    void delete(Migration migration);

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

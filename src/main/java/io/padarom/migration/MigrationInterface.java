package io.padarom.migration;

import java.sql.SQLException;

public interface MigrationInterface {
    void up() throws Exception;

    void down() throws SQLException;
}

package io.padarom.migration.schema;

import io.padarom.migration.schema.grammars.Grammar;
import io.padarom.migration.schema.grammars.MySqlGrammar;
import io.padarom.migration.schema.grammars.PostgresGrammar;
import io.padarom.migration.schema.grammars.SQLiteGrammar;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class Blueprint {
    public String table;
    private List<HashMap<String, String>> commands = new ArrayList<>();
    public List<Column> columns = new ArrayList<>();
    public Grammar grammar = null;

    public boolean temporary = false;

    Blueprint(String table, Consumer<Blueprint> lambda) {
        this.table = table;
    }

    void build(Connection connection) throws Exception {
        for (String sql : this.toSql(connection)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
    }

    void setGrammar(Grammar grammar) {
        this.grammar = grammar;
    }

    private Grammar getGrammar(Connection connection) {
        if (this.grammar != null) {
            return this.grammar;
        }

        String className = connection.getClass().getName();

        switch (className) {
            case "org.sqlite.SQLiteConnection":
                return new SQLiteGrammar();
            case "com.mysql.jdbc.Connection":
                return new MySqlGrammar();
            case "org.postgresql.Connection":
                return new PostgresGrammar();
        }

        return null;
    }

    private List<String> toSql(Connection connection) throws Exception {
        List<String> statements = new ArrayList<>();

        SQLiteGrammar grammar = (SQLiteGrammar) getGrammar(connection);
        if (grammar == null) {
            throw new Exception("The used SQL driver is not supported.");
        }

        for (HashMap<String, String> command : this.commands) {
            if (command.get("name").equals("create")) {
                statements.add(grammar.compileCreate(this, command));
            }
        }

        return statements;
    }

    Blueprint create() {
        return this.addCommand("create");
    }

    public Blueprint drop() {
        return this.addCommand("drop");
    }

    public Blueprint dropIfExists() {
        return this.addCommand("dropIfExists");
    }

    public Blueprint dropColumn(String column) {
        HashMap<String, String> map = new HashMap<>();
        map.put("column", column);

        return this.addCommand("dropColumn", map);
    }

    public Blueprint renameColumn(String from, String to) {
        HashMap<String, String> map = new HashMap<>();
        map.put("from", from);
        map.put("to", to);

        return this.addCommand("renameColumn", map);
    }

    public Blueprint rename(String to) {
        HashMap<String, String> map = new HashMap<>();
        map.put("to", to);

        return this.addCommand("rename", map);
    }

    private Blueprint addCommand(String name, HashMap<String, String> parameters) {
        parameters.put("name", name);
        this.commands.add(parameters);

        return this;
    }

    private Blueprint addCommand(String name) {
        return this.addCommand(name, new HashMap<>());
    }

    private Column addColumn(String type, String name, HashMap<String, String> parameters) {
        Column column = new Column(type, name, parameters);

        this.columns.add(column);

        return column;
    }

    private Column addColumn(String type, String name) {
        return this.addColumn(type, name, new HashMap<>());
    }

    // --------------
    // Column types
    // --------------

    public Column character(String column, int length) {
        HashMap<String, String> map = new HashMap<>();
        map.put("length", Integer.toString(length));

        return this.addColumn("char", column, map);
    }

    public Column character(String column) {
        return this.character(column, 255);
    }

    public Column string(String column, int length) {
        HashMap<String, String> map = new HashMap<>();
        map.put("length", Integer.toString(length));

        return this.addColumn("string", column, map);
    }

    public Column string(String column) {
        return this.string(column, 255);
    }

    public Column uuid(String column) {
        return this.addColumn("uuid", column);
    }

    public Column text(String column) {
        return this.addColumn("text", column);
    }

    public Column mediumText(String column) {
        return this.addColumn("mediumText", column);
    }

    public Column longText(String column) {
        return this.addColumn("longText", column);
    }

    public Column bool(String column) {
        return this.addColumn("boolean", column);
    }

    public Column increments(String column) {
        return this.unsignedInteger(column, true);
    }

    public Column smallIncrements(String column) {
        return this.unsignedSmallInteger(column, true);
    }

    public Column mediumIncrements(String column) {
        return this.unsignedMediumInteger(column, true);
    }

    public Column bigIncrements(String column) {
        return this.unsignedBigInteger(column, true);
    }

    public Column tinyInteger(String column, boolean autoIncrement, boolean unsigned) {
        HashMap<String, String> map = new HashMap<>();
        map.put("autoIncrement", Boolean.toString(autoIncrement));
        map.put("unsigned", Boolean.toString(unsigned));

        return this.addColumn("tinyInteger", column, map);
    }

    public Column tinyInteger(String column, boolean autoIncrement) {
        return this.tinyInteger(column, autoIncrement, false);
    }

    public Column tinyInteger(String column) {
        return this.tinyInteger(column, false);
    }

    public Column smallInteger(String column, boolean autoIncrement, boolean unsigned) {
        HashMap<String, String> map = new HashMap<>();
        map.put("autoIncrement", Boolean.toString(autoIncrement));
        map.put("unsigned", Boolean.toString(unsigned));

        return this.addColumn("smallInteger", column, map);
    }

    public Column smallInteger(String column, boolean autoIncrement) {
        return this.smallInteger(column, autoIncrement, false);
    }

    public Column smallInteger(String column) {
        return this.smallInteger(column, false);
    }

    public Column mediumInteger(String column, boolean autoIncrement, boolean unsigned) {
        HashMap<String, String> map = new HashMap<>();
        map.put("autoIncrement", Boolean.toString(autoIncrement));
        map.put("unsigned", Boolean.toString(unsigned));

        return this.addColumn("mediumInteger", column, map);
    }

    public Column mediumInteger(String column, boolean autoIncrement) {
        return this.mediumInteger(column, autoIncrement, false);
    }

    public Column mediumInteger(String column) {
        return this.mediumInteger(column, false);
    }

    public Column integer(String column, boolean autoIncrement, boolean unsigned) {
        HashMap<String, String> map = new HashMap<>();
        map.put("autoIncrement", Boolean.toString(autoIncrement));
        map.put("unsigned", Boolean.toString(unsigned));

        return this.addColumn("integer", column, map);
    }

    public Column integer(String column, boolean autoIncrement) {
        return this.integer(column, autoIncrement, false);
    }

    public Column integer(String column) {
        return this.integer(column, false);
    }

    public Column bigInteger(String column, boolean autoIncrement, boolean unsigned) {
        HashMap<String, String> map = new HashMap<>();
        map.put("autoIncrement", Boolean.toString(autoIncrement));
        map.put("unsigned", Boolean.toString(unsigned));

        return this.addColumn("bigInteger", column, map);
    }

    public Column bigInteger(String column, boolean autoIncrement) {
        return this.bigInteger(column, autoIncrement, false);
    }

    public Column bigInteger(String column) {
        return this.bigInteger(column, false);
    }

    public Column unsignedTinyInteger(String column, boolean autoIncrement) {
        return this.tinyInteger(column, autoIncrement, true);
    }

    public Column unsignedTinyInteger(String column) {
        return this.tinyInteger(column, false, true);
    }

    public Column unsignedSmallInteger(String column, boolean autoIncrement) {
        return this.smallInteger(column, autoIncrement, true);
    }

    public Column unsignedSmallInteger(String column) {
        return this.smallInteger(column, false, true);
    }

    public Column unsignedMediumInteger(String column, boolean autoIncrement) {
        return this.mediumInteger(column, autoIncrement, true);
    }

    public Column unsignedMediumInteger(String column) {
        return this.mediumInteger(column, false, true);
    }

    public Column unsignedInteger(String column, boolean autoIncrement) {
        return this.integer(column, autoIncrement, true);
    }

    public Column unsignedInteger(String column) {
        return this.integer(column, false, true);
    }

    public Column unsignedBigInteger(String column, boolean autoIncrement) {
        return this.bigInteger(column, autoIncrement, true);
    }

    public Column unsignedBigInteger(String column) {
        return this.bigInteger(column, false, true);
    }

    public Column decimal(String column, int total, int places) {
        HashMap<String, String> map = new HashMap<>();
        map.put("total", Integer.toString(total));
        map.put("places", Integer.toString(places));

        return this.addColumn("decimal", column, map);
    }

    public Column decimal(String column) {
        return this.decimal(column, 8, 2);
    }

    public Column enumeration(String column, String[] allowed) {
        HashMap<String, String> map = new HashMap<>();
        map.put("enumeration-length", Integer.toString(allowed.length));

        int index = 0;
        for (String value : allowed) {
            map.put("allowed-" + Integer.toString(index++), value);
        }

        return this.addColumn("enum", column, map);
    }

    public Column json(String column) {
        return this.addColumn("json", column);
    }

    public Column date(String column) {
        return this.addColumn("date", column);
    }

    public Column dateTime(String column) {
        return this.addColumn("dateTime", column);
    }

    public Column time(String column) {
        return this.addColumn("time", column);
    }

    public Column timestamp(String column) {
        return this.addColumn("timestamp", column);
    }

    public void timestamps() {
        this.timestamp("created_at").nullable();

        this.timestamp("updated_at").nullable();
    }

    public Column softDeletes() {
        return this.timestamp("deleted_at").nullable();
    }

    public Column binary(String column) {
        return this.addColumn("binary", column);
    }
}

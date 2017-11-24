package io.padarom.migration.schema;

public class ForeignKeyConstraint {
    public enum ConstraintType {
        DELETE, CASCADE, SET_NULL, NOTHING
    };

    private String column;

    private String foreignColumn;

    private String foreignTable;

    private ConstraintType onDelete = ConstraintType.NOTHING;

    private ConstraintType onUpdate = ConstraintType.NOTHING;

    public ForeignKeyConstraint(String column, String foreignColumn, String foreignTable) {
        this.column = column;
        this.foreignColumn = foreignColumn;
        this.foreignTable = foreignTable;
    }

    public ForeignKeyConstraint onDelete(ConstraintType action) {
        onDelete = action;

        return this;
    }

    public ForeignKeyConstraint onUpdate(ConstraintType action) {
        onUpdate = action;

        return this;
    }

    private ConstraintType getConstraintFromString(String type) throws IllegalArgumentException {
        switch (type) {
            case "update":
                return ConstraintType.CASCADE;
            case "delete":
                return ConstraintType.DELETE;
            case "set null":
                return ConstraintType.SET_NULL;
            case "nothing":
                return ConstraintType.NOTHING;
            default:
                throw new IllegalArgumentException(type + " is not a valid foreign key constraint action.");
        }
    }

    public ForeignKeyConstraint onDelete(String action) throws IllegalArgumentException {
        onDelete = getConstraintFromString(action);

        return this;
    }

    public ForeignKeyConstraint onUpdate(String action) throws IllegalArgumentException {
        onUpdate = getConstraintFromString(action);

        return this;
    }
}

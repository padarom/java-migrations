package io.padarom.migration.schema.grammars.type;

public class PostgreSQLTypeResolver implements TypeResolverInterface {
    @Override
    public String charType() {
        return "character";
    }

    @Override
    public String stringType() {
        return "varchar";
    }

    @Override
    public String uuidType() {
        return "varchar";
    }

    @Override
    public String enumType() {
        return "varchar";
    }

    @Override
    public String binaryType() {
        return "blob";
    }

    @Override
    public String timestampType() {
        return "timestamp";
    }

    @Override
    public String timeType() {
        return "time";
    }

    @Override
    public String dateType() {
        return "date";
    }

    @Override
    public String textType() {
        return "text";
    }

    @Override
    public String mediumTextType() {
        return "text";
    }

    @Override
    public String longTextType() {
        return "text";
    }

    @Override
    public String jsonType() {
        return "text";
    }

    @Override
    public String integerType() {
        return "integer";
    }

    @Override
    public String tinyIntegerType() {
        return "tinyint";
    }

    @Override
    public String smallIntegerType() {
        return "smallint";
    }

    @Override
    public String mediumIntegerType() {
        return "mediumint";
    }

    @Override
    public String bigIntegerType() {
        return "bigint";
    }

    @Override
    public String floatType() {
        return "float";
    }

    @Override
    public String doubleType() {
        return "double";
    }

    @Override
    public String decimalType() {
        return "decimal";
    }

    @Override
    public String booleanType() {
        return "boolean";
    }
}

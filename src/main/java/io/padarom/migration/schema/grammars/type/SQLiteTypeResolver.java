package io.padarom.migration.schema.grammars.type;

public class SQLiteTypeResolver implements TypeResolverInterface {
    @Override
    public String charType() {
        return "string";
    }

    @Override
    public String uuidType() {
        return "string";
    }

    @Override
    public String enumType() {
        return "string";
    }

    @Override
    public String binaryType() {
        return "blob";
    }

    @Override
    public String timestampType() {
        return "datetime";
    }

    @Override
    public String timeType() {
        return "datetime";
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
        return "integer";
    }

    @Override
    public String smallIntegerType() {
        return "integer";
    }

    @Override
    public String mediumIntegerType() {
        return "integer";
    }

    @Override
    public String bigIntegerType() {
        return "integer";
    }

    @Override
    public String floatType() {
        return "float";
    }

    @Override
    public String doubleType() {
        return "float";
    }

    @Override
    public String decimalType() {
        return "numeric";
    }

    @Override
    public String booleanType() {
        return "tinyint(1)";
    }
}

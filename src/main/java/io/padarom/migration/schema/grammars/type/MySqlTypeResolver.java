package io.padarom.migration.schema.grammars.type;

public class MySqlTypeResolver implements TypeResolverInterface {
    @Override
    public String charType() {
        return "char";
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
        return "enum";
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
        return "mediumtext";
    }

    @Override
    public String longTextType() {
        return "longtext";
    }

    @Override
    public String jsonType() {
        return "text";
    }

    @Override
    public String integerType() {
        return "int";
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
        return "tinyint(1)";
    }
}

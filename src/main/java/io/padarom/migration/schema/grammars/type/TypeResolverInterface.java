package io.padarom.migration.schema.grammars.type;

public interface TypeResolverInterface {
    public String charType();
    public String stringType();
    public String uuidType();
    public String enumType();
    public String binaryType();
    public String timestampType();
    public String timeType();
    public String dateType();
    public String textType();
    public String mediumTextType();
    public String longTextType();
    public String jsonType();
    public String integerType();
    public String tinyIntegerType();
    public String smallIntegerType();
    public String mediumIntegerType();
    public String bigIntegerType();
    public String floatType();
    public String doubleType();
    public String decimalType();
    public String booleanType();
}
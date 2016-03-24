package io.padarom.migration.schema;

import java.util.HashMap;

public class Column {
    private String type;
    private String name;

    public HashMap<String, String> attributes;

    Column(String type, String name, HashMap<String, String> attributes) {
        this.type = type;
        this.name = name;

        this.attributes = attributes;
    }

    public Column(String type, String name) {
        this(type, name, new HashMap<>());
    }

    public String getAttribute(String attribute) {
        return this.attributes.get(attribute);
    }

    public boolean hasAttribute(String attribute) {
        return this.attributes.containsKey(attribute);
    }

    public Column nullable() {
        this.attributes.put("nullable", "true");

        return this;
    }

    public Column first() {
        this.attributes.put("first", "true");

        return this;
    }

    public Column after(String column) {
        this.attributes.put("after", column);

        return this;
    }

    public Column unsigned() {
        this.attributes.put("unsigned", "true");

        return this;
    }

    public Column defaultsTo(String value) {
        this.attributes.put("default", value);

        return this;
    }

    public String getType()
    {
        return this.type;
    }

    public String getName()
    {
        return this.name;
    }
}

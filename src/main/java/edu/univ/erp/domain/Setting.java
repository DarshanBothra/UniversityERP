package edu.univ.erp.domain;

public class Setting {
    private String key;
    private String value;

    public Setting(String key, String value){
        this.key = key;
        this.value = value;
    }

    // getters

    public String getKey(){
        return this.key;
    }

    public String getValue() {
        return value;
    }

    // String representation

    @Override
    public String toString(){
        return String.format("Setting {Key: %s, Value: %s}\n", this.getKey(), this.getValue());
    }
}

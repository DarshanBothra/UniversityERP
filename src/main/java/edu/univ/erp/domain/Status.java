package edu.univ.erp.domain;

public enum Status {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String dbValue;

    Status(String dbValue){
        this.dbValue = dbValue;
    }

    public String getDbValue(){
        return dbValue;
    }

    public static Status fromString(String dbValue){
        for (Status s: values()){
            if (s.dbValue.equalsIgnoreCase(dbValue)){
                return s;
            }
        }
        return ACTIVE;
    }

    @Override
    public String toString(){
        return dbValue;
    }
}

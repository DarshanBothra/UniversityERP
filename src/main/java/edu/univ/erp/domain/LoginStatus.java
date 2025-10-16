package edu.univ.erp.domain;

public enum LoginStatus {
    INACTIVE("Inactive"),
    ACTIVE("Active");

    private final String dbValue;

    LoginStatus(String dbValue){
        this.dbValue = dbValue;
    }

    public String getDbValue(){
        return dbValue;
    }

    public static LoginStatus fromString(String dbValue){
        for (LoginStatus l: values()){
            if (l.dbValue.equalsIgnoreCase(dbValue)){
                return l;
            }
        }
        return INACTIVE;
    }

    @Override
    public String toString(){
        return dbValue;
    }
}

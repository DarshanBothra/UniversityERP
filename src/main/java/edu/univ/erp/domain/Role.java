package edu.univ.erp.domain;

public enum Role {
    ADMIN("Admin"),
    INSTRUCTOR("Instructor"),
    STUDENT("Student"),
    UNKNOWN("Unknown");

    private final String dbValue;

    Role(String dbValue){
        this.dbValue = dbValue;
    }

    public String getDbValue(){
        return dbValue;
    }

    public static Role fromString(String dbValue){
        for (Role r: values()){
            if (r.dbValue.equalsIgnoreCase(dbValue)){
                return r;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString(){
        return dbValue;
    }
}

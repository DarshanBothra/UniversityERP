package edu.univ.erp.domain;

public enum Program {
    B_TECH("B.Tech"),
    M_TECH("M.Tech"),
    PHD("PhD"),
    UNKNOWN("Unknown");

    private final String dbValue;

    Program(String dbValue){
        this.dbValue = dbValue;
    }

    public String getDbValue(){
        return dbValue;
    }

    public static Program fromString(String dbValue){
        for (Program p: values()){
            if (p.dbValue.equalsIgnoreCase(dbValue)){
                return p;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString(){
        return dbValue;
    }
}

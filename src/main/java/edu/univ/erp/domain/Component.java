package edu.univ.erp.domain;

public enum Component {
    QUIZ("Quiz"),
    MIDSEM("Midsem"),
    ENDSEM("Endsem"),
    UNKNOWN("Unknown");

    private final String dbValue;

    Component(String dbValue){
        this.dbValue = dbValue;
    }

    public String getDbValue(){
        return dbValue;
    }

    public static Component fromString(String dbValue){
        for (Component c: values()){
            if (c.dbValue.equalsIgnoreCase(dbValue)){
                return c;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString(){
        return dbValue;
    }
}


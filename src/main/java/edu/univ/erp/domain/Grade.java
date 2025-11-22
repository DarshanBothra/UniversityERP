package edu.univ.erp.domain;

public class Grade {
    // fetch from db

    private int gradeId; // auto-increment in table erp_db.grades
    private int enrollmentId; // query erp_db.enrollments with studentId and sectionId (fetch from instructor entering grade from selected section)
    private Component component; // quiz, midsem or endsem
    private double score; // marks scored in component
    private double final_grade; // adjust score according to weightage

    public Grade(int enrollmentId, Component component, double score, double final_grade){
        this.enrollmentId = enrollmentId;
        this.component = component;
        this.score = score;
        this.final_grade = final_grade;
    }

    public static double computeFinalGrade(double score, Component component){
        // assuming the score is out of 100;
        /**
         * Rule for computing final grade:
         * 1. QUIZ - 20
         * 2. MIDSEM - 35
         * 3. ENDSEM - 45
         */

        if (component == Component.QUIZ){
            return score*0.2;
        }
        if (component == Component.MIDSEM){
            return score*0.35;
        }
        if (component == Component.ENDSEM){
            return score*0.45;
        }
        return -1;
    }

    // setters
    public void setGradeId(int gradeId){
        this.gradeId = gradeId;
    }

    // getters
    public int getEnrollmentId(){
        return this.enrollmentId;
    }

    public int getGradeId(){
        return this.gradeId;
    }

    public Component getComponent(){
        return this.component;
    }

    public double getScore(){
        return this.score;
    }

    public double getFinalGrade(){
        return this.final_grade;
    }

    // String Representation

    @Override
    public String toString(){
        return String.format("Grade {GradeId: %d, EnrollmentId: %d, Component: %s, Score: %f, FinalGrade: %f}\n", this.getGradeId(), this.getEnrollmentId(), this.getComponent().getDbValue(), this.getScore(), this.getFinalGrade());
    }
}

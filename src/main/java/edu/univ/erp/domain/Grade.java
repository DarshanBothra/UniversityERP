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

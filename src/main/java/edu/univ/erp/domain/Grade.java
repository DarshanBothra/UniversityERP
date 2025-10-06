package edu.univ.erp.domain;

public class Grade {
    // fetch from db

    private int grade_id; // auto-increment in table erp_db.grades
    private int enrollment_id; // query erp_db.enrollments with student_id and section_id (fetch from instructor entering grade from selected section)
    private Component component; // quiz, midsem or endsem
    private double score; // marks scored in component
    private double final_grade; // adjust score according to weightage

    Grade(int enrollment_id, Component component, double score, double final_grade){
        this.enrollment_id = enrollment_id;
        this.component = component;
        this.score = score;
        this.final_grade = final_grade;
    }

    // setters
    public void setGradeId(int grade_id){
        this.grade_id = grade_id;
    }

    // getters
    public int getEnrollmentId(){
        return this.enrollment_id;
    }

    public int getGradeId(){
        return this.grade_id;
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
        return String.format("Grade {GradeId: %d, EnrollmentId: %d, Component: %s, Score: %f, FinalGrade: %f}\n", this.getGradeId(), this.getEnrollmentId(), this.getComponent().toString(), this.getScore(), this.getFinalGrade());
    }
}

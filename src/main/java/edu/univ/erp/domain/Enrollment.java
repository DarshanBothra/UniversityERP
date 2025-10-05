package edu.univ.erp.domain;

public class Enrollment {
    // fetch from db

    private int enrollment_id; // auto-increment in table erp_db.enrollments
    private int student_id; // query erp_db.students with roll no
    private int section_id; // query erp_db.section_id with course_id
    private String status; // enrolled or dropped, default: enrolled

    Enrollment(int student_id, int section_id, String status){
        this.student_id = student_id;
        this.section_id = section_id;
        this.status = status;
    }

    // setters
    public void setEnrollmentId(int enrollment_id){
        this.enrollment_id = enrollment_id;
    }

    // getters
    public int getEnrollmentId(){
        return this.enrollment_id;
    }

    public int getStudentId(){
        return this.student_id;
    }

    public int getSectionId(){
        return this.section_id;
    }

    public String getStatus(){
        return this.status;
    }

    // String Representation

    @Override
    public String toString(){
        return String.format("Enrollment {EnrollmentId: %d, StudentId: %d, SectionId: %d, Status: %s}\n", this.getEnrollmentId(), this.getStudentId(), this.getSectionId(), this.getStatus());
    }
}

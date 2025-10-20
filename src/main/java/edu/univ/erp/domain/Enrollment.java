package edu.univ.erp.domain;

public class Enrollment {
    // fetch from db

    private int enrollmentId; // auto-increment in table erp_db.enrollments
    private int studentId; // query erp_db.students with roll no
    private int sectionId; // query erp_db.sectionId with courseId
    private Status status; // enrolled or dropped, default: enrolled

    public Enrollment(int studentId, int sectionId, Status status){
        this.studentId = studentId;
        this.sectionId = sectionId;
        this.status = status;
    }

    // setters
    public void setEnrollmentId(int enrollmentId){
        this.enrollmentId = enrollmentId;
    }

    // getters
    public int getEnrollmentId(){
        return this.enrollmentId;
    }

    public int getStudentId(){
        return this.studentId;
    }

    public int getSectionId(){
        return this.sectionId;
    }

    public Status getStatus(){
        return this.status;
    }

    // String Representation

    @Override
    public String toString(){
        return String.format("Enrollment {EnrollmentId: %d, StudentId: %d, SectionId: %d, Status: %s}\n", this.getEnrollmentId(), this.getStudentId(), this.getSectionId(), this.getStatus().getDbValue());
    }
}

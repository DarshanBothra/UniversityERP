package edu.univ.erp.domain;

public class Section {
    // fetch from db

    private int section_id; // auto-increment in erp_db.sections
    private int course_id; // query courses table with course code to fetch course_id
    private int instructor_id; // instructor table with username
    private int capacity;
    private int year;
    private String day_time;
    private String room;
    private String semester;
    private String name;

    Section(int section_id, int course_id, int instructor_id, String name, int capacity, int year, String day_time, String room, String semester){
        this.section_id = section_id;
        this.course_id = course_id;
        this.instructor_id = instructor_id;
        this.capacity = capacity;
        this.year = year;
        this.day_time = day_time;
        this.room = room;
        this.semester = semester;
        this.name = name;
    }

    // getters

    public int getSectionId(){
        return this.section_id;
    }

    public int getCourseId(){
        return this.course_id;
    }

    public int getInstructorId(){
        return this.instructor_id;
    }

    public int getYear(){
        return this.year;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public String getDayTime(){
        return this.day_time;
    }

    public String getRoom(){
        return this.room;
    }

    public String getName(){
        return this.name;
    }

    public String getSemester(){
        return this.semester;
    }

    // String Representation
    @Override
    public String toString(){
        return String.format("Section {SectionId: %d, CourseId: %d, InstructorId: %d, Name: %s, Capacity: %d, Day Time: %s, Room: %s, Semester: %s, Year: %d", this.getSectionId(), this.getCourseId(), this.getInstructorId(), this.getName(), this.getCapacity(), this.getDayTime(), this.getRoom(), this.getSemester(), this.getYear());
    }

}

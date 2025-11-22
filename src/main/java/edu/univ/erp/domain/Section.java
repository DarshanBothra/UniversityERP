package edu.univ.erp.domain;

public class Section {
    // fetch from db

    private int sectionId; // auto-increment in erp_db.sections
    private int courseId; // query courses table with course code to fetch courseId
    private int instructorId; // instructor table with username
    private int capacity; // fetch capacity by counting the number of students enrolled in the section
    private int year;
    private String dayTime;
    private String room;
    private String semester;
    private String name;

    public Section(int courseId, int instructorId, String name, int capacity, int year, String dayTime, String room, String semester){
        this.courseId = courseId;
        this.instructorId = instructorId;
        this.capacity = capacity;
        this.year = year;
        this.dayTime = dayTime;
        this.room = room;
        this.semester = semester;
        this.name = name;
    }

    // setters
    public void setSectionId(int sectionId){
        this.sectionId = sectionId;
    }

    // getters

    public int getSectionId(){
        return this.sectionId;
    }

    public int getCourseId(){
        return this.courseId;
    }

    public int getInstructorId(){
        return this.instructorId;
    }

    public int getYear(){
        return this.year;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public String getDayTime(){
        return this.dayTime;
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

    public boolean isInstructorAssigned(){
        return getInstructorId() != -1;
    }

    // String Representation
    @Override
    public String toString(){
        return String.format("Section {SectionId: %d, CourseId: %d, InstructorId: %d, Name: %s, Capacity: %d, Day Time: %s, Room: %s, Semester: %s, Year: %d}\n", this.getSectionId(), this.getCourseId(), this.getInstructorId(), this.getName(), this.getCapacity(), this.getDayTime(), this.getRoom(), this.getSemester(), this.getYear());
    }

}

package model;

import java.util.Date;

public class Student {

    private String name;
    private float score;
    private Date dateOfBirth;

    public Student() {
    }

    public Student(String name, float score, Date dateOfBirth) {
        this.name = name;
        this.score = score;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}

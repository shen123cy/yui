package com.github.kahlkn.yui.test.entity;

/**
 * Test entity.
 * @author Kahle
 */
public class Student extends Person {

    private static final Integer PASSING_SCORE = 60;

    private Long studentId;
    private String schoolName;
    private Integer nationalLanguageScore;
    private Integer mathematicsScore;
    private Integer englishScore;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getNationalLanguageScore() {
        return nationalLanguageScore;
    }

    public void setNationalLanguageScore(Integer nationalLanguageScore) {
        this.nationalLanguageScore = nationalLanguageScore;
    }

    public Integer getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(Integer mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public Integer getEnglishScore() {
        return englishScore;
    }

    public void setEnglishScore(Integer englishScore) {
        this.englishScore = englishScore;
    }

}

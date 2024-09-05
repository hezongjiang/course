package com.course.miniapp.repo.model;

public class CourseInfo {
    private Long courseId;

    private String weekday;

    private String nTh;

    private String courseInfo;

    private String userId;

    private String weekNum;

    private String place;

    private String teacher;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday == null ? null : weekday.trim();
    }

    public String getnTh() {
        return nTh;
    }

    public void setnTh(String nTh) {
        this.nTh = nTh == null ? null : nTh.trim();
    }

    public String getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(String courseInfo) {
        this.courseInfo = courseInfo == null ? null : courseInfo.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(String weekNum) {
        this.weekNum = weekNum == null ? null : weekNum.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher == null ? null : teacher.trim();
    }
}
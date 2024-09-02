package com.course.miniapp.repo.model;

public class CourseInf {
    private Integer id;

    private String place;

    private String teacher;

    private String xsks;

    private String building;

    private String name;

    private String duration;

    private String dayOfWeek;

    private String className;

    private String classOfDay;

    private String openid;

    private String weekNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getXsks() {
        return xsks;
    }

    public void setXsks(String xsks) {
        this.xsks = xsks == null ? null : xsks.trim();
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building == null ? null : building.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration == null ? null : duration.trim();
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek == null ? null : dayOfWeek.trim();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getClassOfDay() {
        return classOfDay;
    }

    public void setClassOfDay(String classOfDay) {
        this.classOfDay = classOfDay == null ? null : classOfDay.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(String weekNum) {
        this.weekNum = weekNum == null ? null : weekNum.trim();
    }
}
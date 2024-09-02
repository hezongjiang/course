package com.course.miniapp.repo.model;

import javax.validation.constraints.NotBlank;

public class CourseInfo {
    private Long courseId;

    @NotBlank
    private String weekday;

    @NotBlank
    private String nTh;

    @NotBlank
    private String courseInfo;

    @NotBlank
    private String userId;

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
}

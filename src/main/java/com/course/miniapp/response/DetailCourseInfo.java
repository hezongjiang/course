package com.course.miniapp.response;

import com.course.miniapp.request.CourseInfoReq;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DetailCourseInfo extends CourseInfoReq {

    @NotNull
    private Long courseId;
}

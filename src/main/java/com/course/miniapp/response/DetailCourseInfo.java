package com.course.miniapp.response;

import com.course.miniapp.request.CourseInfoReq;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class DetailCourseInfo extends CourseInfoReq {

    @NotNull
    @Min(value = 1)
    private Long courseId;
}

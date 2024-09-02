package com.course.miniapp.response;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CourseInfoResV2 {

    private Long courseId;

    @NotBlank
    private String weekday;

    @NotEmpty
    private List<Integer> nTh;

    @NotBlank
    private String courseInfo;

    @NotBlank
    private String userId;
}

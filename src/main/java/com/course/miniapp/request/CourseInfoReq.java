package com.course.miniapp.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CourseInfoReq {

    @NotBlank
    private String weekday;

    @JsonProperty("nTh")
    @NotEmpty
    private List<Integer> nTh;

    private String courseInfo;

    @NotBlank
    private String userId;
}

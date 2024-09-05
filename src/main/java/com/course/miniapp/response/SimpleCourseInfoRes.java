package com.course.miniapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class SimpleCourseInfoRes {

    @NotBlank
    private String weekday;

    @JsonProperty("nTh")
    @NotEmpty
    private List<Integer> nTh;

    private String courseInfo;
}

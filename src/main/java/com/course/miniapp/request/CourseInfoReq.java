package com.course.miniapp.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private List<Integer> weekNum = IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList());

    private String place;

    private String teacher;
}

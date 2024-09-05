package com.course.miniapp.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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

    @Size(min = 1)
    private List<Integer> weekNum = IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList());

    @Size(min = 1)
    private String place;

    @Size(min = 1)
    private String teacher;
}

package com.course.miniapp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.course.miniapp.repo.model.CourseInfo;
import com.course.miniapp.request.CourseInfoReq;
import com.course.miniapp.response.DetailCourseInfo;
import com.course.miniapp.response.SimpleCourseInfoRes;
import com.course.miniapp.response.TableResponse;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ConvertData {

    ConvertData INSTANCE = Mappers.getMapper(ConvertData.class);

    @Mapping(target = "weekNum", source = "req.weekNum", qualifiedByName = "convert2JsonString")
    @Mapping(target = "nTh", source = "req.NTh", qualifiedByName = "convert2JsonString")
    @Mapping(target = "courseId", expression = "java(RandomStrGen.generateCourseId())")
    CourseInfo convert2CourseInfo(CourseInfoReq req);

    @Mapping(target = "weekNum", source = "req.weekNum", qualifiedByName = "convert2JsonString")
    @Mapping(target = "nTh", source = "req.NTh", qualifiedByName = "convert2JsonString")
    CourseInfo convert2CourseInfo2(DetailCourseInfo req);

    List<CourseInfo> convert2CourseInfoList(List<CourseInfoReq> req);

    List<CourseInfo> convert2CourseInfoList2(List<DetailCourseInfo> req);


    @Mapping(target = "weekNum", source = "info.weekNum", qualifiedByName = "convert2List")
    @Mapping(target = "NTh", source = "info.nTh", qualifiedByName = "convert2List")
    DetailCourseInfo convert2DetailCourseInfo(CourseInfo info);

    List<DetailCourseInfo> convert2DetailCourseInfoList(List<CourseInfo> info);


    default CourseInfo buildCourseInfo(String userId, TableResponse.Body body, TableResponse.Body firstRow) {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setCourseId(RandomStrGen.generateCourseId());
        courseInfo.setUserId(userId);
        courseInfo.setCourseInfo(body.getWords());
        if (firstRow != null && StringUtils.isNotBlank(firstRow.getWords())) {
            courseInfo.setnTh(JSON.toJSONString(RegexUtil.extractNth(firstRow.getWords())));
        } else {
            courseInfo.setnTh(JSON.toJSONString(NumberUtil.fromToEnd(body.getRowStart(), body.getRowDnd())));
        }
        courseInfo.setWeekday(body.getColStart().toString());
        return courseInfo;
    }


    default SimpleCourseInfoRes buildSimpleCourseInfoRes(TableResponse.Body body, TableResponse.Body firstRow) {
        SimpleCourseInfoRes courseInfo = new SimpleCourseInfoRes();
        courseInfo.setCourseInfo(body.getWords());
        if (firstRow != null && StringUtils.isNotBlank(firstRow.getWords())) {
            courseInfo.setNTh(RegexUtil.extractNth(firstRow.getWords()));
        } else {
            courseInfo.setNTh(NumberUtil.fromToEnd(body.getRowStart(), body.getRowDnd()));
        }
        courseInfo.setWeekday(body.getColStart().toString());
        return courseInfo;
    }

    @Named("convert2List")
    default List<Integer> convert2List(final String str) {
        return JSON.parseObject(str, new TypeReference<List<Integer>>() {});
    }

    @Named("convert2JsonString")
    default String convert2JsonString(final List<Integer> list) {
        return JSON.toJSONString(list);
    }
}

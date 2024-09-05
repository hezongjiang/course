package com.course.miniapp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.course.miniapp.repo.CourseInfoMapper;
import com.course.miniapp.repo.model.CourseInfo;

import com.course.miniapp.request.CourseInfoReq;
import com.course.miniapp.response.DetailCourseInfoRes;
import com.course.miniapp.response.SimpleCourseInfoRes;
import com.course.miniapp.response.ResultData;
import com.course.miniapp.response.TableResponse;
import com.course.miniapp.utils.NumberUtil;
import com.course.miniapp.utils.TableIdentifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 获取课程
 */
@RestController
@Slf4j
@RequestMapping("/course")
@Validated
public class CourseController {

//    @Autowired
//    private CourseInfMapper courseInfMapper;

    private static final List<String> weekday = Arrays.asList("星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日", "周一", "周二", "周三", "周四", "周五", "周六", "周日");

    @Autowired
    private CourseInfoMapper courseInfoMapper;

    /**
     * 解析图片课程
     */
    @PostMapping("/pic/analysis")
    public ResultData<List<SimpleCourseInfoRes>> picAnalysis(@NotNull @RequestParam("image") MultipartFile file) throws IOException {

        TableResponse tableResponse = TableIdentifyUtil.uploadImage(file);
        if (tableResponse == null || tableResponse.getTablesResult() == null || tableResponse.getTablesResult().getBody() == null) {
            log.info("课程解析失败");
            return ResultData.fail(0, "课程图片解析失败");
        }
        List<TableResponse.Body> bodyList = tableResponse.getTablesResult().getBody();
        List<SimpleCourseInfoRes> result = new ArrayList<>();
        for (TableResponse.Body body : bodyList) {
            if (weekday.contains(body.getWords())) {
                continue;
            }
            if (body.getWords().contains("第") && body.getWords().contains("节")) {
                continue;
            }
            if (body.getColStart() == 0 || body.getRowStart() == 0) {
                continue;
            }
            SimpleCourseInfoRes courseInfo = new SimpleCourseInfoRes();
            courseInfo.setCourseInfo(body.getWords());
            courseInfo.setNTh(NumberUtil.fromToEnd(body.getRowStart(), body.getRowDnd()));
            courseInfo.setWeekday(body.getColStart().toString());
            result.add(courseInfo);
        }
        return ResultData.success(result);
    }

    /**
     * 创建课程
     */
    @PostMapping("/createV2")
    public ResultData<Boolean> creatCourseInfoByCourse(@Valid @RequestBody @NotEmpty List<CourseInfoReq> courseInfos) {
        List<CourseInfo> result = new ArrayList<>(courseInfos.size());
        for (CourseInfoReq courseInfo : courseInfos) {
            CourseInfo info = new CourseInfo();
            info.setCourseId(System.currentTimeMillis());
            info.setCourseInfo(courseInfo.getCourseInfo());
            info.setUserId(courseInfo.getUserId());
            info.setWeekday(courseInfo.getWeekday());
            info.setnTh(JSON.toJSONString(courseInfo.getNTh()));
            info.setWeekNum(JSON.toJSONString(courseInfo.getWeekNum()));
            info.setTeacher(courseInfo.getTeacher());
            info.setPlace(courseInfo.getPlace());
            result.add(info);
        }
        courseInfoMapper.batchInsert(result);
        return ResultData.success(true);
    }

    /**
     * 根据图片创建课程
     */
    @PostMapping("/create")
    public ResultData<Boolean> creatCourseInfo(@NotNull @RequestParam("image") MultipartFile file, @NotBlank String userId) throws IOException {

        TableResponse tableResponse = TableIdentifyUtil.uploadImage(file);
        if (tableResponse == null || tableResponse.getTablesResult() == null || tableResponse.getTablesResult().getBody() == null) {
            log.info("课程解析失败, userId = {}", userId);
            return null;
        }
        List<TableResponse.Body> bodyList = tableResponse.getTablesResult().getBody();
        List<CourseInfo> result = new ArrayList<>();
        for (TableResponse.Body body : bodyList) {
            if (weekday.contains(body.getWords())) {
                continue;
            }
            if (body.getWords().contains("第") && body.getWords().contains("节")) {
                continue;
            }
            if (body.getColStart() == 0 || body.getRowStart() == 0) {
                continue;
            }
            CourseInfo courseInfo = new CourseInfo();
            courseInfo.setCourseId(System.currentTimeMillis());
            courseInfo.setUserId(userId);
            courseInfo.setCourseInfo(body.getWords());
            courseInfo.setnTh(JSON.toJSONString(NumberUtil.fromToEnd(body.getRowStart(), body.getRowDnd())));
            courseInfo.setWeekday(body.getColStart().toString());
            result.add(courseInfo);
        }
        courseInfoMapper.batchInsert(result);
        return ResultData.success(true);
    }

    /**
     * 获取课程
     */
    @GetMapping("/info")
    public ResultData<List<DetailCourseInfoRes>> courseInfo(@NotBlank String userId) {
        List<CourseInfo> courseInfos = courseInfoMapper.selectByUserId(userId);
        List<DetailCourseInfoRes> result = courseInfos.stream().map(e -> {
            DetailCourseInfoRes course = new DetailCourseInfoRes();
            course.setCourseId(e.getCourseId());
            course.setCourseInfo(e.getCourseInfo());
            course.setWeekday(e.getWeekday());
            course.setNTh(JSON.parseObject(e.getnTh(), new TypeReference<List<Integer>>() {}));
            course.setUserId(e.getUserId());
            return course;
        }).collect(Collectors.toList());
        return ResultData.success(result);
    }

    /**
     * 删除课程
     */
    @DeleteMapping("/delete")
    public ResultData<Boolean> courseDelete(@NotBlank String userId) {
        return ResultData.success(courseInfoMapper.deleteByUserId(userId) > 0);
    }

    

    /**
     * 获取课程
     */
//    @PostMapping("/info")
//    public List<CourseInfoRes> courseInfo(@Validated UserInfoReq req) {
//
//        if (StringUtils.isNotBlank(req.getUserid())) {
//            List<CourseInf> courseInf1 = courseInfMapper.selectByOpenId(req.getUserid());
//            if (courseInf1 != null && !courseInf1.isEmpty()) {
//                return courseInf1.stream().map(e -> {
//                    CourseInfoRes info = new CourseInfoRes();
//                    info.setBuilding(e.getBuilding());
//                    info.setClassName(e.getClassName());
//                    info.setClassOfDay(e.getClassOfDay());
//                    info.setDuration(e.getDuration());
//                    info.setName(e.getName());
//                    info.setTeacher(e.getTeacher());
//                    info.setPlace(e.getPlace());
//                    info.setXsks(e.getXsks());
//                    info.setWeekNum(JSON.parseArray(e.getWeekNum(), Integer.class));
//                    info.setDayOfWeek(e.getDayOfWeek());
//                    info.setClassName(e.getClassName());
//                    return info;
//                }).collect(Collectors.toList());
//            }
//        }
//
//        Map<String, String> body = new HashMap<>();
//        body.put("username", req.getUsername());
//        body.put("password", req.getPassword());
//        String result = OkHttpUtil.post(OkHttpUtil.okHttpClient, "http://localhost:8081/jwlogin", body);
//        log.info("courseInfo: {}", result);
//        List<CourseInfoRes> courseInfoRes = JSON.parseArray(result, CourseInfoRes.class);
//
//        List<CourseInf> collect = courseInfoRes.stream().map(e -> {
//            CourseInf info = new CourseInf();
//            info.setBuilding(e.getBuilding());
//            info.setClassName(e.getClassName());
//            info.setClassOfDay(e.getClassOfDay());
//            info.setDuration(e.getDuration());
//            info.setName(e.getName());
//            info.setTeacher(e.getTeacher());
//            info.setPlace(e.getPlace());
//            info.setXsks(e.getXsks());
//            info.setWeekNum(JSON.toJSONString(e.getWeekNum()));
//            info.setDayOfWeek(e.getDayOfWeek());
//            info.setClassName(e.getClassName());
//            info.setOpenid(req.getUserid());
//            return info;
//        }).collect(Collectors.toList());
//
//        for (CourseInf courseInf : collect) {
//            courseInfMapper.insertSelective(courseInf);
//        }
//
//        return courseInfoRes;
//    }
}

package com.course.miniapp.controller;

import com.course.miniapp.repo.CourseInfoMapper;
import com.course.miniapp.repo.model.CourseInfo;
import com.course.miniapp.request.CourseInfoReq;
import com.course.miniapp.response.DetailCourseInfo;
import com.course.miniapp.response.ResultData;
import com.course.miniapp.response.SimpleCourseInfoRes;
import com.course.miniapp.response.TableResponse;
import com.course.miniapp.utils.ConvertData;
import com.course.miniapp.utils.TableIdentifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import java.util.Objects;

/**
 * 获取课程
 */
@RestController
@Slf4j
@RequestMapping("/course")
@Validated
public class CourseController {
//    UPDATE mysql.user SET authentication_string=PASSWORD('huyali520') WHERE User='root';
//    FLUSH PRIVILEGES;
//    @Autowired
//    private CourseInfMapper courseInfMapper;

    private static final List<String> weekday = Arrays.asList("星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日", "周一", "周二", "周三", "周四", "周五", "周六", "周日");

    @Autowired
    private CourseInfoMapper courseInfoMapper;


    /**
     * 更新课程信息
     */
    @PutMapping("/update")
    public ResultData<Integer> update(@Valid @RequestBody @NotEmpty List<DetailCourseInfo> req) {
        List<CourseInfo> courseInfos = ConvertData.INSTANCE.convert2CourseInfoList2(req);
        int row = courseInfoMapper.updateByCourseIdAndUserId(courseInfos);
        return ResultData.success(row);
    }
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
            if (isTableHeader(body)) {
                continue;
            }
            TableResponse.Body firstRow = getFirstRow(bodyList, body);
            SimpleCourseInfoRes courseInfo = ConvertData.INSTANCE.buildSimpleCourseInfoRes(body, firstRow);
            result.add(courseInfo);
        }
        return ResultData.success(result);
    }

    @Nullable
    private TableResponse.Body getFirstRow(List<TableResponse.Body> bodyList, TableResponse.Body body) {
        return bodyList.stream().filter(e -> Objects.equals(e.getRowStart(), body.getRowStart()) && Objects.equals(e.getRowDnd(), body.getRowDnd()) && e.getColStart() == 0 && e.getColEnd() == 1).findAny().orElse(null);
    }

    /**
     * 是否为表头
     */
    private boolean isTableHeader(TableResponse.Body body) {
        if (StringUtils.isBlank(body.getWords())) {
            return true;
        }
        if (weekday.contains(body.getWords())) {
            return true;
        }
        if (body.getWords().contains("第") && body.getWords().contains("节")) {
            return true;
        }
        if (body.getColStart() == 0 || body.getRowStart() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 创建课程
     */
    @PostMapping("/createV2")
    public ResultData<Boolean> creatCourseInfoByCourse(@Valid @RequestBody @NotEmpty List<CourseInfoReq> courseInfos) {
        List<CourseInfo> result = ConvertData.INSTANCE.convert2CourseInfoList(courseInfos);
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
            if (isTableHeader(body)) {
                continue;
            }
            TableResponse.Body firstRow = getFirstRow(bodyList, body);
            CourseInfo courseInfo = ConvertData.INSTANCE.buildCourseInfo(userId, body, firstRow);
            result.add(courseInfo);
        }
        courseInfoMapper.batchInsert(result);
        return ResultData.success(true);
    }


    /**
     * 获取课程
     */
    @GetMapping("/info")
    public ResultData<List<DetailCourseInfo>> courseInfo(@NotBlank String userId) {
        List<CourseInfo> courseInfos = courseInfoMapper.selectByUserId(userId);
        List<DetailCourseInfo> result = ConvertData.INSTANCE.convert2DetailCourseInfoList(courseInfos);
        return ResultData.success(result);
    }

    /**
     * 删除课程
     */
    @DeleteMapping("/delete")
    public ResultData<Integer> courseDelete(@NotBlank String userId, Long courseId) {
        return ResultData.success(courseInfoMapper.deleteByUserIdAndCourseId(userId, courseId));
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

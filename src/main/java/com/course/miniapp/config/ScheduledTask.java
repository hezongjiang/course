package com.course.miniapp.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.course.miniapp.repo.CourseInfoMapper;
import com.course.miniapp.repo.model.CourseInfo;
import com.course.miniapp.utils.TimeUtil;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class ScheduledTask {

    private final CourseInfoMapper courseInfoMapper;

    private final WxMaService wxMaService;
    public static void main(String[] args) {

    }
    /**
     *     定义该任务每隔十分钟执行一次
     *     2024-09-08 16:00:00
     *     2024-09-08 16:10:00
     *     2024-09-08 16:20:00
     *     2024-09-08 16:30:00
     *     2024-09-08 16:40:00
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void scheduledTask() {
        ImmutableMap<Object, Object> of = ImmutableMap.of();
        log.info("定时任务开始执行，时间：" + System.currentTimeMillis());
        long courseId = 1;
        List<CourseInfo> courseInfos = courseInfoMapper.batchSelectByCourseId(courseId);
        if (courseInfos == null || courseInfos.isEmpty()) {
            return;
        }
        // 现在是周几
        int week = TimeUtil.dateOfWeek();
        // 现在几点
        int currentHour = TimeUtil.currentHour();
        for (CourseInfo courseInfo : courseInfos) {
            List<Integer> nTh = JSON.parseObject(courseInfo.getnTh(), new TypeReference<List<Integer>>() {});
//            if () {
//
//            }
        }

//        final WxMaSubscribeMessage msg = WxMaSubscribeMessage
//                .builder()
//                .templateId("HL26ZZIWcoIH_URnv_wefBHFTAssIycBrvTr_OcxOI8")
//                .toUser(session.getOpenid())
//                .data(Arrays.asList(
//                    new WxMaSubscribeMessage.MsgData("thing2", "上课提醒"),
//                    new WxMaSubscribeMessage.MsgData("date4", "2024年10月1日 15:01"),
//                    new WxMaSubscribeMessage.MsgData("thing5", "马克思思想"),
//                    new WxMaSubscribeMessage.MsgData("thing10", "博学楼")))
//                .build();
//            wxMaService.getMsgService().sendSubscribeMsg(msg);
    }
}

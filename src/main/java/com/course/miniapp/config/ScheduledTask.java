package com.course.miniapp.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import com.course.miniapp.utils.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
@AllArgsConstructor
public class ScheduledTask {

    private final WxMaService wxMaService;

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
        log.info("定时任务开始执行，时间：" + System.currentTimeMillis());
        // 执行业务代码

        // 现在是周几
        int week = TimeUtil.dateOfWeek();
        // 现在几点
        int currentHour = TimeUtil.currentHour();

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

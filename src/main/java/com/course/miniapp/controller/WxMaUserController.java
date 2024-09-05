package com.course.miniapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.course.miniapp.repo.UserInfoMapper;
import com.course.miniapp.repo.model.UserInfo;
import com.course.miniapp.response.ResultData;
import com.course.miniapp.utils.JsonUtils;
import com.course.miniapp.utils.RandomStrGen;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 微信小程序用户接口
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/wx/user/{appid}")
public class WxMaUserController {
    private final WxMaService wxMaService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 登陆接口
     */
    @GetMapping("/login")
    public ResultData<String> login(@PathVariable String appid, String code) {
        if (StringUtils.isBlank(code)) {
            return ResultData.fail(0, "empty code");
        }

//        if (!wxMaService.switchover(appid)) {
//            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
//        }

        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            log.info(session.getSessionKey());
            log.info(session.getOpenid());

            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(session.getOpenid());
            if (userInfo != null && StringUtils.isNotBlank(userInfo.getUserid())) {
                return ResultData.success(userInfo.getUserid());
            }

            userInfo = new UserInfo();
            userInfo.setOpenid(session.getOpenid());
            userInfo.setUserid(RandomStrGen.generateUserId());
            userInfoMapper.insertSelective(userInfo);
            return ResultData.success(userInfo.getUserid());

//            final WxMaSubscribeMessage msg = WxMaSubscribeMessage
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

        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return ResultData.fail(0, e.toString());
        } finally {
            WxMaConfigHolder.remove();//清理ThreadLocal
        }
    }

    /**
     * 获取用户信息接口
     */
    @GetMapping("/info")
    public String info(@PathVariable String appid, String sessionKey,
                       String signature, String rawData, String encryptedData, String iv) {
//        if (!wxMaService.switchover(appid)) {
//            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
//        }

        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            WxMaConfigHolder.remove();//清理ThreadLocal
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        WxMaConfigHolder.remove();//清理ThreadLocal
        return JsonUtils.toJson(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public String phone(@PathVariable String appid, String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {
//        if (!wxMaService.switchover(appid)) {
//            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
//        }

        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            WxMaConfigHolder.remove();//清理ThreadLocal
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        WxMaConfigHolder.remove();//清理ThreadLocal
        return JsonUtils.toJson(phoneNoInfo);
    }

}

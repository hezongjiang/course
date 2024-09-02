package com.course.miniapp.utils;

import com.alibaba.fastjson.JSON;
import com.course.miniapp.response.AccessToken;
import com.course.miniapp.response.TableResponse;
import okhttp3.Headers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;

public class TableIdentifyUtil {
    public static final String API_KEY = "LEtIiUrV4bseNVzGSVEokhOM";
    public static final String SECRET_KEY = "xI6tZu1h5C6kwtxIT2A1X1GBm8ZUi5G8";

    public static TableResponse uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        // 转换图片为字节数组
        byte[] bytes = file.getBytes();
        String table = table(bytes);
        if (StringUtils.isBlank(table)) {
            return null;
        }

        return JSON.parseObject(table, TableResponse.class);
    }

    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     */
    static String getAccessToken() {
        Headers headers = new Headers.Builder().add("Content-Type", "application/json").build();
        String result = OkHttpUtil.post(OkHttpUtil.okHttpClient, "https://aip.baidubce.com/oauth/2.0/token?client_id=" + API_KEY + "&client_secret=" + SECRET_KEY + "&grant_type=client_credentials", headers);
        AccessToken accessToken = JSON.parseObject(result, AccessToken.class);
        return accessToken.getAccessToken();
    }

    public static String table(byte[] imgData) {
        // 请求url
        try {
            String encode = URLEncoder.encode(Base64Util.encode(imgData), "UTF-8");
            String param = "image=" + encode;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAccessToken();
            if (accessToken == null) {
                return "fail";
            }
            String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/table?client_id=" + API_KEY + "&client_secret=" + SECRET_KEY + "&access_token=" + accessToken;
            return HttpUtil.post(url, accessToken, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

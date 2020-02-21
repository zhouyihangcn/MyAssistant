package com.zyh.wx.tencent.youtu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.zyh.wx.mp.utils.JsonUtils;


@Data
@ConfigurationProperties(prefix = "wx.youtu")
public class YoutuProperties {

        private String appId;

        private String secretId;

        private String secretKey;

        private String userQQ;

	@Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}

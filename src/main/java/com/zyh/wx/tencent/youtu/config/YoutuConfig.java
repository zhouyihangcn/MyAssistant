//package com.zyh.wx.tencent.youtu.config;
//
//import java.util.List;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import com.zyh.wx.tencent.youtu.config.YoutuProperties.YtConfig;
//import lombok.AllArgsConstructor;
//
//@AllArgsConstructor
//@Configuration
//@EnableConfigurationProperties(YoutuProperties.class)
//public class YoutuConfig {
//	private final YoutuProperties properties;
//
//    @Bean
//    public List<YtConfig> getConfig() {
//        final List<YoutuProperties.YtConfig> configs = this.properties.getConfigs();
//        if (configs == null) {
//            throw new RuntimeException("添加下相关配置，注意别配错了！ - Youtu properties.");
//        }
//        return configs;
//    }
//
//}

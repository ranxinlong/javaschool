package com.rxl.weixinmap.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName WXProperties
 * @Author chenghao
 * @Date 2020/1/10 15:44
 **/
@Data
@Component
@ConfigurationProperties(prefix = "wx")
public class WxMpProperties {

    /**
     * 公众号appId
     */
    private String appId;

    /**
     * 公众号appSecret
     */
    private String secret;

    /**
     * 公众号token
     */
    private String token;

    /**
     * 公众号aesKey
     */
    private String aesKey;
}
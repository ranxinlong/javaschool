package com.rxl.weixinmap.service;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Component;

/**
 * ClassName: WxMsgPush
 * Description: WxMsgPush service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/17
 */
@Slf4j
@Component
public class WxMsgPush {
    /**
     * 微信公众号API的Service
     */
    private final WxMpService wxMpService;

    /**
     * 构造注入
     */
    WxMsgPush(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }


    /**
     * 发送微信模板信息
     *
     * @param openId 接受者openId
     * @return 是否推送成功
     */
    public Boolean SendWxMsg(String openId) {
        // 发送模板消息接口
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                // 接收者openid
                .toUser(openId)
                // 模板id
                .templateId("KcXxI-8OhDmL9VjSYi_H5UWgHR499DjgshKNS1LiH5c")
                // 模板跳转链接
                .url("http://www.baidu.com")
                .build();
        // 添加模板数据
        templateMessage.addData(new WxMpTemplateData("first", "我来看看你是不是有问题", "#FF00FF"))
                .addData(new WxMpTemplateData("performance", "我发现你多少有点问题", "#A9A9A9"))
                .addData(new WxMpTemplateData("time", "2021-03-17 17:05:55", "#FF00FF"))
                .addData(new WxMpTemplateData("remark", "你是真的有问题", "#000000"));
        String msgId = null;
        try {
            // 发送模板消息
            msgId = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        log.warn("·==++--·推送微信模板信息：{}·--++==·", msgId != null ? "成功" : "失败");
        return msgId != null;
    }
}

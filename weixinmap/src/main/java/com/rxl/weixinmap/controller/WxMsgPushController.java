package com.rxl.weixinmap.controller;

import com.rxl.weixinmap.service.WxMsgPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: WxMsgPushController
 * Description: WxMsgPushController service impl
 *
 * @author ranxinlong@cirdb.cn
 * @version 1.0.0
 * @date 2021/03/17
 */
@RestController
@RequestMapping("/wxmsg")
public class WxMsgPushController {

    @Autowired
    private WxMsgPush wxMsgPush;

    @GetMapping("/sendWxInfo")
    public void sendWxInfo(String openId) {
        // 执行发送
        Boolean aBoolean = wxMsgPush.SendWxMsg(openId);
        System.out.println(aBoolean);
    }
}

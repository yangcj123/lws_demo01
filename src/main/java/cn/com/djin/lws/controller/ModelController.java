package cn.com.djin.lws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  页面跳转控制器
 */
@Controller
@RequestMapping("/model")
public class ModelController {

    @RequestMapping("/loginUI")
    public String loginUI(){
        return "login";
    }

    @RequestMapping("/toLayout")
    public String toLayout(){
        return "layout";
    }

    //去到聊天记录页面
    @RequestMapping("/toChatlog")
    public String toChatlog(){
        System.out.println("toChatlog..");
        return "html/chatlog";
    }
}

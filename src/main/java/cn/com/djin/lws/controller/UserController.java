package cn.com.djin.lws.controller;

import cn.com.djin.lws.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 *  用户控制器
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User>{

    //登录
    @RequestMapping("/login")
    public @ResponseBody String login(User user, HttpSession session){
        try {
                User htUser = baseService.findObjectByPramas(user);
                if(htUser!=null){
                    session.setAttribute("htUser", htUser);
                    return SUCCESS;
                }else{
                    return FAIL;
                }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }

    }

    /**getMembers
     * @功能:layim页面初始化
     * @作者:djin
     * @时间:2017年12月24日
     * @param htUser登录用户
     * @return  用户数据
     */
    @RequestMapping("/init")
    @ResponseBody
    public Map<String,Object> init(HttpSession session) {
        User htUser = (User) session.getAttribute("htUser");
        try {
            return userService.initFriend(htUser);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }

    }
}

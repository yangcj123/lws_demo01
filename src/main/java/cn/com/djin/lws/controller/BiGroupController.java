package cn.com.djin.lws.controller;

import cn.com.djin.lws.entity.BiGroup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 *   群组的控制器
 */
@Controller
@RequestMapping("/biGroup")
public class BiGroupController extends BaseController<BiGroup> {

    /**
     * @功能:获取群组的组员
     * @作者:djin
     * @时间:2017年12月24日
     * @param htUser登录用户
     * @return  用户数据
     */
    @RequestMapping("/getMembers")
    @ResponseBody
    public Map<String,Object> getMembers(Integer id) {
        System.out.println("id="+id);
        try {
            return groupService.getMembers(id);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }
}

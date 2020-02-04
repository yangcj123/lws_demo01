package cn.com.djin.lws.service.impl;

import cn.com.djin.lws.entity.BiGroup;
import cn.com.djin.lws.entity.Group;
import cn.com.djin.lws.entity.User;
import cn.com.djin.lws.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author djin
 *   用户业务层对象
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	//初始化好友信息
	@Override
	public Map<String, Object> initFriend(User user) throws Exception{
		List<Group> groups = groupMapper.queryManyByOtherId(user.getId());
		List<BiGroup> bigroups = biGroupMapper.queryManyByOtherId(user.getId());
		List<Map<String, Object>> friendList = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		for (int i = 0; i < groups.size(); i++) {
			Map<String, Object> friendMap = new HashMap<String, Object>();
			friendMap.put("groupname", groups.get(i).getGname());
			friendMap.put("id",groups.get(i).getGid());
			friendMap.put("list", userMapper.queryManyByUidAndGid(user.getId(), groups.get(i).getGid()));
			friendList.add(friendMap);
		}
		data.put("mine", user);
		data.put("friend", friendList);
		data.put("group", bigroups);
		map.put("code", 0);
		map.put("msg", "数据加载成功");
		map.put("data", data);
		return map;
	}



}

package cn.com.djin.lws.service.impl;

import cn.com.djin.lws.entity.BiGroup;
import cn.com.djin.lws.service.BiGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author djin
 *   群业务层实现类
 */
@Service
@Transactional
public class BiGroupServiceImpl extends BaseServiceImpl<BiGroup> implements BiGroupService {

	//加载群组组员信息
	@Override
	public Map<String, Object> getMembers(Integer id) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", userMapper.queryManyByOtherId(id));
		map.put("code", 0);
		map.put("msg", "数据加载成功");
		map.put("data", data);
		return map;
	}
}

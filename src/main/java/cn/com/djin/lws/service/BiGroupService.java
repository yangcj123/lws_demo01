package cn.com.djin.lws.service;

import cn.com.djin.lws.entity.BiGroup;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author djin
 *   群业务层接口
 */
public interface BiGroupService extends BaseService<BiGroup> {

	//加载群组组员信息
	Map<String,Object> getMembers(Integer id) throws Exception;
}

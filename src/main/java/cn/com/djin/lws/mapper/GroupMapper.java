package cn.com.djin.lws.mapper;

import cn.com.djin.lws.entity.Group;
import java.util.List;

/**
 * 
 * @author 群组Mapper代理对象
 *
 */
public interface GroupMapper extends BaseMapper<Group> {

	//根据uid查询多个结果 
	List<Group> queryManyByUid(Integer uid) throws Exception;
	
}

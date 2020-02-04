package cn.com.djin.lws.mapper;

import cn.com.djin.lws.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 
 * @author djin
 *   用户Mapper对象
 */
public interface UserMapper extends BaseMapper<User> {

	//根据uid和gid查询用户
	List<User> queryManyByUidAndGid(@Param("uid") Integer uid, @Param("gid") Integer gid) throws Exception;
	
	//根据uid查询其好友id
	List<Integer> queryFuidByUid(Integer uid) throws Exception;
	
	//根据uid查询其好友：状态为1
	List<User> queryFriendByUid1(Integer uid) throws Exception;
	
	//根据uid查询其好友：状态为0
	List<User> queryFriendByUid0(Integer uid) throws Exception;
	
	//根据uid查询其好友：需要处理的好友请求
	List<User> queryFriendByUidApply(Integer uid) throws Exception;
	
	//根据uid查询其同一群的所有成员信息
	List<User> queryBigGroupUsersByUid(Integer uid) throws Exception;
	
}

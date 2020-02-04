package cn.com.djin.lws.service.impl;

import cn.com.djin.lws.mapper.BaseMapper;
import cn.com.djin.lws.mapper.BiGroupMapper;
import cn.com.djin.lws.mapper.GroupMapper;
import cn.com.djin.lws.mapper.UserMapper;
import cn.com.djin.lws.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

public class BaseServiceImpl<T> implements BaseService<T> {
	
	protected Map<String, Object> map = new HashMap<String, Object>();
	
	protected List<T> list = new ArrayList<T>();
	
	//操作成功的常量
	protected static final String SUCCESS = "success";
	//操作成功的常量
	protected static final String FAIL = "fail";
	
	//基础Mapper代理对象
	@Autowired
	protected BaseMapper<T> baseMapper;

	//用户mapper
	@Autowired
	protected UserMapper userMapper;

	//好友分组的Mapper
	@Autowired
	protected GroupMapper groupMapper;

	//群组Mapper
	@Autowired
	protected BiGroupMapper biGroupMapper;

    //添加
	@Override
	public String save(T t) throws Exception {
		if(baseMapper.insert(t)>0){
			return SUCCESS;
		}else{
			return FAIL;
		}
	}
	
	//批量添加
	@Override
	public String saveBatch(List<T> ts) throws Exception {
		if(baseMapper.insBatch(ts)>0){
			return SUCCESS;
		}else{
			return FAIL;
		}
	}

	//修改
	@Override
	public String upd(T t) throws Exception {
		if(baseMapper.update(t)>0){
			return SUCCESS;
		}else{
			return FAIL;
		}
	}
	
	//根据id删除
	@Override
	public String removeById(String id) throws Exception {
		if(baseMapper.deleteById(id)>0){
			return SUCCESS;
		}else{
			return FAIL;
		}
		
	}

	//根据条件单个删除
	@Override
	public String remove(T t) throws Exception {
		if(baseMapper.delete(t)>0){
			return SUCCESS;
		}else{
			return FAIL;
		}
	}

	//批量删除
	@Override
	public String removeBatch(String[] ids) throws Exception {
		if(baseMapper.deleteBatch(ids)>0){
			return SUCCESS;
		}else{
			return FAIL;
		}
	}
	
	//根据条件查询单个结果
	@Override
	public T findObjectByPramas(T t) throws Exception {
		
		return baseMapper.queryObjectByPramas(t);
	}

	//根据条件（无条件）查询多个结果
	@Override
	public List<T> findManyByPramas(T t) throws Exception {
		
		return baseMapper.queryManyByPramas(t);
	}

	//根据条件(无条件)分页查询layui的table
	@Override
	public Map<String, Object> findListByPramas(Integer page, Integer limit, T t) throws Exception {
		map.put("count", baseMapper.queryTotalByPramas(t));
		List<T> list = baseMapper.queryListByPramas((page-1)*limit, limit,t);
		if(list.size()==0&&page>1){
			map.put("data", baseMapper.queryListByPramas((page-2)*limit, limit,t));
		}else{
			map.put("data", list);
		}
		return map;
	}
	
	//获取表的数据记录条数
	@Override
	public Long getTotalByPramas(T t) throws Exception {
		
		return baseMapper.queryTotalByPramas(t);
	}

	//根据条件分页查询，一般分页
	@Override
	public List<T> findPageByPramas(Integer page, Integer limit, T t) throws Exception {
		
		return baseMapper.queryListByPramas((page-1)*limit, limit, t);
	}

	//获取数据页数
	@Override
	public Integer getTotalPageByPramas(Integer pageSize, T t) throws Exception {
		Long totalRecord = baseMapper.queryTotalByPramas(t);
		if(totalRecord%pageSize==0){
			return (int) (totalRecord/pageSize);
		}else{
			return (int) (totalRecord/pageSize + 1);
		}
		
	}

	//查询所有
	@Override
	public List<T> findAll() throws Exception {
		
		return baseMapper.showAll();
	}
	
	//根据id查询单个对象
	@Override
	public T findTById(Integer id) throws Exception {
		
		return baseMapper.queryObjectById(id);
	}


	//轮询查询这两个时间之内的多个订单
	@Override
	public Map<String, Object> findManyObjectByDate(Integer page, Integer limit,Date makedate,Date enddate) throws Exception {
		map.put("count", baseMapper.queryTotalByDate(makedate,enddate));
		List<T> list = baseMapper.queryManyObjectByDate((page-1)*limit, limit, makedate, enddate);
		if(list.size()==0&&page>1){
			map.put("data", baseMapper.queryManyObjectByDate((page-2)*limit, limit, makedate, enddate));
		}else{
			map.put("data", list);
		}
		return map;
	}
	
	//获取表的数据记录条数
	@Override
	public Long getTotalByDate(Date makedate,Date enddate) throws Exception {
		
		return baseMapper.queryTotalByDate(makedate,enddate);
	}

	@Override
	public List<T> findManyByPolling(Date oldDate, Date nowDate) throws Exception {
		
		return baseMapper.queryManyByPolling(oldDate, nowDate);
	}

	//根据其他id查询多个结果
	@Override
	public List<T> findManyByOtherId(Integer otherId) throws Exception {
		
		return baseMapper.queryManyByOtherId(otherId);
	}

	
	
	
}

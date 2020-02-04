package cn.com.djin.lws.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BaseService<T> {
	
	//添加
	String save(T t) throws Exception;
	
	//批量添加
	String saveBatch(List<T> ts) throws Exception;
	
	//修改
	String upd(T t) throws Exception;
	
	//根据id删除
	String removeById(String id) throws Exception;
	
	//删除
	String remove(T t) throws Exception;
	
	//批量删除
	String removeBatch(String[] ids) throws Exception;
	
    //根据条件查询单个结果
    T findObjectByPramas(T t) throws Exception;
    
    //根据id查询单个对象
    T findTById(Integer id) throws Exception;
    
    //查询所有
  	List<T> findAll() throws Exception;
    
    //根据条件（无条件）查询多个结果
  	List<T> findManyByPramas(T t) throws Exception;
  	
  	//根据其他id查询多个结果
  	List<T> findManyByOtherId(Integer otherId) throws Exception;
	
	//根据条件(无条件)分页查询layui的table
	Map<String,Object> findListByPramas(Integer page, Integer limit, T t) throws Exception;

	//获取表的数据记录条数
	Long getTotalByPramas(T t) throws Exception;

	//根据条件分页查询，一般分页
	List<T> findPageByPramas(Integer page, Integer limit, T t) throws Exception;

	//获取数据页数
	Integer getTotalPageByPramas(Integer pageSize, T t) throws Exception;

	//查询这两个时间之内的数据
	Map<String,Object> findManyObjectByDate(Integer page, Integer limit, Date makedate, Date enddate) throws Exception;

	//查询此时间之内的数据条数
	Long getTotalByDate(Date makedate, Date enddate) throws Exception;

	//轮询查询数据
	List<T> findManyByPolling(Date makedate, Date enddate) throws Exception;
	
}

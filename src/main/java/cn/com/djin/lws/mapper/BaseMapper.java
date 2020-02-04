package cn.com.djin.lws.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 *  方法名与mybatis 映射文件SQL ID对应
 */
public interface BaseMapper<T> {
	
	//添加
	Integer insert(T t) throws Exception;
	
	//批量添加
	Integer insBatch(List<T> ts) throws Exception;
	
	//修改
	Integer update(@Param("t") T t) throws Exception;

	//根据条件删除
	Integer delete(@Param("t") T t) throws Exception;

	//根据id删除
	Integer deleteById(String id) throws Exception;

    //批量删除
	Integer deleteBatch(Object[] ids) throws Exception;

	//根据条件查询单个结果
	T queryObjectByPramas(@Param("t") T t) throws Exception;

	//根据id查询单个结果
	T queryObjectById(Integer id) throws Exception;

	//根据其他id查询多个结果集
	List<T> queryManyByOtherId(Integer otherId) throws Exception;

	//查询所有
	List<T> showAll() throws Exception;

	//根据条件（无条件）查询多个结果集
	List<T> queryManyByPramas(@Param("t") T t) throws Exception;

	//根据条件（无条件）分页查询
	List<T> queryListByPramas(@Param("currentRecord") Integer currentRecord, @Param("limit") Integer limit, @Param("t") T t) throws Exception;

	//根据条件（无条件）查询数据条数
	Long queryTotalByPramas(@Param("t") T t) throws Exception;

	//查询此时间之内的多个结果集(有分页)
	List<T> queryManyObjectByDate(@Param("currentRecord") Integer currentRecord, @Param("limit") Integer limit, @Param("makedate") Date makedate, @Param("enddate") Date enddate) throws Exception;

	//查询此时间之内的数据条数
	Long queryTotalByDate(@Param("makedate") Date makedate, @Param("enddate") Date enddate) throws Exception;

	//轮询多个结果集(无分页)
	List<T> queryManyByPolling(@Param("oldDate") Date oldDate, @Param("nowDate") Date nowDate) throws Exception;
	
}

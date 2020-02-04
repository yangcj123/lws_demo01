package cn.com.djin.lws.controller;

import cn.com.djin.lws.service.BaseService;
import cn.com.djin.lws.service.BiGroupService;
import cn.com.djin.lws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;

/**
 * 
 * @author djin
 *    基础控制器层
 * @param <T>
 */
public class BaseController<T> {
	
	protected Map<String,Object> map = new HashMap<String,Object>();
	
	protected List<T> list = new ArrayList<T>();
	
	//访问数据成功的常量
	protected static final Integer SUCCESSCODE = 0;
	//访问数据失败的常量
	protected static final Integer FAILCODE = 200;
	//访问数据失败的数据条数
	protected static final Integer FAILCOUNT = 0;
	//访问数据失败时的提示
	protected static final String FAILMSG = "数据访问失败！！！";
	//操作成功的常量
	protected static final String SUCCESS = "success";
	//用户登录类型的常量
	protected static final String JURISDICTION = "jurisdiction";
	//操作失败的常量
	protected static final String FAIL = "fail";
	//操作异常的常量
	protected static final String ERROR = "error";

	//基础业务层对象
	@Autowired
	protected BaseService<T> baseService;

	//用户业务层对象
	@Autowired
	protected UserService userService;

	//群组的业务层对象
	@Autowired
	protected BiGroupService groupService;

	/**
	 * 
	 * @param code  1 成功  0 失败  
	 * @param msg   消息内容
	 * @param count 最大条数
	 * @param data  具体内容
	 * @return
	 */
	public Map<String,Object> putMsgToJsonString(int code,String msg,int count ,Object data){
		map.put("code", code);
		map.put("msg", msg);
		map.put("count", count);
		map.put("data", data);
		return map;
	}
	
	/**
	 * 加载（分页） 根据是否存在条件加载
	 */
	@RequestMapping("/listByPramas")
	@ResponseBody
	public Map<String, Object> listByPramas(Integer page,Integer limit,T t){
		try {
			map = baseService.findListByPramas(page, limit, t);
			map.put("code", SUCCESSCODE);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return putMsgToJsonString(FAILCODE,FAILMSG,FAILCOUNT,null);
		}
	}
	
	//根据其他id查询多个结果（联表查询）
	@RequestMapping("/loadManyOtherId")
	@ResponseBody
	public List<T> loadManyOtherId(Integer otherId){
		try {
			return baseService.findManyByOtherId(otherId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * 根据（无）条件查询多个结果集
	 * 
	 */
	@RequestMapping("/loadManyByPramas")
	@ResponseBody
	public List<T> loadManyByPramas(T t){	
		try {
			return baseService.findManyByPramas(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 
	 * 根据（无）条件查询多个结果集
	 * 
	 */
	@RequestMapping("/loadPageByPramas")
	@ResponseBody
	public List<T> loadPageByPramas(Integer page,Integer limit,T t){	
		try {
			return baseService.findPageByPramas(page,limit,t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 根据条件查询单个结果
	 * 
	 */
	@RequestMapping("/loadObjectByPramas")
	@ResponseBody
	public T loadObjectByPramas(T t){
		try {
			return baseService.findObjectByPramas(t);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	} 
	
	/**
	 * 根据条件查询所有
	 * 
	 */
	@RequestMapping("/loadAll")
	@ResponseBody
	public List<T> loadAll(){
		try {
			return baseService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 根据id查询单个
	 * 
	 */
	@RequestMapping("/loadObjectById")
	@ResponseBody
	public T loadObjectById(Integer id){
		try {
			return baseService.findTById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 根据id条件删除
	 * 
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public String deleteById(String id){
		try {
			return baseService.removeById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 根据条件删除
	 * 
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(T t){
		try {
			return baseService.remove(t);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("/deletes")
	@ResponseBody
	public String deletes(String[] ids){
		try {
			return baseService.removeBatch(ids);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
	}
	
	/**
	 * 添加单个
	 */
	 @RequestMapping("/saveT")
	 @ResponseBody
	 public String saveT(T entity){
		try {
			return baseService.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}	 
	 }
	 
	 /**
	 * 修改
	 */
	 @RequestMapping("/updT")
	 @ResponseBody
	 public String updT(T entity){
		try {
			return baseService.upd(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}	 
	 }
	 
    /**
	* 查询某一时间段内的数据
	*/
	@RequestMapping("/laodListByDate")
	@ResponseBody
	public Map<String, Object> loadListObject(Integer page,Integer limit,Date makedate,Date enddate){
		try {
		    map = baseService.findManyObjectByDate(page, limit, makedate, enddate);
			map.put("code",SUCCESSCODE);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return putMsgToJsonString(FAILCODE,FAILMSG,FAILCOUNT,null);
		}
	}
	
}

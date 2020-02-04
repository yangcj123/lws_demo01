/**
 * 代号:甲骨兽进化 2017：厚溥
 * 文件名：BaseDao.java
 * 创建人：jh
 * 日期：2018年4月12日
 * 修改人：
 * 描述：
 */
package cn.com.djin.lws.entity;

import java.io.Serializable;

/**
 * 用途：业务模块名称
 */
public class PackMsgBTO<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 随便定义，用于在服务端区分消息类型
	 */
	private String emit;
	/**
	 * data 对象
	 */
	private  T data;
	public String getEmit() {
		return emit;
	}
	public void setEmit(String emit) {
		this.emit = emit;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "PackMsgBTO [emit=" + emit + ", data=" + data + "]";
	}
	
	
}

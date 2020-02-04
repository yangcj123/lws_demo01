/**
 * 代号:甲骨兽进化 2017：厚溥
 * 文件名：BaseDao.java
 * 创建人：jh
 * 日期：2018年4月12日
 * 修改人：
 * 描述：
 */
package cn.com.djin.lws.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 用途：接收websocket msg
 */
@JsonInclude(Include.NON_NULL)  //对象转json  过滤null
public class SendMessageBTO<T> {
	private T mine;
	private T to;
	public T getMine() {
		return mine;
	}
	public void setMine(T mine) {
		this.mine = mine;
	}
	public T getTo() {
		return to;
	}
	public void setTo(T to) {
		this.to = to;
	}
}

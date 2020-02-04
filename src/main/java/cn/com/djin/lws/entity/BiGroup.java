package cn.com.djin.lws.entity;

/**
 * 
 * @author djin
 *   群实体类
 */
public class BiGroup extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//群id
	private Integer id;
	//名称
	private String groupname;
	//群组头像
	private String avatar;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	@Override
	public String toString() {
		return "BiGroup [id=" + id + ", groupname=" + groupname + ", avatar=" + avatar + "]";
	}
	
	
	
}

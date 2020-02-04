package cn.com.djin.lws.entity;

/**
 * 
 * @author djin
 *   用户群(暂时群组与用户为一对多关系)
 */
public class Group extends BaseEntity {

	private static final long serialVersionUID = 1L;

	//群组id
	private Integer gid;
	//群组名
	private String gname;
	//群组地址
	private String gloc;
	
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getGloc() {
		return gloc;
	}
	public void setGloc(String gloc) {
		this.gloc = gloc;
	}
	@Override
	public String toString() {
		return "Group [gid=" + gid + ", gname=" + gname + ", gloc=" + gloc + "]";
	}
	
	
}

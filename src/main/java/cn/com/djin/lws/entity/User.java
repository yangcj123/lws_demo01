package cn.com.djin.lws.entity;

/**
 * 
 * @author djin
 *   用户实体类(暂时用户与群组为多对一关系)
 */
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	//用户id
	private Integer id;
	//name
	private String name;
	//用户姓名
	private String username;
	//用户名（登录名）
	private String uname;
	//用户密码（登录密码）
	private String password;
	//用户头像
	private String avatar;
	//是否登录系统:二种情况：未登录/隐身(hide)，已经登录(online)
	private String status;
    //逻辑删除（默认为1，删除后为2）
    private Integer logictodelete;
    //用户签名
    private String sign;
    //好友分类id
    private Integer groupid;
    //
    private Integer groupIndex;
    
    /**
	 * 内容
	 */
	private String content;
	
	/**
	 * 类型
	 */
	private String type;
    
    /**
	 * 是不是自己发送
	 */
	private boolean mine;
	
	/**
	 * 未知
	 */
	private Long historyTime;

	private String groupname;

	private String list;

    public User() {
    }

    public User(Integer id,String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getLogictodelete() {
		return logictodelete;
	}
	public void setLogictodelete(Integer logictodelete) {
		this.logictodelete = logictodelete;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isMine() {
		return mine;
	}
	public void setMine(boolean mine) {
		this.mine = mine;
	}
	public Long getHistoryTime() {
		return historyTime;
	}
	public void setHistoryTime(Long historyTime) {
		this.historyTime = historyTime;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getList() {
		return list;
	}
	public void setList(String list) {
		this.list = list;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getGroupid() {
		return groupid;
	}
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	public Integer getGroupIndex() {
		return groupIndex;
	}
	public void setGroupIndex(Integer groupIndex) {
		this.groupIndex = groupIndex;
	}
	
	
    
	

}

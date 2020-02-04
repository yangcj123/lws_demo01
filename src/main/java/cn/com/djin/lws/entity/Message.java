package cn.com.djin.lws.entity;

public class Message extends BaseEntity{
    
	private static final long serialVersionUID = 1L;

	/**
     * id
     */
    private Integer cid;

    /**
     * 消息来源用户名
     */
    private String username;

    /**
     * 消息来源用户头像
     */
    private String avatar;

    /**
     * 聊天窗口来源类型，从发送消息传递的to里面获取
     */
    private String type;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否我发送的消息，如果为true，则会显示在右方
     */
    private Boolean mine;

    /**
     * 消息的发送者id
     */
    private Integer fromid;

    /**
     * 消息的来源ID
     */
    private Integer id;

    /**
     * 服务端时间戳毫秒数
     */
    private Long timestamp;

    /**
     * 删除标志(0：删除、1：未删除)
     */
    private Integer delFlag;

    /**
     * 状态码 (1：已发送)
     */
    private Integer status;

    /**
     * 接收消息人的id
     */
    private Integer toId;

    /**
     * 获取id
     *
     * @return cid - id
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * 设置id
     *
     * @param cid id
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * 获取消息来源用户名
     *
     * @return username - 消息来源用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置消息来源用户名
     *
     * @param username 消息来源用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取消息来源用户头像
     *
     * @return avatar - 消息来源用户头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置消息来源用户头像
     *
     * @param avatar 消息来源用户头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取聊天窗口来源类型，从发送消息传递的to里面获取
     *
     * @return type - 聊天窗口来源类型，从发送消息传递的to里面获取
     */
    public String getType() {
        return type;
    }

    /**
     * 设置聊天窗口来源类型，从发送消息传递的to里面获取
     *
     * @param type 聊天窗口来源类型，从发送消息传递的to里面获取
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取消息内容
     *
     * @return content - 消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置消息内容
     *
     * @param content 消息内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取是否我发送的消息，如果为true，则会显示在右方
     *
     * @return mine - 是否我发送的消息，如果为true，则会显示在右方
     */
    public Boolean getMine() {
        return mine;
    }

    /**
     * 设置是否我发送的消息，如果为true，则会显示在右方
     *
     * @param mine 是否我发送的消息，如果为true，则会显示在右方
     */
    public void setMine(Boolean mine) {
        this.mine = mine;
    }

    /**
     * 获取消息的发送者id
     *
     * @return fromid - 消息的发送者id
     */
    public Integer getFromid() {
        return fromid;
    }

    /**
     * 设置消息的发送者id
     *
     * @param fromid 消息的发送者id
     */
    public void setFromid(Integer fromid) {
        this.fromid = fromid;
    }

    /**
     * 获取消息的来源ID
     *
     * @return id - 消息的来源ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置消息的来源ID
     *
     * @param id 消息的来源ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取服务端时间戳毫秒数
     *
     * @return timestamp - 服务端时间戳毫秒数
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * 设置服务端时间戳毫秒数
     *
     * @param timestamp 服务端时间戳毫秒数
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 获取删除标志(0：删除、1：未删除)
     *
     * @return del_flag - 删除标志(0：删除、1：未删除)
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标志(0：删除、1：未删除)
     *
     * @param delFlag 删除标志(0：删除、1：未删除)
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取状态码 (1：已发送)
     *
     * @return status - 状态码 (1：已发送)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态码 (1：已发送)
     *
     * @param status 状态码 (1：已发送)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取接收消息人的id
     *
     * @return to_id - 接收消息人的id
     */
    public Integer getToId() {
        return toId;
    }

    /**
     * 设置接收消息人的id
     *
     * @param toId 接收消息人的id
     */
    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public Message() {
    }

    public Message(Integer cid, Integer status) {
        this.cid = cid;
        this.status = status;
    }

    public Message(Integer delFlag, Integer status, Integer toId) {
        this.delFlag = delFlag;
        this.status = status;
        this.toId = toId;
    }

    @Override
	public String toString() {
		return "Message [cid=" + cid + ", username=" + username + ", avatar=" + avatar + ", type=" + type + ", content="
				+ content + ", mine=" + mine + ", fromid=" + fromid + ", id=" + id + ", timestamp=" + timestamp
				+ ", delFlag=" + delFlag + ", status=" + status + ", toId=" + toId + "]";
	}
    
    
    
    
    
    
}
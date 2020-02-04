package cn.com.djin.lws.utils;

import cn.com.djin.lws.entity.Message;
import cn.com.djin.lws.entity.PackMsgBTO;
import cn.com.djin.lws.entity.SendMessageBTO;
import cn.com.djin.lws.entity.User;
import cn.com.djin.lws.service.BaseService;
import cn.com.djin.lws.service.MessageService;
import cn.com.djin.lws.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *   服务器端的Socket对象
 */
@Component
@RequestMapping("/mySocketHandler")
public class MySocketHandler implements WebSocketHandler {
	
	//日志对象
    private Logger logger = LoggerFactory.getLogger(MySocketHandler.class);
    //用户握手集合  key为用户id   value为WebSocketSession对象，服务器端保存多个客户端的WebSocketSession对象
    private static final Map<Integer, WebSocketSession> users = new ConcurrentHashMap<Integer, WebSocketSession>();
    //json转换器
    ObjectMapper mapper = new ObjectMapper();
    //操作序列化的工具
    @Autowired
    private ResponseAPI responseAPI;

    //依赖注入用户业务层对象
    @Autowired
    private UserService userService;
    //依赖注入消息的业务层对象
    @Autowired
    private MessageService messageService;



    //连接成功后操作
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

        Integer id = this.parsing(webSocketSession);  //登录后的此用户id
        if(id!=null&&id!=0){
            logger.info("用户id为"+id+"的webSocket链接成功。。");
            //存入当前用户的WebSocketSession到服务器端的Map集合中
            users.put(id,webSocketSession);
            //修改用户状态
            userService.upd(new User(id,"online"));
            //推送离线消息给刚刚登陆的用户
            this.sendOffMsg(webSocketSession,id);
            /*  for (Message message:messages) {
                //定义发送消息对象
                PackMsgBTO<Message> chatMessage = new PackMsgBTO<Message>();
                //设置消息大的类型（包括好友消息和群组消息）
                chatMessage.setEmit("offLineMessage");
                //设置消息内容
                chatMessage.setData(message);
                //通过登陆后链接的接收方webSocket对象直接发送
                webSocketSession.sendMessage(new TextMessage(responseAPI.getJsonString(chatMessage), true));
                //发送后修改消息的状态
                messageService.upd(new Message(message.getCid(),1));
            }*/
        }


    }

    /**
     *   处理消息
     * @param webSocketSession  发送方的webSocketSession对象
     * @param webSocketMessage  发送的消息对象
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
		String msgStr = webSocketMessage.getPayload().toString();
		logger.info(msgStr);
		//1.对发送到服务器端的消息进行处理，得到接收方toId
        PackMsgBTO<SendMessageBTO<User>> packMsgBTO = new PackMsgBTO<SendMessageBTO<User>>();
        packMsgBTO = mapper.readValue(msgStr, new TypeReference<PackMsgBTO<SendMessageBTO<User>>>(){});
        PackMsgBTO<Message> chatMessage = new PackMsgBTO<Message>();
        //2-1设置接收方的消息大的类型：chatMessage
        chatMessage.setEmit(packMsgBTO.getEmit());
        //2-2获取发送方发送消息的类型：好友消息还是群组消息
        String type = packMsgBTO.getData().getTo().getType();
        if("friend".equals(type)){  //发送好友消息
            //3-1.获取到接收方的用户id
            Integer toId = packMsgBTO.getData().getTo().getId();
            //3-2.组装发送给好友接收方的消息内容
            Message assembleFriend = this.assembleFriend(packMsgBTO);
            //3-3.将组装后的消息内容设置到要给发送方的消息对象
            chatMessage.setData(assembleFriend);
            //4.由服务器端发送消息给接收方
            this.sendFriend(toId,chatMessage);
        }else if("group".equals(type)){
            //3-1.获取自己发送方的用户id
            Integer fId = packMsgBTO.getData().getMine().getId();
            //3-1.组装发送给群组的消息内容
            Message assembleGroup = this.assembleGroup(packMsgBTO);
            //3-2.将组装后的消息内容设置到要给发的群组消息对象
            chatMessage.setData(assembleGroup);
            //4.由服务器端发送群组消息
            this.sendGroup(fId,chatMessage);
        }

	}

    //连接异常操作
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable exception) throws Exception {
        if(webSocketSession.isOpen()){
            Integer id = this.parsing(webSocketSession);  //登录后的此用户id
            //修改用户状态
            userService.upd(new User(id,"offline"));
            logger.error("用户id为"+id+"的webSocket连接出现错误",exception);
        	webSocketSession.close();
        }
    }

    //关闭连接后的操作
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
    	if (webSocketSession.isOpen()) {
            Integer id = this.parsing(webSocketSession);  //登录后的此用户id
            //修改用户状态
            userService.upd(new User(id,"offline"));
            logger.info("用户id为"+id+"的webSocket连接关闭");
    		webSocketSession.close();
		}
    }

    @Override
    public boolean supportsPartialMessages() {

        return false;
    }
    
    /**
	 * @功能:解析出登录id
	 * @作者:djin
	 * @时间:2018年12月12日
	 * @param webSocketSession
	 * @return
	 */
	private Integer parsing(WebSocketSession webSocketSession) {
		
		return (Integer) webSocketSession.getAttributes().get("user_id");
	}

    /**
     * @param packMsgBTO
     * @功能:friend组装数据
     * @作者:djin
     * @时间:2018年12月12日
     * @return  组装要发送的信息
     */
    private Message assembleFriend(PackMsgBTO<SendMessageBTO<User>> packMsgBTO) {
        Message onMessageBTO = new Message();
        onMessageBTO.setUsername(packMsgBTO.getData().getMine().getUsername());
        onMessageBTO.setAvatar(packMsgBTO.getData().getMine().getAvatar());
        onMessageBTO.setId(packMsgBTO.getData().getMine().getId());
        onMessageBTO.setType(packMsgBTO.getData().getTo().getType());
        onMessageBTO.setContent(packMsgBTO.getData().getMine().getContent());
        onMessageBTO.setMine(false);
        onMessageBTO.setFromid(packMsgBTO.getData().getMine().getId());
        onMessageBTO.setTimestamp(new Date().getTime());
        onMessageBTO.setToId(packMsgBTO.getData().getTo().getId());
        return onMessageBTO;
    }

    /**
     *  @功能:群消息组装
     *  @作者:djin
     *  @时间:2018年12月14日
     *  @param packMsgBTO
     *  @return
     */
    private Message assembleGroup(PackMsgBTO<SendMessageBTO<User>> packMsgBTO) {
        Message onMessageBTO = new Message();
        onMessageBTO.setUsername(packMsgBTO.getData().getMine().getUsername());
        onMessageBTO.setAvatar(packMsgBTO.getData().getMine().getAvatar());
        onMessageBTO.setId(packMsgBTO.getData().getTo().getId());
        onMessageBTO.setType(packMsgBTO.getData().getTo().getType());
        onMessageBTO.setContent(packMsgBTO.getData().getMine().getContent());
        onMessageBTO.setMine(false);
        onMessageBTO.setFromid(packMsgBTO.getData().getMine().getId());
        onMessageBTO.setTimestamp(new Date().getTime());
        onMessageBTO.setToId(packMsgBTO.getData().getTo().getId());
        return onMessageBTO;
    }

    /**
     * @功能:保存群消息
     * @作者:djin
     * @时间:2018年12月14日
     * @param message
     */
    private void saveGroupMsg(Message message) {
        //1.设置此群组消息未读（未发送）
        message.setStatus(WebConstant.NOT_SEND);
        //2.声明此群组成员
        List<User> paramList;
        try {
            //3.在数据库中查询此群中的所有群组成员
            paramList = userService.findManyByOtherId(message.getId());
            //4.获取群组成员的迭代器
            Iterator<User> iterator = paramList.iterator();
            //5.循环将此群组消息保存到每一个用户的消息数据中去
            while (iterator.hasNext()) {
                User user = (User) iterator.next();
                message.setCid(null);
                //设置消息的接收方id（此时所有的群成员都是接收方）
                message.setToId(user.getId());
                //存储消息数据
                messageService.save(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @功能:发送msg,单聊
     * @作者:djin
     * @时间:2018年12月12日
     * @param id
     * @param chatMessage
     * @throws IOException
     */
    private void sendFriend(Integer id, PackMsgBTO<Message> chatMessage) throws Exception {
        //1.找到接收方的webSocketSession对象，通过toId在users集合中找
        WebSocketSession webSocketSession = users.get(id);
        //2-1从消息对象中（消息类型和内容）取出消息内容
        Message message = chatMessage.getData();
        //2-2设置此消息未删除，即可以发送
        message.setDelFlag(WebConstant.NODELETECODE);
        Boolean b = false;
        //3-1只要能找到接收方的webSocketSession对象，则开启，可以发送消息
        b = webSocketSession != null ? webSocketSession.isOpen():false;
        if (b) {
            //3-2.在线则由接收方的webSocketSession对象发送消息到对应接收方用户页面中
            webSocketSession.sendMessage(new TextMessage(responseAPI.getJsonString(chatMessage), true));
            message.setStatus(WebConstant.SENDED);
        }else {
            //3-3.不在线则当其下次上线时初始化未读取的消息（离线消息）
            message.setStatus(WebConstant.NOT_SEND);
        }
        //将消息存入到数据库中
        messageService.save(message);
    }

    /**
     * @param id
     * @功能:群消息发送
     * @作者:djin
     * @时间:2018年12月14日
     * @param responseAPI.getJsonString(chatMessage)
     * @throws IOException
     */
    private void sendGroup(Integer id, PackMsgBTO<Message> chatMessage) throws Exception {
        //1-1.从消息对象中取出消息内容
        Message message = chatMessage.getData();
        //1-2.将消息内容设置为未删除
        message.setDelFlag(WebConstant.NODELETECODE);
        //2-1.向群组中的每一个成员添加消息数据
        if(chatMessage.getEmit().equals("chatMessage")){
            //2-2.保存消息数据
            this.saveGroupMsg(message);
        }
        //3-1得到users的Map集合每一个元素的迭代器
        Iterator<Map.Entry<Integer, WebSocketSession>> iterator = users.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, WebSocketSession> entry = (Map.Entry<Integer, WebSocketSession>) iterator.next();
            //3-2得到用户登陆后的webSocketSession
            WebSocketSession webSocketSession = entry.getValue();
            Boolean b = null;
            b = webSocketSession != null ? webSocketSession.isOpen():false;
            //4-1判断当前群组成员是否在线
            if(b){  //在线则发送
                //4-2得到群消息的发送id
                Integer fromId = message.getFromid();
                //4-3得到当前循环中登陆的用户id
                Integer webId = this.parsing(webSocketSession);
                //4-4设置消息的内容（发送方id,当前接收方id,消息类型）
                Message param = new Message();
                param.setFromid(fromId);
                param.setToId(webId);
                param.setType("group");
                //4-5设置消息发送状态
                param.setStatus(WebConstant.SENDED);
                if(fromId ==  webId ){  //发送消息用户id与当前循环中登陆的用户id一致，则不发送
                    //5-1自己不给自己发送，并将消息设置为已删除
                    param.setDelFlag(WebConstant.DELETECODE);
                }else{
                    //5-2若不一致则未其他群组成员，直接发送
                    webSocketSession.sendMessage(new TextMessage(responseAPI.getJsonString(chatMessage), true));
                }
                //6-1.修改之前保存的消息数据
                messageService.upd(param);
            }
        }
    }

    /**
     *    发送离线消息
     * @param webSocketSession  用户登陆的，接收方的webSocket对象
     * @param id  用户登陆的，接收方的id
     * @throws Exception
     */
    private void sendOffMsg(WebSocketSession webSocketSession,Integer id) throws Exception{
        //查询此用户的离线消息，根据接收方toId和消息的状态0查询
        List<Message> messages = messageService.findManyByPramas(new Message(WebConstant.NODELETECODE,WebConstant.NOT_SEND, id));
        if(messages.size()!=0){
            PackMsgBTO<List<Message>> chatMessage = new PackMsgBTO<List<Message>>();
            //设置消息大的类型（包括好友消息和群组消息）
            chatMessage.setEmit("offLineMessage");
            //设置消息内容(将所有的离线消息全部设置进去)
            chatMessage.setData(messages);
            //通过登陆后链接的接收方webSocket对象直接发送
            webSocketSession.sendMessage(new TextMessage(responseAPI.getJsonString(chatMessage), true));
            //将接收方的消息数据状态全部改为已发送
            messageService.upd(new Message(WebConstant.NODELETECODE,WebConstant.SENDED,id));
        }
    }

 }

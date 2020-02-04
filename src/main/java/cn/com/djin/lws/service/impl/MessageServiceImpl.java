package cn.com.djin.lws.service.impl;

import cn.com.djin.lws.entity.Message;
import cn.com.djin.lws.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author djin
 *   群组业务层实现类
 */
@Service
@Transactional
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService {

}

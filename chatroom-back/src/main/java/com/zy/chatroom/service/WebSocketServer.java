package com.zy.chatroom.service;

import com.zy.chatroom.entity.Message;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/webSocket/{account}")
@Component
public class WebSocketServer {
	 //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineNum = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    //给指定会话发送消息
    public void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            synchronized (session) {
                System.out.println("发送数据：" + message);
                session.getBasicRemote().sendText(message);
            }
        }
    }

    // 群发消息
    public void broadcast(String message){
    	for (Session session: sessionPools.values()) {//依次给每个会话发送消息
            try {
                sendMessage(session, message);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam("account") String account){
        if(account!=null) {
            sessionPools.put(account, session);//会话加入会话池
            addOnlineCount();//在线人数加一
            System.out.println(account + "加入webSocket！当前人数为" + onlineNum);
            // 广播上线消息
            Message msg = new Message();
            msg.setDate(new Date());
            msg.setFromWho("system_connect_open");
            msg.setToWho("all_users");
            msg.setText(account);
            broadcast(JSON.toJSONString(msg, true));//依次给每个会话发送消息
        }
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam("account") String account){
        sessionPools.remove(account);
        subOnlineCount();
        System.out.println(account + "断开webSocket连接！当前人数为" + onlineNum);
        // 广播下线消息
        Message msg = new Message();
        msg.setDate(new Date());
        msg.setFromWho("system_connect_close");
        msg.setToWho("all_users");
        msg.setText(account);
        broadcast(JSON.toJSONString(msg,true));
    }

    //收到客户端信息后，根据接收人的username把消息推下去或者群发
    @OnMessage
    public void onMessage(String message) throws IOException{
        System.out.println("server get" + message);
        Message msg=JSON.parseObject(message, Message.class);
		msg.setDate(new Date());
		if (msg.getToWho().equals("all_users")) {  //发送到系统的消息
			broadcast(JSON.toJSONString(msg,true));
		} else {
            String account = msg.getToWho();
            Session session = sessionPools.get(account); //查找对应的会话
            try {
                sendMessage(session, message);
            }catch (Exception e){
                e.printStackTrace();
            }
		}
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    //添加用户数量 
    public static void addOnlineCount(){
        onlineNum.incrementAndGet();
    }
    //减少用户数量 
    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }
    //当前在线人数
    public static AtomicInteger getOnlineNumber() {
        return onlineNum;
    }
    //获取会话池
    public static ConcurrentHashMap<String, Session> getSessionPools() {
        return sessionPools;
    }
}

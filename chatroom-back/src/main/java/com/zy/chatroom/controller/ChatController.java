package com.zy.chatroom.controller;

import com.zy.chatroom.service.WebSocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.Session;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ChatController {
    @RequestMapping("/onlineUsers")
    @ResponseBody
    public Set<String> onlineusers(@RequestParam("account") String currentuser) {
        ConcurrentHashMap<String, Session> map = WebSocketServer.getSessionPools();
        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        Set<String> nameset = new HashSet<String>();
        while (it.hasNext()) {
            String entry = it.next();
            if (!entry.equals(currentuser))
                nameset.add(entry);
        }
        System.out.println(nameset);
        return nameset;
    }
}

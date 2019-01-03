package websocket;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.EntityAttributeTable;
import util.WebSocketRemindUtil;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/WebSocketRemind")
public class WebSocketRemind {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private Stack<WebSocketRemind> webSocketSetStack;
    protected static ConcurrentHashMap<String, Stack<WebSocketRemind>> webSocketSet = new ConcurrentHashMap<String, Stack<WebSocketRemind>>();
    public static ConcurrentHashMap<String, JSONObject> remindMap = new ConcurrentHashMap<String, JSONObject>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //my variable
    private String userid;
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        URI uri=session.getRequestURI();
        this.userid=WebSocketRemindUtil.onOpenCheck(uri);
        if(userid==null) {
			System.out.println("WebSocket>"+this.getClass().getName()+":有非法连接加入！当前在线人数为" + getOnlineCount());
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
        }
        webSocketSetStack=webSocketSet.get(userid);
        if(webSocketSetStack==null) {
        		webSocketSetStack = new Stack<WebSocketRemind>();
        		webSocketSetStack.push(this);
        		webSocketSet.put(userid, webSocketSetStack);
        }else {
        		webSocketSetStack.push(this);
        }
        addOnlineCount();           //在线数加1
        System.out.println("WebSocket>"+this.getClass().getName()+":有新连接加入！当前在线人数为" + getOnlineCount());
        JSONObject jsonObj=remindMap.get(userid);
        if(jsonObj!=null) {
		    	try {
		    		JSONObject jsonObjReminder=new JSONObject();
		    		Iterator<String> iterator = jsonObj.keys();
		    		String key=null,nname=null;
		    		int value;
		    		while(iterator.hasNext()){
		    			key = (String)iterator.next();
		    			value = (int) jsonObj.get(key);
		    			//get nname
		    			EntityAttributeTable table=new EntityAttributeTable("nname","user");
		    			table.setSqlCondition("userid", key);
		    			table.sqlSelect();
		    			if(table.setObject()) {
		    				nname=table.getValue("nname");
		    				if(nname==null) {
		    					nname=key;
		    				}
		    			}
		    			//set json array:[hyid,amount of new message]
		    			JSONArray jsonArr=new JSONArray();
    		    			jsonArr.put(nname).put(value);
    		    			jsonObjReminder.put(key,jsonArr);
		    		}
		    		this.sendMessage(jsonObjReminder.toString());
		    	} catch (IOException e) {
		    		e.printStackTrace();
		    	}
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
    		webSocketSetStack=webSocketSet.get(userid);
        if(webSocketSetStack!=null&&webSocketSetStack.removeElement(this)) {
        		if(webSocketSetStack.isEmpty()) {
        			webSocketSet.remove(userid);
        		}
        		subOnlineCount();           //在线数减1
        		System.out.println("WebSocket>"+this.getClass().getName()+":有一连接关闭！当前在线人数为" + getOnlineCount());
        }else {
        		System.out.println("WebSocket>"+this.getClass().getName()+":有非法连接关闭！当前在线人数为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("WebSocket>"+this.getClass().getName()+":来自客户端的消息:" + message);
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("WebSocket>"+this.getClass().getName()+":发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
    		//this.session.getAsyncRemote().sendText(message);
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
    		WebSocketRemind.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
    		WebSocketRemind.onlineCount--;
    }
}
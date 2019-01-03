var websocket = null;

//判断当前浏览器是否支持WebSocket
if('WebSocket' in window){
	if($.cookie("token")!="null"){
		websocket = new WebSocket("ws://"+ipAdress+"/gravity2/WebSocketChat?hyid="+getParameter("hyid")+"&token="+$.cookie('token'));
	}
}
else{
	alert("本浏览器不支持WebSocket，聊天功能将无法使用，请更换浏览器。");
}

//连接发生错误的回调方法
websocket.onerror = function(){
	myalert("连接出错，请稍后再试");
};

//连接成功建立的回调方法
websocket.onopen = function(){
	//myalert("连接成功，可以聊天啦");
}

//接收到消息的回调方法
websocket.onmessage = function(event){
	var newline=$(".chatContent").val()?"":"";
	$(".chatContent").val($(".chatContent").val() + newline + event.data);
	autoBottom();
}

//连接关闭的回调方法
websocket.onclose = function(){
	myalert("连接已关闭，若想重新连接请刷新页面");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function(){
	websocket.close();
	websocketRemind.close();
}

//关闭连接
function closeWebSocket(){
    websocket.close();
}

//发送消息
function send(){
	var message=$(".chatSend").val();
	websocket.send(message);
}

//press the send botton
$(document).ready(function(){
	$(".chatSendBtn").click(function(){
		if(!$(".chatSend").val()){
			return;
		}
		send();
		$(".chatSend").val("");
	});
	$(".chatSend").keydown(function(){
		if(event.keyCode ==13){
			$(".chatSendBtn").trigger("click");
		}
	});
});
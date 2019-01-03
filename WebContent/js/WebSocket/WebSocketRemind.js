var websocketRemind = null;
 
//判断当前浏览器是否支持WebSocket
if('WebSocket' in window){
	if($.cookie("token")!="null"){
		websocketRemind = new WebSocket("ws:/"+ipAdress+"/gravity2/WebSocketRemind?token="+$.cookie('token'));
	}
}
else{
	alert("本浏览器不支持WebSocket，聊天功能将无法使用，请更换浏览器。");
}

//连接发生错误的回调方法
websocketRemind.onerror = function(){
	myalert("连接出错，请稍后再试");
};

//连接成功建立的回调方法
websocketRemind.onopen = function(){
	//myalert("连接成功");
}

//接收到消息的回调方法
websocketRemind.onmessage = function(event){
	setReminder(event.data);
	showReminder();
}

//连接关闭的回调方法
websocketRemind.onclose = function(){
	//myalert("连接已断开，请刷新页面重新连接");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function(){
	websocketRemind.close();
	websocket.close();
}

//关闭连接
function websocketRemindClose(){
	websocketRemind.close();
}

//发送消息
function websocketRemindSend(message){
	websocketRemind.send(message);
}

//reminder
//show reminder
function showReminder(){
	var reminderElem=$(".reminderListCon").find(".reminderListElem").eq(1);
	if(reminderElem.length==0){
		return;
	}
	$(".reminder").fadeIn(500);
	$(".reminderTipCon").animate({left:"-10%",width:"80%"},150).animate({left:"10%",width:"110%"},300).animate({left:"0",width:"100%"},150);
}

//reminder botton
$(document).ready(function(){
	//show reminderList
	$("#reminderBtnCheck").click(function(){
		$(".reminderListCon").slideDown();
		$("#reminderBtnCheck").hide();
		$("#reminderBtnCheckBk").fadeIn();
	});
	//close reminderList
	$("#reminderBtnCheckBk").click(function(){
		$(".reminderListCon").slideUp();
		$("#reminderBtnCheck").fadeIn();
		$("#reminderBtnCheckBk").hide();
	});
	//ignore reminderList
	$("#reminderBtnIgnore").click(function(){
		$.post("ServletChatReminderIgnore",{
			"token":$.cookie("token")}
		);
		$(".reminder").fadeOut(500,function(){
			$(".reminderListCon").hide();
			$("#reminderBtnCheckBk").hide();
			$("#reminderBtnCheck").show();
		});
	});
});
//set reminder
var filename=location.pathname;
filename=filename.substr(filename.lastIndexOf('/')+1);
function setReminder(onmessageData){
	var json=JSON.parse(onmessageData);
	for(var key in json){
		if(filename=="sel3hyChat.html"&&key==getParameter("hyid")){
			continue;
		}
		var reminderAdd=$("#reminderListElem"+key);
		if(reminderAdd.length>0){
			reminderAdd.find(".reminderListElemNum").text((json[key])[1]);
		}else{
			$(".reminderListCon").append(
				'<tr class=reminderListElem id=reminderListElem'+key+'>'+
					'<td class=reminderListElemName><a href=sel3hyChat.html?hyid='+key+'><div class=reminderHerf>'+(json[key])[0]+'</div></a></td>'+
					'<td class=reminderListElemNum>'+(json[key])[1]+'</td>'+
				'</tr>'
			);
		}
	}
}
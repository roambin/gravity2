var hyid;

$(document).ready(function(){
	hyid=getParameter("hyid");
	if(hyid==undefined||hyid=="null"||hyid==""){
		window.location.href="sel3hy.html";
		return;
	}
	$.post("ServletChatFriendName",{
		"token":$.cookie("token"),
		"hyid":hyid},function(data,status){
		if(data=="filter:not access"){
			window.location.href="sel3hy.html";
		}else{
			var json=JSON.parse(data);
			$(".chatFriendName").text(json.nname);
		}
	});
});

$(document).ready(function(){
	$.post("ServletChatHistory",{
		"token":$.cookie("token"),
		"hyid":hyid},function(data,status){
		if(data=="filter:not access"){
			window.location.href="sel3hy.html";
		}else{
			var json=JSON.parse(data);
			$.get(json.path,function(data){
				$(".chatContent").val(data);
				autoBottom();
			});
		}
	});
	$(".chatHistory").click(function(){
		$.post("ServletChatHistory",{
			"token":$.cookie("token"),
			"hyid":hyid},function(data,status){
			if(data=="filter:not access"){
				window.location.href="sel3hy.html";
			}else{
				var json=JSON.parse(data);
				$.ajax({
			        url: json.path,
			        type: 'GET',
			        error: function() {
			            myalert("无历史记录");
			        },
			        success: function(data) {
			        		if(data==""){
			        			myalert("无历史记录");
			        		}else{
			        			$(".chatContent").val(data);
			        			autoBottom();
			        		}
			        }
			    });
			}
		});
	});
});


//auto at bottom
function autoBottom(){
	var scrollTop = $("#chatContent")[0].scrollHeight;
	$("#chatContent").scrollTop(scrollTop);
}
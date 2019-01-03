var selnum=0;
var sellength=2;
var isbool=false;
var postname="ServletFriendMy";

function createUser(data,status){
	selnum+=sellength;
	appendUser(data,"#selectactivity");
}
function appendUser(data,container){
	//alert("数据: \n" + data);
	var jsonArr = JSON.parse(data);
	for(var i=0;i<jsonArr.length;i++){
		$(container).append(
		'<div id="bjperinf">'+
			'<div class=hyid style="display:none">'+jsonArr[i].hyid+'</div>'+
		    	'<table id="bjuser">'+
		    	'<tr>'+
		    		'<td id="bjuser1">'+
		    			'<img id="bjuserpic" alt="头像" src="'+jsonArr[i].headpic+'">'+
		    			'<div id="bjusersel">'+jsonArr[i].flag+'</div>'+
		    		'</td>'+
		    		'<td id="bjuser2">'+
		    			'<div id="bjuserinf">昵称：'+jsonArr[i].nname+'</div>'+
		    			'<div id="bjuserinf">姓名：'+jsonArr[i].name+'</div>'+
		    			'<div id="bjuserinf">邮箱：'+jsonArr[i].email+'</div>'+
		    			'<div id="bjuserinf">地区：'+jsonArr[i].area+'</div>'+
		    			'<div id="bjuserinf">地址：'+jsonArr[i].adress+'</div>'+
		    			'<div id="bjuserinf">职业：'+jsonArr[i].job+'</div>'+
		    		'</td>'+
		    		'<td id="bjuser3">'+
		    			'<div id="bjuserty" class="bjuserty")">同<br>意</div>'+
		    			'<div id="bjuserjj" class="bjuserjj")">拒<br>绝</div>'+
		    			'<div id="bjuserlt" class="bjuserlt")">聊<br>天</div>'+
		    			'<div id="bjusertj" class="bjusertj")">添<br>加</div>'+
		    			'<div id="bjusersc" class="bjusersc")">删<br>除</div>'+
		    		'</td>'+
		    	'</tr>'+
		    '</table>'+
		'</div>'
		);
	}
}
$(document).ready(function(){
	$.post(postname,{
		"token":$.cookie("token"),
		"addswitnname":$("#addswitnname").val(),
		"addswitname":$("#addswitname").val(),
		"addswitarea":$("#addswitarea").val(),
		"addswitflag":$("#addswitflag").val(),
		"selnum":selnum,
		"sellength":sellength},function(data,status){
		createUser(data,status);
		$(".bjuserty").hide();
		$(".bjuserjj").hide();
		$(".bjuserlt").show();
		$(".bjusertj").hide();
		$(".bjusersc").show();
		$(".selectTitleCon").hide();
		$("#RecommendUserCon").hide();
		isbool=true;
	});
});

$(document).ready(function(){
	$("#addswitsubmit").click(function(){
		selectActSel();
	});
});
function selectAct(){
	isbool=false;
	selnum=0;
	$("#selectactivity").empty();
	$.post(postname,{
		"token":$.cookie("token"),
		"addswitnname":$("#addswitnname").val(),
		"addswitname":$("#addswitname").val(),
		"addswitarea":$("#addswitarea").val(),
		"addswitflag":$("#addswitflag").val(),
		"selnum":selnum,
		"sellength":sellength},function(data,status){
		createUser(data,status);
		isbool=true;
	});
}

$(document).ready(function(){
	$(document).scroll(function(){
		if ($(document).scrollTop() >= $(document).height() - $(window).height()&&isbool==true) {
		//alert("滚动条已经到达底部为" + $(document).scrollTop());
			isbool=false;
			$.post(postname,{
				"token":$.cookie("token"),
				"addswitnname":$("#addswitnname").val(),
				"addswitname":$("#addswitname").val(),
				"addswitarea":$("#addswitarea").val(),
				"addswitflag":$("#addswitflag").val(),
				"selnum":selnum,
				"sellength":sellength},function(data,status){
					createUser(data,status);
					btnSwitch();
					if(data!="[]"){
						isbool=true;
					}else{
						$("#isbottom").ready(function(){
							$("#isbottom").text("到底啦！");
						});
					}
				});
		}
	});
});


//four tip switch
$(document).ready(function(){
	$("#myftip").click(function(){
		$("#myftipp").animate({opacity:'1'});
		$("#myatipp").animate({opacity:'0'});
		$("#myrtipp").animate({opacity:'0'});
		$("#addtipp").animate({opacity:'0'});
		$(".selectTitleCon").hide();
		postname="ServletFriendMy";
		selectActSel();
	})
	$("#myatip").click(function(){
		$("#myftipp").animate({opacity:'0'});
		$("#myatipp").animate({opacity:'1'});
		$("#myrtipp").animate({opacity:'0'});
		$("#addtipp").animate({opacity:'0'});
		$(".selectTitleCon").hide();
		postname="ServletFriendMyAsk";
		selectActSel();
	})
	$("#myrtip").click(function(){
		$("#myftipp").animate({opacity:'0'});
		$("#myatipp").animate({opacity:'0'});
		$("#myrtipp").animate({opacity:'1'});
		$("#addtipp").animate({opacity:'0'});
		$(".selectTitleCon").hide();
		postname="ServletFriendMyResponse";
		selectActSel();
	})
	$("#addtip").click(function(){
		$("#myftipp").animate({opacity:'0'});
		$("#myatipp").animate({opacity:'0'});
		$("#myrtipp").animate({opacity:'0'});
		$("#addtipp").animate({opacity:'1'});
		$(".selectTitleCon").slideDown();
		postname="ServletFriendMyAdd";
		selectActSel();
	})
});

//ajax post method used by four tip switch
function selectActSel(sel){
	isbool=false;
	selnum=0;
	$("#selectactivity").empty();
	$.post(postname,{
		"token":$.cookie("token"),
		"addswitnname":$("#addswitnname").val(),
		"addswitname":$("#addswitname").val(),
		"addswitarea":$("#addswitarea").val(),
		"addswitflag":$("#addswitflag").val(),
		"selnum":selnum,
		"sellength":sellength},function(data,status){
		createUser(data,status);
		btnSwitch();
		isbool=true;
	});
}
//bottom switch
function btnSwitch(){
	if(postname=="ServletFriendMy"){
		$(".bjuserty").hide();
		$(".bjuserjj").hide();
		$(".bjuserlt").fadeIn();
		$(".bjusertj").hide();
		$(".bjusersc").fadeIn();
		$("#RecommendUserCon").hide();
	}else if(postname=="ServletFriendMyAsk"){
		$(".bjuserty").hide();
		$(".bjuserjj").hide();
		$(".bjuserlt").hide();
		$(".bjusertj").hide();
		$(".bjusersc").fadeIn();
		$("#RecommendUserCon").hide();
	}else if(postname=="ServletFriendMyResponse"){
		$(".bjuserty").fadeIn();
		$(".bjuserjj").fadeIn();
		$(".bjuserlt").hide();
		$(".bjusertj").hide();
		$(".bjusersc").hide();
		$("#RecommendUserCon").hide();
	}else if(postname=="ServletFriendMyAdd"){
		$(".bjuserty").hide();
		$(".bjuserjj").hide();
		$(".bjuserlt").hide();
		$(".bjusertj").fadeIn();
		$(".bjusersc").hide();
		$("#RecommendUserCon").show();
	}
}

//botton event
$(document).ready(function(){
	$("#selectactivity").on("click","#bjuserty",function(){
		$(this).parents("#bjperinf").find("#bjusersel").text("同意");
		btnReq("ServletFriendAr",this);
	});
	$("#selectactivity").on("click","#bjuserjj",function(){
		$(this).parents("#bjperinf").find("#bjusersel").text("拒绝");
		btnReq("ServletFriendAr",this);
	});
	$("#selectactivity").on("click","#bjuserlt",function(){
		window.location.href="sel3hyChat.html?hyid="+$(this).parents("#bjperinf").find(".hyid").text();
	});
	$("#selectactivity,.selectRecommend").on("click","#bjusertj",function(){
		if($(this).parents("#bjperinf").find("#bjusersel").text()=="待审"){
			myalert("待审状态，请审核或等待对方审核");
		}else if($(this).parents("#bjperinf").find("#bjusersel").text()=="同意"){
			myalert("已经是好友");
		}else{
			$(this).parents("#bjperinf").find("#bjusersel").text("待审");
			btnReq("ServletFriendA",this);
		}
	});
	$("#selectactivity").on("click","#bjusersc",function(){
		btnReq("ServletFriendD",this);
	});
});
function btnReq(reqName,perthis){
	$.post(reqName,{
		"token":$.cookie("token"),
		"hyid":$(perthis).parents("#bjperinf").find(".hyid").text(),
		"flag":$(perthis).parents("#bjperinf").find("#bjusersel").text()
		},function(data,status){
			var jsonObj = JSON.parse(data);
			myalert(jsonObj.text);
			if(jsonObj.num=="0"){
				$(perthis).parents("#bjperinf").slideToggle();
			}else if(jsonObj.num=="1"){
				$(perthis).parents("#bjperinf").find("#flag").text("待审");
			}
	});
}


//friends recommend
var recselnum=0;
var recsellength=2;
$(document).ready(function(){
	$("#addtip").click(function(){
		recselnum=0;
		selRecommend();
	});
	$(".selectRecommendChange").click(function(){
		selRecommend();
	});
});
function selRecommend(){
	$(".selectRecommend").empty();
	$(".isHasRecommend").hide();
	$.post("ServletFriendRecommend",{
		"token":$.cookie("token"),
		"addswitnname":$("#addswitnname").val(),
		"addswitname":$("#addswitname").val(),
		"addswitarea":$("#addswitarea").val(),
		"addswitflag":$("#addswitflag").val(),
		"selnum":recselnum,
		"sellength":recsellength},function(data,status){
		if(data!="[]"){
			recselnum+=recsellength;
		}else{
			recselnum=0;
			$(".isHasRecommend").show();
		}
		appendUser(data,".selectRecommend");
		$(".bjuserty").hide();
		$(".bjuserjj").hide();
		$(".bjuserlt").hide();
		$(".bjusertj").show();
		$(".bjusersc").hide();
	});
}
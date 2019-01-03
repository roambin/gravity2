var selnum=0;
var sellength=2;
var isbool=false;
var postname;
var hdid;

function createactivity(data,status){
	if(data=="filter:not access"){
		window.location.href="sel2hd.html";
	}
	selnum+=sellength;
	//alert("数据: \n" + data + "\n状态: " + status);
	var jsonArr = JSON.parse(data);
	for(var i=0;i<jsonArr.length;i++){
		$("#selectactivity").append(
		'<div id="bjperinf">'+
			'<div class=hyid style="display:none">'+jsonArr[i].hyid+'</div>'+
		    	'<table id="bjuser">'+
		    	'<tr>'+
		    		'<td id="bjuser1">'+
		    			'<img id="bjuserpic" alt="头像" src="'+jsonArr[i].headpic+'">'+
		    			'<div id="bjusersel" class="bjusersel">'+jsonArr[i].flag+'</div>'+
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
		    			'<div id="bjuserty" class="bjuserty")"><br>同<br>意</div>'+
		    			'<div id="bjuserjj" class="bjuserjj")"><br>拒<br>绝</div>'+
		    			'<div id="bjuserlt" class="bjuserlt")"><br>聊<br>天</div>'+
		    			'<div id="bjusertj" class="bjusertj")"><br>添<br>加</div>'+
		    			'<div id="bjusersc" class="bjusersc")"><br>删<br>除</div>'+
		    		'</td>'+
		    	'</tr>'+
		    '</table>'+
		'</div>'
		);
	}
}

$(document).ready(function(){
	hdid=getParameter("hdid");
	var sel=getParameter("sel");
	if(sel=="join"){
		postname="ServletManageJoin";
		addswitSwitch();
	}else if(sel=="like"){
		postname="ServletManageLike";
		addswitSwitch();
	}else{
		window.location.href="sel2hd.html";
	}
	$.post(postname,{
		"token":$.cookie("token"),
		"hdid":hdid,
		"addswitnname":$("#addswitnname").val(),
		"addswitname":$("#addswitname").val(),
		"addswitarea":$("#addswitarea").val(),
		"addswitflag":$("#addswitflag").val(),
		"selnum":selnum,
		"sellength":sellength},function(data,status){
		createactivity(data,status);
		btnSwitch();
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
		"hdid":hdid,
		"addswitnname":$("#addswitnname").val(),
		"addswitname":$("#addswitname").val(),
		"addswitarea":$("#addswitarea").val(),
		"addswitflag":$("#addswitflag").val(),
		"selnum":selnum,
		"sellength":sellength},function(data,status){
		createactivity(data,status);
		btnSwitch();
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
				"hdid":hdid,
				"addswitnname":$("#addswitnname").val(),
				"addswitname":$("#addswitname").val(),
				"addswitarea":$("#addswitarea").val(),
				"addswitflag":$("#addswitflag").val(),
				"selnum":selnum,
				"sellength":sellength},function(data,status){
					createactivity(data,status);
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
	$("#manageback").click(function(){
		window.location.href="sel2hd.html?sel=manage";
	})
	$("#managejoin").click(function(){
		postname="ServletManageJoin";
		addswitSwitch();
		selectActSel();
	})
	$("#managelike").click(function(){
		postname="ServletManageLike";
		addswitSwitch();
		selectActSel();
	})
});
function addswitSwitch(){
	if(postname=="ServletManageJoin"){
		$("#managejoinp").animate({opacity:'1'});
		$("#managelikep").animate({opacity:'0'});
		$("#addswitflagt").fadeIn();
	}else if(postname=="ServletManageLike"){
		$("#managejoinp").animate({opacity:'0'});
		$("#managelikep").animate({opacity:'1'});
		$("#addswitflag").find("option[value='']").attr("selected",true);
		$("#addswitflagt").hide();
	}
}

//ajax post method used by four tip switch
function selectActSel(sel){
	isbool=false;
	selnum=0;
	$("#selectactivity").empty();
	$.post(postname,{
		"token":$.cookie("token"),
		"hdid":hdid,
		"addswitnname":$("#addswitnname").val(),
		"addswitname":$("#addswitname").val(),
		"addswitarea":$("#addswitarea").val(),
		"addswitflag":$("#addswitflag").val(),
		"selnum":selnum,
		"sellength":sellength},function(data,status){
		createactivity(data,status);
		btnSwitch();
		isbool=true;
	});
}
//bottom switch
function btnSwitch(){
	if(postname=="ServletManageJoin"){
		$(".bjuserty").show();
		$(".bjuserjj").show();
		$(".bjuserlt").hide();
		$(".bjusertj").hide();
		$(".bjusersc").hide();
		$(".bjusersel").show();
	}else if(postname=="ServletManageLike"){
		$(".bjuserty").hide();
		$(".bjuserjj").hide();
		$(".bjuserlt").hide();
		$(".bjusertj").hide();
		$(".bjusersc").hide();
		$(".bjusersel").hide();
	}
}

//botton event
$(document).ready(function(){
	$("#selectactivity").on("click","#bjuserty",function(){
		if($(this).parents("#bjperinf").find("#bjusersel").text()=="同意"){
			myalert("已同意");
		}else{
			$(this).parents("#bjperinf").find("#bjusersel").text("同意");
			btnReq("ServletManageAr",this);
		}
	});
	$("#selectactivity").on("click","#bjuserjj",function(){
		if($(this).parents("#bjperinf").find("#bjusersel").text()=="拒绝"){
			myalert("已拒绝");
		}else{
			$(this).parents("#bjperinf").find("#bjusersel").text("拒绝");
			btnReq("ServletManageAr",this);
		}
	});
});
function btnReq(reqName,perthis){
	$.post(reqName,{
		"token":$.cookie("token"),
		"hdid":hdid,
		"hyid":$(perthis).parents("#bjperinf").find(".hyid").text(),
		"flag":$(perthis).parents("#bjperinf").find("#bjusersel").text()
		},function(data,status){
			var jsonObj = JSON.parse(data);
			myalert(jsonObj.text);
			if(jsonObj.num=="0"){
				$(perthis).parents("#bjperinf").find("#flag").text("拒绝");
			}else if(jsonObj.num=="1"){
				$(perthis).parents("#bjperinf").find("#flag").text("同意");
			}
	});
}
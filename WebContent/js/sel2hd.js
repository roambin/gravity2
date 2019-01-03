var selnum=0;
var sellength=2;
var isbool=false;
var postname="ServletMyCreate";

function createactivity(data,status){
	selnum+=sellength;
	//alert("数据: \n" + data + "\n状态: " + status);
	var jsonArr = JSON.parse(data);
	for(var i=0;i<jsonArr.length;i++){
		var likedisplay=['style="display:inline"','style="display:none"'];
		var joindisplay=['style="display:inline"','style="display:none"'];
		if(jsonArr[i].liketemp==1){
			likedisplay=['style="display:none"','style="display:display"'];
		}
		if(jsonArr[i].jointemp==1){
			joindisplay=['style="display:none"','style="display:display"'];
		}
		$("#selectactivity").append(
		'<div class=hdxx>'+
			'<div class=hdid style="display:none">'+jsonArr[i].hdid+'</div>'+
			'<div class=hdxxl>'+
				'<div class="pic"><img src='+jsonArr[i].pic+' class="picimg" alt="这个用户<br/>很懒没有<br/>添加图片"/></div>'+
				'<div id="like" class="like">'+
					'<img id="likep0" class="likep" alt="喜欢" src="pic/icon/like0.svg"'+likedisplay[0]+'>'+
					'<img id="likep1" class="likep" alt="喜欢" src="pic/icon/like1.svg"'+likedisplay[1]+'>'+
			    '</div>'+
			    '<div id="join" class="join">'+
			    		'<img id="joinp0" class="joinp" alt="报名" src="pic/icon/join0.svg"'+joindisplay[0]+'>'+
			    		'<img id="joinp1" class="joinp" alt="报名" src="pic/icon/join1.svg"'+joindisplay[1]+'>'+
			    '</div>'+
			    '<div id="likenum">'+jsonArr[i].likenum+'</div>'+
			    '<div id="joinnum">'+jsonArr[i].joinnum+'</div>'+
			'</div>'+
			'<div class=hdxxr>'+
				'<div id="btnb" class="btnb">点击收起▲</div>'+
				'<div class="hdjj">'+
					'<div class="hdjjcon" id="hdjjcon">'+
						'<p>'+jsonArr[i].hdjj+'</p>'+
						'<p class="fqr">发起人：'+jsonArr[i].fqr+'</p>'+
					'</div>'+
				'</div>'+
			    '<div class="hdm"><table><tr>'+
				    '<td>'+jsonArr[i].hdm+'</td>'+
				    '<td id="addfl">&emsp;'+jsonArr[i].classify+'</td>'+
				    '<td id="peonum">&emsp;人数：'+jsonArr[i].joinnum+'/'+jsonArr[i].maxpeo+'</td>'+'<div id="maxpeo" style="display:none">'+jsonArr[i].maxpeo+'</div>'+
				    '<td id="crashflag">&emsp;'+jsonArr[i].crashflag+'</td>'+
				    '<td id="delflag">&emsp;'+jsonArr[i].delflag+'</td>'+
			    '<tr></table></div>'+
			    '<div class="shdjj">'+jsonArr[i].shdjj+'<div id="btna" class="btna">>>展开<<</div></div>'+
			    '<div class="time"><table><tr><td>开始：'+jsonArr[i].stime+'<br>结束：'+jsonArr[i].etime+'</td><td>&ensp;|&ensp;'+jsonArr[i].timeflag+'</td><tr></table></div>'+
			    '<div class="place">'+jsonArr[i].place+'</div>'+
			    '<table id="add">'+
			    '<tr id="addtr">'+
			    		'<td id="addjoin" class="addjoin"><a id="a2" href="sel2hdManage.html?sel=join&hdid='+jsonArr[i].hdid+'">参加者</a></td>'+
			    		'<td id="addlike" class="addlike"><a id="a2" href="sel2hdManage.html?sel=like&hdid='+jsonArr[i].hdid+'">收藏者</a></td>'+
			    		'<td id="hyjoin" class="hyjoin">参加好友：'+jsonArr[i].hyjoinnum+'</td>'+
			    		'<td id="hylike" class="hylike">喜欢好友：'+jsonArr[i].hylikenum+'</td>'+
			    		'<td id="addflag" class="addflag">'+jsonArr[i].addflag+'</td>'+
				    '<td id="addedit" class="addedit"><a class=a4 href="sel2hdActivity.html?hdid='+jsonArr[i].hdid+'">编辑</a></td>'+
				    '<td id="adddele" class="adddele">删除</td>'+
				'</tr>'+
				'</table>'+
			    '</div>'+
			'</div>'
		);
	}
}

$(document).ready(function(){
	$.post(postname,{
		"token":$.cookie("token"),
		"addswitname":$("#addswitname").val(),
		"addswitarea":$("#addswitarea").val(),
		"addswitclas":$("#addswitclas").val(),
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
		"addswitname":$("#addswitname").val(),
		"addswitarea":$("#addswitarea").val(),
		"addswitclas":$("#addswitclas").val(),
		"selnum":selnum,
		"sellength":sellength},function(data,status){
		createactivity(data,status);
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
				"addswitname":$("#addswitname").val(),
				"addswitarea":$("#addswitarea").val(),
				"addswitclas":$("#addswitclas").val(),
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


$(document).ready(function(){
	$("#selectactivity").on("click",".like",function(){
		var perthis=this;
		$.post("ServletLike",{
			"token":$.cookie("token"),
			"hdid":$(this).parents(".hdxx").find(".hdid").text()},function(data,status){
				var jsonObj = JSON.parse(data);
				myalert(jsonObj.text);
				if(jsonObj.num=="0"||jsonObj.num=="1"){
					$(perthis).parents(".hdxx").find(".likep").slideToggle();
					var num=parseInt($(perthis).parents(".hdxx").find("#likenum").text());
					if(jsonObj.num=="0"){
						num--;
					}else if(jsonObj.num=="1"){
						num++;
					}
					$(perthis).parents(".hdxx").find("#likenum").text(num);
				}
		});
	});
});

$(document).ready(function(){
	$("#selectactivity").on("click",".join",function(){
		var perthis=this;
		if($(this).parents(".hdxx").find("#joinp0").css("display")=="none"||
				parseInt($(this).parents(".hdxx").find("#joinnum").text())<
				parseInt($(this).parents(".hdxx").find("#maxpeo").text())){
			$.post("ServletJoin",{
				"token":$.cookie("token"),
				"hdid":$(this).parents(".hdxx").find(".hdid").text()},function(data,status){
					var jsonObj = JSON.parse(data);
					myalert(jsonObj.text);
					if(jsonObj.num=="0"||jsonObj.num=="1"){
						$(perthis).parents(".hdxx").find(".joinp").slideToggle();
						var num=parseInt($(perthis).parents(".hdxx").find("#joinnum").text());
						if(jsonObj.num=="0"){
							num--;
						}else if(jsonObj.num=="1"){
							num++;
						}
						$(perthis).parents(".hdxx").find("#joinnum").text(num);
					}
			});
		}else{
			myalert("报名人数已达上限！");
		}
	});
});

$(document).ready(function(){
	$("#selectactivity").on("click",".btna",function(){
		$(this).parents(".hdxx").find(".hdjj").slideToggle();
		$(this).parents(".hdxx").find(".btnb").fadeToggle();
	});
});
$(document).ready(function(){
	$("#selectactivity").on("click",".btnb",function(){
		$(this).parents(".hdxx").find(".hdjj").slideToggle();
		$(this).parents(".hdxx").find(".btnb").fadeToggle();
	});
});


//four tip switch
$(document).ready(function(){
	$("#jtip").click(function(){
		$("#jtipp").animate({opacity:'1'});
		$("#ltipp").animate({opacity:'0'});
		$("#ctipp").animate({opacity:'0'});
		$("#dtipp").animate({opacity:'0'});
		$("#fqhdpic").slideUp();
		postname="ServletMyJoin";
		selectActSel();
	})
	$("#ltip").click(function(){
		$("#jtipp").animate({opacity:'0'});
		$("#ltipp").animate({opacity:'1'});
		$("#ctipp").animate({opacity:'0'});
		$("#dtipp").animate({opacity:'0'});
		$("#fqhdpic").slideUp();
		postname="ServletMyLike";
		selectActSel();
	})
	$("#ctip").click(function(){
		$("#jtipp").animate({opacity:'0'});
		$("#ltipp").animate({opacity:'0'});
		$("#ctipp").animate({opacity:'1'});
		$("#dtipp").animate({opacity:'0'});
		$("#fqhdpic").slideDown();
		postname="ServletMyCreate";
		selectActSel();
	})
	$("#dtip").click(function(){
		$("#jtipp").animate({opacity:'0'});
		$("#ltipp").animate({opacity:'0'});
		$("#ctipp").animate({opacity:'0'});
		$("#dtipp").animate({opacity:'1'});
		$("#fqhdpic").slideUp();
		postname="ServletMyDelete";
		selectActSel();
	})
});

//ajax post method used by four tip switch
function selectActSel(){
	isbool=false;
	selnum=0;
	$("#selectactivity").empty();
	$.post(postname,{
		"token":$.cookie("token"),
		"addswitname":$("#addswitname").val(),
		"addswitarea":$("#addswitarea").val(),
		"addswitclas":$("#addswitclas").val(),
		"selnum":selnum,
		"sellength":sellength},function(data,status){
		createactivity(data,status);
		btnSwitch();
		isbool=true;
	});
}

//bottom switch
function btnSwitch(){
	if(postname=="ServletMyLike"||postname=="ServletMyJoin"){
		$(".addedit").hide();
		$(".adddele").hide();
		$(".addflag").fadeIn();
		
	}else if(postname=="ServletMyCreate"){
		$(".addedit").fadeIn();
		$(".adddele").text("删除").fadeIn();
		$(".addflag").hide();
	}else{
		$(".addedit").hide();
		$(".adddele").text("恢复").fadeIn();
		$(".addflag").hide();
	}
	if(postname=="ServletMyCreate"){
		$(".hylike").hide();
		$(".hyjoin").hide();
		$(".addlike").show();
		$(".addjoin").show();
	}else{
		$(".addlike").hide();
		$(".addjoin").hide();
		$(".hylike").show();
		$(".hyjoin").show();
	}
}

//delete and recover activity
$(document).ready(function(){
	$("#selectactivity").on("click",".adddele",function(){
		var perthis=this;
		$.post("ServletDelete",{
			"token":$.cookie("token"),
			"hdid":$(this).parents(".hdxx").find(".hdid").text()},function(data,status){
				var jsonObj = JSON.parse(data);
				myalert(jsonObj.text);
				$(perthis).parents(".hdxx").slideUp();
		});
	});
});

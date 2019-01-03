var selnum=0;
var sellength=2;
var isbool=false;

function createactivity(data,status){
	selnum+=sellength;
	appendActivity(data,"#selectactivity");
}
function appendActivity(data,container){
	//alert("数据: \n" + data);
	var jsonArr = JSON.parse(data);
	for(var i=0;i<jsonArr.length;i++){
		if(jsonArr[i].pic==undefined||jsonArr[i].pic=="null"){
			jsonArr[i].pic="pic/icon/defhdpic.svg";
		}
		var likedisplay=['style="display:inline"','style="display:none"'];
		var joindisplay=['style="display:inline"','style="display:none"'];
		if(jsonArr[i].liketemp==1){
			likedisplay=['style="display:none"','style="display:display"'];
		}
		if(jsonArr[i].jointemp==1){
			joindisplay=['style="display:none"','style="display:display"'];
		}
		$(container).append(
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
			    		'<td id="hyjoin">参加好友：'+jsonArr[i].hyjoinnum+'</td>'+
			    		'<td id="hylike">喜欢好友：'+jsonArr[i].hylikenum+'</td>'+
			    		'<td id="addflag">'+jsonArr[i].addflag+'</td>'+
				    '<td id="addedit">编辑</td>'+
				    '<td id="adddele">删除</td>'+
				'</tr>'+
				'</table>'+
			    '</div>'+
			'</div>'
		);
	}
}

$(document).ready(function(){
	$.post("ServletSelectActivity",{
		"token":$.cookie("token"),
		"addswitname":$("#addswitname").val(),
		"addswitarea":$("#addswitarea").val(),
		"addswitclas":$("#addswitclas").val(),
		"selnum":selnum,
		"sellength":sellength},function(data,status){
		createactivity(data,status);
		isbool=true;
	});
});

$(document).ready(function(){
	$("#addswitsubmit").click(function(){
		isbool=false;
		selnum=0;
		$("#selectactivity").empty();
		$.post("ServletSelectActivity",{
			"token":$.cookie("token"),
			"addswitname":$("#addswitname").val(),
			"addswitarea":$("#addswitarea").val(),
			"addswitclas":$("#addswitclas").val(),
			"selnum":selnum,
			"sellength":sellength},function(data,status){
			createactivity(data,status);
			isbool=true;
		});
	});
});

$(document).ready(function(){
	$(document).scroll(function(){
		if ($(document).scrollTop() >= $(document).height() - $(window).height()&&isbool==true) {
		//alert("滚动条已经到达底部为" + $(document).scrollTop());
			isbool=false;
			$.post("ServletSelectActivity",{
				"token":$.cookie("token"),
				"addswitname":$("#addswitname").val(),
				"addswitarea":$("#addswitarea").val(),
				"addswitclas":$("#addswitclas").val(),
				"selnum":selnum,
				"sellength":sellength},function(data,status){
					createactivity(data,status);
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
	$("#selectactivity,.selectRecommend").on("click",".like",function(){
		var perthis=this;
		$.post("ServletLike",{
			"token":$.cookie("token"),
			"hdid":$(this).parents(".hdxx").find(".hdid").text()},function(data,status){
				var jsonObj = JSON.parse(data);
				myalert(jsonObj.text);
				if(jsonObj.num=="0"||jsonObj.num=="1"){
					$(perthis).parents(".hdxx").find(".likep").toggle();
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
	$("#selectactivity,.selectRecommend").on("click",".join",function(){
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
						$(perthis).parents(".hdxx").find(".joinp").toggle();
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
	$("#selectactivity,.selectRecommend").on("click",".btna",function(){
		$(this).parents(".hdxx").find(".hdjj").slideToggle();
		$(this).parents(".hdxx").find(".btnb").fadeToggle();
	});
});
$(document).ready(function(){
	$("#selectactivity,.selectRecommend").on("click",".btnb",function(){
		$(this).parents(".hdxx").find(".hdjj").slideToggle();
		$(this).parents(".hdxx").find(".btnb").fadeToggle();
	});
});


//activity recommend
var recselnum=0;
var recsellength=2;
$(document).ready(function(){
	recselnum=0;
	selRecommend();
	$(".selectRecommendChange").click(function(){
		selRecommend();
	});
});
function selRecommend(){
	$(".selectRecommend").empty();
	$(".isHasRecommend").hide();
	$.post("ServletActivityRecommend",{
		"token":$.cookie("token"),
		"addswitname":$("#addswitname").val(),
		"addswitarea":$("#addswitarea").val(),
		"addswitclas":$("#addswitclas").val(),
		"selnum":recselnum,
		"sellength":recsellength},function(data,status){
		if(data!="[]"){
			recselnum+=recsellength;
		}else{
			recselnum=0;
			$(".isHasRecommend").show();
		}
		appendActivity(data,".selectRecommend");
	});
}
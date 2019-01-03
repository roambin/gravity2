function mchan(id,classn){
		idn=id.replace(/[^0-9]/ig,"");
		x=document.getElementsByClassName(classn+'p');
		y=document.getElementsByClassName(classn+'t');
		x[idn-1].style.display="none";
		y[idn-1].style.display="inline";
	}
	
function mchanbk(id,classn){
		idn=id.replace(/[^0-9]/ig,"");
		x=document.getElementsByClassName(classn+'p');
		y=document.getElementsByClassName(classn+'t');
		x[idn-1].style.display="inline";
		y[idn-1].style.display="none";
	}

function moreinf(id){
	idn=id.replace(/[^0-9]/ig,"");
	x=document.getElementsByClassName("hdjj");
	//alert(idn-1);
	x[idn-1].style.display="inline";
}
function moreinfbk(id){
	idn=id.replace(/[^0-9]/ig,"");
	x=document.getElementsByClassName("hdjj");
	//alert(idn-1);
	x[idn-1].style.display="none";
}
function returnid(){
	  return event.target.id;
}

//=========================


function plslogin(){
	alert("请登入");
}

function imgPreview(fileDom){
    //判断是否支持FileReader
    if (window.FileReader) {
        var reader = new FileReader();
    } else {
        alert("您的设备不支持图片预览功能，如需该功能请升级您的设备！");
    }

    //获取文件
    var file = fileDom.files[0];
    var imageType = /^image\//;
    //是否是图片
    if (!imageType.test(file.type)) {
        alert("请选择图片！");
        return;
    }
    //读取完成
    reader.onload = function(e) {
        //获取图片dom
        var img = document.getElementById("preview");
        //图片路径设置为读取的图片
        img.src = e.target.result;
    };
    reader.readAsDataURL(file);
}

function ckpicfun(){
	var img = document.getElementById("preview");
	var ckpic=document.getElementById("ckpic");
	if (!img.src||img.src==defsrc) {
		ckpic.value=0;
    }
	else{
		ckpic.value=1;
	}
}

function perswitch(id){
	var perinf=document.getElementById("perinf");
	var perpass=document.getElementById("perpass");
	if(id=="perinf"){
		perinf.style.display="inline";
		perpass.style.display="none";
	}
	else if(id=="perpass"){
		perinf.style.display="none";
		perpass.style.display="inline";
	}
}
//=============
function ljfun(num,sel,i){
	if(sel==0){
		document.getElementById("likehdid").value=num;
		var form = document.getElementById("servletLike");
	}
	else{
		document.getElementById("joinhdid").value=num;
		var form = document.getElementById("servletJoin");
	}
	var joint = document.getElementById("joint"+i).innerHTML;
	var addflag= document.getElementById("addflag"+i).innerHTML;
	var joinnum=document.getElementById("joinnum"+i).innerHTML;
	var maxpeo=document.getElementById("maxpeo"+i).innerHTML;
	joinnum=Number(joinnum);
	maxpeo=Number(maxpeo);
	//alert(joinnum+"|"+maxpeo);
	if(sel==1&&joint=="已报名"&&addflag=="拒绝"){
		alert("活动申请被拒绝，无法重复报名操作");
	}
	else if(sel==1&&joint=="未报名"&&joinnum>=maxpeo){
		alert("人数已满，无法报名");
	}
	else{
		form.submit();
	}
}
function bjcjfun(num,sel){
	document.getElementById("hide").value=num;
	document.getElementById("hidesel").value=sel;
	//alert(document.getElementById("hide").value);
	var form = document.getElementById("hideform");
	form.submit();
}
function bjtjhyfun(num,sel,userid,addflag){
	if(num==userid)	alert("不可添加自己为好友");
	else if(addflag!="未添加")	alert("不可重复添加");
	else{
		document.getElementById("hide").value=num;
		document.getElementById("hidesel").value=sel;
		//alert(document.getElementById("hide").value);
		var form = document.getElementById("hideform");
		form.submit();
	}
}
function bjhyfun(num,sel,userid,addflag){
	if(num!=userid&&addflag=="待审")	alert("等待对方同意");
	else if(num!=userid&&addflag=="拒绝")	alert("已被拒绝");
	else if(num!=userid)	alert("你是申请人，不可执行此操作");
	else{
		document.getElementById("hide").value=num;
		document.getElementById("hidesel").value=sel;
		//alert(document.getElementById("hide").value);
		var form = document.getElementById("hideform");
		form.submit();
	}
}
function delfun(num,sel){
	var con;
	if(sel==1)	con=confirm("删除本活动？");
	else if(sel==2)	con=confirm("彻底删除本活动？");
	else if(sel==3)	con=confirm("恢复本活动？");
	if(con==true){
		document.getElementById("hide1").value=num;
		document.getElementById("hidesel1").value=sel;
		//alert(document.getElementById("hide1").value);
		var form = document.getElementById("hideform1");
		form.submit();
	}
}


function addflagcol(sel,num,idn){
	var addflag=document.getElementById(idn+num);
	if(sel=="待审"){
		addflag.style.backgroundColor = "#f1930d";
	}else if(sel=="同意"){
		addflag.style.backgroundColor = "#3bad5e";
	}else if(sel=="拒绝"){
		addflag.style.backgroundColor = "#d85b5b";
	}else{
		addflag.style.backgroundColor = "#616161";
	}
}

function validate_required(field,alerttxt){
		var value=document.getElementById(field).value;
		if (value==null||value==""){
			alert(alerttxt);
			document.getElementById(field).focus();
			return false
		}
		else{
			return true
		}
}
function cksub(){
		if (document.getElementById("preview").src=="")	{alert("请添加图片");	return false}
		if (validate_required("hdm","请填写活动名")==false)	return false
		if (validate_required("stime","请填写活动开始时间")==false)	{document.getElementById("hdm").focus();	return false}
		if (validate_required("etime","请填写活动结束时间")==false)	{document.getElementById("hdm").focus();	return false}
		if (validate_required("place","请填写活动地点")==false)	return false
		if (validate_required("shdjj","请填写一句话简介")==false)	return false
		if (validate_required("hdjj","请填写活动简介")==false)	return false
		if (validate_required("classify","请填写活动分类")==false)	return false
		stime=document.getElementById("stime");
		etime=document.getElementById("etime");
		if (stime.value>etime.value){
			alert("活动开始时间不可晚于结束时间");
			return false
		}
		else if (stime.value==etime.value){
			alert("活动开始时间不可等于结束时间");
			return false
		}
		timeflag=document.getElementById("timeflag");
		var mydate = new Date();//获取系统当前时间
		var now=mydate.getFullYear()+"-"+(mydate.getMonth()+1)+"-"+mydate.getDate()+" "+mydate.getHours()+":"+mydate.getMinutes()+":"+mydate.getSeconds();
		if(now<stime.value)	timeflag.value="预热";
		else if(now>stime.value&&now<etime.value)	timeflag.value="进行";
		else if(now>etime.value)	timeflag.value="结束";
}
function cksubper(){
	if (document.getElementById("preview").src=="")	{alert("请添加图片");	return false}
	if (validate_required("nname","请填写昵称")==false)	return false
	if (validate_required("name","请填写姓名")==false)	return false
	if (validate_required("email","请填写邮箱")==false)	return false
	if (validate_required("area","请填写地区")==false)	return false
	if (validate_required("adress","请填写地址")==false)	return false
	if (validate_required("job","请填写职业")==false)	return false
}
function ckinfper(nameflag){
	if (nameflag==0){
		alert("请先完善个人信息");
		return false
	}
}

function cknowpeo(nowpeo,maxpeo){
	if(nowpeo>=maxpeo){
		alert("人数已满，无法报名。");
		return false;
	}
	else{
		return true;
	}
}

function selectActivity(){
	var form = document.getElementById("selectActivity");
	form.submit();
}

//----new------
//set header
$("#loginCheck").ready(function(){
	$("#tximg").hide();
	$("#hyuser").hide();
	if($("#zxl").length>0) {
		$.post("ServletGetLogin",{
			"token":$.cookie("token")},function(data,status){
			if(data=="filter:not access"||data=="filter:time out"){
				$("body").empty();
				if(data=="filter:time out"){
					myalert("已超时，请重新登入");
				}else{
					myalert("请登入");
				}
				setTimeout(function(){
					window.location.href="sel1syNoid.html";
				}, 1500);
			}
			var jsonObj=JSON.parse(data);
			$("#tximg").attr('src',jsonObj.headpic).show();
			if(jsonObj.nname!=null){
				$("#hyuser").text("欢迎："+jsonObj.nname).show();
			}else{
				$("#hyuser").text("欢迎："+jsonObj.username).show();
			}
		});
	}
});
//logout
$(document).ready(function(){
	$("#zx").click(function(){
		$.cookie("token",null);
	});
});

//my js alert
function myalert(text){
	$("#myalert").remove();
	$("html").append('<div id="myalert"><div id="myalerttext"></div></div>');
	$("#myalerttext").html(text);
	$("#myalert").fadeIn();
	setTimeout(function(){
		$("#myalert").fadeOut();
	}, 1000);
}

//get parameters send by get
function getParameter(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null){
    	 	return  unescape(r[2]); 
     }
     return null;
}

var ipAdress="localhost:8080";
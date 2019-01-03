$(document).ready(function(){
	$("#readperinf").css("border-color","#ff4672");
	$("#perpass").hide();
	$("#preview,#nname,#name,#emai,#area,#adress,#job").prop("readonly",true);
	//$(".myInputFileBtn").hide();
	$(".mybtn").hide();
	$(".picFloatInfo").hide();
	$("#readperinf").click(function(){
		$("#readperinf").css("border-color","#ff4672");
		$("#editperinf").css("border-color","#ffffff");
		$("#editperpass").css("border-color","#ffffff");
		$("#perinf").slideDown()
		$("#perpass").slideUp();
		$("input").prop("readonly",true);
		//$(".myInputFileBtn").fadeOut();
		$(".mybtn").fadeOut();
		$(".picFloatInfo").fadeOut();
		$(".preview,.picFloatInfo").off("click");
		$("#myInputHeadPerinf").text("个人信息").animate({left:"4%"},80).animate({left:"8%"},160).animate({left:"6%"},80);
	});
	$("#editperinf").click(function(){
		$("#readperinf").css("border-color","#ffffff");
		$("#editperinf").css("border-color","#ff4672");
		$("#editperpass").css("border-color","#ffffff");
		$("#perinf").slideDown()
		$("#perpass").slideUp();
		$("input").prop("readonly",false);
		//$(".myInputFileBtn").fadeIn();
		$(".mybtn").fadeIn();
		$(".picFloatInfo").fadeIn();
		$(".preview,.picFloatInfo").on("click",function(){
			$(".myInputFileBtn").click();
		});
		$("#myInputHeadPerinf").text("编辑个人信息").animate({left:"1%"},200).animate({left:"10%"},400).animate({left:"6%"},200);
	});
	$("#editperpass").click(function(){
		$("#readperinf").css("border-color","#ffffff");
		$("#editperinf").css("border-color","#ffffff");
		$("#editperpass").css("border-color","#ff4672");
		$("#perinf").slideUp();
		$("#perpass").slideDown();
		$("#passSubmit").show();
	});
});

function updatePerInfo(data,status){
	//alert("数据: \n" + data + "\n状态: " + status);
	var json = JSON.parse(data);
	$("#preview").attr('src',json.headpic);
	$("#nname").val(json.nname);
	$("#name").val(json.name);
	$("#email").val(json.email);
	$("#area").val(json.area);
	$("#adress").val(json.adress);
	$("#job").val(json.job);
}
function updatePerInfoHeadpic(){
	var formData=new FormData();
	var imgFileId=document.getElementById("headpic");
	var imgFile=imgFileId.files[0];
	formData.append("imgFile",imgFile);
	formData.append("token",$.cookie("token"));
	$.ajax({
		url:"ServletEditPersonalInfoHeadpic",
		type:"POST",
		data:formData,
		async:false,
		processData:false,
		contentType:false,
		success:function(data,status){
			var json = JSON.parse(data);
			if(json.num=="1"){
				myalert("修改成功");
				$("#tximg").attr('src',json.path);
			}else{
				myalert("修改失败，请稍候再试");
			}
		}
	});
}

$(document).ready(function(){
	$.post("ServletEditPersonalInfo",{
		"token":$.cookie("token"),
		"action":"select"},function(data,status){
		updatePerInfo(data,status);
	});
	$("#readperinf,#editperinf").click(function(){
		$.post("ServletEditPersonalInfo",{
			"token":$.cookie("token"),
			"action":"select"},function(data,status){
			updatePerInfo(data,status);
		});
	});
	$("#perinfSubmit").click(function(){
		$.post("ServletEditPersonalInfo",{
			"token":$.cookie("token"),
			"nname":$("#nname").val(),
			"name":$("#name").val(),
			"email":$("#email").val(),
			"area":$("#area").val(),
			"adress":$("#adress").val(),
			"job":$("#job").val(),
			"action":"update"},function(data,status){
			var json = JSON.parse(data);
			if(json.num=="1"){
				myalert("修改成功");
				$("#hyuser").text("欢迎："+$("#nname").val());
			}else{
				myalert("修改失败，请稍候再试");
			}
		});
	});
});

//pic upload check
$(document).ready(function(){
	$("#picSubmitBtn").click(function(){
		var fileCheck=false;
		if($(".myInputFileBtn").val()!=""){
			fileCheck=true;
		}else{
			myalert("请上传合适的图片");
		}
		if(fileCheck==true){
			updatePerInfoHeadpic();
		}
	});
});

//change password
$(document).ready(function(){
	$("#passSubmit").click(function(){
		if($("#oldpass").val()==""||$("#newpass").val()==""){
			myalert("请输入密码");
		}else if($("#newpass").val()!=$("#connewpass").val()){
			myalert("两次密码不一致");
		}else{
			$.post("ServletEditPassword",{
				"token":$.cookie("token"),
				"password":$("#oldpass").val(),
				"newPassword":$("#newpass").val()},function(data,status){
				var json = JSON.parse(data);
				if(json.num=="1"){
					myalert("修改成功");
				}else if(json.num=="-1"){
					myalert("密码错误");
				}else{
					myalert("修改失败，请稍候再试");
				}
			});
		}
	});
});
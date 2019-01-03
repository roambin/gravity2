var hdid=null;
function updateActivity(data,status){
	//alert("数据: \n" + data + "\n状态: " + status);
	var json = JSON.parse(data);
	$("#preview").attr('src',json.pic);
	$("#hdm").val(json.hdm);
	$("#stime").val(json.stime);
	$("#etime").val(json.etime);
	$("#place").val(json.place);
	$("#shdjj").val(json.shdjj);
	$("#hdjj").val(json.hdjj);
	$("#classifySel").val(json.classify);
	$("#maxpeo").val(json.maxpeo);
	$("#crashflagSel").val(json.crashflag);
}
function updatePerInfoHeadpic(){
	var formData=new FormData();
	var imgFileId=document.getElementById("pic");
	var imgFile=imgFileId.files[0];
	formData.append("imgFile",imgFile);
	formData.append("token",$.cookie("token"));
	formData.append("hdid",hdid);
	$.ajax({
		url:"ServletEditActivityPic",
		type:"POST",
		data:formData,
		async:false,
		processData:false,
		contentType:false,
		success:function(data,status){
			var json = JSON.parse(data);
			if(json.num=="1"){
				myalert("提交成功");
			}else{
				myalert("提交失败，请稍候再试");
			}
		}
	});
}

$(document).ready(function(){
	hdid=getParameter("hdid");
	if(hdid==undefined||hdid=="null"||hdid==""){
		$(".myInputHead").text("创建活动");
		$("#actSubmit").text("创建活动");
	}
	if(hdid!=undefined&&hdid!=""&&hdid!="null"){
		$.post("ServletEditActivity",{
			"token":$.cookie("token"),
			"hdid":hdid,
			"action":"select"},function(data,status){
			updateActivity(data,status);
		});
	}
	$("#actSubmit").click(function(){
		var isFull=true;
		var checkArr=new Array("hdm","stime","etime","place","shdjj","hdjj","classify","maxpeo","crashflag");
		for(i in checkArr){
			isFull=isFull&&($("#"+checkArr[i]).val()!="")
		}
		if(isNaN($("#maxpeo").val())){
			myalert("最大人数应是数字");
			return;
		}
		if(isFull){
			$.post("ServletEditActivity",{
				"token":$.cookie("token"),
				"hdid":hdid,
				"hdm":$("#hdm").val(),
				"stime":$("#stime").val(),
				"etime":$("#etime").val(),
				"place":$("#place").val(),
				"shdjj":$("#shdjj").val(),
				"hdjj":$("#hdjj").val(),
				"classify":$("#classifySel").val(),
				"maxpeo":$("#maxpeo").val(),
				"crashflag":$("#crashflagSel").val(),
				"action":"update"},function(data,status){
				var json = JSON.parse(data);
				if(json.num=="1"){
					myalert("提交成功");
					if(hdid==undefined||hdid=="null"||hdid==""){
						hdid=json.hdid;
						setTimeout(function(){
							window.location.href="sel2hdActivity.html?hdid="+json.hdid;
						}, 1200);
					}
				}else{
					myalert("提交失败，请稍候再试");
				}
			});
		}else{
			myalert("请填写完整");
		}
	});
});

//click hide file button when click image
$(document).ready(function(){	
	$(".preview,.picFloatInfo").click(function(){
		$(".myInputFileBtn").click();
	});
});

//can upload image only when hdid exists
$(document).ready(function(){
	$("#picSubmitBtn").click(function(){
		if(hdid==undefined||hdid=="null"||hdid==""){
			myalert("请先创建活动，再上传图片");
		}else{
			var fileCheck=false;
			if($(".myInputFileBtn").val()!=""){
				fileCheck=true;
			}else{
				myalert("请上传合适的图片");
			}
			if(fileCheck==true){
				updatePerInfoHeadpic();
			}
		}
	});
});
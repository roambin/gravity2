
$(document).ready(function(){
	var token=$.cookie("token");
	if(token==undefined||token=="null"||token==""){
		firstComeAnimate();
		setTimeout(function(){
			window.location.href="sel1syNoid.html?";
		}, 1500);
	}else{
		window.location.href="sel1sy.html?";
	}
});

var selestial1css="display:hidden;position:fixed;top:49%;left:49%;z-index:500;maxWidth:1%;"
var selestial2css="display:hidden;position:fixed;top:90%;left:100%;z-index:501;maxWidth:1%;"
var wordCn="display:hidden;position:fixed;top:5%;left:100%;z-index:503;color:#3a5399; text-align:left; font-size:18vmin; font-family:微软雅黑;white-space:nowrap;"
var wordEn="display:hidden;position:fixed;top:20%;left:100%;z-index:499;color:#f68909; text-align:left; font-size:24vmin; font-family:微软雅黑;"
function firstComeAnimate(){
	$("body").append(
			'<img id="selestial1" class="selestial" style="'+selestial1css+'" alt="🌏" src="pic/system/celestial1.svg">'+
			'<img id="selestial2" class="selestial" style="'+selestial2css+'" alt="🌕" src="pic/system/celestial2.svg">'+
			'<div id="wordCn" style="'+wordCn+'">引力</div>'+
			'<div id="wordEn" style="'+wordEn+'">gravity</div>');
	$("body").ready(function(){
		  var pic1=$("#selestial1");
		  var pic2=$("#selestial2");
		  var wordCn=$("#wordCn");
		  var wordEn=$("#wordEn");
		  pic1.animate({maxWidth:'50%',maxWidth:'50%',top:"0%",left:"10%"},1500);
		  pic1.rotate({angle:0,animateTo:240,duration:1500});
		  pic2.animate({maxWidth:'20%',top:"50%",left:"54%"},1000);
		  pic2.rotate({angle:0,animateTo:390,duration:1500});
		  wordCn.animate({top:"10%",left:"60%"},800).animate({top:"10%"},200).animate({fontSize:"26vmin"},200);
		  wordEn.animate({top:"25%",left:"60%"},600).animate({fontSize:"24vmin"},400).animate({fontSize:"22vmin"},200);
	});
}
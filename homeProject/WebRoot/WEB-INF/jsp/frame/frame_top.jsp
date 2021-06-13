<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0"  />
	<title></title>
	<script type="text/javascript" src="${resourceRoot}/js/jquery.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/iss.core.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/iss.cssTable.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/iss.panelBar.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/iss.pageBar.js"></script>
	<script type="text/javascript" src="${resourceRoot}/artDialog/source/artDialog.js"></script>

	
<style type="text/css">
<!--
*{ margin:0px; padding:0px;}
body {margin: 0px;}
li{list-style-type: none;}
.top{ background:#1c60c3 url(${resourceRoot}/images/top_07.gif) repeat-x; height:50px; margin-bottom:8px;}
.top_left{float:left; background:url(${resourceRoot}/images/top_03.gif); width:390px; height:50px;}
.top_right{float:right;}
.top_right li{ float:left; margin-right:5px; height:50px;}
.top_right li span{margin-right:30px;}
.w_txt{ font:12px/50px Arial, Helvetica, sans-serif; color:#f6f6f6;}
-->
</style>
<script type="text/javascript">
<!--
function MM_swapImgRestore() { //v3.0
	var i,x,a=document.MM_sr; 
	for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
	var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; 
	for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
	var p,i,x;  
	if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);
	}

	if(!(x=d[n])&&d.all) x=d.all[n]; 
	for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
	for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
	if(!x && d.getElementById) x=d.getElementById(n);
	return x;
}

function MM_swapImage() { //v3.0
	var i,j=0,x,a=MM_swapImage.arguments; 
	document.MM_sr=new Array; 
	for(i=0;i<(a.length-2);i+=3)

	if ((x=MM_findObj(a[i]))!=null){
		document.MM_sr[j++]=x; 
		if(!x.oSrc) x.oSrc=x.src; 
		x.src=a[i+2];
	}
}



function changePassword(){
	window.parent.frames["mainFrame"].location = "${ctx }/czybm/goChangePassword"
}
function index(){
	window.parent.frames["mainFrame"].location = "${ctx }/frame/goRight"
}
function update(){
	$.fnShowWindow_mid("${ctx}/czybm/goUpdate");
}
function goto_query(){
}
function loginOut(){
	$.ajax({
		    url: "${ctx}/loginOut",
		    type: 'post',
		    data: null,
		    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		   	dataType: 'json',
		    error: function(){
		        alert('用户退出系统出错~');
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		window.top.location.href="${ctx}";
		    	}else{
		    		 alert('用户退出系统出错~');
		    	}
			}
		});
}


//-->
</script>

</head>
<body style="background:#eff5fe;">

<div class="top" >
	<div class="top_left"></div>
	<div class="top_right">
	<ul>
		<li class="w_txt"><a href="javascript:void(0)" onclick="update();">
		  <font color="red">${userName }</font></a>&nbsp;&nbsp;&nbsp;&nbsp;您好</li>
		  &nbsp;&nbsp;
		  <li>
			<a href="javascript:void(0)" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image4','','${resourceRoot}/images/top2_02.gif',1)">
			<img src="${resourceRoot}/images/top_02.gif" name="Image4" width="61" height="50" border="0" id="Image4" onclick="index();"/></a>
		</li>
		<li>
			<a href="javascript:void(0)" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image2','','${resourceRoot}/images/top2_05.gif',1)">
			<img src="${resourceRoot}/images/top_05.gif" name="Image2" width="79" height="50" border="0" id="Image2" onclick="changePassword();"/></a>
		</li>
		<li>
			<a href="javascript:void(0)" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image3','','${resourceRoot}/images/top2_08.gif',1)">
			<img src="${resourceRoot}/images/top_08.gif" name="Image3" width="61" height="50" border="0" id="Image3" onclick="loginOut();"/></a>
		</li>
		<li>
		&nbsp;
		</li>
	</ul>
  </div>
</div>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
	UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
	if(uc == null || StringUtils.isBlank(uc.getCzybh())){
		response.setHeader("Location", request.getContextPath());
		response.sendRedirect(request.getContextPath());
	}
%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <title>${platformName}</title>
    <link href="${resourceRoot}/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" /> 
    <link href="${resourceRoot}/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <link href="${resourceRoot}/jgrowl/jquery.jgrowl.css" rel="stylesheet" type="text/css" />
    <link href="${resourceRoot}/artDialog/skins/chrome.css" rel="stylesheet" />
    <link href="${resourceRoot}/jquery-ui-1.11.4.custom/jquery-ui.min.css" type="text/css" rel="stylesheet"/>
    <link href="${resourceRoot}/css/main_search.css?v=${v}" type="text/css" rel="stylesheet"/><%-- add by zb on 20191203 --%>
    <script type="text/javascript" src="${resourceRoot}/js/jquery.min.js"></script>
	<%-- <script src="${resourceRoot}/jquery/jquery-1.11.3.min.js" type="text/javascript"></script> --%>
	<script src="${resourceRoot}/jquery-ui-1.11.4.custom/jquery-ui.min.js" type="text/javascript"></script>
    <script src="${resourceRoot}/ligerUI/js/ligerui.all.js" type="text/javascript"></script> 
    <%-- <script src="${resourceRoot}/zTree/js/jquery.ztree.all-3.1.js" type="text/javascript"></script> --%>
    <script src="${resourceRoot}/zTree/3.5.41/mainPageSearch/js/jquery.ztree.all-3.1.js" type="text/javascript"></script>
    <script src="${resourceRoot}/zTree/3.5.41/js/jquery.ztree.exhide.js" type="text/javascript"></script>
    <script src="${resourceRoot}/zTree/3.5.41/mainPageSearch/js/fuzzysearch.js" type="text/javascript"></script>
    <script type="text/javascript" src="${resourceRoot}/js/iss.core.js"></script>
    <script type="text/javascript" src="${resourceRoot}/artDialog/source/artDialog.js"></script>
    <script type="text/javascript" src="${resourceRoot}/jgrowl/jquery.jgrowl.js"></script>
    <script src="${resourceRoot}/jqGrid/5.0.0/js/global.js?v=${v}" type="text/javascript"></script>
    <style type="text/css">

div.jGrowl div.manilla {
				background-color: 		#FFF1C2;
				color: 					navy;
			}
		</style>
		
<script type="text/javascript">
var flag_loginOut = 0;
var tab = null;
var accordion = null;
var tabNum = 0;
var emailCount = 0;
var timeout = 60000;
var titleMsg ="有家装饰ERP平台";
var setting = {
	view: {
		dblClickExpand: false,
		showLine: true,
		selectedMulti: false,
		expandSpeed:""
	},
	
	data: {
		simpleData: {
			enable:true,
			idKey: "menuId",
			pIdKey: "parentId"
		},
		key:{
			name: "menuName"
		}
	},

	callback: {
		onClick: function(event, treeId, treeNode){
			var openType = treeNode.openType; //打开类型 
			var menuType = treeNode.menuType; //菜单类型
			var sUrl = "${ctx}" + treeNode.menuUrl;

			if(menuType == "${MENU_TYPE_URL }"){
				if(openType && openType == "${MENU_OPEN_TYPE_INNER }"){
                	f_addTab(treeNode.menuId, treeNode.menuName, sUrl);
				}else if(openType && openType == "${MENU_OPEN_TYPE_FULL }"){
					$.fnShowWindow_mid(sUrl);
				}
			}else if(menuType == "${MENU_TYPE_FOLDER }"){
				zTree = $.fn.zTree.getZTreeObj(treeId);
				zTree.expandNode(treeNode, !treeNode.open);
			}
			//关闭搜索结果tab add by zb on 20200430
			closeSearchResult();
		}
	}
};

//模块搜索 zb
var settingSearch = {
	view: {
		dblClickExpand: false,
		showLine: false,
		selectedMulti: false,
		expandSpeed:"",
		/*
		设置CSS属性
		treeId:对应 zTree 的 treeId
		treeNode:被单击的节点 JSON 数据对象
		*/
		fontCss: function (treeId, treeNode) {
			var menuType = treeNode.menuType;
			return menuType!="${MENU_TYPE_URL}"?{cursor: "not-allowed"}:{};
		}
	},
	data: {
		simpleData: {
			enable:true,
			idKey: "menuId",
			pIdKey: "parentId",
		},
		key:{
			name: "menuName",
			title: "menuType"
		}
	},
	callback: {
		onClick: function(event, treeId, treeNode){
			var openType = treeNode.openType; //打开类型 
			var menuType = treeNode.menuType; //菜单类型
			var sUrl = "${ctx}" + treeNode.menuUrl;
			treeNode.menuName = treeNode.menuName
				.replace(/<span style="color: whitesmoke;background-color: darkred;">/g, "")
				.replace(/<\/span>/g, "");

			if(menuType == "${MENU_TYPE_URL }"){
				if(openType && openType == "${MENU_OPEN_TYPE_INNER }"){
	            	f_addTab(treeNode.menuId, treeNode.menuName, sUrl);
				}else if(openType && openType == "${MENU_OPEN_TYPE_FULL }"){
					$.fnShowWindow_mid(sUrl);
				}
				closeSearchResult();
			}/*else if(menuType == "${MENU_TYPE_FOLDER }"){	//搜索结果中父文件夹不做展开操作
				zTree = $.fn.zTree.getZTreeObj(treeId);
				zTree.expandNode(treeNode, !treeNode.open);
			}*/
		},
		/*
		点击前判断是否可以点击
		treeId:对应 zTree 的 treeId
		treeNode:被单击的节点 JSON 数据对象
		clickFlag:节点被点击后的选中操作类型,详细请看：http://www.treejs.cn/v3/api.php
		*/
		beforeClick: function (treeId, treeNode, clickFlag) {
			var menuType = treeNode.menuType;
			return !(menuType == "${MENU_TYPE_FOLDER }" || menuType == "${MENU_TYPE_TAB}");
		}
	}
};
var	searchInputOpen=false, //搜索输入是否打开 add by zb
	searchResultOpen=false; //搜索结果是否打开
$(function (){
	$("#layout1").ligerLayout({ leftWidth: 190, height: '100%',heightDiff:-5 ,space:4, onHeightChanged: f_heightChanged });
	var height = $(".l-layout-center").height();
	$("#framecenter").ligerTab({ height: height });
	$("#accordion1").ligerAccordion({ height: height - 24, speed: null, changeHeightOnResize: true});
	$(".l-link").hover(function (){
		$(this).addClass("l-link-over");
	}, function (){
		$(this).removeClass("l-link-over");
	});
	tab = $("#framecenter").ligerGetTabManager();
	accordion = $("#accordion1").ligerGetAccordionManager();
	
	${js}
	// $("#pageloading").hide(); //移到下面再隐藏 modify by zb on 20191205
	
	var h=parseInt($("#div_${firstTabId }").css("height"))-12;
	$("#layout1 div ul[class='ztree']").css("height" , h);
	
	if (${mmLength}<6 || ${isPasswordExpired}){
		changePassword();
	}
	//搜索触发 add by zb on 20191203
	$("div .l-scroll.l-accordion-content").css("height", h+36);	//去掉因为多一个搜索框存在的间隙
	$("#layout1 div ul[class='ztree']").css("height" , h+24);
	$(".l-accordion-header-inner:contains('搜索结果')").parent().remove();
	$("#div_search").css("display", "block").toggleClass("close");
	$("#layout1 .l-layout-header-inner").empty().append(
		"<input title='模块搜索' type='text' class='search-input-inner' "+
		"	id='searchInputInner' placeholder='搜索模块...'>"+
		"<span class='clear-span-inner' id='clearSpanInner'>"+
		"	<img class='search-clear-img-inner' id='searchImgInner' "+
		"		src='${ctx}/images/main_search_blue.png'>"+
		"</span>");
	$("#pageloading").hide();
	//搜索按钮图片颜色标识
	var isImgBlue = true;
	//旧版本zTree不会默认展开，所以先全部展开，避免模糊搜索出错
	var treeObj = $.fn.zTree.getZTreeObj("tab_search_inner");
	treeObj.expandAll(true);
	//初始化模糊搜索方法
	fuzzySearch("tab_search_inner","#searchInputInner",null,null);
	$("#searchInputInner").click(function () {
		showSearchResult();
	});
	$("#searchInputInner").bind("input propertychange", function() {
		if ("" != $(this).val() && !searchResultOpen) {
			isShowResult();
		}
	});
	/*$("#searchInputInner").click(function () {
		showSearchResult();
		$(this).bind("focus keyup",function(){
			if($(this).val() != ""){
				$("#clearSpanInner").css("display","block");
			} else {
				$("#clearSpanInner").css("display","none");
			}
		});
	});*/
	//清楚按钮功能改为关闭搜索
	$("#clearSpanInner").click(function () {
		if (!searchResultOpen) {
			showSearchResult();
		} else {
			closeSearchResult();
		}
	});
	/*$("#searchInputInner").blur(function () {
		var isClickClearSpanInner = false;
		$("#clearSpanInner").click(function () {
			showSearchResult();
			$("#searchInputInner").focus();
			isClickClearSpanInner = true;
		});
		//通过添加延迟，解决清除按钮点击后同步执行的问题，也解决关闭太快无法点击模块的问题
		setTimeout(function () {
			if(searchResultOpen && !isClickClearSpanInner) closeSearchResult();
		}, 100);
	});*/
});
// 搜索方法 begin add by zb
//搜索按键触发
$(document).keydown(function(event){  
	if(event.keyCode == 27){ //esc 
		closeSearchResult();
	}
}); 
//开始搜索初始化
function searchResultInit() {
	$("#searchInputInner").val("");
}
//搜索开关关联开关搜索结果
function isOpenResult() {
	if (searchInputOpen && !searchResultOpen) {
		showSearchResult();
	} else if (!searchInputOpen && searchResultOpen) {
		closeSearchResult();
	}
}
//显示搜索结果
function showSearchResult() {
	searchResultInit();
	$("#searchImgInner").prop("src", "${ctx}/images/main_search_clear.png");
	// $("#clearSpanInner").css("display","none");
	if (!searchResultOpen || "none" == $("#div_search").css("display")) isShowResult();
}
//关闭搜索结果
function closeSearchResult() {
	searchResultInit();
	$("#searchImgInner").prop("src", "${ctx}/images/main_search_blue.png");
	// $("#clearSpanInner").css("display","block");
	if (searchResultOpen) isShowResult();
}
//开关搜索结果
function isShowResult() {
	searchResultOpen=!searchResultOpen;
	$("#div_search").css("display", "block").toggleClass("close");
};
// 搜索方法 end add by zb

function f_heightChanged(options){
	if(tab)
		tab.addHeight(options.diff);
	if(accordion && options.middleHeight - 24 > 0)
		accordion.setHeight(options.middleHeight - 24);
	var h=parseInt($("#div_${firstTabId }").css("height"))-12;
	$("#layout1 div ul[class='ztree']").css("height" , h);
	//去掉因为多一个搜索框存在的间隙 add by zb
	$("div .l-scroll.l-accordion-content").css("height", h+36);
	$("#layout1 div ul[class='ztree']").css("height" , h+24);
}

function f_addTab(tabid,text, url){
    
	if(tab.isTabItemExist(tabid)){
        tab.selectTabItem(tabid);
    	//tab.reload(tabid);
    }else{
    	if(tab.getTabItemCount() >= 11){
    		tab.removeTabItem(tab.getTabidList()[1]);
	    	//art.dialog({content: '你打开的窗口太多，请先关闭一些再打开！',lock: true});
	    	//return ;
	    }
	   	tab.addTabItem({ tabid : tabid,text: text, url: url });
    }
}

function loginOut(){
	if (flag_loginOut!=0) return;
	$.ajax({
		    url: "${ctx}/admin/loginOut",
		    type: 'post',
		    data: {},
		    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		   	dataType: 'json',
		    error: function(){
		        window.location.href="${ctx}";
		        var url = window.location.href;
		        if (url.indexOf("https") == 0) {
		    		url = url.replace("/admin/main","/login.jsp");
		    		window.location.replace(url);
		    	}
		    },
		    success: function(obj){
		    	flag_loginOut++;
		    	window.location.href="${ctx}";
		    	var url = window.location.href;
		    	if (url.indexOf("https") == 0) {
		    		url = url.replace("/admin/main","/login.jsp");
		    		window.location.replace(url);
		    	}
			}
		});
}

function changePassword(){
	Global.Dialog.showDialog("changePassword",{
		  title:"修改密码",
		  url:"${ctx}/admin/czybm/goChangePassword?mmLength=${mmLength}&&isPasswordExpired=${isPasswordExpired}",
		  height:350,
		  width:700,
		  open: function (event, ui) {
			if (${mmLength}<6 || ${isPasswordExpired}){
				$(".ui-dialog-titlebar-close", $(this).parent()).hide();
			}
		  }
		});
}

var flag = 0;
//函数体
//函数功能是将标题设置为“欢迎光临我的网站”，将第执行一次将最前面的一个字减去，当全部减完重新赋值
var str = titleMsg.split("");
function funTitle() {
  //将第一个元素加到未尾
  //str.push(str[0]);
  //清除第一个元素
  if(flag==0){
	  document.title = '有家装饰ERP平台'; 
  }else{
 	str.shift();
  	document.title = str.join('');
  	if (str.length <= 0)
  	{
  	    str = titleMsg.split("");
  	    document.title = str.join('');
 	 }
  }
  setTimeout("funTitle()",1000); 
}
function sendMessage(){
	if("${USER_CONTEXT_KEY.czylb}"=="2"){
		$.ajax({
			    url: "${ctx}/admin/supplierItemPreMeasure/sendMessage",
			    type: 'post',
			    data: {},
			    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			   	dataType: 'json',
			    error: function(){
				
			    },
			    success: function(obj){
			    	if (obj && obj.rs){
			    		art.dialog({
			    			content: obj.msg,
			    			width: 200,
			    			time: 2000
			    		});
			    	}
				}
			});
	}
}

// 系统尺寸变化函数
function resizeFn() {
	// 设置快捷菜单位置与大小
	$(".l-topmenu-fastmenu").css({
		"left": "215px",
		"width": ($(".l-topmenu-welcome").offset().left - 215 - 20) + "px"
	});

	loadFastMenus();
	calcFastMenuDivShow();
}

// 加载快捷菜单函数
function loadFastMenus() {
	var fastMenus = listStrToJson("${fastMenus}");
	// 添加快捷菜单子元素
	var topMenuFastMenuDiv = $("#topMenuFastMenuDiv");
	topMenuFastMenuDiv.html("");
	for(var i = 0; fastMenus && i < fastMenus.length; i++) {
		topMenuFastMenuDiv.append("<a href=\"javascript:void(0)\" onclick=\"fastMenuAClick("
			+ fastMenus[i].MENU_ID + ",'" + fastMenus[i].MENU_NAME + "','" + fastMenus[i].MENU_URL + "')\">" + fastMenus[i].MENU_NAME + "</a>");
	}
}

// 计算快捷菜单显示部分
function calcFastMenuDivShow() {
	var topMenuFastMenuDiv = $("#topMenuFastMenuDiv");
	var childrens = topMenuFastMenuDiv.children();
	var topMenuFastMenuDivWidth = ($(".l-topmenu-welcome").offset().left - 215 - 50); // 快捷菜单部分最大支持宽度
	var showWidth = 0; // 元素总宽度计算
	var i = 0; 
	for(;i < childrens.length; i++) {
		showWidth += $(childrens[i]).width();
		// 扩展300的宽度是为了防止showWidth 与 topMenuFastMenuDivWidth过于接近,导致提示按钮下移 
		if(showWidth + 300 > topMenuFastMenuDivWidth) {
			// 说明存在更多部分菜单,添加"更多"按钮
			$("a:eq(" + i + ")", topMenuFastMenuDiv).before("<a class=\"topMenuFastMenuMoreA\" href=\"javascript:void(0)\">更多<img id=\"topMenuFastMenuDownBtn\" class=\"topMenuFastMenuDownBtn\" src=\"${resourceRoot}/images/house_down.png\" /></a>");
			break;
		}
	}
	// 设置"更多"菜单
	fastMenuHideSet($("#topMenuFastMenuDiv a:gt(" + i + ")"));
}

// 快捷菜单隐藏部分设置,其中eles为需要隐藏的元素
function fastMenuHideSet(eles) {
	var topMenuFastMenuMoreDiv = $("#topMenuFastMenuMoreDiv");
	// 清空旧列表
	topMenuFastMenuMoreDiv.html("");
	// 添加隐藏的快捷菜单
	for(var i = 0; i < eles.length; i++) {
		if(i > 0) {
			topMenuFastMenuMoreDiv.append("<br/>");
		}
		topMenuFastMenuMoreDiv.append(eles[i]);
	}
	if(!eles || eles.length <= 0) {
		$("#topMenuFastMenuMoreDiv").css("display", "none");
		
	}else {
		var topMenuFastMenuLastA = $("#topMenuFastMenuDiv a:last");
		
		if(topMenuFastMenuLastA && topMenuFastMenuLastA.length > 0) {
			// 更多按钮绑定事件
			topMenuFastMenuLastA.on("mouseout", topMenuFastMenuMoreHide);
			// 更多按钮绑定事件
			topMenuFastMenuLastA.on("mouseover", topMenuFastMenuMoreShow); 
			// 根据"更多"按钮计算隐藏快捷菜单位置
			topMenuFastMenuMoreDiv.css({
				"left": (topMenuFastMenuLastA.offset().left - 225) + "px",
				"width": (topMenuFastMenuLastA.width() + 180) + "px"
			});
		}
	}
}

// 快捷菜单显示事件处理函数
function topMenuFastMenuMoreShow() {
	$("#topMenuFastMenuMoreDiv").css({"display": "block"});
}

// 快捷菜单隐藏事件处理函数
function topMenuFastMenuMoreHide() {
	$("#topMenuFastMenuMoreDiv").css({"display":"none"});
}

// 快捷菜单绑定事件函数
function fastMenuHideEventBind() {
	$("#topMenuFastMenuMoreDiv").on("mouseout", topMenuFastMenuMoreHide);
	
	$("#topMenuFastMenuMoreDiv").on("mouseover", topMenuFastMenuMoreShow); 
}

// 快捷菜单打开模块函数
function fastMenuAClick (menu_id, menu_name, menu_url) {
	f_addTab(menu_id, menu_name, "${ctx}" + menu_url);
}

// 监听系统尺寸事件
$(window).resize(resizeFn);

function onMainLoad(){
	sendMessage();
	setInterval('sendMessage()',600000);
	// 快捷菜单初始化
	resizeFn();
	fastMenuHideEventBind();
}
//设置的时间为１０００毫秒，即一秒种执行一次funTitle()函数

function goFirstMenu() {
	tab.selectTabItem("home");
}
</script> 

<style type="text/css"> 
    body,html{height:100%;}
    body{ padding:0px; margin:0;   overflow:hidden;}  
    .l-link{ display:block; height:26px; line-height:26px; padding-left:10px; text-decoration:underline; color:#333;}
    .l-link2{text-decoration:underline; color:white; margin-left:2px;margin-right:2px;}
    .l-layout-top{background:#102A49; color:White;}
    .l-layout-bottom{ background:#E5EDEF; text-align:center;}
    #pageloading{position:absolute; left:0px; top:0px; background:white url('${resourceRoot}/images/loading.gif') no-repeat center; width:100%; height:100%;z-index:99999;}
    .l-link{ display:block; line-height:22px; height:22px; padding-left:16px;border:1px solid white; margin:4px;}
    .l-link-over{ background:#FFEEAC; border:1px solid #DB9F00;} 
    .l-winbar{ background:#2B5A76; height:30px; position:absolute; left:0px; bottom:0px; width:100%; z-index:99999;}
    .space{ color:#E7E7E7;}
    /* 顶部 */ 
    .l-topmenu{ margin:0; padding:0; height:40px; line-height:31px; background:url('${resourceRoot}/images/top.jpg') repeat-x bottom;  position:relative; border-top:1px solid #25A0DC;  }
    .l-topmenu-logo{ color:#E7E7E7; padding-left:35px; height:39px; line-height:40px;background:url('${resourceRoot}/images/logo.jpg') no-repeat 10px 0px;}
    .l-topmenu-welcome{  position:absolute; height:40px; line-height:40px;  right:30px; top:0px;color:#070A0C;}
    .l-topmenu-welcome a{ color:#E7E7E7; text-decoration:underline} 
    .l-topmenu-fastmenu {
   		display: block;
		position: absolute;
		height:31px;
		top: 5px;
	}
	.l-topmenu-fastmenu-more {
		display: inline-block;
	}
	.l-topmenu-fastmenu-more img {
	    width: 20px;
	    position: absolute;
	    top: 5px;
	}
	.l-topmenu-fastmenu-div {
		display: inline-block;
		padding: 0px;
		padding-left: 5px;
	}
	.l-topmenu-fastmenu-div a:not(:first-child) {
		margin-left: 20px;
	}
	.l-topmenu-fastmenu-more-div {
		position: absolute;
		width: 150px;
		height: auto;
		background: white;
		z-index: 9999;
		border: 1px solid #ddd;
		display: none;
		padding: 0px 10px;
   		top: 33px;
   		max-height: 410px;
   		overflow-y: scroll;
	}
	.l-topmenu-fastmenu-div a, .l-topmenu-fastmenu-more-div a {
		color: white;
	    text-decoration: none;
	}
	.l-topmenu-fastmenu-more-div a {
		color: black;	
	}
	.topMenuFastMenuDownBtn {
		position: relative;
	    top: 6px;
	    width: 20px;
	}
	.topMenuFastMenuMoreA {
		height: 35px;
    	display: inline-block;
	}
	.letter {
		width: 18px;
	    top: 5px;
	    position: relative;
	}
	.letterTip {
	    padding: 2px;
	    position: absolute;
	    top: 5px;
	    left: 10px;
	    border-radius: 10px;
	    background: red;
	    line-height: 1;
	    text-align: center;
	    color: white;
	    display: none;
	}
	.l-topmenu-welcome span:first-of-type {
		cursor: pointer;
	}
    #bgShadow{ display: none;  position: absolute;  top: 0%;  left: 0%;  width: 100%;  height: 100%;  background-color: gray;  z-index:1001;  -moz-opacity: 0.7;  opacity:.70;  filter: alpha(opacity=70);}
 </style>
</head>
<body id="houseSystemBody" style="padding:0px;background:#EDF5F8;" onbeforeunload="" onload="onMainLoad()">
<div id="pageloading"></div>  
<div id="topmenu" class="l-topmenu">
	<div class="l-topmenu-logo"> &nbsp;</div>
	<div class="l-topmenu-fastmenu">
		<div id="topMenuFastMenuDiv" class="l-topmenu-fastmenu-div"></div>
		<div class="l-topmenu-fastmenu-more-div" id="topMenuFastMenuMoreDiv"></div>
	</div>
    <div class="l-topmenu-welcome">
    	<span onclick="goFirstMenu()">
    		<img class="letter" src="${resourceRoot}/images/letter.png" />
    		<span id="letterTipSpan" class="letterTip">0</span>
    	</span>
        <span class="space">|</span>
        <span style="color: #E7E7E7;">${userName }您好</span>
         <span class="space" id="mailDiv">|</span>
        <a href="javascript:void(0)" class="l-link2" onclick="changePassword()">修改密码</a> 
        <span class="space">|</span>
         <a href="javascript:void(0)" class="l-link2" onclick="loginOut()">退出</a>
    </div> 
</div>
<div id="layout1" style="width:99.2%; margin:0 auto; margin-top:4px;">
	<div position="left"  title="主要菜单" id="accordion1"> 
		<!-- 搜索用 begin -->
		<div title="搜索结果" class="l-scroll l-accordion-content" id="div_search">
			<ul id="tab_search_inner" class="ztree" style="overflow: auto;"></ul>
		</div>
		<!-- end -->
	      ${html }
	</div>
	<div position="center" id="framecenter"> 
	    <div tabid="home" title="我的主页" style="height:300px" >
	        <iframe frameborder="0" name="home" id="home" src="${ctx }/frame/goRight?title=${title}"></iframe>
	    </div> 
	</div> 
</div>
<!-- <div  style="height:25px; line-height:25px; text-align:center;margin:0px;">
   Copyright &copy; 2013 All rights reserved
</div> -->
<div id="bgShadow"></div>
</body>
<script type="text/javascript">
document.addEventListener('webkitvisibilitychange',function(){
	var st = document.webkitVisibilityState;
	if (st=='visible'){
		sendMessage();
	}
});

</script>
</html>

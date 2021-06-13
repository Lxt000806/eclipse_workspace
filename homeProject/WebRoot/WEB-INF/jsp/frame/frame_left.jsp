<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0"  />

	<title> new document </title> 
	<link href="${resourceRoot}/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${resourceRoot}/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
	<script src="${resourceRoot}/js/jquery.js" type="text/javascript"></script>      
	<script src="${resourceRoot}/js/iss.core.js" type="text/javascript"></script>

	<script src="${resourceRoot}/ligerUI/js/core/base.js" type="text/javascript"></script> 
	<script src="${resourceRoot}/ligerUI/js/plugins/ligerAccordion.js" type="text/javascript"></script>
	<script src="${resourceRoot}/zTree/js/jquery.ztree.all-3.1.js" type="text/javascript"></script>

<style type="text/css"> 
	html,body{height:100%; padding:0; margin:0;}
	body{ padding:0px; margin:0; overflow:hidden;}  
</style>

<script type="text/javascript">

//加载Accordion UI
var accordion = null;
$(function(){
	$("#accordion_1").ligerAccordion({ 
		height: $("body").height() - 2,
		speed: null,
		changeHeightOnResize: true
	});

	accordion = $("#accordion_1").ligerGetAccordionManager();
});

$(window).resize(function (){
	accordion.setHeight($.clientHeight() - 2);
});


var setting = {
	view: {
		dblClickExpand: false,
		showLine: true,
		selectedMulti: false
	},
	data: {
		simpleData: {
			enable:true,
			idKey: "MENU_ID",
			pIdKey: "PARENT_ID"
		},
		key:{
			name: "MENU_NAME"
		}
	},

	callback: {
		beforeClick: function(treeId, treeNode) {
			return true;
		},

		onClick: function(event, treeId, treeNode){
			
			var fs = self.parent.frames["mainFrame"];
			var openType = treeNode.OPEN_TYPE; //打开类型 
			var menuType = treeNode.MENU_TYPE; //菜单类型
			var sUrl = "${ctx}" + treeNode.MENU_URL;


			if(menuType == "${MENU_TYPE_URL }"){
				if(openType && openType == "${MENU_OPEN_TYPE_INNER }"){
					fs.location = sUrl;
				}
				else if(openType && openType == "${MENU_OPEN_TYPE_FULL }"){
					$.fnShowWindow_mid(sUrl);
				}
			}
			else if(menuType == "${MENU_TYPE_FOLDER }"){
				zTree = $.fn.zTree.getZTreeObj(treeId);
				if(treeNode.open){
					zTree.expandNode(treeNode, false);
				}
				else{
					zTree.expandNode(treeNode, true);
				}
				
			}
		}
	}
};

//加载tab menu 菜单
$(function(){
		$("#accordion_1").find("div>ul").each(function(){
		//alert(this.id)
		fillMenus(this.id);
	});
});



//异步请求填充菜单
function fillMenus(menuId){
	//验证
	$.ajax({
		url:'${ctx }/menu/treeMenus',
		type: 'post',
		cache: false, 
		data: {"menuId": menuId},
		dataType: 'json',
		error: function(){
	        $("#" + menuId).html('糟糕！读取菜单出错~').css("display","block");
	    },
	    success: function(res){
	    	$.fn.zTree.init($("#" + menuId), setting, res.datas);
	    }
	 });
}

</script>



</head>
<body style="background:">

	<div position="left"  title="主要菜单" id="accordion_1"> 
	<c:forEach items="${tabMenus }" var="item">
		<div title="${item.MENU_NAME }" class="l-scroll" id="div_${item.MENU_ID }" >
			<ul id="${item.MENU_ID }" class="ztree" style="width:175px; overflow:auto;" >
		</div>
	</c:forEach>

<%--
		<div title="应用场景">
			<div style=" height:7px;"></div>
			 <a class="l-link" href="javascript:f_addTab('listpage','列表页面','demos/case/listpage.htm')">列表页面</a> 
			 <a class="l-link" href="demos/dialog/win7.htm" target="_blank">模拟Window桌面</a> 
		</div>    
		<div title="实验室">
			<div style=" height:7px;"></div>
			<a class="l-link" href="lab/generate/index.htm" target="_blank">表格表单设计器</a> 
		</div> 
		<div title="aa">
			<div style=" height:7px;"></div>
			<a class="l-link" href="lab/generate/index.htm" target="_blank">表格表单设计器</a> 
		</div> 
		<div title="aav">
			<div style=" height:7px;"></div>
			<a class="l-link" href="lab/generate/index.htm" target="_blank">表格表单设计器</a> 
		</div> 
		<div title="测试ztree"  > 
			<ul id="tree" class="ztree" style="width:200px; overflow:auto;"></ul>
		</div>
--%>

	</div>
    


</body>
</html>

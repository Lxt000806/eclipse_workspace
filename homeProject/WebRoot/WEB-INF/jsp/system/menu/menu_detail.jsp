<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>菜单维护</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
    <script type="text/javascript">
   	function addSame(){
   		var selectedNodes = parent.zTree.getSelectedNodes();
		if(selectedNodes.length != 1){
			var dialog = art.dialog({
				content: '请选择一个菜单作为同级菜单',
				lock: true,
				ok:function(){
			    	dialog.close();
			    }
			});
			return ;
		}
		var pid = 0;
		if(selectedNodes[0].getParentNode() != null){
			pid = selectedNodes[0].parentId;
		}
		Global.Dialog.showDialog("menuAddSame",{
			  title:"添加同级菜单",
			  url:"${ctx}/admin/menu/goSave?parentId="+pid,
			  height: 400,
			  width:1000,
			  returnFun: goto_menu
			});
   	}
   	
   	function addSub(){
   		var selectedNodes = parent.zTree.getSelectedNodes();
		if(selectedNodes.length != 1){
			var dialog = art.dialog({
				content: '请选择一个菜单作为父级菜单',
				lock: true,
				ok:function(){
			    	dialog.close();
			    }
			});
			return ;
		}
		if($("#menuType").val() == '${urlMenuType}' || selectedNodes[0].menuType == '${urlMenuType}'){
			var dialog = art.dialog({
				content: 'URL类型菜单不能作为父菜单',
				lock: true,
				ok:function(){
			    	dialog.close();
			    }
			});
			return ;
		}
		Global.Dialog.showDialog("menuAddSub",{
			  title:"添加子集菜单",
			  url:"${ctx}/admin/menu/goSave?parentId="+selectedNodes[0].menuId,
			  height: 400,
			  width:1000,
			  returnFun: goto_menu
			});
   	}
   	
	//修改
	function edit(){
		if($("#menuId").val() == ""){
			var dialog = art.dialog({
				content: '请在左边树选择要更改的菜单',
				lock: true,
				ok:function(){
			    	dialog.close();
			    }
			});
			return ;
		}
		Global.Dialog.showDialog("menuUpdate",{
			  title:"修改菜单",
			  url:"${ctx}/admin/menu/goUpdate?menuId="+$.trim($("#menuId").val()),
			  height: 400,
			  width:1000,
			  returnFun: goto_menu
			});
	}
	
	//删除
	function del(){
		var menuId = $("#menuId").val();
		if(menuId == ""){
			var dialog = art.dialog({
				content: '请在左边树选择要删除的菜单',
				lock: true,
				ok:function(){
			    	dialog.close();
			    }
			});
			return ;
		}
		var menuName=$("#menuName").html();
		
		art.dialog({
			 content:'您确定要删除 \"'+menuName+'\" 记录吗？',
			 lock: true,
			 width: 260,
			 height: 100,
			 ok: function () {
		        $.ajax({
					url : "${ctx}/admin/menu/doDelete",
					data : "menuId="+menuId,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: 'json',
					cache: false,
					error: function(){
				        art.dialog({
							content: '删除记录出现异常'
						});
				    },
				    success: function(obj){
				    	if(obj.rs){
				    		var dialog = art.dialog({
								content: obj.msg,
								time: 1000,
								beforeunload: function () {
							        window.location.reload();
									window.parent.treeFrame.location.reload();
							    },
							    ok:function(){
							    	dialog.close();
							    }
							});
				    	}else{
				    		var dialog = art.dialog({
								content: obj.msg,
								ok:function(){
							    	dialog.close();
							    }
							});
				    	}
					}
				});
		    	return true;
			},
			cancel: function () {
				return true;
			}
		});
	}
	
	function goto_menu(){
		//window.location = '${ctx}/admin/menu/goMain?initId=${menu.menuId }';
       	window.location.reload();
		window.parent.treeFrame.location.reload();
	}
	
	//加载菜单具体信息
   	function loadDate(treeNode){
   		var treeId = treeNode.menuId;
   		if(typeof(treeId) == "undefined"){
   			return ;
   		}
   		$.ajax({
		    url: '${ctx}/admin/menu/getMenuInfo',
		    data: "menuId="+treeId,
		    dataType: 'json',
		    cache: false,
		    success: function(data){
		    	if(data.rs){
			    	setDate(data.datas);
			    }
		    }
		});
   	}
   
   	//设置数据到input框页面
	function setDate(obj){
   		$("#menuId").val(obj.menuId);
   		$("#menuName").val(obj.menuName);
   		$("#parentName").val((typeof(obj.parentName) == "undefined") ? "" : obj.parentName);
   		$("#menuUrl").val((typeof(obj.menuUrl) == "undefined") ? "" : obj.menuUrl);
   		$("#menuType").val(obj.menuType);
   		//$("#menuTypeName").html(obj.menuTypeName);
   		$("#sysCode").val(obj.sysCode);
   		//$("#sysCodeName").html(obj.sysCodeName);
   		//$("#code").html(typeof(obj.code) == "undefined" ? "" : obj.code);
   		//$("#status").html(typeof(obj.status) == "undefined" ? "" : obj.status);
   		$("#openType").val(obj.openTypeName);
   		//$("#openIcon").html((typeof(obj.openIcon) == "undefined") ? "" : obj.openIcon);
   		//$("#closeIcon").html((typeof(obj.closeIcon) == "undefined") ? "" : obj.closeIcon);
   		$("#orderNo").val((typeof(obj.orderNo) == "undefined") ? "" : obj.orderNo);
   		$("#remark").val((typeof(obj.remark) == "undefined") ? "" : obj.remark);
   	}
   	
    </script>
</head>
<body>
<div class="body-box-form" style="margin-top: 5px;">
	<div class="btn-panel" >
      <div class="btn-group-sm">
      	<house:authorize authCode="MENU_SAVE">
			<button type="button" class="btn btn-system" id="saveSameBut" onclick="addSame()">添加同级菜单</button>
			<button type="button" class="btn btn-system" id="saveSubBut" onclick="addSub()">添加子级菜单</button>
			</house:authorize>
			<house:authorize authCode="MENU_UPDATE">
			<button type="button" class="btn btn-system" id="updateBut" onclick="edit()">修改</button>
			</house:authorize>
			<house:authorize authCode="MENU_DELETE">
			<button type="button" class="btn btn-system" id="delBut" onclick="del()">删除</button>
			</house:authorize>
      </div>
    </div>
    <div class="query-form">  
         <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
			<input type="hidden" name="menuId" id="menuId" value="${menu.menuId }"/>
			<ul class="ul-form">
				<li>
					<label>菜单名称</label>
					<input type="text" style="width:160px;" id="menuName" name="menuName" value="${menu.menuName }" readonly="readonly"/>
				</li>
				<li>
					<label>菜单类型</label>
					<house:dict id="menuType" dictCode="ABSTRACT_DICT_MENU_TYPE" value="${menu.menuType }" disabled="true"></house:dict>
				</li>
				<li>
					<label>父菜单</label>
					<input type="text" style="width:160px;" id="parentName" name="parentName" value="${parentName }" readonly="readonly"/>
				</li>
				<li>
					<label>打开方式</label>
					<house:dict id="openType" dictCode="ABSTRACT_DICT_MENU_OPEN" value="${menu.openType }" disabled="true"></house:dict>
				</li>
				<li>
					<label>操作链接</label>
					<input type="text" style="width:160px;" id="menuUrl" name="menuUrl" value="${menu.menuUrl }" readonly="readonly"/>
				</li>
				<li>
					<label>排列顺序</label>
					<input type="text" style="width:160px;" id="orderNo" name="orderNo" value="${menu.orderNo }" readonly="readonly"/>
				</li>
				<li>
					<label>平台类型</label>
					<house:dict id="sysCode" dictCode="ptdm" value="${menu.sysCode }" disabled="true"></house:dict>
				</li>
				<li>
					<label>备注</label>
					<input type="text" style="width:160px;" id="remark" name="remark" value="${menu.remark }" readonly="readonly"/>
					<!-- 
					<label class="control-textarea">备注</label>
					<textarea id="remark" name="remark" readonly="readonly">${menu.remark }</textarea>
					 -->
				</li>
			</ul>
		</form>
	</div>
</div>
</body>
</html>


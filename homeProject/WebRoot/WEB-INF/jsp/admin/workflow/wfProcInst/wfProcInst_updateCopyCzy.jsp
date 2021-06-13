<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>抄送人添加管理</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript">
$(function(){  
	var nameChi = "${nameChi}";
	var oldNameChi = "${oldNameChi}";
	var assignee = "${assignee}";
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable", {
		height: $(document).height()-$("#content-list").offset().top-80,
		colModel: [  
			{name: "assignee", index: "assignee", width: 126, label: "抄送人编号", sortable: true, align: "left",hidden:true },
			{name: "namechidescr", index: "namechidescr", width: 126, label: "抄送人名称", sortable: true, align: "left", },
			{name: "canDelete", index: "canDelete", width: 126, label: "是否可删除", sortable: true, align: "left", hidden:true},
	    ],
	});
	var objNameChi;
	if(oldNameChi.trim() != ""){
		var newNameChi = nameChi.split(oldNameChi)[1];
		objNameChi = newNameChi.split("/");
		if(oldNameChi!= "" ){
		  assignee = ","+assignee;
		}
	}else{
		objNameChi = nameChi.split("/");
	}
	var objAssignee = assignee.split(",");
	var objOldNameChi = oldNameChi.split("/");
	for(var i = 0;i<objOldNameChi.length;i++){
		if(objOldNameChi[i]){
			Global.JqGrid.addRowData("dataTable",{assignee:"",namechidescr:objOldNameChi[i],canDelete:"false"});
		}
	}
	
	for(var i = 0;i<objNameChi.length;i++){
		if(objAssignee[i] && objNameChi[i]){
			Global.JqGrid.addRowData("dataTable",{assignee:objAssignee[i],namechidescr:objNameChi[i],canDelete:"true"});
		}
	}
	
}); 
	function selCzy(){
		Global.Dialog.showDialog("selectCzy",{
			title:"选人",
			url:"${ctx}/admin/employee/goCode",
			postData:{},
			height: 680,
			width:1000,
			returnFun:function(data){
			  	if(data){	
					Global.JqGrid.addRowData("dataTable",{assignee:data.number,namechidescr:data.namechi,canDelete:"true"});
				}
			} 
		 });
	}
	
	function saveCopy(){
		var nameChi = Global.JqGrid.allToJson("dataTable","namechidescr");
		var assignee = Global.JqGrid.allToJson("dataTable","assignee");
		var arr = assignee.fieldJson.split(",");
		var str ="";
		var selectRows = [];
		for(var i = 0; i < arr.length; i++){
			if(arr[i] != ""){
				if(str == ""){
					str = arr[i];
				} else {
					str += str + "," + arr[i];
				}
			}
		}		
		
	 	selectRows.push(str);
	 	selectRows.push(nameChi.fieldJson.replace(/,/g,"/"));
	 	
		Global.Dialog.returnData = selectRows;
		closeWin();
	}
	
	function delete_(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		var ret = selectDataTableRow();
		
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		if(ret.canDelete.trim() == "false"){
			art.dialog({
				content:"默认抄送人不可删除",
			});
			return;
		}else{
			Global.JqGrid.delRowData("dataTable",id);
		}
	}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body" >
			    <div class="btn-group-xs" >
					<button type="button" class="btn btn-system" onclick="saveCopy()">保存</button>
					<button type="button" class="btn btn-system" onclick="selCzy()">添加</button>
					<button type="button" class="btn btn-system" onclick="delete_()">删除</button>
				 	<button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
			    </div>
		    </div>
		</div>
		<div class="query-form" hidden="true">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
			</form>
		</div>
		
		<div class="btn-panel" >
   			<div class="btn-group-sm"  >
				
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
		</div>
	</div>
</body>
</html>



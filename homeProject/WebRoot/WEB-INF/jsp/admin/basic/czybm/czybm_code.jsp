<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--操作员编号</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/czybm/goJqGrid",
			postData: $("#page_form").jsonForm(),
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',   
			colModel : [
			  {name : 'czybh',index : 'czybh',width : 100,label:'操作员编号',sortable : true,align : "left"},
			  {name : 'ywxm',index : 'ywxm',width : 100,label:'英文名',sortable : true,align : "left"},
			  {name : 'zwxm',index : 'zwxm',width : 100,label:'中文名',sortable : true,align : "left"},
			  {name : 'positiondescr',index : 'positiondescr',width : 100,label:'职位',sortable : true,align : "left"},
			  {name : 'department1descr',index : 'department1descr',width : 100,label:'一级部门',sortable : true,align : "left"},
			  {name : 'department2descr',index : 'department2descr',width : 100,label:'二级部门',sortable : true,align : "left"},
			  {name : 'department3descr',index : 'department3descr',width : 100,label:'三级部门',sortable : true,align : "left"},
			  {name : 'bmbh',index : 'bmbh',width : 100,label:'部门编号',sortable : true,align : "left"},
			  {name : 'jslx',index : 'jslx',width : 100,label:'角色类型',sortable : true,align : "left"},
		      {name : 'emnum',index : 'emnum',width : 100,label:'员工号',sortable : true,align : "left"}
            ],
            ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            },
            gridComplete:function(){
            	if('${czybm.forTicket}'=='1'){
            		jQuery("#dataTable").setGridParam().hideCol("bmbh").trigger("reloadGrid");
            		jQuery("#dataTable").setGridParam().hideCol("jslx").trigger("reloadGrid");
            		jQuery("#dataTable").setGridParam().hideCol("emnum").trigger("reloadGrid");
            	}
            }
            
            
		});

});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" onsubmit="return false;" class="form-search">
			    <input type="hidden" id="empStatus" name="empStatus" value="${czybm.empStatus}" />
			    <input type="hidden" id="zfbz" name="zfbz" value="${czybm.zfbz}" />
				<ul class="ul-form">
					<li> 
						<label>操作员编号</label>
						<input type="text" id="czybh" name="czybh"   value="${czybm.czybh }" />
					</li>
					<li> 
						<label>中文名</label>
						<input type="text" id="zwxm" name="zwxm"   value="${czybm.zwxm }" />
					</li>
					<li >
						<label>职位</label>
						<house:dict id="position" dictCode="" sql="select Code,Desc2 from tPosition where expired='F'" 
						sqlValueKey="Code" sqlLableKey="Desc2"  value="${czybm.zwxm }"></house:dict>
					</li>
					<li>
						<label>一级部门</label>
						<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')" value="${czybm.department1 }"></house:department1>
					</li>
					<li>
						<label>二级部门</label>
						<house:department2 id="department2" dictCode="${czybm.department1 }" value="${czybm.department2 }" onchange="changeDepartment2(this,'department3','${ctx }')"></house:department2>
					</li>
					<li id="department3_li">
						<label>三级部门</label>
						<house:department3 id="department3"  dictCode="${czybm.department2 }" value="${czybm.department3 }"></house:department3>
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-system btn-sm"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-system btn-sm"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>



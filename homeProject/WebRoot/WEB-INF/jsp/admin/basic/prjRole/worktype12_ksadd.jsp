<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>编辑楼层</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function(){        
	Global.JqGrid.initJqGrid("dataTable_kswork",{
		height:400,
		rowNum: 10000,
		styleUI:'Bootstrap',
		url:"${ctx}/admin/prjRole/goKsworkJqGrid",
		postData:{unSelected:$("#unSelected").val()},
		multiselect: true,
		colModel:[
			{name: "worktype12", index: "worktype12", width: 95, label: "工种分类编号", sortable: true, align: "left"},
			{name: "worktype12descr", index: "worktype12descr", width: 284, label: "工种分类名称", sortable: true, align: "left"}
			
		]
	});
});

//校验函数
$(function() {
$("#saveBtn").on("click",function(){	
	var ids=$("#dataTable_kswork").jqGrid("getGridParam","selarrrow");
	if(ids.length == 0){
		art.dialog({
			content:"请选择数据后保存"
		});				
		return ;
	}
	var selectRows = [];
	$.each(ids,function(i,id){
		var rowData = $("#dataTable_kswork").jqGrid("getRowData",id);
		selectRows.push(rowData);
	});
  		Global.Dialog.returnData = selectRows;
  		closeWin();
});	

function validateRefresh(){
 $('#dataForm').data('bootstrapValidator')
                  .updateStatus('workType12', 'NOT_VALIDATED',null)  
                  .validateField('workType12')
};
});

function prjRolechange(){	
	var str=$("#prjRole").find("option:selected").text();	
	str = str.split(' ');//先按照空格分割成数组
	document.getElementById("prjRoleDescr").value=str;   
}

function workType12change(){	
	var str=$("#workType12").find("option:selected").text();
	str = str.split(' ');//先按照空格分割成数组
	for (var i=1;i<str.length;i++){
		if(str[i]!=" "){
		document.getElementById("workType12Descr").value=str[i]; 
		}
	}
}
</script>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" id="saveBtn" class="btn btn-system " >保存</button>
		      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
				<div class="validate-group">
					<li class="form-validate" hidden="true">
						<label><span class="required">*</span>工程角色</label>
						<house:dict id="prjRole" dictCode="" sql=" select Code,Descr from tPrjRole order by Code " 
								sqlValueKey="Code" sqlLableKey="Descr"  value="${prjRoleWorkType12.prjRole }"  onchange="prjRolechange()"></house:dict>
					</li>				
					<li hidden="true">
						<label><span class="required">*</span>工种分类</label>
							<house:dict id="workType12" dictCode="" sql=" select Code,Code+Descr Descr from tWorkType12 order by Code " 
								sqlValueKey="Code" sqlLableKey="Descr"  value="${prjRoleWorkType12.workType12 }"  onchange="workType12change()"></house:dict>
					</li>
					<li hidden="true"> 
						<label>工程角色名称</label>	
						<input  name="prjRoleDescr" id="prjRoleDescr" />
						
					</li>
					<li hidden="true"> 
						<label>工种分类名称</label>	
						<input type="text" name="workType12Descr" id="workType12Descr" value="${prjRoleWorkType12.workType12Descr }"/>
						<input type="text" name="unSelected" id="unSelected" value="${prjRoleWorkType12.unSelected }"/>
					</li>
				</div>				
				</ul>
			</form>
		</div>
		<div id="content-list-work">
			<table id= "dataTable_kswork"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</div>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
</body>
</html>

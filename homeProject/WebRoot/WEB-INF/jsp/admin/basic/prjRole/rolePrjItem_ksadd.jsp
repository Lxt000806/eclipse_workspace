<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>工程角色施工项目表添加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function(){
Global.JqGrid.initJqGrid("dataTable_ksxz",{
		height:400,
		rowNum: 10000,
		styleUI:'Bootstrap',
		url:"${ctx}/admin/prjRole/goKsxzJqGrid",
		postData:{unSelected:$("#unSelected").val()},
		multiselect: true,
		colModel:[
			{name: "prjitem", index: "prjitem", width: 95, label: "施工项目编号", sortable: true, align: "left"},
			{name: "prjitemdescr", index: "prjitemdescr", width: 284, label: "施工项目名称", sortable: true, align: "left"}
			
		]
	});
});




function onchange1(){	
	var str=$("#itemType3").find("option:selected").text();	
	str = str.split(' ');//先按照空格分割成数组
	str=str[1];
	document.getElementById("itemType3Descr").value=str;   
}

$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	var dataSet = {
				
	};
	Global.LinkSelect.setSelect(dataSet);

});
//校验函数	
$(function() {
	$("#dataForm").validate({
		rules: {
			"itemcode": {
				validIllegalChar: true,
				maxlength: 60,
				required: true
			},			
		}
});
function getData(data){
	if (!data) return;
	var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${itemsetdetail.itemtype1}',
	};
	Global.LinkSelect.setSelect(dataSet);
	$('#itemcode').val(data.code);
	$('#itemcodedescr').val(data.descr);
}	
$("#saveBtn").on("click",function(){	
	var ids=$("#dataTable_ksxz").jqGrid("getGridParam","selarrrow");
	if(ids.length == 0){
		art.dialog({
			content:"请选择数据后保存"
		});				
		return ;
	}
	var selectRows = [];
	$.each(ids,function(i,id){
		var rowData = $("#dataTable_ksxz").jqGrid("getRowData",id);
		selectRows.push(rowData);
	});
	Global.Dialog.returnData = selectRows;
	closeWin();
});	
});

function prjRolechange(){	
	var str=$("#prjRole").find("option:selected").text();	
	str = str.split(' ');//先按照空格分割成数组
	document.getElementById("prjRoleDescr").value=str;   
}

function PrjItemchange(){	
	var str=$("#prjItem").find("option:selected").text();	
	str = str.split(' ');//先按照空格分割成数组
	str=str[1];
	document.getElementById("prjItemDescr").value=str;   
}

</script>

</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" id="saveBtn" class="btn btn-system "  >保存</button>
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
					<li hidden="true">
						<label><span class="required">*</span>施工项111目</label>
							<house:xtdm  id="prjItem" dictCode="PRJITEM"    value="${prjRolePrjItem.prjItem}" onchange="PrjItemchange()"></house:xtdm>
					</li>
					<li hidden="true"> 
						<label>施工项目名称</label>	
						<input type="text" name="prjItemDescr" id="prjItemDescr" value="${prjRolePrjItem.prjItemDescr}"/>
						<input type="text" name="unSelected" id="unSelected" value="${prjRolePrjItem.unSelected}"/>
					</li>
				</ul>
			</form>
		</div>
		<div id="content-list">
			<table id= "dataTable_ksxz"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</div>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
</body>
</html>

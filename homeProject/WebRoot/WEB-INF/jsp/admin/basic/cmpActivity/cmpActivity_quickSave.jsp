<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>礼品明细--快速新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
var selectRows = [];
/**初始化表格*/
$(function(){
	//初始化材料类型1，2，3三级联动
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'LP',
								secondSelect:'itemType2',
								secondValue:'',
								thridSelect:'itemType3',
								thirdValue:'',});
	$("#itemType1").attr("disabled",true);
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		multiselect:true,
		colModel : [
		  {name : 'code',index : 'code',width : 100,label:'材料编号',sortable : true,align : "left"},
	      {name : 'descr',index : 'descr',width : 200,label:'材料名称',sortable : true,align : "left"},
	      {name : 'itemtype1descr',index : 'itemtype1descr',width : 100,label:'材料类型1',sortable : true,align : "left"},
	      {name : 'itemtype2',index : 'itemtype2',width : 100,label:'材料类型2',sortable : true,align : "left",hidden:true},
	      {name : 'itemtype2descr',index : 'itemtype2descr',width : 100,label:'材料类型2',sortable : true,align : "left"},
        ],
	});
	$("#descr").focus();
	 //保存
        $("#saveBtn").on("click",function(){
        	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
        	var Type=$("#Type").val();
        	if(ids.length==0){
        		Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");	
        		return;
        	}
        	if(Type==""){
        		Global.Dialog.infoDialog("请选择活动类型！");	
        		return;
        	}
    		$.each(ids,function(k,id){
    			var row = $("#dataTable").jqGrid('getRowData', id);
    			row["Type"]=Type;
    			row["typedescr"]=$("#typedescr").val();
    			selectRows.push(row);
    		});
    		$("#dataTableExists_selectRowAll").val(JSON.stringify(selectRows));
    		art.dialog({content: "添加成功！",width: 200});
    		Global.JqGrid.jqGridSelectAll("dataTable",false);//全不选
        });
});
function goto_query(){
	$("#itemType1").removeAttr("disabled");
	var postData = $("#page_form").jsonForm();
	var url="${ctx}/admin/item/goJqGrid";
	$("#dataTable").jqGrid("setGridParam", {
    	url: url,
    	postData: postData
  	}).trigger("reloadGrid");
  	$("#itemType1").attr("disabled",true);
}
function onchange1(){		
	var str=$("#Type").text();	
	var Type=$("#Type").val();
	str = str.split(' ');//先按照空格分割成数组
	str=str[Type];
	if(Type==str.length){
	var str1=str.substring(0,str.length);
	}
	else{
	var str1=str.substring(0,str.length-1);
	}
	document.getElementById("typedescr").value=str1; 
}
function closeAndReturn(){
		Global.Dialog.returnData = selectRows;
    	closeWin();
}

function clearForm(){
	$("input").val("");
	$("#itemType2").val("");
}
</script>
</head>
<body>
	<div class="body-box-list" >
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut"
						onclick="closeAndReturn()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info"  style="height:60px;">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<ul class="ul-form">
						<li class="form-validate"><label><span
								class="required">*</span>活动类型</label> <house:xtdm id="Type"
								dictCode="ACTGIFTTYPE" onchange="onchange1()"></house:xtdm></li>
						<li hidden="true"><label>活动类型名称</label> <input id="typedescr"
							name="typedescr" />
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="panel panel-info" style="height:100px;">
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<ul class="ul-form">
						<li><label>材料编号</label> <input type="text" id="code"
							name="code" value="${item.code}" />
						</li>
						<li><label>材料名称</label> <input type="text" id="descr"
							name="descr" value="${item.descr}" />
						</li>
						<li><label>材料类型1</label> <select id="itemType1"
							name="itemType1"></select>
						</li>
						<li><label>材料类型2</label> <select id="itemType2"
							name="itemType2"></select>
						</li>
						<input hidden="true" id="dataTableExists_selectRowAll"
							name="dataTableExists_selectRowAll" value="">
						<li class="search-group"><input type="checkbox" id="expired"
							name="expired" value="${item.expired=='T'?'T':'F'}"
							onclick="changeExpired(this)" ${item.expired== 'T'?'':'checked' }/>
							<p>隐藏过期</p>
							<button type="button" class="btn btn-sm btn-system"
								onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system"
								onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<!--query-form-->
		<div class="pageContent" style="height:380px;">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>



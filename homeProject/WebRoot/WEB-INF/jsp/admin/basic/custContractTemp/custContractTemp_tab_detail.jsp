<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
function goDetail(m_umState,name){
	var ret = selectDataTableRow();		
	var pk=$("#pk").val();
	//var sendRegions = $("#dataTable").getCol("sendregion", false).join(",");
	if(!ret && m_umState!="A"){
		art.dialog({    	
			content: "请选择一条记录"
		});
		return;
	}
    Global.Dialog.showDialog("goAddDetail",{
		title:"模板文本域信息--"+name,
		url:"${ctx}/admin/custContractTemp/goAddDetail",	
		postData:{
		  	pk:pk,code:m_umState=="A"?"":ret.code,expression:m_umState=="A"?"":ret.expression,
		  	m_umState:m_umState,fieldRemarks:m_umState=="A"?"":ret.fieldremarks,
		  	dispSeq:m_umState=="A"?"":ret.dispseq,contemppk:m_umState=="A"?"":ret.contemppk,
		  	fieldExpired:m_umState=="A"?"F":ret.expired
		},	                 			 		 
		height:350,
		width:400,
		rowNum:100000,
		returnFun:function(data){
			var id = $("#dataTable").jqGrid("getGridParam","selrow");
			if(data){
				$.each(data,function(k,v){
					var json = {
						code:v.code,
						expression:v.expression,
						fieldremarks:v.fieldRemarks,
						dispseq:v.dispSeq,
		                lastupdate: new Date(),
		                lastupdatedby:"${USER_CONTEXT_KEY.czybh}",
		                expired: v.fieldExpired,
		                actionlog: m_umState=="M"?"EDIT":"ADD",
					};
					if(m_umState=="M" || m_umState=="U"){
						$("#dataTable").setRowData(id, json);
					}else if(m_umState=="A" || m_umState=="C"){
						Global.JqGrid.addRowData("dataTable",json);
					}
				});
			}
		}   
	}); 
}

function delDetail(){
	var id = $("#dataTable").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({content: "请选择一条记录进行删除操作！"});
		return false;
	}
	art.dialog({
		content:"是否确认要删除？",
		lock: true,
		ok: function () {
			Global.JqGrid.delRowData("dataTable",id);
		},
		cancel: function () {
			return true;
		}
	}); 
}

/**初始化表格*/
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/custContractTemp/goDetailJqGrid?pk="+"${custContractTemp.pk }",
		ondblClickRow: function(){
           goDetail("V","查看");
        },				
		height:200,		
		styleUI: 'Bootstrap',	
		colModel : [		
		  {name : 'pk',index : 'pk',width : 80,label:'编号',sortable : true,align : "left",hidden:true},
	      {name : 'contemppk',index : 'contemppk',width : 80,label:'模板编号',sortable : true,align : "left",hidden:true},
	      {name : 'code',index : 'code',width : 150,label:'书签编码',sortable : true,align : "left"},	
	      {name : 'expression',index : 'expression',width : 150,label:'表达式',sortable : true,align : "left"},	
	      {name : 'fieldremarks',index : 'fieldremarks',width : 150,label:'说明',sortable : true,align : "left"},	
	      {name : 'dispseq',index : 'dispseq',width : 100,label:'序号',sortable : true,align : "left"},		 	 
	      {name : 'lastupdate',index : 'lastupdate',width : 130,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
	      {name : 'lastupdatedby',index : 'lastupdatedby',width : 80,label:'修改人',sortable : true,align : "left"},
	      {name : 'expired',index : 'expired',width : 80,label:'是否过期',sortable : true,align : "left"},
	      {name : 'actionlog',index : 'actionlog',width : 80,label:'操作日志',sortable : true,align : "left"}
        ],
        loadonce:true
	});
});  
</script>
</head>
    
<body >               
<div class="body-box-form" style="margin-top: 0px;">
	<div class="pageContent">         
		<!--query-form-->
			<!--panelBar-->
			<div class="panel panel-system" >
			   <div class="panel-body" >
			     <div class="btn-group-xs" >
			      	<button type="button" class="btn btn-system " id="addItem" onclick="goDetail('A','新增')">新增</button>
					<button type="button" class="btn btn-system " id="updateItem" onclick="goDetail('M','编辑')">编辑</button>
					<button type="button" class="btn btn-system " id="copyItem" onclick="goDetail('C','复制')">复制</button>
					<button type="button" class="btn btn-system " id="delItem" onclick="delDetail()">删除</button>
					<button type="button" class="btn btn-system " id="viewItem" onclick="goDetail('V','查看')">查看</button>	
			     </div>
			  	</div>
			</div>	
			<!--panelBar end-->
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>
	</div>
</div>
</body>
</html>



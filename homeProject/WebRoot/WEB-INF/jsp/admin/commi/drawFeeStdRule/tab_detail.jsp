<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
function goDetail(m_umState,name){
	var ret = selectDataTableRow();		
	if(!ret && m_umState!="A"){
		art.dialog({    	
			content: "请选择一条记录"
		});
		return;
	}
	
    Global.Dialog.showDialog("goAddDetail",{
		title:"效果图公司标准分段明细--"+name,
		url:"${ctx}/admin/drawFeeStdRule/goAddDetail",	
		postData:{
		  	beginArea:m_umState=="A"?0:ret.beginarea,endArea:m_umState=="A"?0:ret.endarea,
		  	colorDrawFee:m_umState=="A"?0:ret.colordrawfee,colorDrawFee3D:m_umState=="A"?0:ret.colordrawfee3d,
		},	     
		height:350,
		width:400,
		rowNum:100000,
		returnFun:function(data){
			var id = $("#dataTable").jqGrid("getGridParam","selrow");
			if(data){
				$.each(data,function(k,v){
					var json = {
						beginarea:v.beginArea,
		                endarea:v.endArea,
		                colordrawfee:v.colorDrawFee,
		                colordrawfee3d:v.colorDrawFee3D,
		                lastupdate: new Date(),
		                lastupdatedby:"${USER_CONTEXT_KEY.czybh}",
		                expired: "F",
		                actionlog: m_umState=="M"?"EDIT":"ADD",
					};
					if(m_umState=="M"){
						$("#dataTable").setRowData(id, json);
					}else if(m_umState=="A"){
						Global.JqGrid.addRowData("dataTable",json);
					}
					$("#sendType").attr("disabled", true);
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
		url:"${ctx}/admin/drawFeeStdRule/goDetailJqGrid",
		postData:{pk:"${drawFeeStdRule.pk }"},
		ondblClickRow: function(){
           goDetail("V","查看");
        },				
		height:180,		
		styleUI: 'Bootstrap',	
		colModel : [		
	      {name : 'pk',index : 'pk',width : 80,label:'pk',sortable : true,align : "left",hidden:true},
	      {name : 'beginarea',index : 'beginarea',width : 70,label:'起始面积',sortable : true,align : "right"},	
	      {name : 'endarea',index : 'endarea',width : 70,label:'截止面积',sortable : true,align : "right"},	
	      {name : 'colordrawfee',index : 'colordrawfee',width : 110,label:'普通效果图标准',sortable : true,align : "right"},	
	      {name : 'colordrawfee3d',index : 'colordrawfee3d',width : 110,label:'3d效果图标准',sortable : true,align : "right"},	
	      {name : 'lastupdate',index : 'lastupdate',width : 130,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
	      {name : 'lastupdatedby',index : 'lastupdatedby',width : 80,label:'修改人',sortable : true,align : "left"},
	      {name : 'expired',index : 'expired',width : 80,label:'是否过期',sortable : true,align : "left"},
	      {name : 'actionlog',index : 'actionlog',width : 80,label:'操作日志',sortable : true,align : "left"},
        ],
	});
});  
</script>
            
<div class="body-box-form" style="margin-top: 0px;">
	<div class="pageContent">         
		<div class="panel panel-system" >
		   <div class="panel-body" >
		     <div class="btn-group-xs" >
		      	<button type="button" class="btn btn-system " id="addItem" onclick="goDetail('A','新增')">新增</button>
				<button type="button" class="btn btn-system " id="updateItem" onclick="goDetail('M','编辑')">编辑</button>
				<button type="button" class="btn btn-system " id="delItem" onclick="delDetail()">删除</button>
				<button type="button" class="btn btn-system " id="viewItem" onclick="goDetail('V','查看')">查看</button>	
		     </div>
		  	</div>
		</div>	
		<div id="content-list">
			<table id= "dataTable"></table>
		</div>
	</div>
</div>

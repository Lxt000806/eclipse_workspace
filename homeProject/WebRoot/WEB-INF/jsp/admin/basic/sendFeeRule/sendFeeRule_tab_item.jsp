<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
function goItem(m_umState,name){
	var ret = selectDataTableRow();		
	var no=$("#no").val();
	var itemType1=$("#itemType1").val();
	var itemType2=$("#itemType2").val();
	var itemType3s = $("#dataTable").getCol("itemtype3", false).join(",");
	if(!ret && m_umState!="A"){
		art.dialog({    	
			content: "请选择一条记录"
		});
		return;
	}
	if(itemType1==""){
		art.dialog({    	
			content: "请选择材料类型1"
		});
		return;
	}
	if(itemType2==""){
		art.dialog({    	
			content: "请选择材料类型2"
		});
		return;
	}
    Global.Dialog.showDialog("goItem",{
		title:name+"材料页面",
		url:"${ctx}/admin/sendFeeRule/goItem",	
		postData:{
		  	no:no,itemType1:itemType1,itemType2:itemType2,itemType3:m_umState=="A"?"":ret.itemtype3,
		  	m_umState:m_umState,itemType3s:itemType3s,itemType3Descr:m_umState=="A"?"":ret.itemtype3descr
		},	                 			 		 
		height:300,
		width:400,
		returnFun:function(data){
			var id = $("#dataTable").jqGrid("getGridParam","selrow");
			if(data){
				$.each(data,function(k,v){
					var json = {
						itemtype3:v.itemType3,
		                itemtype3descr:v.itemType3Descr,
		                lastupdate: new Date(),
		                lastupdatedby:"${sessionScope.USER_CONTEXT_KEY.czybh}",
		                expired: "F",
		                actionlog: m_umState=="M"?"EDIT":"ADD",
					};
					if(m_umState=="M"){
						$("#dataTable").setRowData(id, json);
					}else if(m_umState=="A"){
						Global.JqGrid.addRowData("dataTable",json);
					}
					$("#itemType1,#itemType2").attr("disabled",true);
				});
			}
		}   
	}); 
}
function delItem(){
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
			var records = $("#dataTable").jqGrid('getGridParam', 'records'); //获取数据总条数
			if(records==0){//表格为空时可以选材料类型1
				$("#itemType1,#itemType2").removeAttr("disabled");
			}else{
				$("#itemType1,#itemType2").attr("disabled",true);
			}
		},
		cancel: function () {
			return true;
		}
	}); 
}
/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");
	var dataSet = {
		firstSelect:"itemType1",
		secondSelect:"itemType2",
		firstValue:'${sendFeeRule.itemType1}',
		secondValue:'${sendFeeRule.itemType2}'
	};
	Global.LinkSelect.setSelect(dataSet);	

$(function(){
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/sendFeeRule/goItemJqGrid?no="+"${sendFeeRule.no}",
		ondblClickRow: function(){
           goItem("V","查看");
        },				
		height:180,		
		styleUI: 'Bootstrap',	
		colModel : [		
		  {name : 'pk',index : 'pk',width : 80,label:'pk',sortable : true,align : "left",frozen: true,hidden:true},
	      {name : 'no',index : 'no',width : 80,label:'编号',sortable : true,align : "left",frozen: true,hidden:true},
	      {name : 'itemtype3',index : 'itemtype3',width : 100,label:'编号',sortable : true,align : "left",frozen: true},		 
	      {name : 'itemtype3descr',index : 'itemtype3descr',width : 220,label:'材料类型3',sortable : true,align : "left",frozen: true},	
	      {name : 'lastupdate',index : 'lastupdate',width : 130,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
	      {name : 'lastupdatedby',index : 'lastupdatedby',width : 80,label:'修改人',sortable : true,align : "left"},
	      {name : 'expired',index : 'expired',width : 80,label:'是否过期',sortable : true,align : "left"},
	      {name : 'actionlog',index : 'actionlog',width : 80,label:'操作日志',sortable : true,align : "left"},
        ],
        gridComplete:function(){
        	var records = $("#dataTable").jqGrid('getGridParam', 'records'); //获取数据总条数
			if(records==0 && "${sendFeeRule.m_umState}"!="V"){//表格为空时可以选材料类型1,2
				$("#itemType1,#itemType2").removeAttr("disabled");
			}else{
				$("#itemType1,#itemType2").attr("disabled",true);
			}
        }
	});
});  
});
</script>
             
<div class="body-box-form" style="margin-top: 0px;">
	<div class="pageContent">         

		<div class="panel panel-system" >
		   <div class="panel-body" >
		     <div class="btn-group-xs" >
		      	<button type="button" class="btn btn-system " id="addItem" onclick="goItem('A','添加')">添加</button>
				<button type="button" class="btn btn-system " id="updateItem" onclick="goItem('M','编辑')">编辑</button>
				<button type="button" class="btn btn-system " id="delItem" onclick="delItem()">删除</button>
				<button type="button" class="btn btn-system " id="viewItem" onclick="goItem('V','查看')">查看</button>	
		     </div>
		  	</div>
		</div>	

		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</div>




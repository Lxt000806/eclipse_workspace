<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
function goDetail(m_umState,name){
	var ret = selectDataTableRow();		
	var no=$("#no").val();
	var sendRegions = $("#dataTable").getCol("sendregion", false).join(",");
	if(!ret && m_umState!="A"){
		art.dialog({    	
			content: "请选择一条记录"
		});
		return;
	}
	
	var sendType = $("#sendType").val();
	if (m_umState === "A") {
	    if (!sendType) {
	        art.dialog({content: "请先选择送货类型"});
	        return;
	    }
	}
	
    Global.Dialog.showDialog("goAddDetail",{
		title:"远程费规则明细--"+name,
		url:"${ctx}/admin/longFeeRule/goAddDetail",	
		postData:{
		  	no:no,longFee:m_umState=="A"?0:ret.longfee,sendRegion:m_umState=="A"?"":ret.sendregion,
		  	m_umState:m_umState,sendRegionDescr:m_umState=="A"?"":ret.sendregiondescr,
		  	sendRegions:sendRegions,smallSendType:m_umState=="A"?"":ret.smallsendtype,smallSendMaxValue:m_umState=="A"?0:ret.smallsendmaxvalue,
		  	smallSendTypeDescr:m_umState=="A"?"":ret.smallsendtypedescr, sendType: sendType
		},	                 			 		 
		height:350,
		width:400,
		rowNum:100000,
		returnFun:function(data){
			var id = $("#dataTable").jqGrid("getGridParam","selrow");
			if(data){
				$.each(data,function(k,v){
					var json = {
						longfee:v.longFee,
		                sendregion:v.sendRegion,
		                sendregiondescr:v.sendRegionDescr,
		                lastupdate: new Date(),
		                lastupdatedby:"${USER_CONTEXT_KEY.czybh}",
		                expired: "F",
		                actionlog: m_umState=="M"?"EDIT":"ADD",
		                smallsendtype:v.smallSendType,
		                smallsendmaxvalue:v.smallSendMaxValue,
		                smallsendtypedescr:v.smallSendTypeDescr
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
            var records = $("#dataTable").jqGrid('getGridParam', 'records');
            if (records == 0) {//表格为空时可以选材料类型1
                $("#sendType").removeAttr("disabled");
            } else {
                $("#sendType").attr("disabled", true);
            }
		},
		cancel: function () {
			return true;
		}
	}); 
}
/**初始化表格*/
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/longFeeRule/goDetailJqGrid?no="+"${longFeeRule.no }",
		ondblClickRow: function(){
           goDetail("V","查看");
        },				
		height:180,		
		styleUI: 'Bootstrap',	
		colModel : [		
		  {name : 'pk',index : 'pk',width : 80,label:'pk',sortable : true,align : "left",hidden:true},
	      {name : 'no',index : 'no',width : 80,label:'编号',sortable : true,align : "left",hidden:true},
	      {name : 'sendregion',index : 'sendregion',width : 150,label:'配送区域',sortable : true,align : "left",hidden:true},	
	      {name : 'sendregiondescr',index : 'sendregiondescr',width : 150,label:'配送区域',sortable : true,align : "left"},	
	      {name : 'longfee',index : 'longfee',width : 100,label:'远程费',sortable : true,align : "right"},	
	      {name : 'smallsendtypedescr',index : 'smallsendtypedescr',width : 100,label:'少量配送类型',sortable : true,align : "left"},
	      {name : 'smallsendtype',index : 'smallsendtype',width : 150,label:'少量配送类型',sortable : true,align : "left",hidden:true},			 
	      {name : 'smallsendmaxvalue',index : 'smallsendmaxvalue',width : 120,label:'少量配送最大值',sortable : true,align : "right"},		 	 
	      {name : 'lastupdate',index : 'lastupdate',width : 130,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
	      {name : 'lastupdatedby',index : 'lastupdatedby',width : 80,label:'修改人',sortable : true,align : "left"},
	      {name : 'expired',index : 'expired',width : 80,label:'是否过期',sortable : true,align : "left"},
	      {name : 'actionlog',index : 'actionlog',width : 80,label:'操作日志',sortable : true,align : "left"},
        ],
        gridComplete: function() {
            var records = $("#dataTable").jqGrid('getGridParam', 'records');
            if (records == 0 && "${sendFeeRule.m_umState}" != "V") {
                $("#sendType").removeAttr("disabled");
            } else {
                $("#sendType").attr("disabled", true);
            }
        }
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

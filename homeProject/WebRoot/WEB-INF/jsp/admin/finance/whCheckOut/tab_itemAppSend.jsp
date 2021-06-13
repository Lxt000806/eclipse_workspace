<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czylb=uc.getCzylb();
String czybh="";
if("2".equals(czylb)) czybh=uc.getCzybh();
%>	
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
    if( ($.trim($("#m_umState").val())=="C")||($.trim($("#m_umState").val())=="B")||($.trim($("#m_umState").val())=="V")){
		 $("#addItemAppSend").css("color","gray");
		 $("#delItemAppSend").css("color","gray");
	} 
    if(($.trim($("#m_umState").val())=="B")||($.trim($("#m_umState").val())=="V")){
		 $("#calcSendFeeBtn").css("color","gray");
	} 
    
	if("<%=czylb %>"=="2"){
       $('#viewItemAppSend').hide(); 
       $('#calcSendFeeBtn').hide();  
    } 	
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/whCheckOut/goJqGrid_itemAppSendDetail", 
			postData:{no:$.trim($("#no").val())},
		    rowNum:100000,
			height:280,
        	styleUI: 'Bootstrap',
        	searchBtn:true,
			colModel : [
			     {name: "ischeckout", index: "ischeckout", width: 80, label: "是否记账", sortable: true, align: "left",  hidden: true},
		         {name: "no", index: "no", width: 125, label: "领料发货单号", sortable: true, align: "left",hidden: true},
		         {name: "date", index: "date", width: 75, label: "发货日期", sortable: true, align: "left", formatter: formatTime,count: true,},
		         {name: "iano", index: "iano", width: 90, label: "领料申请单号", sortable: true, align: "left"},
		         {name: "documentno", index: "documentno", width: 85, label: "档案号", sortable: true, align: "left"},
		         {name: "payeecode", index: "payeecode", width: 80, label: "签约公司", sortable: true, align: "left", hidden: true},
		         {name: "payeedescr", index: "payeedescr", width: 80, label: "签约公司", sortable: true, align: "left"},
		         {name: "address", index: "address", width: 154, label: "楼盘", sortable: true, align: "left"},
		         {name: "projectmandescr", index: "projectmandescr", width: 70, label: "项目经理", sortable: true, align: "left"},
		         {name: "type", index: "type", width: 75, label: "领料单类型", sortable: true, align: "left",hidden: true},
		         {name: "typedescr", index: "typedescr", width: 80, label: "领料单类型", sortable: true, align: "left"},
		         {name: "itemtype1descr", index: "itemtype1descr", width: 75, label: "材料类型1", sortable: true, align: "left"},
		         {name: "itemtype2descr", index: "itemtype2descr", width: 75, label: "材料类型2", sortable: true, align: "left"},
		         {name: "itemsumcost", index: "itemsumcost", width: 70, label: "材料金额", sortable: true, align: "right", sum: true},
		         {name: "projectamount", index: "projectamount", width:105, label: "项目经理结算价", sortable: true, align: "right", sum: true},
		         {name: "whfee", index: "whfee", width: 95, label: "仓储费", sortable: true, align: "right", sum: true},
		         {name :"sendfee",index : "sendfee",width : 60,label:"配送费",editable:true,editrules: {number:true,required:true},sortable : true,align : "right",sum: true},
		         {name: "autosendfee", index: "autosendfee", width:95, label: "配送费（自动）", sortable: true, align: "right", sum: true},
		         {name :"longfee",index : "longfee",width : 60,label:"远程费",editable:true,editrules: {number:true,required:true},sortable : true,align : "right",sum: true},
		         {name: "autolongfee", index: "autolongfee", width:95, label: "远程费（自动）", sortable: true, align: "right", sum: true},
		         {name :"carryfee",index : "carryfee",width : 60,label:"搬运费",editable:true,editrules: {number:true,required:true},sortable : true,align : "right",sum: true},
		         {name: "autocarryfee", index: "autocarryfee", width:95, label: "搬运费（自动）", sortable: true, align: "right", sum: true},
				 {name: "projectothercost", index: "projectothercost", width: 85, label: "项目经理调整",editable:true,editrules: {number:true,required:true}, sortable: true, align: "right", sum: true},
				 {name: "sumcost", index: "sumcost", width: 75, label: "总金额", sortable: true, align: "right", sum: true},
		         {name: "sumprojectamount", index: "sumprojectamount", width: 115, label: "项目经理结算总价", sortable: true, align: "right", sum: true},	 
		         {name: "issetitemdescr", index: "issetitemdescr", width: 70, label: "套餐材料", sortable: true, align: "left"},
		         {name: "senddate", index: "senddate", width: 70, label: "送货日期", sortable: true, align: "left", formatter: formatTime},
		         {name: "sendtype", index: "sendtype", width: 70, label: "送货类型", sortable: true, align: "left"},
		         {name: "checkstatusdescr", index: "checkstatusdescr", width: 85, label: "客户结算状态", sortable: true, align: "left"},
		         {name: "remarks", index: "remarks", width: 140, label: "发货备注",editrules: {required:true},editable:true,sortable: true, align: "left",edittype:"textarea"},
		         {name: "itemappremarks", index: "itemappremarks", width: 200, label: "领料备注", sortable: true, align: "left"} ,
		         {name: "checkseq", index: "checkseq", width: 200, label: "显示顺序", sortable: true, align: "left", hidden: true}, 
		         {name: "whfeecosttype", index: "whfeecosttype", width: 90, label: "仓储费成本类型", sortable: true, align: "left", hidden: true},
		         {name: "projectamount", index: "projectamount", width:105, label: "项目经理结算价", sortable: true, align: "right", sum: true},
		         {name: "sendfeecosttype", index: "sendfeecosttype", width: 90, label: "配送费成本类型", sortable: true, align: "left", hidden: true},
		        
            ], 
            loadonce: true,
	  		beforeSaveCell:function(rowId,name,val,iRow,iCol){
				lastCellRowId = rowId;
			},
			afterSaveCell:function(rowId,name,val,iRow,iCol){
              
			  var rowData = $("#dataTable_itemAppSend").jqGrid("getRowData",rowId);
  	    	  /*
			  if (rowData.whfeecosttype=='2'){//公司和项目经理成本
		  	  	  rowData["sumcost"] = (parseFloat(rowData.itemsumcost)+parseFloat(rowData.sendfee)+parseFloat(rowData.whfee)+parseFloat(rowData.carryfee)).toFixed(2);
		          rowData["sumprojectamount"] = (parseFloat(rowData.projectamount)+parseFloat(rowData.sendfee)+parseFloat(rowData.projectothercost)+parseFloat(rowData.whfee)+parseFloat(rowData.carryfee)).toFixed(2);
		    	  Global.JqGrid.updRowData("dataTable_itemAppSend",rowId,rowData);
		  	  }else if (rowData.whfeecosttype=='3'){ //只算公司成本
		  	  	  rowData["sumcost"] = (parseFloat(rowData.itemsumcost)+parseFloat(rowData.sendfee)+parseFloat(rowData.whfee)+parseFloat(rowData.carryfee)).toFixed(2);
		          rowData["sumprojectamount"] = (parseFloat(rowData.projectamount)+parseFloat(rowData.sendfee)+parseFloat(rowData.projectothercost)+parseFloat(rowData.whfee)+parseFloat(rowData.carryfee)).toFixed(2);
		    	  Global.JqGrid.updRowData("dataTable_itemAppSend",rowId,rowData);
		  	  }else {
		  	  	  rowData["sumcost"] = (parseFloat(rowData.itemsumcost)+parseFloat(rowData.sendfee)+parseFloat(rowData.carryfee)).toFixed(2);
		          rowData["sumprojectamount"] = (parseFloat(rowData.projectamount)+parseFloat(rowData.sendfee)+parseFloat(rowData.projectothercost)+parseFloat(rowData.carryfee)).toFixed(2);
		    	  Global.JqGrid.updRowData("dataTable_itemAppSend",rowId,rowData);
		  	  }*/
		  	  var whfee_projectothercost=0.0;
			  var whfee_cost = 0.0;
			  var whfee_project = 0.0;
			  var sendfee_projectothercost=0.0;
			  var sendfee_cost = 0.0;
			  var sendfee_project = 0.0;  
			  if (rowData.whfeecosttype=='2'){//公司和项目经理成本
				  whfee_projectothercost= 0.0;
				  whfee_cost = rowData.whfee;
				  whfee_project = rowData.whfee; 
		  	  }else if (rowData.whfeecosttype=='3'){ //只算公司成本
		  		  whfee_projectothercost= -rowData.whfee;
				  whfee_cost = rowData.whfee;
				  whfee_project = rowData.whfee; 
		  	  }else{
		  		  whfee_projectothercost=0.0;
				  whfee_cost = 0.0;
				  whfee_project = 0.0;  
		  	  }
			  
			  if (rowData.sendfeecosttype=='2'){//公司和项目经理成本
				  sendfee_projectothercost= 0.0;
				  sendfee_cost = rowData.sendfee;
				  sendfee_project = rowData.sendfee; 
		  	  }else if (rowData.sendfeecosttype=='3'){ //只算公司成本
		  		  sendfee_projectothercost =-rowData.sendfee;
		  		  sendfee_cost = rowData.sendfee;
		  		  sendfee_project = rowData.sendfee; 
		  	  }else{
		  		  sendfee_projectothercost=0.0;
		  		  sendfee_cost = 0.0;
		  		  sendfee_project = 0.0;  
		  	  }
			  
			  if (name=="sendfee"){  
				  rowData.projectothercost = (parseFloat(whfee_projectothercost)+parseFloat(sendfee_projectothercost)).toFixed(2);
			  }
			  rowData.sumcost = (parseFloat(rowData.itemsumcost)+parseFloat(sendfee_cost)+parseFloat(whfee_cost)+parseFloat(rowData.carryfee)+parseFloat(rowData.longfee)).toFixed(2);
			  rowData.sumprojectamount = (parseFloat(rowData.projectamount)+parseFloat(sendfee_project)+parseFloat(rowData.projectothercost)+parseFloat(whfee_project)+parseFloat(rowData.carryfee)+parseFloat(rowData.longfee)).toFixed(2);
	          Global.JqGrid.updRowData("dataTable_itemAppSend",rowId,rowData);
	   		},
            beforeSelectRow:function(id){
         	 setTimeout(function(){
	           relocate(id,"dataTable_itemAppSend");
	          },10);
          	} 
 		};
 	     
       //初始化送货申请明细
	   Global.JqGrid.initEditJqGrid("dataTable_itemAppSend",gridOption);
	   if("<%=czylb %>"!="1"){
            $("#dataTable_itemAppSend").jqGrid('hideCol', "itemsumcost");
            $("#dataTable_itemAppSend").jqGrid('hideCol', "projectothercost");
            $("#dataTable_itemAppSend").jqGrid('hideCol', "sumcost");
            $("#dataTable_itemAppSend").jqGrid('hideCol', "sumprojectamount");
            $("#dataTable_itemAppSend").jqGrid('hideCol', "autosendfee");
            $("#dataTable_itemAppSend").jqGrid('hideCol', "autolongfee");
            $("#dataTable_itemAppSend").jqGrid('hideCol', "autocarryfee");
       }   
	     
});

function calcSendFee(){ 
	var ids = $("#dataTable_itemAppSend").jqGrid("getDataIDs");
	if(ids.length == 0){
		art.dialog({
			content:"无领料单明细，无法进行计算"
		});
		return;
	}
	var rowData = $("#dataTable_itemAppSend").jqGrid("getRowData");
	var params = {"itemAppsendDetailJson": JSON.stringify(rowData)};
	Global.Form.submit("dataForm", "${ctx}/admin/whCheckOut/doGenWHCheckOutSendFee", params,function(ret){
		if(ret.rs==true){
			var sendFeeList =ret.datas.sendFeeList;
			if(ids){
				for(var i=0;i<ids.length;i++){
					for(var j=0;j<=sendFeeList.length-1;j++){
						if(i==j){
							// if (sendFeeList[i].hassupplier!='1') break;
							rowData[i]['autosendfee']=sendFeeList[i].autosendfee;
							rowData[i]['autolongfee']=sendFeeList[i].autolongfee;
							rowData[i]['autocarryfee']=sendFeeList[i].autocarryfee;	
						}
					}
		 		}
				$("#dataTable_itemAppSend").jqGrid("clearGridData");
				// $.each(rowData, function(k,v){
				//	 $("#dataTable_itemAppSend").jqGrid("addRowData", k+1, v);
				// });	
				Global.JqGrid.addRowData("dataTable_itemAppSend",rowData);
			}	
			
			$("#_form_token_uniq_id").val(ret.token.token);
			art.dialog({
				content:ret.msg,
				time:1000,
			});				
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
			art.dialog({
				content:ret.msg,
				width:200
			});
		}	
	});	
}

 </script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "  id="addItemAppSend">新增</button>
				<button type="button" class="btn btn-system "  id="calcSendFeeBtn" onclick="calcSendFee()">配送费计算</button>
				<button type="button" class="btn btn-system "  id="delItemAppSend">删除</button>
				<button type="button" class="btn btn-system " id="viewItemAppSend">查看领料单</button>
				<house:authorize authCode="WHCHECKOUT_EXCEL">
					<button type="button" class="btn btn-system "  id="itemAppSendExcel">输出到Excel</button>
				</house:authorize>	
			</div>
		</div>
	</div>
	<table id="dataTable_itemAppSend" style="overflow: auto;"></table>
</div>






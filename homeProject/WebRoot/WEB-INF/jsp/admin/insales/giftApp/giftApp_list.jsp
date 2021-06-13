<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>礼品领用管理</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_cmpactivity.js?v=${v}" type="text/javascript"></script>
	
	<style type="text/css">
	.panelBar{
		height:26px;
	}
	</style>
<script type="text/javascript">
 $(function(){
       //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/giftApp/goJqGrid",
		postData:{status:"OPEN,SEND,RETURN"},
		ondblClickRow: function(){
             view();
        },
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		colModel : [
		  {name : 'no',index : 'no',width : 80,label:'领用单号',sortable : true,align : "left"},
		  {name : 'type',index : 'type',width : 70,label:'领用类型',sortable : true,align : "left",hidden:true},
	      {name : 'typedescr',index : 'typedescr',width : 75,label:'领用类型',sortable : true,align : "left",count:true},
	      {name : 'outtype',index : 'outtype',width :  68,label:'出库类型',sortable : true,align : "left",hidden:true},
	      {name : 'outtypedescr',index : 'outtypedescr',width : 68,label:'出库类型',sortable : true,align : "left",count:true},
	      {name : 'custcode',index : 'custCode',width :  65,label:'客户编号',sortable : true,align : "left"},
	      {name : 'custdescr',index : 'custdescr',width :  68,label:'客户名称',sortable : true,align : "left"},
	      {name : 'address',index : 'address',width : 120,label:'楼盘',sortable : true,align : "left"},
	      {name : 'setdate',index : 'setdate',width : 80,label:'下定时间',sortable : true,align : "left",formatter:formatDate},
          {name : 'signdate',index : 'signdate',width : 80,label:'签订时间',sortable : true,align : "left",formatter:formatDate},
	      {name : 'useman',index : 'useman',width : 100,label:'领用人',sortable : true,align : "left",hidden:true},
	      {name : 'usemandescr',index : 'usemandescr',width : 65,label:'领用人',sortable : true,align : "left"},
	      {name : 'phone',index : 'a.Phone',width : 100,label:'领用人电话',sortable : true,align : "left"},
	      {name : 'status',index : 'status',width : 100,label:'状态',sortable : true,align : "left",hidden:true},
	      {name : 'statusdescr',index : 'statusdescr',width : 60,label:'状态',sortable : true,align : "left"},
	      {name : 'sendtype',index : 'sendType',width : 100,label:'发货类型',sortable : true,align : "left",hidden:true},
	      {name : 'sendtypedescr',index : 'sendtype',width : 70,label:'发货类型',sortable : true,align : "left"},
	      {name : 'actdescr',index : 'actdescr',width : 100,label:'活动名称',sortable : true,align : "left"},
	      {name : 'supplcode',index : 'supplcode',width : 100,label:'供应商',sortable : true,align : "left",hidden:true},
	      {name : 'suppldescr',index : 'suppldescr',width : 100,label:'供应商',sortable : true,align : "left"},
	      {name: 'splstatus', index: 'splstatus', width: 90, label: '供应商状态', sortable: true, align: "left", hidden: true},
          {name: 'splstatusdescr', index: 'splstatusdescr', width: 90, label: '供应商状态', sortable: true, align: "left"},
	      {name : 'whcode',index : 'whcode',width : 100,label:'仓库编号',sortable : true,align : "left",hidden:true},
	      {name : 'whdescr',index : 'whdescr',width : 80,label:'仓库名称',sortable : true,align : "left"},
	      {name : 'puno',index : 'a.puno',width : 85,label:'采购单号',sortable : true,align : "left"},
	      {name : 'oldno',index : 'oldno',width : 80,label:'原领用单号',sortable : true,align : "left"}, 
	      {name : 'date',index : 'date',width : 80,label:'开单时间',sortable : true,align : "left",formatter:formatDate},
	      {name : 'appczydescr',index : 'appczydescr',width : 65,label:'开单人',sortable : true,align : "left"},
	      {name : 'senddate',index : 'senddate',width : 80,label:'发货时间',sortable : true,align : "left",formatter:formatDate},
	      {name : 'sendczydescr',index : 'sendczydescr',width : 65,label:'发货人',sortable : true,align : "left"},
          {name: 'splrcvczydescr', index: 'splrcvczydescr', width: 100, label: '供应商接收人', sortable: true, align: "left"},
          {name: 'splrcvdate', index: 'splrcvdate', width: 110, label: '供应商接收日期', sortable: true, align: "left", formatter: formatDate},
          {name: 'splremark', index: 'splremark', width: 150, label: '供应商备注', sortable: false, align: "left"},
	      {name : 'checkoutno',index : 'checkoutno',width : 100,label:'记账单号',sortable : true,align : "left"},
	      {name : 'checkoutstatus',index : 'checkoutstatus',width : 100,label:'记账单状态',sortable : true,align : "left"},
	      {name : 'checkoutdocumentno',index : 'checkoutdocumentno',width : 105,label:'记账凭证号',sortable : true,align : "left"},
	      {name : 'remarks',index : 'remarks',width : 180,label:'备注',sortable : true,align : "left"},
	      {name: 'lastupdate', index: 'lastupdate', width: 135, label: '最后修改时间', sortable: true, align: 'left', formatter: formatTime},
	      {name : 'lastupdatedby',index : 'a.LastUpdatedBy',width : 100,label:'最后更新人员',sortable : true,align : "left"},
	      {name : 'expired',index : 'a.Expired',width : 100,label:'是否过期',sortable : true,align : "left"},
	      {name : 'actionlog',index : 'a.ActionLog',width : 100,label:'操作',sortable : true,align : "left"}
        ]                        
	 });
	 $("#custCode").openComponent_customer(); 
	 $("#useMan").openComponent_employee();
	 $("#appCzy").openComponent_employee();
	 $("#whCode").openComponent_wareHouse();  
	 $("#supplCode").openComponent_supplier({condition:{itemType1:'LP',readonly:"1",ReadAll:"1"}});  
	 $("#actNo").openComponent_cmpactivity(); 
	 
	 onCollapse(0)
});  
function addComeGive(){	
	Global.Dialog.showDialog("ComeGiveAdd",{			
		  title:"来就送",
		  url:"${ctx}/admin/giftApp/goComeGive",
		  height: 700,
		  width:1000,
		  returnFun: goto_query,
		  close:function(){
			goto_query();
		  }
	});
}
function addSignGive(){	
	Global.Dialog.showDialog("SignGiveAdd",{			
		  title:"下定签单送",
		  url:"${ctx}/admin/giftApp/goSignGive",
		  height: 700,
		  width:1000,
		  returnFun: goto_query,
		  close:function(){
			goto_query();
		  }
	});
}

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	
	$("#splStatus").val('')
    $.fn.zTree.getZTreeObj("tree_splStatus").checkAllNodes(false)
}
function addEmpBuy(){	
	Global.Dialog.showDialog("EmpBuyAdd",{			
		  title:"员工购买",
		  url:"${ctx}/admin/giftApp/goEmpBuy",
		  height: 700,
		  width:1000,
		  returnFun: goto_query
	});
}
function addInternal(){	
	Global.Dialog.showDialog("InternalAdd",{			
		  title:"内部领用",
		  url:"${ctx}/admin/giftApp/goInternal",
		  height: 700,
		  width:1000,
		  returnFun: goto_query
	});
}
function addReturn(){	
	Global.Dialog.showDialog("InternalAdd",{			
		  title:"礼品领用——退回",
		  url:"${ctx}/admin/giftApp/goReturn",
		  height: 700,
		  width:1000,
		  returnFun: goto_query,
	});
}
function ReturnConfirm(){	
	var ret = selectDataTableRow();
    if (ret) {
    	if($.trim(ret.status)!='OPEN'||$.trim(ret.outtype)!='2'){
			art.dialog({
				content:'只能对申请状态的退回单进行退回确认操作',
			});
			return false;
	  	}   	
    	Global.Dialog.showDialog("CarryRuleUpdate",{
			title:"礼品领用——退回确认",
			url:"${ctx}/admin/giftApp/goReturnConfirm?id="+ret.no,
			height: 750,
			width:1000,
			returnFun: goto_query
		});
    }else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function Cancel(){	
	var ret = selectDataTableRow();
    if (ret) {  
    	if($.trim(ret.status)!='OPEN'){
			art.dialog({
				content:'只能对申请状态的领用单进行取消操作',
			});
			return false;
		}
		
		if (ret.splstatus === '2') {
		    art.dialog({
                content:'供应商状态是已接收的领用单不能进行取消操作',
            });
            return false
		}
		
	    Global.Dialog.showDialog("CarryRuleUpdate",{
			title:"礼品领用管理--取消",
			url:"${ctx}/admin/giftApp/goCancel?id="+ret.no,
			height: 750,
			width:1000,
			returnFun: goto_query
		});
	}else {
	    art.dialog({
			content: "请选择一条记录"
		});
	}
}
function update(){
	var ret = selectDataTableRow();
    if (ret) {    
        if($.trim(ret.status)!='OPEN'){
			art.dialog({
				content:'只能对申请状态的领用单进行编辑操作',
			});
		return false;
		}
		
		if (ret.splstatus === '2') {
            art.dialog({
                content:'供应商状态是已接收的领用单不能进行编辑操作',
            });
            return false
        }
		
      	Global.Dialog.showDialog("CarryRuleUpdate",{
			title:"礼品领用管理--修改",
			url:"${ctx}/admin/giftApp/goUpdate?id="+ret.no,
			height: 700,
			width:1000,
			returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {    	
        Global.Dialog.showDialog("giftAppview",{
			title:"礼品管理--查看",
			url:"${ctx}/admin/giftApp/goview?id="+ret.no,
			height: 700,
			width:1000,
			returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function sendBySupp(){
	var ret = selectDataTableRow();
    if (ret) { 
        if($.trim(ret.status)!='OPEN'||$.trim(ret.outtype)!='1'){
			art.dialog({
				content:'只能对申请状态的领用单进行供应商直送操作',
			});
			return false;
	  	}      	
      	Global.Dialog.showDialog("sendBySupp",{
			title:"礼品领用管理--供应商直送",
			url:"${ctx}/admin/giftApp/goSendBySuppl?id="+ret.no,
			height:600,
			width:900,
			returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function send(){
	var ret = selectDataTableRow();
    if (ret) { 
     	if($.trim(ret.status)!='OPEN'||$.trim(ret.outtype)!='1'){
			art.dialog({
				content:'只能对申请状态的领用单进行取消操作',
			});
			return false;
		 }        	
     	Global.Dialog.showDialog("send",{
			title:"礼品领用管理--仓库发货",
			url:"${ctx}/admin/giftApp/goSend?id="+ret.no,
			height:600,
			width:900,
			returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function detailView(){	
	Global.Dialog.showDialog("DetailView",{			
		title:"礼品领用-明细查询",
		url:"${ctx}/admin/giftApp/goDetailView",
		height: 800,
		width:1200,
		returnFun: goto_query
	});
}
 //批量打印
function qPrintGiftApp(){
    //查看窗口
	Global.Dialog.showDialog("goQPrint",{
 	  	title: "批量打印礼品单",
 	  	url: "${ctx}/admin/giftApp/goQPrint",
 	    height: 800,
		width:1200,
	});       	
}
//打印
function printGiftApp(){
	var row = selectDataTableRow();
	if(!row){
		art.dialog({content: "请选择一条记录进行打印操作！",width: 200});
		return false;
	} 
	var	reportName="giftAppDetail.jasper";
      	Global.Print.showPrint(reportName, {
		No: $.trim(row.no) ,
		LogoPath : "<%=basePath%>jasperlogo/",
		SUBREPORT_DIR: "<%=jasperPath%>" 
	});
}
//导出EXCEL
function doExcel(){
  	var url = "${ctx}/admin/giftApp/doExcel";
  	doExcelAll(url);
}
 //批量发货
function sendBatch(){	
	Global.Dialog.showDialog("sendBatch",{			
		title:"礼品领用——批量发货",
		url:"${ctx}/admin/giftApp/goSendBatch",
		height: 800,
		width:1200,
		close:function(){
		   goto_query()
		}
	});				 
}
</script>
</head>
 
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value="${giftApp.expired}" />
				<ul class="ul-form">
					<li>
						<label>领用单号</label>
					    <input type="text" id="no" name="no"   value="${gifApp.no}" />
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${gifApp.address }" />
					</li>
					<li>
						<label>客户编号</label>
						<input type="text" id="custCode" name="custCode" style="width:160px;" value="${gifApp.custCode}" />
					</li>
					<li>
						<label>领用人</label>
						<input type="text" id="useMan" name="useMan" style="width:160px;" value="${gifApp.useMan }" />
					</li>
					<li>
						<label>状态</label>
						<house:xtdmMulit id="status" dictCode="GIFTAPPSTATUS" selectedValue="OPEN,SEND,RETURN"></house:xtdmMulit>
					</li>
					<li>
						<label>发货类型</label>
						<house:xtdm id="sendType" dictCode="GIFTAPPSENDTYPE"  value="${giftApp.sendType}"></house:xtdm>
					</li>
				     <li>
						<label>活动编号</label>
						<input type="text" id="actNo" name="actNo" style="width:160px;" value="${gifApp.actNo }" />
					</li>
					 <li>
						<label>供应商</label>
						<input type="text" id="supplCode" name="supplCode" style="width:160px;" value="${giftApp.supplCode}"/>
					</li>
					<li>
                    <label>供应商状态</label>
                        <house:xtdmMulit id="splStatus" dictCode="GIFTAPPSPLSTAT"></house:xtdmMulit>
                    </li>
					 		
							
					<li>
						<label>仓库</label>
						<input type="text" id="whCode" name="whCode" style="width:160px;" value="${giftApp.whCode}"/>
					</li>	
						
					

					<li id="loadMore">
					    <button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">更多</button>
					    <button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
					    <button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
					<div class="collapse" id="collapse">
					    <ul>
						    <li>
		                        <label>开单人</label>
		                        <input type="text" id="appCzy" name="appCzy" style="width:160px;" value="${gifApp.appczy }" />
		                    </li>
					        <li>
		                        <label>开单时间从</label>
		                        <input type="text" style="width:160px;" id="dateFrom" name="dateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.dateFrom}' pattern='yyyy-MM-dd'/>"/>
		                    </li>                                                       
		                    <li>
		                        <label>到</label>
		                        <input type="text" style="width:160px;" id="dateTo" name="dateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.dateTo }' pattern='yyyy-MM-dd'/>"/>
		                    </li>
		                    <li>
		                        <label>发货时间从</label>
		                        <input type="text" style="width:160px;" id="sendDateFrom" name="sendDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.sendDateFrom }' pattern='yyyy-MM-dd'/>"/>
		                    </li>                   
		                    <li>
		                        <label>到</label>
		                        <input type="text" style="width:160px;" id="sendDateTo" name="sendDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.sendDateTo }' pattern='yyyy-MM-dd'/>"/>
		                    </li>
		                    <li>
		                        <label> 领用类型</label>
		                        <house:xtdm id="type" dictCode="GIFTAPPTYPE"  value="${giftApp.type}" ></house:xtdm>
		                    </li>
		                    <li>
		                        <label>出库类型</label>
		                        <house:xtdm id="outType" dictCode="GIFTAPPOUTTYPE"  value="${giftApp.outType} "></house:xtdm>
		                    </li>
					        <li class="search-group-shrink">
					            <button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">收起</button>
					            <input type="checkbox" id="expired_show" name="expired_show" value="${itemApp.expired}" onclick="hideExpired(this)" ${itemApp.expired!='T' ?'checked':'' }/><p>隐藏过期</p>
					            <button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
					            <button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					        </li>
					    </ul>
					</div>
				</ul>
			</form>
		</div>
		
		<div class="clear_float"> </div>
		
		<div class="btn-panel" >
		<div class="btn-group-sm"  >
		  <house:authorize authCode="GIFTAPP_COMEGIVE">
	      		<button type="button" class="btn btn-system " onclick="addComeGive()">来就送</button>
	      </house:authorize>
          <house:authorize authCode="GIFTAPP_SIGNGIVE">  
	      		<button type="button" class="btn btn-system " onclick="addSignGive()">下定签单送</button>
	      </house:authorize>
          <house:authorize authCode="GIFTAPP_EMPBUY">  
           		<button type="button" class="btn btn-system " onclick="addEmpBuy()">员工购买</button>
	      </house:authorize>
          <house:authorize authCode="GIFTAPP_INTERNAL">  
          		<button type="button" class="btn btn-system " onclick="addInternal()">内部领用</button>
	      </house:authorize>      	            
          <house:authorize authCode="GIFTAPP_RETURN">	
				<button type="button" class="btn btn-system " onclick="addReturn()">退回</button>
	      </house:authorize>      	            	
		  <house:authorize authCode="GIFTAPP_EDIT">	
             	<button type="button" class="btn btn-system " onclick="ReturnConfirm()">退回确认</button>
	      </house:authorize>   
	       <house:authorize authCode="GIFTAPP_EDIT">	
             	<button type="button" class="btn btn-system " onclick="Cancel()">取消</button>
	      </house:authorize>
		  <house:authorize authCode="GIFTAPP_EDIT">	
             	<button type="button" class="btn btn-system " onclick="update()">编辑</button>
	      </house:authorize>    
		  <house:authorize authCode="GIFTAPP_SENDBYSUPPL">	
             	<button type="button" class="btn btn-system " onclick="sendBySupp()">供应商直送</button>
	      </house:authorize> 		
		  <house:authorize authCode="GIFTAPP_SEND">	
			 	<button type="button" class="btn btn-system " onclick="send()">仓库发货</button>
	      </house:authorize> 		
		  <house:authorize authCode="GIFTAPP_VIEW">
		  		<button type="button" class="btn btn-system " onclick="view()">查看</button>
	      </house:authorize>
	      <house:authorize authCode="GIFTAPP_VIEW">
	  		<button type="button" class="btn btn-system " onclick="detailView()">明细查询</button>
         </house:authorize>
          <house:authorize authCode="GIFTAPP_SENDBATCH">	
			 	<button type="button" class="btn btn-system " onclick="sendBatch()">批量发货</button>
	      </house:authorize> 	
	      <house:authorize authCode="GIFTAPP_QPRINT">
					<button id="giftAppPrint" type="button" class="btn btn-system " onclick="printGiftApp()">打印</button>
		  </house:authorize> 
          <house:authorize authCode="GIFTAPP_QPRINT">
               <button type="button" class="btn btn-system " onclick="qPrintGiftApp()">批量打印</button>
		 </house:authorize>
	     <house:authorize authCode="GIFTAPP_EXCEL">
	    		<button type="button" class="btn btn-system " onclick="doExcel()">输出至excel</button>
		 </house:authorize>	 
		</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</div>
	
</body>

</html>

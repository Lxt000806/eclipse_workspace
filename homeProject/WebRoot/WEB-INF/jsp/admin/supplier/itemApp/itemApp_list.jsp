<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemApp列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
		$("#whcode").openComponent_wareHouse({condition: {czybh:'${czybh}'}});
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/supplierItemApp/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: 'Bootstrap',
			colModel : [
			  {name: 'no', index: 'no', width: 100, label: '领料单号', sortable: true, align: "left", count: true},
		      {name: 'regiondescr', index: 'regiondescr', width: 50, label: '片区', sortable: true, align: "left"},
		      {name: 'address', index: 'address', width: 160, label: '楼盘', sortable: true, align: "left"},
		      {name: 'realaddress', index: 'realaddress', width: 160, label: '实际地址', sortable: true, align: "left"},
			  {name: 'custcode', index: 'custcode', width: 90, label: '客户编号', sortable: true, align: "left", hidden: true},
		      {name: 'custdescr', index: 'custdescr', width: 90, label: '客户名称', sortable: true, align: "left"},
		      {name: 'documentno', index: 'documentno', width: 90, label: '档案编号', sortable: true, align: "left"},
		      {name: 'status', index: 'status', width: 70, label: '领料单状态', sortable: true, align: "left", hidden: true},
		      {name: 'splstatus', index: 'splstatus', width: 70, label: '供应商领料单状态', sortable: true, align: "left", hidden: true},
		      {name: 'statusdescr', index: 'statusdescr', width: 100, label: '领料单状态', sortable: true, align: "left"},
		      {name: 'appczydescr', index: 'appczydescr', width: 70, label: '申请人', sortable: true, align: "left"},
		      {name: 'confirmdate', index: 'confirmdate', width: 90, label: '审核日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'senddate', index: 'senddate', width: 90, label: '发货日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'maingjdescr', index: 'maingjdescr', width: 70, label: '主材管家', sortable: true, align: "left"},
		      {name: 'remarks', index: 'remarks', width: 180, label: '备注', sortable: true, align: "left"},
		      {name: 'whcodedescr', index: 'whcodedescr', width: 100, label: '仓库名称', sortable: true, align: "left"},
		      {name: 'projectman', index: 'projectman', width: 70, label: '项目经理', sortable: true, align: "left"},
		      {name: 'phone', index: 'phone', width: 100, label:'电话', sortable: true, align: "left"},
		      {name: 'leadername', index: 'leadername', width: 70, label: '工程经理', sortable: true, align: "left"},
		      {name: 'leaderphone', index: 'leaderphone', width: 100, label:'经理电话', sortable: true, align: "left"},
		      {name: 'delivtypedescr', index: 'delivtypedescr', width: 70, label: '配送方式', sortable: true, align: "left"},
		      {name: 'splstatus', index: 'splstatus', width: 70, label: '供应商状态', sortable: true, align: "left", hidden: true},
		      {name: 'splstatusdescr', index: 'splstatusdescr', width: 110, label: '供应商状态', sortable: true, align: "left"},
		      {name: 'pusplstatusdescr', index: 'pusplstatusdescr', width: 100, label: '供应商结算状态', sortable: true, align: "left"},
		      {name: 'arrivedate', index: 'arrivedate', width: 100, label: '预计到货日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'splremark', index: 'splremark', width: 180, label: '供应商备注', sortable: true, align: "left"},
		      {name: 'lastupdate', index: 'lastupdate', width: 100, label: '最后更新日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'lastupdatedby', index: 'lastupdatedby', width: 100, label: '最后更新人员', sortable: true, align : "left"},
		      {name: 'expired', index: 'expired', width: 100, label: '是否过期', sortable: true, align: "left"},
		      {name: 'actionlog', index: 'actionlog', width: 100, label: '操作', sortable: true, align: "left"},
		      {name: 'pusplstatus', index: 'pusplstatus', width: 100, label: '供应商结算状态', sortable: true, align: "left",hidden:true},
            ]
		});
		if("${supplItemType1}" !='ZC'){
			jQuery("#dataTable").setGridParam().hideCol("maingjdescr").trigger("reloadGrid");
		}
        //接收
        $("#receiveItemApp").on("click", function() {
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行接收操作！",width: 200});
    			return false;
    		}
    		
    		$.ajax({
				url : "${ctx}/admin/supplierItemApp/doCheckReceive",
				data : {no: row.no},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "json",
				type: "post",
				cache: false,
				error: function(){
			        art.dialog({
						content: '出现异常，无法接收'
					});
			    },
			    success: function(obj){
			    	if(obj.rs){
			        	Global.Dialog.showDialog("itemAppReceive", {
			        	  title: "接收领料单",
			        	  url: "${ctx}/admin/supplierItemApp/goReceive?id=" + row.no,
			        	  height: 700,
			        	  width: 1000,
			        	  postData: row.no,
			        	  returnFun: goto_query
			        	}); 			    		
			    	} else {
			    		art.dialog({
							content: obj.msg
						});
			    	}
				}
			});
        });    
            
        //领料修改
        $("#updateItemApp").on("click", function() {  		
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行修改操作！",width: 200});
    			return false;
    		}
    		
    		$.ajax({
				url : "${ctx}/admin/supplierItemApp/doCheckUpdate",
				data : {no: row.no},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "json",
				type: "post",
				cache: false,
				error: function(){
			        art.dialog({
						content: '出现异常，无法修改'
					});
			    },
			    success: function(obj){
			    	if(obj.rs){
			        	Global.Dialog.showDialog("itemAppUpdate", {
			        	  title: "修改领料单",
			        	  url: "${ctx}/admin/supplierItemApp/goUpdate?id=" + row.no,
			        	  height: 700,
			        	  width: 1000,
			        	  postData: row.no,
			        	  returnFun: goto_query
			        	}); 			    		
			    	} else {
			    		art.dialog({
							content: obj.msg
						});
			    	}
				}
			});
    		
        });
        
        //供应商确认
        $("#confirmItemApp").on("click", function() {  		
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行修改操作！",width: 200});
    			return false;
    		}
    		
    		$.ajax({
				url : "${ctx}/admin/supplierItemApp/doCheckSplConfirm",
				data : {no: row.no},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "json",
				type: "post",
				cache: false,
				error: function(){
			        art.dialog({
						content: '出现异常，无法确认'
					});
			    },
			    success: function(obj){
			    	if(obj.rs){
			        	Global.Dialog.showDialog("itemAppSplConfirm", {
			        	  title: "供应商确认",
			        	  url: "${ctx}/admin/supplierItemApp/goSplConfirm?id=" + row.no,
			        	  height: 700,
			        	  width: 1000,
			        	  postData: row.no,
			        	  returnFun: goto_query
			        	}); 			    		
			    	} else {
			    		art.dialog({
							content: obj.msg
						});
			    	}
				}
			});
    		
        });
        
        //领料退回
        $("#returnItemApp").on("click", function() {
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行退回操作！",width: 200});
    			return false;
    		}
    		
    		$.ajax({
				url : "${ctx}/admin/supplierItemApp/doCheckReturn",
				data : {no: row.no},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "json",
				type: "post",
				cache: false,
				error: function(){
			        art.dialog({
						content: '出现异常，无法退回'
					});
			    },
			    success: function(obj){
			    	if(obj.rs){
			        	Global.Dialog.showDialog("itemAppReturn", {
			        	  title: "退回领料单",
			        	  url: "${ctx}/admin/supplierItemApp/goReturn?id=" + row.no,
			        	  height: 700,
			        	  width: 1000,
			        	  postData: row.no,
			        	  returnFun: goto_query
			        	}); 			    		
			    	} else {
			    		art.dialog({
							content: obj.msg
						});
			    	}
				}
			});  	
        });
        
        //发货
        $("#sendItemApp").on("click",function(){
	        var row = selectDataTableRow();
    		if (!row) {
    			art.dialog({content: "请选择一条记录进行发货操作！", width: 200});
    			return false;
    		}
    		
       		$.ajax({
				url : "${ctx}/admin/supplierItemApp/doCheckSend",
				data : {no: row.no},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "json",
				type: "post",
				cache: false,
				error: function(){
			        art.dialog({
						content: '出现异常，无法发货'
					});
			    },
			    success: function(obj){
			    	if(obj.rs){
				    	if(obj.datas == "2"){ //可选通知发货
				    		art.dialog({
							content:"未收到发货通知，是否确认发货？",
							lock: true,
							width: 200,
							height: 100,
							ok: function () {
								Global.Dialog.showDialog("itemAppSend", {
					        	title: "领料发货",
					        	url: "${ctx}/admin/supplierItemApp/goSend?id=" + row.no,
					        	height: 700,
					        	width: 1000,
					        	postData: row.no,
					        	returnFun: goto_query
					        	});
							},
							cancel: function () {return true;}
							});
				    	}else{
				        	Global.Dialog.showDialog("itemAppSend", {
				        	  title: "领料发货",
				        	  url: "${ctx}/admin/supplierItemApp/goSend?id=" + row.no,
				        	  height: 700,
				        	  width: 1000,
				        	  postData: row.no,
				        	  returnFun: goto_query
				        	}); 	
			        	}		    		
			    	} else {
			    		art.dialog({
							content: obj.msg
						});
			    	}
				}
			});        	
        });   
        
       //领料查看
        $("#viewItemApp").on("click",function(){
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行查看操作！",width: 200});
    			return false;
    		}
    
            //查看窗口
        	Global.Dialog.showDialog("itemAppView",{
        	  title: "查看领料单",
        	  url: "${ctx}/admin/supplierItemApp/goView?id=" + row.no+"&splstatus="+row.splstatus,
        	  height: 700,
        	  width: 1000,
        	  postData: row.no,
        	  returnFun: goto_query
        	});
        });    
        
       //打印
        $("#printItemApp").on("click",function(){
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行打印操作！",width: 200});
    			return false;
    		} 
        	if("0"==$.trim(row.splstatus)||"1"==$.trim(row.splstatus)){
        		art.dialog({content: "请先接收再打印！",width: 200});
    			return false;
        	}
        	var reportName = "itemAppSend_main.jasper";
        	Global.Print.showPrint(reportName, {
				No: "'" + $.trim(row.no) + "'",
				LogoPath : "<%=basePath%>jasperlogo/",
				SUBREPORT_DIR: "<%=jasperPath%>" 
			});
        });
        
       //批量打印
        $("#qPrintItemApp").on("click",function(){
            //查看窗口
        	Global.Dialog.showDialog("itemAppView",{
        	  title: "批量打印领料单",
        	  url: "${ctx}/admin/supplierItemApp/goQPrint",
        	  height: 700,
        	  width: 1230
        	});
        });
       //批量接收
        $("#receiveItemAppBatch").on("click", function() {
        	Global.Dialog.showDialog("receiveItemAppBatch",{
        	  title: "批量接收领料单",
        	  url: "${ctx}/admin/supplierItemApp/goReceiveItemAppBatch",
        	  height: 700,
        	  width: 1000,
        	  returnFun: goto_query
        	});
        }); 
       //批量发货
        $("#sendItemAppBatch").on("click",function(){
        	Global.Dialog.showDialog("sendItemAppBatch",{
          	  title: "批量发货领料单",
          	  url: "${ctx}/admin/supplierItemApp/goSendItemAppBatch",
          	  height: 700,
          	  width: 1000,
          	  returnFun: goto_query
          	});
        });
        //结算申请
        $("#checkApp").on("click",function(){
        	var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行结算申请操作！"});
    			return false;
    		}
    		if(row.pusplstatus.trim()==""){
    			art.dialog({content: "请选择已发货的领料单！"});
    			return false;
    		}
        	Global.Dialog.showDialog("checkApp",{
          	  title: "结算申请",
          	  url: "${ctx}/admin/supplierItemApp/goCheckApp",
          	  postData:{id:row.no},
          	  height: 700,
          	  width: 1000,
          	  returnFun: goto_query
          	});
        });
        //领料明细查询
        $("#searchDetail").on("click",function(){    
        	Global.Dialog.showDialog("itemAppDetailList",{
        	  title: "明细查询",
        	  url: "${ctx}/admin/supplierItemApp/goDetailList",
        	  height: 700,
        	  width: 1200,
        	});
        });
        
        //水槽下单查询
        $("#dishesSend").on("click",function(){    
        	Global.Dialog.showDialog("dishesSendDetailList",{
        	  title: "水槽下单查询",
        	  url: "${ctx}/admin/supplierItemApp/goDishesSend",
        	  height: 700,
        	  width: 1200,
        	});
        });
        
        $("#doNotInstall").on("click", function(){
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录！",width: 200});
    			return false;
    		} 
        	Global.Dialog.showDialog("doNotInstall",{
        	  title: "不能安装",
        	  url: "${ctx}/admin/supplierItemApp/doNotInstall",
        	  postData: {
        	  	no: row.no
        	  },
        	  height: 400,
        	  width: 750,
        	});
        });
        $("#notInstallDetail").on("click", function(){
        	Global.Dialog.showDialog("notInstallDetail",{
        	  title: "不能安装明细",
        	  url: "${ctx}/admin/supplierItemApp/goNotInstallDetail",
        	  height: 600,
        	  width: 1000,
        	});
        });
        $("#intReplenishDetail").on("click", function(){
        	Global.Dialog.showDialog("intReplenishDetail",{
        	  title: "补货明细",
        	  url: "${ctx}/admin/supplierItemApp/goIntReplenishDetail",
        	  height: 600,
        	  width: 1100,
        	});
        });
        $("#loadIntPic").on("click", function(){
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录！",width: 200});
    			return false;
    		} 
        	Global.Dialog.showDialog("loadIntPic",{
        	  title: "集成图纸下载",
        	  url: "${ctx}/admin/supplierItemApp/goLoadIntPic",
        	  postData: {
        	  	code: row.custcode
        	  },
			  height:700,
			  width:1250,
        	});
        });
        //生产进度登记
		$("#intProduce").on("click", function(){
			Global.Dialog.showDialog("intProduce",{
				title: "生产进度登记",
				url: "${ctx}/admin/supplierItemApp/goIntProduce",
				height: 716,
				width: 1174,
			});
		});
});

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$("#splStatus").val('');
	$.fn.zTree.getZTreeObj("tree_splStatus").checkAllNodes(false);
}
function goto_query() {
	var postData = $("#page_form").jsonForm();

	if(postData && postData.status && postData.status.indexOf("SEND") >= 0){
		postData.IsMaterialSendJob = "";
	}

	$("#dataTable").jqGrid("setGridParam",{
		postData: postData,
		page:1,
		sortname:''
	}).trigger("reloadGrid");
}
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" id="expired" name="expired" value="${itemApp.expired }" />
				<input type="hidden" id="module" name="module" value="SupplierItemApp" />
				<input type="hidden" id="czybh" name="czybh" value="${itemApp.czybh}" />
				<ul class="ul-form">
					<li>
					<label>领料单状态</label>
					<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='ITEMAPPSTATUS' and (CBM='CONFIRMED' or CBM='SEND')" selectedValue="CONFIRMED"></house:xtdmMulit>
					</li>	
					<li>
					<label>供应商状态</label>
					<house:xtdmMulit id="splStatus" dictCode="APPSPLSTATUS"></house:xtdmMulit>
					</li>
					<li>
					<label>楼盘</label>
					<input type="text" id="custAddress" name="custAddress" />
					</li>
					<li>
					<label>仓库编号</label>
					<input type="text" id="whcode" name="whcode" value="${itemApp.whcode}"/>
					</li> 
					<li>
					<label>审核时间</label>
					<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.confirmDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
					<label>至</label>
					<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.confirmDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
					<label>发货类型</label>
					<house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE" value="${itemApp.sendType }"></house:xtdm>
					</li>
					<li>
					<label>到货状态</label>
					<house:xtdm id="confirmStatus" dictCode="SENDCFMSTATUS" value="${itemApp.confirmStatus }"></house:xtdm>
					</li>
					<li>
					<label>发货日期</label>
					<input type="text" id="sendDateFrom" name="sendDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.sendDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
					<label>至</label>
					<input type="text" id="sendDateTo" name="sendDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.sendDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>供应商结算状态</label> <house:xtdm id="puSplStatus"
							dictCode="PuSplStatus" value="${itemApp.puSplStatus}" ></house:xtdm>
					</li>
					<li>
						<label>配送方式</label>
						<house:xtdm id="delivType" dictCode="DELIVTYPE"/>
					</li>
					<li><label>是否通知发货</label> <house:xtdm id="IsMaterialSendJob" dictCode="YESNO"
							value="${itemApp.isMaterialSendJob}"></house:xtdm>
					</li>
					<li id="loadMore" >
					<button type="button" class="btn btn-system btn-sm" onclick="goto_query();" >查询</button>
					<button type="button" class="btn btn-system btn-sm" onclick="clearForm();" >清空</button>
					</li>
				</ul>
			</form>
		</div>

		<div class="btn-panel">
			<div class="btn-group-sm">
            	<house:authorize authCode="SUPPLIER_ITEMAPP_RECEIVE">
            		<button type="button" class="btn btn-system " id="receiveItemApp">接收</button>	
                </house:authorize>
            	<house:authorize authCode="SUPPLIER_ITEMAPP_UPDATE">
            		<button type="button" class="btn btn-system " id="updateItemApp">修改</button>	
                </house:authorize>
                <house:authorize authCode="SUPPLIER_ITEMAPP_CONFIRM">
                	<button type="button" class="btn btn-system " id="confirmItemApp">确认</button>	
                </house:authorize>
                <house:authorize authCode="SUPPLIER_ITEMAPP_RETURN">
                	<button type="button" class="btn btn-system " id="returnItemApp">退回</button>	
                </house:authorize>
                <house:authorize authCode="	SUPPLIER_ITEMAPP_SEND">
                	<button type="button" class="btn btn-system " id="sendItemApp">发货</button>
                </house:authorize>
                <button type="button" class="btn btn-system " id="viewItemApp">查看</button>
                <button type="button" class="btn btn-system " id="printItemApp">打印</button>
                <button type="button" class="btn btn-system " id="qPrintItemApp">批量打印</button>
				<house:authorize authCode="SUPPLIER_ITEMAPP_RECEIVE_BATCH">
					<button type="button" class="btn btn-system " id="receiveItemAppBatch">批量接收</button>
                </house:authorize>
                <house:authorize authCode="	SUPPLIER_ITEMAPP_SEND_BATCH">
                	<button type="button" class="btn btn-system " id="sendItemAppBatch">批量发货</button>
                </house:authorize>
                <c:if test="${hasAuthCheckApp}"> 
                	<button type="button" class="btn btn-system " id="checkApp">结算申请</button>
                </c:if>
                <button type="button" class="btn btn-system " id="searchDetail">明细查询</button>
                <house:authorize authCode="SUPPLJOB_DISHESSEND">
                	<button type="button" class="btn btn-system " id="dishesSend">水槽下单</button>
                </house:authorize>
                <house:authorize authCode="SUPPLIER_ITEMAPP_NOTINSTALL">
                	<button type="button" class="btn btn-system " id="doNotInstall">不能安装</button>
                </house:authorize>
                <button type="button" class="btn btn-system " id="notInstallDetail">不能安裝明細</button>
                 <house:authorize authCode="SUPPLIER_ITEMAPP_LOADINTPIC">
                	<button type="button" class="btn btn-system " id="loadIntPic">集成图纸下载</button>
                </house:authorize>
                 <house:authorize authCode="SUPPLIER_ITEMAPP_INTREPLENISHDETAIL">
                	<button type="button" class="btn btn-system " id="intReplenishDetail">补货明细</button>
                </house:authorize>
                <house:authorize authCode="SUPPLIER_ITEMAPP_INTPRODUCE"><!-- add by zb on 20200228 -->
                	<button type="button" class="btn btn-system " id="intProduce">生产进度登记</button>
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



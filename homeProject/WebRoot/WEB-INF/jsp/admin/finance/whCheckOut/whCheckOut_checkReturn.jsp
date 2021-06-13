<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>反审核whCheckOut</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
</script>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "   id="checkBackBtn">反审核</button>
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="B"/>
				<ul class="ul-form">
					<div class="validate-group">
						<li>
							<label><span class="required"></span>记账单号</label>
							<input type="text" id="no" name="no" value="${whCheckOut.no}" placeholder="保存自动生成" readonly="readonly"/>
						</li>
						<li>	
							<label><span class="required">*</span>申请日期</label>
							<input type="text" id="date" name="date" class="i-date" 
						     value="<fmt:formatDate value='${whCheckOut.date}'  pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
						</li>
						<li class="form-validate">	
							<label>账单状态</label>
							<house:xtdm id="status" dictCode="WHChkOutStatus" value="${whCheckOut.status}" disabled="true"></house:xtdm>
						</li>
					</div>
					<div class="validate-group">
						<li>	
							<label><span class="required">*</span>申请人</label>
							<input type="text" id="appCZY" name="appCZY" value="${whCheckOut.appCZY}" />
						</li>
						<li>	
						    <label></span>凭证号</label>
							<input type="text" id="documentNo" name="documentNo" value="${whCheckOut.documentNo}" readonly="readonly"/>
						</li>
						<li>	
							<label>审核日期</label>
							<input type="text" id="confirmDate" name="confirmDate" class="i-date" 
							 value="<fmt:formatDate value='${whCheckOut.confirmDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
						</li>
					</div>
					<div class="validate-group">
						<li>	
							<label>记账日期</label>
							<input type="text" id="checkDate" name="checkDate" class="i-date"
							 value="<fmt:formatDate value='${whCheckOut.checkDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"readonly="readonly"/>
						</li>
						<li>	
							<label>审核人</label>
							<input type="text" id="confirmCZY" name="confirmCZY" value=value="${whCheckOut.confirmCZY}" readonly="readonly"/>
						</li>
						<li class="form-validate">	
							<label><span class="required">*</span>仓库</label>
							<input type="text" id="whCode" name="whCode" value="${whCheckOut.whCode}" />
						</li>
					</div>
					<div class="validate-group">
						<li>	
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" >${whCheckOut.remarks}</textarea>
						</li>
					</div>
				</ul>
			</form>
		</div>
	</div>
	<div  class="container-fluid" >  
	     <ul class="nav nav-tabs" >
	        <li class="active"><a href="#tab_itemAppSend" data-toggle="tab">领料发货单</a></li>  
	        <li class=""><a href="#tab_salesInvoice" data-toggle="tab">销售单</a></li>
	        <li class=""><a href="#tab_totalByItemType2" data-toggle="tab">按材料类型2汇总</a></li>  
	        <li class=""><a href="#tab_totalByBrand" data-toggle="tab">按品牌汇总</a></li> 
	        <li id="tabtotalByCompany" class=""><a href="#tab_totalByCompany" data-toggle="tab">按公司汇总</a></li> 
	    </ul>  
	    <div class="tab-content">  
			<div id="tab_itemAppSend"  class="tab-pane fade in active"> 
		    	<jsp:include page="tab_itemAppSend.jsp"></jsp:include>
			</div>  
			<div id="tab_salesInvoice"  class="tab-pane fade "> 
	         	<jsp:include page="tab_salesInvoice.jsp"></jsp:include>
			</div>
			<div id="tab_totalByItemType2"  class="tab-pane fade "> 
	         	<jsp:include page="tab_totalByItemType2.jsp"></jsp:include> 
			</div>
			<div id="tab_totalByBrand"  class="tab-pane fade ">  
	         	<jsp:include page="tab_totalByBrand.jsp"></jsp:include>
			</div>
			<div id="tab_totalByCompany"  class="tab-pane fade ">  
	         	<jsp:include page="tab_totalByCompany.jsp"></jsp:include>
			</div>
		</div>	
	</div>	
    <script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>  
<script type="text/javascript">
	function save(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;//表单校验
		
		//保存表单 
		Global.Form.submit("dataForm","${ctx}/admin/whCheckOut/doSave",$("#dataForm").jsonForm(),function(ret){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					/* time: 1000, */
					beforeunload: function () {
	    				closeWin();
	    			
				    }
				});
	    	}else{
				art.dialog({content: obj.msg,width: 200});
	    	}
		});
	}
	function validateRefresh(){
		 $('#dataForm').data('bootstrapValidator')
	                   .updateStatus('openComponent_wareHouse_whCode', 'NOT_VALIDATED',null)  
	                   .validateField('openComponent_wareHouse_whCode')
	                   .updateStatus('status_disabled', 'NOT_VALIDATED',null)  
	                   .validateField('status_disabled');                   
	}
	function fillData(ret){
		validateRefresh();
	}
	//校验函数
	$(function() {
		$("#whCode").openComponent_wareHouse({showValue:"${whCheckOut.whCode}",showLabel:"${whCheckOut.whDescr}",readonly:true,callBack:fillData});
		$("#appCZY").openComponent_employee({showValue:"${whCheckOut.appCZY}",showLabel:"${whCheckOut.appCZYDescr}",readonly:true});
	    $("#confirmCZY").openComponent_employee({showValue:"${whCheckOut.confirmCZY}",showLabel:"${whCheckOut.confirmCZYDescr}",readonly:true});
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields: {  
				openComponent_wareHouse_whCode:{  
					validators: {  
						notEmpty: {  
							message: '仓库不能为空'  
						},
						stringLength: {
							max: 15,
							message: '长度不超过15个字符'
						}
					}  
				},
				status_disabled:{
					validators:{
						stringLength: {
							max: 10,
							message: '长度不超过10个字符'
						} 
					}
				}
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});
			
	//反审核
	$("#checkBackBtn").on("click",function(){
		if(!$("#dataForm").valid()) {return false;}//表单校验
		var rows = $("#dataTable_itemAppSend").getGridParam("records"); //获取当前显示的记录
		var rows2 = $("#dataTable_salesInvoice").getGridParam("records"); //获取当前显示的记录
		if((rows == 0)&&(rows2 == 0)){
			art.dialog({content: "明细表中无数据！",width: 200});
			return false;
		}
		art.dialog({
			 content:'是否反审核['+rows+']条领料发货单,['+rows2+']条销售单？',
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
			 	var itemAppsendDetail=$('#dataTable_itemAppSend').jqGrid("getRowData");
		    	var salesInvoiceDetail = $('#dataTable_salesInvoice').jqGrid("getRowData");
				/* var itemAppsendDetailJson = Global.JqGrid.allToJson("dataTable_itemAppSend");
				var salesInvoiceDetailJson = Global.JqGrid.allToJson("dataTable_salesInvoice"); */
				var param = {"itemAppsendDetailJson": JSON.stringify(itemAppsendDetail),"salesInvoiceDetailJson":JSON.stringify(salesInvoiceDetail)};
				Global.Form.submit("dataForm","${ctx}/admin/whCheckOut/doCheckBack",param,function(ret){
					if(ret.rs==true){
						art.dialog({
							content: ret.msg,
							time: 1000,
							beforeunload: function () {
			    				closeWin();
						    }
						});
					}else{
						$("#_form_token_uniq_id").val(ret.token.token);
			    		art.dialog({
							content: ret.msg,
							width: 200
						});
					}
					
				});
			},
			cancel: function () {
				return true;
			}
		});
		
	});
	//查看领料单
	$("#viewItemAppSend").on("click",function(){
		var ret = selectDataTableRow("dataTable_itemAppSend");
		if(ret){
	       	Global.Dialog.showDialog(ret.type=="S"?"itemAppSend":"itemAppReturn",{
	       		title:ret.type=="S"?"领料单--查看":"领料退回--查看",
	       	  	url:"${ctx}/admin/itemApp/goLlgzView",
	       	  	postData:{
	       	  		type:ret.type,
	       	  		no:ret.iano
	       	  	},
	       	  	height: 730,
	       	  	width:ret.type=="S"?1400:1300,
	       	});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});
		}
	 });
	
	//发货单输出到Excel
	$("#itemAppSendExcel").on("click",function(){
		doExcelNow("发货单导出","dataTable_itemAppSend","dataForm");
	});	
	//销售单输出到Excel
	$("#salesInvoiceExcel").on("click",function(){
		doExcelNow("销售单导出","dataTable_salesInvoice","dataForm");
	});
	//按材料类型2输出到Excel
	$("#totalByItemTypeExcel").on("click",function(){
		doExcelNow("按材料类型2汇总导出","dataTable_totalByItemType2","dataForm");
	});
	//按品牌输出到Excel
	$("#totalByBrandExcel").on("click",function(){
		doExcelNow("按品牌汇总导出","dataTable_totalByBrand","dataForm");
	});
	//打印按材料类型2统计
	$("#totalByItemType2Print").on("click",function(){
		var totalrow =$("#dataTable_totalByItemType2").jqGrid('getGridParam', 'records'); 
		if(totalrow<1){
			art.dialog({content: "无明细数据，不能打印！",width: 200});
			return false;
		}
	    var reportName = "whCheckOut_totalByItemType2.jasper";
	    Global.Print.showPrint(reportName, {
		No: "${whCheckOut.no}",
		LogoPath : "<%=basePath%>jasperlogo/", 
		SUBREPORT_DIR: "<%=jasperPath%>" 
		});	
	});
	//打印按品牌统计
	$("#totalByBrandPrint").on("click",function(){
		 var totalrow =$("#dataTable_totalByBrand").jqGrid('getGridParam', 'records'); 
		 if(totalrow<1){
			art.dialog({content: "无明细数据，不能打印！",width: 200});
			return false;
		 }
	     var reportName = "whCheckOut_totalByBrand.jasper";
	     Global.Print.showPrint(reportName, {
		 No: "${whCheckOut.no}",
		 LogoPath : "<%=basePath%>jasperlogo/", 
		 SUBREPORT_DIR: "<%=jasperPath%>" 
		});	
	});
</script>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czylb=uc.getCzylb();
String czybh="";
if("2".equals(czylb)) czybh=uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
	<title>添加whCheckOut</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
//tab分页
$(function(){
  if("<%=czylb %>"!="1"){
   	    $("#tabsalesInvoice").css("display","none");
		$("#tabtotalByItemType2").css("display","none");
		$("#tabtotalByBrand").css("display","none");
  }
}); 
</script>

</head> 
<body>

<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "   id="saveBtn">保存</button>
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="A"/>
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
						<li class="form-validate">
						   	<label><span class="required">*</span>仓库</label>
							<input type="text" id="whCode" name="whCode"  />
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
							<input type="text" id="confirmCZY" name="confirmCZY" readonly="readonly"/>
						</li>
						<li id="trdocumentNo">
							<label><div class="labeldocumentNo">凭证号</div></label>
							<input type="text" id="documentNo" name="documentNo" value="${whCheckOut.documentNo}"  readonly="readonly"/>
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
	     <ul class="nav nav-tabs">
	        <li id="tabitemAppSend" class="active"><a href="#tab_itemAppSend" data-toggle="tab">领料发货单</a></li>  
	        <li id="tabsalesInvoice" class=""><a href="#tab_salesInvoice" data-toggle="tab">销售单</a></li>
	        <li id="tabtotalByItemType2" class=""><a href="#tab_totalByItemType2" data-toggle="tab">按材料类型2汇总</a></li>  
	        <li id="tabtotalByBrand" class=""><a href="#tab_totalByBrand" data-toggle="tab">按品牌汇总</a></li> 
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
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>  
<script type="text/javascript">
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
		$("#appCZY").openComponent_czybm({showValue:"${whCheckOut.appCZY}",showLabel:"${whCheckOut.appCZYDescr}",readonly:true});
		if("<%=czylb %>"=="1"){
		       	 	$("#whCode").openComponent_wareHouse({callBack:fillData,condition:{ctrlItemType1:"1"},});
		       }else{
		       		$("#whCode").openComponent_wareHouse({condition:{czybh:"<%=czybh %>"},callBack:fillData});
		            $("#documentNo").hide();
			      	$(".labeldocumentNo").hide();
			        $("#trdocumentNo").hide();
			      	
		       }
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
						},	             
						remote: {
				            message: '',
				            url: '${ctx}/admin/wareHouse/getWareHouse',
				            data: getValidateVal,  
				            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
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
    //保存操作
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var ids = $("#dataTable_itemAppSend").getDataIDs();
		var ids2 = $("#dataTable_salesInvoice").getDataIDs();
		if((!ids || ids.length == 0)&&(!ids2 || ids2.length == 0)){
			art.dialog({content: "明细表中无数据！",width: 200});
			return false;
		}
		var itemAppsendDetail=$('#dataTable_itemAppSend').jqGrid("getRowData");
		var salesInvoiceDetail = $('#dataTable_salesInvoice').jqGrid("getRowData");
		/* var itemAppsendDetailJson = Global.JqGrid.allToJson("dataTable_itemAppSend");
		var salesInvoiceDetailJson = Global.JqGrid.allToJson("dataTable_salesInvoice"); */
		var param = {"itemAppsendDetailJson": JSON.stringify(itemAppsendDetail),"salesInvoiceDetailJson":JSON.stringify(salesInvoiceDetail)};
		Global.Form.submit("dataForm","${ctx}/admin/whCheckOut/doSave",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content: ret.msg,
					/* time: 1000, */
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
	});
	
	//新增发货单
	$("#addItemAppSend").on("click",function(){
		var whCode = $.trim($("#whCode").val());
		if(whCode==''){
			art.dialog({content: "请选择仓库编号",width: 200});
			return false;
		}
		var itemAppsenddetailJson = Global.JqGrid.allToJson("dataTable_itemAppSend","no");
		Global.Dialog.showDialog("addItemAppSend",{
			  title:"送货单-新增",
			  url:"${ctx}/admin/whCheckOut/goWHCheckOutItemAppSendAdd",
			  height: 700,
			  width:1100,
			  postData:{whCode:whCode,whCheckOutNo:'${whCheckOut.no}',unSelected: itemAppsenddetailJson["fieldJson"]},
			  returnFun:function(data){
				  if(data){
					  var sumcost,sumprojectamount,projectothercost;
					  var whfee_projectothercost=0;
					  var whfee_cost = 0;
					  var whfee_project = 0;
					  var sendfee_projectothercost=0;
					  var senfee_cost = 0.0;
					  var sendfee_project = 0.0;	  
					  $.each(data,function(k,v){
					  	  /*
					  	  if (v.whfeecosttype=='2'){//公司和项目经理成本
					  	  	  projectothercost=parseFloat(v.projectothercost);
					  	  	  sumcost=parseFloat(v.itemsumcost)+parseFloat(v.sendfee)+parseFloat(v.whfee)+parseFloat(v.carryfee);
							  sumprojectamount=parseFloat(v.projectamount)+parseFloat(v.sendfee)+projectothercost+parseFloat(v.whfee)+parseFloat(v.carryfee);
					  	  }else if (v.whfeecosttype=='3'){ //只算公司成本
					  	  	  projectothercost=parseFloat(v.projectothercost)-parseFloat(v.whfee);
						  	  sumcost=parseFloat(v.itemsumcost)+parseFloat(v.sendfee)+parseFloat(v.whfee)+parseFloat(v.carryfee);
							  sumprojectamount=parseFloat(v.projectamount)+parseFloat(v.sendfee)+projectothercost+parseFloat(v.whfee)+parseFloat(v.carryfee);
					  	  }else {
					  	  	  projectothercost=parseFloat(v.projectothercost);
					  	      sumcost=parseFloat(v.itemsumcost)+parseFloat(v.sendfee)+parseFloat(v.carryfee);
							  sumprojectamount=parseFloat(v.projectamount)+parseFloat(v.sendfee)+projectothercost+parseFloat(v.carryfee);
					  	  }
					  	  */
 
						  if (v.whfeecosttype=='2'){//公司和项目经理成本
							  whfee_projectothercost=0;
							  whfee_cost = v.whfee;
							  whfee_project = v.whfee; 
					  	  }else if (v.whfeecosttype=='3'){ //只算公司成本
					  		  whfee_projectothercost=-v.whfee;
							  whfee_cost = v.whfee;
							  whfee_project = v.whfee; 
					  	  }else{
					  		  whfee_projectothercost=0.0;
							  whfee_cost = 0.0;
							  whfee_project = 0.0;  
					  	  }
					  	  
						  if (v.sendfeecosttype=='2'){//公司和项目经理成本
							  sendfee_projectothercost=0;
							  sendfee_cost = v.sendfee;
							  sendfee_project = v.sendfee; 
					  	  }else if (v.sendfeecosttype =='3'){ //只算公司成本
					  		  sendfee_projectothercost=-v.sendfee;
					  		  sendfee_cost = v.sendfee;
					  		  sendfee_project = v.sendfee; 
					  	  }else{
					  		  sendfee_projectothercost=0.0;
					  		  sendfee_cost = 0.0;
					  		  sendfee_project = 0.0;  
					  	  }
						  projectothercost=parseFloat(v.projectothercost)+parseFloat(whfee_projectothercost)+parseFloat(sendfee_projectothercost);
				  	  	  sumcost=parseFloat(v.itemsumcost)+parseFloat(sendfee_cost)+parseFloat(whfee_cost)+parseFloat(v.carryfee)+parseFloat(v.longfee);
						  sumprojectamount=parseFloat(v.projectamount)+parseFloat(sendfee_project)+projectothercost+parseFloat(whfee_project)+parseFloat(v.carryfee)+parseFloat(v.longfee);
						  var json = {
						  	  ischeckout:1,
							  no:v.no,
							  date:v.date,
							  iano:v.iano,
							  documentno:v.documentno,
							  payeecode:v.payeecode,
							  payeedescr:v.payeedescr,
							  address:v.address,
							  typedescr:v.typedescr,
							  itemtype1descr:v.itemtype1descr,
							  itemtype2descr:v.itemtype2descr,
							  issetitemdescr:v.issetitemdescr,
							  itemsumcost:v.itemsumcost,
							  projectamount:v.projectamount,
							  senddate:v.senddate,
							  sendtype:v.sendtype,
							  checkstatusdescr:v.checkstatusdescr,
							  remarks:v.remarks,
							  whfee:v.whfee,
							  sendfee:v.sendfee,
							  projectothercost:projectothercost,
							  itemappremarks:v.itemappremarks,
							  type:v.type,
							  whfeecosttype:v.whfeecosttype,
							  sumcost: sumcost,
							  sumprojectamount:sumprojectamount,
							  projectmandescr:v.projectmandescr,
							  carryfee:v.carryfee,  
							  longfee:v.longfee,
							  sendfeecosttype:v.sendfeecosttype,
						  };
						  Global.JqGrid.addRowData("dataTable_itemAppSend",json);
					  });
					  	/* var rows = $("#dataTable_itemAppSend").jqGrid("getRowData");
			   			rows.sort(function (a, b) {
			   				//return new Date(a.date)- new Date(b.date); 
			   				return parseInt(a.checkseq)- parseInt(b.checkseq);
			   			});    
			   			Global.JqGrid.clearJqGrid("dataTable_itemAppSend"); 
			   			$.each(rows,function(k,v){
							Global.JqGrid.addRowData("dataTable_itemAppSend",v);
						}); */
					   $("#whCode").setComponent_wareHouse({readonly: true})
				  }
			  }
			});
	});
	//删除发货单
	$("#delItemAppSend").on("click",function(){
		var rowId = $("#dataTable_itemAppSend").jqGrid("getGridParam","selrow");
		if(rowId){
			art.dialog({		
			 content:'确定删除该记录吗？',
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
			 	//Global.JqGrid.delRowData("dataTable_itemAppSend",id);
			 	 $("#dataTable_itemAppSend").delRowData(rowId);
			    //仓库可以修改
				var id = $("#dataTable_itemAppSend").jqGrid("getGridParam","selrow");
				var id2= $("#dataTable_salesInvoice").jqGrid("getGridParam","selrow");
				if(!id&&!id2){
					$("#whCode").setComponent_wareHouse({readonly: false});
				}
		
			},
			cancel: function () {
				return true;
			}
		});
			
	}else{
		art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
		return false;
	}	
		
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
	//新增销售单
	$("#addSalesInvoice").on("click",function(){
		var whCode = $.trim($("#whCode").val());
		if(whCode==''){
			art.dialog({content: "请选择仓库编号",width: 200});
			return false;
		}
		var salesInvoiceDetailJson = Global.JqGrid.allToJson("dataTable_salesInvoice","no");
		Global.Dialog.showDialog("addSalesInvoice",{
			  title:"销售单-新增",
			  url:"${ctx}/admin/whCheckOut/goWHCheckOutSalesInvoiceAdd",
			  postData:{whCode: whCode,whCheckOutNo:'${whCheckOut.no}',unSelected: salesInvoiceDetailJson["fieldJson"]},
			  height: 700,
			  width:1100,
			  returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
						  	  ischeckout:1,
							  no:v.no,
							  custname:v.custname,
							  address:v.address,
							  salestypedescr:v.salestypedescr, 
							  address:v.address,
							  getitemdate:v.getitemdate,
							  amount:v.amount,
							  totalmaterialcost: v.totalmaterialcost,
							  totaladditionalcost: v.totaladditionalcost,
							  remarks:v.remarks
						  };
						  Global.JqGrid.addRowData("dataTable_salesInvoice",json);
					  });
					  $("#whCode").setComponent_wareHouse({readonly: true});
				  }
			  }
			});
	});
	//删除销售单
	$("#delSalesInvoice").on("click",function(){
		var rowId = $("#dataTable_salesInvoice").jqGrid("getGridParam","selrow");
		if(!rowId){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		art.dialog({
			 content:'确定删除该记录吗？',
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
			 	Global.JqGrid.delRowData("dataTable_salesInvoice",rowId);
				 //仓库可以修改
				var id = $("#dataTable_itemAppSend").jqGrid("getGridParam","selrow");
				var id2= $("#dataTable_salesInvoice").jqGrid("getGridParam","selrow");
				if(!id&&!id2){
					$("#whCode").setComponent_wareHouse({showValue:$("#whCode").val(),readonly: false});
				}
			},
			cancel: function () {
				return true;
			}
		});
		
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
	//按公司输出到Excel
	$("#totalByCompanyExcel").on("click",function(){
		doExcelNow("按公司汇总导出","dataTable_totalByCompany","dataForm");
	});
	//打印按材料类型2统计
	$("#totalByItemType2Print").on("click",function(){
		var totalrow =$("#dataTable_totalByItemType2").jqGrid('getGridParam', 'records'); 
		if(totalrow<1){
			art.dialog({content: "无明细数据，不能打印！",width: 200});
			return false;
		}
	});
	//打印按品牌统计
	$("#totalByBrandPrint").on("click",function(){
		 var totalrow =$("#dataTable_totalByBrand").jqGrid('getGridParam', 'records'); 
		 if(totalrow<1){
			art.dialog({content: "无明细数据，不能打印！",width: 200});
			return false;
		 }
	});
	//打印公司统计
	$("#totalByBrandPrint").on("click",function(){
		 var totalrow =$("#dataTable_totalByBrand").jqGrid('getGridParam', 'records'); 
		 if(totalrow<1){
			art.dialog({content: "无明细数据，不能打印！",width: 200});
			return false;
		 }
	});
	$("#totalByCompanyPrint").on("click",function(){
		 var totalrow =$("#dataTable_totalByCompany").jqGrid('getGridParam', 'records'); 
		 if(totalrow<1){
			art.dialog({content: "无明细数据，不能打印！",width: 200});
			return false;
		 }   
	});
	
</script>
</body>
</html>

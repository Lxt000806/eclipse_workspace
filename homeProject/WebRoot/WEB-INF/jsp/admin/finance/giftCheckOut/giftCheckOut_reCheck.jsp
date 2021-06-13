<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>礼品出库记账反审核</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
  </head>
  <body>
  	 <div class="body-box-form">
  	
  		<div class="content-form">
  			<!-- panelBar -->
  			<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="returnCheck">
  							<span>反审核</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
					</div>
			</div>
  			<div class="infoBox" id="infoBoxDiv"></div>
  			<!-- edit-form -->
  			<div class="panel panel-info" >  
			<div class="panel-body">
			   <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>记账单号</label>
									<input type="text" id="No" name="No" style="width:160px;"  value="${giftCheckOut.no }" readonly="readonly"/>                                             
							</li>	
							<li>
								<label>申请人</label>
									<input type="text" id="appCzy" name="appCzy" style="width:160px;" value="${giftCheckOut.appCzy }" readonly="readonly"/>                                             
							</li>	
							<li>
								<label>状态</label>
									<house:xtdm id="status" dictCode="WHChkOutStatus"  value="${giftCheckOut.status }" disabled="true"></house:xtdm>
							</li>	
							<li>
								<label>申请日期</label>
									<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftCheckOut.date}' pattern='yyyy-MM-dd'/>"  disabled="true"/>
							</li>	
							<li>
								<label>仓库编号</label>
									<input type="text" id="whCode" name="whCode" style="width:160px;" value="${giftCheckOut.whCode }" readonly="readonly"/>                                             
							</li>	
							<li>
								<label>审核人</label>
									<input type="text" id="confirmCzy" name="confirmCzy" style="width:160px;" value="${giftCheckOut.confirmCzy }" readonly="readonly"/>                                             
							</li>	
							<li>
								<label>记账日期</label>
									<input type="text" id="checkDate" name="checkDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftCheckOut.checkDate}' pattern='yyyy-MM-dd'/>"  disabled="true" disabled="true"/>
							</li>	
							<li>
								<label>审核日期</label>
									<input type="text" id="confirmDate" name="confirmDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftCheckOut.confirmDate}' pattern='yyyy-MM-dd'/>" disabled="true" disabled="true"/>
							</li>	
							<li>
								<label>凭证号</label>
									<input type="text" id="documentNo" name="documentNo" style="width:160px;" value="${giftCheckOut.documentNo }" readonly="readonly"/>                                             
							</li>	
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${giftCheckOut.remarks }</textarea>
  							</li>
						</ul>
  				</form>
  				</div>
  				</div>
			<div class="btn-panel" >
			<div class="btn-group-sm"  >
								<button type="button" class="btn btn-system " onclick="doExcelNow('礼品领用单')" title="导出当前excel数据" >
									<span>导出excel</span>
								</button>
				</div>
				</div>
				<ul class="nav nav-tabs" >
	      	<li class="active"><a data-toggle="tab">礼品领用表</a></li>
	   	 </ul>	
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>	
  		</div>
  	</div> 
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">


$("#tabs").tabs();
$(function(){
	$("#appCzy").openComponent_employee({showValue:'${giftCheckOut.appCzy}',showLabel:'${giftCheckOut.appCzyDescr}' ,readonly:true});
	$("#whCode").openComponent_wareHouse({showValue:'${giftCheckOut.whCode}',showLabel:'${giftCheckOut.whCodeDescr}' ,readonly:true});
	$("#confirmCzy").openComponent_employee({showValue:'${giftCheckOut.confirmCzy}',showLabel:'${giftCheckOut.confirmCzyDescr}' ,readonly:true});
	
	var lastCellRowId;
	var gridOption = {
		url:"${ctx}/admin/giftCheckOut/goDetailJqGrid",
		postData:{checkOutNo:'${giftCheckOut.no}'},
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'CheckSeq', index:'checkSeq', width:80, label:'结算顺序号', sortable:true ,align:"left",hidden:true },
			{name:'No', index:'No', width:80, label:'领用单号', sortable:true ,align:"left" },
			{name:'Status', index:'Status', width:80, label:'状态', sortable:true ,align:"left" ,hidden:true},
			{name:'statusdescr', index:'statusdescr', width:80, label:'状态', sortable:true ,align:"left" ,},
			{name:'Type', index:'Type', width:80, label:'类型', sortable:true ,align:"left" ,hidden:true},
			{name:'typedescr', index:'typedescr', width:80, label:'类型', sortable:true ,align:"left" },
			{name:'CustCode', index:'CustCode', width:80, label:'客户编号', sortable:true ,align:"left",hidden:true }, 
			{name:'documentno', index:'documentno', width:85, label:'档案号', sortable:true ,align:"left" }, 
			{name:'address', index:'address', width:115, label:'楼盘', sortable:true ,align:"left" }, 
			{name:'SendDate', index:'SendDate', width:80, label:'发货时间', sortable:true ,align:"left" , formatter:formatDate},
			{name:'UseMan', index:'UseMan', width:80, label:'使用人', sortable:true ,align:"left",hidden:true },
			{name:'usedescr', index:'usedescr', width:80, label:'使用人', sortable:true ,align:"left" },
			{name:'AppCZY', index:'AppCZY', width:80, label:'申请人', sortable:true ,align:"left",hidden:true }, 
			{name:'appdescr', index:'appdescr', width:80, label:'申请人', sortable:true ,align:"left",hidden:true }, 
			{name:'Date', index:'Date', width:80, label:'申请时间', sortable:true ,align:"left" , formatter:formatDate,hidden:true},
			{name:'itemsumcost', index:'itemsumcost', width:80, label:'金额', sortable:true ,align:"right" },
			{name:'Phone', index:'Phone', width:80, label:'电话', sortable:true ,align:"left",hidden:true },
			{name:'bm', index:'bm', width:80, label:'业务员', sortable:true ,align:"left",},
			{name:'dm', index:'dm', width:80, label:'设计师', sortable:true ,align:"left",},
			{name:'bd', index:'bd', width:80, label:'业务部', sortable:true ,align:"left",},
			{name:'dd', index:'dd', width:80, label:'设计部', sortable:true ,align:"left",},
			{name:'ActNo', index:'ActNo', width:80, label:'活动编号', sortable:true ,align:"left",hidden:true },
			{name:'descr', index:'descr', width:80, label:'活动名称', sortable:true ,align:"left" },
			{name:'Remarks', index:'Remarks', width:80, label:'备注', sortable:true ,align:"left" },
		],
	};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	//查看
	$("#view").on("click",function(){
		var ret= selectDataTableRow('dataTable');
	}); 
	//审核取消
	$("#returnCheck").on("click",function(){
		var param= Global.JqGrid.allToJson("dataTable");
					var Ids =$("#dataTable").getDataIDs();
		Global.Form.submit("page_form","${ctx}/admin/giftCheckOut/doReturnCheck",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content:ret.msg,
					time:1000,
					beforeunload:function(){
						closeWin();
					}
				});				
			}else{
				$("#_form_token_uniq_id").val(ret.token.token);
				art.dialog({
					content:ret.msg,
					width:200
				});
			}
		}); 
	});
	
});
</script>
  </body>
</html>


















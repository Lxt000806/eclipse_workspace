<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>仓库调整-审核</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
var noPosiQty;
function doVerify(status, warn){
	var documentNo = $.trim($("#documentNo").val());
	if(documentNo ==''&&status=='2'){
		art.dialog({content: "请填写凭证号",width: 200});
		return false;
	}else{
	art.dialog({
		content:"是否要审核"+warn+"该调整单?",
		ok: function () {
			var datas=$("#page_form").jsonForm();
			datas.detailJson=JSON.stringify($("#dataTable").jqGrid("getRowData"));
		    if(status==2){
		    	url='${ctx}/admin/itemBalAdjHeader/doVerify';
		    }
		    else{ 
		     	url='${ctx}/admin/itemBalAdjHeader/doVerifyCancel';
		    }
		    
			$.ajax({
				url:url,
				type: 'post',
				data:datas,
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				},
				success: function(obj){
					if(obj.rs){
						art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
								closeWin();
							}
						});
					}else{
						$("#_form_token_uniq_id").val(obj.token.token);
						art.dialog({
							content: obj.msg,
							width: 200
						});
					}
				}
			});
		},
		cancel:function(){
		}
	});
	}
}

$(function(){
	$("#whCode").openComponent_wareHouse();
	$("#whCode").openComponent_wareHouse({showValue:'${itemBalAdjHeader.whCode}',showLabel:'${itemBalAdjHeader.whDescr}',readonly:true });
	$("#appEmp").openComponent_employee({showValue:'${itemBalAdjHeader.appEmp}',showLabel:'${itemBalAdjHeader.appEmpDescr}',readonly:true });
	$("#status_NAME").attr("disabled","disabled");
	$("#tabs").tabs();
	
	Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemBalAdjDetail/goJqGrid",
			postData:{ibhNo:'${itemBalAdjHeader.no }',noRepeat:'1'},
			height:271,
			rowNum:10000000,
			styleUI: 'Bootstrap', 
			colModel : [
				{name:'pk', index:'pk', width:105, label:'PK', sortable:true ,align:"left", hidden:true},
				{name:'itcode', index:'itcode', width:105, label:'材料编号', sortable:true ,align:"left",count:true},
				{name:'ibhno', index:'ibhno', width:105, label:'仓库调整编号', sortable:true ,align:"left", hidden:true},
				{name:'itdescr', index:'itdescr', width:250, label:'材料名称', sortable:true ,align:"left"},
				{name:'uomdescr', index:'uomdescr', width:60, label:'单位', sortable:true, align:"left"},
				{name:'adjcost',index:'adjcost',width:70, label:'成本单价', sortable:true,align:"left", },
				{name:'cost', index:'cost', width:60, label:'当R前成本', sortable : true,align : "left", sum:true, hidden:true},
				{name:'adjqty',	index:'adjqty',width:80, label:'调整数量', sortable : true,align : "left",sum:true},
				{name:'qty', index:'qty', width:80, label:'变动后数量', sortable:true ,align:"left",sum:true},
				{name:"adjamount", index:"adjamount", width:70, label:"金额", sortable:true, align:"left", 
					formatter:formatterAdjAmount, sum:true},
				{name:'remarks',index:'remarks',width:220, label:'备注', sortable:true,align:"left"},	
				{name:'allqty',index:'allqty',width:250, label:'现存数量', sortable:true,align:"left", hidden:true},	
				{name:'aftcost',index:'aftcost',width:250, label:'变动后成本', sortable:true,align:"left", hidden:true},
				{name:'lastupdate',index : 'lastupdate',width : 135,label:'最后修改时间',sortable : true,align : "left",formatter:formatTime, hidden:true},
			    {name:'lastupdatedby',index : 'lastupdatedby',width : 42,label:'修改人',sortable : true,align : "left", hidden:true}
			],
		});
	
	
});

//查看
function view(){
		var ret= selectDataTableRow('dataTable');
		var whCode = $.trim($("#whCode").val());
		$.ajax({
			url:"${ctx}/admin/itemBalAdjHeader/getAjaxNoPosi",
			type:'post',
			data:{whCode:whCode, itCode:ret.itcode},
			dataType:'json',
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				if (obj){
					noPosiQty=obj.noPosiQty;
				 	if(ret){
						Global.Dialog.showDialog("SaveView",{
							title:"仓库调整明细——查看",
							url:"${ctx}/admin/itemBalAdjHeader/goSaveView",
							postData:{itCode:ret.itcode, uom:ret.uomdescr, cost:ret.cost,
							 adjQty:ret.adjqty, qty:ret.qty ,remarks:ret.remarks, itDescr:ret.itdescr, 
							 allQty:ret.allqty, adjCost:ret.adjcost, aftCost:ret.aftcost, lastUpdate:ret.lastupdate, 
							 lastUpdatedBy:ret.lastupdatedby ,noPosiQty:noPosiQty,
							  whCode:whCode
							 },
							height:480,
							width:800,
						});
					}else{
						art.dialog({
							content:"请选择一条记录"
						});		
					}
				}
			}
		});
		
};
// 规范金额 add by zb
function formatterAdjAmount(cellvalue, options, rowObject) {
	return myRound(rowObject.adjcost * rowObject.adjqty, 2);
}
</script>
</head>
  
<body>
<div class="body-box-form" >
	<div class="content-form">
  			<!-- panelBar -->
  				<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
 						<button type="button" class="btn btn-system " id="verifyPassBtn" onclick="doVerify(2,'通过')">
 							<span>审核通过</span>
 						</button>	
 						<button type="button" class="btn btn-system " id="verifyCancelsaveBtn"  onclick="doVerify(3,'取消')">
 							<span>审核取消</span>
 						</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
			</div>
		
		<div class="infoBox" id="infoBoxDiv"></div>
		
		<div class="panel panel-info" >  
         <div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<house:token></house:token>
				<input type="hidden" name="jsonString" value="" />
							<ul class="ul-form">
							<li>
								<label>仓库调整编号</label>
								<input type="text" id="No" name="No" style="width:160px;" placeholder="保存自动生成" value="${itemBalAdjHeader.no }" readonly="readonly"/>                                             
							</li>
							<li>
								<label><span class="required">*</span>仓库编号</label>
									<input type="text" id="whCode" name="whCode" style="width:160px;" value="${itemBalAdjHeader.whCode }"/>
							</li>
							<li>
								<label>状态</label>
								<house:xtdmMulit id="status" dictCode="BALADJSTATUS" selectedValue="${itemBalAdjHeader.status}"></house:xtdmMulit>
							</li>
							<li>
								<label><span class="required">*</span>调整日期</label>
								<input type="text" style="width:160px;" id="date" name="date" class="i-date" value="<fmt:formatDate value='${itemBalAdjHeader.date}' pattern='yyyy-MM-dd'/>"  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled="true"/>
							</li>
							<li>
								<label>申请日期</label>
								<input type="text" style="width:160px;" id="appDate" name="appDate" class="i-date" value="<fmt:formatDate value='${itemBalAdjHeader.appDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
							</li>
							<li>
								<label><span class="required">*</span>调整类型</label>
								<house:xtdm id="adjType" dictCode="ADJTYP"  value="${itemBalAdjHeader.adjType }" disabled="true"></house:xtdm>
							</li>
							<li>
								<label>申请人</label>
									<input type="text" id="appEmp" name="appEmp" style="width:160px;" value="${itemBalAdjHeader.appEmp }"/>
							</li>
							<li>
								<label><span class="required">*</span>调整原因</label>
								<house:xtdm id="adjReason" dictCode="AdjReason"  value="${itemBalAdjHeader.adjReason }" disabled="true"></house:xtdm>
							</li>
							<li>
								<label>审核日期</label>
								<input type="text" style="width:160px;" id="confirmDate" name="confirmDate" class="i-date" value="<fmt:formatDate value='${itemBalAdjHeader.confirmDate}'  pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
							</li>
							<li>
								<label>凭证号</label>
								<input type="text" id="documentNo" name="documentNo" style="width:160px;"  value="${itemBalAdjHeader.documentNo}" />
							</li>
							<li>
								<label>审核人</label>
								<input type="text" id="confirmEmp" name="confirmEmp" style="width:160px;"  value="${itemBalAdjHeader.confirmEmp}" disabled='true'/>
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea style="width:160px" id="remarks" name="remarks" rows="2">${itemBalAdjHeader.remarks }</textarea>
							</li>
						</ul>	
			</form>
			</div>
		</div> <!-- edit-form end -->
		<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
						<button type="button" class="btn btn-system " id="view" onclick="view()">
							<span>查看</span>
							</button>
						<button type="button" class="btn btn-system " onclick="doExcelNow('仓库调整明细')" title="导出检索条件数据">
							<span>输出到excel</span>
							</button>
				</div>
			</div>
		<ul class="nav nav-tabs" >
	      	<li class="active"><a  data-toggle="tab">详情</a></li>
	   	 </ul>
			
			
		<div class="pageContent">
			<table id="dataTable" style="overflow: auto;"></table>
		</div>	
	</div>
</div>
</body>
</html>

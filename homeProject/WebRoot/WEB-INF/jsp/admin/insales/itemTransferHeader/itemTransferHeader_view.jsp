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
<title>仓库转移-查看</title>
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
	var isEdited=0;
	function exitPage(){
		if(isEdited==1){
			art.dialog({
				content:"是否保存被更改的数据？",
				lock: true,
				width: 200,
				height: 90,
				okValue:"是",
				cancelValue:"否",
				ok: function () {
					save();
				},
				cancel: function () {
					closeWin();
				}
			});	
		}else{
			closeWin();
		}
	}
	$(function(){
		$("#fromWHCode").openComponent_wareHouse({callBack:function(){validateRefresh();},showValue:"${itemTransferHeader.fromWHCode}",showLabel:"${fromWHouse.desc1}"});
		$("#toWHCode").openComponent_wareHouse({callBack:function(){validateRefresh();},showValue:"${itemTransferHeader.toWHCode}",showLabel:"${toWHouse.desc1}"});
		$("#appEmp").openComponent_employee({showValue:"${itemTransferHeader.appEmp}",showLabel:"${employee.nameChi}",readonly:true });
		$("#confirmEmp").openComponent_employee({showValue:"${itemTransferHeader.confirmEmp}",showLabel:"${itemTransferHeader.confirmCZYDescr}",readonly:true });
		$("#status_NAME").attr("disabled","disabled");
	});
	
	//新增是否允许重复
	function noRepeat(obj){
		if ($(obj).is(":checked")){
			$("#noRepeat").val("1");
		}else{
			$("#noRepeat").val("0");
		}
	}
	//校验函数
	$(function(){
		
		var gridOption = {
			postData:{
					no:$.trim("${itemTransferHeader.no}")
			},
			url:"${ctx}/admin/itemTransferHeader/getDetailBySql",
			height:$(document).height()-$("#content-list").offset().top-70,
			rowNum:10000000,
			styleUI: "Bootstrap", 
			colModel : [
				{name:"pk", index:"pk", width:105, label:"pk", sortable:true ,align:"left",hidden:true},
				{name:"ithno", index:"ithno", width:105, label:"仓库转移号码", sortable:true ,align:"left",hidden:true},
				{name:"seq", index:"seq", width:105, label:"排序", sortable:true ,align:"left",hidden:true},
				{name:"itcode", index:"itcode", width:105, label:"材料编号", sortable:true ,align:"left"},
				{name:"itdescr", index:"itdescr", width:200, label:"材料名称", sortable:true ,align:"left"},
				{name:"itemtype2descr", index:"itemtype2descr", width:76, label:"材料类型2", sortable : true,align : "left", sum:true,},
				{name:"trfqty", index:"trfqty", width:76, label:"转移数量", sortable : true,align : "right", sum:true,},
				{name:"avgcost",index:"avgcost",width:60, label:"成本单价", sortable:true,align:"right", sum:true,},
				{name:"amountcost",	index:"amountcost",width:80, label:"成本总价", sortable : true,align : "right",sum:true},
				{name:"fromafterqty", index:"fromafterqtyqty", width:80, label:"源变动后数量", sortable:true ,align:"right",sum:true},
				{name:"toafterqty",index:"toafterqty",width:85, label:"目的变动后数量", sortable:true,align:"right"},	
				{name:"detailremarks",index:"detailremarks",width:130, label:"备注", sortable:true,align:"left", },	
			],
			loadComplete: function(){
				var Ids =$("#dataTable").getDataIDs();
				if(Ids!=0){
					$("#fromWHCode").openComponent_wareHouse({readonly:true});
					$("#toWHCode").openComponent_wareHouse({readonly:true});
	
				};
            },
		};
		var detailJson = Global.JqGrid.allToJson("dataTable","itcode");
		Global.JqGrid.initJqGrid("dataTable",gridOption);
		if('${costRight}'=='0'){
			jQuery("#dataTable").setGridParam().hideCol("avgcost").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("amountcost").trigger("reloadGrid");
		}
	});
	
	function save(){
		var Ids =$("#dataTable").getDataIDs();
		var param= Global.JqGrid.allToJson("dataTable");
		var fromafterqty = Global.JqGrid.allToJson("dataTable","fromafterqty");
		fromQtyArr= fromafterqty.fieldJson.split(",");
		var toafterqty = Global.JqGrid.allToJson("dataTable","toafterqty");
		toQtyArr= toafterqty.fieldJson.split(",");
 		for(var i=0;i<Ids;i++){
 		if(Ids==0){
			art.dialog({
				content:"明细无数据,保存无意义!",
			});
			return;
		}  
			if(fromQtyArr[i]<0||fromQtyArr[i]==""||toQtyArr[i]<0||toQtyArr[i]==""){
				art.dialog({
					content:"转移后数量不能为空,或者转移后数量不能小于零!",
				});
 				return false;
			}
		}
		Global.Form.submit("page_form","${ctx}/admin/itemTransferHeader/doUpdate",param,function(ret){
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
	};
	//新增
	function add(){
		var ret= selectDataTableRow("dataTable");
		var noRepeat = $.trim($("#noRepeat").val());
		var itcode = Global.JqGrid.allToJson("dataTable","itcode");
			itCodeArr= itcode.fieldJson.split(",");
		if(fromWHCode ==""){
			art.dialog({content: "请选择源仓库编号",width: 200});
			return false;
		}
		if(toWHCode ==""){
			art.dialog({content: "请选择目的仓库编号",width: 200});
			return false;
		}
		Global.Dialog.showDialog("saveAdd",{
			title:"仓库调整明细——新增",
			url:"${ctx}/admin/itemTransferHeader/goAdd",
			postData:{fromWHCode:$("#fromWHCode").val(),toWHCode:$("#toWHCode").val(),noRepeat:noRepeat,itCodeArr:itCodeArr},
			height: 550,
			width:800,
			returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							itcode:v.itCode,
							itdescr:v.itDescr,
							itemtype2descr:v.itemType2Descr,
							trfqty:v.trfQty,
							avgcost:v.avgCost,
							amountcost:myRound(v.trfQty)*myRound(v.avgCost),
							fromafterqty:v.fromAfterQty,
							toafterqty:v.toAfterQty,
							detailremarks:v.detailRemarks,
						};
						Global.JqGrid.addRowData("dataTable",json);
					});
					$("#fromWHCode").openComponent_wareHouse({readonly:true});
					$("#toWHCode").openComponent_wareHouse({readonly:true});
					isEdited=1;
				}
			} 
		});
	};
	function fastAdd(){
		var ret= selectDataTableRow("dataTable");
		var noRepeat = $.trim($("#noRepeat").val());
		var itcode = Global.JqGrid.allToJson("dataTable","itcode");
			itCodeArr= itcode.fieldJson.split(",");
		var fromWHCode = $.trim($("#fromWHCode").val());
		var toWHCode = $.trim($("#toWHCode").val());
		var adjType = $.trim($("#adjType").val());
		if(fromWHCode ==""){
			art.dialog({content: "请选择源仓库编号",width: 200});
			return false;
		}
		if(toWHCode ==""){
			art.dialog({content: "请选择目的仓库编号",width: 200});
			return false;
		}
		if(fromWHCode==toWHCode){
			art.dialog({
				content:"非法操作,与目的仓库相同!",
			});
			return false;
		}
		Global.Dialog.showDialog("saveAdd",{
			  title:"仓库转移详情——增加",
			  url:"${ctx}/admin/itemTransferHeader/goFastAdd",
			  postData:{
			  		fromWHCode:$("#fromWHCode").val(),
			  		toWHCode:$("#toWHCode").val(),
			  		noRepeat:noRepeat,
			  		itCodeArr:itCodeArr
			  },
			  height: 550,
			  width:800,
			    returnFun:function(data){
					if(data){
						$.each(data,function(k,v){
							var json = {
								itcode:v.itCode,
								itdescr:v.itDescr,
								itemtype2descr:v.itemType2Descr,
								trfqty:v.trfQty,
								avgcost:v.avgCost,
								amountcost:myRound(v.trfQty)*myRound(v.avgCost),
								fromafterqty:v.fromAfterQty,
								toafterqty:v.toAfterQty,
								detailremarks:v.detailRemarks,
						 	};
							Global.JqGrid.addRowData("dataTable",json);
						});
					 isEdited=1;
					 $("#fromWHCode").openComponent_wareHouse({readonly:true});
					 $("#toWHCode").openComponent_wareHouse({readonly:true});
				  }
			  } 
		 });
	};
		//编辑
	function update(){
		var ret= selectDataTableRow("dataTable");
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		var fromWHCode = $.trim($("#fromWHCode").val());
		var toWHCode = $.trim($("#toWHCode").val());
		var noRepeat = $.trim($("#noRepeat").val());
		var itcode = Global.JqGrid.allToJson("dataTable","itcode");
			itCodeArr= itcode.fieldJson.split(",");
	 	if(ret){
			Global.Dialog.showDialog("SaveUpdate",{
				title:"仓库转移详情——编辑",
				url:"${ctx}/admin/itemTransferHeader/goAddUpdate",
				postData:{fromWHCode:fromWHCode,
							toWHCode:toWHCode,
							itCode:ret.itcode,
							trfQty:ret.trfqty,
							detailRemarks:ret.detailremarks,
							noRepeat:noRepeat,
							itCodeArr:itCodeArr
				},
				height:550,          
				width:800,
				returnFun:function(data){
					if(data){
						$.each(data,function(k,v){
							var json = {
								itcode:v.itCode,
								itdescr:v.itDescr,
								itemtype2descr:v.itemType2Descr,
								trfqty:v.trfQty,
								avgcost:v.avgCost,
								amountcost:myRound(v.trfQty)*myRound(v.avgCost),
								fromafterqty:v.fromAfterQty,
								toafterqty:v.toAfterQty,
								detailremarks:v.detailRemarks,
							};
								$("#dataTable").setRowData(rowId,json);
						});
						isEdited=1;
					}
				} 
			});
		}else{
			art.dialog({
				content:"请选择一条记录",
			});		
		}
	};  
	
		//删除
	function del(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		Global.JqGrid.delRowData("dataTable",id);
		isEdited=1;
		var Ids =$("#dataTable").getDataIDs();
		if(Ids==0){
			$("#fromWHCode").openComponent_wareHouse({readonly:false});
			$("#toWHCode").openComponent_wareHouse({readonly:false});
		}
	};
		//查看
	function view(){
		var ret= selectDataTableRow("dataTable");
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		var fromWHCode = $.trim($("#fromWHCode").val());
		var toWHCode = $.trim($("#toWHCode").val());
	 	if(ret){
			Global.Dialog.showDialog("SaveUpdate",{
				title:"仓库转移详情——查看",
				url:"${ctx}/admin/itemTransferHeader/goAddView",
				postData:{fromWHCode:fromWHCode,
							toWHCode:toWHCode,
							itCode:ret.itcode,
							trfQty:ret.trfqty,
							detailRemarks:ret.detailremarks,
							fromQty:ret.fromafterqty,
							toQty:ret.toafterqty},
				height:550,          
				width:800,
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}	 
	};
	function validateRefresh(){
		$("#dataForm").data("bootstrapValidator")
				.updateStatus("openComponent_wareHouse_fromWHCode", "NOT_VALIDATED",null)  
                   .validateField("openComponent_wareHouse_fromWHCode")
                   .updateStatus("openComponent_wareHouse_toWHCode", "NOT_VALIDATED",null)  
                   .validateField("openComponent_wareHouse_toWHCode")
                   .updateStatus("date", "NOT_VALIDATED",null)  
                   .validateField("date")  ;
	}
	</script>
</head>
  
<body>
	<div class="body-box-form">
		<div class="content-form">
  			<div class="panel panel-system">
    			<div class="panel-body">
     				 <div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="closeBut" onclick="exitPage()">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="edit-form">
				<div class="panel panel-info">  
         			<div class="panel-body">
         			  	<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
							<input type="hidden" name="jsonString" value=""/>
							<ul class="ul-form">
								<li>
									<label>仓库转移号码</label>
									<input type="text" id="No" name="No" style="width:160px;" value="${itemTransferHeader.no }" readonly="readonly"/>                                             
								</li>
								<li>
									<label>状态</label>
									<house:xtdmMulit id="status" dictCode="BALADJSTATUS" selectedValue="${itemTransferHeader.status}"></house:xtdmMulit>
								</li>
								<li class="form-validate">
									<label>转移日期</label>
									<input type="text" style="width:160px;" onchange="validateRefresh()" id="date" name="date" class="i-date" value="<fmt:formatDate value='${itemTransferHeader.date}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>源仓库编号</label>
									<input type="text" id="fromWHCode" name="fromWHCode" style="width:160px;" value="${fromWHouse.code }"/>
								</li>
								<li>
									<label>申请日期</label>
									<input type="text" style="width:160px;" id="appDate" name="appDate" class="i-date" value="<fmt:formatDate value='${itemTransferHeader.appDate}' pattern='yyyy-MM-dd hh:mm:ss'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" disabled="true"/>
								</li>
								<li>
									<label>申请人</label>
									<input type="text" id="appEmp" name="appEmp" style="width:160px;" value="${itemTransferHeader.appEmp }"/>
								</li>
								<li>
									<label>目的仓库编号</label>
									<input type="text" id="toWHCode" name="toWHCode" style="width:160px;" value="${toWHouse.code }"/>
								</li>
								<li>
									<label>审核日期</label>
									<input type="text" style="width:160px;" id="confirmDate" name="confirmDate" class="i-date" value="<fmt:formatDate value='${itemTransferHeader.confirmDate}'  pattern='yyyy-MM-dd hh:mm:ss'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" disabled="true"/>
								</li>
								<li>
									<label>审核人</label>
									<input type="text" id="confirmEmp" name="confirmEmp" style="width:160px;"  value="${itemTransferHeader.confirmEmp}" disabled="true"/>
								</li>
								<li>
									<label>凭证号</label>
									<input type="text" id="documentNo" name="documentNo" style="width:160px;"  value="${itemTransferHeader.documentNo}" disabled="true"/>
								</li>
								<li>
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2" style="width:160px">${itemTransferHeader.remarks }</textarea>
								</li>
							</ul>	
						</form>
					</div>
				</div>
			</div> 
			<div class="btn-panel">
    			<div class="btn-group-sm">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
						</button>
					<button type="button" class="btn btn-system" onclick="doExcelNow('仓库转移明细')" title="导出检索条件数据">
						<span>输出到excel</span>
						</button>
					<span style="vertical-align:middle;margin-left:30px;color:blue">
						成本及变动后数量，以审核后数据为准。
					</span>	
				</div>
			</div>		
			<ul class="nav nav-tabs">
	      		<li class="active">
	      			<a href="#tabView" data-toggle="tab">详情</a>
	      		</li>
	      		<li>
	      			<a href="#tab_viewSupplier" data-toggle="tab">按供应商汇总</a>
	      		</li>
	   		</ul>	
	   		<div class="tab-content">
	   			<div id="tabView" class="tab-pane fade in active"> 
					<div id="content-list">
						<table id="dataTable"></table>
					</div>
		        </div> 
				<div id="tab_viewSupplier" class="tab-pane fade">
					<jsp:include page="tab_viewSupplier.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

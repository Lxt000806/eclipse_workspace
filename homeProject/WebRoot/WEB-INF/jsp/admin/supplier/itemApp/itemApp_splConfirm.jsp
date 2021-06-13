<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>供应商确认</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
var parentWin=window.opener;
$(function(){
	$document = $(document);
	$('div.panelBar', $document).panelBar();
	
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/supplierItemApp/goJqGridDetail?id=${itemApp.no}",
		height:$(document).height()-$("#content-list").offset().top-65,
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
		  {name : 'itemcode',index : 'a.itemcode',width : 100,label:'材料编号',align : "left",count:true},
		  {name : 'itemdescr',index : 'a.itemdescr',width : 220,label:'材料名称',align : "left"},
		  {name : "fixareadescr",index : "fixareadescr",width : 100,label:"区域",align : "left"},
		  {name : "intproddescr",index : "intproddescr",width : 100,label:"集成产品",align : "left",hidden:true},
		  {name : 'qty',index : 'a.qty',width : 100,label:'数量',	editable:true,editrules: {number:true,required:true},align : "right",sum:true},
		  {name : 'uomdescr',index : 'a.uomdescr',width : 100,label:'单位',align : "left"},
		  {name : 'processcost',index : 'processcost',width : 100,label:'其他费用',align : "right",sum:true},
		  {name : 'remarks',index : 'a.remarks',width : 350,label:'备注',align : "left"},
		  {name : 'pk',index:'pk',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'no',index:'no',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'reqpk',index:'reqpk',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'fixareapk',index:'fixareapk',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'intprodpk',index:'intprodpk',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'reqqty',index:'reqqty',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'sendqty',index:'sendqty',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'shortqty',index:'shortqty',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'sendedqty',index:'sendedqty',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'remarks',index:'remarks',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'lastupdate',index:'lastupdate',width:150, label:'mobile', sortable:true,align:"left",hidden:true, formatter: formatTime},
		  {name : 'lastupdatedby',index:'lastupdatedby',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'expired',index:'expired',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'actionlog',index:'actionlog',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'cost',index:'cost',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'projectcost',index:'projectcost',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'aftqty',index:'aftqty',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'aftcost',index:'aftcost',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'preappdtpk',index:'preappdtpk',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'expired',index:'expired',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'reqremarks',index:'reqremarks',width:150, label:'mobile', sortable:true,align:"left",hidden:true},
		  {name : 'declareqty',index:'declareqty',width:150, label:'declareqty', sortable:true,align:"left",hidden:true}
          ],
          gridComplete:function(){
			if ($.trim("${itemApp.itemType1}") == "JC") {
				$("#dataTable").jqGrid("showCol", "intproddescr");
			}
          }
	});
	
	if ($.trim("${itemApp.itemType1}") == "JC") {
		$("#lblOtherCost").text("安装费");
		$("#lblOtherCostAdj").text("售后费");
	}
});

function doSplConfirm(flag0) {
	var flag=true;
	var ids=$("#dataTable").jqGrid('getDataIDs');
	$.each(ids,function(i,id){
				var rowData = $("#dataTable").jqGrid("getRowData", id);
				if(rowData.qty=="0"){
					flag=false;
				}
			});
	if(!flag){
		art.dialog({
			content:"存在数量为0的记录，确认失败！",
		});
		return;
	}
	var param= Global.JqGrid.allToJson("dataTable");
	if(flag0 != true){
		var tipIndex = 0;
		var sumQty = 0.0;
		var sumDeclareQty = 0.0;
		for(var i = 0;i < param.datas.length;i++){
			if(parseFloat(param.datas[i].qty)+parseFloat(param.datas[i].shortqty)-parseFloat(param.datas[i].declareqty) > 
				parseFloat(param.datas[tipIndex].qty)+parseFloat(param.datas[tipIndex].shortqty)-parseFloat(param.datas[tipIndex].declareqty)){
				tipIndex = i;	
			}
			//控制单项数量差异小于3%
			if((parseFloat(param.datas[i].qty)+parseFloat(param.datas[i].shortqty)-parseFloat(param.datas[i].declareqty))/parseFloat(param.datas[i].declareqty)>0.03){
				art.dialog({
					content: "【"+param.datas[tipIndex].itemcode+"】【"+param.datas[tipIndex].itemdescr+"】数量异常,是否要保存?",
					ok : function(){
						$("#canPass").val("0");//不能直接通过
						doSplConfirm(true);
					},
					cancel : function(){}
				});
				return;
			}
			sumQty += parseFloat(param.datas[i].qty)+parseFloat(param.datas[i].shortqty)-parseFloat(param.datas[i].declareqty);
			sumDeclareQty += parseFloat(param.datas[i].declareqty); 
		}
	 	if(sumQty/sumDeclareQty > 0.01){//控制总数量差异小于1%
			art.dialog({
				content: "【"+param.datas[tipIndex].itemcode+"】【"+param.datas[tipIndex].itemdescr+"】数量异常,是否要保存?",
				ok : function(){
					$("#canPass").val("0");//不能直接通过
					doSplConfirm(true);
				},
				cancel : function(){}
			});
			return;
		} 
	}
 	Global.Form.submit("dataForm","${ctx}/admin/supplierItemApp/doSplConfirm",param,function(ret){
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
}
</script>

</head>
    
<body onunload="closeWin()">
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
	      	<button type="button" class="btn btn-system " onclick="doSplConfirm()">确认</button>
	      	<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      	</div>
	   </div>
	</div>
	
	<div class="panel panel-info"> 
		<div class="panel-body">
			<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				<input type="hidden" name="canPass" id="canPass" value="1"/>
				
				<ul class="ul-form">
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>领料单号 </label>
								<input type="text" id="no" name="no"  value="${itemApp.no}" readonly="readonly"/>
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>单据状态 </label>
								<house:xtdm id="status" dictCode="ITEMAPPSTATUS" value="${itemApp.status}" disabled="true"></house:xtdm>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>项目经理 </label>
								<input type="text" id="projectMan" name="projectMan"  value="${itemApp.projectMan}" readonly="readonly"/>
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>电话号码</label>
								<input type="text" id="phone" name="phone" value="${itemApp.phone}" readonly="readonly"/>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>部门 </label>
								<input type="text" id="projectDept2Descr" name="projectDept2Descr"  value="${itemApp.projectDept2Descr}" readonly="readonly" />
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>编号</label>
								<input type="text" id="custDocumentNo" name="custDocumentNo"  value="${itemApp.custDocumentNo}" readonly="readonly" />
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>楼盘地址 </label>
								<input type="text" id="custAddress" name="custAddress"  value="${itemApp.custAddress}" readonly="readonly" />
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>供应商状态</label>
								<house:xtdm id="splStatus" dictCode="APPSPLSTATUS" value="${itemApp.splStatus}" disabled="true"></house:xtdm>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label>产品类型 </label>
								<house:xtdm id="productType" dictCode="APPPRODUCTTYPE" value="${itemApp.productType}" disabled="true"></house:xtdm>
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>预计到货日期</label>
								<input type="text" id="arriveDate" name="arriveDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.arriveDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6" >
							<li>
								<label id="lblOtherCost">加工费 </label>
								<input type="text" id="otherCost" name="otherCost" value="${itemApp.otherCost}" readonly="readonly" />
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label id="lblOtherCostAdj">物流配送费</label>
								<input type="text" id="otherCostAdj" name="otherCostAdj"  value="${itemApp.otherCostAdj}" readonly="readonly" />
							</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-12" >
							<li>
								<label>到厂日期 </label>
								<input type="text" id="arriveSplDate" name="arriveSplDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.arriveSplDate }' pattern='yyyy-MM-dd'/>" disabled="true" />
							</li>	
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-12" >
							<li>
								<label class="control-textarea">备注 </label>
								<textarea id="splRemark" name="splRemark" style="width:645px;" rows="3" readonly="readonly">${itemApp.splRemark}</textarea>
							</li>	
						</div>
					</div>
				</ul>
			</form>
		</div>
	</div>
	
	<div id="content-list">
		<table id= "dataTable"></table> 
	</div> 
</div>
</body>
</html>

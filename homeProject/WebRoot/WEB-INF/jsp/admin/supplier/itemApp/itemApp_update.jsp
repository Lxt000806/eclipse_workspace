<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改ItemApp</title>
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
	
	if ('${itemApp.isAutoArriveDate }' == 'true') {
		$("#arriveDate").attr("onfocus", "").attr("readonly", "true");
	}
	
	if ("${itemApp.sendType}" == "2") { //仓库发货，加工费，物流配送费不让编辑
		$("#otherCost").attr("readonly","true");
		$("#otherCostAdj").attr("readonly","true");
	}
	
	    //初始化表格
	/*
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/supplierItemApp/goJqGridDetail?id=${itemApp.no}",
		height:$(document).height()-$("#content-list").offset().top-105,
		colModel : [
		  {name : 'itemcode',index : 'a.itemcode',width : 100,label:'材料编号',align : "left",count:true},
		  {name : 'itemdescr',index : 'a.itemdescr',width : 220,label:'材料名称',align : "left"},
		  {name : 'qty',index : 'a.qty',width : 100,label:'数量',	editable:true,editrules: {number:true,required:true},align : "right",sum:true},
		  {name : 'uomdescr',index : 'a.uomdescr',width : 100,label:'单位',align : "left"},
		  {name : 'processcost',index : 'processcost',width : 100,label:'其他费用',align : "right",sum:true},
		  {name : 'remarks',index : 'a.remarks',width : 350,label:'备注',align : "left"}
          ]   
	});
	*/
	
	var lastCellRowId;
	var gridOption = {	
		url:"${ctx}/admin/supplierItemApp/goJqGridDetail?id=${itemApp.no}",
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: "Bootstrap",
		rowNum: 10000,
		colModel : [
		  {name : "itemcode",index : "a.itemcode",width : 70,label:"材料编号",align : "left",count:true},
		  {name : "itemdescr",index : "a.itemdescr",width : 220,label:"材料名称",align : "left"},
		  {name : "fixareadescr",index : "fixareadescr",width : 90,label:"区域",align : "left"},
		  {name : "intproddescr",index : "intproddescr",width : 90,label:"集成产品",align : "left",hidden:true},
		  {name : "qty",index : "a.qty",width : 60,label:"数量",align : "right",editable:true,editrules: {number:true,required:true},sum:true},//	
		  {name : "uomdescr",index : "a.uomdescr",width : 60,label:"单位",align : "left"},
		  {name : "processcost",index : "processcost",width : 70,label:"其他费用",align : "right",sum:true},
		  {name : "cost",index : "cost",width : 60,label:"单价",align : "right",sum:true, hidden : true},
		  {name : "sumcost",index : "sumcost",width : 60,label:"总价",align : "right",sum:true, hidden : true},
		  {name : "remarks",index : "a.remarks",width : 350,label:"备注",align : "left"},
		  {name : "pk",index:"pk",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "no",index:"no",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "reqpk",index:"reqpk",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "fixareapk",index:"fixareapk",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "intprodpk",index:"intprodpk",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "reqqty",index:"reqqty",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "sendqty",index:"sendqty",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "shortqty",index:"shortqty",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "sendedqty",index:"sendedqty",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "remarks",index:"remarks",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "lastupdate",index:"lastupdate",width:150, label:"mobile", sortable:true,align:"left",hidden:true, formatter: formatTime},
		  {name : "lastupdatedby",index:"lastupdatedby",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "expired",index:"expired",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "actionlog",index:"actionlog",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  //{name : "cost",index:"cost",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "projectcost",index:"projectcost",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "aftqty",index:"aftqty",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "aftcost",index:"aftcost",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "preappdtpk",index:"preappdtpk",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "expired",index:"expired",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "reqremarks",index:"reqremarks",width:150, label:"mobile", sortable:true,align:"left",hidden:true},
		  {name : "declareqty",index:"declareqty",width:150, label:"declareqty", sortable:true,align:"left",hidden:true}
          ],
	      gridComplete: function() {
			  if("${itemApp.supplyRecvModel}" == "1"){
			 	  $("#dataTable").jqGrid("showCol", "qty");
			  }else if("${itemApp.supplyRecvModel}" == "2"){
				  if($.trim("${itemApp.itemType1}") != "JC"){
	 				  var ids = $("#dataTable").jqGrid("getDataIDs");
				      $.each(ids, function(index, value){
				          $("#dataTable").jqGrid("setCell", value, "qty", "0");
				      }); 
				  }
			  }else{
				  $("#dataTable").jqGrid("hideCol", "qty");
			  }
			  
			  if ($.trim("${itemApp.itemType1}") == "JC") {
				  $("#dataTable").jqGrid("showCol", "intproddescr");
				  $("#dataTable").jqGrid("showCol", "cost");
				  $("#dataTable").jqGrid("showCol", "sumcost");
			  }
	      },
          cellEdit:true,
          beforeSelectRow: function(id){
         	 setTimeout(function(){
	         	relocate(id, "dataTable");
	         },100)
          },
	      onCellSelect:function(rowid,iCol,cellcontent,e){
		      if("${itemApp.supplyRecvModel}" == "1" && iCol == 5){
                  $("#dataTable").jqGrid("setCell", rowid, iCol, "", "not-editable-cell");  
			  }
		  },
		  beforeSaveCell:function(rowId, name, val, iRow, iCol){
		  	  var ret = $("#dataTable").jqGrid("getRowData", rowId);

			  ret[name]=val;
			
			  var reg = /^[0-9]+.?[0-9]*$/;
			  if(!reg.test(ret.qty)) return; 
			  
			  $("#dataTable").jqGrid("setCell", rowId, "sumcost", parseFloat(ret.cost)*parseFloat(ret.qty)); 
		  }
           
	};  
	Global.JqGrid.initEditJqGrid("dataTable",gridOption);  

	if ($.trim("${itemApp.itemType1}") != "JC") { //只有集成供应商才可编辑数量
/* 		$("#dataTable").setColProp("qty",{editable:false}); */
	} else {
		$("#lblOtherCost").text("安装费");
		$("#lblOtherCostAdj").text("售后费");
/* 		if("${itemApp.sendType}"!="1"){
			$("#dataTable").setColProp("qty",{editable:false});
		} */
	}
});

function save(flag){
	//if(!$("#dataForm").valid()) {return false;}//表单校验
	//if($("#infoBoxDiv").css("display")!='none'){return false;}
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	
	/*
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/supplierItemApp/doUpdate',
		type: 'post',
		data: datas,
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
						if(parentWin != null)
				        	parentWin.goto_query();
	    				closeWin();
				    }
				});
	    	}else{
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
	 
	 */
	 
	var param= Global.JqGrid.allToJson("dataTable");
	
	if(flag != true){
		var tipIndex = 0;
		var sumQty = 0.0;
		var sumDeclareQty = 0.0;
		for(var i = 0;i < param.datas.length;i++){
			if(parseFloat(param.datas[i].qty)+parseFloat(param.datas[i].shortqty)-parseFloat(param.datas[i].declareqty) > 
				parseFloat(param.datas[tipIndex].qty)+parseFloat(param.datas[tipIndex].shortqty)-parseFloat(param.datas[tipIndex].declareqty)){
				tipIndex = i;	
			}
			if((parseFloat(param.datas[i].qty)+parseFloat(param.datas[i].shortqty)-parseFloat(param.datas[i].declareqty))/parseFloat(param.datas[i].declareqty)>0.03){
				art.dialog({
					content: "【"+param.datas[tipIndex].itemcode+"】【"+param.datas[tipIndex].itemdescr+"】数量异常,是否要保存?",
					ok : function(){
						save(true);
					},
					cancel : function(){}
				});
				return;
			}
			sumQty += parseFloat(param.datas[i].qty)+parseFloat(param.datas[i].shortqty)-parseFloat(param.datas[i].declareqty);
			sumDeclareQty += parseFloat(param.datas[i].declareqty); 
		}
		if(sumQty/sumDeclareQty > 0.01){
			art.dialog({
				content: "【"+param.datas[tipIndex].itemcode+"】【"+param.datas[tipIndex].itemdescr+"】数量异常,是否要保存?",
				ok : function(){
					save(true);
				},
				cancel : function(){}
			});
			return;
		}
	}
	
	Global.Form.submit("dataForm","${ctx}/admin/supplierItemApp/doUpdate",param,function(ret){
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

//校验函数
$(function() {
	/*
	$("#dataForm").validate({
		rules: {
				"arriveDate": {
				maxlength: 23,
				required: true
				},
				"splRemark": {
				validIllegalChar: true,
				maxlength: 1000
				},
				"otherCost": {
				number: true,
				maxlength: 19,
				required: true
				},
				"otherCostAdj": {
				number: true,
				maxlength: 19,
				required: true
				},
				"productType": {
				required: true
				}
		}
	});
	*/
	$("#dataForm").bootstrapValidator({
		message: "This value is not valid",
        feedbackIcons: { /*input状态样式图片*/
			/* valid: "glyphicon glyphicon-ok",
			invalid: "glyphicon glyphicon-remove", */
			validating: "glyphicon glyphicon-refresh"
        },
        fields: {  
	    	productType: {  
	        	validators: {  
	            	notEmpty: {  
	            		message: "必填字段"  
	            	}  
	        	}  
	        },
	        otherCost: {  
	        	validators: {  
	            	notEmpty: {  
	            		message: "必填字段"  
	            	},
	            	numeric: {
	            		message: "请输入数值"  
	            	}  
	        	}  
	        },
	        otherCostAdj: {  
	        	validators: {  
	            	notEmpty: {  
	            		message: "必填字段"  
	            	},
	            	numeric: {
	            		message: "请输入数值"  
	            	}   
	        	}  
	        }              
    	},
		submitButtons : 'input[type="submit"]' /*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});

//时间重新验证
function validateRefresh(){
	$('#dataForm').data('bootstrapValidator')  
                  .updateStatus('arriveDate', 'NOT_VALIDATED',null)  
                  .validateField('arriveDate')
                  .updateStatus('productType', 'NOT_VALIDATED',null)  
                  .validateField('productType'); 

}

function onProductTypeChange() {
	if ($("#productType").val() == "") {
		return;
	}

	$.ajax({
		url:'${ctx}/admin/supplierItemApp/doCalcArriveDateBySendDay',
		type: 'post',
		data: {no:$("#no").val(),productType:$("#productType").val()},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '无法计算预计发货日期~'});
	    },
	    success: function(obj){
	    	if (obj.rows[0]) {
	    		var d = new Date();
	    		d.setTime(obj.rows[0].arrivedate);
	    		$("#arriveDate").val(d.format("yyyy-MM-dd"));
	    	} else {
	    		$("#arriveDate").val("");
	    	}
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
	      	<button type="button" class="btn btn-system " id="saveBut" onclick="save()">保存</button>
	      	<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      	</div>
	   </div>
	</div>
	
	<div class="panel panel-info"> 
		<div class="panel-body">
			<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				
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
								<input type="text" id="phone" name="phone"  value="${itemApp.phone}" readonly="readonly"/>
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
						<div class="col-sm-6 form-validate" >
							<li>
								<label>产品类型 </label>
								<house:xtdm id="productType" dictCode="APPPRODUCTTYPE" value="${itemApp.productType}" onchange="onProductTypeChange();"></house:xtdm>
							</li>
						</div>
						<div class="col-sm-6 form-validate" >
							<li>
								<label>预计到货日期</label>
								<input type="text" id="arriveDate" name="arriveDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.arriveDate }' pattern='yyyy-MM-dd'/>" onchange="validateRefresh()" required data-bv-notempty-message="预计到货日期不能为空"/>
							</li>
						</div>
					</div>
					<div class="validate-group row" >
						<div class="col-sm-6 form-validate" >
							<li>
								<label id="lblOtherCost">加工费 </label>
								<input type="text" id="otherCost" name="otherCost"  value="${itemApp.otherCost}" readonly/>
							</li>
						</div>
						<div class="col-sm-6 form-validate" >
							<li>
								<label id="lblOtherCostAdj">物流配送费</label>
								<input type="text" id="otherCostAdj" name="otherCostAdj"  value="${itemApp.otherCostAdj}" readonly/>
							</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-12" >
							<li>
								<label>到厂日期 </label>
								<input type="text" id="arriveSplDate" name="arriveSplDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.arriveSplDate }' pattern='yyyy-MM-dd'/>" />
							</li>	
						</div>
					</div>	
					<div class="validate-group row">
						<div class="col-sm-12" >
							<li>
								<label class="control-textarea">备注 </label>
								<textarea id="splRemark" name="splRemark" style="width:645px;" rows="3">${itemApp.splRemark}</textarea>
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

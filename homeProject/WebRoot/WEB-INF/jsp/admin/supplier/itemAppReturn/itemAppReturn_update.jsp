<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>退货修改</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

</head>
    
<body>
<div class="body-box-form" >
		<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
      <button type="button" id="saveBtn" class="btn btn-system "   >保存</button>
      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
      </div>
   </div>
	</div>
	 <div class="panel panel-info" >  
                <div class="panel-body">
                    <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<input type="hidden" name="isCupboard" id="isCupboard" value="${itemApp.isCupboard}"/>
						<ul class="ul-form">
							<div class="validate-group row" >
							<div class="col-sm-4" >
							<li class="form-validate">
							<label><span class="required">*</span>原领料单号</label>
						
							<input type="text" id="oldNo" name="oldNo"  value="${itemApp.oldNo}" readonly="readonly"/>
							</li>
							</div>					
							<div class="col-sm-4" >
							<li><label><span class="required">*</span>其他费用</label>
							
							<input type="text" id="otherCost" name="otherCost" value="${itemApp.doubleString}" placeholder="正数减少退货金额"/>
							
							</li>
							</div>
							<div class="col-sm-4" >
							<li>
							<label><span class="required">*</span>退货单号</label>
							
							<input type="text" id="no" name="no"  value="${itemApp.no}" readonly="readonly"/>
							</li>
							</div>
							</div>
						<div class="validate-group row" >
							<div class="col-sm-4" >
							<li>
							<label>领料类型</label>
							
							<house:xtdm id="type" dictCode="ITEMAPPTYPE" value="${itemApp.type}" disabled="true"></house:xtdm>
							</li>
							</div>
							<div class="col-sm-4" >
							<li>
							<label><span class="required">*</span>单据状态</label>
						
							<house:xtdm id="status" dictCode="ITEMAPPSTATUS" value="${itemApp.status}" disabled="true"></house:xtdm>
							</li>
							</div>
							<div class="col-sm-4" >
							<li>
							<label><span class="required">*</span>退货类型</label>
						
								<select name="sendType"  disabled="disabled">
									<option value="">请选择...</option>
									<option value="1" ${itemApp.sendType=='1'?'selected':''}>退货到供应商</option>
									<option value="2" ${itemApp.sendType=='2'?'selected':''}>退货到仓库</option>
								</select>
							</li>
						</div>
						</div>	
						<div class="validate-group row" >
							<div class="col-sm-4" >
							<li>
							<label><span class="required">*</span>材料类型1</label>
							
							<select id="itemType1" name="itemType1"  disabled="disabled"></select>
							</li>
							</div>						
							<div class="col-sm-4" >
							<li><label>材料类型2</label>
							
							<select id="itemType2" name="itemType2"  disabled="disabled"></select>
							</li>
							</div>
							</div>
							<div class="validate-group row" >					
							<div class="col-sm-4" >
							<li class="form-validate">
							<label><span class="required">*</span>是否服务性产品</label>
							
							<house:xtdm id="isService" dictCode="YESNO" value="${itemApp.isService}" disabled="true"/>	
							</li>
							</div>
							<div class="col-sm-4" >
							<li class="form-validate">
							<label><span class="required">*</span>客户编号</label>
							
							<input type="text" id="custCode" name="custCode"  value="${itemApp.custCode}" />
							</li>
							</div>
							</div>
							<div class="validate-group row" id="id_supplCode" >	
							<div class="col-sm-4" >
							<li>
							<label><span class="required">*</span>供应商编号</label>
							
							<input type="text" id="supplCode" name="supplCode"  value="${itemApp.supplCode}" readonly="readonly"/>
							</li>
							</div>					
							<div class="col-sm-4" >
							<li>
							<label>供应商名称</label>
							
							<input type="text" id="supplCodeDescr" name="supplCodeDescr" value="${itemApp.supplCodeDescr}" readonly="readonly" />	
							</li>
							</div>
							</div>
						<div class="validate-group row" id="id_whcode" style="display: none;" >	
							<div class="col-sm-4" >
							<li>
							<label><span class="required">*</span>仓库编号</label>
							
							<input type="text" id="whcode" name="whcode"  value="${itemApp.whcode}" readonly="readonly"/>
							</li>
							</div>					
							<div class="col-sm-4" >
							<li>
							<label>仓库名称</label>
							
							<input type="text" id="whcodeDescr" name="whcodeDescr" value="${itemApp.whcodeDescr}" readonly="readonly" />	
							</li>
							</div>
							</div>
						<div class="validate-group row" >				
							<div class="col-sm-4" >
							<li class="form-validate">
							<label><span class="required">*</span>是否套餐材料</label>
							
							<house:xtdm id="isSetItem" dictCode="YESNO" value="${itemApp.isSetItem}" disabled="true"/>
							</li>
							</div>
							<div class="col-sm-4" >
							<li>
							<label>申请人员</label>
							
							<input type="text" id="appCzy" name="appCzy"  value="${itemApp.appCzy}" readonly="readonly"/>
							</li>
							</div>						
							<div class="col-sm-4" >							
							<li>
							<label>申请时间</label>
						<input type="text"   value="<fmt:formatDate value="${itemApp.date}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
							
							</li>
							</div>
					</div>
					
		
					<div class="validate-group row" >
							
						 <div class="col-sm-12" >	
							<li>
							<label  class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks"  style="width: 810px">${itemApp.remarks}</textarea>
							</li>
						</div>	
					</div>	
				</ul>
			</form>
		</div>

	
	</div>
					<!--标签页内容 -->
		<div class="container-fluid" id="tabs">
		    <ul class="nav nav-tabs" >
		    <li class="active"><a href="#tabs-1" data-toggle="tab">领料退回明细</a></li>
		     
		    </ul>
			<div class="tab-content">
				   <div id="tabs-1" class="tab-pane fade in active" >
		  <div class="panel panel-system">
			 <div class="panel-body" >
			      <div class="btn-group-xs"  >
			      <button id="addDetailExistsReturn"  type="button" class="btn btn-system " >新增</button>
			      <button id="delDetail" type="button" class="btn btn-system "  >删除</button>
			      <button   id="detailExcel"   type="button" class="btn btn-system "  >输出至excel</button> 
			      </div>
				</div>
			</div>
			       <div class="pageContent" style="padding-top:1px">
						<div id="content-list">
							<table id= "itemAppDataTable"></table>
							
						</div>
					</div>
		 
			</div>
		
		</div>
		</div>
</div>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_itemApp.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
//校验函数
$(function() {

	function getData(data){
		var dataSet = {
			firstSelect:"itemType1",
			firstValue:data.itemtype1,
			secondSelect:"itemType2",
			secondValue:data.itemtype2
		};
		Global.LinkSelect.setSelect(dataSet);
		$("#custCode").setComponent_customer({showValue:data.custcode,showLabel:data.custdescr});
		//$("#supplCode").setComponent_supplier({showValue:data.supplcode});
		if (data.supplcode){
			$("#id_supplCode").show();
			$("#id_whcode").hide();
		}else{
			$("#id_supplCode").hide();
			$("#id_whcode").show();
		}
		$('#supplCode').val(data.supplcode);
		$('#supplCodeDescr').val(data.supplcodedescr);
		$('#whcode').val(data.whcode);
		$('#whcodeDescr').val(data.whcodedescr);
		$('#isSetItem').val(data.issetitem);
		$('#isSetItem_disabled').val(data.issetitem);
		$('#isService').val(data.isservice);
		$('#isService_disabled').val(data.isservice);
		$('#sendType').val(data.sendtype);
		$('#isCupboard').val(data.iscupboard);
		validateRefresh('openComponent_customer_custCode');
		validateRefresh('openComponent_itemApp_oldNo');
	}
	//$("#supplCode").openComponent_supplier({valueOnly: true});
	//$("#supplCode").setComponent_supplier({showValue:'${itemApp.supplCode}',readonly: true});
	$("#custCode").openComponent_customer({callBack:function(){validateRefresh('openComponent_customer_custCode');}});
	$("#custCode").setComponent_customer({showValue:'${itemApp.custCode}',showLabel:'${itemApp.custCodeDescr}',readonly: true});
	$("#oldNo").openComponent_itemApp();
	$("#oldNo").setComponent_itemApp({showValue:'${itemApp.oldNo}',readonly: true});
	if ('${itemApp.supplCode}'){
		$("#id_supplCode").show();
		$("#id_whcode").hide();
	}else{
		$("#id_supplCode").hide();
		$("#id_whcode").show();
	}
	//初始化材料类型1、材料类型2
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1","itemType2");
	var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${itemApp.itemType1}',
		secondSelect:"itemType2",
		secondValue:'${itemApp.itemType2}'
	};
	Global.LinkSelect.setSelect(dataSet);
	
	//初始化tabs
	
	
	var lastCellRowId;
	var gridOption = {
		height:$(document).height()-$("#content-list").offset().top-95,
		rowNum:10000000,
		styleUI: 'Bootstrap',
		colModel : [
		  {name : 'specreqpk',index : 'specreqpk',width : 100,label:'specreqpk',sortable : true,align : "left",hidden:true},
		  {name : 'pk',index : 'pk',width : 100,label:'pk',sortable : true,align : "left",hidden:true,hidden:true},
		  {name : 'reqpk',index : 'reqpk',width : 100,label:'reqpk',sortable : true,align : "left",hidden:true},
	      {name : 'no',index : 'no',width : 100,label:'批次号',sortable : true,align : "left",hidden:true},
	      {name : 'itemcode',index : 'itemcode',width : 100,label:'材料编号',sortable : true,align : "left"},
	      {name : 'itemdescr',index : 'itemdescr',width : 200,label:'材料名称',sortable : true,align : "left"},
	      {name : 'fixareapk',index : 'fixareapk',width : 100,label:'装修区域',sortable : true,align : "left",hidden:true},
	      {name : 'fixareadescr',index : 'fixareadescr',width : 100,label:'装修区域',sortable : true,align : "left"},
	      {name : 'intprodpk',index : 'intprodpk',width : 100,label:'集成成品',sortable : true,align : "left",hidden:true},
	      {name : 'intproddescr',index : 'intproddescr',width : 100,label:'集成成品',sortable : true,align : "left"},
	      {name : 'uom',index : 'uom',width : 100,label:'单位',sortable : true,align : "left",hidden:true},
	      {name : 'uomdescr',index : 'uomdescr',width : 100,label:'单位',sortable : true,align : "left"},
	      {name : 'qty',index : 'qty',width : 100,label:'退货数量',sortable : true,align : "left",editable:true,editrules: {number:true}},
	      {name : 'reqqty',index : 'reqqty',width : 100,label:'需求数量',sortable : true,align : "left"},
	      {name : 'sendqty',index : 'sendqty',width : 130,label:'总共已发货数量',sortable : true,align : "left"},
	      {name : 'confirmedqty',index : 'confirmedqty',width : 100,label:'已审核数量',sortable : true,align : "left"},
	      {name : 'remarks',index : 'remarks',width : 200,label:'备注',sortable : true,align : "left"},
	      {name : 'cost',index : 'cost',width : 100,label:'cost',sortable : true,align : "left",hidden:true},
  	      {name : 'projectcost',index : 'projectcost',width : 100,label:'projectcost',sortable : true,align : "left",hidden:true},
  	      {name : 'aftqty',index : 'aftqty',width : 100,label:'aftqty',sortable : true,align : "left",hidden:true},
  	      {name : 'aftcost',index : 'aftcost',width : 100,label:'aftcost',sortable : true,align : "left",hidden:true},
  	      {name : 'allcost',index : 'allcost',width : 100,label:'allcost',sortable : true,align : "left",hidden:true},
  	      {name : 'allprojectcost',index : 'allprojectcost',width : 100,label:'allprojectcost',sortable : true,align : "left",hidden:true}
        ],
		beforeSaveCell:function(rowId,name,val,iRow,iCol){
			lastCellRowId = rowId;
		},
		afterSaveCell:function(rowId,name,val,iRow,iCol){
	    	var rowData = $("#itemAppDataTable").jqGrid("getRowData",rowId);
	        rowData["allcost"] = (rowData.qty*rowData.cost).toFixed(2);
	    	rowData["allprojectcost"] = (rowData.qty*rowData.projectcost).toFixed(2);
	    	Global.JqGrid.updRowData("itemAppDataTable",rowId,rowData);
	    },
            beforeSelectRow:function(id){
         	   relocate(id,"itemAppDataTable");
	       
          }
	};
	if($.trim($("#no").val()) != ''){
		$.extend(gridOption,{
			url:"${ctx}/admin/itemAppDetail/goJqGridReturn",
			postData:{no:$.trim($("#no").val())}
		});
	}
	//初始化领料申请明细
	Global.JqGrid.initEditJqGrid("itemAppDataTable",gridOption);
	
	//新增
	$("#addDetailExistsReturn").on("click",function(){
		var oldNo = $.trim($("#oldNo").val());
		if(oldNo==''){
			art.dialog({content: "请选择原领料编号",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("itemAppDataTable","reqpk");
		Global.Dialog.showDialog("addDetailExistsReturn",{
			  title:"领料退回明细-新增",
			  url:"${ctx}/admin/itemAppDetail/goItemAppDetailExistsReturn",
			  height: 680,
			  width:1000,
			  postData:{no: oldNo,unSelected: detailJson["fieldJson"]},
			  returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
								  itemcode: v.itemcode,
								  itemdescr: v.itemdescr,
								  reqpk: v.reqpk,
								  fixareapk: v.fixareapk,
								  fixareadescr: v.fixareadescr,
								  intprodpk: v.intprodpk,
								  intproddescr: v.intproddescr,
								  qty: v.qty,
								  sendqty: v.sendqty,
								  confirmedqty: v.confirmedqty,
								  reqqty: v.reqqty,
								  aftqty: v.aftqty,
								  aftcost: v.aftcost,
								  cost: v.cost,
								  projectcost: v.projectcost,
								  remarks: v.remarks,
								  uomdescr: v.uomdescr,
								  allcost: v.allcost,
								  allprojectcost: v.allprojectcost,
								  specreqpk:v.specreqpk
						  };
						  Global.JqGrid.addRowData("itemAppDataTable",json);
					  });
				  }
			  }
			});
	});
	//删除
	$("#delDetail").on("click",function(){
		var id = $("#itemAppDataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		Global.JqGrid.delRowData("itemAppDataTable",id);
	});
	//输出到Excel
	$("#detailExcel").on("click",function(){
		Global.JqGrid.exportExcel("itemAppDataTable","${ctx}/admin/itemAppDetail/export","领料单导出","targetForm");
	});
	//保存操作
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var ids = $("#itemAppDataTable").getDataIDs();
		if(!ids || ids.length == 0){
			art.dialog({content: "明细表中无数据！",width: 200});
			return false;
		}
		var param = Global.JqGrid.allToJson("itemAppDataTable");
		Global.Form.submit("dataForm","${ctx}/admin/supplierItemAppReturn/doUpdate",param,function(ret){
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
	});
	 $("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
              
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {  
	        openComponent_customer_custCode: {  
	        validators: {  
	            notEmpty: {  
	            message: '客户编号不能为空'  
	            }  
	        }  
	       },
	       openComponent_itemApp_oldNo: {  
	        validators: {  
	            notEmpty: {  
	            message: '原领料单号不能为空'  
	            }  
	        }  
	       },
	        isService: {  
	        validators: {  
	            notEmpty: {  
	            message: '是否服务性产品不能为空'  
	            }  
	        }  
	       },
	        isSetItem: {  
	        validators: {  
	            notEmpty: {  
	            message: '是否套餐材料不能为空'  
	            }  
	        }  
	       }       
    	},
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        });
});

</script>
</body>
</html>

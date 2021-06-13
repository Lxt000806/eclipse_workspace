<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>赠送项目新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_gift.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	var url = "";
	$("#itemDetail").hide();
	function setDetail(data){
		if(data){
			$("#quoteModule").val(data.quotemodule.trim());
			$("#type").val(data.type);
			$("#perfDiscType").val(data.perfdisctype);
			$("#perfDiscPer").val(data.perfdiscper);
			$("#maxDiscAmtExpr").val(data.maxdiscamtexpr);
			$("#isSoftToken").val(data.issofttoken);
			$("#calcDiscCtrlPer").val(data.calcdiscctrlper);
			$("#discAmtType").val(data.discamttype.trim());
			$("#discPer").val(data.discper);
			$("#discAmtCalcType").val(data.discamtcalctype);
			$("#isAdvance").val(data.isadvance);
			$("#isSoftToken").attr("disabled","disabled");
			$("#calcDiscCtrlPer").attr("disabled","disabled");
			$("#perfDiscType").attr("disabled","disabled");
			$("#type").attr("disabled","disabled");
			$("#discAmtType").attr("disabled","disabled");
			$("#quoteModule").attr("disabled","disabled");
			$("#discAmtCalcType").attr("disabled","disabled");
			$("#isAdvance").attr("disabled","disabled");
			chgType();
			
			if(data.type=='1'||data.type=='2'){
				$("#discAmount").val("");
			}
			
			if("JZ"==data.discamttype.trim() || $("#discAmtType").val().trim()=="JZ" ){
				jQuery("#dataTable").setGridParam().showCol("basecost").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("lineamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("beflineamount").trigger("reloadGrid");
				url="${ctx}/admin/itemPlan/goBaseItemPlanJqGrid?custCode=${customer.code}&giftPk="+data.pk;
			} else{
				jQuery("#dataTable").setGridParam().hideCol("basecost").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("lineamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("beflineamount").trigger("reloadGrid");
				url="${ctx}/admin/itemPlan/goItemPlanJqGrid?isCustGift=1&custCode=${customer.code}&giftPk="+data.pk;
				
			}
			var expr = $.trim(data.maxdiscamtexpr);
			$("#maxDiscAmt").val("");
			$.ajax({
				url:"${ctx}/admin/itemPlan/getMaxDiscExpr",
				type:'post',
				data:{pk:data.pk,area:"${customer.area}",custType:"${customer.custType}"},
				dataType:'json',
				cache:false,
				async: false,
				error:function(obj){
					showAjaxHtml({"hidden": false, "msg": '出错~'});
				},
				success:function(obj){
					expr = expr.replace(/@MainSegDiscAmount@/g,obj); 
					expr = expr.replace(/@SoftSegDiscAmount@/g,obj); 
					expr = expr.replace(/@BaseSegDiscAmount@/g,obj); 
					expr = expr.replace(/@Area@/g,"${customer.area}");
					expr = expr.replace(/@MainSetFee@/g, "${customer.mainSetFee}");
					expr = expr.replace(/@WallArea@/g, "${wallArea}")
					$("#maxDiscAmt").val(eval(expr));
				}
			});
		}
	}
	$("#giftPK").openComponent_gift({callBack:setDetail,condition:{custCode:"${customer.code}"}});
	
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","discAmtType");
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","quoteModule");
	
	var postData = $("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		//url:"${ctx}/admin/itemPlan/goItemGiftJqgrid",
		postData:{},
		height:$(document).height()-$("#content-list").offset().top-400,
		colModel : [
			{name: "pk", index: "pk", width: 122, label: "编号", sortable:false, align: "left", hidden: true},
			{name: "itemplanpk", index: "itemplanpk", width: 122, label: "itemplanpk", sortable:false, align: "left",hidden: true},
			{name: "fixareapk", index: "fixareapk", width: 122, label: "区域名称", sortable:false, align: "left", hidden: true},
			{name: "fixareadescr", index: "fixareadescr", width: 122, label: "区域名称", sortable:false, align: "left"},
			{name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable:false, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 220, label: "材料名称", sortable:false, align: "left",},
			{name: "baseitemcode", index: "baseitemcode", width: 90, label: "基础项目编号", sortable:false, align: "left",},
			{name: "baseitemdescr", index: "baseitemdescr", width: 120, label: "基础项目名称", sortable:false, align: "left",},
			{name: "qty", index: "qty", width: 70, label: "数量", sortable:false, align: "right"},
			{name: "unitprice", index: "qty", width: 70, label: "单价", sortable:false, align: "right"},
			{name: "beflineamount", index: "beflineamount", width: 90, label: "折扣前金额", sortable:false, align: "right", sum: true},
			{name: "lineamount", index: "lineamount", width: 90, label: "总价", sortable:false, align: "right", sum: true},
			{name: "totalcost", index: "totalcost", width: 70, label: "成本额", sortable:false, align: "right",sum:true,hidden:true},   
			{name: "markup", index: "markup", width: 60, label: "折扣", sortable:false, align: "right",},
			{name: "cost", index: "cost", width: 1, label: "成本", sortable:false, align: "right",sum:true ,hidden:true},
			{name: "discamount", index: "discamount", width: 90, label: "优惠金额", sortable:false, align: "right", sum: true,hidden:true},
			{name: "projectamount", index: "projectamount", width: 122, label: "项目经理结算额", sortable:false, align: "right",hidden: true},    
		],
		gridComplete:function(){
			var rowData = $("#dataTable").jqGrid('getRowData');
			var discAmount=0;
        	$.each(rowData,function(k,v){
        		if(v.markup!=0){
        			discAmount=myRound(v.beflineamount-myRound(v.beflineamount*v.markup/100));
        			if(discAmount<0){
        				discAmount=0;	
        			}
               		$("#dataTable").setCell(k+1,'discamount',discAmount); 
               	}else{
                	$("#dataTable").setCell(k+1,'discamount',v.beflineamount);
               	} 
       		}); 
			$("#saleAmount").val(myRound($("#dataTable").getCol("beflineamount", false, "sum"),4));	
			$("#totalCost").val(myRound($("#dataTable").getCol("totalcost", false, "sum"),4));	
			$("#projectAmount").val(myRound($("#dataTable").getCol("projectamount", false, "sum"),4));	
			chgType();
			chgPerfDiscType();
		},
	});
	 
	$("#add").on("click",function(){
		if($("#giftPK").val()==""){
			art.dialog({
				content:"请选择赠送项目",
			});
			return;
		}
		Global.Dialog.showDialog("add",{ 
			title:"赠品项目——新增",
			url:"${ctx}/admin/itemPlan/goAddItemPlan",
			postData:{giftPK:$("#giftPK").val(),custCode:"${customer.code}",url:url},
			height: 700,
			width:1000,
			returnFun:function(data){
				if(data){
					var discAmount=0;
					$.each(data,function(k,v){
						if (obj){
							v.totalcost=myRound(v.qty*v.cost);
							if(v.markup!=0){
								discAmount=myRound(v.beflineamount-myRound(v.beflineamount*v.markup/100));
			        			if(discAmount<0){
			        				discAmount=0;	
			        			}
								v.discamount=discAmount;
			               	}else{
			               		v.discamount=v.beflineamount;
			               	} 
							if(v.projectcost){
							   v.projectamount=v.qty*v.projectcost;
							}else{
							   v.projectamount=0;
							}
							Global.JqGrid.addRowData("dataTable",v);
						}
					});
				}
			} 
		});
	});
	
	$("#del").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		art.dialog({
			content:"是删除该记录？",
			lock: true,
			width: 100,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
				$("#saleAmount").val(myRound($("#dataTable").getCol("beflineamount", false, "sum"),4));	
				$("#totalCost").val(myRound($("#dataTable").getCol("totalcost", false, "sum"),4));	
				chgType();	
				chgPerfDiscType();
			},
			cancel: function () {
				return true;
			}
		});
	});
	
	$("#saveBtn").on("click",function(){
		var param= Global.JqGrid.allToJson("dataTable");
		if($.trim($("#type").val())=="3" && param.datas.length==0){
			art.dialog({
				content:"赠品明细不能为空",
			});
			return;
		}
		if(($.trim($("#type").val())=="1"|| $.trim($("#type").val())=="2")&&$.trim($("#discAmount").val())==""){
			art.dialog({
				content:"优惠金额不能为空",
			});
			return;
		}
		
		if ($.trim($("#type").val()) === "1"
		    && $("#maxDiscAmt").val() !== ""
		    && parseFloat($("#discAmount").val()) - parseFloat($("#maxDiscAmt").val()) > 1) {
		
		    art.dialog({
                content:"现金优惠类项目 实际优惠金额不能超过最高优惠金额",
            })
            
		    return
		}
		
		Global.Form.submit("page_form","${ctx}/admin/itemPlan/doCustGiftSave",param,function(ret){
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
function chgPerfDiscType(){
	if($.trim($("#perfDiscType").val())=="1"){
		$("#perfDiscAmount").val(myRound($("#saleAmount").val(),4)*myRound($("#perfDiscPer").val(),4));
	}else if($.trim($("#perfDiscType").val())=="2"){
		$("#perfDiscAmount").val(myRound(myRound($("#totalCost").val(),4)*myRound($("#perfDiscPer").val(),4),4));
	}else if($.trim($("#perfDiscType").val())=="3"){
		$("#perfDiscAmount").val(myRound($("#discAmount").val(),4) *myRound($("#perfDiscPer").val(),4));
	}else if($.trim($("#perfDiscType").val())=="4"){
		$("#perfDiscAmount").val(myRound($("#projectAmount").val(),4) *myRound($("#perfDiscPer").val(),4));
	}else{
		$("#perfDiscAmount").val(0);
	}
}

function chgType(){
	if($("#type").val()!="3"){
		$("#itemDetail").hide();
		$("#discAmount").removeAttr("readonly",true);
	}else{
		//$("#discAmount").val(myRound(myRound($("#saleAmount").val(),4)*myRound($("#discPer").val(),4),4));
		getDiscAmount();
		$("#discAmount").attr("readonly",true);
		$("#itemDetail").show();
	}
}
function getDiscAmount(){
	if($("#discAmtCalcType").val()=="1"){  //按销售额
		$("#discAmount").val(myRound(myRound($("#dataTable").getCol("beflineamount", false, "sum"),4)*myRound($("#discPer").val(),4),4));
	}else if($("#discAmtCalcType").val()=="2") { //按项目经理结算额
		$("#discAmount").val(myRound(myRound($("#dataTable").getCol("projectamount", false, "sum"),4)*myRound($("#discPer").val(),4),4));
	}else{ //按优惠额
		$("#discAmount").val(myRound(myRound($("#dataTable").getCol("discamount", false, "sum"),4)*myRound($("#discPer").val(),4),4));
	}	                                  
}
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="panel panel-system" >
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired"/>
				<input type="hidden" id="discPer" name="discPer" style="width:160px;"/>
				<input type="hidden" id="custCode" name="custCode" style="width:160px;" value= "${customer.code }"/>
				<input type="hidden" id="totalCost" name="totalCost" style="width:160px;" readonly="true"/>
				<input type="hidden" id="maxDiscAmtExpr" name="maxDiscAmtExpr" style="width:450px;" readonly="true"/>
				<ul class="ul-form">
					<li>
						<label>赠送项目</label>
						<input type="text" id="giftPK" name="giftPK" style="width:160px;"/>
					</li>
					<li>
						<label>实际优惠金额</label>
						<input type="text" id="discAmount" name="discAmount" style="width:160px;" onkeyup="chgPerfDiscType()"/>
					</li>
					<li>
						<label>类型</label>
						<house:xtdm  id="type" dictCode="GIFTTYPE" style="width:160px;" onchange="chgType()" ></house:xtdm>
					</li>
					<li>
						<label>报价模块</label>
						<select type="text" id="quoteModule" name="quoteModule" style="width:160px;" ></select>
					</li>
					<li>
						<label>销售额</label>
						<input type="text" id="saleAmount" name="saleAmount" style="width:160px;" readonly="true"/>
					</li>
					<li>
						<label>优惠金额分类</label>
						<select type="text" id=discAmtType name="discAmtType" style="width:160px;" ></select>
					</li>
					<li>
						<label>业绩扣减方式</label>
						<house:xtdm  id="perfDiscType" dictCode="GIFTPERFDSTYPE" style="width:160px;" onchange="chgPerfDiscType()"></house:xtdm>
					</li>
					<li>
						<label>业绩扣减比例</label>
						<input type="text" id="perfDiscPer" name="perfDiscPer" style="width:160px;" readonly="true"/>
					</li>
					<li>
						<label>业绩扣减额</label>
						<input type="text" id="perfDiscAmount" name="perfDiscAmount" style="width:160px;" readonly="true"/>
					</li>
					<li>
						<label style="width:120px;">计入优惠额度控制比例</label>
						<input type="text" id="calcDiscCtrlPer" name="calcDiscCtrlPer" style="width:140px;" value="${custGift.calcDiscCtrlPer}"/>													
					</li>
					<li>
						<label>是否软装券</label>
						<house:xtdm id="isSoftToken" dictCode="YESNO" style="width:160px;" ></house:xtdm>
					</li>
					<li>
						<label>最高优惠金额</label>
						<input type="text" id="maxDiscAmt" name="maxDiscAmt" readonly="true"/>
					</li>
					<li hidden="true">
						<label>项目经理结算额</label>
						<input type="text" id="projectAmount" name="projectAmount" readonly="true"/>
					</li>
					<li>
						<label>优惠额度计算方式</label>
						<house:xtdm id="discAmtCalcType" dictCode="GIFTDACALCTYPE" ></house:xtdm>
					</li>
					 <li>
						<label>额度预支项目</label>
						<house:xtdm id="isAdvance" dictCode="YESNO" ></house:xtdm>
					</li>
					<li>
						<label>显示顺序</label>
						<input type="text" id="discSeq" name="discSeq" style="width:160px;" value="0"/>
					</li>
					<!-- <li>
						<label class="control-textarea">说明</label>
						<textarea id="remarks" name="remarks" rows="2"></textarea>
 					</li> -->
				</ul>	
			</form>
		</div>
		<div class="container-fluid" id="itemDetail"> 
			<div class="btn-panel">
    			<div class="btn-group-sm">
					<button type="button" class="btn btn-system" id="add">
						<span>添加</span>
					</button>
					<button type="button" class="btn btn-system" id="del">
						<span>删除</span>
					</button>
				</div>
			</div> 
			<ul class="nav nav-tabs"> 
		        <li class="active">
		        	<a href="#tab_detail" data-toggle="tab">赠送明细</a>
		        </li>
		    </ul> 
			<div id="content-list">
				<table id= "dataTable"></table>
			</div>	
		</div>
	</div>	
</body>
</html>



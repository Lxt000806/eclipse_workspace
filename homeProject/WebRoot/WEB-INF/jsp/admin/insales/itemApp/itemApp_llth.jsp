<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
	UserContext uc = (UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
	String czybh = uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
	<title>领料退回</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_itemApp.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
		function ajaxPost(url, data, successFun){
		 	$.ajax({
				url:url,
				type: "post",
		    	data: data,
				dataType: "json",
				cache: false,
				async:false,
				error: function(obj){			    		
					art.dialog({
						content: "访问出错,请重试",
						time: 3000,
						beforeunload: function () {}
					});
				},
				success: successFun
			});	
		}
		function editValid(id){
			if($("#"+id).val() == ""){
				art.dialog({
					content:"请输入完整的信息"
				});
				return true;
			}
			return false;
		}
		function save(unCheckFlag){
			
			if(editValid("no")){
				return;
			}
			if(editValid("custCode")){
				return;
			}
			if($("#backType").val() == ""){
				art.dialog({
					content:"请选择退货类型"
				});
				return;
			}
			
			if($("#backType").val() == "2"){
				if(editValid("supplCode")){
					return;
				}
			}else{
				if(editValid("whcode")){
					return;
				}
			}
			
			if($("#status").val() == ""){
				art.dialog({
					content:"请选择领料单状态"
				});
				return;
			}
			
			if($("#itemType1").val() == ""){
				art.dialog({
					content:"请选择材料类型1"
				});
				return;
			}
			
			if($("#oldNo").val() == ""){
				art.dialog({
					content:"请选择原领料单号"
				});
				return;
			}
			
			var ids = $("#dataTable_itemAppReturnDetail").jqGrid("getDataIDs");
			
			if(ids.length == 0){
				art.dialog({
					content:"领料明细不能为空"
				});
				return;
			}
			
			for(var i=0;i<ids.length;i++){
				var rowData = $("#dataTable_itemAppReturnDetail").jqGrid("getRowData",ids[i]);
				if(rowData.qty == 0){
					art.dialog({
						content:"材料["+rowData.itemcode+"-"+rowData.itemdescr+"]退回数量为["+rowData.qty+"],不允许保存!"
					});
					return;
				}
				if((rowData.qty - rowData.sendedqty > 0 ) && $("#m_umState").val() == "C" && $("#itemType1").val() == "JZ" && $("#isSetItem").val() != "1"){
					art.dialog({
						content:"材料["+rowData.itemcode+"-"+rowData.itemdescr+"]退回数量不能大于已发货数量,不允许保存"
					});
					return;
				}
				
				if((unCheckFlag != true) && (rowData.qty - rowData.sendedqty > 0 ) && ($("#isSetItem").val() != "1") 
					&& ($("#m_umState").val() == "C" || $("#m_umState").val() == "A" || $("#m_umState").val() == "M")){
					art.dialog({
						content:"材料["+rowData.itemcode+"-"+rowData.itemdescr+"]退回数量不能大于已发货数量,不允许保存",//改成不让保存
					});
					return;
				}
			}

			var gridJsonData= Global.JqGrid.allToJson("dataTable_itemAppReturnDetail");
			
			if($("#backType").val() == "1"){
				$("#supplCode").val("");
			}else{
				$("#whcode").val("");
			}
			
			Global.Form.submit(
				"dataForm",
				"${ctx}/admin/itemApp/doLlglthSave",
				gridJsonData,
				function(ret){
					var options = {
						content:ret.datas.info,
						ok:function(){
							if(ret.datas.success){
								closeWin();
							}
						}
					};
					if(ret.datas.success){
						$.extend(options, {
							time:1000,
							timeCloseButtonEvent:"ok"
						});
					}else{
						$("#_form_token_uniq_id").val(ret.token.token);
					}
					
					art.dialog(options);
				}
			);
			
		}
		$(function(){
			 if("${itemApp.itemType1}"=="JC"){
				$("#isCupboard").val($.trim("${itemApp.isCupboard}"));
			} 
			$("#whcode").openComponent_wareHouse({
				showValue:"${itemApp.whcode}",
				condition:{
					czybh:"${itemApp.czybh}"
				},
				callBack:function(ret){
					$("#whcodeDescr").val(ret.desc1);
				}
			});//,showLabel:"${itemApp.whcodeDescr}"
			$("#supplCode").openComponent_supplier({
				showValue:"${itemApp.supplCode}",
				callBack:function(ret){
					$("#supplCodeDescr").val(ret.Descr);
				},
				condition:{
					itemRight:"${itemApp.itemRight}"
				}
			});//,showLabel:"${itemApp.supplCodeDescr}"
			$("#oldNo").openComponent_itemApp({
				showValue:"${itemApp.oldNo}",
				condition:{
					type:"S",
					status:"SEND",
					openFrom:"itemApp_llth"
				},
				callBack:function(ret){
					if( ( ret.checkstatus == "3" || ret.checkstatus == "4" ) && ret.itemtype1.trim() == "JZ" ){
						if(ret.checkstatus == "3"){
							art.dialog({
								content:"楼盘【"+ret.address+"】处于项目经理提成要领状态，不允许领料退回"
							});
						}else{
							art.dialog({
								content:"楼盘【"+ret.address+"】处于项目经理提成已领状态，不允许领料退回"
							});
						}
						$("#oldNo").val("");
						$("#openComponent_itemApp_oldNo").val("");
						return false;
					}
					$("#oldNo").val(ret.no);
					$("#custCode").setComponent_customer({
						showValue:ret.custcode,
						showLabel:ret.custdescr,
						condition:{
							status:"4,5",
							disabledEle:"status_NAME"
						}
					});
					$("#custAddress").val(ret.address);
					$("#custDocumentNo").val(ret.documentno);
					$("#itemType1").val(ret.itemtype1.trim());
					$("#isCupboard").val(ret.iscupboard);
					//    CbxItemType1.OnChange(CbxItemType1);
					$("#itemType2").val(ret.itemtype2.trim());
					$("#isService").val(ret.isservice);
					$("#isSetItem").val(ret.issetitem);
					if(ret.sendtype == "2"){
						$("#backType").children("option").each(function(index,val){
							if("1" == $(this).val()){
								$("#backType").val($(this).val());
								$(this).attr("selected",true);
							}else{
								$(this).removeAttr("selected");
							}
						});
						$("#supplCode").val("");
						$("#openComponent_supplier_supplCode").val("");
						$("#supplCode").parent().attr("hidden",true);
						$("#supplCodeDescr").parent().attr("hidden",true);
						
						$("#whcode").parent().removeAttr("hidden");
						$("#whcodeDescr").parent().removeAttr("hidden");
/*						$("#whcode").setComponent_wareHouse({
							showValue:ret.whcode
						});//,showLabel:ret.whcodedescr
						$("#whcodeDescr").val(ret.whcodedescr);*/
						
						$("#backType").attr("disabled", true);
						$("#whcodeDescr").parent().removeAttr("hidden");
						$("#supplCodeDescr").parent().attr("hidden", true);
					}else{
						$("#backType").children("option").each(function(index,val){
							if("2" == $(this).val()){
								$("#backType").val($(this).val());
								$(this).attr("selected",true);
							}else{
								$(this).removeAttr("selected");
							}
						});
						$("#whcode").val("");
						$("#openComponent_wareHouse_whcode").val("");
						$("#whcode").parent().attr("hidden",true);
						$("#whcodeDescr").parent().attr("hidden",true);
						
						$("#supplCode").parent().removeAttr("hidden");
						$("#supplCodeDescr").parent().removeAttr("hidden");
						$("#supplCode").setComponent_supplier({
							showValue:ret.supplcode
						});//,showLabel:ret.supplcodedescr
						$("#supplCodeDescr").val(ret.supplcodedescr);
						
						$("#backType").removeAttr("disabled");
						$("#supplCodeDescr").parent().removeAttr("hidden");
						$("#whcodeDescr").parent().attr("hidden", true);
					}
					$("#projectMan").val(ret.projectman);
				},
				title:"搜寻--原领料单号"
			});
			$("#custCode").openComponent_customer({
				showValue:"${itemApp.custCode}",
				showLabel:"${itemApp.custCodeDescr}",
				condition:{
					status:"4,5",
					disabledEle:"status_NAME"
				},
				callBack:function(ret){
					$("#custAddress").val(ret.address);
					$("#custDocumentNo").val(ret.documentno);
				}
			});

		});
		window.onload = function(){
			$("#openComponent_wareHouse_whcode").attr("readonly",true);
			$("#openComponent_wareHouse_whcode").next().attr("data-disabled",true).css({
				"color":"#888"
			});
   			$("#openComponent_supplier_supplCode").attr("readonly",true);
			$("#openComponent_supplier_supplCode").next().attr("data-disabled",true).css({
				"color":"#888"
			});
   			$("#openComponent_customer_custCode").attr("readonly",true);
			$("#openComponent_customer_custCode").next().attr("data-disabled",true).css({
				"color":"#888"
			});
			$("#type").attr("disabled",true);
			$("#itemType1").attr("disabled",true);
			$("#no").attr("readonly",true);
			$("#status").attr("disabled",true);
			$("#backType").attr("disabled",true);
			$("#itemType1").attr("disabled",true);
			$("#itemType2").attr("disabled",true);
			$("#isService").attr("disabled",true);
			$("#isSetItem").attr("disabled",true);
			$("#isCupboard").attr("disabled",true);
			if($("#costRight").val() == "1"){
				$("#dataTable_itemAppReturnDetail").jqGrid("showCol","cost");
				$("#dataTable_itemAppReturnDetail").jqGrid("showCol","allcost");
				$("#dataTable_itemAppReturnDetail").jqGrid("showCol","projectcost");
				$("#dataTable_itemAppReturnDetail").jqGrid("showCol","allprojectcost");
			}			
			if($("#m_umState").val() == "A" || $("#m_umState").val() == "M"){
   				$("#openComponent_wareHouse_whcode").removeAttr("readonly");
				$("#openComponent_wareHouse_whcode").next().attr("data-disabled",false).css({
					"color":""
				});
			}
			if($("#m_umState").val() == "A"){
				$("#returnBut").css({
					"display":"none"
				});
				$("#cancelBut").css({
					"display":"none"
				});
				$("#pnlAppCZY").attr("hidden",true);
				$("#supplCode").parent().attr("hidden",true);
				$("#supplCodeDescr").parent().attr("hidden",true);
				$("#status").children("option").each(function (index,val){
					if("${itemApp.status}" == $(this).val()){
						$("#status").val($(this).val());
						$(this).attr("selected",true);
					}else{
						$(this).removeAttr("selected");
					}
				});
    			return;
			}
			if($("#m_umState").val() == "V"){
				$("#returnBut").css({
					"display":"none"
				});
				$("#cancelBut").css({
					"display":"none"
				});
				$("#saveBut").attr("disabled",true);
				$("#itemType1").attr("disabled",true);
				$("#appCzy").attr("readonly",true);
				$("#date").attr("readonly",true);
				$("#remarks").attr("readonly",true);
				$("#addBtn").attr("disabled",true);
				$("#delBtn").attr("disabled",true);
	   			$("#openComponent_supplier_supplCode").attr("readonly",true);
				$("#openComponent_supplier_supplCode").next().attr("data-disabled",true).css({
					"color":"#888"
				});
				$("#openComponent_wareHouse_whcode").attr("readonly",true);
				$("#openComponent_wareHouse_whcode").next().attr("data-disabled",true).css({
					"color":"#888"
				});
				/*
					Lbupdateby.Visible := True;
				    Lbdate.Visible := True;
				    TEdtLastUpdatedBy.Visible := True;
				    TEdtLastUpdate.Visible := True;
				*/
				if("${itemApp.sendType}" == "2"){
					$("#supplCode").parent().attr("hidden",true);
					$("#supplCodeDescr").parent().attr("hidden",true);
					$("#whcode").parent().removeAttr("hidden");
					$("#whcodeDescr").parent().removeAttr("hidden");
					$("#backType").children("option").each(function(index,val){
						if("1" == $(this).val()){
							$("#backType").val($(this).val());
							$(this).attr("selected",true);
						}else{
							$(this).removeAttr("selected");
						}
					});
				}else{
					$("#supplCode").parent().removeAttr("hidden");
					$("#supplCodeDescr").parent().removeAttr("hidden");
					$("#whcode").parent().attr("hidden",true);
					$("#whcodeDescr").parent().attr("hidden",true);
					$("#backType").children("option").each(function(index,val){
						if("2" == $(this).val()){
							$("#backType").val($(this).val());
							$(this).attr("selected",true);
						}else{
							$(this).removeAttr("selected");
						}
					});
				}
			}
			if($("#m_umState").val() == "C"){
				$("#itemType1").attr("disabled",true);
				$("#appCzy").attr("readonly",true);
				$("#date").attr("readonly",true);
				$("#addBtn").attr("disabled",true);
				$("#delBtn").attr("disabled",true);
				$("#saveBut").html("审核");
			}
			if($("#m_umState").val() == "M"){
				$("#returnBut").css({
					"display":"none"
				});
				$("#cancelBut").css({
					"display":"none"
				});
				$("#status").attr("disabled",true);
				$("#supplCode").parent().attr("hidden",true);
				$("#supplCodeDescr").parent().attr("hidden",true);
				
				if("${itemApp.oldSendType}" == "2"){
					$("#backType").attr("disabled", true);
				}else{
					$("#backType").removeAttr("disabled");
					$("#supplCode").setComponent_supplier({
						showValue:"${itemApp.oldSupplCode}",
						showLabel:"${itemApp.oldSupplCodeDescr}"
					});
					$("#supplCodeDescr").val("${itemApp.oldSupplCodeDescr}");
				}
			}
			itemType1Change();
			if("${itemApp.sendType}" == "2"){
				$("#supplCode").parent().attr("hidden",true);
				$("#supplCodeDescr").parent().attr("hidden",true);
				$("#whcode").parent().removeAttr("hidden");
				$("#whcodeDescr").parent().removeAttr("hidden");
				$("#backType").children("option").each(function(index,val){
					if("1" == $(this).val()){
						$("#backType").val($(this).val());
						$(this).attr("selected",true);
					}else{
						$(this).removeAttr("selected");
					}
				});
			}else{
				$("#supplCode").parent().removeAttr("hidden");
				$("#supplCodeDescr").parent().removeAttr("hidden");
				$("#whcode").parent().attr("hidden",true);
				$("#whcodeDescr").parent().attr("hidden",true);
				$("#backType").children("option").each(function(index,val){
					if("2" == $(this).val()){
						$("#backType").val($(this).val());
						$(this).attr("selected",true);
					}else{
						$(this).removeAttr("selected");
					}
				});
			}
			$("#status").children("option").each(function (index,val){
				if("${itemApp.status}" == $(this).val()){
					$("#status").val($(this).val());
					$(this).attr("selected",true);
				}else{
					$(this).removeAttr("selected");
				}
			});
			if($("#custCode").val() != "" && $("#itemType1").val().trim() == "JZ" && $("#itemType2").val().trim() == "JZFS"){
				ajaxPost(
					"${ctx}/admin/itemPreApp/getFsArea",
					{"custCode":$("#custCode").val().trim()},
					function(data){
						$("#fsArea").html(data);
						$("#fsArea").css("display","block");
						$("#fsArea").css({
							"left":($("#fsArea").parent().children("select").offset().left+160-288.34)+"px"
						});
					}
				);
			}
		};
 		function selectFirstRow(){
			var ids = $("#dataTable_itemAppReturnDetail").jqGrid("getDataIDs");
     		if(ids.length > 0){
				$("#dataTable_itemAppReturnDetail").jqGrid("setSelection",ids[0]);
				$("#itemType1").attr("disabled",true);
				$("#isService").attr("disabled",true);
	   			$("#openComponent_itemApp_oldNo").attr("readonly",true);
				$("#openComponent_itemApp_oldNo").next().attr("data-disabled",true).css({
					"color":"#888"
				});
	    	}else{
	   			$("#openComponent_itemApp_oldNo").removeAttr("readonly");
				$("#openComponent_itemApp_oldNo").next().attr("data-disabled",false).css({
					"color":""
				});
	    	}
	    }
	    function checkReturn(){
			if($("#m_umState").val() == "V"){
	    		return;
	    	}
			var ids = $("#dataTable_itemAppReturnDetail").jqGrid("getDataIDs");
			if(ids.length <= 0){
				art.dialog({content:"领料明细为空,审核退回失败"});
				return;
			}
			art.dialog({
				content:"确定要进行审核退回操作吗?",
				ok:function(){
					var param= Global.JqGrid.allToJson("dataTable_itemAppReturnDetail");
					
					var formData = $("#dataForm").jsonForm();
					formData.m_umState = "R";
					$.extend(param,formData);
					
					ajaxPost("${ctx}/admin/itemApp/doLlglthSave",param,
						function(ret){
							if(ret.rs == true){
								art.dialog({
									content:ret.msg,
									ok:function(){
										closeWin();
									}
								});
							}else{
								$("#_form_token_uniq_id").val(ret.token.token);
								art.dialog({
									content:ret.msg
								});
							}
						}
					);
				},
				cancel:function(){}
			});
	    }
	    function cancel(){
			if($("#m_umState").val() == "V"){
	    		return;
	    	}
			var ids = $("#dataTable_itemAppReturnDetail").jqGrid("getDataIDs");
			if(ids.length <= 0){
				art.dialog({content:"领料明细为空，取消领料失败"});
				return;
			}
			art.dialog({
				content:"确定要取消该领料单吗?",
				ok:function(){
					var param= Global.JqGrid.allToJson("dataTable_itemAppReturnDetail");
					
					var formData = $("#dataForm").jsonForm();
					formData.m_umState = "D";
					$.extend(param,formData);
					
					ajaxPost("${ctx}/admin/itemApp/doLlglthSave",param,
						function(ret){
							if(ret.rs == true){
								art.dialog({
									content:ret.msg,
									ok:function(){
										closeWin();
									}
								});
							}else{
								$("#_form_token_uniq_id").val(ret.token.token);
								art.dialog({
									content:ret.msg
								});
							}
						}
					);
				},
				cancel:function(){}
			});
	    }
	    function itemType1Change(){
	    	if($("#backType").val() == "1"){
	    		$("#supplCode").parent().attr("hidden",true);
	    		$("#whcode").parent().removeAttr("hidden");
	    		$("#whcodeDescr").parent().removeAttr("hidden");
	    		$("#supplCodeDescr").parent().attr("hidden", true);
	    	}else{
	    		$("#supplCode").parent().removeAttr("hidden");
	    		$("#whcode").parent().attr("hidden",true);
	    		$("#supplCodeDescr").parent().removeAttr("hidden");
	    		$("#whcodeDescr").parent().attr("hidden", true);
	    	}
	    	ajaxPost(
	    		"${ctx}/admin/itemPreApp/getItemType2Opt",
	    		{itemType1:$("#itemType1").val().trim()},
	    		function(ret){
	    			var oldValue = $("#itemType2").val().trim();
	    			$("#itemType2").html(ret.html);
					$("#itemType2").children().each(function(index,value){
						if(oldValue == $(this).val()){
							$(this).attr("selected",true);
						}else{
							$(this).removeAttr("selected");
						}
					});
	    		}
	    	);
	    }
	</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
    				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">保存</button>
    				<button id="returnBut" type="button" class="btn btn-system" onclick="checkReturn()">审核退回</button>
    				<button id="cancelBut" type="button" class="btn btn-system" onclick="cancel()">取消领料</button>
    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
    				<button id="sendViewBut" type="button" class="btn btn-system" onclick="closeWin()" style="display:none">分批发货明细</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body" style="padding:0px;">
				<form action="" method="post" id="dataForm"  class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="m_umState" id="m_umState" value="${itemApp.m_umState }" />
					<input type="hidden" name="costRight" id="costRight" value="${itemApp.costRight }" />
					<input type="hidden" name="expired" id="expired" value="${itemApp.expired }" />
					<input type="hidden" name="czybh" id="czybh" value="<%=czybh %>"/>
					<input type="hidden" id="isCupboard" name="isCupboard" value="${itemApp.isCupboard }"/>
					<ul class="ul-form">
						<li>
							<label>领料单号</label>
							<input type="text" id="no" name="no" value="${itemApp.no }"/>
						</li>
						<li>
							<label>原领料单号</label>
							<input type="text" id="oldNo" name="oldNo" value="${itemApp.oldNo }"/>
						</li>
						<li>
							<label>领料类型</label>
							<house:xtdm id="type" dictCode="ITEMAPPTYPE" value="${itemApp.type }"></house:xtdm>
						</li>
						<li>
							<label>单据状态</label>
							<select id="status" name="status" value="${itemApp.status }">
								<option value="">请选择...</option>
								<option value="OPEN">OPEN 打开</option>
								<option value="RETURN">RETURN 已退回</option>
								<option value="CONRETURN">CONRETURN 审核退回</option>
								<option value="CANCEL">CANCEL 取消</option>
							</select>
						</li>
						<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" value="${itemApp.custCode }"/>
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="custAddress" name="custAddress" value="${itemApp.custAddress }"/>
						</li>
						<li>
							<label>档案号</label>
							<input type="text" id="custDocumentNo" name="custDocumentNo" value="${itemApp.custDocumentNo }"/>
						</li>
						<li>
							<label>是否服务性产品</label>
							<house:xtdm id="isService" dictCode="YESNO" value="${itemApp.isService }" ></house:xtdm>
						</li>
						<li>
							<label>材料类型1</label>
							<house:dict id="itemType1" dictCode="" sql="select Code,(Code+' '+Descr) fd from tItemType1 order by DispSeq asc " sqlValueKey="Code" sqlLableKey="fd"  
										value="${itemApp.itemType1 }" onchange="itemType1Change()"></house:dict>
						</li>
						<li>
							<label>材料类型2</label>
							<house:dict id="itemType2" dictCode="" sql="select Code,(Code+' '+Descr) fd from tItemType2 " sqlValueKey="Code" 
										sqlLableKey="fd"  value="${itemApp.itemType2 }"></house:dict>
							<span id="fsArea" style="top:5px;display:none;position:absolute;font-size:12px"></span>
						</li>
						<li>
							<label>退货类型</label>
							<select id="backType" name="backType" onchange="itemType1Change()">
								<option value="">请选择...</option>
								<option value="1">1 退货到仓库</option>
								<option value="2">2 退货到供应商</option>
							</select>
						</li>
						<li>
							<label>供应商编号</label>
							<input type="text" id="supplCode" name="supplCode" value="${itemApp.supplCode }"/>
						</li>
						<li>
							<label>仓库编号</label>
							<input type="text" id="whcode" name="whcode" value="${itemApp.whcode }"/>
						</li>
						<li>
							<label>仓库名称</label>
							<input type="text" id="whcodeDescr" name="whcodeDescr" value="${itemApp.whcodeDescr }"/>
						</li>
						<li>
							<label>供应商名称</label>
							<input type="text" id="supplCodeDescr" name="supplCodeDescr" value="${itemApp.supplCodeDescr }"/>
						</li>
						<li>
							<label>是否套餐材料</label>
							<house:xtdm id="isSetItem" dictCode="YESNO" value="${itemApp.isSetItem }"></house:xtdm>
						</li>
						<div id="pnlAppCZY">
							<li>
								<label>申请人</label>
								<input type="text" id="appCzy" name="appCzy" value="${itemApp.appCzyDescr }"/>
							</li>
							<li>
								<label>申请时间</label>
								<input type="text" id="date" name="date" value="${itemApp.date }"/>
							</li>
						</div>
						<li>
							<label>项目经理</label>
							<input type="text" id="projectMan" name="projectMan" value="${itemApp.projectMan }" readonly/>
						</li>
						<li>
							<label>其他费用</label>
							<input type="text" id="otherCost" name="otherCost" value="${itemApp.otherCost }"/>
							<span style="color:black;">元</span><span style="color:red"> &nbsp;&nbsp;正数:应付给供应商的金额&nbsp;负数:供应商退还的金额</span>
						</li>
						<li>
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" style="height:55px">${itemApp.remarks }</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div  class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#tabView_itemAppReturnDetail" data-toggle="tab">领料退回明细</a></li>  
			</ul>  
		    <div class="tab-content">  
				<div id="tabView_itemAppReturnDetail"  class="tab-pane fade in active"> 
		         	<jsp:include page="tabView_itemAppReturnDetail.jsp">
		         		<jsp:param value="" name=""/>
		         	</jsp:include>
				</div> 
			</div>	
		</div>	
	</div>
</body>
</html>



<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>独立销售折扣审批</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
    <div class="query-form" style="border-bottom:0px;margin-top:0px">
    	<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
    		<input id="processDefinitionId" name="processDefinitionId" value="${processDefinitionKey }" hidden="true"/>
    		<input id="wfProcNo" name="wfProcNo" value="${wfProcNo }" hidden="true"/>
    		<input id="taskId" name="taskId" value="${taskId }"  hidden="true"/>
    		<input id="address" name="address"  hidden="true"/>
    		<input id="custDescr" name="custDescr"  hidden="true"/>
    		<input id="department" name="department" hidden="true" />
	   		<input id="comment" name="comment" value="" hidden=""/>
	   		<input id="empComment" name="empComment" value="" hidden=""/><!-- 评论   -->
	   		<input id="wfProcInstNo" name="wfProcInstNo" value="${wfProcInstNo }" hidden=""/>
	   		<input id="processInstId" name="processInstId" value="${piId }" hidden=""/>
    		<input id="photoPK" name="photoPK" value=""  hidden="true"/><%--有上传图片时必备--%>
    		<input id="url" name="url" value="${url }" hidden="true" /><%--有上传图片时必备--%>
    		<input id="activityId" name="activityId" value="${activityId }" hidden="true" /> 
    		<input id="fp__tWfCust_SaleDiscApprove__0__MinMarkup" name="fp__tWfCust_SaleDiscApprove__0__MinMarkup" value="${datas.fp__tWfCust_SaleDiscApprove__0__MinMarkup }" hidden="true"/> 
			<div id="detail_rel" hidden="true">
			</div>
        	<house:token></house:token>
        	<ul class="ul-form" id="detail_ul">
				<div class="validate-group row" >	
   					<input type="hidden" id="fp__tWfCust_SaleDiscApprove__0__IsClearInvStatus" name="fp__tWfCust_SaleDiscApprove__0__IsClearInvStatus"  style="width:160px;" value="${datas.fp__tWfCust_SaleDiscApprove__0__IsClearInvStatus}"/>
   					<input type="hidden" id="fp__tWfCust_SaleDiscApprove__0__ItemType1Descr" name="fp__tWfCust_SaleDiscApprove__0__ItemType1Descr"  style="width:160px;" value=""/>
	         		<li class="form-validate">
				        <label>客户名称</label>
						<input type="text" id="custDescr" name=""custDescr"" style="width:160px;" readonly="true" value="${datas.fp__tWfCust_SaleDiscApprove__0__CustDescr}"/>
   						<input type="hidden" id="fp__tWfCust_SaleDiscApprove__0__CustCode" name="fp__tWfCust_SaleDiscApprove__0__CustCode"  style="width:160px;" value="${datas.fp__tWfCust_SaleDiscApprove__0__CustCode}"/>
   						<input type="hidden" id="fp__tWfCust_SaleDiscApprove__0__CustDescr" name="fp__tWfCust_SaleDiscApprove__0__CustDescr"  style="width:160px;" value="${datas.fp__tWfCust_SaleDiscApprove__0__CustDescr}"/>
				    </li>
				    <li class="form-validate" hidden="true">
				        <label>楼盘</label>
   						<input type="text" readonly="true" id="fp__tWfCust_SaleDiscApprove__0__Address" name="fp__tWfCust_SaleDiscApprove__0__Address" 
   							 style="width:160px;" value="${datas.fp__tWfCust_SaleDiscApprove__0__Address}"/>
				    </li>
				    <li class="form-validate">
				        <label>客户类型</label>
						<input type="text" id="custTypeDescr" name="custTypeDescr" style="width:160px;" readonly="true" value="${datas.fp__tWfCust_SaleDiscApprove__0__CustTypeDescr}"/>
   						<input type="hidden" id="fp__tWfCust_SaleDiscApprove__0__CustType" name="fp__tWfCust_SaleDiscApprove__0__CustType"  style="width:160px;" value="${datas.fp__tWfCust_SaleDiscApprove__0__CustType}"/>
   						<input type="hidden" id="fp__tWfCust_SaleDiscApprove__0__CustTypeDescr" name="fp__tWfCust_SaleDiscApprove__0__CustTypeDescr"  style="width:160px;" value="${datas.fp__tWfCust_SaleDiscApprove__0__CustTypeDescr}"/>
				    </li>
				    <li class="form-validate">
				    	<label><span class="required">*</span>材料类型</label>
						<select id="fp__tWfCust_SaleDiscApprove__0__ItemType1" name="fp__tWfCust_SaleDiscApprove__0__ItemType1" 
							onchange="chgItemType1()" value="${datas.fp__tWfCust_SaleDiscApprove__0__ItemType1 }">
								<option value ="">请选择...</option>
								<option value ="软装" ${datas.fp__tWfCust_SaleDiscApprove__0__ItemType1 == '软装' ? 'selected' : ''}>软装</option>
								<!-- <option value ="集成" ${datas.fp__tWfCust_SaleDiscApprove__0__ItemType1 == '集成' ? 'selected' : ''}>集成</option>
								<option value ="主材" ${datas.fp__tWfCust_SaleDiscApprove__0__ItemType1 == '主材' ? 'selected' : ''}>主材</option>
								<option value ="基装" ${datas.fp__tWfCust_SaleDiscApprove__0__ItemType1 == '基装' ? 'selected' : ''}>基装</option> -->
						</select>
				    </li>
 					<li class="form-validate">
				        <label>销售金额</label>
						<input type="text" id="fp__tWfCust_SaleDiscApprove__0__BefAmount" name="fp__tWfCust_SaleDiscApprove__0__BefAmount" style="width:160px;" readonly="true" value="${datas.fp__tWfCust_SaleDiscApprove__0__BefAmount}"/>
				    </li>
				</div>
				<div class="validate-group row" style="max-height:999px">	
				    <li class="form-validate">
				        <label>优惠金额</label>
						<input type="text" id="fp__tWfCust_SaleDiscApprove__0__DiscAmount" name="fp__tWfCust_SaleDiscApprove__0__DiscAmount" style="width:160px;" readonly="true" value="${datas.fp__tWfCust_SaleDiscApprove__0__DiscAmount}"/>
				    </li>
				    <li class="form-validate">
				        <label>折扣</label>
						<input type="text" id="fp__tWfCust_SaleDiscApprove__0__Markup" name="fp__tWfCust_SaleDiscApprove__0__Markup" style="width:160px;" readonly="true" value="${datas.fp__tWfCust_SaleDiscApprove__0__Markup}"/>
				    </li>
					<li class="form-validate" style="max-height:999px">
						<label class="control-textarea">折扣说明</label>
						<textarea id="fp__tWfCust_SaleDiscApprove__0__DiscRemarks" name="fp__tWfCust_SaleDiscApprove__0__DiscRemarks" 
								 rows="2" style="width: 370px">${datas.fp__tWfCust_SaleDiscApprove__0__DiscRemarks}</textarea>
					</li>
				</div>
        	</ul>
        </form>
        <c:if test="${m_umState=='A'}">
			<div class="btn-panel" >
				<div class="btn-group-sm">
					<button type="button" class="btn btn-system" id="addItemPlanDetail">
						<span>从预算导入</span>
					</button>
					<button type="button" class="btn btn-system" id="delItemPlanDetail" onclick="delItemPlanDetail()">
						<span>删除</span>
					</button>
					<span style="color:red">有单品折扣或样品需要导入明细</span>
				</div>
			</div>	
        </c:if>
		<div class="container-fluid" >  
			<div id="content-list2">
				<table id= "fp__tWfCust_SaleDiscApproveDtl"></table>
			</div>	
		</div>	
     </div>
	<script src="${resourceRoot}/pub/component_customerOA.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
//detailJson 查看的时候传回来的明细数据
var html_ = $("#detail_div").html();
var detailJson=${detailJson};

function setElMap(){
	elMaps = {IsClearInvStatus: $.trim($("#fp__tWfCust_SaleDiscApprove__0__IsClearInvStatus").val()), 
			MinMarkup: $.trim($("#fp__tWfCust_SaleDiscApprove__0__MinMarkup").val()),
		};
}


$(function(){
	var detailList = ${detailList};
	document.getElementById("tab_apply_fjView_li").style.display="block";
	
	$("#dataForm").bootstrapValidator({
		excluded:["excluded"],
		message : "This value is not valid",
		feedbackIcons : {
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			fp__tWfCust_SaleDiscApprove__0__ItemType1:{  
		        validators: {  
		            notEmpty: {  
		           		 message: "材料类型1不能为空",
		            },
		        }  
		     },
		},
		submitButtons : "input[type='submit']",
	});
	
	Global.JqGrid.initJqGrid("fp__tWfCust_SaleDiscApproveDtl",{
		height:$(document).height()-$("#content-list2").offset().top-80,
		url:"${ctx}/admin/itemPlan/getWfProcDetail?wfProcInstNo=${wfProcInstNo }&custCode=${datas.fp__tWfCust_SaleDiscApprove__0__CustCode}",
		colModel : [
		  {name : "PK",index : "PK",width : 150,label:"PK",sortable : false ,align : "left",hidden: true},
		  {name : "ItemCode",index : "ItemCode",width : 80,label:"材料编号",sortable : false ,align : "left",},
		  {name : "ItemDescr",index : "ItemDescr",width : 240,label:"材料名称",sortable : false ,align : "left",},
		  {name : "FixAreaPk",index : "FixAreaPk",width : 90,label:"区域pk",sortable : false ,align : "left", hidden: true},
		  {name : "FixAreaDescr",index : "FixAreaDescr",width : 150,label:"区域名称",sortable : false ,align : "left",},
		  {name : "Qty",index : "Qty",width : 100,label:"数量",sortable : false ,align : "right",},
		  {name : "BefLineAmount",index : "BefLineAmount",width : 100,label:"优惠前金额",sortable : false ,align : "right",},
		  {name : "Markup",index : "Markup",width : 100,label:"折扣",sortable : false ,align : "right",},
		  {name : "IsClearInv",index : "IsClearInv",width : 100,label:"是否样品",sortable : false ,align : "left",},
		  {name : "ProcessCost",index : "ProcessCost",width : 100,label:"其他费用",sortable : false ,align : "right",},
	    ],
	});
	
	$("#addItemPlanDetail").on("click", function(){
		var itemType1 = $("#fp__tWfCust_SaleDiscApprove__0__ItemType1").val();
		var minMarkup = $("#fp__tWfCust_SaleDiscApprove__0__MinMarkup").val();
		if(itemType1 == ""){
			art.dialog({
				content:"请选择材料类型1",
			});
			return;
		}
		$("#fp__tWfCust_SaleDiscApproveDtl").jqGrid("clearGridData");
		$.ajax({
			url:"${ctx}/admin/itemPlan/getSaleDiscApproveDetail",
			type:"post",
			data:{custCode:"${datas.fp__tWfCust_SaleDiscApprove__0__CustCode}" , itemType1: itemType1},
			dataType:"json",
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "出错~"});
			},
			success:function(obj){
				if (obj){
					$.each(obj,function(k,v){
						var json = {
							ItemCode: v.ItemCode,
							ItemDescr: v.ItemDescr,
							FixAreaPk: v.FixAreaPk,
							FixAreaDescr: v.FixAreaDescr,
							Qty: v.Qty,
							BefLineAmount: v.BefLineAmount,
							Markup: v.Markup,
							IsClearInv: v.IsClearInv,
							ProcessCost: v.ProcessCost,
						};
						console.log(v.Markup, minMarkup);
						if(v.Markup < minMarkup){
							minMarkup = v.Markup;
							$("#fp__tWfCust_SaleDiscApprove__0__MinMarkup").val(v.Markup);
						}
						if(k==obj.length-1){
							getOperator_();
						}
						Global.JqGrid.addRowData("fp__tWfCust_SaleDiscApproveDtl",json);
					});
				}
			}
		});
	});
	if("${m_umState}"=="A"){
		chgItemType1();
	}
	console.log($.trim("${needDetail}"));
});

function getOperator_(flag){
	var elMap = {
			IsClearInvStatus: $.trim($("#fp__tWfCust_SaleDiscApprove__0__IsClearInvStatus").val()), 
			MinMarkup: $.trim($("#fp__tWfCust_SaleDiscApprove__0__MinMarkup").val()),
	};
	console.log(elMap);
	getOperator(flag,elMap);
}

function chgItemType1(){
	return;
	var itemType1 = $("#fp__tWfCust_SaleDiscApprove__0__ItemType1").val();
	$.ajax({
		url:"${ctx}/admin/itemPlan/getSaleAmountDetail",
		type:"post",
		data:{custCode:"${datas.fp__tWfCust_SaleDiscApprove__0__CustCode}" , itemType1: itemType1},
		dataType:"json",
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": "出错~"});
		},
		success:function(obj){
			if (obj){
				var befLineAmount = obj["BefLineAmount"];
				var discAmount = obj["DiscAmount"];
				$("#fp__tWfCust_SaleDiscApprove__0__BefAmount").val(befLineAmount);
				$("#fp__tWfCust_SaleDiscApprove__0__DiscAmount").val(discAmount);
				if(befLineAmount && befLineAmount != 0.0){
					$("#fp__tWfCust_SaleDiscApprove__0__Markup").val(myRound((1 - myRound(discAmount/befLineAmount,4))*100,2));
				} else {
					$("#fp__tWfCust_SaleDiscApprove__0__Markup").val(0.0);
				}
				$("#fp__tWfCust_SaleDiscApproveDtl").jqGrid("clearGridData");
				getOperator_();
			}
		}
	});
}

function doApply(){
	var datas = $("#dataForm").serialize();
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()){
	 	if($("#wfProcInstApplyTabsUl li[class='active']").attr("id") != "baseInfoLi"){
			$("#baseInfoLi > a").tab("show");
	 	}
	 	return;
	 }
	var isClearInvStatus = $("#fp__tWfCust_SaleDiscApprove__0__IsClearInvStatus").val();
	var markup = $("#fp__tWfCust_SaleDiscApprove__0__Markup").val();
	var param= Global.JqGrid.allToJson("fp__tWfCust_SaleDiscApproveDtl");
	var itemType1 = $("#fp__tWfCust_SaleDiscApprove__0__ItemType1").val();
	if(itemType1 == ""){
		art.dialog({
			content:"材料类型不能为空",
		});
		return;
	}
	
	if(param.datas == 0 && $.trim("${needDetail}") == "true"){
		art.dialog({
			content:"存在样品或折扣",
		});
		return;
	}
	
	if(param.datas && param.datas.length>0){
		$.each(param.datas,function(k,v){
			datas+="&fp__tWfCust_SaleDiscApproveDtl"+"__"+k+"__"+"PK="+v.PK;
			datas+="&fp__tWfCust_SaleDiscApproveDtl"+"__"+k+"__"+"ItemCode="+v.ItemCode;
			datas+="&fp__tWfCust_SaleDiscApproveDtl"+"__"+k+"__"+"ItemDescr="+v.ItemDescr;
			datas+="&fp__tWfCust_SaleDiscApproveDtl"+"__"+k+"__"+"FixAreaPk="+v.FixAreaPk;
			datas+="&fp__tWfCust_SaleDiscApproveDtl"+"__"+k+"__"+"FixAreaDescr="+v.FixAreaDescr;
			datas+="&fp__tWfCust_SaleDiscApproveDtl"+"__"+k+"__"+"Qty="+v.Qty;
			datas+="&fp__tWfCust_SaleDiscApproveDtl"+"__"+k+"__"+"BefLineAmount="+v.BefLineAmount;
			datas+="&fp__tWfCust_SaleDiscApproveDtl"+"__"+k+"__"+"Markup="+v.Markup;
			datas+="&fp__tWfCust_SaleDiscApproveDtl"+"__"+k+"__"+"IsClearInv="+v.IsClearInv;
			datas+="&fp__tWfCust_SaleDiscApproveDtl"+"__"+k+"__"+"ProcessCost="+v.ProcessCost;
		});
	}
	var dataTableManageCzy= Global.JqGrid.allToJson("dataTableManageCzy");
	if(dataTableManageCzy.datas){
		var mistake=false;
		for(var i=0;i<dataTableManageCzy.datas.length;i++){
			if(dataTableManageCzy.datas[i].taskKey != "" && dataTableManageCzy.datas[i].assignee == "" && dataTableManageCzy.datas[i].saveFlag=="option"){
				art.dialog({
					content: "请选择审批人！",
					time: 1000,
					beforeunload: function () {
						var li = document.querySelectorAll("li");
						li[0].className="";
						li[1].className="active";
						li[2].className="";
						li[3].className="";
						li[4].className="";
						li[5].className="";
						li[6].className="";
						li[7].className="";
						$("#tab_apply_manageCzy").addClass("tab-pane fade in active");
						document.getElementById("tab_apply_djxx").className='tab-pane fade';
						document.getElementById("tab_apply_spjl").className='tab-pane fade';
						document.getElementById("tab_apply_lct").className='tab-pane fade';
				    }
				});
				mistake = true;
				break;
			}
		}
		if(mistake==true){
			return;
		}
	}
	$.ajax({
  		url:"${ctx}/admin/wfProcInst/doApply",
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
}

function delItemPlanDetail(){
	var id = $("#fp__tWfCust_SaleDiscApproveDtl").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({
			content: "请选择一条记录进行删除操作！",
			width: 200
		});
		return false;
	}
	
	art.dialog({
		content:"是删除该记录？",
		lock: true,
		width: 100,
		height: 80,
		ok: function () {
			Global.JqGrid.delRowData("fp__tWfCust_SaleDiscApproveDtl",id);
		},
		cancel: function () {
			return true;
		}
	});
}

</script>
</body>
</html>


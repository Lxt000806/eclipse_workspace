<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>项目经理结算</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript"></script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
	    		<button type="button" class="btn btn-system " id="updateQualityFeeBtn" onclick="updateQualityFee()">质保金录入</button>
	    		<button type="button" class="btn btn-system " id="updateProjectCtrlAdjBtn" onclick="updateProjectCtrlAdj()">发包补贴录入</button>
				<button type="button" class="btn btn-system " id="saveBtn"><span id="btnName"></span></button>
				<button type="button" class="btn btn-system " id="JcszxxBtn">基础收支信息</button>
				<button type="button" class="btn btn-system " id="CustXxBtn">客户信息</button>
			    <button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search" target="targetFrame">
				<input type="hidden" name="m_umState" id="m_umState" value="${prjCheck.m_umState}"/>
				<input type="hidden" name="custTypeType" id="custTypeType" value="${prjCheck.custTypeType}"/>
				<input type="hidden" name="jsonString" value="" />
				<house:token></house:token>
				<ul class="ul-form">
						<li class="form-validate">
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode"   value="${prjCheck.custCode}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>面积</label>
							<input type="text" id="area" name="area"   value="${prjCheck.area}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>楼盘</label>
							<input type="text" id="address" name="address"   value="${prjCheck.address}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>套餐费</label>
							<input type="text" id="mainSetFee" name="mainSetFee"   value="${prjCheck.mainSetFee}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>远程费</label>
							<input type="text" id="longFee" name="longFee"   value="${prjCheck.longFee}"  placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>套餐外基础增项</label>
							<input type="text" id="allSetAdd" name="allSetAdd"   value="${prjCheck.allSetAdd}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>套餐内减项</label>
							<input type="text" id="allSetMinus" name="allSetMinus"   value="${prjCheck.allSetMinus}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>材料预算</label>
							<input type="text" id="allItemAmount" name="allItemAmount"   value="${prjCheck.allItemAmount}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>基础管理费</label>
							<input type="text" id="allManageFeeBase" name="allManageFeeBase"   value="${prjCheck.allManageFeeBase}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>升级扣项</label>
							<input type="text" id="upgWithHold" name="upgWithHold"   value="${prjCheck.upgWithHold}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>发包总价</label>
							<input type="text" id="baseCtrlAmt" name="baseCtrlAmt"  value="${prjCheck.baseCtrlAmt}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>预扣</label>
							<input type="text" id="withHold" name="withHold"  value="${prjCheck.withHold}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>支出</label>
							<input type="text" id="cost" name="cost"  value="${prjCheck.cost}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>基础支出</label>
							<input type="text" id="baseCost" name="baseCost"   value="${prjCheck.baseCost}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>主材支出(结算)</label>
							<input type="text" id="mainCost" name="mainCost"   value="${prjCheck.mainCost}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>已领</label>
							<input type="text" id="recvFee" name="recvFee"  value="${prjCheck.recvFee}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>已领(定责)</label>
							<input type="text" id="recvFeeFixDuty" name="recvFeeFixDuty"  value="${prjCheck.recvFeeFixDuty}" placeholder="0" readonly="readonly"/>                                             
						</li>
						
						<li class="form-validate">
							<label>质保金</label>
							<input type="text" id="qualityFee" name="qualityFee"  value="${prjCheck.qualityFee}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>意外险</label>
							<input type="text" id="accidentFee" name="accidentFee"  value="${prjCheck.accidentFee}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>应发金额</label>
							<input type="text" id="mustAmount" name="mustAmount"  value="${prjCheck.mustAmount}"  placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>实发金额</label>
							<input type="text" id="realAmount" name="realAmount"  value="${prjCheck.realAmount}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>发包补贴</label>
							<input type="text" id="projectCtrlAdj" name="projectCtrlAdj"  value="${prjCheck.projectCtrlAdj}" placeholder="0" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label for="designFixDutyAmount">设计定责金额</label>
							<div class="input-group">
								<input type="text" class="form-control" id="designFixDutyAmount" name="designFixDutyAmount"   value="${prjCheck.designFixDutyAmount }"  readonly="true" style="width: 121px;"/>
								<button type="button" class="btn btn-system" id="designFixDutyAmountViewBtn" style="font-size: 12px;width: 40px;" >查看</button>
							</div>
						</li>
						<li class="form-validate">
							<label>申请人</label>
							<input type="text" id="appCzy" name="appCzy"   readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label ></span>申请日期</label>
							<input type="text"  id="date" name="date" class="i-date" value="<fmt:formatDate value='${prjCheck.date}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
						</li>
						<li class="form-validate" id="confirmCZY_show">
							<label>确认人</label>
							<input type="text" id="confirmCZY" name="confirmCZY"   readonly="readonly"/>
						</li>
						<li class="form-validate" id="confirmDate_show">
							<label ></span>确认日期</label>
							<input type="text"  id="confirmDate" name="confirmDate" class="i-date" value="<fmt:formatDate value='${prjCheck.confirmDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
						</li>
						<li class="form-validate"><label >发包公式说明</label> <input type="text"
							id="ctrlExprRemarks" name="ctrlExprRemarks" style="width:450px;"
							value="${prjCheck.ctrlExprRemarks}" />
						</li>
						<li class="form-validate"><label>发包计算结果</label> <input type="text"
							id="ctrlExprWithNum" name="ctrlExprWithNum" style="width:450px;"
							value="${prjCheck.ctrlExprWithNum}" />
						</li>
						<li class="form-validate">
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" >${prjCheck.remarks}</textarea>
					    </li>
					</ul>
			</form>
		</div>
	</div>
	
	<div  class="container-fluid" >  
	     <ul class="nav nav-tabs" >
	        <li id="tabPrjWithHold" class=""><a href="#tab_prjWithHold" data-toggle="tab">预扣录入</a></li>
	     <li id="tabPrjCheckDetail" class=""><a href="#tab_prjCheckDetail" data-toggle="tab">基础支出明细</a></li> 
	    </ul>  
	    <div class="tab-content">      
			<div id="tab_prjWithHold"  class="tab-pane fade in active "> 
	         	<jsp:include page="tab_prjWithHold.jsp"></jsp:include>
			</div>
		      <div id="tab_prjCheckDetail"  class="tab-pane fade "> 
	         	<jsp:include page="tab_prjCheckDetail.jsp"></jsp:include>
			</div>   
		</div>	
	</div>	
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">	
//校验函数
$(function() {
	$("#appCzy").openComponent_employee({showValue:'${prjCheck.appCzy}',showLabel:'${prjCheck.appCZYDescr}',readonly:true });
	$("#confirmCZY").openComponent_employee({showValue:'${prjCheck.confirmCzy}',showLabel:'${prjCheck.confirmCZYDescr}',readonly:true });
	
	if("${prjCheck.m_umState}"=="G"){
		$("#btnName").text("结算数据生成");
		$("#confirmCZY_show").hide();
		$("#confirmDate_show").hide();
	}else if("${prjCheck.m_umState}"=="GC"){
		$("#btnName").text("结算完成");
		$("#updateQualityFeeBtn").hide();
		$("#updateProjectCtrlAdjBtn").hide();
	}else if("${prjCheck.m_umState}"=="GB"){
		$("#btnName").text("结算退回");
		$("#updateQualityFeeBtn").hide();
		$("#updateProjectCtrlAdjBtn").hide();
	}else{
		$("#updateQualityFeeBtn").hide();
		$("#updateProjectCtrlAdjBtn").hide();
		$("#btnName").hide();
		$("#saveBtn").css("display","none");
	}
	
 	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
		
			status: {
				validators: {  
		            notEmpty: {  
		            message: '状态不能为空'  
		            }  
	            }  
			},
			phone: {
	        validators: { 
	            digits: { 
	            	message: '手机号码1只能输入数字'  
	            },
	            stringLength:{
               	 	min: 0,
          			max: 11,
               		message:'手机号码长度必须在0-11之间' 
               	}
	        }
      	},
		},
		submitButtons : 'input[type="submit"]'
	});	
	
}); 
//保存操作    	
$("#saveBtn").on("click",function(){
	$("#dataForm").bootstrapValidator("validate");
	if(!$("#dataForm").valid()) {return false;}//表单校验
	$.ajax({
		url : "${ctx}/admin/prjManCheck/goVerifyPrjCheck",
		data : {custCode: "${prjCheck.custCode}",m_umState:"${prjCheck.m_umState}"},
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		dataType: "json",
		type: "post",
		cache: false,
		error: function(){
	       	art.dialog({
				content: '出现异常，无法修改'
			});
	    },
	    success: function(obj){
		   	if(obj.rs){
		   		if ("${prjCheck.m_umState}"!="GB"){
		   			var sMsg="结算"
		   			if( "${prjCheck.m_umState}"=="GC"){
		   				sMsg="结算完成"
		   			}
		        	$.ajax({                           
						url: "${ctx}/admin/prjManCheck/isAbnormalItemApp",//存在未发货、未回退的基础领料单，是否进行结算
						type: "post",
						data: {custCode :"${prjCheck.custCode}",custTypeType:"${prjCheck.custTypeType}" },
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": "请求数据出错"});
					    },
					    success: function(obj){
					    	if(obj.code=="0"){
					    		art.dialog({
									content: "存在未发货、未回退的基础领料单，是否进行"+sMsg,
									width: 200,
									okValue: "确定",
									ok: function () {
										doSave();
									},
									cancelValue: "取消",
									cancel: function () {
										return ;
									}
								});
								
					    	}else{
					    		art.dialog({
									content: "是否进行"+sMsg,
									width: 200,
									okValue: "确定",
									ok: function () {
										doSave();
									},
									cancelValue: "取消",
									cancel: function () {
										return ;
									}
								});	
					    	}
				    	}
					});
				}else{
					art.dialog({
						content: "是否进行结算完成操作",
						width: 200,
						okValue: "确定",
						ok: function () {
							doSave();
						},
						cancelValue: "取消",
						cancel: function () {
							return ;
						}
					});
				}			    		
	    	}else {
	    		art.dialog({
					content: obj.msg
				});
	    	}
		}
	});
});

$("#designFixDutyAmountViewBtn").on("click",function() {
	Global.Dialog.showDialog("designFixDutyAmount",{
		title:"设计定责金额—明细查看",
		url:"${ctx}/admin/prjManCheck/goDesignFixDutyAmount",
		postData:{
			custCode: "${prjCheck.custCode}"
		},		
		height:700,
		width:1000,
	});
});

function doSave(){
    var datas=$("#dataForm").jsonForm();	
	$.ajax({
		url:'${ctx}/admin/prjManCheck/doCheck',
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
	    				if ("${prjCheck.m_umState}"=="G"){
							getPrjCheck();
							loadPrjDetailTab();
							loadPrjWithHoldTab();
							$("#_form_token_uniq_id").val(obj.token.token);
						}else{
							closeWin();
						}
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
function updateQualityFee(){
    Global.Dialog.showDialog("itemPlan_custTypeView",{
		title:"质保金录入",
		url : "${ctx}/admin/prjManCheck/goQualityFeeSave",
		postData:{custCode:"${prjCheck.custCode}",custDescr :"${prjCheck.custDescr}",address:"${prjCheck.address}",m_umState:"A"},
		height: 330,
		width:700,	
		returnFun:function(){
			getQualityFee()	;
	    },
	});
}
function getQualityFee(){
	$.ajax({
		url:'${ctx}/admin/prjManCheck/getQualityFee',
		type: 'post',
		data: {'custCode':"${prjCheck.custCode}"},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	$("#qualityFee").val(obj.QualityFee);
	    	$("#mustAmount").val(obj.MustAmount);
	    	$("#accidentFee").val(obj.AccidentFee);
	    	
	    }
	});
}

function updateProjectCtrlAdj() {
    Global.Dialog.showDialog("updateProjectCtrlAdj", {
	    title: "分包补贴",
	    url: "${ctx}/admin/jcfbkz/goButiePer",
	    postData: {code: "${prjCheck.custCode}"},
	    height: 350,
	    width: 700,
	    returnFun: refreshProjectCtrlAdj
	});
}

function refreshProjectCtrlAdj() {
	$.ajax({
	    url: "${ctx}/admin/prjManCheck/getProjectCtrlAdj",
	    type: 'get',
	    data: {custCode: "${prjCheck.custCode}"},
	    dataType: 'json',
	    cache: false,
	    success: function(data) {
	        $("#projectCtrlAdj").val(data.projectCtrlAdj);
	    }
	});
}

function getPrjCheck(){
	$.ajax({
		url:'${ctx}/admin/prjManCheck/getPrjCheck',
		type: 'post',
		data: {'custCode':"${prjCheck.custCode}",'prjCtrlType':"${prjCheck.prjCtrlType}"},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	$("#allSetAdd").val(obj.AllSetAdd);
	    	$("#allSetMinus").val(obj.AllSetMinus);
	    	$("#allItemAmount").val(obj.AllItemAmount);
	    	$("#allManageFeeBase").val(obj.AllManageFee_Base);
	        $("#upgWithHold").val(obj.UpgWithHold);
	        $("#baseCtrlAmt").val(obj.BaseCtrlAmt);
	        $("#withHold").val(obj.WithHold);
	        $("#cost").val(obj.Cost);
	        $("#recvFee").val(obj.RecvFee);
	        $("#qualityFee").val(obj.QualityFee);
	        $("#mustAmount").val(obj.MustAmount);
	        $("#realAmount").val(obj.RealAmount);
	        $("#accidentFee").val(obj.AccidentFee);
	        $("#ctrlExprRemarks").val(obj.CtrlExprRemarks);
	    	$("#ctrlExprWithNum").val(obj.CtrlExprWithNum);
	    	$("#baseCost").val(obj.BaseCost);
	    	$("#mainCost").val(obj.MainCost);	
	    	$("#recvFeeFixDuty").val(obj.recvFeeFixDuty);
	    }
	});
}

//基础信息查看
$("#JcszxxBtn").on("click",function(){
	/* var recordDate=$("#dataTable_prjWithHold").jqGrid('getGridParam','records');
	if(recordDate!=0){
		Global.Dialog.showDialog("jcszxx",{
			title:"基础收支信息【"+"${prjCheck.address}"+"】",
			url:"${ctx}/admin/itemSzQuery/goJcszxx?code="+"${prjCheck.custCode}",
			height:730,
			width:1350
		});
	}else{
		art.dialog({
			content:"请先检索出相应的信息"
		});
	}
	 */
	Global.Dialog.showDialog("jcszxx",{
			title:"基础收支信息【"+"${prjCheck.address}"+"】",
			url:"${ctx}/admin/itemSzQuery/goJcszxx?code="+"${prjCheck.custCode}",
			height:730,
			width:1350
	});
});
//客户信息查看
$("#CustXxBtn").on("click",function(){
	 Global.Dialog.showDialog("customerXxView",{
		  title:"查看客户【"+"${prjCheck.address}"+"】",
		  url:"${ctx}/admin/customerXx/goDetail?id="+"${prjCheck.custCode}",
		  height:750,
		  width:1430
	});
});
</script>
</body>
</html>

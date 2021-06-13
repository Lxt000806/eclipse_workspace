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
	<script type="text/javascript">
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "   id="saveBtn"><span id="btnName"></span></button>
				<button type="button" class="btn btn-system " id="updateProjectCtrlAdjBtn" onclick="updateProjectCtrlAdj()">发包补贴录入</button>
			    <button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search" target="targetFrame">
				<input type="hidden" name="m_umState" id="m_umState" value="${prjCheck.m_umState}"/>
				<input type="hidden" name="custTypeType" id="custTypeType" value="${prjCheck.custTypeType}"/>
				<house:token></house:token>
				<ul class="ul-form">
						<li class="form-validate">
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode"   value="${prjCheck.custCode}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>客户名称</label>
							<input type="text" id="custDescr" name="custDescr"   value="${prjCheck.custDescr}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>楼盘</label>
							<input type="text" id="address" name="address"   value="${prjCheck.address}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>基础预算</label>
							<input type="text" id="baseFee" name="baseFee"   value="${prjCheck.baseFee}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>基础增减项</label>
							<input type="text" id="baseChg" name="baseChg"   value="${prjCheck.baseChg}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>直接费</label>
							<input type="text" id="baseFeeDirct" name="baseFeeDirct"   value="${prjCheck.baseFeeDirct}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>主材预算</label>
							<input type="text" id="mainAmount" name="mainAmount"   value="${prjCheck.mainAmount}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>主材增减项</label>
							<input type="text" id="chgMainAmount" name="chgMainAmount"  value="${prjCheck.chgMainAmount}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>主材配合费</label>
							<input type="text" id="mainCoopFee" name="mainCoopFee"  value="${prjCheck.mainCoopFee}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>发包总价</label>
							<input type="text" id="baseCtrlAmt" name="baseCtrlAmt"  value="${prjCheck.baseCtrlAmt}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>支出</label>
							<input type="text" id="cost" name="cost"  value="${prjCheck.cost}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>预扣</label>
							<input type="text" id="withHold" name="withHold"  value="${prjCheck.withHold}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>已领</label>
							<input type="text" id="recvFee" name="recvFee"  value="${prjCheck.recvFee}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>已领(定责)</label>
							<input type="text" id="recvFeeFixDuty" name="recvFeeFixDuty"  value="${prjCheck.recvFeeFixDuty}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>质保金</label>
							<input type="text" id="qualityFee" name="qualityFee"  value="${prjCheck.qualityFee}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>意外险</label>
							<input type="text" id="accidentFee" name="accidentFee"  value="${prjCheck.accidentFee}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>应发金额</label>
							<input type="text" id="mustAmount" name="mustAmount"  value="${prjCheck.mustAmount}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>实发金额</label>
							<input type="text" id="realAmount" name="realAmount"  value="${prjCheck.realAmount}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate" id="one">
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
						<li class="form-validate" id="two">
							<label>申请人</label>
							<input type="text" id="appCzy" name="appCzy"  value="${prjCheck.appCzy}"  readonly="readonly"/>
						</li>
						<li class="form-validate" id="three">
							<label ></span>申请日期</label>
							<input type="text"  id="date" name="date" class="i-date" value="<fmt:formatDate value='${prjCheck.date}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
						</li>
						<li class="form-validate" id="confirmCZY_show">
							<label>确认人</label>
							<input type="text" id="confirmCZY" name="confirmCZY"  value="${prjCheck.confirmCZYDescr}"  readonly="readonly"/>
						</li>
						<li class="form-validate" id="confirmDate_show" >
							<label ></span>确认日期</label>
							<input type="text"  id="confirmDate" name="confirmDate" class="i-date" value="<fmt:formatDate value='${prjCheck.confirmDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
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
	        <li id="tabPrjCheckDetail" class=""><a href="#tab_prjCheckDetail" data-toggle="tab">项目经理结算明细</a></li>
	    </ul>  
	    <div class="tab-content">      
			<div id="tab_prjCheckDetail"  class="tab-pane fade in active "> 
	         	<jsp:include page="tab_prjCheckDetail.jsp"></jsp:include>
			</div> 
		</div>	
	</div>	
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">	
//当为项目经理提成领取查看时，隐藏部分显示框
$(function(){
	if("${prjCheck.prjNo}"=="1"){
		$("#one").prop("hidden",true);
		$("#two").prop("hidden",true);
		$("#three").prop("hidden",true);
		$("#confirmCZY_show").prop("hidden",true);
		$("#confirmDate_show").prop("hidden",true);
	}
});
//校验函数
$(function() {
	if("${prjCheck.m_umState}"=="G"){
		$("#btnName").text("结算数据生成");
		$("#confirmCZY_show").hide();
		$("#confirmDate_show").hide();
	}else if("${prjCheck.m_umState}"=="GC"){
		$("#btnName").text("结算完成");
		$("#updateProjectCtrlAdjBtn").hide();
	}else if("${prjCheck.m_umState}"=="GB"){
		$("#btnName").text("结算退回");
		$("#updateProjectCtrlAdjBtn").hide();
	}else{
		$("#btnName").hide();
		$("saveBtn").hide();
		$("#saveBtn").css("display","none");
		$("#updateProjectCtrlAdjBtn").hide();
	}
	$("#appCzy").openComponent_employee({showValue:'${prjCheck.appCzy}',showLabel:'${prjCheck.appCZYDescr}',readonly:true });  	
	$("#confirmCZY").openComponent_employee({showValue:'${prjCheck.confirmCzy}',showLabel:'${prjCheck.confirmCZYDescr}',readonly:true });  	
 	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			custCode: {
				validators: {  
		            notEmpty: {      
		            message: '客户编号不能为空'  
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
						data: {custCode :"${prjCheck.custCode}",prjCtrlType:"${prjCheck.prjCtrlType}" },
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
	Global.Dialog.showDialog("designFixDutyAmountView",{
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
							$("#_form_token_uniq_id").val(obj.token.token);
							getPrjCheck();
							loadPrjDetailTab();
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
	    	$("#baseChg").val(obj.BaseChg);
	    	$("#baseCtrlAmt").val(obj.BaseCtrlAmt);
	    	$("#cost").val(obj.Cost);
	    	$("#withHold").val(obj.WithHold);
	    	$("#mainCoopFee").val(obj.MainCoopFee);
	    	$("#recvFee").val(obj.RecvFee);
	    	$("#qualityFee").val(obj.QualityFee);
	    	$("#accidentFee").val(obj.AccidentFee);
	    	$("#mustAmount").val(obj.MustAmount);
	    	$("#realAmount").val(obj.RealAmount);
	    	$("#recvFeeFixDuty").val(obj.recvFeeFixDuty);
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

</script>
</body>
</html>

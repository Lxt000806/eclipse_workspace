<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>合同管理-创建合同</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builderNum.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function() {
	$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			excluded:[":disabled"],
			fields: {  
				layout:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '户型不能为空'  
			            }
			        }  
			     },
				concreteAddress:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '具体地址不能为空'  
			            }
			        }  
			     },
			     partyAname:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '甲方姓名不能为空'  
			            }
			        }  
			     },
			     partyAid:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '甲方身份证不能为空'  
			            }
			        }  
			     },
			     partyAphone:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '甲方手机不能为空'  
			            },
			            digits: {
                            message: '甲方手机只能输入数字'
                        },
                        stringLength: {
			                min: 11,
			                max: 11,
			                message: '手机号必须为11位'
			            },
			        }  
			     },
			     conMode:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '承包方式不能为空'  
			            }
			        }  
			     },
			     contractDay:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '合同工期不能为空'  
			            }
			        }  
			     },
			     isFutureCon:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '期房合同不能为空'  
			            }
			        }  
			     },
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});

$(function(){
	$("#position").attr("disabled",true);
	
	//提示词
	$("#partyAphone_span").popover({trigger:"hover"});
	
	//右上角关闭事件
	replaceCloseEvt("custContractCreate",closeWin);
	
	//审核和查看时，设置表单失效
	if("${data.m_umState}" == "C" || "${data.m_umState}" == "V" ){
		disabledForm("dataForm", true);
	}
	
	if("${data.isDesigner}" == "true"){
		$("#partyAphone_req").hide();
	}
	
	$("#saveBtn").on("click",function(){
		var url = "${ctx}/admin/custContract/";
		if("${data.m_umState}" == "A"){
			url += "doCreate";
		}else{
			url += "doUpdate";
		}
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var datas = $("#dataForm").jsonForm();
		console.log(datas.beginDate);
		if(datas.position == ""){
			art.dialog({
				content: "创建合同失败，请先到造价分析模块填写设计费标准",
			});
			return;
		}
		$.ajax({
			url:url,
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
	});
	
	$("#passBtn").on("click",function(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var datas = $("#dataForm").jsonForm();
		art.dialog({
   			title: "请输入审核说明",
   			padding: "0",
   			width: "250px",
   			height: "250px",
   			content: "<div style=\"padding-left:5px;padding-top:5px;padding-right:5px;\"><textarea id=\"confirmRemarks\" style=\"width:500px;height:250px\"></textarea></div>",
   			lock: true,
   			ok: function(){
   				var waitDialog=art.dialog({
					content: "审核中，请稍候...", 
					lock: true,
					esc: false,
					unShowOkButton: true
				});
   				datas.confirmRemarks = $("#confirmRemarks").val();
   				$.ajax({
					url:'${ctx}/admin/custContract/doConfirmPass',
					type: 'post',
					data: datas,
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				    },
				    success: function(obj){
				    	waitDialog.close();
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
   			cancel: function(){}
   		});
	});
	
	$("#cancelBtn").on("click",function(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var datas = $("#dataForm").jsonForm();
		var waitDialog=art.dialog({
			content: "审核取消中，请稍候...", 
			lock: true,
			esc: false,
			unShowOkButton: true
		});
		art.dialog({
   			title: "请输入审核说明",
   			padding: "0",
   			width: "250px",
   			height: "250px",
   			content: "<div style=\"padding-left:5px;padding-top:5px;padding-right:5px;\"><textarea id=\"confirmRemarks\" style=\"width:500px;height:250px\"></textarea></div>",
   			lock: true,
   			ok: function(){
   				var waitDialog=art.dialog({
					content: "审核中，请稍候...", 
					lock: true,
					esc: false,
					unShowOkButton: true
				});
   				datas.confirmRemarks = $("#confirmRemarks").val();
   				$.ajax({
					url:'${ctx}/admin/custContract/doConfirmCancel',
					type: 'post',
					data: datas,
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				    },
				    success: function(obj){
				    	waitDialog.close();
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
   			cancel: function(){}
   		});
	});
});
function viewDoc(){
	Global.Dialog.showDialog("doPrint",{ 
 		title:"预览合同",
 		url:"${ctx}/admin/custContract/doPrint?#toolbar=0",
 		postData: {
 		    pk:"${data.pk}",m_umState:"C"
 		},
 		height:755,
 		width:937,
 		shade:true
   });	
}
</script>
</head>
	<body>
<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
			      		<c:if test="${data.m_umState == 'A' || data.m_umState == 'M'}">
				      		<button type="button" class="btn btn-system " id="saveBtn">
								<span>保存</span>
							</button>
			      		</c:if>
			      		<c:if test="${data.m_umState == 'C' }">
				      		<button type="button" class="btn btn-system " id="passBtn">
								<span>审核通过</span>
							</button>
							<button type="button" class="btn btn-system " id="cancelBtn">
								<span>审核取消</span>
							</button>
			      		</c:if>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin()">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="pk" name="pk" style="width:160px;" value="${data.pk}"/>
						<ul class="ul-form">
							<span style="font-weight:bold;">楼盘信息:</span>
							<hr style="height: 2px;margin:10px 0px 5px 0px;">
							<div class="validate-group row" >
								<li>
									<label>客户编号</label>
									<input type="text" id="code" name="code" style="width:160px;" value="${data.code }"  readonly="readonly"/>
								</li>
								<li >
									<label>客户状态</label>
									<house:xtdm id="status" dictCode="CUSTOMERSTATUS" disabled="true" value="${data.status }"></house:xtdm>
								</li>
								<li>
									<label>楼盘</label>
									<input type="text" style="width:451px;" id="address" name="address"  value="${data.address }" readonly/>
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label><span class="required">*</span>户型</label>
									<house:xtdm id="layout" dictCode="LAYOUT" value="${data.layout }" ></house:xtdm>
								</li>
								<li>
									<label>面积</label>
									<input type="text"  id="area" name="area" value="${data.area }" readonly/>
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label class="control-textarea"><span class="required">*</span>具体地址</label>
									<textarea id="concreteAddress" name="concreteAddress">${data.concreteAddress }</textarea>
								</li>
							</div><br>
							<span style="font-weight:bold;margin-top:10px">合同基本信息:</span>
							<hr style="height: 2px;margin:10px 0px 5px 0px">
							<div class="validate-group row" >
								<li class="form-validate">
									<label><span class="required">*</span>甲方姓名</label>
									<input type="text" style="width:160px;" id="partyAname" name="partyAname" value="${data.partyAname }"/>
								</li>
								<li class="form-validate">
									<label>乙方代表</label>
									<input type="text" style="width:160px;" id="partBrepName" name="partBrepName" value="${data.partBrepName }" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row" >
							    <li class="form-validate" >
									<label><span class="required" id="partyAphone_req">*</span>甲方手机</label>
									<input type="text" id="partyAphone" name="partyAphone" value="${data.partyAphone}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
									<span class="glyphicon glyphicon-question-sign" id = "partyAphone_span" 
										data-container="body"  
										data-content="出于信息保护，请核对并修改最后两位" 
										data-placement="right" 
										style="font-size: 15px;color:rgb(25,142,222);margin-left: 0px">
									</span>
								</li>
								<li class="form-validate">
									<label style="width:80px;">乙方单位</label>
									<input type="text" style="width:160px;" id="partBdescr" name="partBdescr" value="${data.partBdescr }" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label><span class="required">*</span>甲方身份证</label>
									<input type="text" id="partyAid" name="partyAid" style="width:160px;" value="${data.partyAid }" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
								</li>
							</div><br>
							<span style="font-weight:bold;margin-top:10px">合同交易信息:</span>
							<hr style="height: 2px;margin:10px 0px 5px 0px">
							<div class="validate-group row" >
								<li class="form-validate">
									<label><span class="required">*</span>承包方式</label>
									<house:xtdm id="conMode" dictCode="CONTRACTMODE" value="${data.conMode }" disabled="${data.custTypeType=='2'?'true':'false' }"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>期房合同</label>
									<house:xtdm id="isFutureCon" dictCode="YESNO" value="${data.isFutureCon }" disabled="${data.isInitSign=='1'?'true':'false' }"></house:xtdm>                     
								</li>
							</div> 
							<div class="validate-group row" >
								<li >
									<label>开工时间</label>
									<input type="text" id="beginDate" name="beginDate" class="i-date" 
										value="<fmt:formatDate value='${data.beginDate}' pattern='yyyy-MM-dd'/>"  
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>合同工期</label>
									<input type="text" id="contractDay" name="contractDay" value="${data.contractDay }" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
								</li>
							</div>
							<div class="row" >
								<li>
									<label><span class="required">*</span>设计费标准</label> 
									<house:dict id="position" dictCode=""
										sql="select  rtrim(cast(a.DesignFee as varchar(20))) + ' ' + b.Desc2 fd, 
												rtrim(a.DesignFee) DesignFee
											from tDesignFeeSd a
											left join tPosition b on a.Position = b.Code
											order by a.DispSeq "
										sqlValueKey="DesignFee" sqlLableKey="fd" value="${data.position }"></house:dict>
								</li>
								<li>
									<label>工程造价</label>
									<input type="text" id="contractFee" name="contractFee" value="${data.contractFee }" readonly/>
								</li>
							</div>
							<div class="row" >
								<li>
									<label>设计费总额</label>
									<input type="text" id="designFee" name="designFee" value="${data.designFee }" readonly/>
								</li>
								<c:if test="${data.m_umState == 'C' }">
									<li>
										<label>合同文件</label>
										<a href="#" style="color:blue" onclick="viewDoc()">${data.docDescr }</a>
									</li>
								</c:if>
							</div>
						</ul>
				</form>
				</div>
			</div>
		</div>
	</body>	
</html>

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
	<title>资源客户管理增加</title>
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
			fields: {  
				source:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '客户来源不能为空'  
			            }
			        }  
			     },
			     descr:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '客户姓名不能为空'  
			            }
			        }  
			     },
			     mobile1:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '手机号不能为空'  
			            }
			        }  
			     },
			     openComponent_employee_businessMan:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '跟单人员不能为空'  
			            }
			        }  
			     },
			     custKind:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '客户分类不能为空'  
			            }
			        }  
			     },
			     constructType:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '客户类别不能为空'  
			            }
			        }  
			     },
			     custResStat:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '资源客户状态不能为空'  
			            }
			        }  
			     },
			     netChanel:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '渠道不能为空'  
			            }
			        }  
			     },
			     resrCustPoolNo:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '线索池不能为空'  
			            }
			        }  
			     },
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	
	$("#builderNum").openComponent_builderNum({callBack:updateAddress});
	$("#builderCode").openComponent_builder({callBack:getData,condition:{department2:'${resrCust.department2}'}});
	$("#businessMan").openComponent_employee({callBack:validateRefresh_choice,showValue:'${resrCust.businessMan}',showLabel:'${resrCust.businessManDescr}'});	
	$("#crtCzy").openComponent_employee({showValue:'${resrCust.businessMan}',showLabel:'${resrCust.businessManDescr}',readonly:true});	
	$("#crtCZYDept").openComponent_department({showValue:'${resrCust.crtCZYDept}',showLabel:'${resrCust.crtCzyDeptDescr}',readonly:true});	
	$("#openComponent_builder_builderCode").attr("readonly",true);
	Global.LinkSelect.initSelect("${ctx}/admin/ResrCust/sourceByAuthority","source","netChanel",null,false,true,false,true);
	
	chgResrCustPoolNo();
});

$(function(){
	$("#saveBtn").on("click",function(){
	
		if ($("#mobile1").val() === '' && $("#email2").val() === '') {
            art.dialog({
           		content: "请至少输入一个手机号或微信号"
            });
            return;
        }
        
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:'${ctx}/admin/ResrCust/doSave',
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
});
function validateRefresh(){
		$('#dataForm').data('bootstrapValidator')
		                   .updateStatus('openComponent_employee_businessMan', 'NOT_VALIDATED',null)  
		                   .validateField('openComponent_employee_businessMan')
}

function validateRefresh_choice(){
	 $('#dataForm').data('bootstrapValidator')
                 .updateStatus('openComponent_employee_businessMan', 'NOT_VALIDATED',null)  
                 .validateField('openComponent_employee_businessMan');                    
}

function fillData(ret){
	validateRefresh_choice();
}

function getData(data){
	if (!data) return;
	$("#builderNum").openComponent_builderNum({callBack:updateAddress,condition:{builderCode:data.code}});
	$('#address').val(data.descr);
}
	 
function updateAddress(data){
	var builderDescr=data["builderdescr"];	
	var builderNum=data["BuilderNum"];
	var address=$("#address").val();
	var builder=$("#openComponent_builder_builderCode").val().split("|")[1];
	if(builderDescr && builderNum){
		$("#address").val(builderDescr+builderNum);
	}else{
		if(builder){
			$("#address").val(builder+$("#openComponent_builderNum_builderNum").val());
		}
	}
}

function changeNetChanel(){
	if ($("#netChanel").val()=='15'){
		$("#extraOrderNo").prev().children().eq(0).removeClass("hidden");
		$("#dataForm").bootstrapValidator("removeField","extraOrderNo");
		$("#dataForm").bootstrapValidator("addField", "extraOrderNo", {  
            validators: {  
                notEmpty: {  
                    message: '外部订单编号不能为空'  
                } 
            }  
        });
	}else{
		$("#dataForm").bootstrapValidator("removeField","extraOrderNo");
		$("#extraOrderNo").next("small").remove();
		$("#extraOrderNo").prev().children().eq(0).addClass("hidden");
	}
}

function chgResrCustPoolNo(){
	var resrCustPoolNo = $("#resrCustPoolNo").val();
	if(resrCustPoolNo == ""){
		$("#custResStatLi").show();
		$("#businessManLi").show();
		return;
	}
	$.ajax({
		url:"${ctx}/admin/ResrCust/getPoolType?resrCustPoolNo=" + resrCustPoolNo,
		type:'post',
		data:{},
		dataType:'json',
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": '出错~'});
		},
		success:function(obj){
			if (obj){
				if($.trim(obj.dispatchRule)!="0"){ 
					$("#custResStatLi").hide();
					$("#businessManLi").hide();
					$("#businessMan").val("");
					$("#custResStat").val("0");
					$("#openComponent_employee_businessMan").val("");
				} else {
					$("#custResStatLi").show();
					$("#businessManLi").show();
				}
			}
		}
	});
}
</script>
</head>
	<body>
<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
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
					<input type="hidden" id="department2" name="department2" style="width:160px;" value="${resrCust.department2 }"/>
						<ul class="ul-form">
							<span style="font-weight:bold;">基本信息:</span>
							<hr style="height: 2px;margin:10px 0px 5px 0px;">
							<div class="validate-group row" >
								<li>
									<label>客户编号</label>
									<input type="text" id="code" name="code" style="width:160px;" value="${resrCust.code }" placeholder="保存自动生成" readonly="readonly"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>客户姓名</label>
									<input type="text" id="descr" name="descr" style="width:160px;" value="${resrCust.descr }"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>客户类别</label>
									<house:xtdm id="constructType" dictCode="CUSTCLASS" ></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>客户分类</label>
									<house:xtdm id="custKind" dictCode="CUSTKIND" unShowValue ="0"></house:xtdm>      
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label><span class="required">*</span>手机号码</label>
									<input type="text" id="mobile1" name="mobile1" style="width:160px;" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" value="${resrCust.mobile1}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>客户来源</label> 
									<select id="source" name="source" ></select>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>渠道</label> 
									<select id="netChanel" name="netChanel" onchange="changeNetChanel()"></select>
								</li>
								<li class="form-validate">
                                    <label><span class="required hidden">*</span>外部订单编号</label>
                                    <input type="text" id="extraOrderNo" name="extraOrderNo" style="width:160px;" value="${resrCust.extraOrderNo}"/>
                                </li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label>手机号码2</label>
									<input type="text" id="mobile3" name="mobile2" style="width:160px;" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" value="${resrCust.mobile2}"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>微信/Email</label>
									<input type="text" id="email2" name="email2" style="width:160px;" value="${resrCust.email2}"/>
								</li>
								<li>
									<label>性别</label>
									<house:xtdm id="gender" dictCode="GENDER" value="${resrCust.gender}"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>线索池</label>
									<house:dict id="resrCustPoolNo" dictCode="" sqlValueKey="No" sqlLableKey="Descr"
									    sql="select a.No,a.Descr from tResrCustPool a
									    where (exists (select 1 from tResrcustPoolEmp in_a where in_a.ResrCustPoolNo = a.No and in_a.CZYBH = '${resrCust.czybh }')
									    or exists (select 1 from tCZYBM in_b where in_b.DefaultPoolNo = a.No and in_b.CZYBH = '${resrCust.czybh }')
									    or a.Descr = '默认线索池') and expired = 'F'" value="${resrCust.defPoolNo }" onchange="chgResrCustPoolNo()"></house:dict>
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label class="control-textarea">备注</label>
									<textarea id="remark" name="remark"></textarea>
								</li>
							</div><br>
							<span style="font-weight:bold;margin-top:10px">楼盘信息:</span>
							<hr style="height: 2px;margin:10px 0px 5px 0px">
							<div class="validate-group row" >
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address"  value="${resrCust.address }"/>
								</li>
								<li class="form-validate">
									<label>面积</label>
									<input type="text" style="width:160px;" id="area" name="area" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'').replace('.','');" value="${resrCust.area }"/>
									<span style="position: absolute;left:290px;width: 30px;top:5px;">平方</span>
								</li>
								<li class="form-validate">
									<label>项目名称</label>
									<input type="text" id="builderCode" name="builderCode" style="width:160px;" value="${resrCust.builderCode } "/>
								</li>
								<li class="form-validate">
									<label>楼栋号</label>
									<input type="text" style="width:160px;" id="builderNum" name="builderNum" value="${resrCust.builderNum }" />
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label>户型</label>
									<house:xtdm id="layout" dictCode="LAYOUT" value="${resrCust.layout }" ></house:xtdm>
								</li>
								<li>
									<label>楼盘性质</label>
									<house:xtdm id="addrProperty" dictCode="ADDRPROPERTY" ></house:xtdm>                     
								</li>
								<li>
							   		<label>区域</label>
								    <house:dict id="regionCode" dictCode=""  sqlValueKey="Code" sqlLableKey="Descr" sql="select Code,Code+' '+Descr Descr from tRegion a where a.expired='F' " ></house:dict>
								</li>
								<li class="form-validate">
									<label >装修状态</label>
									<house:xtdm id="status" dictCode="RESRCUSTSTS" value="1"></house:xtdm>                     
								</li>
							</div><br>
							<span style="font-weight:bold;margin-top:10px">跟踪信息:</span>
							<hr style="height: 2px;margin:10px 0px 5px 0px">
							<div class="validate-group row" >
								<li class="form-validate" id="custResStatLi">
									<label><span class="required">*</span>资源客户状态</label>
									<house:xtdm id="custResStat" dictCode="CUSTRESSTAT"  unShowValue="3,4,5,6,7,8"></house:xtdm>                     
								</li>
								<li class="form-validate" id="businessManLi">
									<label><span class="required">*</span>跟单人员</label>
									<input type="text" id="businessMan" name="businessMan" style="width:160px;" value="${resrCust.businessMan }" />
								</li>
								<li class="form-validate">
									<label>派单时间</label>
									<input type="text" id="dispatchDate" name="dispatchDate" class="i-date" value="<fmt:formatDate value='${resrCust.crtDate}' pattern='yyyy-MM-dd HH:mm:ss' />"/>
								</li>
								<li class="form-validate">
									<label>创建人</label>
									<input type="text" id="crtCzy" name="crtCzy" style="width:160px;" value="${resrCust.crtCzy }"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label>创建人部门</label>
									<input type="text" id="crtCZYDept" name="crtCZYDept" style="width:160px;" value="${resrCust.crtCZYDept }"/>
								</li>
								<li class="form-validate">
									<label>创建时间</label>
									<input type="text" id="crtDate" name="crtDate" class="i-date" value="<fmt:formatDate value='${resrCust.crtDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly />
								</li>
							</div>
						</ul>
				</form>
				</div>
			</div>
		</div>
	</body>	
</html>

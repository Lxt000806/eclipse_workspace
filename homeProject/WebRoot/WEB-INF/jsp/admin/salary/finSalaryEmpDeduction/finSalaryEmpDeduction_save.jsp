<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>财务扣款管理--新增</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_salaryEmp.js?v=${v}" type="text/javascript"></script>
    <style>
        .form-search .ul-form li label {
            width: 120px;
        }
    </style>
</head>
<body>

<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <c:if test="${finSalaryEmpDeduction.m_umState!='V' }">
	                <button type="button" class="btn btn-system" onclick="save()">保存</button>
	            </c:if>
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="dataForm" class="form-search" target="targetFrame">
            	 <input  type="hidden" id="pk" name="pk" value="${finSalaryEmpDeduction.pk }"/>
            	 <input type="hidden" id="deductDate" name="deductDate" class="i-date" value="<fmt:formatDate value='${finSalaryEmpDeduction.deductDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label><span class="required">*</span>人员</label>
                            <input  type="text" id="salaryEmp" name="salaryEmp"/>
                        </li>
                    </div>
                    <div class="validate-group row ">
						<li>
							<label>一级部门</label>
							<select id="department1" name="department1" ></select>
						</li>
					</div>
					<div class="validate-group row ">
						<li>
							<label>二级部门</label>
							<select id="department2" name="department2" ></select>
						</li>
					</div>
                    <div class="validate-group row">
                        <li class="form-validate">
							<label><span class="required">*</span>扣款科目</label>	
							<house:dict id="deductType2" dictCode=""
								sql="select code, code+' '+descr descr from tSalaryDeductType2 where Expired='F' and DeductType1='1' order by code  "
								sqlLableKey="descr" sqlValueKey="code" value="${finSalaryEmpDeduction.deductType2}"  />
						</li>	
					</div>
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label><span class="required">*</span>金额</label>
                            <input type="text" id="amount" name="amount" value="${finSalaryEmpDeduction.amount }" />
                        </li>
                    </div>
                    <div class="validate-group row">
						<li class="form-validate ">
							<label ><span class="required">*</span>薪酬月份</label>
							<house:dict id="salaryMon" dictCode="" sql="select salaryMon from tSalaryMon a where a.Expired='F' order by salaryMon" 
							 sqlValueKey="salaryMon" sqlLableKey="salaryMon" value="${finSalaryEmpDeduction.salaryMon}"  ></house:dict>							
						</li>
                    </div>
                     <div class="validate-group row">
						<li class="form-validate ">
							<label>薪酬方案类型</label>
							 <house:dict id="salarySchemeType" dictCode="" sql="select * from tSalarySchemeType where expired = 'F'"
								sqlValueKey="Code" sqlLableKey="Descr" value="${finSalaryEmpDeduction.salarySchemeType}"  >
							 </house:dict>
						</li>
                    </div>
                    <div class="validate-group row">
                        <li class="form-validate ">
                            <label class="control-textarea"><span class="required">*</span>扣款内容</label>
                            <textarea type="text" id="remarks" name="remarks" style="width:300px">${finSalaryEmpDeduction.remarks }</textarea>
                        </li>
                    </div>
                    <div class="validate-group row">
                        <li >
                            <label>财务编码</label>
                            <input type="text" id="financialCode" name="financialCode" value="${salaryEmp.financialCode }" readonly/>
                        </li>
                    </div>
                </ul>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
    	$("#salaryEmp").openComponent_salaryEmp({
    		showValue:"${salaryEmp.empCode}",
			showLabel:"${salaryEmp.empName}",
			callBack:function(data){
				$("#financialCode").val(data.financialcode);
				Global.LinkSelect.setSelect({firstSelect:"department1",
							firstValue:data.department1,
							secondSelect:"department2",
							secondValue:data.department2,
							});
				validateRefresh("openComponent_salaryEmp_salaryEmp","","dataForm");//刷新校验
			}
    	});
    	$("#openComponent_salaryEmp_salaryEmp").attr("readonly",true);
    	$("#salaryMon").attr("disabled",true);
    	if("${finSalaryEmpDeduction.m_umState}"!="A"){
	    	$("#deductType2").attr("disabled",true);
	    	$("#salaryEmp").setComponent_salaryEmp({disabled:true});
	    	$("#salarySchemeType").attr("disabled",true);	
        }
    	Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");
		Global.LinkSelect.setSelect({firstSelect:"department1",
							firstValue:"${salaryEmp.department1}",
							secondSelect:"department2",
							secondValue:"${salaryEmp.department2}",
							});
		$("#department1,#department2").attr("disabled",true);
        $("#dataForm").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
            	openComponent_salaryEmp_salaryEmp: {
                    validators: {
                        notEmpty: {message: '人员不能为空'}
                    }
                },
                salaryEmp: {
                    validators: {
                        notEmpty: {message: '人员不能为空'}
                    }
                },
                deductType2: {
                    validators: {
                        notEmpty: {message: '扣款科目不能为空'},
                    }
                },
                amount: {
                    validators: {
                        notEmpty: {message: '金额不能为空'},
                        numeric: {message: '金额须为数字'}
                    }
                },
                salaryMon: {
                    validators: {
                        notEmpty: {message: '薪酬月份不能为空'},
                    }
                },
                salarySchemeType: {
                    validators: {
                        notEmpty: {message: '薪酬方案类型不能为空'},
                    }
                },
                remarks: {
                    validators: {
                        notEmpty: {message: '扣款内容不能为空'},
                    }
                },
                
            }
        });
        
    });

    function save() {
        var bootstrapValidator = $("#dataForm").data('bootstrapValidator');
        bootstrapValidator.validate();
        if (!bootstrapValidator.isValid()) return;

        var data = $("#dataForm").jsonForm();
        
        var requestMap="doSave";
        if("${finSalaryEmpDeduction.m_umState}"=="M"){
        	requestMap="doUpdate";
        }
        if(getSalaryStatusCtrl($("#salaryMon").val(), $("#salarySchemeType").val())){
        	art.dialog({
    			content:$("#salaryMon").val() +"的" + $("#salarySchemeType option:selected").text() + "已结算，请重新选择薪酬月份或薪酬方案类型"
    		});
            return
        }
       
        $.ajax({
            url: "${ctx}/admin/finSalaryEmpDeduction/"+requestMap,
            type: "POST",
            data: data,
            dataType: "json",
            error: function(obj) {
                showAjaxHtml({
                    "hidden": false,
                    "msg": "保存数据出错~"
                });
            },
            success: function(obj) {
                if (obj.rs) {
                    art.dialog({
                        content: obj.msg,
                        time: 1000,
                        beforeunload: function() {
                            closeWin(true)
                        }
                    });
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token)
                    art.dialog({content: obj.msg})
                }
            }
        })

    }
   
    function getSalaryStatusCtrl(salaryMon, salarySchemeType){
    	var hasCheckStatus = false;
    	$.ajax({
    		url:"${ctx}/admin/salaryCalc/getSalaryStatusCtrl",
    		type: "post",
    		data: {salaryMon:salaryMon,salarySchemeType:salarySchemeType, status:'3'},
    		dataType: "json",
    		cache: false,
    		async:false, 
    		error: function(obj){
    			showAjaxHtml({"hidden": false, "msg": "数据出错~"});
    	    },
    	    success: function(obj){
    	    	//薪酬状态为已结算，只能查询信息
    	    	if(obj.schemeInfo.indexOf("已结算") != -1){
    	    		hasCheckStatus = true;
    			}
    	    }
    	});
    	return hasCheckStatus;
    }

</script>
</body>
</html>

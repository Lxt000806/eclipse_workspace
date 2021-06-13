<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>加班信息管理--新增</title>
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
                <c:if test="${salaryEmpOverTime.m_umState!='V' }">
	                <button type="button" class="btn btn-system" onclick="save()">保存</button>
	            </c:if>
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="dataForm" class="form-search" target="targetFrame">
            	 <input  type="hidden" id="pk" name="pk" value="${salaryEmpOverTime.pk }"/>
            	 <input  type="hidden" id="registerCzy" name="registerCzy" value="${salaryEmpOverTime.registerCzy }"/>
            	 <input type="hidden" id="registerDate" name="registerDate" class="i-date" value="<fmt:formatDate value='${salaryEmpOverTime.registerDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
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
                    	<li >
							<label >岗位类别</label>
							<house:dict id="posiClass" dictCode="" sql="select pk,cast(pk as nvarchar(20))+' '+descr descr from tSalaryPosiClass a where a.Expired='F' " 
							 sqlValueKey="pk" sqlLableKey="descr" value="${salaryEmp.posiClass}" ></house:dict>							
						</li>
					</div>
                    <div class="validate-group row">
                    	<c:if test="${salaryEmpOverTime.m_umState=='A' }">
							<li class="form-validate ">
								<label ><span class="required">*</span>薪酬月份</label>
								<house:dict id="salaryMon" dictCode="" sql="select salaryMon from tSalaryMon a where a.Expired='F' and a.Status<>'3'
										and not exists(select 1 from tSalaryStatusCtrl in_a where in_a.SalaryMon=a.SalaryMon and in_a.status ='3')  order by salaryMon" 
								 sqlValueKey="salaryMon" sqlLableKey="salaryMon" value="${salaryEmpOverTime.salaryMon}"  ></house:dict>							
							</li>
						</c:if>
						<c:if test="${salaryEmpOverTime.m_umState!='A' }">
							<li class="form-validate ">
								<label ><span class="required">*</span>薪酬月份</label>
								<house:dict id="salaryMon" dictCode="" sql="select salaryMon from tSalaryMon a where a.Expired='F' order by salaryMon" 
								 sqlValueKey="salaryMon" sqlLableKey="salaryMon" value="${salaryEmpOverTime.salaryMon}"  ></house:dict>							
							</li>
						</c:if>
                    </div>
                     <div class="validate-group row">
                        <li class="form-validate">
                            <label><span class="required">*</span>加班次数</label>
                            <input type="number" id="times" name="times" value="${salaryEmpOverTime.times }" />
                        </li>
                    </div>
                    <div class="validate-group row">
                        <li >
                            <label class="control-textarea">备注</label>
                            <textarea type="text" id="remarks" name="remarks" style="width:300px">${salaryEmpOverTime.remarks }</textarea>
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
				$("#posiClass").val(data.posiclass);
				Global.LinkSelect.setSelect({firstSelect:"department1",
							firstValue:data.department1,
							secondSelect:"department2",
							secondValue:data.department2,
							});
				validateRefresh("openComponent_salaryEmp_salaryEmp","","dataForm");//刷新校验
			}
    	});
    	$("#openComponent_salaryEmp_salaryEmp").attr("readonly",true);
    	if("${salaryEmpOverTime.m_umState}"!="A"){
	    	$("#salaryMon").attr("disabled",true);
	    	$("#salaryEmp").setComponent_salaryEmp({disabled:true});
        }
    	Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");
		Global.LinkSelect.setSelect({firstSelect:"department1",
							firstValue:"${salaryEmp.department1}",
							secondSelect:"department2",
							secondValue:"${salaryEmp.department2}",
							});
		$("#department1,#department2,#posiClass").attr("disabled",true);
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
                times: {
                    validators: {
                        notEmpty: {message: '加班次数不能为空'},
                    }
                },
                salaryMon: {
                    validators: {
                        notEmpty: {message: '薪酬月份不能为空'},
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
        
        if (parseFloat(data.times)<=0) {
            art.dialog({
          		content: "加班次数必须大于0"
            });
            return
        }
        var requestMap="doSave";
        if("${salaryEmpOverTime.m_umState}"=="M"){
        	requestMap="doUpdate";
        }
        $.ajax({
            url: "${ctx}/admin/salaryEmpOverTime/"+requestMap,
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
    
</script>
</body>
</html>

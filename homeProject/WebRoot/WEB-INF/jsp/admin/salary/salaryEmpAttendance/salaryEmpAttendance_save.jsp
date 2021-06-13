<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>考勤信息管理--新增</title>
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
                <c:if test="${salaryEmpAttendance.m_umState!='V' }">
	                <button type="button" class="btn btn-system" onclick="save()">保存</button>
	            </c:if>
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="dataForm" class="form-search" target="targetFrame">
            	 <input  type="hidden" id="pk" name="pk" value="${salaryEmpAttendance.pk }"/>
            	 <input  type="hidden" id="importCzy" name="importCzy" value="${salaryEmpAttendance.importCzy }"/>
            	 <input type="hidden" id="importDate" name="importDate" class="i-date" value="<fmt:formatDate value='${salaryEmpAttendance.importDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label style="width:145px"><span class="required">*</span>人员</label>
                            <input  type="text" id="salaryEmp" name="salaryEmp"/>
                        </li>
                        <li >
                            <label style="width:145px">身份证号</label>
                            <input type="text" id="idnum" name="idnum" value="${salaryEmp.idnum }" readonly/>
                        </li>
                    </div>
                    <div class="validate-group row ">
						<li>
							<label style="width:145px">一级部门</label>
							<select id="department1" name="department1" ></select>
						</li>
						<li>
							<label style="width:145px">二级部门</label>
							<select id="department2" name="department2" ></select>
						</li>
					</div>
                    <div class="validate-group row">
                    	<li >
							<label style="width:145px">岗位类别</label>
							<house:dict id="posiClass" dictCode="" sql="select pk,cast(pk as nvarchar(20))+' '+descr descr from tSalaryPosiClass a where a.Expired='F' " 
							 sqlValueKey="pk" sqlLableKey="descr" value="${salaryEmp.posiClass}" ></house:dict>							
						</li>
						<c:if test="${salaryEmpAttendance.m_umState=='A' }">
							<li class="form-validate ">
								<label style="width:145px"><span class="required">*</span>薪酬月份</label>
								<house:dict id="salaryMon" dictCode="" sql="select salaryMon from tSalaryMon a where a.Expired='F' and a.Status<>'3'
								 and not exists(select 1 from tSalaryStatusCtrl in_a where in_a.SalaryMon=a.SalaryMon and in_a.status = '3' ) order by salaryMon " 
								 sqlValueKey="salaryMon" sqlLableKey="salaryMon" value="${salaryEmpAttendance.salaryMon}"  ></house:dict>							
							</li>
						</c:if>
						<c:if test="${salaryEmpAttendance.m_umState!='A' }">
							<li class="form-validate ">
								<label style="width:145px"><span class="required">*</span>薪酬月份</label>
								<house:dict id="salaryMon" dictCode="" sql="select salaryMon from tSalaryMon a where a.Expired='F' order by salaryMon" 
								 sqlValueKey="salaryMon" sqlLableKey="salaryMon" value="${salaryEmpAttendance.salaryMon}"  ></house:dict>							
							</li>
						</c:if>
					</div>
                    <div class="validate-group row">
						<li class="form-validate">
                            <label style="width:145px"><span class="required">*</span>缺卡次数</label>
                            <input type="number" id="absentTimes" name="absentTimes" value="${salaryEmpAttendance.absentTimes }" />
                        </li>
                        <li class="form-validate">
                            <label style="width:145px"><span class="required">*</span>早退次数</label>
                            <input type="number" id="leaveEarlyTimes" name="leaveEarlyTimes" value="${salaryEmpAttendance.leaveEarlyTimes }" />
                        </li>
                    </div>
                    <div class="validate-group row">
						<li class="form-validate">
                            <label style="width:145px"><span class="required">*</span>事假天数</label>
                            <input type="number" id="leaveDays" name="leaveDays" value="${salaryEmpAttendance.leaveDays }" step="0.1"/>
                        </li>
                        <li class="form-validate">
                            <label style="width:145px"><span class="required">*</span>旷工天数</label>
                            <input type="number" id="absentDays" name="absentDays" value="${salaryEmpAttendance.absentDays }" step="0.1"/>
                        </li>
                    </div>
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label style="width:145px"><span class="required">*</span>迟到（半小时以内）次数</label>
                            <input type="number" id="lateTimes" name="lateTimes" value="${salaryEmpAttendance.lateTimes }" />
                        </li>
                        <li class="form-validate">
                            <label style="width:145px"><span class="required">*</span>迟到（半小时以上）次数</label>
                            <input type="number" id="seriousLateTimes" name="seriousLateTimes" value="${salaryEmpAttendance.seriousLateTimes }" />
                        </li>
                    </div>
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label style="width:145px"><span class="required">*</span>迟到（1小时以上）次数</label>
                            <input type="number" id="lateOverHourTimes" name="lateOverHourTimes" value="${salaryEmpAttendance.lateOverHourTimes }" />
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
				$("#posiClass").val(data.posiclass);
				$("#idnum").val(data.idnum);
				Global.LinkSelect.setSelect({firstSelect:"department1",
							firstValue:data.department1,
							secondSelect:"department2",
							secondValue:data.department2,
							});
				validateRefresh("openComponent_salaryEmp_salaryEmp","","dataForm");//刷新校验
			}
    	});
    	$("#openComponent_salaryEmp_salaryEmp").attr("readonly",true);
    	if("${salaryEmpAttendance.m_umState}"!="A"){
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
                salaryMon: {
                    validators: {
                        notEmpty: {message: '薪酬月份不能为空'},
                    }
                },
                lateTimes: {
                    validators: {
                        notEmpty: {message: '迟到次数不能为空'},
                    }
                },
                seriousLateTimes: {
                    validators: {
                        notEmpty: {message: '迟到（半小时以上）次数不能为空'},
                    }
                },
                absentTimes: {
                    validators: {
                        notEmpty: {message: '缺卡次数不能为空'},
                    }
                },
                leaveEarlyTimes: {
                    validators: {
                        notEmpty: {message: '早退不能为空'},
                    }
                },
                leaveDays: {
                    validators: {
                        notEmpty: {message: '事假天数不能为空'},
                    }
                },
                absentDays: {
                    validators: {
                        notEmpty: {message: '旷工天数不能为空'},
                    }
                },
                lateOverHourTimes: {
                    validators: {
                        notEmpty: {message: '迟到（1小时以上）次数不能为空'},
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
        if("${salaryEmpAttendance.m_umState}"=="M"){
        	requestMap="doUpdate";
        }
        $.ajax({
            url: "${ctx}/admin/salaryEmpAttendance/"+requestMap,
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

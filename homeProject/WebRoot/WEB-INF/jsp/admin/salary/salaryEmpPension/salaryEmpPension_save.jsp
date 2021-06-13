<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>工资补贴管理--新增</title>
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
                <c:if test="${salaryEmpPension.m_umState!='V' }">
	                <button type="button" class="btn btn-system" onclick="save()">保存</button>
	            </c:if>
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="dataForm" class="form-search" target="targetFrame">
            	 <input  type="hidden" id="pk" name="pk" value="${salaryEmpPension.pk }"/>
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
						<li>
							<label>二级部门</label>
							<select id="department2" name="department2" ></select>
						</li>
					</div>
                    <div class="validate-group row">
                        <li class="form-validate">
							<label><span class="required">*</span>补贴科目</label>	
							<house:xtdm id="type" dictCode="SALPENSIONTYPE" value="${salaryEmpPension.type }" ></house:xtdm>
						</li>	
                        <li class="form-validate">
                            <label><span class="required">*</span>金额</label>
                            <input type="number" id="amount" name="amount" value="${salaryEmpPension.amount }" step="0.1"/>
                        </li>
                    </div>
                    <div class="validate-group row">
						<li class="form-validate">
							<label><span class="required">*</span>开始月份</label>
							<input type="text" id="beginMon" name="beginMon" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})" value="${salaryEmpPension.beginMon}" onblur="countMon(this);initEffectDate()"/>
						</li>
						<li class="form-validate ">
							<label><span class="required">*</span>生效日期</label>
							<input type="text" id="effectDate" name="effectDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${salaryEmpPension.effectDate}' pattern='yyyy-MM-dd'/>"/>
						</li>
                    </div>
                    <div class="validate-group row">
                    	<li class="form-validate">
							<label><span class="required">*</span>截止月份</label>
							<input type="text" id="endMon" name="endMon" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})" value="${salaryEmpPension.endMon}" onblur="countMon(this)"/>
						</li>
                        <li >
                            <label>补贴月数</label>
                            <input type="text" id="monNum" name="monNum" readonly/>
                        </li>
                    </div>
                    <div class="validate-group row">
                        <li>
                            <label class="control-textarea">备注</label>
                            <textarea type="text" id="remarks" name="remarks">${salaryEmpPension.remarks }</textarea>
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
				Global.LinkSelect.setSelect({firstSelect:"department1",
							firstValue:data.department1,
							secondSelect:"department2",
							secondValue:data.department2,
							});
			}
    	});
    	$("#openComponent_salaryEmp_salaryEmp").attr("readonly",true);
    	Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");
		Global.LinkSelect.setSelect({firstSelect:"department1",
							firstValue:"${salaryEmp.department1}",
							secondSelect:"department2",
							secondValue:"${salaryEmp.department2}",
							});
		$("#department1,#department2").attr("disabled",true);
		if("${salaryEmpPension.m_umState}"!="A"){
			countMon();
		}else{
			initMon();
		}
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
                type: {
                    validators: {
                        notEmpty: {message: '补贴科目不能为空'},
                    }
                },
                amount: {
                    validators: {
                        notEmpty: {message: '金额不能为空'},
                    }
                },
                beginMon: {
                    validators: {
                        notEmpty: {message: '开始月份不能为空'},
                    }
                },
                endMon: {
                    validators: {
                        notEmpty: {message: '截止月份不能为空'},
                    }
                },
                effectDate: {
                    validators: {
                        notEmpty: {message: '生效日期不能为空'},
                    }
                },
                
            }
        });
        
		if("${salaryEmpPension.m_umState}"=="M" &&"${salaryMon.status}" == "3"){
			$("#type").attr("disabled","disabled");
			$("#amount").attr("readonly","readonly");
			$("#beginMon").attr("disabled","disabled");
			$("#effectDate").attr("disabled","disabled");
		}
		
		if("${salaryEmpPension.m_umState}"=="M" && myRound("${nowMon}") < myRound("${maxCheckedMon}")){
			$("#endMon").attr("disabled","disabled");
		}
		if("${salaryEmpPension.m_umState}"=="M" && myRound("${salaryEmpPension.endMon}")> myRound("${maxCheckedMon}")){
			$("#endMon").attr("disabled","disabled");
		}	
    });

    function save() {
        var bootstrapValidator = $("#dataForm").data('bootstrapValidator');
        bootstrapValidator.validate();
        if (!bootstrapValidator.isValid()) return;

        var data = $("#dataForm").jsonForm();
        
        if ( data.beginMon > data.endMon) {
            art.dialog({
          		content: "开始月份不能大于截止月份"
            });
            return
        }
        
        var requestMap="doSave";
        if("${salaryEmpPension.m_umState}"=="M"){
        	requestMap="doUpdate";
        }
        
        $.ajax({
            url: "${ctx}/admin/salaryEmpPension/"+requestMap,
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
    
	//计算月数
	function countMon(obj){
		var beginMon=$("#beginMon").val();
		var endMon=$("#endMon").val();
		if(beginMon=="" || endMon=="" ){
			return;
		}
		if(beginMon > endMon){
			$(obj).val("");
			art.dialog({
          		content: "开始月份不能大于截止月份"
            });
            return
		}
		var beginYear=beginMon.substr(0,4);
		var beginMonth=beginMon.substr(4,2);
		var endYear=endMon.substr(0,4);
		var endMonth=endMon.substr(4,2);
		var diffYear=parseFloat(endYear)-parseFloat(beginYear);
		var diffMon=parseFloat(endMonth)-parseFloat(beginMonth);
		var monNum=diffYear*12+diffMon+1;
		$("#monNum").val(monNum);
	}
	
	//初始化生效日期
	function initEffectDate(){
		var beginMon=$("#beginMon").val();
		if(beginMon!=""){
			var beginYear=beginMon.substr(0,4);
			var beginMonth=beginMon.substr(4,2);
			$("#effectDate").val(beginYear+"-"+beginMonth+"-01");
		}
	}
	
	//初始化开始结束月份
	function initMon(){
		var today=new Date();
		var year=today.getFullYear();  //月份
		var month=parseFloat(today.getMonth()+1);
		var day=parseFloat(today.getDate());
		console.log(month);
		var lastMon="";
		var thisMon="";
		//月份小于10，前面加0
		if(month<10){
			lastMon="0"+(month-1);
			thisMon="0"+(month);
		}else{
			lastMon=""+(month-1);
			thisMon=""+(month);
		}
		//16号之前的，取上个月，否则取这个月
		if(day<16){
			$("#beginMon,#endMon").val(year+lastMon);
		}else{
			$("#beginMon,#endMon").val(year+thisMon);
		}
		initEffectDate();
		$("#monNum").val(1);
	}
</script>
</body>
</html>

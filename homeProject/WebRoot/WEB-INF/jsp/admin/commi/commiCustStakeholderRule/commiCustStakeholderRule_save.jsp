<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>提成干系人--增加</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_commiCycle.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#custCode").openComponent_customer({
			showValue:"${commiCustStakeholderRule.custCode}",
			showLabel:"${commiCustStakeholderRule.custDescr}",
			callBack:function(){
				validateRefresh("openComponent_customer_custCode","","dataForm");
			}
		});
		$("#role").openComponent_roll({
			showValue:"${commiCustStakeholderRule.role}",
			showLabel:"${commiCustStakeholderRule.roleDescr}",
			callBack:function(){
				validateRefresh("openComponent_roll_role","","dataForm");
			}
		});
		$("#signCommiNo").openComponent_commiCycle({
			showValue:"${commiCustStakeholderRule.signCommiNo}",
			callBack:function(data){
				validateRefresh("openComponent_commiCycle_signCommiNo","","dataForm");
			}
		});
		$("#crtNo").openComponent_commiCycle({
			showValue:"${commiCustStakeholderRule.crtNo}",
		});
		$("#lastUpdateNo").openComponent_commiCycle({
			showValue:"${commiCustStakeholderRule.lastUpdateNo}",
		});
		$("#empCode").openComponent_employee({
			showValue:"${commiCustStakeholderRule.empCode}",
			showLabel:"${commiCustStakeholderRule.empName}",
			callBack:function(data){
				$("#department").val(data.department);
				initDept(data.department1,data.department2,data.department3);
				validateRefresh("openComponent_employee_empCode","","dataForm");
			}
		});
		$("#oldStakeholder").openComponent_employee({
			showValue:"${commiCustStakeholderRule.oldStakeholder}",
			showLabel:"${commiCustStakeholderRule.oldStakeholderDescr}",
			callBack:function(data){
				validateRefresh("openComponent_employee_oldStakeholder","","dataForm");
			}
		});
		$("#oldStakeholder2").openComponent_employee({
			showValue:"${commiCustStakeholderRule.oldStakeholder2}",
			showLabel:"${commiCustStakeholderRule.oldStakeholder2Descr}",
			callBack:function(data){
				validateRefresh("openComponent_employee_oldStakeholder2","","dataForm");
			}
		});
		
		if("${commiCustStakeholderRule.m_umState }"=="V"){
			disabledForm("dataForm");
		}
		$("#saveBtn").on("click", function() {
			var custCode=$("#custCode").val();
			var url="${ctx}/admin/commiCustStakeholderRule/";
			if("${commiCustStakeholderRule.m_umState }"=="A"){
				url+="doSave";
			}else if("${commiCustStakeholderRule.m_umState }"=="M"){
				url+="doUpdate";
			}
			var bootstrapValidator = $("#dataForm").data('bootstrapValidator');
	        bootstrapValidator.validate();
	        if (!bootstrapValidator.isValid()) return;
			var datas = $("#dataForm").serialize();
			$.ajax({
				url : url,
				type : "post",
				data : datas,
				dataType : "json",
				cache : false,
				error : function(obj) {
					showAjaxHtml({
						"hidden" : false,
						"msg" : "保存数据出错~"
					});
				},
				success : function(obj) {
					if (obj.rs) {
						art.dialog({
							content : obj.msg,
							time : 1000,
							beforeunload : function() {
								closeWin();
							}
						});
					} else {
						$("#_form_token_uniq_id").val(obj.token.token);
						art.dialog({
							content : obj.msg,
							width : 200
						});
					}
				}
			});
		});
		
		//初始化部门
		initDept("${commiCustStakeholderRule.department1}","${commiCustStakeholderRule.department2}","${commiCustStakeholderRule.department3}");
		setTimeout(function(){
			if($("#department1").find("option[value="+$.trim("${commiCustStakeholderRule.department1}")+"]").length == 0){
		    	appendOption("department1","${commiCustStakeholderRule.department1}","${commiCustStakeholderRule.department1Descr}");
		    	$("#department1").val("${commiCustStakeholderRule.department1}");
			}	
			if($("#department2").find("option[value="+$.trim("${commiCustStakeholderRule.department2}")+"]").length == 0){
		    	appendOption("department2","${commiCustStakeholderRule.department2}","${commiCustStakeholderRule.department2Descr}");
		    	$("#department2").val("${commiCustStakeholderRule.department2}");
			}
			if($("#department3").find("option[value="+$.trim("${commiCustStakeholderRule.department3}")+"]").length == 0){
				appendOption("department3","${commiCustStakeholderRule.department3}","${commiCustStakeholderRule.department3Descr}");
			}
			$("#department3").val("${commiCustStakeholderRule.department3}");
		},200);
	});
	$(function() {
		$("#dataForm").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
            	openComponent_customer_custCode: {
                    validators: {
                        notEmpty: {message: '客户编号不能为空'}
                    }
                },
                custCode: {
                    validators: {
                        notEmpty: {message: '客户编号不能为空'}
                    }
                },
            	openComponent_commiCycle_signCommiNo: {
                    validators: {
                        notEmpty: {message: '签单提成周期编号不能为空'}
                    }
                },
                signCommiNo: {
                    validators: {
                        notEmpty: {message: '签单提成周期编号不能为空'}
                    }
                },
                openComponent_employee_empCode: {
                    validators: {
                        notEmpty: {message: '员工编号不能为空'}
                    }
                },
                empCode: {
                    validators: {
                        notEmpty: {message: '员工编号不能为空'}
                    }
                },
                openComponent_roll_role: {
                    validators: {
                        notEmpty: {message: '角色不能为空'}
                    }
                },
                role: {
                    validators: {
                        notEmpty: {message: '角色不能为空'}
                    }
                },
                weightPer: {
                    validators: {
                        notEmpty: {message: '权重不能为空'},
                    }
                },
                commiPer: {
                    validators: {
                        notEmpty: {message: '提成点不能为空'},
                    }
                },
                multiple: {
                    validators: {
                        notEmpty: {message: '倍数不能为空'},
                    }
                },
                subsidyPer: {
                    validators: {
                        notEmpty: {message: '补贴点不能为空'},
                    }
                },
                checkCommiType: {
                    validators: {
                        notEmpty: {message: '结算提成点类型不能为空'},
                    }
                },
                checkCommi: {
                    validators: {
                        notEmpty: {message: '结算提成点不能为空'},
                    }
                },
                commiProvidePer: {
                    validators: {
                        notEmpty: {message: '提成发放比例不能为空'},
                    }
                },
                subsidyProvidePer: {
                    validators: {
                        notEmpty: {message: '补贴发放比例不能为空'},
                    }
                },
                multipleProvidePer: {
                    validators: {
                        notEmpty: {message: '倍数发放比例不能为空'},
                    }
                },
                totalProvideAmount: {
                    validators: {
                        notEmpty: {message: '累计发放金额不能为空'},
                    }
                },
                isBearAgainCommi: {
                    validators: {
                        notEmpty: {message: '承担翻单提成不能为空'},
                    }
                },
            }
        });
	});
	
	//重写修改一级部门方法，可手动添加不属于该一级部门的二级部门
	function changeDepartment1(){
		var department1=$("#department1").val();
		$.ajax({
			url:"${ctx}/admin/department1/changeDepartment1?code="+department1,
			type:'post',
			data:{},
			dataType:'json',
			cache:false,
			async:false,
			error:function(obj){
				
			},
			success:function(obj){
				if (obj){
					department2Text=obj;
					$("#department2").html(obj.strSelect).trigger("onchange");
					$("#department2").searchableSelect();
				}
			}
		}); 
	}
	//重写修改二级部门方法
	function changeDepartment2(){
		var department2=$("#department2").val();
		$.ajax({
			url:"${ctx}/admin/department2/changeDepartment2?code="+department2,
			type:'post',
			data:{},
			dataType:'json',
			cache:false,
			async:false,
			error:function(obj){
				
			},
			success:function(obj){
				if (obj){
					department3Text=obj;
					$("#department3").html(obj.strSelect).trigger("onchange");
					$("#department3").searchableSelect();
				}
			}
		});
	}
	//添加二级部门
	function addDepartment2(){
		Global.Dialog.showDialog("Department2Add",{
			title:"搜寻--二级部门信息",
			url:"${ctx}/admin/department2/goCode",
			height:600,
			width:1000,
			returnFun:function(data){
				setDepartment2(data.code,data.code+' '+data.desc1);
			}
		});
	}
	
	function addDepartment1(){
		Global.Dialog.showDialog("Department1Add",{
			title:"搜寻--一级级部门信息",
			url:"${ctx}/admin/department1/goCode",
			height:600,
			width:1000,
			returnFun:function(data){
				if($("#department1").find("option[value="+data.code+"]").length == 0){
					appendOption("department1",data.code,data.desc1);
	
				}			
				$("#department1").val(data.code);
				changeDepartment1();
				setDepartment2(data.code,data.code+' '+data.desc1);
			}
		});
	}
	
	//添加三级部门
	function addDepartment3(){
		Global.Dialog.showDialog("Department3Add",{
			title:"搜寻--三级部门信息",
			url:"${ctx}/admin/department3/goCode",
			height:600,
			width:1000,
			returnFun:function(data){	
				setDepartment3(data.code,data.code+' '+data.desc1);
			}
		});
	}
	
	//重置二级部门下拉框
	function setDepartment2(department2,department2Descr){
		if(department2!="" && department2Text.strSelect.indexOf(department2Descr)==-1){
			department2Text.strSelect+="<option value="+department2+">"+department2Descr+"</option>";
		}
		$("#department2").html(department2Text.strSelect).trigger("onchange");
		if(department2!=""){
			$("#department2").val(department2).trigger("onchange");
		}
	}
	
	//重置三级部门下拉框
	function setDepartment3(department3,department3Descr){
		if(department3!="" && department3Text.strSelect.indexOf(department3Descr)==-1){
			department3Text.strSelect+="<option value="+department3+">"+department3Descr+"</option>";
		}
		$("#department3").html(department3Text.strSelect).trigger("onchange");
		if(department3!=""){
			$("#department3").val(department3).trigger("onchange");
		}
	}
	//初始化部门
	function initDept(dept1,dept2,dept3){
		$("#department1").val(dept1).trigger("onchange");
		$("#department2").val(dept2).trigger("onchange");
		$("#department3").val(dept3).trigger("onchange");
	}
	
	function appendOption(id, code, desc1){
		$("#"+id).append("<option value='"+code+"'>"+code+" "+desc1+"</option>");
		$("#"+id).searchableSelect();
	}
</script>
</head>
<body>
	<form action="" method="post" id="dataForm" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<c:if test="${commiCustStakeholderRule.m_umState !='V'}">
							<button type="button" class="btn btn-system" id="saveBtn">
								<span>保存</span>
							</button>
						</c:if>
						<button type="button" class="btn btn-system" id="closeBut"
							onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<input type="hidden" name="jsonString" value="" /> <input
						type="hidden" name="m_umState" value="${commiCustStakeholderRule.m_umState }" />
					<input type="hidden" name="pk" value="${commiCustStakeholderRule.pk }" />
					<input type="hidden" name="isModified" value="1" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate">
								<label>签单周期编号</label> 
								<input type="text" id="signCommiNo"
							      name="signCommiNo" value="${commiCustStakeholderRule.signCommiNo }" />
							</li>
							<li class="form-validate">
								<label>客户编号</label> 
								<input type="text" id="custCode"
							      name="custCode" value="${commiCustStakeholderRule.custCode }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>角色</label> 
								<input type="text" id="role"
							      name="role" value="${commiCustStakeholderRule.role }" />
							</li>
							<li class="form-validate">
								<label>员工编号</label> 
								<input type="text" id="empCode"
							      name="empCode" value="${commiCustStakeholderRule.empCode }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>权重</label>
								<input type="text" id="weightPer" name="weightPer" 
									value="${commiCustStakeholderRule.weightPer }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
							<li class="form-validate">
								<label>一级部门</label> 
								<div class="input-group">
									<house:department1 id="department1" style="position:relative;top:1px;left:1px;" 
										onchange="changeDepartment1()"
										value="${commiCustStakeholderRule.department1 }">
									</house:department1>
									<button type="button" class="btn btn-system" 
											onclick="addDepartment1()">
											<span class="glyphicon glyphicon-search"></span>
									</button>
								</div>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>提成点</label>
								<input type="text" id="commiPer" name="commiPer" 
									value="${commiCustStakeholderRule.commiPer }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
							<li class="form-validate"><label>二级部门</label>  
								<div class="input-group">
									<house:department2 id="department2"
											dictCode="${commiCustStakeholderRule.department1 }" value="${commiCustStakeholderRule.department2 }"
											style="position:relative;top:1px;left:1px;" 
											onchange="changeDepartment2()">
									</house:department2>
									<button type="button" class="btn btn-system" 
											onclick="addDepartment2()">
											<span class="glyphicon glyphicon-search"></span>
									</button>
								</div>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>补贴点</label>
								<input type="text" id="subsidyPer" name="subsidyPer" 
									value="${commiCustStakeholderRule.subsidyPer }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
							<li class="form-validate"> <label>三级部门</label>
								<div class="input-group">
									<house:department3 id="department3"
											dictCode="${commiCustStakeholderRule.department2 }" value="${commiCustStakeholderRule.department3 }"
											style="position:relative;top:1px;left:1px;" >
									</house:department3>
									<button type="button" class="btn btn-system" 
											onclick="addDepartment3()">
											<span class="glyphicon glyphicon-search"></span>
									</button>
								</div>
							</li>
                        </div>
                        <div class="validate-group row">
                        	<li class="form-validate">
								<label>倍数</label>
								<input type="text" id="multiple" name="multiple" 
									value="${commiCustStakeholderRule.multiple }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
							<li class="form-validate">
							  	 <label>预发提成公式</label> 
							  	 <house:dict id="preCommiExprPk"
                                       dictCode=""
                                       sql="select a.PK,cast(a.PK as nvarchar(10))+' '+a.ExprRemarks descr from tCommiExpr a  where a.Expired='F' order By PK "
                                       sqlValueKey="PK" sqlLableKey="Descr"
                                       value="${commiCustStakeholderRule.preCommiExprPk}">
                                 </house:dict>
                            </li>
                        </div>
						<div class="validate-group row">
                            <li class="form-validate">
							  	 <label>结算提成公式</label> 
							  	 <house:dict id="checkCommiExprPk"
	                                      dictCode=""
                                      sql="select a.PK,cast(a.PK as nvarchar(10))+' '+a.ExprRemarks descr from tCommiExpr a  where a.Expired='F' order By PK "
                                      sqlValueKey="PK" sqlLableKey="Descr"
                                      value="${commiCustStakeholderRule.checkCommiExprPk}">
                                </house:dict>
                            </li>
                            <li class="form-validate">
								<label>结算提成点类型</label>
								<house:xtdm id="checkCommiType" dictCode="COMMIRULETYPE" value="${commiCustStakeholderRule.checkCommiType }"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>结算提成点</label>
								<input type="text" id="checkCommiPer" name="checkCommiPer" 
									value="${commiCustStakeholderRule.checkCommiPer }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
							<li class="form-validate">
							  	 <label>结算提成浮动规则</label> 
							  	 <house:dict id="checkFloatRulePk"
                                       dictCode=""
                                       sql="select a.PK,cast(a.PK as nvarchar(10))+' '+a.Descr descr from tDesignCommiFloatRule a  where a.Expired='F' order By PK "
                                       sqlValueKey="PK" sqlLableKey="Descr"
                                       value="${commiCustStakeholderRule.checkFloatRulePk}">
                                 </house:dict>
                            </li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>原干系人1</label> 
								<input type="text" id="oldStakeholder"
							      name="oldStakeholder" value="${commiCustStakeholderRule.oldStakeholder }" />
							</li>
							<li class="form-validate">
								<label>提成发放比例</label>
								<input type="text" id="commiProvidePer" name="commiProvidePer" 
									value="${commiCustStakeholderRule.commiProvidePer }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>原干系人2</label> 
								<input type="text" id="oldStakeholder2"
							      name="oldStakeholder2" value="${commiCustStakeholderRule.oldStakeholder2 }" />
							</li>
							<li class="form-validate">
								<label>补贴发放比例</label>
								<input type="text" id="subsidyProvidePer" name="subsidyProvidePer" 
									value="${commiCustStakeholderRule.subsidyProvidePer }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>倍数发放比例</label>
								<input type="text" id="multipleProvidePer" name="multipleProvidePer" 
									value="${commiCustStakeholderRule.multipleProvidePer }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
							<li class="form-validate">
								<label>累计发放金额</label>
								<input type="text" id="totalProvideAmount" name="totalProvideAmount" 
									value="${commiCustStakeholderRule.totalProvideAmount }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>提成点</label>
								<input type="text" id="commiPer" name="commiPer" 
									value="${commiCustStakeholderRule.commiPer }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
							<li class="form-validate">
								<label>生成周期</label> 
								<input type="text" id="crtNo"
							      name="crtNo" value="${commiCustStakeholderRule.crtNo }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>是否承担翻单提成</label>
								<house:xtdm id="isBearAgainCommi" dictCode="YESNO" value="${commiCustStakeholderRule.isBearAgainCommi }"></house:xtdm>
							</li>
							<li class="form-validate">
								<label>最后修改周期</label> 
								<input type="text" id="lastUpdateNo"
							      name="lastUpdateNo" value="${commiCustStakeholderRule.lastUpdateNo }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>右边提成点</label>
								<input type="text" id="rightCommiPer" name="rightCommiPer" 
									value="${commiCustStakeholderRule.rightCommiPer }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
						</div>
					</ul>
				</div>
			</div>
		</div>
	</form>
</body>
</html>

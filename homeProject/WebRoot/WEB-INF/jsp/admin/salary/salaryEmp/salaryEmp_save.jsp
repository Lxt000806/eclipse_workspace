<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%><!DOCTYPE html>
<html>
<head>
<title>薪酬人员--新增</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_position.js?v=${v}" type="text/javascript"></script>
<style type="text/css">
.linkFile {
    position: relative;
    display: inline-block;
    border: 1px solid #333;
    padding: 0px 0px;
    margin-left:10px;
    overflow: hidden;
    text-decoration: none;	
    text-indent: 0;
    line-height: 18px;
    border-radius: 1px;
    color: #000;
    font:10px;
    background:#ccc; /* 一些不支持背景渐变的浏览器 */
    background:-moz-linear-gradient(top, #fff, #ccc);
    background:-webkit-gradient(linear, 0 0, 0 bottom, from(#fff), to(#ccc));
    background:-o-linear-gradient(top, #fff, #ccc);
}
a:link{text-decoration:none;color:#000;}
a:hover{text-decoration:none;color:#000;}
a:active{text-decoration:none;color:#000;}
.clearFile {
    position: relative;
    display: inline-block;
    border: 1px solid #333;
    padding: 0px 0px;
    margin-top:5px;
    overflow: hidden;
    text-decoration: none;
    text-indent: 0;
    line-height: 18px;
    border-radius: 1px;
    color: #000;
    font:10px;
    background:#ccc; /* 一些不支持背景渐变的浏览器 */
    background:-moz-linear-gradient(top, #fff, #ccc);
    background:-webkit-gradient(linear, 0 0, 0 bottom, from(#fff), to(#ccc));
    background:-o-linear-gradient(top, #fff, #ccc);
}
.linkFile input {
    position: absolute;
    font-size: 50px;
    right: 0;
    top: 0;
    opacity: 0;
    filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0);
}
</style>
<script type="text/javascript">

$(function() {
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		excluded:[":disabled"],
		fields: {  
			category: {
				validators : {
					notEmpty: {
						message: "人员类别不能为空"
					},
				}
			},
			innerEmp: {
				validators : {
					notEmpty: {
						message: "员工不能为空"
					},
				}
			},
			openComponent_employee_innerEmp: {
				validators : {
					notEmpty: {
						message: "员工不能为空"
					},
				}
			},
			outerEmp: {
				validators : {
					notEmpty: {
						message: "姓名不能为空"
					},
				}
			},
			idnum: {
				validators : {
					notEmpty: {
						message: "身份证号不能为空"
					},
				}
			},
			joinDate: {
				validators : {
					notEmpty: {
						message: "入职日期不能为空"
					},
				}
			},
			status: {
				validators : {
					notEmpty: {
						message: "人员状态不能为空"
					},
				}
			},
			belongType: {
				validators : {
					notEmpty: {
						message: "人员属性不能为空"
					},
				}
			},
		/* 	conSignCmp: {
				validators : {
					notEmpty: {
						message: "签约公司不能为空"
					},
				}
			}, */
			salaryStatus: {
				validators : {
					notEmpty: {
						message: "薪酬状态不能为空"
					},
				}
			},
			workingDays: {
				validators : {
					notEmpty: {
						message: "工作日天数不能为空"
					},
					greaterThan: {
						value: 0
					},
					lessThan: {
						value: 31
					}
				}
			},
			
			/* posiClass: {
				validators : {
					notEmpty: {
						message: "岗位类别不能为空"
					},
				}
			},
			posiLevel: {
				validators : {
					notEmpty: {
						message: "岗位级别不能为空"
					},
				}
			},
			isFront: {
				validators : {
					notEmpty: {
						message: "前后端不能为空"
					},
				}
			}, */
			
 		},
		submitButtons : 'input[type="submit"]'
	});  
	
 	$("#page_form_1").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		excluded:[":disabled"],
		fields: {  
			/* basicSalarySetMode: {
				validators : {
					notEmpty: {
						message: "定薪方式不能为空"
					},
				}
			},
			salary: {
				validators : {
					notEmpty: {
						message: "工资不能为空"
					},
				}
			}, */
			posiSalary: {
				validators : {
					notEmpty: {
						message: "职务工资不能为空"
					},
				}
			},
			skillSubsidy: {
				validators : {
					notEmpty: {
						message: "技能补贴不能为空"
					},
				}
			},
			otherBonuse: {
				validators : {
					notEmpty: {
						message: "其他奖励不能为空"
					},
				}
			},
			perfBonuse: {
				validators : {
					notEmpty: {
						message: "绩效不能为空"
					},
				}
			}, 	
			starSubsidy: {
				validators : {
					notEmpty: {
						message: "星级补贴不能为空"
					},
				}
			}, 	
			otherSubsidy: {
				validators : {
					notEmpty: {
						message: "其他补贴不能为空"
					},
				}
			}, 	
			basicSalary: {
				validators : {
					notEmpty: {
						message: "基本工资不能为空"
					},
				}
			},
			insurBase: {
				validators : {
					notEmpty: {
						message: "缴费标准不能为空"
					},
				}
			},
			
 		},
		submitButtons : 'input[type="submit"]'
	});  
	
	$("#page_form_2").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		excluded:[":disabled"],
		fields: {  
			payMode: {
				validators : {
					notEmpty: {
						message: "发放方式不能为空"
					},
				}
			},
			isTaxable: {
				validators : {
					notEmpty: {
						message: "是否计税不能为空"
					},
				}
			},
			socialInsurParam: {
				validators : {
					notEmpty: {
						message: "社保公积金参数不能为空"
					},
				}
			},
			edmInsurMon: {
				validators : {
					notEmpty: {
						message: "养老保险开始月份不能为空"
					},
				}
			},
			medInsurMon: {
				validators : {
					notEmpty: {
						message: "医保开始月份不能为空"
					},
				}
			},
			houFundMon: {
				validators : {
					notEmpty: {
						message: "公积金开始月份不能为空"
					},
				}
			},
			payCmp1: {
				validators : {
					notEmpty: {
						message: "出款公司1不能为空"
					},
				}
			},
			insurLimit: {
				validators : {
					notEmpty: {
						message: "保险费用额度不能为空"
					},
				}
			},
			cash: {
				validators : {
					notEmpty: {
						message: "现金额度不能为空"
					},
				}
			},
			salarySettleCmp: {
				validators : {
					notEmpty: {
						message: "薪酬结算企业不能为空"
					},
				}
			},
			cmpUsageType: {
				validators : {
					notEmpty: {
						message: "用途备注不能为空"
					},
				}
			},
			isBasicSalaryPayment: {
				validators : {
					notEmpty: {
						message: "基本工资正常出款不能为空"
					},
				}
			},
 		},
		submitButtons : 'input[type="submit"]'
	}); 
	
	$("#page_form_3").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		excluded:[":disabled"],
		fields: {  
			isSocialInsur: {
				validators : {
					notEmpty: {
						message: "是否缴纳五险一金不能为空"
					},
				}
			},
			socialInsurParam: {
				validators : {
					notEmpty: {
						message: "社保公积金参数不能为空"
					},
				}
			},
			edmInsurMon: {
				validators : {
					notEmpty: {
						message: "养老保险开始月份不能为空"
					},
				}
			},
			medInsurMon: {
				validators : {
					notEmpty: {
						message: "医保开始月份不能为空"
					},
				}
			},
			houFundMon: {
				validators : {
					notEmpty: {
						message: "公积金开始月份不能为空"
					},
				}
			},
			
 		},
		submitButtons : 'input[type="submit"]'
	}); 
	
});

$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepAll","department1","department2");
	Global.LinkSelect.setSelect({firstSelect:"department1",
							firstValue:"${salaryEmp.department1}",
							secondSelect:"department2",
							secondValue:"${salaryEmp.department2}",
							});
	/* Global.LinkSelect.initSelect("${ctx}/admin/salaryEmp/posiByAuthority","posiClass","posiLevel");
	Global.LinkSelect.setSelect({firstSelect:"posiClass",
							firstValue:"${salaryEmp.posiClass}",
							secondSelect:"posiLevel",
							secondValue:"${salaryEmp.posiLevel}",
							}); */
	$("#insurBase_span").popover({trigger:"hover"});
	
	$("#position").openComponent_position({
		showValue:"${position.code}",
		showLabel:"${position.desc2}",
		readonly:true
	});
							
	$("#innerEmp").openComponent_employee({
		showValue:"${salaryEmp.empCode}",
		showLabel:"${salaryEmp.empName}",
		condition:{
			isAddSalary:"0"
		},
		callBack:function(data){
			Global.LinkSelect.setSelect({firstSelect:"department1",
							firstValue:data.department1,
							secondSelect:"department2",
							secondValue:data.department2,
							});
			$("#empCode").val(data.number);
			$("#empName").val(data.namechi);
			$("#conSignCmp").val(data.consigncmp);
			$("#idnum").val(data.idnum);
			$("#status").val(data.status.trim());
			$("#joinDate").val(data.joindate);
			$("#regularDate").val(data.regulardate);
			$("#leaveDate").val(data.leavedate);
			$("#position").openComponent_position({
				showValue:data.position,
				showLabel:data.posdesc,
				readonly:true
			});
			validateRefresh("innerEmp","","dataForm");//刷新校验
			validateRefresh("position","","dataForm");//刷新校验
			setMon();
			changeStatus();
			$("#openComponent_position_position").attr("readonly",true);
		}
	});
	
	$("#basicSalary").bind("input propertychange", function(){
		var basicSalary = $("#basicSalary").val();
		$("#basicSalaryTemp").val(basicSalary);
	});
	
	$("#openComponent_employee_innerEmp,#openComponent_position_position").attr("readonly",true);
	//$("#basicSalarySetMode").attr("disabled",true);
	if("${salaryEmp.m_umState}"!="A"){
		$("#innerEmp").setComponent_employee({disabled:true});
		$("#category").attr("disabled",true);
	}
	changeType();
	changeIsSocialInsur();
	changePayMode();
});
$(function(){
	var lastCellRowId;
	var gridOption = {
		height:120,
		rowNum:10000000,
		url:"${ctx}/admin/salaryEmp/goBankJqGrid",
		postData: {empCode:"${salaryEmp.empCode}"},
		styleUI: "Bootstrap", 
		colModel : [
			{name: "pk", index: "pk", width: 84, label: "pk", sortable: true, align: "left", hidden: true},
			{name: "banktype", index: "banktype", width: 100, label: "银行", sortable: true, align: "left", hidden: true},
			{name: "banktypedescr", index: "banktypedescr", width: 120, label: "银行", sortable: true, align: "left"},
			{name: "cardid", index: "cardid", width: 180, label: "卡号", sortable: true, align: "left"},
			{name: "actname", index: "actname", width: 80, label: "户名", sortable: true, align: "left",},
			{name: "remarks", index: "remarks", width: 250, label: "备注", sortable: true, align: "left"},
		],
		loadonce:true
	};
	Global.JqGrid.initJqGrid("dataTable_bank",gridOption);
	
	$("#addBank").on("click",function(){
		var rowNum=$("#dataTable_bank").jqGrid('getGridParam','records');
		Global.Dialog.showDialog("addBank",{
			title:"银行卡信息——新增",
			url:"${ctx}/admin/salaryEmp/goAddBank",
			postData:{actName:$("#empName").val()},
			height: 300,
			width:500,
		    returnFun:function(data){
				if(data){
					var json={
						banktype:data.bankType,
						banktypedescr:data.bankTypeDescr,
						actname:data.actName,
						cardid:data.cardId,
						remarks:data.remarks,
						pk:rowNum*-1
					};
					var rows = $("#dataTable_bank").jqGrid("getRowData");//资源数组
					for(var i in rows){
						if(data.bankType==rows[i].banktype){
							art.dialog({
								content: "银行卡类型重复！",
							});
							return;
						}
					}
					Global.JqGrid.addRowData("dataTable_bank",json);
				}
			} 
		});
	});
	
	$("#updateBank").on("click",function(){
		var id = $("#dataTable_bank").jqGrid("getGridParam","selrow");
		var ret =$("#dataTable_bank").jqGrid("getRowData",id);
		if(!id){
			art.dialog({
				content: "请选择一条记录！",
			});
			return;
		}
		Global.Dialog.showDialog("updateBank",{
			title:"银行卡信息——编辑",
		  	url:"${ctx}/admin/salaryEmp/goAddBank",
		  	postData:{
		  		bankType:ret.banktype,
		  		bankTypeDescr:ret.banktypedescr,
		  		actName:ret.actname,
		  		cardId:ret.cardid,
		  		remarks:ret.remarks,
		  		m_umState:"M",
		  		pk:ret.pk
		  	},
		  	height: 300,
			width:500,
		    returnFun:function(data){
				if(data){
					var json={
						banktype:data.bankType,
						banktypedescr:data.bankTypeDescr,
						actname:data.actName,
						cardid:data.cardId,
						remarks:data.remarks,
						m_umState:"M"
					};
					var rows = $("#dataTable_bank").jqGrid("getRowData");//资源数组
					for(var i in rows){
						if(data.bankType==rows[i].banktype && rows[i].pk!=ret.pk){
							art.dialog({
								content: "银行卡类型重复！",
							});
							return;
						}
					}
			   		$("#dataTable_bank").setRowData(id,json);
				}
			} 
		});
	});
	
	$("#deleteBank").on("click",function(){
		var id = $("#dataTable_bank").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({
				content: "请选择一条记录！",
			});
			return;
		}
		art.dialog({
			content:"是删除该记录？",
			lock: true,
			ok: function () {
				Global.JqGrid.delRowData("dataTable_bank",id);
			},
			cancel: function () {
				return true;
			}
		});
	});
	
	$("#viewBank").on("click",function(){
		var id = $("#dataTable_bank").jqGrid("getGridParam","selrow");
		var ret =$("#dataTable_bank").jqGrid("getRowData",id);
		if(!id){
			art.dialog({
				content: "请选择一条记录！",
			});
			return;
		}
		Global.Dialog.showDialog("viewBank",{
			title:"银行卡信息——查看",
		  	url:"${ctx}/admin/salaryEmp/goAddBank",
		  	postData:{
		  		bankType:ret.banktype,
		  		bankTypeDescr:ret.banktypedescr,
		  		actName:ret.actname,
		  		cardId:ret.cardid,
		  		remarks:ret.remarks,
		  		m_umState:"V"
		  	},
		  	height: 300,
			width:500,
		});
	});
	
});

function save(){
	$("#dataForm,#page_form_1,#page_form_2,#page_form_3").bootstrapValidator("validate");
	if( !$("#dataForm").data("bootstrapValidator").isValid()
		|| !$("#page_form_1").data("bootstrapValidator").isValid()
		|| !$("#page_form_2").data("bootstrapValidator").isValid()
		|| !$("#page_form_3").data("bootstrapValidator").isValid()
	) {
		return;
 	}
 	//银行卡信息不为空
 	var rowNum=$("#dataTable_bank").jqGrid('getGridParam','records');
	if(rowNum==0){
	   art.dialog({
       		content:"请添加银行卡信息"
       });
       return;
	}
	
	//合并表单数据
 	var data = $("#dataForm").jsonForm();
    var pageForm1Data = $("#page_form_1").jsonForm();
    var pageForm2Data = $("#page_form_2").jsonForm();
    var pageForm3Data = $("#page_form_3").jsonForm();
    var bankDetailJson=allToJson('dataTable_bank','bankDetailJson');
    $.extend(data, pageForm1Data, pageForm2Data, pageForm3Data,bankDetailJson);
    
    //薪酬方案不为空
	if(data.salaryScheme=="" && "${salaryEmp.m_umState}" == "A"){
	   art.dialog({
       		content:"请至少选择一个薪酬方案"
       });
       return;
	}
    
    //身份证号格式验证
	var reg = /(^\d{15}$)|(^\d{17}([0-9]|X|x)$)/;
	if(!reg.test(data.idnum)){
	    art.dialog({
	    	content:"身份证输入不合法"
	    });
	    return;
	}  
	
	//单独设置时，基本工资不能大于工资
	/* if(data.basicSalarySetMode=="2" && nanToZero(data.basicSalary)>nanToZero(data.salary) ){
	   art.dialog({
       		content:"基本工资不能大于工资"
       });
       return;
	} */
	
    //控制权重比之和为100%
    if( data.payMode=="3" && (nanToZero(data.weight1)+nanToZero(data.weight2)+nanToZero(data.weight3)+nanToZero(data.weight4)!=100)){
	    art.dialog({
	    	content:"出款公司权重比之和必须为100%"
	    });
	    return;
    }
    
	$.ajax({
		url:"${ctx}/admin/salaryEmp/doSave",
		type: "post",
		data: data,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				if("${salaryEmp.isSeries}"=="1"){//连续新增
	    					$("#_form_token_uniq_id").val(obj.token.token);//刷新token
	    					$("#dataTable_bank").clearGridData();//清空银行卡信息表
	    				}else{
	    					closeWin();
	    				}
				    }
				});
	    	}else{
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
				});
	    	}
    	}
	});  
}  

//修改人员类型
function changeType(){
	var category=$("#category").val();
	if(category=="1"){
		$("#innerEmp").parent().removeClass("hidden");
		$("#innerEmp").removeAttr("disabled");//隐藏时，设置为disable，跳过校验
		$("#outerEmp").parent().addClass("hidden");
		$("#outerEmp").attr("disabled",true);
		
		//身份证，状态，部门编辑时可修改，公司员工一级部门必填
		if("${salaryEmp.m_umState}"=="A"){
			$("#idnum,#status,#department1,#department2,#leaveDate").attr("disabled",true);
			//新增时职位不为空
	        $("#dataForm").bootstrapValidator("removeField","position");
	        $("#dataForm").bootstrapValidator("addField", "position", {  
	            validators: {  
	                notEmpty: {  
	                    message: '职位不能为空'  
	                } 
	            }  
	        });
	        $("#position").prev().prev().children().eq(0).removeClass("hidden");
		}else{
			$("#idnum,#status,#department1,#department2,#regularDate,#leaveDate").removeAttr("disabled");
			$("#dataForm").bootstrapValidator("removeField","department1");
			$("#dataForm").bootstrapValidator("addField", "department1", {  
	            validators: {  
	                notEmpty: {  
	                    message: '一级部门不能为空'  
	                } 
	            }  
	        });
	        //修改时职位非必填
	        $("#dataForm").bootstrapValidator("removeField","position");
	        $("#position").prev().prev().children().eq(0).addClass("hidden");
		}
		$("#department1").prev().children().eq(0).removeClass("hidden");//显示星号
		
	
	}else if(category=="2"){
		$("#outerEmp").parent().removeClass("hidden");
		$("#outerEmp").removeAttr("disabled");
		$("#innerEmp").parent().addClass("hidden");
		$("#innerEmp").attr("disabled",true);
		$("#idnum,#status,#regularDate").removeAttr("disabled");
		
		//状态不为在岗和空时，可以填离职日期
		if($("#status").val()!="1" && $("#status").val()!=""){
			$("#leaveDate").removeAttr("disabled");
		}
		
		//外部人员，一二级部门可手动填
		$("#department1,#department2").removeAttr("disabled");
		$("#dataForm").bootstrapValidator("removeField","department1");
		$("#department1").prev().children().eq(0).addClass("hidden");//隐藏星号
		
        //外部人员，职位可选
        $("#dataForm").bootstrapValidator("removeField","position");
        $("#position").prev().prev().children().eq(0).addClass("hidden");
        $("#position").setComponent_position({
			readonly:false
		});
	}else{
		$("#outerEmp").parent().addClass("hidden");
		$("#innerEmp").parent().addClass("hidden");
		$("#outerEmp").attr("disabled",true);
		$("#innerEmp").attr("disabled",true);
		$("#department1,#department2").attr("disabled",true);
	}
}

//非数字转为0
function nanToZero(x) {
    var val = parseFloat(x);
    if (isNaN(val)) {
        return 0;
    }
    return val;
}

//重写allToJson方法，自定义表格名
function allToJson(tableId,tableName){
	var json = {};
	var rows = $("#"+tableId).jqGrid("getRowData");
	json[tableName] = JSON.stringify(rows);
	return json;
}

//根据岗位类别获取是否前后端
function getIsFront(){
	 $.ajax({
         url : '${ctx}/admin/salaryEmp/getIsFront',
         type : 'post',
         data : {
              'pk' : $("#posiClass").val()
         },
         async:false,
         dataType : 'json',
         cache : false,
         error : function(obj) {
              showAjaxHtml({
                    "hidden" : false,
                    "msg" : '保存数据出错~'
              });
         },
         success : function(obj) {
             $("#isFront").val(obj);
             setMon();
         }
    });
}

//根据岗位级别获取基本工资
/* function getBasicSalary(){
	$.ajax({
         url : '${ctx}/admin/salaryEmp/getSalaryByLevel',
         type : 'post',
         data : {
              'pk' : $("#posiLevel").val()
         },
         async:false,
         dataType : 'json',
         cache : false,
         error : function(obj) {
              showAjaxHtml({
                    "hidden" : false,
                    "msg" : '保存数据出错~'
              });
         },
         success : function(obj) {
         	 // 赋值给隐藏域
             $("#basicSalaryByLevel").val(obj.basicSalary);
	         $("#salaryByLevel").val(obj.salary);
             if($("#basicSalarySetMode").val()=="1"){//定薪方式为1，直接赋值
	             $("#basicSalary").val(obj.basicSalary);
	             $("#salary").val(obj.salary);
             }
         }
    });
} */

//修改发放方式触发
var clickPayMode=0;
function changePayMode(){
	clickPayMode++;
	var payMode=$("#payMode").val();
	if(payMode=="1"){
		$("#insurLimit,#cash").parent().removeClass("hidden");
		$("#insurLimit,#cash").removeAttr("disabled");
		$("#salarySettleCmp,#cmpUsageType,#payCmp1,#payCmp2,#payCmp3,#payCmp4,#isBasicSalaryPayment,#basicSalaryTemp").parent().addClass("hidden");
		$("#salarySettleCmp,#cmpUsageType,#payCmp1,#payCmp2,#payCmp3,#payCmp4").val("").attr("disabled","true");//隐藏时设置为disabled，跳过校验
		if(clickPayMode>1){
			$("#isTaxable").val("1");
			$("#insurLimit,#cash").val(0);
		}
	}else if(payMode=="2"){
		$("#salarySettleCmp,#cmpUsageType,#insurLimit,#cash,#payCmp1,#payCmp2,#payCmp3,#payCmp4,#isBasicSalaryPayment,#basicSalaryTemp").parent().addClass("hidden");
		$("#salarySettleCmp,#cmpUsageType,#insurLimit,#cash,#payCmp1,#payCmp2,#payCmp3,#payCmp4").val("").attr("disabled","true");
		if(clickPayMode>1){
			$("#isTaxable").val("0");
		}
	}else if(payMode=="3"){
		$("#salarySettleCmp,#cmpUsageType,#payCmp1,#payCmp2,#payCmp3,#payCmp4,#isBasicSalaryPayment").parent().removeClass("hidden");
		$("#salarySettleCmp,#cmpUsageType,#payCmp1,#payCmp2,#payCmp3,#payCmp4").removeAttr("disabled");
		$("#insurLimit,#cash").parent().addClass("hidden");
		$("#insurLimit,#cash").val("").attr("disabled","true");
		changeIsBasicSalaryPayment();
		if(clickPayMode>1){
			$("#isTaxable").val("0");
			$("#isBasicSalaryPayment").val("0");
		}
	}else{
		$("#insurLimit,#cash,#salarySettleCmp,#cmpUsageType,#payCmp1,#payCmp2,#payCmp3,#payCmp4,#isBasicSalaryPayment,#basicSalaryTemp").parent().addClass("hidden");
		$("#insurLimit,#cash,#salarySettleCmp,#cmpUsageType,#payCmp1,#payCmp2,#payCmp3,#payCmp4").val("").attr("disabled","true");
		if(clickPayMode>1){
			$("#isTaxable").val("0");
		}
	}
	if(clickPayMode>1){
		validateRefresh("isTaxable","","page_form_2");//刷新校验
	}
}

//修改定薪方式触发
function changeSetMode(){
	var basicSalarySetMode=$("#basicSalarySetMode").val();
	if(basicSalarySetMode=="1"){
		if($("#posiLevel").val()==""){
			art.dialog({
				content :"请先选择薪酬岗位级别",
			});
			$("#basicSalarySetMode").val("");
			return;
		}
		$("#basicSalary").val($("#basicSalaryByLevel").val());
		$("#salary").val($("#salaryByLevel").val());
		
		//刷新校验
		validateRefresh("basicSalary","","page_form_1");//刷新校验
		validateRefresh("salary","","page_form_1");//刷新校验
	
		$("#basicSalary").attr("disabled",true);
		$("#salary").attr("disabled",true);
	}else {
		$("#basicSalary").val("").removeAttr("disabled");
		$("#salary").val("").removeAttr("disabled");
	}
}

//修改员工状态
function changeStatus(){
	var status=$("#status").val();
	if(status=="ACT" || status==""){
		$("#leaveDate").val("").attr("disabled",true);
		$("#leaveDate").prev().css({"color":"#777777"});
	}else{
		$("#leaveDate").removeAttr("disabled");
		$("#leaveDate").prev().css({"color":"black"});
	}
}

//保险费用额度离焦
function insurLimitBlur(){
	var insurLimit= parseFloat($("#insurLimit").val());
	var salary=parseFloat($("#salary").val());
	if(insurLimit>salary){
		art.dialog({
			content :"保险费用额度不能超过工资",
		});
		$("#insurLimit").val("");
		return;
	}
	
}

//修改入职日期，薪酬岗位类别（前后端），设置医社保开始月份，默认外部人员转正日期
function setMon(){
	var joinDate=$("#joinDate").val();
	var isFront=$("#isFront").val();
	var category=$("#category").val();
	var isSocialInsur=$("#isSocialInsur").val();
	var add6Mon=addmulMonth(joinDate, "6");
	var add3Mon=addmulMonth(joinDate, "3");
	if(isSocialInsur=="1"){
		if(isFront=="1"){
			$("#edmInsurMon").val(add6Mon.replace(/\-/g,""));
		}else{
			$("#edmInsurMon").val(add3Mon.replace(/\-/g,""));
		}
		$("#medInsurMon").val(add6Mon.replace(/\-/g,""));
		$("#houFundMon").val(add6Mon.replace(/\-/g,""));
	}
	
	if(category=="2"){
		$("#regularDate").val(joinDate);
	}
}

//设置人员名称
function setEmpName(){
	var outerEmp=$("#outerEmp").val();
	$("#empName").val(outerEmp);
}

//是否缴纳五险一金
var clickIsSocialInsur=0;
function changeIsSocialInsur(){
	clickIsSocialInsur++;
	var isSocialInsur=$("#isSocialInsur").val();
	if(isSocialInsur=="1"){
		$("#socialInsurParam").prev().children().eq(0).removeClass("hidden");
		$("#houFundMon").prev().children().eq(0).removeClass("hidden");
		$("#medInsurMon").prev().children().eq(0).removeClass("hidden");
		$("#edmInsurMon").prev().children().eq(0).removeClass("hidden");
		$("#houFundMon,#medInsurMon,#edmInsurMon,#socialInsurParam").removeAttr("disabled");
		$("#viewParam").removeClass("hidden");
		if(clickIsSocialInsur>1){
			setMon();
		}
	}else{
		$("#socialInsurParam").prev().children().eq(0).addClass("hidden");
		$("#houFundMon").prev().children().eq(0).addClass("hidden");
		$("#medInsurMon").prev().children().eq(0).addClass("hidden");
		$("#edmInsurMon").prev().children().eq(0).addClass("hidden");
		$("#houFundMon,#medInsurMon,#edmInsurMon,#socialInsurParam").val("").attr("disabled",true);
		$("#viewParam").addClass("hidden");
	}
}

//查看社保公积金详情
function viewParam(){
	var socialInsurParam=$("#socialInsurParam").val();
	if(socialInsurParam==""){
		return;
	}
	Global.Dialog.showDialog("socialInsurParamView", {
        title: "社保公积金参数管理--查看",
        url: "${ctx}/admin/socialInsurParam/goView",
        postData: {pk: $("#socialInsurParam").val()},
        height: 660,
        width: 700
    });
}

//计算n个月之后的日期
function addmulMonth(dtstr, n){  
	var s = dtstr.split("-");
	var yy = parseInt(s[0]);
	var mm = parseInt(s[1]); 
	var dd = parseInt(s[2]); 
	var dt = new Date(yy, mm, dd); 
	var num=dt.getMonth() + parseInt(n);
	if(num/12>1){
	   yy+=Math.floor(num/12) ;
	   mm=num%12;
	}else{
	    mm+=parseInt(n);
	}
	if(mm<10){
		mm="0"+mm;
	}
	return yy + "-" + mm ;
}   

function changeIsBasicSalaryPayment(){
	var isBasicSalaryPayment = $("#isBasicSalaryPayment").val();
	if(isBasicSalaryPayment == "1"){
		$("#basicSalaryTemp").val($("#basicSalary").val());
		$("#basicSalaryTemp").parent().removeClass("hidden");
	}else{
		$("#basicSalaryTemp").parent().addClass("hidden");
	}
}
</script>
</head> 
<body>

<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body">
	    	<div class="btn-group-xs">
	    		<c:if test="${salaryEmp.m_umState!='V'}">
					<button type="button" class="btn btn-system" id="saveBtn" onclick="save()">保存</button>
	    		</c:if>
				<button type="button" class="btn btn-system" onclick="closeWin(true)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" style="margin-bottom: 10px;">  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
			<input hidden="true" id="empCode" name="empCode" value="${salaryEmp.empCode}"/>
			<input hidden="true" id="empName" name="empName" value="${salaryEmp.empName}"/>
			<input hidden="true" id="basicSalaryByLevel" name="basicSalaryByLevel" value="${salaryEmp.basicSalaryByLevel}"/>
			<input hidden="true" id="salaryByLevel" name="salaryByLevel" value="${salaryEmp.salaryByLevel}"/>
			<input hidden="true" id="m_umState" name="m_umState" value="${salaryEmp.m_umState}"/>
				<ul class="ul-form">
					<div class="validate-group row">
						<li class="form-validate">
							<label><span class="required">*</span>人员类别</label>
							<house:xtdm id="category" dictCode="SALEMPCATEGORY" value="${salaryEmp.category}" onchange="changeType()"></house:xtdm>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label><span class="required">*</span>员工</label>
							<input type="text" id="innerEmp" name="innerEmp" />
						</li>
						<li class="form-validate hidden">
							<label ><span class="required">*</span>姓名</label>
							<input type="text" id="outerEmp" name="outerEmp" onblur="setEmpName()" value="${salaryEmp.empName}"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>身份证号</label>
							<input type="text" id="idnum" name="idnum" value="${salaryEmp.idnum}" maxlength="18"/>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate ">
							<label><span class="required">*</span>入职日期</label>
							<input type="text" id="joinDate" name="joinDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${salaryEmp.joinDate}' pattern='yyyy-MM-dd'/>" onchange="setMon()"/>
						</li>
						<li  class="form-validate ">
							<label>转正日期</label>
							<input type="text" id="regularDate" name="regularDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${salaryEmp.regularDate}' pattern='yyyy-MM-dd'/>" />
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate ">
							<label><span class="required">*</span>人员状态</label>	
							<house:xtdm id="status" dictCode="EMPSTS" value="${salaryEmp.status }" onchange="changeStatus()"></house:xtdm>
						</li>	
						<li>
							<label style="color:#777777">离职日期</label>
							<input type="text" id="leaveDate" name="leaveDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${salaryEmp.leaveDate}' pattern='yyyy-MM-dd'/>" />
						</li><!-- disabled="true" -->
					</div>
					<div class="validate-group row">
						<li class="form-validate ">
							<label>签约公司</label>
							<house:dict id="conSignCmp" dictCode=""
								sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
								sqlLableKey="descr" sqlValueKey="code" value="${salaryEmp.conSignCmp}"  />
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>薪酬状态</label>	
							<house:xtdm id="salaryStatus" dictCode="SALARYSTATUS" value="${salaryEmp.salaryStatus }" ></house:xtdm>
						</li>	
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label><span class="required">*</span>一级部门</label>
							<select id="department1" name="department1" value="${salaryEmp.department1}"></select>
						</li>
						<li class="form-validate">
							<label>二级部门</label>
							<select id="department2" name="department2" value="${salaryEmp.department2}"></select>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label><span class="required">*</span>人员属性</label>	
							<house:xtdm id="belongType" dictCode="EMPBELONGTYPE" value="${salaryEmp.belongType }" ></house:xtdm>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>职位</label>
							<input type="text" id="position" name="position" value="${salaryEmp.position}" />
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate" >
							<label><span class="required">*</span>工作日天数</label>
							<input type="text" id="workingDays" name="workingDays" value="${salaryEmp.workingDays}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
						</li>
						<li class="form-validate">
							<label >财务编码</label>
							<input type="text" id="financialCode" name="financialCode" value="${salaryEmp.financialCode }"/>
						</li>
						<!-- <li class="form-validate">
							<label><span class="required">*</span>岗位类别</label> 
							<select id="posiClass" name="posiClass" onchange="getIsFront()"></select>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>岗位级别</label> 
							<select id="posiLevel" name="posiLevel" onchange="getBasicSalary()"></select>
						</li> -->
					</div>
					<div class="validate-group row">
						<%-- <li class="form-validate">
							<label><span class="required">*</span>前后端</label>	
							<house:xtdm id="isFront" dictCode="SALISFRONT" value="${salaryEmp.isFront }" ></house:xtdm>
						</li>	 --%>
						<li>
							<label><span class="required">*</span>薪酬方案</label>
							<house:DictMulitSelect id="salaryScheme" dictCode="" 
								sql="select pk, descr from tSalaryScheme where Expired='F' and Status='1' order by pk " 
								sqlLableKey="descr" sqlValueKey="pk" selectedValue="${salaryEmp.salaryScheme}">
							</house:DictMulitSelect>
						</li>
					</div>
					<div class="row">
						<li>
							<label class="control-textarea">备注</label> 
							<textarea id="remarks" name="remarks" style="height: 50px;">${salaryEmp.remarks }</textarea>
						</li>
					</div>
				</ul>
			</form>
		</div>
	</div>
    <div class="container-fluid" >
    	<ul class="nav nav-tabs" >
	    	<li class="active"><a href="#tab_basicSalary" data-toggle="tab">工资信息</a></li>
	    	<li><a href="#tab_payMode" data-toggle="tab">发放方式</a></li>
	    	<li><a href="#tab_socialInsur" data-toggle="tab">社保公积金</a></li>
	    	<li><a href="#tab_bankInfo" data-toggle="tab">银行卡信息</a></li>
  		</ul>
  		<div class="tab-content">  
  			<div id="tab_basicSalary" class="tab-pane fade in active">
				<div class="pageContent">
					<!--enctype="multipart/form-data"属性必须加，上传文件时会使用META协议对数据进行封装 -->
					<form role="form" action="" method="post" id="page_form_1" class="form-search" target="targetFrame">
						<div style="position: relative;">
							<ul class="ul-form">
							<%-- 	<div class="validate-group row">	
									<li class="form-validate">
										<label><span class="required">*</span>定薪方式</label>	
										<house:xtdm id="basicSalarySetMode" dictCode="BASICSALMODE" value="${salaryEmp.basicSalarySetMode }" ></house:xtdm>
									</li>
								</div>
								<div class="validate-group row">	
									<li class="form-validate" >
										<label><span class="required">*</span>工资</label>
										<input type="text" id="salary" name="salary" value="${salaryEmp.salary}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
									</li>
								</div> --%>
								<div class="validate-group row">
									<li class="form-validate" >
										<label><span class="required">*</span>基本工资</label>
										<input type="text" id="basicSalary" name="basicSalary" value="${salaryEmp.basicSalary}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
									</li>
									<li class="form-validate" >
										<label><span class="required">*</span>缴费标准</label>
										<input type="text" id="insurBase" name="insurBase" value="${salaryEmp.insurBase}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
										<span class="glyphicon glyphicon-question-sign" id = "insurBase_span" 
											data-container="body"  
											data-content="医保和公积金的缴费基数" 
											data-placement="top" 
											style="font-size: 15px;color:rgb(25,142,222);margin-left: 0px">
										</span>
									</li>
								</div>
								<div class="validate-group row">
									<li class="form-validate" >
										<label><span class="required">*</span>职务工资</label>
										<input type="text" id="posiSalary" name="posiSalary" value="${salaryEmp.posiSalary}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
									</li>
									<li class="form-validate ">
										<label>职务变动日期</label>
										<input type="text" id="posiChgDate" name="posiChgDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${salaryEmp.posiChgDate}' pattern='yyyy-MM-dd'/>" />
									</li>
								</div>
								<div class="validate-group row">
									<li class="form-validate" >
										<label><span class="required">*</span>技能补贴</label>
										<input type="number" id="skillSubsidy" name="skillSubsidy" value="${salaryEmp.skillSubsidy}" step="0.1"/>
									</li>
									<li class="form-validate" >
										<label><span class="required">*</span>其他奖励</label>
										<input type="number" id="otherBonuse" name="otherBonuse" value="${salaryEmp.otherBonuse}" step="0.1"/>
									</li>
								</div>
								<div class="validate-group row">
									<li class="form-validate" >
										<label><span class="required">*</span>绩效</label>
										<input type="number" id="perfBonuse" name="perfBonuse" value="${salaryEmp.perfBonuse}" step="0.1"/>
									</li>
									<li class="form-validate" >
										<label><span class="required">*</span>星级补贴</label>
										<input type="number" id="starSubsidy" name="starSubsidy" value="${salaryEmp.starSubsidy}" step="0.1"/>
									</li>
								</div>
								<div class="validate-group row">
									<li class="form-validate" >
										<label><span class="required">*</span>其他补贴</label>
										<input type="number" id="otherSubsidy" name="otherSubsidy" value="${salaryEmp.otherSubsidy}" step="0.1"/>
									</li>
								</div>
							</ul>
						 </div>	
					 </form>
					</div>
		  		</div>
		  		<!-- 发放方式-->
		  		<div id="tab_payMode"  class="tab-pane fade"> 
					<div class="pageContent">
						<div class="btn-panel">
							<form action="" method="post" id="page_form_2" class="form-search">
								<ul class="ul-form" >
									<div class="validate-group row">	
										<li class="form-validate">
											<label style="width:110px"><span class="required">*</span>发放方式</label>	
											<house:xtdm id="payMode" dictCode="SALPAYMODE" value="${salaryEmp.payMode }" onchange="changePayMode()"></house:xtdm>
										</li>
										<li class="form-validate">
											<label style="width:110px"><span class="required">*</span>是否计税</label>	
											<house:xtdm id="isTaxable" dictCode="YESNO" value="${salaryEmp.isTaxable }"></house:xtdm>
										</li>	
									</div>
									<div class="validate-group row ">
										<li class="form-validate hidden" >
											<label style="width:110px"><span class="required">*</span>保险费用额度</label>
											<input type="text" id="insurLimit" name="insurLimit" value="${salaryEmp.insurLimit}"  onblur="insurLimitBlur()"
												onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
										</li>
										<li class="form-validate hidden" >
											<label style="width:110px"><span class="required">*</span>现金额度</label>
											<input type="text" id="cash" name="cash" value="${salaryEmp.cash}" 
												onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
										</li>
										<li class="form-validate hidden">
											<label style="width:110px"><span class="required">*</span>薪酬结算企业</label>
											<house:dict id="salarySettleCmp" dictCode="" sql="select pk,cast(pk as nvarchar(20))+' '+descr descr from tSalarySettleCmp a where a.Expired='F' " 
											 sqlValueKey="pk" sqlLableKey="descr" value="${salaryEmp.salarySettleCmp}" ></house:dict>							
										</li>
										<li class="form-validate hidden">
											<label style="width:110px"><span class="required">*</span>用途备注</label>	
											<house:xtdm id="cmpUsageType" dictCode="CMPUSAGETYPE" value="${salaryEmp.cmpUsageType }" ></house:xtdm>
										</li>
									</div>	
									<div class="validate-group row">
										<li class="form-validate hidden">
											<label style="width:110px"><span class="required">*</span>出款公司1</label>
											<house:dict id="payCmp1" style="width:115px" dictCode=""
												sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
												sqlLableKey="descr" sqlValueKey="code" value="${salaryEmp.payCmp1}"  />
												<input style="width:40px" type="text" id="weight1" name="weight1" value="${salaryEmp.weight1}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
												<span style="position: absolute;left:295px;width: 30px;top:5px;">%</span>
										</li>
										<li class="form-validate hidden">
											<label style="width:110px">出款公司2</label>
											<house:dict id="payCmp2" style="width:115px" dictCode=""
												sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
												sqlLableKey="descr" sqlValueKey="code" value="${salaryEmp.payCmp2}"  />
												<input style="width:40px" type="text" id="weight2" name="weight2" value="${salaryEmp.weight2}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
												<span style="position: absolute;left:295px;width: 30px;top:5px;">%</span>
										</li>
									</div>
									<div class="validate-group row">
										<li class="form-validate hidden">
											<label style="width:110px">出款公司3</label>
											<house:dict id="payCmp3" style="width:115px" dictCode=""
												sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
												sqlLableKey="descr" sqlValueKey="code" value="${salaryEmp.payCmp3}"  />
												<input style="width:40px" type="text" id="weight3" name="weight3" value="${salaryEmp.weight3}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
												<span style="position: absolute;left:295px;width: 30px;top:5px;">%</span>
										</li>
										<li class="form-validate hidden">
											<label style="width:110px">出款公司4</label>
											<house:dict id="payCmp4" style="width:115px" dictCode=""
												sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
												sqlLableKey="descr" sqlValueKey="code" value="${salaryEmp.payCmp4}"  />
												<input style="width:40px" type="text" id="weight4" name="weight4" value="${salaryEmp.weight4}" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
												<span style="position: absolute;left:295px;width: 30px;top:5px;">%</span>
										</li>
									</div>
									<div class="validate-group row">
										<li class="form-validate">
											<label style="width:110px"><span class="required">*</span>基本工资正常出款</label>	
											<house:xtdm id="isBasicSalaryPayment" dictCode="YESNO" value="${salaryEmp.isBasicSalaryPayment }" onchange="changeIsBasicSalaryPayment()"></house:xtdm>
										</li>	
										<li class="form-validate" >
											<label style="width:110px">基本工资</label>
											<input type="text" id="basicSalaryTemp" name="basicSalaryTemp" value="${salaryEmp.basicSalary}" readonly/>
										</li>
									</div>
								</ul>
							</form>
						</div>
		    		</div>
		    	</div>
		    	<!--社保公积金 -->
	    		<div id="tab_socialInsur"  class="tab-pane fade"> 
					<div class="pageContent">
						<div class="btn-panel">
							<form action="" method="post" id="page_form_3" class="form-search">
								<ul class="ul-form">
									<div class="validate-group row">	
										<li class="form-validate">
											<label style="width:120px"><span class="required">*</span>是否缴纳五险一金</label>	
											<house:xtdm id="isSocialInsur" dictCode="YESNO" value="${salaryEmp.isSocialInsur }" onchange="changeIsSocialInsur()"></house:xtdm>
										</li>
									</div>
									<div class="validate-group row">	
										<li class="form-validate">
											<label style="width:120px"><span class="required">*</span>社保公积金参数</label>
											<house:dict id="socialInsurParam" dictCode="" sql="select pk,cast(pk as nvarchar(20))+' '+descr descr from tSocialInsurParam a where a.Expired='F' " 
											 sqlValueKey="pk" sqlLableKey="descr" value="${salaryEmp.socialInsurParam}" style="width:215px"></house:dict>							
											 <span id="viewParam" onclick="viewParam()" style="position: absolute;left:375px;width: 30px;top:4px;color:blue;cursor:pointer;">详情</span>
										</li>
									</div>
									<div class="validate-group row">
										<li class="form-validate">
											<label style="width:120px"><span class="required">*</span>养老保险开始月份</label>
											<input type="text" id="edmInsurMon" name="edmInsurMon" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})" value="${salaryEmp.edmInsurMon}" />
										</li>
									</div>
									<div class="validate-group row">
										<li class="form-validate">
											<label style="width:120px"><span class="required">*</span>医保开始月份</label>
											<input type="text" id="medInsurMon" name="medInsurMon" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})" value="${salaryEmp.medInsurMon}" />
										</li>
									</div>
									<div class="validate-group row">
										<li class="form-validate">
											<label style="width:120px"><span class="required">*</span>公积金开始月份</label>
											<input type="text" id="houFundMon" name="houFundMon" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})" value="${salaryEmp.houFundMon}" />
										</li>
									</div>
								</ul>
							</form>
						</div>
		    		</div>
		    	</div>
		    	<!--银行卡信息 -->
		    	<div id="tab_bankInfo"  class="tab-pane fade"> 
		    		<div class="pageContent">
						<div class="body-box-form">
							<div class="panel panel-system" >
							    <div class="panel-body">
							      	<div class="btn-group-xs" >
										<button type="submit" class="btn btn-system " id="addBank">
											<span>新增</span>
										</button>
										<button type="button" class="btn btn-system " id="updateBank">
											<span>编辑</span>
										</button>
										<button type="button" class="btn btn-system" id="deleteBank">
											<span>删除</span>
										</button>
										<button type="button" class="btn btn-system" id="viewBank">
											<span>查看</span>
										</button>
									</div>
								</div>
							</div>
						</div>
						<div id="content-list" style="width:750px;height:150px;">
							<table id= "dataTable_bank"></table>
						</div>	
		    		</div>
				</div>
		  </div>		  	
  	</div>
</div>
</body>
</html>


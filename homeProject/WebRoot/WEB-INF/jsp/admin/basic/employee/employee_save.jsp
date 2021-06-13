<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%><!DOCTYPE html>
<html>
<head>
<title>员工信息--新增</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_applicant.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_position.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
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
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");
	$("#department1,#department2,#department3").attr("disabled",true);
});
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","oldDepartment1","oldDept");
});
$(function() {
	$("#department").openComponent_department({
		condition:{isEmp:"1", isOutChannel: "0"},
		callBack:function(data){
			var path=data.path;
			var pathArr=path.split("/");
			var department1="";
			var department2="";
			var department3="";
			if(pathArr.length==1){
				department1=getCodeByDept("tDepartment1",pathArr[0]);
			}else if(pathArr.length==2){
				department1=getCodeByDept("tDepartment1",pathArr[0]);
				department2=getCodeByDept("tDepartment2",pathArr[1]);
			}else if(pathArr.length==3){
				department1=getCodeByDept("tDepartment1",pathArr[0]);
				department2=getCodeByDept("tDepartment2",pathArr[1]);
				department3=getCodeByDept("tDepartment3",pathArr[2]);
			}
			
			Global.LinkSelect.setSelect({firstSelect:"department1",
							firstValue:department1,
							secondSelect:"department2",
							secondValue:department2,
							thirdSelect:"department3",
							thirdValue:department3,
							});
		}
	});
	$("#openComponent_department_department").attr("readonly",true);	
	$("#page_form_1").bootstrapValidator("addField", "openComponent_position_position", {
        validators: {         
            remote: {
	            message: '',
	           url: '${ctx}/admin/position/getPosition',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }
    });
    $("#page_form_1").bootstrapValidator("addField", "openComponent_position_secondPosition", {
        validators: {         
            remote: {
	            message: '',
	           url: '${ctx}/admin/position/getPosition',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }
    });

	$("#applicantPK").openComponent_applicant({
		showLabel:"${applicant.nameChi}",
		showValue:"${applicant.pk}",
		condition:{
			status:"2"
		},
		callBack: getData
	});
	$("#position").openComponent_position({
		showLabel:"${position.desc2}",
		showValue:"${position.code}",
		condition:{
			status:"3,4,5"
		} , 
	    callBack:function(data){
	    	validateRefresh('openComponent_position_position')	;
			$("#positionType").val(data.type);
	 		if (data.type=='6'||$("#secondPositionType").val()=='6'){
				$("#prjLevel").attr("disabled",false); 
			}else{
				$("#prjLevel").val(' ');
				$("#prjLevel").attr("disabled",true);
			}		
		} 
	});
	$("#secondPosition").openComponent_position({
		showLabel:"${position.desc1}",
		showValue:"${position.code}",
		condition:{
			status:"3,4,5"
		} ,
		callBack:function(data){
			validateRefresh('openComponent_position_secondPosition');
			$("#secondPositionType").val(data.type);
	 		if (data.type=='6'||$("#positionType").val()=='6'){
				$("#prjLevel").attr("disabled",false);
	 		}else{
				$("#prjLevel").val();
				$("#prjLevel").attr("disabled",true);
			}
		} 
	});
	function getData(data){
		if (!data) return;
		$("#nameChi").val(data.namechi);
		$("#gender").val(data.gender);
		$("#birth").val(data.birth);
		$("#address").val(data.address);
		$("#idnum").val(data.idnum);
		$("#phone").val(data.phone);
		$("#birtPlace").val(data.birtplace);
		$("#edu").val(data.edu);
		$("#school").val(data.school);
		$("#schDept").val(data.schdept);				
	}
});	
$(function(){
	
	var lastCellRowId;
	var gridOption = {
		height:$(document).height()-$("#content-list").offset().top-55,
		rowNum:10000000,
		url:"*****",
		postData: "1",
		styleUI: "Bootstrap", 
		colModel : [
			{name: "pk", index: "pk", width: 84, label: "pk", sortable: true, align: "left", hidden: true},
			{name: "number", index: "number", width: 79, label: "编号", sortable: true, align: "left", hidden: true},
			{name: "company", index: "company", width: 172, label: "公司", sortable: true, align: "left"},
			{name: "position", index: "position", width: 79, label: "职位", sortable: true, align: "left"},
			{name: "salary", index: "salary", width: 76, label: "工薪", sortable: true, align: "left"},
			{name: "leaversn", index: "leaversn", width: 161, label: "离职原因", sortable: true, align: "left"},
			{name: "begindate", index: "begindate", width: 88, label: "开始时间", sortable: true, align: "left", formatter: formatDate},
			{name: "enddate", index: "enddate", width: 81, label: "结束时间", sortable: true, align: "left", formatter: formatDate},
			{name: "lastupdatedby", index: "lastupdatedby", width: 96, label: "最后修改人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 76, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 124, label: "最后修改时间", sortable: true, align: "left", formatter: formatDate}
			],
	};
	var gridOption1 = {
		height:$(document).height()-$("#content-list1").offset().top-55,
		rowNum:10000000,
		url:"*****",
		postData: "1",
		styleUI: "Bootstrap", 
		colModel :[
			{name: "certification", index: "certification", width: 720, label: "证书", sortable: true, align: "left"}
			],
	};
	Global.JqGrid.initJqGrid("dataTable_work",gridOption);
	Global.JqGrid.initJqGrid("dataTable_per",gridOption1);
	//新增
	$("#addWork").on("click",function(){
		Global.Dialog.showDialog("addWork",{
			title:"员工工作经验——新增",
			url:"${ctx}/admin/employee/goWorkAdd",
			//postData:{},
			height: 480,
			width:750,
		    returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							company:v.company,
							position:v.position,
							salary:v.salary,
							begindate:v.begindate,
							enddate:v.enddate,
							leaversn:v.leaversn,
							expired:v.expired,
							actionlog:v.actionlog,
							lastupdate:v.lastupdate,
							lastupdatedby:v.lastupdatedby
						};
						Global.JqGrid.addRowData("dataTable_work",json);
					});
				}
			} 
		});
	});
	$("#addPer").on("click",function(){
		Global.Dialog.showDialog("addPer",{
			title:"员工证书——新增",
			url:"${ctx}/admin/employee/goPerAdd",
			//postData:{},
			height: 300,
			width:650,
		    returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							certification:v.certification,
						};
						Global.JqGrid.addRowData("dataTable_per",json);
					});
				}
			} 
		});
	});
	$("#updateWork").on("click",function(){
		var id = $("#dataTable_work").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		var rowId=$("#dataTable_work").jqGrid("getGridParam","selrow");
		var param =$("#dataTable_work").jqGrid("getRowData",rowId);
		Global.Dialog.showDialog("updateWork",{
			title:"员工工作经验——编辑",
		  	url:"${ctx}/admin/employee/goWorkUpdate",
		  	postData:param,
		  	height: 480,
			width:750,
		    returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							company:v.company,
							position:v.position,
							salary:v.salary,
							begindate:v.begindate,
							enddate:v.enddate,
							leaversn:v.leaversn,
							expired:v.expired,
							actionlog:v.actionlog,
							lastupdate:v.lastupdate,
							lastupdatedby:v.lastupdatedby
						  };
			   			$("#dataTable_work").setRowData(rowId,json);
					});
				}
			} 
		});
	});
	
	$("#updatePer").on("click",function(){
		var id = $("#dataTable_per").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		var rowId=$("#dataTable_per").jqGrid("getGridParam","selrow");
		var param =$("#dataTable_per").jqGrid("getRowData",rowId);
		Global.Dialog.showDialog("updatePer",{
			title:"员工信息证书——编辑",
		  	url:"${ctx}/admin/employee/goPerUpdate",
		  	postData:param,
		  	height: 300,
			width:650,
		    returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							certification:v.certification,
						  };
			   			$("#dataTable_per").setRowData(rowId,json);
					});
				}
			} 
		});
	});
	
	//删除
	$("#deleteWork").on("click",function(){
		var id = $("#dataTable_work").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		art.dialog({
			content:"是删除该记录？",
			lock: true,
			width: 100,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable_work",id);
			},
			cancel: function () {
				return true;
			}
		});
	});
	$("#deletePer").on("click",function(){
		var id = $("#dataTable_per").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		art.dialog({
			content:"是删除该记录？",
			lock: true,
			width: 100,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable_per",id);
			},
			cancel: function () {
				return true;
			}
		});
	});
	
	//查看
	$("#viewWork").on("click",function(){
		var id = $("#dataTable_work").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		var rowId=$("#dataTable_work").jqGrid("getGridParam","selrow");
		var param =$("#dataTable_work").jqGrid("getRowData",rowId);
		Global.Dialog.showDialog("workUpdate",{
			title:"员工工作经验——查看",
		  	url:"${ctx}/admin/employee/goWorkView",
		  	postData:param,
		  	height: 480,
			width:750,
		});
	});
	$("#viewPer").on("click",function(){
		var id = $("#dataTable_per").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		var rowId=$("#dataTable_per").jqGrid("getGridParam","selrow");
		var param =$("#dataTable_per").jqGrid("getRowData",rowId);
		Global.Dialog.showDialog("perUpdate",{
			title:"员工信息证书——查看",
		  	url:"${ctx}/admin/employee/goPerView",
		  	postData:param,
		  	height: 300,
			width:650,
		});
	});
});
function onUpLoadImg(file){
	//file代表选择的图片
    if (file.files && file.files[0]){
   		/* var img = document.getElementById('img'); */
   		var img = $("#imgs");
        //准备一个文件读取器对象，并告诉它文件读取完毕之后要做什么。
        var reader = new FileReader();
        //成功读取了图片信息后，把读取结果赋予
        //FileReader.readAsDataURL()
        //开始读取指定的Blob中的内容。一旦完成，result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容。
        reader.readAsDataURL(file.files[0]);
        reader.onload = function(evt){
        	imgs.src= evt.target.result;
        };
    }
}

function changeImg(){
    $("#imgs").attr("src","http://localhost:8080/homeProject/homePhoto/prjProg/1541380596628.png");
    //document.getElementById("files").files[0]=="";
    var file = document.getElementById('files');
    //file.value = ''; //虽然file的value不能设为有字符的值，但是可以设置为空值
    //或者
    file.outerHTML = file.outerHTML; //重新初始化了file的html
    
}

function save(){
	$("form").bootstrapValidator("validate");
	if(!$("form").data("bootstrapValidator").isValid()) return;
	var pkStr = $.trim($("#pk").val());
	var ptAStr = $.trim($("#openComponent_applicant_pk").val());
	
	if(pkStr==""){
		$("#pk").val($("#openComponent_applicant_pk").val());
	}
	if(pkStr=="" && ptAStr==""){
		$("#pk").val("-1");
	}
    if(/* $.trim($("#pk").val())=="" || */ $.trim($("#nameChi").val())=="" || $.trim($("#department1").val())==""
        || $.trim($("#position").val())=="" || $.trim($("#isSchemeDesigner").val())==""){
        if($.trim($("#department").val())=="" && $.trim($("#position").val())!=""){
        	art.dialog({
	    		content: "请填入完整信息，所属部门未选择",
	    	});
        }
        else{        
	    	art.dialog({
	    		content: "请填入完整信息",
	    	});
    	}
    	return false;
    } 
    if($.trim($("#idnum").val())==""){
       art.dialog({content : '身份证号不能为空'});
       return false;
    }else{
   	   var idnum = $("#idnum").val();
	   var reg = /(^\d{15}$)|(^\d{17}([0-9]|X|x)$)/;
	   if(reg.test(idnum)===false){
	       art.dialog({
	       		content:"身份证输入不合法"
	       });
	       return false;
	   }  
    }
    if($("#phone").val()/1!=$("#phone").val()){
    	art.dialog({
    		content:"电话号码必须为数字"
    	});
    	return false;
    }
    if (($("#positionType").val()=='6'||$("#secondPositionType").val()=='6')&& $("#prjLevel").val()==""){
   		art.dialog({
    		content:"请填写项目经理星级"
    	});
    	return false;
	}	
	if($.trim($("#type").val())==""){
		art.dialog({
			content:"请选择员工类型",
		});
		return;
	}	
 	var dataTable_workJSON= Global.JqGrid.allToJson("dataTable_work");
 	var param =JSON.stringify(dataTable_workJSON);
 	var dataTable_perJSON= Global.JqGrid.allToJson("dataTable_per");
 	var paramC =JSON.stringify(dataTable_perJSON);
 	var formData = new FormData();
 	formData.append("number",$("#number").val());
	formData.append("file",document.getElementById("files").files[0]);
	formData.append("refCode",$("#refCode").val());
	formData.append("icnum",$("#icnum").val());
	formData.append("nameChi",$("#nameChi").val());
	formData.append("nameEng",$("#nameEng").val());
	formData.append("gender",$("#gender").val());
	formData.append("department",$("#department").val());
	formData.append("department1",$("#department1").val());
	formData.append("department2",$("#department2").val());
	formData.append("department3",$("#department3").val());
	formData.append("basicWage",0);
	formData.append("position",$("#position").val());
	formData.append("phone",$("#phone").val());
    formData.append("joinDate",$("#joinDate").val());
    formData.append("leaveDate",$("#leaveDate").val());
    formData.append("status",$("#status").val());
    formData.append("isLead",$("#isLead").val());//DESCR
    formData.append("leadLevel",$("#leadLevel").val());//Descr
    formData.append("remarks",$("#remarks").val());
    formData.append("idnum",$("#idnum").val());
    formData.append("birtPlace",$("#birtPlace").val());
    formData.append("secondPosition",$("#secondPosition").val());
    formData.append("chgRemarks",$("#chgRemarks").val());//+
    formData.append("connMan",$("#connMan").val());
    formData.append("connPhone",$("#connPhone").val());
    formData.append("documentNo",$("#documentNo").val()); 
	formData.append("graduationDate",$("#graduationDate").val());//+
	formData.append("posiChgDate",$("#posiChgDate").val());
	formData.append("carNo",$("#carNo").val());
	formData.append("regularDate",$("#regularDate").val());//+
	formData.append("applicantPK",$("#pk").val());
	formData.append("isPreTrain",$("#isPreTrain").val());
	formData.append("isPreTrainPass",$("#isPreTrainPass").val());
	formData.append("courseCode",$("#courseCode").val());
	formData.append("managerRegularDate",$("#managerRegularDate").val());
	formData.append("oldDeptDate",$("#oldDeptDate").val());
	formData.append("perfBelongMode",$("#perfBelongMode").val());
	formData.append("oldDept",$("#oldDept").val());//+
	formData.append("personChgDate",$("#personChgDate").val());
	formData.append("idValidDate",$("#idValidDate").val());
	formData.append("prjLevel",$("#prjLevel").val());//+
	formData.append("IsSupvr",$("#IsSupvr").val());//+
    formData.append("isSchemeDesigner",$("#isSchemeDesigner").val());
    formData.append("marryStatus",$("#marryStatus").val());//+
    formData.append("reTurnDate",$("#reTurnDate").val());
    formData.append("conBeginDate",$("#conBeginDate").val());
   	formData.append("conEndDate",$("#conEndDate").val());
   	formData.append("conSignCmp",$("#conSignCmp").val());
   	formData.append("type",$("#type").val());
   	   
    formData.append("school",$("#school").val());
    formData.append("schDept",$("#schDept").val());
    formData.append("address",$("#address").val());
    formData.append("birth",$("#birth").val());
    formData.append("edu",$("#edu").val());
    formData.append("flag",1);
    formData.append("empExpDetailJson",param);
    formData.append("empCertification",paramC);
	$.ajax({
		url:"${ctx}/admin/employee/uploadEmpImg                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ",
		type:"POST",
		data:formData,
		contentType:false,
		processData:false,
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
			} 
			if(obj.datas==1){
				//onPourse();
				 art.dialog({
						content : obj.msg,
						width : 200,
						okValue: '保存',
					    ok: function () {
					       	secondSave();
					        return false;
					    },
					    cancelValue: '取消',
					    cancel: function () {
					       
					    }
					});
			}
			else {
				art.dialog({
					content : obj.msg,
					width : 200,
				});
		   	}
		}
	});	
    var secondSave= function(){
    	formData.append("flag",2);
	   	$.ajax({
			url:"${ctx}/admin/employee/uploadEmpImg",
			type:"POST",
			data:formData,
			contentType:false,
			processData:false,
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
						beforeunload : function(){
							closeWin();
						}
					});
				} else {
					art.dialog({
						content : obj.msg,
						width : 200,
					});
			   	}
			}
		});		
	};
}  
$(function() {
	$("form").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			connPhone: {
				validators : {
					numeric: {
	                    message:'只能输入数字'
	                }
				}
			},
			
			phone: {
				validators : {
					notEmpty: {
						message: "手机号不能为空"
					},
					numeric: {
	                    message:'只能输入数字'
	                }
				}
			}
 		},
		submitButtons : 'input[type="submit"]'
	}); 
});
//根据所属部门编号获取一二三级部门编号
function getCodeByDept(tableName,department){
	var code="";
	$.ajax({
		url:"${ctx}/admin/employee/getCodeByDept",
		type:'post',
		data:{tableName:tableName,department:department},
		cache:false,
		async:false,
		error:function(obj){
			
		},
		success:function(obj){
			if (obj!=""){
				code=obj;
			}
		}
	});
	return code;
}

function changeStatus(){
	if($("#status").val()=="WRT"){
		$("#reTurnDate").attr("disabled",false);
		$("#chager").css("color","#000000");
		$("#leaveDate").attr("disabled",false);
		$("#chagel").css("color","#000000");
	}
	if($("#status").val()=="SUS"){
		$("#reTurnDate").attr("disabled",true);
		$("#chager").css("color","#777777");
		$("#leaveDate").attr("disabled",false);
		$("#chagel").css("color","#000000");	
	}
	if($("#status").val()=="ACT"){
		$("#reTurnDate").attr("disabled",true);
		$("#chager").css("color","#777777");
		$("#leaveDate").attr("disabled",true);	
		$("#chagel").css("color","#777777");
	}
	if($("#status").val()=="OTH"){
		$("#reTurnDate").attr("disabled",true);
		$("#chager").css("color","#777777");
		$("#leaveDate").attr("disabled",false);
		$("#chagel").css("color","#000000");	
	}
};
</script>
</head> 
<body>

<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body">
	    	<div class="btn-group-xs">
				<button type="button" class="btn btn-system" onclick="saveNew()">保存</button>
				<button type="button" class="btn btn-system" onclick="closeWin(true)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" style="margin-bottom: 10px;">  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
			<input hidden="true" id="positionType" name="positionType" value="${employee.positionType}"/>
			<input hidden="true" id="secondPositionType" name="secondPositionType" value="${employee.secondPositionType}"/>
				<ul class="ul-form">
					<div class="validate-group">
						<li>
							<label><span class="required"></span>员工编号</label>
							<input type="text" id="number"  name="number" placeholder="保存时自动生成"/ readonly="readonly">
						</li>
						<li>
							<label><span></span>参考编号</label>
							<input type="text" id="refCode" name="refCode" value="${employee.refCode}"/>
						</li>
					</div>
				</ul>
			</form>
		</div>
	</div>
    <div class="container-fluid" >
    	<ul class="nav nav-tabs" >
	    	<li class="active"><a href="#tab_employeeSaveMain" data-toggle="tab">主项目</a></li>
	    	<li><a href="#tab_employeeSaveOther" data-toggle="tab">其他项目</a></li>
	    	<li><a href="#tab_employeeSavePerformance" data-toggle="tab">证书</a></li>
	    	<li><a href="#tab_employeeSaveSertificate" data-toggle="tab">业绩相关设置</a></li>
	    	<li><a href="#tab_employeeSaveWork" data-toggle="tab">工作经验</a></li>
  		</ul>
  		<div class="tab-content">  
  			<div id="tab_employeeSaveMain" class="tab-pane fade in active">
				<div class="pageContent">
					<!--enctype="multipart/form-data"属性必须加，上传文件时会使用META协议对数据进行封装 -->
					<form role="form" action="" method="post" id="page_form_1" class="form-search" target="targetFrame">
					    <input type="text" name="photoName" id="photoName" hidden="true"/>
						<div style="position: relative;">
						    <div style=" width: 300px; float: left">
								<ul class="ul-form">
									<div class="validate-group row">			  
									<li class="form-validate">
										<label>应聘序号</label>
										<input type="text" id="applicantPK" name="applicantPK"/>
									</li>
									<li>	
										<label>IC卡号</label>
										<input type="text" id="icnum" name="icnum"/>
									</li>
									<li>
										<label><span class="required">*</span>中文姓名</label>
										<input type="text" id="nameChi" name="nameChi"/>
									</li>
									<li>
										<label>艺名</label>
										<input type="text" id="nameEng" name="nameEng"/>
									</li>
									<li>
										<label>性别</label>
										<house:xtdm id="gender" dictCode="GENDER" value="M"></house:xtdm>		
									</li>
									<li>
										<label>出生日期</label>
										<input type="text" id="birth" name="birth" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.birth}' pattern='yyyy-MM-dd'/>" />
									</li>
									</div>
								</ul>
							</div>
							<div style="width: 345px; height:200px;float: right; margin-right: 20px">
										  <!-- 包含隐藏加载题目和点击按钮，选中一张图片后触发onchange事件-->
								<div style="width: 60px; height: 200px;float: left;margin-top: 20px;">
									<p style="margin-left: 10px;">员工相片</p>
									<a href="javascript:;" class="linkFile">加载相片
									<input type="file" id="files" name="file" onchange="uploadEmpPic(this)"/>
									</a>
									<button onclick="removeImg()" class="clearFile">清除相片</button>
								</div>	
								<div style="float: right;margin-right: 120px;margin-top: 20px;">		  
									<img id="imgs" src="" width="150px" height="150px">
                                </div>							
							</div>
							<div style="width: 750px; float: left;">
								<ul class="ul-form">
									<div class="validate-group row">
										<li>
											<label>家庭住址</label>
											<input type="text" id="address" name="address" style="width: 450px;" value="${employee.address}"/>
										</li>
									</div>
									<div class="validate-group row">	
										<li class="form-validate">
											<label>身份证号</label>
											<input type="text" id="idnum" name="idnum" value="${employee.idnum}" maxlength="18"/>
										</li>
										<li><label><span class="required">*</span>所属部门</label> <input type="text"
												style="width:160px;" id="department" name="department" />
										</li>
									</div>	
									<div class="validate-group row">	
										<li>
											<label>身份证有效期</label>
											<input type="text" id="idValidDate" name="idValidDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.idValidDate}' pattern='yyyy-MM-dd'/>" />
										</li>
										<li class="form-validate">
											<label>一级部门</label>
											<select id="department1" name="department1" value="${employee.department1}"></select>
										</li>
									</div>
									<div class="validate-group row">
										<li class="form-validate">
											<label>电话</label>
											<input type="text" id="phone" name="phone" value="${employee.phone}" maxlength="11"/>
										</li>
										<li>
											<label>二级部门</label>
											<select id="department2" name="department2" value="${employee.department2}"></select>
										</li>
									</div>
									<div class="validate-group row">
										<li>
											<label>籍贯</label>
											<input type="text" id="birtPlace" name="birtPlace" value="${employee.birtPlace}"/>
										</li>
										<li>
											<label>三级部门</label>
											<select id="department3" name="department3" value="${employee.department3}"></select>
										</li>																			
										<li class="form-validate">
											<label><span class="required">*</span>职位编号</label>
											<input type="text" id="position" name="position" value="${employee.position}" />
										</li>		
										<li>
											<label>文化程度</label>
											<house:xtdm id="edu" dictCode="EDU" value="${employee.edu}"></house:xtdm>
										</li>	
										<li class="form-validate">
											<label>副职编号</label>
											<input type="text" id="secondPosition" name="secondPosition" value="${employee.secondPosition}"/>
										</li>
										<li>
											<label>毕业日期</label>
											<input type="text" id="graduationDate" name="graduationDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.graduationDate}' pattern='yyyy-MM-dd'/>" />
										</li>						
										<li>
											<label>是否部门领导</label>
											<house:xtdm id="isLead" dictCode="YESNO" value="0"></house:xtdm>
										</li> 
										<li>
											<label>毕业院校</label>
											<input type="text" id="school" name="school" value="${employee.school}"/>
										</li>
										<li>
											<label>领导级别</label>
											<house:xtdm id="leadLevel" dictCode="LEADLEVEL" value="0"></house:xtdm>
										</li> 
										<li>
											<label>专业</label>
											<input type="text" id="schDept" name="schDept" value="${employee.schDept}"/>
										</li>
										<li>
											<label><span class="required">*</span>方案设计师</label>
											<house:xtdm id="isSchemeDesigner" dictCode="YESNO" value="0"></house:xtdm>
										</li>
										<li>
											<label><span class="required">*</span>加入日期</label>
											<input type="text" id="joinDate" name="joinDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.joinDate}' pattern='yyyy-MM-dd'/>"/>
										</li>
										<li>
											<label>状态</label>	
											<house:xtdm id="status" dictCode="EMPSTS" value="ACT" onchange="changeStatus()"></house:xtdm>
										</li>
										 <li>
											<label id="chagel" style="color:#777777">离开日期</label>
											<input type="text" id="leaveDate" name="leaveDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.leaveDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
										</li>
										<li>
											<label id="chager" style="color:#777777">返岗日期</label>
											<input type="text" id="reTurnDate" name="reTurnDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.reTurnDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
										
										</li>
										<li>
											<label>转正日期</label>
											<input type="text" id="regularDate" name="regularDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.regularDate}' pattern='yyyy-MM-dd'/>" />
										</li>	
										<li>
											<label>工龄</label>
											<input type="text" id="cmpActNo" name="cmpActNo"/>
										</li>	
										<li>
											<label>婚姻</label>
											<house:xtdm dictCode="MARRYSTATUS" id="marryStatus" ></house:xtdm>
										</li>
										<li>
											<label>人事调岗时间</label>
										    <input type="text" id="personChgDate" name="personChgDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.personChgDate}' pattern='yyyy-MM-dd'/>" />
										</li>
										<li>
											<label>合同开始时间</label>
										    <input type="text" id="conBeginDate" name="conBeginDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.conBeginDate}' pattern='yyyy-MM-dd'/>" />
										</li>
										<li>
											<label>合同截止时间</label>
										    <input type="text" id="conEndDate" name="conEndDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.conEndDate}' pattern='yyyy-MM-dd'/>" />
										</li> 	
										<li>
											<label>项目经理星级</label>
											<house:dict id="prjLevel" dictCode=""
			                                    sql="select code, descr from tPrjLevel where Expired='F' order by code  "
			                                    sqlLableKey="descr" sqlValueKey="code" value="${employee.prjLevel}"  />
										</li>		
										<li>
											<label>合同签约公司</label>
											<house:dict id="conSignCmp" dictCode=""
												sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
												sqlLableKey="descr" sqlValueKey="code" value="${employee.conSignCmp}"  />
										</li>
										<li>
											<label><span class="required">*</span>员工类型</label>
											<house:xtdm dictCode="EMPTYPE" id="type" unShowValue="3"></house:xtdm>
										</li>														
										<li>
											<label class="control-textarea">备注</label> 
											<textarea id="remarks" name="remarks" style="height: 50px;"></textarea>
										</li>
									</div>
								 </ul>	
						 	</div>
						 </div>	
					 </form>
					</div>
		  		</div>
		  		<!-- 其他项目-->
		  		<div id="tab_employeeSaveOther"  class="tab-pane fade"> 
					<div class="pageContent">
						<div class="btn-panel">
							<form action="" method="post" id="page_form_2" class="form-search">
								<ul class="ul-form" style="height: 450px;">
									<li>
										<label style="width: 120px;" for="documentNo">紧急联系人</label>
										<input type="text" id="connMan" name="connMan" value="${employee.connMan}"/>
									</li>
									<li>
										<label style="width: 120px;" for="payeeCode">档案编号</label>
										<input type="text" id="documentNo" name="documentNo" value="${employee.documentNo}"/>
									</li>
									<li class="form-validate">
										<label style="width: 120px">紧急联系电话</label>
										<input type="text" id="connPhone" name="connPhone" value="${employee.connPhone}" maxlength="11"/>
									</li>
									<li>
										<label style="width: 120px;" for="laborAmount">车牌号</label>
										<input type="text" id="carNo" name="carNo" value="${employee.carNo}"/>
									</li>
									<li class="form-validate" hidden="true">
										<label style="width: 120px;" for="laborAmount">基本工资</label>
										<input type="text" id="basicWage" name="basicWage" value="${employee.basicWage}"/>
									</li>
									<li>
										<label style="width:120px">是否参加岗前培训</label>
										<house:dict id="isPreTrain" dictCode="" sql="select cbm,cbm+' '+note note from tXTDM a where a.ID='YESNO' " 
										 sqlValueKey="cbm" sqlLableKey="note" value="${employee.isPreTrain}" disabled="true"></house:dict>							
									</li>
									<li>
										<label style="width: 120px;" for="isPubReturn">培训课程期数名称</label>
										<input type="text" id="courseCode" name="courseCode" value="${employee.courseCode}" readonly="readonly"/>
									</li>
									<li>
										<label style="width:120px">岗前培训是否毕业</label>
										<house:dict id="isPreTrainPass" dictCode="" sql="select cbm,cbm+' '+note note from tXTDM a where a.ID='YESNO' " 
						 				sqlValueKey="cbm" sqlLableKey="note" value="${employee.isPreTrainPass}" disabled="true"></house:dict>	
									<li>
										<label style="width: 120px;" class="control-textarea" for="remarks">修改记录</label>
										<textarea id="chgRemarks" name="chgRemarks" rows="8" style="width: 470px">${employee.chgRemarks}</textarea>
									</li>
								</ul>
							</form>
						
						</div>
		    		</div>
		    	</div>
		    	<!--证书 -->
	    		<div id="tab_employeeSavePerformance"  class="tab-pane fade"> 
					<div class="pageContent">
						<div class="body-box-form">
							<div class="panel panel-system" >
							    <div class="panel-body">
							      	<div class="btn-group-xs" >
										<button type="submit" class="btn btn-system " id="addPer">
											<span>新增</span>
										</button>
										<button type="button" class="btn btn-system " id="updatePer">
											<span>编辑</span>
										</button>
										<button type="button" class="btn btn-system" id="deletePer">
											<span>删除</span>
										</button>
										<button type="button" class="btn btn-system" id="viewPer">
											<span>查看</span>
										</button>
									</div>
								</div>
							</div>
						</div>						
	 					<div class="panel panel-info" style="height:450px; width:720px; overflow:hidden;">  
							<div id="content-list1" style="width:720px;height:450px;">
								<table id= "dataTable_per"></table>
							</div>	
						</div> 
		    		</div>
		    	</div>
		    	<!--业绩相关设置 -->
		    	<div id="tab_employeeSaveSertificate"  class="tab-pane fade"> 
					<div class="pageContent">
						<div class="btn-panel">
							<form action="" method="post" id="page_form_3" class="form-search">
								<ul class="ul-form" style="height: 450px;">
									<li>
										<label style="width: 160px;" for="documentNo">调岗时间(代理任命时间)</label>
										<input type="text" id="posiChgDate" name="posiChgDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${employee.posiChgDate}' pattern='yyyy-MM-dd'/>" />
									</li>
 									<li>
										<label style="width: 160px;" for="oldDept">原一级部门</label>
										<select id="oldDepartment1" name="oldDepartment1" value="${employee.oldDepartment1}"></select>
									</li> 
									<li>
										<label style="width: 160px;" for="laborCompny">代理转正时间</label>
										<input type="text" id="managerRegularDate" name="managerRegularDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										 value="<fmt:formatDate value='${employee.managerRegularDate}' pattern='yyyy-MM-dd'/>" />
									</li>
									<li>
										<label style="width: 160px;" for="laborAmount">原二级部门</label>
										<select id="oldDept" name="oldDept" value="${employee.oldDept}"></select>
									</li>
									<li>
										<label style="width: 160px;" for="laborAmount">业绩归属原部门截至时间</label>
										<input type="text" id="oldDeptDate" name="oldDeptDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										 value="<fmt:formatDate value='${employee.oldDeptDate}' pattern='yyyy-MM-dd'/>" />
									</li>
									<li>
										<label style="width:160px">业绩归属模式</label>
										<house:xtdm id="perfBelongMode" dictCode="PERFBELONGMODE" value="${employee.perfBelongMode}"></house:xtdm>
									</li>
								</ul>
							</form>	
						</div>
		    		</div>
		    	</div>
		    	<!--工作经验-->
		    	<div id="tab_employeeSaveWork"  class="tab-pane fade"> 
		    		<div class="pageContent">
						<div class="body-box-form">
							<div class="panel panel-system" >
							    <div class="panel-body">
							      	<div class="btn-group-xs" >
										<button type="submit" class="btn btn-system " id="addWork">
											<span>新增</span>
										</button>
										<button type="button" class="btn btn-system " id="updateWork">
											<span>编辑</span>
										</button>
										<button type="button" class="btn btn-system" id="deleteWork">
											<span>删除</span>
										</button>
										<button type="button" class="btn btn-system" id="viewWork">
											<span>查看</span>
										</button>
									</div>
								</div>
							</div>
						</div>
		    		</div>
		    		<div class="panel panel-info" style="height:450px; width:700px; overflow-y:hidden;">  
						<div id="content-list" style="width:1006px;height:306px;">
							<table id= "dataTable_work"></table>
						</div>	
					</div>
				</div>
		  </div>		  	
  	</div>
<script>

function uploadEmpPic(o) {
    var formData = new FormData()
    formData.append('number', '${employee.number}')
    formData.append('file', o.files[0])

    $.ajax({
        url: '${ctx}/admin/employee/uploadEmpPic',
        data: formData,
        cache: false,
        type: 'POST',
        contentType: false,
        processData: false,
        success: function(result) {
            if (result.rs) {
                $('#imgs').attr('src', result.datas.empPicUrl)
                $('#photoName').val(result.datas.empPicFullName)
            }
        }
    })
}

function removeImg(){
    $("#imgs").attr("src", "");
    $("#photoName").val("");
    
    var file = document.getElementById("files");
    file.outerHTML = file.outerHTML;
}

function saveNew() {

    $("form").bootstrapValidator("validate");
    if (!$("form").data("bootstrapValidator").isValid()) return;
   
    var inputApplicantPK = $("#applicantPK").val() 
    var componentApplicantPK = $("#openComponent_applicant_applicantPK").val()
    var applicantPK = inputApplicantPK || componentApplicantPK
    
    if (!applicantPK) {
        $("#applicantPK").val("-1")
    } else {
        $("#applicantPK").val(applicantPK)
    }
    
    if ($.trim($("#nameChi").val()) == "" 
        || $.trim($("#department1").val()) == ""
        || $.trim($("#position").val()) == ""
        || $.trim($("#isSchemeDesigner").val()) == "") {
        
        if ($.trim($("#department").val()) == "" && $.trim($("#position").val()) != "") {
            art.dialog({
                content: "请填入完整信息，所属部门未选择",
            })
        } else {
            art.dialog({
                content: "请填入完整信息",
            })
        }
        return false
    }
    
    if (!/(^\d{15}$)|(^\d{17}([0-9]|X|x)$)/.test($("#idnum").val())) {
        art.dialog({content: "请输入合法的身份证号码"})
        return false
    }
    
    if (!/^\d{1,11}$/.test($("#phone").val())) {
        art.dialog({content: "请输入正确的电话号码"})
        return false
    }
    
    if (($("#positionType").val() == '6'
        || $("#secondPositionType").val() == '6')
        && $("#prjLevel").val() == "") {
        
        art.dialog({content: "请填写项目经理星级"})
        return false
    }
    
    if ($.trim($("#type").val()) == "") {
        art.dialog({
            content: "请选择员工类型",
        })
        return false
    }

    var data = $("#dataForm").jsonForm()
    var pageForm1Data = $("#page_form_1").jsonForm()
    var pageForm2Data = $("#page_form_2").jsonForm()
    var pageForm3Data = $("#page_form_3").jsonForm()
    $.extend(data, pageForm1Data, pageForm2Data, pageForm3Data)

    var dataTable_workJSON = Global.JqGrid.allToJson("dataTable_work")
    data.empExpDetailJson = dataTable_workJSON.detailJson
    var dataTable_perJSON = Global.JqGrid.allToJson("dataTable_per")
    data.empCertification = dataTable_perJSON.detailJson

    $.ajax({
        url: "${ctx}/admin/employee/existsNameChiExceptNumber",
        type: "post",
        data: {nameChi: $("#nameChi").val(), number: ''},
        dataType: "json",
        cache: false,
        error: function(obj) {
            showAjaxHtml({"hidden": false, "msg": "保存出错"})
        },
        success: function(existed) {
            if (existed) {
                art.dialog({
                    content: '存在相同的中文名记录，确定保存吗？',
                    lock: true,
                    ok: function() {
                        doSaveNew(data)
                    },
                    cancel: function() {}
                })
            } else {
                doSaveNew(data)
            }
        }
    })

}

function doSaveNew(data) {
    $.ajax({
        url: "${ctx}/admin/employee/doSaveNew",
        type: "POST",
        data: data,
        dataType: "json",
        error: function(obj) {
            showAjaxHtml({
                "hidden": false,
                "msg": "保存数据出错~"
            })
        },
        success: function(obj) {
            if (obj.rs) {
                art.dialog({
                    content: obj.msg,
                    time: 1000,
                    beforeunload: function () {
                        closeWin()
                    }
                })
            } else {
                $("#_form_token_uniq_id").val(obj.token.token)
                art.dialog({
                    content: obj.msg,
                })
            }
        }
    })
}

</script>
</div>
</body>
</html>


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>个人薪酬查询</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
<META HTTP-EQUIV="expires" CONTENT="0"/>
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
	::-webkit-input-placeholder { /* Chrome */
	  color: #cccccc;
	}
		.SelectBG{
			color:red;
			background-color:white!important;
		}
	</style>
<script type="text/javascript">
var hidCol = ["转正日期","离职日期","入职日期","月份","工号","身份证号","状态","财务编码","签约公司","一级部门","二级部门","职位","工龄"];
$(function() {
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-100,
		rowNum:50,
		colModel : [
			{name:"label", index:"label", width:150, label:"项目", sortable:true ,align:"center",},
			{name:"value", index:"value", width:200, label:"值", sortable:true ,align:"center",formatter:diyFormat},
		],
		gridComplete:function(){
			$("#1").find("td").addClass("SelectBG");
			$("#1").find("td").css("color","black");
		},
		onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTable").jqGrid("getDataIDs");  
			for(var i=0;i<ids.length;i++){
				if(i!=id-1){
					$('#'+ids[i]).find("td").removeClass("SelectBG");
				}
			}
			$('#'+ids[id-1]).find("td").addClass("SelectBG");
		},
	}; 
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	function diyFormat(c, o, r){
		if ($.trim(c) == "null" || c == null){
			return "";
		}
		return c;
	}
	getSalaryScheme("${salaryMon }");
	
});

function  query(){
	$("#dataTable").jqGrid("clearGridData");
	
	var salaryMon = $("#salaryMon").val();
	var salaryScheme = $("#salaryScheme").val();
	$.ajax({
		url:"${ctx}/admin/personalSalaryQuery/goJqGrid",
		type: "post",
		data: {salaryMon: salaryMon, salaryScheme: salaryScheme},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
			if(obj.length>0){
				for(var key in obj[0]){
					var map = {};
					if(key.split("_hid").length > 1 || key.split("_tip").length > 1){
						continue;
					}
					if(hidCol.indexOf($.trim(key))>=0){
						continue;
					}
					map["label"] = key;
					if(obj[0][key]=="" || obj[0][key]== null){
						continue;
					}
					map["value"] = obj[0][key];
					$("#dataTable").addRowData(1,map,"last");
				}
			}
		}
	});
}

function getSalaryScheme(){
	var salaryMon = $("#salaryMon").val();
	if (salaryMon == ""){
		return;
	}
	$.ajax({
		url:"${ctx}/admin/personalSalaryQuery/getSalaryScheme",
		type: "post",
		data: {salaryMon: salaryMon},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
	    	$("#salaryScheme").find("option").remove();
   			$("#salaryScheme").append("<option value= " + " " + ">" + "请选择..." + "</option>");
	    	if(obj && obj.length>0){
	    		for(var i = 0; i < obj.length; i++){
	    			if(i == 0){
		    			$("#salaryScheme").append("<option value= " + obj[i]["SalaryScheme"] + " selected>" + obj[i]["Descr"] + "</option>");
	    			} else {
		    			$("#salaryScheme").append("<option value= " + obj[i]["SalaryScheme"] + " selected>" + obj[i]["Descr"] + "</option>");
	    			}
	    		}
	    	}
	    	query();
		}
	});
	
}
</script>
</head>

<body style="scrollOffset: 0">
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form" style="padding-top: 15px">
					<li class="form-validate">
						<label>月份</label>
						<house:dict id="salaryMon" dictCode="" sql=" select SalaryMon,SalaryMon descr from tSalaryStatusCtrl where Status = '3' group by SalaryMon"  
							 sqlValueKey="salaryMon" sqlLableKey="descr" value="${salaryMon }" onchange="getSalaryScheme()"></house:dict>
					</li>
					
					<li class="form-validate">
						<label>薪酬方案</label>
						<select id="salaryScheme" name ="salaryScheme">
							<option value="123"></option>
 						</select>	
					</li>
					
					<li class="search-group">
						<button type="button" class="btn  btn-sm btn-system" onclick="query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"></div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
			<div id="readding" style="position: absolute; left:45%;top: 50%;width: 80px; height: 30px; background: rgb(217,237,247);text-align: center;line-height: 30px;border: solid 1px rgb(240,240,240)" hidden="true">读取中...</div>
		</div>
	</div>
</body>
</html>

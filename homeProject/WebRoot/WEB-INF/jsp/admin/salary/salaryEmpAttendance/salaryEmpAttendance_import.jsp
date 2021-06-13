<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>WareHouse明细</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
var hasInvalid=true;
//tab分页
$(document).ready(function() {  
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-72,
		styleUI: 'Bootstrap',
		rowNum:10000,
		colModel : [
				{name: "isinvalid", index: "isinvalid", width: 100, label: "是否有效数据", sortable: true, align: "left",hidden:true},
		   		{name: "isinvaliddescr", index: "isinvaliddescr", width: 130, label: "数据是否有效", sortable: true, align: "left",},
				{name: "salaryemp", index: "salaryemp", width: 80, label: "人员工号", sortable: true, align: "left", },
				{name: "empname", index: "empname", width: 80, label: "姓名", sortable: true, align: "left",},
				{name: "latetimes", index: "latetimes", width: 80, label: "迟到次数", sortable: true, align: "right",  },
                {name: "seriouslatetimes", index: "seriouslatetimes", width: 110, label: "严重迟到次数", sortable: true, align: "right",  },
               	{name: "lateoverhourtimes", index: "lateoverhourtimes", width: 110, label: "迟到(1小时以上)次数", sortable: true, align: "right", hidden:true },
                {name: "leaveearlytimes", index: "leaveearlytimes", width: 80, label: "早退次数", sortable: true, align: "right",  },
                {name: "goabsenttimes", index: "goabsenttimes", width: 80, label: "上班缺卡次数", sortable: true, align: "right",  },
                {name: "outabsenttimes", index: "outabsenttimes", width: 80, label: "下班缺卡次数", sortable: true, align: "right",  },
                {name: "absentdays", index: "absentdays", width: 80, label: "旷工天数", sortable: true, align: "right",  },
                {name: "yearleave", index: "yearleave", width: 80, label: "年假(天)", sortable: true, align: "right",  },
                {name: "eventleave", index: "eventleave", width: 80, label: "事假(小时)", sortable: true, align: "right",  },
                {name: "sickleave", index: "sickleave", width: 80, label: "病假(小时)", sortable: true, align: "right",  },
                {name: "compensatoryleave", index: "compensatoryleave", width: 80, label: "调休(小时)", sortable: true, align: "right",  },
                {name: "maternityleave", index: "maternityleave", width: 80, label: "产假(天)", sortable: true, align: "right",  },
                {name: "accompanymaternityleave", index:"accompanymaternityleave", width: 80, label: "陪产假(天)", sortable: true, align: "right",  },
                {name: "marryleave", index:"marryleave", width: 80, label: "婚假(天)", sortable: true, align: "right",  },
              	{name: "bereavementleave", index:"bereavementleave", width: 80, label: "丧假(天)", sortable: true, align: "right",  },
                {name: "absenttimes", index: "absenttimes", width: 80, label: "缺卡次数", sortable: true, align: "right",hidden:true  },
                {name: "leavedays", index: "leavedays", width: 80, label: "请假天数", sortable: true, align: "right",hidden:true  },
    	]  
	});
    //初始化excel模板表格
	Global.JqGrid.initJqGrid("modelDataTable",{
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
				{name: "empname", index: "empname", width: 80, label: "姓名", sortable: true, align: "left",},
				{name: "latetimes", index: "latetimes", width: 80, label: "迟到次数", sortable: true, align: "right",  },
                {name: "seriouslatetimes", index: "seriouslatetimes", width: 110, label: "严重迟到次数", sortable: true, align: "right",  },
                {name: "leaveearlytimes", index: "leaveearlytimes", width: 80, label: "早退次数", sortable: true, align: "right",  },
                {name: "goabsenttimes", index: "goabsenttimes", width: 80, label: "上班缺卡次数", sortable: true, align: "right",  },
                {name: "outabsenttimes", index: "outabsenttimes", width: 80, label: "下班缺卡次数", sortable: true, align: "right",  },
                {name: "absentdays", index: "absentdays", width: 80, label: "旷工天数", sortable: true, align: "right",  },
                {name: "yearleave", index: "yearleave", width: 80, label: "年假(天)", sortable: true, align: "right",  },
                {name: "eventleave", index: "eventleave", width: 80, label: "事假(小时)", sortable: true, align: "right",  },
                {name: "sickleave", index: "sickleave", width: 80, label: "病假(小时)", sortable: true, align: "right",  },
                {name: "compensatoryleave", index: "compensatoryleave", width: 80, label: "调休(小时)", sortable: true, align: "right",  },
                {name: "maternityleave", index: "maternityleave", width: 80, label: "产假(天)", sortable: true, align: "right",  },
                {name: "accompanymaternityleave", index:"accompanymaternityleave", width: 80, label: "陪产假(天)", sortable: true, align: "right",  },
                {name: "marryleave", index:"marryleave", width: 80, label: "婚假(天)", sortable: true, align: "right",  },
              	{name: "bereavementleave", index:"bereavementleave", width: 80, label: "丧假(天)", sortable: true, align: "right",  },
           ]
	});
	$("#modelDataTable").addRowData(1, {"empname":"张三","latetimes":"1",
		"seriouslatetimes":"2","leaveearlytimes":"4","goabsenttimes":"1","outabsenttimes":"1",
		"absentdays":"5","yearleave":"1","eventleave":"0","sickleave":"0","compensatoryleave":"0",
		"maternityleave":"0","accompanymaternityleave":"0","marryleave":"0","bereavementleave":"0"}, "last");
    return false;  
});
//加载文件验证
function check(){
	var fileName=$("#file").val();
	var reg=/^.+\.(?:xls|xlsx)$/i;
    if(fileName.length==0){
    	art.dialog({
			content: "请选择需要导入的excel文件！"
		});
        return false;
    }else if(fileName.match(reg)==null){
   		 art.dialog({
			content: "文件格式不正确！请导入正确的excel文件！"
		});
		return false;
    }
    return true;
}
//加载excel
	
function loadData(){
	if(check()){
 		var formData = new FormData();
        formData.append("file", document.getElementById("file").files[0]);
        $.ajax({
             url: "${ctx}/admin/salaryEmpAttendance/loadExcel",
             type: "POST",
             data: formData,
             contentType: false,
             processData: false,
             success: function (data) {
                 if(data.hasInvalid) hasInvalid=true;
                 else hasInvalid=false;
                 if (data.success == false) {
                    art.dialog({
						content: data.returnInfo
				 	});   
                 }else{
                     $("#dataTable").clearGridData();
                     $.each(data.datas,function(k,v){
                     	$("#dataTable").addRowData(k+1,v,"last");
                     })
                 }
             },
	         error: function () {
	             art.dialog({
					content: "上传文件失败!"
			 	 });
             }
       });
   }
}
//导入数据
function importData(){
	if($("#dataTable").jqGrid('getGridParam','records')==0){
			art.dialog({
				content: "请先载入要进行批量导入的数据！"
		});
		return;
	}
	if(hasInvalid){
		art.dialog({
			content: "存在无效的数据，无法导入！"
		});
		return;
	}
	if($("#salaryMon").val()==""){
		art.dialog({
			content: "请选择薪酬月份！"
		});
		return;
	}
	var isinvalid= Global.JqGrid.allToJson("dataTable","isinvalid");
	arr= isinvalid.fieldJson.split(",");
	var s=0;		
	for(var i=0;i<arr.length;i++){
		if(arr[i]=="0"){
			art.dialog({
					content: "存在无效的数据，无法导入！"
			});
			return;
		}
	}
	var param=Global.JqGrid.allToJson("dataTable");  //JSON.stringify($('#dataTable').jqGrid("getRowData")) ;                       
	Global.Form.submit("page_form","${ctx}/admin/salaryEmpAttendance/doSaveBatch",param,function(ret){
		if(ret.rs==true){
			art.dialog({
				content:ret.msg,
				time:1000,
				beforeunload:function(){
					closeWin();
				}
			});				
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
			art.dialog({
				content:ret.msg,
				width:200
			});
		}
	});
}

function setMon(){
	$("#salaryMon").val($("#mon").val());
}
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="btn-panel pull-left">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system " onclick="loadData()">加载数据</button>
				<button type="button" class="btn btn-system " onclick="importData()">导入数据</button>
				<button type="button" class="btn btn-system " onclick="doExcelNow('考勤信息导入模板','modelDataTable')" style="margin-right: 15px">下载模板</button>
					<label >薪酬月份</label>
					<house:dict id="mon" style="position: relative;top: 2px;border-radius:5px" dictCode="" sql="select salaryMon from tSalaryMon a where a.Expired='F' and a.Status<>'3'
						 order by salaryMon" 
					 sqlValueKey="salaryMon" sqlLableKey="salaryMon"  onchange="setMon()"></house:dict>			
			</div>
		</div>
		<div class="query-form" style="padding: 0px;border: none">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="salaryMon" name="salaryMon" value="" />
				<div class="form-group">
					<label for="inputfile"></label> <input type="file" style="position: relative;top: -12px;left:50px" id="file" name="file"
						accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
				</div>
			</form>
		</div>
		<div class="pageContent">
			<!--panelBar-->
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
			<div style="display: none">
				<table id="modelDataTable"></table>
				<div id="modelDataTable"></div>
			</div>
		</div>
	</div>
</body>
</html>

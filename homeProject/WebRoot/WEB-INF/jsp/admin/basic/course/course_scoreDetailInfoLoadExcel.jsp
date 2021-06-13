<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>材料毛利率批量导入</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	var hasInvalid=true;
	$(document).ready(function() {  
	        //初始化表格
			Global.JqGrid.initJqGrid("dataTable",{
				height:$(document).height()-$("#content-list").offset().top-70,
				styleUI: "Bootstrap",
				colModel : [
				    {name:"isinvalid", index: "isinvalid", width: 100, label: "是否有效数据", sortable: true, align: "left",hidden:true},
                    {name:"isinvaliddescr", index: "isinvaliddescr", width: 120, label: "是否有效数据", sortable: true, align: "left"},
				    {name:"number", index:"number", width:100, label:"员工编号", sortable:true, align:"left"},
                    {name: "namechi", index: "nameChi", width: 70, label: "姓名", sortable: true, align: "left"},
                    {name: "gender", index: "gender", width: 70, label: "性别", sortable: true, align: "left"},
                    {name: "department1descr", index: "department1Descr", width: 80, label: "一级部门", sortable: true, align: "left"},
                    {name: "department2descr", index: "department2Descr", width: 80, label: "二级部门", sortable: true, align: "left"},
                    {name: "department3descr", index: "department3Descr", width: 80, label: "三级部门", sortable: true, align: "left"},
                    {name: "positionname", index: "positionName", width: 80, label: "职位", sortable: true, align: "left"},
                    {name: "joindate", index: "joinDate", width: 120, label: "加入日期", sortable: true, align: "left", formatter:formatDate},
                    {name: "phone", index: "phone", width: 80, label: "电话", sortable: true, align: "right"},
                    {name: "score", index: "score", width: 60, label: "成绩", sortable: true, align: "right"},
                    {name: "ispassdescr", index: "isPassDescr", width: 70, label: "是否毕业", sortable: true, align: "left"},
                    {name: "ismakeupdescr", index: "isMakeUpDescr", width: 70, label: "是否补考", sortable: true, align: "left"},
                    {name: "makeupscore", index: "makeUpScore", width: 70, label: "补考成绩", sortable: true, align: "left"},
                    {name: "remark", index: "remark", width: 150, label: "备注", sortable: true, align: "left"}
	            ]
			});
			//初始化excel模板表格
			Global.JqGrid.initJqGrid("modelDataTable",{
				height:$(document).height()-$("#content-list").offset().top-70,
				colModel : [
					{name:"isinvalid", index:"isinvalid", width:100, label:"是否有效数据", sortable:true, align:"left",hidden:true},
                    {name:"number", index:"number", width:100, label:"员工编号", sortable:true, align:"left"},
                    {name: "namechi", index: "nameChi", width: 70, label: "姓名", sortable: true, align: "left"},
                    {name: "gender", index: "gender", width: 70, label: "性别", sortable: true, align: "left"},
                    {name: "department1descr", index: "department1Descr", width: 80, label: "一级部门", sortable: true, align: "left"},
                    {name: "department2descr", index: "department2Descr", width: 80, label: "二级部门", sortable: true, align: "left"},
                    {name: "department3descr", index: "department3Descr", width: 80, label: "三级部门", sortable: true, align: "left"},
                    {name: "positionname", index: "positionName", width: 80, label: "职位", sortable: true, align: "left"},
                    {name: "joindate", index: "joinDate", width: 120, label: "加入日期", sortable: true, align: "left", formatter:formatDate},
                    {name: "phone", index: "phone", width: 80, label: "电话", sortable: true, align: "right"},
                    {name: "score", index: "score", width: 60, label: "成绩", sortable: true, align: "right"},
                    {name: "ispass", index: "isPass", width: 70, label: "是否毕业", sortable: true, align: "left"},
                    {name: "ismakeup", index: "isMakeUp", width: 70, label: "是否补考", sortable: true, align: "left"},
                    {name: "makeupscore", index: "makeUpScore", width: 70, label: "补考成绩", sortable: true, align: "left"},
                    {name: "remark", index: "remark", width: 150, label: "备注", sortable: true, align: "left"}
	            ]
			});
			$("#modelDataTable").addRowData(1, { "custcode": "CT025705","agreedate":"2010-11-26", "amount": "100.00","firstdate": "2010-11-26","seconddate":"2010-11-26"}, "last");
	        return false;  
	});
	
	//加载文件验证
	function check() {
		var fileName = $("#file").val();
		var reg = /^.+\.(?:xls|xlsx)$/i;
		if (fileName.length == 0) {
			art.dialog({
				content : "请选择需要导入的excel文件！"
			});
			return false;
		}
		if (fileName.match(reg) == null) {
			art.dialog({
				content : "文件格式不正确！请导入正确的excel文件！"
			});
			return false;
		}
		return true;
	}
	
	//在url中添加查询参数
	function addQueryParam(url, name, value){
	   if(url.indexOf("?") === -1){
	       url = url + "?";
	   }else{
	       url = url + "&";
	   }
	   return url + encodeURIComponent(name) + "=" + encodeURIComponent(value);
	}
	
	//加载excel
	function loadData() {
		if (!check()) {
			return;
		}
		var formData = new FormData();
		formData.append("file", document.getElementById("file").files[0]);

		//发送请求
		$.ajax({
			url : "${ctx}/admin/course/loadExcel",
			type : "POST",
			data : formData,
			contentType : false,
			processData : false,
			success : function(data) {
				if (data.hasInvalid)
					hasInvalid = true;
				else
					hasInvalid = false;
				if (data.success == false) {
					art.dialog({
						content : data.returnInfo
					});

				} else {
					$("#dataTable").clearGridData();
					$.each(data.datas, function(k, v) {
						$("#dataTable").addRowData(k + 1, v, "last");
					})
				}
			},
			error : function() {
				art.dialog({
					content : "上传文件失败!"
				});
			}
		});
	}
	
	//导入数据
	function importData() {
		if ($("#dataTable").jqGrid("getGridParam", "records") == 0) {
			art.dialog({
				content : "请先载入要进行批量导入的材料毛利率数据！"
			});
			return;
		}
		var isinvalid = Global.JqGrid.allToJson("dataTable", "isinvalid");
		arr = isinvalid.fieldJson.split(",");
		var s = 0;
		for ( var i = 0; i < arr.length; i++) {
			if (arr[i] != "1") {
				art.dialog({
					content : "存在无效的数据，无法导入！"
				});
				return;
			}
		}
		Global.Dialog.returnData = Global.JqGrid.allToJson("dataTable");
	    closeWin();
	}
</script>
</head>
<body>
	<div class="tab_content" style="display: block; ">
		<div class="edit-form">
			<form id="page_form">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value="" />
				<table cellspacing="0" cellpadding="0" width="100%">
					<tbody>
						<tr>
							<td class="td-value" colspan="6"><input type="file" id="file" name="file" />
							</td>
						</tr>
					</tbody>
				</table>
			</form>
			<div class="btn-panel">
			    <br/>
				<div class="btn-group-sm">
					<button type="button" class="btn btn-system " type="button" onclick="loadData()">
						<span>加载数据</span>
					</button>
					<button type="button" class="btn btn-system " onclick="importData()">
						<span>导入数据</span>
					</button>
					<button type="button" class="btn btn-system " onclick="doExcelNow('成绩导入模板','modelDataTable')">
						<span>下载模板</span>
					</button>
				</div>
				<br/>
				<div class="pageContent">
					<div id="content-list">
						<table id="dataTable"></table>
					</div>
					<div style="display: none">
						<table id="modelDataTable"></table>
						<div id="modelDataTable"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

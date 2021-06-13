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
				    {name:"custCode", index:"custCode", width:70, label:"客户编号", sortable:true, align:"left", key:true},
				    {name:"mainProPer", index:"mainProPer", width:70, label:"主材毛利率", sortable:true, align:"left"},
				    {name:"servProper",index:"servProper", width:100, label:"服务性产品毛利率", sortable:true, align:"left"},
				    {name:"intProPer", index:"intProPer", width:70, label:"集成橱柜毛利率", sortable:true, align:"left"},
			        {name:"softProPer", index:"softProPer", width:100, label:"软装毛利率", sortable:true, align:"left"},
			        {name:"curtainProPer", index:"curtainProPer", width:80, label:"窗帘毛利率", sortable:true, align:"left"},
			   	    {name:"furnitureProPer", index:"furnitureProPer", width:110, label:"家具毛利率", sortable:true, align:"left"}
	            ]
			});
			//初始化excel模板表格
			Global.JqGrid.initJqGrid("modelDataTable",{
				height:$(document).height()-$("#content-list").offset().top-70,
				colModel : [
					{name:"isinvalid", index:"isinvalid", width:100, label:"是否有效数据", sortable:true, align:"left",hidden:true},
                    {name:"custCode", index:"custCode", width:70, label:"客户编号", sortable:true, align:"left", key:true},
                    {name:"mainProPer", index:"mainProPer", width:70, label:"主材毛利率", sortable:true, align:"left"},
                    {name:"servProper",index:"servProper", width:100, label:"服务性产品毛利率", sortable:true, align:"left"},
                    {name:"intProPer", index:"intProPer", width:70, label:"集成橱柜毛利率", sortable:true, align:"left"},
                    {name:"softProPer", index:"softProPer", width:100, label:"软装毛利率", sortable:true, align:"left"},
                    {name:"curtainProPer", index:"curtainProPer", width:80, label:"窗帘毛利率", sortable:true, align:"left"},
                    {name:"furnitureProPer", index:"furnitureProPer", width:110, label:"家具毛利率", sortable:true, align:"left"}
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
		if(!$(".importType").is(":checked")){
		    art.dialog({
                content : "请选择需导入毛利率的材料类型！"
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
		
		//添加需要导入的材料毛利率类型
		var loadDataUrl = "${ctx}/admin/endProfPer/loadExcel";
		var oImportTypes = $(".importType");
		for(var i = 0; i <oImportTypes.length; i ++){
		  if(oImportTypes[i].checked){
		      loadDataUrl = addQueryParam(loadDataUrl, "importTypes", oImportTypes[i].value);
		  }
		}
		$("#dataTable").hideCol("mainProPer");
		$("#dataTable").hideCol("servProper");
		$("#dataTable").hideCol("intProPer");
		$("#dataTable").hideCol("softProPer");
		$("#dataTable").hideCol("curtainProPer");
		$("#dataTable").hideCol("furnitureProPer");
		for(var i = 0; i <oImportTypes.length; i ++){
          if(oImportTypes[i].checked && oImportTypes[i].value === "ZC"){
              $("#dataTable").showCol("mainProPer");
              $("#dataTable").showCol("servProper");
              continue;
          }
          if(oImportTypes[i].checked && oImportTypes[i].value === "JC"){
              $("#dataTable").showCol("intProPer");
              continue;
          }
          if(oImportTypes[i].checked && oImportTypes[i].value === "RZ"){
              $("#dataTable").showCol("softProPer");
              $("#dataTable").showCol("curtainProPer");
              $("#dataTable").showCol("furnitureProPer");
              continue;
          }
        }
		//发送请求
		$.ajax({
			url : loadDataUrl,
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
		var param = Global.JqGrid.allToJson("dataTable");
		var importDataUrl = "${ctx}/admin/endProfPer/doSaveBatch";
		var oImportTypes = $(".importType");
		for(var i = 0; i <oImportTypes.length; i ++){
          if(oImportTypes[i].checked){
              importDataUrl = addQueryParam(importDataUrl, "importTypes", oImportTypes[i].value);
          }
        }
		
		Global.Form.submit("page_form", importDataUrl,
			param, function(ret) {
				if (ret.rs == true) {
					art.dialog({
						content : ret.msg,
						time : 1000,
						beforeunload : function() {
							closeWin();
						}
					});
				} else {
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content : ret.msg,
						width : 200
					});
				}
			});

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
					<button type="button" class="btn btn-system " onclick="doExcelNow('材料毛利率调整导入模板','modelDataTable')">
						<span>下载模板</span>
					</button>
					
					<input type="checkbox" id="selectAll"/> 全选
					<input type="checkbox" name="importType" class="importType" value="ZC" /> 导入主材毛利率
					<input type="checkbox" name="importType" class="importType" value="JC" /> 导入集成毛利率
					<input type="checkbox" name="importType" class="importType" value="RZ" /> 导入软装毛利率
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
<script type="text/javascript">
$("#selectAll").on("click", function(){
    if($(this).is(":checked")){
        $(".importType").prop("checked", true);
    }else{
        $(".importType").prop("checked", false);
    }
});
</script>

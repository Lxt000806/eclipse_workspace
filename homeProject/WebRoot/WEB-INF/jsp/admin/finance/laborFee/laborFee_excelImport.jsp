<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    	<title>材料升级批量导入</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	var hasInvalid=true;
	$(document).ready(function() { 
	var itemType1="${laborFee.itemType1}".trim();
	        //初始化表格
			Global.JqGrid.initJqGrid("dataTable",{
				height:$(document).height()-$("#content-list").offset().top-70,
				styleUI: "Bootstrap", 
				colModel : [
					{name: "isinvalid", index: "isinvalid", width: 60, label: "是否有效数据", sortable: true, align: "left",hidden:true},
					{name: "isinvaliddescr", index: "isinvaliddescr", width: 60, label: "是否有效数据", sortable: true, align: "left"},
					{name: "custcode", index: "custcode", width: 65, label: "客户编号", sortable: true, align: "left"},
					{name: "address", index: "address", width: 112, label: "楼盘地址", sortable: true, align: "left"},
					{name: "documentno", index: "documentno", width: 60, label: "档案号", sortable: true, align: "left"},
					{name: "checkstatusdescr", index: "checkstatusdescr", width: 60, label: "客户结算状态", sortable: true, align: "left"},
					{name: "custcheckdate", index: "custcheckdate", width:80, label: "客户结算日期", sortable: true, align: "left", formatter: formatTime},
					{name: "feetypedescr", index: "feetypedescr", width: 65, label: "费用类型", sortable: true, align: "left"},
					{name: "feetype", index: "feetype", width: 60, label: "费用类型", sortable: true, align: "left",hidden:true},
					{name: "appsendno", index: "appsendno", width: 60, label: "送货单号", sortable: true, align: "left"},
					{name: "iano", index: "iano", width: 60, label: "领料单号", sortable: true, align: "left"},
					{name: "amount", index: "amount", width: 55, label: "金额", sortable: true, align: "left", sum: true},
					{name: "amount1", index: "amount1", width: 55, label: "配送费", sortable: true, align: "left",hidden:true},
					{name: "amount2", index: "amount2", width:55, label: "配送费（自动生成）", sortable: true, align: "left",hidden:true},
					{name: "hadamount", index: "hadamount", width: 55, label: "已报销金额", sortable: true, align: "left", sum: true},
					{name: "sendnohaveamount", index: "sendnohaveamount", width: 60, label: "本单已报销", sortable: true, align: "left"},
					{name: "actname", index: "actname", width: 65, label: "户名", sortable: true, align: "left"},
					{name: "cardid", index: "cardid", width: 90, label: "卡号", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width:85, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 60, label: "最后更新人员", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 68, label: "操作标志", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 65, label: "是否过期", sortable: true, align: "left", hidden: true}
	            ]
			});
			      //初始化excel模板表格
			Global.JqGrid.initJqGrid("modelDataTable",{
				height:$(document).height()-$("#content-list").offset().top-70,
				colModel : [
						{name: "custcode", index: "custcode", width: 85, label: "客户编号", sortable: true, align: "left"},
						{name: "address", index: "address", width: 85, label: "楼盘地址", sortable: true, align: "left"},
						{name: "documentno", index: "documentno", width: 85, label: "档案号", sortable: true, align: "left"},
						{name: "checkoutstatus", index: "checkoutstatus", width: 85, label: "客户结算状态", sortable: true, align: "left"},
						{name: "checkoutdate", index: "checkoutdate", width: 85, label: "客户结算日期", sortable: true, align: "left"},
						{name: "feetype", index: "feetype", width: 85, label: "费用类型", sortable: true, align: "left",},
						{name: "appsendno", index: "appsendno", width: 76, label: "送货单号", sortable: true, align: "left",},
						{name: "itemappno", index: "itemappno", width: 76, label: "领料单号", sortable: true, align: "left",},
						{name: "amount", index: "amount", width: 80, label: "金额", sortable: true, align: "left"},
						{name: "hasamount", index: "hasamount", width: 80, label: "已报销金额", sortable: true, align: "left"},
						{name: "actname", index: "actname", width: 68, label: "户名", sortable: true, align: "left"},
						{name: "cardid", index: "cardid", width: 90, label: "卡号", sortable: true, align: "left", },
						{name: "remarks", index: "remarks", width: 90, label: "备注", sortable: true, align: "left", },
	            ]
			});
			$("#modelDataTable").addRowData(1, {"custcode":"CT000221","feetype":"01","sendno":"IASLS00000001","amount":"99","actname":"户名","cardid":"123456789000","remarks":"备注"}, "last");
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
			formData.append("itemType1","${laborFee.itemType1}");
			$.ajax({
				url: "${ctx}/admin/laborFee/loadExcel",
				type: "POST",
				data: formData,
				timeout:10000000,
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
		if($("#dataTable").jqGrid("getGridParam","records")==0){
			art.dialog({
				content: "请先载入要进行批量导入的套餐材料数据！"
			});
			return;
		}
		var isinvalid= Global.JqGrid.allToJson("dataTable","isinvalid");
			arr= isinvalid.fieldJson.split(",");
		var s=0;		
		for(var i=0;i<arr.length;i++){
			if(arr[i]!="1"){
				art.dialog({
						content: "存在无效的数据，无法导入！"
				});
				return;
			}
		}
	var returnData=$("#dataTable").jqGrid("getRowData");
	Global.Dialog.returnData = returnData;
	closeWin();
	}
	</script>

	

  </head>
  
<body>
	<div  class="tab_content" style="display: block; ">
		<div class="edit-form">
			<form  id="page_form" >
				<house:token></house:token>
				<input type="hidden" name="jsonString" value="" />
				<table cellspacing="0" cellpadding="0" width="100%">
					<tbody>
						<tr>	
							<td class="td-value"  colspan="6">
								<input type="file" id="file" name="file"  />
							</td>
						</tr>
					</tbody>
				</table>
			</form>
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " type="button"  onclick="loadData()"  >
					<span>加载数据</span></button>
					<button type="button" class="btn btn-system " onclick="importData()"  >
					<span>导入数据</span></button>
					<button type="button" class="btn btn-system "  onclick="doExcelNow('人工费用导入模板','modelDataTable')"  >
					<span>下载模板</span></button>
				</div>
				
				<div class="pageContent">
					<div id="content-list">
						<table id= "dataTable"></table>
					</div> 
					<div style="display: none">
						<table id= "modelDataTable"></table>
						<div id="modelDataTable"></div>
					</div> 
				</div>   
		    </div>  
		</div>
  </div>
  </body>
</html>

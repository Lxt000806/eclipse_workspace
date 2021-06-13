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
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
var hasInvalid=true;
//tab分页
$(document).ready(function() {  
var itemType1='${itemChg.itemType1}'.trim();

        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap', 
			colModel : [
					{name: "isinvalid", index: "isinvalid", width: 100, label: "是否有效数据", sortable: true, align: "left",hidden:true},
					{name: "isinvaliddescr", index: "isinvaliddescr", width: 100, label: "是否有效数据", sortable: true, align: "left"},
					{name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 220, label: "材料名称", sortable: true, align: "left"},
					{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left",hidden:true},
					{name: "uomdescr", index: "uomdescr", width: 60, label: "单位", sortable: true, align: "left"},
					{name: "cost", index: "cost", width: 80, label: "成本单价", sortable: true, align: "left"},
					{name: "avgCost", index: "avgCost", width: 80, label: "原成本", sortable: true,hidden:true, align: "left",hidden:true},
					{name: "aftcost", index: "aftcost", width: 90, label: "变动后成本", sortable: true, align: "left", },
					{name: "adjqty", index: "adjqty", width: 68, label: "调整数量", sortable: true, align: "left", sum: true},
					{name: "aftqty", index: "aftqty", width: 90, label: "变动后数量", sortable: true, align: "left", sum: true},
					{name: "remarks", index: "remarks", width: 90, label: "备注", sortable: true, align: "left", },
					{name: "lastupdate", index: "lastupdate", width: 90, label: "lastupdate", sortable: true, align: "left", sum: true,formatter:formatTime,hidden:true},
					{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "lastupdatedby", sortable: true, align: "left", sum: true,hidden:true},
					{name: "allqty", index: "allqty", width: 187, label: "现存数量", sortable: true, align: "left",hidden:true},
					{name: "adjcost", index: "adjcost", width: 187, label: "adjcost", sortable: true, align: "left",hidden:true},
            ]
       
		});
		      //初始化excel模板表格
		Global.JqGrid.initJqGrid("modelDataTable",{
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
					{name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 76, label: "材料名称", sortable: true, align: "left",},
					{name: "uomdescr", index: "uomdescr", width: 80, label: "单位", sortable: true, align: "left"},
					{name: "cost", index: "cost", width: 68, label: "成本单价", sortable: true, align: "left"},
					{name: "adjcost", index: "adjcost", width: 90, label: "变动成本", sortable: true, align: "left", },
					{name: "adjqty", index: "adjqty", width: 187, label: "调整数量", sortable: true, align: "left"},
					{name: "aftqty", index: "aftqty", width: 120, label: "变动后数量", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"}
            ]
		});
		$("#modelDataTable").addRowData(1, {"itemcode":"12585","itemdescr":"墙纸胶水","uomdescr":"瓶","cost":0,"adjcost":0,"adjqty":0,"aftqty":0,"remarks":"备注"}, "last");
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
            formData.append("whCode",'${itemBalAdjHeader.whCode}');
            formData.append("noRepeat",'${itemBalAdjHeader.noRepeat}');
            formData.append("adjType",'${itemBalAdjHeader.adjType}');
            formData.append("allItCode",'${itemBalAdjHeader.allItCode}');
            formData.append("adjType",'${itemBalAdjHeader.adjType}');
            $.ajax({
                    url: "${ctx}/admin/itemBalAdjHeader/loadExcel",
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
				content: "请先载入要进行批量导入的成绩数据！"
		});
		return;
	}
	if(hasInvalid){
		art.dialog({
				content: "存在无效的数据，无法导入！"
		});
		return;
	}
	var  isinvalid= Global.JqGrid.allToJson("dataTable","isinvalid");
			arr= isinvalid.fieldJson.split(",");//取得开始时间数组
	var s=0;		
	for(var i=0;i<arr.length;i++){
		if(arr[i]!="0"){
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
					</form>
							</tbody>
						</table>
					<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
								<button type="button" class="btn btn-system " type="button"  onclick="loadData()"  >
								<span>加载数据</span></button>
								<button type="button" class="btn btn-system " onclick="importData()"  >
								<span>导入数据</span></button>
								<button type="button" class="btn btn-system "  onclick="doExcelNow('仓库调整导入模板','modelDataTable')"  >
								<span>下载模板</span></button>
				</div>
        	 <div class="panel-body">
         			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
						<input type="hidden" name="jsonString" value="" />
						<input type="hidden" id="adjType" name="adjType" value="${itemBalAdjHeader.adjType }" />
					</form>
			</div>
				<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<!--panelBar-->
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

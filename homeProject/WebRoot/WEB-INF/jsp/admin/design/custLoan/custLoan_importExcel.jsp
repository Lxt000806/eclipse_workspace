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
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	var hasInvalid=true;
	$(document).ready(function() {  
	        //初始化表格
			Global.JqGrid.initJqGrid("dataTable",{
				height:$(document).height()-$("#content-list").offset().top-70,
				styleUI: "Bootstrap", 
				colModel : [
				  {name: "isinvalid", index: "isinvalid", width: 100, label: "是否有效数据", sortable: true, align: "left",hidden:true},
				  {name: "isinvaliddescr", index: "isinvaliddescr", width: 120, label: "是否有效数据", sortable: true, align: "left"},
				  {name : 'custcode',index : 'custcode',width : 70,label:'客户编号',sortable : true,align : "left",key : true},
				  {name : 'custdescr',index : 'custdescr',width : 70,label:'客户名称',sortable : true,align : "left"},
				  {name : 'address',index : 'address',width : 100,label:'楼盘',sortable : true,align : "left"},
				  {name : 'statusdescr',index : 'statusdescr',width : 70,label:'客户状态',sortable : true,align : "left"},
			      {name : 'bank',index : 'bank',width : 100,label:'贷款银行',sortable : true,align : "left"},
			      {name : 'agreedate',index : 'agreedate',width : 80,label:'协议提交日期',sortable : true,align : "left", formatter: formatTime},
			   	  {name : 'amount',index : 'amount',width : 110,label:'贷款总额/万元',sortable : true,align : "left"},
			   	  {name : 'firstamount',index : 'firstamount',width : 110,label:'首次放款金额/万元',sortable : true,align : "left"},
			      {name : 'firstdate',index : 'firstdate',width : 100,label:'首次放款时间',sortable : true,align : "首次放款时间", formatter: formatTime},
			      {name : 'secondamount',index : 'secondamount',width : 110,label:'二次放款金额/万元',sortable : true,align : "left"},
			      {name : 'seconddate',index : 'seconddate',width : 100,label:'二次放款时间',sortable : true,align : "left", formatter: formatTime},
			      {name : 'signremark',index : 'signremark',width : 100,label:'已签约待放款',sortable : true,align : "left"},
			      {name : 'confuseremark',index : 'confuseremark',width : 100,label:'退件拒批',sortable : true,align : "left"},	      
			      {name : 'followremark',index : 'followremark',width : 100,label:'需跟踪',sortable : true,align : "left"},
			      {name : 'remark',index : 'remark',width : 100,label:'备注',sortable : true,align : "left"}
	            ]
			});
			//初始化excel模板表格
			Global.JqGrid.initJqGrid("modelDataTable",{
				height:$(document).height()-$("#content-list").offset().top-70,
				colModel : [
					  {name : 'custcode',index : 'custcode',width : 100,label:'客户编号',sortable : true,align : "left",key : true},
				      {name : 'bank',index : 'bank',width : 100,label:'贷款银行',sortable : true,align : "left"},
				      {name : 'agreedate',index : 'agreedate',width : 100,label:'协议提交日期',sortable : true,align : "left", formatter: formatTime},
				   	  {name : 'amount',index : 'amount',width : 110,label:'贷款总额/万元',sortable : true,align : "left"},
				   	  {name : 'firstamount',index : 'firstamount',width : 110,label:'首次放款金额/万元',sortable : true,align : "left"},
				      {name : 'firstdate',index : 'firstdate',width : 100,label:'首次放款时间',sortable : true,align : "首次放款时间", formatter: formatTime},
				      {name : 'secondamount',index : 'secondamount',width : 110,label:'二次放款金额/万元',sortable : true,align : "left"},
				      {name : 'seconddate',index : 'seconddate',width : 100,label:'二次放款时间',sortable : true,align : "left", formatter: formatTime},
				      {name : 'signremark',index : 'signremark',width : 100,label:'已签约待放款',sortable : true,align : "left"},
				      {name : 'confuseremark',index : 'confuseremark',width : 100,label:'退件拒批',sortable : true,align : "left"},	      
				      {name : 'followremark',index : 'followremark',width : 100,label:'需跟踪',sortable : true,align : "left"},
				      {name : 'remark',index : 'remark',width : 100,label:'备注',sortable : true,align : "left"}
	            ]
			});
			$("#modelDataTable").addRowData(1, { "custcode": "CT025705","agreedate":"2010-11-26", "amount": "100.00","firstdate": "2010-11-26","seconddate":"2010-11-26"}, "last");
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
				url: "${ctx}/admin/custLoan/loadExcel",
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
		var param= Global.JqGrid.allToJson("dataTable");
		Global.Form.submit("page_form","${ctx}/admin/custLoan/doSaveBatch",param,function(ret){
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
				<div class="btn-group-sm">
					<button type="button" class="btn btn-system " type="button" onclick="loadData()">
						<span>加载数据</span>
					</button>
					<button type="button" class="btn btn-system " onclick="importData()">
						<span>导入数据</span>
					</button>
					<button type="button" class="btn btn-system " onclick="doExcelNow('贷款信息导入模板','modelDataTable')">
						<span>下载模板</span>
					</button>
				</div>

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

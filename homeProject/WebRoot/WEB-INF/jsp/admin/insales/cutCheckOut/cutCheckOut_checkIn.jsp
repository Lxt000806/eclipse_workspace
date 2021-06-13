<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>瓷砖加工管理-加工入库</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
		var selectRows = [];
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/cutCheckOut/goDetailJqGrid",
				postData:{isComplete:"0",no:"${cutCheckOut.no}"},
				height:380,
	        	styleUI: "Bootstrap",
				multiselect: true,
				cellEdit:true,
				rowNum:100000000,
				cellsubmit:'clientArray',
				colModel : [
					{name: "pk", index: "pk", width: 107, label: "pk", sortable: true, align: "pk",hidden:true},
					{name: "iano", index: "iano", width: 107, label: "领料单号", sortable: true, align: "left"},
					{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 180, label: "材料名称", sortable: true, align: "left"},
					{name: "fixareadescr", index: "fixareadescr", width: 107, label: "区域", sortable: true, align: "left"},
					{name: "qty", index: "qty", width: 55, label: "数量", sortable: true, align: "right",},
					{name: "cuttypedescr", index: "cuttypedescr", width: 85, label: "切割方式", sortable: true, align: "left", },
					{name: "cutfee", index: "cutfee", width: 72, label: "加工单价", sortable: true, align: "right",},
					{name: "total", index: "total", width: 60, label: "总价", sortable: true, align: "right",sum:true },
					{name: "remarks", index: "remarks", width: 150, label: "加工备注", sortable: true, align: "left",editable:true,edittype:'textarea'},
					{name: "oldcuttypedescr", index: "oldcuttypedescr", width: 85, label: "原切割方式", sortable: true, align: "left"},
					{name: "itemreqremarks", index: "itemreqremarks", width: 150, label: "材料需求备注", sortable: true, align: "left"},
					{name: "refpk", index: "refpk", width: 95, label: "领料明细pk", sortable: true, align: "left"},
					{name: "itemcode", index: "itemcode", width: 84, label: "材料编号", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 95, label: "最后更新人员", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"},
	            ],
			});
		});
		function save(){
			var ids=$("#dataTable").jqGrid("getGridParam", "selarrrow");
			var pks="";
			if(ids.length == 0){
				art.dialog({
					content:"请选择数据后保存"
				});				
				return ;
			}
			$.each(ids,function(i,id){
				var rowData = $("#dataTable").jqGrid("getRowData", id);
				selectRows.push(rowData);
				if(pks != ""){
					pks += ",";
				}
				pks += rowData.pk;
			}); 
			$("#pks").val(pks);
			var cutCheckOutDetailJson = Global.JqGrid.allToJson("dataTable");
			var param = {"cutCheckOutDetailJson": JSON.stringify(cutCheckOutDetailJson)};
			Global.Form.submit("dataForm","${ctx}/admin/cutCheckOut/doCheckIn",param,function(ret){
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 1000,
						beforeunload: function () {
						    closeWin();
						}
					});
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
						art.dialog({
							content: ret.msg,
							width: 200
						});
					}
			});
			
    		/* $.ajax({
				url:'${ctx}/admin/cutCheckOut/doCheckIn',
				type: 'post',
				data: {pks:pks,no:"${cutCheckOut.no}",remarks:$("#remarks").val()},
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
			    				closeWin();
						    }
						});
			    	}else{
			    		$("#_form_token_uniq_id").val(obj.token.token);
			    		art.dialog({
							content: obj.msg,
							width: 200
						});
			    	}
			    }
			 }); */
		}
	</script>
	</head>
	    
	<body>
		<div class="body-box-list">
	 		<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs" style="float:left">
	    				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">保存</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
					</div>
					<input hidden="true" id="dataTableExists_selectRowAll"
							name="dataTableExists_selectRowAll" value="">
				</div>
			</div> 
			
			<div class="panel panel-info" >  
				<div class="panel-body" style="padding:0px;">
					<form action="" method="post" id="dataForm"  class="form-search">
						<input type="hidden" id="no" name="no" value="${cutCheckOut.no }" />
						<input type="hidden" id="pks" name="pks"  />
						<house:token></house:token>
						<ul class="ul-form">
							<li >
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks"></textarea>
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div  class="container-fluid" >  
				<ul class="nav nav-tabs" >
				    <li class="active"><a href="#itemAppSendApplyDetail" data-toggle="tab">出库明细</a></li>  
				</ul>  
				<form action="" method="post" id="page_form" class="form-search" >
					<ul class="ul-form">
						<li>
							<label>楼盘地址</label>
							<input type="text" id="address" name="address"/>
						</li>
						<li>
							<label>领料单号</label>
							<input type="text" id="iano" name="iano"/>
						</li>
						<li class="search-group-shrink" >
							<button type="button" class="btn  btn-sm btn-system" onclick="goto_query()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>
					</ul>
				</form>
			    <div id="content-list">
					<table id="dataTable"></table>
				</div>
			</div>	
		</div>
	</body>
</html>

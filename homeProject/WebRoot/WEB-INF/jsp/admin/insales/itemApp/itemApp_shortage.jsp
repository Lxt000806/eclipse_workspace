<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>领料管理缺货页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<style type="text/css">
			.panelBar{
				height:46px;
			}
		</style>
		<script type="text/javascript">
			$(function(){
		        //初始化表格
				Global.JqGrid.initJqGrid("shortageDataTable",{
					url:"${ctx}/admin/itemApp/getShortageJqGrid",
					postData:{
						no:$("#no").val(),
						custCode:$("#custCode").val()
					},
					height:370,
					rowNum: 10000,
					multiselect: true,
					colModel : [
						{name: "pk", index: "pk", width: 30, label: "编号", sortable: true, align: "left", hidden: true},
						{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left"},
						{name: "itemdescr", index: "itemdescr", width: 140, label: "材料名称", sortable: true, align: "left"},
						{name: "supplierdescr", index: "supplierdescr", width: 100, label: "供应商名称", sortable: true, align: "left"},
						{name: "no", index: "no", width: 60, label: "批次号", sortable: true, align: "left", hidden: true},
						{name: "reqpk", index: "reqpk", width: 70, label: "领料标识", sortable: true, align: "left", hidden: true},
						{name: "fixareapk", index: "fixareapk", width: 84, label: "fixareapk", sortable: true, align: "left", hidden: true},
						{name: "fixareadescr", index: "fixareadescr", width: 70, label: "装修区域", sortable: true, align: "left"},
						{name: "intprodpk", index: "intprodpk", width: 84, label: "intprodpk", sortable: true, align: "left", hidden: true},
						{name: "intproddescr", index: "intproddescr", width: 70, label: "集成产品", sortable: true, align: "left"},
						{name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right"},
						{name: "sendqty", index: "sendqty", width: 100, label: "已发货数量", sortable: true, align: "right"},
						{name: "reqqty", index: "reqqty", width: 70, label: "需求数量", sortable: true, align: "right"},
						{name: "sendedqty", index: "sendedqty", width: 120, label: "总共已发货数量", sortable: true, align: "right"},
						{name: "confirmedqty", index: "confirmedqty", width: 100, label: "已审核数量", sortable: true, align: "right"},
						{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
						{name: "lastupdate", index: "lastupdate", width: 110, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
						{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "更新人员", sortable: true, align: "left", hidden: true},
						{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left", hidden: true},
						{name: "actionlog", index: "actionlog", width: 60, label: "操作", sortable: true, align: "left", hidden: true},
						
						{name: "cost", index: "cost", width: 60, label: "cost", sortable: true, align: "right", hidden: true},
						{name: "projectcost", index: "projectcost", width: 60, label: "projectcost", sortable: true, align: "left", hidden: true}
		            ]
				});
			});
			function doShortage(){
			
				var param = Global.JqGrid.selectToJson("shortageDataTable");
				
				if(param.detailJson == undefined){
					art.dialog({content:"请选择要进行缺货处理的相应信息"});
					return;
				}

				$.extend(param,{no:$("#no").val()});

	        	$.ajax({
	    			url : "${ctx}/admin/itemApp/doShortage",
					data: param,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: "json",
					type: "post",
					cache: false,
					error: function(){
				        art.dialog({
							content: "缺货处理出现异常"
						});
				    },
				    success: function(obj){
				    	art.dialog({
				    		content:obj.msg,
				    		ok:function(){
				    			if(obj.rs){
				    				closeWin();
				    			}
				    		}
				    	});
				    	
				    }
	    		});
			}
		</script>
	</head>
	    
	<body>
		<input type="hidden" name="no" id="no" value="${data.no }"/>
		<input type="hidden" name="m_umState" id="m_umState" value="${data.m_umState }"/>
		<input type="hidden" name="custCode" id="custCode" value="${data.custCode }"/>
		<div class="body-box-list">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
	    				<button id="sendBut" type="button" class="btn btn-system" onclick="doShortage()">缺货处理</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div  class="container-fluid" >  
				<table id="shortageDataTable"></table>
			</div>	
		</div>
	</body>
</html>



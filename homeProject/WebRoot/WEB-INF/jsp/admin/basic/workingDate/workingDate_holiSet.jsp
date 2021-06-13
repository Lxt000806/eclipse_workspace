<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_workType2.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		.date-label {
			width: auto !important;
		}
		.btn-group-xs > .btn.btn-system.date-type {
			width: auto;
			padding: 3px 3px 2px 3px;
		}
		.panel.panel-info {
			margin-bottom: 10px;
		}
	</style>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      	<div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="closeBtn">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">  
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<house:token></house:token><!-- 按钮只能点一次 -->
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" id="expired" name="expired" value="${workingDate.expired}"/>
					<ul class="ul-form">
						<div class="row">
							<li>
								<label class="date-label" for="dateFrom">日期从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" 
									onFocus="WdatePicker({onpicked:tableSearch(),skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${workingDate.dateFrom}' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li>
								<label class="date-label" for="dateTo">到</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
									onFocus="WdatePicker({onpicked:tableSearch(),skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${workingDate.dateTo}' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li>
								<div class="btn-group-xs" id="updateConsole">
									<button type="button" class="btn btn-system date-type" id="dateInit">
										<span>默认节假日类型</span>
									</button>
									<button type="button" class="btn btn-system date-type" id="workDate">
										<span>正常工作日</span>
									</button>
									<button type="button" class="btn btn-system date-type" id="holiDate">
										<span>平时节假日</span>
									</button>
									<button type="button" class="btn btn-system date-type" id="newYearDate">
										<span>春节节假日</span>
									</button>
								</div>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active">
					<a href="#tabView_workingDate" data-toggle="tab">节假日明细</a>
				</li>
			</ul>
			<div class="tab-content">
				<div id="tabView_workingDate" class="tab-pane fade in active">
					<div id="content-list">
						<table id="dataTable"></table>
						<div id="dataTablePager"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" defer>
		$(function() {
			var postData = $("#page_form").jsonForm();
			var gridOption ={
				url:"${ctx}/admin/workingDate/goJqGrid",
				postData:postData,
				height:$(document).height()-$("#content-list").offset().top-70,
				styleUI:"Bootstrap", 
				colModel : [
					{name: "date", index: "date", width: 120, label: "日期", sortable: true, align: "left", formatter: formatTime},
					{name: "holitype", index: "holitype", width: 80, label: "节假日类型", sortable: true, align: "left", hidden: true},
					{name: "holitypedescr", index: "holitypedescr", width: 80, label: "节假日类型", sortable: true, align: "left"},
				],
			};

			Global.JqGrid.initJqGrid("dataTable", gridOption);

			$("#dateInit").on("click", function () {
				var datas = $("#page_form").jsonForm();
				art.dialog({
                    content: "是否初始化",
                    width: 200,
                    okValue: "是",
                    ok: function () {
                        $.ajax({
			                url: "${ctx}/admin/workingDate/doDateInit",
			                type: "post",
			                data: datas,
			                dataType: "json",
			                cache: false,
			                error: function(obj){
			                    showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
			                },
			                success: function(obj){
		                        $("#_form_token_uniq_id").val(obj.token.token);
			                    if(obj.rs){
			                        art.dialog({
			                            content: obj.msg,
			                            time: 700,
			                            beforeunload: function () {
			                                goto_query();
			                            }
			                        });
			                    }else{
			                        art.dialog({
			                            content: obj.msg,
			                            width: 200
			                        });
			                    }
			                }
			            });
                    },
                    cancelValue: "否",
                    cancel: function () {
                        closeWin(false);
                    }
                });
			});

			$("#workDate").on("click", function () {
				var datas = $("#page_form").jsonForm();
				datas.holiType = "1";
				doUpdateHoliType(datas);
			});

			$("#holiDate").on("click", function () {
				var datas = $("#page_form").jsonForm();
				datas.holiType = "2";
				doUpdateHoliType(datas);
			});

			$("#newYearDate").on("click", function () {
				var datas = $("#page_form").jsonForm();
				datas.holiType = "3";
				doUpdateHoliType(datas);
			});

			$("#closeBtn").on("click", function () {
				closeWin();
			});
		});
		//搜索表格
		function tableSearch() {
			goto_query();
		}
		//节假日类型更新
		function doUpdateHoliType(datas) {
			$.ajax({
				url: "${ctx}/admin/workingDate/doUpdateHoliType",
				type: "post",
				data: datas,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
				},
				success: function(obj){
					$("#_form_token_uniq_id").val(obj.token.token);
					if(obj.rs){
						art.dialog({
							content: obj.msg,
							time: 700,
							beforeunload: function () {
								goto_query();
							}
						});
					}else{
						art.dialog({
							content: obj.msg,
							width: 200
						});
					}
				}
			});
		}

	</script>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp"%>
    <script type="text/javascript">
        var loadSuccessfully = true, invalidRecords = 0;

        $(function() {

            Global.JqGrid.initJqGrid("dataTable", {
                height: $(document).height() - $("#content-list").offset().top - 72,
                styleUI: 'Bootstrap',
                rowNum: 10000,
                colModel: [
                    {name: "isvalid", index: "isvalid", width: 100, label: "数据是否有效", sortable: true, align: "left", hidden: true},
                    {name: "info", index: "info", width: 120, label: "数据是否有效", sortable: true, align: "left"},
                    {name: "date", index: "date", width: 80, label: "销售日期", sortable: true, align: "left", formatter: formatDate},
                    {name: "itemType1", index: "itemType1", width: 80, label: "材料类型1", sortable: true, align: "left"},
                    {name: "itemType1Descr", index: "itemType1Descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
                    {name: "supplCode", index: "supplCode", width: 80, label: "供应商编号", sortable: true, align: "left"},
                    {name: "supplDescr", index: "supplDescr", width: 120, label: "供应商名称", sortable: true, align: "left"},
                    {name: "custCode", index: "custCode", width: 80, label: "客户编号", sortable: true, align: "left"},
                    {name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
                    {name: "itemDescr", index: "itemDescr", width: 120, label: "材料名称", sortable: true, align: "left"},
                    {name: "amount", index: "amount", width: 100, label: "返利总金额", sortable: true, align: "right"},
                    {name: "empCode", index: "empCode", width: 80, label: "干系人编号", sortable: true, align: "left"},
                    {name: "empName", index: "empName", width: 80, label: "干系人名字", sortable: true, align: "left"},
                    {name: "commiAmount", index: "commiAmount", width: 100, label: "业务员提成", sortable: true, align: "right"},
                    {name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
                ]
            });

            Global.JqGrid.initJqGrid("modelDataTable", {
                colModel: [
                    {name: "date", index: "date", width: 80, label: "销售日期", sortable: true, align: "left", formatter: formatDate},
                    {name: "itemType1", index: "itemType1", width: 80, label: "材料类型1", sortable: true, align: "left"},
                    {name: "supplCode", index: "supplCode", width: 80, label: "供应商编号", sortable: true, align: "left"},
                    {name: "custCode", index: "custCode", width: 80, label: "客户编号", sortable: true, align: "left"},
                    {name: "itemDescr", index: "itemDescr", width: 120, label: "材料名称", sortable: true, align: "left"},
                    {name: "amount", index: "amount", width: 100, label: "返利总金额", sortable: true, align: "right"},
                    {name: "empCode", index: "empCode", width: 80, label: "干系人编号", sortable: true, align: "left"},
                    {name: "commiAmount", index: "commiAmount", width: 100, label: "业务员提成", sortable: true, align: "right"},
                    {name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
                ]
            });

            $("#modelDataTable").addRowData(1, {
                "date": "2021-02-22",
                "itemType1": "JC",
                "supplCode": "0208",
                "custCode": "CT000001",
                "itemDescr": "斑斓瓷砖",
                "amount": 1000,
                "empCode": "10101",
                "commiAmount": 50,
                "remarks": "商家返利活动"
            }, "last");

        });

        function check() {
            var filename = $("#file").val();

            if (!filename) {
                art.dialog({content: "请选择需要导入的excel文件！"});
                return false;
            } else if (!filename.endsWith(".xls") && !filename.endsWith(".xlsx")) {
                art.dialog({content: "文件格式不正确！请导入正确的excel文件！"});
                return false;
            }

            return true;
        }

        function loadData() {
            if (check()) {
                var formData = new FormData();
                formData.append("file", document.getElementById("file").files[0]);
                
                loadSuccessfully = true;
                invalidRecords = 0;
                
                $.ajax({
                    url: "${ctx}/admin/commiCycle/supplierRebate/loadExcel",
                    type: "POST",
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function(data) {
                        if (data.success) {
                            var grid = $("#dataTable");
                            grid.clearGridData();
                            $.each(data.records, function(index, value) {
                                grid.addRowData(index + 1, value, "last");
                                if (!value.isvalid) invalidRecords++;
                            });
                        } else {
	                        loadSuccessfully = false;
                            art.dialog({content: data.info});
                        }
                    },
                    error: function() {
                        art.dialog({content: "上传文件失败!"});
                    }
                });
            }
        }

		function importData() {
		    var grid = $("#dataTable");

		    if (grid.getGridParam('records') === 0) {
		        art.dialog({content: "请先载入要导入的数据！"});
		        return;
		    }
		
		    if (!loadSuccessfully) {
		        art.dialog({content: "数据加载错误，请检查数据重新加载！"});
		        return;
		    }
		
		    if (invalidRecords) {
		        art.dialog({content: "存在无效数据，请修正数据后再导入！"});
		        return;
		    }

		    Global.Form.submit("page_form",
		        "${ctx}/admin/commiCycle/supplierRebate/doImportExcel",
		        {records: JSON.stringify(grid.getRowData())},
		        function(ret) {
		            if (ret.rs == true) {
		                art.dialog({
		                    content: ret.msg,
		                    time: 1000,
		                    beforeunload: function() {
		                        closeWin(true);
		                    }
		                });
		            } else {
		                $("#_form_token_uniq_id").val(ret.token.token);
		                art.dialog({content: ret.msg});
		            }
		        });
		}
    </script>
</head>
<body>
<div class="body-box-form">
    <div class="btn-panel pull-left">
        <div class="btn-group-sm">
            <button type="button" class="btn btn-system"
                    onclick="doExcelNow('商家返利信息导入模板','modelDataTable')">下载模板
            </button>
            <button type="button" class="btn btn-system" onclick="loadData()">加载数据</button>
            <button type="button" class="btn btn-system" onclick="importData()">导入数据</button>
        </div>
    </div>
    <div class="query-form" style="padding: 0;border: none">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <house:token></house:token>
            <input type="hidden" name="jsonString" value=""/>
            <div class="form-group">
                <input type="file" style="position:relative;top:2px;left:20px;" id="file" name="file"
                       accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="content-list">
            <table id="dataTable"></table>
        </div>
        <div style="display: none">
            <table id="modelDataTable"></table>
        </div>
    </div>
</div>
</body>
</html>

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
                height: $(document).height() - $("#content-list").offset().top - 70,
                rowNum: 10000,
                colModel: [
                    {name: "isvalid", index: "isvalid", width: 100, label: "数据是否有效", sortable: true, align: "left", hidden: true},
                    {name: "info", index: "info", width: 120, label: "数据是否有效", sortable: true, align: "left"},
                    {name: "resrCustPoolNo", index: "resrCustPoolNo", width: 100, label: "线索池编号", sortable: true, align: "left", hidden: true},
                    {name: "czybh", index: "czybh", width: 100, label: "操作员编号", sortable: true, align: "left"},
                    {name: "zwxm", index: "zwxm", width: 80, label: "姓名", sortable: true, align: "left"},
                    {name: "weight", index: "weight", width: 50, label: "权重", sortable: true, align: "right"},
                    {name: "groupSign", index: "groupsign", width: 150, label: "组标签", sortable: true, align: "left"},
                ]
            });

            Global.JqGrid.initJqGrid("dataTable_template", {
                colModel: [
                    {name: "czybh", index: "czybh", width: 100, label: "操作员编号", sortable: true, align: "left"},
                    {name: "weight", index: "weight", width: 50, label: "权重", sortable: true, align: "right"},
                    {name: "groupsign", index: "groupsign", width: 150, label: "组标签", sortable: true, align: "left"},
                ]
            });

            $("#dataTable_template").addRowData(1, {
                "czybh": "00127",
                "weight": "1",
                "groupsign": "AAA"
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
                formData.append("resrCustPoolNo", "${resrCustPoolEmp.resrCustPoolNo}");
                formData.append("file", document.getElementById("file").files[0]);

                loadSuccessfully = true;
                invalidRecords = 0;

                $.ajax({
                    url: "${ctx}/admin/resrCustPool/member/loadExcel",
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
                "${ctx}/admin/resrCustPool/member/doImportExcel",
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
                    onclick="doExcelNow('线索池成员导入模板','dataTable_template')">下载模板
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
            <table id="dataTable_template"></table>
        </div>
    </div>
</div>
</body>
</html>

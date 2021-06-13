<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp"%>
</head>

<body>
<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="submit" class="btn btn-system " id="orderBtn" onclick="doSave()">
                    <span>保存</span>
                </button>
                <button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
                    <span>关闭</span>
                </button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form action="" method="post" id="page_form" class="form-search">
                <house:token></house:token>
                <ul class="ul-form">
                    <div class="row">
                        <div class="col-sm-12">
                            <li class="form-validate">
                                <label><span class="required">*</span>名称</label>
                                <input type="text" id="descr" name="descr"/>
                            </li>
                            <li class="form-validate">
                                <label>颜色</label>
                                <select id="color" name="color" onchange="changeSelectionColor(this)">
                                    <option value ="#FFFFFF" selected="selected">默认白色</option>
								</select>
                            </li>
                            <li class="form-validate">
                                <label>可多选</label>
                                <select id="isMulti" name="isMulti">
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </li>
                            <li class="form-validate">
                                <label>显示顺序</label>
                                <input type="text" id="dispSeq" name="dispSeq" value="0"/>
                            </li>
                        </div>
                    </div>
                </ul>
            </form>
        </div>
    </div>
</div>

<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li class="active"><a href="#content-list" data-toggle="tab">标签组内容</a></li>
    </ul>
    <div class="tab-content">
        <div class="panel panel-system" >
            <div class="panel-body">
                <div class="btn-group-xs">
                    <button type="button" class="btn btn-system" onclick="addRow()">
                        <span>新增</span>
                    </button>
                    <button type="button" class="btn btn-system" onclick="insertRow()">
                        <span>插入</span>
                    </button>
                    <button type="button" class="btn btn-system" onclick="deleteRow()">
                        <span>删除</span>
                    </button>
                    <button type="button" class="btn btn-system" onclick="moveUp()">
                        <span>上移</span>
                    </button>
                    <button type="button" class="btn btn-system" onclick="moveDown()">
                        <span>下移</span>
                    </button>
                </div>
            </div>
        </div>
        <div id="content-list">
            <table id="dataTable"></table>
        </div>
    </div>
</div>
</body>
<script>
    var editingCellReferenceClosure = function() {}

    $(function () {
    
        Global.JqGrid.initEditJqGrid("dataTable", {
	        sortable: true,
	        url: "",
	        postData: "",
	        height: $(document).height() - $("#content-list").offset().top - 80,
	        cellsubmit: "clientArray",
	        colModel: [
	            {name: "pk", index: "pk", width: 80, label: "主键", align: "left", hidden: true},
	            {name: "descr", index: "descr", width: 200, label: "标签名", align: "left", sortable: false, editable: true, edittype: 'text'},
	            {name: "dispseq", index: "dispseq", label: "显示顺序", width: 70, hidden: true, sortable: false},
	        ],
	        gridComplete:function() {
                $("#dataTable").jqGrid('resetSelection')
            },
            beforeSelectRow:function(id) {
                setTimeout(function() { relocate(id) }, 50)
            },
            beforeEditCell: function(rowid, cellname, value, iRow, iCol) {
                editingCellReferenceClosure = function() {
                    $("#dataTable").saveCell(iRow, iCol)
                }
            }
	    })
	    
	    $("#dataTable").sortableRows({
            start: function() { editingCellReferenceClosure() }
        })

        $("#page_form").bootstrapValidator({
            excluded: [':disabled'],
            fields: {
                descr: {
                    validators: {
                        notEmpty: {
                            message: '请输入名称'
                        }
                    }
                }
            },
        })
        
        initColorSelection()

    })
    
    function initColorSelection() {
        var colors = ['#2480E5', '#43B471', '#E69F23', '#00B368', '#9265CE',
                      '#5A89D1', '#F26C63', '#7A87B8', '#FC7736', '#788385',
                      '#7F7399', '#CC8A40', '#9D9A27', '#B46C6E', '#C8557E',
                      '#2FB4B2', '#989898', '#D4952B', '#E16355', '#5B7DCC']

        for (i = 0; i < colors.length; i++) {
            $("#color").append('<option value="' + colors[i] + '"></option>')
        }
    
        $("option").each(function(index, element) {
            $(element).css("background-color", element.value)
        })
    }
    
    function changeSelectionColor(o) {
        $(o).css("background-color", o.value)
    }
    
    var tempRowId = -1
    function addRow() {
        var grid = $("#dataTable")
        
        grid.addRowData(tempRowId, {pk: tempRowId}, 'first')
        grid.setSelection(tempRowId, true)
        deseclectEditingCell()
        
        tempRowId--
    }
    
    function insertRow() {
        var grid = $("#dataTable")
        var selectedRowId = grid.getGridParam("selrow")
        
        grid.addRowData(tempRowId, {pk: tempRowId}, 'before', selectedRowId)
        grid.setSelection(tempRowId, true)
        deseclectEditingCell()
        
        tempRowId--
    }
    
    function deseclectEditingCell() {
        var editingCell = $('td[class*="edit-cell success"]')
        
        if (editingCell.length) editingCell.removeClass('edit-cell success')
    }
    
    function deleteRow() {
        var grid = $("#dataTable")
        
        grid.delRowData(grid.getGridParam("selrow"))
    }
    
    function moveUp() {
        var grid = $("#dataTable")
        var selectedRowId = grid.getGridParam("selrow")
        var selectedTr = $("#" + selectedRowId)
        
        // jqGrid表格会有生成一个隐藏的空行而且是表格中的第一行
        // 此空行没有设置id属性
        // 上下移动行的时候要排除第一行的空行
        if (!selectedTr.prev().attr('id')) return
        
        selectedTr.prev().before(selectedTr)
        reorderRowNumber()
    }
    
    function moveDown() {
        var grid = $("#dataTable")
        var selectedRowId = grid.getGridParam("selrow")
        var selectedTr = $("#" + selectedRowId)
        
        selectedTr.next().after(selectedTr)
        reorderRowNumber()
    }
    
    function reorderRowNumber() {
        $("tr td:first-child").each(function(index, element) {
            if (index > 0) {
                $(element).text(index)
            }
        })
    }
    
    function sortTags() {
        var grid = $("#dataTable")
        var ids = grid.getDataIDs()
                
        for (i = 1; i <= ids.length; i++) {
            grid.setCell(ids[i - 1], "dispseq", i)
        }
    }

    function doSave() {
    
        sortTags()

        $("#page_form").bootstrapValidator("validate")
        if (!$("#page_form").data("bootstrapValidator").isValid()) return
        
        var tags = $("#dataTable").getRowData()
        for (i = 0; i < tags.length; i++) {
            if (!tags[i].descr) {
                art.dialog({content: '不允许保存空的标签'})
                return
            }
        }

        var data = $("#page_form").jsonForm()
        data.tagsJson = JSON.stringify(tags)
                
        $.ajax({
            url: "${ctx}/admin/custTag/doSave",
            type: "post",
            data: data,
            dataType: "json",
            cache: false,
            error: function (obj) {
                showAjaxHtml({"hidden": false, "msg": "保存出错"})
            },
            success: function(obj) {
                if (obj.rs) {
                    art.dialog({
                        content: obj.msg,
                        time: 1000,
                        beforeunload: function () {
                            closeWin()
                        }
                    })
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token)
                    art.dialog({
                        content: obj.msg,
                    })
                }
            }
        })
    }

</script>
</html>

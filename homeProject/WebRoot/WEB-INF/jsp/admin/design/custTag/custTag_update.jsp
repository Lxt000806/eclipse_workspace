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
    <script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
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
                <input type="hidden" id="expired" name="expired" value="${custTagGroup.expired}"/>
                <ul class="ul-form">
                    <div class="row">
                        <div class="col-sm-12">
                            <li class="form-validate">
                                <label>编号</label>
                                <input type="text" id="pk" name="pk" value="${custTagGroup.pk}" readonly="readonly"/>
                            </li>
                            <li class="form-validate">
                                <label><span class="required">*</span>名称</label>
                                <input type="text" id="descr" name="descr" value="${custTagGroup.descr}"/>
                            </li>
                            <li class="form-validate">
                                <label>创建人</label>
                                <input type="text" id="crtCzy" name="crtCzy" value="${custTagGroup.crtCzy}" readonly="readonly"/>
                            </li>
                            <li class="form-validate">
                                <label>显示顺序</label>
                                <input type="text" id="dispSeq" name="dispSeq" value="${custTagGroup.dispSeq}"/>
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
                            <li>
                                <label>过期</label>
                                <input type="checkbox" name="expiredCheckbox"
                                    ${custTagGroup.expired == 'T' ? 'checked' : ''} onclick="checkExpired(this)"/>
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
        <li class="active"><a href="#content-list" data-toggle="tab">标签信息</a></li>
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
    
        $("#crtCzy").openComponent_czybm({showValue:"${custTagGroup.crtCzy}"})
        $("#openComponent_czybm_crtCzy").blur()
    
        Global.JqGrid.initEditJqGrid("dataTable", {
	        url: "${ctx}/admin/custTag/goCustTagJqGridList",
	        postData: {tagGroupPk: $("#pk").val()},
	        height: $(document).height() - $("#content-list").offset().top - 80,
	        cellsubmit: "clientArray",
	        colModel: [
	            {name: "pk", index: "pk", width: 80, label: "主键", align: "left", key: true, hidden: true},
                {name: "descr", index: "descr", width: 200, label: "标签名", align: "left", editable: true, edittype: 'text', sortable: false},
                {name: "dispseq", index: "dispseq", width: 60, label: "顺序", align: "left", sortable: false, hidden: true},
                {name: "crtczyname", index: "crtczyname", width: 100, label: "创建人", align: "left", sortable: false, hidden: true},
                {name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", align: "left", sortable: false, formatter: formatTime, hidden: true},
                {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "最后修改人", align: "left", sortable: false, hidden: true},
                {name: "expired", index: "expired", width: 70, label: "过期", align: "left", sortable: false, hidden: true},
                {name: "actionlog", index: "actionlog", width: 50, label: "操作", align: "left", sortable: false, hidden: true}
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
        $("#color").val("${custTagGroup.color}")
        $("#color").css("background-color", "${custTagGroup.color}")
        
        $("#isMulti").val("${custTagGroup.isMulti}")
    })
    
    function initColorSelection() {
    
        /* var colors = ['#FF66FF', '#FF00FF', '#FFFF66', '#FFFF00',
                         '#00FF66', '#00FF00', '#66FFFF', '#00FFFF',
                         '#0066FF', '#0000FF', '#FF0066', '#FF0000']
        
        var colors = ['#5A89D1', '#2480E5', '#43B471', '#00B368',
                      '#8F7399', '#9265CE', '#F26C63', '#E16355',
                      '#989898', '#788385'] */
                      
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
        var selectedRowId = grid.getGridParam("selrow")
        var rowData = grid.getRowData(selectedRowId)
        
        var pk = parseInt(rowData.pk)
        
        if (!rowData.descr) {
            grid.delRowData(selectedRowId)
        } else {
            $.ajax({
	            url: "${ctx}/admin/custTag/checkTagUsage",
	            type: "post",
	            data: {tagPk: pk},
	            dataType: "json",
	            cache: false,
	            error: function (obj) {
	                showAjaxHtml({"hidden": false, "msg": "保存出错"})
	            },
	            success: function(obj) {
	                if (obj.datas <= 0) {
	                    art.dialog({
                            content:'确定删此除标签吗？',
                            lock: true,
                            ok: function() {
                                grid.delRowData(selectedRowId)
                            },
                            cancel: function() {}
                        })
	                } else {
	                    art.dialog({
						    content:'此标签有' + obj.datas + '条使用记录，确定删除？',
						    lock: true,
						    ok: function() {
						        grid.delRowData(selectedRowId)
						    },
						    cancel: function() {}
						})
	                }
	            }
	        })
        }
        
    }
    
    function moveUp() {
        var grid = $("#dataTable")
        var selectedRowId = grid.getGridParam("selrow")
        var selectedTr = $("#" + selectedRowId)
        
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
            url: "${ctx}/admin/custTag/doUpdate",
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

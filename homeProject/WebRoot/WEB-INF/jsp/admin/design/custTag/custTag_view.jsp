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
                <button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
                    <span>关闭</span>
                </button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form action="" method="post" id="page_form" class="form-search">
                <input type="hidden" id="expired" name="expired" value=""/>
                <house:token></house:token>
                <ul class="ul-form">
                    <div class="row">
                        <div class="col-sm-12">
                            <li class="form-validate">
                                <label>编号</label>
                                <input type="text" id="pk" name="pk" value="${custTagGroup.pk}" readonly="readonly"/>
                            </li>
                            <li class="form-validate">
                                <label>名称</label>
                                <input type="text" id="descr" name="descr" value="${custTagGroup.descr}" readonly="readonly"/>
                            </li>
                            <li class="form-validate">
                                <label>创建人</label>
                                <input type="text" id="crtCzy" name="crtCzy" value="${custTagGroup.crtCzy}" readonly="readonly"/>
                            </li>
                            <li class="form-validate">
                                <label>显示顺序</label>
                                <input type="text" id="dispSeq" name="dispSeq" value="${custTagGroup.dispSeq}" readonly="readonly"/>
                            </li>
                            <li class="form-validate">
                                <label>颜色</label>
                                <input type="text" id="color" name="color" readonly="readonly"/>
                            </li>
                            <li class="form-validate">
                                <label>可多选</label>
                                <select id="isMulti" name="isMulti" disabled="disabled">
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </li>
                            <li>
                                <label>过期</label>
                                <input type="checkbox" ${custTagGroup.expired == 'T' ? 'checked' : ''} disabled="disabled"/>
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

        <div id="content-list">
            <table id="dataTable"></table>
            
        </div>
    </div>
</div>
</body>
<script>

    $(function () {
    
        $("#crtCzy").openComponent_czybm({showValue:"${custTagGroup.crtCzy}"})
        $("#openComponent_czybm_crtCzy").blur()
    
        Global.JqGrid.initJqGrid("dataTable", {
	        url: "${ctx}/admin/custTag/goCustTagJqGridList",
	        postData: {tagGroupPk: $("#pk").val()},
	        height: $(document).height() - $("#content-list").offset().top - 80,
	        styleUI: "Bootstrap",
	        colModel: [
	            {name: "pk", index: "pk", width: 80, label: "主键", align: "left", hidden: true},
	            {name: "descr", index: "descr", width: 200, label: "标签名", sortable: false, align: "left"},
	            {name: "dispseq", index: "dispseq", width: 60, label: "顺序", sortable: false, align: "left"},
	            {name: "crtczyname", index: "crtczyname", width: 100, label: "创建人", sortable: false, align: "left"},
	            {name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: false, align: "left", formatter: formatTime},
	            {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "最后修改人", sortable: false, align: "left"},
	            {name: "expired", index: "expired", width: 70, label: "过期", sortable: false, align: "left"},
	            {name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: false, align: "left"}
	        ]
	    })
        
        initializeColor()

        $("#isMulti").val("${custTagGroup.isMulti}")
    })
    
    function initializeColor() {
        var colorInput = $('#color')
        colorInput.css('background-color', "${custTagGroup.color}")
    }

</script>
</html>

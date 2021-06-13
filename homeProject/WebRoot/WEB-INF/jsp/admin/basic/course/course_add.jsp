<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>课程管理--新增</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
//新增成绩明细信息
function goAddScoreDetailInfo(){
    Global.Dialog.showDialog("addScoreDetailInfo", {
        title : "成绩明细信息-增加",
        url : "${ctx}/admin/course/goAddScoreDetailInfo",
        height : 400,
        width : 500,
        returnFun : function(data){
            if(data){
                Global.JqGrid.addRowData("dataTable",data);
            }
        }
    });
}

//编辑成绩明细信息
function goUpdateScoreDetailInfo(){
    var ret = selectDataTableRow();
    if(!ret){
        art.dialog({
            content : "请选择一条记录"
        });
        return;
    }
    var updateUrl = "${ctx}/admin/course/goUpdateScoreDetailInfo?number=" + ret.number
                    + "&nameChi=" + ret.namechi + "&score=" + ret.score + "&isPass=" + ret.ispass
                    + "&isMakeUp=" + ret.ismakeup + "&makeUpScore=" + ret.makeupscore + "&remark=" + ret.remark;
    Global.Dialog.showDialog("updateScoreDetailInfo", {
        title : "成绩明细信息-编辑",
        url : updateUrl,
        height : 400,
        width : 500,
        returnFun : function(data){
            if(data){
                var id = $("#dataTable").jqGrid("getGridParam","selrow");
                Global.JqGrid.updRowData("dataTable", id, data);
            }
        }
    });
}

//查看成绩明细信息
function goViewScoreDetailInfo(){
    var ret = selectDataTableRow();
    if(!ret){
        art.dialog({
            content : "请选择一条记录"
        });
        return;
    }
    var viewUrl = "${ctx}/admin/course/goViewScoreDetailInfo?number=" + ret.number
                    + "&nameChi=" + ret.namechi + "&score=" + ret.score + "&isPass=" + ret.ispass
                    + "&isMakeUp=" + ret.ismakeup + "&makeUpScore=" + ret.makeupscore + "&remark=" + ret.remark;
    Global.Dialog.showDialog("viewScoreDetailInfo", {
        title : "成绩明细信息-查看",
        url : viewUrl,
        height : 400,
        width : 500
    });
}

//删除成绩明细信息
function deleteScoreDetailInfo(){
    var ret = selectDataTableRow();
    if(!ret){
        art.dialog({
            content : "请选择一条记录"
        });
        return;
    }
    var id = $("#dataTable").jqGrid("getGridParam","selrow");
    Global.JqGrid.delRowData("dataTable", id);
}

//从Excel导入
function goLoadExcel(){
    Global.Dialog.showDialog("loadExcel", {
        title : "成绩明细信息-从Excel导入",
        url : "${ctx}/admin/course/goScoreDetailInfoLoadExcel",
        height : 600,
        width : 1400,
        returnFun : function(data){
            if(data.datas.length > 0){
                for(var i = 0; i < data.datas.length; i ++){
                	if(data.datas[i].ispassdescr == "是"){
                		data.datas[i].ispass = "1";
                	} else {
                		data.datas[i].ispass = "0";
                	}
                	if(data.datas[i].ismakeupdescr == "是"){
                		data.datas[i].ismakeup = "1";
                	} else {
                		data.datas[i].ismakeup = "0";
                	}
                    Global.JqGrid.addRowData("dataTable", data.datas[i]);
                } 
            }
        }
    });
}


    $(function() {
        $("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {
                descr:{
                    validators:{
                        notEmpty:{
                            message:"课程名称不能为空"
                        }
                    }
                }, 
                nums:{  
                    validators: {
                        notEmpty:{
                            message:"课程期数不能为空"
                        },
                        numeric:{
                            message:"课程期数只能是数值"
                        }
                    } 
                },
                courseType:{
                    validators:{
                        notEmpty:{
                            message:"请选择课程类型"
                        }
                    }
                },
                beginDate:{
                    validators:{
                        notEmpty:{
                            message:"请选择开始时间"
                        }
                    }
                },
                endDate:{
                    validators:{
                        notEmpty:{
                            message:"请选择结束时间"
                        }
                    }
                }
            },
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        });
    
        //保存更新按钮                        
        $("#saveBtn").on("click", function() {
            $("#dataForm").bootstrapValidator('validate');
            if (!$("#dataForm").data('bootstrapValidator').isValid()) return;
            var datas = $("#dataForm").serialize();
            var courseScoreList = JSON.stringify($("#dataTable").getRowData());
            datas = datas + "&courseScoreList=" + courseScoreList;
            $.ajax({
                url : "${ctx}/admin/course/doAddCourse",
                type : "post",
                data : datas,
                dataType : "json",
                cache : false,
                error : function(obj) {
                    showAjaxHtml({
                        "hidden" : false,
                        "msg" : "保存数据出错~"
                    });
                },
                success : function(obj) {
                    if (obj.rs) {
                        art.dialog({
                            content : obj.msg,
                            time : 1000,
                            beforeunload : function() {
                                closeWin();
                            }
                        });
                    } else {
                        $("#_form_token_uniq_id").val(obj.token.token);
                        art.dialog({
                            content : obj.msg,
                            width : 200
                        });
                    }
                }
            });
        });
    });
    
$(function(){
    Global.JqGrid.initJqGrid("dataTable", {
            height : $(document).height() - $("#content-list").offset().top - 100,
            styleUI : 'Bootstrap',
            rowNum:-1,
            colModel : [
                    {name:"number", index:"number", width:100, label:"员工编号", sortable:true, align:"left"},
                    {name: "namechi", index: "nameChi", width: 70, label: "姓名", sortable: true, align: "left"},
                    {name: "gender", index: "gender", width: 70, label: "性别", sortable: true, align: "left"},
                    {name: "department1descr", index: "department1Descr", width: 80, label: "一级部门", sortable: true, align: "left"},
                    {name: "department2descr", index: "department2Descr", width: 80, label: "二级部门", sortable: true, align: "left"},
                    {name: "department3descr", index: "department3Descr", width: 80, label: "三级部门", sortable: true, align: "left"},
                    {name: "positionname", index: "positionName", width: 80, label: "职位", sortable: true, align: "left"},
                    {name: "joindate", index: "joinDate", width: 120, label: "加入日期", sortable: true, align: "left", formatter:formatTime},
                    {name: "phone", index: "phone", width: 80, label: "电话", sortable: true, align: "right"},
                    {name: "score", index: "score", width: 60, label: "成绩", sortable: true, align: "right"},
                    {name: "ispass", index: "isPass", hidden:true},
                    {name: "ispassdescr", index: "isPassDescr", width: 70, label: "是否毕业", sortable: true, align: "left"},
                    {name: "ismakeup", index: "isMakeUp", hidden:true},
                    {name: "ismakeupdescr", index: "isMakeUpDescr", width: 70, label: "是否补考", sortable: true, align: "left"},
                    {name: "makeupscore", index: "makeUpScore", width: 70, label: "补考成绩", sortable: true, align: "left"},
                    {name: "remark", index: "remark", width: 180, label: "备注", sortable: true, align: "left"}
            ]
    });
});
</script>
</head>
<body>
<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" class="btn btn-system" id="saveBtn">
                    <span>保存</span>
                </button>
                <button type="button" class="btn btn-system" id="closeBut"
                    onclick="closeWin(true)">
                    <span>关闭</span>
                </button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form action="" method="post" id="dataForm" class="form-search">
             <house:token></house:token>
             <input type="hidden" name="jsonString" value="" />
             <ul class="ul-form">
                 <div class="validate-group">
                     <li class="form-validate">
                         <label><span class="required">*</span>课程编号</label>
                         <input type="text" id="code" maxlength="10" name="code"
                             disabled="disabled" value="保存时自动生成" />
                     </li>
                     <li class="form-validate">
                         <label><span class="required">*</span>课程名称</label>
                         <input type="text" id="descr" name="descr" />
                     </li>
                     <li class="form-validate">
                         <label><span class="required">*</span>课程类型</label>
                         <house:dict id="courseType" dictCode="" sql=" select Code,Descr from tCourseType where expired = 'F' order by Code " 
                             sqlValueKey="Code" sqlLableKey="Descr" />
                     </li>
                     <li class="form-validate">
                         <label><span class="required">*</span>课程期数</label>
                         <input type="text" id="nums" name="nums" />
                     </li>
                 </div>
                 <div class="validate-group">
                     <li class="form-validate">
                         <label><span class="required">*</span>开始时间</label>
                         <input type="text" id="beginDate" name="beginDate" class="i-date"
                             onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                     </li>
                     
                     <li class="form-validate">
                         <label><span class="required">*</span>结束时间</label>
                         <input type="text" id="endDate" name="endDate" class="i-date"
                             onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                     </li>
                     <li class="form-validate">
                         <label class="control-textarea">备注</label>
                         <textarea id="remark" name="remark" style="width:160px;"></textarea>
                     </li>
                  </div>
                </ul>
            </form>
        </div>
    </div>
    <div class="btn-panel">
        <div class="btn-group-sm">
            <button type="button" class="btn btn-system " onclick="goAddScoreDetailInfo()">新增</button>
            <button type="button" class="btn btn-system " onclick="goUpdateScoreDetailInfo()">编辑</button>
            <button type="button" class="btn btn-system " onclick="deleteScoreDetailInfo()">删除</button>
            <button type="button" class="btn btn-system " onclick="goViewScoreDetailInfo()">查看</button>
            <button type="button" class="btn btn-system " onclick="goLoadExcel()">从Excel导入</button>
        </div>
    </div>
    <div class="tab-content">
        <div id="content-list">
            <table id="dataTable"></table>
        </div>
    </div>
</div>
</body>
</html>

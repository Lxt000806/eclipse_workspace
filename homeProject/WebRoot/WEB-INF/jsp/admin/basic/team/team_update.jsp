<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>添加团队信息</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/zTree/3.5.18/js/jquery.ztree.exhide-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript">
//从数组a中除去数组b中已有的元素
function array_diff(a, b) {
    for(var i=0;i<b.length;i++)
    {
      for(var j=0;j<a.length;j++)
      {
        if(a[j].code == b[i].code){
          a.splice(j,1);
          j=j-1;
        }
      }
    }
    return a;
}
    
function checkChildrenIsAllHidden(node){
    for(var subNode of node.children){
        if(subNode.isHidden === false){
            return false;
        }
    }
    return true;
}


function recursionHideNode(zTree, node){
    if(node === undefined || node === null) return;
    if(node.children === undefined || node.children.length === 0 || checkChildrenIsAllHidden(node)){
        zTree.hideNode(node);
        recursionHideNode(zTree, node.getParentNode());
    }
}

function recursionShowNode(zTree, node){
    if(node === undefined || node === null) return;
    var sameCodeNodes = zTree.getNodesByParam("code", node.code, null);
    for(var sameCodeNode of sameCodeNodes){
        zTree.showNode(sameCodeNode);
    }
}

function recursionRemoveNode(zTree, node){
    if(node === undefined || node === null) return;
    //移除非父节点，并递归检查该节点的父节点是否变为了非父节点
    if(node.isParent === false){
        zTree.removeNode(node);
        //递归移除没有子节点的父节点
        recursionRemoveNode(zTree, node.getParentNode());
    }
}

var alreadyAddTeamDept = new Array();

//添加团队部门按钮点击函数
function addTeamDept(){
    var allDeptZTree = $.fn.zTree.getZTreeObj("allDeptZTree");
    var selectedNodes = allDeptZTree.getCheckedNodes(true);
    var teamDeptZTree = $.fn.zTree.getZTreeObj("teamDeptZTree");
    array_diff(selectedNodes, alreadyAddTeamDept);
    alreadyAddTeamDept = alreadyAddTeamDept.concat(JSON.parse(JSON.stringify(selectedNodes)));
    $.fn.zTree.init($("#teamDeptZTree"), teamDeptZTreeSetting, null);
    //将选中的节点添加到团队部门zTree
    teamDeptZTree.addNodes(null, alreadyAddTeamDept, false);
    //递归隐藏选中的节点
    for(var selectedNode of selectedNodes){
       recursionHideNode(allDeptZTree, selectedNode);
    }
    allDeptZTree.checkAllNodes(false);
    teamDeptZTree.checkAllNodes(false);
    teamDeptZTree.expandAll(true);
}

//删除团队部门按钮点击函数
function deleteTeamDept(){
    var teamDeptZTree = $.fn.zTree.getZTreeObj("teamDeptZTree");
    var selectedNodes = teamDeptZTree.getCheckedNodes(true);
    array_diff(alreadyAddTeamDept, selectedNodes);
    var allDeptZTree = $.fn.zTree.getZTreeObj("allDeptZTree");
    for(var selectedNode of selectedNodes){
        recursionRemoveNode(teamDeptZTree, selectedNode);
        recursionShowNode(allDeptZTree, selectedNode);
    }
    allDeptZTree.checkAllNodes(false);
}

function save() {
    $("#dataForm").bootstrapValidator('validate');
       if (!$("#dataForm").data('bootstrapValidator').isValid()) return;
       var datas = $("#dataForm").serialize();
       var teamEmpListJson = JSON.stringify($("#dataTable").getRowData());
       //获取待添加的团队部门zTree的所有叶子节点
       var teamDeptZTree = $.fn.zTree.getZTreeObj("teamDeptZTree");
       //获取叶子节点
       var deptListJson = JSON.stringify(teamDeptZTree.getNodesByParam("isParent", false, null));
       datas += "&deptListJson=" + deptListJson + "&teamEmpListJson=" + teamEmpListJson;
    $.ajax({
           url : "${ctx}/admin/team/doUpdateTeam",
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
               if(obj.code === "1"){
                   art.dialog({
                       content : obj.info,
                       time : 1000,
                       beforeunload : function() {
                           closeWin();
                       }
                   });
               }else{
                   art.dialog({
                       content : obj.info,
                       width : 200
                   });
               }
           }
       });
}
    
//用于将对象的属性名全部转为小写
function nameTo_(object) {
    var regObj = new RegExp("([A-Z]+)", "g");
    for (var i in object) {
        if (object.hasOwnProperty(i)) {
            var temp = object[i];
            if (regObj.test(i.toString())) {
                temp = object[i.replace(regObj, function (result) {
                    return result.toLowerCase();
                })] = object[i];
                delete object[i];
            }
            if (typeof temp === 'object' || Object.prototype.toString.call(temp) === '[object Array]') {
                nameTo_(temp);
            }
        }
    }
    return object;
};
    
function addTeamEmp(){
    Global.Dialog.showDialog("teamEmpAdd",{
	    title:"添加团队成员",
	    url:"${ctx}/admin/team/goAddTeamEmp",
	    height: 300,
	    width:400,
	    returnFun : function(data){
            if(data){
                var emp = nameTo_(data);
                var rowDatas = $("#dataTable").jqGrid("getRowData");
                var maxPk;
                if(rowDatas.length === 0){
                    maxPk = 0;
                }else{
                    maxPk = parseInt(rowDatas[0].pk);
                }
                for(var i = 0; i < rowDatas.length; i ++){
                    if(parseInt(rowDatas[i].pk) > maxPk) maxPk = parseInt(rowDatas[i].pk);
                }
                emp.pk = maxPk + 1;
                Global.JqGrid.addRowData("dataTable",emp);
            }
        }
	});
}

function delTeamEmp(){
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

function updateTeamEmp(){
    var ret = selectDataTableRow();
    if(!ret){
        art.dialog({
            content : "请选择一条记录"
        });
        return;
    }
    Global.Dialog.showDialog("teamEmpUpdate",{
        title:"编辑团队成员",
        url:"${ctx}/admin/team/goUpdateTeamEmp?number=" + ret.number,
        height: 300,
        width:400,
        returnFun : function(data){
            if(data){
                if(data){
                var emp = nameTo_(data);
                emp.pk = ret.pk;
                var id = $("#dataTable").jqGrid("getGridParam","selrow");
                Global.JqGrid.updRowData("dataTable", id, emp);
            }
            }
        }
    });
}

//所有部门ZTree配置
var allDeptZTreeSetting = {
    check: {
        enable: true,
        chkStyle:"checkbox",
        chkboxType:{"Y":"ps", "N":"ps"},
        nocheckInherit: true
    },
    data:{
        key:{
            children:"children",
            name:"desc2"
        }
    }
};
    
//待添加的团队部门ZTree配置
var teamDeptZTreeSetting = {
    check: {
        enable: true,
        chkStyle:"checkbox",
        chkboxType:{"Y":"ps", "N":"ps"},
        nocheckInherit: true
    },
    data:{
        keep:{
            leaf:false,
            parent:false
        },
        key:{
            children:"unSupported",
            name:"desc2"
        },
        simpleData:{
            enable:true,
            idKey:"tId",
            pIdKey:"parentTId",
            rootPId:null
        }
    }
};

function contains(arr, obj) {
    var i = arr.length;
    while (i--) {
        if (arr[i].code === obj.code) {
            return true;
        }
    }
    return false;
}

function filterDeptNode(node){
    if(node.deptLevel === arguments[1].deptLevel && node.code === arguments[1].code){
        return true;
    }
    return false;
}

$(function() {
   //初始化ZTree
   var allDeptZTreeNodes =${deptZTreeJson};
   $.fn.zTree.init($("#allDeptZTree"), allDeptZTreeSetting, allDeptZTreeNodes);
   $.fn.zTree.init($("#teamDeptZTree"), teamDeptZTreeSetting, null);
   
   var alreadyAddDeptList = ${alreadyAddDeptListJson};
   var allDeptZTree = $.fn.zTree.getZTreeObj("allDeptZTree");
   var teamDeptZTree = $.fn.zTree.getZTreeObj("teamDeptZTree");
   
   for(var deptRecord of alreadyAddDeptList){
       var dept1Node = allDeptZTree.getNodesByFilter(filterDeptNode, true, undefined, {code:deptRecord.Department1, deptLevel:"1"});
       var dept2Node = allDeptZTree.getNodesByFilter(filterDeptNode, true, undefined, {code:deptRecord.Department2, deptLevel:"2"});
       var dept3Node = allDeptZTree.getNodesByFilter(filterDeptNode, true, undefined, {code:deptRecord.Department3, deptLevel:"3"});
       if(dept1Node !== null && !contains(alreadyAddTeamDept, dept1Node)) alreadyAddTeamDept.push(dept1Node);
       if(dept2Node !== null && !contains(alreadyAddTeamDept, dept2Node)) alreadyAddTeamDept.push(dept2Node);
       if(dept3Node !== null && !contains(alreadyAddTeamDept, dept3Node)) alreadyAddTeamDept.push(dept3Node);
   }
   teamDeptZTree.addNodes(null, alreadyAddTeamDept, false);
   //展开已添加的部门ztree全部节点
   teamDeptZTree.expandAll(true);
   
   //初始化团队人员表格
   Global.JqGrid.initJqGrid("teamMemberTable",{
       height:$(document).height()-$("#content-list").offset().top,
       styleUI:"Bootstrap",
       colModel : [
         {name:'number',index:'number', width:70, label:"员工编号", sortable:true, align:"right"},
         {name:'nameChi',index : 'nameChi',width : 70,label:'姓名',sortable : true,align : "left"},
         {name : 'positionDescr',index : 'positionDescr',width : 120,label:'职位',sortable : true,align : "left"},
         {name : 'dept1Descr',index : 'dept1Descr',width : 80,label:'一级部门',sortable : true,align : "left"},
         {name : 'dept2Descr',index : 'dept2Descr',width : 70,label:'二级部门',sortable : true,align : "left"},
         {name : 'dept3Descr',index : 'dept3Descr',width : 80,label:'三级部门',sortable : true,align : "left"}
       ]
   });
   
   //初始化设定人员表格
   Global.JqGrid.initJqGrid("dataTable",{
           url:"${ctx}/admin/team/getTeamEmps?teamCode=${team.code}",
           height:$(document).height()-$("#content-list").offset().top,
           styleUI:"Bootstrap",
           colModel : [
             {name:'pk',index:'pk', width:40, label:"PK", sortable:true, align:"right"},
             {name:'number',index:'number', width:70, label:"员工编号", sortable:true, align:"right"},
             {name:'namechi',index : 'namechi',width : 70,label:'姓名',sortable : true,align : "left"},
             {name : 'lastupdate',index : 'lastupdate',width : 120,label:'最后修改时间',sortable : true,align : "left", formatter:formatTime},
             {name : 'lastupdatedby',index : 'lastupdatedby',width : 80,label:'修改人',sortable : true,align : "left"},
             {name : 'expired',index : 'expired',width : 70,label:'是否过期',sortable : true,align : "left"},
             {name : 'actionlog',index : 'actionlog',width : 80,label:'操作',sortable : true,align : "left"}
           ]
       });
       
   $("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {
                code:{
                    validators:{
                        notEmpty:{
                            message:"编号不能为空"
                        },
                        numeric:{
                            message:"编号只能是数字"
                        }
                    }
                }, 
                desc1:{
                    validators: {
                        notEmpty:{
                            message:"团队名称不能为空"
                        }
                    } 
                }
            },
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        });
});
</script>
<style type="text/css">
.help-block{
    margin-left:100px;
}
</style>
</head>
<body>
<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" class="btn btn-system" id="saveBtn" onclick="save()">
               <span>保存</span>
           </button>
           <button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(true)">
                <span>关闭</span>
            </button>
        </div>
    </div>
</div>
<form action="" method="post" id="dataForm" class="form-search">
	<div class="panel panel-info">
	    <div class="panel-body">
	        <input type="hidden" name="jsonString" value="" />
	        <ul class="ul-form">
	            <div class="validate-group row">
	                <li class="form-validate">
	                   <label style="width:200px;">编号</label>
	                   <input type="text" id="code" name="code" value="${team.code}" readonly="readonly" />
	               </li>
	            </div>
	        </ul>
	    </div>
	</div>
	<div class="container-fluid">
	    <!-- 标签页 -->
	    <ul class="nav nav-tabs">
	        <li class="active"><a href="#tab_main" data-toggle="tab">主项目</a></li>
	        <li class=""><a href="#tab_set_dept" data-toggle="tab">设定部门</a></li>
	        <li class=""><a href="#tab_team_member" data-toggle="tab">团队人员</a></li>
	        <li class=""><a href="#tab_set_member" data-toggle="tab">设定人员</a></li>
	    </ul>
	    <div class="tab-content">
	       <div id="tab_main" class="tab-pane fade in active">
                <jsp:include page="team_update_main_tab.jsp" />
           </div>
           <div id="tab_set_dept" class="tab-pane fade" style="overflow:hidden;">
                <jsp:include page="team_save_set_dept_tab.jsp" />
           </div>
           <div id="tab_team_member" class="tab-pane fade">
                <jsp:include page="team_save_member_tab.jsp" />
           </div>
           <div id="tab_set_member" class="tab-pane fade">
                <jsp:include page="team_save_set_member_tab.jsp" />
           </div>
	    </div>
    </div>
</form>
</body>
</html>

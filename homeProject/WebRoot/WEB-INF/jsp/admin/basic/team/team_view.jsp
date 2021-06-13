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
<script type="text/javascript">
//所有部门ZTree配置
var allDeptZTreeSetting = {
    data:{
        key:{
            children:"children",
            name:"desc2"
        }
    }
};

//已添加的团队部门ZTree配置
var teamDeptZTreeSetting = {
    data:{
        keep:{
            leaf:false,
            parent:false
        },
        key:{
            children:"unSupported",
            name:"desc1"
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

var alreadyAddTeamDept = new Array();

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
       url:"${ctx}/admin/team/getTeamMember?teamCode=${team.code}",
       height:$(document).height()-$("#content-list").offset().top - 200,
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
       height:$(document).height()-$("#content-list").offset().top - 200,
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
});
</script>
</head>
<body>
<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
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
	                   <input type="text" id="code" name="code" value="${team.code}" disabled="disabled" />
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
                <jsp:include page="team_view_main_tab.jsp" />
           </div>
           <div id="tab_set_dept" class="tab-pane fade" style="overflow:hidden;">
                <jsp:include page="team_view_set_dept_tab.jsp" />
           </div>
           <div id="tab_team_member" class="tab-pane fade">
                <jsp:include page="team_view_member_tab.jsp" />
           </div>
           <div id="tab_set_member" class="tab-pane fade">
                <jsp:include page="team_view_set_member_tab.jsp" />
           </div>
	    </div>
    </div>
</form>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div style="margin-left:80px;margin-top:20px;margin-bottom:20px;float:left;">
	<span>公司所有部门</span>
	<div style="border:1px solid silver;overflow:auto;">
	    <ul id="allDeptZTree" class="ztree" style="width:250px;height:350px;overflow:auto;"></ul>
	</div>
</div>
<div style="width:50px;float:left;padding-top:100px;">
	<button type="button" style="width:50px;margin-bottom:20px;" onclick="addTeamDept()" disabled="disabled">&gt;&gt;</button>
	<button type="button" style="width:50px;" onclick="deleteTeamDept()" disabled="disabled">&lt;&lt;</button>
</div>
<div style="float:left;margin-left:20px;margin-top:20px;margin-bottom:20px;">
    <span>团队部门</span>
    <div style="border:1px solid silver;overflow:auto;">
        <ul id="teamDeptZTree" class="ztree" style="width:250px;height:350px;overflow:auto;"></ul>
    </div>
</div>

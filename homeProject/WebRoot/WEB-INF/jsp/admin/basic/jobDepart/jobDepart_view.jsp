<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>查看任务部门</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function() {
    
    Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");
    Global.LinkSelect.setSelect({
                                    firstSelect:"department1",
                                    firstValue:"${jobDepart.department1}",
	                                secondSelect:"department2",
	                                secondValue:"${jobDepart.department2}"
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
			<div class="panel panel-info">
			    <div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
					    <input type="hidden" name="expired" value="${jobDepart.expired}" />
						<house:token></house:token>
						<ul class="ul-form">
						   <li class="form-validate">
		                       <label><span class="required">*</span>编号</label>
		                       <input type="text" name="pk" value="${jobDepart.pk}" disabled="disabled" />
	                       </li>
	                       <li class="form-validate">
	                           <label><span class="required">*</span>任务类型</label>
	                           <house:dict id="jobType" dictCode="" sql=" select Code,Descr from tJobType where expired = 'F' order by Code " 
                                    sqlValueKey="Code" sqlLableKey="Descr" value="${jobDepart.jobType}" disabled="true"/>
	                       </li>
	                       <li class="form-validate">
                               <label><span class="required">*</span>项目经理部门2</label>
                               <house:dict id="projectDepartment2" dictCode="" sql=" select Code,Desc2 from tDepartment2 where expired = 'F' and depType = '3' order by Code " 
                                    sqlValueKey="Code" sqlLableKey="Desc2" value="${jobDepart.projectDepartment2}" disabled="true" />
                           </li>
                           <li class="form-validate">
                               <label><span class="required">*</span>可选一级部门</label>
                               <select id="department1" name="department1" disabled="disabled"></select>
                           </li>
                           <li class="form-validate">
                               <label><span class="required">*</span>可选二级部门</label>
                               <select id="department2" name="department2" disabled="disabled"></select>
                           </li>
                           <li class="form-validate">
                               <label>过期</label>
                               <input type="checkbox" ${jobDepart.expired.equals('T')?'checked':''} disabled="disabled" />
                           </li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
	</html>

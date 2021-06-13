<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>JobTypeConfItemType明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
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
				    <input type="hidden" id="expired" name="expired" value="${jobTypeConfItemType.expired}" />
					<house:token></house:token>
					<ul class="ul-form">
					   <li class="form-validate">
	                       <label><span class="required">*</span>编号</label>
	                       <input type="text" name="pk" value="${jobTypeConfItemType.pk}" readonly="readonly" />
                       </li>
                       <li class="form-validate">
                           <label><span class="required">*</span>任务类型</label>
                           <house:dict id="jobType" dictCode="" sql=" select Code,Descr from tJobType where expired = 'F' order by Code " 
                                   sqlValueKey="Code" sqlLableKey="Descr" value="${jobTypeConfItemType.jobType}" disabled="true"/>
                       </li>
                       <li class="form-validate">
                        <label><span class="required">*</span>施工材料类型</label>
                        	<house:dict id="confItemType" dictCode="" sql=" select Code,Descr from tConfItemType where expired = 'F' order by Code " 
                                    sqlValueKey="Code" sqlLableKey="Descr" value="${jobTypeConfItemType.confItemType}" disabled="true"/>
                       </li>
                       <li class="form-validate">
                           <label>过期</label>
                           <input type="checkbox" onclick="checkExpired(this)" ${jobTypeConfItemType.expired.equals('T')?'checked':''} disabled="disabled"/>
                       </li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/common.jsp" %>
<div class="body-box-form">
    <div class="panel-body">
	   <ul class="ul-form">
			<div class="validate-group row" style="min-height:200px;">
			    <li class="form-validate">
			       <label style="width:200px;">团队名称</label>
			       <input type="text" id="desc1" name="desc1" value="${team.desc1}" />
			    </li>
			    <li>
					<label>是否计算业绩</label>
					<house:xtdm id="isCalcPerf" dictCode="YESNO" style="width:160px;"  />
				</li>
			    <li class="form-validate">
		           <label class="control-textarea" style="width:200px;">描述</label>
		           <textarea id="remark" name="remark" rows="8" style="width:300px;">${team.remark}</textarea>
		        </li>
			</div>
		</ul>
    </div>
</div>

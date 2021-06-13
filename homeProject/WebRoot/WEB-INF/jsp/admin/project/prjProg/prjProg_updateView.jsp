<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>采购入库</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
  </head>
  <body>
  	 <div class="body-box-form">
  			<!-- panelBar -->
  				<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
					</div>
			</div>
  			<div class="infoBox" id="infoBoxDiv"></div>
  			<!-- edit-form -->
  				<div class="panel panel-info" >  
         <div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
							<ul class="ul-form">
							<li hidden="true">
								<label>readonly</label>
								<input type="text" id="readonly" name="readonly"   style="width:160px;" value="${prjProg.readonly }" readonly="readonly"/>
							</li>
							<li>
								<label>施工项目编号</label>
								<input type="text" id="pk" name="pk"  placeholder="保存自动生成" style="width:160px;" value="${prjProg.pk }" readonly="readonly"/>
							</li>
							<li>
								<label><span class="required">*</span>施工项目名称</label>
								<house:xtdm id="prjItem" dictCode="PRJITEM"  style="width:166px;"  value="${prjProg.prjItem }" ></house:xtdm>
							</li>
							<li hidden="true">
								<label >施工项目名称</label>
								<input type="text" id="prjDescr" name="prjDescr"   style="width:160px;" value="${prjProg.prjDescr }" readonly="readonly"/>
							</li>
							<li>
								<label>计划开始日期</label>
								<input type="text" id="planBegin" name="planBegin" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${prjProg.planBegin }' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>计划结束日期</label>
								<input type="text" id="planEnd" name="planEnd" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${prjProg.planEnd}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>实际开始日期</label>
								<input type="text" id="beginDate" name="beginDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${prjProg.beginDate }' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>实际结束日期</label>
								<input type="text" id="endDate" name="endDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${prjProg.endDate }' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>调整计划结束时间</label> 
								<input type="text" id="adjPlanEnd" name="adjPlanEnd" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${prjProg.adjPlanEnd }' pattern='yyyy-MM-dd'/>"  />
							</li>
							<li >
								<label>是否过期</label>
								<input type="checkbox"  id="expired_show" name="expired_show"
								 value="${prjProg.expired }" onclick="confirm(this)" 
								 ${prjProg.expired!='F'?'checked':'' } />
								 </li>
							<li hidden="true">
								<label>是否过期</label>
								<input type="text" id="expired" name="expired"   style="width:160px;" value="${prjProg.expired }" />
  							</li>
  						</ul>	
  				</form>
  				</div>
  		</div>
  	</div> 

<script type="text/javascript">

</script>
  </body>
</html>


















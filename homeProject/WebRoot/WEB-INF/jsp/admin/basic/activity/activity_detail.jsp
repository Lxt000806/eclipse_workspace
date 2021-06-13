<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>展会活动管理--修改</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	    <script src="${resourceRoot}/pub/component_cmpactivity.js?v=${v}" type="text/javascript"></script>
	    <script type="text/javascript">
	    	$(function() {
		    $("#cmpActNo").openComponent_cmpactivity({callBack:validateRefresh}); 
			$("#cmpActNo").openComponent_cmpactivity({showLabel:"${activity.cmpActDescr}",showValue:"${activity.cmpActNo}",
				condition:{status:'4'}});
			$("openComponent_cmpactivity_cmpActNo").attr("readonly",true);
			});
		</script>
	</head>
	<body>
	 	<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
	     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<ul class="ul-form">	
							<input type="text" id="expired" name="expired" value="${activity.expired}" hidden="true" />			  
							<li>
								<label>编码</label>
								<input type="text" id="no" name="no" value="${activity.no}" readonly="readonly" />
							</li>
							<li class="form-validate">	
								<label>名称</label>
								<input type="text" id="actName" name="actName" value="${activity.actName}"/>
							</li>
							<li>
								<label>编号前缀</label>
								<input type="text" id="prefix" name="prefix" value="${activity.prefix}"/>
							</li>
							<li class="form-validate">
								<label>界数</label>
								<input type="text" id="times" name="times" value="${activity.times}"/>
							</li>
							<li class="form-validate">
								<label>编号长度</label>
								<input type="text" id="length" name="length" value="${activity.length}" />
							</li>
							<li class="form-validate">
								<label>地点</label>
								<input type="text" id="sites" name="sites" value="${activity.sites}" />
							</li>
							<li>
								<label>公司活动编码</label>
								<input type="text" id="cmpActNo" name="cmpActNo" value="${activity.cmpActNo}" />
							</li>
							<li>
								<label>最后更新人员</label>
								<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" value="${activity.lastUpdatedBy}" />
							</li>
							<li>
								<label>最后更新日期</label>
								<input type="text" id="lastUpdate" name="lastUpdate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${activity.lastUpdate}" pattern='yyyy-MM-dd'/>" />
							</li>
							<li class="form-validate">
								<label>开始时间从</label>
								<input type="text" id="beginDate" name="beginDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${activity.beginDate}" pattern='yyyy-MM-dd'/>" />
							</li>
							<li class="form-validate">						
								<label>至</label>
								<input type="text" id="endDate" name="endDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${activity.endDate}" pattern='yyyy-MM-dd'/>" />
							</li>	
							<li>
								<label class="control-textarea">备注</label>
								<textarea rows="2" id="remarks" name="remarks" maxlength="200">${activity.remarks}</textarea>
							</li>
						</ul>	
						<div class="validate-group row">
							<li>
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show"
									onclick="checkExpired(this)" ${activity.expired=="T"?"checked":""} />
							</li>
						</div>	
					</form>
				</div>
	  		</div>
	  	</div>
	 </body>
</html>



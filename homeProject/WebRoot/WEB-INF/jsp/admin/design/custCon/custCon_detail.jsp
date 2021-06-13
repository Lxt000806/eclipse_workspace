<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>CustCon明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$("#conMan").openComponent_employee({showLabel:"${custCon.conManDescr}",showValue:"${custCon.conMan}"});
	if("${custCon.custCode}"!=""){
		$("#custCode").openComponent_customer({showLabel:"${custCon.descr}",showValue:"${custCon.custCode}"});	
	}
	
});
</script>
</head>
<body>
	<div class="body-box-list">
	     <div class="panel panel-system">
    <div class="panel-body" >
      <div class="btn-group-xs" >
      <button type="button" class="btn btn-system"   onclick="closeWin()">关闭</button>
      </div>
   </div>
	</div>
        <div class="panel panel-info">  
                <div class="panel-body">  
                    <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
						<input type="hidden" name="exportData" id="exportData">
						<ul class="ul-form">
							<c:if test="${custCon.custCode!=''}">
								<li class="form-validate" >
									<label><span class="required">*</span>客户编号</label>
									  <input type="text" id="custCode" name="custCode" class="form-control" value="${custCon.custCode}">	
								</li>
								<li >
									<label>楼盘</label>
									<input readonly="readonly" type="text" id="custAddress" name="custAddress"   value="${custCon.custAddress}" />
								</li>
							</c:if>
							<c:if test="${custCon.custCode==''}">
								<li class="form-validate" >
									<label>客户编号</label>
									  <input type="text" id="resrCustCode" name="resrCustCode" class="form-control" readonly value="${custCon.resrCustCode}">	
								</li>
								<li >
									<label>楼盘</label>
									<input readonly="readonly"  type="text" id="resrAddress" name="resrAddress"   value="${custCon.resrAddress}" />
								</li>
							</c:if>
							<li>
							<label >设计师</label>
								<input readonly="readonly"  type="text" id="designManDescr" name="designManDescr" style="width:160px;"  value="${custCon.designManDescr}" />
							</li>
							<li>
								<label>业务员</label>
								<input readonly="readonly" Descr="NameChi"  type="text" id="businessManDescr" name="businessManDescr" style="width:160px;"  value="${custCon.businessManDescr}" />
							</li>
							<li>
								<label >联系时间 </label>
								<input type="text"   style="width:160px;" id="conDate" name="conDate" class="i-date" value="<fmt:formatDate value='${custCon.conDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
							<li>
								<label >联系人 </label>
								<input type="text" id="conMan" name="conMan" style="width:160px;" value="${custCon.conManDescr}" />
							</li>
							<li>
								<label>客户类型</label>
								<house:dict id="custType" dictCode="" 
									sql="select code,code+' '+desc1 desc1 from tCustType where expired= 'F' order by dispSeq " value="${custCon.custType }" sqlValueKey="Code" sqlLableKey="Desc1"></house:dict>
							</li>
							<li>
								<label>跟踪类型</label>
								<house:xtdm id="type" dictCode="CUSTCONTYPE" value="${custCon.type}"></house:xtdm>
							</li>
							<li>
								<label >跟踪时长 （秒）</label>
								<input type="text" id="conDuration" name="conDuration" style="width:160px;" value="${custCon.conDuration}" />
							</li>
							<li>
								<label >下次联系时间 </label>
								<input type="text" id="nextConDate" name="nextConDate" class="i-date" value="<fmt:formatDate value='${custCon.nextConDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
							<li>
								<label>跟踪方式</label>
								<house:xtdm id="conWay" dictCode="CONWAY" value="${custCon.conWay }"></house:xtdm>
							</li>
						</ul>   
		            </form>
                </div>  
         
        </div>  

   
		</div>
</body>
</html>


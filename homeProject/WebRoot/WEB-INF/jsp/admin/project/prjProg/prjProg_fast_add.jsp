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
  	
  		<div class="content-form">
  			<!-- panelBar -->
  			<div class="panelBar">
  				<ul>
  					<li>
  						<a href="javascript:void(0)" class="a1" id="saveBtn">
  							<span>保存</span>
  						</a>
  					</li>
  					<li id="closeBut" onclick="closeWin(false)">
  						<a href="javescript:void(0)" class="a2">
  							<span>关闭</span>
  						</a>		
  					</li>
  					<li class="line"></li>
  				</ul>
  				<div class="clear_float"></div>
  			</div>
  			<div class="infoBox" id="infoBoxDiv"></div>
  			<!-- edit-form -->
  			<div class="edit-form" id="edit-form">
  				<form action="" method="post" id="dataForm">
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<table cellspacing="0" cellpadding="0" width="100%">
						<col width="100"/>
						<col width="150"/>
						<tbody>
						<tr>	
							<td class="td-label">施工项目编号</td>
							<td class="td-value">
								<input type="text" id="confirmBegin" name="confirmBegin"  placeholder="保存自动生成" style="width:160px;" readonly="readonly"/>
							</td>
						</tr>
						<tr>	
							<td class="td-label">施工项目名称</td>
							<td class="td-value">
								<house:xtdm id="prjItem" dictCode="PRJITEM"  style="width:166px;" value="${prjProg.prjItem }" ></house:xtdm>
							</td>
						</tr>
						<tr>	
							<td class="td-label">计划开始日期</td>
							<td class="td-value">
								<input type="text" id="planBegin" name="planBegin" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${prjProg.planBegin }' pattern='yyyy-MM-dd'/>" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">计划结束日期</td>
							<td class="td-value">
								<input type="text" id="planEnd" name="planEnd" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${prjProg.planEnd}' pattern='yyyy-MM-dd'/>" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">实际开始日期</td>
							<td class="td-value">
								<input type="text" id="beginDate" name="beginDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${prjProg.beginDate }' pattern='yyyy-MM-dd'/>" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">实际结束日期</td>
							<td class="td-value">
								<input type="text" id="endDate" name="endDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${prjProg.endDate }' pattern='yyyy-MM-dd'/>" />
							</td>
						</tr>
					</tbody>
				</table>
  				</form>
  			</div><!-- edit-form end -->
  		</div>
  	</div> 

<script type="text/javascript">
$(function(){
	$("#saveBtn").on("click",function(){
		 var selectRows = [];
 		 var datas=$("#dataForm").jsonForm();
		 	if(datas.prjItem ==''){
				art.dialog({
	  	          content: "请填写完整信息"
			        });
		       return false;
			}	 		
			selectRows.push(datas);
		 	Global.Dialog.returnData = selectRows;
			closeWin();
	}); 

});




</script>
  </body>
</html>


















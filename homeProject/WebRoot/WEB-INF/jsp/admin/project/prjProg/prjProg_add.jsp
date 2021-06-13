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
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
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
							<li hidden="true">
								<label>hasPrjItem</label>
								<input type="text" id="hasPrjItem" name="hasPrjItem"   style="width:160px;" value="${prjProg.hasPrjItem }" readonly="readonly"/>
							</li>
							<li hidden="true">
								<label>custCode</label>
								<input type="text" id="custCode" name="custCode"   style="width:160px;" value="${prjProg.custCode }" readonly="readonly"/>
							</li>
							<li hidden="true">
								<label>actCode</label>
								<input type="text" id="actCode" name="actCode"   style="width:160px;" value="${prjProg.actCode }" readonly="readonly"/>
							</li>
							<li>
								<label>施工项目编号</label>
								<input type="text" id="pk" name="pk"  placeholder="保存自动生成" style="width:160px;" value="${prjProg.pk }" readonly="readonly"/>
							</li>
							<li>
								<label><span class="required">*</span>施工项目名称</label>
								<house:xtdm id="prjItem" dictCode="PRJITEM"  style="width:160px;"  value="${prjProg.prjItem }" ></house:xtdm>
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
							<li >
								<label>是否过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="${prjProg.expired }" onclick="confirm(this)" />
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
$(function(){

$("#saveBtn").on("click",function(){
	var hasPrjItem = $.trim($("#hasPrjItem").val());
	var prjItem = $.trim($("#prjItem").val());
	var arr = [];
	arr = hasPrjItem.split(",");
	var datas=$("#dataForm").jsonForm();
		if(datas.prjItem ==''){
			art.dialog({
	            content: "请填写完整信息"
	        });
	        return false;
		}
	for(var i=0;i<arr.length;i++){
		if(prjItem==arr[i]){
			art.dialog({
 				content:'该项目已存在，不能重复选择',
 			});
 		return false;
		}
	}
	$.ajax({
		url:'${ctx}/admin/prjProg/doUpdateAdd',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
			if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
	    	}else{
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
	}); 
	
	 if("${prjProg.readonly }"!=''){
	 	$(".a1").css("display","none");
	 }
});
function confirm(obj){
	if ($(obj).is(':checked')){
		$('#expired').val('T');
	}else{
		$('#expired').val('F');
	}
}
</script>
  </body>
</html>

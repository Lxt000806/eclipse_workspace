<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>工程进度延误备注</title>
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
							<li>
								<label>客户编号</label>
								<input type="text" id="code" name="code" value="${customer.code }" style="width:160px;" readonly="readonly"/>
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${customer.address}" readonly="readonly"/>
							</li>
							<li>
								<label>客户状态</label>
								<house:xtdm id="status" dictCode="CUSTOMERSTATUS" value="${customer.status}"  disabled="true"></house:xtdm>
							</li>
							<li>
								<label>施工状态</label>
								<house:xtdm id="constructStatus" dictCode="CONSTRUCTSTATUS" value="${customer.constructStatus }" unShowValue="${unshow }"></house:xtdm>
							</li>
							<li>
								<label>关系客户</label>
								<house:xtdm id="relCust" dictCode="RELCUST" value="${customer.relCust }"></house:xtdm>
							</li>
							<li>
								<label>工地状况</label>
								<house:xtdm id="buildSta" dictCode="BUILDSTA" value="${customer.buildSta }"></house:xtdm>
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="prgRemark" name="prgRemark" rows="2">${customer.prgRemark }</textarea>
  							</li>
  						</ul>	
  				</form>
  				</div>
  				</div>
  		</div>
  	</div> 

<script type="text/javascript">
$(function(){
	$("#saveBtn").on("click",function(){
		var datas = $("#dataForm").serialize();
		var constructStatus =$.trim($("#constructStatus").val());
		if(constructStatus==''){
			art.dialog({
				content:"施工状态不能为空",			
			});
			return;
		}
		
		$.ajax({
			url:'${ctx}/admin/prjProg/doPrgRemark',
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


});
</script>
  </body>
</html>








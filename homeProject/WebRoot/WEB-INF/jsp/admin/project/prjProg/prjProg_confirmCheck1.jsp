<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>巡检验收确认</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
$(function() {
	
});

$(function(){

 	//验收通过     
	$("#pass").on("click",function(){
		if(!$("#page_form").valid()) {return false;}//表单校验
		var datas = $("#page_form").serialize();
		$.ajax({
			url:'${ctx}/admin/prjProg/doConfirmCheck',
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
	
	//退回重拍
	$("#return").on("click",function(){
		if(!$("#page_form").valid()) {return false;}//表单校验
		var datas = $("#page_form").serialize();
			$.ajax({
				url:'${ctx}/admin/prjProg/doConfirmReturn',
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
</head>
<body>
	<div  style="width:40%;float:left ;margin-left: 1px; class="container"  >
			<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="pass">
							<span>验收通过</span>
						</button>
						<button type="button" class="btn btn-system " id="return">
							<span>重新整改</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
         <div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
			<input type="hidden" name="m_umState" id="m_umState" value="A"/>
						<ul class="ul-form">
							<li  hidden="true">
								<label>no</label>
								<input type="text" id="no" name="no" style="width:160px;"  value="${prjProgCheck.no }" readonly="readonly"/>                                             
							</li>
							<li hidden="true">
								<label >prjDescr</label>
								<input type="text" id="prjDescr" name="prjDescr" style="width:160px;"  value="${prjProgCheck.prjDescr }" readonly="readonly"/>                                             
							<li hidden="true">
								<label >projectMan</label>
								<input type="text" id="projectMan" name="projectMan" style="width:160px;"  value="${prjProgCheck.projectMan }" readonly="readonly"/>                                             
							<li hidden="true">
								<label >custcode</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${prjProgCheck.custCode }" readonly="readonly"/>                                             
							<li>
								<label>地址</label>
								<input type="text" id="address" name="address" style="width:160px;"  value="${prjProgCheck.address }" readonly="readonly"/>                                             
							<li>
								<label>巡检项目</label>
								<house:xtdm id="prjItem" dictCode="PRJITEM"  value="${prjProgCheck.prjItem}" disabled="true"></house:xtdm>
							<li>
								<label>安全问题</label>
								<house:xtdm id="safeProm" dictCode="CHECKPROM"  value="${prjProgCheck.safeProm}" disabled="true"></house:xtdm>
							<li>
								<label>形象问题</label>
								<house:xtdm id="visualProm" dictCode="CHECKPROM"  value="${prjProgCheck.visualProm}" disabled="true"></house:xtdm>
							<li>
								<label>工艺问题</label>
								<house:xtdm id="artProm" dictCode="CHECKPROM"  value="${prjProgCheck.artProm}" disabled="true"></house:xtdm>
							<li>
								<label>整改限时</label>
								<input  type="text" id="modifyTime" name="modifyTime" style="width:160px;" value="${prjProgCheck.modifyTime}"/>                    
							<li>
								<label>剩余整改限时</label>
								<input type="text" id="remainModifyTime" name="remainModifyTime" style="width:160px" value="${prjProgCheck.remainModifyTime}"/>
							<li>
								<label class="control-textarea">备注</label>
								<textarea style="width:160px"  id="remarks" name="remarks" rows="2" >${prjProgCheck.remarks}</textarea>
							</li>
						</ul>
				</form>
				</div>
				</div>
		</div>
	<div style="width:58%; height:560px ; float: right;margin-left: 1px; ">
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >  
		        <li class="active"><a href="#tab_zgPicture" data-toggle="tab">整改图片</a></li>
		        <li class=""><a href="#tab_xjPicture" data-toggle="tab">巡检图片</a></li>
		    </ul> 
		    <div class="tab-content">  
		        <div id="tab_zgPicture"  class="tab-pane fade in active"> 
		         	<jsp:include page="tab_zgPicture.jsp"></jsp:include>
		        </div> 
		        <div id="tab_xjPicture"  class="tab-pane fade "> 
		         	<jsp:include page="tab_xjPicture.jsp"></jsp:include>
		        </div> 
		    </div>  
		</div>
	</div>		
</body>
</html>

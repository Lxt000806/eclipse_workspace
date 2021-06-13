<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>员工出门申请</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	
	function save(flag){
  		$("#dataForm").bootstrapValidator('validate');
  		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
  		var datas = getJsonData([{
  			key: 'reApply',
  			value: flag,
  			type: 'B' },{
  			key: 'type',
  			value:$("#type").val(),
  			type: 'S' }
  			]);
  		$.ajax({
  			url:"${ctx}/admin/oa/erpApp/doComplete?taskId=${taskId}",
  			type: 'post',
  			data: datas,
  			dataType: 'json',
  			cache: false,
  			error: function(obj){
  				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
  		    },
  		    success: function(obj){
  		    	if(obj.rs){
      				$("#_form_token_uniq_id").val(obj.datas.token);
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
  	}
	//校验函数
	$(function() {
		$("#dataForm").bootstrapValidator({
	        message : 'This value is not valid',
	        feedbackIcons : {/*input状态样式图片*/
	            validating : 'glyphicon glyphicon-refresh'
	        },
	        fields: {
		      	type: {
		      		validators: { 
			            notEmpty: { 
			            	message: '类型不能为空'  
			            }
			        }
		      	},
		      	erpCzy: {
		      		validators: { 
			            notEmpty: { 
			            	message: '审批人不能为空'  
			            }
			        }
		      	},
	      	},
	        submitButtons : 'input[type="submit"]'
	    });
	});
	</script>
</head>
<body>
<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body" >
	     	 <div class="btn-group-xs" >
		    	<button type="button" class="btn btn-system" onclick="save(true)">调整申请</button>
		   	   	<button type="button" class="btn btn-system" onclick="save(false)">取消申请</button>
		   	   	<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	    	  </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
        	<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
            	<house:token></house:token>
            	<ul class="ul-form">
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label class="control-textarea">驳回原因</label>
						<textarea id="erpCzyBackReason" name="erpCzyBackReason" maxlength="200" readonly="readonly">${appErp.erpCzyBackReason }</textarea>
            		</li>
            	</div>	
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>服务类型</label>
						<house:xtdm id="type" dictCode="OA_ERP_TYPE"  style="width:160px;" value="${appErp.type }" ></house:xtdm>
            		</li>
            	</div>
            	<div class="validate-group row">
            		<li class="form-validate">
            			<label><span class="required">*</span>审批人</label>
            			<select id="erpCzy" name="erpCzy" style="width: 160px;">
	            			<c:forEach items="${erpCzyList }" var="item" varStatus="st">
		            			<c:if test="${st.index==0 }">
		            				<option value="${item.id }" selected="selected">${item.id }-${item.name }</option>
		            			</c:if>
		            			<c:if test="${st.index!=0 }">
		            				<option value="${item.id }">${item.id }-${item.name }</option>
		            			</c:if>
	            			</c:forEach>
            			</select>
            		</li>
            	</div>
            	</ul>
            </form>
         </div>
     </div>
</div>
</body>
</html>


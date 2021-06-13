<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>主材选定预约</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<style type="text/css">
	.commiColor{
		background-color: yellow;
	}
</style>
<script type="text/javascript">
function doConfirm(){
//验证
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/custItemConfirm/doConfirm',
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
}
$(function(){
  $("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
              
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {  
	        confItemType_NAME: {  
	        validators: {  
	            notEmpty: {  
	            message: '施工材料分类不能为空'  
	            }  
	        }  
	       }  
    	},
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        });

})

</script>

</head>
    
<body>
<div class="body-box-form" >
 <div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" class="btn btn-system "  onclick="doConfirm()">保存</button>
	      <button type="button" class="btn btn-system"  onclick="closeWin()">关闭</button>
	      </div>
	   </div>
	</div>
		 <div class="panel panel-info">  
             <div class="panel-body">
               <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" id="custCode" name="custCode" 	value="${custItemConfDate.custCode}" />
				<input type="hidden" id="itemTimeCode" name="itemTimeCode" 	value="${custItemConfDate.itemTimeCode}" />
				<ul class="ul-form">
				<div class="validate-group row" >
					<div class="col-sm-6" >
							<li>
							<label>楼盘 </label>
							<input type="text" id="address"
							name="address" 
						readonly="readonly"	value="${custItemConfDate.address}" />
					</li>
					</div>
					<div class="col-sm-6" >
						<li class="form-validate ">
							<label>确认日期 </label>
							<input type="text" id="confirmDate"
								name="confirmDate" class="i-date" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								onchange="validateRefresh('confirmDate')"  required data-bv-notempty-message="确认日期不能为空"
								value="<fmt:formatDate value='${custItemConfDate.confirmDate}' pattern='yyyy-MM-dd'/>" />
						</li>
					</div>
				</div>			
				<div class="validate-group row">
					<div class="col-sm-6" >	
						<li class="form-validate ">
							<label>施工材料分类 </label>
							<house:DictMulitSelect id="confItemType" dictCode="" 
								sql="SELECT code,Descr  FROM tConfItemType  WHERE Code NOT  IN (SELECT ConfItemType FROM  dbo.tCustItemConfirm 
									WHERE CustCode='${custItemConfDate.custCode}' AND  ItemConfStatus IN ('2','3')) and Expired = 'F' and ItemType1 = 'ZC' " 
								sqlLableKey="descr" sqlValueKey="code" onCheck="validateRefresh('confItemType_NAME')" ></house:DictMulitSelect>
						</li>
					</div>	
					<div class="col-sm-6" >	
						<li class="form-validate ">
							<label>确认状态 </label>
						
							<select  id="status" name="status">
							<option value="2">已确认</option>
							<option value="3">无需求</option>
							</select>
						</li>
					</div>		
				</div>				
				<div class="validate-group row">
					<div class="col-sm-12" >
					<li>
							<label class="control-textarea">确认说明 </label>
								<textarea style="width: 800px"  id="remarks" name="remarks"  ></textarea>
							</li>	
					</div>
				</div>	
				</ul>
			</form>
			</div>
		</div>
	<div class="clear_float"> </div>
		<!--query-form-->
	</div>
</body>
</html>



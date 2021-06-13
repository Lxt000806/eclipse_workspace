<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加材料明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
//校验函数	
$(function() {
	$("#saveBtn").on("click",function(){	
		
	 	var custType=$("#custType_NAME").val();
	 	var arr = custType.split(",");
	 	var selectRows = [];
	 	for(var i = 0; i<arr.length; i++){
	 		var custTypeItem = {};
	 		custTypeItem.custType = arr[i].split(" ")[0];
	 		custTypeItem.custTypeDescr = arr[i].split(" ")[1];
	 		selectRows.push(custTypeItem);	
	 	}
		var custTypes="${gift.custTypes}";
		Global.Dialog.returnData = selectRows;			
		closeWin();
	});	
});
</script>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" id="saveBtn" class="btn btn-system "  >保存</button>
		      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<input type="hidden" id="custTypeDescr" name="custTypeDescr" value="${gift.custTypeDescr}" />
					<house:token></house:token>
					<ul class="ul-form">
						<li>
							<label>客户类型</label> 
							<house:DictMulitSelect
									id="custType" dictCode="" sql="select Code,Desc1 from tCusttype where Expired ='F' and Code not in (${gift.custTypes}) order by dispSeq"
									sqlValueKey="code" sqlLableKey="desc1">
							</house:DictMulitSelect>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

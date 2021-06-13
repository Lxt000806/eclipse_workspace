<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  	<title>基装增减业绩</title>
  	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  	<META HTTP-EQUIV="expires" CONTENT="0"/>
  	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  	<%@ include file="/commons/jsp/common.jsp" %>

  	<script type="text/javascript">
    function save() {
      	//验证
      	var datas = $("#dataForm").serialize();
      	$.ajax({
        	url: "${ctx}/admin/baseItemChg/doZjyj",
        	type: "post",
        	data: datas,
        	dataType: "json",
        	cache: false,
        	error: function (obj) {
          		showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
        	},
        	success: function (obj) {
          		if (obj.rs) {
            		art.dialog({
              			content: obj.msg
            		});
            		closeWin();
          		} else {
            		$("#_form_token_uniq_id").val(obj.token.token);
            		art.dialog({
              			content: obj.msg,
              			width: 200
            		});
          		}
       		}
		});
   	}
  </script>
</head>
<body>
<div class="body-box-form">
  	<div class="panel panel-system">
    	<div class="panel-body">
      		<div class="btn-group-xs">
        		<button type="button" id="saveBtn" class="btn btn-system" onclick="save()">保存</button>
        		<button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
      		</div>
    	</div>
  	</div>
  	<div class="panel panel-info">
    	<div class="panel-body">
	      	<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
	        <house:token></house:token>
	        <input type="hidden" name="no" id="no" value="${baseItemChg.no}"/>
	        <ul class="ul-form">
	          	<li>
	            	<label>是否计算业绩</label>
	            	<select id="iscalPerf" name="iscalPerf">
	              		<option value="0">否</option>
	              		<option value="1" ${baseItemChg.iscalPerf==1?"selected":""}>是</option>
	            	</select>
	          	</li>
	        </ul>
	      	</form>
    	</div>
  	</div>
</div>
</body>
</html>

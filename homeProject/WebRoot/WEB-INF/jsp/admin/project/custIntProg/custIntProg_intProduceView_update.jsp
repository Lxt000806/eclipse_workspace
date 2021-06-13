<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>生产进度管理--编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function save(){
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/custIntProg/doIntProduceViewUpdate',
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

</script>

</head>	

	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
                <div class="panel-body">
                    <div class="btn-group-xs">
                        <button type="button" class="btn btn-system" id="saveBtn" onclick="save()">
                            <span>保存</span>
                        </button>
                        <button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
                            <span>关闭</span>
                        </button>
                    </div>
                </div>
            </div>
			<div class="panel panel-info">
			    <div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
					    <input type="hidden" name="pk" value="${intProduce.pk}"/>
						<house:token></house:token>
						<ul class="ul-form">
						   <li class="form-validate">
		                       <label>楼盘</label>
		                       <input type="text" value="${customer.address}" readonly="readonly" />
	                       </li>
	                       <li class="form-validate">
		                       <label>供应商</label>
		                       <input type="text" value="${supplier.descr}" readonly="readonly" />
	                       </li>
	                       <li class="form-validate">
		                       <label>是否橱柜</label>
		                       <input type="text" 
		                       	 value="${intProduce.isCupboard==null?'':intProduce.isCupboard=='1'?'是':'否'}" 
		                       	 readonly="readonly" />
	                       </li>
	                       <li class="form-validate">
						   	   <label>定板时间</label>
						   	   <input type="text" id="setBoardDate" name="setBoardDate" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${intProduce.setBoardDate}' pattern='yyyy-MM-dd'/>"/>
						   </li>
						   <li class="form-validate">
						   	   <label>到板时间</label>
						   	   <input type="text" id="arrBoardDate" name="arrBoardDate" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${intProduce.arrBoardDate}' pattern='yyyy-MM-dd'/>"/>
						   </li>
						   <li class="form-validate">
						   	   <label>开料时间</label>
						   	   <input type="text" id="openMaterialDate" name="openMaterialDate" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${intProduce.openMaterialDate}' pattern='yyyy-MM-dd'/>"/>
						   </li>
						   <li class="form-validate">
						   	   <label>封边时间</label>
						   	   <input type="text" id="sealingSideDate" name="sealingSideDate" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${intProduce.sealingSideDate}' pattern='yyyy-MM-dd'/>"/>
						   </li>
						   <li class="form-validate">
						   	   <label>排孔时间</label>
						   	   <input type="text" id="exHoleDate" name="exHoleDate" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${intProduce.exHoleDate}' pattern='yyyy-MM-dd'/>"/>
						   </li>
						   <li class="form-validate">
						   	   <label>包装时间</label>
						   	   <input type="text" id="packDate" name="packDate" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${intProduce.packDate}' pattern='yyyy-MM-dd'/>"/>
						   </li>
						   <li class="form-validate">
						   	   <label>入库时间</label>
						   	   <input type="text" id="inWhDate" name="inWhDate" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${intProduce.inWhDate}' pattern='yyyy-MM-dd'/>"/>
						   </li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
	</html>

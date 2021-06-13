<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>材料信息</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
function itemType1change(){	
   	if ($("#itemType1").val()=='LP'){
    	$("#isInv").removeAttr("disabled");	
	}else{
	   $("#isInv").val("1");
	   $("#isInv").attr("disabled",true); 
	}
}
function changeSendType(){	
   	if ($("#sendType").val()=='1'){
   		$("#whCode").attr("readonly",true);
	    $("#whCode").val(' ');
	}else{
	    $("#whCode").attr("readonly",false);
	}
}
function calPerfPer(){
	if ($.trim($("#itemType1").val())!="RZ"){
		 $("#perfPer").val(0);
		 return;
	}
	if ($.trim($("#cost").val())==""||$.trim($("#price").val())==""){
		return;	
	}
 	var sItemType2=$.trim($("#itemType2").val());
 	var dCost=0.0, dOfferPri=0.0;
 	dCost = parseFloat($.trim($("#cost").val()));
       dOfferPri = parseFloat($.trim($("#price").val()));
       if(dOfferPri==0.00){
       	$("#perfPer").val(0);
       }
 	$.ajax({
 		url:"${ctx}/admin/item/getPerfPer",
		type:'post',
		data:{itemType2:sItemType2,cost:dCost,price:dOfferPri},
		dataType:'json',
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
		},
		success:function(obj){
			if (obj){  
				$("#perfPer").val(obj.perfPer);
			}
		}
	});
	
}

//校验函数
$(function() {
	$("#isProm_show").show();
	$("#oldPrice_show").show();
	$("#expired_show").show();
	if("${isCostRight}"=="0"){
		$("#cost_show").hide();
		$("#projectCost_show").hide();	
		$("#oldCost_show").hide();
	}else{
		$("#cost_show").show();
		$("#projectCost_show").show();	
		$("#oldCost_show").show();
	}
});
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="content-form">
			<!--panelBar-->
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">关闭</button>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-info"
			style="margin-bottom: 8px;">
			<div class="panel-body">
				<form action="" method="post" id="dataForm1" class="form-search" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="${item.m_umState}" />
					<ul class="ul-form">
						<li class="form-validate"><label style="color:#777777"><span class="required">*</span>材料编号</label>
							<input type="text" id="code" name="code" placeholder="保存自动生成" value="${item.code}" readonly="readonly" />
						</li>
						<li id="expired_show" class="form-validate"><label>过期</label> <input type="checkbox" id="expired"
							name="expired" value="${item.expired}" onclick="checkExpired(this)" ${item.expired== "T"?"checked":""}/>
						</li>
						<li class="form-validate"><label style="color:#777777"><span class="required">*</span>最后修改时间</label>
							<input type="text" id="code" name="code" placeholder="保存自动生成" value="${item.lastUpdate}" readonly="readonly" />
						</li>
						<li class="form-validate"><label style="color:#777777"><span class="required">*</span>最后修改人</label>
							<input type="text" id="code" name="code" placeholder="保存自动生成" value="${employee.nameChi}" readonly="readonly" />
						</li>
					</ul>
				</form>
			</div>
		</div>
		<form action="" method="post" id="dataForm" class="form-search" target="targetFrame">
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_itemMain" data-toggle="tab">基础属性</a>
				</li>
				<li class=""><a href="#tab_itemSoft" data-toggle="tab">扩展属性</a>
				</li>
				<li class=""><a href="#tab_itemPicture" data-toggle="tab">材料图片</a>
				</li>
				<li>
                    <a href="#tab_supplierCost" data-toggle="tab">供应商供货价</a>
                </li>
			</ul>
			<div class="tab-content">
				<div id="tab_itemMain" class="tab-pane fade in active">
					<jsp:include page="tab_itemMain.jsp"></jsp:include>
				</div>
				<div id="tab_itemSoft" class="tab-pane fade ">
					<jsp:include page="tab_itemSoft.jsp"></jsp:include>
				</div>
				<div id="tab_itemPicture" class="tab-pane fade ">
					<jsp:include page="tab_itemPicture.jsp"></jsp:include>
				</div>
				<div id="tab_supplierCost" class="tab-pane fade ">
                        <jsp:include page="tab_supplierCost.jsp"></jsp:include>
                </div>
			</div>
		</div>
		</form>
	</div>
</body>
</html>

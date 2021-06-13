<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>仓库转移-查看新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	$(function() {
		if("${costRight}"=="0"){
			$("#costDiv").attr("hidden","true");
		}	
		(function getFillData(){
			$.ajax({
				url:"${ctx}/admin/itemTransferHeader/getQty",
				type: "get",
				data:{fromWHCode:"${itemTransferHeader.fromWHCode}",toWHCode:"${itemTransferHeader.toWHCode}",itCode:"${itemTransferHeader.itCode}"},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
					$("#fromQty").val(obj[0]);
					$("#avgCost").val(obj[1]);
					$("#toQty").val(obj[2]);
					/* if(obj[0]!=""&&obj[3]!=""){
						$("#notOnSale").val(obj[0]-obj[3]);
					} */
					var fromQty=$.trim($("#fromQty").val());
					var trfQty=$.trim($("#trfQty").val());
					var toQty=$.trim($("#toQty").val());
					//$("#fromAfterQty").val(myRound(fromQty,3)-myRound(trfQty,3));
					//$("#toAfterQty").val(myRound(toQty,3)+myRound(trfQty,3));
				}
			});	
		})();
		function getData(data){
			if (!data) return;
			$.ajax({
				url:"${ctx}/admin/itemTransferHeader/getQty",
				type: "get",
				data:{fromWHCode:"${itemTransferHeader.fromWHCode}",toWHCode:"${itemTransferHeader.toWHCode}",itCode:data.code},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
					$("#fromQty").val(obj[0]);
					$("#avgCost").val(obj[1]);
					$("#toQty").val(obj[2]);
					/* if(obj[0]!=""&&obj[3]!=""){
						$("#notOnSale").val(obj[0]-obj[3]);
					} */
				}
			});	
			$("#uom").val(data.uomdescr);
			$("#trfQty").val("");
			$("#itDescr").val(data.descr);
			$("#itemType2Descr").val(data.itemtype2descr);
		}	
		$("#itCode").openComponent_item({showValue:"${itemTransferHeader.itCode}",showLabel:"${item.descr}"});

		$("#saveBtn").on("click",function(){
			var fromAfterQty =$.trim($("#fromAfterQty").val());
			var toAfterQty =$.trim($("#toAfterQty").val());
			var trfQty =$.trim($("#trfQty").val());
			var notOnSale =$.trim($("#notOnSale").val());
			var noRepeat=$.trim($("#noRepeat").val());
			var itCode=$.trim($("#itCode").val());
			var itCodeArr=$.trim($("#itCodeArr").val());
			arr = itCodeArr.split(",");
			if(noRepeat=="0"){
				for(var i=0;i<arr.length;i++){
					if($.trim(arr[i])==itCode && $.trim(arr[i])!=oldItCode){
						art.dialog({
							content:"该产品编号已存在",
						});
					return false;
					}
				}
			}
			if(myRound(fromAfterQty)<0||myRound(toAfterQty)<0){
				art.dialog({
					content:"变动后数量不能小于0",
				});
				return;
			}
			if(myRound(notOnSale)<myRound(trfQty)){
				art.dialog({
					content:"转移数量不能大于未上架数量",
				});
				return;
			}
			var selectRows = [];
			var datas=$("#page_form").jsonForm();
				selectRows.push(datas);
				Global.Dialog.returnData = selectRows;
				closeWin();
		});
	});
	
	function changeQty(){
		var fromQty=$.trim($("#fromQty").val());
		var trfQty=$.trim($("#trfQty").val());
		var toQty=$.trim($("#toQty").val());
		$("#fromAfterQty").val(myRound(fromQty,3)-myRound(trfQty,3));
		$("#toAfterQty").val(myRound(toQty,3)+myRound(trfQty,3));
		if(myRound(fromQty)<myRound(trfQty)){
			art.dialog({
				content:"转移数量超过仓库剩余数量",
			});
			$("#trfQty").val("");
			return;
		} 		
	}
	</script>
</head>
<body>
	<div class="body-box-form">
		<div class="content-form">
  			<div class="panel panel-system">
   				<div class="panel-body">
      				<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="infoBox" id="infoBoxDiv"></div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
						<input type="hidden" id="fromWHCode" name="fromWHCode" style="width:160px;" value="${itemTransferHeader.fromWHCode}"/>
						<input type="hidden" id="toWHCode" name="toWHCode" style="width:160px;" value="${itemTransferHeader.toWHCode}"/>
						<input type="hidden" id="itDescr" name="itDescr" style="width:160px;" value="${item.descr }">
						<input type="hidden" id="itemType2Descr" name="itemType2Descr" style="width:160px;" value="${itemType2.descr }">
						<input type="hidden" id="noRepeat" name="noRepeat" style="width:160px;" value="${noRepeat }">
						<input type="hidden" id="itCodeArr" name="itCodeArr" style="width:160px;" value="${itCodeArr }">
						<ul class="ul-form">
							<div class="validate-group row">
								<li>
									<label>材料编号</label>
									<input type="text" id="itCode" name="itCode" style="width:160px;" value="${itemTransferHeader.itCode}"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>转移数量</label>
									<input type="text" id="trfQty" name="trfQty"  style="width:160px;" value="${itemTransferHeader.trfQty}"/>
									<input type="text" id="uom" name="uom" value="" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label class="control-textarea">备注</label>
									<textarea id="detailRemarks" name="detailRemarks" rows="2" style="width:160px">${itemTransferHeader.detailRemarks }</textarea>
								</li>
							</div>
							<ul class="nav nav-tabs"  >
						      	<li class="active" style="vertical-align:middle;margin-left:-10px;margin-bottom:0px" >
						      		<a data-toggle="tab">仓库结存</a>
						      	</li>
						   	</ul>
							<div  style="width:48%;height:200px ;float:left; border:1px solid #cfcfcf ;margin-top: 15px; class="container"  >
								<span style="position:relative;top:-10px;left:-200px;background: white; margin-left:-78px">源仓库</span>
								<li style="vertical-align:middle;margin-left:-17px;margin-top:10px">
									<label>现存数量</label>
									<input type="text" id="fromQty" name="fromQty" style="width:160px;" value="${itemTransferHeader.fromQty}" readonly="true"/>
									<input type="text" id="fromNowUom" name="fromNowUom" value="" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
								</li>
								<li style="vertical-align:middle;margin-left:-17px;margin-top:10px">
									<label>转移后数量</label>
									<input type="text" id="fromAfterQty" name="fromAfterQty" style="width:160px;" value="${fromQty}"/>
									<input type="text" id="uom" name="uom" value="" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
								</li>
								<div id="costDiv">
									<li style="vertical-align:middle;margin-left:-17px;">
										<label>成本单价</label>
										<input type="text" id="avgCost" name="avgCost" style="width:160px;" readonly="true"/>
										<input type="text" id="uom" name="uom" value="" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
									</li>
								</div>
								<li style="vertical-align:middle;margin-left:-17px;">
									<label>未上架数量</label>
									<input type="text" id="notOnSale" name="notOnSale" style="width:160px;" value="${itemTransferHeader.notOnSale}" readonly="true"/>
									<input type="text" id="notOnSaleUom" name="notOnSaleUom" value="" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
								</li>
							</div>
							<div style="width:48%; height:200px ;border:1px solid #cfcfcf; float: right;margin-top: 15px; ">
								<span style="position:relative;top:-10px; text-align: center;background: white; margin-left:17px;">至仓库</span>
								<li style="vertical-align:middle;margin-left:-17px;margin-top:10px">
									<label>现存数量</label>
									<input type="text" id="toQty" name="toQty" style="width:160px;" value="${itemTransferHeader.toQty}" readonly="true"/>
									<input type="text" id="toNowUom" name="toNowUom" value="" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
								</li>
								<li style="vertical-align:middle;margin-left:-17px;margin-top:10px">
									<label>转移后数量</label>
									<input type="text" id="toAfterQty" name="toAfterQty" style="width:160px;" value="${toQty }"/>
									<input type="text" id="uom" name="uom" value="" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
								</li>
							</div>
						</ul>	
					</form>
				</div>
			</div>  
		</div>			
	</div>
</body>
</html>

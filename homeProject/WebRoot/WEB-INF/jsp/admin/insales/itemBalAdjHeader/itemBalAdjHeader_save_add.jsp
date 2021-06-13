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
<title>仓库调整明细-新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
//校验函数
$(function() {
	$("#page_form").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			adjQty:{  
				validators: {  
					notEmpty: {  
						message: '调整数量不能为空'  
					}
				}  
			},
			adjCost:{  
				validators: {  
					notEmpty: 	{  
						message: '调整成本不能为空'  
					}
				}  
			},
			openComponent_item_itCode:{  
		        validators: {  
		            notEmpty: {  
		           		 message: '材料编号不能为空'  
		            }
		        }  
		     },
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	
});
	
		

function changeAmount(){
	var adjType = $.trim($("#adjType").val());
	var allQty= isNaN(parseFloat($("#allQty").val()))?0.0:parseFloat($("#allQty").val());//add by zzr parseFloat
  	var adjQty= isNaN(parseFloat($("#adjQty").val()))?0.0:parseFloat($("#adjQty").val());//add by zzr parseFloat
  	var cost=isNaN(parseFloat($("#cost").val()))?0.0:parseFloat($("#cost").val());//add by zzr parseFloat
    var adjCost=isNaN(parseFloat($("#adjCost").val()))?0.0:parseFloat($("#adjCost").val());//add by zzr parseFloat
    var cost2=isNaN(parseFloat($("#cost").val()))?0.0:parseFloat($("#cost").val());//add by zzr parseFloat
    var qty=isNaN(parseFloat($("#qty").val()))?0.0:parseFloat($("#qty").val());//add by zzr parseFloat 
   if(adjType=='2'){
   		if((allQty)<(adjQty)){//remove by zzr parseInt
   			art.dialog({
   				content:'出库数量超过现存储数量',
   			});
   		}
   }
	
    if(adjType=='1'){
	    qty = myRound((parseFloat(allQty) + parseFloat(adjQty))*10000,4)/10000; //update by zzr 
    }else{
    	qty = myRound((parseFloat(allQty) - parseFloat(adjQty))*10000,4)/10000; //update by zzr 
    }

    if(parseFloat(qty)>0){
	    if(!parseFloat(adjCost)){
	    	adjCost=0;
	    }
   		 //(现存*当前+调整数量*调整金额)/调整后数量
		if(adjType=='1'){
		    var aftCost = myRound(((parseFloat(cost)*parseFloat(allQty)+parseFloat(adjCost)*parseFloat(adjQty))/(parseFloat(qty)))*10000,4)/10000 ;//update by zzr
	    }else{
		    if(parseFloat(qty)!=0){
			    var aftCost = myRound(((parseFloat(cost)*parseFloat(allQty)-parseFloat(adjCost)*parseFloat(adjQty))/(parseFloat(qty)))*10000,4)/10000 ;//update by zzr
		    }else{
		    	var aftCost = myRound(parseFloat('${itemBalAdjDetail.cost}')*10000,4)/10000;//update by zzr
		    }
	    }
    }else{
    	var aftCost=cost2;//add by zzr 
    }
    
    if(parseFloat(qty)) {//add by zzr parseFloat
    	$("#qty").val(qty);
    }else{
     	$("#qty").val(0);
    }
    
    if(parseFloat(qty)>0){//add by zzr parseFloat
	    if(aftCost) {
	   		$("#aftCost").val(aftCost);
	    }else{
	     	$("#aftCost").val(0);
	    }
    }else{
    	 $("#aftCost").val(cost2);//update by zzr 
    }
}	

$(function(){
	$("#itCode").openComponent_item({callBack: getData});
	
	function getData(data){
		if (!data) return;

		$('#itCode').val(data.code);
		//$('#allQty').val(data.allqty);
		if(data.avgcostdescr!=''){
			$('#cost').val(data.avgcostdescr);
		}else{
			$('#cost').val(0.0);
		}
		$("#adjCost").val($("#cost").val());//调整成本默认为当前移动成本。 add by zb on 20191111
		$('#remarks').val(data.remark);
		$('#uom').val(data.uomdescr);
		$('#uom1').val(data.uomdescr);
		$('#uom2').val(data.uomdescr);
		$('#itDescr').val(data.descr);
		
		$.ajax({
			url:"${ctx}/admin/itemBalAdjHeader/getAjaxDetail",
			type:'post',
			data:{whCode: '${itemBalAdjDetail.whCode}', itCode:data.code},
			dataType:'json',
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				if (obj){
					$('#cost').val(obj.cost);
					$("#adjCost").val($("#cost").val());//调整成本默认为当前移动成本。
					$('#allQty').val(obj.allQty);
					$.ajax({
						url:"${ctx}/admin/itemBalAdjHeader/getAjaxNoPosi",
						type:'post',
						data:{whCode: '${itemBalAdjDetail.whCode}', itCode:data.code},
						dataType:'json',
						cache:false,
						error:function(obj){
							showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
						},
						success:function(obj){
							if (obj){
								$('#noPosiQty').val(obj.noPosiQty);
							}
						}
					});
				}
			}
		});
		validateRefresh();
	}
	$("#saveBtn").on("click",function(){
		var itCode = $.trim($("#itCode").val());
		var noPosiQty = isNaN(parseFloat($.trim($("#noPosiQty").val())))?0.0:parseFloat($.trim($("#noPosiQty").val())); // add by zzr parseFloat 2017/12/16
		var allQty = isNaN(parseFloat($.trim($("#allQty").val())))?0.0:parseFloat($.trim($("#allQty").val()));// add by zzr parseFloat 2017/12/16
		var adjQty = isNaN(parseFloat($.trim($("#adjQty").val())))?0.0:parseFloat($.trim($("#adjQty").val()));// add by zzr parseFloat 2017/12/16
		var adjCost = isNaN(parseFloat($.trim($("#adjCost").val())))?0.0:parseFloat($.trim($("#adjCost").val()));// add by zzr parseFloat 2017/12/16
		var adjType = $.trim($("#adjType").val());
	 	var qty = isNaN(parseFloat($.trim($("#qty").val())))?0.0:parseFloat($.trim($("#qty").val()));// add by zzr parseFloat 2017/12/16
		var aftCost = isNaN(parseFloat($.trim($("#aftCost").val())))?0.0:parseFloat($.trim($("#aftCost").val()));// add by zzr parseFloat 2017/12/16
		var noRepeat=$.trim($("#noRepeat").val());
		var allItCode=$.trim($("#allItCode").val());
		arr = allItCode.split(",");
		if(noRepeat=='0'){
			for(var i=0;i<arr.length;i++){
				if($.trim(arr[i])==itCode){
					art.dialog({
						content:'该产品编号已存在',
					});
				return false;
				}
			}
		}
		
		if(itCode==''){
				art.dialog({content: "请选取材料编号",width: 200});
				return false;
			}else if(adjQty=='') {
				art.dialog({content: "请填写调整数量",width: 200});
				return false;
			}else if(adjCost=='') {
				art.dialog({content: "请填写调整成本",width: 200});
				return false;
			}else if(qty<0){
				art.dialog({content: "调整后库存数量必须大于等于0，请重新调整",width: 200});
				return false;
			}else if(aftCost<0){
				art.dialog({content: "调整后成本必须大于等于0，请重新调整",width: 200});
				return false;
			}
		 if(adjType=='2'){
	   		if(allQty<adjQty){
	   			art.dialog({
	   				content:'出库数量超过现存储数量,不能保存',
	   			});
	   			return false;
	   		}else if(parseFloat(noPosiQty)<parseFloat(adjQty)){
					art.dialog({content: "出库数量超过现未上架数量,不能保存",width: 200});
				return false;
			}
  		 }	
			
		 var selectRows = [];
		 var datas=$("#page_form").jsonForm();
		 	selectRows.push(datas);
			Global.Dialog.returnData = selectRows;
			closeWin();
	}); 
});
function validateRefresh(){
		$('#page_form').data('bootstrapValidator')
		                   .updateStatus('openComponent_item_itCode', 'NOT_VALIDATED',null)  
		                   .validateField('openComponent_item_itCode');
}
</script>
</head>
  
<body>
<div class="body-box-form" >
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
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<ul class="ul-form">
							<li hidden="true">
								<label>allItCode</label>
								<input type="text" id="allItCode" name="allItCode" style="width:160px;" value="${itemBalAdjDetail.allItCode}"/>
							</li>
							<li hidden="true">
								<label>adjType</label>
								<input type="text" id="adjType" name="adjType" style="width:160px;" value="${itemBalAdjDetail.adjType}"/>
							</li>
							<li hidden="true">
								<label>noRepeat</label>
								<input type="text" id="noRepeat" name="noRepeat" style="width:160px;" value="${itemBalAdjDetail.noRepeat}"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>材料编号</label>
								<input type="text" id="itCode" name="itCode" style="width:160px;" value="${itemBalAdjDetail.itCode}"/>
								<span>&nbsp&nbsp&nbsp&nbsp</span>
							</li>
							<li>
								<label>现存数量</label>
								<input type="text" id="allQty" name="allQty" style="width:160px;" value="${itemBalAdjDetail.allQty}" readonly="true"/>
								<input type="text" id="uom" name="uom" value="${itemBalAdjDetail.uom}" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
							</li>
							<li>
								<label>当前移动成本</label>
								<input type="text" id="cost" name="cost" style="width:160px;"  value="${itemBalAdjDetail.cost}" readonly="true"/>
								<span>&nbsp&nbsp&nbsp&nbsp</span>
							</li>
							<li>
								<label>未上架数量</label>
								<input type="text" id="noPosiQty" name="noPosiQty" style="width:160px;" placeholder="0" value="${itemBalAdjDetail.noPosiQty}" readonly="true"/>
								<input type="text" id="uom2" name="uom2" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>调整成本</label>
								<input type="text" id="adjCost" name="adjCost" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" onblur="changeAmount()" value="${itemBalAdjDetail.adjCost}"/>
								<span>元</span>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>调整数量</label>
								<input type="text" id="adjQty" name="adjQty" style="width:160px;" onkeyup="value=value.replace(/[^\?\d.]/g,'')" onblur="changeAmount()" value="${itemBalAdjDetail.adjQty}"/>
								<input type="text" id="uom1" name="uom1" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
							</li>
							<li>
								<label>调整后成本</label>
								<input type="text" id="aftCost" name="aftCost" style="width:160px;" placeholder="0.0" value="${itemBalAdjDetail.aftCost}" readonly="true"/>
								<span>元</span>
							</li>
							<li>
								<label>调整后数量</label>
								<input type="text" id="qty" name="qty" style="width:160px;" placeholder="0" value="${itemBalAdjDetail.qty}" readonly="true"/>
								<input type="text" id="uom2" name="uom2" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" style="width:160px" rows="2">${itemBalAdjDetail.remarks }</textarea>
							</li>
							<li hidden="true">
								<label>材料名称</label>
								<input type="text" id="itDescr" name="itDescr" style="width:160px;" value="${itemBalAdjDetail.itDescr}"/>
							</li>
							<li hidden="true">
								<label>最后修改时间</label>
								<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;" value="<fmt:formatDate value='${itemBalAdjDetail.lastUpdate}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
							</li>
							<li hidden="true">
								<label>最后修改人</label>
								<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;" value="${itemBalAdjDetail.lastUpdatedBy}"/>
							</li>
							<li hidden="true">
								<label class="control-textarea">仓库编号</label>
								<input type="text" id="whCode" name="whCode" value="${itemBalAdjDetail.whCode}"/>
							</li>
						</ul>	
			</form>
			</div>
		</div> <!-- edit-form end -->
	</div>			
</div>
</body>
</html>

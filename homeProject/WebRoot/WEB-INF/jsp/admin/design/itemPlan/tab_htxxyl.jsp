<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>

<style type="text/css">
.form-search .ul-form li label {
    width: 130px;
    margin-right: 0px;
}
</style>

<script type="text/javascript">
var returnDesignFee=0;
var stdDesignFee=0;
$(function(){
	var baseDiscPerFee=myRound((1-"${custProfit.baseDiscPer}")*"${customer.baseFeeDirct}");
	$("#baseDiscPerFee").val(baseDiscPerFee);
	if ($("#position").val()!=""){
		stdDesignFee=myRound($("#position").val()*"${customer.area}");
		returnDesignFee=myRound("${custProfit.position}"*"${customer.area}"-"${custProfit.designFee }");
		$("#returnDesignFee").val(returnDesignFee);
	}
})
function changeOrder(obj){
 	window.parent.document.getElementById("isChange").value=1;
	var id=$(obj).attr('id');
	if ($(obj).is(':checked')){
		$('#'+id).val('1');
	}else{
		$('#'+id).val('0');
	}
}
function changeType(obj){
    window.parent.document.getElementById("isChange").value=1;
	window.parent.document.getElementById("payType").value=obj.value;
	/* if(obj.value==1) $("#position").hide();
	else $("#position").show(); */
}
function changeInput(obj){
    window.parent.document.getElementById("isChange").value=1;
	/* if(obj.value==1) $("#position").hide();
	else $("#position").show(); */
}
function save(isGo){
	if($("#position").val()==""){
		art.dialog({
			content: "请选择设计费标准！"
		});
		return;
	}
	if($("#payType").val()==""){
		art.dialog({
			content: "请选择付款方式！"
		});
		return;
	}
	var datas = $("#custProfiDataForm").serialize();
	datas=datas+ "&stdDesignFee=" + stdDesignFee;
	$.ajax({
		url:'${ctx}/admin/itemPlan/doCustProfitSave',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){	
				window.parent.document.getElementById("custPayPreIframe").src="${ctx}/admin/itemPlan/goProfitAnalyse_fkjhyl?custCode=${customer.code}&isAuto=1";
				window.parent.document.getElementById("hasCustProfit").value=1;
				window.parent.document.getElementById("isChange").value="";
				if(isGo){
					window.parent.goTab(isGo);
	    		}else{
		    		art.dialog({
						content: obj.msg,
						time: 1000
						
					});
		    	}
	    	}else{
				art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
	 setTimeout(function(){window.parent.document.getElementById("mlfxIframe").contentWindow.reload();},100);
}
function changeDesignFee(e){
	window.parent.document.getElementById("isChange").value=1;
	stdDesignFee=myRound(e.value*"${customer.area}");
	returnDesignFee=myRound(e.value*"${customer.area}"-"${custProfit.designFee }");
	$("#returnDesignFee").val(returnDesignFee);
}
function baseDiscPerChange(e){
	window.parent.document.getElementById("isChange").value=1;
	var baseDiscPerFee=myRound("${customer.baseFeeDirct }"-"${customer.baseFeeDirct}"*e.value);
	$("#baseDiscPerFee").val(baseDiscPerFee);
}

function updateReturnDesignFee(e){
	window.parent.document.getElementById("isChange").value=1;
	returnDesignFee=myRound($("#position").val()*"${customer.area}"-e.value);
	$("#returnDesignFee").val(returnDesignFee);
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div>
			<form role="form" class="form-search" id="custProfiDataForm" action="" method="post" target="targetFrame">
				<input type="hidden" id="custCode" name="custCode" value="${customer.code}" />
				<ul class="ul-form">
					<div class="row">
						<div class="col-sm-6">
							<li><label>付款方式</label> <house:dict id="payType" dictCode=""
										  sql=" select rtrim(a.paytype)+''+x1.note fd,a.payType from tpayrule a left join txtdm x1 on x1.cbm = a.paytype and x1.id ='timepaytype'
									       where a.CustType='${customer.custType}' and (a.Expired='F' or  '${customer.status}' in ('4','5')) order by  a.paytype "
									sqlValueKey="payType" sqlLableKey="fd" value="${custProfit.payType }"
									onchange="changeType(this)"></house:dict>
							</li>
						</div>
						<div class="col-sm-6">
							<li><label for="containBase">总价包含基础费</label> <input type="checkbox" onclick="changeOrder(this)"
								id="containBase" name="containBase" value="${custProfit.containBase}" ${custProfit.containBase==
								'1' ?'checked':'' }   ${customer.status== '4' ?'disabled':'' }/>
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>设计费标准</label> <house:dict id="position" dictCode=""
									sql="select  rtrim(cast(a.DesignFee as varchar(20))) + ' ' + b.Desc2 fd, 
											rtrim(a.DesignFee) DesignFee
										from tDesignFeeSd a
										left join tPosition b on a.Position = b.Code
										where a.CustType='${customer.custType}'
											or (not exists(select 1 from tDesignFeeSd in_b where in_b.CustType='${customer.custType}') 
												and (a.CustType='' or a.CustType is null)) 
											or (''='${customer.custType}' and (a.CustType='' or a.CustType is null))
										order by a.DispSeq "
									sqlValueKey="DesignFee" sqlLableKey="fd" value="${custProfit.position }"
									onchange="changeDesignFee(this)"></house:dict>
							</li>
						</div>
						<div class="col-sm-6">
							<li><label for="containMain">总价包含主材费</label> <input type="checkbox" onclick="changeOrder(this)"
								id="containMain" name="containMain" value="${custProfit.containMain}" ${custProfit.containMain==
								'1' ?'checked':'' } ${customer.status== '4' ?'disabled':'' }/>
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>面积</label> <input type="text" disabled="disabled" value="${customer.area }" />
							</li>
						</div>

						<div class="col-sm-6">
							<li><label for="containSoft">总价包含软装费</label> <input type="checkbox" onclick="changeOrder(this)"
								id="containSoft" name="containSoft" value="${custProfit.containSoft}" ${custProfit.containSoft==
								'1' ?'checked':'' } ${customer.status== '4' ?'disabled':'' }/>
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>实物赠送</label> <input type="text" id="gift" name="gift" value="${custProfit.gift }"  onchange="changeInput(this)"/>
							</li>
						</div>

						<div class="col-sm-6">
							<li><label for="containInt">总价包含集成费</label> <input type="checkbox" onclick="changeOrder(this)"
								id="containInt" name="containInt" value="${custProfit.containInt}" ${custProfit.containInt==
								'1' ?'checked':'' } ${customer.status== '4' ?'disabled':'' }/>
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>设计费</label> <input type="text" id="designFee" name="designFee"
								value="${custProfit.designFee }" onchange="updateReturnDesignFee(this)" />
							</li>
						</div>

						<div class="col-sm-6">
							<li><label for="containCup">总价包含橱柜费</label> <input type="checkbox" onclick="changeOrder(this)"
								id="containCup" name="containCup" value="${custProfit.containCup}" ${custProfit.containCup==
								'1' ?'checked':'' } ${customer.status== '4' ?'disabled':'' }/>
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>设计费返还</label> <input type="text" id="returnDesignFee" name="returnDesignFee" value="${custProfit.returnDesignFee }" readonly="true" />
							</li>
						</div>
						<div class="col-sm-6">
							<li><label for="containMainServ">总价包含服务产品费</label> <input id="containMainServ"
								onclick="changeOrder(this)" name="containMainServ" type="checkbox"
								value="${custProfit.containMainServ}" 
								${custProfit.containMainServ== '1' ?'checked':'' } ${customer.status== '4' ?'disabled':'' }/>
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>预交设计定金</label> <input type="text" id="prepay" name="prepay"
								value="${custProfit.prepay }"  onchange="changeInput(this)"/>
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>基础直接费</label> <input type="text" disabled="disabled" value="${customer.baseFeeDirct }" />
							</li>
						</div>

					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>直接费折让点</label> <input type="text" id="baseDiscPer" name="baseDiscPer"
								value="${custProfit.baseDiscPer }" onchange="baseDiscPerChange(this)" />
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>基础直接费优惠</label> <input type="text" id="baseDiscPerFee" disabled="disabled"  />
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>基础其它优惠1</label> <input type="text" id="baseDisc1" name="baseDisc1" 
								value="${custProfit.baseDisc1 }"  onchange="changeInput(this)" />
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>基础其它优惠2</label> <input type="text" id="baseDisc2" name="baseDisc2"
								value="${custProfit.baseDisc2 }" onchange="changeInput(this)"/>
							</li>
						</div>
					</div>
					<div class="row">
						<li><label></label>
							<button id="saveBut" type="button" class="btn btn-system btn-xs" onclick="save();" ${customer.status==
								'4'||customer.status=='5'||!isProfitEdit||contractStatus=='2'||contractStatus=='3'||contractStatus=='4' ?'disabled':'' }>保 存</button> <span> 说明：保存后将自动生成付款计划</span>
						</li>

					</div>
				</ul>
			</form>
		</div>
	</div>
	</div>
</body>
</html>

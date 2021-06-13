<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>毛利率业绩配置--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
	.ul-form div.row li label {
		width: 120px;
	}
</style>
<script type="text/javascript">
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				itemType12 : {
					validators : {
						notEmpty : {
							message : '材料类型不能为空'
						}
					}
				},
				isClearInv : {
					validators : {
						notEmpty : {
							message : '是否滞销品不能为空'
						}
					}
				},
				designCommiPer : {
					validators : {
						notEmpty : {
							message : '设计师提成点数不能为空'
						}
					}
				},
				buyerCommiPer : {
					validators : {
						notEmpty : {
							message : '买手提成点数不能为空'
						}
					}
				},
				fromProfit : {
					validators : {
						notEmpty : {
							message : '毛利率从不能为空'
						}
					}
				},
				toProfit : {
					validators : {
						notEmpty : {
							message : '毛利率至不能为空'
						}
					}
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});

	$(function() {

		$("#saveBtn").on("click", function() {
			$("#dataForm").bootstrapValidator('validate');
			if (!$("#dataForm").data('bootstrapValidator').isValid())
				return;
			var datas = $("#dataForm").serialize();

			$.ajax({
				url : "${ctx}/admin/profitPerf/doSave",
				type : "post",
				data : datas,
				dataType : "json",
				cache : false,
				error : function(obj) {
					showAjaxHtml({
						"hidden" : false,
						"msg" : "保存数据出错~"
					});
				},
				success : function(obj) {
					if (obj.rs) {
						art.dialog({
							content : obj.msg,
							time : 1000,
							beforeunload : function() {
								closeWin();
							}
						});
					} else {
						$("#_form_token_uniq_id").val(obj.token.token);
						art.dialog({
							content : obj.msg,
							width : 200
						});
					}
				}
			});
		});
	});
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">

					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate"><label>材料类型</label> <house:dict
									id="itemType12" dictCode=""
									sql="select a.Code,a.code+' '+a.descr descr  from tItemType12 a  where  a.Expired='F' and a.ItemType1='RZ'  order By Code "
									sqlValueKey="Code" sqlLableKey="Descr"
									value="${profitPerf.itemType12}" disabled="true">
								</house:dict></li>
							<li class="form-validate"><label>是否滞销品</label> <house:xtdm
									id="isClearInv" dictCode="YESNO"
									value="${profitPerf.isClearInv }" disabled="true"></house:xtdm>
							</li>

						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>设计师提成点数</label> <input
								type="text" id="designCommiPer" name="designCommiPer"
								style="width:160px;" value="${profitPerf.designCommiPer}"
								disabled="disabled" />
							</li>
							<li class="form-validate"><label>买手提成点数</label> <input
								type="text" id="buyerCommiPer" name="buyerCommiPer"
								style="width:160px;" value="${profitPerf.buyerCommiPer}"
								disabled="disabled" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>买手2提成点数</label> 
								<input type="text" id="buyer2CommiPer" name="buyer2CommiPer" 
									style="width:160px;" value="${profitPerf.buyer2CommiPer}" />
							</li>
							<li class="form-validate"><label>翻单员提成点数</label> <input type="text"
								id="againManCommiPer" name="againManCommiPer" style="width:160px;"
								value="${profitPerf.againManCommiPer}" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>业务员提成点数</label> <input type="text"
								id="businessManCommiPer" name="businessManCommiPer" style="width:160px;"
								value="${profitPerf.businessManCommiPer}" />
							</li>
							<li class="form-validate">
								<label>产品部经理提成点数</label> 
								<input type="text" id="prodMgrCommiPer" name="prodMgrCommiPer" 
									style="width:160px;" value="${profitPerf.prodMgrCommiPer}" />
							</li>
						</div>
						<div class="validate-group row">
                            <li class="form-validate">
                                <label>外部业务员提成点数</label> 
                                <input type="text" id="outBusinessManCommiPer" name="outBusinessManCommiPer" 
                                    style="width:160px;" value="${profitPerf.outBusinessManCommiPer}" />
                            </li>
                            <li class="form-validate">
                                <label><span class="required">*</span>软装业务员提成点</label> 
                                <input type="text" id="softBusinessManCommiPer" name="softBusinessManCommiPer" 
                                    style="width:160px;" value="${profitPerf.softBusinessManCommiPer}" />
                            </li>
                        </div>
						<div class="validate-group row">
							<li class="form-validate"><label>毛利率从</label> <input
								type="text" id="fromProfit" name="fromProfit"
								style="width:160px;" value="${profitPerf.fromProfit}"
								disabled="disabled" />
							</li>
							<li class="form-validate"><label>毛利率至</label> <input
								type="text" id="toProfit" name="toProfit" style="width:160px;"
								value="${toProfit}" disabled="disabled" />
							</li>
						</div>

					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>基础项目管理编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_basePlanTemp.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript"> 
	$(function(){
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields: {  
				descr:{  
					validators: {  
						notEmpty: {  
							message: '基础项目名称不能为空'  
						}
					}  
				},
				prjType:{  
					validators: {  
						notEmpty: {  
							message: '施工类型不能为空'  
						}
					}  
				},
				prjCtrlType:{  
					validators: {  
						notEmpty: {  
							message: '发包方式不能为空'  
						}
					}  
				},
				offerCtrl:{  
					validators: {  
						notEmpty: {  
							message: '人工发包不能为空'  
						}
					}  
				},
				category:{  
					validators: {  
						notEmpty: {  
							message: '项目分类不能为空'  
						}
					}  
				},
				baseItemType1:{  
					validators: {  
						notEmpty: {  
							message: '基装类型1不能为空'  
						}
					}  
				},
				cost:{  
					validators: {  
						notEmpty: {  
							message: '成本不能为空'  
						}
					}  
				},
				marketPrice:{  
					validators: {  
						notEmpty: {  
							message: '市场价不能为空'  
						}
					}  
				},
				offerPri:{  
					validators: {  
						notEmpty: {  
							message: '人工单价不能为空'  
						}
					}  
				},
				material:{  
					validators: {  
						notEmpty: {  
							message: '材料单价不能为空'  
						}
					}  
				},
				projectPrice:{  
					validators: {  
						notEmpty: {  
							message: '项目经理结算价不能为空'  
						}
					}  
				},
				perfPer:{  
					validators: {  
						notEmpty: {  
							message: '业绩比例不能为空'  
						}
					}  
				},
				allowPriceRise:{  
					validators: {  
						notEmpty: {  
							message: '是否允许价格上浮不能为空'  
						}
					}  
				},
				isOutSet:{  
					validators: {  
						notEmpty: {  
							message: '套餐外项目不能为空'  
						}
					}  
				},
				materialCtrl:{  
					validators: {  
						notEmpty: {  
							message: '材料发包不能为空'  
						}
					}  
				},
				isBaseItemSet:{  
					validators: {  
						notEmpty: {  
							message: '是否基础套餐包不能为空'  
						}
					}  
				},
				isBaseItemSet:{  
					validators: {  
						notEmpty: {  
							message: '是否基础套餐包不能为空'  
						}
					}  
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
		
	});
		$("#baseTempNo").openComponent_basePlanTemp({showValue:'${baseItem.baseTempNo}',showLabel:'${baseItem.baseTempDescr}',callBack:function(){validateRefresh('openComponent_basePlanTemp_baseTempNo')}});
		$("#dataForm").bootstrapValidator("addField", "openComponent_basePlanTemp_baseTempNo", {
	        validators: {         
	            remote: {
		            message: '',
		            url: '${ctx}/admin/basePlanTemp/getBasePlanTemp',
		            data: getValidateVal,  
		            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
		        }
	        }
	    });
		Global.LinkSelect.initSelect("${ctx}/admin/baseItemType1/baseItemType","baseItemType1","baseItemType2");
		Global.LinkSelect.setSelect({firstSelect:"baseItemType1",
								firstValue:"${baseItem.baseItemType1}",
								secondSelect:"baseItemType2",
								secondValue:"${baseItem.baseItemType2}"});
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemType", "itemType1", "itemType2")
		Global.LinkSelect.setSelect({firstSelect:"itemType1",
                                firstValue:"${baseItem.itemType1}",
                                secondSelect:"itemType2",
                                secondValue:"${baseItem.itemType2}"});
                                
        (function(category) {
            if (category === "4") {
		        $("#itemType1").removeAttr("disabled")
		        $("#itemType2").removeAttr("disabled")
            }
        })("${baseItem.category}");
                                
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator('validate');
			if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
			var datas = $("#dataForm").serialize();
			var prjCtrlType = $.trim($("#prjCtrlType").val());
			var materialCtrl = $.trim($("#materialCtrl").val());
			var offerCtrl = $.trim($("#offerCtrl").val());
			var perfPer = $.trim($("#perfPer").val());
			if(perfPer > 1){
				art.dialog({
					content:"业绩比例只能在0-1之间",
				});
				return;
			}
			if(prjCtrlType == "1"){
				if(offerCtrl > 1){
					art.dialog({
						content:"当发包方式为按比例发包时,人工发包比例不能大于1！",
					});
					return;
				}
				if(materialCtrl > 1){
					art.dialog({
						content:"当发包方式为按比例发包时,材料发包比例不能大于1！",
					});
					return;
				}
			}
			if( $.trim($("#isBaseItemSet").val())=="1"&& ( $.trim($("#baseTempNo").val())==""|| $.trim($("#fixAreaType").val())=="")){
				art.dialog({
					content:"套餐包项目关联模板和关联空间类型不能为空！",
				});
				return;
			}
			$.ajax({
				url:"${ctx}/admin/baseItem/doUpdate",
				type: "post",
				data: datas,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
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
		});
	});
	function changeProPrice(e){
		$("#"+e.id).val(e.value.replace(/[^\?\d.]/g,''));
		$("#projectPrice").val(myRound($("#material").val())+myRound($("#offerPri").val()));
	}
	
    function updateItemType(select) {
        var itemType1 = $("#itemType1")
        var itemType2 = $("#itemType2")
        
        if (select.value === "4") {
            itemType1.removeAttr("disabled")
            itemType2.removeAttr("disabled")
        } else {
            itemType1.val("")
            itemType1.attr("disabled", "true")
            itemType2.val("")
            itemType2.attr("disabled", "true")
        }
    }
	
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
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
						<input type="hidden" name="jsonString" value="" />
						<input type="hidden" id="expired" name="expired" value="${baseItem.expired }"/>
						<ul class="ul-form">
							<div class="row">
								<div class="col-xs-6">
									<li>
										<label>基础项目编号</label> 
										<input type="text" id="code" name="code" style="width:160px;" value="${baseItem.code }"readonly="readonly" />
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>基装项目名称</label> 
										<input type="text" id="descr" name="descr" style="width:160px;" value="${baseItem.descr }"/>
									</li>
									<li class="form-validate"><label>助记码</label> 
										<input type="text" id="remCode" name="remCode" style="width:160px;" value="${baseItem.remCode }"/>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>项目分类</label> 
										<house:xtdm id="category" dictCode="BASEITEMCAT" value="${baseItem.category}" onchange="updateItemType(this)"></house:xtdm>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>基装类型1</label>
										<select id="baseItemType1" name="baseItemType1"></select>
									</li>
									<li class="form-validate">
										<label>基装类型2</label>
										<select id="baseItemType2" name="baseItemType2"></select>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>成本</label> 
										<input type="text" id="cost" name="cost" style="width:160px;" onkeyup="value=value.replace(/[^\?\d.]/g,'')" value="${baseItem.cost }"/>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>市场价</label> 
										<input type="text" id="marketPrice" name="marketPrice" onkeyup="value=value.replace(/[^\?\d.]/g,'')" style="width:160px;" value="${baseItem.marketPrice }"/>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>人工单价</label> 
										<input type="text" id="offerPri" name="offerPri" style="width:160px;" onkeyup="changeProPrice(this)" value="${baseItem.offerPri }"/>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>材料单价</label> 
										<input type="text" id="material" name="material" style="width:160px;" onkeyup="changeProPrice(this)" value="${baseItem.material }"/>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>项目经理结算价</label> 
										<input type="text" id="projectPrice" name="projectPrice" onkeyup="value=value.replace(/[^\?\d.]/g,'')" style="width:160px;" value="${baseItem.projectPrice }"/>
									</li>
									<li class="form-validate">
										<label>是否固定价</label> 
										<house:xtdm id="isFixPrice" dictCode="YESNO" value="${baseItem.isFixPrice }" ></house:xtdm>
									</li>
									<li class="form-validate">
										<label>单位</label> 
										 <house:DataSelect id="uom" className="Uom" keyColumn="code" valueColumn="descr" value="${baseItem.uom }"></house:DataSelect>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>施工类型</label> 
										<house:xtdm id="prjType" dictCode="BASEITEMPRJTYPE" value="${baseItem.prjType }" ></house:xtdm>
									</li>
									<li class="form-validate">
										<label>显示顺序</label> 
										<input type="text" id="dispSeq" name="dispSeq" style="width:160px;" onkeyup="value=value.replace(/[^\?\d]/g,'')" value="${baseItem.dispSeq }"/>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>业绩比例</label> 
										<input type="text" id="perfPer" readonly="true" name="perfPer" onkeyup="value=value.replace(/[^\?\d.]/g,'')" style="width:160px;" value="${baseItem.perfPer }"/>
									</li>
                                    <li>
                                        <label>过期</label>
                                        <input type="checkbox" id="expired_show"  name="expired_show" value="${baseItem.expired }",
                                            onclick="checkExpired(this)" ${baseItem.expired=="T"?"checked":"" }/>
                                    </li>
								</div>
								<div class="col-xs-6">
									<li class="form-validate">
										<label> </label> 
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>发包方式</label> 
										<house:xtdm id="prjCtrlType" dictCode="PRJCTRLTYPE" value="${baseItem.prjCtrlType}"  ></house:xtdm>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>人工发包</label> 
										<input type="text" id="offerCtrl" name="offerCtrl" onkeyup="value=value.replace(/[^\?\d.]/g,'')" style="width:160px;"value="${baseItem.offerCtrl}" />
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>材料发包</label> 
										<input type="text" id="materialCtrl" name="materialCtrl" style="width:160px;"value="${baseItem.materialCtrl}" />
									</li>
									
									<li class="form-validate">
										<label>是否计算管理费</label> 
										<house:xtdm id="isCalMangeFee" dictCode="YESNO" value="${baseItem.isCalMangeFee }"></house:xtdm>
									</li>
									<li class="form-validate">
										<label>客户类型</label> 
										 <house:DataSelect id="custType" className="CustType" keyColumn="code" valueColumn="desc1" value="${baseItem.custType }"></house:DataSelect>
									</li>
									<li>
                                        <label>客户类型组</label>
                                        <house:dict id="custTypeGroupNo" dictCode="" value="${baseItem.custTypeGroupNo}"
                                            sql="select No SQL_VALUE_KEY, Descr SQL_LABEL_KEY from tCustTypeGroup where Expired = 'F'"></house:dict>
                                    </li>
									<li class="form-validate">
										<label><span class="required">*</span>允许价格上浮</label> 
										<house:xtdm id="allowPriceRise" dictCode="YESNO" value="${baseItem.allowPriceRise }"></house:xtdm>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>套餐外项目</label> 
										<house:xtdm id="isOutSet" dictCode="YESNO" value="${baseItem.isOutSet }" ></house:xtdm>
									</li>
									<li class="form-validate" >
										<label><span class="required">*</span>是否基础套餐包</label> 
										<house:xtdm id="isBaseItemSet" dictCode="YESNO" value="${baseItem.isBaseItemSet}"></house:xtdm>
									</li>
									<li><label>关联模板</label> <input type="text" id="baseTempNo" name="baseTempNo" />
									</li>
									<li><label><span class="required">*</span>关联空间类型</label> <house:dict id="fixAreaType" dictCode=""
											sqlValueKey="Code" sqlLableKey="Descr" value="${baseItem.fixAreaType}"
											sql="select code,code+' '+descr descr from tFixAreaType where expired ='F'"></house:dict>
									</li>
                                    <li>
                                        <label>材料类型1</label>
                                        <select id="itemType1" name="itemType1" disabled></select>
                                    </li>
                                    <li>
                                        <label>材料类型2</label>
                                        <select id="itemType2" name="itemType2" disabled></select>
                                    </li>
									<li class="form-validate">
										<label class="control-textarea">备注</label>
										<textarea id="remark" style="width:160px" name="remark" rows="4" >${baseItem.remark }</textarea>
									</li>
								</div>	
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>

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
<title>付款规则添加规则</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
	.form-search .ul-form li label {
	    width: 120px;
	    margin-right: 0px;
	}
	</style>
	<script type="text/javascript">
	$(function() {
	    switchTipType();
	    
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {
				validating : 'glyphicon glyphicon-refresh'
			},
			fields: {  
				payExpr:{  
					validators: {  
						notEmpty: {  
							message: '付款公式不能为空'  
						}
					}  
				},
				payNum:{  
					validators: {  
						notEmpty: {  
							message: '付款期数不能为空'  
						}
					}  
				},
				isRcvDesignFee:{  
					validators: {  
						notEmpty: {  
							message: '是否收设计费不能为空'  
						}
					}  
				},
				chgPer:{  
					validators: {  
						notEmpty: {  
							message: '增减比例不能为空'  
						}
					}  
				},
				tipType:{  
					validators: {  
						notEmpty: {  
							message: '缴款提醒类型不能为空'  
						}
					}  
				},
				workType12:{  
					validators: {  
						notEmpty: {  
							message: '缴款提醒工种不能为空'  
						}
					}  
				},
				tipAddDays:{  
					validators: {  
						notEmpty: {  
							message: '提醒增加天数不能为空'  
						},
						integer: {  
							message: '提醒增加天数需为整数'  
						},
						greaterThan: {  
							message: '提醒增加天数需为正数',
							inclusive: true,
							value: 0
						}
					}  
				},
				isCalcCommi:{  
                    validators: {  
                        notEmpty: {  
                            message: '业绩提成标记不能为空'  
                        }
                    }  
                },
                businessCommiProvidePer:{  
                    validators: {  
                        notEmpty: {  
                            message: '业务提成发放比例不能为空'  
                        },
		                numeric: {
			            	message: '业务提成发放比例只能是数字' 
			            },
                    }  
                },
                businessSubsidyProvidePer:{  
                    validators: {  
                        notEmpty: {  
                            message: '业务补贴发放比例不能为空'  
                        },
		                numeric: {
			            	message: '业务补贴发放比例只能是数字' 
			            },
                    }  
                },
                businessMultipleProvidePer:{  
                    validators: {  
                        notEmpty: {  
                            message: '业务倍数发放比例不能为空'  
                        },
		                numeric: {
			            	message: '业务倍数发放比例只能是数字' 
			            },
                    }  
                },
                designCommiProvidePer:{  
                    validators: {  
                        notEmpty: {  
                            message: '设计提成发放比例不能为空'  
                        },
		                numeric: {
			            	message: '设计提成发放比例只能是数字' 
			            },
                    }  
                },
                designSubsidyProvidePer:{  
                    validators: {  
                        notEmpty: {  
                            message: '设计补贴发放比例不能为空'  
                        },
		                numeric: {
			            	message: '设计补贴发放比例只能是数字' 
			            },
                    }  
                },
                designMultipleProvidePer:{  
                    validators: {  
                        notEmpty: {  
                            message: '设计倍数发放比例不能为空'  
                        },
		                numeric: {
			            	message: '设计倍数发放比例只能是数字' 
			            },
                    }  
                },   
                isCalcSceneDesignCommi:{  
                    validators: {  
                        notEmpty: {  
                            message: '现场设计师提成计算不能为空'  
                        },
                    }  
                }, 
                sceneDesignProvidePer:{  
                    validators: {  
                        notEmpty: {  
                            message: '现场设计师发放比例不能为空'  
                        },
		                numeric: {
			            	message: '现场设计师发放比例只能是数字' 
			            },
                    }  
                },
                isCalcDesignFeeCommi:{  
                    validators: {  
                        notEmpty: {  
                            message: '设计费提成计算不能为空'  
                        },
                    }  
                },
                isCalcDrawFeeCommi:{  
                    validators: {  
                        notEmpty: {  
                            message: '绘图费提成计算不能为空'  
                        },
		                numeric: {
			            	message: '绘图费提成计算只能是数字' 
			            },
                    }  
                },
                isCalcMainCommi:{  
                    validators: {  
                        notEmpty: {  
                            message: '绘图费提成计算不能为空'  
                        },
                    }  
                },
                businessMainCommiProvidePer:{  
                    validators: {  
                        notEmpty: {  
                            message: '业务主材提成发放比例不能为空'  
                        },
		                numeric: {
			            	message: '业务主材提成发放比例比例只能是数字' 
			            },
                    }  
                },
                isCalcIntCommi:{  
                    validators: {  
                        notEmpty: {  
                            message: '集成提成计算不能为空'  
                        }, 
                    }  
                },
                businessIntCommiProvidePer:{  
                    validators: {  
                        notEmpty: {  
                            message: '业务集成提成发放比例不能为空'  
                        },
		                numeric: {
			            	message: '业务集成提成发放比例只能是数字' 
			            },
                    }  
                },
                isCalcSoftCommi:{  
                    validators: {  
                        notEmpty: {  
                            message: '软装提成计算不能为空'  
                        }, 
                    }  
                },
                businessSoftCommiProvidePer:{  
                    validators: {  
                        notEmpty: {  
                            message: '业务软装提成发放比例不能为空'  
                        },
		                numeric: {
			            	message: '业务软装提成发放比例只能是数字' 
			            },
                    }  
                },
                designFeeCommiProvidePer:{  
                    validators: {  
                        notEmpty: {  
                            message: '设计费提成发放比例不能为空'  
                        },
		                numeric: {
			            	message: '设计费提成发放比例只能是数字' 
			            },
                    }  
                },
                drawFeeCommiProvidePer:{  
                    validators: {  
                        notEmpty: {  
                            message: '绘图费提成发放比例不能为空'  
                        },
		                numeric: {
			            	message: '绘图费提成发放比例只能是数字' 
			            },
                    }  
                },
                isCalcBasePersonalCommi:{  
                    validators: {  
                        notEmpty: {  
                            message: '计算基础个性化项目提成'  
                        }, 
                    }  
                },
                basePersonalCommiProvidePer:{  
                    validators: {  
                        notEmpty: {  
                            message: '基础个性化项目发放比例不能为空'  
                        },
		                numeric: {
			            	message: '基础个性化项目发放比例只能是数字' 
			            },
                    }  
                },
                newDesignCommiProvidePer:{  
                    validators: {  
                        notEmpty: {  
                            message: '新设计师发放比例不能为空'  
                        },
		                numeric: {
			            	message: '新设计师发放比例只能是数字' 
			            },
                    }  
                },     
			},
			submitButtons : 'input[type="submit"]'
		});
	});
	
    function switchTipType() {
        var tipType = $("#tipType");
        var workType12 = $("#workType12");
        var workType12Descr = $("#workType12Descr");
        
        if (tipType.val() === "1") {
            workType12.parent().hide();
            workType12.val("");
            workType12Descr.val("");
        } else if (tipType.val() === "2") {
            workType12.parent().show();
        } else {
            workType12.parent().hide();
            workType12.val("");
            workType12Descr.val("");
        }
    }
	
	$(function() {
		(function setValue(){
			$("#payNum").val(eval(${detailXml}).paynum);
			$("#payExpr").val(eval(${detailXml}).payexpr);
			$("#isRcvDesignFee").val(eval(${detailXml}).isrcvdesignfee);
			$("#chgPer").val(eval(${detailXml}).chgper);
			$("#confirmPrjItem").val(eval(${detailXml}).confirmprjitem);
			$("#confirmPrjItemDescr").val(eval(${detailXml}).confirmprjitemdescr);
			$("#payRemark").val(eval(${detailXml}).payremark);
			$("#payTip").val(eval(${detailXml}).paytip);
			$("#tipType").val(eval(${detailXml}).tiptype);
			$("#tipTypeDescr").val(eval(${detailXml}).tiptypedescr);
			$("#workType12").val(eval(${detailXml}).worktype12);
			$("#workType12Descr").val(eval(${detailXml}).worktype12descr);
			$("#tipAddDays").val(eval(${detailXml}).tipadddays);

			$("#isCalcCommi").val(eval(${detailXml}).iscalccommi);
			$("#isCalcCommiDescr").val(eval(${detailXml}).iscalccommidescr);
			$("#businessCommiProvidePer").val(eval(${detailXml}).businesscommiprovideper);
			$("#businessSubsidyProvidePer").val(eval(${detailXml}).businesssubsidyprovideper);
			$("#businessMultipleProvidePer").val(eval(${detailXml}).businessmultipleprovideper);
			$("#designCommiProvidePer").val(eval(${detailXml}).designcommiprovideper);
			$("#designSubsidyProvidePer").val(eval(${detailXml}).designsubsidyprovideper);
			$("#designMultipleProvidePer").val(eval(${detailXml}).designmultipleprovideper);
			$("#isCalcSceneDesignCommi").val(eval(${detailXml}).iscalcscenedesigncommi);
			$("#isCalcSceneDesignCommiDescr").val(eval(${detailXml}).iscalcscenedesigncommidescr);
			$("#sceneDesignProvidePer").val(eval(${detailXml}).scenedesignprovideper);
			$("#isCalcDesignFeeCommi").val(eval(${detailXml}).iscalcdesignfeecommi);
			$("#isCalcDesignFeeCommiDescr").val(eval(${detailXml}).iscalcdesignfeecommidescr);
			$("#isCalcDrawFeeCommi").val(eval(${detailXml}).iscalcdrawfeecommi);
			$("#isCalcDrawFeeCommiDescr").val(eval(${detailXml}).iscalcdrawfeecommidescr);
			$("#isCalcMainCommi").val(eval(${detailXml}).iscalcmaincommi);
			$("#isCalcMainCommiDescr").val(eval(${detailXml}).iscalcmaincommidescr);
			$("#businessMainCommiProvidePer").val(eval(${detailXml}).businessmaincommiprovideper);
			$("#isCalcIntCommi").val(eval(${detailXml}).iscalcintcommi);
			$("#isCalcIntCommiDescr").val(eval(${detailXml}).iscalcintcommidescr);
			$("#businessIntCommiProvidePer").val(eval(${detailXml}).businessintcommiprovideper);
			$("#isCalcSoftCommi").val(eval(${detailXml}).iscalcsoftcommi);
			$("#isCalcSoftCommiDescr").val(eval(${detailXml}).iscalcsoftcommidescr);
			$("#businessSoftCommiProvidePer").val(eval(${detailXml}).businesssoftcommiprovideper);
			$("#designFeeCommiProvidePer").val(eval(${detailXml}).designfeecommiprovideper);
			$("#drawFeeCommiProvidePer").val(eval(${detailXml}).drawfeecommiprovideper);
			$("#isCalcBasePersonalCommi").val(eval(${detailXml}).iscalcbasepersonalcommi);
			$("#basePersonalCommiProvidePer").val(eval(${detailXml}).basepersonalcommiprovideper);
			$("#newDesignCommiProvidePer").val(eval(${detailXml}).newdesigncommiprovideper); 
		})();
	
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator('validate');
			if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
			var arr = $.trim($("#paynums").val()).split(",");
			for(var i=0;i<arr.length;i++){
				if($.trim(arr[i])==$.trim($("#payNum").val()) 
					&& ($.trim(arr[i])!=$.trim(eval(${detailXml}).paynum))){
					art.dialog({
						content:"付款期数已存在",
					});
				return false;
				}
			}
			
			$("#tipTypeDescr").val($("#tipType option:checked").text().split(/ +/)[1]);
			$("#confirmPrjItemDescr").val($("#confirmPrjItem option:checked").text().split(/ +/)[1]);
			$("#workType12Descr").val($("#workType12 option:checked").text().split(/ +/)[1]);
			
			var selectRows = [];
			var datas=$("#dataForm").jsonForm();

			selectRows.push(datas);
			Global.Dialog.returnData = selectRows;
			closeWin();
		});
	});
	
	
	</script>
</head>
  
<body>
	<div class="body-box-form">
		<div class="content-form">
  			<div class="panel panel-system">
    			<div class="panel-body">
      				<div class="btn-group-xs">
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
			<div class="panel panel-info">  
				<div class="panel-body">
					<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
						<input type="hidden" id="paynums" name="paynums" value="${paynums}"/>
						<input type="hidden" id="tipTypeDescr" name="tipTypeDescr" value=""/>
						<input type="hidden" id="confirmPrjItemDescr" name="confirmPrjItemDescr" value=""/>
						<input type="hidden" id="workType12Descr" name="workType12Descr" value=""/>
						<input type="hidden" id="isCalcCommiDescr" name="isCalcCommiDescr" value=""/>
                        <input type="hidden" id="isCalcSceneDesignCommiDescr" name="isCalcSceneDesignCommiDescr" value=""/>
						<input type="hidden" id="isCalcDesignFeeCommiDescr" name="isCalcDesignFeeCommiDescr" value=""/>
						<input type="hidden" id="isCalcDrawFeeCommiDescr" name="isCalcDrawFeeCommiDescr" value=""/>
						<input type="hidden" id="isCalcMainCommiDescr" name="isCalcMainCommiDescr" value=""/>
						<input type="hidden" id="isCalcIntCommiDescr" name="isCalcIntCommiDescr" value=""/>
						<input type="hidden" id="isCalcSoftCommiDescr" name="isCalcSoftCommiDescr" value=""/>
						<ul class="ul-form">
								<li class="form-validate">
									<label><span class="required">*</span>付款期数</label>
									<input type="text" id="payNum" name="payNum" style="width:160px;" />
								</li>
	
								<li class="form-validate">
									<label><span class="required">*</span>付款公式</label>
									<input type="text" id="payExpr" name="payExpr" style="width:465px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>是否收设计费</label>
									<house:xtdm id="isRcvDesignFee" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>增减比例</label>
									<input type="text" id="chgPer" name="chgPer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
							    <li class="form-validate">
                                    <label><span class="required">*</span>缴款提醒类型</label>
                                    <house:xtdm id="tipType" dictCode="PAYRULETIPTYPE" onchange="switchTipType()"></house:xtdm>
                                </li>
                                <li class="form-validate">
                                    <label><span class="required">*</span>提醒增加天数</label>
                                    <input type="text" id="tipAddDays" name="tipAddDays" value=""/>
                                </li>
								<li>
									<label>验收项目</label>
									<house:dict id="confirmPrjItem" dictCode=""
									    sql="select Code, Code + ' ' + Descr Descr from tPrjItem1 where expired = 'F' order by cast(Code as int)"
										sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
								</li>
                                <li class="form-validate">
                                    <label><span class="required">*</span>提醒工种</label>
                                    <house:DataSelect id="workType12" className="WorkType12" keyColumn="code" valueColumn="descr" value=""></house:DataSelect>
                                </li>
								<li>
									<label class="control-textarea">付款说明</label>
									<textarea id="payRemark" name="payRemark" rows="2" style="width:465px;"></textarea>
	 							</li>
	 							<li>
                                    <label class="control-textarea">缴款提示</label>
                                    <textarea id="payTip" name="payTip" rows="2" style="width:465px;"></textarea>
                                </li>
								<li class="form-validate">
									<label><span class="required">*</span>业绩提成标记</label>
									<house:xtdm id="isCalcCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>业务提成发放比例</label>
									<input type="text" id="businessCommiProvidePer" name="businessCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>业务补贴发放比例</label>
									<input type="text" id="businessSubsidyProvidePer" name="businessSubsidyProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>业务倍数发放比例</label>
									<input type="text" id="businessMultipleProvidePer" name="businessMultipleProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								
								<li class="form-validate">
									<label><span class="required">*</span>设计提成发放比例</label>
									<input type="text" id="designCommiProvidePer" name="designCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>新设计师发放比例</label>
									<input type="text" id="newDesignCommiProvidePer" name="newDesignCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="0" />
								</li>	 
								<li class="form-validate">
									<label><span class="required">*</span>设计补贴发放比例</label>
									<input type="text" id="designSubsidyProvidePer" name="designSubsidyProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								
								<li class="form-validate">
									<label><span class="required">*</span>设计倍数发放比例</label>
									<input type="text" id="designMultipleProvidePer" name="designMultipleProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>现场设计师提成计算</label>
									<house:xtdm id="isCalcSceneDesignCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>现场设计师发放比例</label>
									<input type="text" id="sceneDesignProvidePer" name="sceneDesignProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>设计费提成计算</label>
									<house:xtdm id="isCalcDesignFeeCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>设计费提成发放比例</label>
									<input type="text" id="designFeeCommiProvidePer" name="designFeeCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="0" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span> 绘图费提成计算</label>
									<house:xtdm id="isCalcDrawFeeCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>绘图费提成发放比例</label>
									<input type="text" id="drawFeeCommiProvidePer" name="drawFeeCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="0" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>主材提成计算</label>
									<house:xtdm id="isCalcMainCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								
								<li class="form-validate">
									<label><span class="required">*</span>业务主材提成发放比例</label>
									<input type="text" id="businessMainCommiProvidePer" name="businessMainCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>集成提成计算</label>
									<house:xtdm id="isCalcIntCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>业务集成提成发放比例</label>
									<input type="text" id="businessIntCommiProvidePer" name="businessIntCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>软装提成计算</label>
									<house:xtdm id="isCalcSoftCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>业务软装提成发放比例</label>
									<input type="text" id="businessSoftCommiProvidePer" name="businessSoftCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>计算基础个性化项目提成</label>
									<house:xtdm id="isCalcBasePersonalCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>基础个性化项目发放比例</label>
									<input type="text" id="basePersonalCommiProvidePer" name="basePersonalCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="0" />
								</li>
								
						</ul>	
					</form>
				</div>
			</div> <!-- edit-form end -->
		</div>			
	</div>
</body>
</html>

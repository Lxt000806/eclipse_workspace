<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>人工成本导入项目管理--新增</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <style>
        .form-search .ul-form li label {
            width: 120px;
        }
    </style>
</head>
<body>

<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <c:if test="${workCostImportItem.m_umState!='V' }">
	                <button type="button" class="btn btn-system" onclick="save()">保存</button>
	            </c:if>
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="dataForm" class="form-search" target="targetFrame">
               	<input type="hidden" id="expired" name="expired" value="${workCostImportItem.expired}"/>
                <ul class="ul-form">
                	<div class="validate-group row">
                		<li>
                            <label >编码</label>
                            <input type="text" id="no" name="no" value="${workCostImportItem.no }" placeholder="保存时生成" readonly/>
                        </li>
                        <li class="form-validate">
                            <label ><span class="required">*</span>人工成本项目名称</label>
                            <input type="text" id="descr" name="descr" value="${workCostImportItem.descr }" />
                        </li>
					</div>
                    <div class="validate-group row">
						<li class="form-validate">
							<label ><span class="required">*</span>申请类型</label>
							<house:xtdm id="workCostType" dictCode="WorkCostType" value="${workCostImportItem.workCostType}" onchange="changeWorkCostType()"></house:xtdm>
						</li>
						<li class="form-validate">
							<label ><span class="required">*</span>申请节点类型</label>
							<house:xtdm id="type" dictCode="WORKCOSTIMPTYPE" value="${workCostImportItem.type}" onchange="changeType()"></house:xtdm>
						</li>
                    </div>
                    <div class="validate-group row">
                   		<li class="form-validate ">
	                       <label><span class="required hidden">*</span>申请节点</label>
	                       <house:dict id="prjItem" dictCode=""
	                           sql="select Code SQL_VALUE_KEY, Code+' '+Descr SQL_LABEL_KEY from tPrjItem1 where Expired = 'F' order by Seq" value="${workCostImportItem.prjItem}"></house:dict>
	                    </li>
                    	<li class="form-validate ">
							<label ><span class="required hidden">*</span>申请工种分类12</label>
							<house:dict id="workType12" dictCode=""
								sql="select code, code+' '+descr descr from tWorkType12 where Expired='F' order by DispSeq  "
								sqlLableKey="descr" sqlValueKey="code" value="${workCostImportItem.workType12}" />
						</li>
                    </div>
                    <div class="validate-group row">
						<li class="form-validate">
							<label ><span class="required">*</span>计算类型</label>
							<house:xtdm id="calcType" dictCode="WORKCOSTIMPCATP" value="${workCostImportItem.calcType}" ></house:xtdm>
						</li>
                    	<li class="form-validate">
                            <label ><span class="required">*</span>单价</label>
                            <input type="text" id="price" name="price" value="${workCostImportItem.price }" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
                        </li>
                    </div>
                    <div class="validate-group row">
                        <li class="form-validate ">
							<label ><span class="required hidden">*</span>工人工种分类12</label>
							<house:dict id="offerWorkType12" dictCode=""
								sql="select code, code+' '+descr descr from tWorkType12 where Expired='F' order by DispSeq  "
								sqlLableKey="descr" sqlValueKey="code" value="${workCostImportItem.offerWorkType12}" />
						</li>
                        <li class="form-validate ">
							<label ><span class="required">*</span>工资工种分类2</label>
							<house:dict id="workType2" dictCode=""
								sql="select code, code+' '+descr descr from tWorkType2 where Expired='F' order by DispSeq  "
								sqlLableKey="descr" sqlValueKey="code" value="${workCostImportItem.workType2}" />
						</li>
                    </div>
                    <div class="validate-group row">
	                  	<li class="form-validate ">
							<label ><span class="required">*</span>可重复导入</label>
							<house:xtdm id="isRepeatImport" dictCode="YESNO" value="${workCostImportItem.isRepeatImport}" ></house:xtdm>
						</li>
						<c:if test="${workCostImportItem.m_umState!='A'}">
							<li>
	                            <label>过期</label>
	                            <input type="checkbox" name="expiredCheckbox"
	                                   ${workCostImportItem.expired == 'T' ? 'checked' : ''} onclick="checkExpired(this)"/>
	                        </li>
                        </c:if>
					</div>
                    <div class="row">
						<li>
							<label class="control-textarea">备注</label> 
							<textarea id="remark" name="remark" style="height: 50px;">${workCostImportItem.remark }</textarea>
						</li>
					</div>
                </ul>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        $("#dataForm").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                descr: {
                    validators: {
                        notEmpty: {
                        	message: '人工成本项目名称不能为空'
                        },
                    }
                },
                workCostType: {
                    validators: {
                        notEmpty: {
                       		message: '申请类型不能为空'
                        },
                    }
                },
                price: {
                    validators: {
                        notEmpty: {
                    	    message: '单价不能为空'
                        },
                    },
                },
               /*  offerWorkType12: {
                    validators: {
                        notEmpty: {
                    	    message: '工人工种分类12不能为空'
                        },
                    },
                },  */
                /* workType12: {
                    validators: {
                        notEmpty: {
                    	    message: '申请工种分类12不能为空'
                        },
                    },
                }, */
                workType2: {
                    validators: {
                        notEmpty: {
                    	    message: '工资工种分类2不能为空'
                        },
                    },
                },
                type: {
                    validators: {
                        notEmpty: {
                    	    message: '申请节点类型不能为空'
                        },
                    },
                },
                isRepeatImport: {
                    validators: {
                        notEmpty: {
                    	    message: '可重复导入不能为空'
                        },
                    },
                },
                
            }
        });
        changeType();
        changeWorkCostType();
        if("${workCostImportItem.m_umState}" == "V"){
        	disabledForm("dataForm");
        }
    });

    function save() {
        var bootstrapValidator = $("#dataForm").data('bootstrapValidator');
        bootstrapValidator.validate();
        if (!bootstrapValidator.isValid()) return;

        var data = $("#dataForm").jsonForm();
        
        var requestMap="doSave";
        if("${workCostImportItem.m_umState}"=="M"){
        	requestMap="doUpdate";
        }
        $.ajax({
            url: "${ctx}/admin/workCostImportItem/"+requestMap,
            type: "POST",
            data: data,
            dataType: "json",
            error: function(obj) {
                showAjaxHtml({
                    "hidden": false,
                    "msg": "保存数据出错~"
                });
            },
            success: function(obj) {
                if (obj.rs) {
                    art.dialog({
                        content: obj.msg,
                        time: 1000,
                        beforeunload: function() {
                            closeWin(true)
                        }
                    });
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token)
                    art.dialog({content: obj.msg})
                }
            }
        })

    }
    
    function changeType(){
    	var type = $("#type").val();
    	if(type == "0"){
    		$("#workType12").val("");
    		$("#workType12").attr("disabled",true);
    		$("#workType12").prev().children().eq(0).addClass("hidden");
    		$("#workType12").next().next().next("small").remove();
			$("#dataForm").bootstrapValidator("removeField","workType12");
			$("#prjItem").removeAttr("disabled");
			$("#prjItem").prev().children().eq(0).removeClass("hidden");
			$("#dataForm").bootstrapValidator("addField", "prjItem", {  
	            validators: {  
	                notEmpty: {  
	                    message: '申请节点不能为空'  
	                } 
	            }  
	        });
    	}else if(type == "1"){
    		$("#prjItem").val("");
    		$("#prjItem").attr("disabled",true);
    		$("#prjItem").prev().children().eq(0).addClass("hidden");
			$("#dataForm").bootstrapValidator("removeField","prjItem");
			$("#prjItem").next().next().next("small").remove();
			$("#workType12").removeAttr("disabled");
			$("#workType12").prev().children().eq(0).removeClass("hidden");
			$("#dataForm").bootstrapValidator("addField", "workType12", {  
	            validators: {  
	                notEmpty: {  
	                    message: '申请工种分类12不能为空'  
	                } 
	            }  
	        });
    	}
    }
    
    function changeWorkCostType(){
    	var workCostType = $("#workCostType").val();
    	if(workCostType == "1"){
    		$("#offerWorkType12").prev().children().eq(0).removeClass("hidden");
    		$("#dataForm").bootstrapValidator("removeField","offerWorkType12");
			$("#dataForm").bootstrapValidator("addField", "offerWorkType12", {  
	            validators: {  
	                notEmpty: {  
	                    message: '工人工种分类12不能为空'  
	                } 
	            }  
	        });
	        
    	}else{
			$("#dataForm").bootstrapValidator("removeField","offerWorkType12");
    		$("#offerWorkType12").prev().children().eq(0).addClass("hidden");
    		$("#offerWorkType12").next().next().next("small").remove();
    	}
    }
</script>
</body>
</html>

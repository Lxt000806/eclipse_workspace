<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加操作员</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var initSelIds = [];
var zTree;
var zTreeDept;
var zTreeCkAll;
var zTreeCkSelect;
var setting = {
		check: {
			enable: true,
			nocheckInherit: true
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
var zNodes =${nodes};
var zNodesDept =${nodesDept};
var zNodesCkAll =${nodesCkAll};
var zNodesCkSelect =${nodesCkSelect};
$(document).ready(function(){
	document.execCommand("BackgroundImageCache",false,true);//IE6缓存背景图片
	$.fn.zTree.init($("#tree"), setting, zNodes);
	$.fn.zTree.init($("#treeDept"), setting, zNodesDept);
	$.fn.zTree.init($("#treeCkAll"), setting, zNodesCkAll);
	$.fn.zTree.init($("#treeCkSelect"), setting, zNodesCkSelect);
	zTree = $.fn.zTree.getZTreeObj("tree");
	zTreeDept = $.fn.zTree.getZTreeObj("treeDept");
	zTreeCkAll = $.fn.zTree.getZTreeObj("treeCkAll");
	zTreeCkSelect = $.fn.zTree.getZTreeObj("treeCkSelect");
	
    if("${operatorType}"!="ADMIN"){
    	var optionSelect=$("#jslx option");
    	var sValue=""
    	optionSelect.each(function (i,e) {
        	sValue= $(e).text().replace(/[^a-z]+/ig,"");
            if(sValue=="ADMIN"){
                $(this).hide();
            }
        });
    }
});

function save(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	//获取树内容
	var nodes = zTree.getCheckedNodes(true);
	var nodesCkAll = zTreeCkAll.getCheckedNodes(true);
	var addIds = [];
	var addIdsCkAll = [];
	var filterIds = [];//父类的子类全选中的话，只保存父类，不保存子类
	if(nodes != null && nodes.length > 0){
		for(var i=0;i<nodes.length;i++){
			if("department" == nodes[i].nodeType){
				if($("#"+nodes[i].tId+"_check").hasClass('checkbox_true_full')){
					if(filterIds.indexOf(nodes[i].pId)==-1){
						addIds.push(nodes[i].id);
						filterIds.push(nodes[i].id);
					}else{
						continue;
					}
				}
			}
		}
	}
	if(nodesCkAll != null && nodesCkAll.length > 0){
		for(var i=0;i<nodesCkAll.length;i++){
			addIdsCkAll.push(nodesCkAll[i].id);
		}
	}
	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/czybm/doSave?addIds='+addIds.join(',')+'&addIdsCk='+addIdsCkAll.join(','),
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

//校验函数
$(function() {
	  $("#dataForm").bootstrapValidator({
	  	   excluded:[":disabled"],
           message : 'This value is not valid',
           feedbackIcons : {/*input状态样式图片*/
             
               validating : 'glyphicon glyphicon-refresh'
           },
           fields: {  
        czylb: {  
        validators: {  
            notEmpty: {  
            message: '平台类型不能为空'  
            }  
        }  
       },
       openComponent_employee_emnum: {  
        validators: {  
            notEmpty: {  
            message: '员工号不能为空'  
            },
             remote: {
	            message: '',
	            url: '${ctx}/admin/employee/getEmployee',
	            data: getValidateVal,  
	            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }   
        }  
       },
        openComponent_supplier_emnum: {  
        validators: {  
            notEmpty: {  
            message: '员工号不能为空'  
            },
             remote: {
	            message: '',
	            url: '${ctx}/admin/supplier/getSupplier',
	            data: getValidateVal,  
	            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }   
        }  
       },
       custRight: {  
        validators: {  
            notEmpty: {  
            message: '客户权限不能为空'  
            }  
        }  
       },
       czybh: {  
        validators: {  
            notEmpty: {  
            message: '操作员编号不能为空'  
            }  
        }  
       },
      costRight: {  
        validators: {  
            notEmpty: {  
            message: '查看成本不能为空'  
            }  
        }  
       },
       custType: {  
        validators: {  
            notEmpty: {  
            message: '操作客户类型不能为空'  
            }  
        }  
       },
      itemRight_NAME: {  
        validators: {  
            notEmpty: {  
            message: '材料权限不能为空'  
            }  
        }  
       },
      saleType: {  
        validators: {  
            notEmpty: {  
            message: '销售类型权限不能为空'  
            }  
        }  
       },
       mm:{  
			validators: {  
				notEmpty: {  
					message: '必填字段'  
				},
				identical: {
					field: 'checkmm',
					message: '两次密码不一致'
				},
				/* regexp: {
					regexp: /^[a-zA-Z0-9_]+$/,
					message: '密码只能包含大写、小写、数字和下划线'
				}, */
				stringLength: {
					min: 0,
					max: 32,
					message: '密码长度必须在0到32位之间'
				}
			}  
		},
		checkmm:{
			validators: {
				notEmpty: {
					message: '必填字段'
				},
				identical: {
					field: 'mm',
					message: '两次密码不一致'
				}
			}
		},
                      
   	},
           submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
   });
  
  	if ('${czybm.czylb}'=='2'){
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('costRight', false);
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('custRight', false);
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('custType', false);
		//$('#dataForm').data('bootstrapValidator').enableFieldValidators('itemRight_NAME', false);
		$("#costRight_stat").html("");
		$("#custRight_stat").html("");
		$("#custType_stat").html("");
		//$("#itemRight_stat").html("");
		$("#emnum").openComponent_supplier({callBack:function(data){
			$("#czybh").val(data.Code);
			$("#zwxm").val(data.Descr);
			$("#ywxm").val("");
			validateRefresh('openComponent_supplier_emnum');}});
		$("#emnum").setComponent_supplier();
		$("#id_supplyRecvModel").show();	
	}else{
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('costRight', true);
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('custRight', true);
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('custType', true);
		//$('#dataForm').data('bootstrapValidator').enableFieldValidators('itemRight_NAME', true);
		$("#costRight_stat").html("*");
		$("#custRight_stat").html("*");
		$("#custType_stat").html("*");
		//$("#itemRight_stat").html("*");	
		$("#emnum").openComponent_employee({callBack:function(data){
			$("#czybh").val(data.number);
			$("#zwxm").val(data.namechi);
			$("#ywxm").val(data.nameeng);
			validateRefresh('openComponent_employee_emnum');}});
		$("#emnum").setComponent_employee();
		$("#id_supplyRecvModel").hide();
	}
   
});
function checkZfbz(obj){
	if ($(obj).is(':checked')){
		$('#zfbz').val(true);
	}else{
		$('#zfbz').val(false);
	}
}

function changeCustRight(obj){
	var str = obj.value;
	if (obj.value=='2'){
		$('#custRight_show').show();
	}else{
		$('#custRight_show').hide();
	}
}
function changePtdm(obj){
	if (obj.value=='2'){//2 供应商平台
		$("#openComponent_employee_emnum").next().remove();
		$("#openComponent_employee_emnum").remove();
		$("#emnum").openComponent_supplier({callBack:function(data){
			$("#czybh").val(data.Code);
			$("#zwxm").val(data.Descr);
			$("#ywxm").val("");
			validateRefresh('openComponent_supplier_emnum');}});
	$('#dataForm').bootstrapValidator('addField','openComponent_supplier_emnum');
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('openComponent_employee_emnum', false);
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('openComponent_supplier_emnum', true);
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('costRight', false);
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('custRight', false);
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('custType', false);
		//$('#dataForm').data('bootstrapValidator').enableFieldValidators('itemRight_NAME', false); 
		$("#costRight_stat").html("");
		$("#custRight_stat").html("");
		$("#custType_stat").html("");
		//$("#itemRight_stat").html("");
		$("#id_supplyRecvModel").show();
		$("#supplyRecvModel").val("1");
	}else{
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('openComponent_supplier_emnum', false);
		$("#openComponent_supplier_emnum").next().remove();
		$("#openComponent_supplier_emnum").remove();
		$("#emnum").openComponent_employee({callBack:function(data){
			$("#czybh").val(data.number);
			$("#zwxm").val(data.namechi);
			$("#ywxm").val(data.nameeng);
			validateRefresh('openComponent_employee_emnum');}});
		$('#dataForm').bootstrapValidator('addField','openComponent_employee_emnum');
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('openComponent_employee_emnum', true);
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('costRight', true);
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('custRight', true);
		$('#dataForm').data('bootstrapValidator').enableFieldValidators('custType', true);
		//$('#dataForm').data('bootstrapValidator').enableFieldValidators('itemRight_NAME', true); 
		$("#costRight_stat").html("*");
		$("#custRight_stat").html("*");
		$("#custType_stat").html("*");
		//$("#itemRight_stat").html("*");
		$("#id_supplyRecvModel").hide();
		$("#supplyRecvModel").val("1");
	}
}

function allCheck(){
	//var zTree = $.fn.zTree.getZTreeObj("tree");
    var node = zTreeCkAll.getNodes();
    var nodes = zTreeCkAll.transformToArray(node);
    var checkNode = zTreeCkAll.getCheckedNodes().length;
    //已选中的节点数小于总数 - 全选
    if (checkNode < nodes.length) {
        $('#allCheck').prop('checked', true)
        zTreeCkAll.checkAllNodes(true);
    } else{
    	zTreeCkAll.checkAllNodes(false);
    }
}

</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="saveBtn" class="btn btn-system "   onclick="save()">保存</button>
	      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
	      </div>
	   </div>
	</div>
	<!--标签页内容 -->
	<div class="container-fluid" >
	    <ul class="nav nav-tabs" >
	        <li class="active"><a href="#tab1" data-toggle="tab">主项目</a></li>  
	        <li id="custRight_show" style="display: ${czybm.custRight=='2'?'':'none' }"><a href="#tab2" data-toggle="tab">权限部门</a></li> 
	        <li><a href="#tab3" data-toggle="tab">仓库权限</a></li>
	    </ul>
		<div class="tab-content">
		    <div id="tab1" class="tab-pane fade in active">
      			<form action="" method="post" id="dataForm" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="zfbz" id="zfbz" value="${czybm.zfbz==null?'false':czybm.zfbz}"/>
					<input type="hidden" name="bmbh" id="bmbh" value="${czybm.bmbh}"/>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
						<ul class="ul-form">
							<div class="validate-group row" >
								<div class="col-sm-6" >
									<li class="form-validate">
									<label><span class="required">*</span>操作员编号</label>
									<input type="text" id="czybh" name="czybh" data-bv-notempty data-bv-notempty-message="操作员编号不能为空"   maxlength="10" value="${czybm.czybh}" />
									</li>
								</div>
								<div class="col-sm-6" >
									<li class="form-validate">
									<label><span class="required">*</span>平台类型</label>
									<house:dict id="czylb" dictCode="ptdm" value="${czybm.czylb}" onchange="changePtdm(this)"></house:dict>
									</li>
								</div>
							</div>
							
							<div class="validate-group row" >
								<div class="col-sm-6" >
								<li>
								<label><span class="required">*</span>操作员类型</label>
								
								<select id="jslx" name="jslx">
								<option value="OPERATOR" ${czybm.jslx=='OPERATOR'?'selected':'' }>OPERATOR</option>
								<option value="ADMIN" ${czybm.jslx=='ADMIN'?'selected':'' }>ADMIN</option>
								</select>
								</li>
								</div>
								<div class="col-sm-6" >
								<li class="form-validate">
								<label><span class="required">*</span>员工号</label>
							
								<input type="text" id="emnum" name="emnum"   />
								</li>
								</div>
							</div>
							
							<div class="validate-group row" >
								<div class="col-sm-6" >
								<li class="form-validate">
								<label><span class="required">*</span>密码</label>
							
								<input type="password" id="mm" name="mm"  data-bv-notempty data-bv-notempty-message="密码不能为空"   />
								</li>
								</div>
								<div class="col-sm-6" >
								<li class="form-validate">
								<label><span class="required">*</span>确认密码</label>
								
								<input type="password" id="checkmm" name="checkmm"  data-bv-notempty data-bv-notempty-message="确认密码不能为空"   />
								</li>
								</div>
							</div>
							
							<div class="validate-group row" >	
								<div class="col-sm-6" >
								<li class="form-validate">
								<label><span class="required">*</span>中文姓名</label>
							
								<input type="text" id="zwxm" name="zwxm"  data-bv-notempty data-bv-notempty-message="中文姓名不能为空"   />
								</li>
								</div>
								<div class="col-sm-6" >
								<li>
								<label>英文姓名</label>
								
								<input type="text" id="ywxm" name="ywxm"   />
								</li>
								</div>
							</div>
							
							<div class="validate-group row" >
								<div class="col-sm-6" >
								<li class="form-validate">
								<label>菜单风格</label>
							
								<input type="text" id="qmcode" name="qmcode"  value="${czybm.qmcode}" data-bv-stringlength="true" data-bv-stringlength-max="10" data-bv-stringlength-message="菜单风格长度不得超过10 "/>
								</li>
								</div>
								<div class="col-sm-6" >
								<li class="form-validate">
								<label><span id="custRight_stat" class="required">*</span>客户权限</label>
								
								<house:xtdm id="custRight" dictCode="CUSTRIGHT" value="${czybm.custRight }" onchange="changeCustRight(this)"></house:xtdm>
								</li>
								</div>
							</div>
							
							<div class="validate-group row" >
								<div class="col-sm-6" >
								<li class="form-validate">
								<label><span id="costRight_stat" class="required">*</span>查看成本</label>
								
								<house:xtdm id="costRight" dictCode="COSTRIGHT" value="${czybm.costRight }"></house:xtdm>
								</li>
								</div>
								<div class="col-sm-6" >
								<li class="form-validate">
								<label><span id="custType_stat" class="required">*</span>操作客户类型</label>
							
								<house:xtdm id="custType" dictCode="CUSTTYPE" value="${czybm.custType }"></house:xtdm>
								</li>
								</div>
							</div>
							
							<div class="validate-group row" >
								<div class="col-sm-6" >
								<li class="form-validate">
								<label><span id="itemRight_stat" class="required">*</span>材料权限</label>
							
								<house:xtdmMulit id="itemRight" dictCode="ITEMRIGHT" selectedValue="${czybm.itemRight }" onCheck="validateRefresh('itemRight_NAME')"></house:xtdmMulit>
								</li>
								</div>
								<div class="col-sm-6" >
								<li class="form-validate">
								<label><span id="custType_stat" class="required">*</span>销售类型权限</label>
								
								<house:xtdm id="saleType" dictCode="SALETYPE" value="${czybm.saleType }"></house:xtdm>
								</li>
								</div>
							</div>
							
							<div class="validate-group row" >
								<div class="col-sm-6" >
								<li  class="form-validate">
								<label>电话号码</label>
							
								<input type="text" id="dhhm" name="dhhm"   value="${czybm.dhhm}" data-bv-stringlength="true" data-bv-stringlength-max="20" data-bv-stringlength-message="电话号码长度不得超过20"/>
								</li>
								</div>
								<div class="col-sm-6" >
								<li  class="form-validate">
								<label>手机号码</label>
								
								<input type="text" id="sj" name="sj"   value="${czybm.sj}" data-bv-stringlength="true" data-bv-stringlength-max="20" data-bv-stringlength-message="手机号码长度不得超过20"/>
								</li>
								</div>
							</div>
							
							<div class="validate-group row" >	
								<div class="col-sm-6" >
								<li>
								<label>是否外网用户</label>
								
								<house:xtdm id="isOutUser" dictCode="YESNO" value="${czybm.isOutUser }"></house:xtdm>
								</li>
								</div>
								<div class="col-sm-6" >
								<li>
								<label>项目经理成本权限</label>
								
								<house:xtdm id="projectCostRight" dictCode="YESNO" value="${czybm.projectCostRight }"></house:xtdm>
								</li>
								</div>
							</div>
								
							<div class="validate-group row" >
								<div class="col-sm-6" >
								<li>
								<label>工程角色</label>
								<house:dict id="prjRole" dictCode="" sql="select Code,Descr Descr from tPrjRole order by Code " 
										sqlValueKey="Code" sqlLableKey="Descr"  value="${czybm.prjRole }"  ></house:dict>
								</li>
								</div>
								<div class="col-sm-12" >
								<li hidden="true">
								<label>作废标志</label>
								<input type="checkbox" id="zfbz_show" name="zfbz_show" ${czybm.zfbz?'checked':''} onclick="checkZfbz(this)"/>
								</li>
								</div>
							</div>
								
							<div class="validate-group row" id="id_supplyRecvModel" style="display: none;">
								<div class="col-sm-12" >
								<li>
								<label>接单模式</label>
								<house:xtdm id="supplyRecvModel" dictCode="SUPLYRCVMDL" value="${czybm.supplyRecvModel }"></house:xtdm>
								</li>
								</div>
							</div>
						</ul>
					</form>
				</div>
		    	<div id="tab2" class="tab-pane fade " >
		     	   <table>
		        	<tr><td>公司所有部门</td><td>可查看部门</td></tr>
		        	<tr>
		        	<td style="width: 400px">
		        		<div style="width:99.5%;height:420px;flow:left;overflow:auto;">
						<ul id="tree" class="ztree" style="width:350px;"></ul>
						</div>
		        	</td>
		        	<td style="width: 400px">
		        		<div style="width:99.5%;height:420px;flow:left;overflow:auto;">
						<ul id="treeDept" class="ztree" style="width:350px;"></ul>
						</div>
		        	</td>
		        	</tr>
		        	</table> 
		      
			    </div>
		      	<div id="tab3" class="tab-pane fade "  >
	     	    	<table>
			        	<tr><td>所有仓库</td><td>可操作仓库</td></tr>
			        	<tr>
			        	<td style="width: 400px">	 
			        		<div style="width:99.5%;height:420px;flow:left;overflow:scroll;">
								<input style="width:60px;" type="checkbox" id="allCheck" name="allCheck" onclick="allCheck()" /> 
								<span style="margin-left: -20px;">全选</span>  		
								<ul id="treeCkAll" class="ztree" style="width:350px;"></ul>
							</div>
			        	</td>
			        	<td style="width: 400px">
			        		<div style="width:99.5%;height:420px;flow:left;overflow:auto;">
							<ul id="treeCkSelect" class="ztree" style="width:350px;"></ul>
							</div>
			        	</td>
			        	</tr>
			        </table> 
		    	</div>
		</div>
	</div>	
</div>
</body>
</html>

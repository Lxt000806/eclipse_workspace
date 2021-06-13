<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>添加目录</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/tagsinput/css/tagsinput.css?v=${v}" rel="stylesheet" />
	<script src="${resourceRoot}/tagsinput/js/tagsinput.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
	    <style type="text/css">
    	.bootstrap-tagsinput {
		    min-width:  175px;
		    height:24px;
		}
	</style>
</head>
<body >
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" class="btn btn-system" onclick="save()">保存</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
            	<input type="hidden" id="m_umState" name="m_umState" value="${docFolder.m_umState}"  />
            	<input type="hidden" id="pk" name="pk" value="${docFolder.pk}"  />
            	<input type="hidden" id="docFolderViewRole" name="docFolderViewRole" value="${docFolder.docFolderViewRole }"  />
            	<house:token></house:token>
				<ul class="ul-form">	
					<li class="form-validate">
						<label ><span class="required">*</span>上级目录</label>
						<house:docFolderMulitTag id="parentPk"  selectedValue="${docFolder.parentPk }" mulitSel="false" appendAllDom="true" ></house:docFolderMulitTag>
					</li>
					<li class="form-validate" >
						<label><span class="required">*</span>目录名称</label>
						<input type="text" style="width:200px;" id="folderName" name="folderName" value="${docFolder.folderName }"  />
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>目录编码</label>
						<input type="text" style="width:200px;" id="folderCode" name="folderCode" value="${docFolder.folderCode }" />
					</li>
					<li class="form-validate" >
						<label>管理员</label>
						<input type="text" class="form-control" style="width:170px;" id="adminCzy" name="adminCzy"  />
						<button type="button"  onclick="getAdminCzy()"  class="btn btn-system" data-disabled="true" style="width: 26px;height: 24px; margin-left: -9px;">
						<span class="glyphicon glyphicon-search"></span></button>
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>查看权限</label>
						<house:xtdm id="authType" dictCode="DOCFOLDAUTHTYPE" style="width:200px;" value="${docFolder.authType }"  onchange="changeAuthType()"/>
					</li>
					<li id="docFolderViewRole_li">
						<label><span class="required">*</span>查看角色</label>
						<input type="text" style="width:170px;" id="docFolderViewRoleDescr" name="docFolderViewRoleDescr" value="${docFolder.docFolderViewRoleDescr }" readonly="readonly"  />
						<button type="button" class="btn btn-system" id="selectRoleBtn"  onclick="selectRole()" style="font-size: 12px;margin-left: -5px;width: 35px;">选择</button>
					</li>
					<li class="form-validate" >
						<label><span class="required">*</span>开放给项目经理</label>
						<house:xtdm id="isAuthPM" dictCode="YESNO" style="width:200px;" value="${docFolder.isAuthPM}"/>
					</li>
					<li class="form-validate" >
						<label><span class="required">*</span>开放给工人</label>
						<house:xtdm id="isAuthWorker" dictCode="YESNO" style="width:200px;" value="${docFolder.isAuthWorker}"   onchange="changeIsAuthWorker()"/>
					</li>

				   <li id="authWorkerTypes_li" class="form-validate">
						<label><span class="required">*</span>工人工种</label>
						<house:DictMulitSelect  id="authWorkerTypes" dictCode="" sql="select rtrim(a.Code) Code, a.Descr from tWorkType12 a where Expired='F' "
								sqlValueKey="Code" sqlLableKey="Descr" selectedValue="${docFolder.authWorkerTypes }"  width="200px"  ></house:DictMulitSelect>
					</li>
					<li>
						<label>创建时间</label>
						<input type="text" style="width:200px;" id="createDate" name="createDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value='${docFolder.createDate}' pattern='yyyy-MM-dd HH:mm:dd'/>" readonly="readonly"  disabled="disabled"/>
					</li>
					<li>
						<label>创建人</label>
						<input type="text" style="width:200px;" id="createCzy" name=createCzy value="${docFolder.createCzy}" />
					</li>
					<li hidden="true" >
						<label>最后修改人</label>
						<input type="text" style="width:200px;" id="lastUpdatedBy" name=lastUpdatedBy value="${docFolder.lastUpdatedBy }" />
					</li>
					<li hidden="true">
						<label>最后修改时间</label>
						<input type="text" style="width:200px;" id="lastUpdate" name=lastUpdate value="${docFolder.lastUpdate }" />
					</li>
					<li hidden="true">
						<input  id="selectAdminCzy" name="selectAdminCzy" />
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>
</body>

<script type="text/javascript">
//保存数据
function save(){
	if ($.trim($("#isAuthWorker").val())=="1" && $.trim($("#authWorkerTypes").val())==""){
		art.dialog({
			content: "请选择工人工种",
			width: 200
		});
		return false;
	}
	
	if ($.trim($("#isAuthPM").val())==""){
		art.dialog({
			content: "请选择是否开放给项目经理",
			width: 200
		});
		return false;
	}
	
	if ($.trim($("#authType").val())=="2" && $.trim($("#docFolderViewRoleDescr").val())==""){
		art.dialog({
			content: "请选择查看角色",
			width: 200
		});
		return false;
	}
	if ($.trim($("#adminCzy").val())==""){
		art.dialog({
			content: "请选择管理员",
			width: 200
		});
		return false;
	}
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	var datas = $("#dataForm").jsonForm();;
	$.ajax({
		url:'${ctx}/admin/docFolder/doSave',
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
	if($.trim("${docFolder.isAuthPM}")==""){
		$("#isAuthPM").val("0");
	}
	
	//$("#adminCzy").openComponent_czybm({showValue:"${docFolder.adminCzy}",showLabel: "${docFolder.adminCzyDescr}"});
	$("#selectAdminCzy").openComponent_czybm({
		callBack:function(data){  
			selectAdminCzy(data);
     	}
    });
	$("#createCzy").openComponent_czybm({showValue:"${docFolder.createCzy}",showLabel: "${docFolder.createCzyDescr}",disabled:true});

	document.getElementById("parentPk_NAME").style.width="200px";
	document.getElementById("openComponent_czybm_createCzy").style.width="175px";
	changeAuthType();
	changeIsAuthWorker();
	initAdminCzy();
	if("${docFolder.m_umState}"=="M"){
		$("#parentPk_NAME").attr("disabled",true);
	}
	var parentPk = {
		        validators: { 
		            notEmpty: { 
		            	message: '上级目录不能为空'  
		            },
		        }
      		};
      var parentPk_NAME = {
		        validators: { 
		            notEmpty: { 
		            	message: '上级目录不能为空'  
		            },
		        }
      		};
	var fields = {
      		folderCode: {
		        validators: { 
		            notEmpty: { 
		            	message: '目录编码不能为空'  
		            },
		        }
      		},
      		folderName: {
		        validators: { 
		            notEmpty: { 
		            	message: '目录名称不能为空'  
		            },
		        }
      		},
      		authType: {
		        validators: { 
		            notEmpty: { 
		            	message: '查看权限不能为空'  
		            },
		        }
      		},
      		isAuthWorker: {
		        validators: { 
		            notEmpty: { 
		            	message: '开放给工人不能为空'  
		            },
		        }
      		},
      		adminCzy: {
      			validators: { 
		            notEmpty: { 
		            	message: '管理员不能为空'  
		            },
		        }
      		},
      		isAuthPM: {
      			validators: { 
		            notEmpty: { 
		            	message: '开放给项目经理不能为空'  
		            },
		        }
      		},
        };
        if("${docFolder.m_umState}"=="A"){
			fields["parentPk"] = parentPk;
       		fields["parentPk_NAME"] = parentPk_NAME;
		}
	$("#dataForm").bootstrapValidator({
        message : 'This value is not valid',
        feedbackIcons : {/*input状态样式图片*/
            validating : 'glyphicon glyphicon-refresh'
        },
        fields: fields,
        submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
});

function changeAuthType(){
	if($("#authType").val()=="2"){
		$("#docFolderViewRole_li").show();		
	}else{
		$("#docFolderViewRole_li").hide();	
		$("#docFolderViewRole").val("");	
		$("#docFolderViewRoleDescr").val("");
	}	
};

function changeIsAuthWorker(){
	if($("#isAuthWorker").val()=="1"){
		$("#authWorkerTypes_li").show();		
	}else{
		$("#authWorkerTypes_li").hide();	
		$("#authWorkerTypes").val("");	
		$.fn.zTree.getZTreeObj("tree_authWorkerTypes").checkAllNodes(false);
	}
};

function selectRole(){
	Global.Dialog.showDialog("selectRole",{	
		title:"文档目录管理--选择角色",
		url:"${ctx}/admin/docFolder/goDocFolderViewRole",
		postData: {selected:$("#docFolderViewRole").val()}, 
		height: 600,
		width:700,
		returnFun: function(data){
		    if(data.length>0){
		    	var docFolderViewRole = '' , docFolderViewRoleDescr = '';
			    $.each(data,function(k,v){
			    	docFolderViewRole+=v.rolepk+','
			     	docFolderViewRoleDescr+=v.rolename+','
			    });
			    docFolderViewRole=docFolderViewRole.substring(0, docFolderViewRole.length-1);
			    docFolderViewRoleDescr=docFolderViewRoleDescr.substring(0, docFolderViewRoleDescr.length-1);
			    $("#docFolderViewRole").val(docFolderViewRole);
				$("#docFolderViewRoleDescr").val(docFolderViewRoleDescr);
	  		}
		}
	});
};

function getAdminCzy(){
	 $("#openComponent_czybm_selectAdminCzy").next().click(); 
};

function selectAdminCzy(data){
	$('#adminCzy').tagsinput('add', { czybh: data.czybh, zwxm: data.zwxm });
};

function initAdminCzy(){
	$('#adminCzy').tagsinput({
        itemValue: 'czybh',  
        itemText: 'zwxm' 
	});
	if($.trim("${docFolder.adminCzy}") !=''){
		$.ajax({
			url : '${ctx}/admin/czybm/getCzyByIds',
			type : 'post',
			data : {
				'czybhs' :"${docFolder.adminCzy}",
			},
			async:false,
			dataType : 'json',
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : '加载管理员出错~'
				});
				
			},
			success : function(obj) {
				if(obj.length>0){
	                $.each(obj, function(k, v){    
	                	$('#adminCzy').tagsinput('add', { czybh: v.czybh, zwxm: v.zwxm });
	                 });
	            }
	           
			}
		});	
	}
}

</script>
</html>

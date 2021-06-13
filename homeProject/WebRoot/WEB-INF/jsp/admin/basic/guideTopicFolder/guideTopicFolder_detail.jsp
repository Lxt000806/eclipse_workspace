<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>问题类目</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/tagsinput/css/tagsinput.css?v=${v}" rel="stylesheet" />
	<script src="${resourceRoot}/tagsinput/js/tagsinput.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
    <script type="text/javascript">	
    
    $(function() {
		//$("#adminCzy").openComponent_czybm({showValue:"${guideTopicFolder.adminCzy}",showLabel: "${guideTopicFolder.adminCzyDescr}",disabled:true});
		$("#createCzy").openComponent_czybm({showValue:"${guideTopicFolder.createCzy}",showLabel: "${guideTopicFolder.createCzyDescr}",disabled:true});
		changeAuthType();
		changeIsAuthWorker();
		initAdminCzy();
		$("#parentPk_NAME").attr("disabled",true);
		$("#authWorkerTypes_NAME").attr("disabled",true);
    });
	
    //新增
    function add(){
   		var selectedNodes = parent.zTree.getSelectedNodes();
		Global.Dialog.showDialog("guideTopicFolderAdd",{
			title:"添加问题类目",
			url:"${ctx}/admin/guideTopicFolder/goSave?parentId="+selectedNodes[0].pk,
			height: 380,
			width:800,
			returnFun: goto_guideTopicFolder
		});
   	}
   
	//修改
	function edit(){
		if($("#pk").val() == ""){
			var dialog = art.dialog({
				content: '请在左边树选择要更改的目录',
				lock: true,
				ok:function(){
			    	dialog.close();
			    }
			});
			return ;
		}
		Global.Dialog.showDialog("guideTopicFolderUpdate",{
			  title:"修改问题类目",
			  url:"${ctx}/admin/guideTopicFolder/goUpdate?pk="+$.trim($("#pk").val()),
			  height: 380,
			  width:800,
			  returnFun: goto_guideTopicFolder
			});
	}
	
	//删除
	function del(){
		var docFolderId = $("#pk").val();
		if(docFolderId == ""){
			var dialog = art.dialog({
				content: '请在左边树选择要删除的目录',
				lock: true,
				ok:function(){
			    	dialog.close();
			    }
			});
			return ;
		}
		var folderName=$("#folderName").val();
		
		art.dialog({
			 content:'您确定要删除【 '+folderName+'】目录吗？',
			 lock: true,
			 width: 260,
			 height: 100,
			 ok: function () {
		        $.ajax({
					url : "${ctx}/admin/guideTopicFolder/doDelete",
					data : "docFolderId="+docFolderId,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: 'json',
					cache: false,
					error: function(){
				        art.dialog({
							content: '删除记录出现异常'
						});
				    },
				    success: function(obj){
				    	if(obj.rs){
				    		var dialog = art.dialog({
								content: obj.msg,
								time: 1000,
								beforeunload: function () {
							        window.location.reload();
									window.parent.treeFrame.location.reload();
							    },
							    ok:function(){
							    	dialog.close();
							    }
							});
				    	}else{
				    		var dialog = art.dialog({
								content: obj.msg,
								ok:function(){
							    	dialog.close();
							    }
							});
				    	}
					}
				});
		    	return true;
			},
			cancel: function () {
				return true;
			}
		});
	}
	
	function goto_guideTopicFolder(){
       	window.location.reload();
		window.parent.treeFrame.location.reload();
	}
	
	function changeAuthType(){
		if($("#authType").val()=="1"){
			$("#topictopicFolderViewRole_li").hide();		
		}else{
			$("#topictopicFolderViewRole_li").show();	
		}
		
	};
	
	function changeIsAuthWorker(){
		if($("#isAuthWorker").val()=="0"){
			$("#authWorkerTypes_li").hide();	
		}else{
			$("#authWorkerTypes_li").show();
			
		}
	};
	
	function selectRole(){
		Global.Dialog.showDialog("itemPlan_custSave",{	
			title:"问题类目管理--选择角色",
			url:"${ctx}/admin/guideTopicFolder/goTopicFolderViewRole",
			postData: {selected:$("#topicFolderViewRole").val(),m_umState:"V"}, 
			height: 600,
			width:700,
		});
	};
	
	function initAdminCzy(){
		$('#adminCzy').tagsinput({
	        itemValue: 'czybh',  
	        itemText: 'zwxm' 
		});
		if($.trim("${guideTopicFolder.adminCzy}") !=''){
			$.ajax({
				url : '${ctx}/admin/czybm/getCzyByIds',
				type : 'post',
				data : {
					'czybhs' :"${guideTopicFolder.adminCzy}",
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
</head>
<body>
<div class="body-box-form" style="margin-top: 5px;">
	<div class="btn-panel" >
      <div class="btn-group-sm">
      		<house:authorize authCode="GUIDETOPICFOLDER_SAVE">
				<button type="button" class="btn btn-system" id="saveBut" onclick="add()">新增</button>
			</house:authorize>	
			<house:authorize authCode="GUIDETOPICFOLDER_UPDATE">
				<button type="button" class="btn btn-system" id="updateBut" onclick="edit()">修改</button>
			</house:authorize>	
			<house:authorize authCode="GUIDETOPICFOLDER_DELETE">
				<button type="button" class="btn btn-system" id="delBut" onclick="del()">删除</button>
			</house:authorize>
      </div>
    </div>
    <div class="query-form">  
         <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
			<input type="hidden"  name="pk" id="pk" value="${guideTopicFolder.pk }"/>
			            	<input type="hidden" id="topicFolderViewRole" name="topicFolderViewRole" value="${guideTopicFolder.topicFolderViewRole }"  />
			<ul class="ul-form">		
					<li class="form-validate">
						<label ><span class="required">*</span>上级目录</label>
						<house:guideTopicFolderrMulitTag id="parentPk"  selectedValue="${guideTopicFolder.parentPk }" mulitSel="false" appendAllDom="true" ></house:guideTopicFolderrMulitTag>
					</li>
					<li class="form-validate" >
						<label>目录名称</label>
						<input type="text" style="width:160px;" id="folderName" name="folderName" value="${guideTopicFolder.folderName }" readonly="readonly" />
					</li>
					<li class="form-validate">
						<label>目录编码</label>
						<input type="text" style="width:160px;" id="folderCode" name="folderCode" value="${guideTopicFolder.folderCode }" readonly="readonly" />
					</li>
					<li class="form-validate">
						<label>管理员</label>
						<input type="text" style="width:160px;" id="adminCzy" name="adminCzy" value="${guideTopicFolder.adminCzy }" readonly="readonly" />
					</li>
					<li class="form-validate">
						<label>查看权限</label>
						<house:xtdm id="authType" dictCode="DOCFOLDAUTHTYPE" style="width:160px;" value="${guideTopicFolder.authType }"  onchange="changeAuthType()" disabled="true"/>
					</li>
					
					<li id="topicFolderViewRole_li">
						<label>查看角色</label>
						<input type="text" style="width:160px;" id="topicFolderViewRoleDescr" name="topicFolderViewRoleDescr" value="${guideTopicFolder.topicFolderViewRoleDescr }" readonly="readonly"  />
						<button type="button" class="btn btn-system" id="selectRoleBtn"  onclick="selectRole()" style="font-size: 12px;margin-left: -5px;width: 35px;">查看</button>
					</li>
					<li class="form-validate" >
						<label>路径</label>
						<input type="text" style="width:160px;" id="path" name="path" value="${guideTopicFolder.path }" readonly="readonly" />
					</li>
					<li class="form-validate" >
						<label>开放给项目经理</label>
						<house:xtdm id="isAuthPM" dictCode="YESNO" style="width:160px;" value="${guideTopicFolder.isAuthPM}" disabled="true"/>
					</li>
					<li class="form-validate" >
						<label>开放给工人</label>
						<house:xtdm id="isAuthWorker" dictCode="YESNO" style="width:160px;" value="${guideTopicFolder.isAuthWorker}"   onchange="changeIsAuthWorker()" disabled="true"/>
					</li>
				   <li id="authWorkerTypes_li" class="form-validate">
						<label>工人工种</label>
						<house:DictMulitSelect  id="authWorkerTypes" dictCode="" sql="select rtrim(a.Code) Code, a.Descr from tWorkType12 a where Expired='F' "
								sqlValueKey="Code" sqlLableKey="Descr" selectedValue="${guideTopicFolder.authWorkerTypes }"  width="160px"   ></house:DictMulitSelect>
					</li>
					<li>
						<label>创建时间</label>
						<input type="text" style="width:160px;" id="createDate" name="createDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value='${guideTopicFolder.createDate}' pattern='yyyy-MM-dd HH:mm:dd'/>" readonly="readonly"  disabled="disabled"/>
					</li>
					<li>
						<label>创建人</label>
						<input type="text" style="width:160px;" id="createCzy" name=createCzy value="${guideTopicFolder.createCzy}" readonly="readonly"/>
					</li>
					<li>
						<label>最后修改人</label> 
						<input type="text" style="width:160px;" id="lastUpdatedBy" name=lastUpdatedBy value="${guideTopicFolder.lastUpdatedBy }"  readonly="readonly"/>
					</li>
					<li>
						<label>最后修改时间</label>
						<input type="text" style="width:160px;" id="lastUpdate" name=lastUpdate value="${guideTopicFolder.lastUpdate }" readonly="readonly" />
					</li>
				</ul>
		</form>
	</div>
</div>
</body>
</html>


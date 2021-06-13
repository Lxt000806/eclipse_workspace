<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看操作员</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

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
	
});

function checkZfbz(obj){
	if ($(obj).is(':checked')){
		$('#zfbz').val(true);
	}else{
		$('#zfbz').val(false);
	}
}
if ('${czybm.czylb}'=='2'){
	$("#emnum").openComponent_employee({showLabel:'${czybm.emnumDescr}',showValue:'${czybm.emnum}'});
	$("#id_supplyRecvModel").show();
}else{
	$("#emnum").openComponent_employee({showLabel:'${czybm.emnumDescr}',showValue:'${czybm.emnum}'});
	$("#id_supplyRecvModel").hide();
}
function changeCustRight(obj){
	var str = obj.value;
	if (obj.value=='2'){
		$('#custRight_show').show();
	}else{
		$('#custRight_show').hide();
	}
}
//导出EXCEL
function doExcel(){	
	doExcelNow("权限明细","dataTable_authDetail","dataForm");
}

</script>

</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      	 <button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
	      	<button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
	       
	      </div>
	   	</div>
	</div>
	<div class="container-fluid" >
	    <ul class="nav nav-tabs" >
	        <li class="active"><a href="#tab1" data-toggle="tab">主项目</a></li>  
	        <li id="custRight_show" style="display: ${czybm.custRight=='2'?'':'none' }"><a href="#tab2" data-toggle="tab">权限部门</a></li> 
	        <li><a href="#tab3" data-toggle="tab">仓库权限</a></li>
	        <li><a href="#tab_authDetail" data-toggle="tab">权限明细</a></li>
	    </ul>
		<div class="tab-content">
    		<div id="tab1" class="tab-pane fade in active">
       			<form action="" method="post" id="dataForm" class="form-search">
       					<input type="hidden" name="jsonString" value="" />
						<input type="hidden" name="zfbz" id="zfbz" value="${czybm.zfbz}"/>
						<input type="hidden" name="bmbh" id="bmbh" value="${czybm.bmbh}"/>
							<ul class="ul-form">
								<div class="validate-group row" >
									<div class="col-sm-6" >
										<li class="form-validate">
										<label><span class="required">*</span>操作员编号</label>
											<input type="text" id="czybh" name="czybh"data-bv-notempty data-bv-notempty-message="操作员编号不能为空" maxlength="10" value="${czybm.czybh}" readonly="readonly"/>
										</li>
									</div>
									<div class="col-sm-6" >
										<li class="form-validate">
										<label><span class="required">*</span>平台类型</label>
										<house:dict id="czylb" dictCode="ptdm" value="${czybm.czylb}" disabled="true"></house:dict>
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
									
										<input type="text" id="emnum" name="emnum"  value="${czybm.emnum}" />
										</li>
									</div>
								</div>
								<div class="validate-group row" >
									<div class="col-sm-6" >
										<li class="form-validate">
										<label><span class="required">*</span>密码</label>
									
										<input type="password" id="mm" name="mm"  data-bv-notempty data-bv-notempty-message="密码不能为空"  value="${czybm.mm}" />
										</li>
									</div>
									<div class="col-sm-6" >
										<li class="form-validate">
										<label><span class="required">*</span>确认密码</label>
										
										<input type="password" id="checkmm" name="checkmm"  data-bv-notempty data-bv-notempty-message="确认密码不能为空"  value="${czybm.mm}" />
										</li>
									</div>
								</div>
								<div class="validate-group row" >	
									<div class="col-sm-6" >
										<li class="form-validate">
										<label><span class="required">*</span>中文姓名</label>
									
										<input type="text" id="zwxm" name="zwxm"  data-bv-notempty data-bv-notempty-message="中文姓名不能为空"  value="${czybm.zwxm}" />
										</li>
									</div>
									<div class="col-sm-6" >
										<li>
										<label>英文姓名</label>
										
										<input type="text" id="ywxm" name="ywxm"   value="${czybm.ywxm}" />
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
									
										<house:xtdmMulit id="itemRight" dictCode="ITEMRIGHT" selectedValue="${czybm.itemRight }" ></house:xtdmMulit>
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
										<label>分配角色</label>
											<house:roleMulit id="userRole" selectedValue="${czybm.userRole }"></house:roleMulit>
										</li>
									</div>
									<div class="col-sm-6" >
										<li>
										<label>工程角色</label>
										<house:dict id="prjRole" dictCode="" sql="select Code,Descr Descr from tPrjRole order by Code " 
												sqlValueKey="Code" sqlLableKey="Descr"  value="${czybm.prjRole }"  ></house:dict>
										</li>
									</div>
								</div>
								<div class="validate-group row" >
									<div class="col-sm-6" >
									<li>
									<label>作废标志</label>
								
									<input type="checkbox" id="zfbz_show" name="zfbz_show" ${czybm.zfbz?'checked':''} onclick="checkZfbz(this)"/>
									</li>
									</div>
								</div>
								<div class="validate-group row" id="id_supplyRecvModel">
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
    		<div id="tab2" class="tab-pane fade "  >
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
		        	<tr><td>公司所有仓库</td><td>可操作仓库</td></tr>
		        	<tr>
		        	<td style="width: 400px">
		        		<div style="width:99.5%;height:420px;flow:left;overflow:scroll;">
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
	    	<!--权限明细-->
	    	<div id="tab_authDetail"  class="tab-pane fade"> 
				<jsp:include page="tab_authDetail.jsp"></jsp:include> 
			</div>
		</div>
	</div>	
</div>
</body>
</html>

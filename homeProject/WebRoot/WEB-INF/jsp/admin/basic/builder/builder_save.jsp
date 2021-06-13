<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改项目名称</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_latitude.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
function save(){
	$("#dataForm").bootstrapValidator('validate');
	if (!$("#dataForm").data('bootstrapValidator').isValid())
		return;
	//验证
	var datas = $("#dataForm").serialize();
	var tude=$("#tude").val();
	if(tude==""){
		art.dialog({
			content: "请录入经纬度！"
		});
		return;
	}
	$.ajax({
		url:'${ctx}/admin/builder/doSave',
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
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
		//初始化一二级区域联动
		Global.LinkSelect.initSelect("${ctx}/admin/builder/regionCodeByAuthority","regionCode","regionCode2");
		$("#groupCode").openComponent_builderGroup({showLabel:"${builder.groupCodeDescr}",showValue:"${builder.groupCode}"});
		initTude();
		$("#openComponent_latitude_tude").width("190px");
		$("#openComponent_latitude_tude").attr("readonly",true);
		$("#openComponent_builderGroup_groupCode").attr("readonly",true);
});
//初始化经纬度选择输入框
function initTude(){
	var t=$("#tude").val().replace("|","%7C");
	$("#tude").openComponent_latitude({
		showValue:"${builder.tude}",
		condition:{descr:$("#descr").val(),tude:t,
		}
	});
}
</script>
<style type="text/css">

</style>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
	      <button type="button" id="saveBtn" class="btn btn-system" onclick="save()">保存</button>
	      <button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
	      </div>
	   	</div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
        	<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<house:token></house:token>
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate"><label><font color='blue'>项目编号</font></label> <input type="text"
								style="width:160px;" id="code" name="code"
								value="保存时自动生成" readonly="readonly"/>
							</li>
							<li><label>主力户型</label> <input type="text"
								style="width:160px;" id="majorArea" name="majorArea"
								 /><span
								style="position: absolute;left:290px;width: 30px;top:3px;">㎡</span>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label><font color='blue'>项目名称</font></label> <input
								type="text" style="width:160px;" id="descr" name="descr" onchange="initTude()"
								required data-bv-notempty-message="项目名称不能为空"
								 />
							</li>
							<li><label>参考价</label> <input type="text"
								style="width:160px;" id="referPrice" name="referPrice"
								 /><span
								style="position: absolute;left:290px;width: 30px;top:3px;">/㎡</span>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>项目别名</label> <input
								type="text" id="alias" name="alias" style="width:160px;"
								 />
							</li>
							<li><label>物业</label> <input type="text"
								style="width:160px;" id="property" name="property"
								 />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>项目大类</label> <input type="text" id="groupCode"
								name="groupCode" style="width:160px;"
								/>
							</li>
							<li><label>开发商</label> <input type="text" id="developer"
								name="developer" style="width:160px;"
								/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>地址格式类型</label> <house:xtdm
									id="addressType" dictCode="ADDRESSTYPE"
									value="1"></house:xtdm>
							</li>
							<li class="form-validate"><label><font color='blue'>区域</font></label>
								<select id="regionCode"
										name="regionCode"
										required data-bv-notempty-message="区域不能为空"></select>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>装修状况</label> <house:xtdm
									id="dectStatus" dictCode="BLDDECTSTS"
									></house:xtdm>
							</li>
							<li class="form-validate"><label>二级区域</label>
								<select id="regionCode2"
										name="regionCode2"
										></select>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>总户数</label> <input type="text" id="houseNum"
								name="houseNum" style="width:160px;"  />
							</li>
							<li><label>项目类型</label> <house:xtdm id="builderType"
									dictCode="BUILDERTYPE" ></house:xtdm>
							</li>
							<li>
						</div>
						<div class="validate-group row">
							<li><label class="control-textarea">地址</label> <textarea
									id="adress" name="adress"></textarea>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>交房时间</label> <input
								type="text" id="delivDate" name="delivDate" class="i-date"
								style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${builder.dateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>楼盘性质</label> <house:xtdm id="houseType"
									dictCode="HOUSETYPE" ></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li><label class="control-textarea">备注</label> <textarea
									id="remarks" name="remarks"></textarea>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>经纬度</label> <input type="text" id="tude"
								name="tude" style="width:160px;" />
							</li>
						</div>
					</ul>
				</form>
		</div>
	</div>
</div>
</body>
</html>

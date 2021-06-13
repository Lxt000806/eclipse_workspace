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
//校验函数
$(function() {
	$("input").attr("disabled",true);
	$("select").attr("disabled",true);
	$("textarea").attr("disabled",true);
	//初始化一二级区域联动
	Global.LinkSelect.initSelect("${ctx}/admin/builder/regionCodeByAuthority","regionCode","regionCode2");
			 Global.LinkSelect.setSelect({firstSelect:'regionCode',
								firstValue:'${builder.RegionCode}',
								secondSelect:'regionCode2',
								secondValue:'${builder.RegionCode2}'
								});	 
	$("#groupCode").openComponent_builderGroup({showLabel:"${builder.GroupCodeDescr}",showValue:"${builder.GroupCode}",readonly:true});
	$("#tude").openComponent_latitude({showValue:"${builder.tude}",readonly:true});
	$("#openComponent_latitude_tude").width("160px");
	$("#openComponent_latitude_tude").attr("readonly",true);
});
</script>
<style type="text/css">

</style>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
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
								value="${builder.code }" readonly="readonly"/>
							</li>
							<li><label>主力户型</label> <input type="text"
								style="width:160px;" id="majorArea" name="majorArea"
								value="${builder.MajorArea }" /><span
								style="position: absolute;left:290px;width: 30px;top:3px;">㎡</span>
							</li>
						</div>
						<div class="validate-group row">
						<li class="form-validate"><label><font color='blue'>项目名称</font></label> <input
								type="text" style="width:160px;" id="descr" name="descr" value="${builder.Descr }"
								required data-bv-notempty-message="项目名称不能为空"
								 />
							</li>
							<li><label>参考价</label> <input type="text"
								style="width:160px;" id="referPrice" name="referPrice"
								value="${builder.ReferPrice }" /><span
								style="position: absolute;left:290px;width: 30px;top:3px;">/㎡</span>
							</li>
						</div>
						<div class="validate-group row">
							<li><label><font color='blue'>项目别名</font></label> <input
								type="text" id="alias" name="alias" style="width:160px;"
								value="${builder.Alias}" />
							</li>
							<li><label>物业</label> <input type="text"
								style="width:160px;" id="property" name="property"
								value="${builder.Property }" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>项目大类</label> <input type="text" id="groupCode"
								name="groupCode" style="width:160px;"
								value="${builder.GroupCode}" />
							</li>
							<li><label>开发商</label> <input type="text" id="developer"
								name="developer" style="width:160px;"
								value="${builder.Developer}" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>地址格式类型</label> <house:xtdm
									id="addressType" dictCode="ADDRESSTYPE"
									value="${builder.AddressType }"></house:xtdm>
							</li>
							<li class="form-validate"><label>区域</label>
								<select id="regionCode"
										name="regionCode"
										></select>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>装修状况</label> <house:xtdm
									id="dectStatus" dictCode="BLDDECTSTS"
									value="${builder.DectStatus }"></house:xtdm>
							</li>
							<li class="form-validate"><label>二级区域</label>
								<select id="regionCode2"
										name="regionCode2"
										></select>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>总户数</label> <input type="text" id="houseNum"
								name="houseNum" style="width:160px;" value="${builder.HouseNum}" />
							</li>
							<li><label>项目类型</label> <house:xtdm id="builderType"
									dictCode="BUILDERTYPE" value="${builder.BuilderType }"></house:xtdm>
							</li>
							<li>
						</div>
						<div class="validate-group row">
							<li><label class="control-textarea">地址</label> <textarea
									id="adress" name="adress">${builder.Adress }</textarea>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>交房时间</label> <input
								type="text" id="delivDate" name="delivDate" class="i-date"
								style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${builder.DelivDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>楼盘性质</label> <house:xtdm id="houseType"
									dictCode="HOUSETYPE" value="${builder.HouseType }"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li><label class="control-textarea">备注</label> <textarea
									id="remarks" name="remarks">${builder.Remarks }</textarea>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>经纬度</label> <input type="text" id="tude"
								name="tude" style="width:160px;" value="${builder.tude}" />
							</li>
						</div>
					</ul>
				</form>
		</div>
	</div>
</div>
</body>
</html>

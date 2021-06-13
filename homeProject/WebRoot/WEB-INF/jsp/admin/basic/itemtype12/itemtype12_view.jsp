<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_laborFeeType.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**材料类型1*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${itemtype12.itemType1}',
		disabled:"true"
	};
	Global.LinkSelect.setSelect(dataSet);
	$("#installFeeType").openComponent_laborFeeType({
		showValue:"${itemType12.installFeeType}",
		showLabel:"${itemType12.installFeeTypeDescr}",
		readonly:true
	});
	$("#openComponent_laborFeeType_installFeeType").attr("readonly",true);
	$("#transFeeType").openComponent_laborFeeType({
		showValue:"${itemType12.transFeeType}",
		showLabel:"${itemType12.transFeeTypeDescr}",
		readonly:true
	});
	$("#openComponent_laborFeeType_transFeeType").attr("readonly",true);		
});
</script>
</head>
<body>
	<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<li>
						<label>编号</label>
						<input type="text" id="no" name="no"  value="${itemtype12.code}" readonly="true" />
					</li>
					<li>
						<label><span class="required">*</span>材料类型1</label>
							<select id="itemType1" name="itemType1" ></select>
					</li>
					<li>
						<label>名称</label>
						<input type="text" id="descr" name="descr"  value="${itemtype12.descr }" />
					</li>
					<li>
						<label>显示顺序</label>
						<input type="text" id="dispseq" name="dispseq" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" style="width:160px;" value="${itemtype12.dispseq }" />
					</li>
					<li>
						<label>毛利率控制线</label>
						<input type="text" id="proper" name="proper"  onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  value="${itemtype12.proper }" />
					</li>
					<li>
						<label>安装费用类型</label>
						<input type="text" id="installFeeType" name="installFeeType"  value="${itemtype12.installFeeType }" />
					</li>
					<li>
						<label>搬运费用类型</label>
						<input type="text" id="transFeeType" name="transFeeType"  value="${itemtype12.transFeeType }" />
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>
</body>
</html>



<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>基础结算项目管理--更新</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){  
	Global.LinkSelect.initSelect("${ctx}/admin/baseItemType1/baseItemType","baseItemType1","baseItemType2");
	Global.LinkSelect.setSelect({firstSelect:"baseItemType1",
								firstValue:"${baseCheckItem.baseItemType1}",
								secondSelect:"baseItemType2",
								secondValue:"${baseCheckItem.baseItemType2}"});
   	$("#offerPri").val(myRound("${baseCheckItem.offerPri}", 2));
	$("#material").val(myRound("${baseCheckItem.material}", 2));
	$("#prjOfferPri").val(myRound("${baseCheckItem.prjOfferPri}", 2));
	$("#prjMaterial").val(myRound("${baseCheckItem.prjMaterial}", 2));								
});
</script>
</head>
<body>
 	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
	     			 <button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button> 
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">					  
					    <li>
							<label>编号</label>
							<input type="text" placeholder="自动生成" value="${baseCheckItem.code}" readonly="readonly"/>
						</li>
						<li class="form-validate">	
							<label>项目名称</label>
							<input type="text" id="descr" name="descr" value="${baseCheckItem.descr}" readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label>基装类型1</label>
							<select id="baseItemType1" name="baseItemType1" disabled="true"></select>
						</li>
						<li class="form-validate">
							<label>基装类型2</label>
							<select id="baseItemType2" name="baseItemType2" disabled="true"></select>
						</li>
						<li>
							<label>工种分类12</label>
							<house:dict id="workType12" dictCode="" sql="select code,code+' '+ descr descr from tWorkType12"
							sqlLableKey="descr" sqlValueKey="code" value="${baseCheckItem.workType12}" disabled="true"></house:dict>
						</li>
						<li class="form-validate">
							<label>人工成本单价</label>
							<input type="text" id="offerPri" name="offerPri" value="${baseCheckItem.offerPri}" readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label>材料成本单价</label>
							<input type="text" id="material" name="material" value="${baseCheckItem.material}" readonly="readonly"/>
						</li>
						<li  class="form-validate">
							<label>项目经理人工单价</label>
							<input type="text" id="prjOfferPri" name="prjOfferPri" value="${baseCheckItem.prjOfferPri}" readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label>项目经理材料单价</label>
							<input type="text" id="prjMaterial" name="prjMaterial" value="${baseCheckItem.prjMaterial}" readonly="readonly"/>
						</li>
						<li  class="form-validate">
							<label>单位</label>
							<house:dict id="uom" dictCode="" sql="select code,code+' '+descr descr from tuom where expired='F' order by rtrim(Code) " 
							sqlLableKey="descr" sqlValueKey="code"  value="${baseCheckItem.uom}" disabled="true"></house:dict>
						</li>		
						<li  class="form-validate">
							<label>类型</label>
							<house:xtdm id="type" dictCode="BASECKITEMTYPE" value="${baseCheckItem.type}"></house:xtdm>
						</li>
						<li  class="form-validate">
							<label>是否定责项目</label>
							<house:xtdm id="isFixItem" dictCode="YESNO" value="${baseCheckItem.isFixItem}"></house:xtdm>
						</li>
						<li  class="form-validate">
							<label>显示顺序</label>
							<input type="text" id="dispSeq" name="dispSeq" style="width:160px;"
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
								value="${baseCheckItem.dispSeq}"/>
						</li>
						<li>
							<label>工人分类</label>
							<house:xtdmMulit id="workerClassify" dictCode="WORKERCLASSIFY" selectedValue="${baseCheckItem.workerClassify}"></house:xtdmMulit>                     
						</li>
						<li  class="form-validate">
							<label>是否发包补贴项目</label>
							<house:xtdm id="isSubsidyItem" dictCode="YESNO" value="${baseCheckItem.isSubsidyItem}"></house:xtdm>
						</li>
						<li  class="form-validate">
							<label class="control-textarea">描述</label>
							<textarea id="remark" name="remark" rows="3">${baseCheckItem.remark}</textarea>
						</li>
					</ul>		
				</form>
			</div>
	  	</div>
	  </div>
	</body>
</html>



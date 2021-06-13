<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<title>工人签到提醒查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
 <script type="text/javascript">
  	//校验函数
$(function() {	
   //初始化材料类型1，2，3三级联动
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'${workSignAlarm.itemType1}',
								secondSelect:'itemType2',
								secondValue:'${workSignAlarm.itemType2}',		
	});	
	if("${workSignAlarm.type}"=="0"){
		$("#confirmPrjItemSelect").css("display","none");
		$("#workerClassifySelect").css("display","none");
		$("#notExistOffWorkType12Select").css("display", "");
		$("#mission").css("display","");
	}else{
		$("#confirmPrjItemSelect").css("display","");
		$("#workerClassifySelect").css("display","");
		$("#mission").css("display","none");
		$("#notExistOffWorkType12Select").css("display", "none");
	}
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
			<div class="panel-body" >
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" id="pk" name="pk" style="width:160px;"  value="${workSignAlarm.pk}" />
					<ul class="ul-form"  disabled="disabled">
						<div class="validate-group" >
							<li class="form-validate">
                                <label><span class="required">*</span>提醒类型</label> 
                                <house:xtdm id="type" dictCode="WORKALARMTYPE" value="${workSignAlarm.type}"  onchange="typeChange()" disabled="true"></house:xtdm>
                            </li>
						  	<li class="form-validate" id="confirmPrjItemSelect">
								<label><span class="required">*</span>验收节点</label>
								<house:dict id="confirmPrjItem" dictCode="" 
										sql="select a.Code,a.code+' '+a.descr descr  from tPrjItem1 a  where  a.Expired='F' order By Seq " 
										sqlValueKey="Code" sqlLableKey="Descr" value="${workSignAlarm.confirmPrjItem }" disabled="true">
								</house:dict>
							</li>
						 </div>
						<div class="validate-group">
							<li class="form-validate">
								<label><span class="required">*</span>施工项目2</label>
								<house:dict id="prjItem2" dictCode="" 
										sql="select a.Code,a.code+' '+a.descr descr  from tPrjItem2 a  where  a.Expired='F' order By Seq " 
										sqlValueKey="Code" sqlLableKey="Descr" value="${workSignAlarm.prjItem2}" disabled="true" >
								</house:dict>
							</li>
							<li class="form-validate">
								<label>阶段是否完成</label> 
								<house:xtdm id="isComplete" dictCode="YESNO" value="${workSignAlarm.isComplete}" disabled="true"></house:xtdm>
						  	</li>
						 </div>
						 <div id="mission">
							 <div class="validate-group">
							  	<li class="form-validate">
							  		<label>材料类型1</label> 
							  		<select id="itemType1" name="itemType1" value="${workSignAlarm.itemType1}" disabled="disabled"></select>
							  	</li>
							  	<li class="form-validate">
									<label>是否判断需求</label> <house:xtdm
									id="isNeedReq" dictCode="YESNO" value="${workSignAlarm.isNeedReq}" disabled="true"></house:xtdm>
							  	</li>
							 </div>
							 <div class="validate-group">
							  	<li class="form-validate">
							  		<label>材料类型2</label> 
							  		<select id="itemType2" name="itemType2" value="${workSignAlarm.itemType2}" disabled="disabled" ></select>
							  	</li>
							  	<li class="form-validate">
									<label><span class="required">*</span>任务类型</label>
									<house:dict id="jobType" dictCode="" 
											sql="select a.Code,a.code+' '+a.descr descr  from  tJobType a  where  a.Expired='F' " 
											sqlValueKey="Code" sqlLableKey="Descr" value="${workSignAlarm.jobType}" disabled="true" >
									</house:dict>
								</li>
							</div>	
						</div>
						 <div class="validate-group">
						 	<li class="form-validate" id="notExistOffWorkType12Select">
								<label>不存在人工工种</label>
								<house:dict id="notExistOffWorkType12" dictCode="" 
										sql="select a.Code,a.code+' '+a.descr descr  from  tWorkType2 a  where  a.Expired='F'  " 
										sqlValueKey="Code" sqlLableKey="Descr" value="${workSignAlarm.notExistOffWorkType12}"  disabled="true">
								</house:dict>
							</li>
							<li class="form-validate" id="workerClassifySelect">
								<label>工人分类</label>
								<house:xtdm id="workerClassify" dictCode="WORKERCLASSIFY" disabled="true"
										value="${workSignAlarm.workerClassify}"></house:xtdm>
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate" >
								<label>过期</label>
								<input type="checkbox" id="expired" name="expired" value="${workSignAlarm.expired }" onclick="checkExpired(this)" ${workSignAlarm.expired=='T'?'checked':'' } disabled="true"">
							</li>
						</div>
					</ul>	
				</form>
			</div>
  		</div>
  	</div>
  </body>
</html>

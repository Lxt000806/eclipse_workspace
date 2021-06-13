<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<title>工人签到提醒新增</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
 <script type="text/javascript">
  	//校验函数
$(function() {	
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			prjItem2:{  
				validators: {  
					 notEmpty: {  
						message: '施工项目2不能为空'  
					},
					stringLength: {
                           max: 10,
                           message: '长度不超过10个字符'
                    }
				}  
			},
			jobType:{  
				validators: {  
					 notEmpty: {  
						message: '任务类型不能为空'  
					},
					stringLength: {
                           max: 10,
                           message: '长度不超过10个字符'
                    }
				}  
			},
			isNeedReq:{  
				validators: {  
					 notEmpty: {  
						message: '是否判断需求不能为空'  
					},
				}  
			},
			isComplete:{  
				validators: {  
					 notEmpty: {  
						message: '阶段完成不能为空'  
					},
				}  
			},
			type:{  
				validators: {  
					 notEmpty: {  
						message: '提醒类型不能为空'  
					},
				}  
			},
			confirmPrjItem:{  
				validators: {  
					 notEmpty: {  
						message: '验收节点不能为空'  
					},
				}  
			},
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
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
		$("#mission").css("display","");
		$("#mission_li").css("display","");
	}else{
		$("#confirmPrjItemSelect").css("display","");
		$("#workerClassifySelect").css("display","");
		$("#mission").css("display","none");
		$("#mission_li").css("display","none");
		getPrjItem2ListByPrjItem1("${workSignAlarm.confirmPrjItem}", "${workSignAlarm.prjItem2}");
	}
});

function typeChange(){
	//防止初始化
    if(!$("#type").attr("firstLoadFlag")){
 	   $("#type").attr("firstLoadFlag", "1");
 	   return;
    }
    $("#dataForm input[type='text']:not([id=expired])").val('');
    $("#dataForm select:not([id=type])").val('');
	if($("#type").val()==0){
		getPrjItem2ListByPrjItem1("");
		$("#confirmPrjItemSelect").css("display","none");
		$("#workerClassifySelect").css("display","none");
		$("#mission").css("display","");
		$("#mission_li").css("display","");
		
	}else{
		$("#confirmPrjItemSelect").css("display","");
		$("#workerClassifySelect").css("display","");
		$("#mission").css("display","none");
		$("#mission_li").css("display","none");
	}
}

function save(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
 	var datas = $("#dataForm").serialize();	
	$.ajax({
		url:'${ctx}/admin/workSignAlarm/doUpdate',
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

function getPrjItem2ListByPrjItem1(prjItem1, prjItem2){
	$.ajax({
		url:'${ctx}/admin/prjItem2/getPrjItem2ListByPrjItem1',
		type: "get",
		data: {
			prjItem1: prjItem1
		},
		dataType: "json",
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
			if(obj && obj.html && obj.html != ""){
				$("#prjItem2").html(obj.html);
				$("#prjItem2").searchableSelect();
				if(prjItem2){
					$("#prjItem2").val(prjItem2);
				}else{
					$("#prjItem2").val("");
				}
			}
	    }
	 });
}

function confirmPrjItemChange(){
    if(!$("#confirmPrjItem").attr("firstLoadFlag")){
 	   $("#confirmPrjItem").attr("firstLoadFlag", "1");
 	   return;
    }
	var prjItem1 = $("#confirmPrjItem").val();
	if(prjItem1 && prjItem1 != "") {
		getPrjItem2ListByPrjItem1(prjItem1);
	} else {
		getPrjItem2ListByPrjItem1("");
	}
}
</script>
 </head>
  <body>
  	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
     				<button id="saveBut" type="button" class="btn btn-system "   onclick="save()">保存</button>
     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" id="pk" name="pk" style="width:160px;"  value="${workSignAlarm.pk}" />
					<input type="hidden" id="expired" name="expired" value="${workSignAlarm.expired }" />
					<ul class="ul-form">
						<div class="validate-group">
						  	<li class="form-validate">
                                <label><span class="required">*</span>提醒类型</label> 
                                <house:xtdm id="type" dictCode="WORKALARMTYPE" value="${workSignAlarm.type}"  onchange="typeChange()"></house:xtdm>
                            </li>
						  	<li class="form-validate" id="confirmPrjItemSelect">
								<label><span class="required">*</span>验收节点</label>
								<house:dict id="confirmPrjItem" dictCode="" 
										sql="select a.Code,a.code+' '+a.descr descr  from tPrjItem1 a  where  a.Expired='F' order By Seq " 
										sqlValueKey="Code" sqlLableKey="Descr" value="${workSignAlarm.confirmPrjItem }" onchange="confirmPrjItemChange()">
								</house:dict>
							</li>
						 </div>
						<div class="validate-group">
							<li class="form-validate">
								<label><span class="required">*</span>施工项目2</label>
								<house:dict id="prjItem2" dictCode="" 
										sql="select a.Code,a.code+' '+a.descr descr  from tPrjItem2 a  where  a.Expired='F' order By Seq " 
										sqlValueKey="Code" sqlLableKey="Descr" value="${workSignAlarm.prjItem2}"  >
								</house:dict>
							</li>
							<li class="form-validate">
								<label>阶段是否完成</label> 
								<house:xtdm id="isComplete" dictCode="YESNO" value="${workSignAlarm.isComplete}"></house:xtdm>
						  	</li>
						 </div>
						 <div id="mission">
							 <div class="validate-group">
							  	<li class="form-validate">
							  		<label>材料类型1</label> 
							  		<select id="itemType1" name="itemType1" value="${workSignAlarm.itemType1}" ></select>
							  	</li>
							  	<li class="form-validate">
									<label>是否判断需求</label> <house:xtdm
									id="isNeedReq" dictCode="YESNO" value="${workSignAlarm.isNeedReq}"></house:xtdm>
							  	</li>
							 </div>
							 <div class="validate-group">
							  	<li class="form-validate">
							  		<label>材料类型2</label> 
							  		<select id="itemType2" name="itemType2" value="${workSignAlarm.itemType2}" ></select>
							  	</li>
							  	<li class="form-validate">
									<label><span class="required">*</span>任务类型</label>
									<house:dict id="jobType" dictCode="" 
											sql="select a.Code,a.code+' '+a.descr descr  from  tJobType a  where  a.Expired='F' " 
											sqlValueKey="Code" sqlLableKey="Descr" value="${workSignAlarm.jobType}"  >
									</house:dict>
								</li>
							</div>	
						</div>
						<div class="validate-group">
							<li class="form-validate" id="mission_li">
								<label>不存在人工工种</label>
								<house:dict id="notExistOffWorkType12" dictCode="" 
										sql="select a.Code,a.code+' '+a.descr descr  from  tWorkType2 a  where  a.Expired='F'  " 
										sqlValueKey="Code" sqlLableKey="Descr" value="${workSignAlarm.notExistOffWorkType12}"  >
								</house:dict>
							</li>
							<li class="form-validate" id="workerClassifySelect">
								<label>工人分类</label>
								<house:xtdm id="workerClassify" dictCode="WORKERCLASSIFY" value="${workSignAlarm.workerClassify}"></house:xtdm>
							</li>
						</div>
						<div id="mission_expired">
							<div class="validate-group">
								<li class="form-validate" >
									<label>过期</label>
									<input type="checkbox" onclick="checkExpired(this)" ${workSignAlarm.expired=='T'?'checked':'' } >
								</li>
							</div>
						</div>
					</ul>	
				</form>
			</div>
  		</div>
  	</div>
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>员工信息工作经验修改</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	$(function(){
		$("#company").val("${map.company[0]}");
		$("#position").val("${map.position[0]}");
		$("#salary").val("${map.salary[0]}");
	    $("#begindate").val("${map.begindate[0]}");
		$("#enddate").val("${map.enddate[0]}");
		$("#leaversn").val("${map.leaversn[0]}");		
	});	
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields: {  
				company:{  
					validators: {  
						notEmpty: {  
							message: '开始时间不能为空'  
						}
					}  
				},
				enddate:{  
			        validators: {  
			            notEmpty: {  
			           		 message: '结束时间不能为空'  
			            }
			        }  
			     },
			     salary : {
					validators : {
						numeric : {
							message : '只能输入数字'
						}
					}
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});
	function save(){
			$("#dataForm").bootstrapValidator('validate');
			if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
			
			var selectRows = [];
			var datas=$("#dataForm").jsonForm();
			if(""!=$.trim($("#company").val())){
				datas.promType1Descr=$("#company").val();
			}
			if(""!=$.trim($("#position").val())){
				datas.promType2Descr=$("#position").val();
			} 
			if(""!=$.trim($("#salary").val())){
				datas.promRsnDescr=$("#salary").val();
			} 
			if(""!=$.trim($("#begindate").val())){
				datas.begindate=$("#begindate").val();
			} 
			if(""!=$.trim($("#enddate").val())){
				datas.enddate=$("#enddate").val();
			}
			if(""!=$.trim($("#leaversn").val())){
				datas.leaversn=$("#leaversn").val();
			}
			datas.expired="F";
			datas.actionlog="ADD";
			datas.lastupdate= new Date();
			datas.lastupdatedby=1;
		 	selectRows.push(datas);
			Global.Dialog.returnData = selectRows;
			closeWin();
		};		
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
			    		<button type="button" class="btn btn-system" onclick="save()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="text" name="pk" id="pk" hidden="true"/>
						<input type="text" name="number" id="number" hidden="true">
						<ul class="ul-form">
							<li>
								<label>公司</label>
								<input type="text" name="company" id="company" value="${map.company[0]}" />
								</li>
							<li>
								<label>职位</label>
								<input type="text" id="position" name="position" value="${map.position[0]}" />
							</li>
							<li class="form-validate">
								<label>工薪</label>
								<input type="text" id="salary" name="salary" value="${map.salary[0]}" />
							</li>
							 <li class="form-validate">
								<label>开始时间</label>
								<input type="text" id="begindate" name="begindate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
							<li class="form-validate">
								<label>结束时间</label>
								<input type="text" id="enddate" name="enddate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
							<li>
								<label class="control-textarea">离职原因</label>
								<textarea id="leaversn" name="leaversn" rows="3" >${map.leaversn[0]}</textarea>
							</li> 	
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看工种分类</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
//校验函数
$(function() {
	function getData(data){
		if (!data) return;
		var dataSet = {
			firstSelect:"itemType1",
			firstValue:'${itemsetdetail.itemtype1}',
		};
		Global.LinkSelect.setSelect(dataSet);
		$('#itemcode').val(data.code);
		$('#itemcodedescr').val(data.descr);
	}	
$(function() {
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
		},
		fields: {  
			beginFloor:{  
				validators: {  
					notEmpty: {  
						message: '起始楼层不能为空'  
					}
				}  
			},
			endFloor:{  
				validators: {  
					notEmpty: {  
						message: '截止楼层不能为空'  
					}  
				}  
			},
			cardAmount:{  
				validators: {  
					notEmpty: {  
						message: '初始金额不能为空'  
					}
				}  
			},
			incValue:{  
				validators: {  
					notEmpty: {  
						message: '递增金额不能为空'  
					}
				}  
			}
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
});
$("#saveBtn").on("click",function(){	
	$("#dataForm").bootstrapValidator('validate'); //验证表单
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
	var selectRows = [];		 
	var datas=$("#dataForm").jsonForm();					
	selectRows.push(datas);		
	Global.Dialog.returnData = selectRows;			
	closeWin();
});
function validateRefresh(){
	$('#dataForm').data('bootstrapValidator')
                  .updateStatus('beginFloor', 'NOT_VALIDATED',null)  
                  .validateField('beginFloor')
                  .updateStatus('endFloor', 'NOT_VALIDATED',null)  
                  .validateField('endFloor')
                  .updateStatus('cardAmount', 'NOT_VALIDATED',null)  
	              .validateField('cardAmount')
	              .updateStatus('incValue', 'NOT_VALIDATED',null)  
                  .validateField('incValue');                     
};
});

function prjRolechange(){	
	var str=$("#prjRole").find("option:selected").text();	
	str = str.split(' ');//先按照空格分割成数组
	document.getElementById("prjRoleDescr").value=str;   
}

function workType12change(){	
	var str=$("#workType12").find("option:selected").text();	
	str = str.split(' ');//先按照空格分割成数组
	document.getElementById("workType12Descr").value=str;   
}
</script>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
				<div class="validate-group">
					<li class="form-validate" hidden="true">
						<label><span class="required">*</span>工程角色</label>
						<house:dict id="prjRole" dictCode="" sql=" select Code,Descr from tPrjRole order by Code " 
								sqlValueKey="Code" sqlLableKey="Descr"  value="${prjRoleWorkType12.prjRole }"  onchange="prjRolechange()"></house:dict>
					</li>				
					<li class="form-validate">
						<label>工种分类</label>
								<input  name="workType12" id="workType12" value="${prjRoleWorkType12.workType12Descr }" />
						
					</li>
					<li hidden="true"> 
						<label>工程角色名称</label>	
						<input  name="prjRoleDescr" id="prjRoleDescr" />
						
					</li>
					<li hidden="true"> 
						<label>工种分类名称</label>	
						<input type="text" name="workType12Descr" id="workType12Descr" />
					</li>
				</div>				
				</ul>
			</form>
		</div>
	</div>
</div>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
</body>
</html>

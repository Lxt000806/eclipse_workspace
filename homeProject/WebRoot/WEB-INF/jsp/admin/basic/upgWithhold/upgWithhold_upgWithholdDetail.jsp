<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加升级扣项规则明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_baseItem.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">

//校验函数

	$(function() {
	
		if("V"=="${upgWithhold.m_umState}"){
			disabledForm("dataForm");
			$("#saveBtn").hide();
			openComponentView();
		}else{
			openComponent();
		}
	
			
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
			},
			fields: {  
				openComponent_item_itemcode:{ 
					validators: {  
						notEmpty: {  
							message: '材料编号不能为空'  
						},
						callback: {  
				            message: '请输入有效的材料编号'  
				        }  
				    }   						 
				}
			},
			submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});	
	});
	
	function openComponent(){
		if("JZ"=="${upgWithhold.itemType1}"){
			$("#itemCode").openComponent_baseItem({
			callBack: validateRefresh_descr,
			showValue:"${upgWithhold.itemCode}",
			valueOnly:"1",
			});
		}else{
			$("#itemCode").openComponent_item({
			callBack: validateRefresh_descr,
			showValue:"${upgWithhold.itemCode}",
			condition:{itemType1:"${upgWithhold.itemType1}",readonly:"1",},
			valueOnly:"1",
			});
		}
	}
	
	function openComponentView(){
		if("JZ"=="${upgWithhold.itemType1}"){
			$("#itemCode").openComponent_baseItem({
			callBack: validateRefresh_descr,
			showValue:"${upgWithhold.itemCode}",
			valueOnly:"1",
			disabled:true
			});
		}else{
			$("#itemCode").openComponent_item({
			callBack: validateRefresh_descr,
			showValue:"${upgWithhold.itemCode}",
			condition:{itemType1:"${upgWithhold.itemType1}",readonly:"1",},
			valueOnly:"1",
			disabled:true
			});
		}
	}
	
	function validateRefresh_descr(data){
		var code = data.code;
		$("#itemDescr").val(data.descr);
		$("#code").val(code);
	}

	function save(){
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
		var arr=JSON.parse('${itemCodeArray}');
		for(var i=0;i<arr.length;i++){
			if(arr[i]==$.trim($("#code").val())){
				art.dialog({
						content:"该材料编号已存在",
					});
					return false;
			}
		}
		var selectRows = [];		 
		var datas=$("#dataForm").jsonForm();					
		selectRows.push(datas);		
		Global.Dialog.returnData = selectRows;			
		closeWin();
	};
	
	
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" id="saveBtn" class="btn btn-system "   onclick="save()">保存</button>
		      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
            	<input type="hidden" id="itemCodeArray" name=itemCodeArray style="width:160px;" value="${itemCodeArray }"/>
            	<input type="hidden" id="code" name="code" style="width:160px;" value="${code }"/>
             	<input type="hidden" name="lastUpdatedBy" id="lastUpdatedBy" value=${upgWithhold.lastUpdatedBy }"/>
				<house:token></house:token>
				<ul class="ul-form">
					<li class="form-validate">
						<label><span class="required">*</span>材料编号</label>
							<input type="text" id="itemCode" name="itemCode"  readonly="true" />
						</label>	
					</li>
					<li>
						<label>材料名称</label>
							<input type="text" id="itemDescr" name="itemDescr"  value="${upgWithhold.itemDescr}" readonly="true"/>
						</label>						
					</li>
					<li hidden="true">					
						<label>材料类型</label>
							<input type="text" id="itemtype1" name="itemtype1" value="${upgWithhold.itemType1}"/>
						</label>						
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>
</body>
</html>

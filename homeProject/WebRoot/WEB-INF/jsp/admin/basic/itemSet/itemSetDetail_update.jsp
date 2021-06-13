<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加材料套餐包明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
function getData(data){
	if (!data) return;
	var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${itemsetdetail.itemtype1}',
	};
	Global.LinkSelect.setSelect(dataSet);
	$('#itemcode').val(data.code);
	$('#itemcodedescr').val(data.descr);
	validateRefresh_choice();
	}	
//校验函数					

$(function() {
	$("#itemcode").openComponent_item({callBack: getData,condition:{itemType1:'${itemSetDetail.itemtype1}',readonly:"1",custType:"${itemSetDetail.custType}"},valueOnly:'1'});
	$("#itemcode").setComponent_item({showValue:'${itemSetDetail.itemcode}'});
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
		},
		fields: {  
			openComponent_item_itemcode:{  
				validators: {  
					notEmpty: {  
						message: '材料编号不能为空'  
					}  
				}  
			},
			unitprice:{
				validators:{
				notEmpty: {message: '材料价格不能为空'},  			
				numeric: {
                           message: '只能输入数字'
                       }
				}
			}
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
});
function save(){
	$("#dataForm").bootstrapValidator('validate'); //表单验证
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;//表单验证	
	var itemcode=$("#itemcode").val();					
	if(itemcode==''){
		art.dialog({content: "请填写材料编号",width: 200});
		return false;
	}
	if ($("#unitprice").val()==''){
		art.dialog({
			content: "请输入材料单价！",
			width: 200
		});
		return false;	
	}	
	var selectRows = [];		 
	var datas=$("#dataForm").jsonForm();					
	selectRows.push(datas);		
	Global.Dialog.returnData = selectRows;			
	closeWin();
};
function validateRefresh(){
	$('#dataForm').data('bootstrapValidator')
	                 .updateStatus('openComponent_item_itemcode', 'NOT_VALIDATED',null)  
	                 .validateField('openComponent_item_itemcode')
	                 .updateStatus('unitprice', 'NOT_VALIDATED',null)  
	                 .validateField('unitprice');
	                                       
}
function validateRefresh_choice(){
	$('#dataForm').data('bootstrapValidator')
	                 .updateStatus('openComponent_item_itemcode', 'NOT_VALIDATED',null)  
	                 .validateField('openComponent_item_itemcode');                    
}
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
					<house:token></house:token>
				<ul class="ul-form">
					<li class="form-validate">
						<label><span class="required">*</span>材料编号</label>
						<input type="text" id="itemcode" name="itemcode" style="width:160px;" value="${itemSetDetail.itemcode}" readonly="true"/>
					</li>
					<li>
						<label>材料名称</label>
						<input type="text" id="itemcodedescr" name="itemcodedescr" style="width:160px;" value="${itemSetDetail.itemDescr}" readonly="true"/>
					</li>
					<li class="form-validate">
						<label><span class="required"  >*</span>套餐单价</label>
						<input id="unitprice" name="unitprice" id="unitprice"  style="IME-MODE: disabled; WIDTH: 160px" 
							onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
							onafterpaste="this.value=this.value.replace(/\D/g,'')"  size="14"  type="text" value="${itemSetDetail.unitprice}" />								
					</li>				
					<li hidden="true">
						<label>材料类型</label>
						<input type="text" id="itemtype1" name="itemtype1"  value="${itemSetDetail.itemtype1}"/>
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
</body>
</html>

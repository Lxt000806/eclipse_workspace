<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加礼品明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
var selectRows = [];
function onchange1(){		
	var str=$("#Type").text();	
	var Type=$("#Type").val();
	str = str.split(' ');//先按照空格分割成数组
	str=str[Type];
	if(Type==str.length){
	var str1=str.substring(0,str.length);
	}
	else{
	var str1=str.substring(0,str.length-1);
	}
	document.getElementById("typedescr").value=str1; 
}

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
	validateRefresh_choice();
	}	
	
$(function() {
	$("#itemcode").openComponent_item({callBack: getData,condition:{itemType1:'LP',readonly:"1",},valueOnly:'1'});
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
		},
		fields: {  
			openComponent_item_itemcode:{ 
				 validators: {  
		            notEmpty: {  
		            message: '礼品编号不能为空'  
		            },
		            callback: {  
		            message: '请输入有效的材料编号'  
		            }   
		        }   						 
			},
			Type:{
				validators:{
				notEmpty: {message: '活动类型不能为空'},  								
				}
			}
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
});
$("#saveBtn").on("click",function(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var itemcode=$("#itemcode").val();	
		var Type=$("#Type").val();				
		if(itemcode==''){
			art.dialog({content: "请选择礼品编号",width: 200});
			return false;
		}
		if(Type==''){
			art.dialog({content: "请选择类型",width: 200});
			return false;
		}					 
	var datas=$("#dataForm").jsonForm();					
	selectRows.push(datas);		 			
	art.dialog({content: "添加成功！",width: 200});
	$("#dataTableExists_selectRowAll").val(JSON.stringify(selectRows));
	$("#dataForm input[type='text']").val('');
	$("#itemcode").val('');
	$("#itemcodedescr").val('');
});
	
function validateRefresh(){
	$('#dataForm').data('bootstrapValidator')
                  .updateStatus('openComponent_item_itemcode', 'NOT_VALIDATED',null)  
                  .validateField('openComponent_item_itemcode')
                  .updateStatus('Type', 'NOT_VALIDATED',null)  
                  .validateField('Type');
}
function validateRefresh_choice(){
	$('#dataForm').data('bootstrapValidator')
	              .updateStatus('openComponent_item_itemcode', 'NOT_VALIDATED',null)  
	              .validateField('openComponent_item_itemcode');                    
}
	$("#itemcode").openComponent_item({callBack: getData,condition:{itemType1:'LP',readonly:"1",},valueOnly:'1'});		
});
function closeWinCmpActivityGift(){
	Global.Dialog.returnData = selectRows;		
	closeWin();
}
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" id="saveBtn" class="btn btn-system "  >保存</button>
		      <button type="button" class="btn btn-system "   onclick="closeWinCmpActivityGift()">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<li class="form-validate">
						<label><span class="required">*</span>礼品编号</label>
						<input type="text" id="itemcode" name="itemcode"  value="${cmpactivitygift.itemcode}" readonly="true"/>
					</li>
					<li >		
						<label>礼品名称</label>
						<input type="text" id="itemcodedescr" name="itemcodedescr"  value="${cmpactivitygift.itemCodeDescr}" readonly="true"/>
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>活动类型</label>
						<house:xtdm  id="Type" dictCode="ACTGIFTTYPE"    value="${cmpactivitygift.type}" onchange="onchange1()" ></house:xtdm>
					</li>
					<li hidden="true">
						<label>活动类型名称</label>
						<input id="typedescr" name="typedescr"   value="${cmpactivitygift.typedescr}"/>
						<input id="itemtype1" name="itemtype1"   value="LP"/>
					</li>
					<input  hidden="true" id="dataTableExists_selectRowAll" name="dataTableExists_selectRowAll" value="">
				</ul>
			</form>
		</div>
	</div>
</div>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>新增预付金管理页面</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	var isExistsspl;
	//检测供应商号是否已经存在于临时表中
	function splValue(splcode){	
		var str=$.trim($("#unSelected").val());		
		var strs= new Array(); //定义一数组 
		strs = str.split(',');//先按照空格分割成数组
		isExistsspl="0";
		for (var i=0;i<strs.length ;i++){ 
			if(splcode==strs[i].replace(/(^\s*)|(\s*$)/g, "")){
				return isExistsspl="1";	
			};
		} 
	}
//校验函数
	$(function() {
		$("#splcode").openComponent_supplier({callBack: getData,condition:{itemType1:"${supplierPrepayDetail.itemtype1}",valueOnly:'1',readonly:"1"}});
		$("#dataForm").bootstrapValidator({
			message: "This value is not valid",
	        feedbackIcons: { /*input状态样式图片*/
				/* valid: "glyphicon glyphicon-ok",
				invalid: "glyphicon glyphicon-remove", */
				validating: "glyphicon glyphicon-refresh"
	        },
	        fields: {  
		    	amount: {
			        validators: { 
			            numeric: {
			            	message: '付款金额只能是数字' 
			            },
			            notEmpty: { 
			            		message: '付款金额不能为空'  
			            }
			        }
	      	 	}, 	
	    	},
			submitButtons : 'input[type="submit"]' /*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
		$("#dataForm").bootstrapValidator("addField", "openComponent_supplier_splcode", {  
	        validators: {  
	            remote: {
		            message: '',
		            url: '${ctx}/admin/supplier/getSupplier',
		            data: getValidateVal,  
		            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
		        }
	        }  
    	});
	});
	function getData(data){
			if (!data) return;
			var dataSet = {
				firstSelect:"itemtype1",
				firstValue:"${payManageDetail.itemtype1}",
			};
			Global.LinkSelect.setSelect(dataSet);
			if(typeof(data.Descr) == "undefined"){
				$("#spldescr").val(data.descr);
				$("#prepaybalance").val(data.prepayBalance);
				$("#splcode").val(data.code);
			}else{
				$("#splcode").val(data.Code);
				$("#spldescr").val(data.Descr);
				$("#prepaybalance").val(data.PrepayBalance);
			}
			validateRefresh('openComponent_supplier_splcode'); 
	}
	 	
	function save(){
		$("#dataForm").bootstrapValidator('validate');	
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var splcode=$.trim($("#splcode").val());
		splValue(splcode);
		if (isExistsspl=="1"){
			art.dialog({
				content: "该供应商编号已存在！",
				width: 200
			});
			return false;	
		} 
		var splcode=$.trim($("#splcode").val());//预付的供应商编号
		if (splcode=="") {
			art.dialog({
				content: "请选择供应商！",
				width: 200
			});
			return false;	
		}
		if ($.trim($("#amount").val())=="") {
			art.dialog({
					content: "请填写付款金额！",
					width: 200
			});
			return false;	
		}
		var amount=parseFloat($.trim($("#amount").val()));//付款金额
		if (amount==0){
			art.dialog({
					content: "付款金额不能为0！",
					width: 200
			});
			return false;	
		}
		var selectRows = [];		 
		var datas=$("#dataForm").jsonForm();					
			selectRows.push(datas);	
		Global.Dialog.returnData = selectRows;	
		closeWin();
	}

	
</script>	
</head>
<body >
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" id="saveBtn" class="btn btn-system" onclick="save()" >保存</button>
		      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<li class="form-validate">
						<label><span class="required">*</span>供应商编号</label>
						<input type="text" id="splcode" name="splcode" value="${supplierPrepayDetail.splcode}" readonly="true" />
					</li>
					<li class="form-validate">
						<label>供应商名称</label>
						<input type="text" id="spldescr" name="spldescr" value="${supplierPrepayDetail.spldescr}" readonly="true"/>
					</li>			
					<li class="form-validate">
						<label>预付金额</label>
						<input name="prepaybalance" id="prepaybalance" type="text" value="${supplierPrepayDetail.prepaybalance}" readonly="true"/>								
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>付款金额</label>
						<input type="text" id="amount" name="amount" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  value="${supplierPrepayDetail.amount}"/>
					</li>	
					<li class="form-validate">
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks">${itemSet.remarks}</textarea>
					</li>
					<li hidden="true">
						<label>材料类型</label>
						<input type="text" id="itemtype1" name="itemtype1" value="${supplierPrepayDetail.itemtype1}"/>
						<label>类型</label>
						<input type="text" id="type" name="type" value="${supplierPrepayDetail.type}"/>
						<label>supplier</label>
						<input type="text" id="unSelected" name="unSelected" value="${supplierPrepayDetail.unSelected}"/>
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>

</body>
</html>

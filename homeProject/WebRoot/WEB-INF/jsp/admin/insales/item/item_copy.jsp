<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>材料信息</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
function save(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	//$("#isInv").removeAttr("disabled");
	if ($.trim($("#splCode").val())==" "){
		art.dialog({
			content: "请选择品牌！",
			width: 200
		});
		return false;
	}
	if ($.trim($("#uom").val())==""){
		art.dialog({
			content: "请填写单位！",
			width: 200
		});
		return false;
	}
	if ($("#isActualItem").val() == "0" && $.trim($("#wareHouseItemCode").val())==""){
		art.dialog({
			content: "非实际材料时,请填写库存材料编号！",
			width: 200
		});
		return false;
	}
	
	//允许买手录入同一个人 modify by xzy @Date:2020-8-11
	/*if ($.trim($("#buyer1").val())!="" && $.trim($("#buyer2").val())!=""&& $.trim($("#buyer1").val())==$.trim($("#buyer2").val())){
		art.dialog({
			content: "买手1与买手2不能是同一个人！",
			width: 200
		});
		return false;
	}*/
	if ($.trim($("#commiType").val())=="2"&&(parseFloat($("#commiPerc").val())>1||parseFloat($("#commiPerc").val())<0)){
		art.dialog({
			content: "提佣比例不在0-1之间！",
			width: 200
		});
		return false;
	}
	checkIsExistItem();
}

function checkIsExistItem() {
	var datas=$("#dataForm").jsonForm();
	
	var grid = $("#supplierCostTable")
    var rows = grid.getRowData()
    var duplicate = false
    
    // 判断是否存在相同供应商
    outer:
        for (var i = 0; i < rows.length - 1; i++)
            for (var j = i + 1; j < rows.length; j++)
                if (rows[i].supplcode === rows[j].supplcode) {
                    duplicate = true
                    break outer
                }
        
    if (duplicate) {
        art.dialog({content: "同一材料的供应商供货价不能存在相同供应商！"})
        return
    }
    
    datas.supplCostJson = JSON.stringify(rows)
	
 	$.ajax({                    
		url: "${ctx}/admin/item/checkIsExistItem",
		type: "post",
		data: datas,
		//dataType:'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "请求数据出错"});
	    },
	    success: function(obj){
	    	if(obj.code=="0"){
	    		art.dialog({
					content: "已存在相同的材料名称,是否继续保存",
					width: 200,
					okValue: "确定",
					ok: function () {
						doSave(datas);
					},
					cancelValue: "取消",
					cancel: function () {
						return ;
					}
				});
				
	    	}else{
				doSave(datas);
	    	}
    	}
	});
}
function doSave(datas){	
	$.ajax({
		url:'${ctx}/admin/item/doSave',
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

$(function() {
	$("#dataForm").bootstrapValidator({
		excluded:[":disabled"],
        message : 'This value is not valid',
        feedbackIcons : {/*input状态样式图片*/
            validating : 'glyphicon glyphicon-refresh'
        },
        fields: {
	        itemType1: {
		        validators: { 
		            notEmpty: { 
		            	message: '材料类型1不能为空'  
		            },
		        }
	      	},
	      	itemType2: {
		        validators: { 
		            notEmpty: { 
		            	message: '材料类型2不能为空'  
		            },
		        }
	      	},
	      	sendType: {
		        validators: { 
		            notEmpty: { 
		            	message: '发货类型不能为空'  
		            },
		        }
	      	},
	      	uom: {
		        validators: { 
		            notEmpty: { 
		            	message: '单位不能为空'  
		            },
		        }
	      	},
	      	splCode: {
		        validators: { 
		            notEmpty: { 
		            	message: '品牌不能为空'  
		            },
		        }
	      	},
	        itemSize: {
		        validators: { 
		            notEmpty: { 
		            	message: '尺寸不能为空'  
		            },
		             numeric: {
		            	message: '尺寸只能是数字' 
		            },
		        }
	      	},
	      	 perNum: {
		        validators: { 
		            notEmpty: { 
		            	message: '单位片数不能为空'  
		            },
		             numeric: {
		            	message: '单位片数只能是数字' 
		            },
		        }
	      	},
	      	 perWeight: {
		        validators: { 
		            notEmpty: { 
		            	message: '单位重量不能为空'  
		            },
		             numeric: {
		            	message: '单位重量只能是数字' 
		            },
		        }
	      	},
	      	 packageNum: {
		        validators: { 
		            notEmpty: { 
		            	message: '每箱片数不能为空'  
		            },
		             numeric: {
		            	message: '每箱片数只能是数字' 
		            },
		        }
	      	},
	      	 dispSeq: {
		        validators: { 
		            notEmpty: { 
		            	message: '显示顺序不能为空'  
		            },
		             numeric: {
		            	message: '显示顺序只能是数字' 
		            },
		        }
	      	},
	       	cost: {
		        validators: { 
		            notEmpty: { 
		            	message: '成本不能为空'  
		            },
		             numeric: {
		            	message: '成本只能是数字' 
		            },
		        }
	      	},
	      	price: {
		        validators: { 
		            notEmpty: { 
		            	message: '现价不能为空'  
		            },
		             numeric: {
		            	message: '现价只能是数字' 
		            },
		        }
	      	},
	      	marketPrice: {
		        validators: { 
		            notEmpty: { 
		            	message: '市场价价不能为空'  
		            },
		             numeric: {
		            	message: '市场价只能是数字' 
		            },
		        }
	      	},
	      	commiPerc: {
		        validators: { 
		            notEmpty: { 
		            	message: '提成比例不能为空'  
		            },
		             numeric: {
		            	message: '提成比例只能是数字' 
		            },
		        }
	      	},
	      	lampNum: {
		        validators: { 
		            notEmpty: { 
		            	message: '灯头数不能为空'  
		            },
		             numeric: {
		            	message: '灯头数只能是数字' 
		            },
		        }
	      	},
	      	installFee: {
		        validators: { 
		            notEmpty: { 
		            	message: '安装费单价不能为空'  
		            },
		             numeric: {
		            	message: '安装费单价只能是数字' 
		            },
		        }
	      	},
	      	installFeeType: {
		        validators: { 
		            notEmpty: { 
		            	message: '安装费单价不能为空'  
		            },
		        }
	      	},	
	      	isActualItem: {
		        validators: { 
		            notEmpty: { 
		            	message: '是否实际材料不能为空'  
		            },
		        }
	      	},
		},
    	submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    
	});	
	$("#cost_show").show();
	$("#projectCost_show").show();
	$("#projectCost").val(0.0);
		
});
function calPerfPer(){
	if ($.trim($("#itemType1").val())!="RZ"){
		 $("#perfPer").val(0);
		 return;
	}
	if ($.trim($("#cost").val())==""||$.trim($("#price").val())==""){
		return;	
	}
 	var sItemType2=$.trim($("#itemType2").val());
 	var dCost=0.0, dOfferPri=0.0;
 	dCost = parseFloat($.trim($("#cost").val()));
    dOfferPri = parseFloat($.trim($("#price").val()));
    if(dOfferPri==0.00){
      	$("#perfPer").val(0);
    }
 	$.ajax({
 		url:"${ctx}/admin/item/getPerfPer",
		type:'post',
		data:{itemType2:sItemType2,cost:dCost,price:dOfferPri},
		dataType:'json',
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
		},
		success:function(obj){
			if (obj){  
				$("#perfPer").val(obj.perfPer);
			}
		}
	});
	
}
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="content-form">
			<!--panelBar-->
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="saveBtn" onclick="save()">保存</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">关闭</button>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="height:45px;margin-bottom:5px; ">
			<div class="panel-body" style="padding-top:5px; ">
				<form action="" method="post" id="dataForm1" class="form-search" target="targetFrame" >
					<ul class="ul-form">
						<li class="form-validate"><label style="color:#777777"><span class="required">*</span>材料编号</label>
							<input type="text" id="code" name="code" placeholder="保存自动生成" value="${item.code}" readonly="readonly" />
						</li>
					</ul>
				</form>
			</div>
		</div>
		<form action="" method="post" id="dataForm" class="form-search" target="targetFrame">
			<house:token></house:token>
			<input type="hidden" name="m_umState" id="m_umState" value="${item.m_umState}" />
			<div class="container-fluid">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab_itemMain" data-toggle="tab">基础属性</a></li>
					<li class=""><a href="#tab_itemSoft" data-toggle="tab">扩展属性</a></li>
					<li class=""><a href="#tab_itemPicture" data-toggle="tab">材料图片</a></li>
					<li>
                        <a href="#tab_supplierCost" data-toggle="tab">供应商供货价</a>
                    </li>
				</ul>
				<div class="tab-content">
					<div id="tab_itemMain" class="tab-pane fade in active">
						<jsp:include page="tab_itemMain.jsp"></jsp:include>
					</div>
					<div id="tab_itemSoft" class="tab-pane fade ">
						<jsp:include page="tab_itemSoft.jsp"></jsp:include>
					</div>
					<div id="tab_itemPicture" class="tab-pane fade ">
						<jsp:include page="tab_itemPicture.jsp"></jsp:include>
					</div>
					<div id="tab_supplierCost" class="tab-pane fade ">
                        <jsp:include page="tab_supplierCost.jsp"></jsp:include>
                    </div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>

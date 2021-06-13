<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>套餐包明细</title>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <%@ include file="/commons/jsp/common.jsp" %>
  <script src="${resourceRoot}/pub/component_prePlanArea.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	function goto_query(table, form, event) {
		var datas = $("#" + form).jsonForm();
		if(datas.itemSetNo=="") return;
		datas.no = datas.itemSetNo;
		datas.custCode="${itemChg.custCode}";
		$("#" + table).jqGrid("setGridParam", {
		  url: "${ctx}/admin/itemSetDetail/goJqGrid",
		  postData: datas,
		  page: 1
		}).trigger("reloadGrid");
    }
	
    //tab分页
   $(function(){
	     var isOutSet='${itemChg.isOutSet}';
	     if(isOutSet=="") isOutSet="1";
	     loadItemSet(isOutSet);
		 $("#prePlanAreaPK").openComponent_prePlanArea({
			condition: {custCode:"${itemChg.custCode}"},
			callBack:function(data){
				//validateRefresh('openComponent_prePlanArea_prePlanAreaPK');
				validateRefresh('openComponent_prePlanArea_prePlanAreaPK',"NOT_VALIDATED",'tab5_page_form');
		    }
		  });	
	   
		  var itemType1 = '${itemChg.itemType1}'.trim();
	      var id_detailW = window.parent.document.getElementById("id_detail").style.width.substring(0, 4);
	      $("#itemSet").css('width', id_detailW);
	      //初始化表格
	      Global.JqGrid.initJqGrid("itemSetDetaildataTable", {
		      height: 185,
		      styleUI: 'Bootstrap',
				colModel : [
					{name: "itemsetno", index: "itemsetno", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
					{name: "itemsetdescr", index: "itemsetdescr", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
					{name: "itemcode", index: "itemcode", width: 118, label: "材料编号 ", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 241, label: "材料名称", sortable: true, align: "left"},
					{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
					{name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
					{name: "itemtype3descr", index: "itemtype3descr", width: 85, label: "材料类型3", sortable: true, align: "left", hidden: true},
					{name: "uomdescr", index: "uomdescr", width: 60, label: "单位", sortable: true, align: "left"},
					{name: "price", index: "price", width: 80, label: "单价", sortable: true, align: "right"},
					{name: "setunitprice", index: "setunitprice", width: 80, label: "套餐单价", sortable: true, align: "right"},
					{name: "algorithdescr", index: "algorithdescr", width: 80, label: "算法", sortable: true, align: "right"},
					{name: "algorithmper", index: "algorithmper", width: 80, label: "算法系数", sortable: true, align: "right"},
					{name: "algorithmdeduct", index: "algorithmdeduct", width: 80, label: "算法扣除数", sortable: true, align: "right"},
					{name: "cuttypedescr", index: "cuttypedescr", width: 80, label: "切割类型", sortable: true, align: "right"},
					{name: "remarks", index: "remarks", width: 180, label: "材料描述", sortable: true, align: "left"},
					{name: "cost", index: "cost", width: 20, label: "成本", sortable: true, align: "left", hidden: true},
		   		  	{name : 'custtypeitempk',index : 'custtypeitempk',width : 110,label:'套餐材料信息编号',sortable : true,align : "left",hidden:true}, 
				],
	      });
		$("#tab5_page_form").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields: {  
				itemSetNo:{  
					validators: {  
						notEmpty: {  
							message: '套餐包编号不能为空'  
						},	
					}  
				},	
	     		openComponent_prePlanArea_prePlanAreaPK: {  
			        validators: { 
			        	 notEmpty: {  
							message: '空间不能为空'  
						 },
			             remote: {
				            message: '',
				            url: '${ctx}/admin/prePlanArea/getPrePlanArea',
				            data: getValidateVal,  
				            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
				        }     
			        }  
		        }, 
		        
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
		
    });
    
   //添加套餐包
	function itemSetAdd(){
    	if ($.trim($("#itemSetNo").val())==""){
    		art.dialog({
    			content: "请选择套餐包！",
    			width: 200
    		});
    		return false;
    	}	
		$("#tab5_page_form").bootstrapValidator('validate');
		if(!$("#tab5_page_form").data('bootstrapValidator').isValid()) return;
		var datas=$("#tab5_page_form").jsonForm();
		datas.custCode="${itemChg.custCode}";
		$.ajax({
				url:"${ctx}/admin/itemChg/getGenChgMainItemSet",
				type:"post",
		        dataType:"json",
		        data:datas,
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '重新生成数据出错~'});
			    },
		      success: function(obj){
			     	if(obj.rs){
			     		var rowNo = window.parent.document.getElementById("rowNo").value;
				     	$.each(JSON.parse(obj.datas),function(k,v){
				  			$(window.parent.document.getElementById("itemChgDetailDataTable")).addRowData(rowNo, v, "last");
		   			        window.parent.document.getElementById("rowNo").value++;
		   			     	rowNo++;
				     	});
				     	window.parent.goto_query("dataTable", {"descr": ''});	
				  		art.dialog({
							content: obj.msg,
							time: 500,
						});
			  		}else{
			  			art.dialog({
							content: obj.msg
						});
			  		}  	     	
			  }
		});			  	
   } 
   
   function loadItemSet(isOutSet){
	    $("#itemSetNo").val("");
	    $("#itemSetDetaildataTable").jqGrid("clearGridData");
	    //初始化套餐包
	    Global.LinkSelect.initOption("itemSetNo", "${ctx}/admin/itemSet/itemSetNo", {
	        itemType1:'${itemChg.itemType1}',
	        custType: '${itemChg.custType}',
	        isOutSet: isOutSet,
	    });	
	    
   } 
   
  </script>
</head>

<body>
	<div>
		<div id="itemSet" style="float: right;">
			<div>
				<div id="tab1" class="tab_content" style="display: block; ">
					<div class="edit-form">
						<form role="form" class="form-search" action="" method="post" id="tab5_page_form">
							<house:token></house:token>
							<ul class="ul-form">
								<li class="form-validate">
									<label>套餐包</label>
									<select type="text" id="itemSetNo" name="itemSetNo" onchange="goto_query('itemSetDetaildataTable','tab5_page_form',this)"></select>
								</li>
								<li class="form-validate">
									<label>空间</label> 
									<input type="text" id="prePlanAreaPK" name="prePlanAreaPK" />
								</li>
								<li>
								</li>
								<li> 
									 <button type="button" class="btn btn-system" id="addItemSetBtn"  onclick="itemSetAdd()" style="font-size: 12px;margin-left: 100px;width: 35px;">添加</button>  
								</li>
							</ul>
						</form>
					</div>
					<div class="clear_float"></div>
					<!--query-form-->
					<div class="pageContent">
						<div id="content-list1">
							<table id="itemSetDetaildataTable"></table>
							<div id="itemSetDetaildataTablePager"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

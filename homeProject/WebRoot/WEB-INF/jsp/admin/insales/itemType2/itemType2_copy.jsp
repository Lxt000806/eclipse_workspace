<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>材料类型2--复制</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript">
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/itemType1/byItemType1","itemType1","itemType12");
    Global.LinkSelect.setSelect({firstSelect:"itemType1",
								firstValue:"${itemType2.itemType1}",
								secondSelect:"itemType12",
								secondValue:"${itemType2.itemType12}"});
   	Global.LinkSelect.initSelect("${ctx}/admin/workType1/workType","workType1","workType2");
	Global.LinkSelect.setSelect({firstSelect:"workType1",
								firstValue:"${itemType2.workType1}",
								secondSelect:"workType2",
								secondValue:"${itemType2.workType2}"});								
});
$(function(){  
	    Global.LinkSelect.initSelect("${ctx}/admin/itemType2/prjType","appPrjItem");
	    Global.LinkSelect.setSelect({firstSelect:"appPrjItem",
								firstValue:"${itemType2.appPrjItem}",});	
});
function save() {
		if($("#descr").val() == null || $("#descr").val()== undefined || $("#descr").val() == ''){
			art.dialog({
				content : "请填写完整信息",
				width : 200,				
			});
			return;
		} 
 		if($("#itemType1").val() == null || $("#itemType1").val()== undefined || $("#itemType1").val() == ''){
			art.dialog({
				content : "请填写完整信息",
				width : 200,				
			});
			return;
		}  
	   if($("#itemType1").val() == 'JZ'){
	   		if($("#workType1").val() == null || $("#workType1").val()== undefined || $("#workType1").val() == ''){
				art.dialog({
					content : "请选择工种分类1",
					width : 200,				
				});
				return;
			}
			if($("#workType2").val() == null || $("#workType2").val()== undefined || $("#workType2").val() == ''){
				art.dialog({
					content : "请选择工种分类2",
					width : 200,				
				});
				return;
			}
		}
		var datas = $("#dataForm").serialize();
		$.ajax({
			url : "${ctx}/admin/itemType2/doSave",
			type : "get",
			data : datas,
			dataType : "json",
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : "保存数据出错~"
				});
			},
			success : function(obj) {
				if (obj.rs) {
					art.dialog({
						content : obj.msg,
						time : 1000,
						beforeunload : function() {
							closeWin();
						}
					});
				} else {
					$("#_form_token_uniq_id").val(obj.token.token);
					art.dialog({
						content : obj.msg,
						width : 200
					});
				}
			}
		});
	}
	$(function(){
		$("#orderType option[value='0']").hide();
	});
</script>
</head>
<body>
  	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
			    	<button type="submit" class="btn btn-system" id="saveBut" onclick="save()">保存</button>
	     			<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<li>
							<label style="width:120px">编号</label>
							<input type="text" id="code" name="code"  readonly="readonly" value="${null}" placeholder="主键自动生成"/>
						</li>
						<li class="form-validate">	
							<label style="width:120px">名称</label>
							<input type="text" id="descr" name="descr" value="${itemType2.descr}" />
						</li>
						<li>
							<label style="width:120px">其他成本占销售额比例</label>
							<input type="text" id="otherCostPer_Sale" name="otherCostPer_Sale" value="${itemType2.otherCostPer_Sale}"/>
						</li>
						<li class="form-validate">
							<label style="width:120px">材料类型1</label>
							<select id="itemType1" name="itemType1"></select>
						</li>
						<li>
							<label style="width:120px">其他成本占成本比例</label>
							<input type="text" id="otherCostPer_Cost" name="otherCostPer_Cost" value="${itemType2.otherCostPer_Cost}"/>
						</li>
						<li class="form-validate">
							<label style="width:120px">材料分类</label>
							<select id="itemType12" name="itemType12"></select>
						</li>
						<li>
							<label style="width:120px">到货天数</label>
							<input type="text" id="arriveDay" name="arriveDay" value="${itemType2.arriveDay}"/>
						</li>
						<li>
							<label style="width:120px">材料工种分类1</label>
							<select id="workType1" name="workType1"></select>
						</li>
						<li>
							<label style="width:120px">显示顺序</label>
							<input type="text" id="dispSeq" name="dispSeq" value="${itemType2.dispSeq}"/>
						</li>
						<li>
							<label style="width:120px">材料工种分类2</label>
							<select id="workType2" name="workType2"></select>
						</li>
						<li>
							<label style="width:120px">订货验收结点</label>
							<select id="appPrjItem" name="appPrjItem"></select>
						</li>
						<li>
							<label style="width:120px">拆单类型</label>
							<house:xtdm id="orderType" dictCode="ORDERTYPE" value="${itemType2.orderType}"></house:xtdm>				
						</li> 
					</ul>		
				</form>
			</div>
  		</div>
  	</div>
  </body>
</html>

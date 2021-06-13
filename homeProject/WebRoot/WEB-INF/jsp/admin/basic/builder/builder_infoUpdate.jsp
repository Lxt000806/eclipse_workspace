<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>工程信息编辑</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_latitude.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields:{
				offset:{
	        		validators:{
	        			notEmpty: {message: '签到偏移量不能为空'},
	        			numeric: {message: '签到偏移量只能输入数字'}
	        		}
        		},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});

	$(function() {
		//初始化专盘队长
		changePrjSpc();
		//初始化一二级区域联动
		Global.LinkSelect.initSelect("${ctx}/admin/builder/regionCodeByAuthority","regionCode","regionCode2");
				 Global.LinkSelect.setSelect({firstSelect:'regionCode',
									firstValue:'${builder.RegionCode}',
									secondSelect:'regionCode2',
									secondValue:'${builder.RegionCode2}'
									});	 
		//初始化工程大区
		changeRegion2();
		$("#openComponent_employee_prjLeader").attr("readonly",true);
		initTude();
		$("#openComponent_latitude_tude").width("160px");
		$("#openComponent_latitude_tude").attr("readonly",true);
		$("#saveBtn").on("click", function() {
			$("#dataForm").bootstrapValidator('validate');
			if (!$("#dataForm").data('bootstrapValidator').isValid())
				return;
			var datas = $("#dataForm").serialize();
			$.ajax({
				url : "${ctx}/admin/builder/doInfoUpdate",
				type : "post",
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
		});
	});
	function changePrjSpc(){
		var isPrjSpc=$("#isPrjSpc").val();
		if(isPrjSpc=="1"){
			$("#prjLeader").openComponent_employee({showValue:"${builder.PrjLeader}",showLabel:"${builder.PrjLeaderDescr}"});
			$("#leaderDiv").show();
		}else{
			$("#leaderDiv").hide();
		}
	}
	
	function changeRegion2() {
		$.ajax({
			url : '${ctx}/admin/builder/findPrjRegion',
			type : 'post',
			data : {
				'regionCode2' : $("#regionCode2").val()
			},
			async : false,
			dataType : 'json',
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : '保存数据出错~'
				});
			},
			success : function(obj) {
				if(obj.length>0){
					$("#prjRegion").val(obj[0].Descr);
				}else{
					$("#prjRegion").val("");
				}
			}
		});
	}
	//初始化经纬度选择输入框
function initTude(){
	var t=$("#tude").val().replace("|","%7C");
	$("#tude").openComponent_latitude({
		showValue:"${builder.tude}",
		condition:{descr:$("#descr").val(),tude:t,
		}
	});
}
</script>
</head>
<body>
	<form action="" method="post" id="dataForm" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut"
							onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="code" value="${builder.code }" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate"><label >项目名称</label>
								<input type="text" style="width:160px;" id="descr" name="descr"
								value="${builder.Descr }" readonly="readonly" onchange="initTude()"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>签到偏移量</label>
								<input type="text" id="offset" name="offset"
								    style="width:160px;" value="${builder.OffSet}"/>
								<span>米</span>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label >是否工程专盘</label>
								<house:xtdm id="isPrjSpc" dictCode="YESNO"
									value="${builder.IsPrjSpc }" onchange="changePrjSpc()"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row" id="leaderDiv">
							<li class="form-validate"><label >工程专盘队长</label>
								<input type="text" id="prjLeader" name="prjLeader"
								style="width:160px;" value="${builder.PrjLeader}" /></li>
						</div>
						<div class="validate-group row" >
							<li class="form-validate"><label>区域</label> <select
								id="regionCode" name="regionCode" onclick="changeRegion2()"></select>
							</li>

						</div>
						<div class="validate-group row" >
							<li class="form-validate"><label>二级区域</label> <select
								id="regionCode2" name="regionCode2" onchange="changeRegion2()"></select>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label >工程大区</label>
								<input type="text" id="prjRegion" name="prjRegion"
								style="width:160px;" readonly="readonly"/></li>
						</div>
						<div class="validate-group row" id="leaderDiv">
							<li><label>经纬度</label> <input type="text" id="tude"
								name="tude" style="width:160px;" value="${builder.tude}" />
							</li>
						</div>
					</ul>
				</div>
			</div>
		</div>
	</form>
</body>
</html>

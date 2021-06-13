<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<style type="text/css">
		.panel-info {
			margin-bottom: 10px;
		}
		.inside-search {
			padding-top: 25px;
			padding-left: 15px;
		}
		.btn-panel>.btn-group-sm {
			padding-top: 5px;
		}
		.linkFile {
		    position: relative;
		    display: inline-block;
		    border: 1px solid #333;
		    padding: 0px 0px;
		    margin-left:10px;
		    overflow: hidden;
		    text-decoration: none;	
		    text-indent: 0;
		    line-height: 18px;
		    border-radius: 1px;
		    color: #000;
		    font:10px;
		    background:#ccc; /* 一些不支持背景渐变的浏览器 */
		    background:-moz-linear-gradient(top, #fff, #ccc);
		    background:-webkit-gradient(linear, 0 0, 0 bottom, from(#fff), to(#ccc));
		    background:-o-linear-gradient(top, #fff, #ccc);
		}
		a:link{text-decoration:none;color:#000;}
		a:hover{text-decoration:none;color:#000;}
		a:active{text-decoration:none;color:#000;}
		.clearFile {
		    position: relative;
		    display: inline-block;
		    border: 1px solid #333;
		    padding: 0px 0px;
		    margin-top:5px;
		    overflow: hidden;
		    text-decoration: none;
		    text-indent: 0;
		    line-height: 18px;
		    border-radius: 1px;
		    color: #000;
		    font:10px;
		    background:#ccc; /* 一些不支持背景渐变的浏览器 */
		    background:-moz-linear-gradient(top, #fff, #ccc);
		    background:-webkit-gradient(linear, 0 0, 0 bottom, from(#fff), to(#ccc));
		    background:-o-linear-gradient(top, #fff, #ccc);
		}
		.linkFile input {
		    position: absolute;
		    font-size: 50px;
		    right: 0;
		    top: 0;
		    opacity: 0;
		    filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0);
		}
	</style>
	<script type="text/javascript" defer>
		var m_umState = "${supplier.m_umState}";
		$(function(){
			var gridOption = {
				url: "${ctx}/admin/supplier/goPrepayTranJqGrid",
				postData: {code:"${supplier.code}"},
				height: $(document).height()-$("#content-list").offset().top - 221,
				rowNum:100000,
				colModel: [
					{name:"pk",index:"pk",width:80,label:"pk",align:"left",hidden:true},
					{name:"date",index:"date",width:120,label:"变动日期",align:"left",formatter:formatTime},
					{name:"trsamount",index:"trsamount",width:80,label:"变动金额",align:"right"},
					{name:"aftamount",index:"aftamount",width:80,label:"变动后金额",align:"right"},
					{name:"prefixdesc",index:"prefixdesc",width:90,label:"单据类型",align:"left"},
					{name:"document",index:"document",width:80,label:"档案号",align:"left"},
					{name:"remarks",index:"remarks",width:220,label:"备注",align:"left"},
					{name:"splcode",index:"splcode",width:80,label:"splcode",align:"left",hidden:true},
					{name:"prefixcode",index:"prefixcode",width:80,label:"prefixcode",align:"left",hidden:true},
					{name:"lastupdate",index:"lastupdate",width:80,label:"lastupdate",align:"left",hidden:true},
					{name:"lastupdatedby",index:"lastupdatedby",width:80,label:"lastupdatedby",align:"left",hidden:true},
					{name:"expired",index:"expired",width:80,label:"expired",align:"left",hidden:true},
					{name:"actionlog",index:"actionlog",width:80,label:"actionlog",align:"left",hidden:true},
				],
			};

			if ("M" == m_umState || "V" == m_umState) $("#expired_show").parent().attr("hidden", false);
			// 循环输入option
			for (var i = 1; i <= 28; i++) {
				$("#specDay").prepend("<option value='"+i+"'>"+i+"</option>");
			}
			$("#isSpecDay option[value='']").remove();
			$("#isWeb option[value='']").remove();
			$("#isSpecDay").on("change",function() {
				var isSpecDay = $(this).val();
				$("#specDay").prop("disabled", true);	
				$("#specDay").val("");
				if ("1" == isSpecDay) {
					$("#specDay").prop("disabled", false);
					$("#specDay").val("1");
				}
			});
			$("#billCycle").on("blur",function() {
				var billCycle = $(this).val();
				if (isNaN(billCycle) || parseInt(billCycle) < 0) {
					art.dialog({
						content: "结算周期数据有错！应为正整数！请重新输入",
						width: 220,
						okValue: "确定",
						ok: function () {
							$("#billCycle").focus();
						},
					});
				}
				if ("" == billCycle) {
					$(this).val(0);
				}
			});
			
			$("#preOrderDay").on("blur",function() {
				var preOrderDay = $(this).val();
				if (isNaN(preOrderDay) || parseInt(preOrderDay) < 0) {
					art.dialog({
						content: "订货提前天数数据有错！应为正整数！请重新输入",
						width: 220,
						okValue: "确定",
						ok: function () {
							$("#preOrderDay").focus();
						},
					});
				}
				if ("" == preOrderDay) {
					$(this).val(0);
				}
			});
			
			Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
			if ("A" != m_umState) {
				$("#isSpecDay").val("${supplier.isSpecDay}");
				$("#specDay").val("${supplier.specDay}");
				$("#isWeb").val("${supplier.isWeb}");
				Global.LinkSelect.setSelect({
					firstSelect:"itemType1",
					firstValue:"${supplier.itemType1}",
				});
			} else {
				$("#isContainTax").val(0);
			}
			//触发isSpecDay的change行为
			$("#isSpecDay").triggerHandler("change");
			// 当不为查看时，移除预付金变动明细tab
			if ("V" == m_umState) {
				$("#saveBtn").remove();
				disabledForm("page_form_1");
				$("#cmpCode_NAME").removeProp("disabled");
				$("#supplFeeType_NAME").removeProp("disabled");
				Global.JqGrid.initJqGrid("dataTable", gridOption);
			} else {
				$("#preAmountChangeDetail").remove();
			};
			

			$("#page_form_1").bootstrapValidator({
				message: "请输入完整的信息",
				feedbackIcons: {
					validating: "glyphicon glyphicon-refresh"
				},
				fields: {
					descr:{  
						validators: {  
							notEmpty: {  
								message: "商家名称不能为空"  
							},
						}  
					},
					address:{  
						validators: {  
							notEmpty: {  
								message: "地址不能为空"  
							},
						}  
					},
					isSpecDay:{  
						validators: {  
							notEmpty: {  
								message: "固定结算周期不能为空"  
							},
						}  
					},
					contact:{  
						validators: {  
							notEmpty: {  
								message: "联系人不能为空"  
							},
						}  
					},
					itemType1:{  
						validators: {  
							notEmpty: {  
								message: "供应商类型不能为空"  
							},
						}  
					},
					isGroup:{  
						validators: {  
							notEmpty: {  
								message: "是否集团供应商不能为空"  
							},
						}  
					},
				},
				submitButtons: "input[type='submit']"
			});

		});

		function doSave() {
			if ("V" == m_umState) return;
			$("#page_form_1").bootstrapValidator("validate");
			if(!$("#page_form_1").data("bootstrapValidator").isValid()) return;
			if ("M" == m_umState) {
				var url = "${ctx}/admin/supplier/doUpdate";
			} else {
				var url = "${ctx}/admin/supplier/doSave";
			}
			Global.Form.submit("page_form_1", url, {m_umState:m_umState, code:$("#code").val()}, function(ret){
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 500,
						beforeunload: function () {
							closeWin();
						}
					});
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content: ret.msg,
						width: 200
					});
				}
			});
		}
		
		function uploadPic(o) {
		    var formData = new FormData();
		    formData.append('code', '${supplier.code}');
		    formData.append('file', o.files[0]);
		
		    $.ajax({
		        url: '${ctx}/admin/supplier/uploadPic',
		        data: formData,
		        cache: false,
		        type: 'POST',
		        contentType: false,
		        processData: false,
		        success: function(result) {
		            if (result.rs) {
		                $('#imgs').attr('src', result.datas.picUrl).attr('data-src', result.datas.picUrl);
		                $('#businessPhoto').val(result.datas.picFullName);
		            }
		        }
		    });
		}
		
		function removePic(){
		    $("#imgs").attr("src", "");
		    $("#businessPhoto").val("");
		    $("#a_imgs").attr("href", "");
		    
		    var file = document.getElementById("files");
		    file.outerHTML = file.outerHTML;
		}
	</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn" onclick="doSave();">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false);">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li class="form-validate">
							<label for="code"><span class="required">*</span>商家编号</label>
							<input type="text" id="code" name="code" style="width:160px;" value="${supplier.code}" 
								placeholder="保存自动生成" readonly/>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#mainItem" data-toggle="tab">主项目</a></li>  
			    <li id="preAmountChangeDetail"><a href="#preAmountChangeDetail_tab" data-toggle="tab">预付金变动明细</a></li>
			</ul>
		    <div class="tab-content">  
				<div id="mainItem"  class="tab-pane fade in active"> 
					<div class="pageContent">
	         			<form action="" method="post" id="page_form_1" class="form-search inside-search" >
	         				<input type="hidden" id="expired" name="expired" value="${supplier.expired}"/>
	         				<input type="hidden" id="businessPhoto" name="businessPhoto" value="${supplier.businessPhoto}"/>
							<ul class="ul-form">
								<div class="row">
									<div class="col-xs-6">
										<li class="form-validate">
											<label for="descr"><span class="required">*</span>商家名称</label>
											<input type="text" id="descr" name="descr" style="width:160px;" value="${supplier.descr}" />
										</li>
										<li class="form-validate">
											<label for="address"><span class="required">*</span>地址</label>
											<input type="text" id="address" name="address" style="width:160px;" value="${supplier.address}" />
										</li>
										<li class="form-validate">
											<label for="contact"><span class="required">*</span>联系人</label>
											<input type="text" id="contact" name="contact" style="width:160px;" value="${supplier.contact}" />
										</li>
										<li>
											<label for="isSpecDay"><span class="required">*</span>固定结算周期</label>
											<house:xtdm id="isSpecDay" dictCode="YESNO" value="0"></house:xtdm>
										</li>
										<li>
											<label for="specDay">结算日期</label>
											<select name="specDay" id="specDay"></select>
											<span>号</span>
										</li>
										<li>
											<label for="billCycle">结算周期</label>
											<input type="text" id="billCycle" name="billCycle" style="width:160px;"
												onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'');" value="0" 
												value="${supplier.billCycle}" />
											<span>天</span>
										</li>
										<li>
											<label for="phone1">电话1</label>
											<input type="text" id="phone1" name="phone1" style="width:160px;"
												onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^*\d]/g,'');"
												value="${supplier.phone1}" />
										</li>
										<li class="form-validate">
											<label for="itemType1"><span class="required">*</span>供应商类型</label>
											<select id="itemType1" name="itemType1"></select>
										</li>
										<li>
											<label for="phone2">电话2</label>
											<input type="text" id="phone2" name="phone2" style="width:160px;"
												onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^*\d]/g,'');"
												value="${supplier.phone2}" />
										</li>
										<li>
											<label for="isWeb">网淘供应商</label>
											<house:xtdm id="isWeb" dictCode="YESNO" value="0"></house:xtdm>
										</li>
										<li>
											<label for="purchCostModel">采购成本模式</label>
											<house:xtdm id="purchCostModel" dictCode="PURCHCOSTMODEL" value="${supplier.purchCostModel}"></house:xtdm>
										</li>
										<li>
											<label for="inOrderType">套内拆单模式</label>
											<house:xtdm id="inOrderType" dictCode="ORDERTYPE" value="${supplier.inOrderType}"></house:xtdm>
										</li>
										<li>
											<label for="isContainTax">含税价</label>
											<house:xtdm id="isContainTax" dictCode="YESNO" value="${supplier.isContainTax}"></house:xtdm>
										</li>
										<li>
											<label for="cmpCode">供应公司</label>
											<house:DictMulitSelect id="cmpCode" dictCode="" 
												sql="select Code,Desc2 from tCompany where Expired='F' order by Code " 
												sqlLableKey="Desc2" sqlValueKey="Code" selectedValue="${supplier.cmpCode}">
											</house:DictMulitSelect>
										</li>
										<li>
											<label for="outOrderType">套外拆单模式</label>
											<house:xtdm id="outOrderType" dictCode="ORDERTYPE" value="${supplier.outOrderType}"></house:xtdm>
										</li>
										<li class="form-validate">
											<label for="isGroup"><span class="required">*</span>是否集团供应商</label>
											<house:xtdm id="isGroup" dictCode="YESNO" value='${supplier.m_umState == "A" ? 1 : supplier.isGroup}'></house:xtdm>
										</li>
										<li class="form-validate">
											<label for="preOrderDay"><span class="required">*</span>订货提前天数</label>
											<input type="text" id="preOrderDay" name="preOrderDay" style="width:160px;" value="${supplier.preOrderDay > 0 ? supplier.preOrderDay:20}">
										</li>
										<li>
											<label for="sendMode">发货操作模式</label>
											<house:xtdm id="sendMode" dictCode="SUPPLSENDMODE" value="${supplier.sendMode}"></house:xtdm>
										</li>
									</div>
									<div style="width: 345px; height:200px;float: right; margin-right: 20px">
									  <!-- 包含隐藏加载题目和点击按钮，选中一张图片后触发onchange事件-->
										<div style="width: 60px; height: 200px;float: left;margin-top: 20px;">
											<p style="margin-left: 10px;">营业执照</p>
											<a href="javascript:;" class="linkFile">加载图片
											<input type="file" id="files" name="file" onchange="uploadPic(this)"/>
											</a>
											<button onclick="removePic()" class="clearFile">清除图片</button>
										</div>	
										<div style="float: right;margin-right: 70px;margin-top: 10px;">		  
											<img data-magnify="gallery" id="imgs" data-src="${picUrl }" src="${picUrl }" width="180px" height="180px" style="cursor:pointer">
		                                </div>							
									</div>
									<div class="col-xs-6">
										<li>
											<label>传真1</label>
											<input type="text" id="fax1" name="fax1" style="width:160px;"
												value="${supplier.fax1}">
										</li>
										<li>
											<label>传真2</label>
											<input type="text" id="fax2" name="fax2" style="width:160px;"
												value="${supplier.fax2}">
										</li>
										<li>
											<label for="mobile1">手机1</label>
											<input type="text" id="mobile1" name="mobile1" style="width:160px;"
												onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^*\d]/g,'');"
												value="${supplier.mobile1}" />
										</li>
										<li>
											<label for="mobile2">手机2</label>
											<input type="text" id="mobile2" name="mobile2" style="width:160px;"
												onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^*\d]/g,'');"
												value="${supplier.mobile2}" />
										</li>
										<li>
											<label for="supplFeeType">费用类型</label>
											<house:DictMulitSelect id="supplFeeType" dictCode="" 
												sql="select code, Descr from tSupplFeeType where expired <> 'T'" 
												sqlLableKey="Descr" sqlValueKey="code" selectedValue="${supplier.supplFeeType}">
											</house:DictMulitSelect>
										</li>
										<li>
											<label>邮件地址1</label>
											<input type="text" id="email1" name="email1" style="width:160px;"
												value="${supplier.email1}">
										</li>
										<li>
											<label>邮件地址2</label>
											<input type="text" id="email2" name="email2" style="width:160px;" value="${supplier.email2}">
										</li>
										<li>
											<label>账户名</label>
											<input type="text" id="actName" name="actName" style="width:160px;" value="${supplier.actName}">
										</li>
										<li>
											<label>卡号</label>
											<input type="text" id="cardID" name="cardID" style="width:160px;" value="${supplier.cardID}">
										</li>
										<li>
											<label>开户行</label>
											<input type="text" id="bank" name="bank" style="width:160px;" value="${supplier.bank}">
										</li>
										<li>
											<label for="department2">软装战队</label>
											<house:dict id="department2" dictCode="" 
												sql=" select rtrim(Code)+' '+Desc1 fd,Code from tDepartment2 where Department1='04' and Expired='F' "
												sqlLableKey="fd" sqlValueKey="Code" value="${supplier.department2}">
											</house:dict>
										</li>
										<li hidden="true">
											<label>过期</label>
											<input type="checkbox" id="expired_show" name="expired_show" value="${supplier.expired}" 
												onclick="checkExpired(this)" ${supplier.expired=="T"?"checked":""}/>
										</li>	
									</div>
								</div>
							</ul>
						</form>
					</div>
				</div> 
				<div id="preAmountChangeDetail_tab"  class="tab-pane fade"> 
					<div class="pageContent">
						<form action="" method="post" id="page_form_2" class="form-search" >
							<ul class="ul-form">
								<li>
									<label>预付金余额</label>
									<input type="text" id="prepayBalance" name="prepayBalance" style="width:160px;" value="${supplier.prepayBalance}"
										readonly>
								</li>
							</ul>
						</form>
						<div id="content-list">
							<table id="dataTable"></table>
						</div>
					</div>
				</div>
			</div>	
		</div>
	</div>
</body>	
</html>

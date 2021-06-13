<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>业绩明细--新增</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		select{
			width:150px !important
		}
	</style>
	<script type="text/javascript">
		/**初始化表格*/
		$(function(){
			$("#isModified,#dataType").attr("disabled",true);
  			//实时计算费用
			$(".calcfee").bind("input propertychange changeFee", function() {
				var tax=parseFloat(defautNumber($("#tax").val()));//税金
				var manageFee_Base=parseFloat(defautNumber($("#manageFee_Base").val()));//基础管理费
				var manageFee_Main=parseFloat(defautNumber($("#manageFee_Main").val()));//主材管理费
				var manageFee_Serv=parseFloat(defautNumber($("#manageFee_Serv").val()));//服务性管理费
				var manageFee_Soft=parseFloat(defautNumber($("#manageFee_Soft").val()));//软装管理费
				var manageFee_Int=parseFloat(defautNumber($("#manageFee_Int").val()));//集成管理费
				var manageFee_Cup=parseFloat(defautNumber($("#manageFee_Cup").val()));//橱柜管理费
				var manageFee_InSet=parseFloat(defautNumber($("#manageFee_InSet").val()));//套餐内管理费
				var manageFee_OutSet=manageFee_Base-manageFee_InSet;//套餐外管理费
				$("#manageFee_OutSet").val(manageFee_OutSet);
				var f1=accAdd(manageFee_Base,manageFee_Main);
				var f2=accAdd(f1,manageFee_Serv);
				var f3=accAdd(f2,manageFee_Soft);
				var f4=accAdd(f3,manageFee_Int);
				var manageFee_Sum=accAdd(f4,manageFee_Cup);//管理费合计
				$("#manageFee_Sum").val(manageFee_Sum);
				var longFee=parseFloat(defautNumber($("#longFee").val()));//远程费
				var basePlan=parseFloat(defautNumber($("#basePlan").val()));//基础预算
				var baseFee_Dirct=parseFloat(defautNumber($("#baseFee_Dirct").val()));//基础直接费
				var baseFee_Comp=parseFloat(defautNumber($("#baseFee_Comp").val()));//基础综合费
				var mainPlan=parseFloat(defautNumber($("#mainPlan").val()));//主材预算
				var mainServPlan=parseFloat(defautNumber($("#mainServPlan").val()));//服务性预算
				var softPlan=parseFloat(defautNumber($("#softPlan").val()));//软装预算
				var softFee_Furniture=parseFloat(defautNumber($("#softFee_Furniture").val()));//软装家具费
				var integratePlan=parseFloat(defautNumber($("#integratePlan").val()));//集成预算
				var cupboardPlan=parseFloat(defautNumber($("#cupboardPlan").val()));//橱柜预算
				var baseDisc=parseFloat(defautNumber($("#baseDisc").val()));//协议优惠额
				var contractFee=manageFee_Sum+basePlan+mainPlan+mainServPlan+softPlan+integratePlan+cupboardPlan-baseDisc;//合同总价   +tax
				$("#contractFee").val(contractFee);
				var designFee=parseFloat(defautNumber($("#designFee").val()));//设计费
				$("#contractAndDesignFee").val(contractFee+designFee);//合同总价+设计费
				//实际材料业绩=主材预算+集成预算+橱柜预算+（软装预算-软装家具费）+软装家具费/2+服务性产品预算/3
				var realMaterPerf=Math.round((mainPlan+integratePlan+cupboardPlan+(softPlan-softFee_Furniture)+softFee_Furniture*0.5+mainServPlan/3)*10000)/10000;//实际材料业绩
				$("#realMaterPerf").val(realMaterPerf);
				var maxMaterPerfPer=parseFloat(defautNumber($("#maxMaterPerfPer").val()));
				var maxMaterPerf=parseFloat(defautNumber($("#maxMaterPerf").val()));//封顶材料业绩
				var alreadyMaterPerf=parseFloat(defautNumber($("#alreadyMaterPerf").val()));//已算材料业绩
				var materPerf=parseFloat(defautNumber($("#materPerf").val()));//材料业绩
				var regRealMaterPerf=parseFloat(defautNumber($("#regRealMaterPerf").val()));//原业绩实际材料业绩
				var sumChgRealMaterPerf=parseFloat(defautNumber($("#sumChgRealMaterPerf").val()));//此客户增减实际业绩汇总
				var payType=$("#payType").val();//付款类型
				var type=$("#type").val();//业绩类型
				var regAchieveDate=formatDate(parseFloat($("#regAchieveDate").val()));//原业绩达标时间
				var calCheckPerfDate="2018-06-01";//推行材料业绩上限为835元/平米的时间
				var isCalcBaseDisc=$("#isCalcBaseDisc").val();//是否扣基础协议优惠
				var marketFund=parseFloat($("#marketFund").val());//营销基金
				var perfAmount=parseFloat($("#perfAmount").val());//签单业绩
				var basePerfPer=parseFloat($("#basePerfPer").val());//基础业绩比例
				var realPerfAmount=parseFloat($("#realPerfAmount").val());//实际签单业绩
				var tileDeduction=parseFloat($("#tileDeduction").val());//瓷砖扣减业绩
				var bathDeduction=parseFloat($("#bathDeduction").val());//卫浴扣减业绩
				var mainDeduction=tileDeduction+bathDeduction;//主材扣减业绩
				var softTokenAmount=parseFloat(defautNumber($("#softTokenAmount").val()));//软装券金额
				var baseDeduction=parseFloat(defautNumber($("#baseDeduction").val()));//基础单项扣减
				var itemDeduction=parseFloat(defautNumber($("#itemDeduction").val()));//材料单品扣减
				var recalPerf=parseFloat($("#recalPerf").val());//实际业绩
				var perfDisc=parseFloat(defautNumber($("#perfDisc").val()));//业绩折扣金额
				var gift=parseFloat(defautNumber($("#gift").val()));//实物赠送
				var perfExpr=$("#perfExpr").val();//业绩公式
				var perfMarkup=$("#perfMarkup").val();//业绩折扣
				var hasPerfMarkup=false;//公式中是否计算业绩折扣
				var befMarkupPerf;//折扣前金额
				var basePersonalPlan=parseFloat(defautNumber($("#basePersonalPlan").val()));//基础个性化预算
				var manageFee_basePersonal=parseFloat(defautNumber($("#manageFee_basePersonal").val()));//基础个性化管理费
				var manageFee_Base=parseFloat(defautNumber($("#manageFee_Base").val()));//基础管理费
				$("#mainDeduction").val(mainDeduction);
				if(payType=="2" && maxMaterPerfPer>0 && regAchieveDate>=calCheckPerfDate){//福州工程款零首付家装客户才有封顶材料业绩、已算材料业绩
					if(type=="1"){//正常业绩，材料业绩=min(实际材料业绩，封顶材料业绩)
						materPerf=realMaterPerf<=maxMaterPerf?realMaterPerf:maxMaterPerf;	
					}else if(type=="3"){//增减业绩，增项不增业绩，减项有可能减业绩，材料业绩=min(实际材料业绩汇总-已算材料业绩,0)
						if(realMaterPerf<0){
							if(regRealMaterPerf+sumChgRealMaterPerf+realMaterPerf-alreadyMaterPerf<0){
								materPerf=regRealMaterPerf+sumChgRealMaterPerf+realMaterPerf-alreadyMaterPerf;
							}else{
								materPerf=0;
							}
						}else{
							materPerf=0;
						}
					}else if(type=="6"){
						materPerf=realMaterPerf-alreadyMaterPerf;
					}
				}else{
					materPerf=realMaterPerf;
				}
				$("#materPerf").val(materPerf);
				if(type=="1" && isCalcBaseDisc=="0"){//正常业绩，签单业绩需要判断是否扣基础协议优惠
					perfAmount=Math.round((designFee+(basePlan-longFee-marketFund)*basePerfPer+materPerf)*100)/100;
				}else{
					perfAmount=Math.round((designFee+(basePlan-longFee-baseDisc-marketFund)*basePerfPer+materPerf)*100)/100;
				}
				realPerfAmount=Math.round((perfAmount-baseDeduction-itemDeduction-softTokenAmount)*100)/100;
				recalPerf=Math.round((perfAmount-mainDeduction-perfDisc-softTokenAmount-baseDeduction-itemDeduction)*100)/100;
				//2018年10月份开始，实际业绩的计算公式配置在客户类型表中
				if(perfExpr!=""){
					if(perfExpr.indexOf("@PerfMarkup@")!=-1){
						hasPerfMarkup=true;
					}
					perfExpr=perfExpr.replace(/@ContractFee@/g,contractFee);
					perfExpr=perfExpr.replace(/@BasePlan@/g,basePlan);
					perfExpr=perfExpr.replace(/@ManageFee@/g,manageFee_Sum);
					perfExpr=perfExpr.replace(/@SoftFee_Furniture@/g,softFee_Furniture);
					perfExpr=perfExpr.replace(/@MainServPlan@/g,mainServPlan);
					perfExpr=perfExpr.replace(/@OtherDisc@/g,gift+softTokenAmount);
					perfExpr=perfExpr.replace(/@BaseDeduction@/g,baseDeduction);
					perfExpr=perfExpr.replace(/@ItemDeduction@/g,itemDeduction);
					perfExpr=perfExpr.replace(/@LongFee@/g,longFee);
					perfExpr=perfExpr.replace(/@PerfMarkup@/g,perfMarkup);
					perfExpr=perfExpr.replace(/@BaseFee_Dirct@/g,baseFee_Dirct);
					perfExpr=perfExpr.replace(/@BaseFee_Comp@/g,baseFee_Comp);
					perfExpr=perfExpr.replace(/@Tax@/g,tax);
					perfExpr=perfExpr.replace(/@SoftPlan@/g,softPlan);
					perfExpr=perfExpr.replace(/@IntegratePlan@/g,integratePlan);
					perfExpr=perfExpr.replace(/@CupboardPlan@/g,cupboardPlan);
					perfExpr=perfExpr.replace(/@BasePersonalPlan@/g,basePersonalPlan);
					perfExpr=perfExpr.replace(/@ManageFee_BasePersonal@/g,manageFee_basePersonal);
					perfExpr=perfExpr.replace(/@ManageFee_Base@/g,manageFee_Base);
					perfExpr=perfExpr.replace(/@ManageFee_Serv@/g,manageFee_Serv);
					perfExpr=perfExpr.replace(/@BaseDisc@/g,baseDisc);
					realPerfAmount=getExp(perfExpr);//业绩公式计算结果
					recalPerf=realPerfAmount;
					perfAmount=recalPerf+mainDeduction+perfDisc+softTokenAmount+baseDeduction+itemDeduction;
					befMarkupPerf=perfMarkup!=0 && perfMarkup!=""?recalPerf/perfMarkup:recalPerf;//业绩折扣不为0或者空时，折扣前业绩=实际业绩/业绩折扣
				}
				$("#perfAmount").val(perfAmount);
				$("#realPerfAmount").val(realPerfAmount);
				$("#recalPerf").val(recalPerf);
				$("#befMarkupPerf").val(hasPerfMarkup==true?befMarkupPerf:recalPerf);//公式中存在业绩折扣才计算折扣前业绩，否则折扣前业绩=实际业绩
			});
			changePerfType();//初始化业绩类型
			changeIsModified();//初始化是否人工修改 
		});
		//页面切换
		function nextPage(){
			var btnText=$("#nextPageBut").text();
			if(btnText=="▶"){
				$("#nextPageBut").text("◀");
				$("#nextPageBut").attr("title","主项目");
			}else{
				$("#nextPageBut").text("▶");
				$("#nextPageBut").attr("title","其他项目");
			}
		    if($("#a_mainInfo").parent().attr("class").indexOf("active")!=-1){
		    	$("#otherInfo").css({
		    		"height":$("#itemAppFromDiv").height()-23,
		    		"display":"block"
		    	});
		    	$("#mainInfo").css({
		    		"display":"none"
		    	});
		    	$("#a_otherInfo").tab("show");
		    }else{
		    	$("#a_mainInfo").tab("show");
		    	$("#otherInfo").css({
		    		"display":"none"
		    	});
		    	$("#mainInfo").css({
		    		"display":"block"
		    	});
		    }
	    }
		//提示函数
		function alertFun(){
			art.dialog({    	
				content: "已有相关干系人或增减单，不允许修改客户编号！"
			});
		}
		//修改业绩类型
		function changePerfType(){
			var calcType=$("#calcType").text();
			var type=$("#type").val();
			if(type=="3"){
				$("#isCalPkPerf").attr("disabled",true);
				$("#quantity").val("0");
				$("#tabJczj,#tabClzj,#tabHtfyzj").css("display","block");
				initCustomer("4,5","1,2","");
				$("#isModified").val("0").trigger("onchange");
				$("#calcType").removeAttr("disabled");
				$("#isAddAllInfo").removeAttr("disabled");
			}else{
				if(type=="4"){
					initCustomer("5","1,2,3,4","4");
				}else if(type=="5"){
					initCustomer("3,4,5","1,2","");
				}else if(type=="2"){
					initCustomer("5","1,2,3,4","1");
				}
				$("#isCalPkPerf").removeAttr("disabled");
				$("#quantity").val("-1");
				$("#tabJczj,#tabClzj,#tabHtfyzj").css("display","none");
				$("#isModified").val("1").trigger("onchange");
				$("#calcType").text(calcType).attr("disabled",true);
				$("#isAddAllInfo").val("1");
				$("#isAddAllInfo").attr("disabled",true);
			}
		}
		//改变是否人工修改触发
		function changeIsModified(){
			if($("#isModified").val()=="0"){
				$("#calcType").text("人工修改");
				$(".init").attr("readonly",true);
				$("#signDate,#setDate").attr("disabled",true);
				$("#calcType").unbind("click").on("click",function(){
					art.dialog({
						content:"设置该业绩明细为人工修改之后，业绩明细相关字段可修改，是否确认设置？",
						lock: true,
						ok: function () {
							$("#isModified").val("1");
							changeIsModified();
						},
						cancel: function () {
							return true;
						}
					}); 
				});
			}else{
				$("#calcType").text("自动计算");
				$(".init").removeAttr("readonly");
				$("#signDate,#setDate").removeAttr("disabled");
				$("#documentNo").removeAttr("readonly");
				$("#calcType").unbind("click").on("click",function(){
					art.dialog({
						content:"设置该业绩明细为自动计算之后，业绩明细相关字段不可修改，是否确认设置？",
						lock: true,
						ok: function () {
							$("#isModified").val("0");
							changeIsModified();
						},
						cancel: function () {
							return true;
						}
					}); 
				});
			}
		}
		//改变独立销售触发
		function changeIsAddAllInfo(){
			if($("#isModified").val()=="1"){
			   return;
			}
			if($("#isAddAllInfo").val()=="0"){
				$("#gxrDataTable").jqGrid("clearGridData");
				$("#gxrxglsDataTable").jqGrid("clearGridData");
			}else{
				doQuery('gxrDataTable');
			    doQuery('gxrxglsDataTable');
			}	
		}
		//初始化客户
		function initCustomer(defautValue,unShowValue,endCode){
			$("#custCode").openComponent_customer({
				showValue:"${perfCycle.custCode}",
				showLabel:"${perfCycle.custDescr}",
				callBack:function(data){
					getInfoByCustCode(data.code);
					$("#custDescr").val(data.descr);
					$("#address").val(data.address);
					$("#regPerfPk").val(data.perfpk);
					$("#isInitSign").val(data.isinitsign);
					$("#documentNo").val(data.documentno);
					$("#payeeCode").val(data.custpayeecode.trim());
					if($.trim($("#payeeCode").val()) != ""){
						$("#payeeCode").attr("disabled","true");
					}
					if($("#isAddAllInfo").val()!="0"){
						doQuery('gxrDataTable');
						doQuery('gxrxglsDataTable');
					}
					doQuery('fkxxDataTable');
				},
				condition:{status:defautValue,laborFeeCustStatus:unShowValue,mobileHidden:"1",endCode:endCode}
			});
		}
		//客户号查数据
		function getInfoByCustCode(custCode){
			var pk="${perfCycle.pk}";
			var type=$("#type").val();
			$.ajax({
				url:"${ctx}/admin/perfCycle/getInfoByCustCode?custCode="+custCode+"&pk="+pk,
				type:'post',
				data:{},
				dataType:'json',
				cache:false,
				async:false,
				error:function(obj){
					
				},
				success:function(obj){
					if (obj){
						$("#area").val(obj.Area);
						$("#setDate").val(formatDate(parseFloat(obj.SetDate)));
						$("#signDate").val(formatDate(parseFloat(obj.SignDate)));
						$("#alreadyMaterPerf").val(obj.AlreadyMaterPerf);
						$("#payType").val(obj.PayType);
						$("#regAchieveDate").val(obj.RegAchieveDate);
						$("#maxMaterPerfPer").val(obj.MaxMaterPerfPer);
						$("#basePerfPer").val(obj.BasePerfPer);
						$("#isCalcBaseDisc").val(obj.IsCalcBaseDisc);
						$("#regRealMaterPerf").val(obj.RegRealMaterPerf);
						$("#perfMarkup").val(obj.PerfMarkup);
						$("#baseFee_Dirct").val(obj.BaseFee_Dirct);
						$("#baseFee_Comp").val(obj.baseFee_Comp);
						//增减业绩用增减业绩公式计算，税金取合同增减管理
						if(type!="3"){
							$("#tax").val(obj.Tax).trigger("changeFee");
							$("#perfExpr").val(obj.PerfExpr);
							$("#perfExprRemarks").val(obj.PerfExprRemarks);
						}else{
							$("#perfExpr").val(obj.ChgPerfExpr);
							$("#perfExprRemarks").val(obj.ChgPerfExprRemarks);
						}
						$("#sumChgRealMaterPerf").val(obj.SumChgRealMaterPerf).trigger("changeFee");
					}
				}
			});
		}
		//跳转到导入原业绩页面
		function importOldPerf(){
			var custCode=$("#custCode").val();
			if(custCode==""){
				art.dialog({
					content: "请先选择客户编号！",
				});
				return;
			}
			Global.Dialog.showDialog("goRegPerformance",{
				title:"业绩明细--导入原业绩",
				url:"${ctx}/admin/perfCycle/goRegPerformance?custCode="+custCode,
				height:650,
				width:1300,
				returnFun:function(data){
					$.ajax({
						url:"${ctx}/admin/perfCycle/getRegImport",
						type:'post',
						data:{pks:data,custCode:custCode},
						cache:false,
						async:false,
						error:function(obj){
							art.dialog({
								content: "数据错误！",
							});
						},
						success:function(obj){
							if (obj.length>0){
								var type=$("#type").val();
								var map=obj[0];
								$("#baseDeduction").val(map.BaseDeduction);
								$("#baseDisc").val(map.BaseDisc);
								$("#basePlan").val(map.BasePlan);
								$("#contractFee").val(map.ContractFee);
								$("#cupboardPlan").val(map.CupboardPlan);
								$("#designFee").val(map.DesignFee);
								$("#gift").val(map.Gift);
								$("#integratePlan").val(map.IntegratePlan);
								$("#longFee").val(map.LongFee);
								$("#mainPlan").val(map.MainPlan);
								$("#mainServPlan").val(map.MainServPlan);
								$("#manageFee_Sum").val(map.ManageFee);
								$("#manageFee_Cup").val(map.ManageFee_Cup);
								$("#manageFee_InSet").val(map.ManageFee_InSet);
								$("#manageFee_Int").val(map.ManageFee_Int);
								$("#manageFee_Main").val(map.ManageFee_Main);
								$("#manageFee_Serv").val(map.ManageFee_Serv);
								$("#manageFee_Soft").val(map.ManageFee_Soft);
								$("#perfAmount").val(map.PerfAmount);
								$("#softFee_Furniture").val(map.SoftFee_Furniture);
								$("#softPlan").val(map.SoftPlan);
								$("#softTokenAmount").val(map.SoftTokenAmount);
								$("#documentNo").val(map.PerfDocumentNo);
								$("#setAdd").val(map.SetAdd);
								$("#area").val(map.Area);
								// if($.trim(type)!="3"){  //退单扣业绩和重签扣业绩取原正常业绩的主材毛利率
									$("#mainProPer").val(map.MainProper);//2020年5月22日  modify by xzy增减业绩主材毛利率生成时取原正常业绩的主材毛利率
								// }
								$("#manageFee_Base").val(map.ManageFee_Base).trigger("changeFee");
								$("#remarks").val("停工扣业绩！"+$("#remarks").val());
							}
						}
					});
				}
			});
		}
		//计算基础增减金额
		function calcBaseItemChg(){
	 	 	var basePlan=$("#jczjDataTable").getCol('befamount',false,'sum');
	 	 	$("#basePlan").val(basePlan).trigger("changeFee"); 
	 	 	$("#setAdd").val(basePlan).trigger("changeFee");  // 增减套外基础增项同基础预算 by zjf20201221
		}
		//计算材料增减金额
		function calcItemChg(){
	 	 	var mainPlan=0,mainServPlan=0,softPlan=0,cupboardPlan=0,integratePlan=0;
	 	 	var ids=$("#clzjDataTable").jqGrid("getDataIDs");
	 	 	$.each(ids,function(i,id){
				var rowData = $("#clzjDataTable").getRowData(id);
				if(rowData.itemtype1.trim()=="ZC" && rowData.isservice=="0"){
					if(rowData.befamount<0){
						mainPlan+=parseFloat(rowData.befamount)+parseFloat(rowData.disccost);
					}else{
						mainPlan+=rowData.befamount-rowData.disccost;
					}
				}else if(rowData.itemtype1.trim()=="ZC" && rowData.isservice=="1"){
					if(rowData.befamount<0){
						mainServPlan+=parseFloat(rowData.befamount)+parseFloat(rowData.disccost);
					}else{
						mainServPlan+=rowData.befamount-rowData.disccost;
					}
				}else if(rowData.itemtype1.trim()=="RZ"){
					if(rowData.befamount<0){
						softPlan+=parseFloat(rowData.befamount)+parseFloat(rowData.disccost);
					}else{
						softPlan+=rowData.befamount-rowData.disccost;
					}
				}else if(rowData.itemtype1.trim()=="JC" && rowData.iscupboard=="0"){
					if(rowData.befamount<0){
						integratePlan+=parseFloat(rowData.befamount)+parseFloat(rowData.disccost);
					}else{
						integratePlan+=rowData.befamount-rowData.disccost;
					}
				}else if(rowData.itemtype1.trim()=="JC" && rowData.iscupboard=="1"){
					if(rowData.befamount<0){
						cupboardPlan+=parseFloat(rowData.befamount)+parseFloat(rowData.disccost);
					}else{
						cupboardPlan+=rowData.befamount-rowData.disccost;
					}
				}
			});
	 	 	$("#mainPlan").val(mainPlan);
	 	 	$("#mainServPlan").val(mainServPlan); 
	 	 	$("#softPlan").val(softPlan); 
	 	 	$("#integratePlan").val(integratePlan); 
	 	 	$("#cupboardPlan").val(cupboardPlan).trigger("changeFee");;   
		}
		//计算管理费
		function calcConFeeChg(){
	 	 	var designFee=0,manageFee_Base=0,manageFee_Main=0,manageFee_Serv=0,manageFee_Soft=0,manageFee_Int=0,manageFee_Cup=0,tax=0;
	 	 	var ids=$("#htfyzjDataTable").jqGrid("getDataIDs");
	 	 	$.each(ids,function(i,id){
				var rowData = $("#htfyzjDataTable").getRowData(id);
				if(rowData.chgtype=="1"){
					designFee+=parseFloat(rowData.chgamount);
				}else if(rowData.chgtype=="2" && rowData.itemtype1.trim()=="JZ"){
					manageFee_Base+=parseFloat(rowData.chgamount);
				}else if(rowData.chgtype=="2" && rowData.itemtype1.trim()=="ZC" && rowData.isservice=="0"){
					manageFee_Main+=parseFloat(rowData.chgamount);
				}else if(rowData.chgtype=="2" && rowData.itemtype1.trim()=="ZC" && rowData.isservice=="1"){
					manageFee_Serv+=parseFloat(rowData.chgamount);
				}else if(rowData.chgtype=="2" && rowData.itemtype1.trim()=="RZ"){
					manageFee_Soft+=parseFloat(rowData.chgamount);
				}else if(rowData.chgtype=="2" && rowData.itemtype1.trim()=="JC" && rowData.iscupboard=="0"){
					manageFee_Int+=parseFloat(rowData.chgamount);
				}else if(rowData.chgtype=="2" && rowData.itemtype1.trim()=="JC" && rowData.iscupboard=="1"){
					manageFee_Cup+=parseFloat(rowData.chgamount);
				}else if(rowData.chgtype=="4"){
					tax+=parseFloat(rowData.chgamount);
				}
			});
	 	 	$("#designFee").val(designFee);
	 	 	$("#manageFee_Main").val(manageFee_Main); 
	 	 	$("#manageFee_Serv").val(manageFee_Serv); 
	 	 	$("#manageFee_Soft").val(manageFee_Soft);   
	 	 	$("#manageFee_Int").val(manageFee_Int); 
	 	 	$("#manageFee_Cup").val(manageFee_Cup);
	 	 	$("#tax").val(tax);
	 	 	$("#manageFee_Base").val(manageFee_Base).trigger("changeFee"); 
		}
		//计算增减单的优惠
		function calcChgDisc(){
			var jcIds=$("#jczjDataTable").jqGrid("getDataIDs");
			var clIds=$("#clzjDataTable").jqGrid("getDataIDs");
			var baseChgDisc=0,matChgDisc=0,baseDisc=0;
	 	 	$.each(jcIds,function(i,id){
				var rowData = $("#jczjDataTable").getRowData(id);
				if(rowData.befamount<0){
					baseChgDisc+=parseFloat(rowData.discamount)*-1;
				}else{
					baseChgDisc+=parseFloat(rowData.discamount);
				}
			});
			$.each(clIds,function(i,id){
				var rowData = $("#clzjDataTable").getRowData(id);
				if(rowData.befamount<0){
					matChgDisc+=(rowData.discamount-rowData.disccost)*-1;
				}else{
					matChgDisc+=rowData.discamount-rowData.disccost;
				}
			});
			baseDisc=baseChgDisc+matChgDisc;
			$("#baseDisc").val(baseDisc).trigger("changeFee");
		}
		//计算增减生成的基础单项扣减、材料单品扣减
		function calcChgDeduction(){
			var baseChgNos=$("#jczjDataTable").getCol("no", false).join(",");
			var itemChgNos=$("#clzjDataTable").getCol("no", false).join(",");
			$.ajax({
				url:"${ctx}/admin/perfCycle/calcChgDeduction",
				type: "post",
				data: {baseChgNos:baseChgNos,itemChgNos:itemChgNos},
				dataType: "json",
				cache: false,
				async:false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
				},
				success: function(obj){
					$("#baseDeduction").val(obj.BaseDeduction);
					$("#itemDeduction").val(obj.ItemDeduction).trigger("changeFee");
				}
			});
		}
		//设置业绩类型不可编辑（新增时才判断）
		function perfTypeDisabled(){
			var type=$("#type").val();
			if(type=="3"){
				var isDisabled=$("#type").prop("disabled");
				if(!isDisabled){//业绩类型原来为可修改，新增明细时改为不可修改
					$("#type").attr("disabled",true);
				}
			}
		}
		//设置业绩类型可编辑（新增时才判断）
		function perfTypeEnable(){
			var type=$("#type").val();
			if(type=="3"){
				var htfyzjRecord=$("#htfyzjDataTable").jqGrid("getGridParam", "records");
				var jczjRecord=$("#jczjDataTable").jqGrid("getGridParam", "records");
				var clzjRecord=$("#clzjDataTable").jqGrid("getGridParam", "records");
				if(htfyzjRecord==0 && jczjRecord==0 && clzjRecord==0){//三个增减表格都为空时可修改业绩类型
					$("#type").removeAttr("disabled",true);
					$("#isAddAllInfo").removeAttr("disabled",true);
				}
			}
		}
		//按业绩公式计算
		function getExp(perfExpr){
			var recalPerf=0;
			$.ajax({
				url:"${ctx}/admin/perfCycle/getExp",
				type:'post',
				data:{perfExpr:perfExpr},
				dataType:'json',
				cache:false,
				async:false,
				error:function(obj){
					
				},
				success:function(obj){
					if (obj.length>0){
						recalPerf=parseFloat(obj[0].RecalPerf);
					}
				}
			});
			return recalPerf;
		}
		//查询，设置loadonce要加datatype:"json"
		function doQuery(tableId){
			$("#"+tableId).jqGrid(
				"setGridParam",{
					datatype:"json",
					postData:$("#page_form").jsonForm(),
					page:1,
				}
			).trigger("reloadGrid"); 
		}
		//查看原业绩
		function viewOldPerf(){
			var regPerfPk=$("#regPerfPk").val();
			if(regPerfPk==""){
				art.dialog({
					content: "没有原业绩！"
				});
				return;
			}
			Global.Dialog.showDialog("ViewOldPerf0",{
				title:"业绩明细--查看原业绩",
				url:"${ctx}/admin/perfCycle/goViewOldPerf?regPerfPk="+$("#regPerfPk").val()+"&openCount=1&m_umState=R",
				height:750,
				width:1450
			});
		}
		//重写allToJson方法，自定义表格名
		function allToJson(tableId,tableName){
			var json = {};
			var rows = $("#"+tableId).jqGrid("getRowData");
			json[tableName] = JSON.stringify(rows);
			return json;
		}
		//保存
		function save(){
			var achieveDate=$("#achieveDate").val();
			var signDate=$("#signDate").val();
			var setDate=$("#setDate").val();
			var type=$("#type").val();
			if(achieveDate==""){
				art.dialog({
					content: "请选择达标时间！",
				});
				return;
			}
			if(type!="2" && signDate==""){
				art.dialog({
					content: "请选择签订时间！",
				});
				return;
			}
			if(setDate==""){
				art.dialog({
					content: "请选择下定时间！",
				});
				return;
			}
			if($("#isAddAllInfo").val()==""){
				art.dialog({
					content: "常规变更不能为空！",
				});
				return;
			}
			$(".init").each(function(){
				if($(this).val()=="" && $(this).prev().text()!="备注"){
					art.dialog({
						content: "请填写"+$(this).prev().text()+"！",
					});
					return false;
				}
			});
			
			var resultStr = checkPerfPer();
			if(resultStr != ""){
				art.dialog({
					content: resultStr,
				});
				return;
			}
			
			var datas = $("#page_form").jsonForm();//form表单
			var gxrDetailJson=allToJson('gxrDataTable','gxrDetailJson');//干系人表格数据
			var jczjDetailJson=allToJson('jczjDataTable','jczjDetailJson');//基础增减表格数据
			var clzjDetailJson=allToJson('clzjDataTable','clzjDetailJson');//材料增减表格数据
			var htfyzjDetailJson=allToJson('htfyzjDataTable','htfyzjDetailJson');//合同费用增减表格数据
			$.extend(datas,gxrDetailJson);//合并json
			$.extend(datas,jczjDetailJson);
			$.extend(datas,clzjDetailJson);
			$.extend(datas,htfyzjDetailJson);
			$.ajax({
				url:"${ctx}/admin/perfCycle/doCountAdd",
				type: "post",
				data: datas,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
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
		//为空时默认为0来算
		function defautNumber(value){
			if(value==""){
				return 0;
			}
			return value;
		}
		//加法计算  解决js小数加法精度问题，防止出现一大串小数
		function accAdd(arg1,arg2){  
			var r1,r2,m;  
			try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;}
			try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;}
			m=Math.pow(10,Math.max(r1,r2));  
			return (arg1*m+arg2*m)/m;  
		}
		// 根据基础增减获取套外基础增项
		/*
		function getBaseChgSetAdd(){
			if($("#type").val() != "3") return;
			var jczjRecord = $("#jczjDataTable").jqGrid("getGridParam", "records");
			if(jczjRecord < 1){
				// $("#setAdd").val("0"); 增减套外基础增项同基础预算 by zjf20201221
				$("#setMinus").val("0");
				return;
			}
			var baseChgNos = $("#jczjDataTable").getCol("no", false).join(",");
			$.ajax({
				url:"${ctx}/admin/perfCycle/getBaseChgSetAdd",
				type: "post",
				data: {baseChgNos:baseChgNos},
				dataType: "json",
				cache: false,
				async:false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
				},
				success: function(obj){
					if(obj && obj.length>0){
						// $("#setAdd").val(obj[0].SetAdd);
						$("#setMinus").val(obj[0].SetMinus);
					}else{
						// $("#setAdd").val(0);
						$("#setMinus").val(0);
					}
				}
			});
		}
		*/
		// 根据基础增减获取套外基础增项
		function getChgMainProPer(){
			if($("#type").val() != "3") return;
			var zjRecord = $("#clzjDataTable").jqGrid("getGridParam", "records");
			if(zjRecord < 1){
				$("#mainProper").val("0");
				return;
			}
			var chgNos = $("#clzjDataTable").getCol("no", false).join(",");
			$.ajax({
				url:"${ctx}/admin/perfCycle/getChgMainProPer",
				type: "post",
				data: {chgNos:chgNos},
				dataType: "json",
				cache: false,
				async:false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
				},
				success: function(obj){
					if(obj && obj.length>0){
						$("#mainProper").val(obj[0].MainProPer);
					}else{
						$("#mainProper").val(0);
					}
				}
			});
		}
		
		// 根据基础增减获取基础个性化预算、基础个性化管理费
		function getBasePersonalPlan(){
			if($("#type").val() != "3") return;
			var jczjRecord = $("#jczjDataTable").jqGrid("getGridParam", "records");
			if(jczjRecord < 1){
				$("#basePersonalPlan").val("0").trigger("changeFee");
				$("#manageFee_basePersonal").val("0").trigger("changeFee");
				$("#woodPlan").val(0);
				$("#manageFee_wood").val(0);
				return;
			}
			var baseChgNos = $("#jczjDataTable").getCol("no", false).join(",");
			$.ajax({
				url:"${ctx}/admin/perfCycle/getBasePersonalPlan",
				type: "post",
				data: {baseChgNos:baseChgNos},
				dataType: "json",
				cache: false,
				async:false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
				},
				success: function(obj){
					if(obj && obj.length>0){
						$("#basePersonalPlan").val(obj[0].basePersonalPlan);
						$("#manageFee_basePersonal").val(obj[0].manageFee_basePersonal).trigger("changeFee");
						$("#woodPlan").val(obj[0].woodPlan);
						$("#manageFee_wood").val(obj[0].manageFee_wood);
					}else{
						$("#basePersonalPlan").val(0).trigger("changeFee");
						$("#manageFee_basePersonal").val(0).trigger("changeFee");
						$("#woodPlan").val(0);
						$("#manageFee_wood").val(0);
					}
				}
			});
		}
		
		//检查同一个角色的业绩和是否小于等于1
		function checkPerfPer(){
			var rows = $("#gxrDataTable").jqGrid("getRowData");//资源数组
			var json ={};
			for(var i=0;i<rows.length;i++){
				var row = rows[i];
				if(json.hasOwnProperty(row.roledescr)){
					json[row.roledescr] = parseFloat(json[row.roledescr]) + parseFloat(row.perfper);
				}else{
					json[row.roledescr] = row.perfper;
				}
			}
			
			for(var roledescr in json){
				if(json[roledescr] > 1){
					return "【"+roledescr+"】的业绩占比之和【"+json[roledescr]+"】大于1，不允许保存！";
				}
			}
			
			return "";
		}
	</script>
	<style>
    .col-xs-1 {
    	padding: 10px;
    	width: 282px;
    	height:150px;
    }
	</style>
	</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
					<button type="button" class="btn btn-system" onclick="save()">保存</button>
					<button type="button" class="btn btn-system" id="calcType">人工修改</button>
					<button type="button" class="btn btn-system"
						onclick="importOldPerf()">导入原业绩</button>
					<button type="button" class="btn btn-system"
						onclick="viewOldPerf()">查看原业绩</button>
					<button id="closeBut" type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
					<button id="nextPageBut" type="button" class="btn btn-system"
						onclick="nextPage()" title="其他项目">▶</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info"
			style="width:1420px;height:400px;position:absolute;padding:0px">
			<form action="" method="post" id="page_form" class="form-search">
				<input style="width:150px" type="hidden" name="jsonString" value="" /><input style="width:150px"
					type="hidden" name="isExitTip" id="isExitTip" value="0" /> <input style="width:150px"
					type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input style="width:150px" type="hidden" name="perfCycleNo" id="perfCycleNo" value="${perfCycle.perfCycleNo}"/>
					<input style="width:150px" type="hidden" name="regPerfPk" id="regPerfPk"/>
					<input style="width:150px" type="hidden" name="pk" id="pk" />
					<input style="width:150px" type="hidden" name="payType" id="payType"/>
					<input style="width:150px" type="hidden" name="regAchieveDate" id="regAchieveDate"/>
					<input style="width:150px" type="hidden" name="maxMaterPerfPer" id="maxMaterPerfPer"/>
					<input style="width:150px" type="hidden" name="basePerfPer" id="basePerfPer"/>
					<input style="width:150px" type="hidden" name="isCalcBaseDisc" id="isCalcBaseDisc"/>
					<input style="width:150px" type="hidden" name="perfExpr" id="perfExpr"/>
					<input style="width:150px" type="hidden" name="regRealMaterPerf" id="regRealMaterPerf"/>
					<input style="width:150px" type="hidden" name="sumChgRealMaterPerf" id="sumChgRealMaterPerf"/>
					<input style="width:150px" type="hidden" name="perfAmount" id="perfAmount" value="0"/>
					<input style="width:150px" type="hidden" name="tileDeduction" id="tileDeduction" value="0"/>
					<input style="width:150px" type="hidden" name="bathDeduction" id="bathDeduction" value="0"/>
					<input style="width:150px" type="hidden" name="mainDeduction" id="mainDeduction" value="0"/>
					<input style="width:150px" type="hidden" name="realPerfAmount" id="realPerfAmount" value="0"/>
					<input style="width:150px" type="hidden" name="tileStatus" id="tileStatus" value="1"/>
					<input style="width:150px" type="hidden" name="bathStatus" id="bathStatus" value="1"/>
					<input style="width:150px" type="hidden" name="marketFund" id="marketFund" value="0"/>
					<input style="width:150px" type="hidden" name="basePerfRadix" id="basePerfRadix" value="0"/>
					<input style="width:150px" type="hidden" name="isInitSign" id="isInitSign"/>
					<input style="width:150px" type="hidden" name="baseFee_Dirct" id="baseFee_Dirct" value="0"/>
					<input style="width:150px" type="hidden" name="baseFee_Comp" id="baseFee_Comp" value="0"/>
					<input style="width:150px" type="hidden" name="baseChgNo" id="baseChgNo" value=""/>
					<input style="width:150px" type="hidden" name="chgNo" id="chgNo" value=""/>
				<ul class="ul-form">
					<ul id="itemAppUl" class="nav nav-tabs" hidden>
						<li class="active"><a id="a_mainInfo" href="#mainInfo"
							data-toggle="tab">主信息</a></li>
						<li><a id="a_otherInfo" href="#otherInfo" data-toggle="tab">其他信息</a>
						</li>
					</ul>
					<div id="mainInfo" class="tab-pane fade in active"
						style="border:0px">
						<div class="row">
							<li><label>业绩类型</label> <house:xtdm id="type"
									dictCode="PERFTYPE" unShowValue="1,2,6" onchange="changePerfType()" value="3"></house:xtdm>
							</li>
							<li><label>常规变更</label> <house:xtdm id="isAddAllInfo"
									dictCode="YESNO"  onchange="changeIsAddAllInfo()" value="1" ></house:xtdm>
							</li>
							<li><label>客户编号</label> <input style="width:150px" type="text" id="custCode"
								name="custCode" />
							</li>
							<li><label>档案号</label> <input style="width:120px" type="text" id="documentNo"
								name="documentNo" readonly/>
							</li>
							<li><label>楼盘</label> <input type="text" id="address"
								name="address" style="width:160px;" readonly/>
							</li>
						</div>
						<span
							style="position:absolute;left:20px;top:33px;z-index:1;background-color:white"><b>预算金额</b></span>
						<div class="panel panel-info"
							style="width:555px;height:262px;position:absolute;left:1px">
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>基础预算</label> <input style="width:150px" style="width:150px"
									type="number" id="basePlan" name="basePlan" class="init calcfee" value="0"/>
								</li>
								<li><label>远程费</label> <input style="width:150px" style="width:150px" type="number" id="longFee"
									name="longFee" class="init calcfee" value="0">
								</li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>主材预算</label> <input style="width:150px" style="width:150px"
									type="number" id="mainPlan" name="mainPlan" class="init calcfee" value="0"/>
								</li>
								<li><label>服务性预算</label> <input style="width:150px" style="width:150px" type="number"
									id="mainServPlan" name="mainServPlan" class="init calcfee" value="0"/>
								</li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>软装预算</label> <input style="width:150px" style="width:150px"
									type="number" id="softPlan" name="softPlan" class="init calcfee" value="0"/>
								</li>
								<li><label>软装家具费</label> <input style="width:150px" type="number"
									id="softFee_Furniture" name="softFee_Furniture" class="init calcfee" value="0"/>
								</li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>集成预算</label> <input style="width:150px"
									type="number" id="integratePlan" name="integratePlan" class="init calcfee" value="0"/></li>
								<li><label>橱柜预算</label> <input style="width:150px" type="number"
									id="cupboardPlan" name="cupboardPlan" class="init calcfee" value="0"/>
								</li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>套餐内减项</label> <input style="width:150px"
									type="number" id="setMinus" name="setMinus" class="init" value="0"/>
								</li>
								<li><label>协议优惠额</label> <input style="width:150px" type="number" id="baseDisc"
									name="baseDisc" class="init calcfee" value="0"/>
								</li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>套外基础增项</label> <input style="width:150px"
									type="number" id="setAdd" name="setAdd" class="init" value="0"/>
								</li>
								<li style="padding:0px"><label>基础个性化预算</label> <input style="width:150px"
									type="number" id="basePersonalPlan" name="basePersonalPlan" class="init calcfee" value="${perfCycle.basePersonalPlan}"/>
								</li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>木作预算</label> <input style="width:150px"
									type="number" id="woodPlan" name="woodPlan" class="init" value="${perfCycle.woodPlan}"/>
								</li>
							</div>
						</div>
						<span
							style="position:absolute;left:585px;top:33px;z-index:1;background-color:white"><b>管理费</b></span>
						<div class="panel panel-info"
							style="width:555px;height:222px;position:absolute;left:565px"">
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>管理费合计 </label> <input style="width:150px"
									type="number" id="manageFee_Sum" name="manageFee_Sum" value="0" class="calcfee" readonly/></li>
								<li><label>基础管理费</label> <input style="width:150px" type="number"
									id="manageFee_Base" name="manageFee_Base" class="init calcfee" value="0"/>
								</li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>主材管理费</label> <input style="width:150px"
									type="number" id="manageFee_Main" name="manageFee_Main" class="init calcfee" value="0"/></li>
								<li><label>服务性管理费</label> <input style="width:150px" type="number"
									id="manageFee_Serv" name="manageFee_Serv" class="init calcfee" value="0"/>
								</li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>集成管理费</label> <input style="width:150px"
									type="number" id="manageFee_Int" name="manageFee_Int" class="init calcfee" value="0"/></li>
								<li><label>橱柜管理费</label> <input style="width:150px" type="number"
									id="manageFee_Cup" name="manageFee_Cup" class="init calcfee" value="0"/>
								</li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>软装管理费</label> <input style="width:150px"
									type="number" id="manageFee_Soft" name="manageFee_Soft" class="init calcfee" value="0"/></li>
								<li><label>税金</label> <input style="width:150px"
									type="number" id="tax" name="tax" value="0" class="init calcfee"/></li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>套餐内管理费</label> <input style="width:150px"
									type="number" id="manageFee_InSet" name="manageFee_InSet" class="init calcfee" value="0"/></li>
								<li><label>套餐外管理费</label> <input style="width:150px" type="number"
									id="manageFee_OutSet" name="manageFee_OutSet" readonly value="0"/>
								</li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>基础个性化管理费</label> <input style="width:150px"
									type="number" id="manageFee_basePersonal" name="manageFee_basePersonal" class="init calcfee" value="${perfCycle.manageFee_basePersonal}"/>
								</li>
								<li ><label>木作管理费</label> <input style="width:150px"
									type="number" id="manageFee_wood" name="manageFee_wood" class="init" value="${perfCycle.manageFee_wood}"/>
								</li>
							</div>
						</div>
						<span
							style="position:absolute;left:1150px;top:33px;z-index:1;background-color:white"><b>材料业绩（支持封顶业绩1300）</b></span>
						<div class="panel panel-info"
							style="width:275px;height:155px;position:absolute;left:1130px"">
							<div class="row" style="padding:12px 2px 2px 2px">
								<li style="padding:0px"><label>实际材料业绩</label> <input style="width:150px"
									type="number" id="realMaterPerf" name="realMaterPerf" readonly value="0"/></li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>已算材料业绩</label> <input style="width:150px"
									type="number" id="alreadyMaterPerf" name="alreadyMaterPerf" readonly value="0"/></li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>封顶材料业绩</label> <input style="width:150px"
									type="number" id="maxMaterPerf" name="maxMaterPerf" readonly value="0"/></li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>材料业绩</label> <input style="width:150px"
									type="number" id="materPerf" name="materPerf" readonly value="0"/>
								</li>
							</div>
						</div>
						<span
							style="position:absolute;left:20px;top:305px;z-index:1;background-color:white"><b>业绩增减项目</b></span>
						<div class="panel panel-info"
							style="width:555px;height:68px;position:absolute;left:1px;top:315px">
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>实物赠送</label> <input style="width:150px"
									type="number" id="gift" name="gift" class="init calcfee" value="0"/>
								</li>
								<li><label>软装券金额</label> <input style="width:150px" type="number"
									id="softTokenAmount" name="softTokenAmount" class="init calcfee" value="0"/>
								</li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>基础单项扣减</label> <input style="width:150px"
									type="number" id="baseDeduction" name="baseDeduction" class="init calcfee" value="0"/></li>
								<li><label>材料单品扣减</label> <input style="width:150px" type="number"
									id="itemDeduction" name="itemDeduction" class="init calcfee" value="0"/>
								</li>
							</div>
						</div>
						<span
							style="position:absolute;left:585px;top:265px;z-index:1;background-color:white"><b>合同总价与业绩</b></span>
						<div class="panel panel-info"
							style="width:555px;height:131px;position:absolute;left:565px;top:270px">
							<div class="row" style="padding:0px">
								<li style="padding:0px"><label>合同总价</label> <input style="width:150px"
									type="number" id="contractFee" name="contractFee" value="0" class="calcfee" readonly/>
								</li>
								<li><label>实收设计费</label> <input style="width:150px" type="number" id="designFee"
									name="designFee" class="init calcfee"  value="0"/>
								</li>
							</div>
							<div class="row" style="padding:0px">
								<li style="padding:0px"><label>总价+税金</label>
									<input style="width:150px" type="number" id="contractAndTax"
									name="contractAndTax" value="${perfCycle.contractAndTax}"  readonly/>
								</li>
								<li><label>总价+设计费</label>
									<input style="width:150px" type="number" id="contractAndDesignFee"
									name="contractAndDesignFee" value="0" readonly/>
								</li>
							</div>
							<div class="row" style="padding:0px">
								<li style="padding:0px"><label>实际业绩</label> <input style="width:150px"
									type="number" id="recalPerf" name="recalPerf" class="init" value="0"/>
								</li>
								<li><label>计算PK业绩</label> <house:xtdm id="isCalPkPerf"
										dictCode="YESNO" value="0"></house:xtdm>
								</li>
							</div>
							<div class="row" style="padding:0px">
								<li style="padding:0px"><label>业绩折扣</label> <input style="width:150px"
									type="number" id="perfMarkup" name="perfMarkup" class=" calcfee"/>
								</li>
								<li><label>折扣前业绩</label> <input style="width:150px"
									type="number" id="befMarkupPerf" name="befMarkupPerf"  value="0" readonly/>
								</li>
							</div>
						</div>
						<span
							style="position:absolute;left:1150px;top:225px;z-index:1;background-color:white"><b>局装业绩折半</b></span>
						<div class="panel panel-info"
							style="width:275px;height:78px;position:absolute;left:1130px;top:230px">
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>业绩比例</label> <input style="width:150px"
									type="number" id="perfPerc" name="perfPerc" class="init" value="1"/>
								</li>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label>业绩折扣金额</label> <input style="width:150px"
									type="number" id="perfDisc" name="perfDisc" class="init calcfee" value="0"/>
								</li>
							</div>
						</div>
					</div>
					<div id="otherInfo" class="tab-pane fade" style="border:0px;display:none">
						<span
							style="position:absolute;left:20px;top:6px;z-index:1;background-color:white"><b>其他项目</b></span>
						<div class="panel panel-info"
							style="width:1400px;height:148px;position:absolute;left:1px;top:13px;">
							<div class="row" style="padding:2px">
								<div class="col-xs-1">
									<li><label style="width:50px">首付款</label> <input style="width:150px"
										type="number" id="firstPay" name="firstPay" class="init" value="0"/>
									</li>
									<li><label style="width:50px">达标应收</label> <input style="width:150px"
										type="number" id="mustReceive" name="mustReceive" class="init" value="0"/></li>
									<li><label style="width:50px">实收</label> <input style="width:150px"
										type="number" id="realReceive" name="realReceive" class="init" value="0"/></li>
									<li><label style="width:50px">数据类型</label> <house:xtdm
											id="dataType" dictCode="PERFDATATYPE" value="2"></house:xtdm>
									</li>
								</div>
								<div class="col-xs-1">
									<li><label style="width:50px">签订时间</label> <input style="width:150px"
										type="text" id="signDate" name="signDate" class="i-date"
										style="width:160px;"
										value="<fmt:formatDate value='${perfCycle.achieveDate }' pattern='yyyy-MM-dd'/>"
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
									</li>
									<li><label style="width:50px">下定时间</label> <input style="width:150px"
										type="text" id="setDate" name="setDate" class="i-date"
										style="width:160px;"
										value="<fmt:formatDate value='${perfCycle.achieveDate }' pattern='yyyy-MM-dd'/>"
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
									</li>
									<li><label style="width:50px">达标时间</label> <input style="width:150px"
										type="text" id="achieveDate" name="achieveDate" class="i-date"
										style="width:160px;"
										value="<fmt:formatDate value='${perfCycle.achieveDate }' pattern='yyyy-MM-dd'/>"
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
									</li>
									<li><label style="width:50px">人工修改</label> <house:xtdm
											id="isModified" dictCode="YESNO" onchange="changeIsModified()"></house:xtdm>
									</li>
								</div>
								<div class="col-xs-1">
									<li style="padding:0px"><label style="width:50px">优惠折扣</label>
										<input style="width:150px" type="number" id="markup" name="markup" class="init" value="1"/>
									<li style="padding:0px"><label class="control-textarea"
										style="width:50px">优惠政策</label> <textarea id="discRemark"
											name="discRemark" readonly
											style="overflow-y:scroll; overflow-x:hidden; height:92px;width: 170px;"
											 /></textarea></li>
									</li>
								</div>
								<div class="col-xs-1">
									<li style="padding:0px"><label class="control-textarea"
										style="width:50px">备注</label> <textarea id="remarks"
											name="remarks" class="init"
											style="overflow-y:scroll; overflow-x:hidden; height:125px;width: 170px;"
											/></textarea></li>
								</div>
								<div class="col-xs-1">
									<li><label style="width:60px">单量</label> <input style="width:150px"
										type="number" id="quantity" name="quantity" class="init" value="0"/>
									</li>
									<li><label style="width:60px">面积</label> <input style="width:150px"
										type="number" id="area" name="area" class="init" value="0"/>
									</li>
									<li><label style="width:60px">主材毛利率</label> <input style="width:150px"
										type="number" id="mainProPer" name="mainProPer" class="init" value="0"/>
									</li>
									<li>
										<label style="width:60px">签约公司</label>
										<house:dict id="payeeCode" dictCode="" style="width:150px"
											sql=" select code val,code+' '+Descr descr from tTaxPayee 
												where Expired='F' "  width="150px"
											sqlValueKey="val" sqlLableKey="descr" value=""/>
									</li>
								</div>
							</div>
							<div class="row" style="padding:2px">
								<li style="padding:0px"><label class="control-textarea"
									style="width:50px">业绩公式</label> <textarea id="perfExprRemarks"
										name="perfExprRemarks" readonly
										style="overflow-y:scroll; overflow-x:hidden; height:80px;width: 1300px;"
										/></textarea></li>
							</div>
						</div>
					</div>
				</ul>
			</form>
		</div>
	</div>
	<div class="container-fluid" style="padding:402px 0px 0px 0px">
		<ul class="nav nav-tabs">
			<li id="tabGxr" class="active" ><a
				href="#tab_Gxr" data-toggle="tab">干系人</a>
			</li>
			<li id="tabGxrxgls" class=""><a
				href="#tab_Gxrxgls" data-toggle="tab">干系人修改历史</a>
			</li>
			<li id="tabJczj" class=""><a
				href="#tab_Jczj" data-toggle="tab">基础增减</a>
			</li>
			<li id="tabClzj" class=""><a
				href="#tab_Clzj" data-toggle="tab">材料增减</a>
			</li>
			<li id="tabHtfyzj" class=""><a
				href="#tab_Htfyzj" data-toggle="tab">合同费用增减</a>
			</li>
			<li id="tabFkxx" class=""><a
				href="#tab_Fkxx" data-toggle="tab">付款信息</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_Gxr" class="tab-pane fade in active">
				<jsp:include page="perfCycle_tab_gxr.jsp"></jsp:include>
			</div>
			<div id="tab_Gxrxgls" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_gxrxgls.jsp"></jsp:include>
			</div>
			<div id="tab_Jczj" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_jczj.jsp"></jsp:include>
			</div>
			<div id="tab_Clzj" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_clzj.jsp"></jsp:include>
			</div>
			<div id="tab_Htfyzj" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_htfyzj.jsp"></jsp:include>
			</div>
			<div id="tab_Fkxx" class="tab-pane fade ">
				<jsp:include page="perfCycle_tab_fkxx.jsp"></jsp:include>
			</div>
		</div>
	</div> 
</body>
</html>

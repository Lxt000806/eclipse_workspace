<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>基装类型2--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/workType1/workOfferWorkType","offerWorkType1","offerWorkType2");
	Global.LinkSelect.setSelect({firstSelect:"offerWorkType1",
									firstValue:"${baseItemType2.offerWorkType1}",
									secondSelect:"offerWorkType2",
									secondValue:"${baseItemType2.offerWorkType2}",
	}); 
    Global.LinkSelect.initSelect("${ctx}/admin/workType1/workType","materWorkType1","materWorkType2");
	Global.LinkSelect.setSelect({firstSelect:"materWorkType1",
								firstValue:"${baseItemType2.materWorkType1}",
								secondSelect:"materWorkType2",
								secondValue:"${baseItemType2.materWorkType2}",
	});
});
$(function(){
$("input").prop("readly",true);
});
</script>
</head>
	<body>
	 	<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body" >
			    	<div class="btn-group-xs">
	     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" id="expired" name="expired" value="${baseItemType2.expired}">
						<ul class="ul-form">	
							<div class="validate-group row">				  
								<li class="form-validate">
									<label>基装类型2编号</label>
									<input id="code" name="code" type="text" value="${baseItemType2.code }" readonly="readonly"/>
								</li>
								<li class="form-validate" >	
									<label>基装类型2名称</label>
									<input type="text" id="descr" name="descr" value="${baseItemType2.descr}"/>
								</li>
								<li class="form-validate" >	
									<label>基装类型1</label>
									<house:dict id="baseItemType1" dictCode="" sql="select code,code+' '+Descr fd from tBaseItemType1 order by DispSeq " 
										sqlValueKey="code" sqlLableKey="fd" value="${baseItemType2.baseItemType1}" disabled="true"></house:dict>				
								</li>
								<li class="form-validate" >	
									<label>显示顺序</label>
									<input type="text" id="dispSeq" name="dispSeq" value="${baseItemType2.dispSeq}"/>
								</li>
								<li class="form-validate" >	
									<label>人工工种类型1</label>
									<select id="offerWorkType1" name="offerWorkType1" disabled="disabled"></select>
								</li>
								<li class="form-validate" >	
									<label>人工工种类型2</label>
									<select id="offerWorkType2" name="offerWorkType2" disabled="disabled"></select>
								</li>
								<li class="form-validate" >	
									<label>材料工种类型1</label>
									<select id="materWorkType1" name="materWorkType1" disabled="disabled"></select>
								</li>	
								<li class="form-validate" >	
									<label>材料工种类型2</label>
									<select id="materWorkType2" name="materWorkType2" disabled="disabled"></select>
								</li>
							</div>
							<div class="validate-group row" style="margin-left: 20px">	
								<li>
									<input type="checkbox" id="expired_show" name="expired_show"
							 			onclick="checkExpired(this)" ${baseItemType1.expired=="T"?"checked":""}/>
				 			   		<p style="float: right;margin-top: 5px;">过期</p>
				 				</li>
					 		</div>	
						</ul>		
					</form>
				</div>
	  		</div>
	  	</div>
	 </body>
</html>



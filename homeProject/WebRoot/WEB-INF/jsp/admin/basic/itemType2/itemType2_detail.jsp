<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
  	<title>材料类型2--查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
  </head>
  <body>
  	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
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
							<input type="text" id="code" name="code" value="${itemType2.code}" />
						</li>
						<li>	
							<label style="width:120px">名称</label>
							<input type="text" id="descr" name="descr" value="${itemType2.descr}" />
						</li>
						<li>
							<label style="width:120px">其他成本占销售额比例</label>
							<input type="text" id="otherCostPer_Sale" name="otherCostPer_Sale" value="${itemType2.otherCostPer_Sale}" />
						</li>
						<li>
							<label style="width:120px">材料类型1</label>
							<input type="text" id="itemType1Descr" name="itemType1Descr" value="${itemType2.itemType1Descr}" />
						</li>
						<li>
							<label style="width:120px">其他成本占成本比例</label>
							<input type="text" id="otherCostPer_Cost" name="otherCostPer_Cost" value="${itemType2.otherCostPer_Cost}" />
						</li>
						<li>
							<label style="width:120px">材料分类</label>
							<input type="text" id="itemType12Descr" name="itemType12Descr" value="${itemType2.itemType12Descr}" />
						</li>
						<li>
							<label style="width:120px">到货天数</label>
							<input type="text" id="arriveDay" name="arriveDay" value="${itemType2.arriveDay}" />
						</li>
						
						<li>
							<label style="width:120px">材料工种分类1</label>
							<input type="text" id="workType1" name="workType1" value="${itemType2.workType1}" />
						</li>
						<li>
							<label style="width:120px">显示顺序</label>
							<input type="text" id="dispSeq" name="dispSeq" value="${itemType2.dispSeq}" />
						</li>
						<li>
							<label style="width:120px">材料工种分类2</label>
							<input type="text" id="workType2" name="workType2" value="${itemType2.workType2}" />
						</li>
						<li>
						    <label style="width:120px">订货验收结点</label> 
							<input type="text" id="prjDescr" name="prjDescr" value="${itemType2.prjDescr}" />
						</li> 
					</ul>		
				</form>
			</div>
  		</div>
  	</div>
  </body>
</html>

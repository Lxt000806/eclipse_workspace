<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>工地巡检分析——明细</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">


</script>
</head>
    
<body >
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="V"/>
				<ul class="ul-form">
					<li>
						<label>楼盘</label>
							<input type="text" id="address" name="address"  value="${customer.address }" readonly="true" />
					</li>
					<li>
						<label>巡检项目</label>
						<house:xtdm id="prjitem" dictCode="PRJITEM" value="${prjProgCheck.prjItem }"   disabled="true"></house:xtdm>						
					</li>
					<li>
						<label>安全问题</label>
						<house:xtdm id="safeProm" dictCode="CHECKSAFEPROM" value="${prjProgCheck.safeProm }"  disabled="true"></house:xtdm>			
					</li>
					<li>
						<label>形象问题</label>
						<house:xtdm id="visualProm" dictCode="CHECKPROM" value="${prjProgCheck.visualProm }"  disabled="true"></house:xtdm>					
					</li>
					<li>
						<label>工艺问题</label>
						<house:xtdm id="artProm" dictCode="CHECKPROM" value="${prjProgCheck.artProm }"  disabled="true"></house:xtdm>					
					</li>
					<li>
						<label>整改时限</label>
						<input type="text" id="modifyTime" name="modifyTime"  value="${prjProgCheck.modifyTime }" readonly="true"/>
					</li>
					<li>
						<label>剩余整改时限</label>
						<input type="text" id="remainModifyTime" name="remainModifyTime"  value="${prjProgCheck.remainModifyTime }" readonly="true"/>
					</li>					
					<li>
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks" readonly="true">${prjProgCheck.remarks }</textarea>
					</li>				
				</ul>	
				</div>
			</form>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_PrjXJ" data-toggle="tab">巡检图片</a></li>
		        <li class=""><a href="#tab_PrjZG" data-toggle="tab">整改图片</a></li>
		    </ul> 
		    <div class="tab-content">  		    
		        <div id="tab_PrjXJ" class="tab-pane fade in active"> 
		         	<jsp:include page="tab_PrjXJ.jsp"></jsp:include>
		        </div> 
		       
		        <div id="tab_PrjZG" class="tab-pane fade "> 
		         	<jsp:include page="tab_PrjZG.jsp"></jsp:include>
		        </div> 
		    
		    </div>  
		</div>
	</div>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	
</body>
</html>



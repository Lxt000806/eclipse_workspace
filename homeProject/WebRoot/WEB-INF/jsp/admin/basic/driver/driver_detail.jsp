<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>司机信息明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
	<body>
		<div class="body-box-form">
			<div class="content-form">
			<!--panelBar-->
				<div class="panel panel-system">
					<div class="panel-body">
						<div class="btn-group-xs">
							<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
									<span>关闭</span>
							</button>
						</div>
					</div>
				</div>
				<div class="panel panel-info" style="margin: 0px;">  
						<div class="panel-body">
							<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
								<house:token></house:token>
								<ul class="ul-form">
									<div class="row">
										<div class="col-xs-12">
											<li class="form-validate">
												<label><span class="required">*</span>编码</label>
												<input type="text" style="width:160px;" value="${driver.code}" disabled="disabled" placeholder="保存后自动生成"> 
											</li>
										</div>
										<div class="col-xs-6">
											<li class="form-validate">
												<label>司机类型</label>
												<house:xtdm id="driverType" dictCode="DRIVERTYPE" value="${driver.driverType}"></house:xtdm>
											</li>
											<li class="form-validate">
												<label>姓    名</label>
												<input type="text" style="width:160px;" id="nameChi" name="nameChi" value="${driver.nameChi }"/> 
											</li>	
											<li class="form-validate">
												<label>性    别</label>
												<house:xtdm id="gender" dictCode="GENDER" value="${driver.gender }"></house:xtdm> 
											</li>
											<li class="form-validate">
												<label>车 牌 号</label>
												<input type="text" style="width:160px;" id="carNo" name="carNo" value="${driver.carNo }"/>
											</li>
											<li class="form-validate">
												<label>电    话</label>
												<input type="text" style="width:160px;" id="phone" name="phone" value="${driver.phone }"/>
											</li>
											<li class="form-validate">
												<label>密   码</label>
												<input type="password" id="mm" name="mm" style="width:160px;"  value="${driver.mm}" />
											</li>	
											<li class="form-validate">
												<label>确认密码</label>
												<input type="password" id="checkmm" name="checkmm" style="width:160px;"  value="${driver.mm}" />
											</li>
										</div>
										<div class="col-xs-6">
											<li class="form-validate">
												<label><span class="required"></span>身份证号</label>
												<input type="text" style="width:160px;" id="idNum" name="idNum" value="${driver.idNum }"/> 
											</li>		
											
											<li class="form-validate">
												<label>加入日期</label>
												<input value="<fmt:formatDate value="${driver.joinDate}" pattern="yyyy-MM-dd"/>"/>
												<%-- <input type="text" style="width:160px;" id="joinDate" name="joinDate" class="i-date" value="<fmt:formatDate value='${driver.joinDate}'/>"/>  --%>
											</li>
												
											<li class="form-validate">
												<label>离开日期</label>
												<input value="<fmt:formatDate value="${driver.leaveDate}" pattern="yyyy-MM-dd"/>"/>
												<%-- <input type="text" style="width:160px;" id="leaveDate" name="leaveDate" class="i-date" value="<fmt:formatDate value='${driver.leaveDate}'/>"/> --%>
											</li>	
											<li class="form-validate">
												<label class="control-textarea">备    注</label>
												<textarea id="remarks" name="remarks" style="width: 160px;height: 125px;">${driver.remarks }</textarea>
											</li>
										</div>
										<div class="col-xs-12">
											<li class="form-validate">
												<label>家庭住址</label>
												<input type="text" style="width:500px;" id="address" name="address" value="${driver.address }"/>
											</li>	
										</div>
										<div class="col-xs-12">
											<li style="width: 190px;">
												<input type="checkbox" id="expired_show" name="expired_show" value="${driver.expired}" disabled="disabled"
												onclick="checkExpired(this)" ${driver.expired == 'T'?'checked':'' } ${flag=='doc'?'disabled':'' }/>过期
											</li>
										</div>
									</div>
								</ul>
							</form>
						</div>
					</div>
			</div>
		</div>
	</body>
</html>


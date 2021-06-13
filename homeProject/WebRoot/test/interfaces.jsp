<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>接口测试页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		.green{
			background-color: #33ff00;
		}
		.green td{
			width: 250px;
		}
	</style>
	<script type="text/javascript">
	window.onscroll = function () {
        var oWatermark = document.getElementById("id_iframe");
        oWatermark.style.top = document.body.scrollTop+"px";
    }
	</script>
  </head>
<body style="padding-top: 10px; padding-right: 20px;">
<div>
<div style="height:600px;width:250px;overflow-y:auto;float: left;">
	<table id="id_table" border="1">
		<tr>
			<td>客户推荐接口测试模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				    <tr class="green" >
				      <td><a href="test/custRecommend/addCustRecommend.jsp" target="id_iframe">新增客户推荐</a></td>
				   	</tr>
				    <tr class="green" >
				      <td><a href="test/custRecommend/refreshAccessToken.jsp" target="id_iframe">刷新AccessToken</a></td>
				   	</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>麦田接口测试模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				    <tr class="green" >
				      <td><a href="test/mt/mtAddCustInfo.jsp" target="id_iframe">新增麦田客户</a></td>
				   	</tr>
				    
				</table>
			</td>
		</tr>

		<tr>
			<td>验收申请模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				    <tr class="green" >
				      <td><a href="test/prjConfirmApp/getPrjConfirmAppList.jsp" target="id_iframe">获取验收申请列表</a></td>
				   	</tr>
				    <tr class="green" >
				      <td><a href="test/prjConfirmApp/addPrjConfirmApp.jsp" target="id_iframe">新增验收申请</a></td>
				   	</tr>
				    <tr class="green" >
				      <td><a href="test/prjConfirmApp/getPrjConfirmAppDetail.jsp" target="id_iframe">获取验收申请详细</a></td>
				   	</tr>
				    <tr class="green" >
				      <td><a href="test/prjConfirmApp/updatePrjConfirmApp.jsp" target="id_iframe">更新验收申请信息</a></td>
				   	</tr>
				    
				</table>
			</td>
		</tr>
		<tr>
			<td>工人申请模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				    <tr class="green" >
				      <td><a href="test/workerApp/getWorkerAppList.jsp" target="id_iframe">获取工人申请列表</a></td>
				   	</tr>
				    <tr class="green" >
				      <td><a href="test/workerApp/addWorkerApp.jsp" target="id_iframe">新增工人申请</a></td>
				   	</tr>
				    <tr class="green" >
				      <td><a href="test/workerApp/getWorkerAppDetail.jsp" target="id_iframe">获取工人申请详细</a></td>
				   	</tr>
				    <tr class="green" >
				      <td><a href="test/workerApp/updateWorkerApp.jsp" target="id_iframe">更新工人申请信息</a></td>
				   	</tr>
				    
				</table>
			</td>
		</tr>
		<tr>
			<td>员工模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/employee/employeeDetail.jsp" target="id_iframe">员工详情接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/employee/employeeList.jsp" target="id_iframe">员工列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/employee/employeeLoginOut.jsp" target="id_iframe">用户注销接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/employee/employeeUpdatePwd.jsp" target="id_iframe">用户修改密码接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/employee/employeeRegister.jsp" target="id_iframe">用户注册接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/employee/EmployeeResetPwd.jsp" target="id_iframe">用户忘记密码接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/employee/employeePermission.jsp" target="id_iframe">用户权限接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>进度模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/prjProg/prjProgList.jsp" target="id_iframe">进度列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProg/prjProgDetailList.jsp" target="id_iframe">进度明细列表接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/prjProg/prjProgDate.jsp" target="id_iframe">进度工期详情接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProg/prjProgDetail.jsp" target="id_iframe">进度详情接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProg/prjProgUpdate.jsp" target="id_iframe">进度修改接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProg/prjProgUpload.jsp" target="id_iframe">上传图片接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProg/prjProgUploadNew.jsp" target="id_iframe">上传图片接口(新)</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProg/prjProgUploadApp.jsp" target="id_iframe">上传图片接口(App)</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProg/prjProgPhotoAdd.jsp" target="id_iframe">增加进度图片接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProg/prjProgAlarmDetail.jsp" target="id_iframe">工程进度提醒模板详情</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProg/getPrjProgPhotoList.jsp" target="id_iframe">获取工程进度图片列表</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProg/getPrjProgPhoto.jsp" target="id_iframe">获取工程进度单个图片</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProg/getPrjProgCurrent.jsp" target="id_iframe">根据客户编号获得当前节点</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProg/getNoSendItem.jsp" target="id_iframe">获取未出货材料</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>客户模块</td>
		</tr>
			<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/customer/customerList.jsp" target="id_iframe">客户列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/customerList_job.jsp" target="id_iframe">客户列表接口(job)</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/customer/customerList_report.jsp" target="id_iframe">客户列表接口(施工未报备)</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/getAllCustomerList.jsp" target="id_iframe">客户列表(不限部门权限)接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/getCustomerListByCustRight.jsp" target="id_iframe">根据客户权限获取客户列表</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/customerDetail.jsp" target="id_iframe">客户详情接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/getGdDetail.jsp" target="id_iframe">工地概况接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/customerPayDetail.jsp" target="id_iframe">客户付款详情接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/customerCostDetail.jsp" target="id_iframe">客户成本支出详情接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/getPayInfoDetailList.jsp" target="id_iframe">客户付款明细列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/customerItemReqList.jsp" target="id_iframe">客户材料需求列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/customerItemChangeList.jsp" target="id_iframe">客户材料变更列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/customerItemChangeDetailList.jsp" target="id_iframe">客户材料变更详情列表</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/customerItemAppList.jsp" target="id_iframe">客户领料列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/customerItemAppDetailList.jsp" target="id_iframe">客户领料详情列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/getGxrList.jsp" target="id_iframe">获取干系人列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/getBaseItemReqList.jsp" target="id_iframe">客户基础需求列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/getBaseItemChgList.jsp" target="id_iframe">客户基础变更列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/getBaseItemChgDetailList.jsp" target="id_iframe">客户基础变更详情列表</a></td>
				   <tr class="green" >
				      <td><a href="test/customer/getExecuteCustomerList.jsp" target="id_iframe">客户列表(近两天待巡检)接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/updateCustomerInfo.jsp" target="id_iframe">更新客户信息表:可拓展</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/getCustomerByConditions.jsp" target="id_iframe">通过条件获取客户列表接口:可拓展</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/getCustCheck.jsp" target="id_iframe">获取客户结算信息</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/getWorkerList.jsp" target="id_iframe">获取工地相关的工人信息</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/customer/getRollList.jsp" target="id_iframe">获取角色列表信息</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>消息模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				  <tr class="green" >
				      <td><a href="test/personMessage/personMessageSummary.jsp" target="id_iframe">消息列表总览接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/personMessage/personMessageList.jsp" target="id_iframe">消息列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/personMessage/personMessageDetail.jsp" target="id_iframe">消息详情接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/personMessage/personMessageUpdate.jsp" target="id_iframe">消息修改状态接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/personMessage/personMessagePush.jsp" target="id_iframe">消息推送接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/personMessage/personMessageNumber.jsp" target="id_iframe">获取个人消息未读条数</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/personMessage/personMessageUpdateBatch.jsp" target="id_iframe">批量修改消息状态接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>地图签到模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/signIn/getSignInList.jsp" target="id_iframe">获取地图签到列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/signIn/saveSignIn.jsp" target="id_iframe">地图签到保存接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/signIn/getSignInCount.jsp" target="id_iframe">获取当天地图签到次数</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/signIn/updatePosiInfo.jsp" target="id_iframe">更新导入数据位置信息</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/signIn/getPosiInfo.jsp" target="id_iframe">获取项目名称的位置信息</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/signIn/getPosiDistance.jsp" target="id_iframe">获取位置距离</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/signIn/getSignInType1List.jsp" target="id_iframe">获取签到类型1列表</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/signIn/getSignInType2List.jsp" target="id_iframe">获取签到类型2列表</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/signIn/uploadSignInPhoto.jsp" target="id_iframe">上传签到图片</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/signIn/delSignInPic.jsp" target="id_iframe">删除签到图片</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>领料模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/itemApp/getItemAppList.jsp" target="id_iframe">领料列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemApp/getItemAppDetailList.jsp" target="id_iframe">领料详情列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemApp/getItemReqList.jsp" target="id_iframe">领料需求列表接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>预领料模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/itemPreApp/getItemPreAppList.jsp" target="id_iframe">预领料列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemPreApp/getItemPreApp.jsp" target="id_iframe">预领料详情接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemPreApp/getItemPreAppDetailList.jsp" target="id_iframe">预领料明细列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemPreApp/getItemPreAppDetail.jsp" target="id_iframe">预领料明细接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemPreApp/checkItemPreApp.jsp" target="id_iframe">check预领料接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemPreApp/addItemPreApp.jsp" target="id_iframe">新增预领料接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemPreApp/updateItemPreApp.jsp" target="id_iframe">修改预领料接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemPreApp/updateItemPreAppStatus.jsp" target="id_iframe">修改预领料状态接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemPreApp/updateItemPreAppRemarks.jsp" target="id_iframe">修改预领料说明接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemPreApp/uploadItemPreAppPhoto.jsp" target="id_iframe">上传预领料图片接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemPreApp/deleteItemPreAppPhoto.jsp" target="id_iframe">删除预领料图片接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemPreApp/getItemPreAppPhotoList.jsp" target="id_iframe">查看预领料图片接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemPreApp/getItemBatchHeaderList.jsp" target="id_iframe">材料批次列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemPreApp/getItemBatchDetailList.jsp" target="id_iframe">材料批次明细列表接口(无需求)</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemPreApp/getItemReqList.jsp" target="id_iframe">客户材料需求列表接口(有需求)</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemPreApp/getIsSetItem.jsp" target="id_iframe">是否套餐材料接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/itemPreApp/getItemAppList.jsp" target="id_iframe">下单明细列表接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/itemPreApp/getItemAppTempList.jsp" target="id_iframe">获取领料模板列表接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/itemPreApp/getItemAppTempAreaDetail.jsp" target="id_iframe">获取领料模板明细列表接口</a></td>
				   </tr>
				       <tr class="green" >
				      <td><a href="test/itemPreApp/isSetItemNeed.jsp" target="id_iframe">是否套餐内需求接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>退货申请模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/itemReturn/getItemReturnList.jsp" target="id_iframe">退货申请列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemReturn/getItemReturn.jsp" target="id_iframe">退货申请详情接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemReturn/getItemReturnDetailList.jsp" target="id_iframe">退货明细列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemReturn/getItemReturnDetail.jsp" target="id_iframe">退货明细详情接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemReturn/addItemReturn.jsp" target="id_iframe">新增退货申请接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemReturn/updateItemReturn.jsp" target="id_iframe">修改退货申请接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemReturn/updateItemReturnStatus.jsp" target="id_iframe">修改退货申请状态接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/itemReturn/getItemReturnDetailSelectList.jsp" target="id_iframe">可选取退货明细列表接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>版本模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/version/getVersion.jsp" target="id_iframe">核对版本接口</a></td>
				   </tr>
				</table>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/version/updateVersion.jsp" target="id_iframe">更新版本接口</a></td>
				   </tr>
				</table>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/version/copyPictures.jsp" target="id_iframe">复制图片文件接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>基础模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				  <tr class="green" >
				      <td><a href="test/basic/getItemTypeList.jsp" target="id_iframe">员工获取材料类型接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/basic/getItemType1List.jsp" target="id_iframe">获取材料类型1接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/basic/getItemType2List.jsp" target="id_iframe">获取材料类型2接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/basic/getAppServerUrlList.jsp" target="id_iframe">获取App服务端地址接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/basic/getXtdmListById.jsp" target="id_iframe">根据ID获取系统代码接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/basic/getCustomerByUserCustRight.jsp" target="id_iframe">根据用户权限获取工地列表</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/basic/getPrjItemList.jsp" target="id_iframe">获取施工项目列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/basic/getSalaryTypeList.jsp" target="id_iframe">获取工资类型列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/basic/getSalaryType.jsp" target="id_iframe">获取工资类型接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/basic/getWorkType1List.jsp" target="id_iframe">获取工种类型1接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/basic/getWorkType2List.jsp" target="id_iframe">获取工种类型2接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/basic/getWorkerList.jsp" target="id_iframe">获取工人列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/basic/getIntMeasureBrandList.jsp" target="id_iframe">获取集成测量品牌列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/basic/getWorkType12List.jsp" target="id_iframe">获取工种类型12接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/basic/getPrjItem1List.jsp" target="id_iframe">获取施工项目(tPrjItem1)接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/basic/getDepartment2List.jsp" target="id_iframe">获取二级部门列表接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>短信验证模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/sms/getSmsPassword.jsp" target="id_iframe">获取项目经理App短信验证码接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/sms/getDriverSmsPassword.jsp" target="id_iframe">获取司机App短信验证码接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/sms/getCustAccountSmsPassword.jsp" target="id_iframe">获取客户App短信验证码接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/sms/getWorkerSmsPassword.jsp" target="id_iframe">获取工人APP短信验证码接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>材料模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				  <tr class="green" >
				      <td><a href="test/item/getItemBatchHeader.jsp" target="id_iframe">查询材料批次接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/item/getItemBatchDetail.jsp" target="id_iframe">查询材料批次明细接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/item/getItemDetail.jsp" target="id_iframe">获取材料详情接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/item/saveItem.jsp" target="id_iframe">保存材料项接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/item/deleteItem.jsp" target="id_iframe">删除材料项接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/item/updateItem.jsp" target="id_iframe">更新材料项接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>工地验收模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				  <tr class="green" >
				      <td><a href="test/prjProgConfirm/getSiteConfirmList.jsp" target="id_iframe">查询工地验收列表接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/prjProgConfirm/getSiteConfirmDetail.jsp" target="id_iframe">工地验收详情接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/prjProgConfirm/getPrjItemDescr.jsp" target="id_iframe">未验收施工阶段列表接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/prjProgConfirm/prjProgPhotoUpload.jsp" target="id_iframe">上传图片接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/prjProgConfirm/prjProgPhotoDelete.jsp" target="id_iframe">删除工地进度图片接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/prjProgConfirm/savePrjProgConfirm.jsp" target="id_iframe">工地验收保存接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/prjProgConfirm/confirmPass.jsp" target="id_iframe">验收通过接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/prjProgConfirm/getUnPassConfirmInfo.jsp" target="id_iframe">获取未验收通过的次数和最后一次no</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/prjProgConfirm/getPrjConfirmByPage.jsp" target="id_iframe">获取验收申请列表</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>仓库模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				  <tr class="green" >
				      <td><a href="test/wareHouse/getWareHouseList.jsp" target="id_iframe">获取可用仓库列表接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/wareHouse/getWareHouseItemDetail.jsp" target="id_iframe">获取仓库材料详情接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/wareHouse/getWareHousePosiList.jsp" target="id_iframe">货架查询接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/wareHouse/operateWareHousePosi.jsp" target="id_iframe">仓库上下架接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>活动签到模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				  <tr class="green" >
				      <td><a href="test/activity/getActivityList.jsp" target="id_iframe">获取活动列表接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/activity/getTicketList.jsp" target="id_iframe">获取门票业主信息列表接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/activity/getTicketDetail.jsp" target="id_iframe">门票详情询接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/activity/updateTicket.jsp" target="id_iframe">活动签到接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/activity/getCurrActivity.jsp" target="id_iframe">获取当前活动接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>到货确认模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				  <tr class="green" >
				      <td><a href="test/itemAppSendConfirm/getItemAppSendConfirmList.jsp" target="id_iframe">获取确认到货单列表接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/itemAppSendConfirm/getItemAppSendConfirmDetail.jsp" target="id_iframe">确认到货单详情接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/itemAppSendConfirm/itemAppSendDoConfirm.jsp" target="id_iframe">到货单确认接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/itemAppSendConfirm/itemAppSendDoException.jsp" target="id_iframe">到货单异常接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>司机模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/driver/driverDetail.jsp" target="id_iframe">司机详情接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/driver/driverLoginOut.jsp" target="id_iframe">司机注销接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/driver/driverUpdatePwd.jsp" target="id_iframe">司机修改密码接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/driver/driverResetPwd.jsp" target="id_iframe">司机忘记密码接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>送货模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/itemAppSend/getItemAppSendList.jsp" target="id_iframe">送货列表接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/itemAppSend/getItemAppSendArrivedList.jsp" target="id_iframe">到货列表接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/itemAppSend/getItemAppSendArrivedDetail.jsp" target="id_iframe">送货到货详情接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/itemAppSend/getItemAppSendDetail.jsp" target="id_iframe">送货详情接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/itemAppSend/itemAppSendUpload.jsp" target="id_iframe">到货图片上传接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/itemAppSend/getItemAppSendPhotoList.jsp" target="id_iframe">查看送货图片接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/itemAppSend/deleteItemAppSendPhoto.jsp" target="id_iframe">删除送货图片接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/itemAppSend/updateItemAppSend.jsp" target="id_iframe">到货保存接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>退货模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/itemAppReturn/getItemAppReturnList.jsp" target="id_iframe">待退货列表接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/itemAppReturn/getItemReturnArrivedList.jsp" target="id_iframe">已退货列表接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/itemAppReturn/getItemReturnDetail.jsp" target="id_iframe">退货详情接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/itemAppReturn/getReturnMaterial.jsp" target="id_iframe">待退货明细列表</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/itemAppReturn/updateItemReturnReceive.jsp" target="id_iframe">收货接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/itemAppReturn/getItemReturnArrivedPhotoList.jsp" target="id_iframe">查看退货到达图片接口</a></td>
				   </tr>
				      <tr class="green" >
				      <td><a href="test/itemAppReturn/deleteItemAppReturnPhoto.jsp" target="id_iframe">删除退货货图片接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/itemAppReturn/saveItemReturnArrive.jsp" target="id_iframe">退货到货保存接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>工地巡检模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/prjProgCheck/getPrjProgCheckList.jsp" target="id_iframe">工地巡检列表查看</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProgCheck/addPrjProgCheck.jsp" target="id_iframe">工地巡检新增</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/prjProgCheck/updatePrjProgCheck.jsp" target="id_iframe">工地巡检编辑</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/prjProgCheck/updateSiteModify.jsp" target="id_iframe">重新整改接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProgCheck/getPrjProgCheck.jsp" target="id_iframe">工地巡检详情查看</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProgCheck/uploadPrjProgCheck.jsp" target="id_iframe">工地巡检图片上传</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProgCheck/getPrjProgCheckPhotoList.jsp" target="id_iframe">工地巡检图片列表</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProgCheck/getPrjProgCheckPhoto.jsp" target="id_iframe">工地巡检单个图片</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProgCheck/getIsModifyList.jsp" target="id_iframe">获取整改次数列表</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/prjProgCheck/getModifyConfirmList.jsp" target="id_iframe">获取整改验收列表</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>项目任务模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/prjJob/getPrjJobList.jsp" target="id_iframe">项目任务列表接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/prjJob/getDealPrjJobList.jsp" target="id_iframe">待处理项目任务列表接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/prjJob/dealPrjJob.jsp" target="id_iframe">项目任务处理接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjJob/getPrjJob.jsp" target="id_iframe">项目任务详情接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjJob/addPrjJob.jsp" target="id_iframe">新增项目任务接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjJob/updatePrjJob.jsp" target="id_iframe">修改项目任务接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjJob/updatePrjJobStatus.jsp" target="id_iframe">修改项目任务状态接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjJob/uploadPrjJob.jsp" target="id_iframe">项目任务图片上传</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjJob/getPrjJobPhotoList.jsp" target="id_iframe">项目任务图片列表</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjJob/getPrjJobPhoto.jsp" target="id_iframe">项目任务单个图片</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjJob/getJobTypeList.jsp" target="id_iframe">获取任务类型列表</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjJob/getJobType.jsp" target="id_iframe">获取任务类型详情</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjJob/getPrjJobEmployeeList.jsp" target="id_iframe">项目任务处理人列表</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/prjJob/deletePrjJobPhoto.jsp" target="id_iframe">任务图片删除接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjJob/getPrjJobTypeList.jsp" target="id_iframe">根据权限获取任务类型</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjJob/getPrjJobReceiveList.jsp" target="id_iframe">获取接收任务列表</a></td>
				   </tr>
				   	<tr class="green" >
				      <td><a href="test/prjJob/updatePrjJobReceive.jsp" target="id_iframe">更新接收状态接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>工地整改</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/siteModify/getSiteModifyList.jsp" target="id_iframe">工地整改列表接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/siteModify/getSiteModifiedList.jsp" target="id_iframe">工地已整改列表接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/siteModify/getSiteModifyDetail.jsp" target="id_iframe">工地整改详情接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/siteModify/uploadSiteModifyPhoto.jsp" target="id_iframe">整改图片上传接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/siteModify/saveSiteModify.jsp" target="id_iframe">工地整改保存接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/siteModify/deleteSiteModifyPhoto.jsp" target="id_iframe">工地图片删除接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>工资预申请明细模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/preWorkCostDetail/getPreWorkCostDetailList.jsp" target="id_iframe">工资预申请明细列表</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/preWorkCostDetail/getPreWorkCostDetail.jsp" target="id_iframe">工资预申请明细详情</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/preWorkCostDetail/addPreWorkCostDetail.jsp" target="id_iframe">新增工资预申请明细</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/preWorkCostDetail/updatePreWorkCostDetail.jsp" target="id_iframe">修改工资预申请明细</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/preWorkCostDetail/updatePreWorkCostDetailStatus.jsp" target="id_iframe">修改工资预申请明细状态</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/preWorkCostDetail/getIsCommit.jsp" target="id_iframe">是否可以提交接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/preWorkCostDetail/getYkCustomerList.jsp" target="id_iframe">获取预扣客户列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/preWorkCostDetail/getYkCustomerList_new.jsp" target="id_iframe">获取预扣客户列表接口new</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/preWorkCostDetail/getPrjWithHoldList.jsp" target="id_iframe">项目经理预扣单列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/preWorkCostDetail/getPrjWithHold.jsp" target="id_iframe">项目经理预扣单接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>客户登录模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/custAccount/custAccountDetail.jsp" target="id_iframe">客户账号详情接口</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/custAccount/custAccountLoginOut.jsp" target="id_iframe">客户账号注销接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/custAccount/custAccountRegister.jsp" target="id_iframe">客户注册接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/custAccount/custAccountUpdatePwd.jsp" target="id_iframe">客户修改密码接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/custAccount/custAccountResetPwd.jsp" target="id_iframe">客户忘记密码接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>工地报备模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/sites/getSitePreparationList.jsp" target="id_iframe">获取工地报备列表</a></td>
				   </tr>
				    <tr class="green" >
				      <td><a href="test/sites/addSitePrepartion.jsp" target="id_iframe">新增工地报备</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/sites/getSitePrepartionDetail.jsp" target="id_iframe">获取工地报备详细</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/sites/updateSitePrepartion.jsp" target="id_iframe">更新工地报备信息</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>工人APP模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				    <tr class="green" >
				      <td><a href="test/worker/getWorkerDetail.jsp" target="id_iframe">工人登入接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/worker/loginOutWorker.jsp" target="id_iframe">工人注销接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/worker/updateWorkerPwd.jsp" target="id_iframe">工人修改密码接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/worker/resetWorkerPwd.jsp" target="id_iframe">工人忘记密码接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/worker/getSiteConstructList.jsp" target="id_iframe">获取在建工地列表</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/worker/getWorkerPrjItem.jsp" target="id_iframe">获取当前项目阶段</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/worker/workerSignIn.jsp" target="id_iframe">工人签到接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/worker/getCustWorkInfo.jsp" target="id_iframe">获取客户工人表信息接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/worker/getWokerCostApply.jsp" target="id_iframe">获取工人工资申请列表</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/worker/addWorkerCost.jsp" target="id_iframe">工人申请工资接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/worker/getWorkSignInCount.jsp" target="id_iframe">获取工人今日签到次数</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/worker/getWorkPrjItemList.jsp" target="id_iframe">获取prjItem1项目节点列表</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		
		<tr>
			<td>活动礼品模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				    <tr class="green" >
				      <td><a href="test/activityGift/getActivityGiftList.jsp" target="id_iframe">获取活动礼品列表接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/activityGift/getActGiftList.jsp" target="id_iframe">获取礼品列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/activityGift/doSaveActivityGift.jsp" target="id_iframe">添加活动礼品接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/activityGift/getActGiftDetail.jsp" target="id_iframe">获取礼品信息接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/activityGift/getActGiftDetailByPk.jsp" target="id_iframe">获取活动礼品详情接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/activityGift/doUpdateActGift.jsp" target="id_iframe">更新活动礼品接口</a></td>
				   </tr>
				   
				</table>
			</td>
		</tr>
		
		<tr>
			<td>活动下定模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				    <tr class="green" >
				      <td><a href="test/activitySet/getActivitySetList.jsp" target="id_iframe">获取活动下定列表接口</a></td>
				   </tr>
				     <tr class="green" >
				      <td><a href="test/activitySet/getActSupplList.jsp" target="id_iframe">获取活动供应商列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/activitySet/doSaveActivitySet.jsp" target="id_iframe">添加下定接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/activitySet/getActSupplDetail.jsp" target="id_iframe">获取活动供应商信息接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/activitySet/getActSetDetail.jsp" target="id_iframe">获取活动下定详情接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/activitySet/doActSetCancel.jsp" target="id_iframe">活动下定作废接口</a></td>
				   </tr>
				   
				</table>
			</td>
		</tr>
		<tr>
			<td>工地信息模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				    <tr class="green" >
				      <td><a href="test/sites/getIntProgress.jsp" target="id_iframe">获取集成进度信息接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/sites/getCustLoan.jsp" target="id_iframe">获取客户贷款信息接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>工地问题模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/prjProblem/getPrjProblemList.jsp" target="id_iframe">获取工地问题列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProblem/getPrjPromDeptList.jsp" target="id_iframe">获取责任部门列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProblem/getPrjPromTypeList.jsp" target="id_iframe">获取责任分类列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProblem/doSavePrjProblem.jsp" target="id_iframe">保存工地问题接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProblem/getPrjProblem.jsp" target="id_iframe">获取工地问题详情接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/prjProblem/doUpdatePrjProblem.jsp" target="id_iframe">更新工地问题接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>客户投诉模块</td>
		</tr>
		<tr>
			<td>
				<table border="1">
				   <tr class="green" >
				      <td><a href="test/custComplaint/getCustComplaintList.jsp" target="id_iframe">获取客户投诉列表接口</a></td>
				   </tr>
				   <tr class="green" >
				      <td><a href="test/custComplaint/getCustComplaintDetail.jsp" target="id_iframe">获取客户投诉详细接口</a></td>
				   </tr>
				</table>
			</td>
		</tr>
	</table>
</div>
	<iframe id="id_iframe" name="id_iframe" height="600" width="70%" frameborder="0"   
	style="float: left;left: 10px;top: 0px;position: relative;"></iframe>
</div>
</body>
</html>

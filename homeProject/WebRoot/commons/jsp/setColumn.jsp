<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化设置表格字段*/
$(function(){
	var moduleUrl = "${moduleUrl}";
	if(moduleUrl){
		var tableCode = "${tableCode}";
		var setColumnUrl = "${ctx}/admin/table/goSetColumn";
		$(".btn-group-sm:eq(0)").append("<button type='button' class='btn btn-system'"
		+" onclick='setColumn(\""+moduleUrl+"\",\""+tableCode+"\",\""
		+setColumnUrl+"\")'>设置显示字段</button>");
	}
});	

function setColumn(moduleUrl,tableCode,setColumnUrl){
	Global.Dialog.showDialog("setColumn",{
		title:"设置显示字段",
		url:setColumnUrl,
		postData:{moduleUrl:moduleUrl,tableCode:tableCode},
		height:600,
		width:900,
		returnFun:function(){
			//刷新当前窗口
			$(".l-selected").context.defaultView.location.reload();
		}
	});
} 	
</script>
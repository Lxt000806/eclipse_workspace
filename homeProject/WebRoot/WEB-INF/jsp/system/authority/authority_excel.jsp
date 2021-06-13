<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
		<table border="1">
			<col width="30px"/>
			<col width="30px"/>
			<col width="100px"/>
			<col />
			<col />
			<col />
			<thead>
				<tr>
					<th>
						序号
					</th>
					<th>
						权限编码
					</th>
					<th>
						权限名称
					</th>
					<th>
						链接地址
					</th>
					<th>
						所属菜单
					</th>
					<th>
						备注提示
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${page.result}"  varStatus="item_index">
				<tr >
					<td >
						${item_index.index+1}
					</td>

					<td >
						${item.AUTH_CODE}
					</td>
					<td>
						${item.AUTH_NAME}
					</td>
					<td>
						${item.URL_STR}
					</td>
					<td>
						${item.MENU_NAME}
					</td>
					<td>
						${item.REMARK}
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>

</body>
</html>

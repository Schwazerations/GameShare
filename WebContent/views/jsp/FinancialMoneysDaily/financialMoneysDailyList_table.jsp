<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/views/Css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/views/Css/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/views/Css/style.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/views/Js/common.js"></script>
</head>
<script type="text/javascript">
	function findUsers(daily_code,daily_create_timeStr) {
		if (daily_code == '') {
			alert("邀请码为空");
		} else {
			window.parent.location.href = "financialMoneysDaily.do?queryUsers&daily_code="
			+ daily_code + "&daily_create_timeStr=" + daily_create_timeStr;
		}
	}
</script>
<style>
table {
	table-layout: fixed;
	word-break: break-all;
	word-wrap: break-word;
}
</style>
<body onload="initParentFrameHeight();">
	<table class="table table-bordered table-hover definewidth m10"
		id="table1">
		<thead>
			<tr>
				<th>手机号</th>
				<th>姓名</th>
				<th>邀请码</th>
				<th>工号</th>
				<th>状态</th>
				<th>日志创建时间</th>
				<th>所属地区</th>
				<th>邀请用户出资总额</th>
				<th>邀请用户</th>
			</tr>
		</thead>
		<c:forEach items="${financialMoneysDailyList}" var="financial">
			<tr>
				<td>${financial.daily_phone}</td>
				<td>${financial.daily_name}</td>
				<td>${financial.daily_code}</td>
				<td></td>
				<td>
				 <c:choose>
						<c:when test="${financial.daily_state=='1'}">
						禁用
						</c:when>
						<c:when test="${financial.daily_state=='0'}">
						正常
						</c:when>
					</c:choose></td>
				<td>${financial.daily_create_timeStr}</td>
				<td>${financial.daily_location}</td>
				<td>${financial.daily_user_money}</td>
				<td><a href="javascript:void('0');"
					onclick="findUsers('${financial.daily_code}','${financial.daily_create_timeStr}');">查看</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
<%@ include file="../common/pageHiddenInfo.jsp"%>
</html>

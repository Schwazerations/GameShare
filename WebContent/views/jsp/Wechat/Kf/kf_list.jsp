<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page  import="crf.util.PageUtil" %> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/WEB-INF/tld/tyTag.tld" prefix="tyTag"%> 
<%@ page isELIgnored="false"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用户列表Init</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/views/Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/views/Css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/views/Css/page.css" />
</head>
<body>
<form name="form0" class="form-inline definewidth m20"  method="post">
<table width="100%" align="center" border="0" class="q-cls" >
	<tr>
		<td width="7%" align="right">客服账号:</td>
		<td width="8%" align="left"><input type="text"  name="kf_account"></td>
		
		<td width="7%" align="right">昵称:</td>
		<td width="8%" align="left"><input type="text"  name="nickname"></td>
		
		<td align="right" style="padding-right: 2%;">
			<button type="button" onclick="query();" class="btn btn-primary">查询</button>
			<button type="button" class="btn btn-success" onclick="addKf();" style="margin-left: 2px;">新增客服</button>
		</td>
	</tr>
</table>
<tyTag:page tarFrame="frame1" method="wechatController.do?queryKfListTable" pageTurn="<%=PageUtil.TURN_YES %>"></tyTag:page>
</form>
</body>
    <script type="text/javascript">
    	function query(){
    		document.forms[0].action = "wechatController.do?queryKfListTable";
    		document.forms[0].target="frame1";
    		document.forms[0].submit();
    	}
    	function addKf(){
    		window.location.href="wechatController.do?toAddOrUpdateKf&flag=add";
    	}
    </script>
</html>

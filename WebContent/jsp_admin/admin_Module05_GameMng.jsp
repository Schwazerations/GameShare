<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<jsp:include page="admin_query_game.jsp" />
<%
System.out.println("——————————————————————————————————————————————到了模块主页：————————————————————————————————————————");
Object allGames = request.getAttribute("allGames");
String path = "admin_Module05_GameMng.jsp";
request.setAttribute("path", path);
session.setAttribute("path", path);
String query = request.getParameter("query");
if (allGames == null) { // 对象为空
    System.out.println("【主页检测】空   ：allGames为空。query=" + query);
    if ("queryAll".equals(query) || query == null) { // 自动全查 和 手动全查
        request.getRequestDispatcher("../QueryAllGameServlet.do?path=" + path).forward(request, response);
    } else {/* query = none（没查到） 或 get_it（查到了）。不做任何处理。*/}
} else {
    System.out.println("【主页检测】√   ：allGames非空。query=" + query);
}
%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>游戏管理页面</title>
  <script type="text/javascript" src="../js/jquery-3.6.0.js"></script>
  <script type="text/javascript" src="../js/admin_Module05_GameMng.js"></script>
  <style>
    table input {
        border: none;
        padding: 0;
        height: 30px;
        width: 100px;
        background-color: transparent;
        font-family: Arial, sans-serif;
        font-size: 16px;
    }
    .hidden {
        display: none;
    }
    .cursor-banned {
        cursor: not-allowed;
    }
  </style>
</head>

<body>

<c:if test="${param.query == 'none'}">
    <h3>没有找到游戏。</h3>
</c:if>

<c:if test="${param.query != 'none'}">
	
    <!-- 增添文字数据按钮： -->
	<button id="addBtn" onclick="addBtn(this)">增添游戏</button>
    <form id="addForm" action="../UploadGameServlet.do" enctype="multipart/form-data" method="post">
	    <!-- 上传文件按钮： -->
	    <input type="file" name="addFile" onchange="submitUploads()" id="addFile" style="display:none;"/> 
    </form>
    
    <table border="" style="border-collapse: collapse; border: 1px solid black;">
        <tr>
            <th>游戏ID</th>
            <th>游戏名称</th>
            <th>价格</th>
            <th>开发商</th>
            <th>发行商</th>
            <th>发行日期</th>
            <th>描述</th>
            <th>中文名称</th>
            <th>标签</th>
            <!-- 操作按钮 -->
		    <th>删除操作</th>
		    <th>修改操作</th>
        </tr>
	        
	        
	    <!-- 增添模块： -->
	    <jsp:include page="admin_game_add.jsp" />
        
        <!-- 动态输出信息 -->
        <c:forEach items="${allGames}" var="game">
            <tr>
                <td><input class="editing read-only" readonly name="gid" value="${game.gid}"></td>
                <td><input class="editing read-only" readonly name="gname" value="${game.gname}"></td>
                <td><input class="editing read-only" readonly name="gprice" value="${game.gprice}"></td>
                <td><input class="editing read-only" readonly name="gdeveloper" value="${game.gdeveloper}"></td>
                <td><input class="editing read-only" readonly name="gpublisher" value="${game.gpublisher}"></td>
                <td><input class="editing read-only" readonly name="grelease_date" value="${game.grelease_date}"></td>
                <td><input class="editing read-only" readonly name="gdescription" value="${game.gdescription}"></td>
                <td><input class="editing read-only" readonly name="gzhname" value="${game.gzhname}"></td>
                <td><input class="editing read-only" readonly name="gtag" value="${game.gtag}"></td>
                <td>
                    <button class="updateBtn" onclick="updateBtn(this.parentNode.parentNode)">修改</button>
                </td>
                <td>
                    <button class="deleteBtn" onclick="deleteBtn('${game.gid}')">删除</button>
                </td>
            </tr>
        </c:forEach>
        
    </table>
</c:if>

</body>
</html>
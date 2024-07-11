<%@page import="sun.security.util.Length"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="game.bean.User" %>
<html>
<head>
<meta charset="UTF-8">
<title>购买页面</title>
<link rel="stylesheet" type="text/css" href="css/public.css" />
<link rel="stylesheet" type="text/css" href="css/game.css" />
<script src="js/jquery-3.6.0.js" type="text/javascript"
	charset="utf-8"></script>
<script src="js/jquery.validate.min.js" type="text/javascript"
	charset="utf-8"></script>

</head>
<body>
	<%	String gid = request.getParameter("gid"); 
		String gname = request.getParameter("gname");
		String gprice = request.getParameter("gprice");
		String gzhname = request.getParameter("gzhname");
		User user = (User)session.getAttribute("Login_user");
		String upoint = user.getUpoint();
	%>
	<div class="head">
		<div class="head_zi">
			<div class="huan">
				<a href="DetailServlet?gid=<%= gid%>">返回游戏详情页</a>
			</div>
			<div class="info">
				<a href="info.jsp"><img src="images/info.png" height="28px"></a>
			</div>
			<div class="login">
				<%
					String userName = (String)session.getAttribute("Login_uname");
					if(userName == null){
				%>
				<div class="login2">
					<a href="LR.jsp">登录&注册</a>
				</div>
				<%
					} else {
				%>
				<div class="ulogin">
					<p>欢迎您，${sessionScope.Login_uname}</p>
				</div>
				<div class="login2"><a href="logout.jsp">登出</a></div>
				<%
					}
				%>
			</div>
		</div>
	</div>
	<div class="dowebok" id="dowebok">
	<div class="gtitle"><%=gzhname %></div>
	<hr/>
	<div class="gcover">
		<img src="images/game/<%=gid %>/1.jpg"/>
		<table border="0";align="center">
			<tr><td>游戏名：</td><td><%=gname %></td></tr>
			<tr><td>价格：</td><td><%=gprice %>积分</td></tr>
			<tr><td>是否购买？<br/>(当前账户剩余积分:<%=upoint %>)</td><td><div id="steam"><a href="PurchaseServlet?gid=<%=gid%>&uid=${Login_uid}"><img  src="images/yes.png"/></a></div></td></tr>
		</table>
	</div>
	
	
	</div>

	<div class="footer">
		<div class="translate">
			<div class="footer-links">
				<h5>官方网站</h5>
				<ul>
					<li><a href="https://store.steampowered.com">steam官网</a></li>
					<li><a href="https://www.epicgames.com/site/zh-CN/home">Epic官网</a></li>
					<li><a href="https://www.origin.com">Origin官网</a></li>
					<li><a href="https://www.ubisoft.com/zh-tw">Ubisoft官网</a></li>
					<li><a href="https://www.wegame.com.cn">Wegame官网</a></li>
				</ul>
			</div>
			<div class="footer-links">
				<h5>游戏攻略</h5>
				<ul>
					<li><a href="https://www.3dmgame.com">3DM游戏网站</a></li>
					<li><a href="https://www.gamersky.com">游民星空</a></li>
					<li><a href="https://www.ucg.cn">游戏机实用技术</a></li>
					<li><a href="https://www.vgtime.com">游戏时光</a></li>
					<li><a href="https://nga.178.com">NGA</a></li>
					<li><a href="https://gl.ali213.net">游侠攻略</a></li>
				</ul>
			</div>
			<div class="footer-links">
				<h5>游戏MOD</h5>
				<ul>
					<li><a href="https://www.nexusmods.com">Nexus Mods</a></li>
					<li><a href="https://steamcommunity.com/workshop">steam创意工坊</a></li>
					<li><a href="https://mod.3dmgame.com">3DM Mod站</a></li>
					<li><a href="https://down.gamersky.com/oth/mod">游民星空下载站</a></li>
					<li><a href="https://patch.ali213.net/">游侠补丁</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>

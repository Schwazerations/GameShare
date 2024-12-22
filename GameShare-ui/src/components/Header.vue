<template>
<article class="header">
	<!-- 最左侧的文本 -->
	<section class="left-text">
		<p v-if=" $route.fullPath =='/' ">GameShareWebsite</p><!-- 判断uri，若为index页面则发生修改；当前路径为：{{ $route.fullPath }} -->
		<router-link v-else to="/">返回首页</router-link>
	</section>

	<!-- 中间的文本 -->
	<section class="middle-text">
		<template v-if="isAuth()">
			<p :title="getName()">欢迎您，{{ getName() }} </p>
			<p class="logout" @click="logout()">登出</p>
		</template>
		<router-link v-else to="/LR" >登录﹠注册</router-link>
	</section>

	<!-- 最右侧的个人资料logo-->
	 <section class="right-text">
		<router-link :to="{ name:'Profile' }" class="profile">
			<img src="/src/assets/images/profile.png" height="28px" title="个人资料" >
		</router-link>
	</section>
</article>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { getDecodedToken } from '@/utils/auth.js'
import util, { startLoading, endLoading } from '@/utils/public.js'

const router = useRouter()
let jwt = ref(getDecodedToken());
console.debug('jwt:', jwt.value)


const isAuth = () => {
	return !util.isNull(jwt.value);
}
const getName = () => {
	return jwt.value.uname
}

const logout = () => {
	startLoading()
	localStorage.removeItem('Token')
	endLoading()
	router.push('/LR')
	ElMessage({ message: '登出成功', type: 'success' })
}
</script>

<style scoped>
/*****************************<!-- 页眉 -->*********/
*{
	line-height:45px; /*  子元素全部垂直居中  */
}
.header {
	height: 45px;
	color:#ffffff;
	background-color: #4d4343;
	margin: 0px auto;
	display: flex;
	justify-content: space-between;
	align-items: center;
	flex-direction: row;
}
.left-text{
	margin-left: 20px;
	font-size: 22px;
	font-weight: 600;
	color:#ffffff;
	line-height: 45px;
	min-width: 216px;
}
.middle-text{
	font-size:15px;
	margin-left: 700px;
	display: flex;
	flex-direction: row;
}
.middle-text a{	/* 注意这里是router-link内置的a标签的样式，只能用后代选择器 */
	padding-left:60px;
	color:#ffffff;
}
.middle-text .logout{
	padding-left:60px;
	cursor: pointer;
}
.right-text a:hover {
	color: #f10215;
}

/* <!-- profile（个人信息） --> */
.profile {
	float:right;
	position: relative;/**/
	height: 45px;
	background-color: #919198;
}
.profile > img {
	margin-top: 8px;
	margin-left: 10px;
	margin-right: 10px;
}
</style>
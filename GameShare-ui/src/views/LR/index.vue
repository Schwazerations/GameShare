<template>
<button @click="toggle_Validate">
<span v-if="isValidate">关闭前台验证</span>
<span v-if="!isValidate">开启前台验证</span></button>

	<div class="container" :class="{ 'right-panel-active': isActive }">
        <!-- 注册 -->
        <Register/>
        <!-- 登录 -->
        <Login/>
        <!-- 两边的蒙版 -->
		<Overlay @click_Overlay_Btn="click_Overlay_Btn"/>
	</div>
</template>

<script>
import Register from './Register.vue'
import Login from './Login.vue'
import Overlay from './Overlay.vue'

export default {
  components: {
	 Register, 
	 Login,
	 Overlay, 
  },
  data() {
    return {
        isValidate: false,       // 前台验证的总开关，管理员手动按需更改；测试用
        isActive: false,        // 负责蒙版css切换
    }
  },
  created() {
    if(this.isValidate)
        localStorage.setItem('isValidate', true);
    else 
        localStorage.setItem('isValidate', false);
  },
  methods:{
    clickFlushCheckCode() {
        this.$refs.checkinput.onclick = () => {
            const date = new Date().getTime();
            img.src = "CheckCodeServlet?" + date;
        } 
    },
    click_Overlay_Btn(){
        this.isActive = !this.isActive;     // 切换css，使得蒙版居右或居左
    },
    toggle_Validate(){
        this.isValidate = !this.isValidate;
        localStorage.setItem('isValidate', this.isValidate);
    }

  },

}
</script>

<style scoped>
.container {
    margin: 20px auto 0px;
    background:#fff;
    border-radius:20px;
    box-shadow:0 14px 28px rgba(0,0,0,.25),0 10px 10px rgba(0,0,0,.22);
    position:relative;
    overflow:hidden;
    width:1000px;
    max-width:100%;
    height:750px;
}



/* Move signin to right */
.right-panel-active :deep() .sign-in {
    transform:translateY(100%);
}
/* Move overlay to left */
.right-panel-active :deep() .overlay-container {
    transform:translateX(-100%);
}
/* Bring signup over signin */
.right-panel-active :deep() .sign-up {
    transform:translateX(100%);
    opacity:1;
    z-index:5;
}
/* Move overlay back to right */
.right-panel-active :deep() .overlay {
    transform:translateX(50%);
}
/* Bring back the text to center */
.right-panel-active :deep() .mask-left {
    transform:translateY(0);
}
/* Same effect for right */
.right-panel-active :deep() .mask-right {
    transform:translateY(50%);
}
.error{
    color: #ff0000;
}
</style>

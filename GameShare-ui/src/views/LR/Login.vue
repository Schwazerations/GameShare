<template>
    <!-- 登录 -->
    <div class="form-container sign-in">
        <form @submit.prevent="preLogin" class="sign" method="post">
            <h1>登录</h1>

            <input v-model="formData.utel" type="text" autocomplete="username" name="utel" placeholder="手机号（*必填）" />

            <input v-model="formData.upsw" type="password" autocomplete="password" name="upsw" placeholder="密码（*必填）" />

            <input v-model="formData.checkinput" name="checkinput" placeholder="验证码（*必填）" value="" class="checkinput" >
            <div class="login-code">
                <img :src="codeUrl" @click="getCode" class="login-code-img" alt="验证码图片"/>
            </div>
            <!-- <a href="jsp_admin/admin_login.jsp">管理员登录</a> -->
            <input type="submit" value="登录">
        </form>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus'
import util from '@/utils/public'
import auth from '@/utils/auth'
import { login, getCodeImg } from '@/api/login'

const router = useRouter()
// 常量
const rules = {
    utel: {
        required: true,
        rangelength: [11, 11],
        pattern: /[0-9]{11}/
    },
    upsw: {
        required: true,
        rangelength: [8, 20],
        pattern: /[a-zA-Z0-9]{8,20}/
    },
    checkinput: {
        required: true,
    }
}
const messages = {
    utel: {
        required: '手机号不能为空',
        rangelength: "手机号长度必须为11位",
        pattern: "格式错误：手机号格式必须为数字"
    },
    upsw: {
        required: '密码不能为空',
        rangelength: "密码长度在8~20位",
        pattern: "格式错误：密码必须包含数字或大小写字母"
    },
    checkinput: {
        required: "验证码不能为空",
    }
}
// 响应式变量
let codeUrl = ref(null)
let formData = ref({
    utel: null,
    upsw: null,
    checkinput: null,
    uuid: null
})


const preLogin = () => {
    if (localStorage.getItem('isValidate') == 'false') {
        doLogin();
        console.debug('跳过验证');
        return
    }
    const utel = formData.utel;
    const upsw = formData.upsw;
    const _utel = rules.utel;
    const _upsw = rules.upsw;
    let errorMsgs = [];
    if (_utel.required && util.isNull(utel)) errorMsgs.push(messages.utel.required);    // 手机号不能为空
    if (_upsw.required && util.isNull(upsw)) errorMsgs.push(messages.upsw.required);    // 密码不能为空

    if (!util.isNull(utel)) if (utel.length < _utel.rangelength[0] || utel.length > _utel.rangelength[1])    // 手机号长度不满足
        errorMsgs.push(messages.utel.rangelength);
    if (!util.isNull(upsw)) if (upsw.length < _upsw.rangelength[0] || upsw.length > _upsw.rangelength[1])    // 密码长度不满足
        errorMsgs.push(messages.upsw.rangelength);

    if (!util.isNull(utel)) if (!_utel.pattern.test(utel)) errorMsgs.push(messages.utel.pattern);   // 手机号格式不正确
    if (!util.isNull(upsw)) if (!_upsw.pattern.test(upsw)) errorMsgs.push(messages.upsw.pattern);   // 密码格式不正确


    // 验证失败：报出错误信息 or 验证通过：提交表单
    if (errorMsgs.length > 0) {
        alert(errorMsgs.join('\n'));
    } else {
        doLogin();
    }

}

const doLogin = () => {
    login(formData.value).then(res => {
        ElMessage({ message: '登录成功', type: 'success' })
        auth.setToken(res.data.Token)
        console.debug('登录成功, ', auth.getDecodedToken());
        router.push({ name: 'Index' });
    })
}

const getCode = () => {
    getCodeImg().then(res=>{
        formData.value.uuid = res.data.uuid
        codeUrl.value = "data:image/gif;base64," + res.data.img;	// Base64格式加载验证码
    })
}

getCode();

</script>



<style scoped>
h1 {
    font-weight:bold;
}
p {
    font-size:14px;
    line-height:10px;
    margin:20px 0 20px;
}
span {
    font-size:12px;
}
a {
    color:#333;
    font-size:14px;
    text-decoration:none;
    margin:15px 0;
}
button {
    border-radius:15px;
    border:1px solid #20b2aa ;
    background:#20b2aa;
    width: 100px;
    height: 40px;
    line-height: 30px;
    color:#fff;
    font-size:17px;
    font-weight:bold;
    letter-spacing:4px;
    transition:transform 80ms ease-in;
    cursor:pointer;
}
button:active {
    transform:scale(.90);
}
button:focus {
    outline:none;
}


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

.form-container form {
    background:#fff;
    display:flex;
    flex-direction:column;
    /* padding:0 50px; */
    height:100%;
    justify-content:center;
    align-items:center;
    /* text-align:center; */
}

.form-container input {
    background:#eee;
    border:none;
    padding:12px 15px;
    margin:8px 0;
    width:300px;
    outline:none;
}
.form-container {
    position:absolute;
    top:0;
    height:100%;
    transition:all .6s ease-in-out;
}
.sign-in {
    left:0;
    width:50%;
    z-index:2;
}
</style>
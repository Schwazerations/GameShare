<template>
    <div class="form-container sign-up">
        <el-form 
            ref="formInstance" 
            style="max-width: 600px"
            :model="registerForm" 
            :rules="rules" 
            label-position="left" 
            label-width="auto" 
            class="register"
            status-icon
        >
            <h1 style="margin-bottom: 15px;">注册</h1>
            <el-form-item prop="uname" >
                <el-input v-model="registerForm.uname" type="text" placeholder="用户名*" >
                    <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
                </el-input>
            </el-form-item>
            <el-form-item prop="utel">
                <el-input v-model="registerForm.utel" type="text" placeholder="手机号*" autocomplete="off"/>
            </el-form-item>
            <el-form-item prop="upsw">
                <el-input v-model="registerForm.upsw" type="password" placeholder="密码*" autocomplete="off"/>
            </el-form-item>
            <el-form-item prop="second_pwd">
                <el-input v-model="registerForm.second_pwd" type="password" placeholder="确认密码*" autocomplete="off"/>
            </el-form-item>
            <el-form-item prop="checkinput" class="register-code-item">
                <el-input v-model.number="registerForm.checkinput" type="text" placeholder="验证码*" class="register-code-input"/>
                <div class="register-code-div">
                    <img :src="codeUrl" @click="getCode" alt="验证码图片"/>
                </div>
            </el-form-item>
            <el-form-item prop="uemail">
                <el-input v-model="registerForm.uemail" type="text" placeholder="邮箱" autocomplete="off"/>
            </el-form-item>
            <el-form-item prop="ugender">
                <el-input v-model="registerForm.ugender" type="text" placeholder="性别" autocomplete="off"/>
            </el-form-item>
            <el-form-item prop="uaddress">
                <el-input v-model="registerForm.uaddress" type="text" placeholder="地址" autocomplete="off"/>
            </el-form-item>

            <el-button type="primary"  @click.native.prevent="preRegister">注册</el-button>
        </el-form>
    </div>
</template>


<script setup lang="ts">
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage, FormInstance, type FormRules } from 'element-plus'
import { register, getCodeImg } from "@/api/login";
import auth from '@/utils/auth'

interface RegisterForm {
    uname: string,
    utel: string,
    upsw: string,
    second_pwd: string,
    checkinput: string,
    uemail: string,
    ugender: string,
    uaddress: string,
    uuid: string
}

const router = useRouter();
const formInstance = ref<FormInstance>(); 
const registerForm = reactive<RegisterForm>({
    uname: '',
    utel: '',
    upsw: '',
    second_pwd: '',
    checkinput: '',
    uemail: '',
    ugender: '',
    uaddress: '',
    uuid: ''
})

let codeUrl = ref(null)
const rules = reactive<FormRules<RegisterForm>>({
    uname: [
        { required: true, message: '用户名不能为空' , trigger: 'blur' },
        { min: 1, max: 20, message: '用户名长度在1~20位' , trigger: 'blur' }
    ],
    utel: [
        { required: true, message: '手机号码不能为空' , trigger: 'blur' },
        { min:1, max:11, message: '手机号长度不能超过11位' , trigger: 'blur' },
        { pattern: /^1\d{10}$/ , message: '不是合法的中国大陆手机号' , trigger: 'blur' }
    ],
    upsw: [
        { required: true, message: '密码不能为空' , trigger: 'blur' },
        { min:8, max:20, message: '密码长度在8~20位' , trigger: 'blur' },
        { pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,20}$/, message: '密码必须包含至少一个小写字母、一个大写字母和一个数字', trigger: 'blur' }
    ],
    second_pwd: [
        { required: true, message: '请再次输入您的密码' , trigger: 'blur' },
        { validator: (rule, value, callback) => {
            if (value !== registerForm.upsw) {
                return callback(new Error('两次密码不一致'))
            } else {
                callback()
            }
        }, trigger: 'blur' }
    ],
    checkinput: [
        { required: true, message: '验证码不能为空' , trigger: 'blur' },
        { type: 'number', min:0, max:100, message: '验证码不合法' , trigger: 'blur' }
    ],
    uemail: [
        { pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/, message: '不是合法的邮箱地址' , trigger: 'blur' }
    ]
})

const getCode = () => {
    getCodeImg().then(res=>{
        registerForm.uuid = res.data.uuid
        codeUrl.value = "data:image/gif;base64," + res.data.img;	// Base64格式加载验证码
    })
}

/* 初始化验证码 */
getCode();

const preRegister = () => {
    formInstance.value.validate(valid => {
        if (valid) {
            doRegister();
        } else {
            console.log('error submit!!');
        }
    })
}

const doRegister = () => {
    register(registerForm).then(res=>{
        ElMessage({ message: '注册成功，已自动视为已登录', type: 'success' })
        auth.setToken(res.data.Token)
        console.debug('注册成功, ', auth.getDecodedToken());
        router.push({ name: 'Index' });
    })
}

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
button :active {
    transform:scale(.90);
}
button :focus {
    outline:none;
}
.el-input{
    width: 250px;
}


.form-container form {
    background:#fff;
    display:flex;
    flex-direction:column;
    height:100%;
    justify-content:center;
    align-items:center;
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
.sign-up {
    left:0;
    width:50%;
    z-index:1;
    opacity:0;
}

/* 验证码 */
.register-code-item {
    width: 50%;
}
.register-code-input {
    width: 30%;
    flex-grow: 2;
}
.register-code-div {
  margin: 10px 0 0 10px; 
}
.register-code-div img {
    width: 120px;
}


</style>
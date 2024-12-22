import axios from 'axios'
import { ElMessage } from 'element-plus'
import { saveAs } from 'file-saver'
import { getRawToken } from '@/utils/auth'
import { startLoading, endLoading } from '@/utils/public'
import errorCode from '@/utils/errorCode'

// 设置默认Content-Type
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'

// 创建axios实例
const service = axios.create({
    baseURL: 'http://localhost:8080/',
    timeout: 10000
})

// 请求拦截器，接受的参数：config => {url, headers{setToken}, method, data}
service.interceptors.request.use(
  config => {
    startLoading()
    const setToken = (config.headers || {}).setToken !== false   // 是否需要设置 token
    if (getRawToken() && setToken) {
      config.headers.Authorization = `Bearer ${getRawToken()}`; // 让每个请求携带自定义token
    }
  // // get请求映射params参数
  // if (config.method === 'get' && config.params) {
  //   let url = config.url + '?' + tansParams(config.params);
  //   url = url.slice(0, -1);
  //   config.params = {};
  //   config.url = url;
  // }
    return config;
  }, 
  error => {
    endLoading()
    return Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(
res => {
  console.log('response', res)
  endLoading();    // 结束加载动画
  const code = res.data.code || 200;  // 未设置状态码则默认成功状态
  const msg = errorCode[code] || res.data.message || errorCode['default']  // 获取错误信息

  if (code === 200) {  // 正常返回
    return res.data;  // 剥一层
  } else {
    if (code === 401) {
      ElMessage({ message: '登录信息失效，请重登', type: 'error' });
    } else {
      const errorCode = res.data.data.ErrorCode
      console.log('errorCode', errorCode)
      if (errorCode === 'user.captcha.invalid') {
        ElMessage({ message: '输入的验证码错误或验证码已过期，请刷新后重新输入验证码', type: 'warning' });
      } else if(errorCode === 'user.not.found') {
        ElMessage({ message: '用户不存在，请重新输入', type: 'warning' });
      } else if (errorCode === 'user.username.password.invalid') {
        ElMessage({ message: '手机号或密码不合法，请重新输入', type: 'warning' });
      } else if (errorCode === 'user.password.not.match') {
        ElMessage({ message: '密码不对，请重新输入', type: 'warning' });
      } else if (errorCode === 'user.register.utel.duplicate') {
        ElMessage({ message: '该手机号已注册，请重新输入', type: 'warning' });
      } else if (errorCode === 'user.login.input.invalid') {
        ElMessage({ message: res.data.data.ErrorMessage, type: 'warning' });  // 后端代替前端校验时，异常信息由后端返回的数据决定
      }
      else {
        ElMessage({ message: '系统未知异常，请呼叫管理员：' + msg, type: 'error' });
      }
    }
    return Promise.reject('error');
  }
},
error => {
  endLoading();
  console.error('err', error)
  ElMessage({ message: `服务器抢修中，请耐心等待。${error}`, type: 'error' })

  return Promise.reject(error);
}
)

// 通用下载方法
export function download(url, filename) {
  startLoading()
  service.get(url, {
    'responseType': 'blob'
  })
  .then(res => {
    endLoading()
    const blob = new Blob([res])
    saveAs(blob, filename)
  })
  .catch(err => {
    endLoading()
    ElMessage({ message: `下载失败！请呼叫管理员。${err}`, type: 'error' })
  })
} 


export default service
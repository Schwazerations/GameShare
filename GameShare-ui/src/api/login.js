import request from '@/utils/request'

export function login(fromData){
    return request({
        url: '/login',
        method: 'post',
        data: fromData,
        headers: {
            'setToken': false   // 不携带Token
        }
    })
}

export function register(formData) {
    return request({
        url: '/register',
        method: 'post',
        data: formData,
        headers: {
            'setToken': false   // 不携带Token
        }
    })
}

export function getCodeImg() {
    return request({
        url: '/captchaImage',
        method: 'get',
        headers: {
            'setToken': false   // 不携带Token
        }
    })
}
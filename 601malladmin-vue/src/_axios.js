import axios from "axios";
import router from "./router";
import Element from "element-ui";

axios.defaults.baseURL = "http://jysx.xyz:8082";

const request = axios.create({
    timeout: 5000,
    headers: {
        // 'Content-Type': 'application/json; charset=utf-8'
        'Content-Type': 'application/x-www-form-urlencoded'
    },
});

request.interceptors.request.use(config => {
    let token = localStorage.getItem("token");
    // console.log("请求拦截：" + token);
    if (token) {
        // config.headers['Authorization'] = token;
        config.headers['token'] = token;
    }
    return config;
})

request.interceptors.response.use(
    response => {
        let result = response.data;
        if(result.code === 200) {
            return response;
        } else {
            Element.Message.error(result.msg?result.msg:'系统异常');
            return Promise.reject(response.data.msg);
        }
    },
    error => {
        console.log(error);
        if(error.response) {
            if(error.response.data) {
                error.message = error.response.data.msg;
            }
            if(error.response.status === 401) {
                router.push("/admin/login");
            }
        }
        Element.Message.error(error.message);
        return Promise.reject(error);
    }
)

export default request
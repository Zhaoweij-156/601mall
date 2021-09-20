const Mock = require("mockjs")

const Random = Mock.Random

let Result = {
    code: 200,
    msg: '操作成功',
    data: null,
    token: Random.string(32),
}

// 登录
Mock.mock("/admin/captcha", 'post', () => {
    Result.data = {
        captchaImageUrl: Random.dataImage('100x40', 'p7n5w'),
    };
    return Result;
})
Mock.mock("/admin/login", 'post', () => {
    Result.code = 200;  // 200
    Result.msg = '登录成功';    // 登录成功
    Result.data = {
        admin: {
            id: 1,
            username: 'fjz',
            email: '123456@163.com',
            mobile: '12345678910',
            status: '1',
            createUserId: 0,
            createTime: '2021-07-26 00:42:48',
            modifiedTime: '2021-07-26 00:42:50'
        },
    }
    return Result;
})

// 菜单
Mock.mock("/sys/menu/nav", 'get', () => {
    let nav = [
        {
            name: "SysManage",
            title: "系统管理",
            icon: "el-icon-s-operation",
            component: "",
            path: "",
            children: [
                {
                    name: "CategoryManage",
                    title: "商品分类",
                    icon: "el-icon-menu",
                    component: "/systemComponent/CategoryManage",
                    path: "/admin/sys/categoryManage",
                    children: [],
                },
                {
                    name: "GoodsManage",
                    title: "商品管理",
                    icon: "el-icon-s-shop",
                    component: "",
                    path: "",
                    children: [
                        {
                            name: "SpuManage",
                            title: "SPU商品管理",
                            icon: "el-icon-goods",
                            component: "/systemComponent/SpuManage",
                            path: "/admin/sys/spuManage",
                            children: [],
                        },
                        {
                            name: "SkuManage",
                            title: "SKU商品管理",
                            icon: "el-icon-s-goods",
                            component: "/systemComponent/SkuManage",
                            path: "/admin/sys/skuManage",
                            children: [],
                        },
                    ],
                },
                {
                    name: "SeckillManage",
                    title: "秒杀商品管理",
                    icon: "el-icon-star-on",
                    component: "/systemComponent/SeckillManage",
                    path: "/admin/sys/seckillManage",
                    children: [],
                },
                {
                    name: "OrderManage",
                    title: "订单管理",
                    icon: "el-icon-s-order",
                    path: "",
                    children: [
                        {
                            name: "OrderDeliver",
                            title: "订单发货",
                            icon: "el-icon-s-check",
                            path: "/admin/sys/orderDeliver",
                            children: [],
                        },
                        {
                            name: "OrderReturn",
                            title: "订单退货",
                            icon: "el-icon-s-release",
                            path: "/admin/sys/orderReturn",
                            children: [],
                        },
                    ],
                },
                {
                    name: "UserManage",
                    title: "用户管理",
                    icon: "el-icon-s-custom",
                    component: "/systemComponent/UserManage",
                    path: "/admin/sys/userManage",
                    children: [],
                },
            ],
        },
        {
            name: "SysTools",
            title: "系统工具",
            icon: "el-icon-s-tools",
            component: "",
            path: "",
            children: [],
        },
    ];
    let authoritys = [];
    Result.data = {
        nav: nav,
        authoritys: authoritys,
    }

    return Result;
})

// 商品类别
Mock.mock("/admin/allcategory?flag=1", 'get', () => {
    Result.data = [
        {
            id: 1,
            name: '王1虎',
            showStatus: 1,
            icon: ''
        },
        {
            id: 2,
            name: '王2虎',
            showStatus: 1,
            icon: ''
        },
        {
            id: 3,
            name: '王3虎',
            showStatus: 1,
            icon: ''
        },
    ];
    return Result;
})
Mock.mock("/admin/allcategory?flag=0", 'get', () => {
    Result.data = [
        {
            id: 4,
            name: '王4虎',
            showStatus: 0,
            icon: ''
        },
        {
            id: 5,
            name: '王5虎',
            showStatus: 0,
            icon: ''
        },
        {
            id: 6,
            name: '王6虎',
            showStatus: 0,
            icon: ''
        },
    ];
    return Result;
})
Mock.mock("/admin/category", 'post', () => {
    return Result;
})
Mock.mock("/admin/category", 'put', () => {
    return Result;
})
Mock.mock("/admin/category?id=1", 'delete', () => {
    return Result;
})

// 用户管理
Mock.mock("/admin/allmember?pageNo=1&pageSize=10", 'get', () => {
    let records = [];
    for(let i = 0; i < 10; i++) {
        let obj = {
                id: (i+1),
                username: 'fjzkuroko',
                password: '123456',
                salt: '123456',
                nickname: 'qqq',
                mobile: '12345678910',
                email: '123456@163.com',
                avatar: '',
                gender: '男',
                birth: '2021-07-31',
                city: '广州',
                job: '程序猿',
                sign: '你好啊！',
                status: 1,
                createTime: '2021-07-31 23:34:22',
                modifiedTime: '2021-07-31 23:34:24',
            };
        if(i >= 5) {
            obj.status = 0;
            obj.gender = '女'
        }
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 20,
        size: 10,
        current: 1,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 2,
    };
    return Result;
})
Mock.mock("/admin/allmember?pageNo=2&pageSize=10", 'get', () => {
    let records = [];
    for(let i = 0; i < 10; i++) {
        let obj = {
            id: (i+1),
            username: 'fjzkuroko',
            password: '123456',
            salt: '123456',
            nickname: 'qqq',
            mobile: '12345678910',
            email: '123456@163.com',
            avatar: '',
            gender: '女',
            birth: '2021-07-31',
            city: '广州',
            job: '程序猿',
            sign: '你好啊！',
            status: 0,
            createTime: '2021-07-31 23:34:22',
            modifiedTime: '2021-07-31 23:34:24',
        };
        if(i >= 5) {
            obj.status = 1;
            obj.gender = '男'
        }
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 20,
        size: 10,
        current: 2,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 2,
    };
    return Result;
})
Mock.mock("/admin/allmember?pageNo=1&pageSize=20", 'get', () => {
    let records = [];
    for(let i = 0; i < 20; i++) {
        let obj = {
            id: (i+1),
            username: 'fjzkuroko',
            password: '123456',
            salt: '123456',
            nickname: 'qqq',
            mobile: '12345678910',
            email: '123456@163.com',
            avatar: '',
            gender: '男',
            birth: '2021-07-31',
            city: '广州',
            job: '程序猿',
            sign: '你好啊！',
            status: 1,
            createTime: '2021-07-31 23:34:22',
            modifiedTime: '2021-07-31 23:34:24',
        };
        if(i >= 10) {
            obj.status = 0;
            obj.gender = '女'
        }
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 20,
        size: 20,
        current: 1,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 1,
    };
    return Result;
})
Mock.mock("/admin/changestatus", 'post', () => {
    return Result;
})
Mock.mock("/admin/member?id=1", 'get', () => {
    Result.data = {
        id: 1,
        username: 'fjzkuroko',
        password: '123456',
        salt: '123456',
        nickname: 'qqq',
        mobile: '12345678910',
        email: '123456@163.com',
        avatar: '',
        gender: '男',
        birth: '2021-07-31',
        city: '广州',
        job: '程序猿',
        sign: '你好啊！',
        status: 1,
        createTime: '2021-07-31 23:34:22',
        modifiedTime: '2021-07-31 23:34:24',
        consumeAmount: 1,
        couponAmount: 1,
        orderCount: 1,
        returnOrderCount: 1,
        loginCount: 1,
    };
    return Result;
})
Mock.mock("/admin/member?id=2", 'get', () => {
    Result.data = {
        id: 2,
        username: 'fjzkuroko',
        password: '123456',
        salt: '123456',
        nickname: 'qqq',
        mobile: '12345678910',
        email: '123456@163.com',
        avatar: '',
        gender: '女',
        birth: '2021-07-31',
        city: '广州',
        job: '程序猿',
        sign: '你好啊！',
        status: 0,
        createTime: '2021-07-31 23:34:22',
        modifiedTime: '2021-07-31 23:34:24',
        consumeAmount: 1,
        couponAmount: 1,
        orderCount: 1,
        returnOrderCount: 1,
        loginCount: 1,
    };
    return Result;
})

// 订单发货
Mock.mock("/admin/order/all?pageNo=1&pageSize=10", 'get', () => {
    let records = [];
    for(let i = 0; i < 10; i++) {
        let obj = {
            id: (i+1),
            memberId: 1,
            orderSn: '123',
            memberUsername: 'fjz',
            status: null,
            deleteStatus: 0,
        };
        if(i >= 5) {
            obj.deleteStatus = 1;
        }
        if(i <= 5) {
            obj.status = i;
        } else {
            obj.status = 3;
        }
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 20,
        size: 10,
        current: 1,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 2,
    };
    return Result;
})
Mock.mock("/admin/order/all?pageNo=2&pageSize=10", 'get', () => {
    let records = [];
    for(let i = 0; i < 10; i++) {
        let obj = {
            id: (i+1),
            memberId: 1,
            orderSn: '123',
            memberUsername: 'fjz',
            status: null,
            deleteStatus: 0,
        };
        if(i >= 5) {
            obj.deleteStatus = 1;
        }
        if(i <= 5) {
            obj.status = i;
        } else {
            obj.status = 5;
        }
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 20,
        size: 10,
        current: 2,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 2,
    };
    return Result;
})
Mock.mock("/admin/order/all?pageNo=1&pageSize=20", 'get', () => {
    let records = [];
    for(let i = 0; i < 20; i++) {
        let obj = {
            id: (i+1),
            memberId: 1,
            orderSn: '123',
            memberUsername: 'fjz',
            status: null,
            deleteStatus: 0,
        };
        if(i >= 10) {
            obj.deleteStatus = 1;
        }
        if(i <= 5) {
            obj.status = i;
        } else {
            obj.status = 3;
        }
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 20,
        size: 20,
        current: 1,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 1,
    };
    return Result;
})
Mock.mock("/admin/order/all?pageNo=1&pageSize=10&deleteStatus=0", 'get', () => {
    let records = [];
    for(let i = 0; i < 5; i++) {
        let obj = {
            id: (i+1),
            memberId: 1,
            orderSn: '123',
            memberUsername: 'fjz',
            status: null,
            deleteStatus: 0,
        };
        if(i <= 5) {
            obj.status = i;
        } else {
            obj.status = 3;
        }
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 5,
        size: 10,
        current: 1,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 1,
    };
    return Result;
})
Mock.mock("/admin/order/all?pageNo=1&pageSize=10&deleteStatus=1", 'get', () => {
    let records = [];
    for(let i = 0; i < 5; i++) {
        let obj = {
            id: (i+1),
            memberId: 1,
            orderSn: '123',
            memberUsername: 'fjz',
            status: null,
            deleteStatus: 1,
        };
        if(i <= 5) {
            obj.status = i;
        } else {
            obj.status = 3;
        }
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 5,
        size: 10,
        current: 1,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 1,
    };
    return Result;
})
Mock.mock("/admin/order/deliver", 'post', () => {
    return Result;
})
Mock.mock("/admin/order/detail?id=1", 'get', () => {
    Result.data = {
        id: 1,
        memberId: 1,
        orderSn: '123',
        memberUsername: 'fjz',
        totalAmount: 110,
        payAmount: 110,
        freightAmount: 10,
        payType: 1,
        status: 1,
        deliveryCompany: '12222',
        deliverySn: 'dddsn',
        autoConfirmDay: 10,
        receiverName: 'kuroko',
        receiverPhone: '12345678910',
        receiverPostCode: '524400',
        receiverProvince: '广东省',
        receiverCity: '佛山市',
        receiverRegion: '南海区',
        receiverDetailAddress: '华南师范大学',
        note: '无',
        confirmStatus: 0,
        deleteStatus: 0,
        payTime: '2021-08-08 14:42:10',
        deliveryTime: '2021-08-08 14:42:12',
        receiveTime: '2021-08-08 14:42:14',
        createTime: '2021-08-08 14:42:16',
        modifiedTime: '2021-08-08 14:42:18',
        orderItems: [
            {
                id: 1,
                orderId: 1,
                orderSn: '123',
                spuId: 1,
                spuName: 'IQUNIX L80-动力方程式机械键盘',
                categoryId: 1,
                skuId: 1,
                skuName: 'IQUNIX L80-动力方程式机械键盘 三模机械无线键盘 蓝牙键盘 热插拔客制化键盘 cherry红轴RGB版',
                skuImg: 'https://img11.360buyimg.com/n1/s450x450_jfs/t1/193085/7/7351/100636/60c174caEdab6e827/31384f1f07845a56.jpg',
                skuOriginalPrice: 999,
                skuPresentPrice: 896,
                skuCount: 1,
            },
        ],
    };
    return Result;
})

// 订单退货
Mock.mock("/admin/order/return/allByUsername?pageNo=1&pageSize=10", 'get', () => {
    let records = [];
    for(let i = 0; i < 10; i++) {
        let obj = {
            id: (i+1),
            memberUsername: 'fjz',
            orderSn: '12356',
            createTime: '2021-08-07 18:37:36',
            status: 1,
        };
        if(i <= 3) {
            obj.status = i;
        } else {
            obj.status = 0;
        }
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 20,
        size: 10,
        current: 1,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 2,
    };
    return Result;
})
Mock.mock("/admin/order/return/allByUsername?pageNo=2&pageSize=10", 'get', () => {
    let records = [];
    for(let i = 0; i < 10; i++) {
        let obj = {
            id: (i+1),
            memberUsername: 'fjz',
            orderSn: '12356',
            createTime: '2021-08-07 18:37:36',
            status: 1,
        };
        if(i <= 3) {
            obj.status = i;
        } else {
            obj.status = 3;
        }
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 20,
        size: 10,
        current: 2,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 2,
    };
    return Result;
})
Mock.mock("/admin/order/return/allByUsername?pageNo=2&pageSize=20", 'get', () => {
    let records = [];
    for(let i = 0; i < 20; i++) {
        let obj = {
            id: 1,
            memberUsername: 'fjz',
            orderSn: '12356',
            createTime: '2021-08-07 18:37:36',
            status: 1,
        };
        if(i <= 3) {
            obj.status = i;
        } else {
            obj.status = 3;
        }
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 20,
        size: 20,
        current: 2,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 2,
    };
    return Result;
})
Mock.mock("/admin/order/return/allByUsername?pageNo=1&pageSize=10&username=fjz", 'get', () => {
    let records = [];
    for(let i = 0; i < 10; i++) {
        let obj = {
            id: (i+1),
            memberUsername: 'fjz',
            orderSn: '12356',
            createTime: '2021-08-07 18:37:36',
            status: 1,
        };
        if(i <= 3) {
            obj.status = i;
        } else {
            obj.status = 3;
        }
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 20,
        size: 10,
        current: 1,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 2,
    };
    return Result;
})
Mock.mock("/admin/order/return/operate", 'post', () => {
    return Result;
})
Mock.mock("/admin/order/return/detail?id=1", 'get', () => {
    Result.data = {
        id: 1,
        orderId: 1,
        orderSn: '123456',
        skuId: 1,
        createTime: '2021-08-07 18:37:36',
        memberUsername: 'fjz',
        returnAmount: 99,
        returnName: 'fjz',
        returnPhone: '12345678910',
        status: 0,
        handleTime: '2021-08-07 18:38:08',
        skuImg: 'https://img11.360buyimg.com/n1/s450x450_jfs/t1/193085/7/7351/100636/60c174caEdab6e827/31384f1f07845a56.jpg',
        skuName: 'IQUNIX L80-动力方程式机械键盘 三模机械无线键盘 蓝牙键盘 热插拔客制化键盘 cherry红轴RGB版',
        skuCount: 1,
        skuOriginalPrice: 108,
        skuPresentPrice: 99,
        reason: 'no reason',
        handleNote: '退货',
        handleMan: 'fjz',
        receiveMan: 'ttttttttttt',
        receiveTime: '2021-08-08 18:38:00',
        receiveNote: '无备注',
        receivePhone: '10987654321',
        companyAddress: '华南师范大学',
    };
    return Result;
})

// spu管理
Mock.mock("/admin/product/allforpage?pageNo=1&pageSize=10", 'get', () => {
    let records = [];
    for(let i = 0; i < 10; i++) {
        let obj = {
            spuId: 2,
            skuId: null,
            spuName: '联想小新Air15酷睿版大屏轻薄本',
            skuName: '联想小新Air15酷睿版大屏轻薄本 英特尔酷睿i5 15.6英寸全面屏办公笔记本电脑 (i5-1155G7 16G 512G 高色域 )',
            publishStatus: 1,
            defaultImg: 'https://img14.360buyimg.com/n1/s450x450_jfs/t1/189339/28/4700/72053/60ab27c9E550f7054/1e6884bd4ccc4191.jpg',
        };
        if(i >= 5) {
            obj.publishStatus = 0;
        }
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 20,
        size: 10,
        current: 1,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 2,
    };
    return Result;
})
Mock.mock("/admin/product/allforpage?pageNo=2&pageSize=10", 'get', () => {
    let records = [];
    for(let i = 0; i < 10; i++) {
        let obj = {
            spuId: 2,
            skuId: 2,
            spuName: '联想小新Air15酷睿版大屏轻薄本',
            skuName: '联想小新Air15酷睿版大屏轻薄本 英特尔酷睿i5 15.6英寸全面屏办公笔记本电脑 (i5-1155G7 16G 512G 高色域 )',
            publishStatus: 0,
            defaultImg: 'https://img14.360buyimg.com/n1/s450x450_jfs/t1/189339/28/4700/72053/60ab27c9E550f7054/1e6884bd4ccc4191.jpg',
        };
        if(i >= 5) {
            obj.publishStatus = 1;
        }
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 20,
        size: 10,
        current: 2,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 2,
    };
    return Result;
})
Mock.mock("/admin/product/allforpage?pageNo=2&pageSize=20", 'get', () => {
    let records = [];
    for(let i = 0; i < 20; i++) {
        let obj = {
            spuId: 2,
            skuId: 2,
            spuName: '联想小新Air15酷睿版大屏轻薄本',
            skuName: '联想小新Air15酷睿版大屏轻薄本 英特尔酷睿i5 15.6英寸全面屏办公笔记本电脑 (i5-1155G7 16G 512G 高色域 )',
            publishStatus: 1,
            defaultImg: 'https://img14.360buyimg.com/n1/s450x450_jfs/t1/189339/28/4700/72053/60ab27c9E550f7054/1e6884bd4ccc4191.jpg',
        };
        if(i >= 10) {
            obj.publishStatus = 0;
        }
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 20,
        size: 20,
        current: 2,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 2,
    };
    return Result;
})
Mock.mock("/admin/product/allforpage?pageNo=1&pageSize=10&categoryId=1", 'get', () => {
    let records = [];
    for(let i = 0; i < 5; i++) {
        let obj = {
            spuId: 2,
            skuId: 2,
            spuName: '联想小新Air15酷睿版大屏轻薄本',
            skuName: '联想小新Air15酷睿版大屏轻薄本 英特尔酷睿i5 15.6英寸全面屏办公笔记本电脑 (i5-1155G7 16G 512G 高色域 )',
            publishStatus: 1,
            defaultImg: 'https://img14.360buyimg.com/n1/s450x450_jfs/t1/189339/28/4700/72053/60ab27c9E550f7054/1e6884bd4ccc4191.jpg',
        };
        records.push(obj);
    }
    Result.data = {
        records: records,
        total: 5,
        size: 10,
        current: 1,
        orders: [],
        optimizeCountSql: true,
        hitCount: false,
        countId: null,
        maxLimit: null,
        searchCount: true,
        pages: 1,
    };
    return Result;
})
Mock.mock("/admin/spu", 'post', () => {
    return Result;
})
Mock.mock("/admin/spu", 'put', () => {
    return Result;
})
Mock.mock("/admin/spu?spuId=2", 'delete', () => {
    return Result;
})

// sku管理
Mock.mock("/admin/sku?skuId=2", 'get', () => {
    Result.data = {
        id: 1,
        spuId: 2,
        skuName: 'IQUNIX L80-动力方程式机械键盘 三模机械无线键盘 蓝牙键盘 热插拔客制化键盘 cherry红轴RGB版',
        skuDesc: 'IQUNIX L80-动力方程式机械键盘 三模机械无线键盘 蓝牙键盘 热插拔客制化键盘 cherry红轴RGB版',
        categoryId: 1,
        skuDefaultImg: 'https://img11.360buyimg.com/n1/s450x450_jfs/t1/193085/7/7351/100636/60c174caEdab6e827/31384f1f07845a56.jpg',
        originalPrice: 999,
        presentPrice: 896,
        saleCount: 123,
        publishStatus: 1,
        stock: 999,
        createTime: '2021-07-31 21:14:59',
        modifiedTime: '2021-07-31 21:15:01',
        skuImages: [
            {
                id: 2,
                skuId: 2,
                imgUrl: 'https://img11.360buyimg.com/n1/s450x450_jfs/t1/187986/8/7598/127562/60c174d2E66add529/511f0d06f57124cd.jpg',
                defaultImg: 0,
            },
        ],
    };
    return Result;
})
Mock.mock("/admin/sku", 'post', () => {
    return Result;
})
Mock.mock("/admin/sku", 'put', () => {
    return Result;
})
Mock.mock("/admin/sku?skuId=2", 'delete', () => {
    return Result;
})

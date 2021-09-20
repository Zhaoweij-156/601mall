const Mock = require('mockjs')

const Random = Mock.Random

let Result = {
    code: 200,
    msg: '操作成功',
    data: null,
}

Mock.mock('/captcha', 'get', () => {
    Result.data = {
        token: Random.string(32),
        captchaImg: Random.dataImage('90x37', 'p7n5w')
    }
    return Result
})

Mock.mock('/login', 'post', () => {
    //无法在header中传入数据
    Result.code = 400
    Result.msg = "验证码错误"
    return Result
})

Mock.mock('/address/getAddressList', 'post', () => {
    Result.data = {
        addressList: [
            {
                id: '1',
                name: '陈泽飚',
                phone: '111111111',
                post_code: '000000',
                province: '广东省',
                city: '佛山市',
                region: '南海区',
                detail_address: '菜鸟驿站',
                default_status: true
            },
            {
                id: '2',
                name: '臭狗屎',
                phone: '123321231',
                post_code: '000000',
                province: '广东省',
                city: '佛山市',
                region: '南海区',
                detail_address: 'oignregeroignregeroignreger',
                default_status: false
            },
        ]
    }
    return Result
})

Mock.mock('/myInfo/getMyInfo', 'post', () => {
    Result.data = {
        myInfo: {
            avatar: Random.dataImage('240x240', '吴签'),
            nickname: '墓地点歌',
            sign: '',
            gender: '男',
            birth: '2021-07-14',
            mobile: '11111111',
            job: '发当时',
            city: '曹县',
        }
    }
    return Result
})

// Mock.mock('/sku/getGoodDetail', 'post', (req) => {
//     // const {skuid} = JSON.parse(req.body)
//     const skuid = JSON.parse(req.body);
//     Result.data = {
//         ass : skuid
//     }
//FIXME 这里不知道为什么总是不能判断正确
//     if (skuid === "1")
//     {
//         Result.data = {
//             goodDetail: {
//                 sku_name: '猫城记',
//                 sku_desc: '随书附赠猫人藏书票，更适合收藏的精装小开本，初版小说完整呈现，收入自序、新序及《我怎样写猫城记》。科幻X讽喻X反乌托邦小说。入选世界科幻文库，国际影响力比肩于《骆驼祥子》',
//                 sku_default_img: null,
//                 original_price: 46,
//                 present_price: 44.2,
//                 sale_count: 0,
//                 stock: 0,
//             },
//         }
//     }
//
//
//
//         // case '2':
//         // {
//         //     Result.data = {
//         //         goodDetail: {
//         //             sku_name: '白金数据',
//         //             sku_desc: '凶手，竟然是我自己；拥有值得反复思考的深度，远远超越“推理小说”范畴。《嫌疑人X的献身》之后，推理天王东野圭吾烧脑巨献。二宫和也主演同名电影原著小说，日文版狂销170万册。',
//         //             sku_default_img: null,
//         //             original_price: 23.1,
//         //             present_price: 42,
//         //             sale_count: 0,
//         //             stock: 0,
//         //         },
//         //     }
//         // }
//     return Result
// })

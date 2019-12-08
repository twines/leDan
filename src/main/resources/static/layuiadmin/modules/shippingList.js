layui.define(['table', 'form'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form;

    //用户管理
    table.render({
        elem: '#shipping-list'
        , url: '/shipping/list.json' //模拟接口
        , cols: [[
            {field: 'id', width: 100, title: 'ID'}
            , {field: '公司名称', title: '用户名'}
            , {field: 'createdAt', title: '时间'}
            , {field: '状态', title: '状态'}
        ]]
        , parseData: function (res) { //res 即为原始返回的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                "count": res.data.total + '', //解析数据长度
                "data": res.data.records //解析数据列表
            };
        }
        , page: true
        , limit: 30
        , height: 'full-220'
        , text: '对不起，加载出现异常！'
    });

    exports('shippingList', {})
});
layui.define(['table', 'form'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form;

    //用户管理
    table.render({
        elem: '#shipping-list'
        , url: '/shipping/list.json' //模拟接口
        , cols: [[
            {field: 'id', width: 100, title: 'ID', sort: true}
            , {field: '公司名称', title: '用户名', minWidth: 100}
            , {field: '时间', title: '时间', width: 100, templet: '#imgTpl'}
            , {field: '状态', title: '状态'}
        ]]
        , page: true
        , limit: 30
        , height: 'full-220'
        , text: '对不起，加载出现异常！'
    });

    exports('shippingList', {})
});
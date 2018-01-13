/**
 * Created by ljc on 2018/1/3.
 */
var vcollege;
var vname;
var vid;
function Table(_data) {
    layui.config({
        base: Bm["path"] + '/static/js/'
    });

    layui.use(['paging', 'form'], function () {
        var $ = layui.jquery,
            paging = layui.paging(), edit
        layerTips = parent.layer === undefined ? layui.layer : parent.layer, //获取父窗口的layer对象
            layer = layui.layer, //获取当前窗口的layer对象
            form = layui.form();
        paging.init({
            openWait: true,
            url: Bm["path"] + '/student/list?isBind=1', //地址
            elem: '#content', //内容容器
            params: _data,
            type: 'POST',
            tempElem: '#tpl', //模块容器
            pageConfig: { //分页参数配置
                elem: '#paged', //分页容器
                pageSize: 5 //分页大小
            },
            success: function () { //渲染成功的回调
            },
            fail: function (msg) { //获取数据失败的回调
            },
            complate: function () { //完成的回调
                //绑定所有编辑按钮事件
                $('#content').children('tr').each(function () {
                  var $that = $(this);
                    $that.children('td:last-child').children('a[data-opt=edit]').on('click', function () {
                       vname=$that.find('td').eq(5).text();
                       vcollege=$that.find('td').eq(2).text();
                        vid=$that.attr("class");
                        $("#user_name").val(vname);
                        $("#college").val(vcollege);
                    });

                });
                //绑定所有删除按钮事件
                $('#content').children('tr').each(function () {
                    var $that = $(this);
                    $that.children('td:last-child').children('a[data-opt=del]').on('click', function () {
                        // layer.msg($(this).data('name'));
                        console.log($(this).data('id'));
                    });

                });
            },
        });
        //处理搜索表单提交
        form.on('submit(search)', function (data) {
            new Table(data.field);
            return false;
        });
    });
}

window.onload = function(){
    new Table();

}
var callbackdata = function () {
    var data = {
        vname:vname,
        vcollege:vcollege,
        vid:vid,
    };
    return data;
};
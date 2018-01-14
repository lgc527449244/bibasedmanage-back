/**
 * Created by ljc on 2018/1/3.
 */
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
            url: Bm["path"] + '/user/list', //地址
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
                        window.location.href = Bm["path"] + "/user/update.html?id=" + $(this).data('id');
                    });
                });
                //绑定所有删除按钮事件
                $('#content').children('tr').each(function () {
                    var $that = $(this);
                    $that.children('td:last-child').children('a[data-opt=del]').on('click', function () {
                        var role_id=$(this).data('id');
                        layer.msg('你确定删除么？', {
                            time: 0 //不自动关闭
                            ,btn: ['确定', '取消']
                            ,yes: function(ret){
                                $.post(Bm["path"]+"/user/delete", {'id':role_id}, function(ret){
                                    if(ret.status == "SUCCESS"){
                                        setTimeout(function () {
                                            layer.msg("删除成功",{time:1000});
                                            $("#"+role_id).remove();

                                        },1000);
                                        //因为这里面的不知道为什么执行不了  所以只能吧页面的删除操作放在外面了,
                                    }
                                });

                                //关闭弹窗
                                layer.close(ret);
                            }
                        });
                    /*    $.post(Bm["path"]+"/user/delete", {'id':$(this).data('id')}, function(ret){

                            if(ret.status == "SUCCESS"){
                                setTimeout(function () {
                                    layer.msg("删除成功",{time:1000});
                                },1000);

                                window.location.href = Bm["path"] + "/user/list.html";
                                //因为这里面的不知道为什么执行不了  所以只能吧页面的删除操作放在外面了,
                            }
                        });*/
                    });

                });
                //绑定所有查看个人想信息按钮事件
                $('#content').children('tr').each(function () {
                    var $that = $(this);

                    $that.children('td:last-child').children('a[data-opt=check]').on('click', function () {
                        if($(this).attr("id")=="教师")
                                layer.open({
                                    title: '查看教师信息',
                                    maxmin: false,
                                    type: 2,
                                    content: Bm["path"] + '/teacher/teacher-info.html?id='+$(this).data('id'),
                                    btn: ['确定', '关闭'],
                                    yes: function (index) {
                                        layer.close(index);
                                    },
                                    area: ['800px','500px']
                                });
                        if($(this).attr("id")=="学生")
                            layer.open({
                                title: '查看学生信息',
                                maxmin: false,
                                type: 2,
                                content: Bm["path"] + '/user/student-info.html?id='+$(this).data('id'),
                                btn: ['确定', '关闭'],
                                yes: function (index) {
                                    layer.close(index);
                                },
                                area: ['800px','500px']
                            });
                    });
                });
            },
        });
        //处理搜索表单提交
        form.on('submit(search)', function (data) {
            new Table(data.field);
            return false;
        });
        $('#import').on('click', function () {
            console.log(1);
            layer.open({
                title: '导入信息',
                maxmin: false,
                type: 2,
                content: Bm["path"] + '/user/excel-upload.html',
                area: ['300px', '200px']
            });
        });


    });
}


window.onload = function(){
    new Table();

}


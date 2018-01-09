/**
 * Created by ljc on 2018/1/3.
 */
function Table(_data){
    layui.config({
        base: Bm["path"] + '/static/js/'
    });
    layui.use(['paging', 'form'], function() {
        var $ = layui.jquery,
            paging = layui.paging(),
            layerTips = parent.layer === undefined ? layui.layer : parent.layer, //获取父窗口的layer对象
            layer = layui.layer, //获取当前窗口的layer对象
            form = layui.form();
        paging.init({
            openWait: true,
            url: Bm["path"] + '/operationGroup/list', //地址
            elem: '#content', //内容容器
            params: _data,
            type: 'POST',
            tempElem: '#tpl', //模块容器
            pageConfig: { //分页参数配置
                elem: '#paged', //分页容器
                pageSize: 5 //分页大小
            },
            success: function() { //渲染成功的回调
            },
            fail: function(msg) { //获取数据失败的回调
            },
            complate: function() { //完成的回调
                //绑定所有编辑按钮事件
                $('#content').children('tr').each(function() {
                    var $that = $(this);
                    $that.children('td:last-child').children('a[data-opt=edit]').on('click', function() {
                        window.location.href = Bm["path"] + "/operationGroup/update.html?id=" + $(this).data('id');
                    });

                });
                //绑定所有删除按钮事件
                $('#content').children('tr').each(function() {
                    var $that = $(this);
                    $that.children('td:last-child').children('a[data-opt=del]').on('click', function() {
                        // layer.msg($(this).data('name'));
                        operationGroup_id=$(this).data('id');
                        layer.msg('你确定删除么？', {
                            time: 0 //不自动关闭
                            ,btn: ['确定', '取消']
                            ,yes: function(data){
                                $.post(Bm["path"]+"/operationGroup/delete", {'id':operationGroup_id}, function(ret){
                                    if(ret.status == "SUCCESS"){

                                        //因为这里面的不知道为什么执行不了  所以只能吧页面的删除操作放在外面了,错误是因为明明删除了，却显示路径不再
                                    }
                                });

                                setTimeout(function () {
                                    $("#tr-"+operationGroup_id).remove();
                                    layer.msg("删除成功",{time:1000});
                                },1000);
                                //关闭弹窗
                                layer.close(data);
                            }
                        });

                    });

                });
            },
        });
        //处理搜索表单提交
        form.on('submit(search)',function (data) {
            new Table(data.field);
            return false;
        });
        $('#import').on('click', function() {
            console.log(1);
            layer.open({
                title: '导入信息',
                maxmin: false,
                type: 2,
                content: Bm["path"] + '/role/excel-upload.html',
                area: ['300px', '200px']
            });
        });
    });
}


window.onload = function(){
    new Table();

}


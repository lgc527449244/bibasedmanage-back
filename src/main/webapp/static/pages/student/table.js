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
            url: Bm["path"] + '/student/list', //地址
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
                        window.location.href = Bm["path"] + "/student/update.html?id=" + $(this).data('id');
                    });

                });
                //绑定所有删除按钮事件
                $('#content').children('tr').each(function() {
                    var $that = $(this);
                    $that.children('td:last-child').children('a[data-opt=del]').on('click', function() {
                        var id = $(this).data('id');
                        layer.confirm('确认删除？', {
                            title:"提示"
                        }, function(index){
                            $.post(Bm['path'] + "/student/delete", {id : id}, function (ret) {
                                if (ret.status == "SUCCESS"){
                                    layer.alert("删除成功");
                                    setTimeout(function () {
                                        window.location.reload();
                                    },2000);
                                }else{
                                    layer.alert(ret.result);
                                }
                            });
                            layer.close(index);
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
        //导入信息
        $('#import').on('click', function() {
            layer.open({
                title: '导入信息',
                maxmin: false,
                type: 2,
                content: Bm["path"] + '/student/excel-upload.html',
                area: ['300px', '200px'],
                cancel:function () {
                    window.location.reload();
                }
            });
        });
    });
}


window.onload = function(){
    new Table();

}


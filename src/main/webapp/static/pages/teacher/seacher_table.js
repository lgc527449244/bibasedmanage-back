/**
 * Created by ljc on 2018/1/3.
 */
  var teach_id ;
   var teach_name;
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
            url: Bm["path"] + '/teacher/list', //地址
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
                //手动添加单选框的效果
                $('#content').children('tr').each(function (){
                    var $that = $(this);
                  
                    var redio='<input type="radio" name="1" title=" "></input>';
                      $that.children('td:first-child').html(redio).on('click', function() {
                    	  teach_id  =$(this).data('id');
                    	  teach_name=$(this).data('name');
                      });
                      
                      form.render();
                })
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
                content: Bm["path"] + '/student/excel-upload.html',
                area: ['300px', '200px']
            });
        });
    });
  
}
var callbackdata = function () {
    var data = {
    	teacherName:teach_name,
    	teacherId:teach_id
    };
    return data;
};
window.onload = function(){
    new Table();
}


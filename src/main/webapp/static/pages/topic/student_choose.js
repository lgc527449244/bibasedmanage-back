/**
 * Created by ljc on 2018/1/3.
 */
 var disabled="true";
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
            var student_id = $("#studentId").val();
        paging.init({
            openWait: true,
            url: Bm["path"] + '/topic/list', //地址
            elem: '#content', //内容容器
            params: _data,
            type: 'POST',
            tempElem: '#tpl', //模块容器
            pageConfig: { //分页参数配置
                elem: '#paged', //分页容器
                pageSize: 2 //分页大小
            },
            success: function() { //渲染成功的回调
            },
            fail: function(msg) { //获取数据失败的回调
            },
            complate: function() { //完成的回调
              
                //绑定所有是否启动或停止按钮事件
                $('#content').children('tr').each(function (){
                   var $that = $(this);
                  
                     $that.children('td:last-child').children('a[data-opt=isChoose]').on('click', function() {
                   //   alert($("[data-sid='"+ $(this).data('id')+"']").text().trim());
                    	 var currenNum =$("[data-sid='"+ $(this).data('id')+"']").text();
                    	 var currenId =$(this).data('id');
                    	if(disabled=="true"){
                    		disabled="false";
                    	 if( $that.children('td:last-child').children('a[data-opt=isChoose]').text().trim()=="选择"){
                    		
                    		 $.post(Bm["path"] + "/topic/choose-topic", {id:$(this).data('id'),studentId:student_id,choose:"Y"},function(ret){
                                 if(ret.status == "SUCCESS"){
                                	 currenNum=Number(currenNum)+1;
                                	 alert($("[data-sid='"+currenId+"']").text());
                            		 $("[data-sid='"+ currenId+"']").text(currenNum);
                                	 $that.children('td:last-child').children('a[data-opt=isChoose]').text("取消");
                                	 disabled="true";
                                 }
                                 else{
                                	 disabled="true";
                                	 alert("你已经选过课题了！请先取消课题");
                                 }
                    		 });
                    		// window.location.href = Bm["path"] + "/?id=" + $(this).data('id')+"&status=DISENABLE";
                    	 }
                    	 else if( $that.children('td:last-child').children('a[data-opt=isChoose]').text().trim()=="取消"){
                    		
                    		 $.post(Bm["path"] + "/topic/choose-topic", {id:$(this).data('id'),studentId:student_id,choose:"N"},function(ret){
                                 if(ret.status == "SUCCESS"){
                                	 currenNum=Number(currenNum)-1;
                                	 $("[data-sid='"+ currenId+"']").text(currenNum);
                                	  $that.children('td:last-child').children('a[data-opt=isChoose]').text("选择");
                                	  disabled="true";
                                 }});
                    	 }
                    	 else{
                    		 disabled="true";
                    		 alert("当前人数已达上限!");
                    	 }
                    	}
                    });
                });
            },
        });
        //处理搜索表单提交
        form.on('submit(search)',function (data) {
            new Table(data.field);
            return false;
        });
      
    });
}

window.onload = function(){
    new Table();

}


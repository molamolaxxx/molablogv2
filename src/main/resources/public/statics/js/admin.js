layui.use(['layer','element'], function(){
    //加载所需模块
    var layer = layui.layer,
        element = layui.element,
        $ = layui.jquery;


    $.ajax({url: "/molablog/session/user",
        type: "get",
        dataType: "json",
        success: function (result) {
            if(result.status == 0) {
                $('#userNameTag').text(result.data.username + "，你好");

                window.userId = result.data.userId;
                window.username = result.data.username;
            }
        }
    });


    //加载博客管理页面
    load('blog')

    i=0;    //定义一个变量i以判断动画收缩
    $('#switchNav').click(function(){

        if(i==0){
            //关闭菜单栏
            $(".layui-side").animate({width:'toggle'});
            $(".layui-body").animate({left:'0px'});
            $(".layui-footer").animate({left:'0px'});
            //更换指示图标方向
            $("#switchNav .layui-icon").text("\ue65b");

            //收起二级菜单
            var className=$("#nav-item")[0].className
            //console.log(className)
            if(className=="layui-nav-item layui-nav-itemed")
            {
                //判断根节点样式,如果是打开的状态,则点击按钮
                $(".layui-nav-more").click()
            }

            i++;
        }else{
            //打开菜单栏
            $(".layui-side").animate({width:'toggle'});
            $(".layui-body").animate({left:'200px'});
            $(".layui-footer").animate({left:'200px'});
            //更换指示图标方向
            $("#switchNav .layui-icon").text("\ue65a");
            i--;
        }
    });
});

load = function(view) {
    window.searchFlag = "notOnSearch";
    layui.jquery(".layui-body").load("/molablog/statics/html/"+view+"_admin.html",function(){
    });
    sessionJudge();
}


search = function() {
    selectNav(2);
    window.searchFlag = "onSearch";
    layui.jquery(".layui-body").load("/molablog/statics/html/blog_admin.html",function(){
    });
}

loadEditor = function(isFromEdit) {
    if(isFromEdit)
        window.isFromEdit=true;
    else
        window.isFromEdit=false;
    layui.jquery(".layui-body").load("/molablog/statics/html/editor_admin.html",function(){});
    sessionJudge();
}

selectNav = function(number) {
    layui.jquery("div.layui-side dd:nth-child("+number+") > a").click();
}
clearSession =function() {
    layui.jquery.ajax({url: "/molablog/session/clear",
        type: "get",
        dataType: "json",
        success: function (result) {

            setTimeout(function () {
                location.replace("/molablog");
            },500);
        }
    });
}
//--判断是否在当前页面,返回时检查session是否过期-->

var hiddenProperty = 'hidden' in document ? 'hidden' :
    'webkitHidden' in document ? 'webkitHidden' :
        'mozHidden' in document ? 'mozHidden' :
            null;
var visibilityChangeEvent = hiddenProperty.replace(/hidden/i, 'visibilitychange');
var onVisibilityChange = function(){
    if (document[hiddenProperty]) {
        //判断session有没有失效
        sessionJudge();
    }
}
document.addEventListener(visibilityChangeEvent, onVisibilityChange);

sessionJudge = function() {
    //判断session有没有失效
    var $ = layui.jquery;
    $.ajax({
        url: "/molablog/session/user",
        type: "get",
        success: function (result) {
            //如果会话过期
            if(result.status == 31)
            {
                layui.layer.alert('会话已过期，请重新登录!', {icon: 2}, function (index) {
                    location.replace("/molablog");
                    layer.close(index)
                });
            }
        }
    });
}





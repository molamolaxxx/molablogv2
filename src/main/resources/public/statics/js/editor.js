layui.use(['jquery', 'form'], function() {
    var $ = layui.jquery,
        form = layui.form,
        $layuiBody = $(".layui-body")[0],
        $form = $('#typeForm'),
        ue = UE.getEditor('container',{autoFloatEnabled:false}),
        createTime,
        articleId,
        classId,
        title,
        content;

    function setOldData() {
        //获取传来的文章id
        articleId=window.editBlogInfo.blogId;

        classId=window.editBlogInfo.classId;

        title=window.editBlogInfo.title;

        //将消息填充至元素
        //1.填充分类
        $.ajax({
            url: "/molablog/type/"+classId,
            type: "get",
            dataType: "json",
            success: function (result) {
                initCategory(result.data.id,result.data.children?result.data.children[0].id:null);
            }
        });
        //2.填充题目
        $("#title").val(title);
        //3.获取内容,填充编辑器
        $.ajax({
            url: "/molablog/blog/content",
            type: "get",
            data:{
                "blogId":articleId
            },
            dataType: "json",
            success: function (result) {
                //添加内容

                content=result.data.content;
                ue.setContent(content);
            }
        });
    }
    //初始化操作选项：是否为新键
    appendOption($("#action"),"编辑博客",0);
    appendOption($("#action"),"新建博客",1);

    layui.jquery('#action').next().find('.layui-select-title input').click();
    setTimeout(function () {
        if(window.isFromEdit)
            layui.jquery('#action').next().find('.layui-anim').children('dd[lay-value=0]').click();
        else
            layui.jquery('#action').next().find('.layui-anim').children('dd[lay-value=1]').click();
    },10);

    setTimeout(function () {
        //根据是否来自编辑链接确定是否恢复本地保存数据
        if(!window.isFromEdit) {
            ue.execCommand('drafts');
        }
        else {
            setOldData();
        }
    }, 100);

    //富文本编辑器初始化
    ue.ready(function() {
        $.ajax({
            url: "/getTime",
            data:"format=yyyy年 MM月 dd日 HH:mm:ss",
            type: "get",
            success: function (result) {
                createTime=result;
                ue.setContent("<p>写下文字，记录生活</p><p>创建于："+result+"</p>");
            }
        });
        ue.addListener('focus',function(){
            if(/^写下文字，记录生活创建于：\d{4}年\s\d{2}月\s\d{2}日\s\d{2}:\d{2}:\d{2}$/.test(ue.getContentTxt())){
                ue.setContent("<p></p><p>创建于："+createTime+"</p>");
                ue.focus(false);
            }
        });

        window.onbeforeunload=function(e){
            if(!/^写下文字，记录生活创建于：\d{4}年\s\d{2}月\s\d{2}日\s\d{2}:\d{2}:\d{2}$/.test(ue.getContentTxt())) {
                return (e || window.event).returnValue = '内容可能尚未保存，确定离开吗';
            }
        }
    });

    //dom操作部分
    //动态加入div到最底部
    layui.jquery("#edui1").after("<div class=\"inline-button-div\">\n" +
        "    <div class=\"layui-input-inline inline-button\">\n" +
        "        <button id=\"publish\" class=\"layui-btn\" lay-filter=\"submit\">发表博客</button>\n" +
        "    </div>\n" +
        "    <div class=\"layui-input-inline inline-button\">\n" +
        "        <button id=\"draft\" class=\"layui-btn\" lay-filter=\"submit\">保存草稿</button>\n" +
        "    </div>\n" +
        "</div>");
    //改变editor position属性
    layui.jquery("#edui1_toolbarbox")
    //jquery
    var $ = layui.jquery;
    //获得所有数据
    //return data:成功　0:有元素为空  1:新建与编辑混淆
    function getAllData(published) {
        //获得编辑器实体
        var ue = UE.getEditor('container',{autoFloatEnabled:false});
        //获得文章题目
        var title;
        title = $("#title").val()
        //文章内容
        var content = ue.getContent();
        //获取纯文本内容
        var text=ue.getContentTxt();
        var classId = null;

        console.log("ca:"+categoryId+"ty:"+typeId);
        //选取博客类型
        if (typeId == null)
            classId = categoryId;
        else
            classId = typeId;

        var isEdit=$("#action").val();

        //检查元素是否为空,为空返回１
        if(!checkBeforeCommit(title,content,classId))
            return 0;

        //发送数据
        var data = {
            "id":articleId,
            "title": title,
            "classId": classId,
            "content": content,
            "text":text,
            "published": published,
            "isEdit":isEdit
        };
        //新建模式无法更改，错误返回0
        if(data.isEdit==0&&data.id==null)
            return 1;

        return data;
        //发表
    }

    //提交
    function commit(data) {
        var successMsg , errorMsg ,url,type;

        if (data.isEdit == 0){
            //更新
            url = "/molablog/blog/"+articleData.id;
            successMsg = "更新博客成功";
            errorMsg = "更新博客失败";
            type = "PUT";
        }
        else {
            //插入
            url = "/molablog/blog";
            successMsg = "添加博客成功";
            errorMsg = "添加博客失败";
            type = "POST";
        }
        //发送数据
        $.ajax({
            url: url,
            type: type,
            data: JSON.stringify({
                title : articleData.title,
                classId : articleData.classId,
                content : articleData.content,
                published : articleData.published,
                text : articleData.text,
                userId : window.userId
            }),
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                layer.msg(successMsg, {icon: 1});
                //跳转
                setTimeout(function () {
                        layui.jquery("div.layui-side dd:nth-child(2) > a").click();
                        load("blog");
                    }, 500
                );
            },
            error: function (result) {
                layer.msg(errorMsg, {icon: 2});
            }
        });
    }

    $("#publish").click(function () {
        //获取文章数据
        if((articleData=getAllData(0))==1)
        {
            console.log(articleData);
            layer.msg('请选择新建博客', {icon: 2});
            return false;
        }
        //有问题则不提交
        if(articleData==false)
            return false;

        commit(articleData);
    });
    //存草稿
    $("#draft").click(function () {
        //获取文章数据
        if((articleData=getAllData(1))==1)
        {
            layer.msg('请选择新建博客', {icon: 2});
            return false;
        }
        //有问题则不提交
        if(articleData==false)
            return false;

        commit(articleData);
    });

    //提交前的元素检查
    function checkBeforeCommit(title,content,classId) {
        if(title=="") {
            layer.msg('题目不能为空', {icon: 2});
            return false;
        }
        if(content=="") {
            layer.msg('内容不能为空', {icon: 2});
            return false;
        }
        if(classId==null){
            layer.msg('分类不能为空', {icon: 2});
            return false;
        }
        return true;
    }
    var udd = $("#updown")
    udd.isToTop = true;
    upAndDown = function() {
        if (udd.isToTop){
            var top = 0;
        }else {
            var top = 1000000;
        }
        $("#udIcon").animate({opacity:0},function(){
            if (udd.isToTop){
                $("#udIcon").removeClass("layui-icon-up")
                $("#udIcon").addClass("layui-icon-down")
            }else {
                $("#udIcon").removeClass("layui-icon-down")
                $("#udIcon").addClass("layui-icon-up")
            }
            udd.isToTop = !udd.isToTop
            $("#udIcon").animate({opacity:1})
        })

        $layuiBody.scrollTo({
            top:top,
            behavior: "smooth"
        })

    }
    // 滑动
    setTimeout(function () {
            $bar = document.getElementById("edui1_toolbarbox");
            var preStyle = $bar.style;
            $layuiBody.onscroll = function (ev) {
            var scrollTop = $layuiBody.scrollTop;
            if (scrollTop > 260) {
                // 将bar变成fix
                $bar.style = "position: fixed; z-index: 9999; margin-top: -175px; width: 67.5%;";
            }
            if (scrollTop < 200) {
                $bar.style = preStyle;
            }
        }
    }, 200);

});






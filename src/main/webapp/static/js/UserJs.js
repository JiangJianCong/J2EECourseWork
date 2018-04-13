/**
 * 设置修改
 */
function allowModify(dom) {
    dom.show();
    $("#user_name").removeAttr("disabled");
    $("#user_sex").removeAttr("disabled");
    $("#user_desc").removeAttr("disabled");
    return false;
}

/**
 *
 */
function modifyUserData() {
    $("#user_name").attr("disabled","disable");
    $("#user_sex").attr("disabled","disable");
    $("#user_desc").attr("disabled","disable");
    // alert($("#user_account").attr("userId"))
    $.ajax({
       type : "POST",
        url : "/user/update",
        data : {
            id : $("#user_account").attr("userId"),
            userName : $("#user_name").val(),
            password: $("#user_password").val(),
            sex : $("#user_sex").val(),
            userDesc : $("#user_desc").val()
        },success(result){
           if (result=="success"){
               alert("成功")
           }else {
               alert("失败")
           }
        }

    });

    return false;
}

var addnums=0;

function addNewStudent() {
    addnums++;
    if (addnums>9) {
        alert("最多只能报名9位同学")
        return;

    }
    var price = $(".order_price").attr("price");
    $(".order_price").val(price*(addnums+1))

    var cho_list = "<select class=\"choose_list uk-form-select\" onchange=\"test(this)\" onclick=\"test($(this))\">\n";
    // alert($("#choose_list_num").val())
    for (var i =0;i< $("#choose_list_num").val();i++){

        cho_list = cho_list + "<option value='"+(i+1)+"' text='"+(i+1)+"'>"+(i+1)+"</option>";
    }
    cho_list = cho_list + "</select>";
    // alert(cho_list)


    $(".stu_list").append("<div class=\"one_stu uk-form-row\" style=\"margin-top: 5px\">\n" +
        "\n" +
        "                    <label>学生学号：</label>\n" +
        "                    <input class=\"stu_num\" type=\"text\" placeholder=\"请输入学号\" name=\"studentId\"/>\n" +
        "                    <label>学生名字：</label>\n" +
        "                    <input class=\"stu_name\" type=\"text\" placeholder=\"请输入名字\" name=\"studentName\"/>\n" +cho_list+


        "                    <button type=\"button\" onclick=\"closeThis($(this).parent())\"><i class=\"uk-icon-close\">删除此学生</i></button>\n" +
        "                </div>")


    if (addnums>2){
        alert("超过3个，不能自选课，需要系统自动分配")
        $(".choose_list").attr("disabled","disabled")
    }
}

function closeThis(dom) {
    dom.remove();
    addnums--;
    var price = $(".order_price").attr("price");
    $(".order_price").val(price*(addnums+1))
}

/**
 * 下订单
 */
function makeOrder() {
    var stu_list = "";
    var one_stu = $(".stu_list").find(".one_stu");
    var len = one_stu.length;
    var chooseList = "";

    // one_stu.each(function () {
    //     var number = $(this).find(".stu_num").val();
    //     var name = $(this).find(".stu_name").val();
    //
    //     if (number==""){
    //         alert("学生学号不能为空")
    //         return false;
    //     }
    //     if (name==""){
    //         alert("学生名字不能为空")
    //         return false;
    //     }
    //     stu_list = stu_list + number + "-"+name +",";
    // })

    var number = one_stu.find(".stu_num").val();
    var name = one_stu.find(".stu_name").val();

    for (var i=0;i<len;i++){
        if (number==""){
            // alert(one_stu.find(".choose_list").val())

            alert("学号不能为空")
            return false;
        }
        if (name==""){
            alert("姓名不能为空")
            return false;
        }

        if (len < 4){
            chooseList = chooseList + one_stu.find(".choose_list").val() + ",";
        }


        stu_list = stu_list + number+"-"+name+","
        one_stu = one_stu.next();
        number = one_stu.find(".stu_num").val();
        name = one_stu.find(".stu_name").val();


    }

    // alert(chooseList)


    $(".submit_cho_course").val(chooseList)
    $(".submit_stu_list").val(stu_list)
    $(".user_order_form").submit();

    addnums=0;

    return false;



}
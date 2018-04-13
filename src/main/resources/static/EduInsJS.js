

/**
 * 修改资料
 */
function modifyEduInsData(dom) {
    $("#edu_ins_desc").attr("disabled","disable")
    $("#edu_ins_name").attr("disabled","disable")
    $("#edu_ins_location_modify").attr("disabled","disable")
    $("#bank_account").attr("disabled","disable")


    $.ajax({
        type : "POST",
        url : "/eduIns/update",
        data : {
            eduInsId : $("#edu_ins_id").val(),
            waitType : "2",
            eduInsName : $("#edu_ins_name").val(),
            eduInsDesc : $("#edu_ins_desc").val(),
            location : $("#edu_ins_location_modify").val(),
            bankAccount : $("#bank_account").val()
        },success(result){
            alert(result);
        }
    })
    dom.hide()
    return false;
}

function allowModify(dom){
    alert($("#edu_ins_location_modify").val())
    alert($("#edu_ins_name").val())

    dom.show()
    $("#edu_ins_name").removeAttr("disabled")
    $("#edu_ins_location_modify").removeAttr("disabled")
    $("#bank_account").removeAttr("disabled")
    $("#edu_ins_desc").removeAttr("disabled")
    return false;
}


var team_id=0;
var project_id=0;
var p1=1;
var p2=1;
var p3=1;
var p4=1;
var select;
function resize() {
    var width = document.body.clientWidth;
    var height = document.body.clientHeight;

    if(width<1200){
        $(".table").css("width","100%");
        $("#table_1").css("margin","12px 2% 12px 4%");
        $("#table_2").css("margin","12px 2% 12px 4%");
        $("#table_3").css("margin","12px 2% 12px 4%");
        $("#table_4").css("margin","12px 2% 12px 4%");
    }else{
        $(".table").css("width","50%");
        $("#table_1").css("margin","24px 2% 12px 4%");
        $("#table_2").css("margin","24px 4% 12px 2%");
        $("#table_3").css("margin","12px 2% 24px 4%");
        $("#table_4").css("margin","12px 4% 24px 2%");
    }

}

function listen(){
    $("tbody tr").click(function () {
        if ($(this).attr("class")!=="page") {
            $(this).siblings().css("background-color","transparent");
            $(this).css("background-color","lavender");
        }
    });

    $("tr").dblclick(function () {

    });
}

$(document).ready(function() {
    resize();
    load();
});

window.onresize = function resizeBody() {
    resize();
};

function page(m,n) {
    switch (m){
        case 1:
            break;
        case 2:
            break;
        case 3:
            p3 = n;
            load_user(team_id);
            break;
        case 4:
            break;
    }
}

function load() {
    load_team();
    load_project(team_id);
    load_user(team_id);
    load_code(project_id);
}

function load_team() {
    $.get("/teams",function (data) {
        var n = Math.ceil(data.length/8);
        for(var i=1;i<n+1;i++){
            $("#li_1").before("<li onclick='page(1,"+i+")' class=\"page-item\"><a class=\"page-link\" href=\"#\">"+i+"</a></li>");
        }
        for (var i=8*(p1-1);i<8*p1;i++) {
            if(data[i]){
                var d = data[i];

                $("#tr_1").before("<tr onclick=\"load_project_and_user(" + data[i].team_id + ",'" + data[i].team_name + "')\"><td>" +
                    "<input readonly='readonly' type='text' value='" + data[i].team_id + "'/><td>" +
                    "<input onkeyup=\"team_up('" + data[i].team_id + "',this)\" type='text' value='" + data[i].team_name + "'/></td><td>" +
                    "<input readonly='readonly' value='0' id='online_num_" + data[i].team_id + "' type='text'/></td><td>" +
                    "<input readonly='readonly' value='0' id='total_num_" + data[i].team_id + "' type='text'/></td><td style=\"width: 80px;margin-left: 5px\"><i onclick=\"team_del(" + data[i].team_id + ")\" class=\"far fa-trash-alt\"></i></td></tr>");

                $.get("/online/" + d.team_id, function (data) {
                    $("#online_num_" + d.team_id).val(data);
                });
                $.get("/team/total/" + d.team_id, function (data) {
                    $("#total_num_" + d.team_id).val(data);
                });

            }else{
                $("#tr_1").before("<tr><td></td><td></td><td></td><td></td><td></td></tr>");
            }

        }
        listen();
    });
}

function team_del(tid) {
    $.post("/team/del", {"team_id": tid}, function (delete_successfully) {
        if (delete_successfully) {
            window.location.reload();
        }
    });
}

function team_up(tid, e) {
    $.post("/team/update", {"team_id": tid, "team_name": $(e).val()}, function (data) {
        console.log(data);
    });
}

function load_project(tid) {

    $(".project_load").remove();
    $.post("/projects",{"team_id":tid},function (data) {

        var n = Math.ceil(data.length/8);
        for(var i=1;i<n+1;i++){
            $("#li_2").before("<li onclick='page(2,"+i+")' class=\"page-item project_load\"><a class=\"page-link\" href=\"#\">"+i+"</a></li>");
        }
        var maint;
        for (var i=8*(p2-1);i<8*p2;i++) {

            if(data[i]){
                if (data[i].maintainer == null) {
                    maint = "无";
                } else {
                    maint = data[i].maintainer.name;
                }
                $("#tr_2").before("<tr onclick='load_code(" + data[i].project_id + ")' class='project_load'><td>" +
                    "<input readonly='readonly' type='text' value='" + data[i].project_id + "'/><td>" +
                    "<input onkeyup=\"project_mod('" + data[i].project_id + "',this)\" type='text' value='" + data[i].project_name + "'/></td><td>" +
                    "<input readonly='readonly' type='text' value='" + maint + "'/></td><td style=\"width: 80px;margin-left: 5px\"><i onclick=\"del_project('" + data[i].project_id + "')\" class=\"far fa-trash-alt\"></i></td></tr>");
            }else{
                $("#tr_2").before("<tr class='project_load'><td></td><td></td><td></td><td></td></tr>");
            }
        }


        listen();
    });
}

function project_mod(pid, e) {
    $.post("/project/update", {"project_id": pid, "project_name": $(e).val()}, function (data) {
        console.log(data);
    });
}

function load_user(tid) {
    select = "";
    $.get("/teams", function (data) {
        for (var i = 0; i < data.length; i++) {
            var team = data[i];
            if (team.team_id === tid) {
                select = select + "<option selected='selected' value='" + team.team_id + "'>" + team.team_name + "</option>";
            } else {
                select = select + "<option value='" + team.team_id + "'>" + team.team_name + "</option>";
            }

        }

        $("#add_userd").html("<select id='add_user'>" + select + "</select>");
        $(".user_load").remove();
        $.post("/users", {"team_id": tid}, function (data) {

            var n = Math.ceil(data.length / 8);
            for (var i = 1; i < n + 1; i++) {
                $("#li_3").before("<li onclick='page(3," + i + ")' class=\"page-item user_load\"><a class=\"page-link\" href=\"#\">" + i + "</a></li>");
            }

            for (var i = 8 * (p3 - 1); i < 8 * p3; i++) {
                if (data[i]) {

                    $("#tr_3").before("<tr id='user_load_" + data[i].username + "' class='user_load'><td>" +
                        "<input readonly='readonly' class='ipt_user' type='text' id='" + data[i].username + "_user_username' value='" + data[i].username + "'/></td><td>" +
                        "<input class='ipt_user' type='text' id='" + data[i].username + "_user_name' value='" + data[i].name + "'/></td><td style='padding: 0'>" +
                        "<select class='ipt_user' id='" + data[i].username + "_user_teamname'>" + select + "</select></td><td>" +
                        // "<input class='ipt_user' type='text' id='"+data[i].team.team_name+"_user_teamname' value='"+data[i].team.team_name+"'/></td><td>" +
                        // "<input class='ipt_user' type='text' id='"+data[i].username+"_user_role' value='"+data[i].role+"'/>" +
                        "<select class='ipt_user' id='" + data[i].username + "_user_role'><option value='User'>普通用户</option><option value='Administrator'>管理员</option></select> " +
                        "</td><td style=\"width: 80px;margin-left: 5px\"><i onclick=\"del_user('" + data[i].username + "')\" class=\"far fa-trash-alt\"></i></td></tr>");
                    $("#" + data[i].username + "_user_teamname").val(data[i].team.team_id);
                    $("#" + data[i].username + "_user_role").val(data[i].role);
                } else {
                    $("#tr_3").before("<tr class='user_load'><td></td><td></td><td></td><td></td><td></td></tr>");
                }


            }

            $("#table_3 select").change(function () {
                var username = $(this).attr("id").split("_")[0];
                var name = $("#" + username + "_user_name").val();
                var team_id = $("#" + username + "_user_teamname").val();
                var role = $("#" + username + "_user_role").val();

                $.post("/user/update", {
                    "username": username,
                    "name": name,
                    "team_id": team_id,
                    "role": role
                }, function (data) {
                    console.log(data);
                    load_project(team_id);
                });
            });

            $(".ipt_user").keyup(function () {

                var username = $(this).attr("id").split("_")[0];
                var name = $("#" + username + "_user_name").val();
                var team_id = $("#" + username + "_user_teamname").val();
                var role = $("#" + username + "_user_role").val();

                $.post("/user/update", {
                    "username": username,
                    "name": name,
                    "team_id": team_id,
                    "role": role
                }, function (data) {
                    console.log(data);
                    load_project(team_id);
                });
            });


            listen();
        });
    });
}

function load_project_and_user(tid, tnm) {
    team_id = tid;
    // $(this).siblings().css("background-color","transparent");
    // $(this).css("background-color","lavender");
    $("#add_user").val(tnm);
    load_project(tid);
    load_user(tid);

}

function load_code(pid) {
    project_id = pid;
    $(".code_load").remove();
    $.get("/codes",{"project_id":pid},function (data) {
        var n = Math.ceil(data.length/8);
        for(var i=1;i<n+1;i++){
            $("#li_4").before("<li onclick='page(4,"+i+")' class=\"page-item code_load\"><a class=\"page-link\" href=\"#\">"+i+"</a></li>");
        }
        for (var i=8*(p4-1);i<8*p4;i++) {
            if(data[i]){
                var code_id = data[i].code_id;
                var code_title = data[i].code_title;
                var executable = data[i].executable;
                var mode = data[i].mode;
                $("#tr_4").before("<tr id='code_load_" + code_id + "' class='code_load'><td>" +
                    "<input readonly class='ipt_code' id='" + code_id + "_code_id" + "' type='text' value='" + code_id + "'/></td><td>" +
                    "<input class='ipt_code' id='" + code_id + "_code_title" + "' type='text' value='" + code_title + "'/></td><td>" +
                    "<input readonly='readonly' class='ipt_code' id='" + code_id + "_executable" + "' type='text' value='" + executable + "'/></td><td>" +
                    // "<select class='ipt_code' id='"+code_id+"_executable"+"'><option value=true>是</option><option value=false>否</option></select></td><td>" +
                    // "<input class='ipt_code' id='"+code_id+"_executable"+"' type='text' value='"+executable+"'/></td><td>" +
                    "<input class='ipt_code' id='" + code_id + "_mode" + "' type='text' value='" + mode + "'/></td><td style=\"width: 80px;margin-left: 5px\">" +
                    "<i onclick='del_code(" + code_id + ")' class=\"far fa-trash-alt\"></i></td></tr>");
                // $("#"+code_id+"_executable").val(executable);
                // console.log(executable);

            }else{
                $("#tr_4").before("<tr class='code_load'><td></td><td></td><td></td><td></td><td></td></tr>");
            }


        }
        $(".ipt_code").keyup(function () {

            var code_id = $(this).attr("id").split("_")[0];
            var code_title = $("#" + code_id + "_code_title").val();
            var executable = $("#" + code_id + "_executable").val();
            var mode = $("#" + code_id + "_mode").val();


            $.post("/code/update", {
                "code_id": code_id,
                "code_title": code_title,
                "executable": executable,
                "mode": mode
            }, function (data) {
                console.log(data);
            });
        });
    });

}

function add_code(project_id) {

    if (project_id === 0) {
        alert("先选择项目");
    } else {
        $.post("/code/add", {"code_title": $("#new_code_title").val(), "project_id": project_id}, function (data) {
            if (data.code_title === $("#new_code_title").val()) {
                alert("添加成功");
            }
        });
    }
}

function add_user(team_id) {
    if (team_id === 0) {
        alert("先选择团队");
    } else {
        $.post("/user/add", {
            "username": $("#user_add_username").val(),
            "team_id": team_id,
            "name": $("#user_add_name").val(),
            "role": $("#user_add_role").val()
        }, function (data) {
            if (data.username === $("#user_add_username").val()) {
                alert("添加成功");
            }
        });
    }
}

function add_team() {
    $.post("/team/add", {"team_name": $("#team_add_team_id").val()}, function (data) {
        if (data.team_name === $("#team_add_team_id").val()) {
            alert("添加成功");
            window.location.href = "/admin";
        }
    });
}

function add_project(team_id) {
    var tid = team_id;
    var pid = $("#add_project_name").val();

    $.post("/project/add", {"team_id": tid, "project_name": pid}, function (data) {
        if (data.project_name === pid) {
            alert("添加成功");
            window.location.href = "/admin";
        }
    });
}

function del_code(code_id) {
    $.post("/code/del", {"code_id": code_id}, function (delete_successfully) {
        if (delete_successfully) {
            $("#code_load_" + code_id).remove();
            load_code(project_id);
        }
    });
}

function del_user(username) {
    $.post("/user/del", {"username": username}, function (delete_successfully) {
        if (delete_successfully) {
            $("#user_load_" + username).remove();
            load_user(team_id);
        }
    });
}

function del_project(pid) {
    $.post("/project/del", {"project_id": pid}, function (delete_successfully) {
        if (delete_successfully) {
            load_project(team_id);
        }
    });
}
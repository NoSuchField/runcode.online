var stompClient = null;
var connected = false;
var cursor = null;
var username = null;
var code_id = null;
var is_maintainer = false;
var project_id = null;

var timestamp;
var parameter = $("#parameter");
var input_parameter = $("#input_parameter");
//通知相同帐号下线
function login_broadcast(){
    if (connected) {
        timestamp = ""+new Date().getTime();
        stompClient.send("/server/message", {}, JSON.stringify({
            'project_id':project_id,
            'channel': 3,
            'content':username,
            'extra':timestamp
        }));
    }
}
function connect() {
    stompClient = Stomp.over(new SockJS('/message'));
    stompClient.connect({}, function(frame) {
        connected = true;
        login_broadcast();
        stompClient.subscribe('/clients/message', function(message) {
            var response = JSON.parse(message.body);
            var text = response.content;
            var from = response.from;
            var pid = response.project_id;

            if(pid===project_id){
                switch (response.channel) {
                    case 0:
                        handleMessage(text, from);
                        break;
                    case -1:
                        if (from !== username) {
                            cursor = editor.doc.getCursor();
                            editor_fill(text);
                            editor.doc.setCursor(cursor);
                        }
                        break;
                    case 1:
                        editor.setOption("readOnly",false);
                        $("#load_mask").hide();
                        $("#console_text").show();
                        $("#btn_run").css({"cursor":"pointer","color":"black"});
                        $("#btn_run").removeAttr("disabled");
                        $("#console_text").val(new Date().toLocaleString() + ":\n" + text);
                        break;
                    case 3:
                        if(text===username&&response.extra!==timestamp){
                            logout();
                            alert("你的帐号在别处登录");
                        }
                        break;
                    case 4:
                        if (from !== username) {
                            //tabClick(text);
                            code_id = parseInt(text);
                            $(".tabs_selected").attr({"class": "tabs"});
                            $("#tab_" + text).attr({"class": "tabs_selected"});
                        }
                        break;
                }
            }

        });
    });
}

function editor_fill(text) {
    editor.getDoc().setValue(text);
    $(".CodeMirror-code div").click(function () {
        $(".CodeMirror-code div").css({"border": "#FFFFFF 1px solid"});
        $(this).css({"border": "#66CCFF 1px solid"});
    });
}

function sendMessage() {
    if (connected) {
        var input = $("#inputBox");
        stompClient.send("/server/message", {}, JSON.stringify({
            'project_id':project_id,
            'channel': 0,
            'content': input.val()
        }));
        input.val("");
    }
}

function handleMessage(msg, from) {

    if (from === username) {
        content.append("<div class=\"customer_lists clearfix\"><div class=\"header_img jimi3\" style=\"background: url(../img/mine.jpg) no-repeat center;\"><div class=\"header_img_hover\"></div></div><div class=\"bkbubble left\"><p>" + msg + "</p></div></div>");
    } else {
        content.append("<div class=\"jimi_lists clearfix\"><div class=\"header_img jimi3 fl\"></div><div class=\"bkbubble right\"><p>" + msg + "</p></div></div>");
    }
    content.scrollTop(content[0].scrollHeight);
}
var editor = CodeMirror.fromTextArea(document.getElementById("codemirror"), {
    lineNumbers: true
});

function tabClick(index) {
    code_id = index;
    if (connected && is_maintainer) {
        stompClient.send("/server/message", {}, JSON.stringify({
            'project_id':project_id,
            'channel': 4,
            'content': code_id
        }));
    }



    $(".tabs_selected").attr({"class": "tabs"});
    $("#tab_" + index).attr({"class": "tabs_selected"});
    $.post("/code/" + index, function(data) {
        editor_fill(data.content);
    });
}

//加载当前project的所有code
$.post("/code/all", function(data) {
    for (var i = 0; i < data.length; i++) {
        var title = data[i].code_title;
        code_id = data[i].code_id;

        $("#tab_new").before("<span class='tabs' id='tab_" + code_id + "' onclick='tabClick(" + code_id + ")'><i class='file alternate icon'></i>" + title + "</span>");

        if (data[i].executable) {
            var tabs = document.getElementsByClassName("tabs");
            tabClick(data[i].code_id);
            loadMode(data[i].mode);
        }
    }
});

function loadMode(mode) {
    switch (mode) {
        case "c":
            editor.setOption("mode", "text/x-c++src");
            CodeMirror.autoLoadMode(editor, "clike");
            break;
    }
}

function mtoString(data) {
    var lines = "";
    for (var i = 0; i < data.length - 1; i++) {
        lines += data[i] + "\n";
    }
    lines += data[i];
    return lines;
}


//调用Docker编译运行
function run() {
    var console_text = $("#console_text");
    var btn_run = $("#btn_run");
    var load_mask = $("#load_mask");

    //编译等待期间控制台样式
    editor.setOption("readOnly",true);

    console_text.hide();
    load_mask.show();
    btn_run.css({"color":"#AABBCC","cursor":"wait"});
    btn_run.attr("disabled", "disabled");
    console_text.text("正在编译请稍候...");
    var inputs = "";
    if (parameter.val() === "off") {
        var input_parameters = $(".parameter");
        for (var i = 0; i < input_parameters.length; i++) {
            if (input_parameters[i].value !== "") {
                inputs += input_parameters[i].value + "|";
            }
        }
        inputs = inputs.substring(0, inputs.length - 1);
    }


    stompClient.send("/server/message", {}, JSON.stringify({
        'project_id':project_id,
        'channel': 1,
        'content': editor.getValue(),
        'extra': inputs
    }));
}

function resize() {
    var width = document.body.clientWidth;
    var height = document.body.clientHeight;
    $("body").height(height - 60);
    $("#panel").height(height - 40);
    $("#online_users").height(height - 593);
    editor.setSize(width - 360, height - 242);
}

function heartbeat() {
    // if (connected) {
    //     stompClient.send("/server/message", {}, JSON.stringify({
    //         'project_id':project_id,
    //         'channel': 2
    //     }));
    //
    //     load_online_user();
    // }
}

$(document).ready(function() {
    connect();
    resize();
    $.get("/user", function(data) {
        username = data.username;
        $("#avatar").attr({"src": data.avatar});
        $("#user_name").html(data.name);
        loadProject();
        setInterval(heartbeat, 3000);
        $.get("/project", function (data) {
            //
            if (data.maintainer) {
                //         $("#maintain_status").html(data.maintainer.name + "(" + data.maintainer.username + ")正在编辑...");
                is_maintainer = (username === data.maintainer.username);
            }
        });
        load_online_user();
    });
});

window.onresize = function resizeBody() {
    resize();
};

//阻止内容相同时触发的change事件，防止死循环
//再简单点...
editor.on('beforeChange', function(cm, obj) {
    if (obj.origin === "setValue") {
        if (cm.getValue() === mtoString(obj.text)) {
            obj.cancel();
        }
    } else {
        if (is_maintainer) {
            if (cm.getValue() === mtoString(obj.text)) {
                obj.cancel();
            }
        } else {
            obj.cancel();
            $("#console_text").val(new Date().toLocaleString() + " :请先取得该文档的编辑权限!");
        }
    }
});

editor.on('change', function(cm) {
    if (connected && is_maintainer) {
        stompClient.send("/server/message", {}, JSON.stringify({
            'project_id':project_id,
            'channel': -1,
            'content': editor.getValue(),
            'extra': code_id
        }));
    }
});
//切换代码高亮模式
CodeMirror.modeURL = "js/codemirror/mode/%N/%N.js";

function change(mime, mode, selected) {
    editor.setOption("mode", mime);
    CodeMirror.autoLoadMode(editor, mode);
}

function create_code() {
    if (is_maintainer) {
        var tab_new = $("#tab_new");
        tab_new.before("<span class='tabs tab_new'><i class='file alternate icon'></i><input id='new_code_input' style='outline: none;margin: 0;padding: 0;height: 30px;line-height: 30px;border: none;background-color: transparent' placeholder='请输入文件名' type='text' /></span>");
        var new_code_input = $("#new_code_input");
        new_code_input.focus();

        new_code_input.blur(function () {
            if (new_code_input.val() === "") {
                $(".tab_new").remove();
            } else {
                $.post("/code/new", {'code_title': new_code_input.val()}, function (data) {
                    $(".tab_new").remove();
                    code_id = data.code_id;
                    var tabs = document.getElementsByClassName("tabs_selected");
                    for (var i = 0; i < tabs.length; i++) {
                        tabs[i].className = "tabs";
                    }
                    tab_new.before("<span class='tabs tabs_selected' onclick='tabClick(" + data.code_id + ")'><i class='file alternate icon'></i>" + data.code_title + "</span>");

                    $.post("/code/" + data.code_id, function (data) {
                        editor_fill(data.content);
                    });
                });
            }
        });
    } else {
        $("#console_text").val(new Date().toLocaleString() + " :请先取得该文档的编辑权限!");
    }
}

function m_input_focus(e) {
    var parameters = $("#parameters");
    var children = parameters.children("input");

    for (var i = 0; i < children.length; i++) {
        if (children[i] !== e && children[i].value === "") {
            children[i].remove();
        }
    }
    parameters.append("<input onfocus=\"m_input_focus(this)\" type=\"text\" class=\"form-control input-parameters\" placeholder=\"请输入参数\" />");
}

function loadProject() {
    $.post("/project", function (data) {
        project_id = data.project_id;

        var pid = data.project_id;
        var project_name = data.project_name;

        var team_name = data.team.team_name;
        var maintainer = "无";
        if (data.maintainer) {
            maintainer = data.maintainer.name;
        }
        $.get("/online/project/"+pid,function (d) {
            $("#td_online").html(d);
        });
        $("#td_project_id").html(pid);
        $("#td_project_name").html(project_name);
        $("#td_team_name").html(team_name);
        $("#td_maintainer").html(maintainer);
    });

    $.post("/project/all", function (data) {

        $("#all_project .item").remove();
        for (var i = 0; i < data.length; i++) {
            var note = "项目闲置中";
            console.log(data[i]);
            if (data[i].maintainer) {
                note = data[i].maintainer.name + "正在编辑";
            }

            if (data[i].project_id === project_id) {
                $("#project_flag").after("<div onclick='projectSwitch(" + data[i].project_id + ")' class=\"item\" style='border: #00b5ad 1px dashed'><i class=\"large github middle aligned icon\"></i><div class=\"content\"><a class=\"header\">" + data[i].project_name + "</a><div class=\"description\">" + note + "</div></div></div>");
            } else {
                $("#project_flag").after("<div onclick='projectSwitch(" + data[i].project_id + ")' class=\"item\"><i class=\"large github middle aligned icon\"></i><div class=\"content\"><a class=\"header\">" + data[i].project_name + "</a><div class=\"description\">" + note + "</div></div></div>");
            }

        }
    })
}

function projectSwitch(pid) {
    project_id = parseInt(pid);
    $.post("/user/project", {'project_id': pid}, function (data) {
        window.location.reload();
    });
}

//项目接管、释放

function takeCharge() {
    $.post("/project/take", function(data) {
        window.location.reload();
    });
}

function logout() {
    $.post("/logout", function(data) {
        window.location.reload();
    });
}

function parameter_switch() {
    if (parameter.val() === "on") {
        parameter.val("off");
        input_parameter.show();
    } else {
        parameter.val("on");
        input_parameter.hide();
    }
}

function load_online_user() {
    $.post("/users/online", function (data) {
        $("#online_users .item").remove();
        for (var i = 0; i < data.length; i++) {
            $("#online_flag").after("<div class=\"item\"><img class=\"ui avatar image\" src=\"img/spring_logo.png\"/><div class=\"content\"><div class=\"header\">" + data[i].name + "</div></div></div>");
        }
    });
}
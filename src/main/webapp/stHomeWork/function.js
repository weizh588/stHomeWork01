let searchStudents = (name) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: "http://localhost:8080/student/searchByName",
            method: "get",
            xhrFields: {
                withCredentials: true
            },
            dataType: "json",
            data: {
                name
            },
            crossDomain: true,
            success(res) {
                if (res.state === 1) {
                    resolve(res.students)
                } else {
                    reject(res.errMsg)
                }
            },
            error(e) {
                reject(e)
            }
        })
    })
}
$(() => {
    $("#table").on("click", ".delete-btn", function (e) {
        const id = e.target.dataset.id
        $.ajax({
            url: "http://localhost:8080/student/deleteStudent",
            method: "get",
            xhrFields: {
                withCredentials: true
            },
            dataType: "json",
            data: {
                id
            },
            crossDomain: true,
            success(res) {
                if (res.state === 1) {
                    alert(res.msg)
                } else {
                    alert(res.errMsg)
                }
            }
        })
    })
    $("#table").on("click", ".update-btn", function (e) {
        const student = JSON.parse(e.target.dataset.student)
        $("#update-mask").css("display", "block")
        $("#update-name").val(student.name)
        $("#update-id").val(student.id).attr("disabled", true)
        $("#update-birDate").val(student.birDate)
        $("input[name=update-gender][value=" + student.gender + "]").attr("checked", true)
    })
    $("#insert").click(() => {
        $("#insert-mask").css("display", "block")
    })
    $("#insert-cancel").click(() => {
        $("#insert-mask").css("display", "none")
    })
    $("#insert-confirm").click(() => {
        const name = $("#name").val()
        const id = $("#id").val()
        const gender = $("input[name=gender]:checked").val()
        const birDate = $("#birDate").val()
        if (!(/^\d{10}$/).test(id)) {
            alert("学号不符合规范")
        } else if (!/^\d{4}\/\d{2}\/\d{2}$/.test(birDate)) {
            alert("出生日期格式不正确")
        } else if (!name || !id) {
            alert("内容不能为空")
        } else {
            $.ajax({
                url: "http://localhost:8080/student/insertStudent",
                method: "post",
                xhrFields: {
                    withCredentials: true
                },
                dataType: "json",
                data: {
                    studentStr: JSON.stringify({
                        name,
                        id,
                        gender,
                        birDate
                    })
                },
                crossDomain: true,
                success(res) {
                    if (res.state === 1) {
                        alert(res.msg)
                    } else {
                        alert(res.errMsg)
                    }
                }
            })
        }
    })
    $("#output").click(() => {
        $("#output-mask").css("display", "block")
        $.ajax({
            url: "http://localhost:8080/student/showAllStudents",
            method: "get",
            xhrFields: {
                withCredentials: true
            },
            dataType: "json",
            crossDomain: true,
            success(res) {
                if (res.state === 1) {
                    let str = ` 
                            <tr>
                                <th>姓名</th>
                                <th>学号</th>
                                <th>性别</th>
                                <th>出生日期</th>
                            </tr>`
                    res.studentList.forEach(student => {
                        str += `
                            <tr>
                                <td>${student.name}</td>
                                <td>${student.id}</td>
                                <td>${student.gender ? '男' : '女'}</td>
                                <td>${student.birDate}</td>
                            </tr>`
                    })
                    $("#table").html('')
                    $(str).appendTo($("#table"))
                } else {
                    alert(res.errMsg)
                }
            }
        })
    })
    $("#output-close").click(() => {
        $("#output-mask").css("display", "none")
    })
    $("#delete").click(() => {
        const name = prompt("请输入要删除的学生姓名：")
        if (name) {
            searchStudents(name).then(res => {
                $("#output-mask").css("display", "block")
                let str = ` 
                            <tr>
                                <th>姓名</th>
                                <th>学号</th>
                                <th>性别</th>
                                <th>出生日期</th>
                                <th>操作</th>
                            </tr>`
                res.forEach(student => {
                    str += `
                            <tr>
                                <td>${student.name}</td>
                                <td>${student.id}</td>
                                <td>${student.gender ? '男' : '女'}</td>
                                <td>${student.birDate}</td>
                                <td><span data-id="${student.id}" class="delete-btn">删除</span></td>
                            </tr>`
                })
                $("#table").html('')
                $(str).appendTo($("#table"))
            }).catch(e => {
                alert(e)
            })
        }
    })
    $("#search").click(() => {
        const name = prompt("请输入要查找的学生姓名：")
        if (name) {
            searchStudents(name).then(res => {
                $("#output-mask").css("display", "block")
                let str = ` 
                            <tr>
                                <th>姓名</th>
                                <th>学号</th>
                                <th>性别</th>
                                <th>出生日期</th>
                            </tr>`
                res.forEach(student => {
                    str += `
                            <tr>
                                <td>${student.name}</td>
                                <td>${student.id}</td>
                                <td>${student.gender ? '男' : '女'}</td>
                                <td>${student.birDate}</td>
                            </tr>`
                })
                $("#table").html('')
                $(str).appendTo($("#table"))
            }).catch(e => {
                alert(e)
            })
        }
    })
    $("#update").click(() => {
        const name = prompt("请输入要修改的学生姓名：")
        if (name) {
            searchStudents(name).then(res => {
                $("#output-mask").css("display", "block")
                let str = ` 
                            <tr>
                                <th>姓名</th>
                                <th>学号</th>
                                <th>性别</th>
                                <th>出生日期</th>
                                <th>操作</th>
                            </tr>`
                res.forEach(student => {
                    str += `
                            <tr>
                                <td>${student.name}</td>
                                <td>${student.id}</td>
                                <td>${student.gender ? '男' : '女'}</td>
                                <td>${student.birDate}</td>
                                <td><span data-student='${JSON.stringify(student)}' class="update-btn">修改</span></td>
                            </tr>`
                })
                $("#table").html('')
                $(str).appendTo($("#table"))
            }).catch(e => {
                alert(e)
            })
        }
    })
    $("#update-cancel").click(() => {
        $("#update-mask").css("display", "none")
    })
    $("#update-confirm").click(() => {
        const name = $("#update-name").val()
        const id = $("#update-id").val()
        const gender = $("input[name=update-gender]:checked").val()
        const birDate = $("#update-birDate").val()
        if (!(/^\d{10}$/).test(id)) {
            alert("学号不符合规范")
        } else if (!/^\d{4}\/\d{2}\/\d{2}$/.test(birDate)) {
            alert("出生日期格式不正确")
        } else if (!name || !id) {
            alert("内容不能为空")
        } else {
            $.ajax({
                url: "http://localhost:8080/student/updateStudent",
                method: "post",
                xhrFields: {
                    withCredentials: true
                },
                dataType: "json",
                data: {
                    studentStr: JSON.stringify({
                        name,
                        id,
                        gender,
                        birDate
                    })
                },
                crossDomain: true,
                success(res) {
                    if (res.state === 1) {
                        alert(res.msg)
                    } else {
                        alert(res.errMsg)
                    }
                    $("#update-cancel").click()
                }
            })
        }
    })
    $("#quite").click(() => {
        $.ajax({
            url: "http://localhost:8080/student/quite",
            method: "get",
            xhrFields: {
                withCredentials: true
            },
            dataType: "json",
            crossDomain: true,
            success(res) {
                if (res.state === 1) {
                    location.href = "./index.html"
                } else {
                    alert(res.errMsg)
                }
            }
        })
    })
})

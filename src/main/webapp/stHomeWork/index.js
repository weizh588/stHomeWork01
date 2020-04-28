$(() => {
    $("#enter-btn").click(() => {
        $.ajax({
            url: "http://localhost:8080/student/login",
            method: "get",
            xhrFields: {
                withCredentials: true
            },
            dataType: "json",
            crossDomain: true,
            success(res) {
                if(res.state === 1) {
                    location.href="./function.html"
                } else {
                    alert(res.errMsg)
                }
            }
        })
    })
})
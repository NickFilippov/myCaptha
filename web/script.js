/**
 * Created by fil-n on 17.09.17.
 */

window.onload = function () {
    this.ar = [];
    console.log("load");
    this.selectItem = function (n) {
        if (this.ar.indexOf(n) === -1) {
            this.ar.push(n);
        } else {
            delete this.ar[this.ar.indexOf(n)];
        }
        //console.log(this.ar);
    };
    this.submitArray = function () {
        console.log("clecked");
        $.ajax({
            url: "/process",
            error: function (message) {
                console.log("err" + message.responseText);
                console.log(message);
                document.getElementById("res").innerHTML = message.responseText;
                if (message.responseText === "OK") {
                    swal("Good job!", "You are human!", "success");
                } else {
                    swal("Something's not right.", "Are you really human?", "error");
                }
            },
            success: function (message) {
                console.log("m"+message.responseText);
                document.getElementById("res").innerHTML = message.responseText;
            }
        });
    }
    this.addOutline = function (data) {
        //console.log(data.className.indexOf('selected'));
        if (data.className.indexOf('selected') < 0) {
            data.className += " selected";
        } else {
            data.classList.remove('selected');
        }
        //console.log(data.className);
    }
}

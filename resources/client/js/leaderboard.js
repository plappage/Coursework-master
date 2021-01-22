"use strict";

function getEntryList() {
    console.log("Invoked getEntryList()");
    const url = "/entry/list/";
    fetch(url, {
        method: "GET",
    }).then(response => {
        return response.json();
    }).then(response => {
        if (response.hasOwnProperty("Error")) {
            alert(JSON.stringify(response));
        } else {
            formatEntryList(response.leaderboard);
        }
    });
}

function formatEntryList(myJSONArray) {
    let dataHTML = "";
    let i = 0;
    for (let item of myJSONArray) {
        i++
        //if (i>10) break;
        dataHTML += "<tr><td><img src='img/linksprite.png' style='height:100px;width:auto;'></td><td>" + i + "." + "</td><td>" + item.name + "</td><td>" + item.time + "</td></tr>";
    }
    document.getElementById("leaderboardTable").innerHTML = dataHTML;
}

function postEntrySave() {
    console.log("Invoked postEntrySave()");
    const formData = new FormData(document.getElementById('entry'));
    formData.append('time', document.getElementById('timer').innerHTML);

    let url = "/entry/save";
    fetch(url, {
        method: "POST",
        body: formData,
    }).then(response => {
        return response.json()
    }).then(response => {
        if (response.hasOwnProperty("Error")) {
            alert(JSON.stringify(response));
        } else {
            closeForm();   //URL replaces the current page.  Create a new html file
        }                                                  //in the client folder called welcome.html
    });
}

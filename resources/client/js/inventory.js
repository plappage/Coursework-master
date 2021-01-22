"use strict";

function getInventoryLoad(x) {
    if (document.getElementById(x).style.display === "block") {
        document.getElementById(x).style.display = "none";
    } else {
        console.log("Invoked getInventoryLoad()");
        const url = "/inventory/load/";
        fetch(url, {
            method: "GET",
        }).then(response => {
            return response.json();
        }).then(response => {
            if (response.hasOwnProperty("Error")) {
                alert(JSON.stringify(response));
            } else {
                if (x === "inventory") {
                    formatInventoryLoadForInventory(response);
                } else {
                    formatInventoryLoadForShop(response);
                }

            }
        });
    }
}

function formatInventoryLoadForInventory(myJSONArray) {
    let dataHTML = "";
    for (let item of myJSONArray.inventory) {
        if (item.itemID === 4) break;
        dataHTML += "<p class='items' onclick='inventoryClick(" + item.itemID + "," + item.quantity + ")'>" + item.name + "   " + item.quantity + "</p>";
    }
    document.getElementById("inventory").innerHTML = dataHTML;
    document.getElementById("inventory").style.display = "block";
}

function formatInventoryLoadForShop(myJSONArray) {
    let dataHTML = "";
    for (let item of myJSONArray.inventory) {
        if (item.itemID === 4) break;
        dataHTML += "<p class='items' onclick='shopClick(" + item.itemID + "," + item.price + "," + item.quantity + "," + parseInt(document.getElementById('rupees').innerHTML) + ")'>" + item.name + "   " + item.price + "</p>";
    }
    document.getElementById("shop").innerHTML = dataHTML;
    document.getElementById("shop").style.display = "block";
}

function inventoryClick(item, quantity) {
    console.log("Invoked inventoryClick()");
    if (quantity === 0) {
        window.alert("You don't have any of those");
    } else {
        quantity--;
        postInventoryUpdate(item, quantity);
        if (item === 0) {
            window.alert("You used the bomb");
        } else if (item === 1) {
            window.alert("You used the hookshot");
        } else if (item === 2) {
            window.alert("You used the lens");
        } else if (item === 3) {
            window.alert("You used the boomerang");
        }
        document.getElementById("inventory").style.display = "none";
    }
}

function shopClick(item, price, quantity, rupees) {
    console.log("Invoked shopClick()");
    if (price > rupees) {
        window.alert("Not enough rupees!");
    } else {
        rupees = rupees - price;
        document.getElementById("rupees").innerHTML = rupees;
        postInventoryUpdate(4, rupees);
        quantity++
        postInventoryUpdate(item, quantity);
        document.getElementById("shop").style.display = "none";
    }
}

function postInventoryUpdate(itemID, quantity) {
    console.log("invoked postInventoryUpdate()");

    var formData = new FormData();
    formData.append('quantity', quantity);
    formData.append('itemID', itemID);
    var url = "/inventory/update";

    fetch(url, {
        method: "POST",
        body: formData,
    }).then(response => {
        return response.json()          //method returns a promise, have to return from here to get text
    }).then(response => {
        if (response.hasOwnProperty("Error")) {   //checks if response from server has a key "Error"
            alert(JSON.stringify(response));        // if it does, convert JSON object to string and alert
        } else {

        }
    });
}

function rupees() {
    console.log("Invoked rupees()");
    var rupees = parseInt(document.getElementById("rupees").innerHTML);
    rupees = rupees + 10;
    document.getElementById("rupees").innerHTML = rupees;
    postInventoryUpdate(4, rupees);
}
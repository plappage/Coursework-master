var x = 0;

function onLoad() {
    x = setInterval(function () {
        document.getElementById("timer").innerHTML++
    }, 1000);
}

function openForm() {
    document.getElementById("entry").style.display = "block";
    clearInterval(x);
    console.log(x);
    console.log(document.getElementById('timer').innerHTML);
    c
}

function closeForm() {
    document.getElementById("entry").style.display = "none";
}

function leaderboardClick() {
    if (confirm("End the game and view the leaderboard?")) {
        window.open('/client/leaderboard.html', '_self');
    }
}
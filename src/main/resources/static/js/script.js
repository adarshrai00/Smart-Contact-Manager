console.log("this is script");

function getTheme() {
    let theme = localStorage.getItem("theme");
    if (theme) return theme;
    else return "light";
}

let currtheme = getTheme();
changeTheme(currtheme);
console.log(currtheme);

function changeTheme(theme) {
    document.querySelector('html').classList.add(theme);

    const change_theme = document.getElementById('theme_ch');
    change_theme.addEventListener("click", () =>    {
        document.querySelector('html').classList.remove(currtheme)
        console.log("button clicked");
        if(currtheme==="dark")
        {
            currtheme="light"
        }
        else
        {
            currtheme="dark"
        }
        setTheme(currtheme)
        document.querySelector('html').classList.add(currtheme);
        change_theme.querySelector('span').textContent= currtheme=="light"?"dark":"light"
    });
}

function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

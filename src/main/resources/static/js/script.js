let menu = document.querySelector('#menu-btn') || document.createElement('div');
let navbar = document.querySelector('.header .nav') || document.createElement('div');
let header = document.querySelector('.header') || document.createElement('div');

menu.onclick = () => {
    menu.classList.toggle('fa-times');
    navbar.classList.toggle('active');
}

window.onscroll = () => {
    menu.classList.remove('fa-times');
    navbar.classList.remove('active');
    if (window.scrollY > 0) {
        header.classList.add('active');
    } else {
        header.classList.remove('active');
    }
}

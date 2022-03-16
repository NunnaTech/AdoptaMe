let menu = document.querySelector('#menu-btn') || document.createElement('div');
let navbar = document.querySelector('.nav') || document.createElement('div');
let header = document.querySelector('.header') || document.createElement('div');
let section = document.querySelectorAll('section');
let navLinks = document.querySelectorAll('header .nav a');

menu.onclick = () => {
    menu.classList.toggle('fa-times');
    navbar.classList.toggle('active');
}

window.onscroll = () => {
    menu.classList.remove('fa-times');
    navbar.classList.remove('active');
    section.forEach(sec => {
        let top = window.scrollY;
        let height = sec.offsetHeight;
        let offset = sec.offsetTop - 150;
        let id = sec.getAttribute('id');
        if (top >= offset && top < offset + height) {
            navLinks.forEach(links => {
                links.classList.remove('active');
                document.querySelector('header .nav a[href*=' + id + ']').classList.add('active');
            });
        }
        ;
    });
    if (window.scrollY > 0) {
        header.classList.add('active');
    } else {
        header.classList.remove('active');
    }
}

// $(document).ready(function () {
//     $('#datatable').DataTable({responsive: true});
// });




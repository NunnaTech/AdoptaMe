

let menu = document.querySelector('#menu-btn');
let navbar = document.querySelector('.navbar');

let header = document.querySelector('.header');

menu.onclick = () =>{
    menu.classList.toggle('fa-times');
    navbar.classList.toggle('active');
}

let section = document.querySelectorAll('section');
console.log(section)

let navLinks = document.querySelectorAll('header .navbar a');
console.log(navLinks)





window.onscroll = () =>{
    menu.classList.remove('fa-times');
    navbar.classList.remove('active');

    section.forEach(sec =>{

        let top = window.scrollY;
        let height = sec.offsetHeight;
        let offset = sec.offsetTop - 150;
        let id = sec.getAttribute('id');

        if(top >= offset && top < offset + height){
            navLinks.forEach(links =>{
                links.classList.remove('active');
                document.querySelector('header .navbar a[href*='+id+']').classList.add('active');
            });
        };

    });

    if(window.scrollY > 0){
        header.classList.add('active');
    }else{
        header.classList.remove('active');
    }

    var swiper = new Swiper(".home-slider", {
        spaceBetween: 20,
        centeredSlides: true,

        autoplay: {
            delay: 30000,
            disableOnInteraction: false,
        },
        pagination: {
            el: ".swiper-pagination",
            clickable: true,
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },

        scrollbar: {
            el: '.swiper-scrollbar',
        },

        loop:true,
    });


}







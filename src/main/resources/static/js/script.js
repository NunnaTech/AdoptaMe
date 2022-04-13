let menu = document.querySelector('#menu-btn') || document.createElement('div');
let navbar = document.querySelector('.nav') || document.createElement('div');
let header = document.querySelector('.header') || document.createElement('div');

menu.onclick = () => {
    menu.classList.toggle('fa-times');
    navbar.classList.toggle('active');
}

function mostrarContrasena() {
    let tipo = document.getElementById("password");
    if (tipo.type == "password") {
        tipo.type = "text";
        document.getElementById('seePasswordButton').innerHTML = "<i class=\"fa-solid fa-eye-slash\"></i>";
    } else {
        tipo.type = "password";
        document.getElementById('seePasswordButton').innerHTML = "<i class=\"fa-solid fa-eye\"></i>";
    }
}

function bothPasswords(i,j) {
    let tipo = document.getElementById(i);
    if (tipo.type == "password") {
        tipo.type = "text";
        document.getElementById(j).innerHTML = "<i class=\"fa-solid fa-eye-slash\"></i>";
    } else {
        tipo.type = "password";
        document.getElementById(j).innerHTML = "<i class=\"fa-solid fa-eye\"></i>";
    }
}

window.onscroll = () => {
    if (window.scrollY > 0) {
        header.classList.add('active');
    } else {
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
        loop: true,
    });

}

$(document).ready(function () {
    $('#datatable').DataTable({
        "autoWidth": true,
        "responsive": true,
        "language": {
            "sProcessing": "Procesando/Procesing...",
            "sLengthMenu": "Mostrar/Show: _MENU_",
            "sZeroRecords": "Sin resultados / Not results",
            "sEmptyTable": "Ningún dato / No data",
            "sInfo": "Registros / Records: _START_ al _END_",
            "sInfoEmpty": "Sin registros / No records: ",
            "sSearch": "Buscar / Search: ",
            "sLoadingRecords": "Cargando / Loading...",
            "oPaginate": {
                "sFirst": "Primero / First",
                "sLast": "Último / Last",
                "sNext": "Siguiente / Next",
                "sPrevious": "Anterior / Previous"
            },
        }
    });
});




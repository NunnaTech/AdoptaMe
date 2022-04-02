function checkPasswords(e){
    let newPassword = document.querySelector("#newPassword").value;
    let repeatPassword = document.querySelector("#repeatPassword").value;
    if(newPassword === repeatPassword){
        e.submit();
    }else{
        Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: 'Las nuevas contraseñas no coinciden',
            text: 'Por favor revisa que sean las mismas',
            showConfirmButton: false,
            timer: 3000
        })
    }
}
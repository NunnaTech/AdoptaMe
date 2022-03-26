function uploadImage(e) {
    let image = e.elements['preImage'].files[0];
    console.log(image)
    if (image) {
        if (
            image === "image/png" ||
            image === "image/jpg" ||
            image === "image/jpeg"
        ) {

        } else {
            sweetAlertNoty('Formato de imagen no admitido')
        }
    } else {
        sweetAlertNoty('Sin imagen seleccionada')
    }
}

function sweetAlertNoty(title){
    Swal.fire({
        position: 'top-end',
        icon: 'error',
        title,
        showConfirmButton: false,
        timer: 1500
    })
}

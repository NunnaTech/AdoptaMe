const save = document.querySelector("#noty_save") || document.createElement('input');
const deleted = document.querySelector("#noty_deleted") || document.createElement('input');

callbackMessages()

function callbackMessages() {
    if (save.attributes[3]) {
        if (save.attributes[3].value === 'true') {
            sweetAlert(save.attributes[4].value)
        }
    }
    if (deleted.attributes[3]) {
        if (deleted.attributes[3].value === 'true') {
            sweetAlert(deleted.attributes[4].value)
        }
    }
}

function sweetAlert(title) {
    Swal.fire({
        position: 'top-end',
        icon: 'success',
        title,
        showConfirmButton: false,
        timer: 1500
    })
}

function confirmSweetAlert(e){
    e.preventDefault();
    console.log(e)
    Swal.fire({
        title: '¿Estás seguro?',
        text: "Este elemento será removido para siempre",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#F47A1F',
        cancelButtonColor: '#666',
        confirmButtonText: 'Sí, eliminar!',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire(
                'Deleted!',
                'Your file has been deleted.',
                'success'
            )
        }
    })
}



let URL_BATCHE = 'https://allowingcors.herokuapp.com/https://api-upscaler-origin.icons8.com/api/frontend/v1/batches';
async function uploadImage(e, img) {
    let image = e.elements['preImage'].files[0];
    if (image) {
        if (image.type === "image/png" || image.type === "image/jpg" || image.type === "image/jpeg") {
            sweetAlertWait();
            let getBatche = await batche();
            let formData = new FormData();
            formData.append("image", image, image.name);
            let imageResponse = await uploadImageAws(getBatche.id, formData);
            if (imageResponse) {
                e.elements[img].value = imageResponse.source.url;
                e.submit();
            }else{
                sweetAlertNoty('Imagen corrompida')
            }
        } else sweetAlertNoty('Formato de imagen no admitido, solo se admite formatos .png, .jpeg o .jpg')

    } else sweetAlertNoty('Debe seleccionar una imagen')
}
async function uploadImageAws(code, formData) {
    return await fetch(`${URL_BATCHE}/${code}`, {
        method: 'POST',
        body: formData,
    }).then(response => response.json()).then(data => data).catch(err => sweetAlertNoty('Error en el servidor'));
}
async function batche() {
    return await fetch(URL_BATCHE, {
        method: 'POST',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {'Content-Type': 'application/json'},
        redirect: 'follow',
        referrerPolicy: 'no-referrer',
    }).then(response => response.json()).then(data => data).catch(err => sweetAlertNoty('Error en el servidor'));
}
function sweetAlertNoty(title) {
    Swal.fire({
        position: 'top-end',
        icon: 'error',
        title,
        showConfirmButton: false,
        timer: 2000
    })
}
function sweetAlertWait() {
    Swal.fire({
        title: 'Espere un momento',
        text: 'Estamos subiendo la imagen',
        didOpen: () => {
            Swal.showLoading()
        },
    })
}

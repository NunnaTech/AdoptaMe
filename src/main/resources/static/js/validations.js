const NAMES = 'áéíóúÁÉÍÓÚabcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ. ';
const ALPHANUMERIC = 'abcdefghijklmnopqrstuvwxyzáéíóúÁÉÍÓÚABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@._ ';
const TEXT = '0123456789áéíóúÁÉÍÓÚabcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ.,;()\'\'- #/ ';
const DIGITS = '0123456789';

setInputs();

function setInputs() {
    let attribute = ['onkeyup', 'onkeypress', 'onkeydown'];
    let inputs = document.getElementsByTagName('input');
    for (let i = 0; i < inputs.length; i++)
        for (let j = 0; j < attribute.length; j++)
            inputs[i].setAttribute(attribute[j], 'this.value = remove(this.value, this.name)');
}

function remove(value, inputName) {
    let VALIDATE_TO = '';
    let output = '';
    switch (inputName) {
        case 'user.profile.name':
        case 'user.profile.lastName':
        case 'user.profile.secondName':
        case 'name':
        case 'lastName':
        case 'secondName':
        case 'query':
        case 'breed':
        case 'type':
            VALIDATE_TO = NAMES;
            break;

        case 'email':
        case 'user.username':
        case 'authority':
        case 'title':
        case 'description':
        case 'range':
            VALIDATE_TO = ALPHANUMERIC;
            break;

        case 'password':
        case 'newPassword':
        case 'repeatedPassword':
        case 'reason':
        case 'street':
        case 'references':
        case 'currentPassword':
        case 'repeatPassword':
        case 'hex_code':
        case 'user.password':
        case 'address.street':
            VALIDATE_TO = TEXT;
            break;

        case 'phone':
        case 'externalNumber':
        case 'internalNumber':
        case 'zipCode':
        case 'address.zipCode':
        case 'address.internalNumber':
        case 'address.externalNumber':
            VALIDATE_TO = DIGITS;
            break;
    }

    for (let i = 0; i < value.length; i++)
        if (VALIDATE_TO.indexOf(value.charAt(i)) != -1)
            output += value.charAt(i);
    return output;
}


function checkPasswords(e) {
    let newPassword = document.querySelector("#newPassword").value;
    let repeatPassword = document.querySelector("#repeatPassword").value;
    if (newPassword === repeatPassword) {
        e.submit();
    } else {
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
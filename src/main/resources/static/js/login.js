let adotador = document.querySelector("#adoptador");
adotador.setAttribute("checked", true);
let enabled = document.querySelector("#enabled");
enabled.setAttribute("checked", true)
let voluntario = document.querySelector("#voluntario");
adotador.addEventListener('click', () => enabled.setAttribute("checked", true))
voluntario.addEventListener('click', () => enabled.removeAttribute("checked"))
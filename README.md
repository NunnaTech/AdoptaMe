<div id="top"></div>

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<br />
<div align="center">
  <a href="https://github.com/ThePandaDevs/AdoptaMe">
    <img src="https://i.imgur.com/uMAVgsf.png" alt="Logo" width="auto" height="150">
  </a>

<h1 style="font-weight:bold" align="center">AdoptaMe</h1>
  <p align="center">
    Proyecto AdoptaMe: Una aplicación web para la adopción de mascotas.
    <br />
    <br />
    <a href="https://adoptame-mx.herokuapp.com/" target="_blank">Ver la aplicación</a>
    ·
    <a href="https://github.com/ThePandaDevs/AdoptaMe/issues" target="_blank">Reportar errores</a>
    ·
    <a href="https://github.com/ThePandaDevs/AdoptaMe/issues" target="_blank">Solicitar nuevas funciones</a>
  </p>
</div>

## Acerca del proyecto

[![Product Name Screen Shot][product-screenshot]](https://adoptame-mx.herokuapp.com/)

AdoptaMe es una plataforma digital que reúne personas voluntarias como protectores de animales y adoptadores que buscan adoptar una mascota.

Estamos convencidos que es posible acabar con el maltrato animal, creemos que la sociedad está cambiando y cada vez se preocupa más por la adopción de animales, necesitamos acabar con el maltrato animal promoviendo la adopción de animales, la esterilización, la conciencia sobre el tráfico ilegal de especies en peligro.

<p align="right">(<a href="#top">regresar al inicio</a>)</p>

### Herramientas para su construcción

En esta sección listaremos todas aquellas tecnologías, frameworks, dependencias o herramientas utilizadas para el desarrollo de este proyecto y sean de su conocimiento.


- [Spring](https://spring.io/)
  - [Thymeleaf](https://www.thymeleaf.org/)
  - [MySQL8](https://www.mysql.com/)
  - [Lombok](https://projectlombok.org/)
  - [Spring security](https://spring.io/projects/spring-security)
  - [Spring data JPA](https://spring.io/projects/spring-data-jpa)
- [Bootstrap 4](https://getbootstrap.com/)
- [SweetAlert2](https://sweetalert2.github.io/)
- [Datatables](https://datatables.net/)
- [Summernote](https://summernote.org/)


<p align="right">(<a href="#top">regresar al inicio</a>)</p>

## Cómo empezar

Para poder usar localmente el proyecto en tu propia computadora, necesitarás algunos requisitos para poder ejecutarlo correctamente.

### Requisitos

Necesitas tener instalado y debidamente configurado las siguientes herramientas:

- [Git](https://git-scm.com/)
- [JDK 11](https://www.oracle.com/mx/java/technologies/javase/jdk11-archive-downloads.html)
- [MySQL8](https://www.mysql.com/)


### Instalación

1. Clona el repositorio, puede ser mediante HTTPS o SSH
   ```sh
   git clone https://github.com/ThePandaDevs/AdoptaMe.git
   ```
2. Ir a la carpeta clonada
   ```sh
    cd AdoptaMe
   ```
3. Activa el servicio de MySQL
4. Asegurate que en el archivo de propiedad de la aplicación `application.properties` se tenga la siguiente línea para crear la base de datos junto con la opción de creado correcta.
   ```sh
   # create-drop
   # create
   # update
   spring.jpa.hibernate.ddl-auto=update
   ```
5. Accede al archivo `application-dev.properties` y configura tu entorno para poder enviar correos desde tu propia cuenta de correos y asu vez puedes cambiar el puerto.
6. Encuentra el archivo `fillpets.sql` que se encuentra en la carpeta `src > main > java/mx/com/adoptame > util`
7. Ejecuta el script .sql para inicializar con datos la aplicación
8. Ejecuta la aplicación con Spring
9. Los datos por defecto para iniciar sesión dentro son:
   ```sh
   # super@adoptame.com:admin  ADMINISTRADOR
   # volun@adoptame.com:admin  VOLUNTARIO
   # adopt@adoptame.com:admin  ADOPTADOR
   ```
10. Listo, podrás usar la aplicación.

<p align="right">(<a href="#top">regresar al inicio</a>)</p>

## Contribuidores

- Álvarez Ortiz Luis Enrique
- Castellanos Martínez Christopher Eduardo
- Loya García Alexis
- López Domitilo Rubén
- Saldaña Espinoza Hector

## Licencia

Distributed under the MIT License. Mirar `LICENSE.txt` para más información.

<p align="right">(<a href="#top">regresar al inicio</a>)</p>


[contributors-shield]: https://img.shields.io/github/contributors/ThePandaDevs/AdoptaMe.svg?style=for-the-badge
[contributors-url]: https://github.com/ThePandaDevs/AdoptaMe/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/ThePandaDevs/AdoptaMe.svg?style=for-the-badge
[forks-url]: https://github.com/ThePandaDevs/AdoptaMe/network/members
[stars-shield]: https://img.shields.io/github/stars/ThePandaDevs/AdoptaMe.svg?style=for-the-badge
[stars-url]: https://github.com/ThePandaDevs/AdoptaMe/stargazers
[issues-shield]: https://img.shields.io/github/issues/ThePandaDevs/AdoptaMe.svg?style=for-the-badge
[issues-url]: https://github.com/ThePandaDevs/AdoptaMe/issues
[license-shield]: https://img.shields.io/github/license/ThePandaDevs/AdoptaMe.svg?style=for-the-badge
[license-url]: https://github.com/ThePandaDevs/AdoptaMe/blob/master/LICENSE.txt
[product-screenshot]: https://i.imgur.com/Jp67nbG.png
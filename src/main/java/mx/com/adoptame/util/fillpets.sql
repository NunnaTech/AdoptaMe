use adoptame;
INSERT INTO adoptame.tbl_pets (age, breed, gender, is_active, is_adopted, name, character_id, color_id, size_id, type_id, user_id, description)
VALUES ('Joven', 'Azawakh', 1, 1, 0, 'Awaki', 3, 7, 3, 1, 1,
        'Una mascota muy comportada y bonita con el que puedes compartir momento con tu familia'),
       ('Joven', 'Akita Inu', 0, 1, 0, 'Aki', 4, 8, 2, 1, 1,
        'Una mascota muy comportada y bonita con el que puedes compartir momento con tu familia'),
       ('Adulto', 'Airedale terrier', 1, 1, 0, 'Seri', 7, 7, 3, 1, 1,
        'Una mascota muy comportada y bonita con el que puedes compartir momento con tu familia'),
       ('Adulto', 'Cane Corso', 1, 1, 0, 'Demon', 5, 6, 3, 1, 1,
        'Una mascota muy comportada y bonita con el que puedes compartir momento con tu familia'),
       ('Cachorro/Cria', 'Mapa de barbour', 0, 1, 0, 'Torti', 6, 3, 1, 3,
        1, 'Una mascota muy comportada y bonita con el que puedes compartir momento con tu familia'),
       ('Cachorro/Cria', 'Persa', 0, 1, 0, 'Persi', 8, 9, 1, 2, 1,
        'Una mascota muy comportada y bonita con el que puedes compartir momento con tu familia'),
       ('Adulto', 'Esfinge', 1, 1, 0, 'Sinpe', 2, 9, 1, 2, 1,
        'Una mascota muy comportada y bonita con el que puedes compartir momento con tu familia'),
       ('Joven', 'Marina', 1, 1, 0, 'Tor', 2, 3, 1, 3, 1,
        'Una mascota muy comportada y bonita con el que puedes compartir momento con tu familia'),
       ('Joven', 'Pug', 1, 1, 0, 'Puggy', 3, 6, 1, 1, 1, 'Una mascota muy comportada y bonita con el que puedes compartir momento con tu familia');


INSERT INTO adoptame.tbl_pets_images (image, pet_id)
VALUES ('https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/911/2cb359af-1476-4464-8312-e984f0b925aa.jpg',
        1),
       ('https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/582/6058dd72-e9e0-4253-b958-c88f80806b77.jpg',
        1),
       ('https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/674/da4e9d59-a3a2-4b8c-80ad-999c36b8ee72.jpg',
        2),
       ('https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/182/71ef3947-78b4-413c-ade0-f5fbe1c8b7dd.jpg',
        2),
       ('https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/541/2b0bb2d4-1c02-4498-af0a-35423856362f.jpg',
        3),
       ('https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/704/b7c11724-44fe-41bf-99a4-6c3de24c0d8d.jpg',
        3),
       ('https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/526/1e6481f9-3bab-49bf-bb72-c4c4017d25be.jpg',
        4),
       ('https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/566/fefcafa0-9935-43bb-8658-04e71d133cf1.jpg',
        4),
       ('https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/322/80dc14cc-01e3-4365-ac0d-e8403173e03d.jpeg',
        5),
       ('https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/1/cd70712b-c5ea-4aca-9871-728010b3248f.jpg',
        6),
       ('https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/370/f8bad6c8-80b8-4600-b016-f45a24768c2d.jpg',
        6),
       ('https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/954/6dd9fe1b-b9f1-447f-a0eb-05bd7a6e97b6.jpg',
        7),
       ('https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/395/0558d8f0-0fb1-4e01-9931-f4ab36e1103f.jpg',
        7),
       ('https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/744/11b7c812-9b16-4edf-87c9-d9e043b309d4.png',
        9),
       ('https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/786/a52165c1-60d4-4478-9c58-c70a3fa7647a.jpg',
        9);

INSERT INTO adoptame.tbl_news (content, image, is_main, is_published, title, user_id) VALUES ('<p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Un animal puede ser adoptado de un albergue o de un hogar de paso y en ambos casos, al adoptar, se salva la vida del adoptado y de un nuevo animal en estado vulnerable que tomará su lugar para ser rehabilitado. Sin embargo, como todas las decisiones importantes, la adopción debe ser un consenso entre todos los miembros de una familia.</span></p><p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Es importante plantearse algunas preguntas para reflexionar bien antes de acoger a un animal de compañía en casa.</span></p><p style="font-family: sans-serif; line-height: 1.1; color: rgb(51, 51, 51); margin-top: 3.5rem; margin-bottom: 10px;"><b><span style="font-family: Montserrat;">¿Por qué quiere un animal?</span></b></p><p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Adoptar un animal solo por “moda”, “capricho” o porque “los niños han pedido un cachorro para Navidad” generalmente termina siendo un error. Hay que recordar que los animales de compañía viven alrededor de 10 a 15 años en el caso de los perros, o hasta 20 años en el caso de los gatos. ¿Tiene tiempo para cuidarlo, ejercitarlo, bañarlo, alimentarlo, peinarlo? Los animales de compañía no pueden ser ignorados solo porque se está cansado u ocupado. Necesitan de cuidado y compañía todos los días. ¿Cuenta con los recursos económicos? Cuidado veterinario de rutina, emergencias de salud, juguetes, comida, accesorios, guardería, entrenamiento. Todo esto conlleva costos que deben asumirse. ¿Tiene el espacio suficiente para un animal? ¿Su arrendador le permite tener animales? ¿Qué hará con el animal si se muda de vivienda, de ciudad o país? ¿Sabe quién lo cuidará cuando salga de vacaciones? ¿Conoce los costos de una guardería u hotel para animales? ¿Está preparado para mantener y cuidar a su animal por el resto de su vida? Adoptar un animal es un compromiso de por vida. Puede parecer una lista muy larga de preguntas, pero muchos animales terminan en las calles o en los albergues porque sus acudientes no pensaron en ellas antes de adoptar. Si usted desea adoptar un perro o gato, puede ponerse en contacto con la cantidad de refugios y hogares de paso en su ciudad. Recuerde que si no puede adoptar, puede apadrinar o ser voluntario. Las personas a cargo de los procesos de adopción además de ser muy cuidadosos y meticulosos con la elección de las nuevas familias, deben tener en cuenta el uso de protocolos tanto para la selección de los adoptantes como para el seguimiento presencial después de entregar al animal, por medio de visitas domiciliarias. Dicho seguimiento es fundamental ya que a través de él se podrá saber realmente si el animal está bien en su nuevo hogar, permitirá medir la adaptación del mismo con su nueva familia y garantizará realmente el éxito de la adopción. Además al realizar seguimiento se podrá asesorar a los acudientes sobre la resolución de posibles problemas que pueden presentarse, previniendo que estos sean causa de un nuevo abandono.</span></p><p style="font-family: sans-serif; line-height: 1.1; color: rgb(51, 51, 51); margin-top: 3.5rem; margin-bottom: 10px;"><span style="font-weight: 700; font-family: Montserrat;">¿Por qué adoptar una mascota?</span></p><p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Salva una vida. Muchas personas abandonan a sus mascotas en la calle mientras que otras las entregan en albergues o refugios. Debido al poco espacio en estos lugares, en muchos casos los animales son sacrificados. Los que terminan en la calle pueden morir de hambre, atropellados, abusados o enfermos.</span></p><p style="font-family: sans-serif; line-height: 1.1; color: rgb(51, 51, 51); margin-top: 3.5rem; margin-bottom: 10px;"><span style="font-weight: 700; font-family: Montserrat;">Le brindan amor incondicional</span></p><p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Pase lo que pase, la mascota siempre estará disponible y será la primera que se alegre cuando llegue a casa. El amor que brindan los animales es el más puro.</span></p><p style="margin-bottom: 10px;"><font color="#333333" face="Montserrat"><span style="font-size: 14px;">Las mascotas que se encuentran en tiendas, por lo general, viven dentro de jaulas y lejos del calor humano. Además, se suele desconocer su origen y si fueron criadas responsable mente. Al comprar, se continúa con el ciclo de venta y encierro.</span></font></p><p style="font-family: sans-serif; line-height: 1.1; color: rgb(51, 51, 51); margin-top: 3.5rem; margin-bottom: 10px;"><span style="font-weight: 700; font-family: Montserrat;">Compañía</span></p><p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Tener una mascota hará que nunca se sienta solo. Son fieles compañeros.</span></p><p style="font-family: sans-serif; line-height: 1.1; color: rgb(51, 51, 51); margin-top: 3.5rem; margin-bottom: 10px;"><span style="font-weight: 700; font-family: Montserrat;">Lo mantienen activo</span></p><p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Pasar tiempo paseando al perro o jugando con el gato es una buena manera de hacer ejercicio, de fortalecer el vínculo con la mascota y, además, en el caso de los perros, lo expone a socializar con otras personas.</span></p><p style="font-family: sans-serif; line-height: 1.1; color: rgb(51, 51, 51); margin-top: 3.5rem; margin-bottom: 10px;"><span style="font-weight: 700; font-family: Montserrat;">Se le da oportunidad a otros</span></p><p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Al adoptar, no solo ilumina la vida de un animal, además ayuda a liberar espacio en un albergue, lo que permite rescatar a más animales abandonados a su suerte.</span></p>', 'https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/205/8c6622de-704f-4942-9af5-1efadca5647f.jpg', 1, 1, 'Antes de adoptar', 1);

INSERT INTO adoptame.tbl_tags_news (id_news, id_tag) VALUES (1, 1), (1, 3);

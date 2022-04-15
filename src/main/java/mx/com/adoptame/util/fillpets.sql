use adoptame;

DROP PROCEDURE IF EXISTS log_tlb_types;
DELIMITER $$;
CREATE PROCEDURE log_tlb_types(action_in VARCHAR(20), id_in INT, name_in VARCHAR(20), description_in VARCHAR(60),
                               status_in TINYINT(1), user_id_in INT)
BEGIN
    DECLARE table_in VARCHAR(50) DEFAULT 'types';
    DECLARE data_from_db TEXT;


    IF (action_in = 'Crear' AND id_in IS NULL) THEN
        INSERT INTO tbl_logs(action, board, new_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{name:', name_in, ',description:', description_in, ',status:', status_in, '}'),
                user_id_in);


    ELSEIF (action_in = 'Actualizar' AND id_in IS NOT NULL) THEN

        SELECT CONCAT('{name:', name, ',description:', description, ',status:', status, '}')
        INTO data_from_db
        FROM tbl_types t
        WHERE t.id_type = id_in;

        INSERT INTO tbl_logs(action, board, new_data, old_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{name:', name_in, ',description:', description_in, ',status:', status_in, '}'),
                data_from_db,
                user_id_in);

    ELSE

        SELECT CONCAT('{name:', name, ',description:', description, ',status:', status, '}')
        INTO data_from_db
        FROM tbl_types t
        WHERE t.id_type = id_in;


        INSERT INTO tbl_logs(action, board, old_data, new_data, user_id)
        VALUES (action_in, table_in,
                data_from_db,
                null,
                user_id_in);
    END IF;
END $$;

DROP PROCEDURE IF EXISTS log_tbl_pets;
DELIMITER $$;
CREATE PROCEDURE log_tbl_pets(
    action_in VARCHAR(20),
    id_in INT,
    name_in VARCHAR(30),
    age_in VARCHAR(20),
    breed_in VARCHAR(60),
    description_in VARCHAR(160),
    gender_in TINYINT(1),
    is_active_in TINYINT(1),
    is_adopted_in TINYINT(1),
    is_dropped_in TINYINT(1),
    character_id_in INT,
    color_id_in INT,
    size_id_in INT,
    type_id_in INT,
    user_in INT,
    user_id_in INT)

BEGIN
    DECLARE table_in VARCHAR(50) DEFAULT 'pets';
    DECLARE data_from_db TEXT;

    IF (action_in = 'Crear' AND id_in IS NULL) THEN
        INSERT INTO tbl_logs(action, board, new_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{age:', age_in, ',breed:', breed_in, ',description:', description_in, ',gender:', gender_in,
                       ',is_active:', is_active_in, ',is_adopted:', is_adopted_in, ',is_dropped:', is_dropped_in,
                       ',name:', name_in, ',character_id:', character_id_in, ',color_id:', color_id_in, ',size_id:',
                       size_id_in, ',type_id:', type_id_in, ',user_id:', user_in, '}'),
                user_id_in);

    ELSEIF (action_in = 'Actualizar' AND id_in IS NOT NULL) THEN

        SELECT CONCAT('{age:', COALESCE(age, 'null'), ',breed:', COALESCE(breed, 'null'), ',description:',
                      COALESCE(description, 'null'), ',gender:', gender,
                      ',is_active:', is_active, ',is_adopted:', is_adopted, ',is_dropped:', is_dropped,
                      ',name:', COALESCE(name, 'null'), ',character_id:', character_id, ',color_id:', color_id,
                      ',size_id:',
                      size_id, ',type_id:', type_id, ',user_id:', user_id, '}')
        INTO data_from_db
        FROM tbl_pets t
        WHERE t.id_pet = id_in;

        INSERT INTO tbl_logs(action, board, new_data, old_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{age:', age_in, ',breed:', breed_in, ',description:', description_in, ',gender:', gender_in,
                       ',is_active:', is_active_in, ',is_adopted:', is_adopted_in, ',is_dropped:', is_dropped_in,
                       ',name:', name_in, ',character_id:', character_id_in, ',color_id:', color_id_in, ',size_id:',
                       size_id_in, ',type_id:', type_id_in, ',user_id:', user_in, '}'),
                data_from_db,
                user_id_in);

    ELSE

        SELECT CONCAT('{age:', COALESCE(age, 'null'), ',breed:', COALESCE(breed, 'null'), ',description:',
                      COALESCE(description, 'null'), ',gender:', gender,
                      ',is_active:', is_active, ',is_adopted:', is_adopted, ',is_dropped:', is_dropped,
                      ',name:', name, ',character_id:', character_id, ',color_id:', color_id, ',size_id:',
                      size_id, ',type_id:', type_id, ',user_id:', user_id, '}')
        INTO data_from_db
        FROM tbl_pets t
        WHERE t.id_pet = id_in;

        INSERT INTO tbl_logs(action, board, old_data, new_data, user_id)
        VALUES (action_in, table_in,
                data_from_db,
                null,
                user_id_in);
    END IF;
END $$;

DROP PROCEDURE IF EXISTS log_tbl_news;
DELIMITER $$;
CREATE PROCEDURE log_tbl_news(
    action_in VARCHAR(20),
    id_in INT,
    content_in LONGTEXT,
    image_in VARCHAR(250),
    is_main_in TINYINT(1),
    is_published_in TINYINT(1),
    title_in VARCHAR(50),
    user_in INT,
    user_id_in INT)
BEGIN
    DECLARE table_in VARCHAR(50) DEFAULT 'news';
    DECLARE data_from_db TEXT;


    IF (action_in = 'Crear' AND id_in IS NULL) THEN
        INSERT INTO tbl_logs(action, board, new_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{content:', content_in, ',image:', image_in, ',is_main:', is_main_in, ',is_published:',
                       is_published_in, ',title:', title_in, ',user_id:', user_in, '}'),
                user_id_in);

    ELSEIF (action_in = 'Actualizar' AND id_in IS NOT NULL) THEN

        SELECT CONCAT('{content:', content, ',image:', COALESCE(image, 'null'), ',is_main:', is_main, ',is_published:',
                      is_published, ',title:', title, ',user_id:', user_id, '}')
        INTO data_from_db
        FROM tbl_news t
        WHERE t.id_news = id_in;

        INSERT INTO tbl_logs(action, board, new_data, old_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{content:', content_in, ',image:', image_in, ',is_main:', is_main_in, ',is_published:',
                       is_published_in, ',title:', title_in, ',user_id:', user_in, '}'),
                data_from_db,
                user_id_in);

    ELSE

        SELECT CONCAT('{content:', content, ',image:', COALESCE(image, 'null'), ',is_main:', is_main, ',is_published:',
                      is_published, ',title:', title, ',user_id:', user_id, '}')
        INTO data_from_db
        FROM tbl_news t
        WHERE t.id_news = id_in;

        INSERT INTO tbl_logs(action, board, old_data, new_data, user_id)
        VALUES (action_in, table_in,
                data_from_db,
                null,
                user_id_in);
    END IF;
END $$;

DROP PROCEDURE IF EXISTS log_tbl_donations;
DELIMITER $$;
CREATE PROCEDURE log_tbl_donations(
    action_in VARCHAR(20),
    id_in INT,
    authorization_in VARCHAR(100),
    is_completed_in TINYINT(1),
    quantity_in DOUBLE(10, 2),
    user_in INT,
    user_id_in INT)
BEGIN
    DECLARE table_in VARCHAR(50) DEFAULT 'donations';
    DECLARE data_from_db TEXT;

    IF (action_in = 'Crear' AND id_in IS NULL) THEN
        INSERT INTO tbl_logs(action, board, new_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{authorization:', authorization_in, ',is_completed:', is_completed_in, ',quantity:',
                       quantity_in, ',user_id:', user_in, '}'),
                user_id_in);


    ELSEIF (action_in = 'Actualizar' AND id_in IS NOT NULL) THEN

        SELECT CONCAT('{authorization:', COALESCE(authorization, 'null'), ',is_completed:', is_completed, ',quantity:',
                      quantity, ',user_id:', user_id, '}')
        INTO data_from_db
        FROM tbl_donations t
        WHERE t.id_donation = id_in;

        INSERT INTO tbl_logs(action, board, new_data, old_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{authorization:', authorization_in, ',is_completed:', is_completed_in, ',quantity:',
                       quantity_in, ',user_id:', user_in, '}'),
                data_from_db,
                user_id_in);

    ELSE

        SELECT CONCAT('{authorization:', COALESCE(authorization, 'null'), ',is_completed:', is_completed, ',quantity:',
                      quantity, ',user_id:', user_id, '}')
        INTO data_from_db
        FROM tbl_donations t
        WHERE t.id_donation = id_in;


        INSERT INTO tbl_logs(action, board, old_data, new_data, user_id)
        VALUES (action_in, table_in,
                data_from_db,
                null,
                user_id_in);
    END IF;
END $$;

DROP PROCEDURE IF EXISTS log_tbl_colors;
DELIMITER $$;
CREATE PROCEDURE log_tbl_colors(
    action_in VARCHAR(20),
    id_in INT,
    hexCode_in VARCHAR(10),
    name_in VARCHAR(20),
    status_in TINYINT(1),
    user_id_in INT)
BEGIN
    DECLARE table_in VARCHAR(50) DEFAULT 'colors';
    DECLARE data_from_db TEXT;

    IF (action_in = 'Crear' AND id_in IS NULL) THEN
        INSERT INTO tbl_logs(action, board, new_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{hexCode:', hexCode_in, ',name:', name_in, ',status:', status_in, '}'),
                user_id_in);

    ELSEIF (action_in = 'Actualizar' AND id_in IS NOT NULL) THEN

        SELECT CONCAT('{hexCode:', hexCode, ',name:', name, ',status:', status, '}')
        INTO data_from_db
        FROM tbl_colors t
        WHERE t.id_color = id_in;

        INSERT INTO tbl_logs(action, board, new_data, old_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{hexCode:', hexCode_in, ',name:', name_in, ',status:', status_in, '}'),
                data_from_db,
                user_id_in);

    ELSE

        SELECT CONCAT('{hexCode:', hexCode, ',name:', name, ',status:', status, '}')
        INTO data_from_db
        FROM tbl_colors t
        WHERE t.id_color = id_in;

        INSERT INTO tbl_logs(action, board, old_data, new_data, user_id)
        VALUES (action_in, table_in,
                data_from_db,
                null,
                user_id_in);
    END IF;
END $$;

DROP PROCEDURE IF EXISTS log_tbl_characters;
DELIMITER $$;
CREATE PROCEDURE log_tbl_characters(
    action_in VARCHAR(20),
    id_in INT,
    description_in VARCHAR(60),
    name_in VARCHAR(60),
    status_in TINYINT(1),
    user_id_in INT)
BEGIN
    DECLARE table_in VARCHAR(50) DEFAULT 'characters';
    DECLARE data_from_db TEXT;

    IF (action_in = 'Crear' AND id_in IS NULL) THEN
        INSERT INTO tbl_logs(action, board, new_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{description:', description_in, ',name:', name_in, ',status:', status_in, '}'),
                user_id_in);

    ELSEIF (action_in = 'Actualizar' AND id_in IS NOT NULL) THEN

        SELECT CONCAT('{description:', COALESCE(description, 'null'), ',name:', name, ',status:', status, '}')
        INTO data_from_db
        FROM tbl_characters t
        WHERE t.id_character = id_in;

        INSERT INTO tbl_logs(action, board, new_data, old_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{description:', description_in, ',name:', name_in, ',status:', status_in, '}'),
                data_from_db,
                user_id_in);

    ELSE

        SELECT CONCAT('{description:', COALESCE(description, 'null'), ',name:', name, ',status:', status, '}')
        INTO data_from_db
        FROM tbl_characters t
        WHERE t.id_character = id_in;


        INSERT INTO tbl_logs(action, board, old_data, new_data, user_id)
        VALUES (action_in, table_in,
                data_from_db,
                null,
                user_id_in);
    END IF;
END $$;

DROP PROCEDURE IF EXISTS log_tbl_address;
DELIMITER $$;
CREATE PROCEDURE log_tbl_address(
    action_in VARCHAR(20),
    id_in INT,
    external_number_in VARCHAR(5),
    internal_number_in VARCHAR(5),
    references_street_in VARCHAR(128),
    street_in VARCHAR(50),
    zip_code_in VARCHAR(50),
    user_id_in INT)
BEGIN
    DECLARE table_in VARCHAR(50) DEFAULT 'address';
    DECLARE data_from_db TEXT;

    IF (action_in = 'Crear' AND id_in IS NULL) THEN
        INSERT INTO tbl_logs(action, board, new_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{external_number:', external_number_in, ',internal_number:', internal_number_in,
                       ',references_street:', references_street_in, ',street:', street_in, ',zip_code:', zip_code_in,
                       '}'),
                user_id_in);

    ELSEIF (action_in = 'Actualizar' AND id_in IS NOT NULL) THEN

        SELECT CONCAT('{external_number:', external_number, ',internal_number:', COALESCE(internal_number, 'null'),
                      ',references_street:',
                      COALESCE(references_street, 'null'), ',street:', street, ',zip_code:', zip_code, '}')
        INTO data_from_db
        FROM tbl_address t
        WHERE t.id_address = id_in;

        INSERT INTO tbl_logs(action, board, new_data, old_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{external_number:', external_number_in, ',internal_number:', internal_number_in,
                       ',references_street:', references_street_in, ',street:', street_in, ',zip_code:', zip_code_in,
                       '}'),
                data_from_db,
                user_id_in);
    ELSE
        SELECT CONCAT('{external_number:', external_number, ',internal_number:', COALESCE(internal_number, 'null'),
                      ',references_street:',
                      COALESCE(references_street, 'null'), ',street:', street, ',zip_code:', zip_code, '}')
        INTO data_from_db
        FROM tbl_address t
        WHERE t.id_address = id_in;

        INSERT INTO tbl_logs(action, board, old_data, new_data, user_id)
        VALUES (action_in, table_in,
                data_from_db,
                null,
                user_id_in);
    END IF;
END $$;

DROP PROCEDURE IF EXISTS log_user;
DELIMITER $$;
CREATE PROCEDURE log_user(action_in VARCHAR(20),
                          id_in INT,
                          username_in VARCHAR(50),
                          password_in VARCHAR(100),
                          enabled_in TINYINT(1),
                          user_id_in INT)
BEGIN

    DECLARE table_in VARCHAR(50) DEFAULT 'users';
    DECLARE data_from_db TEXT;

    IF (action_in = 'Crear' AND id_in IS NULL) THEN
        INSERT INTO tbl_logs(action, board, new_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{"username":', '"', username_in, '"', ',"password":', '"', password_in, '"', ',"enabled":', '"',
                       enabled_in, '"', '}'),
                user_id_in);


    ELSEIF (action_in = 'By' AND id_in IS NOT NULL) THEN

        SELECT CONCAT('{"username":', '"', username, '"', ',"password":', '"', password, '"', ',"enabled":', '"',
                      enabled, '"', '}')
        INTO data_from_db
        FROM users u
        WHERE u.id_user = id_in;

        INSERT INTO tbl_logs(action, board, new_data, old_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{"username":', '"', username_in, '"', ',"password":', '"', password_in, '"', ',"enabled":', '"',
                       enabled_in, '"', '}'),
                data_from_db,
                user_id_in);

    ELSE

        SELECT CONCAT('{"username":', '"', username, '"', ',"password":', '"', password, '"', ',"enabled":', '"',
                      enabled, '"', '}')
        INTO data_from_db
        FROM users u
        WHERE u.username = id_in;

        INSERT INTO tbl_logs(action, board, old_data, new_data, user_id)
        VALUES (action_in, table_in,
                data_from_db,
                null,
                user_id_in);
    END IF;
END $$;

DROP PROCEDURE IF EXISTS log_tbl_sizes;
DELIMITER $$;
CREATE PROCEDURE log_tbl_sizes(action_in VARCHAR(20),
                               id_in INT,
                               name_in VARCHAR(50),
                               size_range_in VARCHAR(100),
                               status_in TINYINT(1),
                               user_id_in INT)
BEGIN

    DECLARE table_in VARCHAR(50) DEFAULT 'sizes';
    DECLARE data_from_db TEXT;

    IF (action_in = 'Crear' AND id_in IS NULL) THEN
        INSERT INTO tbl_logs(action, board, new_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{"name":', '"', name_in, '"', ',"size_range":', '"', size_range_in, '"', ',"status":', '"',
                       status_in, '"', '}'),
                user_id_in);


    ELSEIF (action_in = 'Actualizar' AND id_in IS NOT NULL) THEN

        SELECT CONCAT('{"name":', '"', name, '"', ',"size_range":', '"', COALESCE(size_range, 'null'), '"',
                      ',"status":', '"', status, '"', '}')
        INTO data_from_db
        FROM tbl_sizes s
        WHERE s.id_size = id_in;

        INSERT INTO tbl_logs(action, board, new_data, old_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{"name":', '"', name_in, '"', ',"size_range":', '"', size_range_in, '"', ',"status":', '"',
                       status_in, '"', '}'),
                data_from_db,
                user_id_in);

    ELSE

        SELECT CONCAT('{"name":', '"', name, '"', ',"size_range":', '"', COALESCE(size_range, 'null'), '"',
                      ',"status":', '"', status, '"', '}')
        INTO data_from_db
        FROM tbl_sizes s
        WHERE s.id_size = id_in;

        INSERT INTO tbl_logs(action, board, old_data, new_data, user_id)
        VALUES (action_in, table_in,
                data_from_db,
                null,
                user_id_in);
    END IF;
END $$;

DROP PROCEDURE IF EXISTS log_tbl_request;
DELIMITER $$;
CREATE PROCEDURE log_tbl_request(action_in VARCHAR(20),
                                 id_in INT,
                                 is_accepted_in TINYINT(1),
                                 reason_in VARCHAR(100),
                                 is_canceled_in TINYINT(1),
                                 user_id_in INT)
BEGIN

    DECLARE table_in VARCHAR(50) DEFAULT 'request';
    DECLARE data_from_db TEXT;

    IF (action_in = 'Crear' AND id_in IS NULL) THEN
        INSERT INTO tbl_logs(action, board, new_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{"reason":', '"', reason_in, '"', ',"is_accepted":', '"', is_accepted_in, '"',
                       ',"is_canceled":', '"', is_canceled_in, '"', '}'),
                user_id_in);


    ELSEIF (action_in = 'Actualizar' AND id_in IS NOT NULL) THEN

        SELECT CONCAT('{"reason":', '"', reason, '"', ',"is_accepted":', '"', is_accepted, '"', ',"is_canceled":', '"',
                      is_canceled, '"', '}')
        INTO data_from_db
        FROM tbl_requests r
        WHERE r.id_request = id_in;

        INSERT INTO tbl_logs(action, board, new_data, old_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{"reason":', '"', reason_in, '"', ',"is_accepted":', '"', is_accepted_in, '"',
                       ',"is_canceled":', '"', is_canceled_in, '"', '}'),
                data_from_db,
                user_id_in);

    ELSE

        SELECT CONCAT('{"reason":', '"', reason, '"', ',"is_accepted":', '"', is_accepted, '"', ',"is_canceled":', '"',
                      is_canceled, '"', '}')
        INTO data_from_db
        FROM tbl_requests r
        WHERE r.id_request = id_in;

        INSERT INTO tbl_logs(action, board, old_data, new_data, user_id)
        VALUES (action_in, table_in,
                data_from_db,
                null,
                user_id_in);
    END IF;
END $$;

DROP PROCEDURE IF EXISTS log_tbl_pets_adopted;
DELIMITER $$;
CREATE PROCEDURE log_tbl_pets_adopted(action_in VARCHAR(20),
                                      id_in INT,
                                      is_accepted_in TINYINT(1),
                                      is_canceled_in TINYINT(1),
                                      user_id_in INT)
BEGIN

    DECLARE table_in VARCHAR(50) DEFAULT 'pets_adopted';
    DECLARE data_from_db TEXT;

    IF (action_in = 'Crear' AND id_in IS NULL) THEN
        INSERT INTO tbl_logs(action, board, new_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{"is_accepted":', '"', is_accepted_in, '"', ',"is_canceled":', '"', is_canceled_in, '"', '}'),
                user_id_in);


    ELSEIF (action_in = 'Actualizar' AND id_in IS NOT NULL) THEN

        SELECT CONCAT('{"is_accepted":', '"', is_accepted, '"', ',"is_canceled":', '"', is_canceled, '"', '}')
        INTO data_from_db
        FROM tbl_pets_adopted r
        WHERE r.id_pet_adopted = id_in;

        INSERT INTO tbl_logs(action, board, new_data, old_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{"is_accepted":', '"', is_accepted_in, '"', ',"is_canceled":', '"', is_canceled_in, '"', '}'),
                data_from_db,
                user_id_in);

    ELSE

        SELECT CONCAT('{"is_accepted":', '"', is_accepted, '"', ',"is_canceled":', '"', is_canceled, '"', '}')
        INTO data_from_db
        FROM tbl_pets_adopted r
        WHERE r.id_pet_adopted = id_in;

        INSERT INTO tbl_logs(action, board, old_data, new_data, user_id)
        VALUES (action_in, table_in,
                data_from_db,
                null,
                user_id_in);
    END IF;
END $$;

DROP PROCEDURE IF EXISTS log_tbl_profile;
DELIMITER $$;
CREATE PROCEDURE log_tbl_profile(
    action_in VARCHAR(20),
    id_in INT,
    username_in VARCHAR(50),
    password_in VARCHAR(100),
    enabled_in TINYINT(1),
    name_in VARCHAR(50),
    last_name_in VARCHAR(50),
    second_name_in VARCHAR(50),
    phone_in VARCHAR(17),
    image_in VARCHAR(250),
    external_number_in VARCHAR(5),
    internal_number_in VARCHAR(5),
    street_in VARCHAR(128),
    zip_code_in VARCHAR(50),
    reference_street_in VARCHAR(50),

    user_id_in INT)
BEGIN

    DECLARE table_in VARCHAR(50) DEFAULT 'profile';
    DECLARE data_from_db TEXT;

    IF (action_in = 'Crear' AND id_in IS NULL) THEN
        INSERT INTO tbl_logs(action, board, new_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{','"email":', '"', COALESCE(username_in, 'null'), '"',',"enabled":', COALESCE(enabled_in, 'null'),',"password":', '"', COALESCE(password_in, 'null'), '"',',"name":', '"', COALESCE(name_in, 'null'), '"', ',"last_name":', '"', COALESCE(last_name_in, 'null'),
                       '"', ',"second_name":', '"', COALESCE(second_name_in, 'null'), '"', ',"phone":', '"',
                       COALESCE(phone_in, 'null'), '"', ',"image":', '"', COALESCE(image_in, 'null'), '"',
                       ',"address":{', '"internal_number":','"',COALESCE(internal_number_in, 'null'),'"', ',"external_number":', '"',COALESCE(external_number_in, 'null'),'"', ',"street":', '"', COALESCE(street_in, 'null'), '"', ',"zip_code":', '"', COALESCE(zip_code_in, 'null'), '"', ',"references_street":', '"', COALESCE(reference_street_in, 'null'),'"','}','}'),
                user_id_in);


    ELSEIF (action_in = 'Actualizar' AND id_in IS NOT NULL) THEN

        SELECT CONCAT('{','"email":', '"', COALESCE(u.username, 'null'), '"',',"enabled":', COALESCE(u.enabled, 'null'),',"password":', '"', COALESCE(u.password, 'null'), '"',',"name":', '"', COALESCE(name, 'null'), '"', ',"last_name":', '"', COALESCE(last_name, 'null'),
                      '"', ',"second_name":', '"', COALESCE(second_name, 'null'), '"', ',"phone":', '"',
                      COALESCE(phone, 'null'), '"', ',"image":', '"', COALESCE(image, 'null'), '"',
                      ',"address":{', '"internal_number":','"',COALESCE(a.internal_number, 'null'),'"', ',"external_number":', '"',COALESCE(a.external_number, 'null'), '"',',"street":', '"', COALESCE(a.street, 'null'), '"', ',"zip_code":', '"', COALESCE(a.zip_code, 'null'), '"', ',"references_street":', '"', COALESCE(a.references_street, 'null'), '"','}','}')
        INTO data_from_db
        FROM tbl_profiles p
                 join users u on p.user_id = u.id_user
                 join tbl_address a on p.address_id = a.id_address
        WHERE p.id_profile = id_in;

        INSERT INTO tbl_logs(action, board, new_data, old_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{','"email":', '"', COALESCE(username_in, 'null'), '"',',"enabled":', COALESCE(enabled_in, 'null'),',"password":', '"', COALESCE(password_in, 'null'), '"',',"name":', '"', COALESCE(name_in, 'null'), '"', ',"last_name":', '"', COALESCE(last_name_in, 'null'),
                       '"', ',"second_name":', '"', COALESCE(second_name_in, 'null'), '"', ',"phone":', '"',
                       COALESCE(phone_in, 'null'), '"', ',"image":', '"', COALESCE(image_in, 'null'), '"',
                       ',"address":{', '"internal_number":','"',COALESCE(internal_number_in, 'null'),'"', ',"external_number":', '"',COALESCE(external_number_in,'"', 'null'), ',"street":', '"', COALESCE(street_in, 'null'), '"', ',"zip_code":', '"', COALESCE(zip_code_in, 'null'), '"', ',"references_street":', '"', COALESCE(reference_street_in, 'null'),'"','}','}'),
                data_from_db,
                user_id_in);

    ELSE

        SELECT CONCAT('{','"email":', '"', COALESCE(u.username, 'null'), '"',',"enabled":', COALESCE(u.enabled, 'null'),',"password":', '"', COALESCE(u.password, 'null'), '"',',"name":', '"', COALESCE(name, 'null'), '"', ',"last_name":', '"', COALESCE(last_name, 'null'),
                      '"', ',"second_name":', '"', COALESCE(second_name, 'null'), '"', ',"phone":', '"',
                      COALESCE(phone, 'null'), '"', ',"image":', '"', COALESCE(image, 'null'), '"',
                      ',"address":{', '"internal_number":','"',COALESCE(a.internal_number, 'null'),'"', ',"external_number":', '"',COALESCE(a.external_number, 'null'), '"',',"street":', '"', COALESCE(a.street, 'null'), '"', ',"zip_code":', '"', COALESCE(a.zip_code, 'null'), '"', ',"references_street":', '"', COALESCE(a.references_street, 'null'), '"','}','}')
        INTO data_from_db
        FROM tbl_profiles p
                 join users u on p.user_id = u.id_user
                 join tbl_address a on p.address_id = a.id_address
        WHERE p.id_profile = id_in;

        INSERT INTO tbl_logs(action, board, old_data, new_data, user_id)
        VALUES (action_in, table_in,
                data_from_db,
                null,
                user_id_in);
    END IF;
END $$;

DROP PROCEDURE IF EXISTS log_tbl_tags;
DELIMITER $$;
CREATE PROCEDURE log_tbl_tags(action_in VARCHAR(20),
                              id_in INT,
                              name_in VARCHAR(20),
                              description_in VARCHAR(50),
                              user_id_in INT)
BEGIN

    DECLARE table_in VARCHAR(50) DEFAULT 'tags';
    DECLARE data_from_db TEXT;

    IF (action_in = 'Crear' AND id_in IS NULL) THEN
        INSERT INTO tbl_logs(action, board, new_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{"name":', '"', name_in, '"', ',"description":', '"', description_in, '}'),
                user_id_in);


    ELSEIF (action_in = 'Actualizar' AND id_in IS NOT NULL) THEN

        SELECT CONCAT('{"name":', '"', name, '"', ',"description":', '"', COALESCE(description, 'null'), '}')
        INTO data_from_db
        FROM tbl_tags t
        WHERE t.id_tag = id_in;

        INSERT INTO tbl_logs(action, board, new_data, old_data, user_id)
        VALUES (action_in,
                table_in,
                CONCAT('{"name":', '"', name_in, '"', ',"description":', '"', description_in, '}'),
                data_from_db,
                user_id_in);

    ELSE

        SELECT CONCAT('{"name":', '"', name, '"', ',"description":', '"', COALESCE(description, 'null'), '}')
        INTO data_from_db
        FROM tbl_tags t
        WHERE t.id_tag = id_in;

        INSERT INTO tbl_logs(action, board, old_data, new_data, user_id)
        VALUES (action_in, table_in,
                data_from_db,
                null,
                user_id_in);
    END IF;
END $$;

INSERT INTO adoptame.tbl_pets (age, breed, gender, is_active, is_adopted, name, character_id, color_id, size_id,
                               type_id, user_id, description)
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
       ('Joven', 'Pug', 1, 1, 0, 'Puggy', 3, 6, 1, 1, 1,
        'Una mascota muy comportada y bonita con el que puedes compartir momento con tu familia');

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

INSERT INTO adoptame.tbl_news (content, image, is_main, is_published, title, user_id)
VALUES ('<p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Un animal puede ser adoptado de un albergue o de un hogar de paso y en ambos casos, al adoptar, se salva la vida del adoptado y de un nuevo animal en estado vulnerable que tomará su lugar para ser rehabilitado. Sin embargo, como todas las decisiones importantes, la adopción debe ser un consenso entre todos los miembros de una familia.</span></p><p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Es importante plantearse algunas preguntas para reflexionar bien antes de acoger a un animal de compañía en casa.</span></p><p style="font-family: sans-serif; line-height: 1.1; color: rgb(51, 51, 51); margin-top: 3.5rem; margin-bottom: 10px;"><b><span style="font-family: Montserrat;">¿Por qué quiere un animal?</span></b></p><p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Adoptar un animal solo por “moda”, “capricho” o porque “los niños han pedido un cachorro para Navidad” generalmente termina siendo un error. Hay que recordar que los animales de compañía viven alrededor de 10 a 15 años en el caso de los perros, o hasta 20 años en el caso de los gatos. ¿Tiene tiempo para cuidarlo, ejercitarlo, bañarlo, alimentarlo, peinarlo? Los animales de compañía no pueden ser ignorados solo porque se está cansado u ocupado. Necesitan de cuidado y compañía todos los días. ¿Cuenta con los recursos económicos? Cuidado veterinario de rutina, emergencias de salud, juguetes, comida, accesorios, guardería, entrenamiento. Todo esto conlleva costos que deben asumirse. ¿Tiene el espacio suficiente para un animal? ¿Su arrendador le permite tener animales? ¿Qué hará con el animal si se muda de vivienda, de ciudad o país? ¿Sabe quién lo cuidará cuando salga de vacaciones? ¿Conoce los costos de una guardería u hotel para animales? ¿Está preparado para mantener y cuidar a su animal por el resto de su vida? Adoptar un animal es un compromiso de por vida. Puede parecer una lista muy larga de preguntas, pero muchos animales terminan en las calles o en los albergues porque sus acudientes no pensaron en ellas antes de adoptar. Si usted desea adoptar un perro o gato, puede ponerse en contacto con la cantidad de refugios y hogares de paso en su ciudad. Recuerde que si no puede adoptar, puede apadrinar o ser voluntario. Las personas a cargo de los procesos de adopción además de ser muy cuidadosos y meticulosos con la elección de las nuevas familias, deben tener en cuenta el uso de protocolos tanto para la selección de los adoptantes como para el seguimiento presencial después de entregar al animal, por medio de visitas domiciliarias. Dicho seguimiento es fundamental ya que a través de él se podrá saber realmente si el animal está bien en su nuevo hogar, permitirá medir la adaptación del mismo con su nueva familia y garantizará realmente el éxito de la adopción. Además al realizar seguimiento se podrá asesorar a los acudientes sobre la resolución de posibles problemas que pueden presentarse, previniendo que estos sean causa de un nuevo abandono.</span></p><p style="font-family: sans-serif; line-height: 1.1; color: rgb(51, 51, 51); margin-top: 3.5rem; margin-bottom: 10px;"><span style="font-weight: 700; font-family: Montserrat;">¿Por qué adoptar una mascota?</span></p><p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Salva una vida. Muchas personas abandonan a sus mascotas en la calle mientras que otras las entregan en albergues o refugios. Debido al poco espacio en estos lugares, en muchos casos los animales son sacrificados. Los que terminan en la calle pueden morir de hambre, atropellados, abusados o enfermos.</span></p><p style="font-family: sans-serif; line-height: 1.1; color: rgb(51, 51, 51); margin-top: 3.5rem; margin-bottom: 10px;"><span style="font-weight: 700; font-family: Montserrat;">Le brindan amor incondicional</span></p><p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Pase lo que pase, la mascota siempre estará disponible y será la primera que se alegre cuando llegue a casa. El amor que brindan los animales es el más puro.</span></p><p style="margin-bottom: 10px;"><font color="#333333" face="Montserrat"><span style="font-size: 14px;">Las mascotas que se encuentran en tiendas, por lo general, viven dentro de jaulas y lejos del calor humano. Además, se suele desconocer su origen y si fueron criadas responsable mente. Al comprar, se continúa con el ciclo de venta y encierro.</span></font></p><p style="font-family: sans-serif; line-height: 1.1; color: rgb(51, 51, 51); margin-top: 3.5rem; margin-bottom: 10px;"><span style="font-weight: 700; font-family: Montserrat;">Compañía</span></p><p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Tener una mascota hará que nunca se sienta solo. Son fieles compañeros.</span></p><p style="font-family: sans-serif; line-height: 1.1; color: rgb(51, 51, 51); margin-top: 3.5rem; margin-bottom: 10px;"><span style="font-weight: 700; font-family: Montserrat;">Lo mantienen activo</span></p><p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Pasar tiempo paseando al perro o jugando con el gato es una buena manera de hacer ejercicio, de fortalecer el vínculo con la mascota y, además, en el caso de los perros, lo expone a socializar con otras personas.</span></p><p style="font-family: sans-serif; line-height: 1.1; color: rgb(51, 51, 51); margin-top: 3.5rem; margin-bottom: 10px;"><span style="font-weight: 700; font-family: Montserrat;">Se le da oportunidad a otros</span></p><p style="margin-bottom: 10px; color: rgb(51, 51, 51); font-family: sans-serif; font-size: 14px;"><span style="font-family: Montserrat;">Al adoptar, no solo ilumina la vida de un animal, además ayuda a liberar espacio en un albergue, lo que permite rescatar a más animales abandonados a su suerte.</span></p>',
        'https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/205/8c6622de-704f-4942-9af5-1efadca5647f.jpg',
        1, 1, 'Antes de adoptar', 1);

INSERT INTO tbl_tags_news (id_news, id_tag)
VALUES (1, 1),
       (1, 3);

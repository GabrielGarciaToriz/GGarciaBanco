CREATE OR REPLACE PROCEDURE agregar_usuario (
    p_id_usuario       OUT NUMBER,
    p_public_id        IN VARCHAR2,
    p_nombre           IN VARCHAR2,
    p_apellido_paterno IN VARCHAR2,
    p_apellido_materno IN VARCHAR2,
    p_correo           IN VARCHAR2,
    p_password_hash    IN VARCHAR2,
    p_id_banco         IN NUMBER,
    p_numero_tarjeta   IN VARCHAR2
) AS
BEGIN
    INSERT INTO usuario (
        public_id,
        nombre,
        apellido_paterno,
        apellido_materno,
        correo,
        password_hash
    ) VALUES (
        p_public_id,
        p_nombre,
        p_apellido_paterno,
        p_apellido_materno,
        p_correo,
        p_password_hash
    ) RETURNING id_usuario INTO p_id_usuario;

    INSERT INTO tarjeta (
        id_usuario,
        id_banco,
        numero_tarjeta
    ) VALUES (
        p_id_usuario,
        p_id_banco,
        p_numero_tarjeta
    );

END agregar_usuario;
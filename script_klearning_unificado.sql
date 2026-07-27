-- ============================================================================
-- BASE DE DATOS klearning - SCRIPT UNIFICADO (ESQUEMA + DATOS)
-- Unifica: klearning_schema_camelCase.sql + klearning_inserts.sql
-- Listo para ejecutarse de principio a fin en MySQL Workbench.
--
-- Convención: columnas en camelCase, nombres completos y descriptivos.
-- Compatible con MySQL 8.0+
--
-- Convenciones usadas en este script:
--  - Tablas: snake_case, minúsculas, plural       -> roles, personas, proyecto_lider
--  - Columnas: camelCase                          -> idPersona, fechaNacimiento
--  - PK de cada tabla: id + NombreEntidadSingular  -> idRol, idPersona, idProyecto
--  - FK: mismo nombre que la PK que referencian    -> idPersona, idRol, idProyecto
--  - Auditoría uniforme en todas las tablas: createUser, createDate,
--    updatedUser, updatedDate, deletedDate, deletedUser
--
-- ORDEN DE EJECUCIÓN EN ESTE ARCHIVO:
--   1) Creación de base de datos y tablas
--   2) Creación de stored procedures
--   3) Inserts de datos (migrados desde el dump original)
-- ============================================================================


-- ############################################################################
-- PARTE 1: CREACIÓN DE BASE DE DATOS Y TABLAS
-- ############################################################################

DROP DATABASE IF EXISTS klearning;
CREATE DATABASE klearning CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE klearning;

-- ============================================================================
-- TABLA: roles
-- ============================================================================
CREATE TABLE roles (
    idRol         VARCHAR(36)      NOT NULL,
    nombreRol     VARCHAR(100) NOT NULL,
    descripcion   VARCHAR(200) NOT NULL,
    estado        CHAR(1)      NOT NULL DEFAULT '1',
    createUser    VARCHAR(50)  NOT NULL,
    createDate    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedUser   VARCHAR(50)  DEFAULT NULL,
    updatedDate   DATETIME     DEFAULT NULL,
    deletedDate   DATETIME     DEFAULT NULL,
    deletedUser   VARCHAR(50)  DEFAULT NULL,
    PRIMARY KEY (idRol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ============================================================================
-- TABLA: personas
-- ============================================================================
CREATE TABLE personas (
    idPersona            VARCHAR(36)      NOT NULL,
    apellidos            VARCHAR(200) NOT NULL,
    nombres              VARCHAR(200) NOT NULL,
    dniCe                CHAR(12)     NOT NULL,
    sexo                 CHAR(1)      NOT NULL,
    fechaNacimiento      DATE         NOT NULL,
    estadoCivil          CHAR(1)      NOT NULL,
    nacionalidad         VARCHAR(50)  NOT NULL,
    celular              CHAR(9)      NOT NULL,
    email                VARCHAR(100) NOT NULL,
    formacionAcademica   VARCHAR(200) NOT NULL,
    fechaIngreso         DATE         NOT NULL,
    fechaCese            DATE         DEFAULT NULL,
    modalidadTrabajo     VARCHAR(100) NOT NULL,
    skillPrincipal       VARCHAR(100) NOT NULL,
    estado               CHAR(1)      NOT NULL DEFAULT '1',
    createUser           VARCHAR(50)  NOT NULL,
    createDate           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedUser          VARCHAR(50)  DEFAULT NULL,
    updatedDate          DATETIME     DEFAULT NULL,
    deletedDate           DATETIME     DEFAULT NULL,
    deletedUser          VARCHAR(50)  DEFAULT NULL,
    PRIMARY KEY (idPersona)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ============================================================================
-- TABLA: ovas
-- ============================================================================
CREATE TABLE ovas (
    idOva                 VARCHAR(36)      NOT NULL,
    nombre                VARCHAR(200) NOT NULL,
    tipo                  VARCHAR(100) NOT NULL,
    fechaInicioVigencia   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fechaFinVigencia      DATETIME     DEFAULT NULL,
    duracion              INT          NOT NULL,
    estado                CHAR(1)      NOT NULL DEFAULT '1',
    createUser            VARCHAR(50)  NOT NULL,
    createDate            DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedUser           VARCHAR(50)  DEFAULT NULL,
    updatedDate           DATETIME     DEFAULT NULL,
    deletedDate           DATETIME     DEFAULT NULL,
    deletedUser           VARCHAR(50)  DEFAULT NULL,
    PRIMARY KEY (idOva)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ============================================================================
-- TABLA: modulos
-- ============================================================================
CREATE TABLE modulos (
    idModulo       VARCHAR(36)      NOT NULL,
    nombreModulo   VARCHAR(100) NOT NULL,
    descripcion    VARCHAR(200) DEFAULT NULL,
    objetivo       VARCHAR(300) DEFAULT NULL,
    estado         CHAR(1)      NOT NULL DEFAULT '1',
    createUser     VARCHAR(50)  NOT NULL,
    createDate     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedUser    VARCHAR(50)  DEFAULT NULL,
    updatedDate    DATETIME     DEFAULT NULL,
    deletedDate    DATETIME     DEFAULT NULL,
    deletedUser    VARCHAR(50)  DEFAULT NULL,
    PRIMARY KEY (idModulo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ============================================================================
-- TABLA: lecciones
-- ============================================================================
CREATE TABLE lecciones (
    idLeccion       VARCHAR(36)      NOT NULL,
    idModulo        VARCHAR(36)      NOT NULL,
    nombreLeccion   VARCHAR(100) NOT NULL,
    descripcion     VARCHAR(200) DEFAULT NULL,
    objetivo        VARCHAR(300) DEFAULT NULL,
    duracion        INT          NOT NULL,
    estado          CHAR(1)      NOT NULL DEFAULT '1',
    createUser      VARCHAR(50)  NOT NULL,
    createDate      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedUser     VARCHAR(50)  DEFAULT NULL,
    updatedDate     DATETIME     DEFAULT NULL,
    deletedDate     DATETIME     DEFAULT NULL,
    deletedUser     VARCHAR(50)  DEFAULT NULL,
    PRIMARY KEY (idLeccion),
    KEY FK_Leccion_Modulo (idModulo),
    CONSTRAINT FK_Leccion_Modulo FOREIGN KEY (idModulo) REFERENCES modulos (idModulo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ============================================================================
-- TABLA: leccion_ova
-- ============================================================================
CREATE TABLE leccion_ova (
    idLeccionOva   VARCHAR(36)      NOT NULL,
    idLeccion      VARCHAR(36)      NOT NULL,
    idOva          VARCHAR(36)      NOT NULL,
    nombre         VARCHAR(100) NOT NULL,
    descripcion    VARCHAR(200) DEFAULT NULL,
    objetivo       VARCHAR(300) DEFAULT NULL,
    duracion       INT          NOT NULL,
    estado         CHAR(1)      NOT NULL DEFAULT '1',
    createUser     VARCHAR(50)  NOT NULL,
    createDate     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedUser    VARCHAR(50)  DEFAULT NULL,
    updatedDate    DATETIME     DEFAULT NULL,
    deletedDate    DATETIME     DEFAULT NULL,
    deletedUser    VARCHAR(50)  DEFAULT NULL,
    PRIMARY KEY (idLeccionOva),
    KEY FK_LeccionOva_Leccion (idLeccion),
    KEY FK_LeccionOva_Ova (idOva),
    CONSTRAINT FK_LeccionOva_Leccion FOREIGN KEY (idLeccion) REFERENCES lecciones (idLeccion),
    CONSTRAINT FK_LeccionOva_Ova FOREIGN KEY (idOva) REFERENCES ovas (idOva)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ============================================================================
-- TABLA: proyectos
-- ============================================================================
-- NOTA: la columna original "anio" era tipo DATE pero guardaba fechas completas
-- (ej. '2026-06-09'), no solo el año. La renombré a "fecha" por ser más honesta
-- con lo que realmente contiene. Si en verdad solo necesitas el año, cambia el
-- tipo a YEAR y el nombre a "anio".
CREATE TABLE proyectos (
    idProyecto        VARCHAR(36)      NOT NULL,
    fecha             DATE         NOT NULL,
    tipoVertical      VARCHAR(100) NOT NULL,
    nombre            VARCHAR(200) NOT NULL,
    kpis              VARCHAR(100) NOT NULL,
    objetivo          VARCHAR(300) NOT NULL,
    necesidades       VARCHAR(300) NOT NULL,
    cuenta            VARCHAR(100) NOT NULL,
    segmento          VARCHAR(100) NOT NULL,
    areaSolicitante   VARCHAR(200) NOT NULL,
    productOwner      VARCHAR(100) NOT NULL,
    sponsor           VARCHAR(100) NOT NULL,
    fechaSolicitud    DATE         NOT NULL,
    antecedentes      VARCHAR(400) NOT NULL,
    descripcion       VARCHAR(300) DEFAULT NULL,
    estado            CHAR(1)      NOT NULL DEFAULT '1',
    createUser        VARCHAR(50)  NOT NULL,
    createDate        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedUser       VARCHAR(50)  DEFAULT NULL,
    updatedDate       DATETIME     DEFAULT NULL,
    deletedDate       DATETIME     DEFAULT NULL,
    deletedUser       VARCHAR(50)  DEFAULT NULL,
    PRIMARY KEY (idProyecto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ============================================================================
-- TABLA: proyecto_lider
-- ============================================================================
CREATE TABLE proyecto_lider (
    idProyectoLider   VARCHAR(36)     NOT NULL,
    idProyecto        VARCHAR(36)     NOT NULL,
    idPersona         VARCHAR(36)     NOT NULL,
    fechaAsignacion   DATETIME    NOT NULL,
    fechaSalida       DATE        DEFAULT NULL,
    estado            CHAR(1)     NOT NULL DEFAULT '1',
    createUser        VARCHAR(50) NOT NULL,
    createDate        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedUser       VARCHAR(50) DEFAULT NULL,
    updatedDate       DATETIME    DEFAULT NULL,
    deletedDate       DATETIME    DEFAULT NULL,
    deletedUser       VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (idProyectoLider),
    KEY FK_ProyectoLider_Proyecto (idProyecto),
    KEY FK_ProyectoLider_Persona (idPersona),
    CONSTRAINT FK_ProyectoLider_Proyecto FOREIGN KEY (idProyecto) REFERENCES proyectos (idProyecto),
    CONSTRAINT FK_ProyectoLider_Persona FOREIGN KEY (idPersona) REFERENCES personas (idPersona)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ============================================================================
-- TABLA: produccion
-- ============================================================================
CREATE TABLE produccion (
    idProduccion         VARCHAR(36)      NOT NULL,
    idProyecto           VARCHAR(36)      NOT NULL,
    idLeccionOva         VARCHAR(36)      NOT NULL,
    idPersona            VARCHAR(36)      NOT NULL,
    fechaHoraInicio      DATETIME     NOT NULL,
    fechaHoraFin         DATETIME     DEFAULT NULL,
    horasEstimadas       DECIMAL(6,2) NOT NULL,
    horasReales          DECIMAL(6,2) DEFAULT NULL,
    huboCorte            BIT(1)       NOT NULL DEFAULT b'0',
    descripcionCorte     VARCHAR(300) DEFAULT NULL,
    horasContratiempo    DECIMAL(6,2) DEFAULT NULL,
    estadoTarea          VARCHAR(20)  NOT NULL,
    cumplimiento         BIT(1)       DEFAULT NULL,
    observaciones        VARCHAR(300) DEFAULT NULL,
    estado               CHAR(1)      NOT NULL DEFAULT '1',
    createUser           VARCHAR(50)  NOT NULL,
    createDate           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedUser          VARCHAR(50)  DEFAULT NULL,
    updatedDate           DATETIME     DEFAULT NULL,
    deletedDate          DATETIME     DEFAULT NULL,
    deletedUser          VARCHAR(50)  DEFAULT NULL,
    PRIMARY KEY (idProduccion),
    KEY FK_Produccion_Proyecto (idProyecto),
    KEY FK_Produccion_LeccionOva (idLeccionOva),
    KEY FK_Produccion_Persona (idPersona),
    CONSTRAINT FK_Produccion_Proyecto FOREIGN KEY (idProyecto) REFERENCES proyectos (idProyecto),
    CONSTRAINT FK_Produccion_LeccionOva FOREIGN KEY (idLeccionOva) REFERENCES leccion_ova (idLeccionOva),
    CONSTRAINT FK_Produccion_Persona FOREIGN KEY (idPersona) REFERENCES personas (idPersona)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ============================================================================
-- TABLA: usuarios
-- ============================================================================
CREATE TABLE usuarios (
    idUsuario     VARCHAR(36)      NOT NULL,
    idPersona     VARCHAR(36)      NOT NULL,
    idRol         VARCHAR(36)      NOT NULL,
    usuario       VARCHAR(100) NOT NULL,
    password      VARCHAR(300) NOT NULL,
    estado        CHAR(1)      NOT NULL DEFAULT '1',
    createUser    VARCHAR(50)  NOT NULL,
    createDate    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedUser   VARCHAR(50)  DEFAULT NULL,
    updatedDate   DATETIME     DEFAULT NULL,
    deletedDate   DATETIME     DEFAULT NULL,
    deletedUser   VARCHAR(50)  DEFAULT NULL,
    PRIMARY KEY (idUsuario),
    KEY FK_Usuario_Persona (idPersona),
    KEY FK_Usuario_Rol (idRol),
    CONSTRAINT FK_Usuario_Persona FOREIGN KEY (idPersona) REFERENCES personas (idPersona),
    CONSTRAINT FK_Usuario_Rol FOREIGN KEY (idRol) REFERENCES roles (idRol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ############################################################################
-- PARTE 2: STORED PROCEDURES
-- ############################################################################

-- ------------------------------------------------------------
-- ROLES
-- ------------------------------------------------------------
DELIMITER $$
CREATE PROCEDURE sp_listar_rol()
BEGIN
    SELECT * FROM roles WHERE deletedDate IS NULL;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_obtener_rol(IN p_idRol VARCHAR(36))
BEGIN
    SELECT * FROM roles WHERE idRol = p_idRol AND deletedDate IS NULL;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_insertar_rol(
    IN p_nombreRol VARCHAR(100),
    IN p_descripcion VARCHAR(200),
    IN p_createUser VARCHAR(50)
)
BEGIN
    DECLARE v_nuevoCodigo VARCHAR(36);

    SET v_nuevoCodigo = UUID();

    INSERT INTO roles (idRol, nombreRol, descripcion, createUser)
    VALUES (v_nuevoCodigo, p_nombreRol, p_descripcion, p_createUser);

    SELECT v_nuevoCodigo AS codigoGenerado;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_actualizar_rol(
    IN p_idRol VARCHAR(36),
    IN p_nombreRol VARCHAR(100),
    IN p_descripcion VARCHAR(200),
    IN p_updatedUser VARCHAR(50)
)
BEGIN
    UPDATE roles
    SET nombreRol = p_nombreRol,
        descripcion = p_descripcion,
        updatedUser = p_updatedUser,
        updatedDate = NOW()
    WHERE idRol = p_idRol;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_eliminar_rol(
    IN p_idRol VARCHAR(36),
    IN p_deletedUser VARCHAR(50)
)
BEGIN
    UPDATE roles
    SET estado = '2', deletedDate = NOW(), deletedUser = p_deletedUser
    WHERE idRol = p_idRol AND deletedDate IS NULL;
END$$
DELIMITER ;

-- ------------------------------------------------------------
-- PERSONAS
-- ------------------------------------------------------------
DELIMITER $$
CREATE PROCEDURE sp_listar_persona()
BEGIN
    SELECT * FROM personas WHERE deletedDate IS NULL;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_obtener_persona(IN p_idPersona VARCHAR(36))
BEGIN
    SELECT * FROM personas WHERE idPersona = p_idPersona AND deletedDate IS NULL;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_insertar_persona(
    IN p_apellidos VARCHAR(200),
    IN p_nombres VARCHAR(200),
    IN p_dniCe CHAR(12),
    IN p_sexo CHAR(1),
    IN p_fechaNacimiento DATE,
    IN p_estadoCivil CHAR(1),
    IN p_nacionalidad VARCHAR(50),
    IN p_celular CHAR(9),
    IN p_email VARCHAR(100),
    IN p_formacionAcademica VARCHAR(200),
    IN p_fechaIngreso DATE,
    IN p_modalidadTrabajo VARCHAR(100),
    IN p_skillPrincipal VARCHAR(100),
    IN p_createUser VARCHAR(50)
)
BEGIN
    DECLARE v_nuevoCodigo VARCHAR(36);

    SET v_nuevoCodigo = UUID();

    INSERT INTO personas (
        idPersona, apellidos, nombres, dniCe, sexo, fechaNacimiento,
        estadoCivil, nacionalidad, celular, email, formacionAcademica,
        fechaIngreso, modalidadTrabajo, skillPrincipal, createUser
    ) VALUES (
        v_nuevoCodigo, p_apellidos, p_nombres, p_dniCe, p_sexo, p_fechaNacimiento,
        p_estadoCivil, p_nacionalidad, p_celular, p_email, p_formacionAcademica,
        p_fechaIngreso, p_modalidadTrabajo, p_skillPrincipal, p_createUser
    );

    SELECT v_nuevoCodigo AS codigoGenerado;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_actualizar_persona(
    IN p_idPersona VARCHAR(36),
    IN p_apellidos VARCHAR(200),
    IN p_nombres VARCHAR(200),
    IN p_dniCe CHAR(12),
    IN p_sexo CHAR(1),
    IN p_fechaNacimiento DATE,
    IN p_estadoCivil CHAR(1),
    IN p_nacionalidad VARCHAR(50),
    IN p_celular CHAR(9),
    IN p_email VARCHAR(100),
    IN p_formacionAcademica VARCHAR(200),
    IN p_fechaIngreso DATE,
    IN p_modalidadTrabajo VARCHAR(100),
    IN p_skillPrincipal VARCHAR(100),
    IN p_updatedUser VARCHAR(50)
)
BEGIN
    UPDATE personas
    SET apellidos = p_apellidos,
        nombres = p_nombres,
        dniCe = p_dniCe,
        sexo = p_sexo,
        fechaNacimiento = p_fechaNacimiento,
        estadoCivil = p_estadoCivil,
        nacionalidad = p_nacionalidad,
        celular = p_celular,
        email = p_email,
        formacionAcademica = p_formacionAcademica,
        fechaIngreso = p_fechaIngreso,
        modalidadTrabajo = p_modalidadTrabajo,
        skillPrincipal = p_skillPrincipal,
        updatedUser = p_updatedUser,
        updatedDate = NOW()
    WHERE idPersona = p_idPersona;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_eliminar_persona(
    IN p_idPersona VARCHAR(36),
    IN p_deletedUser VARCHAR(50)
)
BEGIN
    UPDATE personas
    SET estado = '2', deletedDate = NOW(), deletedUser = p_deletedUser
    WHERE idPersona = p_idPersona AND deletedDate IS NULL;
END$$
DELIMITER ;

-- ------------------------------------------------------------
-- OVAS
-- ------------------------------------------------------------
DELIMITER $$
CREATE PROCEDURE sp_listar_ova()
BEGIN
    SELECT * FROM ovas WHERE deletedDate IS NULL;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_obtener_ova(IN p_idOva VARCHAR(36))
BEGIN
    SELECT * FROM ovas WHERE idOva = p_idOva AND deletedDate IS NULL;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_insertar_ova(
    IN p_nombre VARCHAR(200),
    IN p_tipo VARCHAR(100),
    IN p_duracion INT,
    IN p_createUser VARCHAR(50)
)
BEGIN
    DECLARE v_nuevoCodigo VARCHAR(36);

    SET v_nuevoCodigo = UUID();

    INSERT INTO ovas (idOva, nombre, tipo, duracion, createUser)
    VALUES (v_nuevoCodigo, p_nombre, p_tipo, p_duracion, p_createUser);

    SELECT v_nuevoCodigo AS codigoGenerado;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_actualizar_ova(
    IN p_idOva VARCHAR(36),
    IN p_nombre VARCHAR(200),
    IN p_tipo VARCHAR(100),
    IN p_duracion INT,
    IN p_fechaVigencia DATE,
    IN p_updatedUser VARCHAR(50)
)
BEGIN
    UPDATE ovas
    SET nombre = p_nombre,
        tipo = p_tipo,
        duracion = p_duracion,
        fechaInicioVigencia = p_fechaVigencia,
        updatedUser = p_updatedUser,
        updatedDate = NOW()
    WHERE idOva = p_idOva;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_eliminar_ova(
    IN p_idOva VARCHAR(36),
    IN p_deletedUser VARCHAR(50)
)
BEGIN
    UPDATE ovas
    SET estado = '2', deletedDate = NOW(), deletedUser = p_deletedUser
    WHERE idOva = p_idOva AND deletedDate IS NULL;
END$$
DELIMITER ;

-- ------------------------------------------------------------
-- USUARIOS
-- ------------------------------------------------------------
DELIMITER $$
CREATE PROCEDURE sp_listar_usuario()
BEGIN
    SELECT
        p.idPersona,
        p.nombres,
        p.apellidos,
        p.dniCe,
        p.skillPrincipal,

        u.idUsuario,
        u.usuario,
        u.password,
        u.createUser,
        u.createDate,
        u.updatedUser,
        u.updatedDate,
        u.deletedUser,
        u.deletedDate,
        u.estado,

        r.idRol,
        r.nombreRol
    FROM usuarios u
    INNER JOIN personas p ON u.idPersona = p.idPersona
    INNER JOIN roles r ON u.idRol = r.idRol;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_obtener_usuario(IN p_idUsuario VARCHAR(36))
BEGIN
    SELECT
        p.idPersona,
        p.nombres,
        p.apellidos,
        p.dniCe,

        u.idUsuario,
        u.usuario,
        u.password,
        u.createUser,
        u.createDate,
        u.updatedUser,
        u.updatedDate,
        u.deletedUser,
        u.deletedDate,
        u.estado,

        r.idRol,
        r.nombreRol
    FROM usuarios u
    INNER JOIN personas p ON u.idPersona = p.idPersona
    INNER JOIN roles r ON u.idRol = r.idRol
    WHERE u.idUsuario = p_idUsuario;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_insertar_usuario(
    IN p_idPersona VARCHAR(36),
    IN p_idRol VARCHAR(36),
    IN p_usuario VARCHAR(100),
    IN p_password VARCHAR(300),
    IN p_createUser VARCHAR(50)
)
BEGIN
    DECLARE v_nuevoCodigo VARCHAR(36);

    SET v_nuevoCodigo = UUID();

    INSERT INTO usuarios (idUsuario, idPersona, idRol, usuario, password, createUser)
    VALUES (v_nuevoCodigo, p_idPersona, p_idRol, p_usuario, p_password, p_createUser);

    SELECT v_nuevoCodigo AS codigoGenerado;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_actualizar_usuario(
    IN p_idUsuario VARCHAR(36),
    IN p_idPersona VARCHAR(36),
    IN p_idRol VARCHAR(36),
    IN p_usuario VARCHAR(100),
    IN p_password VARCHAR(300),
    IN p_updatedUser VARCHAR(50)
)
BEGIN
    UPDATE usuarios
    SET idPersona = p_idPersona,
        idRol = p_idRol,
        usuario = p_usuario,
        password = p_password,
        updatedUser = p_updatedUser,
        updatedDate = NOW()
    WHERE idUsuario = p_idUsuario;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_eliminar_usuario(
    IN p_idUsuario VARCHAR(36),
    IN p_deletedUser VARCHAR(50)
)
BEGIN
    UPDATE usuarios
    SET estado = '2', deletedDate = NOW(), deletedUser = p_deletedUser
    WHERE idUsuario = p_idUsuario AND deletedDate IS NULL;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_validar_usuario(
    IN p_username VARCHAR(100),
    IN p_password VARCHAR(300)
)
BEGIN
    SELECT *
    FROM usuarios
    WHERE TRIM(usuario) = TRIM(p_username)
      AND TRIM(password) = TRIM(p_password);
END$$
DELIMITER ;


-- ############################################################################
-- PARTE 3: INSERTS DE DATOS (migrados desde el dump original)
--
-- CAMBIO IMPORTANTE: los códigos legibles tipo 'ROL00001', 'PER00001', etc.
-- se reemplazaron por UUID() para que coincidan con el esquema (VARCHAR(36))
-- y con la estrategia de generación de la entidad JPA:
--   @Id
--   @GeneratedValue(strategy = GenerationType.UUID)
--
-- Como los UUID son aleatorios, no se pueden escribir directo en los VALUES
-- de las tablas que son referenciadas por otras (roles, personas, ovas,
-- proyectos). Por eso se declara una variable de sesión (@VARIABLE = UUID())
-- por cada fila, y esa misma variable se reutiliza en los INSERT de las
-- tablas hijas para no romper las relaciones (FK) que existían en el dump
-- original. El nombre de la variable conserva el código original solo como
-- referencia visual (@ROL00001, @PER00001, etc.), pero el valor real que se
-- guarda en la base de datos es un UUID.
-- ############################################################################

-- ============================================================================
-- roles
-- ============================================================================
SET @ROL00001 = UUID();
SET @ROL00002 = UUID();
SET @ROL00003 = UUID();
SET @ROL00004 = UUID();
SET @ROL00005 = UUID();
SET @ROL00006 = UUID();
SET @ROL00007 = UUID();
SET @ROL00008 = UUID();
SET @ROL00009 = UUID();
SET @ROL00010 = UUID();

INSERT INTO roles
    (idRol, nombreRol, descripcion, estado, createUser, createDate, updatedUser, updatedDate, deletedDate, deletedUser)
VALUES
    (@ROL00001,'Analista','Proyectos','1','HECTOR','2025-12-15 20:48:36','Hector','2025-12-17 18:40:27',NULL,NULL),
    (@ROL00002,'Jefatura','Coordina los proyectos iniciales','2','HECTOR','2025-12-15 21:38:02','Hector','2025-12-15 21:38:36','2025-12-15 21:38:46',NULL),
    (@ROL00003,'Jefatura','Supervision','1','HECTOR','2025-12-15 21:46:57','Hector','2025-12-17 18:40:35',NULL,NULL),
    (@ROL00004,'Diseñador Instruccional','Inicio de proyectos.','1','HECTOR','2025-12-17 18:33:18',NULL,NULL,NULL,NULL),
    (@ROL00005,'Analista','Descripcion','1','HECTOR','2025-12-21 17:37:17',NULL,NULL,NULL,NULL),
    (@ROL00006,'Administrador de BD','Analista Administrador','1','HECTOR','2025-12-22 18:30:47',NULL,NULL,NULL,NULL),
    (@ROL00007,'Consejero','Corta cabezas 2','2','hquispe2','2026-06-13 04:24:00','hquispe2','2026-06-13 17:48:47','2026-06-13 17:48:50','hquispe2'),
    (@ROL00008,'Admin_Madrugador','Roderick_tu_rol_ideal','1','hquispe2','2026-06-13 18:21:01',NULL,NULL,NULL,NULL),
    (@ROL00009,'Jefe de Proyectos','Control de producción','1','hquispec','2026-06-24 01:46:05','hquispec','2026-06-24 01:47:02',NULL,NULL),
    (@ROL00010,'Jefatura de Diseño','xxx','1','hquispec','2026-06-24 23:26:34',NULL,NULL,NULL,NULL);

-- ============================================================================
-- personas
-- ============================================================================
SET @PER00001 = UUID();
SET @PER00002 = UUID();
SET @PER00003 = UUID();
SET @PER00004 = UUID();
SET @PER00005 = UUID();
SET @PER00006 = UUID();
SET @PER00007 = UUID();
SET @PER00008 = UUID();
SET @PER00009 = UUID();
SET @PER00010 = UUID();
SET @PER00011 = UUID();

INSERT INTO personas
    (idPersona, apellidos, nombres, dniCe, sexo, fechaNacimiento, estadoCivil, nacionalidad, celular, email,
     formacionAcademica, fechaIngreso, fechaCese, modalidadTrabajo, skillPrincipal, estado,
     createUser, createDate, updatedUser, updatedDate, deletedDate, deletedUser)
VALUES
    (@PER00001,'Quispe Gómez','Héctor Luis','12345678','M','1985-06-15','S','1','987654321','hector@gmail.com','Técnico','2024-01-15',NULL,'Remoto','Programador','1','HECTOR','2025-12-17 23:27:40',NULL,NULL,NULL,NULL),
    (@PER00002,'MENDOZA JUAREZ','RAUL','32233222','M','2025-12-18','S','3','987788766','raul.mendoza69@gmail.com','UNIVERSITARIA','2025-12-18',NULL,'REMOTO','PROGRAMADOR','1','HECTOR','2025-12-18 23:23:02','Hector','2025-12-18 23:32:59',NULL,NULL),
    (@PER00003,'QUISPE','BRAYAN','32233222','M','2025-12-18','S','2','987788766','brayan06@gmail.com','TECNICO','2025-12-18',NULL,'SEMIPRESENCIAL','DISEÑADOR GRAFICO','1','HECTOR','2025-12-18 23:27:21','Hector','2025-12-18 23:33:05',NULL,NULL),
    (@PER00004,'BAZAN','ENRIQUE','45678900','M','2025-12-18','C','2','987788766','enrique@gmail.com','SECUNDARIA','2025-12-18',NULL,'SEMIPRESENCIAL','PROGRAMADOR','2','HECTOR','2025-12-18 23:29:02',NULL,NULL,'2025-12-21 10:21:28',NULL),
    (@PER00005,'Ramírez','Lorena ','77673323','F','1986-12-08','C','3','987788766','lorena@hotmail.com','TECNICO','2025-12-21',NULL,'SEMIPRESENCIAL','PROGRAMADOR','1','HECTOR','2025-12-21 22:14:35',NULL,NULL,NULL,NULL),
    (@PER00006,'Gutierrez','Elena ','45434333','F','2025-12-22','S','1','987788766','elena.ramirez@gmail.com','TECNICO','2025-12-22',NULL,'SEMIPRESENCIAL','DISEÑADOR GRAFICO','1','HECTOR','2025-12-22 14:28:12',NULL,NULL,NULL,NULL),
    (@PER00007,'Quispe Coronado','Bryan Arturo','77656566','M','1990-06-12','S','1','987788766','bryan.segundo@gmail.com','UNIVERSITARIA','2025-12-22',NULL,'PRESENCIAL','PROGRAMADOR','1','HECTOR','2025-12-22 18:30:27','Hector','2026-06-13 12:49:47',NULL,NULL),
    (@PER00008,'QUISPE CORONADO','DIEGO GAEL','44433322','M','2009-12-27','S','1','987788766','diego.peru@gmail.com','SECUNDARIA','2026-06-13',NULL,'REMOTO','DISEÑADOR GRAFICO','2','hquispe2','2026-06-13 18:07:51','hquispe2','2026-06-13 18:08:17','2026-06-15 00:24:31','hquispec'),
    (@PER00009,'ESPINOZA FERRO','RODERICK D','77665544','M','2000-05-08','V','1','987788766','roderick_madrugador@gmail.com','SECUNDARIA','2026-06-13',NULL,'SEMIPRESENCIAL','PROGRAMADOR','1','hquispe2','2026-06-13 18:22:01','hquispec','2026-06-15 00:24:43',NULL,NULL),
    (@PER00010,'Coronado De La Cruz','Elisa','44433322','F','2006-01-24','S','1','987788766','elisa.coronado@gmail.com','SECUNDARIA','2026-06-24',NULL,'SEMIPRESENCIAL','DISEÑO INSTRUCCIONAL','1','hquispec','2026-06-24 23:26:00',NULL,NULL,NULL,NULL),
    (@PER00011,'Ampuero','Franco ','33445566','M','2004-01-24','S','1','987788766','groupfayferr.15@gmail.com','SECUNDARIA','2026-06-24',NULL,'SEMIPRESENCIAL','DISEÑO INSTRUCCIONAL','1','hquispec','2026-06-25 01:01:25',NULL,NULL,NULL,NULL);

-- ============================================================================
-- ovas
-- ============================================================================
SET @OVA00001 = UUID();
SET @OVA00002 = UUID();
SET @OVA00003 = UUID();
SET @OVA00004 = UUID();
SET @OVA00005 = UUID();

INSERT INTO ovas
    (idOva, nombre, tipo, fechaInicioVigencia, fechaFinVigencia, duracion, estado,
     createUser, createDate, updatedUser, updatedDate, deletedDate, deletedUser)
VALUES
    (@OVA00001,'2D','TEORICO','2025-12-21 22:37:29',NULL,30,'2','HECTOR','2025-12-21 22:37:29',NULL,NULL,'2025-12-21 22:38:16',NULL),
    (@OVA00002,'SIMULADORES','PRÁCTICO','2025-12-21 00:00:00',NULL,15,'1','HECTOR','2025-12-21 22:38:01','Hector','2026-01-02 15:26:27',NULL,NULL),
    (@OVA00003,'2D','TEÓRICO','2025-12-22 13:03:45',NULL,10,'1','HECTOR','2025-12-22 13:03:45',NULL,NULL,NULL,NULL),
    (@OVA00004,'INFOVISUAL','PRÁCTICO','2025-12-22 00:00:00',NULL,20,'2','HECTOR','2025-12-22 19:18:49','Hector','2025-12-22 19:18:58','2025-12-22 19:19:46',NULL),
    (@OVA00005,'LABS','PRÁCTICO','2026-06-24 18:27:15',NULL,10,'1','HECTOR','2026-06-24 18:27:15',NULL,NULL,NULL,NULL);

-- ============================================================================
-- proyectos
-- ============================================================================
-- NOTA: la columna "anio" del dump original se renombró a "fecha" en el nuevo
-- esquema porque el tipo es DATE y almacenaba fechas completas, no solo el año.
SET @PROY0001 = UUID();
SET @PROY0002 = UUID();
SET @PROY0003 = UUID();

INSERT INTO proyectos
    (idProyecto, fecha, tipoVertical, nombre, kpis, objetivo, necesidades, cuenta, segmento,
     areaSolicitante, productOwner, sponsor, fechaSolicitud, antecedentes, descripcion, estado,
     createUser, createDate, updatedUser, updatedDate, deletedDate, deletedUser)
VALUES
    (@PROY0001,'2026-06-09','SEGUROS','Proyecto Microlearning ATC ','NPS, TMO','xxx222333','xxx222','Pacifico','ATC','Centro de Capacitación','Omar Valdivieso','Claudia Valdez','2026-06-16','Cliente necesita capacitar a sus agentes de atención al cliente sobre reclamos consecutivos de error','xxx','1','hquispec','2026-06-20 22:03:05','hquispec','2026-06-20 22:07:46',NULL,NULL),
    (@PROY0002,'2026-06-24','BANCA','CONTROLAR NPS - GESTIÓN DE CALIDAD','NPS','Gestionar Habilidades Blandas. ','XX','BANMEDICA','ATC','OPERACIONES','RAUL GOMEZ','SELENA URIARTE','2026-06-24','Cliente necesita capacitar a sus agentes de atención al cliente sobre reclamos consecutivos de error','xx','1','hquispec','2026-06-24 23:28:31',NULL,NULL,NULL,NULL),
    (@PROY0003,'2026-06-24','BANCA','BANCA MOVIL','NPS','XX','XX','CLARO','ATC','Centro de Capacitación','Omar Valdivieso','CLAUDIA RAMIREZ','2026-06-24','Cliente necesita capacitar a sus agentes de atención al cliente sobre reclamos consecutivos de error','XX','1','hquispec','2026-06-25 01:02:24',NULL,NULL,NULL,NULL);

-- ============================================================================
-- proyecto_lider
-- ============================================================================
SET @LID0001 = UUID();
SET @LID0002 = UUID();
SET @LID0003 = UUID();
SET @LID0004 = UUID();
SET @LID0005 = UUID();

INSERT INTO proyecto_lider
    (idProyectoLider, idProyecto, idPersona, fechaAsignacion, fechaSalida, estado,
     createUser, createDate, updatedUser, updatedDate, deletedDate, deletedUser)
VALUES
    (@LID0001,@PROY0001,@PER00001,'2026-06-20 05:00:00',NULL,'1','hquispec','2026-06-21 00:03:04','hquispec','2026-06-21 00:21:22',NULL,NULL),
    (@LID0002,@PROY0001,@PER00002,'2026-06-20 05:00:00','2026-06-20','2','hquispec','2026-06-21 00:09:13','hquispec','2026-06-21 00:26:53','2026-06-21 00:27:28','hquispec'),
    (@LID0003,@PROY0001,@PER00002,'2026-06-21 05:00:00',NULL,'1','hquispec','2026-06-21 00:27:42',NULL,NULL,NULL,NULL),
    (@LID0004,@PROY0002,@PER00010,'2026-06-24 05:00:00',NULL,'1','hquispec','2026-06-24 23:28:48',NULL,NULL,NULL,NULL),
    (@LID0005,@PROY0003,@PER00011,'2026-06-24 05:00:00',NULL,'1','hquispec','2026-06-25 01:02:56',NULL,NULL,NULL,NULL);

-- ============================================================================
-- usuarios
-- ============================================================================
SET @USER0001 = UUID();
SET @USER0002 = UUID();
SET @USER0003 = UUID();
SET @USER0004 = UUID();
SET @USER0005 = UUID();
SET @USER0006 = UUID();

INSERT INTO usuarios
    (idUsuario, idPersona, idRol, usuario, password, estado,
     createUser, createDate, updatedUser, updatedDate, deletedDate, deletedUser)
VALUES
    (@USER0001,@PER00001,@ROL00002,'hquispec','123456','1','HECTOR','2025-12-21 17:58:39','roderick_facherito','2026-06-13 19:04:52',NULL,NULL),
    (@USER0002,@PER00002,@ROL00004,'rmendoza','123456','1','HECTOR','2025-12-22 13:24:34','Hector','2025-12-22 13:29:47',NULL,NULL),
    (@USER0003,@PER00006,@ROL00004,'elenaga','12345','1','HECTOR','2025-12-22 14:28:29','Hector','2025-12-22 18:50:24',NULL,NULL),
    (@USER0004,@PER00007,@ROL00004,'bryan2','123456','1','HECTOR','2025-12-22 19:01:07',NULL,NULL,NULL,NULL),
    (@USER0005,@PER00007,@ROL00004,'bryan3','123456','2','HECTOR','2025-12-22 19:01:29',NULL,NULL,'2025-12-22 19:08:35',NULL),
    (@USER0006,@PER00010,@ROL00004,'elisa','123456','1','hquispec','2026-06-24 23:26:17',NULL,NULL,NULL,NULL);

-- ============================================================================
-- produccion
-- ============================================================================
-- ⚠️ ADVERTENCIA: en el dump original, "id_proLecOva" (ahora idLeccionOva) usaba
-- códigos con formato de OVAS ('OVA002', 'OVA0003'), NO códigos de leccion_ova.
-- Como el nuevo esquema sí valida la FK produccion.idLeccionOva -> leccion_ova,
-- y las tablas modulos/lecciones/leccion_ova no traían datos en tu dump original,
-- estos INSERTs fallarían por integridad referencial si se ejecutan tal cual.
--
-- Para no perder este dato histórico, desactivo temporalmente la verificación de
-- FK solo para esta sección. Esto es una solución temporal: te recomiendo revisar
-- estos 8 registros y, cuando tengas lecciones/leccion_ova reales, actualizar
-- idLeccionOva para que apunte a un idLeccionOva válido.
--
-- Los valores de idLeccionOva de estas filas también se generan con UUID() (ya
-- que la columna ahora es VARCHAR(36)), pero al no existir todavía leccion_ova
-- real, no se reutiliza ninguna variable existente: son placeholders sueltos,
-- igual que lo eran los códigos 'OVA002'/'OVA0003' en el dump original.

SET FOREIGN_KEY_CHECKS = 0;

SET @PRD0001 = UUID();
SET @PRD0002 = UUID();
SET @PRD0003 = UUID();
SET @PRD0004 = UUID();
SET @PRD0005 = UUID();
SET @PRD0006 = UUID();
SET @PRD0007 = UUID();
SET @PRD0008 = UUID();

SET @LECCIONOVA_PLACEHOLDER_1 = UUID();
SET @LECCIONOVA_PLACEHOLDER_2 = UUID();

INSERT INTO produccion
    (idProduccion, idProyecto, idLeccionOva, idPersona, fechaHoraInicio, fechaHoraFin,
     horasEstimadas, horasReales, huboCorte, descripcionCorte, horasContratiempo,
     estadoTarea, cumplimiento, observaciones, estado,
     createUser, createDate, updatedUser, updatedDate, deletedDate, deletedUser)
VALUES
    (@PRD0001,@PROY0001,@LECCIONOVA_PLACEHOLDER_1,@PER00005,'2026-06-21 21:52:00','2026-06-22 21:52:00',24.00,40.00,b'1','Asignación de subida de Proyectos antiguos',16.00,'COMPLETADO',b'1','XXXX','1','hquispec','2026-06-21 21:53:08','hquispec','2026-06-23 02:22:29',NULL,NULL),
    (@PRD0002,@PROY0001,@LECCIONOVA_PLACEHOLDER_1,@PER00004,'2026-06-23 23:50:00','2026-06-23 23:58:00',24.00,0.13,b'0','',NULL,'COMPLETADO',b'1','Se trataba en actualización.','1','hquispec','2026-06-23 23:50:42','hquispec','2026-06-23 23:58:58',NULL,NULL),
    (@PRD0003,@PROY0001,@LECCIONOVA_PLACEHOLDER_1,@PER00008,'2026-06-24 00:02:00','2026-06-24 00:12:00',24.00,0.17,b'0','',NULL,'COMPLETADO',b'1','Pasa por diseño','1','hquispec','2026-06-24 00:03:02','hquispec','2026-06-24 00:12:33',NULL,NULL),
    (@PRD0004,@PROY0001,@LECCIONOVA_PLACEHOLDER_1,@PER00009,'2026-06-24 00:21:00','2026-06-24 00:24:00',24.00,0.00,b'1','Se gestionó kpis de Pacifico',4.00,'COMPLETADO',b'1','Se da un aspecto más amigable y con variables de reconocimiento de voz.','1','hquispec','2026-06-24 00:21:49','hquispec','2026-06-24 00:24:55',NULL,NULL),
    (@PRD0005,@PROY0001,@LECCIONOVA_PLACEHOLDER_1,@PER00006,'2026-06-24 00:28:00','2026-06-24 01:14:00',24.00,0.77,b'0','',NULL,'COMPLETADO',b'1','xxx','1','hquispec','2026-06-24 00:29:10','hquispec','2026-06-24 01:14:30',NULL,NULL),
    (@PRD0006,@PROY0001,@LECCIONOVA_PLACEHOLDER_1,@PER00003,'2026-06-24 22:35:00','2026-06-24 23:31:00',24.00,0.93,b'0','',NULL,'COMPLETADO',b'1','xxx','1','hquispec','2026-06-24 22:36:04','hquispec','2026-06-24 23:31:09',NULL,NULL),
    (@PRD0007,@PROY0002,@LECCIONOVA_PLACEHOLDER_2,@PER00010,'2026-06-24 23:30:00','2026-06-25 01:05:00',24.00,1.58,b'0','',NULL,'COMPLETADO',b'1','xx','1','hquispec','2026-06-24 23:31:00','hquispec','2026-06-25 01:05:12',NULL,NULL),
    (@PRD0008,@PROY0003,@LECCIONOVA_PLACEHOLDER_2,@PER00010,'2026-06-25 01:04:00',NULL,24.00,NULL,b'0','',NULL,'EN PROGRESO',b'0','XX','1','hquispec','2026-06-25 01:04:33',NULL,NULL,NULL,NULL);

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================================
-- modulos / lecciones / leccion_ova
-- ============================================================================
-- Estas 3 tablas no traían datos en tu dump original (solo existían como
-- entidades en tu código Spring), así que no hay nada que migrar aquí todavía.

-- ============================================================================
-- FIN DEL SCRIPT UNIFICADO
-- ============================================================================

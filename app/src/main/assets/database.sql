
CREATE DATABASE IF NOT EXISTS fastandsafe;
USE fastandsafe;

CREATE TABLE CLIENTES (
    id_cliente     VARCHAR(50)  PRIMARY KEY,
    nombre         VARCHAR(100) NOT NULL,
    apellidos      VARCHAR(150) NOT NULL,
    telefono       VARCHAR(20),
    email          VARCHAR(100) NOT NULL UNIQUE,
    direccion      VARCHAR(255),
    fecha_registro DATE         NOT NULL,
    contrasena     VARCHAR(255) NOT NULL,
    activo         VARCHAR(2)   DEFAULT 'SI'
);

CREATE TABLE COCHES (
    id_coche    VARCHAR(50)   PRIMARY KEY,
    marca       VARCHAR(50)   NOT NULL,
    modelo      VARCHAR(100)  NOT NULL,
    anio        INT           NOT NULL,
    kilometros  INT           DEFAULT 0,
    precio      DECIMAL(10,2) NOT NULL,
    matricula   VARCHAR(15)   NOT NULL UNIQUE,
    combustible VARCHAR(30)   NOT NULL,
    transmision VARCHAR(30),
    color       VARCHAR(30),
    estado      VARCHAR(20)   DEFAULT 'Disponible',
    fecha_alta  DATE          NOT NULL,
    activo      VARCHAR(2)    DEFAULT 'SI'
);

CREATE TABLE RESERVAS (
    id_reserva    VARCHAR(50) PRIMARY KEY,
    id_cliente    VARCHAR(50) NOT NULL,
    id_coche      VARCHAR(50) NOT NULL,
    fecha_inicio  DATE        NOT NULL,
    fecha_fin     DATE        NOT NULL,
    estado        VARCHAR(30) DEFAULT 'Pendiente',
    fecha_reserva DATE        NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES CLIENTES(id_cliente),
    FOREIGN KEY (id_coche)   REFERENCES COCHES(id_coche)
);

CREATE TABLE FAVORITOS (
    id_favorito    VARCHAR(50) PRIMARY KEY,
    id_cliente     VARCHAR(50) NOT NULL,
    id_coche       VARCHAR(50) NOT NULL,
    fecha_agregado DATE        NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES CLIENTES(id_cliente),
    FOREIGN KEY (id_coche)   REFERENCES COCHES(id_coche)
);

-- Datos de prueba para los coches (Sincronizados con el menú principal)
INSERT INTO COCHES VALUES ('C001','Toyota','Corolla',2023,15000,16500.00,'1234ABC','Hibrido','Automática','Burdeo','Disponible',CURDATE(),'SI');
INSERT INTO COCHES VALUES ('C002','Hyundai','Tucson',2022,25000,22000.00,'5678DEF','Gasolina','Automática','Gris','Disponible',CURDATE(),'SI');
INSERT INTO COCHES VALUES ('C003','Seat','Leon',2021,40000,15800.00,'9101GHI','Diesel','Manual','Blanco','Reservado',CURDATE(),'SI');
INSERT INTO COCHES VALUES ('C004','Kia','Sportage',2023,10000,19000.00,'1122JKL','Gasolina','Automática','Negro','Disponible',CURDATE(),'SI');

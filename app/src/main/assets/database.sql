-- 0. Tabla de Oficinas
CREATE TABLE OFICINAS (
    id_oficina VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    tiene_taller VARCHAR(2) DEFAULT 'NO',
    capacidad_coches INT DEFAULT 0,
    activo VARCHAR(2) DEFAULT 'SI'
);


-- 1. Tabla de Clientes
CREATE TABLE CLIENTES (
   id_cliente VARCHAR(50) PRIMARY KEY,
   nombre VARCHAR(100) NOT NULL,
   apellidos VARCHAR(150) NOT NULL,
   telefono VARCHAR(20),
   email VARCHAR(100) NOT NULL UNIQUE,
   direccion VARCHAR(255),
   fecha_registro DATE NOT NULL,
   activo VARCHAR(2) DEFAULT 'SI'
);


-- 2. Tabla de Coches
CREATE TABLE COCHES (
   id_coche VARCHAR(50) PRIMARY KEY,
   marca VARCHAR(50) NOT NULL,
   modelo VARCHAR(100) NOT NULL,
   anio INT NOT NULL,
   kilometros INT DEFAULT 0,
   precio DECIMAL(10, 2) NOT NULL,
   matricula VARCHAR(15) NOT NULL UNIQUE,
   combustible VARCHAR(30) NOT NULL,
   transmision VARCHAR(30),
   color VARCHAR(30),
   estado VARCHAR(20) DEFAULT 'Disponible', -- 'Disponible' o 'Reservado'
   id_oficina VARCHAR(50),
   fecha_alta DATE NOT NULL,
   activo VARCHAR(2) DEFAULT 'SI',
   FOREIGN KEY (id_oficina) REFERENCES OFICINAS(id_oficina)
);


-- 3. Tabla de Reservas
CREATE TABLE RESERVAS (
   id_reserva VARCHAR(50) PRIMARY KEY,
   id_cliente VARCHAR(50) NOT NULL,
   id_coche VARCHAR(50) NOT NULL,
   fecha_inicio DATE NOT NULL,
   fecha_fin DATE NOT NULL,
   estado VARCHAR(30) DEFAULT 'Pendiente',
   fecha_reserva DATE NOT NULL,
   FOREIGN KEY (id_cliente) REFERENCES CLIENTES(id_cliente),
   FOREIGN KEY (id_coche) REFERENCES COCHES(id_coche)
);


-- 4. Tabla de Favoritos
CREATE TABLE FAVORITOS (
   id_favorito VARCHAR(50) PRIMARY KEY,
   id_cliente VARCHAR(50) NOT NULL, -- Corregido a id_cliente según lógica de negocio
   id_coche VARCHAR(50) NOT NULL,
   fecha_agregado DATE NOT NULL,
   FOREIGN KEY (id_cliente) REFERENCES CLIENTES(id_cliente),
   FOREIGN KEY (id_coche) REFERENCES COCHES(id_coche)
);

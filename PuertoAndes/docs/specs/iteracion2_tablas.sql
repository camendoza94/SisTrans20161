alter session set current_schema = ISIS2304A141610;

DROP TABLE MUELLES;
DROP TABLE MERCANCIAS_ASOCIADAS_FACTURAS;
DROP TABLE MERCANCIAS;
DROP TABLE CLASE_MERCANCIA;
DROP TABLE FACTURAS;
DROP TABLE EXPORTADORES;
DROP TABLE IMPORTADORES;
DROP TABLE TIPO_IMPORTADOR;
DROP TABLE USUARIOS;
DROP TABLE TIPO_PERSONA;
DROP TABLE TIPO_USUARIO;
DROP TABLE CAMIONES;
DROP TABLE BUQUES;
DROP TABLE TIPO_BUQUE;
DROP TABLE CUARTOS_FRIOS;
DROP TABLE PATIOS;
DROP TABLE COBERTIZOS;
DROP TABLE SILOS;
DROP TABLE BODEGAS;
DROP TABLE EQUIPOS;
DROP TABLE AREAS_ALMACENAMIENTO;

CREATE TABLE AREAS_ALMACENAMIENTO(
ID_AREA INTEGER,
LLENO CHAR NOT NULL,
CONSTRAINT PK_ID_AREA PRIMARY KEY (ID_AREA),
CONSTRAINT CK_AA_LLENO CHECK (LLENO IN (0,1))
);

CREATE TABLE EQUIPOS(
ID_EQUIPO INTEGER,
NOMBRE VARCHAR(255) NOT NULL,
PRECIO REAL,
CAPACIDAD REAL,
DISPONIBLE CHAR NOT NULL,
CONSTRAINT PK_ID_EQUIPO PRIMARY KEY (ID_EQUIPO),
CONSTRAINT CK_E_PRECIO CHECK (CAPACIDAD>=0),
CONSTRAINT CK_E_CAPACIDAD CHECK (CAPACIDAD>=0),
CONSTRAINT CK_E_DISPONIBLE CHECK (DISPONIBLE IN (0,1))
);

CREATE TABLE BODEGAS(
ID_BODEGA INTEGER,
ANCHO REAL,
LARGO REAL,
PLATAFORMA CHAR NOT NULL,
SEPARACION_COLUMNA REAL,
CONSTRAINT PK_ID_BODEGA PRIMARY KEY (ID_BODEGA),
CONSTRAINT FK_B_ID_BODEGA FOREIGN KEY (ID_BODEGA)
      REFERENCES AREAS_ALMACENAMIENTO (ID_AREA),
CONSTRAINT CK_B_ANCHO CHECK (ANCHO>=0),
CONSTRAINT CK_B_LARGO CHECK (LARGO>=0),
CONSTRAINT CK_B_PLATAFORMA CHECK (PLATAFORMA IN (0,1)),
CONSTRAINT CK_B_SEPARACION_COLUMNA CHECK (SEPARACION_COLUMNA>=0)
);

CREATE TABLE SILOS(
ID_SILO INTEGER,
NOMBRE VARCHAR(255) NOT NULL,
CAPACIDAD REAL,
CONSTRAINT PK_ID_SILO PRIMARY KEY(ID_SILO),
CONSTRAINT FK_S_ID_SILO FOREIGN KEY (ID_SILO)
        REFERENCES AREAS_ALMACENAMIENTO (ID_AREA),
CONSTRAINT CK_S_CAPACIDAD CHECK (CAPACIDAD >=0)
);

CREATE TABLE COBERTIZOS(
ID_COBERTIZO INTEGER,
DIMEN REAL,
TIPO_CARGA VARCHAR(255) NOT NULL,
CONSTRAINT PK_ID_COBERTIZO PRIMARY KEY (ID_COBERTIZO),
CONSTRAINT FK_C_ID_COBERTIZO FOREIGN KEY (ID_COBERTIZO)
      REFERENCES AREAS_ALMACENAMIENTO (ID_AREA),
CONSTRAINT CK_C_DIMEN CHECK (DIMEN>=0)
);

CREATE TABLE PATIOS(
ID_PATIO INTEGER,
DIMEN REAL,
TIPO_CARGA VARCHAR(255) NOT NULL,
CONSTRAINT PK_ID_PATIO PRIMARY KEY (ID_PATIO),
CONSTRAINT FK_P_ID_PATIO FOREIGN KEY (ID_PATIO)
  REFERENCES AREAS_ALMACENAMIENTO (ID_AREA),
CONSTRAINT CK_P_DIMEN CHECK (DIMEN>=0)
);

CREATE TABLE CUARTOS_FRIOS(
ID_CUARTO INTEGER,
ID_BODEGA INTEGER,
AREA REAL,
LARGO REAL,
ALTURA REAL,
AREA_F_BODEGA REAL,
CONSTRAINT PK_ID_CUARTO PRIMARY KEY (ID_CUARTO),
CONSTRAINT FK_CF_ID_BODEGA FOREIGN KEY (ID_BODEGA)
              REFERENCES BODEGAS (ID_BODEGA),
CONSTRAINT CK_CF_AREA CHECK (AREA>=0),
CONSTRAINT CK_CF_LARGO CHECK (LARGO>=0),
CONSTRAINT CK_CF_ALTURA CHECK (ALTURA>=0),
CONSTRAINT CK_CF_AREA_F_BODEGA CHECK (AREA_F_BODEGA>=0)
);

CREATE TABLE TIPO_BUQUE(
TIPO VARCHAR(255),
CONSTRAINT PK_TIPO_BUQUE PRIMARY KEY (TIPO)
);

CREATE TABLE BUQUES(
ID_BUQUE INTEGER,
NOMBRE VARCHAR(255) NOT NULL,
NOMBRE_AGENTE VARCHAR(255) NOT NULL,
CAPACIDAD REAL,
LLENO CHAR,
TIPO_CARGA VARCHAR(255) NOT NULL,
FECHA_INGRESO DATE NOT NULL,
FECHA_SALIDA DATE NOT NULL,
REGISTRO_CAPITANIA VARCHAR(255) NOT NULL,
DESTINO VARCHAR(255) NOT NULL,
ORIGEN VARCHAR(255) NOT NULL,
TIPO_BUQUE VARCHAR(255),
CONSTRAINT PK_ID_BUQUE PRIMARY KEY (ID_BUQUE),
CONSTRAINT CK_BQ_CAPACIDAD CHECK (CAPACIDAD >=0),
CONSTRAINT CK_BQ_LLENO CHECK (LLENO IN (0,1)),
CONSTRAINT FK_BQ_TIPO_BUQUE FOREIGN KEY (TIPO_BUQUE)
          REFERENCES TIPO_BUQUE (TIPO)
CONSTRAINT CK_FECHA_SALIDA CHECK (FECHA_INGRESO<=FECHA_SALIDA)
);

CREATE TABLE CAMIONES(
ID_CAMION INTEGER,
NOMBRE VARCHAR(255) NOT NULL,
CONSTRAINT PK_ID_CAMION PRIMARY KEY (ID_CAMION)
);

CREATE TABLE TIPO_USUARIO(
TIPO VARCHAR(255),
CONSTRAINT PK_TIPO_USUARIO PRIMARY KEY (TIPO)
);

CREATE TABLE TIPO_PERSONA(
TIPO VARCHAR(255),
CONSTRAINT PK_TIPO_PERSONA PRIMARY KEY (TIPO)
);

CREATE TABLE USUARIOS(
ID_USUARIO INTEGER,
NOMBRE VARCHAR(255) NOT NULL,
TIPO_PERSONA VARCHAR(255) NOT NULL,
TIPO_USUARIO VARCHAR(255),
CONSTRAINT PK_ID_USUARIO PRIMARY KEY (ID_USUARIO),
CONSTRAINT FK_U_TIPO_PERSONA FOREIGN KEY (TIPO_PERSONA)
      REFERENCES TIPO_PERSONA (TIPO),
CONSTRAINT FK_U_TIPO_USUARIO FOREIGN KEY (TIPO_USUARIO)
        REFERENCES TIPO_USUARIO (TIPO)
);

CREATE TABLE TIPO_IMPORTADOR(
TIPO VARCHAR(255),
CONSTRAINT PK_TIPO_IMPORTADOR PRIMARY KEY (TIPO)
);

CREATE TABLE IMPORTADORES(
ID_USUARIO INTEGER,
REGISTRO_ADUANA VARCHAR(255) NOT NULL ,
TIPO VARCHAR(255) NOT NULL,
CONSTRAINT PK_I_ID_USUARIO PRIMARY KEY (ID_USUARIO),
CONSTRAINT FK_I_ID_USUARIO FOREIGN KEY (ID_USUARIO)
        REFERENCES USUARIOS (ID_USUARIO),
CONSTRAINT FK_I_TIPO FOREIGN KEY (TIPO)
    REFERENCES TIPO_IMPORTADOR (TIPO)
);

CREATE TABLE EXPORTADORES (
ID_USUARIO INTEGER,
RUT VARCHAR(255) NOT NULL,
CONSTRAINT PK_E_ID_USUARIO PRIMARY KEY (ID_USUARIO),
CONSTRAINT FK_E_ID_USUARIO FOREIGN KEY (ID_USUARIO)
          REFERENCES USUARIOS (ID_USUARIO),
CONSTRAINT UQ_E_RUT UNIQUE (RUT)
);

CREATE TABLE FACTURAS(
ID_FACTURA INTEGER,
FECHA DATE NOT NULL,
ID_EXPORTADOR INTEGER,
ID_BUQUE INTEGER,
CONSTRAINT PK_ID_FACTURA PRIMARY KEY (ID_FACTURA),
CONSTRAINT FK_F_ID_EXPORTADOR FOREIGN KEY (ID_EXPORTADOR)
        REFERENCES EXPORTADORES (ID_USUARIO),
CONSTRAINT FK_F_ID_BUQUE FOREIGN KEY (ID_BUQUE)
     REFERENCES BUQUES (ID_BUQUE)
);

CREATE TABLE CLASE_MERCANCIA (
CLASE VARCHAR(255),
CONSTRAINT PK_CLASE_MERCANCIA PRIMARY KEY (CLASE)
);

CREATE TABLE MERCANCIAS(
ID_MERCANCIA INTEGER,
ID_AREA_ALMACENAMIENTO INTEGER,
ID_CAMION INTEGER,
ID_BUQUE INTEGER,
PRECIO REAL,
FECHA DATE NOT NULL,
PROPOSITO VARCHAR(255) NOT NULL,
CANTIDAD REAL,
PROPIETARIO VARCHAR(255),
CONSTRAINT PK_ID_MERCANCIA PRIMARY KEY (ID_MERCANCIA),
CONSTRAINT FK_M_ID_AREA_ALMACENAMIENTO FOREIGN KEY (ID_AREA_ALMACENAMIENTO)
        REFERENCES AREAS_ALMACENAMIENTO (ID_AREA),
CONSTRAINT FK_M_ID_CAMION FOREIGN KEY (ID_CAMION)
   REFERENCES CAMIONES (ID_CAMION),
CONSTRAINT FK_M_ID_BUQUE FOREIGN KEY (ID_BUQUE)
    REFERENCES BUQUES (ID_BUQUE),
CONSTRAINT CK_M_PRECIO CHECK (PRECIO>=0),
CONSTRAINT FK_M_PROPOSITO FOREIGN KEY (PROPOSITO)
      REFERENCES CLASE_MERCANCIA (CLASE),
CONSTRAINT CK_M_CANTIDAD CHECK (CANTIDAD>=0),
CONSTRAINT CK1_M CHECK ((ID_CAMION IS NOT NULL AND ID_BUQUE IS NULL AND ID_AREA_ALMACENAMIENTO IS NULL) 
                        OR (ID_BUQUE IS NOT NULL AND ID_CAMION IS NULL AND ID_AREA_ALMACENAMIENTO IS NULL)
                        OR (ID_AREA_ALMACENAMIENTO IS NOT NULL AND ID_BUQUE IS NULL AND ID_CAMION IS NULL) )
);

CREATE TABLE MERCANCIAS_ASOCIADAS_FACTURAS(
ID_FACTURA INTEGER,
ID_MERCANCIA INTEGER,
CONSTRAINT PK_MF_MERCANCIAS_FACTURAS PRIMARY KEY (ID_FACTURA,ID_MERCANCIA),
CONSTRAINT FK_MF_FACTURA FOREIGN KEY (ID_FACTURA)
        REFERENCES FACTURAS (ID_FACTURA),
CONSTRAINT FK_MF_MERCANCIA FOREIGN KEY (ID_MERCANCIA)
        REFERENCES MERCANCIAS (ID_MERCANCIA)
);

CREATE TABLE MUELLES(
ID_MUELLE INTEGER,
ID_BUQUE INTEGER,
NOMBRE VARCHAR(255) NOT NULL,
CONSTRAINT PK_ID_MUELLE PRIMARY KEY (ID_MUELLE),
CONSTRAINT FK_MC_ID_BUQUE FOREIGN KEY (ID_BUQUE)
      REFERENCES BUQUES (ID_BUQUE)
);

CREATE TABLE PUERTOS(
ID_PUERTO INTEGER,
NOMBRE VARCHAR(255) NOT NULL, 
PAIS VARCHAR(255) NOT NULL,
CIUDAD VARCHAR(255) NOT NULL,
CONSTRAINT PK_ID_PUERTO PRIMARY KEY (ID_PUERTO)
);

CREATE TABLE ARRIBO_BUQUES(
ID_BUQUE INTEGER,
FECHA DATE NOT NULL,
PUERTO_ANTERIOR INTEGER NOT NULL,
PUERTO_SIGUIENTE INTEGER NOT NULL,
CONSTRAINT PK_ID_BUQUE PRIMARY KEY (ID_BUQUE),
CONSTRAINT FK_ID_BUQUE FOREIGN KEY (ID_BUQUE)
    REFERENCES BUQUES(ID_BUQUE),
CONSTRAINT FK_PUERTO_ANTERIOR FOREIGN KEY (PUERTO_ANTERIOR)
    REFERENCES PUERTOS (ID_PUERTO),
CONSTRAINT FK_PUERTO_SIGUIENTE FOREIGN KEY (PUERTO_SIGUIENTE)
    REFERENCES PUERTOS (ID_PUERTO)
);

CREATE TABLE SALIDA_BUQUE(
ID_BUQUE INTEGER,
FECHA DATE NOT NULL,
CONSTRAINT PK_ID_BUQUE PRIMARY KEY(ID_BUQUE, FECHA),
CONSTRAINT FK_ID_BUQUE FOREIGN KEY (ID_BUQUE)
      REFERENCES BUQUES (ID_BUQUE)
);
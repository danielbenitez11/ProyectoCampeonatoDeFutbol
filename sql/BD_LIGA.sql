create TABLE equipo(
	cod_equipo VARCHAR(4),
	nombre VARCHAR(30) NOT NULL,
	localidad VARCHAR(15),
    CONSTRAINT PK_equipo PRIMARY KEY(cod_equipo)
);

create TABLE jugador(
	cod_jugador VARCHAR(4),
	nombre VARCHAR(30) NOT NULL,
	fecha_nacimiento DATE,
	demarcacion VARCHAR(10),
	cod_equipo VARCHAR(4) NOT NULL,
	CONSTRAINT PK_jugador PRIMARY KEY(cod_jugador),
    CONSTRAINT FK_jugador_equipo FOREIGN KEY (cod_equipo) REFERENCES equipo(cod_equipo),
    CONSTRAINT CK_demarcacion CHECK(demarcacion='portero' OR demarcacion='defensa' OR demarcacion='delantero' OR demarcacion='medio')
);

CREATE TABLE partido(
	cod_partido VARCHAR(4),
	cod_equipo_local VARCHAR(4),
	cod_equipo_visitante VARCHAR(4),
	fecha DATE,
	jornada VARCHAR(20),
	CONSTRAINT PK_partido PRIMARY KEY(cod_partido),
	CONSTRAINT FK_partido_equipo_local FOREIGN KEY(cod_equipo_local) REFERENCES equipo(cod_equipo),
	CONSTRAINT FK_partido_equipo_visitante FOREIGN KEY(cod_equipo_visitante) REFERENCES equipo(cod_equipo),
	CONSTRAINT CK_fecha CHECK(month(fecha) != month('00-07-00') AND month(fecha) != month('00-08-00'))
);

CREATE TABLE incidencia(
	num_incidencia VARCHAR(6),
	cod_partido VARCHAR(4),
	cod_jugador VARCHAR(4),
	minuto INTEGER(2),
	tipo VARCHAR(20),
	CONSTRAINT PK_incidencia PRIMARY KEY(num_incidencia),
	CONSTRAINT FK_incidencia_partido FOREIGN KEY(cod_partido) REFERENCES partido(cod_partido),
	CONSTRAINT FK_incidencia_jugador FOREIGN KEY(cod_jugador) REFERENCES jugador(cod_jugador),
	CONSTRAINT CK_minuto CHECK(minuto > 0 AND minuto < 100),
	CONSTRAINT CK_tipo CHECK(LOWER(tipo) LIKE 'gol' OR LOWER(tipo) LIKE 'tarjetaAmarilla' OR LOWER(tipo) LIKE 'tarjetaRoja')
);

INSERT INTO equipo (cod_equipo, nombre, localidad) VALUES ('E1', 'Real Betis Balonpie', 'Sevilla');
INSERT INTO equipo (cod_equipo, nombre, localidad) VALUES ('E2', 'Levante Union Deportiva', 'Valencia');
INSERT INTO equipo (cod_equipo, nombre, localidad) VALUES ('E3', 'Sevilla Futbol Club', 'Sevilla');
INSERT INTO equipo (cod_equipo, nombre, localidad) VALUES ('E4', 'Real Madrid', 'Madrid');
INSERT INTO equipo (cod_equipo, nombre, localidad) VALUES ('E5', 'Cadiz Club de Futbol', 'Cadiz');

INSERT INTO jugador (cod_jugador, nombre, fecha_nacimiento, demarcacion, cod_equipo) VALUES ('J1', 'Fekir', '1993-08-17', 'Delantero', 'E1');
INSERT INTO jugador (cod_jugador, nombre, fecha_nacimiento, demarcacion, cod_equipo) VALUES ('J2', 'Joaquin', '1981-01-27', 'Medio', 'E1');
INSERT INTO jugador (cod_jugador, nombre, fecha_nacimiento, demarcacion, cod_equipo) VALUES ('J3', 'Bono', '1991-05-14', 'Portero', 'E3');
INSERT INTO jugador (cod_jugador, nombre, fecha_nacimiento, demarcacion, cod_equipo) VALUES ('J4', 'Jose', '1993-02-25', 'Defensa', 'E4');
INSERT INTO jugador (cod_jugador, nombre, fecha_nacimiento, demarcacion, cod_equipo) VALUES ('J5', 'Antonio', '1992-07-15', 'Delantero', 'E2');

INSERT INTO partido (cod_partido, cod_equipo_local, cod_equipo_visitante, fecha, jornada) VALUES ('P1', 'E1', 'E2', '2023-02-01', 'Betis-Levante');
INSERT INTO partido (cod_partido, cod_equipo_local, cod_equipo_visitante, fecha, jornada) VALUES ('P2', 'E3', 'E4', '2023-12-03', 'Sevilla-Real Madrid');
INSERT INTO partido (cod_partido, cod_equipo_local, cod_equipo_visitante, fecha, jornada) VALUES ('P3', 'E4', 'E1', '2023-01-02', 'Real Madrid-Betis');
INSERT INTO partido (cod_partido, cod_equipo_local, cod_equipo_visitante, fecha, jornada) VALUES ('P4', 'E2', 'E5', '2023-04-02', 'Levante-Cadiz');
INSERT INTO partido (cod_partido, cod_equipo_local, cod_equipo_visitante, fecha, jornada) VALUES ('P5', 'E5', 'E3', '2023-04-02', 'Cadiz-Sevilla');

INSERT INTO incidencia (num_incidencia, cod_partido, cod_jugador, minuto, tipo) VALUES ('I1', 'P1', 'J1', '55', 'gol');
INSERT INTO incidencia (num_incidencia, cod_partido, cod_jugador, minuto, tipo) VALUES ('I2', 'P2', 'J5', '30', 'tarjetaAmarilla');
INSERT INTO incidencia (num_incidencia, cod_partido, cod_jugador, minuto, tipo) VALUES ('I3', 'P5', 'J5', '40', 'tarjetaRoja');
INSERT INTO incidencia (num_incidencia, cod_partido, cod_jugador, minuto, tipo) VALUES ('I4', 'P4', 'J4', '70', 'gol');
INSERT INTO incidencia (num_incidencia, cod_partido, cod_jugador, minuto, tipo) VALUES ('I5', 'P3', 'J3', '12', 'tarjetaAmarilla');
-- PUNTO 2
DELIMITER $$

CREATE PROCEDURE resultado_partido (IN cod_local VARCHAR(4), IN cod_visitante VARCHAR(4))
BEGIN
  SELECT
  -- usamos concat para combinar los valores del codigo partido y la fecha, y
  -- los valores de los nombres del equipo local y  visitante
    CONCAT(partido.cod_partido, '-', partido.fecha) AS "Partido-Fecha",
    CONCAT(equipo_local.nombre, ' - ', equipo_visitante.nombre) AS "Equipo Local - Equipo Visitante",
  -- sumamos los valores de tipo gol mediante la comprobacion de un if que comprueba si la incidencia es de tipo gol
  -- y si el codigo del equipo del jugador es el mismo que el codigo del equipo local, lo mismo con el visitante, devuelve
  -- 1 si es correcto y devuelve 0 si no lo es
    SUM(IF(incidencia.tipo = 'gol' AND jugador.cod_equipo = equipo_local.cod_equipo, 1, 0)) AS "Goles Equipo Local",
    SUM(IF(incidencia.tipo = 'gol' AND jugador.cod_equipo = equipo_visitante.cod_equipo, 1, 0)) AS "Goles Equipo Visitante"
  FROM partido
  -- Uso el join para unir las tablas de equipo con el codigo del equipo local para crear un codigo
  -- de equipo para el equipo local, lo mismo con el visitante
    JOIN equipo AS equipo_local ON partido.cod_equipo_local = equipo_local.cod_equipo
    JOIN equipo AS equipo_visitante ON partido.cod_equipo_visitante = equipo_visitante.cod_equipo
 -- Unimos aqui la tabla incidencia con la tabla partido para igualar los valores de los codigo partido en
 -- la tabla incidencia. Hacemos lo mismo con el codigo jugador de la tabla incidencia y de la tabla codigo jugador
    LEFT JOIN incidencia ON partido.cod_partido = incidencia.cod_partido
    LEFT JOIN jugador ON incidencia.cod_jugador = jugador.cod_jugador
  WHERE partido.cod_equipo_local = cod_local
    AND partido.cod_equipo_visitante = cod_visitante
  -- agrupamos los resultados en la tabla codigo partido
  GROUP BY
    partido.cod_partido;
END$$

DELIMITER ;

CREATE SCHEMA IF NOT EXISTS CuerpoAcademico;
USE  CuerpoAcademico;

 CREATE TABLE IF NOT EXISTS CuerpoAcademico(clave varchar(10) NOT NULL,
nombre varchar(100) NOT NULL, objetivo varchar(400) NOT NULL,
mision varchar(500) NOT NULL,vision varchar(500) NOT NULL,
gradoConsolidacion varchar(30) NOT NULL, primary Key (clave));

CREATE TABLE IF NOT EXISTS LGAC(nombre varchar(200) NOT NULL,
descripcion varchar(400) NOT NULL, primary Key (nombre) );

CREATE TABLE IF NOT EXISTS CuerpoLGAC( claveCuerpoAcademico varchar(10) NOT NULL,
nombreLGAC varchar(200) NOT NULL,  foreign key (nombreLGAC) REFERENCES  LGAC (nombre) ,
 foreign key (claveCuerpoAcademico) REFERENCES  CuerpoAcademico (clave)  );

CREATE TABLE IF NOT EXISTS Anteproyecto(idAnteproyecto int NOT NULL auto_increment,
  titulo varchar(200) NOT NULL,
  descripcion varchar(1000) NOT NULL, fechaInicio varchar(30) NOT NULL,
  fechaFin varchar(30)  NOT NULL,clave_CA VARCHAR(10), primary key (idAnteproyecto),foreign key(clave_CA) references CuerpoAcademico(clave));

   CREATE TABLE IF NOT EXISTS TrabajoRecepcional (idTrabajoRecepcional int NOT NULL auto_increment,
  titulo varchar(200) NOT NULL, tipo varchar(30) NOT NULL,
  descripcion varchar(400) NOT NULL, fechaInicio varchar(20) NOT NULL,
  fechaFin varchar(20) NOT NULL , estadoActual varchar(30) NOT NULL,  idAnteproyecto int NOT NULL,
  primary key (idTrabajoRecepcional),clave_CA VARCHAR(10),
  foreign key (idAnteproyecto) REFERENCES Anteproyecto (idAnteproyecto) ON DELETE CASCADE,foreign key(clave_CA) references CuerpoAcademico(clave));




CREATE TABLE Miembro (
  cedula varchar(10) NOT NULL,
  nombre varchar(150) NOT NULL,
  rol varchar(20) NOT NULL,
  grado varchar(50) DEFAULT NULL,
  nombreGrado varchar(200) NOT NULL,
  universidad varchar(200) NOT NULL,
  anio int NOT NULL,
  clave varchar(10) DEFAULT NULL,
  estado varchar(50) DEFAULT NULL,
  PRIMARY KEY (cedula),
  KEY clave (clave),
 FOREIGN KEY (clave) REFERENCES CuerpoAcademico (clave)
);

CREATE TABLE IF NOT EXISTS Credenciales(cedula varchar(10) not null, usuario varchar(15) not null,
contrasenia  varchar(200)  not null, primary key(cedula), foreign key (cedula) references Miembro(cedula));

CREATE TABLE  IF NOT EXISTS Reunion (idReunion int auto_increment NOT NULL ,
asunto varchar(200) NOT NULL , hora varchar(10) NOT NULL ,
 fecha varchar(20) NOT NULL, estado varchar(30) NOT NULL , clave_CA VARCHAR(10),
 primary key (idReunion),foreign key(clave_CA) references CuerpoAcademico(clave) );

 CREATE TABLE IF NOT EXISTS Prerequisito(idPrerequisito int auto_increment NOT NULL ,
  descripcion varchar(200) NOT NULL , idReunion int NOT NULL , cedula varchar(10) not null,
  primary key (idPrerequisito),
  foreign key (idReunion) REFERENCES Reunion (idReunion) ON DELETE CASCADE,
   foreign key (cedula) REFERENCES Miembro(cedula));


  CREATE TABLE IF NOT EXISTS Estudiante(matricula varchar(10) NOT NULL,
  nombre varchar(150) NOT NULL, primary key (matricula));


CREATE TABLE IF NOT EXISTS Proyecto(idProyecto int NOT NULL AUTO_INCREMENT ,
titulo varchar(200) NOT NULL, descripcion varchar(3000) NOT NULL,fechaInicio varchar(30) not null,
fechaFin varchar(30) not null, clave varchar(10) NOT NULL,foreign key(clave) references CuerpoAcademico(clave),
 primary key(idProyecto) );



CREATE TABLE IF NOT EXISTS Tema(
idTema int NOT NULL AUTO_INCREMENT, tema varchar(200) NOT NULL, horaInicio varchar(10) NOT NULL,
idReunion int NOT NULL, horaFin varchar(10) NOT NULL, primary key(idTema,idReunion), cedula varchar(10) NOT NULL,
foreign key (idReunion)  REFERENCES Reunion(idReunion)ON DELETE CASCADE,
foreign key (cedula)  REFERENCES miembro(cedula)ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS Minuta(idMinuta int NOT NULL AUTO_INCREMENT ,
nota varchar(500), estado varchar(30) NOT NULL,
pendiente varchar(500), idReunion int NOT NULL,
primary key(idMinuta), foreign key(idReunion) references reunion(idReunion)ON DELETE CASCADE);


CREATE TABLE IF NOT EXISTS Acuerdo(idAcuerdo int NOT NULL AUTO_INCREMENT,
periodo varchar(30) NOT NULL, descripcion varchar(200) NOT NULL,
idMinuta int NOT NULL, primary key(idAcuerdo),cedula varchar(19) not null,
foreign key (cedula) references Miembro(cedula),
foreign key (idMinuta) references Minuta(idMinuta)ON DELETE CASCADE);


CREATE TABLE IF NOT EXISTS ParticipaTrabajoRecepcional(idTrabajoRecepcional int NOT NULL,
matricula varchar(10), primary key(idTrabajoRecepcional, matricula), foreign key(idTrabajoRecepcional)
references TrabajoRecepcional(idTrabajoRecepcional), foreign key(matricula)
references Estudiante(matricula));

CREATE TABLE IF NOT EXISTS Dirige( idTrabajoRecepcional int not null ,cedula VARCHAR(10) NOT NULL,
primary key(cedula,idTrabajoRecepcional), rol varchar(20) not null,
 foreign key(idTrabajoRecepcional) references TrabajoRecepcional(idTrabajoRecepcional),
 foreign key(cedula) references Miembro(cedula));

 CREATE TABLE IF NOT EXISTS Colabora( idAnteproyecto int not null ,cedula VARCHAR(10) NOT NULL,
primary key(cedula,idAnteproyecto), rol varchar(20) not null,
 foreign key(idAnteproyecto) references Anteproyecto(idAnteproyecto),
 foreign key(cedula) references Miembro(cedula));

 CREATE TABLE IF NOT EXISTS Realiza( idAnteproyecto int not null ,matricula VARCHAR(10) NOT NULL,
primary key(matricula,idAnteproyecto),
 foreign key(idAnteproyecto) references Anteproyecto(idAnteproyecto),
 foreign key(matricula) references Estudiante(matricula));


CREATE TABLE IF NOT EXISTS ParticipaReunion( idReunion int not null ,cedula VARCHAR(10) NOT NULL, rol varchar(50) NOT NULL,
primary key(cedula,idReunion), foreign key(idReunion)
references Reunion(idReunion), foreign key(cedula)
references Miembro(cedula));


CREATE TABLE IF NOT EXISTS CultivaProyecto(idProyecto int NOT NULL,
nombreLGAC varchar(200), primary key(idProyecto, nombreLGAC), foreign key(idProyecto)
references Proyecto(idProyecto), foreign key(nombreLGAC)
references LGAC(nombre));


CREATE TABLE IF NOT EXISTS CultivaTrabajoRecepcional(idTrabajoRecepcional int NOT NULL,
nombreLGAC varchar(200), primary key(idTrabajoRecepcional, nombreLGAC), foreign key(idTrabajoRecepcional)
references TrabajoRecepcional(idTrabajoRecepcional), foreign key(nombreLGAC)
references LGAC(nombre));

CREATE TABLE IF NOT EXISTS ValidarMinuta (
  idMinuta int NOT NULL,
  cedula varchar(10) NOT NULL,
  estado varchar(30) default null,
  comentario varchar(500) default null,
  PRIMARY KEY (IdMinuta,cedula),
  FOREIGN KEY (idMinuta) REFERENCES Minuta(idMinuta),
  FOREIGN KEY (cedula) REFERENCES Miembro(cedula)
);

CREATE TABLE IF NOT EXISTS ParticipaProyecto(
  idProyecto int NOT NULL,
  matricula varchar(10) NOT NULL,
  PRIMARY KEY (idProyecto,matricula),
  KEY matricula (matricula),
  FOREIGN KEY (idProyecto) REFERENCES Proyecto(idProyecto),
  FOREIGN KEY (matricula) REFERENCES Estudiante(matricula)
);

CREATE TABLE IF NOT EXISTS DesarrollaProyecto (
  idProyecto int NOT NULL,
  cedula varchar(10) NOT NULL,
  PRIMARY KEY (IdProyecto,cedula),
  KEY cedula (cedula),
  FOREIGN KEY (idProyecto) REFERENCES ProyectO(idProyecto),
   FOREIGN KEY (cedula) REFERENCES Miembro (cedula)
);

CREATE TABLE ProyectoTrabajoRecepcional(
  idProyecto int NOT NULL,
  idTrabajoRecepcional int NOT NULL,
  PRIMARY KEY (idProyecto,idTrabajoRecepcional),
  KEY idTrabajoRecepcional (idTrabajoRecepcional),
  FOREIGN KEY (idProyecto) REFERENCES Proyecto(idProyecto),
  FOREIGN KEY (idTrabajoRecepcional) REFERENCES TrabajoRecepcional(idTrabajoRecepcional)
);
/*INSERT INTO ParticipaReunion (idReunion, cedula, rol) values (1, "8325134", "Asistente");
INSERT INTO Prerequisito_Encargado(cedula, idPrerequisito) values ("8325134", 1);
INSERT INTO Prerequisito(idReunion, descripcion, cedula) values (1, "Verificar situacion de FEIBook", "8325134");
INSERT INTO Prerequisito(idReunion, descripcion, cedula) values (1, "Evaluar el plan de trabajo", "8325134");
INSERT INTO LGAC(nombre,descripcion) values("Evaluación del modelo de calidad de seguridad para arquitecturas de software ",  "Se orienta al estudio de los diversos métodos y enfoques para la gestión, modelado y desarrollo de software, de manera que se obtenga software de calidad. Gestión de las diversas etapas del proceso de desarrollo, incluyendo hasta la medición del proceso y artefactos. Modelado de los diversos artefactos en las distintas etapas del proceso de desarrollo.");
INSERT INTO Anteproyecto(titulo, descripcion, fechaInicio, fechaFin) values ("Evaluación del modelo de calidad de seguridad para arquitecturas de software",
"Una arquitectura de software define no sólo la estructura o estructuras de un sistema de software, sino las características de calidad del propio sistema. Una característica o atributo de calidad altamente crítico en nuestros días es la seguridad. Esta característica, por supuesto que también es importante considerar en el desarrollo de la plataforma de comunicación y educación",
"13/11/2019", "13/07/2020");
INSERT INTO LGAC(nombre,descripcion) values("Aplicaciones de las técnicas estadísticas",  "Se orienta al estudio de los diversos métodos");
INSERT INTO Anteproyecto (titulo, descripcion, fechaInicio, fechaFin) values ("Revisión de articulos sobre microservicios ",
        "Revisión de articulos relacionados al apartado de microservicios",
        "13/01/2021", "13/07/2021");
INSERT INTO Estudiante(matricula, nombre) values ("S19014023", "Karina Valdes Iglesias");

INSERT INTO Reunion(asunto,hora, fecha, estado) VALUES ("Actualizar plan de trabajo de la LIS","13:00","11/05/2021", "Registrada");
INSERT INTO CuerpoAcademico(clave, nombre, objetivo, mision, vision, gradoConsolidacion) values ( "UVCA184", "Tecnología Computacional y Educativa", "Analizar y evaluar software relacionado con las innovaciones educativas desde el enfoque pedagógico y técnico.",
"El CA adscrito a la FEI de la UV tiene como misión desarrollar las áreas de sistemas operativos, lenguajes de programación, tecnología educativa, bases de datos, multimedia que permiten fortalecer la formación de los estudiantes de los programas educativo de la FEI, particularmente de la Licenciatura de Informática",
"Se visualiza un CA integrado y consistente, con líneas definidas de generación y aplicación del conocimiento, que produce soluciones a problemas y conocimientos básicos de las áreas de redes, sistemas operativos, bases de datos, lenguajes de programación",
"En formación");

INSERT INTO TrabajoRecepcional(titulo, tipo, descripcion, fechaInicio,fechaFin, estadoActual, idAnteproyecto ) VALUES ("Propuesta de Aplicación de Aprendizaje Máquina y Cómputo Evolutivo en la Clasificación de Requisitos de Calidad", "Tesis",
"Propuesta de uso de aprendizaje máquina en la clasificación de requisitos", "14/11/2019", "14/05/2021", "Concluido", 8);

INSERT INTO Colabora(idAnteproyecto, cedula, rol ) values (20,"8325134", "Codirector");
INSERT INTO Realiza(idAnteproyecto, matricula) values (7, "S19014013");
INSERT INTO Realiza(idAnteproyecto, matricula) values (7, "S19014023");
INSERT INTO CuerpoLGAC (claveCuerpoAcademico, nombreLGAC) values ("JDOEIJ804","Evaluación del modelo de calidad de seguridad para arquitecturas de software ");
INSERT INTO LGAC (nombre,descripcion) values ("Evaluación de modelos matematico", "Estudio y administración de modelos matematicos");
INSERT INTO cuerpoLGAC(claveCuerpoAcademico, nombreLGAC) values("UVCA107","Divulgación de la Estadística");
INSERT INTO cuerpoLGAC(claveCuerpoAcademico, nombreLGAC) values("CA424","Grafos aplicados a busquedas");
INSERT INTO TrabajoRecepcional(titulo, tipo, descripcion, fechaInicio,fechaFin, estadoActual, idAnteproyecto ) VALUES ("Prácticas automatizadas sobre la elicitación de requisitos", "Tesis",
"Propuesta de uso de aprendizaje máquina en elicitación de requisitos y fases posteriores", "18/02/2019", "14/05/2020", "Concluido", 20);
INSERT INTO TrabajoRecepcional(titulo, tipo, descripcion, fechaInicio,fechaFin, estadoActual, idAnteproyecto ) VALUES ("Los microservicios enfocados en la ingeniería de software", "Tesis",
        "Los microservicios enfocados en la ingeniería de software y sus distintas aplicaciones", "14/11/2019", "14/05/2021", "Concluido",26);
INSERT INTO TrabajoRecepcional(titulo, tipo, descripcion, fechaInicio,fechaFin, estadoActual, idAnteproyecto ) VALUES ("Los microservicios enfocados en la ingeniería de software", "Tesis",
        "Los microservicios enfocados en la ingeniería de software y sus distintas aplicaciones", "14/11/2019", "14/05/2021", "Concluido",26);


insert into dirige(idTrabajoRecepcional,cedula,rol) values(5,"8325134","Director");

insert into participatrabajorecepcional (idTrabajoRecepcional,matricula) values (5, "S19014023");

insert into dirige(idTrabajoRecepcional,cedula,rol) values(2,"7938268","Codirector");

insert into dirige(idTrabajoRecepcional,cedula,rol) values(6,"7938268","Codirector");
insert into participatrabajorecepcional (idTrabajoRecepcional,matricula) values (6, "S19014013");
insert into dirige(idTrabajoRecepcional,cedula,rol) values(6,"7938268","Codirector");
insert into participatrabajorecepcional (idTrabajoRecepcional,matricula) values (6, "S19014013");


*/
/*create user 'integrante'@'localhost' identified by 'password';
Grant SELECT, UPDATE, DELETE, INSERT on cuerpoAcademico.* TO 'integrante'@'localhost'; /

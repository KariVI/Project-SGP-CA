drop database cuerpoAcademico;
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

/*create user 'integrante'@'localhost' identified by 'password';
Grant SELECT, UPDATE, DELETE, INSERT on cuerpoAcademico.* TO 'integrante'@'localhost'; */

CREATE TABLE PlanTrabajo(
  idPlanTrabajo int auto_increment NOT NULL,
  periodo varchar(50) NOT NULL,
  objetivo varchar(500) NOT NULL,
  PRIMARY KEY (idPlanTrabajo)
  );

CREATE TABLE Meta(
  idPlanTrabajo int NOT NULL,
 idMeta int auto_increment NOT NULL,
 descripcion varchar(500) NOT NULL,
 PRIMARY KEY (idMeta,idPlanTrabajo),
FOREIGN KEY (idPlanTrabajo) REFERENCES PlanTrabajo(idPlanTrabajo)
 );

CREATE TABLE Accion(
  idAccion int auto_increment NOT NULL,
  idMeta int NOT NULL,
  descripcion varchar(500) NOT NULL,
  fechaConclusion varchar(50) NOT NULL,
  responsable varchar(50) NOT NULL,
  recurso varchar(500) NOT NULL,
  PRIMARY KEY (idAccion, idMeta),
  FOREIGN KEY (idMeta) REFERENCES Meta(idMeta)
  );


use cuerpoAcademico;
INSERT INTO PlanTrabajo(periodo, objetivo) VALUES("2020-2023", "Mantener el grado en consolidación del cuerpo academico");
INSERT INTO Meta(idPlanTrabajo,descripcion) VALUES(1, "Lograr un acuerdo de colaboración con un CA externo");
INSERT INTO Accion(idMeta,descripcion,fechaConclusion,responsable,recurso) VALUES(1, "Obtener una lista de contactos de los CA", "Diciembre 2020","KVC","Lista de cuerpos académicos de PRODEP");




INSERT INTO PlanTrabajo(periodo,objetivo) VALUES("2018-2020", "Mantener el grado en consolidación del cuerpo académico");

INSERT INTO Meta(idPlanTrabajo, descripcion) VALUES (2, "Para el 2022 el 80 % de los integrantes del CA tiene el grado de doctor");
INSERT INTO Meta(idPlanTrabajo, descripcion) VALUES (2, "Participar en al menos 1 convocatoria para la obtención de recursos externos");

INSERT INTO Accion (idMeta, descripcion, fechaConclusion, responsable, recurso) VALUES (2, "Integrar a un nuevo PTC con grado de Doctor al CA", "Mayo -Agosto 2019", "KVC y XLR", "Documentación del nuevo PTC");
INSERT INTO Accion (idMeta, descripcion, fechaConclusion, responsable, recurso) VALUES (3, "Rastrear cuales son los proyectos con financiamiento externo que reportan los CA (dependencias de gobierno, ONGs, Empresas", "Mayo 2018– Septiembre 2020", "JCPA, OOH Y XLR", "Proyectos del CA, , Convocatorias, Información de cuerpos académicos de PRODEP");

INSERT INTO Miembro(cedula, nombre, rol, grado, nombreGrado, universidad, anio, estado) VALUES("7953781","José Rafael Rojano Cáceres","Responsable","Doctorado", "Doctorado en Ciencias Computacionales", "UNAM", "2001", "Activo");

INSERT INTO CuerpoAcademico(clave,nombre, objetivo, mision , vision , gradoConsolidacion) VALUES("JDOEIJ804", "Ingenieria y Tecnologias de Software", "Desarrollar métodos, técnicas y herramientas para el desarrollo de software con un enfoque sistemático, disciplinado y cuantificable y apegado a estándares de calidad"
        , " Generar conocimiento y formar recursos humanos en Ingeniería de Software que contribuyan al desarrollo de software de calidad; a través de proyectos de investigación cuyos resultados se trasladen a  la docencia y la sociedad; y se difundan en foros especializados y de divulgación, fortaleciendo la vinculación academia-industria",
        "El Cuerpo Académico se encuentra consolidado y es líder en Ingeniería de Software y áreas relacionadas; todos los miembros trabajan colaborativamente en actividades de docencia, vinculación, generación y aplicación del conocimiento en las que participan activamente estudiantes de licenciatura y posgrado", "En consolidacion");
INSERT INTO CuerpoAcademico(clave,nombre, objetivo, mision , vision , gradoConsolidacion) VALUES("UVERCA12","Metodología Cuantitativa y Aplicaciones Diversas de la Estadística",
        "Diseñar e implementar la metodología estadística en procesos de investigación y estudios técnicos, para resolver problemas concretos de otras áreas del conocimiento.",
        "El CA adscrito a la FEI de la UV tiene como misión desarrollar las áreas métodos estadísticos","Se visualiza un CA integrado y consistente, con líneas definidas de generación y aplicación del conocimiento, que produce soluciones a problemas y conocimientos básicos de las áreas de estadistica", "En formación");
INSERT INTO CuerpoAcademico(clave,nombre, objetivo, mision , vision , gradoConsolidacion) VALUES("UVCA107","Metodología y Aplicaciones de las Técnicas y Modelos Estadísticos",
        "Los miembros del Cuerpo Académico se orientan al estudio y aplicación de los modelos estadísticos y al desarrollo de teorías, métodos y procedimientos para obtener datos, analizarlos y reportar los resultados en el contexto de investigaciones y estudios técnicos.", "Los miembros del Cuerpo Académico se orientan al estudio y aplicación de los modelos estadísticos y al desarrollo de teorías, métodos y procedimientos para obtener datos, analizarlos y reportar los resultados en el contexto de investigaciones y estudios técnicos.",
        "Realizar investigación en los aspectos teóricos y metodológicos del proceso de modelación estadística, promover y realizar aplicaciones de modelos, métodos y técnicas estadísticas ", "En consolidación");

INSERT INTO LGAC(nombre,descripcion) VALUES ("Tecnologías de software", "Se orienta al estudio de diversas propiedades, enfoques, métodos de modelado y herramientas que conforman cada una de las diversas tecnologías aplicables al desarrollo del software con vistas a su adaptación, mejora y sustitución en el medio nacional");
INSERT INTO LGAC(nombre,descripcion) VALUES ("Metodología de la investigación y la estadística", " Se orienta al estudio de los procesos de aplicación de la metodología estadística, considerando los diferentes enfoques, dependiendo de la disciplina de donde proviene el problema");
INSERT INTO LGAC(nombre,descripcion) VALUES ("Encuestas y estudios de opinión", "  Estudia el diseño de muestreos y sondeos, así como el proceso del levantamiento de encuestas, el análisis estadístico de los datos y la elaboración de reportes.");
INSERT INTO LGAC(nombre,descripcion) VALUES("Aplicaciones de las técnicas estadísticas","Aplicación de técnicas");
INSERT INTO LGAC(nombre,descripcion) VALUES("Gestión, modelado y desarrollo de software",  "Se orienta al estudio de los diversos métodos y enfoques para la gestión, modelado y desarrollo de software, de manera que se obtenga software de calidad. Gestión de las diversas etapas del proceso de desarrollo, incluyendo hasta la medición del proceso y artefactos. Modelado de los diversos artefactos en las distintas etapas del proceso de desarrollo.");

INSERT INTO CuerpoLGAC (claveCuerpoAcademico,nombreLGAC) VALUES ("JDOEIJ804", "Tecnologías de software");
INSERT INTO CuerpoLGAC (claveCuerpoAcademico,nombreLGAC) VALUES ("UVERCA12","Metodología de la investigación y la estadística");
INSERT INTO CuerpoLGAC (claveCuerpoAcademico,nombreLGAC) VALUES("UVERCA12","Encuestas y estudios de opinión");
INSERT INTO CuerpoLGAC (claveCuerpoAcademico,nombreLGAC) VALUES("UVCA107","Aplicaciones de las técnicas estadísticas");

INSERT INTO Miembro(cedula, nombre, rol, grado, nombreGrado, universidad, anio, estado, clave) VALUES('4065161', 'Maria de los Angeles Arenas Valdes', 'Colaborador', 'Maestria', 'Maestria en ciencias de la computacion', 'Fundacion Arturo Rosenbulth', '1999', 'Inactivo',"JDOEIJ804");
INSERT INTO Miembro(cedula, nombre, rol, grado, nombreGrado, universidad, anio, estado, clave) VALUES('8325134', 'Juan Carlos Perez Arriaga', 'Integrante', 'Maestria', 'Maestria en ciencias de la computacion', 'Fundacion Arturo Rosenbulth', '2013','Activo',"JDOEIJ804");
INSERT INTO Miembro(cedula, nombre, rol, grado, nombreGrado, universidad, anio, estado, clave) VALUES('7938268', 'Maria Karen Cortes Verdin', 'Responsable', 'Doctorado', 'Ciencias de la Computación', 'Centro de Investigación en Matemáticas A.C', '2005', 'Activo',"JDOEIJ804");

INSERT INTO Anteproyecto(titulo,descripcion, fechaInicio,fechaFin, clave_CA ) VALUES ("Revisión de la Literatura acerca de Varamiento de Mamíferos Marinos",
        "La Secretaría de Medio Ambiente y Recursos Naturales, a través de la Subsecretaría de Fomento y Normatividad Ambiental en coordinación con las áreas del sector ambiental y con la Secretaría de Marina (SEMAR)",
        "13/01/2021", "13/07/2021","JDOEIJ804");
INSERT INTO Anteproyecto(titulo,descripcion, fechaInicio,fechaFin, clave_CA ) VALUES ("Métricas de Cohesión y Acoplamiento", "La facilidad de evolución permite al software adaptarse a distintas necesidades conforme pasa el tiempo y suceden cambios tanto en el mercado como en la organización",
        "13/09/2020" , "17/05/2021","JDOEIJ804");
INSERT INTO Anteproyecto(titulo,descripcion, fechaInicio,fechaFin, clave_CA )   VALUES("Revisión de articulos sobre microservicios ",
        "Revisión de articulos relacionados al apartado de microservicios",
        "13/01/2021", "13/07/2021","JDOEIJ804");

INSERT INTO Anteproyecto(titulo,descripcion, fechaInicio,fechaFin, clave_CA )   VALUES ("Evaluación del modelo de calidad de seguridad para arquitecturas de software", "Una arquitectura de software define no sólo la estructura o estructuras de un sistema de software, sino las características de calidad del propio sistema. Una característica o atributo de calidad altamente crítico en nuestros días es la seguridad. Esta característica, por supuesto que también es importante considerar en el desarrollo de la plataforma de comunicación y educación",
        "13/11/2019","13/07/2020","JDOEIJ804");

INSERT INTO Anteproyecto(titulo,descripcion, fechaInicio,fechaFin, clave_CA )   VALUES ("Evaluación de investigaciones sobre lenguaje máquina", "Investigaciones recientes sobre los hallazgos de el aprendizake del lenguajes máquina",
"13/11/2019","13/07/2020","JDOEIJ804");



INSERT INTO Estudiante (matricula,nombre) values ("S19014023", "Karina Valdes Iglesias");
INSERT INTO Estudiante (matricula,nombre) values ("S19014013", "Mariana Yazmin Vargas Segura");
INSERT INTO Colabora(idAnteproyecto,cedula,rol) values (1,"8325134","Codirector");
INSERT INTO Colabora(idAnteproyecto,cedula,rol) values (4,"8325134","Director");
INSERT INTO Realiza(idAnteproyecto,matricula) VALUES(1, "S19014023");
INSERT INTO Realiza(idAnteproyecto,matricula) VALUES(4, "S19014013");

INSERT INTO TrabajoRecepcional(titulo, tipo, descripcion, fechaInicio,fechaFin, estadoActual, idAnteproyecto ,clave_CA) VALUES ("Análisis comparativo de métodos de evaluación de arquitecturas de software guiado por ESSENCE 1.2",
        "Práctico técnico","Resultados de la investigación y aplicación de ESSENCE 1.2 en distintas arquitecturas de software enfocadas a proyectos guiados",
        "14/06/2020","01/02/2021", "Concluido",4, "JDOEIJ804");

INSERT INTO TrabajoRecepcional(titulo, tipo, descripcion, fechaInicio,fechaFin, estadoActual, idAnteproyecto ,clave_CA) VALUES("Propuesta de Aplicación de Aprendizaje Máquina y Cómputo Evolutivo en la Clasificación de Requisitos de Calidad", "Tesis",
        "Propuesta de uso de aprendizaje máquina en la clasificación de requisitos", "14/11/2019", "14/05/2021", "Concluido",5,"JDOEIJ804");

   INSERT INTO TrabajoRecepcional(titulo, tipo, descripcion, fechaInicio,fechaFin, estadoActual, idAnteproyecto ,clave_CA) VALUES     ("Prácticas automatizadas sobre la elicitación de requisitos", "Tesis",
"Propuesta de uso de aprendizaje máquina en elicitación de requisitos y fases posteriores", "18/02/2019", "14/05/2020", "Concluido",2,"JDOEIJ804");

INSERT INTO TrabajoRecepcional(titulo, tipo, descripcion, fechaInicio,fechaFin, estadoActual, idAnteproyecto ,clave_CA) VALUES  ("Los microservicios enfocados en la ingeniería de software", "Tesis",
        "Los microservicios enfocados en la ingeniería de software y sus distintas aplicaciones", "14/11/2019", "14/05/2021", "Concluido",3,"JDOEIJ804");

INSERT INTO Dirige(idTrabajoRecepcional, cedula,rol) VALUES (4,"7938268","Director");

INSERT INTO Dirige(idTrabajoRecepcional, cedula,rol) VALUES (3,"8325134","Director");
INSERT INTO ParticipaTrabajoRecepcional(idTrabajoRecepcional,matricula) values (3,"S19014023");
INSERT INTO ParticipaTrabajoRecepcional(idTrabajoRecepcional,matricula) values (4,"S19014013");
INSERT INTO CultivaTrabajoRecepcional(idTrabajoRecepcional, nombreLGAC) values(3,"Tecnologías de software");
INSERT INTO CultivaTrabajoRecepcional(idTrabajoRecepcional, nombreLGAC) values(4,"Tecnologías de software");

INSERT INTO Credenciales(cedula,usuario,contrasenia) VALUES("8325134","JuanPer",hex(AES_ENCRYPT("14Sunblast60",'Key')));
INSERT INTO Credenciales(cedula,usuario,contrasenia) VALUES("7938268","KarenCor",hex(AES_ENCRYPT("wcm^Q$*aBe",'Key')));
INSERT INTO Credenciales(cedula,usuario,contrasenia) VALUES("7953781","JoseRoj",hex(AES_ENCRYPT("Jose11Rafael",'Key')));


INSERT INTO Reunion(asunto,hora, fecha, estado,clave_CA) VALUES ("Revisión de avances en proyectos actuales" ,"11:30","04/08/2021","Registrada", "JDOEIJ804");
INSERT INTO Reunion(asunto,hora, fecha, estado,clave_CA) VALUES( "Organizar agenda de actividades del seminario" ,"15:30","15/09/2021","Concluida","JDOEIJ804");
INSERT INTO Reunion(asunto,hora, fecha, estado,clave_CA) VALUES("Actualizar plan de trabajo de la LIS","13:00","11/08/2021", "Concluida","JDOEIJ804");
INSERT INTO Reunion(asunto,hora, fecha, estado,clave_CA) VALUES("Lanzamiento FEIBook" ,"12:30","11/11/2021","Concluida","JDOEIJ804");
INSERT INTO Reunion(asunto,hora, fecha, estado,clave_CA) VALUES("Lanzamiento del GastroCafe" ,"12:30","21/06/2021","´Proxima","JDOEIJ804");

INSERT INTO Tema(tema,horaInicio,idReunion,horaFin,cedula) VALUES("Agenda","11:00",2,"14:00","8325134");

INSERT INTO Minuta(nota, estado, pendiente, idReunion) VALUES ("El seminario se realizara el dia 13 de agosto del 2022","Registrada","Asignar horario a ponentes",2);
INSERT INTO Minuta(nota, estado, pendiente, idReunion) VALUES ("La segunda version de FEIBook estará disponible hasta el 11 de septiembre","Registrada","Sin pendientes",4);

INSERT INTO Acuerdo(periodo, descripcion,idMinuta,cedula) values ("Ene-Jul", "Seminario con tematica de IA", 1,"7938268");

INSERT INTO ParticipaReunion(idReunion, cedula, rol) VALUES (2,"8325134","Secretario");
INSERT INTO ParticipaReunion(idReunion, cedula, rol) VALUES (2,"7938268","Asistente");
INSERT INTO ParticipaReunion(idReunion, cedula, rol) VALUES (4,"8325134","Asistente");
INSERT INTO ParticipaReunion(idReunion,cedula,rol) VALUES(4,"7938268","Lider");
INSERT INTO ParticipaReunion(idReunion, cedula, rol) VALUES (5,"8325134","Asistente");
INSERT INTO ParticipaReunion(idReunion,cedula,rol) VALUES(5,"7938268","Lider");

INSERT INTO ValidarMinuta(idMinuta, cedula, estado, comentario) VALUES (2,8325134,"Pendiente","Falto el pendiente de agendar la siguiente reunión");
INSERT INTO ValidarMinuta(idMinuta, cedula) VALUES (2,7938268);

INSERT INTO Prerequisito(idReunion, descripcion, cedula) values (1, "Verificar situacion de FEIBook", "8325134");
INSERT INTO Prerequisito(idReunion, descripcion, cedula) values (1, "Evaluar el plan de trabajo", "8325134");
INSERT INTO Prerequisito(idReunion, descripcion, cedula) values (1, "Evaluar el GastroCafe", "7938268");
INSERT INTO Prerequisito(idReunion, descripcion, cedula) values (2, "Plan de mitigacion de riesgos", "7938268");

INSERT INTO Proyecto(titulo,descripcion,fechaInicio,fechaFin,clave) VALUES("Hacia un Modelo de Campus Accesible: Facultad de Estadística e Informática" ,"Actualmente la democratización de la educación representa un reto para cualquier Institución de Educación Superior, al mismo tiempo que el término “Institución Incluyente” cobra mayor sentido como\n parte de esta democratización.","04/05/2021","05/11/2021","JDOEIJ804");
INSERT INTO Proyecto(titulo,descripcion,fechaInicio,fechaFin,clave) VALUES("Bioinformática en la Universidad Veracruzana: Proyecto FEI-IIB" ,"Nuestro país cuenta con una amplia biodiversidad distribuida por todo el territorio nacional encontrándose entre los 17 países que, por la riqueza de especies y su endemismo se reconocen como megadiversos. Particularmente el Estado de Veracruz presenta diferentes ecosistemas en los que se localizan diferentes especies entre los que se encuentran 208 especies diferentes de mamíferos (192 continentales y 16 marinas) por mencionar solo un ejemplo. Dicha biodiversidad ha permitido la subsistencia y evolución a diferentes pueblos que han habitado el territorio nacional y siguen siendo una de las bases de nuestra economía al traducirse en pesca, caza, prácticas agrícolas, así como productos de investigaciones aplicadas.","04/05/2015","05/11/2021","JDOEIJ804");
INSERT INTO ProyectoTrabajoRecepcional(idProyecto,idTrabajoRecepcional) VALUES(1,1);
INSERT INTO CultivaProyecto(idProyecto,nombreLGAC) VALUES(1,"Tecnologías de software");
INSERT INTO DesarrollaProyecto(idProyecto,Cedula) VALUES(1,"4065161");
INSERT INTO ParticipaProyecto(idProyecto,Matricula) VALUES(1,"S19014013");
INSERT INTO ParticipaProyecto(idProyecto,Matricula) VALUES(1,"S19014023");
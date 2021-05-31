drop database cuerpoAcademico;
CREATE SCHEMA IF NOT EXISTS CuerpoAcademico;
USE  CuerpoAcademico;

CREATE TABLE IF NOT EXISTS Prerequisito(idPrerequisito int auto_increment NOT NULL , descripcion varchar(200) NOT NULL , idReunion int NOT NULL , primary key (idPrerequisito), foreign key (idReunion) REFERENCES Reunion (idReunion) ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS Anteproyecto(idAnteproyecto int NOT NULL, 
  titulo varchar(200) NOT NULL, 
  descripcion varchar(400) NOT NULL, fechaInicio varchar(30) NOT NULL, 
  fechaFin varchar(30)  NOT NULL, primary key (idAnteproyecto));

   CREATE TABLE IF NOT EXISTS TrabajoRecepcional (idTrabajoRecepcional int NOT NULL auto_increment, 
  titulo varchar(200) NOT NULL, tipo varchar(30) NOT NULL, 
  descripcion varchar(400) NOT NULL, fechaInicio varchar(20) NOT NULL, 
  fechaFin varchar(20) NOT NULL , estadoActual varchar(30) NOT NULL,  idAnteproyecto int NOT NULL, 
  primary key (idTrabajoRecepcional),
  foreign key (idAnteproyecto) REFERENCES Anteproyecto (idAnteproyecto) ON DELETE CASCADE);
  

  CREATE TABLE IF NOT EXISTS CuerpoAcademico(clave varchar(10) NOT NULL, 
nombre varchar(100) NOT NULL, objetivo varchar(400) NOT NULL, 
mision varchar(500) NOT NULL,vision varchar(500) NOT NULL, 
gradoConsolidacion varchar(30) NOT NULL, primary Key (clave));

CREATE TABLE IF NOT EXISTS LGAC(nombre varchar(200) NOT NULL,
descripcion varchar(400) NOT NULL, primary Key (nombre) );

CREATE TABLE IF NOT EXISTS CuerpoLGAC( claveCuerpoAcademico varchar(10) NOT NULL,
nombreLGAC varchar(200) NOT NULL,  foreign key (nombreLGAC) REFERENCES  LGAC (nombre) ,
 foreign key (claveCuerpoAcademico) REFERENCES  CuerpoAcademico (clave)  );

CREATE TABLE  IF NOT EXISTS Reunion (idReunion int auto_increment NOT NULL , 
asunto varchar(200) NOT NULL , hora varchar(10) NOT NULL ,
 fecha varchar(20) NOT NULL, estado varchar(30) NOT NULL , 
 primary key (idReunion) );
 
 CREATE TABLE IF NOT EXISTS Prerequisito(idPrerequisito int auto_increment NOT NULL , 
  descripcion varchar(200) NOT NULL , idReunion int NOT NULL ,
  primary key (idPrerequisito), 
  foreign key (idReunion) REFERENCES Reunion (idReunion) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS Estudiante(matricula varchar(10) NOT NULL, 
  nombre varchar(150) NOT NULL, primary key (matricula));
  

CREATE TABLE IF NOT EXISTS Proyecto(idProyecto int NOT NULL AUTO_INCREMENT ,
titulo varchar(200) NOT NULL, descripcion varchar(400) NOT NULL,fechaInicio varchar(30) not null, 
fechaFin varchar(30) not null , primary key(idProyecto) );

CREATE TABLE IF NOT EXISTS Agenda(idAgenda int NOT NULL AUTO_INCREMENT , 
primary key (idAgenda), idReunion int NOT NULL,
foreign key (idReunion) REFERENCES Reunion (idReunion) ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS Agenda_Tema(idAgenda int NOT NULL AUTO_INCREMENT , 
idTema int NOT NULL, tema varchar(200) NOT NULL, horaInicio varchar(10) NOT NULL,
horaFin varchar(10) NOT NULL, primary key(idAgenda,idTema),
foreign key (idAgenda)  REFERENCES agenda(idAgenda)ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS Minuta(idMinuta int NOT NULL AUTO_INCREMENT ,
nota varchar(500), estado varchar(30) NOT NULL,
pendiente varchar(500), idReunion int NOT NULL, 
primary key(idMinuta), foreign key(idReunion) references reunion(idReunion)ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS Acuerdo(idAcuerdo int NOT NULL AUTO_INCREMENT, 
periodo varchar(30) NOT NULL, descripcion varchar(200) NOT NULL,
idMinuta int NOT NULL, primary key(idAcuerdo),
foreign key (idMinuta) references Minuta(idMinuta)ON DELETE CASCADE);



CREATE TABLE IF NOT EXISTS CapituloLibro(idCapituloLibro int not null AUTO_INCREMENT,
titulo varchar(100) NOT NULL, descripcion varchar(300) NOT NULL, estado varchar(30) NOT NULL, 
editorial varchar(50) NOT NULL, ISBN varchar (8) NOT NULL, tituloLibro varchar (100) NOT NULL,
id_Proyecto int, primary key (idCapituloLibro), foreign key (id_Proyecto) references Proyecto (idProyecto));

CREATE TABLE IF NOT EXISTS  Memoria(idMemoria int NOT NULL auto_increment, 
titulo varchar(100) NOT NULL, descripcion varchar (300) NOT NULL, estado varchar(30) NOT NULL,
congreso varchar(100) NOT NULL,  proposito varchar(300) NOT NULL,
id_Proyecto int, primary key (idMemoria), foreign key (id_Proyecto) references Proyecto (idProyecto));

CREATE TABLE IF NOT EXISTS  Prototipo(idPrototipo int NOT NULL auto_increment, 
titulo varchar(100) NOT NULL, descripcion varchar(300) NOT NULL, estado varchar(30) NOT NULL, 
institucion varchar(100) NOT NULL,  caracteristicas varchar(400),
id_Proyecto int, primary key (idPrototipo), foreign key (id_Proyecto) references Proyecto (idProyecto));

CREATE TABLE if NOT EXISTS PlanTrabajo(clave VARCHAR(10) NOT NULL,
objetivo VARCHAR(200) NOT NULL, periodo VARCHAR(50) NOT NULL, clave_CA VARCHAR(10), 
primary key(clave), foreign key(clave_CA) references CuerpoAcademico(clave));
 
CREATE TABLE IF NOT EXISTS Meta(idMeta int NOT NULL AUTO_INCREMENT,
descripcion VARCHAR(300) NOT NULL, clave_CA VARCHAR(10), 
PRIMARY KEY(idMeta), FOREIGN KEY(clave_CA) references CuerpoAcademico(clave));

CREATE TABLE IF NOT EXISTS Accion(idAccion int NOT NULL AUTO_INCREMENT,
descripcion VARCHAR(300) NOT NULL, clave_Plan VARCHAR(10), 
PRIMARY KEY(idAccion), FOREIGN KEY(clave_Plan) references PlanTrabajo(clave));

CREATE TABLE IF NOT EXISTS Recurso(idRecurso int NOT NULL AUTO_INCREMENT,
descripcion VARCHAR(100) NOT NULL, PRIMARY KEY(idRecurso));

CREATE TABLE IF NOT EXISTS Miembro(cedula VARCHAR(10),
nombre varchar(150) NOT NULL, rol VARCHAR(20) NOT NULL,
gradoMaximo VARCHAR(50), correo VARCHAR(50) NOT NULL, clave_CA VARCHAR(10), 
primary key(cedula), foreign key(clave_CA) references CuerpoAcademico(clave));

CREATE TABLE IF NOT EXISTS ParticipaPlanTrabajo(clave VARCHAR(10) NOT NULL, 
cedula VARCHAR(10) NOT NULL , primary key(clave, cedula), 
foreign key(clave) references PlanTrabajo(clave), 
foreign key(cedula) references Miembro(cedula));

CREATE TABLE IF NOT EXISTS Requiere(idAccion int NOT NULL,
idRecurso int NOT NULL, primary key(idAccion,idRecurso),
foreign key(idAccion) references Accion(idAccion),
foreign key(idRecurso) references Recurso(idRecurso));

CREATE TABLE IF NOT EXISTS Encarga(Cedula VARCHAR(10) NOT NULL,
idAccion int NOT NULL, estadoAccion varchar(30) NOT NULL,
primary key(cedula,idAccion), foreign key(idAccion) 
references Accion(idAccion), foreign key(cedula) 
references miembro(cedula));

CREATE TABLE IF NOT EXISTS  Articulo(idArticulo int NOT NULL auto_increment, 
  titulo varchar(100) NOT NULL, estado varchar(30) NOT NULL, 
  nombreRevista varchar(50) NOT NULL, editorial varchar(50) NOT NULL, 
  ISNN varchar (8) NOT NULL , descripcion varchar(300) NOT NULL, id_Proyecto int, tipo varchar(15) not null, 
  primary key (idArticulo), foreign key (id_Proyecto) references Proyecto (idProyecto));

  CREATE TABLE IF NOT EXISTS  Libro(idLibro int NOT NULL auto_increment, 
  titulo varchar(100) NOT NULL,descripcion varchar(300) NOT NULL, estado varchar(30) NOT NULL, 
  editorial varchar(50) NOT NULL, ISBN varchar (8) NOT NULL , id_Proyecto int,
  primary key (idLibro), foreign key (id_Proyecto) references Proyecto (idProyecto));

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
 
 CREATE TABLE IF NOT EXISTS Prerequisito_Encargado( idPrerequisito int not null ,cedula VARCHAR(10) NOT NULL,
matricula varchar(10) NOT NULL, primary key(matricula,idPrerequisito),
 foreign key(idPrerequisito) references Prerequisito(idPrerequisito), 
 foreign key(cedula) references Miembro(cedula));

CREATE TABLE IF NOT EXISTS ParticipaReunion( idReunion int not null ,cedula VARCHAR(10) NOT NULL,
primary key(cedula,idReunion), foreign key(idReunion) 
references Reunion(idReunion), foreign key(cedula) 
references Miembro(cedula));

use cuerpoacademico;
select * from reunion;
select * from prerequisito;
/*create user 'integrante'@'localhost' identified by 'password';
Grant SELECT, UPDATE, DELETE, INSERT on cuerpoAcademico.* TO 'integrante'@'localhost'; /


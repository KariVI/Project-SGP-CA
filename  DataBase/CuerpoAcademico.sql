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
titulo varchar(200) NOT NULL, descripcion varchar(400) NOT NULL,fechaInicio varchar(30) not null, 
fechaFin varchar(30) not null , primary key(idProyecto) );



CREATE TABLE IF NOT EXISTS Tema(
idTema int NOT NULL AUTO_INCREMENT, tema varchar(200) NOT NULL, horaInicio varchar(10) NOT NULL,
idReunion int NOT NULL, horaFin varchar(10) NOT NULL, primary key(idTema,idReunion), cedula varchar(10) NOT NULL,
foreign key (idReunion)  REFERENCES Reunion(idReunion)ON DELETE CASCADE,
foreign key (cedula)  REFERENCES miembro(cedula)ON DELETE CASCADE);
CREATE TABLE IF NOT EXISTS Minuta(idMinuta int NOT NULL AUTO_INCREMENT ,
nota varchar(500), estado varchar(30) NOT NULL,
pendiente varchar(500), idReunion int NOT NULL, 
primary key(idMinuta), foreign key(idReunion) references reunion(idReunion)ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS Minuta(idMinuta int NOT NULL AUTO_INCREMENT ,
nota varchar(500), estado varchar(30) NOT NULL,
pendiente varchar(500), idReunion int NOT NULL, 
primary key(idMinuta), foreign key(idReunion) references reunion(idReunion)ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS Acuerdo(idAcuerdo int NOT NULL AUTO_INCREMENT, 
periodo varchar(30) NOT NULL, descripcion varchar(200) NOT NULL,
idMinuta int NOT NULL, primary key(idAcuerdo),
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

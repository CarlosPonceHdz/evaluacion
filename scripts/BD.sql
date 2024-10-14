create database almacen;
use almacen;

create table Roles(
idRol int (2) not null primary key,
nombre varchar (13) not null
);

create table Usuario(
idUsuario int (6) auto_increment not null primary key,
correo varchar (20) not null,
contrasenia varchar (20) not null,
gafet varchar (6) not null,
nombre varchar (20) not null,
apellido varchar (20) not null,
idRol int (2) not null,
estatus int(1) not null,
foreign key (idRol) references Roles(idRol)
);

create table Productos(
idProducto int auto_increment not null primary key,
nombre varchar (20) not null,
tipo varchar (20) not null,
provedor varchar (20) not null,
precio varchar (10) not null,
cantidad int not null,
estatus int not null
);

create table HistorialMovimientos(
idHistorial int auto_increment not null primary key,
tipoMovimiento varchar (7) not null,
fecha date not null,
hora time not null,
cantidadAnterior int not null,
cantidadNueva int not null,
idProducto int not null,
idUsuario int not null,
foreign key (idProducto) references Productos(idProducto),
foreign key (idUsuario) references Usuario(idUsuario)
);
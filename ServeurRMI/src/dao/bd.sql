create database gestionserveur;

use gestionserveur;

create table salle(
	num varchar(10),
	nom varchar(255) not null,
	primary key(num)
);

create table admin(
	id int auto_increment,
	pseudo varchar(25) not null,
	nom varchar(255) not null,
	prenom varchar(255) not null,
	primary key(id)
);

create table serveur(
	num varchar(10),
	nom varchar(255) not null,
	num_salle varchar(10) not null, 
	id_admin int not null,
	primary key(num),
	foreign key(num_salle) references salle(num),
	foreign key(id_admin) references admin(id)
);

grant all privileges on gestionserveur to 'alioune'@'localhost';

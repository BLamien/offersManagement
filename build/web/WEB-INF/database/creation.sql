-- ********************************************************************************************************
-- ********************************************************************************************************
-- **********************************             USERS          ******************************************
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE utulisateur(
matricule varchar(50),
cin varchar(30) NOT NULL,
nom varchar(50) NOT NULL,
prenom varchar(50) NOT NULL,
date_naissance date NOT NULL,
email varchar(100) NOT NULL,
tele varchar(20),
id_sexe int not null,
id_situation int not null,
-- acces
password varchar(25) NOT NULL,
-- CONSTRAINTS
CONSTRAINT pk_user PRIMARY KEY(matricule),
CONSTRAINT fk_sexe FOREIGN KEY(id_sexe) REFERENCES sexe(id_sexe),
CONSTRAINT fk_situation_familiale FOREIGN KEY(id_situation) REFERENCES situation_familiale (id_situation)
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE situation_familiale (
id_situation int auto_increment,
libele varchar(100) not null,
-- CONSTRAINT
CONSTRAINT pk_sexe PRIMARY KEY (id_situation)
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE sexe (
id_sexe int auto_increment,
libele varchar(100) not null,
-- CONSTRAINT
CONSTRAINT pk_sexe PRIMARY KEY (id_sexe)
);
-- ********************************************************************************************************
-- ********************************************************************************************************
-- **********************************       DROIT ACCES          ******************************************
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABle droit_acces(
id_droit int auto_increment,
libele varchar(300) not null,
-- CONSTRAINT
CONSTRAINT id_droit PRIMARY KEY (id_droit)
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABle profil(
id_profil int auto_increment,
libele varchar(300) not null,
-- CONSTRAINT
CONSTRAINT pk_profil PRIMARY KEY (id_profil)
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABle profil_droit(
id_profil int,
id_droit int,
-- CONSTRAINT
CONSTRAINT pk_profil_droit PRIMARY KEY (id_profil,id_droit),
CONSTRAINT fk_profil FOREIGN KEY (id_profil) REFERENCES profil(id_profil),
CONSTRAINT fk_droit_acces FOREIGN KEY (id_droit) REFERENCES droit_acces(id_droit)
);
-- ********************************************************************************************************
-- ********************************************************************************************************
-- **********************************         ARCHIVAGE          ******************************************
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABle range_archivage(
id_range int auto_increment,
num_range int not null,
-- CONSTRAINT
CONSTRAINT pk_range PRIMARY KEY (id_range)
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABle ligne_archivage(
id_ligne int auto_increment,
num_ligne int not null,
id_range int not null,
-- CONSTRAINT
CONSTRAINT pk_ligne PRIMARY KEY (id_ligne),
CONSTRAINT fk_range FOREIGN KEY (id_range) REFERENCES range_archivage (id_range) ON UPDATE CASCADE
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABle colonne_archivage(
id_colonne int auto_increment,
num_colonne int not null,
id_ligne int not null,
-- CONSTRAINT
CONSTRAINT pk_colonne PRIMARY KEY (id_colonne),
CONSTRAINT fk_ligne FOREIGN KEY (id_ligne) REFERENCES ligne_archivage (id_ligne) ON UPDATE CASCADE
);
-- ********************************************************************************************************
-- ********************************************************************************************************
-- **********************************           DOSSIER         ******************************************
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE nature (
id_nature int auto_increment,
libele varchar(100) not null,
-- CONSTRAINT
CONSTRAINT pk_nature PRIMARY KEY (id_nature)
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE phase (
id_phase int auto_increment,
libele varchar(100) not null,
-- CONSTRAINT
CONSTRAINT pk_phase PRIMARY KEY (id_phase)
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE dpetl (
id_dpetl int auto_increment,
libele varchar(300) not null,
-- CONSTRAINT
CONSTRAINT pk_dpetl PRIMARY KEY (id_dpetl)
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE avis(
id_avis int auto_increment,
libele varchar(100) not null,
-- CONSTRAINT
CONSTRAINT pk_avis PRIMARY KEY (id_avis)
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE dossier(
id_dossier int not null,
id_dpetl int not null,
route varchar(500) not null,
dossier varchar(500) not null,
date_entree date,
id_avis int not null,
date_sortie date,
id_nature int not null,
matricule_user varchar(50),
ref_appro varchar(100),
programmation varchar(300),
id_archivage int,
-- ALL CONSTRAINTS
CONSTRAINT PK_DOSSIER PRIMARY KEY (id_dossier),
-- ALL FOREIGN KEY CONSTRAINTs
CONSTRAINT FK_dpetl FOREIGN KEY(id_dpetl) REFERENCES dpetl(id_dpetl) ON UPDATE CASCADE,
CONSTRAINT FK_avis FOREIGN KEY(id_avis) REFERENCES avis(id_avis) ON UPDATE CASCADE,
CONSTRAINT FK_nature FOREIGN KEY(id_nature) REFERENCES nature(id_nature) ON UPDATE CASCADE,
CONSTRAINT FK_user FOREIGN KEY(matricule_user) REFERENCES utilisateur(matricule) ON UPDATE CASCADE,
CONSTRAINT FK_colonne FOREIGN KEY(id_archivage) REFERENCES colonne_archivage(id_colonne) ON UPDATE CASCADE,
CONSTRAINT unique_dossier_archivage UNIQUE(id_archivage)
);
ALTER TABLE dossier add column date_creation date not null;
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE note(
id_note int auto_increment,
note varchar(1000),
lien varchar(200),
date_creation date not null,
matricule_user varchar(50) not null,
id_dossier int not null,
-- CONSTRAINT
CONSTRAINT pk_note2 PRIMARY KEY(id_note),
CONSTRAINT fk_user2 FOREIGN KEY(matricule_user) REFERENCES utilisateur(matricule) ON UPDATE CASCADE,
CONSTRAINT fk_dossier FOREIGN KEY(id_dossier) REFERENCES dossier(id_dossier) ON UPDATE CASCADE
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE lettre_appro(
id_dossier int not null,
lien varchar(200),
date_creation date not null,
matricule_user varchar(50) not null,
-- CONSTRAINT
CONSTRAINT pk_note3 PRIMARY KEY(id_dossier),
CONSTRAINT fk_user3 FOREIGN KEY(matricule_user) REFERENCES utilisateur(matricule) ON UPDATE CASCADE,
CONSTRAINT fk_dossier2 FOREIGN KEY(id_dossier) REFERENCES dossier(id_dossier)ON UPDATE CASCADE
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE dossier_liaison (
id_dossier int not null,
longueur decimal,
bet varchar(100),
id_phase int not null,
-- CONSTRAINT
CONSTRAINT pk_dossier_ PRIMARY KEY(id_dossier),
CONSTRAINT fk_phase_laison foreign key(id_phase) REFERENCES phase(id_phase)
);
ALTER TABLE dossier_liaison
ADD CONSTRAINT fk_id_dossier_ FOREIGN KEY (id_dossier)
REFERENCES dossier(id_dossier) ON UPDATE CASCADE;

-- ********************************************************************************************************
-- ********************************************************************************************************

CREATE TABLE dossier_carrefour(
id_dossier int not null,
CONSTRAINT pk_dossier_ PRIMARY KEY(id_dossier),
CONSTRAINT fk_id_dossier_caref FOREIGN KEY (id_dossier) REFERENCES dossier(id_dossier) ON UPDATE CASCADE
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE dossier_oa(
id_dossier int not null,
CONSTRAINT pk_dossier_oa PRIMARY KEY(id_dossier),
CONSTRAINT fk_id_dossier_oa FOREIGN KEY (id_dossier) REFERENCES dossier(id_dossier) ON UPDATE CASCADE
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE dossier_cps_etude(
id_dossier int not null,
CONSTRAINT pk_dossier_cps_etude PRIMARY KEY(id_dossier),
CONSTRAINT fk_id_dossier_cps_etude FOREIGN KEY (id_dossier) REFERENCES dossier(id_dossier) ON UPDATE CASCADE
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE dossier_cps_travaux(
id_dossier_etud int not null,
id_dossier int,
CONSTRAINT pk_dossier_cps_travaux PRIMARY KEY(id_dossier_etud),
CONSTRAINT fk_id_dossier_cps_etude_ FOREIGN KEY (id_dossier_etud) REFERENCES dossier_cps_etude(id_dossier) ON UPDATE CASCADE,
CONSTRAINT fk_id_dossier__ FOREIGN KEY (id_dossier) REFERENCES dossier (id_dossier) ON UPDATE CASCADE
);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE dossier_cps_controle(
id_dossier_travaux int not null,
id_dossier int,
CONSTRAINT pk_dossier_cps_controle PRIMARY KEY(id_dossier_travaux),
CONSTRAINT fk_id_dossier_cps_controle_ FOREIGN KEY (id_dossier_travaux) REFERENCES dossier_cps_travaux(id_dossier_etud) ON UPDATE CASCADE,
CONSTRAINT fk_id_dossier_details FOREIGN KEY (id_dossier) REFERENCES dossier (id_dossier) ON UPDATE CASCADE
);
-- ********************************************************************************************************
-- ********************************************************************************************************
ALTER TABLE dossier_cps_travaux ADD CONSTRAINT unique_dossier UNIQUE(id_dossier);

ALTER TABLE dossier_cps_controle ADD CONSTRAINT unique_dossier_ UNIQUE(id_dossier);
-- ********************************************************************************************************
-- ********************************************************************************************************
CREATE TABLE archivage (
id_dossier int not null,
id_colonne_archivage int not null,
date_archivage date not null,
-- CONSTRAINT
CONSTRAINT pk_archivage PRIMARY KEY(id_dossier),
CONSTRAINT fk_id_dossier_archi FOREIGN KEY (id_dossier) REFERENCES dossier(id_dossier) ON UPDATE CASCADE,
CONSTRAINT fk_id_colonne_archi FOREIGN KEY (id_colonne_archivage) REFERENCES colonne_archivage(id_colonne) ON UPDATE CASCADE,
CONSTRAINT unique_archi UNIQUE(id_dossier),
CONSTRAINT unique_archi2 UNIQUE(id_colonne_archivage),
);

-- ********************************************************************************************************
-- ********************************************************************************************************
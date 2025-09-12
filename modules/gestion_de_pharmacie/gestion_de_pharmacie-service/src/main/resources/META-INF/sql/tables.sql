create table FOO_Commande (
	commandeId LONG not null primary key,
	dateCommande DATE null,
	statut VARCHAR(75) null,
	montantTotal DOUBLE,
	fournisseurId LONG,
	utilisateurId LONG
);

create table FOO_Medicament (
	medicamentId LONG not null primary key,
	code_ VARCHAR(75) null,
	nom VARCHAR(75) null,
	description VARCHAR(75) null,
	categorie VARCHAR(75) null,
	prixUnitaire DOUBLE,
	seuilMinimum INTEGER,
	dateAjout DATE null
);

create table FOO_Stock (
	stockId LONG not null primary key,
	medicamentId LONG,
	quantiteDisponible INTEGER,
	dateDerniereMaj DATE null
);

create table Pharma_Commande (
	idCommande LONG not null primary key,
	idFournisseur LONG,
	dateCommande DATE null,
	statut VARCHAR(75) null,
	montantTotal DOUBLE
);

create table Pharma_CommandeDetail (
	idDetail LONG not null primary key,
	idCommande LONG,
	idMedicament LONG,
	quantite INTEGER,
	prixUnitaire DOUBLE
);

create table Pharma_Fournisseur (
	idFournisseur LONG not null primary key,
	nom VARCHAR(75) null,
	adresse VARCHAR(75) null,
	telephone VARCHAR(75) null,
	email VARCHAR(75) null
);

create table Pharma_Medicament (
	idMedicament LONG not null primary key,
	code_ VARCHAR(75) null,
	codeBarre VARCHAR(75) null,
	nom VARCHAR(75) null,
	description VARCHAR(75) null,
	categorie VARCHAR(75) null,
	prixUnitaire DOUBLE,
	seuilMinimum INTEGER,
	dateAjout DATE null
);

create table Pharma_MouvementStock (
	idMouvement LONG not null primary key,
	idStock LONG,
	typeMouvement VARCHAR(75) null,
	quantite INTEGER,
	dateMouvement DATE null
);

create table Pharma_Notification (
	idNotification LONG not null primary key,
	idUtilisateur LONG,
	type_ VARCHAR(75) null,
	message VARCHAR(75) null,
	statut VARCHAR(75) null,
	dateCreation DATE null
);

create table Pharma_Stock (
	idStock LONG not null primary key,
	idMedicament LONG,
	quantiteDisponible INTEGER,
	dateDerniereMaj DATE null
);

create table Pharma_Utilisateur (
	idUtilisateur LONG not null primary key,
	nom VARCHAR(75) null,
	prenom VARCHAR(75) null,
	email VARCHAR(75) null,
	motDePasse VARCHAR(75) null,
	role_ VARCHAR(75) null,
	dateCreation DATE null,
	lastLogin DATE null
);

create table Pharma_Vente (
	idVente LONG not null primary key,
	idUtilisateur LONG,
	dateVente DATE null,
	montantTotal DOUBLE
);

create table Pharma_VenteDetail (
	idDetail LONG not null primary key,
	idVente LONG,
	idMedicament LONG,
	quantite INTEGER,
	prixUnitaire DOUBLE,
	sousTotal DOUBLE
);
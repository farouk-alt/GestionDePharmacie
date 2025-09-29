create index IX_1B9D1C16 on Pharma_Commande (idUtilisateur);

create index IX_D1857A5C on Pharma_CommandeDetail (idCommande);
create index IX_49F8453B on Pharma_CommandeDetail (idMedicament);

create index IX_28858466 on Pharma_Medicament (categorie[$COLUMN_LENGTH:75$]);
create index IX_831C7248 on Pharma_Medicament (codeBarre[$COLUMN_LENGTH:75$]);
create index IX_D456CF01 on Pharma_Medicament (code_[$COLUMN_LENGTH:75$]);
create index IX_94680E7B on Pharma_Medicament (nom[$COLUMN_LENGTH:75$]);

create index IX_3F19CC6C on Pharma_Notification (idUtilisateur, statut[$COLUMN_LENGTH:75$]);

create index IX_B191FEA2 on Pharma_Stock (idMedicament);

create unique index IX_C1D2E025 on Pharma_Utilisateur (email[$COLUMN_LENGTH:75$]);
create index IX_758693F2 on Pharma_Utilisateur (role_[$COLUMN_LENGTH:75$]);

create index IX_247AEE9E on Pharma_Vente (dateVente);
create index IX_8F211044 on Pharma_Vente (idUtilisateur);

create index IX_3D4BBDCD on Pharma_VenteDetail (idMedicament);
create index IX_745FFC80 on Pharma_VenteDetail (idVente);
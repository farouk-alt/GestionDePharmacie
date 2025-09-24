create index IX_3CEE43B4 on Pharma_Commande (idFournisseur);

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
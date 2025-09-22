<%@ page import="java.util.List" %>
<%@ page import="gestion_de_pharmacie.model.Utilisateur" %>
<%@ page import="gestion_de_pharmacie.model.Medicament" %>
<%@ page import="gestion_de_pharmacie.model.Commande" %>
<%@ page import="gestion_de_pharmacie.service.UtilisateurLocalServiceUtil" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<liferay-theme:defineObjects />

<h2>Créer une nouvelle commande</h2>

<%
	List<Utilisateur> fournisseurs = (List<Utilisateur>) request.getAttribute("fournisseurs");
	List<Medicament> medicaments = (List<Medicament>) request.getAttribute("medicaments");
%>

<aui:form actionName="createCommande" method="post">

	<!-- Fournisseur -->
	<aui:select label="Fournisseur" name="fournisseurId" required="true">
		<%
			for (Utilisateur f : fournisseurs) {
		%>
		<aui:option value="<%= f.getIdUtilisateur() %>">
			<%= f.getNom() + " " + f.getPrenom() %>
		</aui:option>
		<%
			}
		%>
	</aui:select>

	<hr/>

	<!-- Médicaments -->
	<table class="table table-bordered">
		<thead>
		<tr>
			<th>Sélection</th>
			<th>Médicament</th>
			<th>Prix Unitaire</th>
			<th>Quantité</th>
		</tr>
		</thead>
		<tbody>
		<%
			for (Medicament m : medicaments) {
		%>
		<tr>
			<td>
				<input type="checkbox" name="medicamentId" value="<%= m.getIdMedicament() %>" />
			</td>
			<td><%= m.getNom() %></td>
			<td><%= m.getPrixUnitaire() %> DH</td>
			<td>
				<!-- use unique name tied to medicamentId -->
				<input type="number" name="quantite_<%= m.getIdMedicament() %>" value="1" min="1" />
			</td>
		</tr>
		<%
			}
		%>
		</tbody>
	</table>

	<aui:button type="submit" value="Créer la commande" />
</aui:form>
<hr/>

<h2>Liste des Commandes</h2>

<%
	List<Commande> commandes = (List<Commande>) request.getAttribute("commandes");
%>

<c:if test="${empty commandes}">
	<div>Aucune commande trouvée.</div>
</c:if>

<c:if test="${not empty commandes}">
	<table class="table table-striped table-bordered" style="width:100%;border-collapse:collapse;">
		<thead>
		<tr>
			<th>ID</th>
			<th>Fournisseur</th>
			<th>Date</th>
			<th>Statut</th>
			<th>Montant Total (DH)</th>
		</tr>
		</thead>
		<tbody>
		<%
			for (Commande c : commandes) {
				String fournisseurName = "-";
				try {
					Utilisateur f = UtilisateurLocalServiceUtil.getUtilisateur(c.getIdFournisseur());
					fournisseurName = f.getNom() + " " + f.getPrenom();
				} catch (Exception ignore) {}
		%>
		<tr>
			<td><%= c.getIdCommande() %></td>
			<td><%= fournisseurName %></td>
			<td><%= c.getDateCommande() %></td>
			<td><%= c.getStatut() %></td>
			<td><%= c.getMontantTotal() %></td>
		</tr>
		<%
			}
		%>
		</tbody>
	</table>
</c:if>

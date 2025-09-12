<%@ page import="java.util.List" %>
<%@ page import="gestion_de_pharmacie.model.Utilisateur" %>
<%@ page import="gestion_de_pharmacie.model.Medicament" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<h2>Créer une nouvelle commande</h2>

<%
	List<Utilisateur> fournisseurs = (List<Utilisateur>) request.getAttribute("fournisseurs");
	List<Medicament> medicaments = (List<Medicament>) request.getAttribute("medicaments");
%>

<aui:form action="<portlet:actionURL name='createCommande'/>" method="post">

	<!-- Fournisseur -->
	<aui:select label="Fournisseur" name="fournisseurId" required="true">
		<%
			for (Utilisateur f : fournisseurs) {
		%>
		<aui:option value="<%=f.getIdUtilisateur()%>"><%=f.getNom() + " " + f.getPrenom()%></aui:option>
		<%
			}
		%>
	</aui:select>

	<hr/>

	<!-- Medicaments table -->
	<table class="table table-bordered">
		<thead>
		<tr>
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
				<aui:input type="checkbox" name="medicamentId" value="<%=m.getIdMedicament()%>" />
				<%=m.getNom()%>
			</td>
			<td><%=m.getPrixUnitaire()%> DH</td>
			<td>
				<aui:input type="number" name="quantite" value="1" min="1" />
			</td>
		</tr>
		<%
			}
		%>
		</tbody>
	</table>

	<aui:button type="submit" value="Créer la commande" />

</aui:form>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="gestion_de_pharmacie.model.Medicament" %>

<h2>Stock des Médicaments</h2>

<%
	List<Map<String, Object>> stockList = (List<Map<String, Object>>) request.getAttribute("stockList");
%>

<table class="table table-bordered table-striped">
	<thead>
	<tr>
		<th>Nom</th>
		<th>Catégorie</th>
		<th>Prix Unitaire</th>
		<th>Quantité Disponible</th>
		<th>Remarques</th>
	</tr>
	</thead>
	<tbody>
	<%
		if (stockList != null) {
			for (Map<String, Object> entry : stockList) {
				Medicament m = (Medicament) entry.get("medicament");
				Integer quantite = (Integer) entry.get("quantite");
				Long medicamentId = entry.get("medicamentId") != null ? (Long) entry.get("medicamentId") : null;
	%>
	<tr>
		<td><%= (m != null) ? m.getNom() : ("-- Supprimé (id: " + medicamentId + ") --") %></td>
		<td><%= (m != null) ? m.getCategorie() : "-" %></td>
		<td><%= (m != null) ? (m.getPrixUnitaire() + " DH") : "-" %></td>
		<td><%= (quantite != null) ? quantite : 0 %></td>
		<td>
			<%
				if (m == null) {
			%>
			<span style="color:orange">Le médicament référencé n'existe plus.</span>
			<%
			} else {
			%>
			&nbsp;
			<%
				}
			%>
		</td>
	</tr>
	<%
		}
	} else {
	%>
	<tr>
		<td colspan="5">Aucun stock trouvé.</td>
	</tr>
	<%
		}
	%>
	</tbody>
</table>

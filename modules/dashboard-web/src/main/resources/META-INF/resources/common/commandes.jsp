<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="gestion_de_pharmacie.model.Commande" %>
<%@ page import="gestion_de_pharmacie.model.Fournisseur" %>
<%@ page import="gestion_de_pharmacie.service.CommandeLocalServiceUtil" %>
<%@ page import="gestion_de_pharmacie.service.FournisseurLocalServiceUtil" %>
<%@ page import="auth.web.constants.AuthWebSessionKeys" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
  // Grab HttpSession from request
  HttpSession hs = request.getSession(false);
  String userEmail = (hs != null) ? (String) hs.getAttribute(AuthWebSessionKeys.USER_EMAIL) : null;
  String userRole  = (hs != null) ? (String) hs.getAttribute(AuthWebSessionKeys.USER_ROLE) : null;

  List<Commande> commandes = new ArrayList<>();
  Fournisseur fournisseur = null;

  if ("FOURNISSEUR".equals(userRole) && userEmail != null) {
    try {
      // Map fournisseur by email
      fournisseur = FournisseurLocalServiceUtil.fetchFournisseurByEmail(userEmail);

      if (fournisseur != null) {
        final long fournisseurId = fournisseur.getIdFournisseur(); // <-- final for lambda
        commandes = CommandeLocalServiceUtil.getCommandes(-1, -1)
                .stream()
                .filter(c -> c.getIdFournisseur() == fournisseurId)
                .toList();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
%>

<div class="commandes-wrapper">
  <c:choose>
    <c:when test="<%= (fournisseur != null && commandes != null && !commandes.isEmpty()) %>">
      <table class="table table-striped table-bordered">
        <thead>
        <tr>
          <th>ID</th>
          <th>Date</th>
          <th>Statut</th>
          <th>Montant Total</th>
        </tr>
        </thead>
        <tbody>
        <%
          for (Commande c : commandes) {
        %>
        <tr>
          <td><%= c.getIdCommande() %></td>
          <td><%= (c.getDateCommande() != null ? c.getDateCommande() : "") %></td>
          <td><%= c.getStatut() %></td>
          <td><%= c.getMontantTotal() %> DH</td>
        </tr>
        <%
          }
        %>
        </tbody>
      </table>
    </c:when>
    <c:otherwise>
      <div class="alert alert-info">
        Aucune commande trouvée pour ce fournisseur.
      </div>
    </c:otherwise>
  </c:choose>
</div>

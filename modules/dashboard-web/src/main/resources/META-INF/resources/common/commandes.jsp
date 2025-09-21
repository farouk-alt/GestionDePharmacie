<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
  // Get the user email and role from session
  String userEmail = (String) session.getAttribute("USER_EMAIL");
  String userRole  = (String) session.getAttribute("USER_ROLE");
%>

<div class="dashboard-cards">

  <div class="card">
    <div class="card-header">
      <div class="card-icon"><i class="fas fa-box"></i></div>
      <h3 style="margin:0;">Commandes</h3>
    </div>
    <p>Liste des commandes qui vous sont destinées.</p>

    <c:if test="${not empty commandes}">
      <table class="table" style="width:100%; border-collapse: collapse; margin-top:15px;">
        <thead style="background:#f0f4ff;">
        <tr>
          <th>ID</th>
          <th>Client</th>
          <th>Date</th>
          <th>Status</th>
          <th>Total</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="cmd" items="${commandes}">
          <tr style="border-bottom:1px solid #ddd;">
            <td>${cmd.id}</td>
            <td>${cmd.clientName}</td>
            <td><fmt:formatDate value="${cmd.date}" pattern="dd/MM/yyyy HH:mm"/></td>
            <td>${cmd.status}</td>
            <td>${cmd.total} DH</td>
            <td>
              <form action="<portlet:actionURL name='viewCommande'/>" method="post" style="display:inline;">
                <input type="hidden" name="<portlet:namespace/>commandeId" value="${cmd.id}" />
                <button type="submit" class="btn" style="padding:5px 10px;">
                  <i class="fas fa-eye"></i> Voir
                </button>
              </form>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </c:if>

    <c:if test="${empty commandes}">
      <div class="message info" style="margin-top:10px;">Aucune commande pour le moment.</div>
    </c:if>
  </div>
</div>

<style>
  .table th, .table td {
    text-align: left;
    padding: 8px;
  }
  .table th {
    font-weight: bold;
  }
  .card {
    background: white;
    border-radius: 10px;
    padding: 20px;
    box-shadow: 0 5px 15px rgba(0,0,0,0.1);
    margin-bottom: 20px;
  }
  .card-header {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
  }
  .card-icon {
    width: 40px;
    height: 40px;
    background: #f0f4ff;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 15px;
    color: #4e54c8;
  }
  .btn {
    background: #4e54c8;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-weight: bold;
    transition: all 0.2s;
  }
  .btn:hover {
    background: #8f94fb;
  }
  .message.info {
    background: #e7f3ff;
    color: #3178c6;
    padding: 10px;
    border-radius: 5px;
  }
</style>

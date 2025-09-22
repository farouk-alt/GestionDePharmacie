<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<portlet:defineObjects />

<%
    @SuppressWarnings("unchecked")
    java.util.List<gestion_de_pharmacie.model.Notification> notifs =
            (java.util.List<gestion_de_pharmacie.model.Notification>) request.getAttribute("notifs");
    if (notifs == null) notifs = java.util.Collections.emptyList();
%>

<style>
    :root{ --primary:#1E3A8A; --border:#E5E7EB; --text:#111827; --white:#fff; --muted:#6B7280; }
    .wrap{font-family:'Segoe UI',Tahoma,Verdana,sans-serif;color:var(--text)}
    .hdr{display:flex;align-items:center;justify-content:space-between;margin:0 0 14px 0}
    .btn{background:#fff;color:var(--primary);border:1px solid var(--border);padding:8px 12px;border-radius:10px;font-weight:600;cursor:pointer;text-decoration:none}
    .btn:hover{background:#F8FAFC}
    table.tbl{width:100%;border-collapse:separate;border-spacing:0;background:var(--white);border:1px solid var(--border);border-radius:12px;overflow:hidden}
    .tbl thead th{background:var(--primary);color:#fff;text-align:left;padding:12px 14px}
    .tbl tbody td{padding:12px 14px;border-bottom:1px solid var(--border)}
    .empty{padding:16px;border:1px dashed var(--border);border-radius:10px;background:#fff;color:var(--muted)}
    .badge{display:inline-block;padding:4px 8px;border-radius:999px;font-size:12px;font-weight:700;background:#eef2ff;color:#3730a3}
</style>

<div class="wrap">
    <div class="hdr">
        <h3 style="margin:0">🔔 Notifications</h3>

        <!-- Mark all as read -->
        <c:if test="<%= !notifs.isEmpty() %>">
            <portlet:actionURL name="markAllRead" var="markAllURL" />
            <form method="post" action="${markAllURL}" style="margin:0">
                <button class="btn" type="submit">Tout marquer comme lu</button>
            </form>
        </c:if>
    </div>

    <liferay-ui:success key="notif-updated" message="Notifications mises à jour." />
    <liferay-ui:error   key="notif-error"   message="Erreur lors de la mise à jour des notifications." />

    <c:choose>
        <c:when test="<%= notifs.isEmpty() %>">
            <div class="empty">Aucune notification.</div>
        </c:when>
        <c:otherwise>
            <table class="tbl">
                <thead>
                <tr>
                    <th>Type</th>
                    <th>Message</th>
                    <th>Statut</th>
                    <th>Date</th>
                    <th style="width:1%">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="n" items="<%= notifs %>">
                    <tr>
                        <td><span class="badge"><c:out value="${n.type}"/></span></td>
                        <td><c:out value="${n.message}"/></td>
                        <td><c:out value="${n.statut}"/></td>
                        <td><fmt:formatDate value="${n.dateCreation}" pattern="dd/MM/yyyy HH:mm"/></td>
                        <td>
                            <c:if test="${n.statut ne 'READ'}">
                                <portlet:actionURL name="markRead" var="markURL">
                                    <portlet:param name="id" value="${n.idNotification}" />
                                </portlet:actionURL>
                                <form method="post" action="${markURL}" style="margin:0">
                                    <button class="btn" type="submit">Marquer comme lue</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

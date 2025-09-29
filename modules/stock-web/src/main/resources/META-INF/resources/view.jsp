<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<portlet:defineObjects />

<%
    List<Map<String,Object>> rows = (List<Map<String,Object>>) request.getAttribute("stockRows");
    if (rows == null) rows = new java.util.ArrayList<>();
%>

<portlet:actionURL name="adjustStock" var="adjustURL" />
<portlet:actionURL name="setStock"    var="setURL" />
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8" />
    <title>Stocks</title>
    <style>
        .stk-card{background:#fff;border:1px solid #E5E7EB;border-radius:12px;padding:16px;box-shadow:0 6px 18px rgba(17,24,39,.06)}
        #stkTable{width:100%;border-collapse:collapse}
        #stkTable thead th{background:#F8FAFC;color:#1E3A8A;padding:10px 12px;border-bottom:1px solid #E5E7EB;font-weight:700;text-align:left}
        #stkTable tbody td{padding:10px 12px;border-bottom:1px solid #E5E7EB}
        .num{text-align:right}
        .stk-controls{display:flex;gap:12px;align-items:center;flex-wrap:wrap;margin-bottom:10px}
        .stk-controls input[type="search"]{flex:1;min-width:240px;padding:10px;border:1px solid #E5E7EB;border-radius:10px;background:#fff}
        .btn{display:inline-block;background:#fff;color:#1E3A8A;border:1px solid #E5E7EB;padding:8px 12px;border-radius:10px;font-weight:600;text-decoration:none;cursor:pointer}
        .btn-primary{background:#10B981;color:#fff;border-color:#10B981}
        .inline{display:inline}
    </style>
</head>

<body>
<div class="stk-card">
    <div class="stk-controls">
        <input id="stkSearch" type="search" placeholder="Rechercher (médicament)..." />
        <span id="stkRange" style="color:#6B7280;font-size:12px;"></span>
    </div>

    <table id="stkTable">
        <thead>
        <tr>
            <th>Médicament</th>
            <th class="num">Prix (DH)</th>
            <th class="num">Quantité</th>
            <th>Dernière MAJ</th>
        </tr>
        </thead>
        <tbody id="stkBody">
        <c:forEach var="r" items="${stockRows}">
            <tr data-name="${fn:escapeXml(r['nomMedicament'])}">
                <td>${fn:escapeXml(r['nomMedicament'])}</td>
                <td class="num"><fmt:formatNumber value="${r['prixUnitaire']}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td class="num">${r['quantite']}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty r['dateMaj']}"><fmt:formatDate value="${r['dateMaj']}" pattern="dd/MM/yyyy HH:mm" /></c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>



<script>
    (function(){
        const q = document.getElementById('stkSearch');
        const body = document.getElementById('stkBody');
        const rows = Array.from(body.querySelectorAll('tr'));
        const range = document.getElementById('stkRange');
        function apply(){
            const s = (q && q.value || '').toLowerCase().trim();
            let vis = 0;
            rows.forEach(tr=>{
                const name = (tr.getAttribute('data-name')||'').toLowerCase();
                const keep = !s || name.includes(s);
                tr.style.display = keep ? '' : 'none';
                if (keep) vis++;
            });
            if (range) range.textContent = vis+' élément'+(vis>1?'s':'');
        }
        if (q) q.addEventListener('input', apply);
        apply();
    })();
</script>
</body>
</html>
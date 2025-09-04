<%@ include file="/init.jsp" %>

<div class="auth-container">
    <h2>Connexion à la Pharmacie</h2>

    <portlet:actionURL name="login" var="loginURL" />
    <c:if test="${not empty loginError}">
        <div class="alert alert-danger">${loginError}</div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>


    <aui:form action="${loginURL}" method="post" name="fmLogin">
        <aui:input name="email" label="Email" type="email" required="true" />
        <aui:input name="password" label="Mot de passe" type="password" required="true" />

        <aui:button-row>
            <aui:button type="submit" value="Se connecter" cssClass="btn-primary" />
        </aui:button-row>
    </aui:form>

    <p>Pas encore de compte ?
        <a href="<portlet:renderURL><portlet:param name='action' value='register' /></portlet:renderURL>">
            Créer un compte
        </a>
    </p>
</div>

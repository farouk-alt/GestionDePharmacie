<%@ include file="/init.jsp" %>

<div class="auth-container">
    <h2>Créer un compte</h2>

    <portlet:actionURL name="register" var="registerURL" />
    <c:if test="${not empty loginError}">
        <div class="alert alert-danger">${loginError}</div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>


    <aui:form action="${registerURL}" method="post" name="fmRegister">
        <aui:input name="fullname" label="Nom complet" required="true" />
        <aui:input name="email" label="Email" type="email" required="true" />
        <aui:input name="password" label="Mot de passe" type="password" required="true" />
        <aui:input name="confirmPassword" label="Confirmer le mot de passe" type="password" required="true" />

        <aui:button-row>
            <aui:button type="submit" value="S'inscrire" cssClass="btn-success" />
        </aui:button-row>
    </aui:form>

    <p>Déjà inscrit ?
        <a href="<portlet:renderURL><portlet:param name='mvcPath' value='/login.jsp' /></portlet:renderURL>">
            Se connecter
        </a>
    </p>
</div>

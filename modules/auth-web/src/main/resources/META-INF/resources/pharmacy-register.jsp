<%--
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pharmacie - Inscription</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        /* Your existing CSS remains the same */
        .pharmacy-auth-portlet {
            background: linear-gradient(135deg, #e8f5e9 0%, #c8e6c9 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 0;
            margin-bottom: 2.8%;
            margin-top: 2.8%;
            width: 100%;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .pharmacy-auth-portlet .container {
            display: flex;
            width: 100%;
            max-width: 100%;
            min-height: 100vh;
            background: white;
            margin: 0;
            padding: 0;
        }

        .pharmacy-auth-portlet .left-section {
            flex: 1;
            padding: 50px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            position: relative;
            background: white;
        }

        .pharmacy-auth-portlet .right-section {
            flex: 1.2;
            background: linear-gradient(to bottom right, #2e7d32, #4caf50);
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 40px;
            color: white;
        }

        .pharmacy-auth-portlet .logo {
            display: flex;
            align-items: center;
            margin-bottom: 40px;
        }

        .pharmacy-auth-portlet .logo i {
            font-size: 36px;
            color: #2e7d32;
            margin-right: 15px;
        }

        .pharmacy-auth-portlet .logo h1 {
            font-size: 32px;
            color: #333;
            font-weight: 700;
        }

        .pharmacy-auth-portlet .form-container {
            width: 100%;
            max-width: 450px;
            margin: 0 auto;
        }

        .pharmacy-auth-portlet h2 {
            color: #2e7d32;
            margin-bottom: 30px;
            font-size: 28px;
            font-weight: 600;
        }

        .pharmacy-auth-portlet .form-group {
            margin-bottom: 25px;
        }

        .pharmacy-auth-portlet label {
            display: block;
            margin-bottom: 8px;
            color: #455a64;
            font-weight: 500;
            font-size: 16px;
        }

        .pharmacy-auth-portlet input, .pharmacy-auth-portlet .aui-field-input {
            width: 100%;
            padding: 16px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s;
            box-sizing: border-box;
        }

        .pharmacy-auth-portlet input:focus, .pharmacy-auth-portlet .aui-field-input:focus {
            outline: none;
            border-color: #4caf50;
            box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.2);
        }

        .pharmacy-auth-portlet .btn {
            width: 100%;
            padding: 16px;
            border: none;
            border-radius: 8px;
            background: #4caf50;
            color: white;
            font-size: 17px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            margin-top: 15px;
        }

        .pharmacy-auth-portlet .btn:hover {
            background: #388e3c;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .pharmacy-auth-portlet .switch-form {
            text-align: center;
            margin-top: 25px;
            color: #666;
            font-size: 15px;
        }

        .pharmacy-auth-portlet .switch-form a {
            color: #2e7d32;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s;
        }

        .pharmacy-auth-portlet .switch-form a:hover {
            color: #1b5e20;
            text-decoration: underline;
        }

        .pharmacy-auth-portlet .error-message {
            color: #d32f2f;
            text-align: center;
            margin-top: 20px;
            padding: 12px;
            background: #ffebee;
            border-radius: 8px;
            border-left: 4px solid #d32f2f;
        }

        .pharmacy-auth-portlet .success-message {
            color: #2e7d32;
            text-align: center;
            margin-top: 20px;
            padding: 12px;
            background: #e8f5e9;
            border-radius: 8px;
            border-left: 4px solid #2e7d32;
        }

        .pharmacy-auth-portlet .pharmacy-illustration {
            text-align: center;
            max-width: 80%;
            margin-bottom: 30px;
        }

        .pharmacy-auth-portlet .pharmacy-illustration i {
            font-size: 200px;
            margin-bottom: 30px;
            color: rgba(255, 255, 255, 0.9);
            opacity: 0.9;
        }

        .pharmacy-auth-portlet .illustration-text {
            text-align: center;
            margin-bottom: 40px;
        }

        .pharmacy-auth-portlet .illustration-text h3 {
            font-size: 32px;
            margin-bottom: 20px;
            font-weight: 600;
        }

        .pharmacy-auth-portlet .illustration-text p {
            font-size: 18px;
            line-height: 1.6;
            max-width: 400px;
            opacity: 0.9;
        }

        .pharmacy-auth-portlet .features {
            margin-top: 40px;
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 20px;
        }

        .pharmacy-auth-portlet .feature {
            display: flex;
            align-items: center;
            background: rgba(255, 255, 255, 0.15);
            padding: 15px 20px;
            border-radius: 10px;
            backdrop-filter: blur(10px);
            width: 45%;
            font-weight: 500;
        }

        .pharmacy-auth-portlet .feature i {
            font-size: 20px;
            margin-right: 12px;
        }

        .session-info {
            position: absolute;
            top: 30px;
            right: 30px;
            background: white;
            padding: 12px 18px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            z-index: 100;
            border-left: 4px solid #4caf50;
        }

        .session-info p {
            margin: 0 0 8px 0;
            color: #455a64;
            font-weight: 500;
        }

        .session-info a {
            color: #2e7d32;
            text-decoration: none;
            font-weight: 600;
            display: inline-block;
            padding: 5px 0;
        }

        .session-info a:hover {
            text-decoration: underline;
        }

        .test-page-link {
            position: absolute;
            top: 30px;
            left: 30px;
            background: #ff9800;
            color: white;
            padding: 12px 18px;
            border-radius: 8px;
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 8px;
            font-weight: 500;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            transition: all 0.3s;
        }

        .test-page-link:hover {
            background: #f57c00;
            transform: translateY(-2px);
            box-shadow: 0 6px 16px rgba(0,0,0,0.15);
        }

        @media (max-width: 992px) {
            .pharmacy-auth-portlet .container {
                flex-direction: column;
            }

            .pharmacy-auth-portlet .right-section {
                display: none;
            }

            .pharmacy-auth-portlet .left-section {
                padding: 40px 30px;
            }

            .session-info {
                position: relative;
                top: 0;
                right: 0;
                margin: 0 0 20px 0;
                display: inline-block;
                width: 100%;
                box-sizing: border-box;
            }
        }
    </style>
</head>
<body>
<c:if test="${not empty sessionScope.authenticated}">
    <div class="session-info">
        <p>Bonjour, ${sessionScope.userEmail} !</p>
        <a href="<portlet:actionURL name='logout'/>">Se déconnecter</a>
    </div>
</c:if>

<div class="pharmacy-auth-portlet">
    <div class="container">
        <!-- Left Section - Forms -->
        <div class="left-section">
            <div class="logo">
                <i class="fas fa-mortar-pestle"></i>
                <h1>Pharma<span style="color: #2e7d32;">Care</span></h1>
            </div>

            <!-- Register Form -->
            <div id="register-form" class="form-container">
                <h2>Créer un compte</h2>
                <portlet:actionURL name="register" var="registerURL" />

                <aui:form action="${registerURL}" method="post" name="registerForm">
                    <div class="form-group">
                        <aui:input
                                label="Nom complet *"
                                name="fullname"
                                type="text"
                                placeholder="Votre nom complet"
                                required="true"
                                cssClass="aui-field-input"
                        />
                    </div>
                    <div class="form-group">
                        <aui:input
                                label="Email *"
                                name="email"
                                type="email"
                                placeholder="votre@email.com"
                                required="true"
                                cssClass="aui-field-input"
                        />
                    </div>
                    <div class="form-group">
                        <aui:input
                                label="Mot de passe *"
                                name="password"
                                type="password"
                                placeholder="Créez un mot de passe"
                                required="true"
                                cssClass="aui-field-input"
                        />
                    </div>
                    <div class="form-group">
                        <aui:input
                                label="Confirmer le mot de passe *"
                                name="confirmPassword"
                                type="password"
                                placeholder="Confirmez votre mot de passe"
                                required="true"
                                cssClass="aui-field-input"
                        />
                    </div>
                    <aui:button type="submit" value="S'inscrire" cssClass="btn" />
                </aui:form>

                <c:if test="${not empty errorMessage}">
                    <div class="error-message" id="register-error">
                            ${errorMessage}
                    </div>
                </c:if>

                <c:if test="${not empty successMessage}">
                    <div class="success-message" id="register-success">
                            ${successMessage}
                    </div>
                </c:if>

                <div class="switch-form">
                    <p>Vous avez déjà un compte ? <a href="<portlet:renderURL></portlet:renderURL>">Se connecter</a></p>
                </div>
            </div>
        </div>

        <!-- Right Section - Illustration -->
        <div class="right-section">
            <div class="pharmacy-illustration">
                <i class="fas fa-user-plus"></i>
            </div>
            <div class="illustration-text">
                <h3>Rejoignez PharmaCare</h3>
                <p>Créez votre compte pour accéder à tous nos services de gestion de pharmacie.</p>
            </div>
            <div class="features">
                <div class="feature">
                    <i class="fas fa-lock"></i>
                    <span>Sécurité des données</span>
                </div>
                <div class="feature">
                    <i class="fas fa-bolt"></i>
                    <span>Processus rapide</span>
                </div>
                <div class="feature">
                    <i class="fas fa-headset"></i>
                    <span>Support 24/7</span>
                </div>
                <div class="feature">
                    <i class="fas fa-chart-line"></i>
                    <span>Analytiques avancées</span>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<!-- URLs -->
<portlet:actionURL name="register" var="registerURL" />
<portlet:actionURL name="logout"   var="logoutURL" />
<portlet:renderURL var="loginRenderURL" />

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Pharmacie - Inscription</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root{
            --primary:#1E3A8A;
            --secondary:#3B82F6;
            --accent:#10B981;
            --bg:#F3F4F6;
            --white:#fff;
            --text:#111827;
            --muted:#6B7280;
            --error:#DC2626;
        }
        html,body{height:100%}
        body{margin:0;background:var(--bg);color:var(--text);font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif}

        .pharmacy-auth-portlet{min-height:100vh;display:flex;justify-content:center;align-items:center;padding:24px}
        .container{
            display:flex;width:100%;max-width:1100px;background:var(--white);border-radius:16px;
            box-shadow:0 20px 60px rgba(30,58,138,.18);overflow:hidden
        }
        .left-section{flex:1;padding:48px;display:flex;flex-direction:column;justify-content:center;position:relative}
        .right-section{
            flex:1;background:linear-gradient(135deg,var(--primary),var(--secondary));
            display:flex;flex-direction:column;align-items:center;justify-content:center;color:var(--white);padding:42px
        }

        .logo{display:flex;align-items:center;margin-bottom:36px}
        .logo i{font-size:36px;color:var(--primary);margin-right:14px}
        .logo h1{margin:0;font-size:30px;font-weight:800;color:var(--primary)}
        .logo h1 span{color:var(--secondary)}

        .form-container{width:100%;max-width:460px;margin:0 auto}
        h2{margin:0 0 22px 0;color:var(--primary);font-size:26px}
        .form-group{margin-bottom:18px}
        label{display:block;margin-bottom:8px;color:var(--muted);font-weight:600}
        input, .aui-field-input{
            width:100%;padding:14px 12px;border:1px solid #E5E7EB;border-radius:10px;font-size:16px;
            transition:border-color .2s, box-shadow .2s;box-sizing:border-box
        }
        input:focus, .aui-field-input:focus{
            outline:none;border-color:var(--secondary);
            box-shadow:0 0 0 3px rgba(59,130,246,.2)
        }

        .btn{
            width:100%;padding:14px;border:none;border-radius:10px;background:var(--primary);
            color:var(--white);font-weight:700;font-size:16px;cursor:pointer;transition:transform .15s, box-shadow .15s, background .15s
        }
        .btn:hover{background:#1b3174;transform:translateY(-2px);box-shadow:0 8px 20px rgba(30,58,138,.25)}

        .switch-form{margin-top:18px;text-align:center;color:var(--muted)}
        .switch-form a{color:var(--secondary);text-decoration:none;font-weight:700}
        .switch-form a:hover{text-decoration:underline}

        .error-message,.success-message{
            padding:12px 14px;border-radius:10px;margin-top:14px;font-weight:600
        }
        .error-message{background:#FEE2E2;color:var(--error);border:1px solid #FCA5A5}
        .success-message{background:#ECFDF5;color:var(--accent);border:1px solid #A7F3D0}

        .pharmacy-illustration{text-align:center;margin-bottom:24px}
        .pharmacy-illustration i{font-size:180px;opacity:.95}
        .illustration-text{text-align:center;margin-bottom:20px}
        .illustration-text h3{margin:0 0 10px 0;font-size:28px}
        .illustration-text p{margin:0;opacity:.95;max-width:420px}

        .features{display:flex;flex-wrap:wrap;gap:14px;justify-content:center;margin-top:22px}
        .feature{display:flex;align-items:center;background:rgba(255,255,255,.15);padding:10px 14px;border-radius:10px}
        .feature i{margin-right:10px}

        .session-info{
            position:absolute;top:18px;right:18px;background:var(--white);color:var(--text);padding:10px 14px;border-radius:10px;
            box-shadow:0 8px 20px rgba(17,24,39,.12);border-left:4px solid var(--accent)
        }
        .session-info a{color:var(--secondary);font-weight:700;text-decoration:none}
        .session-info a:hover{text-decoration:underline}

        @media (max-width: 980px){
            .container{flex-direction:column}
            .right-section{display:none}
            .left-section{padding:32px}
        }
    </style>
</head>
<body>

<c:if test="${not empty sessionScope.authenticated}">
    <div class="session-info">
        <p style="margin:0 0 6px 0;">Bonjour, ${fn:escapeXml(sessionScope.userEmail)} !</p>
        <a href="${logoutURL}">Se déconnecter</a>
    </div>
</c:if>

<div class="pharmacy-auth-portlet">
    <div class="container">
        <!-- Left Section -->
        <div class="left-section">
            <div class="logo">
                <i class="fas fa-mortar-pestle"></i>
                <h1>Pharma<span>Care</span></h1>
            </div>

            <div id="register-form" class="form-container">
                <h2>Créer un compte</h2>

                <aui:form action="${registerURL}" method="post" name="registerForm">
                    <div class="form-group">
                        <aui:input label="Nom complet *" name="fullname" type="text" placeholder="Votre nom complet" required="true" cssClass="aui-field-input"/>
                    </div>
                    <div class="form-group">
                        <aui:input label="Email *" name="email" type="email" placeholder="vous@exemple.com" required="true" cssClass="aui-field-input"/>
                    </div>
                    <div class="form-group">
                        <aui:input label="Mot de passe *" name="password" type="password" placeholder="Créez un mot de passe" required="true" cssClass="aui-field-input"/>
                    </div>
                    <div class="form-group">
                        <aui:input label="Confirmer le mot de passe *" name="confirmPassword" type="password" placeholder="Confirmez votre mot de passe" required="true" cssClass="aui-field-input"/>
                    </div>
                    <aui:button type="submit" value="S'inscrire" cssClass="btn" />
                </aui:form>

                <c:if test="${not empty errorMessage}">
                    <div class="error-message" id="register-error">${fn:escapeXml(errorMessage)}</div>
                </c:if>

                <c:if test="${not empty successMessage}">
                    <div class="success-message" id="register-success">${fn:escapeXml(successMessage)}</div>
                </c:if>

                <div class="switch-form">
                    <p>Vous avez déjà un compte ? <a href="${loginRenderURL}">Se connecter</a></p>
                </div>
            </div>
        </div>

        <!-- Right Section -->
        <div class="right-section">
            <div class="pharmacy-illustration"><i class="fas fa-user-plus"></i></div>
            <div class="illustration-text">
                <h3>Rejoignez PharmaCare</h3>
                <p>Créez votre compte pour accéder à tous nos services de gestion de pharmacie.</p>
            </div>
            <div class="features">
                <div class="feature"><i class="fas fa-lock"></i><span>Sécurité des données</span></div>
                <div class="feature"><i class="fas fa-bolt"></i><span>Processus rapide</span></div>
                <div class="feature"><i class="fas fa-headset"></i><span>Support 24/7</span></div>
                <div class="feature"><i class="fas fa-chart-line"></i><span>Analytiques avancées</span></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

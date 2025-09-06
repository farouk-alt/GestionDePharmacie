<%--
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        .pharmacy-auth-portlet {
            background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .pharmacy-auth-portlet .container {
            display: flex;
            width: 90%;
            max-width: 1200px;
            min-height: 600px;
            background: white;
            border-radius: 20px;
            overflow: hidden;
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.3);
        }

        .pharmacy-auth-portlet .left-section {
            flex: 1;
            padding: 50px;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }

        .pharmacy-auth-portlet .right-section {
            flex: 1;
            background: linear-gradient(to bottom right, #4e54c8, #8f94fb);
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 30px;
            color: white;
        }

        .pharmacy-auth-portlet .logo {
            display: flex;
            align-items: center;
            margin-bottom: 30px;
        }

        .pharmacy-auth-portlet .logo i {
            font-size: 32px;
            color: #4e54c8;
            margin-right: 10px;
        }

        .pharmacy-auth-portlet .logo h1 {
            font-size: 28px;
            color: #333;
        }

        .pharmacy-auth-portlet .form-container {
            width: 100%;
            max-width: 400px;
        }

        .pharmacy-auth-portlet h2 {
            color: #333;
            margin-bottom: 30px;
            font-size: 28px;
        }

        .pharmacy-auth-portlet .form-group {
            margin-bottom: 20px;
        }

        .pharmacy-auth-portlet label {
            display: block;
            margin-bottom: 8px;
            color: #555;
            font-weight: 500;
        }

        .pharmacy-auth-portlet input {
            width: 100%;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 10px;
            font-size: 16px;
            transition: all 0.3s;
        }

        .pharmacy-auth-portlet input:focus {
            outline: none;
            border-color: #4e54c8;
            box-shadow: 0 0 0 2px rgba(78, 84, 200, 0.2);
        }

        .pharmacy-auth-portlet .btn {
            width: 100%;
            padding: 15px;
            border: none;
            border-radius: 10px;
            background: #4e54c8;
            color: white;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            margin-top: 10px;
        }

        .pharmacy-auth-portlet .btn:hover {
            background: #3a3fb8;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .pharmacy-auth-portlet .switch-form {
            text-align: center;
            margin-top: 20px;
            color: #666;
        }

        .pharmacy-auth-portlet .switch-form a {
            color: #4e54c8;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s;
        }

        .pharmacy-auth-portlet .switch-form a:hover {
            color: #3a3fb8;
            text-decoration: underline;
        }

        .pharmacy-auth-portlet .error-message {
            color: #e74c3c;
            text-align: center;
            margin-top: 15px;
            padding: 10px;
            background: #ffeaea;
            border-radius: 8px;
            <c:if test="${empty errorMessage}">display: none;</c:if>
        }

        .pharmacy-auth-portlet .success-message {
            color: #27ae60;
            text-align: center;
            margin-top: 15px;
            padding: 10px;
            background: #eaffea;
            border-radius: 8px;
            <c:if test="${empty successMessage}">display: none;</c:if>
        }

        .pharmacy-auth-portlet .pharmacy-illustration {
            text-align: center;
            max-width: 80%;
        }

        .pharmacy-auth-portlet .pharmacy-illustration i {
            font-size: 180px;
            margin-bottom: 30px;
            color: rgba(255, 255, 255, 0.9);
        }

        .pharmacy-auth-portlet .illustration-text {
            text-align: center;
        }

        .pharmacy-auth-portlet .illustration-text h3 {
            font-size: 28px;
            margin-bottom: 20px;
            font-weight: 600;
        }

        .pharmacy-auth-portlet .illustration-text p {
            font-size: 18px;
            line-height: 1.6;
            max-width: 350px;
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
            padding: 12px 20px;
            border-radius: 10px;
            backdrop-filter: blur(10px);
            width: 45%;
        }

        .pharmacy-auth-portlet .feature i {
            font-size: 20px;
            margin-right: 10px;
        }

        @media (max-width: 900px) {
            .pharmacy-auth-portlet .container {
                flex-direction: column;
                max-width: 500px;
            }

            .pharmacy-auth-portlet .right-section {
                display: none;
            }

            .pharmacy-auth-portlet .left-section {
                padding: 30px;
            }
        }

        .session-info {
            position: absolute;
            top: 20px;
            right: 20px;
            background: white;
            padding: 10px 15px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            z-index: 100;
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
                <h1>Pharma<span style="color: #4e54c8;">Care</span></h1>
            </div>

            <!-- Register Form -->
            <div id="register-form" class="form-container">
                <h2>Créer un compte</h2>
                <portlet:actionURL name="register" var="registerURL" />
                <form action="${registerURL}" method="post">
                    <div class="form-group">
                        <label for="register-fullname">Nom complet</label>
                        <input type="text" id="register-fullname" name="fullname" placeholder="Votre nom complet" required>
                    </div>
                    <div class="form-group">
                        <label for="register-email">Email</label>
                        <input type="email" id="register-email" name="email" placeholder="votre@email.com" required>
                    </div>
                    <div class="form-group">
                        <label for="register-password">Mot de passe</label>
                        <input type="password" id="register-password" name="password" placeholder="Créez un mot de passe" required>
                    </div>
                    <div class="form-group">
                        <label for="register-confirm-password">Confirmer le mot de passe</label>
                        <input type="password" id="register-confirm-password" name="confirmPassword" placeholder="Confirmez votre mot de passe" required>
                    </div>
                    <button type="submit" class="btn">S'inscrire</button>
                </form>

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
                <i class="fas fa-clinic-medical"></i>
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
            margin: 0;
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
</html>
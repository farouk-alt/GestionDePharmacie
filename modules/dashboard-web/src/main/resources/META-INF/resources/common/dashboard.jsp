<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>



<portlet:defineObjects />
<%-- put this near the top, after <portlet:defineObjects/>, and definitely OUTSIDE any <c:choose> --%>
<liferay-portlet:resourceURL
        var="notifUnreadURL"
        id="unread"
        portletName="notification_web_NotificationWebPortlet_INSTANCE_NOTIF"
/>


<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Tableau de Bord - PharmaCare</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        :root{
            --primary:#1E3A8A;   /* Deep blue */
            --secondary:#3B82F6; /* Light blue */
            --accent:#10B981;    /* Green */
            --bg:#F3F4F6;        /* Light gray background */
            --white:#FFFFFF;
            --text:#111827;      /* Slate-900 */
            --muted:#6B7280;     /* Slate-500 */
            --border:#E5E7EB;    /* Slate-200 */
            --tab-bg:#EFF6FF;    /* very light blue */
            --icon-bg:#E0F2FE;   /* light blue tint */
        }

        body{background:var(--bg);margin:0;color:var(--text)}
        .dashboard-container{max-width:1200px;margin:0 auto;padding:20px;font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif}

        /* Header */
        .dashboard-header{
            background:linear-gradient(135deg,var(--primary),var(--secondary));
            color:#fff;padding:20px;border-radius:14px;margin-bottom:20px;
            display:flex;justify-content:space-between;align-items:center;box-shadow:0 10px 24px rgba(30,58,138,.25)
        }
        .user-info{display:flex;align-items:center;gap:15px}
        .user-avatar{
            width:60px;height:60px;border-radius:14px;
            background:rgba(255,255,255,.15);display:flex;align-items:center;justify-content:center;font-size:24px
        }

        /* Buttons */
        .btn{
            background:var(--white);color:var(--primary);border:1px solid rgba(255,255,255,.0);
            padding:10px 16px;border-radius:10px;cursor:pointer;font-weight:600;text-decoration:none;display:inline-block;transition:all .2s
        }
        .btn:hover{background:#F8FAFC;transform:translateY(-1px)}
        .btn-primary{background:var(--accent);color:#fff;border:1px solid var(--accent)}
        .btn-primary:hover{filter:brightness(.95);color:var(--primary)}
        .btn-danger{background:#DC2626;color:#fff;border:1px solid #DC2626}
        .btn-danger:hover{filter:brightness(.95)}

        /* Tabs */
        .tabs{display:flex;gap:10px;margin:14px 0 22px 0;flex-wrap:wrap}
        .tab{
            background:var(--tab-bg);color:var(--primary);padding:8px 12px;border-radius:10px;text-decoration:none;font-weight:600;
            border:1px solid var(--border)
        }
        .tab:hover{background:#DBEAFE}
        .tab.active{background:var(--primary);color:#fff;border-color:var(--primary)}

        /* Cards */
        .dashboard-cards{
            display:grid;grid-template-columns:repeat(auto-fill,minmax(300px,1fr));
            gap:20px;margin-bottom:30px
        }
        .card{
            background:var(--white);border-radius:12px;padding:20px;border:1px solid var(--border);
            box-shadow:0 10px 24px rgba(17,24,39,.06);transition:transform .2s
        }
        .card:hover{transform:translateY(-3px)}
        .card-header{display:flex;align-items:center;margin-bottom:12px}
        .card-icon{
            width:44px;height:44px;background:var(--icon-bg);border-radius:12px;display:flex;align-items:center;justify-content:center;margin-right:12px;color:var(--primary)
        }

        /* Admin sections */
        .admin-section{
            background:var(--white);border-left:4px solid var(--accent);padding:18px;border-radius:10px;border:1px solid var(--border)
        }
        .employee-management{
            margin-top:16px;background:var(--white);border-radius:12px;padding:18px;border:1px solid var(--border);
            box-shadow:0 6px 18px rgba(17,24,39,.05)
        }

        /* Tables */
        .employee-table{width:100%;border-collapse:collapse;margin-top:10px}
        .employee-table th,.employee-table td{padding:10px 12px;text-align:left;border-bottom:1px solid var(--border)}
        .employee-table th{background:#F8FAFC;font-weight:700;color:var(--primary)}
        .employee-table tr:hover td{background:#F9FAFB}
        .role-select{padding:8px;border-radius:8px;border:1px solid var(--border);width:100%}

        /* Messages & debug */
        .message{padding:10px;margin:10px 0;border-radius:8px;text-align:center;border:1px solid}
        .success{background:#ECFDF5;color:#065F46;border-color:#A7F3D0}
        .error{background:#FEF2F2;color:#991B1B;border-color:#FECACA}
        .debug-info{background:#F9FAFB;border:1px solid var(--border);padding:10px;margin:10px 0;border-radius:8px;font-family:monospace;font-size:12px;color:var(--muted)}

        /* Sortable headers + controls */
        th.sortable{cursor:pointer;user-select:none}
        th.sortable .arrow{font-size:12px;opacity:.6;margin-left:6px}
        .table-controls{display:flex;justify-content:space-between;gap:12px;align-items:center;margin-top:10px;margin-bottom:6px;flex-wrap:wrap}
        .table-controls input[type="search"]{flex:1;min-width:220px;padding:10px;border:1px solid var(--border);border-radius:10px;background:#fff}

        /* Pagination */
        .pager{display:flex;gap:8px;align-items:center;flex-wrap:wrap;margin-top:12px}
        .pager .btn{padding:6px 12px}
        .pager .btn.btn-primary{background:var(--secondary);border-color:var(--secondary)}
        .pager-info{color:var(--muted);font-size:12px;margin-left:auto}





        /* Toolbar above the table */
        .people-toolbar{display:flex;gap:10px;align-items:center;margin:10px 0 12px}
        .people-toolbar .spacer{flex:1}
        .role-filter{padding:10px;border:1px solid var(--border);border-radius:10px;background:#fff}

        /* Icon buttons (pencil / trash) */
        .icon-btn{
            display:inline-flex;align-items:center;justify-content:center;
            width:36px;height:36px;border-radius:10px;border:1px solid var(--border);
            background:#fff;cursor:pointer;transition:.15s; margin-right:6px;
        }
        .icon-btn:hover{background:#F8FAFC;transform:translateY(-1px)}
        .icon-btn--danger{border-color:#fecaca;background:#fee2e2}
        .icon-btn--danger:hover{filter:brightness(.98)}
        .icon-btn i{font-size:14px;color:var(--primary)}
        .icon-btn--danger i{color:#b91c1c}

        /* === Admins section: reuse meds look === */
        .adm-header{
            background:linear-gradient(135deg,var(--primary),var(--secondary));
            color:#fff;border-radius:14px;padding:18px 20px;margin-bottom:16px;
            display:flex;align-items:center;justify-content:space-between;
            box-shadow:0 10px 24px rgba(30,58,138,.25)
        }
        .adm-header h2{margin:0;font-size:20px}
        .adm-header .header-actions{display:flex;gap:8px;align-items:center}
        .adm-count{color:#E0E7FF;font-size:12px;margin-right:6px}
        .adm-card{background:var(--white);border:1px solid var(--border);
            border-radius:12px;padding:16px;box-shadow:0 6px 18px rgba(17,24,39,.06)}
        .adm-controls{display:flex;gap:12px;align-items:center;flex-wrap:wrap;margin-bottom:10px}
        .adm-controls input[type="search"]{flex:1;min-width:240px;padding:10px;border:1px solid var(--border);border-radius:10px;background:#fff}
        .adm-range{color:var(--muted);font-size:12px;margin-left:auto}

        /* table (same as meds) */
        #admTable{width:100%;border-collapse:collapse}
        #admTable thead th{background:#F8FAFC;color:var(--primary);padding:10px 12px;border-bottom:1px solid var(--border);font-weight:700;text-align:left}
        #admTable tbody td{padding:10px 12px}
        #admTable tbody tr{box-shadow: inset 0 -1px 0 var(--border)}
        #admTable tbody tr:last-child{box-shadow:none}
        th.sortable{cursor:pointer;user-select:none}
        th.sortable .arrow{font-size:12px;opacity:.6;margin-left:6px}

        /* action icons (match meds icon buttons) */
        .icon-btn{display:inline-flex;align-items:center;justify-content:center;
            width:36px;height:36px;border-radius:10px;border:1px solid var(--border);
            background:#fff;color:var(--primary);cursor:pointer;margin-right:8px;
            transition:.15s}
        .icon-btn:hover{background:#F8FAFC;transform:translateY(-1px)}
        .icon-btn.danger{background:#DC2626;border-color:#DC2626;color:#fff}
        .icon-btn:last-child{margin-right:0}

        /* modal grid (same as meds form) */
        .adm-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(240px,1fr));gap:12px}
        .adm-grid .fld{display:flex;flex-direction:column;gap:6px}
        .adm-grid input,.adm-grid select{width:100%;box-sizing:border-box;background:#fff;color:var(--text);border:1px solid var(--border);border-radius:10px;padding:10px}
        .adm-grid input:focus,.adm-grid select:focus{outline:none;border-color:var(--secondary);box-shadow:0 0 0 3px rgba(59,130,246,.15)}
        /* header + card mimic your Médicaments section */
        .adm-header{
            background:linear-gradient(135deg,var(--primary),var(--secondary));
            color:#fff;border-radius:14px;padding:18px 20px;margin-bottom:16px;
            display:flex;align-items:center;justify-content:space-between;
            box-shadow:0 10px 24px rgba(30,58,138,.25)
        }
        .adm-header h2{margin:0;font-size:20px}
        .adm-header .header-actions{display:flex;gap:8px;align-items:center}
        .adm-count{color:#E0E7FF;font-size:12px;margin-right:6px}

        .adm-card{background:var(--white);border:1px solid var(--border);
            border-radius:12px;padding:16px;box-shadow:0 6px 18px rgba(17,24,39,.06)}

        .adm-controls{display:flex;gap:12px;align-items:center;flex-wrap:wrap;margin-bottom:10px}
        .adm-controls input[type="search"]{flex:1;min-width:240px;padding:10px;border:1px solid var(--border);border-radius:10px;background:#fff}
        .adm-range{color:var(--muted);font-size:12px;margin-left:auto}

        /* table = same pattern as meds (single crisp row separator) */
        #admTable{width:100%;border-collapse:separate;border-spacing:0}
        #admTable thead th{background:#F8FAFC;color:var(--primary);padding:10px 12px;border-bottom:1px solid var(--border);font-weight:700;text-align:left}
        #admTable tbody td{padding:10px 12px}
        #admTable tbody tr{box-shadow: inset 0 -1px 0 var(--border)}
        #admTable tbody tr:last-child{box-shadow:none}
        th.sortable{cursor:pointer;user-select:none}
        th.sortable .arrow{font-size:12px;opacity:.6;margin-left:6px}

        /* pill icon buttons */
        .icon-btn{
            display:inline-flex;align-items:center;justify-content:center;
            width:36px;height:36px;border-radius:10px;border:1px solid var(--border);
            background:#fff;color:var(--primary);cursor:pointer;margin-right:8px;transition:.15s
        }
        .icon-btn:hover{background:#F8FAFC;transform:translateY(-1px)}

        /* RED pill with WHITE icon for delete */
        .icon-btn.danger{background:#DC2626;border-color:#DC2626;color:#fff}
        .icon-btn:last-child{margin-right:0}

        /* modal form grid (same as meds) */
        .adm-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(240px,1fr));gap:12px}
        .adm-grid .fld{display:flex;flex-direction:column;gap:6px}
        .adm-grid input,.adm-grid select{
            width:100%;box-sizing:border-box;background:#fff;color:var(--text);
            border:1px solid var(--border);border-radius:10px;padding:10px
        }
        .adm-grid input:focus,.adm-grid select:focus{
            outline:none;border-color:var(--secondary);box-shadow:0 0 0 3px rgba(59,130,246,.15)
        }
        /* make FA icons inherit the button color */
        .icon-btn i,
        .icon-btn svg { color: inherit !important; }

        /* red pill + white icon */
        .icon-btn.danger { background:#DC2626; border-color:#DC2626; color:#fff !important; }
        .icon-btn.danger i,
        .icon-btn.danger svg { color:#fff !important; }

        /* if you ever use duotone icons, force both layers to white */
        .icon-btn.danger .fa-duotone-icon {
            --fa-primary-color:#fff;
            --fa-secondary-color:#fff;
        }
        /* Wider role modal; still responsive on small screens */
        #<portlet:namespace/>roleModal .modal-dialog {
            max-width: 720px;                /* desktop */
            width: clamp(480px, 48vw, 720px);
        }

        #<portlet:namespace/>roleModal .modal-body select {
            width: 100%;
            min-width: 100%;
        }

        /* If you see the dropdown clipped in some themes, unclip overflow */
        #<portlet:namespace/>roleModal .modal { overflow: visible; }
        /* make the role modal comfortably tall */
        #<portlet:namespace/>roleModal .modal-dialog {
            max-width: 720px;                  /* keep the wider look */
            width: min(92vw, 720px);
        }

        #<portlet:namespace/>roleModal .modal-content {
            min-height: 420px;                 /* ⬅️ actual vertical growth */
        }

        /* give the body more usable height and allow scroll if it overflows */
        #<portlet:namespace/>roleModal .modal-body {
            max-height: calc(100vh - 180px);   /* more headroom than default */
            overflow: auto;                    /* scroll if needed */
        }

        /* keep the select full width */
        #<portlet:namespace/>roleModal .modal-body select {
            width: 100%;
            min-width: 100%;
        }

        /* OPTIONAL: if the native select dropdown ever gets clipped, unclip it */
        #<portlet:namespace/>roleModal .modal,
        #<portlet:namespace/>roleModal .modal-body {
            overflow: visible;                 /* use only if you see clipping */
        }
        #<portlet:namespace />roleModal .modal-content { min-height:420px; }
        #<portlet:namespace />roleModal .modal-body { max-height:calc(100vh - 180px); overflow:auto; }
        /* Taller role modal */
        #<portlet:namespace />roleModal .modal-dialog {
            /* optional: bring it down from the very top/bottom */
            margin-top: 6vh;
            margin-bottom: 6vh;
        }

        #<portlet:namespace />roleModal .modal-content {
            /* pick one of these heights */
            /* A) fixed minimum height */
            min-height: 560px;                      /* was 420px */
            /* B) or: viewport-based height (comment A if you use B) */
            /* height: min(72vh, 720px); */
        }

        #<portlet:namespace />roleModal .modal-body {
            /* give the body more usable vertical room and enable scroll */
            max-height: calc(100vh - 180px);
            /* if you used B) above, use this instead: */
            /* height: calc(100% - 110px); */       /* header+footer approx */
            overflow: auto;
        }

        /* make form controls comfy */
        #<portlet:namespace />roleModal .modal-body select,
        #<portlet:namespace />roleModal .modal-body .form-control {
            min-height: 44px;
        }

        /* if dropdowns get clipped in some themes */
        #<portlet:namespace />roleModal .modal,
        #<portlet:namespace />roleModal .modal-body {
            overflow: visible;
        }

        /* mobile: don’t force a huge height */
        @media (max-width: 640px) {
            #<portlet:namespace />roleModal .modal-content {
                min-height: unset;
                height: auto;
            }
        }
        /* Target by exact ID */
        /* Role modal: reasonable height */
        #<portlet:namespace />roleModal .modal-dialog {
            margin-top: 6vh;
            margin-bottom: 6vh;
            max-width: 720px;
            width: min(92vw, 720px);
        }

        #<portlet:namespace />roleModal .modal-content {
            min-height: 460px;           /* was 560; dialed down */
        }

        #<portlet:namespace />roleModal .modal-body {
            max-height: calc(100vh - 180px);
            overflow: auto;
        }
        <%--/* Modern select styling (closed state) */--%>
        <%--#<portlet:namespace />roleModal .select-wrap {--%>
        <%--    position: relative;--%>
        <%--}--%>

        #<portlet:namespace />roleModal .adm-select {
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
            width: 100%;
            border: 1px solid var(--border);
            border-radius: 10px;
            background: #fff;
            padding: 10px 40px 10px 12px;   /* space for chevron */
            font-size: 14px;
            line-height: 1.2;
            color: var(--text);
            box-shadow: inset 0 1px 2px rgba(0,0,0,.04);
        }

        #<portlet:namespace />roleModal .adm-select:focus {
            outline: none;
            border-color: var(--secondary);
            box-shadow: 0 0 0 3px rgba(59,130,246,.15);
        }

        <%--/* Custom chevron */--%>
        <%--#<portlet:namespace />roleModal .select-wrap::after {--%>
        <%--    content: "";--%>
        <%--    position: absolute;--%>
        <%--    right: 12px;--%>
        <%--    top: 50%;--%>
        <%--    width: 14px;--%>
        <%--    height: 14px;--%>
        <%--    transform: translateY(-50%);--%>
        <%--    pointer-events: none;--%>
        <%--    background: no-repeat center / contain--%>
        <%--    url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='%23566' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpolyline points='6 9 12 15 18 9'/%3E%3C/svg%3E");--%>
        <%--}--%>
        /* single set of modal rules — keep only one copy */
        #<portlet:namespace/>roleModal .modal-dialog{
            margin-top:6vh; margin-bottom:6vh;
            max-width:720px; width:min(92vw,720px);
        }
        #<portlet:namespace/>roleModal .modal-content{ min-height:460px; }
        #<portlet:namespace/>roleModal .modal-body{
            max-height:calc(100vh - 180px); overflow:auto;
        }

        /* modern select, but nothing that can block clicks */
        #<portlet:namespace/>roleModal .adm-select{
            -webkit-appearance:none; -moz-appearance:none; appearance:none;
            width:100%;
            border:1px solid var(--border);
            border-radius:10px;
            background:#fff;
            padding:10px 40px 10px 12px;      /* room for chevron */
            font-size:14px; line-height:1.2; color:var(--text);
            box-shadow:inset 0 1px 2px rgba(0,0,0,.04);

            /* draw the chevron on the element itself (no overlay element) */
            background-image:url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='%23566' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpolyline points='6 9 12 15 18 9'/%3E%3C/svg%3E");
            background-repeat:no-repeat;
            background-position:right 12px center;
            background-size:14px;
        }

        #<portlet:namespace/>roleModal .adm-select:focus{
            outline:none;
            border-color:var(--secondary);
            box-shadow:0 0 0 3px rgba(59,130,246,.15);
        }

        /* Label + hint spacing */
        #<portlet:namespace />roleModal .fld label {
            font-weight: 600;
            margin-bottom: 6px;
        }
        #<portlet:namespace />roleModal .fld .hint {
            color: var(--muted);
            font-size: 12px;
            margin-top: 6px;
        }


        /* Fallback if the id is slightly different (rare) */
        div[id$="roleModal"] .modal-content { min-height: 560px !important; }
        div[id$="roleModal"] .modal-body    { max-height: calc(100vh - 180px) !important; overflow: auto !important; }
        div[id$="roleModal"] .modal-dialog  { margin-top: 6vh; margin-bottom: 6vh; }
        /* === Add Admin modal — scoped by exact id === */
        #<portlet:namespace/>admAdd .modal-dialog{
            max-width: 760px;
            width: min(94vw, 760px);
            margin-top: 6vh;
            margin-bottom: 6vh;
        }
        #<portlet:namespace/>admAdd .modal-content{ min-height: 520px; }
        #<portlet:namespace/>admAdd .modal-body{
            max-height: calc(100vh - 180px);
            overflow: auto;
        }

        /* form layout */
        #<portlet:namespace/>admAdd .adm-form .row{
            display: grid;
            grid-template-columns: repeat(2, minmax(240px, 1fr));
            gap: 14px;
        }
        #<portlet:namespace/>admAdd .adm-form .fld{ display:flex; flex-direction:column; }
        #<portlet:namespace/>admAdd .adm-form label{ font-weight:600; margin-bottom:6px; }
        #<portlet:namespace/>admAdd .adm-form .hint{ color:var(--muted); font-size:12px; margin-top:6px; }

        /* inputs */
        #<portlet:namespace/>admAdd .form-control{
            width: 100%;
            border: 1px solid var(--border);
            border-radius: 10px;
            background: #fff;
            padding: 12px;
            font-size: 14px;
            line-height: 1.2;
            color: var(--text);
            box-shadow: inset 0 1px 2px rgba(0,0,0,.04);
        }
        #<portlet:namespace/>admAdd .form-control:focus{
            outline: none;
            border-color: var(--secondary);
            box-shadow: 0 0 0 3px rgba(59,130,246,.15);
        }

        /* password toggle */
        #<portlet:namespace/>admAdd .password-wrap{ position:relative; }
        #<portlet:namespace/>admAdd .pwd-toggle{
            position:absolute; right:8px; top:50%; transform:translateY(-50%);
            border:1px solid var(--border); background:#fff; cursor:pointer;
            width:36px; height:36px; border-radius:8px; display:flex; align-items:center; justify-content:center;
        }
        #<portlet:namespace/>admAdd .pwd-toggle:hover{ background:#F8FAFC; }

        /* responsive: single column on small screens */
        @media (max-width: 640px){
            #<portlet:namespace/>admAdd .adm-form .row{ grid-template-columns: 1fr; }
        }
        /* === Add Admin modal: make it taller/wider === */
        #<portlet:namespace/>admAdd .modal-dialog{
            max-width: 800px;                       /* a touch wider */
            width: min(96vw, 800px);
            margin-top: 6vh;
            margin-bottom: 6vh;
        }
        #<portlet:namespace/>admAdd .modal-content{
            min-height: 620px;                      /* ⬅️ taller */
        }
        #<portlet:namespace/>admAdd .modal-body{
            max-height: calc(100vh - 160px);        /* a bit more usable height */
            padding-bottom: 20px;                   /* keep content off the footer buttons */
        }

        /* === Form sizing & eye alignment (scoped to this modal only) === */
        #<portlet:namespace/>admAdd .adm-form .row{
            grid-template-columns: repeat(3, minmax(220px, 1fr)); /* 3 fields on first row */
            gap: 16px;
        }
        @media (max-width: 900px){
            #<portlet:namespace/>admAdd .adm-form .row{
                grid-template-columns: repeat(2, minmax(220px, 1fr));
            }
        }
        @media (max-width: 640px){
            #<portlet:namespace/>admAdd .adm-form .row{
                grid-template-columns: 1fr;
            }
        }

        /* Inputs: fixed height + extra right padding for the eye */
        #<portlet:namespace/>admAdd .form-control{
            height: 46px;                           /* consistent control height */
            padding: 10px 14px;
            font-size: 14px;
        }
        #<portlet:namespace/>admAdd .password-wrap .form-control{
            padding-right: 48px;                    /* room for the eye button */
        }

        /* Eye button perfectly centered inside the input */
        #<portlet:namespace/>admAdd .password-wrap{ position: relative; }
        #<portlet:namespace/>admAdd .pwd-toggle{
            position: absolute;
            right: 8px;
            top: 50%;
            transform: translateY(-50%);            /* true vertical centering */
            width: 36px;
            height: 36px;
            display: flex;
            align-items: center;
            justify-content: center;
            border: 1px solid var(--border);
            border-radius: 8px;
            background: #fff;
            cursor: pointer;
        }
        #<portlet:namespace/>admAdd .pwd-toggle:hover{ background:#F8FAFC; }
        /* --- Add Admin modal: size & spacing (final override) --- */
        #<portlet:namespace/>admAdd .modal-dialog{
            max-width: 880px !important;
            width: min(96vw, 880px) !important;
            margin-top: 6vh !important;
            margin-bottom: 6vh !important;
        }
        #<portlet:namespace/>admAdd .modal-content{
            min-height: 680px !important;          /* ⬅️ taller */
        }
        #<portlet:namespace/>admAdd .modal-body{
            max-height: calc(100vh - 140px) !important;
            padding-bottom: 24px !important;
        }
        /* Inputs: consistent height */
        #<portlet:namespace/>admAdd .form-control{
            height: 52px !important;
            padding: 12px 14px !important;
            line-height: normal !important;        /* avoid browser differences */
            box-sizing: border-box !important;
        }

        /* Extra right space for the eye */
        #<portlet:namespace/>admAdd .password-wrap .form-control{
            padding-right: 56px !important;
        }

        /* Eye button: absolute + centered */
        #<portlet:namespace/>admAdd .password-wrap{ position: relative !important; }
        #<portlet:namespace/>admAdd .pwd-toggle{
            position: absolute !important;
            top: 50% !important;
            right: 10px !important;
            transform: translateY(-50%) !important;
            width: 40px !important;
            height: 40px !important;
            display: flex !important;
            align-items: center !important;
            justify-content: center !important;
            border: 1px solid var(--border) !important;
            border-radius: 10px !important;
            background: #fff !important;
            cursor: pointer !important;
        }
        #<portlet:namespace/>admAdd .pwd-toggle:hover{ background:#F8FAFC !important; }
        /* --- Add Admin modal: polished layout & alignment (final) --- */
        #<portlet:namespace/>admAdd .modal-dialog{
            max-width: 880px !important;
            width: min(96vw, 880px) !important;
            margin-top: 6vh !important;
            margin-bottom: 6vh !important;
        }
        #<portlet:namespace/>admAdd .modal-content{ min-height: 640px !important; }
        #<portlet:namespace/>admAdd .modal-body{ max-height: calc(100vh - 140px) !important; }

        /* Grid: 3 columns on wide, 2 on medium, 1 on small */
        #<portlet:namespace/>admAdd .adm-form .row{
            display: grid;
            grid-template-columns: repeat(3, minmax(240px,1fr));
            gap: 16px;
            align-items: start;                   /* keep labels/inputs aligned */
        }
        @media (max-width: 1024px){
            #<portlet:namespace/>admAdd .adm-form .row{ grid-template-columns: repeat(2, minmax(220px,1fr)); }
        }
        @media (max-width: 640px){
            #<portlet:namespace/>admAdd .adm-form .row{ grid-template-columns: 1fr; }
        }

        /* Make email full-width and password span 2 cols for a nice rhythm */
        #<portlet:namespace/>admAdd #<portlet:namespace/>email{ width: 100%; }
        #<portlet:namespace/>admAdd .fld--email{ grid-column: 1 / -1; }
        #<portlet:namespace/>admAdd .fld--password{ grid-column: 1 / span 2; }
        @media (max-width: 1024px){
            #<portlet:namespace/>admAdd .fld--password{ grid-column: 1 / -1; }
        }

        /* Inputs: consistent visual height */
        #<portlet:namespace/>admAdd .form-control{
            height: 48px !important;
            padding: 10px 14px !important;
            line-height: normal !important;
            box-sizing: border-box !important;
        }

        /* Password wrap with extra right padding for the eye */
        #<portlet:namespace/>admAdd .password-wrap{ position: relative !important; }
        #<portlet:namespace/>admAdd .password-wrap .form-control{ padding-right: 52px !important; }

        /* Eye button: visible, centered, no layout shift */
        #<portlet:namespace/>admAdd .pwd-toggle{
            position: absolute !important;
            top: 50% !important;
            right: 8px !important;
            transform: translateY(-50%) !important;
            width: 36px !important; height: 36px !important;
            display: inline-flex !important; align-items: center !important; justify-content: center !important;
            border: 1px solid var(--border) !important;
            border-radius: 8px !important;
            background: #fff !important;
            cursor: pointer !important;
            padding: 0 !important;
        }
        #<portlet:namespace/>admAdd .pwd-toggle i,
        #<portlet:namespace/>admAdd .pwd-toggle svg{ pointer-events: none; font-size: 16px; }

        /* Spacings */
        #<portlet:namespace/>admAdd .adm-form label{ font-weight: 600; margin-bottom: 6px; }
        #<portlet:namespace/>admAdd .adm-form .hint{ color: var(--muted); font-size: 12px; margin-top: 6px; }
        /* ===== Add Admin modal: vertical, professional layout ===== */
        #<portlet:namespace/>admAdd .modal-dialog{
            max-width: 560px !important;         /* compact, readable form width */
            width: min(96vw, 560px) !important;
            margin-top: 6vh !important;
            margin-bottom: 6vh !important;
        }
        #<portlet:namespace/>admAdd .modal-content{ min-height: 0 !important; }

        /* Stack fields: one per row */
        #<portlet:namespace/>admAdd .adm-form .row{
            display: grid !important;
            grid-template-columns: 1fr !important;
            gap: 14px !important;
            align-items: start !important;
        }

        /* Each field spans full width; remove earlier multi-col rules */
        #<portlet:namespace/>admAdd .adm-form .fld{
            grid-column: 1 / -1 !important;
        }

        /* Inputs: consistent height + clear focus */
        #<portlet:namespace/>admAdd .form-control{
            height: 48px !important;
            padding: 10px 14px !important;
            font-size: 14px !important;
            border: 1px solid var(--border) !important;
            border-radius: 10px !important;
            box-shadow: inset 0 1px 2px rgba(0,0,0,.04) !important;
        }
        #<portlet:namespace/>admAdd .form-control:focus{
            outline: none !important;
            border-color: var(--secondary) !important;
            box-shadow: 0 0 0 3px rgba(59,130,246,.15) !important;
        }

        /* Labels & hints */
        #<portlet:namespace/>admAdd .adm-form label{ font-weight:600; margin-bottom:6px; }
        #<portlet:namespace/>admAdd .adm-form .hint{ color:var(--muted); font-size:12px; margin-top:6px; }

        /* Password with eye: aligned and visible */
        #<portlet:namespace/>admAdd .password-wrap{ position: relative !important; }
        #<portlet:namespace/>admAdd .password-wrap .form-control{ padding-right: 52px !important; }
        #<portlet:namespace/>admAdd .pwd-toggle{
            position: absolute !important;
            top: 50% !important; right: 8px !important; transform: translateY(-50%) !important;
            width: 36px !important; height: 36px !important;
            display: inline-flex !important; align-items: center !important; justify-content: center !important;
            border: 1px solid var(--border) !important; border-radius: 8px !important;
            background: #fff !important; cursor: pointer !important; padding: 0 !important;
        }
        #<portlet:namespace/>admAdd .pwd-toggle i{ font-size:16px; pointer-events:none; }
        /* Password toggle — solid, centered icon; no font dependency */
        #<portlet:namespace/>admAdd .password-wrap{ position:relative !important; }
        #<portlet:namespace/>admAdd .password-wrap .form-control{
            padding-right:56px !important;
        }

        #<portlet:namespace/>admAdd .pwd-toggle{
            position:absolute !important;
            top:50% !important; right:10px !important; transform:translateY(-50%) !important;
            width:40px !important; height:40px !important;
            display:inline-flex !important; align-items:center !important; justify-content:center !important;
            border:1px solid var(--border) !important; border-radius:10px !important;
            background:#fff !important; cursor:pointer !important;
        }
        #<portlet:namespace/>admAdd .pwd-toggle:hover{ background:#F8FAFC !important; }
        #<portlet:namespace/>admAdd .pwd-toggle svg{ width:20px; height:20px; }

        /* Show one of the two SVGs based on aria-pressed */
        #<portlet:namespace/>admAdd .pwd-toggle[aria-pressed="false"] .ic-eye{ display:block; }
        #<portlet:namespace/>admAdd .pwd-toggle[aria-pressed="false"] .ic-eye-off{ display:none; }
        #<portlet:namespace/>admAdd .pwd-toggle[aria-pressed="true"]  .ic-eye{ display:none; }
        #<portlet:namespace/>admAdd .pwd-toggle[aria-pressed="true"]  .ic-eye-off{ display:block; }


    </style>
    <style>
        /* Admin Dashboard Styles */
        .admin-dashboard {
            background: var(--bg);
            padding: 0;
        }

        /* Header with Stats */
        .admin-header-card {
            background: linear-gradient(135deg, #1E3A8A 0%, #3B82F6 100%);
            border-radius: 16px;
            padding: 32px;
            margin-bottom: 24px;
            color: white;
            box-shadow: 0 10px 30px rgba(30, 58, 138, 0.3);
        }

        .admin-header-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 24px;
            flex-wrap: wrap;
        }

        .admin-header-left h2 {
            margin: 0 0 8px;
            font-size: 28px;
            font-weight: 700;
        }

        .admin-header-left p {
            margin: 0;
            opacity: 0.9;
            font-size: 14px;
        }

        .admin-stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
            gap: 16px;
            flex: 1;
        }

        .stat-box {
            background: rgba(255, 255, 255, 0.15);
            backdrop-filter: blur(10px);
            border-radius: 12px;
            padding: 16px;
            text-align: center;
        }

        .stat-number {
            font-size: 32px;
            font-weight: 700;
            line-height: 1;
            margin-bottom: 6px;
        }

        .stat-label {
            font-size: 11px;
            opacity: 0.9;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .stat-change {
            font-size: 12px;
            margin-top: 4px;
            opacity: 0.8;
        }

        .stat-change.up { color: #10B981; }
        .stat-change.down { color: #EF4444; }

        /* Control Panel */
        .admin-controls {
            background: white;
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        }

        .controls-row {
            display: flex;
            gap: 12px;
            align-items: center;
            flex-wrap: wrap;
            margin-bottom: 16px;
        }

        .controls-row:last-child {
            margin-bottom: 0;
        }

        .search-wrapper {
            position: relative;
            flex: 1;
            min-width: 280px;
        }

        .search-wrapper input {
            width: 100%;
            padding: 12px 40px 12px 40px;
            border: 2px solid var(--border);
            border-radius: 10px;
            font-size: 14px;
            transition: all 0.2s;
        }

        .search-wrapper input:focus {
            outline: none;
            border-color: var(--secondary);
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
        }

        .search-icon {
            position: absolute;
            left: 12px;
            top: 50%;
            transform: translateY(-50%);
            color: var(--muted);
        }

        .filter-group {
            display: flex;
            gap: 8px;
            align-items: center;
        }

        .filter-select {
            padding: 10px 36px 10px 14px;
            border: 2px solid var(--border);
            border-radius: 10px;
            font-size: 14px;
            background: white;
            cursor: pointer;
            min-width: 150px;
        }

        .filter-select:focus {
            outline: none;
            border-color: var(--secondary);
        }

        .btn-group {
            display: flex;
            gap: 8px;
        }

        .btn-icon {
            padding: 10px 16px;
            border: 2px solid var(--border);
            border-radius: 10px;
            background: white;
            cursor: pointer;
            transition: all 0.2s;
            display: flex;
            align-items: center;
            gap: 6px;
            font-weight: 600;
        }

        .btn-icon:hover {
            background: var(--hover);
            border-color: var(--secondary);
        }

        .btn-icon.active {
            background: var(--secondary);
            color: white;
            border-color: var(--secondary);
        }

        /* Table Card */
        .admin-table-card {
            background: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        }

        .admin-table {
            width: 100%;
            border-collapse: collapse;
        }

        .admin-table thead th {
            background: #F8FAFC;
            color: var(--text);
            text-align: left;
            padding: 16px;
            font-weight: 700;
            font-size: 12px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            border-bottom: 2px solid var(--border);
            white-space: nowrap;
        }

        .admin-table thead th.sortable {
            cursor: pointer;
            user-select: none;
        }

        .admin-table thead th.sortable:hover {
            background: #EFF6FF;
        }

        .admin-table thead th .sort-icon {
            margin-left: 6px;
            opacity: 0.4;
            font-size: 10px;
        }

        .admin-table tbody td {
            padding: 16px;
            border-bottom: 1px solid var(--border);
            vertical-align: middle;
        }

        .admin-table tbody tr {
            transition: all 0.2s;
        }

        .admin-table tbody tr:hover {
            background: var(--hover);
        }

        /* User Cell */
        .user-cell {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .user-avatar-box {
            width: 44px;
            height: 44px;
            border-radius: 10px;
            background: linear-gradient(135deg, var(--secondary), var(--accent));
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: 700;
            font-size: 16px;
            flex-shrink: 0;
            text-transform: uppercase;
        }

        .user-info-box {
            min-width: 0;
        }

        .user-name {
            font-weight: 600;
            font-size: 14px;
            color: var(--text);
            margin-bottom: 2px;
        }

        .user-email {
            font-size: 12px;
            color: var(--muted);
        }

        /* Status Badge */
        .status-badge {
            display: inline-flex;
            align-items: center;
            gap: 6px;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            white-space: nowrap;
        }

        .status-badge.active {
            background: #D1FAE5;
            color: #065F46;
        }

        .status-badge.inactive {
            background: #FEE2E2;
            color: #991B1B;
        }

        .status-badge.suspended {
            background: #FEF3C7;
            color: #78350F;
        }

        .status-dot {
            width: 8px;
            height: 8px;
            border-radius: 50%;
            background: currentColor;
        }

        /* Role Badge */
        .role-badge {
            display: inline-flex;
            align-items: center;
            padding: 6px 12px;
            border-radius: 8px;
            font-size: 11px;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .role-badge.super-admin {
            background: #FEE2E2;
            color: #991B1B;
        }

        .role-badge.admin {
            background: #FEF3C7;
            color: #78350F;
        }

        .role-badge.pharmacien {
            background: #D1FAE5;
            color: #065F46;
        }

        .role-badge.fournisseur {
            background: #DBEAFE;
            color: #1E3A8A;
        }

        /* Activity Indicator */
        .activity-indicator {
            display: flex;
            align-items: center;
            gap: 8px;
            font-size: 13px;
        }

        .activity-dot {
            width: 10px;
            height: 10px;
            border-radius: 50%;
            animation: pulse 2s infinite;
        }

        .activity-dot.online { background: #10B981; }
        .activity-dot.away { background: #F59E0B; }
        .activity-dot.offline { background: #6B7280; }

        @keyframes pulse {
            0%, 100% { opacity: 1; }
            50% { opacity: 0.5; }
        }

        /* Action Buttons */
        .action-buttons {
            display: flex;
            gap: 6px;
        }

        .action-btn {
            width: 36px;
            height: 36px;
            border-radius: 8px;
            border: 1px solid var(--border);
            background: white;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            transition: all 0.2s;
        }

        .action-btn:hover {
            background: var(--hover);
            transform: translateY(-1px);
        }

        .action-btn.view {
            color: var(--secondary);
        }

        .action-btn.message {
            color: #10B981;
        }

        .action-btn.suspend {
            color: #F59E0B;
        }

        .action-btn.activate {
            color: #10B981;
        }

        /* Pagination */
        .admin-pagination {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px;
            border-top: 1px solid var(--border);
        }

        .pagination-info {
            font-size: 14px;
            color: var(--muted);
        }

        .pagination-controls {
            display: flex;
            gap: 6px;
        }

        .page-btn {
            padding: 8px 14px;
            border: 1px solid var(--border);
            background: white;
            border-radius: 8px;
            cursor: pointer;
            font-size: 14px;
            transition: all 0.2s;
            font-weight: 600;
        }

        .page-btn:hover {
            background: var(--hover);
        }

        .page-btn.active {
            background: var(--secondary);
            color: white;
            border-color: var(--secondary);
        }

        .page-btn:disabled {
            opacity: 0.5;
            cursor: not-allowed;
        }

        /* Empty State */
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: var(--muted);
        }

        .empty-icon {
            font-size: 48px;
            margin-bottom: 16px;
            opacity: 0.5;
        }

        .empty-title {
            font-size: 18px;
            font-weight: 600;
            color: var(--text);
            margin-bottom: 8px;
        }

        /* Responsive */
        @media (max-width: 1024px) {
            .admin-header-content {
                flex-direction: column;
                align-items: stretch;
            }

            .admin-stats-grid {
                grid-template-columns: repeat(2, 1fr);
            }

            .controls-row {
                flex-direction: column;
                align-items: stretch;
            }

            .search-wrapper {
                min-width: 100%;
            }
        }

        @media (max-width: 768px) {
            .admin-table thead th:nth-child(4),
            .admin-table thead th:nth-child(5),
            .admin-table tbody td:nth-child(4),
            .admin-table tbody td:nth-child(5) {
                display: none;
            }
        }
    </style>


</head>
<body>
<div class="dashboard-container">

    <!-- Header -->
    <portlet:actionURL var="logoutURL" name="logout" />
    <div class="dashboard-header">
        <div class="user-info">
            <div class="user-avatar"><i class="fas fa-user-md"></i></div>
            <div>
                <h2 style="margin:0;" class="b">
                    Bonjour, ${empty userEmail ? 'Utilisateur' : userEmail}!
                </h2>
                <p style="opacity:.9;margin:.25rem 0 0 0;">
                    Rôle: ${empty userRole ? '-' : userRole}
                </p>
            </div>
        </div>
        <a href="${logoutURL}" class="btn"><i class="fas fa-sign-out-alt"></i> Se déconnecter</a>
    </div>

    <!-- Resolve section -->
    <c:set var="currentSection" value="${empty requestScope.section ? param.section : requestScope.section}" />
    <c:if test="${empty currentSection}">
        <c:set var="currentSection" value="overview"/>
    </c:if>

    <!-- Tabs -->
    <portlet:renderURL var="tabOverview"><portlet:param name="mvcPath" value="/common/dashboard.jsp"/><portlet:param name="section" value="overview"/></portlet:renderURL>
    <portlet:renderURL var="tabAdmins"><portlet:param name="mvcPath" value="/common/dashboard.jsp"/><portlet:param name="section" value="admins"/></portlet:renderURL>
    <%--
        <portlet:renderURL var="tabUsers"><portlet:param name="mvcPath" value="/common/dashboard.jsp"/><portlet:param name="section" value="users"/></portlet:renderURL>
    --%>
    <portlet:renderURL var="tabLogs"><portlet:param name="mvcPath" value="/common/dashboard.jsp"/><portlet:param name="section" value="logs"/></portlet:renderURL>
    <portlet:renderURL var="tabStats"><portlet:param name="mvcPath" value="/common/dashboard.jsp"/><portlet:param name="section" value="stats"/></portlet:renderURL>
    <portlet:renderURL var="tabSecurity"><portlet:param name="mvcPath" value="/common/dashboard.jsp"/><portlet:param name="section" value="security"/></portlet:renderURL>
    <!-- add this alongside your other tab URLs -->
    <portlet:renderURL var="tabMedicaments">
        <portlet:param name="mvcPath" value="/common/dashboard.jsp"/>
        <portlet:param name="section" value="medicaments"/>
    </portlet:renderURL>
    <portlet:renderURL var="tabCommandes">
        <portlet:param name="mvcPath" value="/common/dashboard.jsp"/>
        <portlet:param name="section" value="commandes"/>
    </portlet:renderURL>
    <portlet:renderURL var="tabStocks">
        <portlet:param name="mvcPath" value="/common/dashboard.jsp"/>
        <portlet:param name="section" value="stocks"/>
    </portlet:renderURL>
    <portlet:renderURL var="tabVentes">
        <portlet:param name="mvcPath" value="/common/dashboard.jsp"/>
        <portlet:param name="section" value="ventes"/>
    </portlet:renderURL>
    <portlet:renderURL var="tabNotifications">
        <portlet:param name="mvcPath" value="/common/dashboard.jsp"/>
        <portlet:param name="section" value="notifications"/>
    </portlet:renderURL>





    <div class="tabs">
        <a class="tab ${currentSection=='overview' ? 'active' : ''}" href="${tabOverview}">Aperçu</a>
        <c:if test="${userRole == 'ADMIN' || userRole == 'SUPER_ADMIN'}">
            <a class="tab ${currentSection=='admins' ? 'active' : ''}" href="${tabAdmins}">Utilisateurs</a>
            <a class="tab ${currentSection=='logs' ? 'active' : ''}" href="${tabLogs}">Logs</a>
            <a class="tab ${currentSection=='security' ? 'active' : ''}" href="${tabSecurity}">Sécurité</a>
        </c:if>

        <%--
                <a class="tab ${currentSection=='users'    ? 'active' : ''}" href="${tabUsers}">Employés</a>
        --%>
        <a class="tab ${currentSection=='stats'    ? 'active' : ''}" href="${tabStats}">Statistiques</a>
        <c:if test="${userRole == 'ADMIN'}">
            <a class="tab ${currentSection=='medicaments' ? 'active' : ''}" href="${tabMedicaments}">Médicaments</a>
        </c:if>
        <c:if test="${userRole == 'ADMIN' || userRole == 'PHARMACIEN' || userRole == 'FOURNISSEUR'}">
            <a class="tab ${currentSection=='commandes' ? 'active' : ''}" href="${tabCommandes}">Commandes</a>
        </c:if>
        <c:if test="${userRole == 'ADMIN' || userRole == 'PHARMACIEN'}">
            <a class="tab ${currentSection=='stocks' ? 'active' : ''}" href="${tabStocks}">Stocks</a>
        </c:if>
        <c:if test="${userRole == 'ADMIN' || userRole == 'PHARMACIEN'}">
            <a class="tab ${currentSection=='ventes' ? 'active' : ''}" href="${tabVentes}">Ventes</a>
        </c:if>
        <a class="tab ${currentSection=='notifications' ? 'active' : ''}" href="${tabNotifications}">
            Notifications
            <span id="notifBadge"
                  style="margin-left:8px; padding:2px 8px; border-radius:999px; background:#ef4444; color:#fff; font-size:12px; display:none;">
        0
    </span>
        </a>




    </div>

    <!-- Flash messages -->
    <c:if test="${not empty param.successMsg}">
        <div class="message success">${fn:escapeXml(param.successMsg)}</div>
    </c:if>
    <c:if test="${not empty param.errorMsg}">
        <div class="message error">${fn:escapeXml(param.errorMsg)}</div>
    </c:if>



    <%-- base for includes --%>
    <c:set var="fragBase" value="/common" />

    <c:choose>
        <%-- Overview --%>
        <c:when test="${currentSection == 'overview'}">
            <div class="dashboard-cards">

                <!-- Médicaments (ADMIN only) -->
                <c:if test="${userRole == 'ADMIN'}">
                    <div class="card">
                        <div class="card-header">
                            <div class="card-icon"><i class="fas fa-pills"></i></div>
                            <h3 style="margin:0;">Gestion des Médicaments</h3>
                        </div>
                        <p>Consulter et gérer votre inventaire de médicaments.</p>
                        <a href="${tabMedicaments}" class="btn btn-primary">Ouvrir les Médicaments</a>
                    </div>
                </c:if>

                <!-- Commandes (ADMIN / PHARMACIEN / FOURNISSEUR) -->
                <c:if test="${userRole == 'ADMIN' || userRole == 'PHARMACIEN' || userRole == 'FOURNISSEUR'}">
                    <div class="card">
                        <div class="card-header">
                            <div class="card-icon"><i class="fas fa-shopping-cart"></i></div>
                            <h3 style="margin:0;">Commandes</h3>
                        </div>
                        <p>Créer et suivre les commandes fournisseurs et clients.</p>
                        <a href="${tabCommandes}" class="btn btn-primary">Aller aux Commandes</a>
                    </div>
                </c:if>

                <!-- Stocks (ADMIN / PHARMACIEN) -->
                <c:if test="${userRole == 'ADMIN' || userRole == 'PHARMACIEN'}">
                    <div class="card">
                        <div class="card-header">
                            <div class="card-icon"><i class="fas fa-boxes-stacked"></i></div>
                            <h3 style="margin:0;">Stocks</h3>
                        </div>
                        <p>Suivre les niveaux de stock et mouvements.</p>
                        <a href="${tabStocks}" class="btn btn-primary">Gérer les Stocks</a>
                    </div>
                </c:if>

                <!-- Ventes (ADMIN / PHARMACIEN) -->
                <c:if test="${userRole == 'ADMIN' || userRole == 'PHARMACIEN'}">
                    <div class="card">
                        <div class="card-header">
                            <div class="card-icon"><i class="fas fa-cash-register"></i></div>
                            <h3 style="margin:0;">Ventes</h3>
                        </div>
                        <p>Enregistrer et consulter les ventes.</p>
                        <a href="${tabVentes}" class="btn btn-primary">Ouvrir Ventes</a>
                    </div>
                </c:if>

                <!-- Notifications (badge syncs with tab) -->
                <div class="card">
                    <div class="card-header" style="position:relative;">
                        <div class="card-icon"><i class="fas fa-bell"></i></div>
                        <h3 style="margin:0;">Notifications</h3>
                        <span id="notifBadgeCard"
                              style="position:absolute; right:8px; top:8px; min-width:26px; height:26px; padding:0 8px;
                         display:none; border-radius:999px; background:#ef4444; color:#fff;
                         font-weight:700; font-size:13px; line-height:26px; text-align:center;">
              0
            </span>
                    </div>
                    <p>Consulter vos notifications et alertes.</p>
                    <a href="${tabNotifications}" class="btn btn-primary">Voir Notifications</a>
                </div>

                <!-- Logs -->
                <div class="card">
                    <div class="card-header">
                        <div class="card-icon"><i class="fas fa-clipboard-list"></i></div>
                        <h3 style="margin:0;">Logs</h3>
                    </div>
                    <p>Consulter l’historique des connexions et actions.</p>
                    <a href="${tabLogs}" class="btn btn-primary">Voir Logs</a>
                </div>

                <!-- Statistiques -->
                <div class="card">
                    <div class="card-header">
                        <div class="card-icon"><i class="fas fa-chart-pie"></i></div>
                        <h3 style="margin:0;">Statistiques</h3>
                    </div>
                    <p>Visualiser les statistiques globales de l’application.</p>
                    <a href="${tabStats}" class="btn btn-primary">Voir Statistiques</a>
                </div>

                <!-- Sécurité -->
                <div class="card">
                    <div class="card-header">
                        <div class="card-icon"><i class="fas fa-lock"></i></div>
                        <h3 style="margin:0;">Sécurité</h3>
                    </div>
                    <p>Gérer les paramètres de sécurité et les autorisations.</p>
                    <a href="${tabSecurity}" class="btn btn-primary">Paramètres Sécurité</a>
                </div>

                    <%-- Removed (no features yet)
                    <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-prescription"></i></div><h3 style="margin:0;">Ordonnances</h3></div><p>…</p></div>
                    <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-users"></i></div><h3 style="margin:0;">Clients</h3></div><p>…</p></div>
                    <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-chart-line"></i></div><h3 style="margin:0;">Rapports</h3></div><p>…</p></div>
                    --%>
            </div>

        </c:when>

        <%-- Admins management --%>
        <%-- Replace the admin section in your dashboard.jsp with this --%>

        <c:when test="${currentSection == 'admins'}">
            <c:if test="${userRole == 'SUPER_ADMIN' || userRole == 'ADMIN'}">

                <portlet:actionURL name="deleteUser" var="deleteUserURL"/>

                <div class="admin-dashboard">
                    <!-- Enhanced Header with More Stats -->
                    <div class="admin-header-card">
                        <div class="admin-header-content">
                            <div class="admin-header-left">
                                <h2><i class="fas fa-users-cog"></i> Gestion des Utilisateurs</h2>
                                <p>Surveillez et gérez tous les utilisateurs du système</p>
                            </div>
                            <div class="admin-stats-grid" style="grid-template-columns: repeat(auto-fit, minmax(120px, 1fr)); gap: 12px;">
                                <div class="stat-box">
                                    <div class="stat-number" id="totalUsersCount">0</div>
                                    <div class="stat-label">Total</div>
                                </div>
                                <div class="stat-box">
                                    <div class="stat-number" id="adminUsersCount">0</div>
                                    <div class="stat-label">Admins</div>
                                </div>
                                <div class="stat-box">
                                    <div class="stat-number" id="pharmacienCount">0</div>
                                    <div class="stat-label">Pharmaciens</div>
                                </div>
                                <div class="stat-box">
                                    <div class="stat-number" id="fournisseurCount">0</div>
                                    <div class="stat-label">Fournisseurs</div>
                                </div>
                                <div class="stat-box">
                                    <div class="stat-number" id="newUsersCount">0</div>
                                    <div class="stat-label">Ce mois</div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Enhanced Control Panel -->
                    <div class="admin-controls">
                        <div class="controls-row">
                            <div class="search-wrapper">
                                <i class="fas fa-search search-icon"></i>
                                <input type="search" id="adminSearch" placeholder="Rechercher par nom, email ou rôle...">
                            </div>
                            <div class="btn-group">
                                <button class="btn-icon" id="exportBtn" title="Exporter en CSV">
                                    <i class="fas fa-download"></i>
                                    Exporter
                                </button>
                                <button class="btn-icon" id="refreshBtn" title="Actualiser">
                                    <i class="fas fa-sync-alt"></i>
                                </button>
                            </div>
                        </div>

                        <div class="controls-row">
                            <div class="filter-group">
                                <!-- Role Filter -->
                                <select id="roleFilter" class="filter-select">
                                    <option value="">Tous les rôles</option>
                                    <option value="SUPER_ADMIN">Super Admin</option>
                                    <option value="ADMIN">Admin</option>
                                    <option value="PHARMACIEN">Pharmacien</option>
                                    <option value="FOURNISSEUR">Fournisseur</option>
                                </select>

                                <!-- Date Filter -->
                                <select id="dateFilter" class="filter-select">
                                    <option value="">Toutes les dates</option>
                                    <option value="today">Aujourd'hui</option>
                                    <option value="week">Cette semaine</option>
                                    <option value="month">Ce mois</option>
                                    <option value="year">Cette année</option>
                                </select>

                                <!-- Sort Order -->
                                <select id="sortOrder" class="filter-select">
                                    <option value="name-asc">Nom (A-Z)</option>
                                    <option value="name-desc">Nom (Z-A)</option>
                                    <option value="date-new">Plus récent</option>
                                    <option value="date-old">Plus ancien</option>
                                    <option value="email-asc">Email (A-Z)</option>
                                </select>
                            </div>

                            <button class="btn-icon" id="resetFilters">
                                <i class="fas fa-redo"></i>
                                Réinitialiser
                            </button>
                        </div>

                        <!-- Active Filters Display -->
                        <div id="activeFilters" style="display:none; margin-top:12px; display:flex; gap:8px; flex-wrap:wrap;"></div>
                    </div>

                    <!-- Table -->
                    <div class="admin-table-card">
                        <table class="admin-table">
                            <thead>
                            <tr>
                                <th class="sortable" data-sort="name">
                                    Utilisateur <span class="sort-icon">↕</span>
                                </th>
                                <th class="sortable" data-sort="role">
                                    Rôle <span class="sort-icon">↕</span>
                                </th>
                                <th class="sortable" data-sort="created">
                                    Créé le <span class="sort-icon">↕</span>
                                </th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody id="adminTableBody">
                            <c:forEach var="u" items="${employees}">
                                <tr data-name="${fn:escapeXml(u.prenom)} ${fn:escapeXml(u.nom)}"
                                    data-email="${fn:escapeXml(u.email)}"
                                    data-role="${fn:escapeXml(u.role)}"
                                    data-created="${u.dateCreation.time}">

                                    <td>
                                        <div class="user-cell">
                                            <div class="user-avatar-box">
                                                    ${!empty u.prenom ? fn:toUpperCase(fn:substring(u.prenom,0,1)) : '?'}${!empty u.nom ? fn:toUpperCase(fn:substring(u.nom,0,1)) : ''}
                                            </div>
                                            <div class="user-info-box">
                                                <div class="user-name">${fn:escapeXml(u.prenom)} ${fn:escapeXml(u.nom)}</div>
                                                <div class="user-email">${fn:escapeXml(u.email)}</div>
                                            </div>
                                        </div>
                                    </td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${u.role == 'SUPER_ADMIN'}">
                                                <span class="role-badge super-admin">Super Admin</span>
                                            </c:when>
                                            <c:when test="${u.role == 'ADMIN'}">
                                                <span class="role-badge admin">Admin</span>
                                            </c:when>
                                            <c:when test="${u.role == 'PHARMACIEN'}">
                                                <span class="role-badge pharmacien">Pharmacien</span>
                                            </c:when>
                                            <c:when test="${u.role == 'FOURNISSEUR'}">
                                                <span class="role-badge fournisseur">Fournisseur</span>
                                            </c:when>
                                        </c:choose>
                                    </td>

                                    <td data-sort="${u.dateCreation.time}">
                                        <fmt:formatDate value="${u.dateCreation}" pattern="dd/MM/yyyy"/>
                                    </td>

                                    <td>
                                        <div class="action-buttons">
                                            <c:if test="${u.role != 'ADMIN' && u.role != 'SUPER_ADMIN'}">
                                                <button class="action-btn view" title="Consulter"
                                                        onclick="consultUser('${fn:escapeXml(u.email)}', '${fn:escapeXml(u.prenom)}', '${fn:escapeXml(u.nom)}', '${fn:escapeXml(u.role)}', '${u.dateCreation.time}')">
                                                    <i class="fas fa-eye"></i>
                                                </button>
                                            </c:if>

                                            <c:if test="${u.role != 'SUPER_ADMIN'}">
                                                <form action="${deleteUserURL}" method="post" style="display:inline"
                                                      onsubmit="return confirm('Supprimer ${fn:escapeXml(u.email)} ?');">
                                                    <input type="hidden" name="<portlet:namespace/>email" value="${fn:escapeXml(u.email)}"/>
                                                    <button type="submit" class="action-btn" style="color:#EF4444;" title="Supprimer">
                                                        <i class="fa-solid fa-trash-can"></i>
                                                    </button>
                                                </form>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <div id="emptyState" class="empty-state" style="display:none;">
                            <div class="empty-icon"><i class="fas fa-users-slash"></i></div>
                            <div class="empty-title">Aucun utilisateur trouvé</div>
                            <p>Essayez de modifier vos critères de recherche</p>
                        </div>

                        <div class="admin-pagination">
                            <div class="pagination-info" id="paginationInfo">
                                Affichage 1-5 sur 0
                            </div>
                            <div class="pagination-controls" id="paginationControls"></div>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:when>


        <%-- Logs --%>
        <c:when test="${currentSection == 'logs'}">
            <c:catch var="incErr">
                <liferay-util:include page="${fragBase}/logs.jsp" servletContext="<%= application %>" />
            </c:catch>
            <c:if test="${not empty incErr}">
                <div class="message error">Impossible d’inclure ${fragBase}/logs.jsp. Vérifiez le chemin et les erreurs de compilation.</div>
                <pre class="debug-info">${fn:escapeXml(incErr)}</pre>
            </c:if>
        </c:when>

        <%-- Stats --%>
        <c:when test="${currentSection == 'stats'}">
            <c:catch var="incErr">
                <liferay-util:include page="${fragBase}/stats.jsp" servletContext="<%= application %>" />
            </c:catch>
            <c:if test="${not empty incErr}">
                <div class="message error">Impossible d’inclure ${fragBase}/stats.jsp. Vérifiez le chemin et les erreurs de compilation.</div>
                <pre class="debug-info">${fn:escapeXml(incErr)}</pre>
            </c:if>
        </c:when>

        <%-- Security --%>
        <c:when test="${currentSection == 'security'}">
            <c:catch var="incErr">
                <liferay-util:include page="${fragBase}/security.jsp" servletContext="<%= application %>" />
            </c:catch>
            <c:if test="${not empty incErr}">
                <div class="message error">Impossible d’inclure ${fragBase}/security.jsp. Vérifiez le chemin et les erreurs de compilation.</div>
                <pre class="debug-info">${fn:escapeXml(incErr)}</pre>
            </c:if>
        </c:when>
        <c:when test="${currentSection == 'medicaments'}">
            <c:choose>
                <c:when test="${userRole == 'ADMIN'}">
                    <div class="admin-section">
                        <h3 style="margin-top:0;"><i class="fas fa-pills"></i> Médicaments</h3>

                        <liferay-portlet:runtime
                                portletName="medicament_web_MedicamentWebPortlet"
                                instanceId="MEDS" />
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="message error">Section réservée aux administrateurs (ADMIN).</div>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:when test="${currentSection == 'commandes'}">
            <c:choose>
                <c:when test="${userRole == 'ADMIN' || userRole == 'PHARMACIEN' || userRole == 'FOURNISSEUR'}">
                    <div class="commands-section">
                        <h3>Méthode de commande</h3>
                        <liferay-portlet:runtime portletName="commande_web" instanceId="CMDS" />
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="message error">Section réservée (ADMIN / PHARMACIEN).</div>
                </c:otherwise>
            </c:choose>
        </c:when>

        <c:when test="${currentSection == 'stocks'}">
            <c:choose>
                <c:when test="${userRole == 'ADMIN' || userRole == 'PHARMACIEN'}">
                    <div class="admin-section">
                        <h3 style="margin-top:0;"><i class="fas fa-boxes-stacked"></i> Stocks</h3>
                        <liferay-portlet:runtime
                                portletName="stock_web"
                                instanceId="STK" />
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="message error">Section réservée (ADMIN / PHARMACIEN).</div>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:when test="${currentSection == 'ventes'}">
            <c:choose>
                <c:when test="${userRole == 'ADMIN' || userRole == 'PHARMACIEN'}">
                    <div class="admin-section">
                        <h3 style="margin-top:0;"><i class="fas fa-cash-register"></i> Ventes</h3>
                        <liferay-portlet:runtime portletName="vente_web_VenteWebPortlet" instanceId="VNT" />
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="message error">Section réservée (ADMIN / PHARMACIEN).</div>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:when test="${currentSection == 'notifications'}">
            <div class="admin-section">
                <h3 style="margin-top:0;"><i class="fas fa-bell"></i> Notifications</h3>

                <!-- The ONLY thing inside: your NotificationWebPortlet -->
                <liferay-portlet:runtime
                        portletName="notification_web_NotificationWebPortlet"
                        instanceId="NOTIF" />
            </div>
        </c:when>



        <%-- Fallback --%>
        <c:otherwise>
            <p>Section inconnue.</p>
        </c:otherwise>

    </c:choose>
    <portlet:actionURL name="addAdmin" var="addUserURL"/>
    <portlet:actionURL name="switchRole" var="switchRoleURL"/>

    <!-- Add Admin modal template -->
    <template id="admAddTPL">
        <form id="<portlet:namespace/>admAddForm" class="adm-form" method="post" novalidate>
            <div class="row">

                <div class="fld">
                    <label for="<portlet:namespace/>nom">Nom</label>
                    <input class="form-control" id="<portlet:namespace/>nom" name="<portlet:namespace/>nom"
                           autocomplete="family-name" required>
                </div>

                <div class="fld">
                    <label for="<portlet:namespace/>prenom">Prénom</label>
                    <input class="form-control" id="<portlet:namespace/>prenom" name="<portlet:namespace/>prenom"
                           autocomplete="given-name" required>
                </div>

                <div class="fld fld--email">
                    <label for="<portlet:namespace/>email">Email</label>
                    <input class="form-control" id="<portlet:namespace/>email" type="email"
                           name="<portlet:namespace/>email" autocomplete="email" required>
                    <small class="hint">Nous enverrons les informations de connexion à cette adresse.</small>
                </div>

                <div class="fld fld--password">
                    <label for="<portlet:namespace/>motDePasse">Mot de passe</label>
                    <div class="password-wrap">
                        <input class="form-control" id="<portlet:namespace/>motDePasse" type="password"
                               name="<portlet:namespace/>motDePasse" autocomplete="new-password" minlength="8" required>
                        <button type="button"
                                class="pwd-toggle"
                                id="<portlet:namespace/>pwdToggle"
                                aria-label="Afficher le mot de passe"
                                aria-pressed="false">
                            <!-- eye (shown when aria-pressed="false") -->
                            <svg class="ic-eye" viewBox="0 0 24 24" width="20" height="20" aria-hidden="true">
                                <path d="M12 5c5.23 0 9.32 3.3 10.8 7-1.48 3.7-5.57 7-10.8 7S2.68 15.7 1.2 12C2.68 8.3 6.77 5 12 5zm0 2C7.97 7 4.6 9.39 3.27 12 4.6 14.61 7.97 17 12 17s7.4-2.39 8.73-5C19.4 9.39 16.03 7 12 7zm0 2.5A2.5 2.5 0 1 1 9.5 12 2.5 2.5 0 0 1 12 9.5z"/>
                            </svg>

                            <!-- eye-off (shown when aria-pressed="true") -->
                            <svg class="ic-eye-off" viewBox="0 0 24 24" width="20" height="20" aria-hidden="true">
                                <path d="M2.1 3.51 3.5 2.1l18.4 18.39-1.41 1.42-3.06-3.06A12.22 12.22 0 0 1 12 19c-5.23 0-9.32-3.3-10.8-7a12.9 12.9 0 0 1 4.1-5.36L2.1 3.51zM7.3 8.71A9.9 9.9 0 0 0 3.27 12C4.6 14.61 7.97 17 12 17c1.57 0 3.03-.31 4.3-.86l-2.06-2.06a4 4 0 0 1-5.38-5.37l-1.56-1.56zM12 5c5.23 0 9.32 3.3 10.8 7a12.88 12.88 0 0 1-3.25 4.33l-1.42-1.42A9.9 9.9 0 0 0 20.73 12C19.4 9.39 16.03 7 12 7c-.45 0-.89.03-1.32.08L9.2 5.6C10.1 5.2 11.03 5 12 5z"/>
                            </svg>
                        </button>

                    </div>
                    <small class="hint">Au moins 8 caractères.</small>
                </div>

            </div>
        </form>
    </template>

    <template id="admRoleTPL">
        <form id="<portlet:namespace/>admRoleForm" class="adm-grid" method="post">
            <input type="hidden" name="<portlet:namespace/>targetUserEmail" id="<portlet:namespace/>targetUserEmail">
            <div class="fld" style="grid-column:1/-1">
                <label for="<portlet:namespace/>newRole">Nouveau rôle</label>
                <select class="adm-select"
                        id="<portlet:namespace/>newRole"
                        name="<portlet:namespace/>newRole">
                    <option value="PHARMACIEN">Pharmacien</option>
                    <option value="FOURNISSEUR">Fournisseur</option>
                    <c:if test="${userRole == 'SUPER_ADMIN'}">
                        <option value="ADMIN">Admin</option>
                    </c:if>
                </select>
                <small class="hint">Choisissez le nouveau rôle de l’utilisateur puis cliquez sur “Mettre à jour”.</small>
            </div>

        </form>
    </template>
    <template id="userViewTPL">
        <div style="padding:20px;">
            <div style="display:grid; grid-template-columns:repeat(2,1fr); gap:16px; margin-bottom:20px;">
                <div><strong>Nom complet:</strong> <span data-f="fullName"></span></div>
                <div><strong>Email:</strong> <span data-f="email"></span></div>
                <div><strong>Rôle:</strong> <span data-f="role"></span></div>
                <div><strong>Créé le:</strong> <span data-f="created"></span></div>
            </div>
            <p style="color:#6B7280; font-size:14px; margin:0;">
                <i class="fas fa-info-circle"></i>
                Fonctionnalités supplémentaires (commandes, ventes, etc.) à venir
            </p>
        </div>
    </template>


</div>
<script>
    function consultUser(email, prenom, nom, role){
        // find the row to read the created date text
        const row = Array.from(document.querySelectorAll('#adminTableBody tr'))
            .find(r => r.dataset.email === email);
        const createdText = row ? row.querySelector('td[data-sort]').textContent.trim() : '';

        const tpl = document.getElementById('userViewTPL');
        const div = document.createElement('div');
        div.innerHTML = tpl.innerHTML;

        div.querySelector('[data-f="fullName"]').textContent = (prenom + ' ' + nom).trim();
        div.querySelector('[data-f="email"]').textContent = email;
        div.querySelector('[data-f="role"]').textContent = role;
        div.querySelector('[data-f="created"]').textContent = createdText;

        Liferay.Util.openModal({
            id: '<portlet:namespace/>userView',
            title: 'Fiche Utilisateur',
            size: 'lg',
            bodyHTML: div.innerHTML,
            buttons: [{ label: 'Fermer', onClick: ({processClose}) => processClose && processClose() }]
        });
    }
</script>

<script>
    (function(){
        const ns = '<portlet:namespace />';  // or: const ns = '<%= renderResponse.getNamespace() %>';

        const addURL    = '${addAdminURL}';
        const switchURL = '${switchRoleURL}';

        // ---------- table controls (clone of meds) ----------
        const table = document.getElementById('admTable');
        if (!table) return;

        const tbody = table.querySelector('tbody');
        const allRows = Array.prototype.slice.call(tbody.querySelectorAll('tr'));
        let filtered = allRows.slice();
        const search = document.getElementById('admSearch');
        const pager  = document.getElementById('admPager');
        const range  = document.getElementById('admRange');
        const topCount = document.getElementById('admTopCount');

        let pageSize = 10, currentPage = 1, sort = {key:null, dir:1};

        function txt(el){ return (el && el.textContent || '').trim(); }
        function num(v){ v = parseInt(v,10); return isNaN(v) ? 0 : v; }
        function cell(row, key){ return row.querySelector('td[data-key="'+key+'"]'); }
        function val(row, key){
            if (key==='created' || key==='last') return num(cell(row,key).getAttribute('data-sort'));
            return txt(cell(row,key)).toLowerCase();
        }

        function applyFilter(){
            const q = (search && search.value ? search.value.toLowerCase().trim() : '');
            filtered = allRows.filter(r=>{
                if(!q) return true;
                return ['name','email','role'].some(k => val(r,k).indexOf(q)>-1);
            });
            currentPage = 1; applySort();
        }

        function applySort(){
            if (sort.key){
                filtered.sort((a,b)=>{
                    const va = val(a,sort.key), vb = val(b,sort.key);
                    if (va<vb) return -1*sort.dir; if (va>vb) return 1*sort.dir; return 0;
                });
            }
            render();
        }

        function render(){
            tbody.innerHTML = '';
            const total = filtered.length;
            const pages = Math.max(1, Math.ceil(total/pageSize));
            if (currentPage>pages) currentPage = pages;
            const start = (currentPage-1)*pageSize, end = start+pageSize;
            filtered.slice(start,end).forEach(r=>tbody.appendChild(r));

            // arrows
            Array.from(table.querySelectorAll('th.sortable .arrow')).forEach(a=>a.textContent='↕');
            if (sort.key){
                const th = table.querySelector('th.sortable[data-sort-key="'+sort.key+'"] .arrow');
                if (th) th.textContent = (sort.dir===1 ? '↑' : '↓');
            }

            // pager
            pager.innerHTML = '';
            if (pages>1){
                const prev = Object.assign(document.createElement('button'), {className:'btn', textContent:'Précédent', disabled: currentPage===1});
                prev.onclick = ()=>{ currentPage--; render(); }; pager.appendChild(prev);
                for(let i=1;i<=pages;i++){
                    const b = Object.assign(document.createElement('button'), {className:'btn'+(i===currentPage?' btn-primary':''), textContent:i});
                    b.onclick = ()=>{ currentPage=i; render(); }; pager.appendChild(b);
                }
                const next = Object.assign(document.createElement('button'), {className:'btn', textContent:'Suivant', disabled: currentPage===pages});
                next.onclick = ()=>{ currentPage++; render(); }; pager.appendChild(next);
            }
            if(range){
                const from = total===0?0:start+1, to = Math.min(end,total);
                range.textContent = 'Affichage '+from+'–'+to+' sur '+total;
            }
            if(topCount) topCount.textContent = total + ' utilisateur' + (total>1?'s':'');
        }

        if (search) search.addEventListener('input', applyFilter);
        Array.from(table.querySelectorAll('th.sortable')).forEach(th=>{
            th.addEventListener('click', ()=>{
                const key = th.getAttribute('data-sort-key');
                if (sort.key===key) sort.dir *= -1; else { sort.key = key; sort.dir = 1; }
                applySort();
            });
        });
        applyFilter();

        function openAdd(){
            const tpl = document.getElementById('admAddTPL');
            Liferay.Util.openModal({
                id: '<portlet:namespace/>admAdd',
                title: 'Ajouter un Administrateur',
                size: 'lg',
                bodyHTML: tpl.innerHTML,
                buttons: [
                    { label: 'Annuler', onClick: ({processClose}) => processClose && processClose() },
                    { label: 'Ajouter', primary:true, onClick: () => {
                            const f = document.getElementById('<portlet:namespace/>admAddForm');
                            if (!f) return;

                            // tiny client-side check
                            const email = document.getElementById('<portlet:namespace/>email');
                            const pwd   = document.getElementById('<portlet:namespace/>motDePasse');
                            if (email && !email.checkValidity()) { email.reportValidity(); return; }
                            if (pwd && !pwd.checkValidity())     { pwd.reportValidity(); return; }

                            f.action = '${addAdminURL}';
                            f.requestSubmit ? f.requestSubmit() : f.submit();
                        }
                    }
                ],
                onOpen: () => {
                    // focus first field
                    const first = document.getElementById('<portlet:namespace/>nom');
                    if (first) first.focus();

                    // show/hide password
                    const pwd = document.getElementById('<portlet:namespace/>motDePasse');
                    const tog = document.getElementById('<portlet:namespace/>pwdToggle');

                    if (tog && pwd){
                        tog.addEventListener('click', () => {
                            const show = pwd.type === 'password';
                            pwd.type = show ? 'text' : 'password';
                            tog.setAttribute('aria-pressed', show ? 'true' : 'false');
                        });
                        // ensure initial state
                        tog.setAttribute('aria-pressed', 'false');
                    }



                }
            });
        }
        function openRole(email, current){
            const tpl = document.getElementById('admRoleTPL');
            Liferay.Util.openModal({
                id: '<portlet:namespace />roleModal',
                title: 'Modifier le rôle',
                size: 'lg',
                bodyHTML: tpl.innerHTML,
                buttons: [
                    { label: 'Annuler', onClick: ({processClose}) => processClose && processClose() },
                    { label: 'Mettre à jour', primary: true, onClick: () => {
                            const f = document.getElementById('<portlet:namespace />admRoleForm');
                            if (!f) return;
                            f.action = '${switchRoleURL}';
                            f.requestSubmit ? f.requestSubmit() : f.submit();
                        }
                    }
                ],
                onOpen: ({modal}) => {
                    // Fill fields
                    requestAnimationFrame(() => {
                        const emailEl = document.getElementById('<portlet:namespace />targetUserEmail');
                        const roleEl  = document.getElementById('<portlet:namespace />newRole');
                        if (emailEl) emailEl.value = email || '';
                        if (roleEl && current) roleEl.value = current;
                    });

                    // 🔧 Force vertical size so the body gets taller
                    const dlg   = document.querySelector('#<portlet:namespace />roleModal .modal-dialog');
                    const cont  = document.querySelector('#<portlet:namespace />roleModal .modal-content');
                    const body  = document.querySelector('#<portlet:namespace />roleModal .modal-body');

                    if (dlg)  { dlg.style.marginTop = '6vh'; dlg.style.marginBottom = '6vh'; }/*
                    if (cont) { cont.style.minHeight = '560px'; }                  // <- make it taller
                    if (body) {
                        body.style.maxHeight = 'calc(100vh - 180px)';                // usable space
                        body.style.overflow  = 'auto';
                    }*/
                }
            });
        }

        // openers
        document.querySelectorAll('.js-admin-open').forEach(b=>{
            b.addEventListener('click', e=>{ e.preventDefault(); openAdd(); });
        });
        document.querySelectorAll('.js-role').forEach(b=>{
            b.addEventListener('click', ()=> openRole(b.dataset.email, b.dataset.current));
        });
    })();
</script>
<script>
    (function(){
        var badge = document.getElementById('notifBadge');
        if (!badge) return;

        // Hits NotificationWebPortlet.serveResource(id="unread") on the same page instance (INSTANCE_NOTIF)
        var url = '${notifUnreadURL}';

        function refreshBadge(){
            fetch(url, {credentials: 'same-origin'})
                .then(function(r){ return r.ok ? r.json() : {unread:0}; })
                .then(function(d){
                    var n = (d && typeof d.unread === 'number') ? d.unread : 0;
                    if (n > 0) {
                        badge.textContent = n;
                        badge.style.display = 'inline-block';
                    } else {
                        badge.style.display = 'none';
                    }
                })
                .catch(function(){ /* ignore */ });
        }

        refreshBadge();
        setInterval(refreshBadge, 30000); // every 30s
    })();
</script>
<script>
    (function () {
        const tbody = document.getElementById('adminTableBody');
        if (!tbody) return;

        // UI refs
        const emptyState           = document.getElementById('emptyState');
        const searchInput          = document.getElementById('adminSearch');
        const roleFilter           = document.getElementById('roleFilter');
        const dateFilter           = document.getElementById('dateFilter');
        const sortOrder            = document.getElementById('sortOrder');
        const resetBtn             = document.getElementById('resetFilters');
        const exportBtn            = document.getElementById('exportBtn');
        const refreshBtn           = document.getElementById('refreshBtn');
        const paginationInfo       = document.getElementById('paginationInfo');
        const paginationControls   = document.getElementById('paginationControls');

        // Data
        const pageSize = 5;
        let allRows      = Array.from(tbody.querySelectorAll('tr'));
        let filteredRows = allRows.slice();
        let currentPage  = 1;

        // ---- Stats (header cards) ----
        function updateStats() {
            const total        = allRows.length;
            const admins       = allRows.filter(r => (r.dataset.role === 'ADMIN' || r.dataset.role === 'SUPER_ADMIN')).length;
            const pharmaciens  = allRows.filter(r => r.dataset.role === 'PHARMACIEN').length;
            const fournisseurs = allRows.filter(r => r.dataset.role === 'FOURNISSEUR').length;

            const monthAgo = Date.now() - (30 * 24 * 60 * 60 * 1000);
            const newUsers = allRows.filter(r => (parseInt(r.dataset.created || '0', 10) > monthAgo)).length;

            const put = (id, v) => { const el = document.getElementById(id); if (el) el.textContent = v; };

            put('totalUsersCount', total);
            put('adminUsersCount', admins);
            put('pharmacienCount', pharmaciens);
            put('fournisseurCount', fournisseurs);
            put('newUsersCount', newUsers);
        }

        // ---- Filtering ----
        function applyFilters() {
            const query     = (searchInput.value || '').toLowerCase().trim();
            const roleValue = roleFilter.value;
            const dateRange = dateFilter.value;

            filteredRows = allRows.filter(row => {
                const name    = (row.dataset.name   || '').toLowerCase();
                const email   = (row.dataset.email  || '').toLowerCase();
                const rowRole = (row.dataset.role   || '').toLowerCase();

                const matchSearch  = !query || name.includes(query) || email.includes(query) || rowRole.includes(query);
                const matchRole    = !roleValue || row.dataset.role === roleValue;

                // Date range
                let matchDate = true;
                if (dateRange) {
                    const created = parseInt(row.dataset.created || '0', 10);
                    const now = Date.now();
                    const day = 24 * 60 * 60 * 1000;

                    switch (dateRange) {
                        case 'today': matchDate = (now - created) < day; break;
                        case 'week':  matchDate = (now - created) < (7 * day); break;
                        case 'month': matchDate = (now - created) < (30 * day); break;
                        case 'year':  matchDate = (now - created) < (365 * day); break;
                    }
                }

                return matchSearch && matchRole && matchDate;
            });

            currentPage = 1;
            applySorting();
        }

        // ---- Sorting ----
        function applySorting() {
            const order = sortOrder.value;

            filteredRows.sort((a, b) => {
                switch (order) {
                    case 'name-asc':
                        return (a.dataset.name || '').localeCompare(b.dataset.name || '');
                    case 'name-desc':
                        return (b.dataset.name || '').localeCompare(a.dataset.name || '');
                    case 'date-new':
                        return (parseInt(b.dataset.created || '0', 10) - parseInt(a.dataset.created || '0', 10));
                    case 'date-old':
                        return (parseInt(a.dataset.created || '0', 10) - parseInt(b.dataset.created || '0', 10));
                    case 'email-asc':
                        return (a.dataset.email || '').localeCompare(b.dataset.email || '');
                    default:
                        return 0;
                }
            });

            renderTable();
        }

        // ---- Pagination + render ----
        function renderTable() {
            tbody.innerHTML = '';

            const total = filteredRows.length;
            const totalPages = Math.max(1, Math.ceil(total / pageSize));
            if (currentPage > totalPages) currentPage = totalPages;

            const start = (currentPage - 1) * pageSize;
            const end   = start + pageSize;
            const pageRows = filteredRows.slice(start, end);

            if (pageRows.length === 0) {
                emptyState.style.display = 'block';
                // hide body so headers remain
                tbody.parentElement.style.display = 'none';
            } else {
                emptyState.style.display = 'none';
                tbody.parentElement.style.display = '';
                pageRows.forEach(row => tbody.appendChild(row));
            }

            // Info
            const from = total === 0 ? 0 : start + 1;
            const to   = Math.min(end, total);
            paginationInfo.textContent = `Affichage ${from}-${to} sur ${total}`;

            // Controls
            paginationControls.innerHTML = '';
            if (totalPages > 1) {
                const makeBtn = (txt, cls = 'page-btn') => {
                    const b = document.createElement('button');
                    b.className = cls;
                    b.textContent = txt;
                    return b;
                };

                // Prev
                const prev = makeBtn('←');
                prev.disabled = currentPage === 1;
                prev.onclick = () => { currentPage--; renderTable(); };
                paginationControls.appendChild(prev);

                // Pages (compact with ellipses)
                for (let i = 1; i <= totalPages; i++) {
                    if (i === 1 || i === totalPages || (i >= currentPage - 1 && i <= currentPage + 1)) {
                        const b = makeBtn(String(i), 'page-btn' + (i === currentPage ? ' active' : ''));
                        b.onclick = () => { currentPage = i; renderTable(); };
                        paginationControls.appendChild(b);
                    } else if (i === currentPage - 2 || i === currentPage + 2) {
                        const span = document.createElement('span');
                        span.textContent = '...';
                        span.style.padding = '0 8px';
                        span.style.color = '#6B7280';
                        paginationControls.appendChild(span);
                    }
                }

                // Next
                const next = makeBtn('→');
                next.disabled = currentPage === totalPages;
                next.onclick = () => { currentPage++; renderTable(); };
                paginationControls.appendChild(next);
            }
        }

        // ---- Export CSV (reads from rendered cells, not data-*) ----
        // ---- Export CSV (reads from rendered cells, not data-*) ----
        // ---- Export CSV (reads from rendered cells, not data-*) ----
        exportBtn.addEventListener('click', function () {
            function esc(v) {
                var s = (v == null ? '' : String(v));
                return '"' + s.replace(/"/g, '""') + '"';
            }

            var csv = 'Nom,Email,Rôle,Date création\n';

            filteredRows.forEach(function (row) {
                var name    = (row.querySelector('.user-name') || {}).textContent || '';
                var email   = (row.querySelector('.user-email') || {}).textContent || '';
                var roleEl  = row.querySelector('td:nth-child(2) .role-badge');
                var role    = (roleEl ? roleEl.textContent : row.dataset.role || '');
                var createdCell = row.querySelector('td[data-sort]');
                var created = createdCell
                    ? createdCell.textContent.trim()
                    : (function () {
                        var ts = Number(row.dataset.created || 0);
                        return ts ? new Date(ts).toLocaleDateString('fr-FR') : '';
                    })();

                csv += [esc(name.trim()), esc(email.trim()), esc(role.trim()), esc(created)].join(',') + '\n';
            });

            var blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
            var url  = URL.createObjectURL(blob);
            var a    = document.createElement('a');
            a.href = url;
            a.download = 'utilisateurs_' + new Date().toISOString().split('T')[0] + '.csv';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            URL.revokeObjectURL(url);
        });


        // ---- Refresh ----
  refreshBtn.addEventListener('click', () => {
    refreshBtn.querySelector('i')?.classList.add('fa-spin');
    setTimeout(() => location.reload(), 500);
  });

  // ---- Listeners ----
  searchInput.addEventListener('input', () => {
    clearTimeout(searchInput._t);
    searchInput._t = setTimeout(applyFilters, 300);
  });
  roleFilter.addEventListener('change', applyFilters);
  dateFilter.addEventListener('change', applyFilters);
  sortOrder.addEventListener('change', applySorting);

  resetBtn.addEventListener('click', () => {
    searchInput.value = '';
    roleFilter.value  = '';
    dateFilter.value  = '';
    sortOrder.value   = 'name-asc';
    applyFilters();
  });

  // ---- Init ----
  updateStats();
  applyFilters();
})();
</script>

<script>
    (function () {
        var tabBadge  = document.getElementById('notifBadge');
        var cardBadge = document.getElementById('notifBadgeCard');
        var url = '${notifUnreadURL}';
        if (!tabBadge && !cardBadge) return;

        function setBadge(el, n) {
            if (!el) return;
            if (n > 0) { el.textContent = n; el.style.display = 'inline-block'; }
            else { el.style.display = 'none'; }
        }

        function refreshBadge(){
            fetch(url, {credentials: 'same-origin'})
                .then(r => r.ok ? r.json() : {unread:0})
                .then(d => {
                    const n = (d && typeof d.unread === 'number') ? d.unread : 0;
                    setBadge(tabBadge, n);
                    setBadge(cardBadge, n);
                })
                .catch(() => {});
        }

        refreshBadge();
        setInterval(refreshBadge, 30000);
    })();
</script>


</body>
</html>

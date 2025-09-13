<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>



<portlet:defineObjects />

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



    <div class="tabs">
        <a class="tab ${currentSection=='overview' ? 'active' : ''}" href="${tabOverview}">Aperçu</a>
        <a class="tab ${currentSection=='admins'   ? 'active' : ''}" href="${tabAdmins}">Admins</a>
<%--
        <a class="tab ${currentSection=='users'    ? 'active' : ''}" href="${tabUsers}">Employés</a>
--%>
        <a class="tab ${currentSection=='logs'     ? 'active' : ''}" href="${tabLogs}">Logs</a>
        <a class="tab ${currentSection=='stats'    ? 'active' : ''}" href="${tabStats}">Statistiques</a>
        <a class="tab ${currentSection=='security' ? 'active' : ''}" href="${tabSecurity}">Sécurité</a>
        <c:if test="${userRole == 'ADMIN'}">
            <a class="tab ${currentSection=='medicaments' ? 'active' : ''}" href="${tabMedicaments}">Médicaments</a>
        </c:if>



    </div>

    <!-- Flash messages -->
    <c:if test="${not empty param.successMsg}">
        <div class="message success">${fn:escapeXml(param.successMsg)}</div>
    </c:if>
    <c:if test="${not empty param.errorMsg}">
        <div class="message error">${fn:escapeXml(param.errorMsg)}</div>
    </c:if>

    <!-- Debug -->
    <div class="debug-info">
        <b>Debug:</b>
        section=${currentSection}
        · user=${empty userEmail ? '-' : userEmail}
        · role=${empty userRole ? '-' : userRole}
        · employees=${employeesTotal}
    </div>

    <%-- base for includes --%>
    <c:set var="fragBase" value="/common" />

    <c:choose>
        <%-- Overview --%>
        <c:when test="${currentSection == 'overview'}">
            <div class="dashboard-cards">
                <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-pills"></i></div><h3 style="margin:0;">Gestion des Médicaments</h3></div><p>Consulter et gérer votre inventaire de médicaments.</p><c:if test="${userRole == 'ADMIN'}">
                    <a href="${tabMedicaments}" class="btn btn-primary">Ouvrir les Médicaments</a>
                </c:if>

                </div>
                <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-prescription"></i></div><h3 style="margin:0;">Ordonnances</h3></div><p>Gérer les ordonnances et les commandes des clients.</p></div>
                <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-users"></i></div><h3 style="margin:0;">Clients</h3></div><p>Consulter et gérer les informations des clients.</p></div>
                <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-chart-line"></i></div><h3 style="margin:0;">Rapports</h3></div><p>Générer des rapports et analyses de ventes.</p></div>
                <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-clipboard-list"></i></div><h3 style="margin:0;">Logs</h3></div><p>Consulter l’historique des connexions et actions.</p><a href="${tabLogs}" class="btn btn-primary">Voir Logs</a></div>
                <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-chart-pie"></i></div><h3 style="margin:0;">Statistiques</h3></div><p>Visualiser les statistiques globales de l’application.</p><a href="${tabStats}" class="btn btn-primary">Voir Statistiques</a></div>
                <div class="card"><div class="card-header"><div class="card-icon"><i class="fas fa-lock"></i></div><h3 style="margin:0;">Sécurité</h3></div><p>Gérer les paramètres de sécurité et les autorisations.</p><a href="${tabSecurity}" class="btn btn-primary">Paramètres Sécurité</a></div>
            </div>
        </c:when>

        <%-- Admins management --%>
<%--
        <c:when test="${currentSection == 'admins'}">
            <c:if test="${userRole == 'SUPER_ADMIN' || userRole == 'ADMIN'}">
                <div class="admin-section">
                    <h3 style="margin-top:0;"><i class="fas fa-shield-alt"></i> Panneau d'Administration</h3>

                    <div class="employee-management">
                        <h4><i class="fas fa-user-shield"></i> Liste des Admins</h4>
                        <c:if test="${not empty employees}">
                            <table class="employee-table">
                                <thead>
                                <tr>
                                    <th>Nom</th><th>Email</th><th>Rôle</th><th>Date Création</th><th>Dernière Connexion</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="employee" items="${employees}">
                                    <c:if test="${employee.role == 'ADMIN'}">
                                        <tr>
                                            <td>${employee.prenom} ${employee.nom}</td>
                                            <td>${employee.email}</td>
                                            <td>${employee.role}</td>
                                            <td><fmt:formatDate value="${employee.dateCreation}" pattern="dd/MM/yyyy HH:mm"/></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty employee.lastLogin}">
                                                        <fmt:formatDate value="${employee.lastLogin}" pattern="dd/MM/yyyy HH:mm"/>
                                                    </c:when>
                                                    <c:otherwise>-</c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                    </div>

                    <div class="employee-management">
                        <h4><i class="fas fa-users"></i> Liste des Employés</h4>

                            &lt;%&ndash; SEARCH + CONTROLS &ndash;%&gt;
                        <c:if test="${not empty employees}">
                            <div class="table-controls">
                                <input id="employeeSearch" type="search" placeholder="Rechercher par nom, email ou rôle…" />
                                <span class="pager-info" id="pagerInfo"></span>
                            </div>
                        </c:if>

                        <c:if test="${not empty employees}">
                            <table class="employee-table" id="employeesTable">
                                <thead>
                                <tr>
                                    <th class="sortable" data-sort-key="name">Nom <span class="arrow">↕</span></th>
                                    <th class="sortable" data-sort-key="email">Email <span class="arrow">↕</span></th>
                                    <th class="sortable" data-sort-key="role">Rôle <span class="arrow">↕</span></th>
                                    <th class="sortable" data-sort-key="dateCreation">Date Création <span class="arrow">↕</span></th>
                                    <th class="sortable" data-sort-key="lastLogin">Dernière Connexion <span class="arrow">↕</span></th>
                                    <th>Nouveau Rôle</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="employee" items="${employees}">
                                    <c:if test="${employee.role != 'ADMIN'}">
                                        <tr>
                                            <td data-key="name">${employee.prenom} ${employee.nom}</td>
                                            <td data-key="email">${employee.email}</td>
                                            <td data-key="role">${employee.role}</td>
                                            <td data-key="dateCreation"
                                                data-sort="${employee.dateCreation.time}">
                                                <fmt:formatDate value="${employee.dateCreation}" pattern="dd/MM/yyyy HH:mm"/>
                                            </td>
                                            <td data-key="lastLogin"
                                                data-sort="${empty employee.lastLogin ? 0 : employee.lastLogin.time}">
                                                <c:choose>
                                                    <c:when test="${not empty employee.lastLogin}">
                                                        <fmt:formatDate value="${employee.lastLogin}" pattern="dd/MM/yyyy HH:mm"/>
                                                    </c:when>
                                                    <c:otherwise>-</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <portlet:actionURL name="switchRole" var="switchRoleURL"/>
                                                <form class="inline" method="post" action="${switchRoleURL}">
                                                    <input type="hidden" name="<portlet:namespace/>targetUserEmail" value="${employee.email}"/>
                                                    <select name="<portlet:namespace/>newRole" class="role-select">
                                                        <option value="PHARMACIEN" ${employee.role=='PHARMACIEN' ? 'selected' : ''}>Pharmacien</option>
                                                        <option value="FOURNISSEUR" ${employee.role=='FOURNISSEUR' ? 'selected' : ''}>Fournisseur</option>
                                                        <c:if test="${userRole == 'SUPER_ADMIN'}">
                                                            <option value="ADMIN" ${employee.role=='ADMIN' ? 'selected' : ''}>Admin</option>
                                                        </c:if>
                                                    </select>
                                                    <button type="submit" class="btn btn-primary" style="width:100%;margin-top:6px;">Mettre à jour</button>
                                                </form>
                                            </td>
                                            <td>
                                                <c:if test="${userRole == 'SUPER_ADMIN'}">
                                                    <portlet:actionURL name="deleteAdmin" var="deleteAdminURL">
                                                        <portlet:param name="email" value="${employee.email}" />
                                                    </portlet:actionURL>
                                                    <form method="post" action="${deleteAdminURL}" style="margin:0;">
                                                        <button type="submit" class="btn btn-danger"
                                                                onclick="return confirm('Supprimer ${employee.email}?')">
                                                            Supprimer
                                                        </button>
                                                    </form>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                                </tbody>
                            </table>

                            &lt;%&ndash; PAGINATION &ndash;%&gt;
                            <div class="pager" id="pager"></div>
                        </c:if>

                        <c:if test="${empty employees}"><p>Aucun employé à afficher.</p></c:if>
                    </div>

                    <c:if test="${userRole == 'SUPER_ADMIN'}">
                        <div class="employee-management">
                            <h4><i class="fas fa-user-plus"></i> Ajouter un Admin</h4>
                            <portlet:actionURL name="addAdmin" var="addAdminURL"/>
                            <form method="post" action="${addAdminURL}" class="stack-sm">
                                <input type="text"  name="<portlet:namespace/>nom"         placeholder="Nom" required />
                                <input type="text"  name="<portlet:namespace/>prenom"      placeholder="Prénom" required />
                                <input type="email" name="<portlet:namespace/>email"       placeholder="Email" required />
                                <input type="password" name="<portlet:namespace/>motDePasse" placeholder="Mot de passe" required />
                                <button type="submit" class="btn btn-primary"><i class="fas fa-user-plus"></i> Ajouter Admin</button>
                            </form>
                        </div>

                        <div class="employee-management">
                            <h4><i class="fas fa-user-times"></i> Supprimer un Administrateur</h4>
                            <portlet:actionURL name="deleteAdmin" var="deleteAdminDirectURL"/>
                            <form method="post" action="${deleteAdminDirectURL}" class="stack-sm">
                                <input type="email" name="<portlet:namespace/>email" placeholder="Email Admin à supprimer" required />
                                <button type="submit" class="btn btn-danger"><i class="fas fa-user-times"></i> Supprimer Admin</button>
                            </form>
                        </div>
                    </c:if>
                </div>
            </c:if>
        </c:when>
--%>
        <c:when test="${currentSection == 'admins'}">
            <c:if test="${userRole == 'SUPER_ADMIN' || userRole == 'ADMIN'}">

                <%-- actions --%>
                <portlet:actionURL name="addAdmin"    var="addAdminURL"/>
                <portlet:actionURL name="switchRole"  var="switchRoleURL"/>

                <%-- reuse your existing delete action server-side.
                     If you have a generic delete, rename the action accordingly. --%>
                <portlet:actionURL name="deleteAdmin" var="deleteUserURL"/>

                <div class="adm-header">
                    <h2>👥 Utilisateurs & Admins</h2>
                    <div class="header-actions">
                        <span class="adm-count" id="admTopCount"></span>

                        <c:if test="${userRole == 'SUPER_ADMIN'}">
                            <button type="button" class="btn btn-primary js-admin-open" data-mode="add">
                                + Ajouter Admin
                            </button>
                        </c:if>
                    </div>
                </div>

                <div class="adm-card">
                    <div class="adm-controls">
                        <input id="admSearch" type="search" placeholder="Rechercher (nom, email, rôle)…"/>
                        <span class="adm-range" id="admRange"></span>
                    </div>

                    <table id="admTable">
                        <thead>
                        <tr>
                            <th class="sortable" data-sort-key="name">Nom <span class="arrow">↕</span></th>
                            <th class="sortable" data-sort-key="email">Email <span class="arrow">↕</span></th>
                            <th class="sortable" data-sort-key="role">Rôle <span class="arrow">↕</span></th>
                            <th class="sortable" data-sort-key="created">Date création <span class="arrow">↕</span></th>
                            <th class="sortable" data-sort-key="last">Dernière connexion <span class="arrow">↕</span></th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="u" items="${employees}">
                            <tr>
                                <td data-key="name">${fn:escapeXml(u.prenom)} ${fn:escapeXml(u.nom)}</td>
                                <td data-key="email">${fn:escapeXml(u.email)}</td>
                                <td data-key="role">${fn:escapeXml(u.role)}</td>
                                <td data-key="created" data-sort="${u.dateCreation.time}">
                                    <fmt:formatDate value="${u.dateCreation}" pattern="dd/MM/yyyy HH:mm"/>
                                </td>
                                <td data-key="last" data-sort="${empty u.lastLogin ? 0 : u.lastLogin.time}">
                                    <c:choose>
                                        <c:when test="${not empty u.lastLogin}">
                                            <fmt:formatDate value="${u.lastLogin}" pattern="dd/MM/yyyy HH:mm"/>
                                        </c:when>
                                        <c:otherwise>-</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <!-- EDIT (role) for everyone -->
                                    <button class="icon-btn js-role"
                                            title="Modifier le rôle"
                                            data-email="${fn:escapeXml(u.email)}"
                                            data-current="${fn:escapeXml(u.role)}">
                                        <i class="fa-regular fa-pen-to-square"></i>
                                    </button>

                                    <!-- DELETE for everyone (white trash on red) -->
                                    <form action="${deleteUserURL}" method="post" style="display:inline"
                                          onsubmit="return confirm('Supprimer ${fn:escapeXml(u.email)} ?');">
                                        <input type="hidden" name="<portlet:namespace/>email" value="${fn:escapeXml(u.email)}"/>
                                        <button type="submit" class="icon-btn danger" title="Supprimer">
                                            <i class="fa-solid fa-trash-can"></i>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <div class="pager" id="admPager"></div>
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

</div>

<script>
    (function(){
        // Elements
        var searchInput = document.getElementById('employeeSearch');
        var table = document.getElementById('employeesTable');
        if(!table){ return; } // not on admins section or no employees
        var tbody = table.querySelector('tbody');
        var allRows = Array.prototype.slice.call(tbody.querySelectorAll('tr'));
        var pager = document.getElementById('pager');
        var pagerInfo = document.getElementById('pagerInfo');

        var pageSize = 5;
        var currentPage = 1;
        var currentSort = { key: null, dir: 1 };
        var filtered = allRows.slice();

        function text(el){ return (el.textContent || '').trim(); }
        function num(v){ v = parseInt(v,10); return isNaN(v) ? 0 : v; }

        function getCell(row, key){
            return row.querySelector('td[data-key="'+key+'"]');
        }
        function getVal(row, key){
            switch(key){
                case 'name': return text(getCell(row,'name')).toLowerCase();
                case 'email': return text(getCell(row,'email')).toLowerCase();
                case 'role': return text(getCell(row,'role')).toLowerCase();
                case 'dateCreation': return num(getCell(row,'dateCreation').getAttribute('data-sort'));
                case 'lastLogin': return num(getCell(row,'lastLogin').getAttribute('data-sort'));
                default: return '';
            }
        }

        function applyFilter(){
            var q = (searchInput && searchInput.value ? searchInput.value : '').trim().toLowerCase();
            filtered = allRows.filter(function(row){
                var name = getVal(row,'name'), email = getVal(row,'email'), role = getVal(row,'role');
                return !q || name.indexOf(q) > -1 || email.indexOf(q) > -1 || role.indexOf(q) > -1;
            });
            currentPage = 1;
            applySort();
        }

        function applySort(){
            if(currentSort.key){
                filtered.sort(function(a,b){
                    var va = getVal(a, currentSort.key), vb = getVal(b, currentSort.key);
                    if(va < vb) return -1 * currentSort.dir;
                    if(va > vb) return  1 * currentSort.dir;
                    return 0;
                });
            }
            renderPage();
        }

        function renderPage(){
            tbody.innerHTML = '';
            var total = filtered.length;
            var totalPages = Math.max(1, Math.ceil(total / pageSize));
            if(currentPage > totalPages) currentPage = totalPages;
            var start = (currentPage - 1) * pageSize;
            var end = start + pageSize;
            filtered.slice(start, end).forEach(function(r){ tbody.appendChild(r); });

            // pager buttons
            pager.innerHTML = '';
            if(totalPages > 1){
                var prev = document.createElement('button');
                prev.textContent = 'Précédent';
                prev.className = 'btn';
                prev.disabled = currentPage === 1;
                prev.addEventListener('click', function(){ currentPage--; renderPage(); });
                pager.appendChild(prev);

                for(var p=1; p<=totalPages; p++){
                    var btn = document.createElement('button');
                    btn.textContent = p;
                    btn.className = 'btn' + (p===currentPage ? ' btn-primary' : '');
                    (function(page){ btn.addEventListener('click', function(){ currentPage = page; renderPage(); }); })(p);
                    pager.appendChild(btn);
                }

                var next = document.createElement('button');
                next.textContent = 'Suivant';
                next.className = 'btn';
                next.disabled = currentPage === totalPages;
                next.addEventListener('click', function(){ currentPage++; renderPage(); });
                pager.appendChild(next);
            }

            if(pagerInfo){
                var from = total === 0 ? 0 : start + 1;
                var to = Math.min(end, total);
                pagerInfo.textContent = 'Affichage ' + from + '–' + to + ' sur ' + total;
            }
        }

        // sorting header clicks
        Array.prototype.slice.call(table.querySelectorAll('thead th.sortable')).forEach(function(th){
            th.addEventListener('click', function(){
                var key = th.getAttribute('data-sort-key');
                if(currentSort.key === key){
                    currentSort.dir *= -1;
                }else{
                    currentSort.key = key;
                    currentSort.dir = 1;
                }
                // update arrow indicators
                Array.prototype.slice.call(table.querySelectorAll('thead th.sortable .arrow')).forEach(function(a){ a.textContent = '↕'; });
                var arrow = th.querySelector('.arrow');
                if(arrow){ arrow.textContent = currentSort.dir === 1 ? '↑' : '↓'; }
                applySort();
            });
        });

        if(searchInput){ searchInput.addEventListener('input', applyFilter); }

        // init
        applyFilter();
    })();
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


</body>
</html>

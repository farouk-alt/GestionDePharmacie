<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<portlet:defineObjects />

<portlet:actionURL name="checkout" var="checkoutURL" />
<portlet:resourceURL id="" var="searchURL" />

<style>
    .v-card{background:#fff;border:1px solid #E5E7EB;border-radius:12px;padding:16px}
    .btn{border:1px solid #E5E7EB;border-radius:10px;padding:8px 12px;background:#fff;cursor:pointer}
    .btn:hover{background:#F8FAFC}
    .btn-primary{background:#10B981;border-color:#10B981;color:#fff}
    .btn-primary:hover{filter:brightness(.95)}
    .btn-sm{padding:4px 10px}
    .inp{border:1px solid #E5E7EB;border-radius:10px;padding:10px;background:#fff}
</style>

<div class="v-card">
    <div style="padding:6px 10px;margin-bottom:8px;border:1px dashed #bbb;color:#555">
        VenteWeb view.jsp — ns: <code><portlet:namespace/></code>
        — medsCount: <strong>${medsCount}</strong>
    </div>

    <div style="display:flex;gap:10px;align-items:center;margin-bottom:10px">
        <input id="inpScan" class="inp" placeholder="Scanner code barre… ou saisir nom/code puis Entrée" autofocus>
        <button id="btnAdd" class="btn">Ajouter</button>
        <span id="hint" style="color:#6B7280;font-size:12px;margin-left:auto">Entrée = ajouter | +/- pour ajuster</span>
    </div>

    <!-- Suggestions (when typing a name) -->
    <div id="suggest" style="display:none;border:1px solid #E5E7EB;border-radius:10px;padding:8px;background:#fff;margin-bottom:10px"></div>

    <!-- Cart -->
    <table id="cart" style="width:100%;border-collapse:collapse">
        <thead>
        <tr>
            <th style="text-align:left;background:#F8FAFC;border-bottom:1px solid #E5E7EB;padding:10px">Article</th>
            <th style="text-align:right;background:#F8FAFC;border-bottom:1px solid #E5E7EB;padding:10px">PU</th>
            <th style="text-align:right;background:#F8FAFC;border-bottom:1px solid #E5E7EB;padding:10px">Dispo</th>
            <th style="text-align:right;background:#F8FAFC;border-bottom:1px solid #E5E7EB;padding:10px">Qté</th>
            <th style="text-align:right;background:#F8FAFC;border-bottom:1px solid #E5E7EB;padding:10px">Sous-total</th>
            <th style="background:#F8FAFC;border-bottom:1px solid #E5E7EB;padding:10px"></th>
        </tr>
        </thead>
        <tbody id="cartBody"></tbody>
    </table>

    <!-- Hidden arrays for submit -->
    <form id="saleForm" action="${checkoutURL}" method="post" data-senna-off="true">
        <div id="hidden"></div>
        <div style="display:flex;gap:10px;align-items:center;justify-content:space-between;margin-top:14px">
            <strong>Total: <span id="total">0.00</span> DH</strong>
            <button type="submit" class="btn btn-primary">Enregistrer la vente</button>
            <a class="btn" href="<portlet:actionURL><portlet:param name="mvcPath" value="/history.jsp"/></portlet:actionURL>">
                Voir l’historique
            </a>

        </div>
    </form>
</div>

<script>
    (function(){
        const NS = '<portlet:namespace />';
        const scan = document.getElementById('inpScan');
        const suggest = document.getElementById('suggest');
        const cartBody = document.getElementById('cartBody');
        const hidden = document.getElementById('hidden');
        const totalEl = document.getElementById('total');
        const addBtn = document.getElementById('btnAdd');
        const searchURL = '${searchURL}';

        const cart = new Map(); // id -> {id,nom,prix,dispo,qty}

        function fmt(n){ return (Math.round(n*100)/100).toFixed(2); }

        function rowEl(item){
            const tr = document.createElement('tr');
            tr.innerHTML =
                '<td style="padding:10px;border-bottom:1px solid #E5E7EB">' + item.nom + '</td>' +
                '<td style="padding:10px;border-bottom:1px solid #E5E7EB;text-align:right">' + fmt(item.prix) + '</td>' +
                '<td style="padding:10px;border-bottom:1px solid #E5E7EB;text-align:right">' + item.dispo + '</td>' +
                '<td style="padding:10px;border-bottom:1px solid #E5E7EB;text-align:right">' +
                '<button type="button" class="btn btn-sm js-minus">-</button>' +
                '<input class="inp js-qty" value="' + item.qty + '" style="width:70px;text-align:right;margin:0 6px">' +
                '<button type="button" class="btn btn-sm js-plus">+</button>' +
                '</td>' +
                '<td style="padding:10px;border-bottom:1px solid #E5E7EB;text-align:right" class="js-sub">' + fmt(item.qty * item.prix) + '</td>' +
                '<td style="padding:10px;border-bottom:1px solid #E5E7EB;text-align:center">' +
                '<button type="button" class="btn js-del">Suppr</button>' +
                '</td>';

            tr.querySelector('.js-minus').onclick = function(){ setQty(item.id, item.qty-1); };
            tr.querySelector('.js-plus').onclick  = function(){ setQty(item.id, item.qty+1); };
            tr.querySelector('.js-del').onclick   = function(){ cart.delete(item.id); render(); };
            const inp = tr.querySelector('.js-qty');
            inp.oninput = function(){
                var v = parseInt(inp.value||'0',10); if (isNaN(v)) v=0;
                setQty(item.id, v);
            };
            return tr;
        }

        function setQty(id, q){
            if (!cart.has(id)) return;
            const it = cart.get(id);
            q = Math.max(0, Math.min(q, it.dispo));
            it.qty = q;
            render();
        }

        function render(){
            cartBody.innerHTML = '';
            let sum = 0;
            for (const it of cart.values()){
                if (it.qty<=0) continue;
                cartBody.appendChild(rowEl(it));
                sum += it.qty * it.prix;
            }
            totalEl.textContent = fmt(sum);
        }

        async function searchOnce(q){
            const url = searchURL + '&' + NS + 'q=' + encodeURIComponent(q);
            const res = await fetch(url, {headers:{'Accept':'application/json'}});
            if (!res.ok) return [];
            return res.json();
        }


        async function addFromQuery(q){
            const list = await searchOnce(q);
            if (list.length === 0){ flash('Aucun résultat'); return; }

            if (list.length === 1){
                addItem(list[0]);
                return;
            }

            // show choices
            suggest.style.display = 'block';
            suggest.innerHTML = list.map(function(x){
                return '<button type="button" class="btn" data-id="' + x.id + '">' +
                    x.nom + ' — ' + fmt(x.prix) + ' DH (dispo ' + x.dispo + ')</button>';
            }).join(' ');

            suggest.querySelectorAll('button').forEach(function(b){
                b.onclick = function(){
                    const found = list.find(function(x){ return String(x.id) === b.dataset.id; });
                    addItem(found);
                    suggest.style.display='none';
                };
            });
        }

        function addItem(x){
            if (!x) return;
            const existing = cart.get(x.id);
            const starting = Math.min(1, x.dispo);
            if (existing){
                existing.qty = Math.min(existing.qty + 1, existing.dispo);
            } else {
                cart.set(x.id, {id:x.id, nom:x.nom, prix:x.prix, dispo:x.dispo, qty: starting});
            }
            scan.value = '';
            render();
        }
        function debounce(fn, ms){
            let t; return function(){ clearTimeout(t); t = setTimeout(()=>fn.apply(this, arguments), ms); };
        }
        const updateSuggest = debounce(async function(){
            const q = scan.value.trim();
            if (!q) { suggest.style.display = 'none'; suggest.innerHTML=''; return; }

            const list = await searchOnce(q);
            if (!list.length) { suggest.style.display='none'; suggest.innerHTML=''; return; }

            suggest.style.display = 'block';
            suggest.innerHTML = list.map(function(x){
                return '<button type="button" class="btn" data-id="' + x.id + '">' +
                    x.nom + ' — ' + fmt(x.prix) + ' DH (dispo ' + x.dispo + ')</button>';
            }).join(' ');
            suggest.querySelectorAll('button').forEach(function(b){
                b.onclick = function(){
                    const found = list.find(function(x){ return String(x.id) === b.dataset.id; });
                    addItem(found);
                    suggest.style.display='none';
                    scan.value = '';
                };
            });
        }, 250);

        // type-to-search by name
        scan.addEventListener('input', updateSuggest);

        // Enter key keeps working (add first best match)
        scan.addEventListener('keydown', async function(ev){
            if (ev.key === 'Enter'){
                ev.preventDefault();
                const q = scan.value.trim();
                if (!q) return;
                const list = await searchOnce(q);
                if (!list.length){ flash('Aucun résultat'); return; }
                addItem(list[0]);   // add the top result
                suggest.style.display='none';
                scan.value = '';
            }
        });

        // Add button uses same logic
        addBtn.onclick = async function(){
            const q = scan.value.trim();
            if (!q) return;
            const list = await searchOnce(q);
            if (!list.length){ flash('Aucun résultat'); return; }
            addItem(list[0]);
            suggest.style.display='none';
            scan.value = '';
        };

        function flash(msg){
            suggest.style.display = 'block';
            suggest.innerHTML = '<span style="color:#6B7280">' + msg + '</span>';
            setTimeout(function(){ suggest.style.display='none'; }, 1500);
        }

        // Submit -> create hidden arrays (namespaced)
        document.getElementById('saleForm').addEventListener('submit', function(e){
            hidden.innerHTML = '';
            let count=0;
            for (const it of cart.values()){
                if (it.qty<=0) continue;

                const i1 = document.createElement('input');
                i1.type  = 'hidden';
                i1.name  = NS + 'idMedicament';
                i1.value = it.id;

                const i2 = document.createElement('input');
                i2.type  = 'hidden';
                i2.name  = NS + 'qty';
                i2.value = it.qty;

                hidden.appendChild(i1);
                hidden.appendChild(i2);
                count++;
            }
            if (!count){
                e.preventDefault(); alert('Panier vide.'); return false;
            }
        });

        // Scanner / Enter key
        scan.addEventListener('keydown', function(ev){
            if (ev.key === 'Enter'){
                ev.preventDefault();
                const q = scan.value.trim();
                if (q) addFromQuery(q);
            }
        });

        addBtn.onclick = function(){
            const q = scan.value.trim();
            if (q) addFromQuery(q);
        };
    })();
</script>

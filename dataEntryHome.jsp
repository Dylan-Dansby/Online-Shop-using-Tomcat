<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Data Entry Portal</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: Arial, sans-serif; background: #f0f2f5; color: #333; min-height: 100vh; }

        header {
            background: #2d3436;
            padding: 16px 32px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        header h1 { color: #fdcb6e; font-size: 1.4rem; }
        header span { font-size: 0.85rem; color: #aaa; }

        .container { max-width: 860px; margin: 36px auto; padding: 0 20px; }

        /* Tabs */
        .tabs { display: flex; gap: 4px; margin-bottom: 0; }
        .tab-btn {
            padding: 10px 22px;
            border: none;
            border-radius: 8px 8px 0 0;
            background: #b2bec3;
            color: #2d3436;
            cursor: pointer;
            font-size: 0.9rem;
            font-weight: bold;
            transition: background 0.2s;
        }
        .tab-btn.active { background: #fdcb6e; color: #2d3436; }
        .tab-btn:hover:not(.active) { background: #dfe6e9; }

        .card {
            background: white;
            border-radius: 0 8px 8px 8px;
            padding: 28px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.09);
            border-top: 4px solid #fdcb6e;
            margin-bottom: 24px;
        }

        .tab-panel { display: none; }
        .tab-panel.active { display: block; }

        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 16px;
            margin-bottom: 20px;
        }
        .form-group { display: flex; flex-direction: column; gap: 6px; }
        .form-group.full { grid-column: 1 / -1; }

        label { font-size: 0.85rem; color: #636e72; font-weight: bold; text-transform: uppercase; letter-spacing: 0.5px; }

        input[type="text"], input[type="number"] {
            padding: 9px 12px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 0.95rem;
            color: #333;
            transition: border-color 0.2s;
        }
        input:focus { outline: none; border-color: #fdcb6e; }

        .submit-btn {
            background: #fdcb6e;
            color: #2d3436;
            border: none;
            padding: 11px 30px;
            border-radius: 6px;
            font-size: 0.95rem;
            font-weight: bold;
            cursor: pointer;
            letter-spacing: 0.5px;
        }
        .submit-btn:hover { background: #e0b04a; }

        .section-title {
            font-size: 1rem;
            color: #2d3436;
            margin-bottom: 18px;
            padding-bottom: 10px;
            border-bottom: 2px solid #f0f2f5;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        /* Results */
        .results-card {
            background: white;
            border-radius: 8px;
            padding: 20px 28px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
        }
        .results-card h3 { color: #2d3436; margin-bottom: 12px; font-size: 0.95rem; text-transform: uppercase; letter-spacing: 1px; }

        .business-logic-note {
            background: #fff8e1;
            border: 1px solid #fdcb6e;
            border-radius: 6px;
            padding: 10px 14px;
            margin-bottom: 16px;
            font-size: 0.83rem;
            color: #7d6608;
        }

        .logout { color: #aaa; text-decoration: none; font-size: 0.85rem; }
        .logout:hover { color: #fdcb6e; }
    </style>
</head>
<body>

<header>
    <h1>&#128221; Data Entry Portal</h1>
    <div>
        <span>Logged in as: <strong style="color:#fdcb6e;">dataentry</strong></span>
        &nbsp;&nbsp;
        <a href="AuthenticationPage.Html" class="logout">&#8594; Logout</a>
    </div>
</header>

<div class="container">

    <!-- Tab Buttons -->
    <div class="tabs">
        <button class="tab-btn active" onclick="showTab('suppliers', this)">&#127981; Suppliers</button>
        <button class="tab-btn" onclick="showTab('parts', this)">&#9881; Parts</button>
        <button class="tab-btn" onclick="showTab('jobs', this)">&#128119; Jobs</button>
        <button class="tab-btn" onclick="showTab('shipments', this)">&#128666; Shipments</button>
    </div>

    <div class="card">

        <!-- SUPPLIERS -->
        <div id="tab-suppliers" class="tab-panel active">
            <div class="section-title">&#127981; Insert New Supplier</div>
            <form action="SupplierServlet" method="post">
                <div class="form-grid">
                    <div class="form-group">
                        <label for="snum">Supplier Number (snum)</label>
                        <input type="text" id="snum" name="snum" placeholder="e.g. S6" required>
                    </div>
                    <div class="form-group">
                        <label for="sname">Supplier Name (sname)</label>
                        <input type="text" id="sname" name="sname" placeholder="e.g. Adams" required>
                    </div>
                    <div class="form-group">
                        <label for="status">Status (integer)</label>
                        <input type="number" id="status" name="status" placeholder="e.g. 30" required>
                    </div>
                    <div class="form-group">
                        <label for="scity">City</label>
                        <input type="text" id="scity" name="city" placeholder="e.g. London" required>
                    </div>
                </div>
                <button type="submit" class="submit-btn">&#43; Insert Supplier</button>
            </form>
        </div>

        <!-- PARTS -->
        <div id="tab-parts" class="tab-panel">
            <div class="section-title">&#9881; Insert New Part</div>
            <form action="PartsServlet" method="post">
                <div class="form-grid">
                    <div class="form-group">
                        <label for="pnum">Part Number (pnum)</label>
                        <input type="text" id="pnum" name="pnum" placeholder="e.g. P7" required>
                    </div>
                    <div class="form-group">
                        <label for="pname">Part Name (pname)</label>
                        <input type="text" id="pname" name="pname" placeholder="e.g. Cog" required>
                    </div>
                    <div class="form-group">
                        <label for="color">Color (integer code)</label>
                        <input type="number" id="color" name="color" placeholder="e.g. 3" required>
                    </div>
                    <div class="form-group">
                        <label for="weight">Weight</label>
                        <input type="text" id="weight" name="weight" placeholder="e.g. 19.5" required>
                    </div>
                    <div class="form-group">
                        <label for="pcity">City</label>
                        <input type="text" id="pcity" name="city" placeholder="e.g. Paris" required>
                    </div>
                </div>
                <button type="submit" class="submit-btn">&#43; Insert Part</button>
            </form>
        </div>

        <!-- JOBS -->
        <div id="tab-jobs" class="tab-panel">
            <div class="section-title">&#128119; Insert New Job</div>
            <form action="JobsServlet" method="post">
                <div class="form-grid">
                    <div class="form-group">
                        <label for="jnum">Job Number (jnum)</label>
                        <input type="text" id="jnum" name="jnum" placeholder="e.g. J7" required>
                    </div>
                    <div class="form-group">
                        <label for="jname">Job Name (jname)</label>
                        <input type="text" id="jname" name="jname" placeholder="e.g. Console" required>
                    </div>
                    <div class="form-group">
                        <label for="numworkers">Number of Workers</label>
                        <input type="text" id="numworkers" name="numworkers" placeholder="e.g. 1400" required>
                    </div>
                    <div class="form-group">
                        <label for="jcity">City</label>
                        <input type="text" id="jcity" name="city" placeholder="e.g. Rome" required>
                    </div>
                </div>
                <button type="submit" class="submit-btn">&#43; Insert Job</button>
            </form>
        </div>

        <!-- SHIPMENTS -->
        <div id="tab-shipments" class="tab-panel">
            <div class="section-title">&#128666; Insert New Shipment</div>
            <div class="business-logic-note">
                &#9888; <strong>Business Logic:</strong> If the quantity entered is &ge; 100, 
                the corresponding supplier's status will automatically be incremented by 5.
            </div>
            <form action="ShipmentsServlet" method="post">
                <div class="form-grid">
                    <div class="form-group">
                        <label for="ship-snum">Supplier Number (snum)</label>
                        <input type="text" id="ship-snum" name="snum" placeholder="e.g. S1" required>
                    </div>
                    <div class="form-group">
                        <label for="ship-pnum">Part Number (pnum)</label>
                        <input type="text" id="ship-pnum" name="pnum" placeholder="e.g. P1" required>
                    </div>
                    <div class="form-group">
                        <label for="ship-jnum">Job Number (jnum)</label>
                        <input type="text" id="ship-jnum" name="jnum" placeholder="e.g. J1" required>
                    </div>
                    <div class="form-group">
                        <label for="quantity">Quantity</label>
                        <input type="number" id="quantity" name="quantity" placeholder="e.g. 200" required>
                    </div>
                </div>
                <button type="submit" class="submit-btn">&#43; Insert Shipment</button>
            </form>
        </div>

    </div><!-- end card -->

    <!-- Results section -->
    <% String results = (String) request.getAttribute("results");
       if (results != null && !results.isEmpty()) { %>
    <div class="results-card">
        <h3>&#128203; Result</h3>
        <%= results %>
    </div>
    <% } %>

</div><!-- end container -->

<script>
    function showTab(tabName, btnEl) {
        // Hide all panels
        document.querySelectorAll('.tab-panel').forEach(p => p.classList.remove('active'));
        document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
        // Show selected
        document.getElementById('tab-' + tabName).classList.add('active');
        btnEl.classList.add('active');
    }
</script>
</body>
</html>

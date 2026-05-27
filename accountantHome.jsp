<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accountant Dashboard</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: Arial, sans-serif; background: #f5f6fa; color: #333; min-height: 100vh; }

        header {
            background: #1e3a2f;
            padding: 16px 32px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        header h1 { color: #2ecc71; font-size: 1.4rem; }
        header span { font-size: 0.85rem; color: #aaa; }

        .container { max-width: 860px; margin: 40px auto; padding: 0 20px; }

        .card {
            background: white;
            border-radius: 8px;
            padding: 28px;
            margin-bottom: 24px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            border-top: 4px solid #2ecc71;
        }
        .card h2 { color: #1e3a2f; margin-bottom: 18px; font-size: 1rem; text-transform: uppercase; letter-spacing: 1px; }

        .report-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 12px;
            margin-bottom: 20px;
        }

        .report-option {
            border: 2px solid #ddd;
            border-radius: 8px;
            padding: 14px 16px;
            cursor: pointer;
            transition: all 0.2s;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .report-option:hover { border-color: #2ecc71; background: #f0faf4; }
        .report-option input[type="radio"] { accent-color: #2ecc71; width: 16px; height: 16px; }
        .report-option label { cursor: pointer; font-size: 0.92rem; color: #333; }
        .report-option .desc { font-size: 0.78rem; color: #888; margin-top: 3px; }

        button {
            background: #2ecc71;
            color: white;
            border: none;
            padding: 11px 30px;
            border-radius: 6px;
            font-size: 0.95rem;
            cursor: pointer;
            letter-spacing: 0.5px;
        }
        button:hover { background: #27ae60; }

        .results-box table {
            width: 100%;
            border-collapse: collapse;
            font-size: 0.9rem;
        }
        .results-box table th {
            background: #1e3a2f;
            color: #2ecc71;
            padding: 9px 14px;
            text-align: left;
        }
        .results-box table td {
            padding: 8px 14px;
            border-bottom: 1px solid #eee;
        }
        .results-box table tr:hover td { background: #f0faf4; }

        .logout { color: #aaa; text-decoration: none; font-size: 0.85rem; }
        .logout:hover { color: #2ecc71; }
    </style>
</head>
<body>

<header>
    <h1>&#128200; Accountant Dashboard</h1>
    <div>
        <span>Logged in as: <strong style="color:#2ecc71;">accountant</strong></span>
        &nbsp;&nbsp;
        <a href="AuthenticationPage.Html" class="logout">&#8594; Logout</a>
    </div>
</header>

<div class="container">

    <div class="card">
        <h2>Generate Report</h2>
        <form action="AccountantServlet" method="post">
            <div class="report-grid">

                <div class="report-option">
                    <input type="radio" name="ReportSelection" id="r1" value="MaxStatus" required>
                    <div>
                        <label for="r1">&#127942; Max Supplier Status</label>
                        <div class="desc">Get the maximum status value across all suppliers</div>
                    </div>
                </div>

                <div class="report-option">
                    <input type="radio" name="ReportSelection" id="r2" value="TotalWeight">
                    <div>
                        <label for="r2">&#9878; Total Parts Weight</label>
                        <div class="desc">Sum of all part weights in the database</div>
                    </div>
                </div>

                <div class="report-option">
                    <input type="radio" name="ReportSelection" id="r3" value="TotalShipments">
                    <div>
                        <label for="r3">&#128666; Total Shipments</label>
                        <div class="desc">Count of all shipment records</div>
                    </div>
                </div>

                <div class="report-option">
                    <input type="radio" name="ReportSelection" id="r4" value="MostWorkers">
                    <div>
                        <label for="r4">&#128119; Job With Most Workers</label>
                        <div class="desc">Name of the job with the highest worker count</div>
                    </div>
                </div>

                <div class="report-option">
                    <input type="radio" name="ReportSelection" id="r5" value="AllSuppliers">
                    <div>
                        <label for="r5">&#128221; All Suppliers</label>
                        <div class="desc">List name and status of every supplier</div>
                    </div>
                </div>

            </div>
            <button type="submit">&#128202; Run Report</button>
        </form>
    </div>

    <% String results = (String) request.getAttribute("results");
       if (results != null && !results.isEmpty()) { %>
    <div class="card">
        <h2>Report Results</h2>
        <div class="results-box">
            <%= results %>
        </div>
    </div>
    <% } %>

</div>
</body>
</html>

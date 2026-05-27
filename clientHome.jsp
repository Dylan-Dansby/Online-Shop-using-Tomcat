<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Client Portal</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: Arial, sans-serif; background: #f0f4f8; color: #333; min-height: 100vh; }

        header {
            background: #2c3e50;
            padding: 16px 32px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        header h1 { color: #3498db; font-size: 1.4rem; }
        header span { font-size: 0.85rem; color: #bbb; }

        .container { max-width: 900px; margin: 40px auto; padding: 0 20px; }

        .card {
            background: white;
            border-radius: 8px;
            padding: 28px;
            margin-bottom: 24px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            border-top: 4px solid #3498db;
        }
        .card h2 { color: #2c3e50; margin-bottom: 16px; font-size: 1rem; text-transform: uppercase; letter-spacing: 1px; }

        .info-box {
            background: #eaf4fb;
            border: 1px solid #3498db;
            border-radius: 6px;
            padding: 10px 16px;
            margin-bottom: 16px;
            font-size: 0.85rem;
            color: #1a6fa8;
        }

        textarea {
            width: 100%;
            height: 100px;
            border: 1px solid #ccc;
            border-radius: 6px;
            padding: 12px;
            font-family: monospace;
            font-size: 0.95rem;
            resize: vertical;
            color: #333;
        }
        textarea:focus { outline: none; border-color: #3498db; }

        button {
            margin-top: 12px;
            background: #3498db;
            color: white;
            border: none;
            padding: 10px 26px;
            border-radius: 6px;
            font-size: 0.95rem;
            cursor: pointer;
        }
        button:hover { background: #2176ae; }

        .results-box table {
            width: 100%;
            border-collapse: collapse;
            font-size: 0.9rem;
        }
        .results-box table th {
            background: #2c3e50;
            color: white;
            padding: 9px 14px;
            text-align: left;
        }
        .results-box table td {
            padding: 8px 14px;
            border-bottom: 1px solid #e8e8e8;
        }
        .results-box table tr:hover td { background: #f0f7ff; }

        .logout { color: #bbb; text-decoration: none; font-size: 0.85rem; }
        .logout:hover { color: #3498db; }
    </style>
</head>
<body>

<header>
    <h1>&#128196; Client Portal</h1>
    <div>
        <span>Logged in as: <strong style="color:#3498db;">client</strong></span>
        &nbsp;&nbsp;
        <a href="AuthenticationPage.Html" class="logout">&#8594; Logout</a>
    </div>
</header>

<div class="container">

    <div class="card">
        <h2>Query Database</h2>
        <div class="info-box">
            &#8505; You may enter any SQL SELECT statement to query the database. 
            Non-SELECT statements are also permitted for this user role.
        </div>
        <form action="ClientServlet" method="post">
            <textarea name="sqlCommand" placeholder="e.g. SELECT * FROM suppliers"></textarea>
            <br>
            <button type="submit">&#128269; Run Query</button>
        </form>
    </div>

    <% String results = (String) request.getAttribute("results");
       if (results != null && !results.isEmpty()) { %>
    <div class="card">
        <h2>Query Results</h2>
        <div class="results-box">
            <%= results %>
        </div>
    </div>
    <% } %>

</div>
</body>
</html>

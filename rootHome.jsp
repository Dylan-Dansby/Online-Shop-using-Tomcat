<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Root Console</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: Arial, sans-serif; background: #1a1a2e; color: #eee; min-height: 100vh; }

        header {
            background: #16213e;
            padding: 16px 32px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            border-bottom: 2px solid #e94560;
        }
        header h1 { color: #e94560; font-size: 1.4rem; letter-spacing: 1px; }
        header span { font-size: 0.85rem; color: #aaa; }

        .container { max-width: 960px; margin: 40px auto; padding: 0 20px; }

        .card {
            background: #16213e;
            border-radius: 8px;
            padding: 28px;
            margin-bottom: 28px;
            border: 1px solid #0f3460;
        }
        .card h2 { color: #e94560; margin-bottom: 18px; font-size: 1.1rem; text-transform: uppercase; letter-spacing: 1px; }

        textarea {
            width: 100%;
            height: 110px;
            background: #0f3460;
            color: #eee;
            border: 1px solid #e94560;
            border-radius: 6px;
            padding: 12px;
            font-family: monospace;
            font-size: 0.95rem;
            resize: vertical;
        }
        textarea:focus { outline: none; border-color: #fff; }

        button {
            margin-top: 14px;
            background: #e94560;
            color: white;
            border: none;
            padding: 10px 28px;
            border-radius: 6px;
            font-size: 0.95rem;
            cursor: pointer;
            letter-spacing: 0.5px;
        }
        button:hover { background: #c73652; }

        .warning {
            background: #3a1a1a;
            border: 1px solid #e94560;
            border-radius: 6px;
            padding: 12px 18px;
            margin-bottom: 18px;
            font-size: 0.85rem;
            color: #f5a0a0;
        }

        .results-box { margin-top: 10px; }
        .results-box table {
            width: 100%;
            border-collapse: collapse;
            font-size: 0.9rem;
        }
        .results-box table th {
            background: #e94560;
            color: white;
            padding: 8px 12px;
            text-align: left;
        }
        .results-box table td {
            padding: 8px 12px;
            border-bottom: 1px solid #0f3460;
        }
        .results-box table tr:hover td { background: #0f3460; }

        .logout { color: #aaa; text-decoration: none; font-size: 0.85rem; }
        .logout:hover { color: #e94560; }
    </style>
</head>
<body>

<header>
    <h1>&#9881; Root Console</h1>
    <div>
        <span>Logged in as: <strong style="color:#e94560;">root</strong></span>
        &nbsp;&nbsp;
        <a href="Authenticate.Html" class="logout">&#8594; Logout</a>
    </div>
</header>

<div class="container">

    <div class="warning">
        &#9888; <strong>Root Access:</strong> You have unrestricted database access. All SQL statements are executed directly. 
        INSERT/UPDATE on shipments with quantity &ge; 100 will automatically increment the affected supplier's status by 5.
    </div>

    <div class="card">
        <h2>Execute SQL</h2>
        <form action="RootServlet" method="post">
            <textarea name="sqlCommand" placeholder="Enter any SQL statement here (SELECT, INSERT, UPDATE, DELETE...)"></textarea>
            <br>
            <button type="submit">&#9654; Execute</button>
        </form>
    </div>

    <% String results = (String) request.getAttribute("results");
       if (results != null && !results.isEmpty()) { %>
    <div class="card">
        <h2>Results</h2>
        <div class="results-box">
            <%= results %>
        </div>
    </div>
    <% } %>

</div>
</body>
</html>

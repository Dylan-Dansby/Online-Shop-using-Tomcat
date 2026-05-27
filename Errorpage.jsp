<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <div class="top-bar"></div>
    <div style="position: fixed; top: 0; left: 0; width: 100%; height: 30px; background-color: red; z-index: 9999; font-size: 25px; text-align: center">Error Page :(</div>
    <div class='header' style="text-align: center">
        <h1 style="text-align:center">Server Error occured</h1>
        <p style="text-align:center">Please try again</p>
        <button onclick="history.back()">Go Back</button>
    </div>
    <h2>Something went wrong</h2>
    <p>
    <% = request.getAttribute("errorMessage") != null 
    ? request.getAttribute("errorMessage") 
    : "An unknown error occurred." %>
    </p>
    
</body>
</html>


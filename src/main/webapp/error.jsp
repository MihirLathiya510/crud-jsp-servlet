<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
  <!DOCTYPE html>
  <html>

  <head>
    <title>Error</title>
    <style>
      .error-container {
        margin: 50px auto;
        max-width: 600px;
        padding: 20px;
        background-color: #ffebee;
        border-radius: 5px;
        border-left: 5px solid #f44336;
      }
    </style>
  </head>

  <body>
    <div class="error-container">
      <h1>Error</h1>
      <p>Sorry, something went wrong!</p>
      <p>Error details:</p>
      <pre>${pageContext.exception.message}</pre>
      <a href="${pageContext.request.contextPath}/list">Back to User List</a>
    </div>
  </body>

  </html>
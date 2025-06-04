<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html>

    <head>
      <title>User Management Application</title>
      <style>
        .form-group {
          margin-bottom: 15px;
        }

        label {
          display: block;
          margin-bottom: 5px;
        }

        input[type=text] {
          width: 100%;
          padding: 8px;
          box-sizing: border-box;
          border-radius: 4px;
          border: 1px solid #ccc;
        }

        .btn {
          background-color: #4CAF50;
          border: none;
          color: white;
          padding: 10px 20px;
          text-align: center;
          text-decoration: none;
          display: inline-block;
          font-size: 14px;
          margin: 4px 2px;
          cursor: pointer;
          border-radius: 4px;
        }
      </style>
    </head>

    <body>
      <header>
        <h1>User Management</h1>
        <nav>
          <a href="<%=request.getContextPath()%>/list" class="btn">Users</a>
        </nav>
      </header>
      <div>
        <c:if test="${user != null}">
          <form action="update" method="post">
            <h2>Edit User</h2>
            <input type="hidden" name="id" value="<c:out value='${user.id}' />" />
        </c:if>
        <c:if test="${user == null}">
          <form action="insert" method="post">
            <h2>Add New User</h2>
        </c:if>

        <div class="form-group">
          <label>Name</label>
          <input type="text" name="name" value="<c:out value='${user.name}' />" />
        </div>

        <div class="form-group">
          <label>Email</label>
          <input type="text" name="email" value="<c:out value='${user.email}' />" />
        </div>

        <div class="form-group">
          <label>Country</label>
          <input type="text" name="country" value="<c:out value='${user.country}' />" />
        </div>

        <button type="submit" class="btn">Save</button>
        </form>
      </div>
    </body>

    </html>
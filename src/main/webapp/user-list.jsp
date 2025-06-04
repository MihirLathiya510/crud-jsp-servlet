<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html>

    <head>
      <title>User Management Application</title>
      <style>
        table {
          border-collapse: collapse;
          width: 100%;
        }

        th,
        td {
          padding: 8px;
          text-align: left;
          border-bottom: 1px solid #ddd;
        }

        tr:hover {
          background-color: #f5f5f5;
        }

        .btn {
          background-color: #4CAF50;
          border: none;
          color: white;
          padding: 8px 16px;
          text-align: center;
          text-decoration: none;
          display: inline-block;
          font-size: 14px;
          margin: 4px 2px;
          cursor: pointer;
          border-radius: 4px;
        }

        .btn-danger {
          background-color: #f44336;
        }

        .btn-warning {
          background-color: #ff9800;
        }
      </style>
    </head>

    <body>
      <header>
        <h1>User Management</h1>
        <nav>
          <a href="<%=request.getContextPath()%>/list" class="btn">Users</a>
          <a href="<%=request.getContextPath()%>/new" class="btn">Add New User</a>
        </nav>
      </header>

      <div>
        <h2>List of Users</h2>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Country</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="user" items="${listUser}">
              <tr>
                <td>
                  <c:out value="${user.id}" />
                </td>
                <td>
                  <c:out value="${user.name}" />
                </td>
                <td>
                  <c:out value="${user.email}" />
                </td>
                <td>
                  <c:out value="${user.country}" />
                </td>
                <td>
                  <a href="edit?id=<c:out value='${user.id}' />" class="btn btn-warning">Edit</a>
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <a href="delete?id=<c:out value='${user.id}' />" class="btn btn-danger">Delete</a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </body>

    </html>
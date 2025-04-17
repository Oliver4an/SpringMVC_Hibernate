<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard</title>
  <style>
    body {
        margin: 0;
        padding: 0;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: #3ce5ff;
      }
      .card {
          display: flex;
          text-align: center;
          height: 300px;
          width: 500px;
          color: white;
          border-radius: 20px;
          box-shadow: 5px 5px 20px rgb(1, 14, 27);
          background: linear-gradient(-135deg, #00fea1, #1cabee);
          padding: 20px;
        }
        
        .left {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            margin-right: 30%;
            transform: translateX(50px);
        }
        
        .right {
          flex: 2;
          text-align: left;
          display: flex;
          flex-direction: column;
          justify-content: center; 
        }
        
        #thumbnail {
          font-size: 100px;
          margin-bottom: 10px;
        }
  </style>
</head>
<body>
  <div class="card">
    <div class="left">
        <div class="avatar-block">
          <i class="fa-solid fa-user" id="thumbnail"></i>
          <h2><span>${user.name}</span></h2>
        </div>
      </div>
    <div class="right">
      <p><h3>Email:</h3> <span>${user.email}</span></p>
      <p><h3>Phone: </h3><span>${user.number}</span></p>
    </div>
  </div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Log In / Sign Up</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>"/>
</head>
<body>


    <div class="panel">
        <div>
            <div class="container">
                <button class="switch onfocus" id="switch_login" onclick="switchToLogin()">Log In</button>
                <button class="switch unfocus" id="switch_signup" onclick="switchToSignup()">Sign Up</button>
            </div>

            <form class="form" id="login">
                <div class="warring" id="loginWarning"></div>
                <div class="form-group">
                    <i class="fa-solid fa-user"></i>
                    <input type="text" name="uname" placeholder="user name"/>
                </div>
                <div class="form-group">
                    <i class="fa-solid fa-lock"></i>
                    <input type="text" name="pwd" placeholder="password"/>
                </div>
                <div id="forg"><u>forgot password ? </u></div>
                <button type="submit" class="act">Log In</button>
            </form>

            <form class="form hidden" id="signup">
                <div class="warring" id="signupWarning"></div>
                <div class="form-group">
                    <i class="fa-solid fa-user"></i>
                    <input type="text" name="uname" id="uname" placeholder="user name"/>
                </div>
                <div class="form-group">
                    <i class="fa-solid fa-lock"></i>
                    <input type="text" name="pwd" id="pwd" placeholder="password" />
                </div>
                <div class="form-group">
                    <i class="fa-solid fa-envelope"></i>
                    <input type="text" name="email" id="email" placeholder="email"  />
                </div>
                <div class="form-group">
                    <i class="fa-solid fa-phone"></i>
                    <input type="text" name="phone" id="phone" placeholder="phone number"  />
                </div>
                <button type="submit" class="act">Sign up</button>
            </form>
        </div>
        <div id="customModal" class="modal hidden">
            <div class="modal-content">
                <div class="modal-header">
                    <span class="close-btn" onclick="Modal.close()">&times;</span>
                    <h2 id="modalTitle">Title</h2>
                  </div>
                  
              <div class="modal-body">
                <p id="modalMessage">Message</p>
                <input type="text" id="modalInput" placeholder="Input here...">
              </div>
          
              <div class="modal-footer">
                <button id="modalSubmitBtn">Submit</button>
              </div>
            </div>
          </div>
    </div>
    <script src="<c:url value='/js/modal.js' />"></script>
    <script src="<c:url value='/js/login-signup.js'/>"></script>
    <script src="/js/login-signup.js?v=<%= System.currentTimeMillis() %>"></script>
</body>
</html>

// ====================== 切換表單 ======================
function switchToSignup() {
    document.getElementById("login").classList.add("hidden");
    document.getElementById("signup").classList.remove("hidden");

    document.getElementById("switch_signup").classList.add("onfocus");
    document.getElementById("switch_signup").classList.remove("unfocus");

    document.getElementById("switch_login").classList.remove("onfocus");
    document.getElementById("switch_login").classList.add("unfocus");
    }

function switchToLogin() {
    document.getElementById("signup").classList.add("hidden");
    document.getElementById("login").classList.remove("hidden");

    document.getElementById("switch_login").classList.add("onfocus");
    document.getElementById("switch_login").classList.remove("unfocus");

    document.getElementById("switch_signup").classList.remove("onfocus");
    document.getElementById("switch_signup").classList.add("unfocus");
    }
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("forg").addEventListener("click", function () {
        Modal.open({
          title: "Reset your password",
          message: "Enter your email address that you  use with your account",
          placeholder: "example@email.com",
          onSubmit: function(email){
                fetch("/SpringMVC_Hibernate/sendResetEmail", {
                  method: "POST",
                  headers: { "Content-Type": "application/x-www-form-urlencoded" },
                  body: "email=" + encodeURIComponent(email)
                })
                .then(res => res.json())
                .then(data => {
                  alert(data.message);
                  Modal.close();
                })
                .catch(err => {
                  alert("發送失敗：" + err);
                });
              }
        });
    });

    // ====================== 驗證設定 ======================
    const fields = [
    { id: "uname", msg: "⚠️ user name can't be empty.", required: true },
    { id: "pwd", msg: "⚠️ password can't be empty.", required: true },
    { id: "email", msg: "⚠️ email can't be empty.", required: true },
    { id: "phone", msg: "⚠️ phone number can't be empty.", required: true }
    ];
    let signupData = {}; // 暫存使用者資料
    let isNameDuplicate = false;

    // ====================== 驗證邏輯 ======================
    function validateFields() {
        for (let field of fields) {
        const value = document.getElementById(field.id).value.trim();
        if (field.required && value === "") {
            showWarning(field.msg);
            return false;
        }
        }

        if (isNameDuplicate) {
        showWarning("⚠️ User name has been used.");
        return false;
        }

        hideWarning();
        return true;
        }

    function showWarning(message) {
        const warn = document.getElementById("signupWarning");
        warn.textContent = message;
        warn.style.display = "block";
        }

    function hideWarning() {
        const warn = document.getElementById("signupWarning");
        warn.style.display = "none";
        }

    function addBlurValidation(fieldId, errorMsg) {
        document.getElementById(fieldId).addEventListener("blur", function () {
            const value = this.value.trim();
            if (!value) {
            showFieldError(fieldId, errorMsg);  // 顯示對應的錯誤訊息
            } else {
            showFieldError(fieldId, "");        // 清除該欄位錯誤
            }
        });
        }
    // ====================== 註冊帳號重複檢查 ======================
    document.getElementById("uname").addEventListener("blur", function () {
        const uname = this.value.trim();
        if (!uname) return;

        fetch("/SpringMVC_Hibernate/checkUname", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "uname=" + encodeURIComponent(uname)
        })
        .then(res => res.text())
        .then(data => {
            isNameDuplicate = data === "true";
            if (isNameDuplicate) {
            showWarning("⚠️ User name has been used.");
            } else {
            hideWarning();
            }
        })
        .catch(err => {
            console.error("Error:", err);
        });
        });

    // ====================== 註冊送出 ======================
    document.getElementById("signup").addEventListener("submit", function (event) {
        event.preventDefault();
        if (!validateFields()) return;
        showWarning("processing...");
        //  暫存資料
        signupData = {
            name: this.uname.value.trim(),
            password: this.pwd.value.trim(),
            email: this.email.value.trim(),
            number: this.phone.value.trim()
        };

        // 先發送驗證碼（只送 email 給後端）
        fetch("/SpringMVC_Hibernate/sendVerifyCode", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email: signupData.email })
        })
        .then(res => res.text())
        .then(msg => {
            console.log("驗證碼發送結果:", msg);
            hideWarning();
            Modal.open({
                title: "Enter Verification Code",
                message: "Code has been sent to your email",
                placeholder: "Verification code",
                onSubmit: submitVerificationCode
            });
        })
        .catch(err => alert("發送驗證碼失敗：" + err));
    });

    // ====================== 登入送出 ======================
    document.getElementById("login").addEventListener("submit", function (event) {
        event.preventDefault();
        const username = this.uname.value.trim();
        const password = this.pwd.value.trim();
        const parms = new URLSearchParams();
        parms.append("uname",username);
        parms.append("pwd",password);
        if (!username || !password) {
        //console.log("empty");
        return;
        }


        fetch("/SpringMVC_Hibernate/login", {
        method: "POST",
        headers:{
          "Content-Type": "application/x-www-form-urlencoded"
        },
        body: parms
        })
        .then(res => res.text())
        .then(data => {
            console.log("後端回傳跳轉網址：", data);
            if (data.startsWith("/")) {
            window.location.href = data;
            } else {
                const warn = document.getElementById("loginWarning");
                warn.textContent = "⚠️ User name or Password is not correct";
                warn.style.display = "block";
            }
        })
        .catch(err => {
            alert("Error: " + err);
        });
    });

    function openModal() {
        document.getElementById("verifyModal").classList.remove("hidden");
        }
    function closeModal() {
        document.getElementById("verifyModal").classList.add("hidden");
    }
    function submitVerificationCode(code) {
        // code 是直接從 modal 輸入欄位拿到的值
        fetch("/SpringMVC_Hibernate/verify", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ code: code })
        })
        .then(res => res.text())
        .then(response => {
          if (response === "success") {
            fetch("/SpringMVC_Hibernate/signup", {
              method: "POST",
              headers: {
                "Content-Type": "application/json"
              },
              body: JSON.stringify(signupData)
            })
            .then(res => res.text())
            .then(msg => {
              alert(msg);
              Modal.close();
              location.reload();
            })
            .catch(err => alert("註冊失敗：" + err));
          } else {
            alert("Verification code incorrect. Please try again");
          }
        })
        .catch(err => {
          alert("驗證失敗：" + err);
        });
      }

    addBlurValidation("pwd", "⚠️ password can't be empty.");
    addBlurValidation("email", "⚠️ email can't be empty.");
    addBlurValidation("phone", "⚠️ phone number can't be empty.");
});
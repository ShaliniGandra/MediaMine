
/* =========================================================
   MEDIAMINE — SHARED FRONTEND SCRIPT
   Safe on every page; login-only code runs only on index.html.
   ========================================================= */

document.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById("loginForm");
  const usernameInput = document.getElementById("username");
  const passwordInput = document.getElementById("password");
  const togglePassword = document.getElementById("togglePassword");
  const demoLogin = document.getElementById("demoLogin");
  const loginMessage = document.getElementById("loginMessage");
  const forgotPassword = document.getElementById("forgotPassword");

  /* ---------- Login page ---------- */
  if (loginForm && usernameInput && passwordInput) {
    if (togglePassword) {
      togglePassword.addEventListener("click", () => {
        const visible = passwordInput.type === "text";
        passwordInput.type = visible ? "password" : "text";
        togglePassword.textContent = visible ? "◉" : "◌";
        togglePassword.setAttribute(
          "aria-label",
          visible ? "Show password" : "Hide password"
        );
      });
    }

    const showMessage = (message, type = "") => {
      if (!loginMessage) return;
      loginMessage.textContent = message;
      loginMessage.className = "login-message" + (type ? " " + type : "");
    };

    loginForm.addEventListener("submit", (event) => {
      event.preventDefault();

      const username = usernameInput.value.trim();
      const password = passwordInput.value.trim();

      if (!username || !password) {
        showMessage("Please enter your username and password.", "error");
        return;
      }

      if (username === "admin" && password === "mediamine") {
        sessionStorage.setItem("mediaMineLoggedIn", "true");
        showMessage("Login successful. Opening MediaMine...", "success");
        setTimeout(() => {
          window.location.href = "dashboard.html";
        }, 500);
      } else {
        showMessage("Invalid username or password.", "error");
      }
    });

    if (demoLogin) {
      demoLogin.addEventListener("click", () => {
        usernameInput.value = "admin";
        passwordInput.value = "mediamine";
        loginForm.requestSubmit();
      });
    }

    if (forgotPassword) {
      forgotPassword.addEventListener("click", (event) => {
        event.preventDefault();
        showMessage(
          "Password recovery will be available in the full authentication system."
        );
      });
    }
  }

  /* ---------- Shared logout ---------- */
  const logoutButtons = document.querySelectorAll(
    "#logoutButton, .logout-button, .logout-btn"
  );

  logoutButtons.forEach((button) => {
    button.addEventListener("click", () => {
      sessionStorage.removeItem("mediaMineLoggedIn");
      window.location.href = "index.html";
    });
  });

  /* ---------- Session guard for app pages ---------- */
  const isLoginPage = !!document.getElementById("loginForm");
  if (!isLoginPage) {
    const appPages = [
      "dashboard.html",
      "dataset.html",
      "algorithms.html",
      "analytics.html",
      "about.html"
    ];
    const current = window.location.pathname.split("/").pop();
    if (appPages.includes(current) &&
        sessionStorage.getItem("mediaMineLoggedIn") !== "true") {
      window.location.href = "index.html";
    }
  }
});

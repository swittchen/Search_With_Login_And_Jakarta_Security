// ================================
// Login Logik
// ================================
(function () {
  const form = document.getElementById("loginForm");
  const messageEl = document.getElementById("loginMessage");

  if (!form) return;

  function setMessage(text, type) {
    if (!messageEl) return;
    messageEl.textContent = text;
    messageEl.className = "login-message " + (type || "");
  }

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const loginInput = document.getElementById("login");
    const passwordInput = document.getElementById("password");

    const username = loginInput.value.trim();
    const password = passwordInput.value.trim();

    if (!username || !password) {
      setMessage("Please enter username and password.", "error");
      return;
    }

    setMessage("Authenticating…", "success");

    try {
      const response = await fetch(
        "http://localhost:8888/backend/api/auth/login",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ username: username, password: password }),
        }
      );

      if (!response.ok) {
        console.log(response);
        const text = await response.text();
        throw new Error(text || "Login failed");
      }

      const data = await response.json();
      // erwartet: { token: "...", username: "...", role: "ADMIN|USER|GUEST" }
      console.log(response);
      console.log(data);
      localStorage.setItem("crimsy_token", data.token);
      localStorage.setItem("crimsy_username", data.username);
      localStorage.setItem("crimsy_role", data.role);

      const response2 = await fetch(
        "http://localhost:8888/backend/api/search",
        {
          headers: {
            Authorization: "Bearer " + data.token,
          },
        }
      );
      if (response2.status === 200) {
        window.location.href = "../search.html";
      }
      if (response2.status === 401 || response2.status === 403) {
        // Nicht eingeloggt oder nicht genug Rechte
        window.location.href = "../login.html";
      }
      // if (data.role !== "GUEST") {
      //   console.log(data.role);
      //   window.location.href = "../search.html";
      // } else {
      //   console.log("ACCESS DENIDED");

      //   setMessage(
      //     `Welcome ${data.username} (role: ${data.role}) – token stored.`,
      //     "success"
      //   );
      // }
      // hier kannst du später redirecten:
      // window.location.href = "/backend/app/index.html";
    } catch (err) {
      console.error(err);
      setMessage("Invalid credentials.", "error");
    }
  });
})();

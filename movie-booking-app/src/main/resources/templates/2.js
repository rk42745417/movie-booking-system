/*
btn.addEventListener("click", function(e) {
    e.preventDefault(); // 防止表單自動重新整理
});
    const username = document.getElementById("account").value;
    const password = document.getElementById("password").value;
*/
  

document.getElementById("login-form").addEventListener("submit", function (event) {
  event.preventDefault(); // 阻止表單預設送出

  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;

  // 傳送給後端的 JSON 物件
  const payload = {
    username: username,
    password: password
  };

  fetch("https://your-backend.com/api/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(payload)
  })
    .then(response => response.json())
    .then(data => {
      if (data.success) {
        alert("登入成功！");
        window.location.href = "dashboard.html"; // 導向登入後頁面
      } else {
        alert("登入失敗：" + data.message);
      }
    })
    .catch(error => {
      console.error("發生錯誤：", error);
      alert("伺服器錯誤，請稍後再試");
    });
});



/*
fetch("http://localhost:8080/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      username: username,
      password: password
    })
  })
  .then(res => res.json())
  .then(data => {
    if (data.status === "success") {
      alert("註冊成功！");
    } else {
      alert("註冊失敗：" + data.message);
    }
  })
  .catch(err => {
    console.error("錯誤：", err);
    alert("伺服器錯誤，請稍後再試");
  });
});
*/
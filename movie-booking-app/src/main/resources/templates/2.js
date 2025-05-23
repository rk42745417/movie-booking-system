
btn.addEventListener("click", function(e) {
    e.preventDefault(); // 防止表單自動重新整理
});
    const username = document.getElementById("account").value;
    const password = document.getElementById("password").value;
    
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
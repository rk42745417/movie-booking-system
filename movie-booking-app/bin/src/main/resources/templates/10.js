document.getElementById("btn").addEventListener("click", function () {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    if (!username || !password) {
        alert("請填寫帳號與密碼");
        return;
    }

    const payload = {
        username: username,
        password: password
    };

    fetch("https://your-backend.com/api/login", { // ⬅️ 替換成你的後端網址
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
    })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                alert("登入成功！");
                window.location.href = "admin_home.html"; // ⬅️ 成功後跳轉的頁面
            } else {
                alert("登入失敗：" + data.message);
            }
        })
        .catch(err => {
            console.error("發生錯誤：", err);
            alert("連線失敗，請稍後再試");
        });
});


    async function fetchOrders() {
      const container = document.getElementById("order-container");
      container.innerHTML = "載入中...";

      try {
        const response = await fetch('https://your-api-url.com/orders'); // 替換成你的 API URL
        if (!response.ok) throw new Error('查詢失敗');

        const orders = await response.json();
        container.innerHTML = "";

        if (orders.length === 0) {
          container.innerHTML = "沒有找到任何訂單。";
        } else {
          orders.forEach(order => {
            const div = document.createElement("div");
            div.className = "order";
            div.innerHTML = `
              <strong>訂單編號：</strong>${order.id}<br>
              <strong>客戶名稱：</strong>${order.customer}<br>
              <strong>金額：</strong>${order.total}<br>
              <strong>狀態：</strong>${order.status}
            `;
            container.appendChild(div);
          });
        }
      } catch (error) {
        container.innerHTML = "發生錯誤：" + error.message;
      }
    }

    // 頁面載入完成後執行
    window.addEventListener('DOMContentLoaded', fetchOrders);

const maxSeats = 3; // 使用者剛剛選的張數，可從 URL 或 localStorage 取得
let selectedSeats = [];

fetch("small_room.json") // JSON 路徑
  .then(res => res.json())
  .then(data => {
    const container = document.getElementById("seat-container");

    data.forEach(seat => {
      const btn = document.createElement("button");
      btn.textContent = `${seat.row}${seat.seatNum}`;
      btn.className = "seat";
      
      // 假設你有個欄位 seat.booked 表示是否被訂走
      if (seat.booked) {
        btn.disabled = true;
        btn.classList.add("booked");
      }

      btn.addEventListener("click", () => {
        if (btn.classList.contains("selected")) {
          btn.classList.remove("selected");
          selectedSeats = selectedSeats.filter(s => s !== btn.textContent);
        } else {
          if (selectedSeats.length < maxSeats) {
            btn.classList.add("selected");
            selectedSeats.push(btn.textContent);
          } else {
            alert(`最多只能選擇 ${maxSeats} 張票`);
          }
        }
        document.getElementById("selected-count").textContent = selectedSeats.length;
      });

      container.appendChild(btn);
    });
  });

function submitSeats() {
  // 傳送到後端，例如用 fetch POST
  fetch("/api/submitSeats", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      selectedSeats: selectedSeats
    })
  })
  .then(res => res.json())
  .then(result => {
    // 跳轉到下一頁
    window.location.href = "confirm.html";
  });
}

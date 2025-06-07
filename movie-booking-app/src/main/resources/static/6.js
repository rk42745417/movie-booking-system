document.addEventListener("DOMContentLoaded", function () {
  const nextBtn = document.getElementById("next-btn");
  const sessionSelect = document.getElementById("session-time");
  const ticketInput = document.getElementById("ticket-count");

  nextBtn.addEventListener("click", function () {
    const selectedOption = sessionSelect.options[sessionSelect.selectedIndex];
    const selectedShowtimeId = sessionSelect.value;
    const hallType = selectedOption ? selectedOption.getAttribute("data-hall-type") || "" : "";
    const ticketCount = parseInt(ticketInput.value, 10);

    // Get movieId and movieTitle from URL
    const urlParams = new URLSearchParams(window.location.search);
    const movieId = urlParams.get("movieId");
    const movieTitle = urlParams.get("movieTitle");

    // Validation
    if (!selectedShowtimeId) {
      alert("請選擇場次時間！");
      return;
    }

    if (isNaN(ticketCount) || ticketCount <= 0) {
      alert("請輸入有效的票數！");
      return;
    }

    fetch("/api/bookings/select-session", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        showtimeId: selectedShowtimeId,
        tickets: ticketCount,
        movieId: movieId,
        movieTitle: movieTitle
      })
    })
    .then(response => {
      if (response.ok) {
        // Decide next page by hallType
        let baseUrl = null;
        if (hallType === "small_room") {
          baseUrl = "8訂票用戶＿訂票（小廳座位）.html";
        } else if (hallType === "big_room") {
          baseUrl = "7訂票用戶＿訂票（大廳座位）.html";
        }

        if (!baseUrl) {
          alert("未知廳別，請聯絡管理員");
          return;
        }

        const nextUrl = `${baseUrl}?showtimeId=${encodeURIComponent(selectedShowtimeId)}&tickets=${ticketCount}&movieId=${encodeURIComponent(movieId)}&movieTitle=${encodeURIComponent(movieTitle)}`;
        window.location.href = nextUrl;
      } else {
        alert("送出失敗，請稍後再試");
      }
    })
    .catch(error => {
      console.error("錯誤：", error);
      alert("連線錯誤，請檢查網路");
    });
  });
});





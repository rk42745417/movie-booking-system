document.addEventListener("DOMContentLoaded", () => {
  const btnLoadMovies = document.getElementById("btn-load-movies");

  if (btnLoadMovies) {
    btnLoadMovies.addEventListener("click", (e) => {
      e.preventDefault(); // prevent <a> default navigation
      loadMovies();
    });
  }
});

function loadMovies() {
  fetch("/api/movies")
    .then((res) => {
      if (!res.ok) throw new Error("HTTP error " + res.status);
      return res.json();
    })
    .then((data) => {
      const list = document.getElementById("movie-list");
      list.innerHTML = ""; // clear any previous movies

      if (data.length === 0) {
        list.innerHTML = "<p>目前沒有上映電影。</p>";
        return;
      }

      data.forEach((movie) => {
        const div = document.createElement("div");
        div.className = "movie-card";
		
		console.log("movie =", movie);
		console.log("movie.id =", movie.id);
		console.log("movie.movieId =", movie.movieId);
        div.innerHTML = `
          <h2>${movie.title}</h2>
          <p><strong>分級：</strong> ${movie.ratingCategory}</p>
          <p><strong>片長：</strong> ${movie.durationMinutes} 分鐘</p>
          <p><strong>簡介：</strong> ${movie.synopsis}</p>
          <a href="/booking-page?movieId=${movie.movieId}" class="button-link">立即訂票</a>
        `;

        list.appendChild(div);
      });
    })
    .catch((err) => {
      console.error("載入電影資料失敗", err);
      document.getElementById("movie-list").innerHTML =
        "<p style='color:red;'>無法載入電影資料，請稍後再試。</p>";
    });
}


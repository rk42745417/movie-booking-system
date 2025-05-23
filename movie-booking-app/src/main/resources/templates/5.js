fetch("movie_info.json")
      .then(res => res.json())
      .then(data => {
        const list = document.getElementById("movie-list");
        data.forEach(movie => {
          const div = document.createElement("div");
          div.className = "movie-card";
          div.innerHTML = `
            
            <h2>${movie.title_zh} (${movie.title_en})</h2>
            <p><strong>分級：</strong> ${movie.classification}</p>
            <p><strong>片長：</strong> ${movie.length} 分鐘</p>
            <p><strong>簡介：</strong> ${movie.summary}</p>
            <a href ="6訂票用戶_訂票（場次）.html" class="button-link"> 立即訂票 </a>
          `;
          list.appendChild(div);
        });
      })
      .catch(err => console.error("載入電影資料失敗", err));


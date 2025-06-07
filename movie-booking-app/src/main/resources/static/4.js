document.addEventListener("DOMContentLoaded", function () {
  // Handle menu button clicks
  const menuButtons = document.querySelectorAll(".menu-button");

  menuButtons.forEach(button => {
    button.addEventListener("click", function (event) {
      const text = button.textContent.trim();

      // Determine section from button text
      if (text.includes("上映電影")) {
        showSection("movies");
      } else if (text.includes("我要訂票")) {
        showSection("booking");
      } else if (text.includes("查詢訂單")) {
        showSection("orders");
      }
    });
  });

  // Optionally: Load section from session storage or URL hash (deep linking)
  const hash = window.location.hash;
  if (hash) {
    const section = hash.substring(1); // e.g., #booking → "booking"
    showSection(section);r
  }
});

// Utility function: Show a section by ID
function showSection(id) {
  // Hide all sections
  document.querySelectorAll('.section').forEach(sec => {
    sec.classList.remove('active');
  });

  // Show the selected section
  const sectionEl = document.getElementById(id);
  if (sectionEl) {
    sectionEl.classList.add('active');
  }

  // Optional: Update URL hash
  window.location.hash = id;
}



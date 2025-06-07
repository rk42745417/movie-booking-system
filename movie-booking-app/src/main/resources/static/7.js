function getMaxSeatsFromURL() {
  const params = new URLSearchParams(window.location.search);
  const count = parseInt(params.get('tickets'), 10);
  return isNaN(count) ? 3 : count; // default 3 if missing/invalid
}

const maxSeats = getMaxSeatsFromURL();
const selectedSeats = new Set();

function updateSelectedCount() {
  document.getElementById('selected-count').textContent = selectedSeats.size;
}

function renderSeats(seats) {
  const seatContainer = document.getElementById('seat-container');
  const rows = {};
  seats.forEach(seat => {
    if (!rows[seat.row]) rows[seat.row] = [];
    rows[seat.row].push(seat);
  });

  seatContainer.innerHTML = '';

  for (const row in rows) {
    const seatRowDiv = document.createElement('div');
    seatRowDiv.className = 'seat-row';

    const label = document.createElement('span');
    label.className = 'seat-label';
    label.textContent = row;
    seatRowDiv.appendChild(label);

    rows[row].forEach(seat => {
      const btn = document.createElement('button');
      btn.className = 'seat-btn';
      btn.textContent = seat.seatNum;
      btn.dataset.row = seat.row;
      btn.dataset.seatNum = seat.seatNum;

      if (seat.booked) {
        btn.disabled = true;
        btn.classList.add('booked');
      }

      btn.addEventListener('click', () => {
        if (btn.classList.contains('booked')) return;

        const seatId = `${seat.row}${seat.seatNum}`;
        if (selectedSeats.has(seatId)) {
          selectedSeats.delete(seatId);
          btn.classList.remove('selected');
        } else {
          if (selectedSeats.size < maxSeats) {
            selectedSeats.add(seatId);
            btn.classList.add('selected');
          } else {
            alert(`最多只能選擇 ${maxSeats} 張票`);
          }
        }
        updateSelectedCount();
      });

      seatRowDiv.appendChild(btn);
    });

    seatContainer.appendChild(seatRowDiv);
  }
}

function submitSeats() {
  if (selectedSeats.size === 0) {
    alert('請先選擇至少一個座位');
    return;
  }

  fetch('/api/submitSeats', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ selectedSeats: Array.from(selectedSeats) })
  })
  .then(res => {
    if (!res.ok) throw new Error('伺服器回應錯誤');
    return res.json();
  })
  .then(() => {
    window.location.href = 'confirm.html';
  })
  .catch(err => {
    alert('送出失敗，請稍後再試');
    console.error(err);
  });
}

// Initialize seats on DOM load
document.addEventListener('DOMContentLoaded', () => {
  fetch('big_room.json')  // <- Use big_room layout
    .then(res => res.json())
    .then(data => renderSeats(data))
    .catch(err => {
      console.error('無法讀取座位資料:', err);
    });

  document.getElementById('submit-seats').addEventListener('click', submitSeats);
});



console.log("헤더 js 연결");

function updateClock() {
  const now = new Date();

  const dateOptions = { year: 'numeric', month: '2-digit', day: '2-digit' };
  const timeOptions = { hour: '2-digit', minute: '2-digit', second: '2-digit' };

  const currentDate = now.toLocaleDateString('ko-KR', dateOptions);
  const currentTime = now.toLocaleTimeString('ko-KR', timeOptions);

  document.getElementById('current-date').textContent = currentDate;
  document.getElementById('current-time').textContent = currentTime;
}

// 페이지 로딩 시 바로 실행
updateClock();
// 1초마다 갱신
setInterval(updateClock, 1000);

document.addEventListener('DOMContentLoaded', function() {
  const menuToggle = document.getElementById('menu-toggle');

  menuToggle.addEventListener('click', function() {
    const menu = document.querySelector('.header-auth');
    const info = document.querySelector('.header-info');
    const clock = document.getElementById('header-clock');

    // 모바일에서는 숨겨져 있으니까 토글로 보이게
    menu.style.display = menu.style.display === 'flex' ? 'none' : 'flex';
    info.style.display = info.style.display === 'flex' ? 'none' : 'flex';
    clock.style.display = clock.style.display === 'block' ? 'none' : 'block';
  });
});
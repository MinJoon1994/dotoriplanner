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

function side_menu(){

    document.querySelector(".mobile-menu-icon").addEventListener("click",function(e){
        const sidebar = document.querySelector(".side-bar-hidden");
          if (sidebar.style.display === "flex") {
            sidebar.style.display = "none";
          } else {
            sidebar.style.display = "flex";
            sidebar.style.flexDirection = "column";
          }
    });

    document.querySelector(".menu-toggle").addEventListener("click",function(e){
        const sidebar = document.querySelector(".side-bar-hidden");
          if (sidebar.style.display === "flex") {
            sidebar.style.display = "none";
          } else {
            sidebar.style.display = "flex";
            sidebar.style.flexDirection = "column";
          }
    });

}

document.addEventListener('DOMContentLoaded', function() {
  var calendarEl = document.getElementById('calendar');

  var calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: 'dayGridMonth', // 월간 뷰
    locale: 'ko', // 한국어
    height: 'auto', // 높이 자동 맞춤
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: ''
    },
    dateClick: function(info) {
      alert(info.dateStr); // 클릭 시 날짜 출력
    }
  });

  calendar.render();
});

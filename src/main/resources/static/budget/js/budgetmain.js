document.addEventListener('DOMContentLoaded', function() {
  var calendarEl = document.getElementById('calendar');

  var calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: 'dayGridMonth',
    locale: 'ko',
    height: 'auto',
    dayMaxEventRows: true,
    headerToolbar: false,
    dayCellContent: function(arg) {
      return {
        html: String(arg.date.getDate()) // 날짜만 숫자로 출력
      };
    },
    dateClick:function(info){
      const clickedDate = info.dateStr;
      window.location.href= `/budget/daily/${clickedDate}`;
    },
    events: [
      { title: '-12,000원', start: '2025-07-03', display: 'block', className: 'expense-event' },
      { title: '+15,000원', start: '2025-07-03', display: 'block', className: 'income-event' },
      { title: '-8,000원', start: '2025-07-04', display: 'block', className: 'expense-event' }
    ]
  });

  calendar.render();
});

const totalBudget = parseInt(document.getElementById("totalBudget").value);
const fixedTotal = parseInt(document.getElementById("fixedTotal").value);
const etcTotal = parseInt(document.getElementById("etcTotal").value);
const budgetMonth = document.getElementById("budgetMonth").value;  // "2025-07"
const createdAt = document.getElementById("createdAt").value;      // "2025-07-05"

console.log("totalBudget: "+ totalBudget); //totalBudget: 3000000
console.log("fixedTotal: "+ fixedTotal);  //fixedTotal: 696800
console.log("etcTotal: "+ etcTotal);    //etcTotal: 146000
console.log("budgetMonth: "+ budgetMonth);  //budgetMonth: 2025-07
console.log("createdAt: "+createdAt); //createdAt: 2025-07-04T11:33:33.500813

const [year1, month1] = budgetMonth.split("-");
const formattedTitle = `${year1}년 ${month1}월`;

document.getElementById("budgetTitle").innerText=formattedTitle+" 가계부";

//날짜만 추출
const createdDateStr = createdAt.split("T")[0];
const createdDate = new Date(createdDateStr);

const year = createdDate.getFullYear();
const month = createdDate.getMonth() +1;

//이번달 마지막 날
const endOfMonth = new Date(year,month,0);

const remainingTime = endOfMonth.getTime() - createdDate.getTime();
const remainingDays = Math.max(0,Math.ceil(remainingTime/(1000*60*60*24))+1); //생성일 포함

//총 예산
const availableBudget = totalBudget - fixedTotal - etcTotal;
const totalSpent=  0; // 나중에 일일 소비 금액 합계 들어올 자리

const formattedAvailableBudget = availableBudget.toLocaleString();
document.getElementById("availableBudget").innerText=formattedAvailableBudget+"원";

//남은 예산
const remainingBudget = availableBudget - totalSpent;
const formattedRemainingBudget = remainingBudget.toLocaleString();
document.getElementById("remainingBudget").innerText=formattedRemainingBudget+"원";

//권장 일일 소비 금액
const recommendedDailySpend = remainingDays > 0 ? Math.floor(remainingBudget/remainingDays) : 0;

const formattedDailySpend = recommendedDailySpend.toLocaleString();

document.getElementById("recommendedDailySpend").innerText=formattedDailySpend+"원";

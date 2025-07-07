let fixedIdx = 1;
function addFixedExpenseRow() {
    const table = document.getElementById('fixedExpenseTable');
    const row = document.createElement('tr');
    row.innerHTML = `
      <td><input type="text" name="fixedExpenses[${fixedIdx}].name" class="form-control" required></td>
      <td>
        <select name="fixedExpenses[${fixedIdx}].category" class="form-select" required>
          <option value="">선택</option>
          <option value="주거">주거</option>
          <option value="통신">통신</option>
          <option value="구독">구독</option>
          <option value="보험">보험</option>
          <option value="교통">교통</option>
          <option value="교육">교육</option>
          <option value="금융">금융</option>
          <option value="기타">기타</option>
        </select>
      </td>
      <td><input type="number" name="fixedExpenses[${fixedIdx}].amount" class="form-control" required></td>
      <td><button type="button" class="delete-btn" onclick="removeRow(this)">삭제</button></td>
    `;
    table.appendChild(row);
    fixedIdx++;
}

let miscIdx = 1;
function addMiscExpenseRow() {
    const table = document.getElementById('miscExpenseTable');
    const row = document.createElement('tr');
    row.innerHTML = `
      <td><input type="text" name="miscExpenses[${miscIdx}].name" class="form-control" required></td>
      <td><input type="number" name="miscExpenses[${miscIdx}].amount" class="form-control" required></td>
      <td><input type="date" name="miscExpenses[${miscIdx}].spendDate" class="form-control"></td>
      <td><button type="button" class="delete-btn" onclick="removeRow(this)">삭제</button></td>
    `;
    table.appendChild(row);
    miscIdx++;
}

function removeRow(btn) {
    btn.closest('tr').remove();
}

document.addEventListener("DOMContentLoaded", () => {
    const monthInput = document.getElementById("budgetMonth");
    if (monthInput) {
        const today = new Date();
        const year = today.getFullYear();
        const month = String(today.getMonth() + 1).padStart(2, "0");
        monthInput.value = `${year}-${month}`;
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const now = new Date();
    const month = now.getMonth() + 1;
    document.getElementById("budgetTitle").innerText = `${month}월 예산 설정하기`;
});

document.getElementById("totalBudget").addEventListener("input", function(e) {
    let value = e.target.value;

    // 숫자만 남기기
    value = value.replace(/[^0-9]/g, "");

    // 세 자리마다 콤마
    value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ",");

    e.target.value = value;
});

// 서버에 전송할 땐 콤마 제거해서 실제 숫자로 전송
document.querySelector("form").addEventListener("submit", function() {
    const input = document.getElementById("totalBudget");
    input.value = input.value.replace(/,/g, "");
});
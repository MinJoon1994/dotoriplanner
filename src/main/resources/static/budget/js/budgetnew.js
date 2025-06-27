let fixedIdx = 1;
function addFixedExpenseRow() {
    const table = document.getElementById('fixedExpenseTable');
    const row = document.createElement('tr');
    row.innerHTML = `
      <td><input type="text" name="fixedExpenses[${fixedIdx}].name" class="form-control" required></td>
      <td>
        <select name="fixedExpenses[${fixedIdx}].category" class="form-select" required>
          <option value="">선택</option>
          <option value="HOUSING">주거</option>
          <option value="COMMUNICATION">통신</option>
          <option value="SUBSCRIPTION">구독</option>
          <option value="INSURANCE">보험</option>
          <option value="TRANSPORT">교통</option>
          <option value="EDUCATION">교육</option>
          <option value="FINANCE">금융</option>
          <option value="ETC">기타</option>
        </select>
      </td>
      <td><input type="number" name="fixedExpenses[${fixedIdx}].amount" class="form-control" required></td>
      <td><button type="button" class="btn btn-danger" onclick="removeRow(this)">삭제</button></td>
    `;
    table.appendChild(row);
    fixedIdx++;
}

let miscIdx = 1;
function addMiscExpenseRow() {
    const table = document.getElementById('miscExpenseTable');
    const row = document.createElement('tr');
    row.innerHTML = `
      <td><input type="text" name="etcExpenses[${miscIdx}].name" class="form-control" required></td>
      <td><input type="number" name="etcExpenses[${miscIdx}].amount" class="form-control" required></td>
      <td><input type="date" name="etcExpenses[${miscIdx}].date" class="form-control"></td>
      <td><button type="button" class="btn btn-danger" onclick="removeRow(this)">삭제</button></td>
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
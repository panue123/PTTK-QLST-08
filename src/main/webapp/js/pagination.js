document.addEventListener("DOMContentLoaded", function () {
    const rowsPerPage = 3;
    const table = document.getElementById("tableData");
    const pagination = document.getElementById("pagination");

    if (!table || !pagination) return;

    const rows = table.querySelectorAll("tbody tr");
    const totalRows = rows.length;
    const totalPages = Math.ceil(totalRows / rowsPerPage);

    function renderPagination() {
        pagination.innerHTML = "";

        for (let i = 1; i <= totalPages; i++) {
            const btn = document.createElement("button");
            btn.textContent = i;
            btn.classList.add("page-btn");
            btn.onclick = function () {
                showPage(i);
            };
            pagination.appendChild(btn);
        }
    }

    window.showPage = function (page) {
        currentPage = page;
        const start = (page - 1) * rowsPerPage;
        const end = start + rowsPerPage;

        rows.forEach((row, index) => {
            row.style.display = index >= start && index < end ? "" : "none";
        });

        const buttons = pagination.querySelectorAll("button");
        buttons.forEach((btn, idx) => {
            if (idx === page - 1) btn.classList.add("active");
            else btn.classList.remove("active");
        });
    };

    renderPagination();
    showPage(1);
});

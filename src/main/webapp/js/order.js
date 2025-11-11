function addToCart(productId) {
    const qty = document.getElementById(`qty-${productId}`).value;

    fetch(`${contextPath}/OrderOnlineServlet?action=add&productId=${productId}&qty=${qty}`, {
        method: "GET",
        headers: { "X-Requested-With": "XMLHttpRequest" }
    })
        .then(res => {
            if (!res.ok) throw new Error("Network response was not ok");
            return res.json();
        })
        .then(data => {
            showMessage(data.msg, "success");
        })
        .catch(err => {
            console.error("Error:", err);
            showMessage("Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng!", "error");
        });
}

function showMessage(message, type) {
    const box = document.getElementById("messageBox");
    box.textContent = message;
    box.style.display = "block";
    box.style.transition = "all 0.3s ease";

    if (type === "success") {
        box.style.background = "#e8f5e9";
        box.style.border = "1px solid #4caf50";
        box.style.color = "#2e7d32";
    } else {
        box.style.background = "#ffebee";
        box.style.border = "1px solid #f44336";
        box.style.color = "#c62828";
    }
    setTimeout(() => {
        box.style.opacity = "0";
        setTimeout(() => {
            box.style.display = "none";
            box.style.opacity = "1";
        }, 600);
    }, 6000);
}

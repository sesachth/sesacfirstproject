// 데이터 가져오기 및 페이지 이동 기능
function navigateWithParams(targetPage) {
    const data = {
        title: document.getElementById("title").textContent,
        completeMessage: document.getElementById("completeMessage").textContent,
        vehicleNumber: document.getElementById("vehicleNumber").textContent.replace("차량 번호: ", ""),
        date: document.getElementById("date").textContent,
        vehicleType: document.getElementById("vehicleType").textContent,
        paymentAmount: document.getElementById("paymentAmount").textContent.replace("결제 금액: ", ""),
        balance: document.getElementById("balance").textContent.replace("현재 잔액: ", ""),
        unpaidAmount: document.getElementById("unpaidAmount").textContent.replace("미납 금액: ", "")
    };

    // URLSearchParams를 사용하여 데이터를 URL에 추가
    const params = new URLSearchParams(data).toString();

    // 데이터가 포함된 URL로 이동
    window.location.href = `${targetPage}?${params}`;
}

// 브라우저 인쇄 기능 실행
function printReceipt() {
    window.print(); // 브라우저 인쇄창 호출
}
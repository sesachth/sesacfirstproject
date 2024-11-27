// 통행료 게이트 목록 (위치 정보 포함)
const tollgates = [
    { tollgate_id: 101, tollgate_name: "서울", latitude: 50, longitude: 10, basic_fee: 600, is_entry: 1 },
    { tollgate_id: 102, tollgate_name: "대전", latitude: 30, longitude: 20, basic_fee: 450, is_entry: 1 },
    { tollgate_id: 103, tollgate_name: "구미", latitude: 35, longitude: 30, basic_fee: 400, is_entry: 1 },
    { tollgate_id: 104, tollgate_name: "전주", latitude: 10, longitude: 15, basic_fee: 500, is_entry: 0 },
    { tollgate_id: 105, tollgate_name: "대구", latitude: 15, longitude: 40, basic_fee: 400, is_entry: 1 },
    { tollgate_id: 106, tollgate_name: "춘천", latitude: 55, longitude: 25, basic_fee: 450, is_entry: 1 },
    { tollgate_id: 107, tollgate_name: "안양", latitude: 40, longitude: 5, basic_fee: 600, is_entry: 1 },
    { tollgate_id: 108, tollgate_name: "포항", latitude: 30, longitude: 60, basic_fee: 500, is_entry: 1 },
    { tollgate_id: 109, tollgate_name: "부산", latitude: 10, longitude: 50, basic_fee: 550, is_entry: 0 },
    { tollgate_id: 110, tollgate_name: "강릉", latitude: 60, longitude: 55, basic_fee: 400, is_entry: 0 },
];

// 주행 경로 정의
const drivingPath = ["안양", "전주", "포항", "대전"];

// Canvas 엘리먼트와 그리기 컨텍스트 초기화
const canvas = document.getElementById('tollgateCanvas');
const ctx = canvas.getContext('2d');

// 애니메이션 속도
const animationSpeed = 0.02;

// 실제 해상도 증가를 위한 스케일 설정
const canvasScale = 2; // 전체 확대 스케일
const canvasWidth = 720 * canvasScale; // 확대
const canvasHeight = 720 * canvasScale; // 확대
canvas.width = canvasWidth;
canvas.height = canvasHeight;

// CSS 크기를 원래대로 조정 (확대를 시각적으로 숨김)
canvas.style.width = "720px";
canvas.style.height = "720px";

// 스타일 설정
const circleRadius = 40 * canvasScale;
const fontSize = 23 * canvasScale;
const lineWidth = 5 * canvasScale;
const arrowLength = 20 * canvasScale; // 화살표 (머리) 길이

// 색상 설정
const colors = {
    blank: "#FFFFFF", // 배경 색상
    defaultTollgate: "#2C3E50", // 일반 원 색상
    highlightedTollgate: "#1E90FF", // 경로 포함 원 색상
    arrow: "#DC143C" // 화살표 색상
};

// 스케일 조정 값 계산
const latScale = (canvasHeight - 40) / 70;
const longScale = (canvasWidth - 40) / 70;
const margin = 0;

// 위도와 경도를 Canvas 좌표로 변환하는 함수
const getCoordinates = (latitude, longitude) => {
    const x = margin + longitude * longScale;
    const y = canvasHeight - margin - latitude * latScale;
    return { x, y };
};

// 개별 톨게이트를 원과 이름으로 그리기
const drawTollgate = (tollgate, color) => {
    const { x, y } = getCoordinates(tollgate.latitude, tollgate.longitude);

    // 원 그리기
    ctx.beginPath();
    ctx.arc(x, y, circleRadius, 0, Math.PI * 2);
    ctx.fillStyle = color;
    ctx.fill();
    ctx.closePath();

    // 원 안에 이름 텍스트 그리기
    ctx.font = `bold ${fontSize}px Nanum Gothic`;
    ctx.fillStyle = "white";
    ctx.textAlign = "center";
    ctx.textBaseline = "middle";
    ctx.fillText(tollgate.tollgate_name, x, y);
};

// 모든 톨게이트를 그리기
const drawTollgates = () => {
    tollgates.forEach(tollgate => {
        // 각 톨게이트의 색상을 전달
        drawTollgate(tollgate, colors.defaultTollgate);
    });
};

// 화살표를 그리는 함수 (애니메이션 지원)
const drawArrowAnimated = (startCoords, endCoords, callback) => {
    const angle = Math.atan2(endCoords.y - startCoords.y, endCoords.x - startCoords.x);

    // 원 경계를 고려한 시작점과 끝점 계산
    const startX = startCoords.x + circleRadius * Math.cos(angle);
    const startY = startCoords.y + circleRadius * Math.sin(angle);
    const endX = endCoords.x - (circleRadius + arrowLength / 2) * Math.cos(angle); // 화살표 머리 길이만큼 줄임
    const endY = endCoords.y - (circleRadius + arrowLength / 2) * Math.sin(angle);
    const endXhead = endCoords.x - circleRadius * Math.cos(angle); // 원 경계에 맞게 다시 계산
    const endYhead = endCoords.y - circleRadius * Math.sin(angle);
    
    const distance = Math.hypot(endX - startX, endY - startY);
    let progress = 0;

    const animate = () => {
        progress += animationSpeed;
        if (progress > 1) progress = 1;

        // 현재 진행 상태에 따른 끝점 계산
        const currentX = startX + progress * (endX - startX);
        const currentY = startY + progress * (endY - startY);

        // 선 그리기
        ctx.strokeStyle = colors.arrow;
        ctx.lineWidth = lineWidth;
        ctx.beginPath();
        ctx.moveTo(startX, startY);
        ctx.lineTo(currentX, currentY);
        ctx.stroke();

        // 애니메이션 진행 중이면 다시 호출
        if (progress < 1) {
            requestAnimationFrame(animate);
        } else {
            // 애니메이션 완료 시 화살표 머리와 원 색상 변경
            drawArrowHead({ x: endXhead, y: endYhead }, angle);

            if (callback) callback(); // 다음 애니메이션 호출
        }
    };

    animate();
};

// 화살표 머리 그리기
const drawArrowHead = (endCoords, angle) => {
    ctx.fillStyle = colors.arrow;
    ctx.beginPath();
    ctx.moveTo(endCoords.x, endCoords.y);
    ctx.lineTo(
        endCoords.x - arrowLength * Math.cos(angle - Math.PI / 6),
        endCoords.y - arrowLength * Math.sin(angle - Math.PI / 6)
    );
    ctx.lineTo(
        endCoords.x - arrowLength * Math.cos(angle + Math.PI / 6),
        endCoords.y - arrowLength * Math.sin(angle + Math.PI / 6)
    );
    ctx.lineTo(endCoords.x, endCoords.y);
    ctx.fill();
};

// 주행 경로를 따라 애니메이션으로 화살표 그리기 (애니메이션 반복 출력 수정)
const drawDrivingPathAnimated = (index = 0) => {
    if (index >= drivingPath.length - 1) {
        // 마지막 톨게이트를 하이라이트
        const lastTollgate = tollgates.find(tollgate => tollgate.tollgate_name === drivingPath[index]);
        if (lastTollgate) {
            drawTollgate(lastTollgate, colors.highlightedTollgate); // 마지막 톨게이트 다시 그리기
        }

        // 애니메이션이 끝났으므로 새 경로 생성 및 재시작
        setTimeout(() => startRandomPathAnimation(), 1000); // 0.5초 대기 후 시작
        return;
    }

    const startTollgate = tollgates.find(tollgate => tollgate.tollgate_name === drivingPath[index]);
    const endTollgate = tollgates.find(tollgate => tollgate.tollgate_name === drivingPath[index + 1]);

    if (startTollgate && endTollgate) {
        const startCoords = getCoordinates(startTollgate.latitude, startTollgate.longitude);
        const endCoords = getCoordinates(endTollgate.latitude, endTollgate.longitude);

        drawTollgate(startTollgate, colors.highlightedTollgate); // 시작 톨게이트 다시 그리기

        drawArrowAnimated(startCoords, endCoords, () => drawDrivingPathAnimated(index + 1));
    }
};

// 초기 원 그리기 및 애니메이션 시작
// drawTollgates();
// drawDrivingPathAnimated();

// 랜덤 주행 경로 생성 함수
const generateRandomPath = () => {
    const pathLength = Math.floor(Math.random() * 3) + 3; // 경로 길이: 3~5
    const selectedTollgates = [];

    while (selectedTollgates.length < pathLength) {
        const randomIndex = Math.floor(Math.random() * tollgates.length);
        const selected = tollgates[randomIndex].tollgate_name;

        // 중복 방지
        if (!selectedTollgates.includes(selected)) {
            selectedTollgates.push(selected);
        }
    }

    return selectedTollgates;
};

// 애니메이션 시작 함수
const startRandomPathAnimation = () => {
    // Canvas 초기화
    ctx.fillStyle = colors.blank;
    ctx.fillRect(0, 0, canvasWidth, canvasHeight);

    // 톨게이트 전체 그리기
    drawTollgates();

    // 랜덤 경로 생성 및 애니메이션 실행
    const randomPath = generateRandomPath();
    console.log("New Path:", randomPath);
    drivingPath.length = 0; // 기존 경로 초기화
    drivingPath.push(...randomPath); // 새 경로로 업데이트
    drawDrivingPathAnimated(0);
};

// 실행
startRandomPathAnimation();
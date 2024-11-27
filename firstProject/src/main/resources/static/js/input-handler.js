function removeInput() {
    // 특정 input 요소를 제거
    const unwantedField = document.querySelector('input[name="password"]');
    if (unwantedField) {
        unwantedField.remove(); // DOM에서 해당 요소 제거
    }
}

/*
document.addEventListener('DOMContentLoaded', function () {
    const popup = document.getElementById('popup');
    const closePopupButton = document.getElementById('close-popup');

    closePopupButton.addEventListener('click', function () {
        popup.classList.add('hidden');
    });

    const loginButton = document.querySelector('.signin-button');
    loginButton.addEventListener('click', async function (event) {
        event.preventDefault();

        const username = document.querySelector('input[name="username"]').value;

        try {
            const response = await fetch('/egate/input', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({ username })
            });

            const isLoginSuccessful = await response.json();

            if (isLoginSuccessful) {
                window.location.href = "/egate/output?userName=" + encodeURIComponent(username);
            } else {
                popup.textContent = "failed";
                popup.classList.remove('hidden.');
            }
        } catch (error) {
            console.error("Error during login:", error);
            popup.textContent = "failed.";
            popup.classList.remove('hidden');
        }
    });
});
*/
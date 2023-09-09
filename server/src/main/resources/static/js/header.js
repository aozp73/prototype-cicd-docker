// 스크롤 시 navbar 출렁이는 효과
window.addEventListener('scroll', function() {
    let navbar = document.querySelector('.navbar');
    if (window.scrollY > 50) { 
        navbar.classList.add('scrolled');
    } else {
        navbar.classList.remove('scrolled');
    }
});


function checkIfLoggedIn() {
    return localStorage.getItem('jwtToken') !== null;
}
window.onload = function() {
    // 토큰이 만료되었다면 제거
    checkAndRemoveExpiredToken();

    const jwtToken = localStorage.getItem('jwtToken'); 

    // 토큰이 있다면,
    if (jwtToken) {
        $.ajax({
            url: '/validateToken',
            type: 'GET',
            headers: {
                'Authorization': jwtToken  
            },
            // 토큰 검증 후, 관리자가 수정할 수 있는 버튼 랜더링
            success: function(response) {
                if (response === true) {
                    showEditControls();
                } else {
                    hideEditControls();
                }
            },
            error: function(error) {
                console.log(error);
                hideEditControls();
            }
        });
    } else {
        hideEditControls();
    }
};

function showEditControls() {
    document.querySelector('.edit-button').style.display = 'block';
    document.querySelector('.logout-button').style.display = 'block';
    const controls = document.querySelectorAll('.edit-controls');
    controls.forEach(control => {
        control.style.display = 'block';
    });
}

function hideEditControls() {
    document.querySelector('.edit-button').style.display = 'none';
    document.querySelector('.logout-button').style.display = 'none';
    const controls = document.querySelectorAll('.edit-controls');
    controls.forEach(control => {
        control.style.display = 'none';
    });
}
function logout() {
    localStorage.removeItem('jwtToken'); 
    location.reload(); // 현재 페이지를 새로고침하여 UI 변경 반영
}

function isTokenExpired(token) {
    try {
        const [, payload,] = token.split('.');
        
        // Base64에서 JSON 문자열로 디코딩
        const decodedPayload = atob(payload);
        
        // JSON 문자열을 JavaScript 객체로 변환
        const { exp } = JSON.parse(decodedPayload);

        // 현재 시간을 가져와 JWT의 exp와 비교
        const currentUnixTime = Math.floor(Date.now() / 1000);
        return currentUnixTime > exp;
    } catch (e) {
        console.error("Error decoding JWT:", e);
        return false;
    }
}

// 토큰이 만료되었는지 확인하고 만료되면 삭제
function checkAndRemoveExpiredToken() {
    const token = localStorage.getItem('jwtToken');
    if (token && isTokenExpired(token)) {
        localStorage.removeItem('jwtToken');
        alert("로그아웃")
    }
}
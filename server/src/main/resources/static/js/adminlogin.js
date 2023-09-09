window.addEventListener('scroll', function() {
    let navbar = document.querySelector('.navbar');
    if (window.scrollY > 50) { 
        navbar.classList.add('scrolled');
    } else {
        navbar.classList.remove('scrolled');
    }
});

function login() {
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    var loginDTO = {
        email: email,
        password: password
    };
    console.log(loginDTO)
    $.ajax({
        url: '/login',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(loginDTO),

        success: function(data, textStatus, jqXHR) {
            const jwtToken = jqXHR.getResponseHeader('Authorization'); 
            if (jwtToken) {
                // 올바른 로그인 정보를 입력하였다면, Service에서 DB 조회 후 토큰을 Header에 넣어서 응답함
                // 해당 토큰 저장
                localStorage.setItem('jwtToken', jwtToken);
                window.location.href = '/mainpage'; 
            } else {
                alert("내부 서버에러 발생 (code 1)")
            }
        },
        error: function(error) {
            alert(error.responseJSON.data)
        }
    });
}
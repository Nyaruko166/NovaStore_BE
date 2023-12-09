document.addEventListener('DOMContentLoaded', function () {
    // Kiểm tra nếu có giá trị trong Local Storage và đặt nó vào trường input
    let storedEmailValue = localStorage.getItem('emailValue');
    if (storedEmailValue) {
        document.getElementById('email').value = storedEmailValue;
        localStorage.removeItem('emailValue');
    }
});

function validateEmailC() {
    // Lấy thẻ input bằng ID
    let emailInput = document.getElementById('email');
    let emailError = document.getElementById('email-error');
    // Lấy giá trị từ thẻ input
    let emailValue = emailInput.value;

    // Kiểm tra xem giá trị có trống không
    if (emailValue.trim() === '') {
        emailError.textContent = 'Vui lòng nhập địa chỉ email.';
        return;
    }

    // Kiểm tra xem giá trị có phải là địa chỉ email hợp lệ không
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(emailValue)) {
        emailError.textContent = 'Vui lòng nhập địa chỉ email hợp lệ.';
        return;
    }

    // Nếu giá trị hợp lệ, có thể thực hiện các hành động khác ở đây
    emailError.textContent = '';

    // Tạo một đối tượng URL từ URL hiện tại
    let urlObject = new URL(window.location.href);

    // Cắt bỏ phần query string
    urlObject.search = '';

    // Lấy URL mới
    let newUrl = urlObject.href;

    // Hiển thị URL mới trong console (hoặc bạn có thể sử dụng newUrl theo cách khác)

    let redirectUrl = newUrl + '?email=' + encodeURIComponent(emailValue);

    console.log('redirectUrl:', redirectUrl);
    localStorage.setItem('emailValue', emailValue);
    window.location.href = redirectUrl;
}

function validateSubmitC() {

    let emailInput = document.getElementById('email');
    let emailError = document.getElementById('email-error');

    let codeInput = document.getElementById('code');
    let codeError = document.getElementById('code-error');

    let currentPassInput = document.getElementById('currentPass');
    let currentPassError = document.getElementById('currentPass-error');

    let newPasswordInput = document.getElementById('newPass');
    let newPasswordError = document.getElementById('newPass-error');

    let reNewPassInput = document.getElementById('reNewPass');
    let reNewPassError = document.getElementById('reNewPass-error');

    if (emailInput.value.trim() === '') {
        emailError.textContent = 'Vui lòng nhập địa chỉ email.';
        return false;
    }

    // Kiểm tra xem giá trị có phải là địa chỉ email hợp lệ không
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(emailInput.value)) {
        emailError.textContent = 'Vui lòng nhập địa chỉ email hợp lệ.';
        return false;
    }

    emailError.textContent = '';

    if (codeInput.value.trim() === '') {
        codeError.textContent = 'Vui lòng nhập mã xác minh.';
        return false;
    }

    codeError.textContent = '';

    if (currentPassInput.value.trim() === '') {
        currentPassError.textContent = 'Mật khẩu hiện tại đang trống.';
        return false;
    }

    currentPassError.textContent = '';

    if (newPasswordInput.value.trim() === '') {
        newPasswordError.textContent = 'Mật khẩu mới đang trống.';
        return false;
    }

    newPasswordError.textContent = '';

    if (reNewPassInput.value.trim() === '') {
        reNewPassError.textContent = 'Hãy xác nhận mật khẩu mới.';
        return false;
    }

    if (reNewPassInput.value !== newPasswordInput.value) {
        reNewPassError.textContent = 'Mật khẩu mới không khớp.';
        return false;
    }

    reNewPassError.textContent = '';

    return true;
}
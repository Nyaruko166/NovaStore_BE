document.addEventListener('DOMContentLoaded', function () {
    // Kiểm tra nếu có giá trị trong Local Storage và đặt nó vào trường input
    let storedEmailValue = localStorage.getItem('emailValue');
    if (storedEmailValue) {
        document.getElementById('email').value = storedEmailValue;
        localStorage.removeItem('emailValue');
    }
});

function validateEmail() {
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

function validateSubmit() {

    let emailInput = document.getElementById('email');
    let emailError = document.getElementById('email-error');

    let codeInput = document.getElementById('code');
    let codeError = document.getElementById('code-error');

    let newPasswordInput = document.getElementById('newPassword');
    let newPasswordError = document.getElementById('newPass-error');

    let rePasswordInput = document.getElementById('rePassword');
    let rePasswordError = document.getElementById('rePassword-error');

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

    if (newPasswordInput.value.trim() === '') {
        newPasswordError.textContent = 'Vui lòng nhập mật khẩu mới.';
        return false;
    }

    newPasswordError.textContent = '';

    if (rePasswordInput.value.trim() === '') {
        rePasswordError.textContent = 'Hãy xác nhận mật khẩu mới.';
        return false;
    }

    rePasswordError.textContent = '';

    if (!newPasswordInput.value === rePasswordInput.value) {
        rePasswordError.textContent = 'Mật khẩu không khớp.';
        return false;
    }
    return true;
}
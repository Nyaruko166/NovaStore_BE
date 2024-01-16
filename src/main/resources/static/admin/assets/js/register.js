function validateReg() {

    let nameInput = document.getElementById('name');
    let nameError = document.getElementById('name-error');

    let emailInput = document.getElementById('email');
    let emailError = document.getElementById('email-error');

    let sdtInput = document.getElementById('sdt');
    let sdtError = document.getElementById('sdt-error');

    let passwordInput = document.getElementById('password');
    let passwordError = document.getElementById('password-error');

    let rePasswordInput = document.getElementById('rePassword');
    let rePasswordError = document.getElementById('rePassword-error');

    if (nameInput.value.trim() === '') {
        nameError.textContent = 'Vui lòng nhập họ tên.';
        return false;
    }

    nameError.textContent = '';

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

    const phoneNumberRegex = /^(0[1-9]|84[1-9]|\\+84[1-9])[0-9]{8}$/;

    if (sdtInput.value.trim() === '') {
        sdtError.textContent = 'Vui lòng nhập số điện thoại.';
        return false;
    }

    if (!phoneNumberRegex.test(sdtInput.value.trim())) {
        sdtError.textContent = 'Vui lòng nhập đúng số điện thoại.';
        return false;
    }

    // const sdtRegex = /(84|0[3|5|7|8|9])+([0-9]{8})\b/;
    // if (sdtInput.value.trim() === '') {
    //     sdtError.textContent = 'Vui lòng nhập số điện thoại.';
    //     return false;
    // }

    sdtError.textContent = '';

    if (passwordInput.value.trim() === '') {
        passwordError.textContent = 'Vui lòng nhập mật khẩu.';
        return false;
    }

    passwordError.textContent = '';

    if (rePasswordInput.value.trim() === '') {
        rePasswordError.textContent = 'Vui lòng nhập lại mật khẩu.';
        return false;
    }

    rePasswordError.textContent = '';
    return true;
}
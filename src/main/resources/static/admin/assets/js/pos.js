var lastResult, countResults = 0;

function init() {
    const scanner = new Html5QrcodeScanner('reader', {
        // Scanner will be initialized in DOM inside element with id of 'reader'
        qrbox: {
            width: 250,
            height: 250
        },  // Sets dimensions of scanning box (set relative to reader element width)
        fps: 30, // Frames per second to attempt a scan
    });


    scanner.render(onScanSuccess);
}

// Starts scanner

function onScanSuccess(decodedText) {
    if (decodedText !== lastResult) {
        ++countResults;
        lastResult = decodedText;
        // Handle on success condition with the decoded message.
        let queryString = window.location.search;
        let urlParam = new URLSearchParams(queryString);
        let billId;
        if (!urlParam.has('billId')) {
            billId = 0;
        } else {
            billId = urlParam.get('billId');
        }

        // console.log(billId);

        fetch("/nova/pos/add", {
            method: "POST",
            body: JSON.stringify({
                billId: billId,
                data: decodedText,
                qty: 1
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        }).then(function (response) {
            if (response.status === 418) {
                Swal.fire({
                    icon: "error",
                    title: "Vui lòng kiểm tra lại số lượng",
                    text: "Số lượng tồn kho không đủ!",
                });
            } else {
                updateOutput();
            }
        });
    }
}

function updateOutput() {
    $.post("/nova/pos/frag").done(function (fragment) {
        // console.log(fragment);
        $("#output").replaceWith(fragment);
    });

    $.post("/nova/pos/frag-checkout").done(function (fragment_checkout) {
        // console.log(fragment);
        // console.log('change checkout')
        $("#output_checkout").replaceWith(fragment_checkout);
    });
}

function searchAndReplace() {
    let keyword = document.getElementById('keyword').value;
    let url = "/nova/pos/api/filter?keyword=" + keyword;

    $.post(url).done(function (fragment_modal) {
        // console.log(fragment);
        $("#modal_replace").replaceWith(fragment_modal);
    });
}

function searchAndReplaceProduct() {
    let keyword = document.getElementById('keywordProduct').value;
    // console.log(keyword)
    let url = "/nova/pos/api/productFilter?keyword=" + keyword;

    $.post(url).done(function (fragment_product) {
        // console.log(fragment_product);
        $("#modal_product_replace").replaceWith(fragment_product);
    });
}

async function addItems(obj) {

    let parts = obj.value.split('|');

    let productCode = parts[0].trim();
    let qtyInStock = Number(parts[1].trim());

    // console.log(productCode)
    // console.log(qtyInStock)

    const {value: qty} = await Swal.fire({
        title: "Nhập số lượng",
        input: "number",
        showCancelButton: true,
        inputValidator: (value) => {
            if (!value) {
                return "Vui lòng nhập số lượng!";
            } else if (Math.sign(Number(value)) !== 1) {
                return "Vui lòng nhập số nguyên dương!";
            } else if (Number(value) > qtyInStock) {
                return "Số lượng trong kho không đủ!";
            }
        }
    });

    if (qty) {

        let queryString = window.location.search;
        let urlParam = new URLSearchParams(queryString);
        let billId;
        if (!urlParam.has('billId')) {
            billId = 0;
        } else {
            billId = urlParam.get('billId');
        }

        fetch("/nova/pos/add", {
            method: "POST",
            body: JSON.stringify({
                billId: billId,
                data: productCode,
                qty: qty
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        }).then(function (response) {
            if (response.status === 418) {
                Swal.fire({
                    icon: "error",
                    title: "Vui lòng kiểm tra lại số lượng",
                    text: "Số lượng tồn kho không đủ!",
                });
            } else {
                updateOutput();
            }
        });
    }
}

function isNumeric(str) {
    if (typeof str != "string") return false // we only process strings!
    return !isNaN(str) && // use type coercion to parse the _entirety_ of the string (`parseFloat` alone does not do this)...
        !isNaN(parseFloat(str)) // ...and ensure strings of whitespace fail
}

function calCashBack() {

    let khachPhaiTra = document.getElementById('khachPhaiTra').innerText.match(/\d+/);

    let khachDua = document.getElementById('khachDua').value;

    document.getElementById('tienThua').innerText = khachDua - khachPhaiTra + ' VNĐ'
}

function thanhToan() {
    let selectedValue;
    let radios = document.getElementsByName('mode');
    for (i = 0; i < radios.length; i++) {
        if (radios[i].checked)
            selectedValue = radios[i].value;
    }

    if (selectedValue === 'vietqr') {

        let khachPhaiTra = document.getElementById('khachPhaiTra').innerText.match(/\d+/);
        let billCode = document.getElementById('billCode').innerText;

        fetch("https://api.vietqr.io/v2/generate", {
            method: "POST",
            body: JSON.stringify({
                accountNo: "0936163632",
                accountName: "PHUNG MINH QUAN",
                acqId: 970422,
                amount: khachPhaiTra,
                addInfo: "Nova Store - Thanh toan hoa don #" + billCode,
                format: "text",
                template: "compact2"
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        }).then((response) => response.json())
            .then((json) => {
                let dataUri = json.data.qrDataURL;
                showImagePopup(dataUri)
            });

    } else {

        let khachPhaiTra = document.getElementById('khachPhaiTra').innerText.match(/\d+/);
        let khachDua = document.getElementById('khachDua').value;

        // let queryString = window.location.search;
        // let urlParam = new URLSearchParams(queryString);
        // let check;
        // if (!urlParam.has('check')) {
        //     check = 0;
        // } else {
        //     check = urlParam.get('check');
        // }

        if (Number(khachDua) >= Number(khachPhaiTra[0])) {
            Swal.fire({
                title: "Thanh toán thành công!",
                text: "Bạn đã thanh toán thành công!",
                icon: "success"
            }).then(openPopUp);
        } else {
            Swal.fire({
                icon: "error",
                title: "Khách đưa thiếu tiền",
                text: "Vui lòng kiểm tra lại!",
            });
        }
    }

}

function reloadPage() {
    location.href = '/nova/pos';
}

function openPopUp() {
    const width = 800;
    const height = 1000;

    // Tính toán vị trí trung tâm
    let left = (window.innerWidth - width) / 2;
    let top = (window.innerHeight - height) / 2;

    // Các tùy chọn của cửa sổ pop-up
    let options = 'width=' + width + ',height=' + height + ',top=' + top + ',left=' + left;

    // Mở cửa sổ pop-up và điều hướng đến liên kết "/nova/pos/checkout"
    window.open('/nova/pos/checkout', '_blank', options);
}

function showImagePopup(imageDataURI) {
    var popup = document.getElementById("imagePopup");
    var overlay = document.getElementById("overlay");
    var popupImage = document.getElementById("popupImage");

    // Set the image source
    popupImage.src = imageDataURI;

    // Show the popup and overlay
    popup.style.display = "block";
    overlay.style.display = "block";
}

// Function to close the popup
function closeImagePopup() {
    var popup = document.getElementById("imagePopup");
    var overlay = document.getElementById("overlay");

    // Hide the popup and overlay
    popup.style.display = "none";
    overlay.style.display = "none";
}

function updateCart(obj) {
    let productCode = obj.id;
    let qty = obj.value;
    let defaultQty = obj.name;

    if (qty <= 0) {
        Swal.fire({
            icon: "error",
            title: "Vui lòng kiểm tra lại số lượng",
            text: "Vui lòng nhập số nguyên dương!",
        });
        document.getElementById(productCode).value = defaultQty;
    } else {
        let queryString = window.location.search;
        let urlParam = new URLSearchParams(queryString);
        let billId;
        if (!urlParam.has('billId')) {
            billId = 0;
        } else {
            billId = urlParam.get('billId');
        }

        fetch("/nova/pos/update", {
            method: "POST",
            body: JSON.stringify({
                billId: billId,
                data: productCode,
                qty: qty
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        }).then(function (response) {
            if (response.status === 418) {
                Swal.fire({
                    icon: "error",
                    title: "Vui lòng kiểm tra lại số lượng",
                    text: "Số lượng tồn kho không đủ!",
                });
                document.getElementById(productCode).value = defaultQty;
            } else {
                updateOutput();
            }
        });
    }

    // console.log(productCode);
    // console.log(qty);
}
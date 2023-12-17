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
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        }).then(updateOutput);
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
    let url = "/nova/account/api/filter?keyword=" + keyword;

    $.post(url).done(function (fragment_modal) {
        // console.log(fragment);
        $("#modal_replace").replaceWith(fragment_modal);
    });
}

function searchAndReplaceProduct(){}

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

        let tienthua = document.getElementById('tienThua').innerText;

        if (Math.sign(Number(tienthua)) != -1) {
            window.location = "/nova/pos/checkout";
        }else {
            Swal.fire({
                icon: "error",
                title: "Khách đưa thiếu tiền",
                text: "Vui lòng kiểm tra lại!",
            });
        }
    }

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
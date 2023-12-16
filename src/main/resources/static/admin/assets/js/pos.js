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
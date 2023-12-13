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
        var url = "/nova/pos/add?detail=" + decodedText;
        // console.log(decodedText)

        fetch("/nova/pos/add", {
            method: "POST",
            body: JSON.stringify({
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
}

function playSound(soundName) {
    let src = "./../audio/"
    let audio = new Audio(src + soundName)
    audio.play();
}

// function test() {
//     $('.collapse').collapse({
//         toggle: true
//     })
// }

// $('.collapse').collapse({
//     toggle: true
// })
/*
 * Copyright 2022 Rudy De Busscher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
function init() {
    $("#video").hide();
    $("#click-photo").hide();
    $("#send-photo").hide();
    let inputField = $(".cameraCapture");

    $("#cameraCapture").css("width", $("#cameraCapture .child:visible").width() + 3);

    // let x = 0; $("#cameraCapture .child:visible").each(function() {x = x+ this.clientWidth;}); console.log(x);

    let video = $("#video")[0];
    let canvas = $("#canvas")[0];

    $("#start-camera").click(async function () {
        let stream = await navigator.mediaDevices.getUserMedia({video: true, audio: false});
        $("#video").show();
        video.srcObject = stream;
        video.play();
        $("#click-photo").show();
        $(this).addClass("dnone");

        $("#cameraCapture").css("width", $("#cameraCapture .child:visible").width());

    });

    $("#click-photo").click(function () {
        canvas.getContext('2d').drawImage(video, 0, 0, canvas.width, canvas.height);

        $("#video").hide();
        $(this).addClass("dnone");
        $("#canvas").show();
        $("#send-photo").show();


        let image_data_url = canvas.toDataURL('image/jpeg');
        inputField.val(image_data_url);


        $("#cameraCapture").css("width", $("#cameraCapture .child:visible").width());
    });
}

jQuery(document).ready(function () {
    init();
});
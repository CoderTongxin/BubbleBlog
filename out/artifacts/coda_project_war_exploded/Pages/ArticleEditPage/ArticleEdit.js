
$(document).ready(function () {
    $('.ui.normal.dropdown').dropdown();
    $('.input').each(function () {
        $(this).change(function () {
            var data = new FormData();
            data.append('file', $(this).prop('files')[0]);
            $.ajax({
                url: 'AlbumsChange',
                type: 'POST',
                data: data,
                processData: false,  // tell jQuery not to process the data
                contentType: false,  // tell jQuery not to set contentType
                success: function (data) {
                    $('#editor').append(data + '<div><br></div>');
                }
            });
            $(this).val('');

        });
    });

    $("#youtube").off().click(function () {
        var input = prompt("Please put your video link", "link here");
        if (input != null) {
            var data = {'action': 'createYoutube', 'youtubeAddress': input};
            $.ajax({
                url: 'AlbumsChange',
                type: 'POST',
                data: data,
                success: function (data) {
                    $('#editor').append(input + '<div><br></div>');
                }
            });
        }
    });

    $("#form").submit(function (e) {
        var content = $("#editor").html();
        if (!$.trim(content).length) {
            alert("Content is empty!");
            e.preventDefault();
        } else {
            $("#articleContentSubmit").val(content);
            $(this).find("#submitBtn").addClass("loading").prop("disabled", true);
            $('#editBtn').prop("disabled", true);
            $('#deleteBtn').prop("disabled", true);
        }
    });

    $('#editBtn').click(function () {
        $('#actionInput').val("update");
        $(this).addClass("loading");
    });

    $('#deleteBtn').click(function () {
        $('#actionInput').val("delete");
        $(this).addClass("loading");
    });

    $(function () {
        function initToolbarBootstrapBindings() {
            var fonts = ['Serif', 'Sans', 'Arial', 'Arial Black', 'Courier',
                    'Courier New', 'Comic Sans MS', 'Helvetica', 'Impact', 'Lucida Grande', 'Lucida Sans', 'Tahoma', 'Times',
                    'Times New Roman', 'Verdana'],
                fontTarget = $('[title=Font]').siblings('.dropdown-menu');
            $.each(fonts, function (idx, fontName) {
                fontTarget.append($('<li><a data-edit="fontName ' + fontName + '" style="font-family:\'' + fontName + '\'">' + fontName + '</a></li>'));
            });
            $('a[title]').tooltip({container: 'body'});
            $('.dropdown-menu input').click(function () {
                return false;
            })
                .change(function () {
                    $(this).parent('.dropdown-menu').siblings('.dropdown-toggle').dropdown('toggle');
                })
                .keydown('esc', function () {
                    this.value = '';
                    $(this).change();
                });

            $('[data-role=magic-overlay]').each(function () {
                var overlay = $(this), target = $(overlay.data('target'));
                overlay.css('opacity', 0).css('position', 'absolute').offset(target.offset()).width(target.outerWidth()).height(target.outerHeight());
            });
            if ("onwebkitspeechchange" in document.createElement("input")) {

                var editorOffset = $('#editor').offset();
                $('#voiceBtn').css('position', 'absolute').offset({
                    top: editorOffset.top,
                    left: editorOffset.left + $('#editor').innerWidth() - 35
                });
            } else {
                $('#voiceBtn').hide();
            }
        }

        function showErrorAlert(reason, detail) {
            var msg = '';
            if (reason === 'unsupported-file-type') {
                msg = "Unsupported format " + detail;
            }
            else {
                console.log("error uploading file", reason, detail);
            }
            $('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>' +
                '<strong>File upload error</strong> ' + msg + ' </div>').prependTo('#alerts');
        }

        initToolbarBootstrapBindings();
        $('#editor').wysiwyg({fileUploadError: showErrorAlert});
        window.prettyPrint && prettyPrint();
    });
});
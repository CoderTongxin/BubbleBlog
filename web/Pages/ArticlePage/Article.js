
$(document).ready(function () {
    likeArticle();
    //edit comment
    $('.editBtn').each(function (i, obj) {
        $(obj).click(function () {
            var commentID = $(obj).attr('name');
            var commentContent = $(obj).parent().prev(".commentContent").text();
            var labelName = 'Edit Your Comment';
            $("#editorLabel").text(labelName);
            $("#editorTextArea").text(commentContent);
            $("#commentIDInput").val(commentID);
            $("#articleIDInput").val(articleID);
            $("#editorSubmitBtn").val("update");
            $("#editor")
                .modal('setting', 'transition', 'vertical flip')
                .modal('show')
            ;
        })
    });
    //reply comment
    $('.replyBtn').each(function (i, obj) {
        $(obj).click(function () {
            var commentID = $(obj).attr('name');
            var labelName = 'Reply Comment Here';
            $("#editorLabel").text(labelName);
            $("#editorTextArea").text("");
            $("#commentIDInput").val(commentID);
            $("#articleIDInput").val(articleID);
            $("#editorSubmitBtn").val("reply");
            $("#editor")
                .modal('setting', 'transition', 'vertical flip')
                .modal('show')
            ;
        })
    });
    $('#editArticleBtn,#deleteArticleBtn,#editorSubmitBtn').click(function () {
        $(this)
            .attr("class", "ui loading disabled button")
            .bind('click', false)
        ;
        disableCommentBtn()
    });

    $('#commentSubmitBtn').click(function () {
        $(this)
            .attr("class", "ui loading disabled button")
            .bind('click', false)
        ;
        disableArticleBtn()
    });
});

function disableArticleBtn() {
    $('#deleteArticleBtn,#editorSubmitBtn,#editArticleBtn')
        .attr("class", "ui disabled button")
        .bind('click', false);
}
function disableCommentBtn() {
    $('#deleteArticleBtn,#editArticleBtn,#commentSubmitBtn')
        .attr("class", "ui disabled button")
        .bind('click', false)
    ;
}

function likeArticle() {
    var id = $('#id').text();
    $('.ui.button.like').off().click(function () {
        console.log("likeClick");
        $(this).prop("disabled", true);
        var numLike = $('.ui.basic.right.pointing.label').text();
        $('.ui.basic.right.pointing.label').text(parseInt(numLike, 10) + 1);
        $('.heart.icon').addClass('red');
        $('#ifLiked').text('liked');
        $.ajax({
            url: 'Article',
            type: 'post',
            data: {action: 'like', articleID: id},
            success: function () {
                console.log('success');
                $('.ui.button.like').removeClass('like').addClass('liked');
                $('#likeButton').prop("disabled", false);
                likeArticle();
            }
        })
    });
    $('.ui.button.liked').off().click(function () {
        console.log("dislikeClick");
        $(this).prop("disabled", true);
        var numLike = $('.ui.basic.right.pointing.label').text();
        $('.ui.basic.right.pointing.label').text(parseInt(numLike, 10) - 1);
        $('.heart.icon').removeClass('red');
        $('#ifLiked').text('like');
        $.ajax({
            url: 'Article',
            type: 'post',
            data: {action: 'dislike', articleID: id},
            success: function () {
                console.log('success2');
                $('.ui.button.liked').removeClass('liked').addClass('like');
                $('#likeButton').prop("disabled", false);
                likeArticle();
            }
        })
    });
}

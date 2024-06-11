// 영화 삭제 버튼 클릭 이벤트 처리
$(document).ready(function () {
  $(".delete-button").on("click", function () {
    var movieId = $(this).data("movie-id");
    deleteMovie(movieId);
  });
});

// 영화 삭제 함수
function deleteMovie(movieId) {
  $.ajax({
    type: "DELETE",
    url: "/v1/favorites",
    dataType: "text",
    contentType: "text/plain; charset=utf-8",
    data: movieId,
  })
    .done(() => {
      location.reload();
    })
    .fail((jqXHR) => {
      if (jqXHR.status == 400) {
        alert("영화를 삭제할 수 없습니다.");
      }
    });
}

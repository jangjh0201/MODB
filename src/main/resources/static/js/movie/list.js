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
    url: "/v1/movie/delete",
    dataType: "text",
    contentType: "application/json; charset=utf-8",
    data: JSON.stringify(movieId),
  })
    .done(() => {
      alert("삭제되었습니다.");
      location.reload();
    })
    .fail((jqXHR) => {
      if (jqXHR.status == 400) {
        alert("영화를 삭제할 수 없습니다.");
      }
    });
}

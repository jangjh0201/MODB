function errorCheck() {
  if (document.searchForm.title.value == "") {
    alert("검색어는 필수입니다.");
    return false;
  } else {
    document.searchForm.submit(); // 폼을 제출합니다.
  }
}

// 영화 저장 버튼 클릭 이벤트 처리
$(document).ready(function () {
  $(".save-button").on("click", function () {
    var movieId = $(this).data("movie-id");
    saveMovie(movieId);
  });
});

// 영화 저장 함수
function saveMovie(movieId) {
  $.ajax({
    type: "POST",
    url: "/v1/favorites",
    dataType: "text",
    contentType: "application/json; charset=utf-8",
    data: JSON.stringify(movie),
  })
    .done(() => {
      alert("등록되었습니다.");
    })
    .fail((jqXHR) => {
      if (jqXHR.status == 409) {
        alert("이미 등록된 영화입니다.");
      } else if (jqXHR.status == 404) {
        alert("영화를 찾을 수 없습니다.");
      }
    });
}

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
    var movieIndex = $(this).data("movie-index");
    saveMovie(movieIndex);
  });
});

// 영화 저장 함수
function saveMovie(movieIndex) {
  $.ajax({
    type: "POST",
    url: "/movie/new",
    dataType: "text",
    contentType: "application/json; charset=utf-8",
    data: JSON.stringify(movieIndex),
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

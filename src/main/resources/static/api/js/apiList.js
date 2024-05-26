function errorCheck() {
  if (document.forms["searchForm"]["title"].value == "") {
    alert("검색어는 필수입니다.");
    return false;
  } else {
    document.forms["searchForm"].submit();
  }
}

function saveMovie(movie) {
  $.ajax({
    type: "POST",
    url: "/movie/new",
    data: JSON.stringify(movie),
    dataType: "text",
    contentType: "application/json; charset=utf-8",
  })
    .done(() => {
      alert("위시리스트에 저장되었습니다.");
    })
    .fail((jqXHR) => {
      if (jqXHR.status === 409) {
        alert("이미 등록된 영화입니다.");
      }
    });
}

function errorCheck() {
  if (document.forms["searchForm"]["title"].value == "") {
    alert("검색어는 필수입니다.");
    return false;
  } else {
    document.forms["searchForm"].submit();
  }
}

function saveMovie(object) {
  $.ajax({
    type: "POST",
    async: false,
    url: "/api/new",
    data: JSON.stringify(object),
    dataType: "text",
    contentType: "application/json; charset=utf-8",
  })
    .done(function (data, textStatus, jqXHR) {
      alert("위시리스트에 저장되었습니다.");
    })
    .fail(function (jqXHR, textStatus, errorThrown) {
      switch (jqXHR.status) {
        case 409:
          alert("이미 등록된 영화입니다.");
      }
    });
}

function errorCheck() {
  if (document.forms["searchForm"]["title"].value == "") {
    alert("검색어는 필수입니다.");
    return false;
  } else {
    document.forms["searchForm"].submit();
  }
}

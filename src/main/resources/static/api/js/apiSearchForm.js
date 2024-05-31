function errorCheck() {
  if (document.searchForm.title.value == "") {
    alert("검색어는 필수입니다.");
    return false;
  } else {
    document.searchForm.submit(); // 폼을 제출합니다.
  }
}

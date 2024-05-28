$(document).ready(function () {
  $(".delete-button").on("click", function () {
    var movieId = $(this).data("movie-id");
    deleteMovie(movieId);
  });

  function deleteMovie(movieId) {
    $.ajax({
      type: "POST",
      url: "/movies/delete",
      data: JSON.stringify({ id: movieId }),
      dataType: "text",
      contentType: "application/json; charset=utf-8",
    }).done(() => {
      alert("삭제되었습니다.");
      location.reload();
    });
  }
});

export function saveMovie(movie) {
  fetch("/v1/wish", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(movie),
  })
    .then((response) => {
      if (response.status === 201) {
        alert("등록되었습니다.");
      } else if (response.status === 409) {
        alert("이미 등록된 영화입니다.");
      } else {
        alert("오류가 발생했습니다.");
      }
    })
    .catch((error) => console.error("Error:", error));
}

export function deleteWish(wishId) {
  fetch(`/v1/wish/${wishId}`, {
    method: "DELETE",
  })
    .then((response) => {
      if (response.status === 200) {
        alert("삭제되었습니다.");
        location.reload(); // Reload the wish list
      } else {
        alert("오류가 발생했습니다.");
      }
    })
    .catch((error) => console.error("Error:", error));
}

export function getMovies(queryParams) {
  return fetch(`/v1/movie?${queryParams}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => response.json())
    .catch((error) => {
      console.error("Error:", error);
      throw error;
    });
}

export function getWishes() {
  return fetch("/v1/wish", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => response.json())
    .catch((error) => {
      console.error("Error:", error);
      throw error;
    });
}

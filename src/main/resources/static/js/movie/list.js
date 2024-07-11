import { showMovieModal } from "../common/modal.js";
import { saveMovie } from "../common/api.js";

document.addEventListener("DOMContentLoaded", function () {
  const urlParams = new URLSearchParams(window.location.search);
  if (
    urlParams.has("title") ||
    urlParams.has("nation") ||
    urlParams.has("genre")
  ) {
    searchMovies(urlParams.toString());
    document.getElementById("inputTitle").value = urlParams.get("title") || "";
    document.getElementById("inputNation").value =
      urlParams.get("nation") || "";
    document.getElementById("inputGenre").value = urlParams.get("genre") || "";
  }

  document
    .getElementById("searchForm")
    .addEventListener("submit", function (event) {
      event.preventDefault();
      updateUrlAndSearch();
    });
});

function updateUrlAndSearch() {
  let form = document.getElementById("searchForm");
  let formData = new FormData(form);
  let queryParams = new URLSearchParams();

  for (let [key, value] of formData.entries()) {
    if (value) {
      queryParams.append(key, value);
    }
  }

  let newUrl = `${window.location.pathname}?${queryParams.toString()}`;
  history.pushState(null, "", newUrl);

  searchMovies(queryParams.toString());
}

function searchMovies(queryParams) {
  fetch(`/v1/movie?${queryParams}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => response.json())
    .then((data) => {
      renderMovies(data);
    })
    .catch((error) => console.error("Error:", error));
}

function renderMovies(movies) {
  const container = document.getElementById("movieList");
  container.innerHTML = "";

  if (movies.length === 0) {
    container.innerHTML = "<p class='text-center'>검색 결과가 없습니다.</p>";
    return;
  }

  const table = document.createElement("table");
  table.classList.add("table", "table-hover", "table-bordered");

  const thead = document.createElement("thead");
  thead.innerHTML = `
    <tr>
      <th scope="col">제목</th>
      <th scope="col">개봉</th>
      <th scope="col">장르</th>
      <th scope="col">제작국가</th>
      <th scope="col">상영시간(분)</th>
      <th scope="col">감독</th>
      <th scope="col">배우</th>
      <th scope="col"></th>
    </tr>`;
  table.appendChild(thead);

  const tbody = document.createElement("tbody");
  tbody.classList.add("table-group-divider");

  movies.forEach((movie) => {
    const tr = document.createElement("tr");

    tr.innerHTML = `
      <td><a href="#" class="movie-title" data-movie='${encodeURIComponent(
        JSON.stringify(movie)
      )}'>${movie.title}</a></td>
      <td>${movie.prodYear}</td>
      <td>${movie.genre}</td>
      <td>${movie.nation}</td>
      <td>${movie.runtime}</td>
      <td>${movie.director}</td>
      <td class="fixed-width-td">${movie.actor}</td>
      <td>
        <button class="btn btn-outline-primary save-button" data-movie='${encodeURIComponent(
          JSON.stringify(movie)
        )}'>저장</button>
      </td>`;
    tbody.appendChild(tr);
  });

  table.appendChild(tbody);
  container.appendChild(table);

  document.querySelectorAll(".save-button").forEach((button) => {
    button.addEventListener("click", function () {
      saveMovie(JSON.parse(decodeURIComponent(this.dataset.movie)));
    });
  });

  document.querySelectorAll(".movie-title").forEach((title) => {
    title.addEventListener("click", function (event) {
      event.preventDefault();
      showMovieModal(JSON.parse(decodeURIComponent(this.dataset.movie)));
    });
  });
}

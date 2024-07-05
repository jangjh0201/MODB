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

function showMovieModal(movie) {
  const posters = movie.detail.posters.filter((poster) => poster); // 빈 문자열 필터링
  const hasMultiplePosters = posters.length > 1;
  const posterCarousel = hasMultiplePosters
    ? `
      <div id="posterCarousel" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">
          ${posters
            .map(
              (poster, index) => `
            <div class="carousel-item ${index === 0 ? "active" : ""}">
              <img src="${poster}" class="d-block w-100" alt="${
                movie.title
              } 포스터">
            </div>`
            )
            .join("")}
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#posterCarousel" data-bs-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#posterCarousel" data-bs-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>`
    : `<img src="${
        posters[0] || "../../images/No-Image-Placeholder.svg.png"
      }" class="d-block w-100" alt="${movie.title} 포스터">`;

  let modalHtml = `
    <div class="modal fade" id="movieModal" tabindex="-1" aria-labelledby="movieModalLabel" aria-hidden="true">
      <div class="modal-dialog custom-modal-width">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="movieModalLabel">영화 정보</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            ${posterCarousel}
            <p id="modalPlot">${movie.detail.plot}</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-outline-primary" id="saveButton">저장</button>
          </div>
        </div>
      </div>
    </div>`;

  document.body.insertAdjacentHTML("beforeend", modalHtml);

  document.getElementById("saveButton").addEventListener("click", function () {
    saveMovie(movie);
    let modal = bootstrap.Modal.getInstance(
      document.getElementById("movieModal")
    );
    modal.hide();
  });

  let modal = new bootstrap.Modal(document.getElementById("movieModal"));
  modal.show();

  document
    .getElementById("movieModal")
    .addEventListener("hidden.bs.modal", function () {
      this.remove();
    });
}

function saveMovie(movie) {
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

function checkEnter(event) {
  if (event.key === "Enter") {
    event.preventDefault();
    updateUrlAndSearch();
  }
}

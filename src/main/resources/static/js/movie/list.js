document.addEventListener("DOMContentLoaded", function () {
  // 페이지가 로드될 때 URL에서 쿼리 스트링을 읽어와 검색 수행
  const urlParams = new URLSearchParams(window.location.search);
  if (
    urlParams.has("title") ||
    urlParams.has("nation") ||
    urlParams.has("genre")
  ) {
    searchMovies(urlParams.toString());
    // 폼 필드 값을 URL 쿼리 스트링 값으로 설정
    document.getElementById("inputTitle").value = urlParams.get("title") || "";
    document.getElementById("inputNation").value =
      urlParams.get("nation") || "";
    document.getElementById("inputGenre").value = urlParams.get("genre") || "";
  }

  document
    .getElementById("searchForm")
    .addEventListener("submit", function (event) {
      event.preventDefault(); // 폼 기본 제출 방지
      updateUrlAndSearch(); // URL 업데이트 및 검색 수행
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

  // URL 업데이트
  let newUrl = `${window.location.pathname}?${queryParams.toString()}`;
  history.pushState(null, "", newUrl);

  // 검색 수행
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
  container.innerHTML = ""; // Clear previous results

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
      <td>${movie.title}</td>
      <td>${movie.prodYear}</td>
      <td>${movie.genre}</td>
      <td>${movie.nation}</td>
      <td>${movie.runtime}</td>
      <td>${movie.director}</td>
      <td class="fixed-width-td">${movie.actor}</td>
      <td>
        <button class="btn btn-outline-primary save-button" data-movie='${JSON.stringify(
          movie
        )}'>저장</button>
      </td>`;
    tbody.appendChild(tr);
  });

  table.appendChild(tbody);
  container.appendChild(table);

  document.querySelectorAll(".save-button").forEach((button) => {
    button.addEventListener("click", function () {
      saveMovie(JSON.parse(this.dataset.movie));
    });
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

function searchMovies() {
  let nation = document.getElementById("inputNation").value;
  let genre = document.getElementById("inputGenre").value;
  let title = document.getElementById("inputTitle").value;

  if (title === "") {
    alert("검색어는 필수입니다.");
    return;
  }

  let request = {
    nation: nation,
    genre: genre,
    title: title,
  };

  fetch("/v1/movie", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(request),
  })
    .then((response) => response.json()) // JSON 응답으로 변환
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
        <button class="btn btn-outline-primary save-button" data-movie-id="${movie.id}">저장</button>
      </td>`;
    tbody.appendChild(tr);
  });

  table.appendChild(tbody);
  container.appendChild(table);

  // Save button event listener
  document.querySelectorAll(".save-button").forEach((button) => {
    button.addEventListener("click", function () {
      saveMovie(this.dataset.movieId);
    });
  });
}

function saveMovie(movieId) {
  fetch("/v1/wish", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ id: movieId }),
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
    event.preventDefault(); // 기본 Enter 키 동작을 막습니다.
    searchMovies(); // 검색 함수 호출
  }
}

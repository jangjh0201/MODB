document.addEventListener("DOMContentLoaded", function () {
  fetch("/v1/wish")
    .then((response) => response.json())
    .then((data) => {
      renderWishes(data);
    })
    .catch((error) => console.error("Error:", error));
});

function renderWishes(wishes) {
  const container = document.getElementById("wishList");
  container.innerHTML = ""; // Clear previous results

  if (wishes.length === 0) {
    container.innerHTML =
      "<p class='text-center no-movies-message'>저장된 영화가 없습니다.</p>";
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

  wishes.forEach((wish) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td><a href="#" class="wish-title" data-wish='${encodeURIComponent(
        JSON.stringify(wish)
      )}'>${wish.title}</a></td>
      <td>${wish.prodYear}</td>
      <td>${wish.genre}</td>
      <td>${wish.nation}</td>
      <td>${wish.runtime}</td>
      <td>${wish.director}</td>
      <td class="fixed-width-td">${wish.actor}</td>
      <td>
        <button class="btn btn-outline-primary delete-button" data-wish-id="${
          wish.id
        }">삭제</button>
      </td>`;
    tbody.appendChild(tr);
  });

  table.appendChild(tbody);
  container.appendChild(table);

  // Delete button event listener
  document.querySelectorAll(".delete-button").forEach((button) => {
    button.addEventListener("click", function () {
      deleteWish(this.dataset.wishId);
    });
  });

  // Wish title click event listener for modal
  document.querySelectorAll(".wish-title").forEach((title) => {
    title.addEventListener("click", function (event) {
      event.preventDefault();
      showWishModal(JSON.parse(decodeURIComponent(this.dataset.wish)));
    });
  });
}

function deleteWish(wishId) {
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

function showWishModal(wish) {
  const posters = wish.detail.posters.filter((poster) => poster); // 빈 문자열 필터링
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
                wish.title
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
      }" class="d-block w-100" alt="${wish.title} 포스터">`;

  let modalHtml = `
    <div class="modal fade" id="wishModal" tabindex="-1" aria-labelledby="wishModalLabel" aria-hidden="true">
      <div class="modal-dialog custom-modal-width">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="wishModalLabel">영화 정보</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            ${posterCarousel}
            <p id="modalPlot">${wish.detail.plot}</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-outline-primary" id="deleteButton" data-wish-id="${wish.id}">삭제</button>
          </div>
        </div>
      </div>
    </div>`;

  document.body.insertAdjacentHTML("beforeend", modalHtml);

  document
    .getElementById("deleteButton")
    .addEventListener("click", function () {
      deleteWish(this.dataset.wishId);
      let modal = bootstrap.Modal.getInstance(
        document.getElementById("wishModal")
      );
      modal.hide();
    });

  let modal = new bootstrap.Modal(document.getElementById("wishModal"));
  modal.show();

  document
    .getElementById("wishModal")
    .addEventListener("hidden.bs.modal", function () {
      this.remove();
    });
}

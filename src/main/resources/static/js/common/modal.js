import { saveMovie, deleteWish } from "./api.js";

export function showMovieModal(movie) {
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
        posters[0] || "../../images/No-Image-Placeholder.png"
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

export function showWishModal(wish) {
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
        posters[0] || "../../images/No-Image-Placeholder.png"
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

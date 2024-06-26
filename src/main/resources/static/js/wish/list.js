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
      <td>${wish.title}</td>
      <td>${wish.prodYear}</td>
      <td>${wish.genre}</td>
      <td>${wish.nation}</td>
      <td>${wish.runtime}</td>
      <td>${wish.director}</td>
      <td class="fixed-width-td">${wish.actor}</td>
      <td>
        <button class="btn btn-outline-primary delete-button" data-wish-id="${wish.id}">삭제</button>
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
}

function deleteWish(wishId) {
  fetch("/v1/wish", {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ id: wishId }),
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

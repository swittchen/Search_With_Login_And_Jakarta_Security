let input = document.querySelector(".sF");
let button = document.querySelector(".btn1");
let output = document.querySelector(".out1");
let arr = document.querySelectorAll(".gropCheckBoxAndLabel .checkbox");
let textFromInput = "";
let arrOfCategories = "";
let molFile = "";

// Function SEARCH
function search() {
  arrOfCategories = [];
  // Content of search area
  textFromInput = input.value;
  // output.innerHTML = `<h1>${textFromInput}</h1>`;
  console.log(textFromInput.split(" "));

  // Content of checkboxes
  // take values from cheked checkbox

  for (let i = 0; i < arr.length; i++) {
    if (arr[i].checked == true) {
      arrOfCategories.push(arr[i].value);
    }
  }
  console.log(arrOfCategories);
}

async function fetchRequestValues(textFromInput, arrOfCategories, molFile) {
  const url = "http://localhost:8888/backend/api/search";
  const token = localStorage.getItem("crimsy_token");

  try {
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
      body: JSON.stringify({
        searchQuery: textFromInput.split(" "),
        searchTarget: arrOfCategories,
        searchValue: ["Biomaterial", "Item"],
        molFile: molFile,
      }),
    });

    if (!response.ok) {
      throw new Error(`Response status: ${response.status}`);
    }

    const result = await response.json();

    // ✅ JSON-like Output
    const formatted = formatResponseLikeJson({
      Status: result.status,
      SearchQuery: result.searchQuery,
      SearchTarget: result.searchTarget,
      SearchValue: result.searchValue,
      MolFile: result.molFile ? "[molfile present]" : "null",
    });

    output.innerHTML = `
      <pre class="json-like">
${formatted}
      </pre>
    `;
  } catch (error) {
    output.innerHTML = `
      <pre class="json-like error">
Error: ${error.message}
      </pre>
    `;
  }
}

function formatResponseLikeJson(obj) {
  return Object.entries(obj)
    .map(([key, value]) => {
      if (Array.isArray(value)) {
        return `${key}: ${value.join(", ")}`;
      }
      if (value === null || value === undefined) {
        return `${key}: null`;
      }
      if (typeof value === "object") {
        return `${key}: ${JSON.stringify(value)}`;
      }
      return `${key}: ${value}`;
    })
    .join("\n");
}

// EVENTS
button.addEventListener("click", async () => {
  // Content of Mol Editor
  molFile = await getMolFile();
  console.log(molFile);
  search();
  fetchRequestValues(textFromInput, arrOfCategories, molFile);
});

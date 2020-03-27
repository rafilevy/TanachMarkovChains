let startString = "s";
let endString = "e"

let transitionMatrix;

async function loadTransitionMatrix(src) {
    let file = await fetch(src);
    let matrix = await file.json();
    return matrix;
}

function generateVerse(matrix) {
    while (true) {
        let verse = "";
        currentWord = startString;
        while (currentWord != endString) {
            if (currentWord != startString) verse += currentWord + " ";
            let rnd = Math.random();
            for (let key in matrix[currentWord]) {
                rnd -= matrix[currentWord][key];
                if (rnd <= 0)  {
                    currentWord = key;
                    break;
                }
            }
        }
        if (verse.length > 1) return verse;
    }
}

function newVerse() {
    document.getElementById("verse").innerText = generateVerse(transitionMatrix);
}

window.onload = async () => {
    transitionMatrix = await loadTransitionMatrix("resources/transitionMatrix.json");
    newVerse();
}
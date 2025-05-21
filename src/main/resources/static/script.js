async function shortenUrl() {
  const originalUrl = document.getElementById("originalUrl").value;

  if (!originalUrl) {
    alert("Por favor, insira uma URL.");
    return;
  }

  try {
    const response = await fetch("http://localhost:8080/url", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ originalUrl })
    });

    if (!response.ok) {
      throw new Error("Erro ao encurtar a URL");
    }

    const data = await response.json();
    const shortUrl = `http://localhost:8080/url/${data.shortUrl}`;

    document.getElementById("shortUrl").textContent = shortUrl;
    document.getElementById("shortUrl").href = shortUrl;
    document.getElementById("result").classList.remove("hidden");
  } catch (error) {
    alert("Houve um problema ao encurtar a URL.");
    console.error(error);
  }
}
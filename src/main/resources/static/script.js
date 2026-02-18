async function shortenUrl() {
  const input = document.getElementById("originalUrl");
  const button = document.querySelector("button");
  const result = document.getElementById("result");
  const link = document.getElementById("shortUrl");
  const error = document.getElementById("errorMessage");

  const originalUrl = input.value.trim();

  // Reset UI
  error.classList.add("hidden");
  error.textContent = "";
  result.classList.add("hidden");

  if (!originalUrl) {
    error.textContent = "Digite uma URL válida.";
    error.classList.remove("hidden");
    input.focus();
    return;
  }

  button.disabled = true;
  button.textContent = "Encurtando...";

  try {
    const response = await fetch("/url", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ originalUrl })
    });

    if (!response.ok) {
      if (response.status === 400) {
        throw new Error("A URL informada é inválida.");
      }
      throw new Error("Erro ao encurtar a URL. Tente novamente.");
    }

    const data = await response.json();
    const shortUrl = `${window.location.origin}/url/${data.shortUrl}`;

    link.textContent = shortUrl;
    link.href = shortUrl;
    result.classList.remove("hidden");

  } catch (err) {
    console.error(err);
    error.textContent = err.message;
    error.classList.remove("hidden");
  } finally {
    button.disabled = false;
    button.textContent = "Encurtar";
  }
}

/* ===============================
   ANIMAÇÃO DOS ÍCONES
   =============================== */

document.addEventListener("DOMContentLoaded", () => {
  const icons = document.querySelectorAll(".tech-icon");
  const container = document.querySelector(".container");

  icons.forEach(icon => {
    const iconWidth = icon.offsetWidth;
    const iconHeight = icon.offsetHeight;

    let x = Math.random() * (window.innerWidth - iconWidth);
    let y = Math.random() * (window.innerHeight - iconHeight);

    let dx = (Math.random() - 0.5) * 3 || 1;
    let dy = (Math.random() - 0.5) * 3 || 1;

    function animate() {
      const containerRect = container.getBoundingClientRect();

      x += dx;
      y += dy;

      // Colisão com as bordas da tela
      if (x <= 0 || x + iconWidth >= window.innerWidth) dx *= -1;
      if (y <= 0 || y + iconHeight >= window.innerHeight) dy *= -1;

      // Colisão com o container central
      const intersects =
        x < containerRect.right &&
        x + iconWidth > containerRect.left &&
        y < containerRect.bottom &&
        y + iconHeight > containerRect.top;

      if (intersects) {
        dx *= -1;
        dy *= -1;
      }

      icon.style.transform = `translate(${x}px, ${y}px)`;
      requestAnimationFrame(animate);
    }

    animate();
  });
});
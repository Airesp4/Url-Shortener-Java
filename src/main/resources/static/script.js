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

document.addEventListener("DOMContentLoaded", () => {
  const icons = document.querySelectorAll(".tech-icon");
  const container = document.querySelector(".container");
  const containerRect = container.getBoundingClientRect();

  icons.forEach(icon => {
    let x, y;

    do {
      x = Math.random() * (window.innerWidth - icon.clientWidth);
      y = Math.random() * (window.innerHeight - icon.clientHeight);
    } while (
      x + icon.clientWidth > containerRect.left &&
      x < containerRect.right &&
      y + icon.clientHeight > containerRect.top &&
      y < containerRect.bottom
    );

    let dx = (Math.random() - 0.5) * 5;
    let dy = (Math.random() - 0.5) * 5;

    if (dx === 0) dx = 1;
    if (dy === 0) dy = 1;

    const animate = () => {
      x += dx;
      y += dy;

      if (x <= 0 || x + icon.clientWidth >= window.innerWidth) dx *= -1;
      if (y <= 0 || y + icon.clientHeight >= window.innerHeight) dy *= -1;

      const iconRect = {
        left: x,
        right: x + icon.clientWidth,
        top: y,
        bottom: y + icon.clientHeight
      };

      const intersectsHorizontally =
        iconRect.right > containerRect.left && iconRect.left < containerRect.right;
      const intersectsVertically =
        iconRect.bottom > containerRect.top && iconRect.top < containerRect.bottom;

      if (intersectsHorizontally && intersectsVertically) {
        dx *= -1;
        dy *= -1;
      }

      icon.style.transform = `translate(${x}px, ${y}px)`;
      requestAnimationFrame(animate);
    };

    animate();
  });
});
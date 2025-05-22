async function shortenUrl() {
  const originalUrl = document.getElementById("originalUrl").value;

  if (!originalUrl) {
    alert("Por favor, insira uma URL.");
    return;
  }

  try {
    const response = await fetch("/url", {
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
    const shortUrl = `${window.location.origin}/url/${data.shortUrl}`;

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

  icons.forEach(icon => {
    const iconWidth = icon.clientWidth;
    const iconHeight = icon.clientHeight;

    let x = 0, y = 0;
    let attempts = 0;
    const maxAttempts = 100;

    while (attempts < maxAttempts) {
      x = Math.random() * (window.innerWidth - iconWidth);
      y = Math.random() * (window.innerHeight - iconHeight);

      const containerRect = container.getBoundingClientRect();

      const iconRect = {
        left: x,
        right: x + iconWidth,
        top: y,
        bottom: y + iconHeight
      };

      const intersects =
        iconRect.right > containerRect.left &&
        iconRect.left < containerRect.right &&
        iconRect.bottom > containerRect.top &&
        iconRect.top < containerRect.bottom;

      if (!intersects) break;
      attempts++;
    }

    let dx = (Math.random() - 0.5) * 5;
    let dy = (Math.random() - 0.5) * 5;
    if (dx === 0) dx = 1;
    if (dy === 0) dy = 1;

    const animate = () => {
      const containerRect = container.getBoundingClientRect();

      x += dx;
      y += dy;

      if (x <= 0 || x + iconWidth >= window.innerWidth) dx *= -1;
      if (y <= 0 || y + iconHeight >= window.innerHeight) dy *= -1;

      const iconRect = {
        left: x,
        right: x + iconWidth,
        top: y,
        bottom: y + iconHeight
      };

      const intersects =
        iconRect.right > containerRect.left &&
        iconRect.left < containerRect.right &&
        iconRect.bottom > containerRect.top &&
        iconRect.top < containerRect.bottom;

      if (intersects) {
        dx *= -1;
        dy *= -1;
        x += dx * 2;
        y += dy * 2;
      }

      x = Math.max(0, Math.min(x, window.innerWidth - iconWidth));
      y = Math.max(0, Math.min(y, window.innerHeight - iconHeight));

      icon.style.transform = `translate(${x}px, ${y}px)`;
      requestAnimationFrame(animate);
    };

    animate();
  });
});
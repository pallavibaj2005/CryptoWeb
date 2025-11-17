const backendUrl = "http://localhost:8080/api/trader";

function showMessage(html) {
  document.getElementById("output").innerHTML = html;
}

// ✅ Test Backend
async function testBackend() {
  try {
    const res = await fetch(`${backendUrl}/test`);
    const data = await res.json();
    showMessage("✅ " + data.message);
  } catch (err) {
    showMessage("❌ Backend not responding: " + err.message);
  }
}

// ✅ Top 10
async function showTop10() {
  try {
    const res = await fetch(`${backendUrl}/top10`);
    const text = await res.text();
    const data = JSON.parse(text);
    let html = "<h3>🌍 Top 10 Cryptos</h3><ul>";
    data.forEach(c => {
      html += `<li><b>${c.name}</b> (${c.symbol.toUpperCase()}) — 💲${c.current_price}</li>`;
    });
    html += "</ul>";
    showMessage(html);
  } catch (err) {
    showMessage("❌ Error fetching top 10: " + err.message);
  }
}

// ✅ History (fixed)
async function showHistory() {
  const coin = prompt("Enter coin name or symbol (e.g., bitcoin / btc):");
  if (!coin) return;
  try {
    const res = await fetch(`${backendUrl}/history/${coin}`);
    const text = await res.text();
    const data = JSON.parse(text);
    if (data.error) {
      showMessage("❌ " + data.error);
      return;
    }
    const prices = data.prices?.slice(-10) || [];
    if (!prices.length) {
      showMessage("⚠️ No price data found for " + coin);
      return;
    }
    let html = `<h3>📈 ${coin.toUpperCase()} - Last 10 Price Points</h3><ul>`;
    prices.forEach(p => {
      const date = new Date(p[0]).toLocaleDateString();
      html += `<li>${date}: 💲${p[1].toFixed(2)}</li>`;
    });
    html += "</ul>";
    showMessage(html);
  } catch (err) {
    showMessage("❌ Error fetching history: " + err.message);
  }
}

// ✅ Portfolio
async function viewPortfolio() {
  const res = await fetch(`${backendUrl}/portfolio`);
  const data = await res.json();
  if (Object.keys(data).length === 0) {
    showMessage("💼 Portfolio empty.");
    return;
  }
  let html = "<h3>💼 Portfolio</h3><ul>";
  for (let [coin, amt] of Object.entries(data)) {
    html += `<li>${coin}: ${amt}</li>`;
  }
  html += "</ul>";
  showMessage(html);
}

// ✅ Transactions
async function viewTransactions() {
  const res = await fetch(`${backendUrl}/transactions`);
  const data = await res.json();
  if (!data.length) {
    showMessage("🧾 No transactions yet.");
    return;
  }
  let html = "<h3>🧾 Transaction History</h3><ul>";
  data.forEach(t => (html += `<li>${t}</li>`));
  html += "</ul>";
  showMessage(html);
}

// ✅ Buy
async function buyCrypto() {
  const coin = prompt("Enter coin name or symbol (e.g., bitcoin / btc):");
  const amt = prompt("Enter amount to buy:");
  if (!coin || !amt) return;
  const res = await fetch(`${backendUrl}/buy/${coin}/${amt}`, { method: "POST" });
  const text = await res.text();
  const data = JSON.parse(text);
  showMessage(data.message);
}

// ✅ Sell
async function sellCrypto() {
  const coin = prompt("Enter coin name or symbol (e.g., bitcoin / btc):");
  const amt = prompt("Enter amount to sell:");
  if (!coin || !amt) return;
  const res = await fetch(`${backendUrl}/sell/${coin}/${amt}`, { method: "POST" });
  const text = await res.text();
  const data = JSON.parse(text);
  showMessage(data.message);
}

document.getElementById("testBackend").onclick = testBackend;
document.getElementById("showTop10").onclick = showTop10;
document.getElementById("getHistory").onclick = showHistory;
document.getElementById("viewPortfolio").onclick = viewPortfolio;
document.getElementById("viewTransactions").onclick = viewTransactions;
document.getElementById("buyCrypto").onclick = buyCrypto;
document.getElementById("sellCrypto").onclick = sellCrypto;

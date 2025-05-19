const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');
const moveLeftButton = document.getElementById('moveLeft');
const moveRightButton = document.getElementById('moveRight');
const attackButton = document.getElementById('attack');

let player = {
    x: canvas.width / 40,
    y: canvas.height - 50,
    width: 50,
    height: 50,
    speed: 50
};

let enemies = [];
let bullets = [];

// Menggambar player
function drawPlayer() {
    ctx.fillStyle = 'blue';
    ctx.fillRect(player.x, player.y, player.width, player.height);
}

// Menggambar musuh
function drawEnemies() {
    enemies.forEach((enemy, index) => {
        ctx.fillStyle = 'red';
        ctx.fillRect(enemy.x, enemy.y, enemy.width, enemy.height);
        if (enemy.y > canvas.height) {
            enemies.splice(index, 1);
        } else {
            enemy.y += 2;
        }
    });
}

// Menggambar peluru
function drawBullets() {
    bullets.forEach((bullet, index) => {
        ctx.fillStyle = 'black';
        ctx.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
        if (bullet.y < 0) {
            bullets.splice(index, 2);
        } else {
            bullet.y -= 10;
        }
    });
}

// Membuat musuh
function createEnemy() {
    let enemy = {
        x: Math.random() * (canvas.width - 50),
        y: 0,
        width: 50,
        height: 50
    };
    enemies.push(enemy);
}

// Menghandle input
moveLeftButton.addEventListener('click', () => {
    player.x -= player.speed;
    if (player.x < 0) player.x = 0;
});

moveRightButton.addEventListener('click', () => {
    player.x += player.speed;
    if (player.x > canvas.width - player.width) player.x = canvas.width - player.width;
});

attackButton.addEventListener('click', () => {
    let bullet = {
        x: player.x + player.width / 2,
        y: player.y,
        width: 10,
        height: 20
    };
    bullets.push(bullet);
});

// Menggambar semua elemen
function draw() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawPlayer();
    drawEnemies();
    drawBullets();
    checkCollisions();
    requestAnimationFrame(draw);
}

// Mengecek tabrakan
function checkCollisions() {
    bullets.forEach((bullet, bIndex) => {
        enemies.forEach((enemy, eIndex) => {
            if (bullet.x > enemy.x && bullet.x < enemy.x + enemy.width && bullet.y > enemy.y && bullet.y < enemy.y + enemy.height) {
                bullets.splice(bIndex, 1);
                enemies.splice(eIndex, 1);
            }
        });
    });
}

// Inisialisasi game
setInterval(createEnemy, 1000);
draw();
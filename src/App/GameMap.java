package App;



import javax.swing.*;
import java.awt.*;

/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date: 05.08.2021
 */

public class GameMap extends JPanel {

    private final int LABEL_PLAYER = 1;
    private final int LABEL_ENEMY = 2;
    private final int LABEL_EMPTY = 0;
    private final int LABEL_READY = 90;

    public static final int DIRECTION_UP = 8;
    public static final int DIRECTION_DOWN = 2;
    public static final int DIRECTION_LEFT = 4;
    public static final int DIRECTION_RIGHT = 6;

    private Player player;
    private Enemy enemy;
    private GameWindow gameWindow;

    private int cellWidth;
    private int cellHeight;

    private int currentGameLevel;

    private int[][] map;
    private int[][] invisibleMap;
    private int mapWidth;
    private int mapHeight;
    private int mapSizeMin = 2;
    private int mapSizeMax = 4;

    private int countEnemies;

    private boolean isMapActive;
    private boolean isGameStart;

    GameMap(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        setBackground(Color.BLACK);
        this.isMapActive = false;
        this.isGameStart = false;
    }

    void initGame() {
        player = new Player(0,0);
        currentGameLevel = 0;
        startNewRound();
        isMapActive = true;
        isGameStart = true;
    }

    private void startNewRound() {
        initGameWorld();
        initPlayerInWorld();
        initEnemiesInWorld();
        ++currentGameLevel;
        gameWindow.refreshGuiInfo(this);
        gameWindow.recordLog("Start Level " + currentGameLevel);
    }

    private void initGameWorld() {
        mapWidth = Tools.randomIntRange(mapSizeMin, mapSizeMax);
        mapHeight = Tools.randomIntRange(mapSizeMin, mapSizeMax);
        map = new int[mapHeight][mapWidth];
        invisibleMap = new int[mapHeight][mapWidth];
        repaint();
    }

    private void initPlayerInWorld() {
        player.setPosition(mapWidth / 2, mapHeight / 2);
        map[player.getY()][player.getX()] = LABEL_PLAYER;
    }

    private void initEnemiesInWorld() {
        int x;
        int y;

        countEnemies = (mapWidth + mapHeight) / 4;

        for (int i = 0; i < countEnemies; i++) {

            do {
                x = Tools.random.nextInt(mapWidth);
                y = Tools.random.nextInt(mapHeight);
            } while (x == player.getX() && y == player.getY());

            invisibleMap[y][x] = LABEL_ENEMY;
        }
        enemy = new Enemy();

    }

    private void render(Graphics g) {
        drawMap(g);

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if (map[y][x] == LABEL_EMPTY) {
                    continue;
                }

                if (map[y][x] == LABEL_PLAYER) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                }

                if (map[y][x] == LABEL_READY) {
                    g.setColor(Color.GRAY);
                    g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                }
            }
        }
    }

    void update(int direction) {

        if (!isGameStart) {
            return;
        }

        int currentX = player.getX();
        int currentY = player.getY();

        switch (direction) {
            case DIRECTION_UP:
                player.moveUp();
                break;
            case DIRECTION_DOWN:
                player.moveDown();
                break;
            case DIRECTION_LEFT:
                player.moveLeft();
                break;
            case DIRECTION_RIGHT:
                player.moveRight();
                break;
        }

        if (!isValidNextMove(currentY, currentX, player.getY(), player.getX())) {
            return;
        }

        playerNextMoveAction(currentY, currentX, player.getY(), player.getX());
        gameWindow.refreshGuiInfo(this);
        repaint();

        if (isFullMap()) {
            startNewRound();
        }

        if (!player.isPlayerAlive()) {
            isGameStart = false;
            JOptionPane.showMessageDialog(this, "Game Over!");
        }

    }

    private void drawMap(Graphics g) {

        if (!isMapActive) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

        cellWidth = width / mapWidth;
        cellHeight = height / mapHeight;

        g.setColor(Color.WHITE);

        for (int i = 0; i < mapHeight; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, width, y);
        }

        for (int i = 0; i < mapWidth; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, height);
        }

    }

    public String getMapSize() {
        return mapWidth + "x" + mapHeight;
    }

    public int getCurrentGameLevel() {
        return currentGameLevel;
    }

    public int getCountEnemies() {
        return countEnemies;
    }

    public int getPlayerHP() {
        return player.getHealth();
    }

    public int getPlayerSteps() {
        return player.getSteps();
    }

    public void playerNextMoveAction(int currentY, int currentX, int nextY, int nextX) {
        if (invisibleMap[nextY][nextX] == LABEL_ENEMY) {
            player.decreaseHealth(enemy.getPowerAttack());
            countEnemies--;
            gameWindow.recordLog("Enemy give damage > " + enemy.getPowerAttack() + ". Player HP = " + player.getHealth());
        }

        map[currentY][currentX] = LABEL_READY;
        invisibleMap[currentY][currentX] = LABEL_READY;
        map[player.getY()][player.getX()] = LABEL_PLAYER;

        if (Tools.randomAction()) {
            player.heal(Tools.randomIntRange(10, 20));
        }
    }

    public boolean isValidNextMove(int currentY, int currentX, int nextY, int nextX) {
        if (nextY >= 0 && nextY < mapHeight && nextX >= 0 && nextX < mapWidth) {
            gameWindow.recordLog("Player move to [" + (nextX + 1) + ":" + (nextY + 1) + "] success");
            return true;
        } else {
            player.setPosition(currentX, currentY);
            gameWindow.recordLog("Invalid move. Please try again!");
            return false;
        }
    }

    public boolean isFullMap() {
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if (map[y][x] == LABEL_EMPTY) return false;
            }
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }
}


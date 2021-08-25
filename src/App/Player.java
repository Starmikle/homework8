package App;

public class Player {

    private int x;
    private int y;
    private int health;
    private int steps;

    public Player(int x, int y) {
        setPosition(x, y);
        this.health = 100;
        this.steps = 0;
    }

    public void moveUp() {
        this.y -= 1;
        ++steps;
    }

    public void moveDown() {
        this.y += 1;
        ++steps;
    }

    public void moveLeft() {
        this.x -= 1;
        ++steps;
    }

    public void moveRight() {
        this.x += 1;
        ++steps;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void heal(int value) {
        this.health += value;
    }

    public void decreaseHealth(int value) {
        this.health -= value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public int getSteps() {
        return steps;
    }

    public boolean isPlayerAlive() {
        return health > 0;
    }
}


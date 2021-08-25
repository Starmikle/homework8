package App;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date: 05.08.2021
 */

public class GameWindow extends JFrame {

    private int winWidth = 800;
    private int winHeight = 600;
    private int winPosX = 200;
    private int winPoSY = 150;

    private GameMap map;
    private JPanel gui;

    private JPanel gameControlsPanel;
    private JButton btnStartGame;
    private JButton btnExitGame;
    private JButton btnClearLog;

    private JPanel gameInfoPanel;
    private JLabel currentLevel;
    private JLabel currentMapSize;
    private JLabel countEnemies;

    private JPanel playerInfoPanel;
    private JLabel playerHealth;
    private JLabel playerSteps;

    private JPanel playerActionPanel;
    private JButton moveUp;
    private JButton moveDown;
    private JButton moveLeft;
    private JButton moveRight;

    private JScrollPane scrollPanel;
    private JTextArea gameLog;

    GameWindow() {
        prepareWindow();

        map = new GameMap(this);

        prepareGui();

        add(gui, BorderLayout.EAST);
        add(map);

        setVisible(true);
    }

    private void prepareGui() {
        gui = new JPanel();
        gui.setLayout(new GridLayout(5,1));

        prepareGameControls();
        prepareGameInfo();
        preparePlayerInfo();
        preparePlayerActions();
        prepareGameLog();

        gui.add(gameControlsPanel);
        gui.add(gameInfoPanel);
        gui.add(playerInfoPanel);
        gui.add(playerActionPanel);
        gui.add(scrollPanel, BorderLayout.SOUTH);

    }

    private void prepareGameControls() {
        gameControlsPanel = new JPanel();
        gameControlsPanel.setLayout(new GridLayout(4,1));

        btnStartGame = new JButton("Start New Game");
        btnStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordLog("Game Start");
                map.initGame();
            }
        });

        btnExitGame = new JButton("Exit Game");
        btnExitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        btnClearLog = new JButton("Clear Log");
        btnClearLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLog.setText("");
            }
        });

        gameControlsPanel.add(new JLabel("== Game Menu =="));
        gameControlsPanel.add(btnStartGame);
        gameControlsPanel.add(btnExitGame);
        gameControlsPanel.add(btnClearLog);

    }

    private void prepareGameInfo() {
        gameInfoPanel = new JPanel();
        gameInfoPanel.setLayout(new GridLayout(4,1));
        gameInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        currentLevel = new JLabel("Current Level: -");
        currentMapSize = new JLabel("Current map size: -");
        countEnemies = new JLabel("Count Enemies: -");

        gameInfoPanel.add(new JLabel("== Game Info =="));
        gameInfoPanel.add(currentLevel);
        gameInfoPanel.add(currentMapSize);
        gameInfoPanel.add(countEnemies);
    }

    private void preparePlayerInfo() {
        playerInfoPanel = new JPanel();
        playerInfoPanel.setLayout(new GridLayout(3,1));
        playerInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        playerHealth = new JLabel("Health: -");
        playerSteps = new JLabel("Steps: -");

        playerInfoPanel.add(new JLabel("== Player Info =="));
        playerInfoPanel.add(playerHealth);
        playerInfoPanel.add(playerSteps);

    }

    private void preparePlayerActions() {
        playerActionPanel = new JPanel();
        playerActionPanel.setLayout(new GridLayout(1,4));

        moveUp = new JButton("\uD83E\uDC61");
        moveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.update(GameMap.DIRECTION_UP);
            }
        });

        moveDown = new JButton("\uD83E\uDC63");
        moveDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.update(GameMap.DIRECTION_DOWN);
            }
        });

        moveLeft = new JButton("\uD83E\uDC60");
        moveLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.update(GameMap.DIRECTION_LEFT);
            }
        });

        moveRight = new JButton("\uD83E\uDC62");
        moveRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.update(GameMap.DIRECTION_RIGHT);
            }
        });

        playerActionPanel.add(moveLeft);
        playerActionPanel.add(moveRight);
        playerActionPanel.add(moveUp);
        playerActionPanel.add(moveDown);

    }

    private void prepareGameLog() {
        gameLog = new JTextArea();
        scrollPanel = new JScrollPane(gameLog);
        gameLog.setEditable(false);
        gameLog.setLineWrap(true);

    }

    private void prepareWindow() {
        setSize(winWidth, winHeight);
        setLocation(winPosX, winPoSY);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("This is GAME");
    }

    void recordLog(String msg) {
        gameLog.append(msg + "\n");
    }

    void refreshGuiInfo(GameMap map) {
        currentLevel.setText("Current Level: " + map.getCurrentGameLevel());
        currentMapSize.setText("Current map size: " + map.getMapSize());
        countEnemies.setText("Count Enemies: " + map.getCountEnemies());
        playerHealth.setText("Health: " + map.getPlayerHP());
        playerSteps.setText("Steps: " + map.getPlayerSteps());
    }
}


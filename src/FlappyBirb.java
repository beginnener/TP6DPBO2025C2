import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBirb extends JPanel implements ActionListener, KeyListener {
    //  atribut frame
    int frameWidth = 360;
    int frameHeight = 640;

    // image attributes
    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    // attribute player
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    Player player;

    // atribut pipe
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    ArrayList<Pipe> pipes; // array untuk simpan pipe-pipe

    // timer
    Timer gameLoop;
    Timer pipeSpawner;

    // gravitasi
    int gravity = 1;

    // atribut game tambahan
    boolean gameOver = false; // utk cek kalau gameover
    boolean isPaused = false; // utk cek kalau pause
    int score = 0; // utk hitung skor
    Font gameFont = new Font("Arial", Font.BOLD, 32); // utk font game

    // constructor
    public FlappyBirb(){
        setPreferredSize(new Dimension(360, 640));
        setFocusable(true);
        //setBackground(Color.BLUE);
        addKeyListener(this);

        // load images;
        backgroundImage = new ImageIcon(getClass().getResource("Assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("Assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("Assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("Assets/upperPipe.png")).getImage();

        // instansiasi player
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        // instansiasi pipe
        pipes = new ArrayList<Pipe>();

        // untuk menampilkan pipe
        pipeSpawner = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        pipeSpawner.start();

        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    public void placePipes(){
        int randomPosY = (int) (pipeStartPosY - pipeHeight/4 - Math.random() * pipeHeight/2);
        int openingSpace = frameHeight/4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);

        System.out.println("upperPipe spawned at: " + upperPipe.getPosY() + " lowerPipe spawned at: " + lowerPipe.getPosY());
    }

    public void checkCollision() {
        Rectangle playerRect = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());

        // pakai enhanced for (biar lebih rapih aja)
        for (Pipe pipe : pipes) {
            Rectangle pipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());
            if (playerRect.intersects(pipeRect)) {
                gameOver = true;
                gameLoop.stop();
                pipeSpawner.stop();
            }
        }

        // cek kalau burung jatuh ke bawah
        if (player.getPosY() > frameHeight) {
            gameOver = true;
            gameLoop.stop();
            pipeSpawner.stop();
        }
    }

    public void paintComponent(Graphics g){ // dipanggil otomatis setiap repaint() dipanggil
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight()
        , null);

        // untuk tampilan score
        g.setColor(Color.WHITE);
        g.setFont(gameFont);

        g.drawString("Score: " + score, frameWidth/3, 40);

        // tampilkan pipe
        //System.out.println("Banyak pipe: " + pipes.size());
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }

        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("GAME OVER", frameWidth / 4, frameHeight / 2);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to Restart", frameWidth / 4 + (frameWidth/24), frameHeight / 2 + 40);
        }

        if (isPaused && !gameOver) {
            g.setColor(Color.YELLOW);
            g.drawString("PAUSED", frameWidth / 3, frameHeight / 2);
        }
    }

    public void move(){
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        // atur posisi pipa
        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());

            // Hapus pipe jika sudah keluar dari layar
            if (pipe.getPosX() + pipe.getWidth() < 0) {
                pipes.remove(i);
                i--; // Kurangi i karena kita menghapus elemen
            }
        }
    }

    public void restartGame() {
        gameOver = false;
        isPaused = false;
        score = 0;
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);
        pipes.clear();
        gameLoop.start();
        pipeSpawner.start();
    }

    @Override
    public void actionPerformed(ActionEvent e){ // per 1000/60 ms method ini dipanggil otomatis oleh sistem (dar gameloop.star())
        if(!gameOver && !isPaused){
            move();
            checkCollision(); // cek collision setiap habis move
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            player.setVelocityY(-10);
        }
        if(e.getKeyCode() == KeyEvent.VK_R && gameOver){
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e){

    }

}

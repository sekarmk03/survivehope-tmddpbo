/*
 * Filename     : Game.java
 * Programmer   : Sekar Madu Kusumawardani
 * Date         : 2022-06-14
 * Email        : sekarmadu99@gmail.com
 * Website      : https://sekarmk03.github.io/
 * Deskripsi    : kelas Game untuk membuat game baru
*/

package viewmodel;

import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Random;
import javax.sound.sampled.Clip;
// mengakses model
import model.Player;
import model.TableExperiences;
// mengakses konstanta
import static viewmodel.Constants.gameOption.GAME_WIDTH;
// mengakses view
import view.GameWindow;
import view.Menu;

/**
 *
 * @author sekar
 */
public class Game extends JPanel implements Runnable {
    // mengimplementasikan Runnable java untuk membuat thread
    
    // properti thread
    private Thread gameThread;
    private boolean running = false; // deteksi game berjalan
    
    private GameWindow window; // window game
    private Clip audio; // backsound
    
    // properti objek dlm game
    private final Player player; // player
    private final ObstacleHandler obs_handler; // obstacle
    private String username; // username
    private int adapt; // skor adapt
    private int fall; // skor fall
    
    public enum STATE{
        Game,
        GameOver
    }
    
    public Game(){
        // konstruktor
        
        // membuat player dgn posisi random
        Random rand = new Random();
        int playerPos = rand.nextInt(1200 - 800) + 800;
        this.player = new Player(GAME_WIDTH - playerPos, 200);
        
        // membuat obstacle
        this.obs_handler = new ObstacleHandler();
        
        // membuat backsound
        Sound bgm = new Sound();
        audio = bgm.playSound(this.audio, "02_Main_BGM.wav");
    }
    
    // mengeset STATE game
    public STATE gameState = STATE.Game;
    
    public synchronized void StartGame(GameWindow gw){
        // memulai menjalankan game
        gameThread = new Thread(this); // buat thread baru
        gameThread.start(); // thread dijalankan
        this.window = gw; // buat window
        running = true; // set running
    }
    
    @Override
    public void paint(Graphics g){
        // membuat Component
        super.paint(g); // method parent
        player.render(g); // me render objek player
        obs_handler.renderObstacle(g); // me render objek obstacle
    }
    
    @Override
    public void run() {
        // meng override method run dari parent Runnable
        while(true){
            // selama true (game loop)
            try {
                updateGame(); // update objek game
                repaint(); // membuat ulang Component (update paint())
                Thread.sleep(1000L/60L); // pause thread
                this.adapt = player.getAdapt(); // mengambil skor adapt
                this.fall = player.getFall(); // mengambil skor fall
                if(this.player.getBoundRight().x > 1000) {
                    // jika posisi tabrakan player > 1000
                    // maka player tergerus zaman (keluar frame dari kanan)
                    this.gameState = STATE.GameOver;
                }
                if(gameState == STATE.GameOver) {
                    // jika state saat ini GameOver
                    Sound bgm = new Sound(); 
                    bgm.stopSound(this.audio); // stop bgm
                    saveScore(); // simpan skor adapt dan fall
                    close(); // tutup window
                    new Menu().setVisible(true); // menampilkan menu
                    stopGame(); // stop game
                }
            } catch (InterruptedException ex) {
                // log error
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void updateGame(){
        // update objek dalam game
        obs_handler.addObstacle(); // menambah obstacle
        obs_handler.updateObstacle(); // mengupdate kondisi obstacle
        player.update(obs_handler.getObstacles()); // mengupdate kondisi player
    }
    
    public synchronized void stopGame() {
        // menghentikan game
        try{
            gameThread.join(); // menghentikan thread
            running = false; // set tidak berjalan
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    void close() {
        // menutup window
        window.CloseWindow();
    }

    public Player getPlayer(){
        // mengambil player
        return this.player;
    }
    
    public void setUsername(String username) {
        // mengeset username game
        this.username = username;
    }
    
    public void setScore(int adapt, int fall) {
        // mengeset skor player di awal game
        this.player.setAdapt(adapt);
        this.player.setFall(fall);
    }
    
    public void saveScore() {
        // menyimpan skor saat game over
        // mainkan backsound game over
        Sound gobgm = new Sound();
        audio = gobgm.playSound(this.audio, "03_Game_Over.wav");
        
        try {
            // tambah atau update data (skor adapt dan fall)
            TableExperiences texperiences = new TableExperiences();
            texperiences.insertData(this.username, this.adapt, this.fall);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        // menampilan panel game over
        JOptionPane.showMessageDialog(null, "Username : " + this.username + "\nAdapt : " + this.adapt + "\nFall : " + this.fall, "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
        // berhentikan sound saat panel game over hilang
        gobgm.stopSound(this.audio);
    }
    
}

/*
 * Filename     : ObstacleHandler.java
 * Programmer   : Sekar Madu Kusumawardani
 * Date         : 2022-06-14
 * Email        : sekarmadu99@gmail.com
 * Website      : https://sekarmk03.github.io/
 * Deskripsi    : kelas ObstacleHandler untuk menangani pembuatan objek obstacle
*/
package viewmodel;

import java.util.Random;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
// mengakses konstanta
import static viewmodel.Constants.gameOption.GAME_HEIGHT;
import static viewmodel.Constants.gameOption.GAME_WIDTH;
// mengakses model
import model.Obstacle;

/**
 *
 * @author sekar
 */
public class ObstacleHandler {
    private final int MIN_Y = 100; // posisi y minimal
    private final int MAX_Y = GAME_HEIGHT - 50; // posisi y maksimal (batasan bawah)
    private final Random rand = new Random(); // inisialisasi library random
    
    private final int MAX_OBSTACLE = 15; // jumlah maks obstacle dlm 1 frame
    private final int MIN_GAP = 52; // lebar gap minimum antar obstacle
    private int OBS_WIDTH = 50; // lebar obstacle
    private int obstacleNumber = 0; // jumlah obstacle
    private final ArrayList<Obstacle> obstacles = new ArrayList<>(); // list obstacle
    
    public ObstacleHandler() {
        // konstruktor
        makeFloor(); // buat lantai
    }
    
    private void makeFloor(){
        // buat lantai
        // karena lantai, maka nilai obsType nya 0 (argumen ke 4)
        int floorHeight = 50; // tinggi lantai
        Obstacle floor = new Obstacle(0, GAME_HEIGHT - floorHeight, GAME_WIDTH, floorHeight, 0);
        obstacles.add(floor); // tambah lantai yg baru dibuat
        obstacleNumber++; // increment jumlah obstacle
    }
    
    public void updateObstacle(){
        // mengupdate kondisi obstacle
        Iterator<Obstacle> itr = obstacles.iterator(); // iterator untuk setiap obstacle
        while(itr.hasNext()) {
            // selama obstacle ada
            Obstacle ob = itr.next();
            if(ob.getX() > GAME_WIDTH){
                // jika posisi y obstacle melebihi batas y frame
                itr.remove(); // hilangkan obstacle
                obstacleNumber--; // decrement jumlah obstacle
            }else{
                // jika tidak, update posisi obstacle
                ob.update();
            }
        }
    }
    
    public void renderObstacle(Graphics g){
        // merender obstacle
        for (Obstacle ob : obstacles) {
            // untuk setiap obstacle
            ob.render(g); // gambar objeknya
        }
    }
     
    public void addObstacle(){
        // menambah jumlah obstacle
        if(obstacleNumber < MAX_OBSTACLE){
            // jika jumlah obstacle dalam frame
            // masih kurang dari batas maks obstacle
            float x = 0; // posisi x di paling kiri
            float y = rand.nextInt((MAX_Y - 50) - MIN_Y) + MIN_Y; // posisi y sesuai batas
            if(obstacleNumber > 1){
                // jika jumlah obstacle lebih dari 1
                // maka ambil obstacle, simpan data x obstacle
                x =  obstacles.get(obstacles.size() - 1).getX() - ((rand.nextInt(4 - 1) + 1)*MIN_GAP);
            }
            // buat obstacle baru
            Obstacle obstacle = new Obstacle(x, y, OBS_WIDTH, (int) (MAX_Y - y), 1);
            obstacles.add(obstacle); // tambahkan ke list
            obstacleNumber++; // increment jumlah obstacle
        }
    }

    public ArrayList<Obstacle> getObstacles() {
        // mengambil obstacle
        return obstacles;
    }
    
}

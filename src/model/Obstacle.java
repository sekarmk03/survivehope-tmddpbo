/*
 * Filename     : Obstacle.java
 * Programmer   : Sekar Madu Kusumawardani
 * Date         : 2022-06-14
 * Email        : sekarmadu99@gmail.com
 * Website      : https://sekarmk03.github.io/
 * Deskripsi    : kelas Obstacle yaitu objek batang
*/
package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
// mengakses viewmodel
import viewmodel.Constants;
import static viewmodel.Constants.gameOption.GAME_SPEED;

/**
 *
 * @author sekar
 */
public class Obstacle extends GameObject{
    // properti obstacle
    private final int obsType; // penentu tipenya (lantai/pilar)
    
    // inisialisasi library
    Random rand = new Random(); // fungsi random
    Color color; // warna
    
    public Obstacle(float x, float y, int width, int height, int obsType) {
        // konstruktor
        super(x, y, width, height);
        this.obsType = obsType;
        setColorObstacle();
    }
    
    private void setColorObstacle(){
        // penentu warna obstacle
        // jika tipenya 0 (lantai) maka warnanya hijau
        if(obsType  == 0) color = new Color(51, 255, 102);
        else{
            // jika tipenya 1 (bukan lantai) maka warnanya di random
            int r =  ((Constants.gameOption.GAME_HEIGHT / 255) * height) % 255;
            int g = rand.nextInt(255);
            int b = rand.nextInt(255 - 100) + 100;
            color = new Color(r, g, b);
        }
    }
    
    public void update() {
        // mengupdate posisi dan collisionBox
        updatePos();
        updateCollisionBox();
    }
    
    @Override
    public void render(Graphics g) {
        // mengoverride fungsi render di parent
        g.setColor(color); // mengubah warna
        g.fillRect((int)x, (int)y, width, height); // membuat persegi panjang
    }
    
    private void updatePos(){
        // mengupdate posisi
        if(obsType > 0)
            x += GAME_SPEED;
    }

    public float getX(){
        // mengambil nilai x si obstacle
        return x;
    }
}

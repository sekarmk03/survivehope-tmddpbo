/*
 * Filename     : Player.java
 * Programmer   : Sekar Madu Kusumawardani
 * Date         : 2022-06-14
 * Email        : sekarmadu99@gmail.com
 * Website      : https://sekarmk03.github.io/
 * Deskripsi    : kelas Player yaitu pemain
*/

package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
// mengakses konstanta
import static viewmodel.Constants.gameOption.*;

/**
 *
 * @author sekar
 */
public class Player extends GameObject{
    private boolean left;
    private boolean up;
    private boolean right;
    private boolean down;
    private boolean inAir = true;
    
    private float playerSpeed = 5.0f;
    private float jumpStrength = 20.0f;
    private float airSpeed = 0;
    private float gravity = 0.3f;
    private float xSpeed = 0;
    
    private int fall = 0;
    private int adapt = 0;
    private int count = 0;
    private int tempY = 0;
    
    public Player(int x, int y) {
        // konstruktor, set properti parent
        super(x, y, 50, 50);
    }
    
    public void update(ArrayList<Obstacle> ob){
        // mengupdate posisi obstable
        updatePos(ob);
        updateCollisionBox();
    }
    
    @Override
    public void render(Graphics g){
        // mengoverride method parent
        
        // background
        g.setColor(new Color(228, 250, 255));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        
        // kepala manusia
        g.setColor(new Color(242, 242, 2));
        g.fillOval((int)x, (int)y, 50, 50);
        
        // wajah manusia
        // mata
        g.setColor(Color.BLACK);
        g.fillOval((int)x + 10, (int)y + 12, 7, 7);
        g.fillOval((int)x + 30, (int)y + 12, 7, 7);
        // mulut
        g.drawArc((int)x + 12, (int)y + 5, 25, 30, -30, -120);
        
        // skor adapt dan fall
        g.setFont(new java.awt.Font("Segoe UI", 1, 13));
        g.setColor(Color.WHITE);
        g.fillRoundRect(15, 5, 100, 20, 15, 15);
        g.fillRoundRect(15, 35, 100, 20, 15, 15);
        g.setColor(Color.decode("#3A1070"));
        g.drawString("Adapt : " + Integer.toString(this.adapt), 20, 20);
        g.drawString("Fall : " + Integer.toString(this.fall), 20, 50);
    }
    
    public void updatePos(ArrayList<Obstacle> AOb){
        // mengupdate kondisi obstacle dan player
        if(left && right || !left && !right) {
            // jika klik kanan dan kiri atau tidak klik kanan dan kiri
            // kecepatan player biasa (mengikuti game)
            xSpeed = GAME_SPEED;
        } else if(left) {
            // jika klik kiri
            // kecepatan player berkurang
            xSpeed -= playerSpeed;
        } else if(right) {
            // jika klik kanan
            // kecepatan player bertambah
            xSpeed += playerSpeed + 1;
        }

        if(xSpeed > 4) {
            // kecepatan maks 4
            xSpeed = 4;
        } else if(xSpeed < -4) {
            // kecepatan min -4
            xSpeed = -4;
        }
        
        // lompat
        if(up && !inAir){
            inAir = true;
            airSpeed -= jumpStrength;
        }
        
        // di lantai
        if(!inAir && !isOnFloor(AOb)){
            inAir = true;
        }
        
        // di udara
        if(inAir){
            airSpeed += gravity;
        }
        
        for(Obstacle ob : AOb){
            // untuk setiap obstacle
            if(getBoundBottom().intersects(ob.getCollisionBox())){
                // jika bound bawah player beririsan dengan bound collisionBox
                inAir = false; // berarti tidak di udara
                airSpeed = 0; // kecepatan udara 0
                y = ob.getCollisionBox().y - height; // y tempat collision
                
                if(fall == 0 && count == 0) {
                    // jika game baru dimulai
                    tempY = (int) y;
                    count++;
                }
                
                if(tempY != y) {
                    // jika y player bertabrakan sebelumnya
                    // tidak sama dengan y sekarang
                    if(y == 500) {
                        // jika y 500 berarti collision dgn lantai
                        fall++;
                    }
                    if(y < 500) {
                        // jika y < 500 berarti collision dgn pilar
                        adapt++;
                    }
                    tempY = (int) y;
                }
            }
            
            // jika player nabrak
            // kembalikan ke speed normal
            if(getBoundRight().intersects(ob.getCollisionBox())){
                // jika nabrak kanan
                xSpeed = GAME_SPEED;
                x = ob.getCollisionBox().x - width - 1;
            }
            if(getBoundLeft().intersects(ob.getCollisionBox())){
                // jika nabrak kiri
                xSpeed = GAME_SPEED;
                x = ob.getCollisionBox().x + ob.getCollisionBox().width + 1;
            }
            if(getBoundTop().y < 0){
                // jika nabrak atas
                y = 0;
                airSpeed = 0;
            }
            if(getBoundLeft().x < 0){
                // jika nabrak kiri sampai mentok ke frame game
                x = 0;
            }
        }
        x += xSpeed;
        y += airSpeed;
    }
    
    public boolean isOnFloor(ArrayList<Obstacle> AOb){
        // method mengecek apakah di lantai
        for(Obstacle ob : AOb){
            // jika batas bawah player beririsan dgn collision box
            if(getBoundBottom().intersects(ob.getCollisionBox())) return true;
            // System.out.println("FALL");
        }
        return false;
    }
    
    public Rectangle getBoundBottom(){
        // membuat batas bawah
        return new Rectangle((int) (x+(width/2)-(width/4)), (int) (y+(height/2)), width/2, height/2);
    }
    
    public Rectangle getBoundTop(){
        // membuat batas atas
        return new Rectangle((int) (x+(width/2)-(width/4)), (int) (y), width/2, height/2);
    }
    
    public Rectangle getBoundRight(){
        // membuat batas kanan
        return new Rectangle((int) x+width-5, (int)y + 5, 5, height-10);
    }
    
    public Rectangle getBoundLeft(){
        // membuat batas kiri
        return new Rectangle((int) x, (int)y + 5, 5, height-10);
    }

    public void setLeft(boolean left) {
        // set player ke kiri
        this.left = left;
    }

    public void setUp(boolean up) {
        // set player ke atas
        this.up = up;
    }

    public void setRight(boolean right) {
        // set player ke kanan
        this.right = right;
    }
    
    public void setDown(boolean down) {
        // set player ke bawah
        this.down = down;
    }
    
    public void setAdapt(int adapt) {
        // set skor adapt
        this.adapt = adapt;
    }
    
    public void setFall(int fall) {
        // set skor fall
        this.fall = fall;
    }
    
    public int getAdapt() {
        // mendapatkan skor adapt
        return this.adapt;
    }
    
    public int getFall() {
        // mendapatkan skor fall
        return this.fall;
    }
    
}

/*
 * Filename     : GameObject.java
 * Programmer   : Sekar Madu Kusumawardani
 * Date         : 2022-06-14
 * Email        : sekarmadu99@gmail.com
 * Website      : https://sekarmk03.github.io/
 * Deskripsi    : kelas GameObject untuk blueprint objek dalam game
*/
package model;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author sekar
 */
public abstract class GameObject {

    // properti posisi
    protected float x;
    protected float y;
    // properti ukuran
    protected int width;
    protected int height;
    // box pendeteksi collision
    protected Rectangle collisionBox;
    
    public GameObject(float x, float y, int width, int height) {
        // konstruktor
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        // buat box baru
        this.collisionBox = new Rectangle((int)x, (int)y, width, height);
    }
    
    protected void updateCollisionBox(){
        // mengupdate posisi box
        this.collisionBox.x = (int) x;
        this.collisionBox.y = (int) y;
    }

    public Rectangle getCollisionBox() {
        // mengambil box (berbentuk Rectangle)
        return this.collisionBox;
    }
    
    public abstract void render(Graphics g);
    
}

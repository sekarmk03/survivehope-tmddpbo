/*
 * Filename     : Main.java
 * Programmer   : Sekar Madu Kusumawardani
 * Date         : 2022-06-15
 * Email        : sekarmadu99@gmail.com
 * Website      : https://sekarmk03.github.io/
 * Deskripsi    : kelas Main untuk mengakses package view
*/

// mengakses package view
import view.Menu;

/**
 *
 * @author sekar
 * Saya Sekar Madu Kusumawardani mengerjakan evaluasi Tugas Masa Depan dalam mata
 * kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
 * tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
 * 
*/

public class Main {
    public static void main(String[] args) {
        // memunculkan window menu
        new Menu().setVisible(true);
    }
}

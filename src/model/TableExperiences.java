/*
 * Filename     : TableExperiences.java
 * Programmer   : Sekar Madu Kusumawardani
 * Date         : 2022-06-14
 * Email        : sekarmadu99@gmail.com
 * Website      : https://sekarmk03.github.io/
 * Deskripsi    : kelas TableExperiences untuk mengakses tabel pengguna
*/

package model;

import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sekar
 */
public class TableExperiences extends DB {
    private String tableName; // nama tabel

    public TableExperiences() throws Exception, SQLException{
        // konstruktor
        super();
        this.tableName = "texperiences";
    }

    public void getTExperiences(){
        // mengeksekusi query untuk mengambil semua data pada tabel pengguna
        try {
            String query = "SELECT * from " + this.tableName;
            createQuery(query);
        } catch (Exception e) {
            // menampilkan error
            System.out.println(e.toString());
        }
    }

    public void getDataTExperiences(String username) {
        // mengeksekusi query untuk mengambil 1 record data
        // berdasarkan username
        try {
            String query = "SELECT * from " + this.tableName +" WHERE username='" + username + "'";
            createQuery(query);
        } catch (Exception e) {
            // menampilkan error
            System.err.println(e.toString());
        }
    }

    public void insertData(String username, int adapt, int fall){
        // Cek apakah harus ada update
        boolean update = false;
        try {
            TableExperiences temp = new TableExperiences();
            temp.getDataTExperiences(username);
            // cek apakah username sudah ada dalam database
            if(temp.getResult().next()) {
                update = true;
            } else {
                update = false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Untuk insert
        if(!update){
            try {
                String query = "INSERT INTO " + this.tableName + " VALUES(null, '" + username + "', " + Integer.toString(adapt) + ", " + Integer.toString(fall) + ")";
                createUpdate(query);
            } catch (Exception e) {
                // menampilkan error
                System.out.println(e.toString());
            }
        }
        // Untuk update
        else if(update){
            try {
                String query = "UPDATE " + this.tableName + " SET adapt=" + adapt + ", fall=" + fall + " WHERE username='" + username + "'";
                createUpdate(query);
            } catch (Exception e) {
                // menampilkan error
                System.out.println(e.getMessage());
            }
        }
    }
    
    // Membuat datatable
    public DefaultTableModel setTable(){
        
        DefaultTableModel dataTable = null;
        try{
            // membuat header tabel
            Object[] column = {"Username", "Adapt", "Fall"};
            dataTable = new DefaultTableModel(null, column);
            
            // query data untuk ditampilkan di tabel
            String query = "SELECT * from " + this.tableName + " order by adapt DESC";
            this.createQuery(query);
            // mengambil data per baris
            while(this.getResult().next()){
                Object[] row = new Object[3];
                // mengambil per kolom
                row[0] = this.getResult().getString(2);
                row[1] = this.getResult().getString(3);
                row[2] = this.getResult().getString(4);
                dataTable.addRow(row);
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        // mengembalikan data yang sudah diambil
        return dataTable;
    }
}

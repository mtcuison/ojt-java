/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ojt;

/**
 *
 * @author user
 */
public class FormName {

    String name;
    int rows;

    public FormName(int rows, String name) {
        this.rows = rows;
        this.name = name;

    }

    public void setRow(int rows) {
        this.rows = rows;
    }

    public int getRow() {
        return rows;
    }

    public void setName(String madename) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

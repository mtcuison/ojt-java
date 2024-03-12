/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ojt;

import javafx.scene.control.TextField;

/**
 *
 * @author user
 */
public class MadeEntry extends FormName {

    String madeId;
     public MadeEntry(int rows, String name) {
        super(rows,name);
    }
    public MadeEntry(int rows, String name, String madeId) {
        super(rows,name);
        this.madeId = madeId;
    }
    public void setMadeId(String madeId) {
        this.madeId = madeId;
    }

    public String getMadeId() {
        return madeId;
    }

    public void setRow(int rows) {
        this.rows = rows;
    }

    public int getRows() {
        return rows;
    }

    public void setMadeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

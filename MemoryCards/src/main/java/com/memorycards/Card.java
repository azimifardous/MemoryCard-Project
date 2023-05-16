package com.memorycards;

public class Card {
    private String name;
    private int row;
    private int column;
    private boolean isFlipped = false;

    public Card(String name, int row, int column) {
        this.name = name;
        this.row = row;
        this.column = column;
    }

    public boolean isFlipped() { return isFlipped; }
    public void setFlipped(boolean flipped) { isFlipped = flipped; }
    public String getName() {
        return name;
    }
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }

}

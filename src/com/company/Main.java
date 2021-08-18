package com.company;

public class Main {

    public static void main(String[] args) {
	    RedBlackTree red = new RedBlackTree();
	    red.insert(1);
        red.insert(2);
        red.insert(3);
        red.insert(4);
        red.insert(5);
        red.delete(1);
        red.delete(4);
        red.delete(5);
        red.delete(3);
        red.delete(2);
        red.printData();
    }
}

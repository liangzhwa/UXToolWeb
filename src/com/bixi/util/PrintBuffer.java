package com.bixi.util;

import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class PrintBuffer implements Runnable {
    private BufferedReader br;
    public JFrame console;
    JTextArea text;
    
    public PrintBuffer(BufferedReader br) {
        this.br = br;
//        console = new JFrame("UXTool");
//        text = new JTextArea("hello uxtool \n");
//        console.add(text);
//        console.setBounds(200, 100, 800, 600);
//        console.setVisible(true);
//        console.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void run() {
        String line = null;
        try {
             while ((line = br.readLine()) != null) {
             }
         } catch (IOException e) {
             e.printStackTrace();
        }
    }
    
}

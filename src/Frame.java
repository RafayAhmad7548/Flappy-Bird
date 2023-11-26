import java.awt.event.*;

import javax.swing.*;

public class Frame extends JFrame implements KeyListener{
    
    Panel panel;

    public Frame(){

        panel = new Panel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Flappy Bird");
        this.setResizable(false);
        this.setVisible(true);
        this.setBounds(500, 190, 920, 700);
        this.addKeyListener(this);
        this.add(panel);

    }

    public void keyTyped(KeyEvent e){
    }

    
    public void keyPressed(KeyEvent e){

        switch(e.getKeyCode()){
            case 87 : panel.birdY-=50;
            break;
            case 32 :
            if(panel.isGameOver==true){
                panel.gameStart();
            }
        }
    
    }
    public void keyReleased(KeyEvent e){
    }
}

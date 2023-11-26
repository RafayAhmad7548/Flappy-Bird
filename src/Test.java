import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;


public class Test implements ActionListener{

    Timer timer;
    JFrame frame;
    JButton button;


    public Test(){

        button = new JButton("button", null);
        button.addActionListener(this);

        frame = new JFrame("test", null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(500, 500);
        frame.setLayout(new FlowLayout());
        frame.add(button);

        

        timer = new Timer(10, this);
        timer.setRepeats(true);
        timer.setDelay(1000);
        timer.start();
        
    }
    public static void main(String[] args){
        new Test();
        

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==button){
            timer.stop();
        }
        if(e.getSource()==timer){
            System.out.println("yay");
        }
    }
}

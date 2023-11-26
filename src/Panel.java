import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Panel extends JPanel implements MouseListener, ActionListener{

    //main menu stuff
    JLabel title;
    JLabel play;
    boolean isGameStarted;

    //gameover stuff
    JLabel gameOver;
    JLabel yourScore;
    JLabel highScoreLabel;
    JLabel playAgain;
    boolean isGameOver;

    //gameplay stuff
    JLabel bird;
    JLabel[] pipeUps = {new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel()};
    JLabel[] pipeDowns = {new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel()};
    JLabel scoreLabel;
    JLabel bg = new JLabel(new ImageIcon("lib/bg.jpg"));

    int birdY = 225;
    int[] pipeX = new int[5];
    int[] pipeY = new int[5];
    int pipeGap = 400;
    int score;
    int highScore;

    //timer
    Timer moveBirdDown;
    Timer movePipes;
    Timer scoreCounter;

    
    public Panel(){

        //main menu stuff
        title = new JLabel(new ImageIcon("lib/Title.png"));
        play = new JLabel(new ImageIcon("lib/play.png"));

        title.setBounds(235, 50, 450, 130);
        play.setBounds(360, 300, 200, 141);

        //gameover stuff
        gameOver = new JLabel(new ImageIcon("lib/gameover.png"));
        yourScore = new JLabel();
        highScoreLabel = new JLabel();
        playAgain = new JLabel(new ImageIcon("lib/playagain.png"));

        gameOver.setBounds(260, 100, 400, 111);
        yourScore.setBounds(0, 225, 920, 50);
        yourScore.setHorizontalAlignment(SwingConstants.CENTER);
        yourScore.setFont(new Font(null, Font.PLAIN, 35));
        highScoreLabel.setBounds(0, 275, 920, 50);
        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        highScoreLabel.setFont(new Font(null, Font.PLAIN, 35));
        playAgain.setBounds(335, 350, 250, 70);

        //gameplay stuff
        bird = new JLabel(new ImageIcon("lib/bird.png"));
        scoreLabel = new JLabel(" Score: " + score);

        bird.setSize(60, 50);
        bird.setLocation(430, birdY);

        scoreLabel.setFont(new Font(null, Font.BOLD, 25));
        scoreLabel.setBackground(Color.BLACK);
        scoreLabel.setOpaque(true);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(5, 10, 150, 50);

        bg.setBounds(0, 0, 920, 700);

        //timer stuff
        moveBirdDown = new Timer(0, this);
        movePipes = new Timer(0, this);
        scoreCounter = new Timer(0, this);

        moveBirdDown.setDelay(5);
        movePipes.setDelay(5);
        scoreCounter.setDelay(100);

        //panel stuff
        this.setLayout(null);
        this.setPreferredSize(new Dimension(920, 700));
        this.addMouseListener(this);
        
        this.add(title);
        this.add(play);
        this.add(bird);
        this.add(bg);
        
    }
  
    public void gameStart(){

        birdY = 225;

        for(int i=0;i<5;i++){
            pipeX[i] = 920 + (i*pipeGap);
            pipeY[i] = (int)Math.floor(Math.random() *(550 - 200 + 1) + 200);
        }

        this.remove(gameOver);
        this.remove(yourScore);
        this.remove(highScoreLabel);
        this.remove(playAgain);

        this.remove(title);
        this.remove(play);
        this.remove(bg);

        this.add(scoreLabel);
        for(JLabel ups : pipeUps){
        ups.setIcon(new ImageIcon("lib/up.png"));
        ups.setSize(150, 550);
        this.add(ups);
        }
        for(JLabel downs : pipeDowns){
            downs.setIcon(new ImageIcon("lib/down.png"));
            downs.setSize(150, 550);
            this.add(downs);
        }
        this.add(bg);

        scoreCounter.start();
        moveBirdDown.start();
        movePipes.start();

        isGameOver = false;

    }

    public void gameOver(){
       
        scoreCounter.stop();
        moveBirdDown.stop();
        movePipes.stop();
        
        //remove stuff
        this.remove(scoreLabel);
        this.remove(bird);
        for(JLabel ups : pipeUps){
            this.remove(ups);
        }
        for(JLabel downs : pipeDowns){
            this.remove(downs);
        }
        this.remove(bg);

        //add more stuff
        this.add(gameOver);
        this.add(yourScore);
        this.add(highScoreLabel);
        this.add(playAgain);

        this.add(bird);
        for(JLabel ups : pipeUps){
            this.add(ups);
        }
        for(JLabel downs : pipeDowns){
            this.add(downs);
        }
        this.add(bg);
        this.repaint();

        //set scores
        if(score>highScore)
            highScore = score;

        yourScore.setText("Score: " + score);
        highScoreLabel.setText("High Score: " + highScore);

        score = 0;
        isGameOver = true;

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==moveBirdDown){
            //bird fly
            birdY+=2;
            bird.setLocation(new Point(410, birdY));

            //bird out of bound check
            if(birdY+100>=700){
                gameOver();
            }
            if(birdY+25<=0){
                gameOver();
            }
        }else if(e.getSource()==movePipes){
            //pipe mover
            for(int i=0;i<5;i++){
                pipeX[i]-=2;
                if((birdY+50>=pipeY[i] && ((pipeX[i]<=470 && pipeX[i]>=410) || (pipeX[i]+150<=470 && pipeX[i]+150>=410) || (pipeX[i]+75<=470 && pipeX[i]+75>=410)))){
                    gameOver();
                }
                if(birdY<=pipeY[i]-200 && ((pipeX[i]<=470 && pipeX[i]>=410) || (pipeX[i]+150<=470 && pipeX[i]+150>=410) || (pipeX[i]+75<=470 && pipeX[i]+75>=410))){
                    gameOver();
                }
                pipeUps[i].setLocation(pipeX[i], pipeY[i]);
                pipeDowns[i].setLocation(pipeX[i], pipeY[i]-750);
            }

            //pipe recycler
            if(pipeX[0]<=-150){
                pipeGap = (int)Math.floor(Math.random() *(400 - 250 + 1) + 250);
                pipeX[0] = 920 + (0*pipeGap);
                pipeX[0] = pipeX[4] + pipeGap;
            }
            if(pipeX[1]<=-150){
                pipeGap = (int)Math.floor(Math.random() *(400 - 250 + 1) + 250);
                pipeX[1] = 920 + (1*pipeGap);
                pipeX[1] = pipeX[0] + pipeGap;
            } 
            if(pipeX[2]<=-150){
                pipeGap = (int)Math.floor(Math.random() *(400 - 250 + 1) + 250);
                pipeX[2] = 920 + (2*pipeGap);
                pipeX[2] = pipeX[1] + pipeGap;
            } 
            if(pipeX[3]<=-150){
                pipeGap = (int)Math.floor(Math.random() *(400 - 250 + 1) + 250);
                pipeX[3] = 920 + (3*pipeGap);
                pipeX[3] = pipeX[2] + pipeGap;
            } 
            if(pipeX[4]<=-150){
                pipeGap = (int)Math.floor(Math.random() *(400 - 250 + 1) + 250);
                pipeX[4] = 920 + (4*pipeGap);
                pipeX[4] = pipeX[3] + pipeGap;
            }
        }else{
            score++;
            scoreLabel.setText(" Score: " + score);
        }
        
    }

    public void mouseClicked(MouseEvent e){
        if(e.getX()>360 && e.getY()>300 && e.getX()<560 && e.getY()<443 && isGameStarted==false){
            gameStart();
            isGameStarted = true;
        }
        if(e.getX()>335 && e.getY()>350 && e.getX()<585 && e.getY()<420 && isGameStarted==true){
            gameStart();
        }
    }
    
    public void mousePressed(MouseEvent e){
    }
    public void mouseReleased(MouseEvent e){
    }
    public void mouseEntered(MouseEvent e){
    }
    public void mouseExited(MouseEvent e){
    }

    
}

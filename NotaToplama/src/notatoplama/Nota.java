/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package notatoplama;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates {
    private int x;
    private int y;
    private String nota;
    public Ates(int x, int y,String nota) {
        this.x = x;
        this.y = y;
        this.nota = nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getNota() {
        return nota;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}
public class Nota extends JPanel implements KeyListener,ActionListener{
    Timer timer = new Timer(2,this);
    private BufferedImage sepet,background; 
    private int kacan_nota = 15;
    private int nota_sayisi = 10;
    private int ates_artis = 2;
    private int puan = 0;
    private int nota_kontrol_sayisi = 0;
    private int sepetX = 0;
    private ArrayList<Ates> atesler = new ArrayList<Ates>(nota_sayisi);
    private ArrayList<String> Notalar = new ArrayList<String>();
    private int dirsepetX = 20;
    Random random = new Random();
    public void SelfKontrol(){
        for(Ates ates : atesler) {
            if(new Rectangle(ates.getX(),ates.getY(),10,10).intersects(new Rectangle(sepetX,530,110,30))){
                try {
                    File file = new File("music/"+ates.getNota()+".wav");
                    AudioInputStream audio = AudioSystem.getAudioInputStream(file);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audio);
                    clip.start();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Nota.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Nota.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Nota.class.getName()).log(Level.SEVERE, null, ex);
                }
                puan++;
                int y = random.nextInt(700)+10;
                int a = random.nextInt(7);
                int x = random.nextInt(700)+10;
                ates.setY(-y);
                ates.setX(x);
                ates.setNota(Notalar.get(a));
                
                
                
               
                
            }
        }
        if(kacan_nota == 0){
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over Puanınız: "+puan);
            System.exit(0);
        }
        
       
    }
    public Nota(){
        try {
            sepet = ImageIO.read(new FileImageInputStream(new File("sepet.png")));
            background = ImageIO.read(new FileImageInputStream(new File("background.jpg")));
        } catch (IOException ex) {
            System.out.println("İmage Yüklenirken Bir Hata Meydana Geldi.");
        }
        setBackground(Color.BLACK);
        Notalar.add("Do");Notalar.add("Re");Notalar.add("Mi");Notalar.add("Fa");Notalar.add("Sol");Notalar.add("La");Notalar.add("Si");
        timer.start();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        g.drawImage(background,0,0, 800, 600, this);
        g.drawImage(sepet,sepetX,530,110,60, this);
            g.setColor(Color.WHITE);
        g.drawString("Puan: "+puan, 10, 20);
        
        for(Ates ates : atesler) {
            if(ates.getY() > 800) {
                int y = random.nextInt(700)+10;
                int a = random.nextInt(7);
                int x = random.nextInt(700)+10;
                ates.setY(-y);
                ates.setX(x);
                ates.setNota(Notalar.get(a));
                kacan_nota = kacan_nota - 1;
            }    
        }
        
        for(int i = 0;i< nota_sayisi;i++){
            if(nota_kontrol_sayisi == 0){
                int a = random.nextInt(7);
                int x = random.nextInt(700)+10;
                int y = random.nextInt(700)+10;
                atesler.add(new Ates(x,-y,Notalar.get(a)));
            }
        }
        nota_kontrol_sayisi = 1;
        for(Ates ates : atesler) {
            g.drawString(ates.getNota(), ates.getX(), ates.getY());
        }
        
       SelfKontrol();
        
    }

    

    @Override
    public void repaint() {
        super.repaint(); 
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        
        if(c == KeyEvent.VK_LEFT){
            if(sepetX <= 0){
                sepetX = 0;
            }
            else{
                sepetX -= dirsepetX;
            }
            
        }
        else if(c == KeyEvent.VK_RIGHT){
                if(sepetX >= 680){
                    sepetX = 680;
            }
                else{
                    sepetX+=dirsepetX;
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(Ates ates : atesler) {
            ates.setY(ates.getY() + ates_artis);
        }
        repaint();
    }
    
}

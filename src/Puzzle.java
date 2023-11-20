import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import javax.swing.border.*;

class Main{
	public static void main(String args[]){
		Puzzle puzzle = new Puzzle("Image Puzzle Game");
	}
}

class Puzzle implements ActionListener{
	JFrame f;
	Time t = new Time();
	ImageIcon m1;
	JLabel l1 = new JLabel(m1);
	JButton[][] b1;
	Image[] image;
	Panel p = new Panel();	
	int i, j, size=0, temp;
	int c1,c2,ch1,ch2;
	int[] check;
	int height=0, width=0;
	Set hs = new LinkedHashSet();
	Iterator it;
	
	Puzzle(String name){
		randPhoto();
		f=new JFrame(name);
		f.setSize(width+100, height+100);
		name = JOptionPane.showInputDialog("원하시는 필드의 크기를 입력해주세요.");
		size = Integer.parseInt(name);
		flow();
	}
	
	void flow(){
		fset();
		hsSet();
		makeMap();
	}
	
	void randPhoto(){
		int rand; String temp = null;
		rand = (int)(Math.random()*3);
		if(rand==0){ temp = "a" + ".jpg"; }
		else if(rand==1){temp = "b" + ".jpg"; }
		else if(rand==2) { temp = "c" + ".jpg"; }
		m1 = new ImageIcon(temp);
		height=m1.getIconHeight(); width=m1.getIconWidth();
	}
	
	void fset(){
		p.setSize(height, width);
		p.setLayout(new GridLayout(size,size));
	    f.add(p,"West");
	    f.add(t.tl,"North");
	    t.start();
		f.setVisible(true);
	}
	
	
	
	void hsSet(){
		while(hs.size()<(size*size)){
			temp=(int)(Math.random()*(size*size));
			hs.add(temp);
		}
		it = hs.iterator();
	}
	
	void makeMap(){
		int x=0,y=0, temp=0;
		check = new int[size*size];
		image = new Image[size*size];
		b1 = new JButton[size][size];
		
		i=width/size; j=height/size;
		while(temp<size*size){
			Image im = m1.getImage();
			im = f.createImage(new FilteredImageSource(im.getSource(), new CropImageFilter(x,y,i,j)));
			image[temp]= im;
			x+=j; temp++;
			if(temp%size==0){y+=i; x=0; }
		}
		
		
		int a=0; String stemp;
		int d=i, b=j;
		for(i=0;i<size;i++){
			for(j=0;j<size;j++){
				if(it.hasNext()) {
					a=(int) it.next();
				}
				b1[i][j] = new JButton(new ImageIcon(image[a]));
				b1[i][j].addActionListener(this);
				
				stemp=String.valueOf(a);
				b1[i][j].setActionCommand(stemp);
				b1[i][j].setPreferredSize(new Dimension(d, b));
				p.add(b1[i][j]);
			}
		}
	}
		
	void Counting() {
		int k=0, temp, check=0;
		for(i=0;i<size;i++) {
			for(j=0;j<size;j++,k++) {
				temp = Integer.parseInt(b1[i][j].getActionCommand());
				if(temp==k) {
					check++;
				}
			}
		}System.out.println("check = "+ check);
		if(check==(size*size)) {
			end();
		}
	}
	
	void end() {
		JOptionPane.showMessageDialog(f, "퍼즐을 완성하셨습니다.\nClear Time : "+t.time);
		System.exit(0);
	}
	
@Override
public void actionPerformed(ActionEvent ae) {
	if(ch1==0){
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(ae.getActionCommand()==b1[i][j].getActionCommand()){
					c1=i; c2=j;
					LineBorder b2 = new LineBorder(Color.red,5);
					b1[i][j].setBorder(b2);
				}
			}
		} ch1=1; ch2=0;
	}
	
	else if(ch2==0){
		int a=0,b=0;
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(ae.getActionCommand()==b1[i][j].getActionCommand()){
					a=i; b=j;					
				}
			}
		}
		
		JButton tt = new JButton();
		b1[c1][c2].setBorder(tt.getBorder());
		if(a+1==c1&&b==c2) {
			tt.setActionCommand(b1[a][b].getActionCommand());
			tt.setIcon(b1[a][b].getIcon());
			b1[a][b].setActionCommand(b1[c1][c2].getActionCommand());
			b1[a][b].setIcon(b1[c1][c2].getIcon());
			b1[c1][c2].setActionCommand(tt.getActionCommand());
			b1[c1][c2].setIcon(tt.getIcon());
		}
		else if(a-1==c1&&b==c2){
			tt.setActionCommand(b1[a][b].getActionCommand());
			tt.setIcon(b1[a][b].getIcon());
			b1[a][b].setActionCommand(b1[c1][c2].getActionCommand());
			b1[a][b].setIcon(b1[c1][c2].getIcon());
			b1[c1][c2].setActionCommand(tt.getActionCommand());
			b1[c1][c2].setIcon(tt.getIcon());
		}
		else if(a==c1&&b+1==c2){
			tt.setActionCommand(b1[a][b].getActionCommand());
			tt.setIcon(b1[a][b].getIcon());
			b1[a][b].setActionCommand(b1[c1][c2].getActionCommand());
			b1[a][b].setIcon(b1[c1][c2].getIcon());
			b1[c1][c2].setActionCommand(tt.getActionCommand());
			b1[c1][c2].setIcon(tt.getIcon());
		}
		else if(a==c1&&b-1==c2){
			tt.setActionCommand(b1[a][b].getActionCommand());
			tt.setIcon(b1[a][b].getIcon());
			b1[a][b].setActionCommand(b1[c1][c2].getActionCommand());
			b1[a][b].setIcon(b1[c1][c2].getIcon());
			b1[c1][c2].setActionCommand(tt.getActionCommand());
			b1[c1][c2].setIcon(tt.getIcon());
		}
		ch1=0;
	}
	Counting();
  }
}



class Time extends Thread{
	int time = 0;
	JLabel tl = new JLabel("Time :" + time);
	public void run(){
		tl.setFont(new Font("Serif", Font.BOLD, 30));
		tl.setForeground(Color.MAGENTA);
		while(true){
			try {
				sleep(1000);
				time++;
				tl.setText("Time : " + time);
			} catch (InterruptedException e) {}
		}
	}
}


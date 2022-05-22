import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class MyFrame extends JFrame{
	JButton setButton;
	MyFrame(){
		setSize(400,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		setButton=new JButton("Set Title");
		setButton.setFont(new Font("",1,20));
		setButton.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent evt){
				System.out.println("Mouse clicked..");
			}
			
			public void mouseEntered(MouseEvent evt){
				System.out.println("Mouse entered..");
			}
			public void mousePressed(MouseEvent evt){
				System.out.println("Mouse pressed..");
			}
			public void mouseReleased(MouseEvent evt){
				System.out.println("Mouse Released..");
			}
			
			public void mouseExited(MouseEvent evt){
				System.out.println("Mouse exited..");
			}
			
		});
		add(setButton);
	}
	
}
class Demo{
	public static void main(String args[]){
		new MyFrame().setVisible(true);
	}
}

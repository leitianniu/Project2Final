import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



@SuppressWarnings("serial")

public class mineGUI extends JFrame{
	
	private button mineButtons[] = new button[100];	
	private GridLayout mineBoard, infoBoard;
	private Container boardContainer;
	private JMenuBar topMenu;
	private JMenu gameMenu,hMenu;
	private JMenuItem gReset, gTopTen, gExit, hHelp, hAbout;

	
	public mineGUI(){
		
		super("Minesweeper");
		setSize(400,400);
		
		
		
		topMenu = new JMenuBar();
		setJMenuBar(topMenu);
		
		gameMenu = new JMenu("Game");
		topMenu.add(gameMenu);
		
		gReset = new JMenuItem("Reset");
		gameMenu.add(gReset);
		gameMenu.addSeparator();
		
		gTopTen = new JMenuItem("Top Ten");
		gameMenu.add(gTopTen);
		gameMenu.addSeparator();
		
		gExit = new JMenuItem("Exit");
		gameMenu.add(gExit);
		
		hMenu = new JMenu("Help");
		topMenu.add(hMenu);
		
		hHelp = new JMenuItem("Help");
		hMenu.add(hHelp);
		hMenu.addSeparator();
		
		hAbout = new JMenuItem("About");
		hMenu.add(hAbout);
		
		
		
		
		
		
		
		
		
		
		
		
		
		JPanel mboard = new JPanel(new GridLayout(10,10));
		
		JPanel top = new JPanel();
		
		
		
		
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		
		
		//boardContainer=getContentPane();
		
		//boardContainer.add(top);
		JLabel time = new JLabel("Time");
		
		top.add(time);
		
		
		
		
		//boardContainer.add(mboard);
		
		
		
		
		
		
		
	
		
		
		for(int i=0; i<100; i++){
			mineButtons[i] = new button();
			mboard.add(mineButtons[i]);
			
		}
		
		container.add(top);
		
		container.add(mboard);
		
		add(container);
		
		
		
		
		
		
		
		
		
		
		
		
		
		setVisible(true);
		
		
	
	}
	
}
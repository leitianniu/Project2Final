import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class button extends JButton{
	
	
	
	int xCoord=-1;
	
	
	
	public void setXcoord(int x){
		xCoord=x;
	}
	
	int yCoord=-1;
	public void setYcoord(int y){
		yCoord=y;
	}
	
	public int getXcoord(){
		return xCoord;
	}
	
	public int getYcoord(){
		return yCoord;
	}
	
	int pos=-1;
	public void setPos(int z){
		pos=z;
	}
	
	public int getPos(){
		return pos;
	}
	
	int state=0;
	public void setState(int a){
		state=a;
	}
	
	public int getState(){
		return state;
	}
	
	int isBomb=0;
	public void setBomb(int b){
		isBomb=b;
	}
	
	public int getBomb(){
		return isBomb;
	}
	
	int adjacent=0;
	public void setAdjacentBombs(int n){
		adjacent=n;
	}
	
	public int getAdjacentBombs(){
		return adjacent;
	}
	
	public String getAdjacentBombsString(){
		return Integer.toString(adjacent);
	}
	
	
	}
	
	
	
	
package rPi1;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RPi1 extends Frame implements WindowListener, ActionListener {
	
	private Button b;
	private TextField txt0 = new TextField(1);
	private TextField txt1 = new TextField(1);
	private TextField txt2 = new TextField(1);
	private TextField txt3 = new TextField(1);
	private TextField txt4 = new TextField(1);
	private TextField txt5 = new TextField(1);
	private TextField txt6 = new TextField(1);
	private TextField txt7 = new TextField(1);
	private TextField ip = new TextField(27);
	private String host = "0.0.0.0";
	private Label lb = new Label("Write IP address and PRESS ENTER");

	public static void main(String args[]) {
		RPi1 myWindow = new RPi1();
        myWindow.setSize(450,180);
        myWindow.setVisible(true);
	}
	
	public RPi1() {
		super();
		setLayout(new FlowLayout());
		addWindowListener(this);
		b = new Button("Actualizar");
		b.addActionListener(this);
		ip.setText(host);
		ip.addActionListener(this);
		add(b); add(txt0); add(txt1); add(txt2); add(txt3); add(txt4); 
		add(txt5); add(txt6); add(txt7); add(lb); add(ip);
        
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == b) {
			Boolean[] datos = new Boolean[8];
			try{
				datos = this.actualizar();
			}catch(IOException ex){}
			
			for(int i=0; i < datos.length; i++) {
				if(datos[i] == false){
					
					switch(i){
						case 0: txt0.setText("0"); case 1: txt1.setText("0"); case 2: txt2.setText("0"); case 3: txt3.setText("0");
						case 4: txt4.setText("0"); case 5: txt5.setText("0"); case 6: txt6.setText("0"); case 7: txt7.setText("0");
					}
				}else{
					
					switch(i){
						case 0: txt0.setText("1"); case 1: txt1.setText("1"); case 2: txt2.setText("1"); case 3: txt3.setText("1");
						case 4: txt4.setText("1"); case 5: txt5.setText("1"); case 6: txt6.setText("1"); case 7: txt7.setText("1");
					}
				}
			}
		}
		
		if(e.getSource() == ip){
			host = ip.getText();
		}
	}
	
	public void windowClosing(WindowEvent e) {
         
 		dispose();
        System.exit(0);
	}

	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	
	
	private Boolean[] actualizar() throws IOException{
		
		URL url = new URL("http", host, 80, "/newWeb/index.php");
		InputStreamReader is = new InputStreamReader (url.openStream());
		BufferedReader in = new BufferedReader(is);
		
		Pattern pat = Pattern.compile("Start of readed data:", Pattern.CASE_INSENSITIVE);
		String s, data="";
		
		while((s=in.readLine()) != null) {	
			Matcher m = pat.matcher(s);
			if(m.find()){
				data = s.substring(24, 32);
				break;
			}
		}
		
		in.close();  
		Boolean[] aDevolver = new Boolean[8];
	
		System.out.println(data);
		
		for(int i = 0; i < data.length(); i++) {

			if(data.substring(i, i+1).equals("1")){
				aDevolver[i] = true;
			}else{
				aDevolver[i] = false;
			}
		}
						
		return aDevolver;
	}

	
}

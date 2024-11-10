import java.io.File;
import java.net.*;
import javax.swing.*;

public class ShowHTML {
	public ShowHTML() {
	}
	
	public void show (String file) {
		JTextPane pane = new JTextPane();
		JScrollPane scroll = new JScrollPane();
		scroll.getViewport().add(pane);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(scroll);
		frame.setSize(800,800);
		frame.setVisible(true); 
  
		try {
			File f = new File(file);
			URI uri = f.toURI();
			pane.setPage(uri.toURL());
		} 
		catch (Exception e) {
			System.out.println("Error opening HTML document. "+e.getMessage());
		}
  }
 }
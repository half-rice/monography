package main;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Monography extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private static List<String> list;
	private static Random r;
	
	private static String dir;

	JPanel jpanel = new JPanel();
	JFileChooser fc = new JFileChooser();

	// Center
	JPanel center = new JPanel();
	JButton random = new JButton("Random");
	
	// South
	JPanel south = new JPanel();
	//JLabel inputLabel = new JLabel("Dir:");
	JTextField input = new JTextField(21);
	JButton directory = new JButton("...");
	
	public Monography(){
		super("Monography");
		
		setSize(300, 100);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		jpanel.setLayout(new BorderLayout());
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File("."));
		dir = fc.getCurrentDirectory().toString();
		input.setText(dir);
				
		// Center - Buttons
		center.setLayout(new GridLayout(1, 1));
		//menu1.setPreferredSize(new Dimension(100, 40));
		random.addActionListener(this);
		center.add(random);
		jpanel.add(center, BorderLayout.CENTER);				
		
		// South - Buttons
		input.addActionListener(this);
		directory.addActionListener(this);
		//south.add(inputLabel);
		south.add(input);
		south.add(directory);
		jpanel.add(south, BorderLayout.SOUTH);
		
		// 
		add(jpanel);	
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src.equals(random)){
			final File folder = new File(dir);
			//dir = folder.toString();
			
			indexFiles(folder);	
			
			File f = new File(dir + "\\" + getPic(list));
			//System.out.println(f);
			Desktop dt = Desktop.getDesktop();
			try{
				dt.open(f);
			}
			catch(IOException e1){
				e1.printStackTrace();
			}
		}
		
		if(src.equals(input)){
			dir = input.getText().toString();
			System.out.println(dir);
		}
		
		if(src.equals(directory)){
			int returnVal = fc.showOpenDialog(Monography.this);
			
			if(returnVal == JFileChooser.APPROVE_OPTION){
				//File file = fc.getSelectedFile();
				dir = fc.getSelectedFile().toString();
				input.setText(dir);
			}
		}
	}
	
	public static void indexFiles(final File folder){
		list = new ArrayList<String>();
		
		String filename, extension;

		
	    for (final File fileEntry : folder.listFiles()){
    		filename = fileEntry.getName();
    		extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
    		
    		System.out.println(extension);
	    	
	        if(fileEntry.isDirectory()){
	        	indexFiles(fileEntry);
	        }
	        else if(extension.equals("jpg") || extension.equals("png") || extension.equals("bmp") || extension.equals("gif")){
	        	list.add(fileEntry.getName());
	        	//System.out.print(filename);
	        	//System.out.print(list.size());
	        	//System.out.println();
	        }
	    }
	    
	    //System.out.println(list.size());
	}
	
	public static String getPic(List<String> list){
	    //System.out.println(list);
		r = new Random();		
	    int dice = r.nextInt(list.size());
	    //System.out.println("getPic > " + list.get(dice));
	    
	    return (String) list.get(dice);
	}
	
	public static void main(String[] args) throws IOException{
		Monography monography = new Monography();
	}
}

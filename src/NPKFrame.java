import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class NPKFrame extends JFrame implements ActionListener{
	JPanel 	pan, 
			namepan = new JPanel(), 
			pnamepan = new JPanel(), 
			verpan = new JPanel(),
			pathpan = new JPanel(),
			finishpan = new JPanel();
	
	JTextField	namef = new JTextField(5),
				pnamef = new JTextField(10),
				verf = new JTextField(2),
				abspathf = new JTextField(20);
	
	JButton		browseB = new JButton("Browse"),
				finishB = new JButton("Finish");
	
	NewPluginKit npk;
	
	StringBuilder absPath;
	
	public NPKFrame(String title, NewPluginKit npk){
		super(title);
		this.npk = npk;
		
		//get abspath
		absPath = new StringBuilder(npk.getAbsPath());
		
		//Setup the frame
		pan = (JPanel) getContentPane();
		pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
		pan.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		pan.add(namepan);
		namepan.setLayout(new BoxLayout(namepan, BoxLayout.LINE_AXIS));
		namepan.add(new JLabel("Name"));
		namepan.add(Box.createRigidArea(new Dimension(10,10)));
		namepan.add(namef);
		namepan.add(Box.createHorizontalGlue());
		
		pan.add(Box.createRigidArea(new Dimension(10,10)));
		
		pan.add(pnamepan);
		pnamepan.setLayout(new BoxLayout(pnamepan, BoxLayout.LINE_AXIS));
		pnamepan.add(new JLabel("Plugin Name"));
		pnamepan.add(Box.createRigidArea(new Dimension(10,10)));
		pnamepan.add(pnamef);
		pnamepan.add(Box.createHorizontalGlue());
		
		pan.add(Box.createRigidArea(new Dimension(10,10)));
		
		pan.add(verpan);
		verpan.setLayout(new BoxLayout(verpan, BoxLayout.LINE_AXIS));
		verpan.add(new JLabel("Version"));
		verpan.add(Box.createRigidArea(new Dimension(10,10)));
		verpan.add(verf);
		verpan.add(Box.createHorizontalGlue());
		
		pan.add(Box.createRigidArea(new Dimension(10,10)));
		
		abspathf = new JTextField(absPath.toString());
		pan.add(pathpan);
		pathpan.setLayout(new BoxLayout(pathpan, BoxLayout.LINE_AXIS));
		pathpan.add(new JLabel("Path"));
		pathpan.add(Box.createRigidArea(new Dimension(10,10)));
		pathpan.add(abspathf);
		pathpan.add(Box.createRigidArea(new Dimension(10,10)));
		pathpan.add(browseB);
		browseB.addActionListener(this);
		
		pan.add(Box.createRigidArea(new Dimension(10,10)));
		
		pan.add(finishpan);
		finishpan.setLayout(new BoxLayout(finishpan, BoxLayout.LINE_AXIS));
		finishpan.add(Box.createHorizontalGlue());
		finishpan.add(finishB);
		finishpan.add(Box.createHorizontalGlue());
		finishB.addActionListener(this);
		
		//maintenance
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton)e.getSource();
		
		//Figure out which button was pressed
		if(src == browseB){
			//Browse for the path
			JFileChooser browse = new JFileChooser();
			browse.setCurrentDirectory(new java.io.File("."));
		    browse.setDialogTitle("Select Path");
		    browse.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    browse.setAcceptAllFileFilterUsed(false);
		    
		    if (browse.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
		    	absPath = new StringBuilder("" + browse.getSelectedFile());
		    	abspathf.setText(absPath.toString());
		    	System.out.println(absPath);
		    }
		        	    
		}else if(src == finishB){
			//validate and send values to NewPluginKit to process and exit
			if(	namef.getText().equals("") ||
				pnamef.getText().equals("") ||
				verf.getText().equals("") ||
				abspathf.getText().equals("")){}
			else{
				npk.setValues(namef.getText(), pnamef.getText(), verf.getText(), abspathf.getText());
				npk.Copy();
				JOptionPane.showMessageDialog(null, "Successful");
				System.exit(0);
			}
			
		}
		
	}
	
}

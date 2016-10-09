package bn.blaszczyk.rose.themes.defaulttheme;

import java.awt.Dialog.ModalityType;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;

import bn.blaszczyk.rose.interfaces.EntityModel;
import bn.blaszczyk.rose.interfaces.MyPanel;

public class GUIController {

	public static void createFullPanelFrame(EntityModel entityModel)
	{
		JFrame frame = new JFrame( entityModel.getName() );
		MyPanel panel = new FullPanel(entityModel);
		frame.add(panel.getPanel());
		frame.setSize( panel.getWidth() + 16, panel.getHeight() + 46 );
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void createFullPanelDialog(Window owner, EntityModel entityModel)
	{
		JDialog dialog = new JDialog(owner, entityModel.getName(), ModalityType.APPLICATION_MODAL);
		MyPanel panel = new FullPanel(entityModel);
		dialog.add(panel.getPanel());
		dialog.setSize( panel.getWidth() + 16, panel.getHeight() + 46 );
		dialog.setLocationRelativeTo(owner);
		dialog.setVisible(true);				
	}
}

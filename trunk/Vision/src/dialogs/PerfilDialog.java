package dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import java.awt.Font;

public class PerfilDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	public JButton cancelButton;
	public JPanel panel;
	public JScrollPane scrollPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PerfilDialog dialog = new PerfilDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public PerfilDialog() {
		setAlwaysOnTop(true);
		setBounds(100, 100, 665, 493);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblNiveles = new JLabel("Define una recta con el rat\u00F3n");
		lblNiveles.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		ButtonGroup niveles = new ButtonGroup();
		
		ButtonGroup tipo = new ButtonGroup();
		
		scrollPane = new JScrollPane();
		
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addComponent(lblNiveles))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNiveles)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(2, 2, 0, 0));
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				cancelButton = new JButton("Salir");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}

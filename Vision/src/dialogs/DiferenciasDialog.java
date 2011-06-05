package dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DiferenciasDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	public JLabel titulo;
	public JButton okButton;
	public JButton cancelButton;
	public JScrollBar scrollBar;
	public JLabel nombreImagen;
	public JLabel dimensiones;
	public JLabel error;
	
	public static void main(String[] args) {
		try {
			DiferenciasDialog dialog = new DiferenciasDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DiferenciasDialog() {
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		titulo = new JLabel("Elije la primera imagen:");
		
		scrollBar = new JScrollBar();
		scrollBar.setOrientation(JScrollBar.HORIZONTAL);
		
		nombreImagen = new JLabel("New label");
		
		dimensiones = new JLabel("New label");
		
		error = new JLabel("New label");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(nombreImagen, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
						.addComponent(dimensiones, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
						.addComponent(error, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
						.addComponent(titulo)
						.addComponent(scrollBar, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(titulo)
					.addGap(54)
					.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(nombreImagen)
					.addGap(18)
					.addComponent(dimensiones)
					.addGap(18)
					.addComponent(error)
					.addContainerGap(20, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}

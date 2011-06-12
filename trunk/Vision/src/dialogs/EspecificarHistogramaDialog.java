package dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class EspecificarHistogramaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JButton okButton;
	public JButton cancelButton;
	public JLabel lbNombreIm1;
	public JScrollBar scrollBar;
	public JPanel panelHistAnterior;
	public JPanel panelHistDeseado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EspecificarHistogramaDialog dialog = new EspecificarHistogramaDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EspecificarHistogramaDialog() {
		setTitle("Especificaci\u00F3n del histograma deseado");
		setModal(true);
		setBounds(100, 100, 348, 521);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblEspecificarElHistograma = new JLabel("Especificar el histograma de:");
		
		lbNombreIm1 = new JLabel("New label");
		
		JLabel lblSeleccionaLaImagen = new JLabel("Selecciona la imagen con el histograma deseado:");
		
		scrollBar = new JScrollBar();
		scrollBar.setMaximum(0);
		scrollBar.setOrientation(JScrollBar.HORIZONTAL);
		
		panelHistAnterior = new JPanel();
		panelHistAnterior.setLayout(new GridLayout(1, 1, 0, 0));
		
		panelHistDeseado = new JPanel();
		panelHistDeseado.setLayout(new GridLayout(1, 1, 0, 0));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lbNombreIm1, Alignment.LEADING)
						.addComponent(lblEspecificarElHistograma, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSeleccionaLaImagen, Alignment.LEADING))
					.addContainerGap(87, Short.MAX_VALUE))
				.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)
				.addComponent(panelHistAnterior, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
				.addComponent(panelHistDeseado, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(6)
					.addComponent(lblEspecificarElHistograma)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lbNombreIm1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblSeleccionaLaImagen)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelHistAnterior, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelHistDeseado, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
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
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}

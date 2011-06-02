package vision;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JSpinner;

public class MuestrearDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	public JSpinner anchoSpinner;
	public JSpinner altoSpinner;
	public JLabel muestras;
	public JButton okButton;
	public JButton cancelButton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MuestrearDialog dialog = new MuestrearDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MuestrearDialog() {
		setModal(true);
		setBounds(100, 100, 425, 265);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblSeleccionaLasDimensiones = new JLabel("Selecciona el tama\u00F1o de cada muestra:");
		
		anchoSpinner = new JSpinner();
		
		JLabel lblAncho = new JLabel("Ancho (p\u00EDxels): ");
		
		JLabel lblAltopxels = new JLabel("Alto (p\u00EDxels): ");
		
		altoSpinner = new JSpinner();
		
		muestras = new JLabel("Cantidad de muestras:");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSeleccionaLasDimensiones)
								.addComponent(muestras)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(24)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblAltopxels, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(altoSpinner))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblAncho)
									.addGap(18)
									.addComponent(anchoSpinner, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(152, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSeleccionaLasDimensiones)
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAncho, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(anchoSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAltopxels, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(altoSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(muestras)
					.addContainerGap(24, Short.MAX_VALUE))
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

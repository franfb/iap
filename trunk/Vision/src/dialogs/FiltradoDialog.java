package dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JSpinner;

public class FiltradoDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JSpinner spTam;
	public JRadioButton rbMedia;
	public JRadioButton rbMediana;
	public JRadioButton rbModa;
	public JButton okButton;
	public JButton cancelButton;
	public JRadioButton rbKvecinos;
	private JLabel lblNDeVecinos;
	public JSpinner spK;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FiltradoDialog dialog = new FiltradoDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FiltradoDialog() {
		setTitle("Filtrado de la imagen");
		setModal(true);
		setBounds(100, 100, 450, 455);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Tipo de filtro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblTamaoDeLa = new JLabel("Tama\u00F1o de la ventana");
		
		spTam = new JSpinner();
		
		lblNDeVecinos = new JLabel("N\u00BA de vecinos (k)");
		
		spK = new JSpinner();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTamaoDeLa)
								.addComponent(lblNDeVecinos))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(spK)
								.addComponent(spTam, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))))
					.addContainerGap(210, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTamaoDeLa)
						.addComponent(spTam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNDeVecinos)
						.addComponent(spK, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(179, Short.MAX_VALUE))
		);
		
		rbMedia = new JRadioButton("Media");
		
		rbMediana = new JRadioButton("Mediana");
		
		rbModa = new JRadioButton("Moda");
		
		rbKvecinos = new JRadioButton("K-Vecinos");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(rbMedia)
						.addComponent(rbMediana)
						.addComponent(rbModa)
						.addComponent(rbKvecinos))
					.addContainerGap(75, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(rbMedia)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbMediana)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbModa)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbKvecinos)
					.addContainerGap(14, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
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

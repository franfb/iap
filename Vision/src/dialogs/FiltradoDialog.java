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
import javax.swing.SpinnerNumberModel;

public class FiltradoDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JSpinner spTam;
	public JRadioButton rbMedia;
	public JRadioButton rbMediana;
	public JRadioButton rbModa;
	public JButton okButton;
	public JButton cancelButton;
	public JRadioButton rbKvecinos;
	public JLabel lblKSigma;
	public JSpinner spK;
	public JRadioButton rbDifEstadstica;
	public JSpinner spSigma;
	public JRadioButton rbGaussiano;
	public JRadioButton rbMedianaV;

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
		setBounds(100, 100, 215, 391);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Tipo de filtro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblTamaoDeLa = new JLabel("Tama\u00F1o de la ventana");
		
		spTam = new JSpinner();
		
		lblKSigma = new JLabel("N\u00BA de vecinos (k)");
		
		spK = new JSpinner();
		
		JLabel lblSigma = new JLabel("Sigma");
		
		spSigma = new JSpinner();
		spSigma.setModel(new SpinnerNumberModel(1.0, 1.0, 20.0, 0.1));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTamaoDeLa)
								.addComponent(lblKSigma)
								.addComponent(lblSigma))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(spTam, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
								.addComponent(spK)
								.addComponent(spSigma))))
					.addContainerGap(244, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTamaoDeLa)
						.addComponent(spTam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKSigma)
						.addComponent(spK, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSigma)
						.addComponent(spSigma, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(80, Short.MAX_VALUE))
		);
		
		rbMedia = new JRadioButton("Media");
		
		rbMediana = new JRadioButton("Mediana");
		
		rbModa = new JRadioButton("Moda");
		
		rbKvecinos = new JRadioButton("K-Vecinos");
		
		rbDifEstadstica = new JRadioButton("Dif. Estad\u00EDstica");
		
		rbGaussiano = new JRadioButton("Gaussiano");
		
		rbMedianaV = new JRadioButton("Mediana V6");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(rbMedia)
						.addComponent(rbMediana)
						.addComponent(rbModa)
						.addComponent(rbKvecinos)
						.addComponent(rbDifEstadstica)
						.addComponent(rbGaussiano)
						.addComponent(rbMedianaV))
					.addContainerGap(53, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(rbMedia)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbMediana)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbMedianaV)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbModa)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbKvecinos)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbDifEstadstica)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbGaussiano)
					.addContainerGap(35, Short.MAX_VALUE))
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

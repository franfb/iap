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
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.JSpinner;

public class RuidoDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	public JButton okButton;
	public JButton cancelButton;
	public JRadioButton rdbtnGaussiano;
	public JRadioButton rdbtnUniforme;
	public JRadioButton rdbtnImpulsivo;
	public JSpinner contaminacionMin;
	public JSpinner contaminacionMax;
	public JSpinner porcentajeRuido;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RuidoDialog dialog = new RuidoDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public RuidoDialog() {
		setModal(true);
		setBounds(100, 100, 504, 386);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Funci\u00F3n de distribuci\u00F3n de ruido", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblRangoDeValores = new JLabel("Rango de valores de contaminaci\u00F3n:  De");
		
		contaminacionMin = new JSpinner();
		
		JLabel lblA = new JLabel("a");
		
		contaminacionMax = new JSpinner();
		
		JLabel lblPorcentajeDePxeles = new JLabel("Porcentaje de p\u00EDxeles de ruido (%):");
		
		porcentajeRuido = new JSpinner();
		
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 318, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblRangoDeValores)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(contaminacionMin, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblA)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(contaminacionMax, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblPorcentajeDePxeles)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(porcentajeRuido, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(117, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRangoDeValores)
						.addComponent(contaminacionMin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblA)
						.addComponent(contaminacionMax, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(33)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPorcentajeDePxeles)
						.addComponent(porcentajeRuido, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(148, Short.MAX_VALUE))
		);
		
		rdbtnGaussiano = new JRadioButton("Gaussiano");
		rdbtnUniforme = new JRadioButton("Uniforme");
		rdbtnImpulsivo = new JRadioButton("Impulsivo");
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnUniforme)
						.addComponent(rdbtnImpulsivo)
						.addComponent(rdbtnGaussiano))
					.addContainerGap(225, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(rdbtnUniforme)
					.addGap(18)
					.addComponent(rdbtnImpulsivo)
					.addGap(18)
					.addComponent(rdbtnGaussiano)
					.addContainerGap(90, Short.MAX_VALUE))
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
				cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}

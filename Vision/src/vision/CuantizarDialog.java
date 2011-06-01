package vision;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButtonMenuItem;

public class CuantizarDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	public JLabel lblTipo;
	public JButton okButton;
	public JButton cancelButton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CuantizarDialog dialog = new CuantizarDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JRadioButton rdbtnNiveles7;
	private JRadioButton rdbtnNiveles6;
	private JRadioButton rdbtnNiveles5;
	private JRadioButton rdbtnNiveles4;
	private JRadioButton rdbtnNiveles3;
	private JRadioButton rdbtnNiveles2;
	private JRadioButton rdbtnNiveles1;
	
	private JRadioButton rdbtnEscalaDeGrises;
	private JRadioButton rdbtnRgb;
	
	public boolean isRGB(){
		return rdbtnRgb.isSelected();
	}
	
	public int getNiveles(){
		if (rdbtnNiveles7.isSelected()){
			return 128;
		}
		if (rdbtnNiveles6.isSelected()){
			return 64;
		}
		if (rdbtnNiveles5.isSelected()){
			return 32;
		}
		if (rdbtnNiveles4.isSelected()){
			return 16;
		}
		if (rdbtnNiveles3.isSelected()){
			return 8;
		}
		if (rdbtnNiveles2.isSelected()){
			return 4;
		}
		if (rdbtnNiveles1.isSelected()){
			return 2;
		}
		return 0;
	}
	
	/**
	 * Create the dialog.
	 */
	public CuantizarDialog() {
		setModal(true);
		setBounds(100, 100, 425, 494);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		/*TitledBorder border = javax.swing.BorderFactory.createTitledBorder("Hola gente");
		border.setTitleFont(new Font("Segoe UI", Font.PLAIN, 34));
		contentPanel.setBorder(border);*/
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblNiveles = new JLabel("Niveles de cuantizaci\u00F3n:");
		lblNiveles.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		lblTipo = new JLabel("Tipo de cuantizaci\u00F3n:");
		lblTipo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		rdbtnNiveles7 = new JRadioButton("128 niveles (7 bits)");
		rdbtnNiveles7.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		rdbtnNiveles6 = new JRadioButton("64 niveles (6 bits)");
		rdbtnNiveles6.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		rdbtnNiveles5 = new JRadioButton("32 niveles (5 bits)");
		rdbtnNiveles5.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		rdbtnNiveles4 = new JRadioButton("16 niveles (4 bits)");
		rdbtnNiveles4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		rdbtnNiveles3 = new JRadioButton("8 niveles (3 bits)");
		rdbtnNiveles3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		rdbtnNiveles2 = new JRadioButton("4 niveles (2 bits)");
		rdbtnNiveles2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		rdbtnNiveles1 = new JRadioButton("2 niveles (1 bit)");
		rdbtnNiveles1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		ButtonGroup niveles = new ButtonGroup();
		niveles.add(rdbtnNiveles1);
		niveles.add(rdbtnNiveles2);
		niveles.add(rdbtnNiveles3);
		niveles.add(rdbtnNiveles4);
		niveles.add(rdbtnNiveles5);
		niveles.add(rdbtnNiveles6);
		niveles.add(rdbtnNiveles7);
		rdbtnNiveles7.setSelected(true);
		
		rdbtnEscalaDeGrises = new JRadioButton("Escala de grises");
		rdbtnEscalaDeGrises.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		rdbtnRgb = new JRadioButton("RGB");
		rdbtnRgb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		ButtonGroup tipo = new ButtonGroup();
		tipo.add(rdbtnEscalaDeGrises);
		tipo.add(rdbtnRgb);
		rdbtnEscalaDeGrises.setSelected(true);
		
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNiveles)
								.addComponent(lblTipo)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(39)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnNiveles1)
								.addComponent(rdbtnNiveles2)
								.addComponent(rdbtnNiveles3)
								.addComponent(rdbtnNiveles5)
								.addComponent(rdbtnNiveles6)
								.addComponent(rdbtnNiveles7)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(rdbtnRgb, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
										.addComponent(rdbtnEscalaDeGrises, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE))
									.addComponent(rdbtnNiveles4)))))
					.addContainerGap(243, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNiveles)
					.addGap(18)
					.addComponent(rdbtnNiveles7)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnNiveles6)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnNiveles5)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnNiveles4)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnNiveles3)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnNiveles2)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnNiveles1)
					.addGap(18)
					.addComponent(lblTipo)
					.addGap(18)
					.addComponent(rdbtnEscalaDeGrises, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnRgb, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}

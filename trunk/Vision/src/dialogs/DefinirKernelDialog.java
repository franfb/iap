package dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;

public class DefinirKernelDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JButton okButton;
	public JButton cancelButton;
	public JTable tKernel;
	public JTextField tfNormalizacion;
	private JLabel lblTamaoDelKernel;
	public JSpinner spTam;
	public JButton btnGuardarKernel;
	public JButton btnCargarKernel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DefinirKernelDialog dialog = new DefinirKernelDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DefinirKernelDialog() {
		setTitle("Definir Kernel");
		setModal(true);
		setBounds(100, 100, 490, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblFactorDeNormalizacin = new JLabel("Factor de normalizaci\u00F3n");
		
		tfNormalizacion = new JTextField();
		tfNormalizacion.setColumns(10);
		lblTamaoDelKernel = new JLabel("Tama\u00F1o del kernel");
		
		spTam = new JSpinner();
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Valores del kernel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		btnCargarKernel = new JButton("Cargar kernel");
		
		btnGuardarKernel = new JButton("Guardar kernel");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblFactorDeNormalizacin)
								.addComponent(lblTamaoDelKernel))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(spTam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfNormalizacion, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)))
						.addComponent(btnCargarKernel)
						.addComponent(btnGuardarKernel))
					.addGap(18)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTamaoDelKernel)
								.addComponent(spTam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblFactorDeNormalizacin)
								.addComponent(tfNormalizacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(btnCargarKernel)
							.addGap(18)
							.addComponent(btnGuardarKernel)))
					.addContainerGap())
		);
		
		JScrollPane scrollPane = new JScrollPane();
		{
			tKernel = new JTable();
			tKernel.setFillsViewportHeight(true);
			tKernel.setRowSelectionAllowed(false);
			scrollPane.setViewportView(tKernel);
		}
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
					.addContainerGap())
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

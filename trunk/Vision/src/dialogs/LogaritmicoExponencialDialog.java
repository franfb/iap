package dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.GridLayout;

public class LogaritmicoExponencialDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public JRadioButton rbLogaritmico;
	public JRadioButton rbExponencial;
	public JRadioButton rbTipo1;
	public JRadioButton rbTipo2;
	public JRadioButton rbTipo3;
	public JSpinner spValorK;
	public JPanel panelGrafica;
	public JButton okButton;
	public JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LogaritmicoExponencialDialog dialog = new LogaritmicoExponencialDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LogaritmicoExponencialDialog() {
		setTitle("Ajustes Logar\u00EDtmico y Exponencial");
		setModal(true);
		setBounds(100, 100, 470, 417);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Tipo de ajuste", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 107, 82);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		rbLogaritmico = new JRadioButton("Logar\u00EDtmico");
		rbLogaritmico.setBounds(6, 21, 95, 23);
		panel.add(rbLogaritmico);
		
		rbExponencial = new JRadioButton("Exponencial");
		rbExponencial.setBounds(6, 47, 95, 23);
		panel.add(rbExponencial);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Ecuaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 104, 107, 136);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		
		rbTipo1 = new JRadioButton("Tipo 1");
		rbTipo1.setBounds(6, 21, 91, 23);
		panel_1.add(rbTipo1);
		
		rbTipo2 = new JRadioButton("Tipo 2");
		rbTipo2.setBounds(6, 47, 91, 23);
		panel_1.add(rbTipo2);
		
		rbTipo3 = new JRadioButton("Tipo 3");
		rbTipo3.setBounds(6, 73, 91, 23);
		panel_1.add(rbTipo3);
		
		JLabel lblK = new JLabel("K = ");
		lblK.setBounds(12, 103, 32, 14);
		panel_1.add(lblK);
		
		spValorK = new JSpinner();
		spValorK.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spValorK.setBounds(42, 100, 55, 20);
		panel_1.add(spValorK);
		
		panelGrafica = new JPanel();
		panelGrafica.setBounds(127, 11, 315, 319);
		contentPanel.add(panelGrafica);
		panelGrafica.setLayout(new GridLayout(1, 1, 0, 0));
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
		
		ButtonGroup groupTipo = new ButtonGroup();
		groupTipo.add(rbLogaritmico);
		groupTipo.add(rbExponencial);
		
		rbLogaritmico.setSelected(true);
		
		ButtonGroup groupFormula = new ButtonGroup();
		groupFormula.add(rbTipo1);
		groupFormula.add(rbTipo2);
		groupFormula.add(rbTipo3);
		
		rbTipo1.setSelected(true);
	}
}

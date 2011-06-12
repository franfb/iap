package procesos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.SpinnerNumberModel;

import dialogs.FiltradoDialog;
import filtros.FiltroMedia;
import filtros.FiltroMediana;
import filtros.FiltroModa;
import filtros.FiltroOp;

import vision.Image;
import vision.MainWindow;

public class Filtrado {

	public static void run() {
		final Image im = MainWindow.getCurrentImage();
		Copiar.run("Filtrado de ");
		final Image newIm = MainWindow.getCurrentImage();
		final FiltradoDialog dialog = new FiltradoDialog();
		
		dialog.spTam.setModel(new SpinnerNumberModel(3, 3, 55, 2));
		
		ButtonGroup groupTipo = new ButtonGroup();
		groupTipo.add(dialog.rbMedia);
		groupTipo.add(dialog.rbMediana);
		groupTipo.add(dialog.rbModa);
		
		dialog.rbMedia.setSelected(true);
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
				MainWindow.removeCurrentImage();
			}
		});
		
		dialog.okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dialog.rbMedia.isSelected()) {
					FiltroOp.ventanaMovil(im, newIm, new FiltroMedia(), (Integer)dialog.spTam.getValue());
				}
				else if (dialog.rbMediana.isSelected()) {
					FiltroOp.ventanaMovil(im, newIm, new FiltroMediana(), (Integer)dialog.spTam.getValue());
				}
				else if (dialog.rbModa.isSelected()) {
					FiltroOp.ventanaMovil(im, newIm, new FiltroModa(), (Integer)dialog.spTam.getValue());
				}
				newIm.resetInfo();
				newIm.panel.repaint();
				dialog.setVisible(false);
			}
		});
		
		dialog.setVisible(true);
	}
}

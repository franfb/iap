import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import com.swtdesigner.SWTResourceManager;


public class MainWindow {

	protected Shell shlEditorDeImagenes;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlEditorDeImagenes.open();
		shlEditorDeImagenes.layout();
		while (!shlEditorDeImagenes.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private MenuItem menuFile;
	private MenuItem menuOpenImage;
	private Label label;
	private MenuItem mntmOpenImageFrom;
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlEditorDeImagenes = new Shell();
		shlEditorDeImagenes.setSize(609, 514);
		shlEditorDeImagenes.setText("Editor de imagenes");
		
		Menu menu = new Menu(shlEditorDeImagenes, SWT.BAR);
		shlEditorDeImagenes.setMenuBar(menu);
		menuFile = new MenuItem(menu, SWT.CASCADE);
		menuFile.setText("File");
		
		Menu menu_1 = new Menu(menuFile);
		menuFile.setMenu(menu_1);
		
		menuOpenImage = new MenuItem(menu_1, SWT.NONE);
		menuOpenImage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String file = new FileDialog(shlEditorDeImagenes).open();
				int type = FileType.getFileType(file);
				switch (type){
				case FileType.BMP:{
					label.setText("El fichero abierto es BMP");
					break;
				}
				case FileType.GIF:{
					label.setText("El fichero abierto es GIF");
					break;
				}
				case FileType.JPEG:{
					label.setText("El fichero abierto es JPEG");
					break;
				}
				case FileType.PNG:{
					label.setText("El fichero abierto es PNG");
					break;
				}
				case FileType.TIFF:{
					label.setText("El fichero abierto es TIFF");
					break;
				}
				case FileType.UNKNOWN:{
					label.setText("El fichero abierto no es conocido");
					break;
				}
				}
			}
		});
		menuOpenImage.setText("Open image from file");
		
		mntmOpenImageFrom = new MenuItem(menu_1, SWT.NONE);
		mntmOpenImageFrom.setText("Open image from URL");
		
		label = new Label(shlEditorDeImagenes, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("Segoe UI", 20, SWT.NORMAL));
		label.setBounds(10, 130, 573, 42);
		label.setText("New Label");

	}
}

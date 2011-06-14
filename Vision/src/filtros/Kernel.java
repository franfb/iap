package filtros;

import org.ejml.simple.SimpleMatrix;

public class Kernel {

	private SimpleMatrix mat;
	private int filIni, colIni;
	
	public Kernel(int numFil, int numCol, int filIni, int colIni) {
		mat = new SimpleMatrix(numFil, numCol);
		this.filIni = filIni;
		this.colIni = colIni;
	}
	
	public void trasponer() {
		mat = mat.transpose();
		int temp = filIni;
		filIni = colIni;
		colIni = temp;
	}
	
	public double get(int fil, int col) {
		return mat.get(fil - filIni, col - colIni);
	}
	
	public void set(int fil, int col, double value) {
		mat.set(fil - filIni, col - colIni, value);
	}
	
	public int getNumFil() {
		return mat.numRows();
	}
	
	public int getNumCol() {
		return mat.numCols();
	}
	
	public int getFilMax() {
		return mat.numRows() + filIni;
	}
	
	public int getColMax() {
		return mat.numCols() + colIni;
	}

	/**
	 * @return the filIni
	 */
	public int getFilIni() {
		return filIni;
	}

	/**
	 * @return the colIni
	 */
	public int getColIni() {
		return colIni;
	}
}

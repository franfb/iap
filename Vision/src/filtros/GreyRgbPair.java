package filtros;

public class GreyRgbPair implements Comparable<GreyRgbPair>{

	private int rgb;
	private int grey;
	
	public GreyRgbPair(int rgb, int grey) {
		this.rgb = rgb;
		this.grey = grey;
	}
	
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (this.getClass() != obj.getClass()) {
			return false;
		}
		else {
			return (this.grey == ((ValueDifPair)obj).getGrey());
		}
	}
	
	public int compareTo(GreyRgbPair grp) {
			return this.grey - grp.getGrey();
	}

	/**
	 * @return the value
	 */
	public int getRgb() {
		return rgb;
	}

	/**
	 * @param rgb the value to set
	 */
	public void setRgb(int rgb) {
		this.rgb = rgb;
	}

	/**
	 * @return the dif
	 */
	public int getGrey() {
		return grey;
	}

	/**
	 * @param dif the dif to set
	 */
	public void setGrey(int grey) {
		this.grey = grey;
	}
	
}

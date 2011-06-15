package filtros;

public class ValueDifPair implements Comparable<ValueDifPair> {

	private int value;
	private int dif;
	
	public ValueDifPair(int value, int dif) {
		this.value = value;
		this.dif = dif;
	}
	
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (this.getClass() != obj.getClass()) {
			return false;
		}
		else {
			return (this.dif == ((ValueDifPair)obj).getGrey());
		}
	}
	
	public int compareTo(ValueDifPair vdp) {
//		if (this.equals(vdp)) {
//			return 0;
//		}
//		else {
			return this.dif - vdp.getGrey();
//		}
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the dif
	 */
	public int getGrey() {
		return dif;
	}

	/**
	 * @param dif the dif to set
	 */
	public void setDif(int dif) {
		this.dif = dif;
	}
	
}

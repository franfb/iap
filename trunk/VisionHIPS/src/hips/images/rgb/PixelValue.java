package hips.images.rgb;

public class PixelValue extends hips.pixel.PixelValue<PixelValue, Integer> {
	private int[] r;
	private int[] g;
	private int[] b;

	public PixelValue(int n) {
		r = new int[n];
		g = new int[n];
		b = new int[n];
	}

	public static int red(int value) {
		return ((0x0FF0000 & value) >> 16);
	}

	public static int green(int value) {
		return ((0x00FF00 & value) >> 8);
	}

	public static int blue(int value) {
		return (0x0FF & value);
	}

	public static int rgb(int r, int g, int b) {
		return (((0x0FF & r) << 16) | ((0x0FF & g) << 8) | (0x0FF & b));
	}

	public void setValueInt(int value, int slice) {
		r[slice] = red(value);
		g[slice] = green(value);
		b[slice] = blue(value);
	}

	public void setValue(int slice, int red, int green, int blue) {
		r[slice] = red;
		g[slice] = green;
		b[slice] = blue;
	}

	public int getValueInt(int slice) {
		return rgb(r[slice], g[slice], b[slice]);
	}

	public int getSlices() {
		return r.length;
	}

	public PixelValue copy() {
		PixelValue p = new PixelValue(r.length);
		System.arraycopy(r, 0, p.r, 0, r.length);
		System.arraycopy(g, 0, p.g, 0, g.length);
		System.arraycopy(b, 0, p.b, 0, b.length);
		return p;
	}

	public String getString() {
		String returned = "[";
		for (int i = 0; i < r.length - 1; i++) {
			returned += "(" + getString(i) + "), ";
		}
		return returned + getString(r.length - 1) + "]";

	}

	public String getString(int index) {
		return "R=" + r[index] + ", G=" + g[index] + ", B="
				+ b[index];
	}

	public void setLower(PixelValue p) {
		for (int i = 0; i < r.length; i++) {
			if (p.r[i] < r[i]) {
				r[i] = p.r[i];
			}
			if (p.g[i] < g[i]) {
				g[i] = p.g[i];
			}
			if (p.b[i] < b[i]) {
				b[i] = p.b[i];
			}
		}
	}

	public void setGreater(PixelValue p) {
		for (int i = 0; i < r.length; i++) {
			if (p.r[i] > r[i]) {
				r[i] = p.r[i];
			}
			if (p.g[i] > g[i]) {
				g[i] = p.g[i];
			}
			if (p.b[i] > b[i]) {
				b[i] = p.b[i];
			}
		}
	}

	public boolean isLowerOrEqual(PixelValue p) {
		for (int i = 0; i < r.length; i++) {
			if (p.r[i] < r[i]) {
				return false;
			}
			if (p.g[i] < g[i]) {
				return false;
			}
			if (p.b[i] < b[i]) {
				return false;
			}
		}
		return true;
	}

	public boolean isGreaterOrEqual(PixelValue p) {
		for (int i = 0; i < r.length; i++) {
			if (p.r[i] > r[i]) {
				return false;
			}
			if (p.g[i] > g[i]) {
				return false;
			}
			if (p.b[i] > b[i]) {
				return false;
			}
		}
		return true;
	}

	public boolean isEqual(PixelValue p) {
		for (int i = 0; i < r.length; i++) {
			if (p.r[i] != r[i]) {
				return false;
			}
			if (p.g[i] != g[i]) {
				return false;
			}
			if (p.b[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

	public PixelValue range(PixelValue p) {
		PixelValue pRange = new PixelValue(r.length);
		for (int i = 0; i < r.length; i++) {
			pRange.r[i] = Math.abs(r[i] - p.r[i]);
			pRange.g[i] = Math.abs(g[i] - p.g[i]);
			pRange.b[i] = Math.abs(b[i] - p.b[i]);
		}
		return pRange;
	}

	public void setValueAsFloat(float value, int slice) {
		r[slice] = (int) value;
		g[slice] = (int) value;
		b[slice] = (int) value;
	}

	public float getValueAsFloat(int slice) {
		return r[slice];
	}

	public Integer minValue() {
		return minValueInt();
	}

	public Integer maxValue() {
		return maxValueInt();
	}

	public void setValue(Integer value, int slice) {
		setValueInt(value, slice);
	}

	public Integer getValue(int slice) {
		return getValueInt(slice);
	}

	public void add(PixelValue p) {
		for (int i = 0; i < r.length; i++) {
			r[i] += p.r[i];
			g[i] += p.g[i];
			b[i] += p.b[i];
		}
	}

	public void add(float value) {
		for (int i = 0; i < r.length; i++) {
			r[i] += value;
			g[i] += value;
			b[i] += value;
		}
	}

	public void sub(PixelValue p) {
		for (int i = 0; i < r.length; i++) {
			r[i] -= p.r[i];
			g[i] -= p.g[i];
			b[i] -= p.b[i];
		}
	}

	public void sub(float value) {
		for (int i = 0; i < r.length; i++) {
			r[i] -= value;
			g[i] -= value;
			b[i] -= value;
		}
	}

	public void div(float value) {
		for (int i = 0; i < r.length; i++) {
			r[i] /= value;
			g[i] /= value;
			b[i] /= value;
		}
	}

	public void mult(float value) {
		for (int i = 0; i < r.length; i++) {
			r[i] *= value;
			g[i] *= value;
			b[i] *= value;
		}
	}

	public int minValueInt() {
		int min = r[0];
		for (int i = 0; i < r.length; i++) {
			if (min > r[i]) {
				min = r[i];
			}
			if (min > g[i]) {
				min = g[i];
			}
			if (min > b[i]) {
				min = b[i];
			}
		}
		return min;
	}

	public int maxValueInt() {
		int max = r[0];
		for (int i = 0; i < r.length; i++) {
			if (max < r[i]) {
				max = r[i];
			}
			if (max < g[i]) {
				max = g[i];
			}
			if (max < b[i]) {
				max = b[i];
			}
		}
		return max;
	}

	public void setValue(Integer value, int slice, int channel) {
		if (channel == 0){
			r[slice] = value;
			return;
		}
		if (channel == 1){
			g[slice] = value;
			return;
		}
		if (channel == 2){
			b[slice] = value;
		}
	}

	public void setValueAsFloat(float value, int slice, int channel) {
		if (channel == 0){
			r[slice] = (int) value;
			return;
		}
		if (channel == 1){
			g[slice] = (int) value;
			return;
		}
		if (channel == 2){
			b[slice] = (int) value;
		}
	}

	public Integer getValue(int slice, int channel) {
		if (channel == 0){
			return r[slice];
		}
		if (channel == 1){
			return g[slice];
		}
		if (channel == 2){
			return b[slice];
		}
		return null;
	}

	public float getValueAsFloat(int slice, int channel) {
		if (channel == 0){
			return r[slice];
		}
		if (channel == 1){
			return g[slice];
		}
		if (channel == 2){
			return b[slice];
		}
		return 0;
	}
}

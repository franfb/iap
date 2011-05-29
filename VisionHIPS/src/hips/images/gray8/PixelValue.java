package hips.images.gray8;

public class PixelValue extends hips.pixel.PixelValue<PixelValue, Integer> {

    private int[] slices;

    public PixelValue(int n) {
        slices = new int[n];
    }

    public void setLower(PixelValue p) {
        for (int i = 0; i < slices.length; i++) {
            if (p.slices[i] < slices[i]) {
                slices[i] = p.slices[i];
            }
        }
    }

    public void setGreater(PixelValue p) {
        for (int i = 0; i < slices.length; i++) {
            if (p.slices[i] > slices[i]) {
                slices[i] = p.slices[i];
            }
        }
    }

    public boolean isEqual(PixelValue p) {
        for (int i = 0; i < slices.length; i++) {
            if (p.slices[i] != slices[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean isGreaterOrEqual(PixelValue p) {
        for (int i = 0; i < slices.length; i++) {
            if (p.slices[i] > slices[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean isLowerOrEqual(PixelValue p) {
        for (int i = 0; i < slices.length; i++) {
            if (p.slices[i] < slices[i]) {
                return false;
            }
        }
        return true;
    }

    public PixelValue range(PixelValue p) {
        PixelValue pRange = new PixelValue(slices.length);
        for (int i = 0; i < slices.length; i++) {
            pRange.slices[i] = Math.abs(slices[i] - p.slices[i]);
        }
        return pRange;
    }

    public int maxValueInt() {
        int max = slices[0];
        for (int i = 1; i < slices.length; i++) {
            if (max < slices[i]) {
                max = slices[i];
            }
        }
        return max;
    }

    public int minValueInt() {
        int min = slices[0];
        for (int i = 1; i < slices.length; i++) {
            if (min > slices[i]) {
                min = slices[i];
            }
        }
        return min;
    }

    public String getString() {
        String returned = "[";
        for (int i = 0; i < slices.length - 1; i++) {
            returned += slices[i] + ", ";
        }
        return returned + slices[slices.length - 1] + "]";
    }

    public String getString(int index) {
        return "" + slices[index];
    }

    public void setValueInt(int value, int slice) {
        slices[slice] = value;
    }

    public void setValueAsFloat(float value, int slice) {
        slices[slice] = (int) value;
    }

    public int getValueInt(int slice) {
        return slices[slice];
    }

    public float getValueAsFloat(int slice) {
        return slices[slice];
    }

    public int getSlices() {
        return slices.length;
    }

    public void add(PixelValue p) {
        for (int i = 0; i < slices.length; i++){
            slices[i] += p.slices[i];
        }
    }

    public void add(float value) {
        for (int i = 0; i < slices.length; i++){
            slices[i] += (int) value;
        }
    }

    public void sub(PixelValue p) {
        PixelValue r = new PixelValue(slices.length);
        for (int i = 0; i < slices.length; i++){
            slices[i] -= p.slices[i];
        }
    }

    public void sub(float value) {
        PixelValue r = new PixelValue(slices.length);
        for (int i = 0; i < slices.length; i++){
            r.slices[i] -= (int) value;
        }
    }

    public void mult(float value) {
        for (int i = 0; i < slices.length; i++){
            slices[i] *= (int) value;
        }
    }

    public void div(float value) {
        for (int i = 0; i < slices.length; i++){
            slices[i] /= (int) value;
        }
    }

    public PixelValue copy() {
        PixelValue p = new PixelValue(slices.length);
        System.arraycopy(slices, 0, p.slices, 0, slices.length);
        return p;
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

	public void setValue(Integer value, int slice, int channel) {
		setValue(value, slice);
	}

	public void setValueAsFloat(float value, int slice, int channel) {
		setValueAsFloat(value, slice);
	}

	public Integer getValue(int slice, int channel) {
		return getValue(slice);
	}

	public float getValueAsFloat(int slice, int channel) {
		return getValueAsFloat(slice);
	}
}
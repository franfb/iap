package hips.images.gray32;


public class PixelValue extends hips.pixel.PixelValue<PixelValue, Float> {

    private float[] slices;

    public PixelValue(int n) {
        slices = new float[n];
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

    public float maxValueFloat() {
        float max = slices[0];
        for (int i = 1; i < slices.length; i++) {
            if (max < slices[i]) {
                max = slices[i];
            }
        }
        return max;
    }

    public float minValueFloat() {
        float min = slices[0];
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

    public void setValueFloat(float value, int slice) {
        slices[slice] = value;
    }

    public void setValueAsFloat(float value, int slice) {
        slices[slice] = value;
    }

    public float getValueFloat(int slice) {
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
            slices[i] += value;
        }
    }

    public void sub(PixelValue p) {
        for (int i = 0; i < slices.length; i++){
            slices[i] -= p.slices[i];
        }
    }

    public void sub(float value) {
        for (int i = 0; i < slices.length; i++){
            slices[i] -= value;
        }
    }

    public void mult(float value) {
        for (int i = 0; i < slices.length; i++){
            slices[i] *= value;
        }
    }

    public void div(float value) {
        for (int i = 0; i < slices.length; i++){
            slices[i] /= value;
        }
    }

    public PixelValue copy() {
        PixelValue p = new PixelValue(slices.length);
        System.arraycopy(slices, 0, p.slices, 0, slices.length);
        return p;
    }

    public Float minValue() {
        return minValueFloat();
    }

    public Float maxValue() {
        return maxValueFloat();
    }

    public void setValue(Float value, int slice) {
        setValueFloat(value, slice);
    }

    public Float getValue(int slice) {
        return getValueFloat(slice);
    }
    
    public void setValue(Float value, int slice, int channel) {
		setValue(value, slice);
	}

	public void setValueAsFloat(float value, int slice, int channel) {
		setValueAsFloat(value, slice);
	}

	public Float getValue(int slice, int channel) {
		return getValue(slice);
	}

	public float getValueAsFloat(int slice, int channel) {
		return getValueAsFloat(slice);
	}
}

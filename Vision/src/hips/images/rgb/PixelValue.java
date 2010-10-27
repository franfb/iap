package hips.images.rgb;

public class PixelValue extends hips.pixel.PixelValue<PixelValue, Integer> {

    private int[] rgb;

    public PixelValue(int n) {
        rgb = new int[n];
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

    public void setValueInt(int value, int slice) {
        rgb[slice] = value;
    }

    public void setValue(int slice, int r, int g, int b) {
        rgb[slice] = hips.images.rgb.ImageRGB.toRGB(r, g, b);
    }

    public int getValueInt(int slice) {
        return rgb[slice];
    }

    public int getSlices() {
        return rgb.length;
    }

    public PixelValue copy() {
        PixelValue p = new PixelValue(rgb.length);
        System.arraycopy(rgb, 0, p.rgb, 0, rgb.length);
        return p;
    }

    public String getString() {
        String returned = "[";
        for (int i = 0; i < rgb.length - 1; i++) {
            returned += "(" + getString(i) + "), ";
        }
        return returned + getString(rgb.length - 1) + "]";

    }

    public String getString(int index) {
        return  "R=" + red(rgb[index]) +
                ", G=" + green(rgb[index]) +
                ", B=" + blue(rgb[index]);
    }

    public void setValue(Integer value, int slice) {
        setValueInt(value, slice);
    }

    public Integer getValue(int slice) {
        return getValueInt(slice);
    }

	public void setLower(PixelValue p) {
        for (int i = 0; i < rgb.length; i++) {
            if (red(p.rgb[i]) < red(rgb[i])) {
//                slices[i] = p.slices[i];
            }
        }
    }
	
	@Override
	public void setGreater(PixelValue pixel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLowerOrEqual(PixelValue pvalue) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGreaterOrEqual(PixelValue pvalue) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEqual(PixelValue pvalue) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PixelValue range(PixelValue pvalue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValueAsFloat(float value, int slice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getValueAsFloat(int slice) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer minValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer maxValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(PixelValue pvalue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(float value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sub(PixelValue pvalue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sub(float value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void div(float value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mult(float value) {
		// TODO Auto-generated method stub
		
	}
	
	 public int maxValueInt() { return 0;}
}

package hips.images.gray32;

class ListFloat {
    
    private float[] prios;
    private int[] pixels;
    private int[] lower;
    private int[] greater;
    private int[] lowerDif;
    private int[] greaterDif;
    private int greatest;
    private int lowest;
    private int greatestDif;
    private int size;
    private int n;

    protected ListFloat(int n) {
        this.n = n;
        prios = new float[n];
        pixels = new int[n];
        lower = new int[n];
        greater = new int[n];
        lowerDif = new int[n];
        greaterDif = new int[n];
        lowest = -1;
        greatest = -1;
        greatestDif = -1;
        size = 0;
    }

    protected void add(int pixel, float prio) {
        if (getSize() == getPixels().length) {
            float[] newPrios = new float[getSize() + getN()];
            int[] newPixels = new int[getSize() + getN()];
            int[] newLower = new int[getSize() + getN()];
            int[] newGreater = new int[getSize() + getN()];
            int[] newLowerDif = new int[getSize() + getN()];
            int[] newGreaterDif = new int[getSize() + getN()];
            for (int i = 0; i < getPixels().length; i++) {
                newPrios[i] = getPrios()[i];
                newPixels[i] = getPixels()[i];
                newLower[i] = getLower()[i];
                newGreater[i] = getGreater()[i];
                newLowerDif[i] = getLowerDif()[i];
                newGreaterDif[i] = getGreaterDif()[i];
            }
            setPrios(newPrios);
            setPixels(newPixels);
            setLower(newLower);
            setGreater(newGreater);
            setLowerDif(newLowerDif);
            setGreaterDif(newGreaterDif);
        }
        getPrios()[getSize()] = prio;
        getPixels()[getSize()] = pixel;
        if (getSize() == 0) {
            setLowest(0);
            setGreatest(0);
            setGreatestDif(0);
            getLower()[0] = -1;
            getGreater()[0] = -1;
            getLowerDif()[0] = -1;
            getGreaterDif()[0] = -1;
            setSize(1);
            return;
        }
        if (Math.abs(getPrios()[getLowest()] - prio) < Math.abs(getPrios()[getGreatest()] - prio)) {
            lowInsert(prio);
        } else {
            highInsert(prio);
        }
    }

    protected void lowInsert(float prio) {
        if (getPrios()[getLowest()] > prio) {
            getLowerDif()[getLowest()] = getSize();
            getGreaterDif()[getSize()] = getLowest();
            getLowerDif()[getSize()] = -1;
            getLower()[getLowest()] = getSize();
            getGreater()[getSize()] = getLowest();
            setLowest(getSize());
            getLower()[getSize()] = -1;
            setSize(getSize() + 1);
            return;
        }
        for (int pos = getLowest(); pos != getGreatestDif(); pos = getGreaterDif()[pos]) {
            if (getPrios()[pos] >= prio) {
                if (getPrios()[pos] > prio) {
                    getGreaterDif()[getLowerDif()[pos]] = getSize();
                    getLowerDif()[getSize()] = getLowerDif()[pos];
                    getGreaterDif()[getSize()] = pos;
                    getLowerDif()[pos] = getSize();
                    getGreater()[getLower()[pos]] = getSize();
                    getLower()[getSize()] = getLower()[pos];
                    getGreater()[getSize()] = pos;
                    getLower()[pos] = getSize();
                    setSize(getSize() + 1);
                    return;
                }
                getLowerDif()[getSize()] = -1;
                getGreaterDif()[getSize()] = -1;
                getLower()[getGreater()[pos]] = getSize();
                getLower()[getSize()] = pos;
                getGreater()[getSize()] = getGreater()[pos];
                getGreater()[pos] = getSize();
                setSize(getSize() + 1);
                return;
            }
        }
        if (getPrios()[getGreatestDif()] > prio) {
            getGreaterDif()[getLowerDif()[getGreatestDif()]] = getSize();
            getLowerDif()[getSize()] = getLowerDif()[getGreatestDif()];
            getGreaterDif()[getSize()] = getGreatestDif();
            getLowerDif()[getGreatestDif()] = getSize();
            getGreater()[getLower()[getGreatestDif()]] = getSize();
            getLower()[getSize()] = getLower()[getGreatestDif()];
            getGreater()[getSize()] = getGreatestDif();
            getLower()[getGreatestDif()] = getSize();
            setSize(getSize() + 1);
            return;
        }
        if (getPrios()[getGreatestDif()] == prio) {
            getGreaterDif()[getSize()] = -1;
            getLowerDif()[getSize()] = -1;
            if (getGreater()[getGreatestDif()] != -1) {
                getLower()[getGreater()[getGreatestDif()]] = getSize();
                getLower()[getSize()] = getGreatestDif();
                getGreater()[getSize()] = getGreater()[getGreatestDif()];
                getGreater()[getGreatestDif()] = getSize();
                setSize(getSize() + 1);
                return;
            }
            setGreatest(getSize());
            getLower()[getGreatest()] = getGreatestDif();
            getGreater()[getGreatest()] = -1;
            getGreater()[getGreatestDif()] = getGreatest();
            setSize(getSize() + 1);
            return;
        }
        getGreaterDif()[getGreatestDif()] = getSize();
        getLowerDif()[getSize()] = getGreatestDif();
        getGreaterDif()[getSize()] = -1;
        getGreater()[getGreatest()] = getSize();
        getLower()[getSize()] = getGreatest();
        setGreatest(getSize());
        setGreatestDif(getSize());
        getGreater()[getSize()] = -1;
        setSize(getSize() + 1);
    }

    protected void highInsert(float prio) {
        if (getPrios()[getGreatestDif()] <= prio) {
            if (getPrios()[getGreatestDif()] < prio) {
                getGreaterDif()[getGreatestDif()] = getSize();
                getLowerDif()[getSize()] = getGreatestDif();
                setGreatestDif(getSize());
            } else {
                getLowerDif()[getSize()] = -1;
            }
            getGreaterDif()[getSize()] = -1;
            getGreater()[getGreatest()] = getSize();
            getLower()[getSize()] = getGreatest();
            setGreatest(getSize());
            getGreater()[getSize()] = -1;
            setSize(getSize() + 1);
            return;
        }
        for (int pos = getLowerDif()[getGreatestDif()]; pos != -1; pos = getLowerDif()[pos]) {
            if (getPrios()[pos] <= prio) {
                if (getPrios()[pos] < prio) {
                    getLowerDif()[getGreaterDif()[pos]] = getSize();
                    getGreaterDif()[getSize()] = getGreaterDif()[pos];
                    getLowerDif()[getSize()] = pos;
                    getGreaterDif()[pos] = getSize();
                    getLower()[getSize()] = getLower()[getGreaterDif()[getSize()]];
                    getGreater()[getLower()[getGreaterDif()[getSize()]]] = getSize();
                    getLower()[getGreaterDif()[getSize()]] = getSize();
                    getGreater()[getSize()] = getGreaterDif()[getSize()];
                    setSize(getSize() + 1);
                    return;
                }
                getLowerDif()[getSize()] = -1;
                getGreaterDif()[getSize()] = -1;
                getLower()[getGreater()[pos]] = getSize();
                getGreater()[getSize()] = getGreater()[pos];
                getLower()[getSize()] = pos;
                getGreater()[pos] = getSize();
                setSize(getSize() + 1);
                return;
            }
        }
        getLowerDif()[getLowest()] = getSize();
        getGreaterDif()[getSize()] = getLowest();
        getLowerDif()[getSize()] = -1;
        getLower()[getLowest()] = getSize();
        getGreater()[getSize()] = getLowest();
        setLowest(getSize());
        getLower()[getSize()] = -1;
        setSize(getSize() + 1);
    }

    protected boolean isEmpty() {
        if (getSize() == 0) {
            return true;
        }
        return false;
    }

    protected PixelPriority removeMax() {
        if (getSize() == 1) {
            setSize(0);
            return new PixelPriority(getPixels()[0], getPrios()[0]);
        }
        PixelPriority p = new PixelPriority(getPixels()[getGreatest()], getPrios()[getGreatest()]);
        setSize(getSize() - 1);
        int oldGreat = getGreatest();
        setGreatest(getLower()[getGreatest()]);
        getGreater()[getGreatest()] = -1;
        if (getPrios()[getGreatest()] != getPrios()[oldGreat]) {
            setGreatestDif(getLowerDif()[oldGreat]);
            getGreaterDif()[getGreatestDif()] = -1;
        }
        fill(oldGreat);
        return p;
    }

    protected PixelPriority removeMin() {
        if (getSize() == 1) {
            setSize(0);
            return new PixelPriority(getPixels()[0], getPrios()[0]);
        }
        PixelPriority p = new PixelPriority(getPixels()[getLowest()], getPrios()[getLowest()]);
        setSize(getSize() - 1);
        int oldLow = getLowest();
        setLowest(getGreater()[getLowest()]);
        getLower()[getLowest()] = -1;
        if (getPrios()[getLowest()] == getPrios()[oldLow]) {
            if (getGreaterDif()[oldLow] != -1) {
                getLowerDif()[getGreaterDif()[oldLow]] = getLowest();
            } else {
                setGreatestDif(getLowest());
            }
            getGreaterDif()[getLowest()] = getGreaterDif()[oldLow];
        }
        getLowerDif()[getLowest()] = -1;
        fill(oldLow);
        return p;
    }

    protected PixelPriority removeDontCare() {
        PixelPriority p = new PixelPriority(getPixels()[getLowest()], getPrios()[getLowest()]);
        setLowest(getGreater()[getLowest()]);
        setSize(getSize() - 1);
        return p;
    }

    protected PixelPriority removePrio(float prio) {
        for (int pos = getLowest(); pos != -1; pos = getGreaterDif()[pos]) {
            if (getPrios()[pos] >= prio) {
                if (getPrios()[pos] > prio) {
                    pos = getLowerDif()[pos];
                    if (pos == -1) {
                        return null;
                    }
                }
                if (pos == getGreatestDif()) {
                    return removeMax();
                }
                if (pos == getLowest()) {
                    return removeMin();
                }
                PixelPriority p = new PixelPriority(getPixels()[pos], getPrios()[pos]);
                setSize(getSize() - 1);
                getGreater()[getLower()[pos]] = getGreater()[pos];
                getLower()[getGreater()[pos]] = getLower()[pos];
                getGreaterDif()[getLowerDif()[pos]] = getGreater()[pos];
                getLowerDif()[getGreater()[pos]] = getLowerDif()[pos];
                if (getPrios()[pos] == getPrios()[getGreater()[pos]]) {
                    getLowerDif()[getGreaterDif()[pos]] = getGreater()[pos];
                    getGreaterDif()[getGreater()[pos]] = getGreaterDif()[pos];
                }
                fill(pos);
                return p;
            }
        }
        return removeMax();
    }

    protected PixelPriority removePrioOrMin(float prio) {
        for (int pos = getLowest(); pos != -1; pos = getGreaterDif()[pos]) {
            if (getPrios()[pos] >= prio) {
                if (getPrios()[pos] > prio) {
                    if (getLowerDif()[pos] != -1) {
                        pos = getLowerDif()[pos];
                    }
                }
                if (pos == getGreatestDif()) {
                    return removeMax();
                }
                if (pos == getLowest()) {
                    return removeMin();
                }
                PixelPriority p = new PixelPriority(getPixels()[pos], getPrios()[pos]);
                setSize(getSize() - 1);
                getGreater()[getLower()[pos]] = getGreater()[pos];
                getLower()[getGreater()[pos]] = getLower()[pos];
                getGreaterDif()[getLowerDif()[pos]] = getGreater()[pos];
                getLowerDif()[getGreater()[pos]] = getLowerDif()[pos];
                if (getPrios()[pos] == getPrios()[getGreater()[pos]]) {
                    getLowerDif()[getGreaterDif()[pos]] = getGreater()[pos];
                    getGreaterDif()[getGreater()[pos]] = getGreaterDif()[pos];
                }
                fill(pos);
                return p;
            }
        }
        return removeMax();
    }

    private void fill(int gap) {
        if (gap != getSize()) {
            getPrios()[gap] = getPrios()[getSize()];
            getPixels()[gap] = getPixels()[getSize()];
            getLower()[gap] = getLower()[getSize()];
            getGreater()[gap] = getGreater()[getSize()];
            getLowerDif()[gap] = getLowerDif()[getSize()];
            getGreaterDif()[gap] = getGreaterDif()[getSize()];
            if (getGreatest() == getSize()) {
                setGreatest(gap);
            }
            if (getGreatestDif() == getSize()) {
                setGreatestDif(gap);
            }
            if (getLowest() == getSize()) {
                setLowest(gap);
            }
            if (getLower()[getSize()] != -1) {
                getGreater()[getLower()[getSize()]] = gap;
            }
            if (getGreater()[getSize()] != -1) {
                getLower()[getGreater()[getSize()]] = gap;
            }
            if (getLowerDif()[getSize()] != -1) {
                getGreaterDif()[getLowerDif()[getSize()]] = gap;
            }
            if (getGreaterDif()[getSize()] != -1) {
                getLowerDif()[getGreaterDif()[getSize()]] = gap;
            }
        }
    }

    /**
     * @return the prios
     */
    protected float[] getPrios() {
        return prios;
    }

    /**
     * @param prios the prios to set
     */
    protected void setPrios(float[] prios) {
        this.prios = prios;
    }

    /**
     * @return the pixels
     */
    protected int[] getPixels() {
        return pixels;
    }

    /**
     * @param pixels the pixels to set
     */
    protected void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    /**
     * @return the lower
     */
    protected int[] getLower() {
        return lower;
    }

    /**
     * @param lower the lower to set
     */
    protected void setLower(int[] lower) {
        this.lower = lower;
    }

    /**
     * @return the greater
     */
    protected int[] getGreater() {
        return greater;
    }

    /**
     * @param greater the greater to set
     */
    protected void setGreater(int[] greater) {
        this.greater = greater;
    }

    /**
     * @return the lowerDif
     */
    protected int[] getLowerDif() {
        return lowerDif;
    }

    /**
     * @param lowerDif the lowerDif to set
     */
    protected void setLowerDif(int[] lowerDif) {
        this.lowerDif = lowerDif;
    }

    /**
     * @return the greaterDif
     */
    protected int[] getGreaterDif() {
        return greaterDif;
    }

    /**
     * @param greaterDif the greaterDif to set
     */
    protected void setGreaterDif(int[] greaterDif) {
        this.greaterDif = greaterDif;
    }

    /**
     * @return the greatest
     */
    protected int getGreatest() {
        return greatest;
    }

    /**
     * @param greatest the greatest to set
     */
    protected void setGreatest(int greatest) {
        this.greatest = greatest;
    }

    /**
     * @return the lowest
     */
    protected int getLowest() {
        return lowest;
    }

    /**
     * @param lowest the lowest to set
     */
    protected void setLowest(int lowest) {
        this.lowest = lowest;
    }

    /**
     * @return the greatestDif
     */
    protected int getGreatestDif() {
        return greatestDif;
    }

    /**
     * @param greatestDif the greatestDif to set
     */
    protected void setGreatestDif(int greatestDif) {
        this.greatestDif = greatestDif;
    }

    /**
     * @return the size
     */
    protected int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    protected void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the n
     */
    protected int getN() {
        return n;
    }

    /**
     * @param n the n to set
     */
    protected void setN(int n) {
        this.n = n;
    }
}

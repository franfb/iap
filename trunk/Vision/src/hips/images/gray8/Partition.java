package hips.images.gray8;

import hips.tools.ListPixels;
import hips.tools.Neighborhood;
import hips.region.NewRegionEvent;
import hips.region.Region;

public class Partition extends hips.Partition<ImageGray8, PixelValue> {

    public Partition(ImageGray8 input, PixelValue alpha, PixelValue omega, float ci) {
        super(input, alpha, omega, ci);
    }

    public Partition(ImageGray8 input) {
        super(input);
    }

    public void makeRegions() {
        Object[] returned = getWorkingData();
        int alphaInt = (Integer) returned[0];
        ImageGray8 f = (ImageGray8) returned[1];
        PixelValue omegaTr = (PixelValue) returned[2];

        Neighborhood nbh = new Neighborhood(f.getWidth(), f.getHeight());
        Queue PQueue = new Queue(256);
        pixels = new int[f.getSize()];
        ListPixels refs = new ListPixels(f.getSize() / 8);
        ListPixels reserve = new ListPixels(f.getSize() / 8);
        ListPixels all = new ListPixels(f.getSize() / 8);
        ListPixels stack = new ListPixels(f.getSize() / 8);
        int base = 0;
        lbl = new int[f.getSize()];
        int[] rl = new int[f.getSize()];
        boolean[] connected = new boolean[f.getSize()];
        int lblval = 0;
        for (int i = 0; i < f.getSize(); i++) {
            lbl[i] = -1;
            rl[i] = Integer.MAX_VALUE;
            connected[i] = false;
        }
        for (int p = 0; p < f.getSize(); p++) {
            if (lbl[p] == -1) {
                stack.clear();
                reserve.clear();
                all.clear();
                PixelValue mincc = f.getPixelValue(p);
                PixelValue maxcc = f.getPixelValue(p);
                int rlcrt = alphaInt;
                int rcrt = 0;
                int edges = 0;
                int edgesOK = 0;
                PQueue.add(p, 0);
                while (!PQueue.isEmpty()) {
                    PixelPriority datum = PQueue.remove(rcrt);
                    // Añadido al algoritmo original: si el pixel ya fue visitado, no lo vuelve a visitar
                    if (rl[datum.getPixel()] == -1) {
                        continue;
                    }
                    if (lbl[datum.getPixel()] != -1) {
                        continue;
                    }
                    if (datum.getPriority() > rcrt) {
                        while (stack.hasNew()) {
                            int r = stack.getNew();
                            for (int z = 0; z < Neighborhood.NEIGHBORS; z++) {
                                int q = nbh.getNeighbor(r, z);
                                if (q == -1) {
                                    continue;
                                }
                                if (connected[q]) {
                                    edges++;
                                    if (f.getPixelValue(r).range(f.getPixelValue(q)).maxValueInt() <= alphaInt) {
                                        edgesOK++;
                                    }
                                }
                            }
                            connected[r] = true;
                            reserve.add(r);
                        }
                        if (edges == 0 || ((float) edgesOK / (float) edges) >= getCindex()) {
                            for (int i = 0; i < reserve.getSize(); i++) {
                                lbl[reserve.getPixel(i)] = lblval;
                                all.add(reserve.getPixel(i));
                            }
                            reserve.clear();
                        }
                        rcrt = datum.getPriority();
                        if (lbl[datum.getPixel()] != -1) {
                            continue;
                        }
                    }
                    stack.add(datum.getPixel());
                    // Añadido al algoritmo original: el pixel se pone a -1 en rl, para que no lo visite mas
                    rl[datum.getPixel()] = -1;
                    PixelValue fp = f.getPixelValue(datum.getPixel());
                    mincc.setLower(fp);
                    maxcc.setGreater(fp);
                    if (!omegaTr.isGreaterOrEqual(maxcc.range(mincc)) || rcrt > rlcrt) {
                        while (!PQueue.isEmpty()) {
                            rl[PQueue.remove().getPixel()] = Integer.MAX_VALUE;
                        }
                        while (stack.hasNew()) {
                            stack.getNew();
                        }
                        break;
                    }
                    for (int z = 0; z < Neighborhood.NEIGHBORS; z++) {
                        int q = nbh.getNeighbor(datum.getPixel(), z);
                        if (q == -1) {
                            continue; // No hay vecino, está en un borde de la imagen.
                        }
                        int rlval = (f.getPixelValue(q).range(f.getPixelValue(datum.getPixel()))).maxValueInt();
                        if ((lbl[q] != -1) && (lbl[q] != lblval) && rlcrt >= rlval) {
                            //rlcrt = f.getNextLower(rlval);
                            rlcrt = rlval - 1;
                            if (rcrt > rlcrt) {
                                while (!PQueue.isEmpty()) {
                                    rl[PQueue.remove().getPixel()] = Integer.MAX_VALUE;
                                }
                                while (stack.hasNew()) {
                                    stack.getNew();
                                }
                                break;
                            }
                        } else if (rlval <= rlcrt && rl[q] != -1 && rlval < rl[q]) {
                            rl[q] = rlval;
                            PQueue.add(q, rlval);
                        }
                    }
                } // Fin del while
                while (stack.hasNew()) {
                    int r = stack.getNew();
                    for (int z = 0; z < Neighborhood.NEIGHBORS; z++) {
                        int q = nbh.getNeighbor(r, z);
                        if (q == -1) {
                            continue;
                        }
                        if (connected[q]) {
                            edges++;
                            if (f.getPixelValue(r).range(f.getPixelValue(q)).maxValueInt() <= alphaInt) {
                                edgesOK++;
                            }
                        }
                    }
                    connected[r] = true;
                    reserve.add(r);
                }
                if (edges == 0 || ((float) edgesOK / (float) edges) >= getCindex()) {
                    for (int i = 0; i < reserve.getSize(); i++) {
                        lbl[reserve.getPixel(i)] = lblval;
                        all.add(reserve.getPixel(i));
                    }
                }
                stack.clearIterator();
                while (stack.hasNew()) {
                    int r = stack.getNew();
                    rl[r] = Integer.MAX_VALUE;
                    connected[r] = false;
                }
                for (int i = 0; i < all.getSize(); i++) {
                    pixels[base + i] = all.getPixel(i);
                }
                refs.add(base);
                fireRegionEvent(new NewRegionEvent(this, new Region(pixels, base, all.getSize(), lblval)));
                base += all.getSize();
                lblval++;
            }
        }
        references = new int[refs.getSize()];
        for (int i = 0; i < refs.getSize(); i++) {
            references[i] = refs.getPixel(i);
        }
    }
}

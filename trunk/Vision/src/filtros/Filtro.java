package filtros;

import vision.Image;

public interface Filtro {

	public abstract int evaluar(Image im, int x, int y, int w, int h);
}

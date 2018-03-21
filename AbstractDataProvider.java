package Framework;


import java.util.Iterator;

public abstract class AbstractDataProvider implements Iterator<Object[]> {

	public abstract boolean hasNext();

	public abstract Object[] next();

	public void remove() {
		throw new UnsupportedOperationException("remove unsupported.");
	}

}
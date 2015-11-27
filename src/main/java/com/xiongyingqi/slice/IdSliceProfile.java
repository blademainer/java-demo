package com.xiongyingqi.slice;

/**
 * @author xiongyingqi
 * @version 2015-11-27 16:34
 */
public class IdSliceProfile {
    private int current;
    private int size;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof IdSliceProfile))
            return false;

        IdSliceProfile that = (IdSliceProfile) o;

        if (current != that.current)
            return false;
        if (size != that.size)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = current;
        result = 31 * result + size;
        return result;
    }

    @Override
    public String toString() {
        return "IdSliceProfile{" +
                "current=" + current +
                ", size=" + size +
                '}';
    }
}

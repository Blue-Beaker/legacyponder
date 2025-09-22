package io.bluebeaker.legacyponder.utils;

import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class Palette<E> implements Iterable<E> {
    protected final List<E> palette = new ArrayList<>();
    protected final HashMap<E,Integer> paletteMap = new HashMap<>();

    public boolean contains(Object o) {
        return paletteMap.containsKey(o);
    }

    public int indexOf(Object o) {
        return contains(o) ? paletteMap.get(o) : -1;
    }

    public int lastIndexOf(Object o) {
        return indexOf(o);
    }

    public int add(E e){
        return add(e,false);
    }

    public int add(E e,boolean force) {
        if(!force && contains(e)) return -1;
        palette.add(e);
        int i = palette.size() - 1;
        paletteMap.put(e, i);
        return i;
    }

    public E get(int i) {
        return palette.get(i);
    }

    public void clear() {
        palette.clear();
        paletteMap.clear();
    }

    public int size(){
        return palette.size();
    }

    @Nonnull
    @Override
    public Iterator<E> iterator() {
        return palette.iterator();
    }
    @Override
    public String toString(){
        return palette.toString();
    }
}

package edu.jl.springrediscache.mapper;

import java.util.List;

public interface Mapper {
    <O, D> D mapProperties(O source, Class<D> destination);
    <O, D> List<D> mapToList(List<O> source, Class<D> destination);
    <O, D> void mapProperties(O source, D destination);
}

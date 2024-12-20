package edu.jl.springrediscache.mapper;

import java.util.List;

public interface Mapper {
    <O, D> D convertToObject(O source, Class<D> destination);
    <O, D> List<D> convertToList(List<O> source, Class<D> destination);
    <O, D> void mapToObject(O source, D destination);
}

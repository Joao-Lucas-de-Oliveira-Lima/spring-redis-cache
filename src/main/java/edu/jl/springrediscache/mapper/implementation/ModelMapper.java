package edu.jl.springrediscache.mapper.implementation;

import edu.jl.springrediscache.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModelMapper implements Mapper {

    private final org.modelmapper.ModelMapper mapper;

    public ModelMapper(){
        this.mapper = new org.modelmapper.ModelMapper();
    }

    @Override
    public <O, D> D convertToObject(O source, Class<D> destination) {
        return mapper.map(source, destination);
    }

    @Override
    public <O, D> List<D> convertToList(List<O> source, Class<D> destination) {
        return source.stream().map(object -> mapper.map(object, destination)).toList();
    }

    @Override
    public <O, D> void mapToObject(O source, D destination) {
        mapper.map(source, destination);
    }
}
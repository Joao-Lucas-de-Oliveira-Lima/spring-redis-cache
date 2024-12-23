package edu.jl.springrediscache.mapper.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelMapperTest {

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
    }

    @Test
    @DisplayName("Should correctly map an object to another type")
    void shouldMapObjectToAnotherType() {
        SourceObject source = new SourceObject("John Doe", 25);
        DestinationObject destination = modelMapper.mapProperties(source, DestinationObject.class);

        assertNotNull(destination);
        assertEquals(source.getName(), destination.getName());
        assertEquals(source.getAge(), destination.getAge());
    }

    @Test
    @DisplayName("Should correctly map a list of objects to another type")
    void shouldMapListToAnotherType() {
        List<SourceObject> sourceList = List.of(
                new SourceObject("John Doe", 25),
                new SourceObject("Jane Doe", 30)
        );

        List<DestinationObject> destinationList = modelMapper.mapToList(sourceList, DestinationObject.class);

        assertNotNull(destinationList);
        assertEquals(sourceList.size(), destinationList.size());
        assertEquals(sourceList.get(0).getName(), destinationList.get(0).getName());
        assertEquals(sourceList.get(1).getName(), destinationList.get(1).getName());
    }

    @Test
    @DisplayName("Should correctly map properties from one object to another")
    void shouldMapPropertiesFromOneObjectToAnother() {
        SourceObject source = new SourceObject("John Doe", 25);
        DestinationObject destination = new DestinationObject();

        modelMapper.mapProperties(source, destination);

        assertNotNull(destination);
        assertEquals(source.getName(), destination.getName());
        assertEquals(source.getAge(), destination.getAge());
    }

    @Test
    @DisplayName("Should initialize ModelMapper correctly with default constructor")
    void shouldInitializeWithDefaultConstructor() {
        ModelMapper defaultModelMapper = new ModelMapper();
        assertNotNull(defaultModelMapper);
    }

    @Test
    @DisplayName("Should initialize ModelMapper correctly with parameterized constructor")
    void shouldInitializeWithParameterizedConstructor() {
        org.modelmapper.ModelMapper externalMapper = new org.modelmapper.ModelMapper();
        ModelMapper parameterizedModelMapper = new ModelMapper(externalMapper);

        assertNotNull(parameterizedModelMapper);
    }

    private static class SourceObject {
        private String name;
        private int age;

        public SourceObject(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    private static class DestinationObject {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
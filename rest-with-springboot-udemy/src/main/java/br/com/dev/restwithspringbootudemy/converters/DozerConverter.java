package br.com.dev.restwithspringbootudemy.converters;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class DozerConverter {

	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}

	public static <O, D> List<D> parseListObjects(List<O> origins, Class<D> destination) {
		List<D> destinationObjects = new ArrayList<>();
		origins.stream().forEach(origem -> destinationObjects.add(mapper.map(origem, destination)));
		return destinationObjects;
	}

}

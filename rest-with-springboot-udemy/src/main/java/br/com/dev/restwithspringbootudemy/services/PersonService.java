package br.com.dev.restwithspringbootudemy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dev.restwithspringbootudemy.converters.DozerConverter;
import br.com.dev.restwithspringbootudemy.data.models.Person;
import br.com.dev.restwithspringbootudemy.data.vo.PersonVO;
import br.com.dev.restwithspringbootudemy.exceptions.ResourceNotFoundException;
import br.com.dev.restwithspringbootudemy.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository repository;

	public PersonVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found this id"));
		return DozerConverter.parseObject(entity, PersonVO.class);
	}

	public Page<PersonVO> findAll(Pageable pageable) {
		var page = repository.findAll(pageable);
		return page.map(this::convertToPersonVO);
	}

	public PersonVO create(PersonVO person) {
		var entity = DozerConverter.parseObject(person, Person.class);
		var personVO = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return personVO;
	}

	public PersonVO update(PersonVO person) {
		Person entity = DozerConverter.parseObject(this.findById(person.getKey()), Person.class);
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		return DozerConverter.parseObject(repository.save(entity), PersonVO.class);
	}
	
	@Transactional
	public PersonVO disablePerson(Long id) {
		repository.disablePerson(id);
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found this id"));
		return DozerConverter.parseObject(entity, PersonVO.class);
	}

	public void delete(Long id) {
		PersonVO personVO = this.findById(id);
		repository.delete(DozerConverter.parseObject(personVO, Person.class));
	}

	private PersonVO convertToPersonVO(Person entity) {
		return DozerConverter.parseObject(entity, PersonVO.class);
	}

}

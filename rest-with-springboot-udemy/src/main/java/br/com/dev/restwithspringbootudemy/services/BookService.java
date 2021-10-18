package br.com.dev.restwithspringbootudemy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dev.restwithspringbootudemy.converters.DozerConverter;
import br.com.dev.restwithspringbootudemy.data.models.Book;
import br.com.dev.restwithspringbootudemy.data.vo.BookVO;
import br.com.dev.restwithspringbootudemy.exceptions.ResourceNotFoundException;
import br.com.dev.restwithspringbootudemy.repositories.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;

	public BookVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found this id"));
		return DozerConverter.parseObject(entity, BookVO.class);
	}

	public List<BookVO> findAll() {
		return DozerConverter.parseListObjects(repository.findAll(), BookVO.class);
	}

	public BookVO create(BookVO book) {
		var entity = DozerConverter.parseObject(book, Book.class);
		var bookVO = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		return bookVO;
	}

	public BookVO update(BookVO book) {
		Book entity = DozerConverter.parseObject(this.findById(book.getKey()), Book.class);
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		return DozerConverter.parseObject(repository.save(entity), BookVO.class);
	}

	public void delete(Long id) {
		BookVO bookVO = this.findById(id);
		repository.delete(DozerConverter.parseObject(bookVO, Book.class));
	}

}

package br.com.dev.restwithspringbootudemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dev.restwithspringbootudemy.data.models.Book;
import br.com.dev.restwithspringbootudemy.data.models.Person;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}

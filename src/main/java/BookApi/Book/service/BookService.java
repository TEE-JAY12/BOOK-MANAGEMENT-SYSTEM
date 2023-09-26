package BookApi.Book.service;

import BookApi.Book.dto.BookRequest;
import BookApi.Book.dto.BookResponse;
import BookApi.Book.dto.BookDto;
import org.springframework.data.domain.Page;

public interface BookService {

    BookResponse saveBook(BookRequest request) throws Exception;
    BookDto getBookDetail(Long Id);
    Page<BookDto> getBookPaged(int pageNumber, int pageSize, String sortBy) throws Exception;
    Page<BookDto> getBookByTitleOrAuthor(int pageNumber, int pageSize, String sortBy, String searchTerm) throws Exception;


}

package BookApi.Book.service;

import BookApi.Book.dto.BookRequest;
import BookApi.Book.dto.BookResponse;
import BookApi.Book.dto.BookDto;
import BookApi.Book.model.Book;
import BookApi.Book.repository.BookRepository;
import BookApi.Book.utill.LoggingUtil;
import BookApi.Book.utill.ResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service("BookService")
public class BookServiceImpl implements BookService{

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private BookRepository bookRepository;
    /*
      Intelligent Method to save and update a book by setting the id git
    */
    @Override
    public BookResponse saveBook(BookRequest request) throws Exception {

        BookResponse response  = new BookResponse();
        String language = "en";

        response.setResponseCode(HttpServletResponse.SC_BAD_REQUEST);
        response.setStatusCode(ResponseCodes.ERROR);
        response.setResponseMessage(ResponseCodes.ERROR);
        try {

            Book book = null;


            if (request.getId()>0){
                book = bookRepository.findById(request.getId()).orElse(null);
                if (book != null) {
                    book = fromMtmRequestToEntity(request, true);
                }
            }

            if (book == null){
                book = fromMtmRequestToEntity(request, false);
                book.setId(0L);
            }

            Book book1 = bookRepository.save(book);

            response.setResponseCode(HttpServletResponse.SC_OK);
            response.setStatusCode(ResponseCodes.SUCCESS);
            BookDto bookdto= fromMtmEntityToDto(book1);
            response.setData(bookdto);

            String msg = "SAVED SUCCESSFULLY";
            System.out.println("Output-terminal-save : " + msg);
            response.setResponseMessage(msg);

            //response.setEntityCode(gacLienAccount1.getEntityCode());
            return  response;
        }
        catch (Exception e)
        {
            LoggingUtil.DebugInfo(Arrays.toString(e.getStackTrace()).replaceAll(",","\n"));
            e.printStackTrace();
        }
        return response;

    }
     /*
       Method to get List of books paginated
    */

    @Override
    public Page<BookDto> getBookPaged(int pageNumber, int pageSize, String sortBy) throws Exception{
        List<BookDto> dtos = new ArrayList<>();
        Page<BookDto> results = null;
        try {
            Pageable pageableRequest = PageRequest.of(pageNumber - 1, pageSize);
            Page<Book> terminals = bookRepository.findAll(pageableRequest);

            if (terminals != null && terminals.getSize()>0){
                for (Book book : terminals.toList()){
                    BookDto dto = fromMtmEntityToDto(book);
                    dtos.add(dto);
                }
            }
            results = new PageImpl<>(dtos, pageableRequest, terminals.getTotalElements());
        }
        catch (Exception e)
        {
            LoggingUtil.DebugInfo(Arrays.toString(e.getStackTrace()).replaceAll(",","\n"));
        }
        return  results;
    }

     /*
       Method to get a book detail
    */

    @Override
    public BookDto getBookDetail(Long Id){
        BookDto bookdto = new BookDto();
        try{

            Optional<Book> book = bookRepository.findById(Id);

            if(book != null){
                bookdto = fromMtmEntityToDto(book.get());
            }
        }catch (Exception e) {
            LoggingUtil.DebugInfo(Arrays.toString(e.getStackTrace()).replaceAll(",", "\n"));
        }
        return bookdto;
    }

    @Override
    public BookDto deleteBook(Long Id){
        BookDto bookdto = new BookDto();
        try{

            Optional<Book> book = bookRepository.findById(Id);

            if(book != null){
                bookdto = fromMtmEntityToDto(book.get());
                bookRepository.deleteById(Id);
            }
        }catch (Exception e) {
            LoggingUtil.DebugInfo(Arrays.toString(e.getStackTrace()).replaceAll(",", "\n"));
        }
        return bookdto;
    }

    /*
      Method to search books by Title or Author
   */
    @Override
    public Page<BookDto> getBookByTitleOrAuthor(int pageNumber, int pageSize, String sortBy, String searchTerm) throws Exception{
        List<BookDto> dtos = new ArrayList<>();
        Page<BookDto> results = null;
        try {
            Pageable pageableRequest = PageRequest.of(pageNumber - 1, pageSize);
            Page<Book> terminals = bookRepository.findByTitleContainingOrAuthorContaining(searchTerm, searchTerm, pageableRequest);

            if (terminals != null && terminals.getSize()>0){
                for (Book book : terminals.toList()){
                    BookDto dto = fromMtmEntityToDto(book);
                    dtos.add(dto);
                }
            }
            results = new PageImpl<>(dtos, pageableRequest, terminals.getTotalElements());
        }
        catch (Exception e)
        {
            LoggingUtil.DebugInfo(Arrays.toString(e.getStackTrace()).replaceAll(",","\n"));
        }
        return  results;
    }





//    Convert request to entity
    public Book fromMtmRequestToEntity(BookRequest request,boolean isModified) {
        Book book = Book.builder()
                .id(request.getId())
                .title(request.getTitle())
                .author(request.getAuthor())
                .publicationYear(request.getPublicationYear())
                .createdDate(new Date())
                .modifiedDate(isModified ? new Date() : null)
                .build();
        return book;
    }

//Convert Entity to DTO
    public BookDto fromMtmEntityToDto(Book book) {
        BookDto bookdto = BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publicationYear(book.getPublicationYear())
                .createdDate(book.getCreatedDate())
                .modifiedDate(book.getModifiedDate())
                .build();
        return bookdto;
    }

}

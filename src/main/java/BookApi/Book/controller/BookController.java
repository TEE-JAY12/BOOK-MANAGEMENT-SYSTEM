package BookApi.Book.controller;

import BookApi.Book.dto.BookRequest;
import BookApi.Book.dto.BookResponse;
import BookApi.Book.dto.BookDto;
import BookApi.Book.service.BookService;
import BookApi.Book.utill.LoggingUtil;
import BookApi.Book.utill.ResponseCodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;



    @PostMapping("/savebook")
    public ResponseEntity<?> saveBook(HttpServletRequest request, @Valid @RequestBody BookRequest bookRequest) throws Exception {
        BookResponse response = bookService.saveBook(bookRequest);
        if (response.getResponseCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/getAllBooks")
    public ResponseEntity<?> getAllBook(HttpServletRequest request, HttpServletResponse response){
        Page<BookDto> results = null;
        BookResponse<Page<BookDto>> responses = new BookResponse<>();
        try {

            int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            int pageSize = Integer.parseInt(request.getParameter("pageSize"));

            results = bookService.getBookPaged(pageNumber,pageSize,"");


            responses.setResponseMessage(ResponseCodes.SUCCESS_STATUS);
            responses.setResponseCode(HttpServletResponse.SC_OK);
            responses.setStatusCode(ResponseCodes.SUCCESS);

            responses.setData(results);
            return new ResponseEntity<>(responses, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            LoggingUtil.DebugInfo(ex.getMessage());
            LoggingUtil.ExceptionInfo(ex);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getbook/{id}")
    public ResponseEntity<?> getbookDetails(HttpServletRequest request, HttpServletResponse response,
                                            @PathVariable("id") Long id ) {
        BookDto result = null;
        BookResponse<BookDto> responses = new BookResponse<>();
        try {


            result = bookService.getBookDetail(id);
            if (result.getId() != null){

                responses.setResponseMessage(ResponseCodes.SUCCESS_STATUS);
                responses.setResponseCode(HttpServletResponse.SC_OK);
                responses.setStatusCode(ResponseCodes.SUCCESS);
                responses.setData(result);
                return new ResponseEntity<>(responses, HttpStatus.OK);
            }
            responses.setResponseMessage(ResponseCodes.NO_RECORD_FOUND);
            responses.setResponseCode(HttpServletResponse.SC_NOT_FOUND);
            responses.setStatusCode(ResponseCodes.NO_RECORD_STATUS);

            return new ResponseEntity<>(responses, HttpStatus.NOT_FOUND);
        }
        catch (Exception ex)
        {
            LoggingUtil.DebugInfo(ex.getMessage());
            LoggingUtil.ExceptionInfo(ex);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getbooks/{searchTerm}")
    public ResponseEntity<?> getUserDetails(HttpServletRequest request, HttpServletResponse response,
                                            @PathVariable("searchTerm") String searchTerm ) {
        Page<BookDto> results = null;
        BookResponse<Page<BookDto>> responses = new BookResponse<>();
        try {
            int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            int pageSize = Integer.parseInt(request.getParameter("pageSize"));

            results = bookService.getBookByTitleOrAuthor(pageNumber,pageSize,"",searchTerm);


            responses.setResponseMessage(ResponseCodes.SUCCESS_STATUS);
            responses.setResponseCode(HttpServletResponse.SC_OK);
            responses.setStatusCode(ResponseCodes.SUCCESS);

            responses.setData(results);
            return new ResponseEntity<>(responses, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            LoggingUtil.DebugInfo(ex.getMessage());
            LoggingUtil.ExceptionInfo(ex);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/getbook/{id}")
    public ResponseEntity<?> deleteBook(HttpServletRequest request, HttpServletResponse response,
                                            @PathVariable("id") Long id ) {
        BookDto result = null;
        BookResponse<BookDto> responses = new BookResponse<>();
        try {


            result = bookService.deleteBook(id);
            if (result.getId() != null){

                responses.setResponseMessage(ResponseCodes.DELETE_STATUS);
                responses.setResponseCode(HttpServletResponse.SC_OK);
                responses.setStatusCode(ResponseCodes.SUCCESS);
                responses.setData(result);
                return new ResponseEntity<>(responses, HttpStatus.OK);
            }
            responses.setResponseMessage(ResponseCodes.NO_RECORD_FOUND);
            responses.setResponseCode(HttpServletResponse.SC_NOT_FOUND);
            responses.setStatusCode(ResponseCodes.NO_RECORD_STATUS);

            return new ResponseEntity<>(responses, HttpStatus.NOT_FOUND);
        }
        catch (Exception ex)
        {
            LoggingUtil.DebugInfo(ex.getMessage());
            LoggingUtil.ExceptionInfo(ex);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}

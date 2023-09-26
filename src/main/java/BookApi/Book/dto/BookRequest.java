package BookApi.Book.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
@Data
public class BookRequest {
    private Long id;
    @NotEmpty(message = "title is required")
    private String title;
    @NotEmpty(message = "author is required")
    private String author;
    @NotNull(message = "Year is required")
    @Positive(message = "Year can not be negative")
    private Integer publicationYear;



}

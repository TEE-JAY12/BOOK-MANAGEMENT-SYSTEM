package BookApi.Book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private Integer publicationYear;
    private Date createdDate;
    private Date modifiedDate;
}

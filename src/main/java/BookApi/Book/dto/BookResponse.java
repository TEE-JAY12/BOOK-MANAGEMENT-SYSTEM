package BookApi.Book.dto;

import lombok.Data;

@Data
public class BookResponse<T> {
    private int responseCode;
    private String responseMessage;
    private String statusCode;
    private T data;
}

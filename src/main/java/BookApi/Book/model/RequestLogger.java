package BookApi.Book.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class RequestLogger {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "REQUEST_PAYLOAD")
    private String requestPayload;

    @Lob
    @Column(name = "RESPONSE_PAYLOAD")
    private String responsePayload;
}

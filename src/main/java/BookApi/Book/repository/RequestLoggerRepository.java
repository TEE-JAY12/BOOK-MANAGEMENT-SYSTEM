package BookApi.Book.repository;

import BookApi.Book.model.Book;
import BookApi.Book.model.RequestLogger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLoggerRepository extends JpaRepository<RequestLogger, Long>, JpaSpecificationExecutor<RequestLogger> {

}

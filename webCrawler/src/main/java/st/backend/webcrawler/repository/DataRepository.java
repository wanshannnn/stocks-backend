package st.backend.webcrawler.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import javax.xml.crypto.Data;

public interface DataRepository extends MongoRepository<Data, String> {
}

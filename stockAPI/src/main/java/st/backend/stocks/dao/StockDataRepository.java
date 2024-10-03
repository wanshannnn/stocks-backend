package st.backend.stocks.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDataRepository extends MongoRepository<StockData, String> {
}

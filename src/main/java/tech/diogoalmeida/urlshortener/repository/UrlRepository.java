package tech.diogoalmeida.urlshortener.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.diogoalmeida.urlshortener.entities.UrlEntity;

public interface UrlRepository extends MongoRepository<UrlEntity, String> {

}

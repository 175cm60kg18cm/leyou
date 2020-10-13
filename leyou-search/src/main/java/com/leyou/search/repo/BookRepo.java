package com.leyou.search.repo;

import com.leyou.search.pojo.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepo extends ElasticsearchRepository<Book,String> {
}

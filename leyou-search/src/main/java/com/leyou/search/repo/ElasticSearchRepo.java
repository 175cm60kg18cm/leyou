package com.leyou.search.repo;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticSearchRepo extends ElasticsearchRepository<Goods,Long> {
}

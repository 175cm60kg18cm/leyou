package com.leyou;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Brand;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.pojo.Book;
import com.leyou.search.pojo.Goods;
import com.leyou.search.repo.BookRepo;
import com.leyou.search.repo.ElasticSearchRepo;
import com.leyou.search.service.SearchService;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ESTest {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    ElasticSearchRepo elasticSearchRepo;
    @Autowired
    SearchService searchService;
    @Autowired
    GoodsClient goodsClient;
    @Test
    public void test(){
        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);
        Integer page=1;
        Integer rows=100;
        //分页查询结果集
        do{
            PageResult<SpuBo> result = goodsClient.querySpuByPage(null, true, page, rows);
            //获取当前页的数据
            List<SpuBo> items = result.getItems();
            System.out.println(items);
            List<Goods> goodsList = items.stream().map(spuBo -> {
                Goods goods = null;
                try {
                    goods = searchService.buildGoods(spuBo);
                    return goods;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
            elasticSearchRepo.saveAll(goodsList);
            page++;
            rows=items.size();
        }
        while (rows==100);
    }
    @Autowired
    BookRepo bookRepo;
    @Autowired
    BrandClient brandClient;
    @MockBean
    private EurekaAutoServiceRegistration eurekaAutoServiceRegistration;
    @Test
    public void testBookRepo (){
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //构建查询条件
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("all","手机"));
        //添加分页，分页页码是从0开始的
        nativeSearchQueryBuilder.withPageable(PageRequest.of(0, 20));
        //添加结果集过滤
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","skus","subTitle"},null));
        //执行查询
        Page<Goods> goodsPage = elasticSearchRepo.search(nativeSearchQueryBuilder.build());
        goodsPage.getContent().forEach(System.out::println);
    }
}

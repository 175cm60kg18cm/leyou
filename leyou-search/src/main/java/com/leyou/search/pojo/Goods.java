package com.leyou.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(type = "docs",indexName = "goods")
public class Goods {
    @Id
    private Long  id;
    @Field(type=FieldType.Text,analyzer = "ik_max_word")
    private String all;//所有需要被搜索到信息
    @Field(type = FieldType.Keyword,index = false) //keyword不会被用来创建索引
    private String subTitle;//子标题
    private Long brandId;//品牌ID
    private Long cid1;
    private Long cid2;
    private Long cid3;
    private Date createTime;//创建时间
    private List<Long> prices;//价格
    //描述sku的json数据格式
    @Field(type = FieldType.Keyword,index = false)
    private String skus;

    private Map<String,Object> specs; //可被搜索的规格参数

    public Goods() {
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", all='" + all + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", brandId=" + brandId +
                ", cid1=" + cid1 +
                ", cid2=" + cid2 +
                ", cid3=" + cid3 +
                ", createTime=" + createTime +
                ", prices=" + prices +
                ", skus='" + skus + '\'' +
                ", specs=" + specs +
                '}';
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid1() {
        return cid1;
    }

    public void setCid1(Long cid1) {
        this.cid1 = cid1;
    }

    public Long getCid2() {
        return cid2;
    }

    public void setCid2(Long cid2) {
        this.cid2 = cid2;
    }

    public Long getCid3() {
        return cid3;
    }

    public void setCid3(Long cid3) {
        this.cid3 = cid3;
    }

    public List<Long> getPrices() {
        return prices;
    }

    public void setPrices(List<Long> prices) {
        this.prices = prices;
    }

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    public Map<String, Object> getSpecs() {
        return specs;
    }

    public void setSpecs(Map<String, Object> specs) {
        this.specs = specs;
    }
}

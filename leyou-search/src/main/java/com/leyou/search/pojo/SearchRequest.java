package com.leyou.search.pojo;

public class SearchRequest {
    private String key; //搜索条件
    private Integer page;   //当前页
    private static final Integer DEFAULT_SIZE=20;//默认大小
    private static final Integer DEFAULT_PAGE=1;//默认页数

    public SearchRequest() {
    }

    public static Integer getSize() {
        return DEFAULT_SIZE;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}

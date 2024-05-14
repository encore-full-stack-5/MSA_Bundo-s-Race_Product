package com.example.bundosRace.domain;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class BaseElasticData {
    protected long domainId;
    protected int type; // 1 상품 2 블로그 3 카페 4 부동산
    protected String name; // 타이틀
    protected String description; // 컨텐츠
    protected String url ; //  http/192.168.0.1/post/2
}

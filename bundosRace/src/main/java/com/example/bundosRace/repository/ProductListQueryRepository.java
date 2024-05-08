package com.example.bundosRace.repository;

import com.example.bundosRace.domain.Product;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.bundosRace.domain.QCategory.category;
import static com.example.bundosRace.domain.QProduct.product;
import static com.example.bundosRace.domain.QSeller.seller;

@Repository
@RequiredArgsConstructor
public class ProductListQueryRepository implements ProductListCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<Product> filterProductList(Long categoryId, Integer startPrice, Integer endPrice, Long sellerId, Pageable pageable) {
        // 조건 생성 빌더쓰지 않고 안뭉치고 날리는게 있을까? 각 테이블별로 쪼갤대로 쪼개고 join해서 보낼 수 있을까?
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(startPrice != null && endPrice != null)
            booleanBuilder.and(product.price.between(startPrice,endPrice));
        if (categoryId != null)
            booleanBuilder.and(product.category.id.eq(categoryId));
        if(sellerId != null)
            booleanBuilder.and(product.seller.id.eq(sellerId));

        booleanBuilder.and(product.isDeleted.eq(false));

        List<Product>productList = jpaQueryFactory
                .selectFrom(product)
//                .where(booleanBuilder) // 1.35s
                .leftJoin(product.category, category)
                .leftJoin(product.seller, seller) //1.43s
                .where(booleanBuilder) //72ms - endPrice=10000000&categoryId=1&sellerId=3&page=1&startPrice=100
                .orderBy(product.price.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())//페이지의 갯수만큼 가져오기
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(product.count())
                .from(product)
                .leftJoin(product.category, category)
                .leftJoin(product.seller, seller)
                .where(booleanBuilder);

        return PageableExecutionUtils.getPage(productList, pageable, count::fetchOne);
    }
}

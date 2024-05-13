package com.example.bundosRace.repository.jpa;

import com.example.bundosRace.domain.Product;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            booleanBuilder.and(product.discountPrice.between(startPrice,endPrice));
        if (categoryId != null)
            booleanBuilder.and(product.category.id.eq(categoryId));
        if(sellerId != null)
            booleanBuilder.and(product.seller.id.eq(sellerId));
        booleanBuilder.and(product.isDeleted.eq(false));
        booleanBuilder.and(product.status.eq(1));

        JPAQuery<Product>query = jpaQueryFactory
                .selectFrom(product)
//                .where(booleanBuilder) // 1.35s
                .leftJoin(product.category, category)
                .leftJoin(product.seller, seller) //1.43s
                .where(booleanBuilder)
                .orderBy(productSort(pageable));//72ms - endPrice=10000000&categoryId=1&sellerId=3&page=1&startPrice=100
//                .orderBy(product.price.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())//페이지의 갯수만큼 가져오기
//                .fetch();

        List<Product> productList = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        JPAQuery<Long> count = jpaQueryFactory
                .select(product.count())
                .from(product)
                .leftJoin(product.category, category)
                .leftJoin(product.seller, seller)
                .where(booleanBuilder);

        return PageableExecutionUtils.getPage(productList, pageable, count::fetchOne);
    }

    private OrderSpecifier<?> productSort(Pageable page) {
        //서비스에서 보내준 Pageable 객체에 정렬조건 null 값 체크
        if (!page.getSort().isEmpty()) {
            //정렬값이 들어 있으면 for 사용하여 값을 가져온다
            for (Sort.Order order : page.getSort()) {
                // 서비스에서 넣어준 DESC or ASC 를 가져온다.
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                // 서비스에서 넣어준 정렬 조건을 스위치 케이스 문을 활용하여 셋팅하여 준다.
                switch (order.getProperty()){
                    case "price":
                        return new OrderSpecifier<>(direction, product.discountPrice);
                    case "createdAt":
                        return new OrderSpecifier<>(direction, product.createdAt);
                }
            }
        }
        return null;
    }
}

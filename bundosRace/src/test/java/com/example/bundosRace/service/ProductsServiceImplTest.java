package com.example.bundosRace.service;

import com.example.bundosRace.core.error.UnexpectedError;
import com.example.bundosRace.domain.*;
import com.example.bundosRace.dto.request.*;
import com.example.bundosRace.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;

// Get은 테스트안함
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService 테스트")
class ProductsServiceImplTest {
    private static final Logger log = LoggerFactory.getLogger(ProductsServiceImplTest.class);
    @Autowired
    private ProductsService productsService;

    @MockBean
    private ProductsRepository productsRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private SellerRepository sellerRepository;
    @MockBean
    private OptionGroupRepository optionGroupRepository;
    @MockBean
    private OptionRepository optionRepository;


    private final Category category = new Category(1L, "test");
    private final Seller seller = new Seller(1L, "test", LocalDateTime.now());
    private final Option dummyOption = Option.builder().id(1L).name("test").build();
    private final List<Option> dummyOptions = List.of(Option.builder().id(1L).name("test1").build(), Option.builder().id(2L).name("test2").build());
    private final OptionGroup dummyOptionGroup = OptionGroup.builder().id(1L).name("test").options(new ArrayList<>(dummyOptions)).build();
    private final Product dummyProduct = Product.builder()
            .id(1L)
            .name("test")
            .amount(1111)
            .discountRate(50)
            .optionGroups(new ArrayList<>(List.of(dummyOptionGroup)))
            .build();

    CreateProductRequest makeDummyRequest() {
        List<CreateOptionRequest> optionRequests = new ArrayList<>(List.of(
                new CreateOptionRequest("test1", 1000, 1000L),
                new CreateOptionRequest("test2", 2000, 2000L)
        ));

        List<CreateOptionGroupRequest> optionGroupRequests = new ArrayList<>(List.of(
                new CreateOptionGroupRequest(true, "test1", optionRequests),
                new CreateOptionGroupRequest(true, "test2", optionRequests)
        ));

        return new CreateProductRequest(
                1L,
                1L,
                "test",
                10000,
                1000,
                1,
                "테스트 상품테스트 상품테스트 상품테스트 상품테스트 상품",
                0,
                0,
                List.of("test1", "test2"),
                optionGroupRequests
        );
    }

    @BeforeEach
    void setup() {
        // 각 테스트 실행 전 공통 설정, Mock 객체 리턴값 설정
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        Mockito.when(sellerRepository.findById(anyLong())).thenReturn(Optional.of(seller));
        Mockito.when(productsRepository.save(Mockito.any(Product.class))).thenReturn(dummyProduct);
        Mockito.when(optionRepository.findById(anyLong())).thenReturn(Optional.of(dummyOption));
        Mockito.when(optionGroupRepository.findById(anyLong())).thenReturn(Optional.of(dummyOptionGroup));
        Mockito.when(productsRepository.findById(anyLong())).thenReturn(Optional.of(dummyProduct));
    }

    @Nested
    @DisplayName("createProduct 테스트")
    class CreateProductTest {

        @Test
        @DisplayName("정상적으로 상품을 생성한다")
        void 정상적으로_상품을_생성한다() {
            // given
            CreateProductRequest request = makeDummyRequest();

            // when
            productsService.createProduct(request);

            // then
            ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
            Mockito.verify(productsRepository).save(productCaptor.capture());
            assertThat(productCaptor.getValue().getPrice()).isEqualTo(10000);
            assertThat(productCaptor.getValue().getCategory().getName()).isEqualTo("test");
            assertThat(productCaptor.getValue().getSeller().getName()).isEqualTo("test");
        }

        @Test
        @DisplayName("카테고리가 존재하지 않으면 에러를 반환한다")
        void 카테고리가_존재하지_않으면_에러를_반환한다() {
            // given
            CreateProductRequest request = makeDummyRequest();
            Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

            // when
            Exception exception = assertThrows(UnexpectedError.IllegalArgumentException.class, () -> {
                productsService.createProduct(request);
            });

            // then
            assertThat(exception.getMessage()).contains("해당 카테고리가 존재하지 않습니다.");
        }

        @Test
        @DisplayName("판매자가 존재하지 않으면 에러를 반환한다")
        void 판매자가_존재하지_않으면_에러를_반환한다() {
            // given
            CreateProductRequest request = makeDummyRequest();
            Mockito.when(sellerRepository.findById(anyLong())).thenReturn(Optional.empty());

            // when
            Exception exception = assertThrows(UnexpectedError.IllegalArgumentException.class, () -> {
                productsService.createProduct(request);
            });

            // then
            assertThat(exception.getMessage()).contains("해당 판매자가 존재하지 않습니다.");
        }
    }

    @Test
    @DisplayName("createProductOptionGroup 테스트")
    void createProductOptionGroup() {
        // given
        Long productId = 1L;
        CreateOptionGroupRequest request = new CreateOptionGroupRequest(true, "New Option Group", List.of(
                new CreateOptionRequest("Option 1", 100, 10L),
                new CreateOptionRequest("Option 2", 200, 20L)
        ));

        // when
        productsService.createProductOptionGroup(productId, request);

        // then
        assertThat(dummyProduct.getOptionGroups().get(0).getName()).isEqualTo("test");
        assertThat(dummyProduct.getOptionGroups().get(1).getName()).isEqualTo("New Option Group");
    }

    @Test
    @DisplayName("createProductOption 테스트")
    void createProductOption() {
        // given
        Long productOptionGroupId = 1L;
        CreateOptionRequest request = new CreateOptionRequest("New Option", 300, 30L);

        // when
        productsService.createProductOption(productOptionGroupId, request);

        // then
        assertThat(dummyOptionGroup.getOptions().get(0).getName()).isEqualTo("test1");
        assertThat(dummyOptionGroup.getOptions().get(1).getName()).isEqualTo("test2");
        assertThat(dummyOptionGroup.getOptions().get(2).getName()).isEqualTo("New Option");

    }

    @DisplayName("deleteProduct 테스트")
    @Nested
    class DeleteProductTest {
        @Test
        @DisplayName("상품이 존재하지 않으면 에러를 반환한다")
        void 상품이_존재하지_않으면_에러를_반환한다() {
            // given
            Long productId = 1L;
            Mockito.when(productsRepository.findById(productId)).thenReturn(Optional.empty());

            // when
            Exception exception = assertThrows(UnexpectedError.IllegalArgumentException.class, () -> {
                productsService.deleteProduct(productId);
            });

            // then
            assertThat(exception.getMessage()).contains("해당 상품이 존재하지 않습니다.");
        }

        @Test
        @DisplayName("상품이 존재하면 삭제한다")
        void 상품이_존재하면_삭제한다() {
            // given
            Long productId = 1L;

            // when
            productsService.deleteProduct(productId);

            // then
            ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
            Mockito.verify(productsRepository).delete(productCaptor.capture());
            assertThat(productCaptor.getValue().getAmount()).isEqualTo(1111);
            assertThat(productCaptor.getValue().getName()).isEqualTo("test");
        }
    }

    @Test
    @DisplayName("createSeller 테스트")
    void createSeller() {
        // given
        CreateSellerRequest request = new CreateSellerRequest("test");

        // when
        productsService.createSeller(request);

        // then
        ArgumentCaptor<Seller> sellerCaptor = ArgumentCaptor.forClass(Seller.class);
        Mockito.verify(sellerRepository).save(sellerCaptor.capture());
        assertThat(sellerCaptor.getValue().getName()).isEqualTo("test");
    }

    @Nested
    @DisplayName("updateProduct 테스트")
    class UpdateProductTest {
        @Test
        @DisplayName("상품이 존재하지 않으면 에러를 반환한다")
        void 상품이_존재하지_않으면_에러를_반환한다() {
            // given
            Long productId = 1L;
            Mockito.when(productsRepository.findById(productId)).thenReturn(Optional.empty());
            UpdateProductRequest request = new UpdateProductRequest(
                    "change",
                    "change",
                    333,
                    333,
                    333,
                    333
            );

            // when
            Exception exception = assertThrows(UnexpectedError.IllegalArgumentException.class, () -> {
                productsService.updateProduct(productId, request);
            });

            // then
            assertThat(exception.getMessage()).contains("해당 상품이 존재하지 않습니다.");
        }

        @Test
        @DisplayName("상품이 존재하고 모든 값을 수정한다")
        void 상품이_존재하고_모든_값을_수정한다() {
            // given
            Long productId = 1L;
            UpdateProductRequest request = new UpdateProductRequest(
                    "change",
                    "change",
                    333,
                    333,
                    333,
                    333
            );
            // when
            productsService.updateProduct(productId, request);

            // then
            assertThat(dummyProduct.getPrice()).isEqualTo(333);
            assertThat(dummyProduct.getDiscountRate()).isEqualTo(333);
            assertThat(dummyProduct.getName()).isEqualTo("change");
        }

        @Test
        @DisplayName("상품이 존재하고 가격만 수정한다")
        void 상품이_존재하고_가격만_수정한다() {
            // given
            Long productId = 1L;
            UpdateProductRequest request = new UpdateProductRequest(
                    null,
                    null,
                    333,
                    null,
                    null,
                    null
            );
            // when
            productsService.updateProduct(productId, request);

            // then
            assertThat(dummyProduct.getPrice()).isEqualTo(333);
            assertThat(dummyProduct.getDiscountRate()).isEqualTo(50);
            assertThat(dummyProduct.getName()).isEqualTo("test");
        }
    }

}


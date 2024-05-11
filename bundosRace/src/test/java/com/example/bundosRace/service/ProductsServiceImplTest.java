package com.example.bundosRace.service;

import com.example.bundosRace.core.error.ExpectedError;
import com.example.bundosRace.core.error.UnexpectedError;
import com.example.bundosRace.domain.*;
import com.example.bundosRace.dto.request.*;
import com.example.bundosRace.repository.*;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

// Get은 테스트안함
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService 테스트")
class ProductsServiceImplTest {
    private static final Logger log = LoggerFactory.getLogger(ProductsServiceImplTest.class);

    @Mock
    private ProductsRepository productsRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private SellerRepository sellerRepository;
    @Mock
    private OptionGroupRepository optionGroupRepository;
    @Mock
    private OptionRepository optionRepository;
    @Mock
    private ProductListQueryRepository productOptionRepository;
    @Mock
    private RestTemplate restTemplate; // Mock RestTemplat

    @InjectMocks
    private ProductsServiceImpl productsService;

    private final Category dummyCategory = new Category(1L, "test");
    private final Seller dummySeller = new Seller(1L, "test", "test", LocalDateTime.now());
    private final Option dummyOption = Option.builder().id(1L).name("test").build();
    private final List<Option> dummyOptions = List.of(
            Option.builder().id(1L).name("test1").price(500).amount(1000L).build(),
            Option.builder().id(2L).price(500).name("test2").amount(1000L).build()
    );
    private final OptionGroup dummyOptionGroup = OptionGroup.builder()
            .id(1L).name("test").options(new ArrayList<>(dummyOptions)).build();
    private final Product dummyProduct = Product.builder()
            .id(1L)
            .name("test")
            .amount(1111)
            .discountRate(50)
            .sellCount(0)
            .category(dummyCategory)
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

    @Before("setup")
    void setup() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        optionRepository = Mockito.mock(OptionRepository.class);
        optionGroupRepository = Mockito.mock(OptionGroupRepository.class);
        sellerRepository = Mockito.mock(SellerRepository.class);
        productsRepository = Mockito.mock(ProductsRepository.class);
    }

    @Nested
    @DisplayName("createProduct 테스트")
    class CreateProductTest {

        @Test
        @DisplayName("정상적으로 상품을 생성한다")
        void 정상적으로_상품을_생성한다() {
            // given
            CreateProductRequest request = makeDummyRequest();
            Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(dummyCategory));
            Mockito.when(sellerRepository.findById(anyLong())).thenReturn(Optional.of(dummySeller));

            // when
            productsService.createProduct(request);

            // then
            ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
            verify(productsRepository).save(productCaptor.capture());
            assertThat(productCaptor.getValue().getPrice()).isEqualTo(10000);
            assertThat(productCaptor.getValue().getIsDeleted()).isEqualTo(false);
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
            Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(dummyCategory));

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
        Mockito.when(productsRepository.findById(1L)).thenReturn(Optional.of(dummyProduct));

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
        Long productId = 1L;
        Long productOptionGroupId = 1L;
        CreateOptionRequest request = new CreateOptionRequest("New Option", 300, 30L);
        Mockito.when(optionGroupRepository.findById(anyLong())).thenReturn(Optional.of(dummyOptionGroup));

        // when
        productsService.createProductOption(productId, productOptionGroupId, request);

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
            Mockito.when(productsRepository.findById(1L)).thenReturn(Optional.of(dummyProduct));

            // when
            productsService.deleteProduct(productId);

            // then
            assertThat(dummyProduct.getIsDeleted()).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("createSeller 테스트")
    void createSeller() {
        // given
        CreateSellerRequest request = new CreateSellerRequest("test", "test");

        // when
        productsService.createSeller(request);

        // then
        ArgumentCaptor<Seller> sellerCaptor = ArgumentCaptor.forClass(Seller.class);
        verify(sellerRepository).save(sellerCaptor.capture());
        assertThat(sellerCaptor.getValue().getName()).isEqualTo("test");
    }

    @Nested
    @DisplayName("sellProducts 테스트")
    class SellProductsTest {

        @Test
        @DisplayName("옵셥을 포함한 상품이 정상적으로 판매된다")
        void 옵셥을_포함한_상품이_정상적으로_판매된다() {
            // given
            SellProductsRequest request = new SellProductsRequest(1, List.of(
                    new sellProduct(1L, 1, List.of(1L, 2L)),
                    new sellProduct(2L, 1, List.of(3L))
            ));
            Long productId1 = 1L;
            Long productId2 = 2L;
            OptionGroup dummyOptionGroup1 = OptionGroup.builder().id(1L).name("test1").options(new ArrayList<>(List.of(Option.builder().id(1L).name("test1").amount(100L).build(), Option.builder().id(2L).name("test2").amount(100L).build()))).build();
            OptionGroup dummyOptionGroup2 = OptionGroup.builder().id(2L).name("test2").options(new ArrayList<>(List.of(Option.builder().id(3L).name("test3").amount(100L).build()))).build();
            Product dummyProduct1 = Product.builder().id(productId1).name("test").amount(1111).optionGroups(new ArrayList<>(List.of(dummyOptionGroup1))).build();
            Product dummyProduct2 = Product.builder().id(productId2).name("test").amount(1111).optionGroups(new ArrayList<>(List.of(dummyOptionGroup2))).build();
            Mockito.when(productsRepository.findById(productId1)).thenReturn(Optional.of(dummyProduct1));
            Mockito.when(productsRepository.findById(productId2)).thenReturn(Optional.of(dummyProduct2));

            // when
            productsService.sellProducts(request);

            // then
            assertThat(dummyProduct1.getAmount()).isEqualTo(1110);
            assertThat(dummyProduct1.getSellCount()).isEqualTo(1);
            assertThat(dummyProduct1.getOptionGroups().get(0).getOptions().get(0).getAmount()).isEqualTo(99L);
            assertThat(dummyProduct1.getOptionGroups().get(0).getOptions().get(1).getAmount()).isEqualTo(99L);
            assertThat(dummyProduct2.getOptionGroups().get(0).getOptions().get(0).getAmount()).isEqualTo(99L);
        }

        @Test
        @DisplayName("옵셥이 없는 상품이 정상적으로 판매된다")
        void 옵셥이_없는_상품이_정상적으로_판매된다() {
            // given
            Long productId = 1L;
            SellProductsRequest request = new SellProductsRequest(1, List.of(
                    new sellProduct(productId, 1, null)
            ));
            Mockito.when(productsRepository.findById(productId)).thenReturn(Optional.of(dummyProduct));

            // when
            productsService.sellProducts(request);

            // then
            assertThat(dummyProduct.getAmount()).isEqualTo(1110);
            assertThat(dummyProduct.getSellCount()).isEqualTo(1);
            assertThat(dummyProduct.getOptionGroups().get(0).getOptions().get(0).getAmount()).isEqualTo(1000L);
        }

        @Test
        @DisplayName("상품이 존재하지 않으면 에러를 반환한다")
        void 상품이_존재하지_않으면_에러를_반환한다() {
            // given
            Long productId = 100L;
            SellProductsRequest request = new SellProductsRequest(1, List.of(
                    new sellProduct(productId, 1, List.of(1L, 2L))
            ));
            Mockito.when(productsRepository.findById(productId)).thenReturn(Optional.empty());

            // when
            Exception exception = assertThrows(ExpectedError.ResourceNotFoundException.class, () -> {
                productsService.sellProducts(request);
            });

            // then
            assertThat(exception.getMessage()).contains("해당 " + productId + " 상품이 존재하지 않습니다.");
        }

        @Test
        @DisplayName("옵션이 존재하지 않으면 에러를 반환한다")
        void 옵션이_존재하지_않으면_에러를_반환한다() {
            // given
            Long productId = 1L;
            Long optionId = 6L;
            SellProductsRequest request = new SellProductsRequest(1, List.of(
                    new sellProduct(productId, 1, List.of(optionId))
            ));
            Mockito.when(productsRepository.findById(productId)).thenReturn(Optional.of(dummyProduct));

            // when
            Exception exception = assertThrows(UnexpectedError.IllegalArgumentException.class, () -> {
                productsService.sellProducts(request);
            });

            // then
            assertThat(exception.getMessage()).contains("해당 " + optionId + " 옵션이 포함된 옵션그룹이 존재하지 않습니다.");
        }


        @Test
        @DisplayName("물품수량이 부족하면 에러를 반환한다.")
        void 물품수량이_부족하면_에러를_반환한다() {
            // given
            // given
            Long productId = 1L;
            SellProductsRequest request = new SellProductsRequest(1, List.of(
                    new sellProduct(productId, 10000, null)
            ));
            Mockito.when(productsRepository.findById(productId)).thenReturn(Optional.of(dummyProduct));

            // when
            Exception exception = assertThrows(ExpectedError.ResourceNotFoundException.class, () -> {
                productsService.sellProducts(request);
            });

            // then
            assertThat(exception.getMessage()).contains("재고가 부족합니다.");
        }

        @Test
        @DisplayName("옵션수량이 부족하면 에러를 반환한다.")
        void 옵션수량이_부족하면_에러를_반환한다() {
            // given
            Long productId = 1L;
            SellProductsRequest request = new SellProductsRequest(1, List.of(
                    new sellProduct(productId, 1050, List.of(1L, 2L))
            ));
            Mockito.when(productsRepository.findById(productId)).thenReturn(Optional.of(dummyProduct));

            // when
            Exception exception = assertThrows(ExpectedError.ResourceNotFoundException.class, () -> {
                productsService.sellProducts(request);
            });

            // then
            assertThat(exception.getMessage()).contains("옵션 " + dummyOptions.get(0).getName() + " 재고가 부족합니다.");
        }
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
                    555,
                    555,
                    555,
                    555
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
                    555,
                    555,
                    555,
                    555
            );
            Mockito.when(productsRepository.findById(productId)).thenReturn(Optional.of(dummyProduct));

            // when
            productsService.updateProduct(productId, request);

            // then
            assertThat(dummyProduct.getPrice()).isEqualTo(555);
            assertThat(dummyProduct.getDiscountRate()).isEqualTo(555);
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
                    555,
                    null,
                    null,
                    null
            );
            Mockito.when(productsRepository.findById(productId)).thenReturn(Optional.of(dummyProduct));

            // when
            productsService.updateProduct(productId, request);

            // then
            assertThat(dummyProduct.getPrice()).isEqualTo(555);
            assertThat(dummyProduct.getDiscountRate()).isEqualTo(50);
            assertThat(dummyProduct.getName()).isEqualTo("test");
        }
    }

    @Nested
    @DisplayName("validateProduct 테스트")
    class ValidateProductTest {

        @Test
        @DisplayName("상품의 상태가 판매중이 아닐때 에러를 반환한다")
        void 상품의_상태가_판매중이_아닐때_에러를_반환한다() {
            // given
            Long productId = 1L;
            ValidateProductRequest request = new ValidateProductRequest(List.of(1L, 2L), 1, 1000);
            Product product = Product.builder().id(productId).status(2).amount(1000).build();
            Mockito.when(productsRepository.findById(productId)).thenReturn(Optional.of(product));

            // when
            Exception exception = assertThrows(ExpectedError.BusinessException.class, () -> {
                productsService.validateProduct(productId, request);
            });

            // then
            assertThat(exception.getMessage()).contains("판매중인 상품이 아닙니다.");
        }

        @Test
        @DisplayName("상품 재고가 부족할 때 에러를 반환한다.")
        void 상품_재고가_부족할_때_에러를_반환한다() {
            // given
            Long productId = 1L;
            ValidateProductRequest request = new ValidateProductRequest(List.of(1L, 2L), 10000, 1000);
            Product product = Product.builder().id(productId).status(1).amount(500).build();
            Mockito.when(productsRepository.findById(productId)).thenReturn(Optional.of(product));

            // when
            Exception exception = assertThrows(ExpectedError.ResourceNotFoundException.class, () -> {
                productsService.validateProduct(productId, request);
            });

            // then
            assertThat(exception.getMessage()).contains("상품 재고가 부족합니다.");
        }

        @Test
        @DisplayName("옵션 정보가 변경되어서 구매가 불가능할 때 에러를 반환한다")
        void 옵션_정보가_변경되어서_구매가_불가능할_때_에러를_반환한다() {
            // given
            Long productId = 1L;
            OptionGroup dummyOptionGroup1 = OptionGroup.builder().id(1L).name("test1").options(
                    new ArrayList<>(List.of(
                            Option.builder().id(4L).name("test1").price(100).amount(100L).build(),
                            Option.builder().id(5L).name("test2").price(100).amount(100L).build())
                    )
            ).build();
            OptionGroup dummyOptionGroup2 = OptionGroup.builder().id(2L).name("test2").options(new ArrayList<>(List.of(Option.builder().id(7L).name("test3").amount(100L).build()))).build();
            Product dummyProduct = Product.builder().id(productId).name("test")
                    .price(100)
                    .amount(1111)
                    .status(1)
                    .optionGroups(new ArrayList<>(List.of(dummyOptionGroup1, dummyOptionGroup2)))
                    .build();
            ValidateProductRequest request = new ValidateProductRequest(List.of(5L, 2L), 300, 1000);
            Mockito.when(productsRepository.findById(productId)).thenReturn(Optional.of(dummyProduct));

            // when
            Exception exception = assertThrows(ExpectedError.BusinessException.class, () -> {
                productsService.validateProduct(productId, request);
            });

            // then
            assertThat(exception.getMessage()).contains("옵션 정보가 변경되어서 구매가 불가능합니다.");
        }

        @Test
        @DisplayName("옵션 재고가 부족해서 에러를 반환한다.")
        void 옵션_재고가_부족해서_에러를_반환한다() {
            // given
            Long productId = 1L;
            ValidateProductRequest request = new ValidateProductRequest(List.of(1L, 2L), 1, 2000);
            Product product = Product.builder().id(productId).status(1).amount(10000).optionGroups(new ArrayList<>(List.of(dummyOptionGroup))).build();
            Mockito.when(productsRepository.findById(productId)).thenReturn(Optional.of(product));

            // when
            Exception exception = assertThrows(ExpectedError.ResourceNotFoundException.class, () -> {
                productsService.validateProduct(productId, request);
            });

            // then
            assertThat(exception.getMessage()).contains("옵션 " + dummyOptions.get(0).getName() + " 재고가 부족합니다.");
        }

        @Test
        @DisplayName("가격 정보가 변경되어서 에러가 반환된다")
        void 가격_정보가_변경되어서_에러가_반환된다() {
            // given
            Long productId = 1L;
            ValidateProductRequest request = new ValidateProductRequest(List.of(1L, 2L), 1, 1000);
            Product product = Product.builder().id(productId).price(1000).status(1).amount(10000).optionGroups(new ArrayList<>(List.of(dummyOptionGroup))).build();
            Mockito.when(productsRepository.findById(productId)).thenReturn(Optional.of(product));

            // when
            Exception exception = assertThrows(ExpectedError.BusinessException.class, () -> {
                productsService.validateProduct(productId, request);
            });

            // then
            assertThat(exception.getMessage()).contains("가격이 변경되어 구매불가능합니다.");
        }

        @Test
        @DisplayName("모든 값이 정상적으로 검증된다")
        void 모든_값이_정상적으로_검증된다() {
            // given
            Long productId = 1L;
            ValidateProductRequest request = new ValidateProductRequest(List.of(1L, 2L), 2000, 1);
            Product product = Product.builder().id(productId).status(1).amount(1000).price(1000).optionGroups(new ArrayList<>(List.of(dummyOptionGroup))).build();
            Mockito.when(productsRepository.findById(productId)).thenReturn(Optional.of(product));

            // when
            productsService.validateProduct(productId, request);

            // then
            assertThat(product.getAmount() > request.count()).isTrue();

        }
    }
}


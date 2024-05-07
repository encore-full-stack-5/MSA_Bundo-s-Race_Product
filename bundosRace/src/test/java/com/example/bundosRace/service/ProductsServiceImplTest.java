package com.example.bundosRace.service;

import com.example.bundosRace.domain.Category;
import com.example.bundosRace.domain.Product;
import com.example.bundosRace.dto.request.CreateProductRequest;
import com.example.bundosRace.repository.ProductsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceImpl 클래스")
class ProductsServiceImplTest {
    @Autowired
    private ProductsService productsService;

    @Test
    void getProductDetail() {
    }

    @Test
    @DisplayName("카테고리 목록을 조회한다.")
    void getCategories() {
        List<Category> categories = productsService.getCategories();
        categories.forEach((category) -> {
            System.out.println("category = " + category.getName() + " id = " + category.getId());
        });
        assertThat(categories).isNotNull();
        assertThat(categories.size()).isGreaterThan(0);
    }


    @Nested
    @DisplayName("createProduct 테스트")
    class CreateProductTest {

        @Test
        @DisplayName("UserRepository.findByUsername()을 호출하고 결과가 없으면 UserNotFoundException을 발생시킨다.")
        void 유저_아이디로_유저를_가져와서_결과가_없으면_UserNotFoundException을_발생시킨다() {
            // given
            User user = new User(1L, "whdgh9595", "a123b123", "whdgh9595", "01012341234", null);
            when(userRepository.findOneByUsername(user.getUsername())).thenReturn(Optional.empty());

            // when then
            assertThrows(UserNotFoundException.class, () -> userService.getUser("whdgh9595"));
        }

        @Test
        @DisplayName("UserRepository.findByUsername()을 호출하고 결과가 있으면 유저를 반환한다.")
        void 유저_아이디로_유저를_가져와서_결과가_있으면_유저를_반환한다() {
            // given
            User user = new User(1L, "whdgh9595", "a123b123", "whdgh9595", "01012341234", null);
            when(userRepository.findOneByUsername(user.getUsername())).thenReturn(Optional.of(user));

            // when
            User result = userService.getUser("whdgh9595");

            // then
            assertEquals(user, result);
        }
    }

    @Test
    void createProductOptionGroup() {
    }

    @Test
    void createProductOption() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void updateProduct() {
    }
}


@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceImpl 클래스")
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private UserSignUpDto user;
    @Nested
    @DisplayName("signUp 메소드는")
    class Describe_signUp {

        @BeforeEach
        void setUp() {
            user = new UserSignUpDto("jonghao", "a123b123", "whdgh9595", "01012341234", null);
        }
        @Test()
        @DisplayName("UserRepository.findByUsername()을 호출하고 결과가 있으면 UserDuplicatedException을 발생시킨다.")
        void 유저_아이디가_중복된_경우_UserDuplicatedException을_발생시킨다() {
            // given
            when(userRepository.findOneByUsername(user.getUsername())).thenReturn(Optional.of(user.toUser()));

            // when // then
            UserDuplicatedException e = assertThrows(UserDuplicatedException.class, () -> userService.signUp(user));
            assertEquals("이미 존재하는 아이디입니다.", e.getMessage());
        }

        @Test()
        @DisplayName("UserRepository.findByPhoneNumber()을 호출하고 결과가 있으면 UserDuplicatedException을 발생시킨다.")
        void 유저_핸드폰번호가_중복된_경우_UserDuplicatedException을_발생시킨다() {
            // given
            when(userRepository.findOneByPhoneNumber(user.getPhoneNumber())).thenReturn(Optional.of(user.toUser()));

            // when // then
            UserDuplicatedException e = assertThrows(UserDuplicatedException.class, () -> userService.signUp(user));
            assertEquals("이미 가입된 전화번호입니다.", e.getMessage());
        }

        @Test()
        @DisplayName("핸드폰번호와 아이디가 중복되지 않으면 유저를 생성하고 회원가입이 완료된다.")
        void 핸드폰번호와_아이디가_중복되지_않으면_유저를_생성하고_회원가입이_완료된다() {
            // given
            when(userRepository.findOneByUsername(user.getUsername())).thenReturn(Optional.empty());
            when(userRepository.findOneByPhoneNumber(user.getPhoneNumber())).thenReturn(Optional.empty());

            // when
            userService.signUp(user);

            // then
            verify(userRepository, times(1)).createUser(any());
        }
    }



    @Nested
    @DisplayName("getUserById 메소드는")
    class Describe_getUserById {
        @Test
        @DisplayName("UserRepository.findById()을 호출하고 결과가 있으면 유저를 반환한다.")
        void 유저_아이디로_유저를_가져와서_결과가_있으면_유저를_반환한다() {
            // given
            User user = new User(1L, "whdgh9595", "a123b123", "whdgh9595", "01012341234", null);
            when(userRepository.findOneById(user.getId())).thenReturn(Optional.of(user));

            // when
            User result = userService.getUserById(1L).orElseThrow();

            // then
            assertEquals(user, result);
        }
    }
}
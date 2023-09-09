package com.portfolio.portfolio_project.repository_test.jpa_repository_test.myblog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import com.portfolio.portfolio_project.domain.jpa.myblog.my_blog.MyBlog;
import com.portfolio.portfolio_project.domain.jpa.myblog.my_blog.MyBlogRepository;

@DataJpaTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MyBlogRepositoryTest {

    @Autowired
    private MyBlogRepository myBlogRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE my_blog_tb AUTO_INCREMENT = 1").executeUpdate();
        setUp();
    }

    @Test
    void selectAll() {
        List<MyBlog> myBlogs = myBlogRepository.findAll();
        Assertions.assertEquals(2, myBlogs.size());
    }

    @Test
    void selectAndUpdate() {
        Optional<MyBlog> optionalMyBlog = myBlogRepository.findById(1L);
        if (optionalMyBlog.isPresent()) {
            MyBlog myBlog = optionalMyBlog.get();
            String updatedMainTitle = "update 테스트";
            myBlog.setMainTitle(updatedMainTitle);

            MyBlog updatedMyBlog = entityManager.merge(myBlog);
            Assertions.assertEquals(updatedMainTitle, updatedMyBlog.getMainTitle());
        } else {
            Assertions.fail("Select 테스트 에러");
        }
    }

    @Test
    void insertAndDelete() {
        MyBlog newMyBlog = MyBlog.builder()
            .mainTitle("New Main Title")
            .subTitle("New Sub Title")
            .content("New Content")
            .blogImgName("New Image Name")
            .blogImgUrl("New Image URL")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        MyBlog persistedMyBlog = entityManager.persist(newMyBlog);
        Optional<MyBlog> optionalMyBlog = myBlogRepository.findById(persistedMyBlog.getId());

        if (optionalMyBlog.isPresent()) {
            entityManager.remove(persistedMyBlog);
            Optional<MyBlog> deletedMyBlog = myBlogRepository.findById(persistedMyBlog.getId());
            Assertions.assertTrue(deletedMyBlog.isEmpty());
        } else {
            Assertions.fail("Insert 테스트 에러");
        }
    }

    private void setUp(){
        MyBlog myBlog1 = MyBlog.builder()
            .mainTitle("Main Title 1")
            .subTitle("Sub Title 1")
            .content("Content 1")
            .blogImgName("Image Name 1")
            .blogImgUrl("Image URL 1")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        MyBlog myBlog2 = MyBlog.builder()
            .mainTitle("Main Title 2")
            .subTitle("Sub Title 2")
            .content("Content 2")
            .blogImgName("Image Name 2")
            .blogImgUrl("Image URL 2")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        entityManager.persist(myBlog1);
        entityManager.persist(myBlog2);
    }
}

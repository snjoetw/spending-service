package com.sj.spending.transaction.data.repo;

import com.sj.spending.transaction.data.entity.CategoryEntity;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.sj.spending.transaction.CommonTestHelper.assertCategory;
import static com.sj.spending.transaction.CommonTestHelper.createCategory;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository repository;

    @Test
    public void shouldReturnAllNonDeletedCategoriesWhenFindingAllCategories() {
        CategoryEntity firstCategory = repository.save(createCategory("Drinks", null, false));
        CategoryEntity secondCategory = repository.save(createCategory("Coffee", firstCategory.getCategoryId(), false));
        repository.delete(secondCategory);
        repository.save(createCategory("BBT", firstCategory.getCategoryId(), false));

        List<CategoryEntity> categories = Lists.newArrayList(repository.findAll());
        assertThat(categories, hasSize(2));
        assertCategory(categories.get(0), 1L, "Drinks", null, false);
        assertCategory(categories.get(1), 3L, "BBT", 1L, false);
    }

    @Test
    public void shouldReturnTrueWhenCategoryIdExistis() {
        repository.save(createCategory("Drinks", null, false));

        assertThat(repository.existsById(1L), is(true));
        assertThat(repository.existsById(2L), is(false));
    }

    @Test
    public void shouldReturnCategoryWhenFindingByExistingCategoryId() {
        repository.save(createCategory("Drinks", null, false));

        Optional<CategoryEntity> category = repository.findById(1L);
        assertCategory(category.get(), 1L, "Drinks", null, false);
    }

    @Test
    public void shouldReturnCategoryWhenFindingByNonExistingCategoryId() {
        repository.save(createCategory("Drinks", null, false));

        Optional<CategoryEntity> category = repository.findById(2L);
        assertThat(category.isPresent(), is(false));
    }

}

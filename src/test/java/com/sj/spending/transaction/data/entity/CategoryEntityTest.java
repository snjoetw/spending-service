package com.sj.spending.transaction.data.entity;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CategoryEntityTest {

    @Test
    public void shouldSetAndGetCorrectFields() {
        CategoryEntity entity = new CategoryEntity();
        entity.setCategoryId(1L);
        entity.setDeleted(true);
        entity.setName("Category Name");
        entity.setParentCategoryId(10L);

        assertThat(entity.getCategoryId(), is(1L));
        assertThat(entity.isDeleted(), is(true));
        assertThat(entity.getName(), is("Category Name"));
        assertThat(entity.getParentCategoryId(), is(10L));
    }

}

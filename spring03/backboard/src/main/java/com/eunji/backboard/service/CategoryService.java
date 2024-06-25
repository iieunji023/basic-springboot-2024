package com.eunji.backboard.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eunji.backboard.common.NotFoundException;
import com.eunji.backboard.entity.Category;
import com.eunji.backboard.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  // 카테고리를 생성하는 메서드
  public Category setCategory(String title) {
    Category cate = new Category();
    cate.setTitle(title);
    cate.setCreateDate(LocalDateTime.now());

    Category category = this.categoryRepository.save(cate);
    return category;

  }

  // free, qna..
  // 생성한 카테고리를 가져오기 위한 메서드
  public Category getCategory(String title) {
    Optional<Category> cate = this.categoryRepository.findByTitle(title);

    if(cate.isEmpty()) {    // free나 qna라는 이름의 카테고리 데이터가 없으면
       cate = Optional.ofNullable(setCategory(title));    // db Category 테이블에 해당 카테고리 값을 생성(insert)

    }
    
    if(cate.isPresent()) {  // 카테고리가 있으면
      return cate.get();

    } else 
        throw new NotFoundException("Category NOT FOUND!!");    // 발생할 일이 없음
  }
  
}

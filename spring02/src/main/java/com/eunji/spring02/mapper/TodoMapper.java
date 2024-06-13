package com.eunji.spring02.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.eunji.spring02.domain.Todo;

@Mapper
public interface TodoMapper {
  
   List<Todo> selectTodos();
  
   Todo selectTodo(int tno);
}

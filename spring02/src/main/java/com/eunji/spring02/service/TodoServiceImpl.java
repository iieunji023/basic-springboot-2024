package com.eunji.spring02.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eunji.spring02.domain.Todo;
import com.eunji.spring02.mapper.TodoMapper;

@Service
public class TodoServiceImpl implements TodoService{
  @Autowired
  TodoMapper todoMapper;

  @Override
  public List<Todo> getTodos() throws Exception {
    return todoMapper.selectTodos();

  }

  @Override
  public Todo getTodo(int tno) throws Exception {
    return todoMapper.selectTodo(tno);

  }
}

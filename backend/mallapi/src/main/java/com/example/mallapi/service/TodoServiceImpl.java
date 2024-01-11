package com.example.mallapi.service;

import com.example.mallapi.domain.Todo;
import com.example.mallapi.dto.TodoDTO;
import com.example.mallapi.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor // 생성자 자동 주입
public class TodoServiceImpl implements TodoService{

    //자동주입 대상은 final 키워드를 붙여준다.
    private final ModelMapper modelMapper;
    private final TodoRepository todoRepository;

    @Override
    public Long register(TodoDTO todoDTO) {
        Todo todo = modelMapper.map(todoDTO, Todo.class);
        Todo savedTodo = todoRepository.save(todo);
        return savedTodo.getTno();
    }
}

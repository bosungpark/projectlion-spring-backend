package mission_2.crud.service;

import mission_2.crud.model.BoardDto;

import java.util.Collection;

public interface BoardService {
    BoardDto create(BoardDto dto);
    BoardDto read(Long id);
    Collection<BoardDto> readAll();
    boolean update(Long id, BoardDto dto);
    boolean delete(Long id);
}

package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTest {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    public void shouldMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Task1",
                "Test task",
                "for test purpose only",
                "1"
        );
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertTrue(trelloCard instanceof TrelloCard);
        assertTrue(trelloCard.getClass().equals(TrelloCard.class));
        assertEquals(trelloCardDto.getName(), "Task1");
        assertEquals(trelloCard.getName(), "Task1");
    }

    @Test
    public void shouldMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard(
                "Task2",
                "Test task",
                "for test purpose only",
                "2"
        );
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertTrue(trelloCardDto instanceof TrelloCardDto);
        assertTrue(trelloCardDto.getClass().equals(TrelloCardDto.class));
        assertEquals(trelloCard.getName(), "Task2");
        assertEquals(trelloCardDto.getName(), "Task2");
    }

    @Test
    public void shouldMapToList() {
        //Given
        List<TrelloListDto> listOfDto = new ArrayList<>();
        TrelloListDto trelloListDto = new TrelloListDto(
                "1",
                "",
                true
        );
        listOfDto.add(trelloListDto);
        //When
        List<TrelloList> list = trelloMapper.mapToList(listOfDto);
        //Then
        assertTrue(listOfDto.get(0).getClass().equals(TrelloListDto.class));
        assertTrue(list.get(0).getClass().equals(TrelloList.class));
        assertEquals(listOfDto.size(), 1);
        assertEquals(list.size(), 1);
    }

    @Test
    public void shouldMapToListDto() {
        //Given
        List<TrelloList> list = new ArrayList<>();
        TrelloList trelloList = new TrelloList(
                "3",
                "List",
                false
        );
        list.add(trelloList);
        //When
        List<TrelloListDto> listOfDto = trelloMapper.mapToListDto(list);
        //Then
        assertTrue(list.get(0).getClass().equals(TrelloList.class));
        assertTrue(listOfDto.get(0).getClass().equals(TrelloListDto.class));
        assertTrue(listOfDto.get(0).getId() == list.get(0).getId());
        assertEquals(list.size(), 1);
        assertEquals(listOfDto.size(), 1);
    }

    @Test
    public void shouldMapToBoards() {
        //Given
        List<TrelloListDto> listOfDto = new ArrayList<>();
        TrelloListDto trelloListDto = new TrelloListDto(
                "1",
                "",
                true
        );
        listOfDto.add(trelloListDto);

        List<TrelloBoardDto> dtoBoards = new ArrayList<>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto(
                "TestBoard",
                "12",
                listOfDto
        );
        dtoBoards.add(trelloBoardDto);
        //When
        List<TrelloBoard> boards = trelloMapper.mapToBoards(dtoBoards);
        //Then
        assertTrue(dtoBoards.get(0).getClass().equals(TrelloBoardDto.class));
        assertTrue(boards.get(0).getClass().equals(TrelloBoard.class));
        assertTrue(dtoBoards.get(0).getId() == boards.get(0).getId());
        assertEquals(dtoBoards.size(), 1);
        assertEquals(boards.size(), 1);
        assertEquals(dtoBoards.get(0).getName(), "TestBoard");
        assertEquals(boards.get(0).getName(), "TestBoard");
    }

    @Test
    public void shouldMapToDtoBoards() {
        //Given
        List<TrelloList> list = new ArrayList<>();

        TrelloList trelloList = new TrelloList(
                "32",
                "Test",
                true
        );
        list.add(trelloList);

        List<TrelloBoard> boards = new ArrayList<>();
        TrelloBoard trelloBoard = new TrelloBoard(
                "21",
                "TestBoard",
                list
        );
        boards.add(trelloBoard);
        //When
        List<TrelloBoardDto> dtoBoards = trelloMapper.mapToBoardsDto(boards);
        //Then
        assertTrue(dtoBoards.get(0).getClass().equals(TrelloBoardDto.class));
        assertTrue(boards.get(0).getClass().equals(TrelloBoard.class));
        assertTrue(dtoBoards.get(0).getId() == boards.get(0).getId());
        assertEquals(dtoBoards.size(), 1);
        assertEquals(boards.size(), 1);
        assertEquals(dtoBoards.get(0).getId(), "21");
        assertEquals(boards.get(0).getId(), "21");
    }
}

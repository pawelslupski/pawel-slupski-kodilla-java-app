package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloServiceTest {

    @InjectMocks
    TrelloService trelloService;
    @Mock
    TrelloMapper trelloMapper;
    @Mock
    AdminConfig adminConfig;
    @Mock
    TrelloClient trelloClient;
    @Mock
    SimpleEmailService emailService;
    @Mock
    Trello trello;
    @Mock
    AttachmentByTypeDto attachmentByTypeDto;


    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        List<TrelloListDto> listOfDto = new ArrayList<>();
        TrelloListDto trelloListDto = new TrelloListDto(
                "1",
                "No name",
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
        when(trelloClient.getTrelloBoards()).thenReturn(dtoBoards);

        //When
        List<TrelloBoardDto> trelloDtoBoards = trelloService.fetchTrelloBoards();

        //Then
        assertNotNull(trelloDtoBoards);
        assertEquals(1, trelloDtoBoards.size());

        trelloDtoBoards.forEach(t -> {
            assertEquals("12", t.getId());
            assertEquals("TestBoard", t.getName());

            t.getLists().forEach(tLD -> {
                assertEquals("1", tLD.getId());
                assertEquals("No name", tLD.getName());
                assertEquals(true, tLD.isClosed());
            });
        });
    }

    @Test
    public void shouldCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "trelloCard",
                "center",
                "this is an example trelloCard",
                "1"
        );
        when(trello.getBoard()).thenReturn(5);
        when(trello.getCard()).thenReturn(2);
        when(attachmentByTypeDto.getTrello()).thenReturn(trello);

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "9",
                "test Card",
                "http://test.com",
                new CreatedTrelloCardBadgeDto(
                        4,
                        new AttachmentByTypeDto(
                                new Trello(trello.getBoard(), trello.getCard())))
        );

        when(adminConfig.getAdminMail()).thenReturn("admin_mail@wp.pl");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto cardDto = trelloService.createTrelloCard(trelloCardDto);

        //Then
        verify(adminConfig, times(1)).getAdminMail();
    }
}

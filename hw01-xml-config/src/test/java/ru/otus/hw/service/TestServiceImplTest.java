package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.mockito.Mockito.*;

class TestServiceImplTest {

    @Test
    void executeTest_shouldPrintQuestions() {
        IOService ioService = mock(IOService.class);
        QuestionDao questionDao = mock(QuestionDao.class);

        when(questionDao.findAll()).thenReturn(List.of(
                    new Question("What is 2 + 2?",
                        List.of(
                        new Answer("3", false),
                        new Answer("4", true),
                        new Answer("5", false)
                ))
        ));

        TestServiceImpl service = new TestServiceImpl(ioService, questionDao);
        service.executeTest();
        
        verify(ioService).printFormattedLine("Question: %s", "What is 2 + 2?");
        verify(ioService).printLine("Choose the correct answer:");
        verify(ioService).printFormattedLine("%d. %s", 1, "3");
        verify(ioService).printFormattedLine("%d. %s", 2, "4");
        verify(ioService).printFormattedLine("%d. %s", 3, "5");
    }
}

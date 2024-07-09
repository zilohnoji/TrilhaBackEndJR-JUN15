package com.donatoordep.rg.code.entities;

import com.donatoordep.rg.code.enums.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Task - Domain Class Test")
public class TaskTest {

    private static final String TITLE = "Lavar a louça";
    private static final String CONTENT = "Pegarei o prato e passarei uma esponja";

    @Test
    @DisplayName("Creation test of 'COMPLETED' status")
    void ofCompletedShouldReturnTaskWithStatusOnCompleted() {
        // Arrange - Instanciar objetos necessários || // Act - Executar ações necessárias
        Task entity = Task.ofCompleted("Lavar a louça", "Pegarei o prato e passarei uma esponja");

        // Assert - Declarar o que deve acontecer
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(TITLE, entity.getTitle(),
                () -> String.format("The title expected is %s, but Task return %s", TITLE, entity.getTitle()));

        Assertions.assertEquals(CONTENT, entity.getContent(),
                () -> String.format("The title expected is %s, but Task return %s", CONTENT, entity.getContent()));

        Assertions.assertEquals(TaskStatus.COMPLETED, entity.getStatus(),
                () -> String.format("The status expected is %s, but Task return %s", TaskStatus.COMPLETED, entity.getStatus())
        );
    }

    @Test
    @DisplayName("Creation test of 'PROGRESS' status")
    void ofProgressShouldReturnTaskWithStatusOnCompleted() {
        // Arrange - Instanciar objetos necessários || // Act - Executar ações necessárias
        Task entity = Task.ofProgress(TITLE, CONTENT);

        // Assert - Declarar o que deve acontecer
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(TITLE, entity.getTitle(),
                () -> String.format("The title expected is %s, but Task return %s", TITLE, entity.getTitle()));

        Assertions.assertEquals(CONTENT, entity.getContent(),
                () -> String.format("The title expected is %s, but Task return %s", CONTENT, entity.getContent()));

        Assertions.assertEquals(TaskStatus.PROGRESS, entity.getStatus(),
                () -> String.format("The status expected is %s, but Task return %s", TaskStatus.PROGRESS, entity.getStatus())
        );
    }

    @Test
    @DisplayName("Creation test of 'QUIT' status")
    void ofQuitShouldReturnTaskWithStatusOnCompleted() {
        // Arrange - Instanciar objetos necessários || // Act - Executar ações necessárias
        Task entity = Task.ofQuit(TITLE, CONTENT);

        // Assert - Declarar o que deve acontecer
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(TITLE, entity.getTitle(),
                () -> String.format("The title expected is %s, but Task return %s", TITLE, entity.getTitle()));

        Assertions.assertEquals(CONTENT, entity.getContent(),
                () -> String.format("The title expected is %s, but Task return %s", CONTENT, entity.getContent()));

        Assertions.assertEquals(TaskStatus.QUIT, entity.getStatus(),
                () -> String.format("The status expected is %s, but Task return %s", TaskStatus.QUIT, entity.getStatus())
        );
    }
}
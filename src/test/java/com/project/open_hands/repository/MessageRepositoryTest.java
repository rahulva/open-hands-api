package com.project.open_hands.repository;

import com.project.open_hands.Constants;
import com.project.open_hands.entity.Message;
import com.project.open_hands.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase
class MessageRepositoryTest {

    @Autowired
    private MessageRepository repository;

    @Test
    void findByFromEmail() {
        Message entity = new Message();
        entity.setName("FName");
        entity.setFromEmail("test_from@gmail.com");
        entity.setToEmail("test_from@gmail.com");
        entity.setTelephoneNo("+4919212221222");
        entity.setMessageText("");
        entity.setId("");
        entity.setRequestTime(LocalDateTime.parse("2023-09-09 17:08:36.163538", Constants.dateTimeFormatter));

        Message message = repository.save(entity);
        assertThat(message).isNotNull();
        System.out.println("message = " + message);

        List<Message> fromDb = repository.findByFromEmail(message.getFromEmail());
        assertThat(message).isEqualTo(fromDb.get(0));


        List<Message> toDb = repository.findByToEmail(message.getToEmail());
        assertThat(message).isEqualTo(toDb.get(0));
    }
}
package shakepost.shake.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import shakepost.shake.domain.Message;
import shakepost.shake.domain.Views;
import shakepost.shake.repo.MessageRepo;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepo messageRepo;

    @Autowired
    MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list() {
        return messageRepo.findAll();
    }

    @JsonView(Views.FullName.class)
    @GetMapping("{id}")
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message createMessage(@RequestBody Message newMessage) {
        newMessage.setCreationDate(LocalDateTime.now());
        messageRepo.save(newMessage);
        System.out.println("сохранили сообщение: " + newMessage);

        return newMessage;
    }

    @PutMapping("{id}")
    public Message changeMessage(@PathVariable("id") Message messageFromDb, @RequestBody Message message) {
        //копируем в объект из БД, все свойства, кроме id
        BeanUtils.copyProperties(message, messageFromDb, "id");
        messageRepo.save(messageFromDb);

        System.out.println("изменили сообщение: " + messageFromDb);
        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable("id") Message message) {
        System.out.println("Удалили с id: " + message);
        messageRepo.delete(message);
    }

    @MessageMapping("/changeMessage")
    @SendTo("/topic/activity")
    public Message sendMessage(Message message) throws Exception {
        return messageRepo.save(message);
    }

}

package shakepost.shake.controller;

import org.springframework.web.bind.annotation.*;
import shakepost.shake.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("message")
public class MainController {
    private int count = 4;
    ArrayList<HashMap<String, String>> messages = new ArrayList<>() {{
        add(new HashMap<>() {{
            put("id", "1");
            put("text", "Spring");
        }});
        add(new HashMap<>() {{
            put("id", "2");
            put("text", "is");
        }});
        add(new HashMap<>() {{
            put("id", "3");
            put("text", "wonderful");
        }});
    }};

    @GetMapping
    public ArrayList<HashMap<String, String>> list() {
        return messages;
    }

    @GetMapping("{id}")
    public HashMap<String, String> getOne(@PathVariable String id) {
        return getMessage(id);
    }

    private HashMap<String, String> getMessage(String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst().orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public HashMap<String, String> createMessage(@RequestBody HashMap<String, String> newMessage) {
        newMessage.put("id", String.valueOf(count++));
        messages.add(newMessage);
        System.out.println("сохранили сообщение: " + newMessage.get("id"));

        return newMessage;
    }

    @PutMapping("{id}")
    public HashMap<String, String> changeMessage(@PathVariable String id, @RequestBody HashMap<String, String> message) {
        HashMap<String, String> messageFromDb = getMessage(id);
        messageFromDb.putAll(message);

        messageFromDb.put("id", id);
        System.out.println("изменили сообщение: " + messageFromDb.get("id"));
        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable int id) {
        System.out.println("Удалили с id: " + id);
        messages.remove(getMessage(String.valueOf(id)));
    }

}

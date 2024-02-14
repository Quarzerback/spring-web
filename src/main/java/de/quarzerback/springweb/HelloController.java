package de.quarzerback.springweb;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HelloController {
    List<Message> messageList = new ArrayList<>();
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages(){
        return messageList;
    }

    @PostMapping("/messages")
    public List<Message> addMessage(@RequestBody Message message){
        messageList.add(message);
        return messageList;
    }

    @DeleteMapping("/messages/{id}")
    public List<Message> removeMessage(@PathVariable String id) {
        Message removalMessage = getMessageById(id);
        if (removalMessage != null) {
            messageList.remove(removalMessage);
        }
        return messageList;
    }

    private Message getMessageById(String id) {
        return messageList.stream()
                .filter(message -> id.equals(message.getId()))
                .findFirst()
                .orElse(null);
    }
}

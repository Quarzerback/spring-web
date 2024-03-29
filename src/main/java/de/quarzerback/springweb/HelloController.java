package de.quarzerback.springweb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    @GetMapping("/messages/search")
    public Message getMessageByParam(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String message,
            @RequestParam(required = false) String id
    ){
        for (Message msg : messageList){
            if (msg.getId().equals(id)) {
                return msg;
            }
        }
        for (Message msg : messageList){
            if (msg.getName().equals(name)) {
                return msg;
            }
        }
        for (Message msg : messageList){
            if (msg.getMessage().equals(message)) {
                return msg;
            }
        }
        throw new NoSuchElementException("This message doesn't exist.");
    }

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
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

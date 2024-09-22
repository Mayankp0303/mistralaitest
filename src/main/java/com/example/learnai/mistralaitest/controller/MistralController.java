package com.example.learnai.mistralaitest.controller;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.learnai.mistralaitest.chatmodel.Mistralmodel;
import com.example.learnai.mistralaitest.chatmodel.Response;
import com.example.learnai.mistralaitest.chatmodel.querymodel;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import reactor.core.publisher.Flux;

@Controller
public class MistralController {

    @Autowired
    private Mistralmodel chatModel;

    @GetMapping("/message") 
    public String getMessage(@RequestParam String query) {
        // Create a Prompt from the query parameter
        Prompt prompt = new Prompt(query);
        
        // Call the chat model and get the response
        ChatResponse response = chatModel.call(prompt);
        System.out.println("Response: " + response.getResult().getOutput().getContent());
        

        
        return response.getResult().getOutput().getContent() ;
    }

    @PostMapping("/mistralAIgetResponse") 
    public String getresponseFromMistralAi(@ModelAttribute querymodel query,HttpSession session) {
        // Create a Prompt from the query parameter
        Prompt prompt = new Prompt(query.getQuestion());
        
        // Call the chat model and get the response
        ChatResponse response = chatModel.call(prompt);
        System.out.println("Response: " + response);   
        
        Response message = Response.builder().content(response.getResult().getOutput().getContent()).build();
        session.setAttribute("message", message);
        
        
        return "redirect:/index";
    }


    @GetMapping("/message2") // Updated path to be more RESTful
    public Flux<ChatResponse> getMessageflux(@RequestParam String query) {
        // Create a Prompt from the query parameter
        Prompt prompt = new Prompt(query);
        
        // Call the chat model and get the response
        Flux<ChatResponse> response = chatModel.stream(prompt);

        // Log the response for debugging
        System.out.println("Response: " + response);

        // Return the response as a String, adjust as needed based on your response structure
        return response;
    }


    @RequestMapping("/index") 
    public String gethome(org.springframework.ui.Model model) {
        querymodel query = new querymodel();
        model.addAttribute("query", query);
        return "home";
    }


    @GetMapping("/index") 
    public String displayMessage(org.springframework.ui.Model model, HttpSession session) {
        querymodel query = new querymodel();
        model.addAttribute("query", query);

        // Retrieve the message from the session if it exists
        Response message = (Response) session.getAttribute("message");
        if (message != null) {
            model.addAttribute("message", message.getContent());
            System.out.println("test message value"+ message.getContent());
            session.removeAttribute("message"); // Clear it after fetching
            System.out.println("Inside new index");
        }

        return "home"; // Return the home view
    }


}

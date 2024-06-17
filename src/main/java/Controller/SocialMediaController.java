package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::createAccount);
        app.post("/login", this::verifyLogin);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::retrieveAllMessages);
        app.get("/messages/{message_id}", this::retrieveMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}", this::updateMessageById);
        app.get("/accounts/{account_id}/messages", this::retrieveAllMessagesByUser);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void createAccount(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        if(account.getPassword().length()<4 || account.getUsername()==""){
            context.status(400);
        }
        else if(accountService.verifyUserExistsByUser(account.getUsername())){
            context.status(400);
        }
        else{
            Account newAccount = accountService.createAccount(account);
            context.json(mapper.writeValueAsString(newAccount));
        }
    }

    private void verifyLogin(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        if(accountService.verifyUserCreds(account.getUsername(), account.getPassword())){
            Account accountRetrieved = accountService.retrieveUserByUser(account.getUsername());
            context.json(mapper.writeValueAsString(accountRetrieved));
        }
        else{
            context.status(401);
        }
    }

    private void createMessage(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        if(message.getMessage_text()==null){
            context.status(400);
        }
        else if(message.getMessage_text()==""||message.getMessage_text().length()>255){
            context.status(400);
        }
        else if(accountService.verifyUserExistsById(message.getPosted_by())){
            Message newmMessage = messageService.createMessage(message);
            context.json(mapper.writeValueAsString(newmMessage));
        }
        else{
            context.status(400);
        }
    }
    private void retrieveAllMessages(Context context) {
        context.json(messageService.retrieveAllMessages());
    }
    private void retrieveMessageById(Context context) {
        if(!messageService.verifyMessageIdExists(context.pathParam("message_id"))){
            context.status(200);
        }
        else{
            context.json(messageService.retrieveMessageById(context.pathParam("message_id")));
        }
    }
    private void deleteMessageById(Context context) throws JsonProcessingException{
        if(!messageService.verifyMessageIdExists(context.pathParam("message_id"))){
            context.status(200);
        }
        else{
            ObjectMapper mapper = new ObjectMapper();
            Message message = messageService.retrieveMessageById(context.pathParam("message_id"));
            messageService.deleteMessageById(context.pathParam("message_id"));
            context.json(mapper.writeValueAsString(message));
        }
    }
    private void updateMessageById(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        String msg = message.getMessage_text();
        if(!(msg.length()<=255 && msg.length()>0)){
            context.status(400);
        }
        else if(!messageService.verifyMessageIdExists(context.pathParam("message_id"))){
            context.status(400);
        }
        else{
            Message newMessage = messageService.updateMessageById(context.pathParam("message_id"), msg);
            context.json(mapper.writeValueAsString(newMessage));
        }
    }
    private void retrieveAllMessagesByUser(Context context) {
        List<Message> messages = messageService.retrieveAllMessagesByUser(context.pathParam("account_id"));
        context.json(messages);
    }


}
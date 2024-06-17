package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {

    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message){
        return messageDAO.createMessage(message);
    }

    public List<Message> retrieveAllMessages(){
        return messageDAO.retrieveAllMessages();
    }

    public boolean verifyMessageIdExists(String id){
        return messageDAO.verifyMessageIdExists(id);
    }

    public Message retrieveMessageById(String id){
        return messageDAO.retrieveMessageById(id);
    }

    public void deleteMessageById(String id){
        messageDAO.deleteMessageById(id);
    }

    public Message updateMessageById(String id, String messageString){
        return messageDAO.updateMessageById(id, messageString);
    }

    public List<Message> retrieveAllMessagesByUser(String id){
        return messageDAO.retrieveAllMessagesByUser(id);
    }
}

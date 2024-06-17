package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    public List<Message> retrieveAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "select * from message";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public List<Message> retrieveAllMessagesByUser(String id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "select * from message where posted_by = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
            return messages;

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public boolean verifyMessageIdExists(String id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "select * from message where message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                return true;
            }
        return false;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Message retrieveMessageById(String id){
        Connection connection = ConnectionUtil.getConnection();
        Message message = new Message();
        try {
            //Write SQL logic here
            String sql = "select * from message where message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }
        return message;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return message;
    }

    public void deleteMessageById(String id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "delete * from message where message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public Message updateMessageById(String id, String messageString){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "update message set message_text = ? where message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, messageString);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();

            return retrieveMessageById(id);

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return new Message();
    }

    public Message createMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            //Write SQL logic here
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while(rs.next()){
                return new Message(rs.getInt(1), message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
            return new Message();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return new Message();
    }

}

package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {

    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public boolean verifyUserExistsByUser(String user){
        return accountDAO.verifyUserExistsByUser(user);
    }

    public boolean verifyUserExistsById(int id){
        return accountDAO.verifyUserExistsById(id);
    }

    public boolean verifyUserCreds(String user, String pass){
        return accountDAO.verifyUserCreds(user, pass);
    }

    public Account retrieveUserByUser(String user){
        return accountDAO.retrieveUserByUser(user);
    }

    public Account createAccount(Account account){
        return accountDAO.createAccount(account);
    }


    
}

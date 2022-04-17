package com.andi.DungeonExplorer.homeUI.Account;

/**
 * Created by Ntcarter on 11/7/2017.
 */

/**
 * This class keeps track of all the user information once the user has logged in (methods should be self explanatory)
 */
public class Account {

    private String userName;
    private String userPassword;
    private int userID;
    private int adminID;

    /**
     * Creates a new account that stores the users information.
     */
    public Account(){
        userName = "";
        userPassword = "";
        userID = -1;
        adminID = 0;
    }


    /**
     * Changes the accounts username
     * @param name
     */
    public void SetUserName(String name){
        userName = name;
    }

    /**
     * Changes the accounts password
     * @param password
     */
    public void SetUserPassword(String password){
        userPassword = password;
    }

    /**
     * Changes the accounts userID
     * @param id
     */
    public void SetUserID(int id){
        userID = id;
    }

    /**
     * Changes the accounts adminID
     * @param id
     */
    public void SetAdminID(int id){
        adminID = id;
    }


    /**
     * Changes all of the accounts parameters at the same time.
     * @param name
     * @param password
     * @param id
     * @param aID
     */
    public void SetFullAccount(String name, String password, int id, int aID){
        userName = name;
        userPassword = password;
        userID = id;
        adminID = id;
    }

    /**
     * Gets the accounts user name.
     * @return
     */
    public String GetUserName(){
        return userName;
    }

    /**
     * Gets the accounts password.
     * @return
     */
    public String GetUserPassword(){
        return userPassword;
    }

    /**
     * Simulates the account logging out by setting all the values to the logged out values
     */
    public void LogOut(){
        userName = "";
        userPassword = "";
        userID = -1;
        adminID = 0;
    }

    /**
     * Returns the accounts user ID
     * @return
     */
    public int GetUserID(){
        return userID;
    }

    /**
     * Returns the accounts Admin ID
     * @return
     */
    public int GetAdminID(){
        return  adminID;
    }
}

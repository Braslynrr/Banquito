/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Login;

import Logic.User;

/**
 *
 * @author gaira
 */
public class Model {
    
    User current;

    public Model() {
        this.reset();
    }
    
    public void reset(){
        setCurrent(new User());        
    }
    
    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }
   
    
    
    
    
    
}

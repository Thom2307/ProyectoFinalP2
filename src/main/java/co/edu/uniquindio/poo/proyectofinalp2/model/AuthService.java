
package model;
public class AuthService {
    private static AuthService instance;
    private AuthService(){}
    public static AuthService getInstance(){
        if(instance==null) instance=new AuthService();
        return instance;
    }
}

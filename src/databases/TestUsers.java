
package databases;

import dao.UserDAO;

public class TestUsers {

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        dao.displayUsers();
    }
}

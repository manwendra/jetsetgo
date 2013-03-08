/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jetsetgo;
/**
 *
 * @author megh
 */
public class JetSetGo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        MySql dao = new MySql();
        dao.readDataBase();
    }
}

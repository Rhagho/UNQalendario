import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.Administracion;
import pageobjects.Home;
import pageobjects.Login;

public class AdministracionTest extends BaseTest {

    Administracion administracionPage;

    @BeforeMethod
    public void newAdministration(){
        administracionPage = new Administracion(driver);
    }

    @AfterClass
    public void deleteAllUsers(){
        MongoConnection connection = new MongoConnection();
        connection.deleteAllSubjects();
    }


    @Test
    public void cuandoCreoUnaNuevaMateriaApareceEnElMenu(){
        String nombreMateria = "DummyMateria1";
        administracionPage.crearMateria(nombreMateria);
        Assert.assertTrue(administracionPage.estaLaMateriaEnElMenu(nombreMateria));
    }

    @Test
    public void cuandoRegistroAUnProfesorEnUnaMateriaLeApareceLaMateriaEnHomePage(){
        String username = "fede";
        String password = "fede";
        String nombreMateria = "DummyMateria2";
        registerNewUser(username, password, "Federico", "Sawady");
        driver.get("http://localhost:3000/administracion");
        administracionPage.crearMateria(nombreMateria);
        administracionPage.agregarAdministradorAMateria(username,nombreMateria);
        Login loginPage = new Login(driver);
        Home homePage = loginPage.loginWith(username,password);
        Assert.assertTrue(homePage.isAdministradorBadgeVisible());
    }
}

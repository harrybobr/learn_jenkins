import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import io.qameta.allure.Description;
import net.datafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AccountParamTest {
  private final String name;

  private final boolean expectedResult;

  public AccountParamTest(String name, boolean expectedResult) {
    this.name = name;
    this.expectedResult = expectedResult;
  }

  @Parameterized.Parameters(name = "Проверяемая строка:\"{0}\", ожидаемый результат - {1}")
  public static Object[][] getNameData() {
    Faker faker = new Faker();
    return new Object[][]{
        //Позитивная проверка
        {faker.name().firstName()+" "+faker.name().lastName(), true},
        //Количество символов 3 - true
        {faker.text().text(1)+" "+faker.text().text(1), true},
        //Количество символов 19 - true
        {faker.text().text(9)+" "+faker.text().text(9), true},
        //Количество символов 4 - true
        {faker.text().text(2)+" "+faker.text().text(1), true},
        //Количество символов 20 - false
        {faker.text().text(10)+" "+faker.text().text(9), false},
        //Количество символов 25 - false
        {faker.text().text(12)+" "+faker.text().text(12), false},
        //Пробел в начале строки - false
        {faker.text().text(10)+" "+faker.text().text(9), false},
        //Пробел в конце строки - false
        {faker.name().firstName()+faker.name().lastName()+" ", false},
        //Два пробела в строке - false
        {" "+faker.name().firstName()+faker.name().lastName(), false},
        //Четыре пробела в строке - false
        {faker.text().text(3)+" "+faker.text().text(2)+" "+faker.text().text(3)+" "
            +faker.text().text(2)+" "+faker.text().text(3), false}
    };
  }

  @Test
  @Description("Проверка строки")
  public void checkNameTest() {
    Account account = new Account(name);
    assertThat(account.checkNameToEmboss(), equalTo(expectedResult));
  }
}

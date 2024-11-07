package tests;

import dataproviders.DataProviderBoards;
import dto.BoardDTO;
import dto.UserDTO;
import manader.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.BoardsPage;
import pages.HomePage;
import pages.PersonalBoardPage;

import java.lang.reflect.Method;
import java.util.Random;

public class BoardsTests extends ApplicationManager {

    UserDTO user = UserDTO.builder()
            .email("z0559882272@gmail.com")
            .password("Mmar123456$")
            .build();
    BoardsPage boardsPage = new BoardsPage(getDriver());
    @BeforeMethod
    public void loginBeforeBoards() {
        HomePage homePage = new HomePage(getDriver());
        boardsPage= homePage.clickBtnLogin()
                .typeEmail(user)
                .typePassword(user);
    }
    @Test
    public void createBoardPositive(Method method){
        int i =new Random().nextInt(1000)+1000;
        BoardDTO board=BoardDTO.builder()
                .boardTitle("QA44_"+i)
                .build();
        logger.info(method.getName()+" test starts with bord title--->  "+board.getBoardTitle());

       // HomePage homePage=new HomePage(getDriver());
              Assert.assertTrue( //homePage.clickBtnLogin()
                       // .typeEmail(user)
                       // .typePassword(user)
                     boardsPage.typeBoardTitle(board)
                        .clickBtnCreateSubmitPositive()
                        .isTextInElementPresent_nameBoard(board.getBoardTitle()));


    }
    @Test
    public void createBoardNegative() {

        BoardDTO board = BoardDTO.builder()
                .boardTitle("   ")
                .build();

       // HomePage homePage = new HomePage (getDriver ());
        Assert.assertFalse(//homePage.clickBtnLogin()
                       // . typeEmail(user)
                        //.typePassword (user)
                      boardsPage  .typeBoardTitle(board)
                        .clickBtnCreateSubmitNegative ()
                .isElementClickable_btnCreateSubmit(),"element is clickable");

    }

    @Test(dataProvider = "DPFile_deleteBoardPositiveTest", dataProviderClass = DataProviderBoards.class)
    public void deleteBoardPositiveTest(BoardDTO board) {
//        int i = new Random().nextInt(1000) + 1000;
//
//        BoardDTO board = BoardDTO.builder()
//                .boardTitle("QA44-" + i)
//                .build();

        PersonalBoardPage personalBoardPage = boardsPage
                .typeBoardTitle(board)
                .clickBtnCreateSubmitPositive();

        if (personalBoardPage.isTextInElementPresent_nameBoard(board.getBoardTitle())) {
            Assert.assertTrue(personalBoardPage.deleteBoard(board)
                    .isTextPopUpPresent());
        } else {
            Assert.fail("board isn`t create");
        }

    }
    @Test
    public void deleteAllBoard() {
        pause(3000);
        List<WebElement> listBoars = getDriver().findElements(
                By.xpath("//li[@class='boards-page-board-section-list-item']"));
        System.out.println("size list --> " + listBoars.size());
        //boardsPage.clickElement2ListBoards().deleteBoard();
        for (int i = 0; i < listBoars.size()-2; i++) {
            boardsPage.clickElement2ListBoards().deleteBoard();
            pause(5000);
        }
    }
}

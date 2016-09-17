package com.example.leejunbeom.test;

import com.example.leejunbeom.bookMarker.network.jericho.Jericho;
import com.example.leejunbeom.bookMarker.model.pojo.Book;
import com.example.leejunbeom.bookMarker.util.html.HtmlBookParser;
import com.example.leejunbeom.bookMarker.util.html.HtmlParser;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import rx.Observable;

import static org.junit.Assert.*;

/**
 * Created by Jun on 16. 3. 22..
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class jerichoTest {

    Jericho jericho;
    HtmlParser htmLBookparser;
    @Before
    public void setUp(){
        htmLBookparser=new HtmlBookParser();
        jericho = new Jericho(htmLBookparser);
    }

    @After
    public void tearDown(){

    }
    @Test
    public void should_htmlparseintobookobject_test(){
        Observable<Book> observableBook=jericho.postBook("cid=5241729");
        assertNotNull("htmlToString are null", observableBook);
        //Book book = (Book)htmLBookparser.sourceToObject(htmltoString);
        //assertEquals("Book{dataType='국내서단행본', titileAuthorsType='양안시와사시/진가헌,최혜정,이준범편저', editionStateMent='개정3판', formMatters='null', publicationMatter='서울:대학서림,2011', generalAspects='색인수록<br/>', isbn='9788980168866', symbolicRequest='617.762진가헌양3'}",book.toString());
    }

    @Test
    public void shoud_string_html_work_test(){
        String html="<html> <head> <title>My Title</title> </head> <body> <p>Hello World</p> </body> </html>";
        Source source = new Source(html);
        List<Element> headList=source.getAllElements(HTMLElementName.HEAD);
        System.out.print(headList.toString());
    }

}

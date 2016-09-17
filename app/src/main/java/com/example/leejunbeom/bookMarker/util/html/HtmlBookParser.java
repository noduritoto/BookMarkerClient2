package com.example.leejunbeom.bookMarker.util.html;

import com.example.leejunbeom.bookMarker.model.pojo.Book;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;


import java.util.List;

/**
 * Created by Jun on 16. 3. 22..
 */
public class HtmlBookParser implements HtmlParser{
    public HtmlBookParser() {
    }

    @Override
    public Object sourceToObject(Source htmltoString) {

        Element metaDataBodyInfoList= htmltoString.getElementById("metaDataBody");
        List<Element> ElmentList=metaDataBodyInfoList.getAllElements(HTMLElementName.TR);
        Book book = new Book();
        for (Element elemet:ElmentList) {
            Segment bookAttribute=elemet.getAllElements().get(1).getContent();
            Segment bookAttributeValue=elemet.getAllElements().get(2).getContent();
            String bookAttributeValueString=bookAttributeValue.toString().replaceAll("\\s+", "");
            this.setBookAttribute(book,bookAttribute.toString().replaceAll("\\s+",""),bookAttributeValueString);
        }

        return book;
    }

    public void setBookAttribute(Book book, String attribute, String bookAttributeValueString){
/*
        switch (attribute){
            case "자료유형:":
                book.setDataType(bookAttributeValueString);
                break;
            case "서명/저자:":
                book.setTitileAuthorsType(bookAttributeValueString);
                break;
            case "판사항:":
                book.setEditionStateMent(bookAttributeValueString);
                break;
            case "발행사항:":
                book.setPublicationMatter(bookAttributeValueString);
                break;
            case "일반사항:":
                book.setGeneralAspects(bookAttributeValueString);
                break;
            case "ISBN:":
                book.setIsbn(bookAttributeValueString);
                break;
            case "청구기호:":
                book.setSymbolicRequest(bookAttributeValueString);
                break;
            default:
                break;
        }
*/
    }
}

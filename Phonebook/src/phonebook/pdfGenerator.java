package phonebook;

import com.itextpdf.text.BaseColor;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.TabStop;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPHeaderCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

/**
 * @author Ádám
 */
public class pdfGenerator {

    protected void pdfGenerator(String file_name, String text, ObservableList data) { //,
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file_name + ".pdf"));
            document.open();
            Image imagepdf = Image.getInstance(getClass().getResource("/pdfgeneratoriconblue.png"));
            imagepdf.scaleToFit(200, 89);
            imagepdf.setAbsolutePosition(255f, 750f);
            imagepdf.setSpacingAfter(250f);
            document.add(imagepdf);
            
            
            //szöveges tartalom hozzáadása 
            document.add(new Paragraph("\n\n\n\n\n\n\n" + text, FontFactory.getFont("Century Gothic", 
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));

            float[] columnWidth = {3, 3, 4};
            PdfPTable table = new PdfPTable(columnWidth);
            table.setWidthPercentage(100);
            //felső (header) hozzáadása a táblázathoz
            PdfPCell cell = new PdfPCell(new Phrase("Kontaktlista",FontFactory.getFont("Century Gothic", 14, BaseColor.WHITE)));
            
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor( new BaseColor(46, 78, 130));
            
            
            
            
            //a cell hánny oszlopot szélességű legyen (pl mint a celleegyesítés)
            cell.setColspan(3);
            table.addCell(cell);

            table.getDefaultCell().setBackgroundColor( new BaseColor(110, 166, 194));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            
            
            table.addCell(new Phrase("Firstname",FontFactory.getFont("Century Gothic", 12, BaseColor.WHITE)));
            table.addCell(new Phrase("Lastname",FontFactory.getFont("Century Gothic", 12, BaseColor.WHITE)));
            table.addCell(new Phrase("E-mail address",FontFactory.getFont("Century Gothic", 12, BaseColor.WHITE)));
//            table.addCell("Lastname");
//            table.addCell("E-mail address");
            table.setHeaderRows(1);

            table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setMinimumHeight(15f);

            for (int counter = 1; counter <= data.size(); counter++) {
//                Person actualPerson = (Person)task.get();
                Person actualPerson = (Person)data.get(counter-1);
                
                table.addCell(actualPerson.getFirstName());
                table.addCell(actualPerson.getLastName());
                table.addCell(actualPerson.getEmailAddress());

            }
            document.add(table);
            Chunk signature = new Chunk("\n\n Generálva a telefonkönyv segítségével. \n\n\n");
            Paragraph base = new Paragraph(signature);
            document.add(base);
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();

    }

}

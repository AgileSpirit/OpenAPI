package com.agile.spirit.openapi.utils;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.agile.spirit.openapi.domain.Note;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenerator {

    public static ByteArrayOutputStream generateNotesExport(final List<Note> notes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        try {
            PdfWriter.getInstance(document, baos);
            document.open();
            document.addTitle("OpenAPI - iText PDF generation demo");
            for (Note note : notes) {
                Paragraph paragraph = new Paragraph();
                paragraph.add(new Phrase(note.getTitle(), new Font(FontFamily.HELVETICA, 14)));
                paragraph.add(Chunk.NEWLINE);
                paragraph.add(new Phrase(note.getContent(), new Font(FontFamily.HELVETICA, 11)));
                document.add(paragraph);
            }
        } catch (DocumentException de) {
        } finally {
            document.close();
        }
        return baos;
    }
}

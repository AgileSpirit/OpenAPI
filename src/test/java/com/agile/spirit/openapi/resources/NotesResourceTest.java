package com.agile.spirit.openapi.resources;

import java.util.List;

import javax.persistence.EntityManager;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.agile.spirit.openapi.domain.Note;
import com.agile.spirit.openapi.domain.NoteFactory;
import com.agile.spirit.openapi.utils.PersistenceUtil;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;

public class NotesResourceTest extends JerseyTest {

    private final EntityManager em;

    public NotesResourceTest() {
        super("com.agile.spirit.openapi.resources");
        PersistenceUtil.createEntityManagerFactory();
        em = PersistenceUtil.getEntityManager();
    }

    @After
    public void tearDown() {
        em.clear();
        /*
         * Required in order to launch several tests (cf. JerseyTest source
         * code).
         */
        try {
            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetNoteById() {
        DateTime fixedDate = new DateTime(2013, DateTimeConstants.FEBRUARY, 26, 0, 0);
        DateTimeUtils.setCurrentMillisFixed(fixedDate.getMillis());

        Note.save(NoteFactory.createNote(1234, "title", "content"), em);

        WebResource webResource = resource().path("notes/" + 1);

        String expected = "{\"content\":\"content\",\"creationDate\":\"26-02-2013 12:00:00\",\"noteId\":\"1\",\"ownerId\":\"1234\",\"title\":\"title\"}";
        String actual = webResource.get(String.class);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCreateNote() {
        List<Note> notes = Note.list(em);
        Assert.assertEquals(0, notes.size());

        WebResource webResource = resource().path("notes");
        webResource.post(NoteFactory.createNote(1234, "title", "content"));

        notes = Note.list(em);
        Assert.assertEquals(1, notes.size());
    }

    @Test
    public void testUpdateNote() {
        Note managed = Note.save(NoteFactory.createNote(1234, "title", "content"), em);
        List<Note> notes = Note.list(em);
        Assert.assertEquals(1, notes.size());

        managed.setTitle("updated title");
        managed.setContent("updated content");

        WebResource webResource = resource().path("notes");
        webResource.put(managed);

        notes = Note.list(em);
        Assert.assertEquals(1, notes.size());
    }

    @Test
    public void testDeleteNote() {
        Note.save(NoteFactory.createNote(1234, "title", "content"), em);
        List<Note> notes = Note.list(em);
        Assert.assertEquals(1, notes.size());

        WebResource webResource = resource().path("notes/" + 1);
        webResource.delete();
        notes = Note.list(em);
        Assert.assertEquals(0, notes.size());

    }

}

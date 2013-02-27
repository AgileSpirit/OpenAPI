package com.agile.spirit.openapi;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;


public class NoteFactory {

    public static Note createNote(Integer ownerId, String title, String content) {
        return createNote(null, ownerId, title, content);
    }

    public static Note createNote(Integer noteId, Integer ownerId,
            String title, String content) {
        Note note = new Note();
        note.setNoteId(noteId);
        note.setOwnerId(ownerId);
        note.setTitle(title);
        note.setContent(content);
        note.setCreationDate(new DateTime());
        note.setModificationDate(null);
        return note;
    }

    public static List<Note> createListOfNotes(int nbNotes) {
        List<Note> notes = new ArrayList<Note>();
        for (int i = 0; i < nbNotes; i++) {
            notes.add(createNote(12345, i + " - Lorem Ipsum",
                    "Dolor sic amet nunc verbotten"));
        }
        return notes;
    }
}

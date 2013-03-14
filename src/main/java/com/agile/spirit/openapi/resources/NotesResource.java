package com.agile.spirit.openapi.resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agile.spirit.openapi.domain.Note;
import com.agile.spirit.openapi.domain.events.EventStore;
import com.agile.spirit.openapi.domain.events.LoggingEvent;
import com.agile.spirit.openapi.utils.PdfGenerator;
import com.agile.spirit.openapi.utils.PersistenceUtil;

/*
 * 1. All our resources are accessible via the URL "http://localhost:9998/notes/Xxx"
 * 2. We use a GenericEntity in order to marshal the JSON list from the Java beans.
 */

@Path("/notes")
public class NotesResource {

    private static Logger LOGGER = LoggerFactory.getLogger(NotesResource.class);

    private EntityManager em = PersistenceUtil.getEntityManager();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findNotesByOwner(@QueryParam("owner_id") Integer ownerId) {
        // Guava Domain Event in action ^^
        EventStore.getEventBus().post(new LoggingEvent("List notes for owner_id " + ownerId));

        if (ownerId == null) {
            LOGGER.error("note is null !");
            return Response.status(Status.BAD_REQUEST).build();
        }
        List<Note> notes = Note.findByOwnerId(ownerId, em);
        GenericEntity<List<Note>> entity = new GenericEntity<List<Note>>(notes) {
        };
        return Response.ok(entity).build();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listNotes() {
        // Guava Domain Event in action ^^
        EventStore.getEventBus().post(new LoggingEvent("List all notes"));

        List<Note> notes = Note.list(em);
        GenericEntity<List<Note>> entity = new GenericEntity<List<Note>>(notes) {
        };
        return Response.ok(entity).build();
    }

    @GET
    @Path("/all/export")
    @Produces({ MediaType.APPLICATION_OCTET_STREAM })
    public Response exportNotesAsPdf() {
        // Guava Domain Event in action ^^
        EventStore.getEventBus().post(new LoggingEvent("Export all notes as PDF"));

        final List<Note> notes = Note.list(em);
        StreamingOutput data = new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                ByteArrayOutputStream pdfFile = PdfGenerator.generateNotesExport(notes);
                output.write(pdfFile.toByteArray());
            }
        };
        ResponseBuilder response = Response.ok(data);
        response.header("Content-Disposition", "attachment; filename=notes-export.pdf");
        return response.build();
    }

    @GET
    @Path("/search/{pattern}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listNotes(@PathParam("pattern") String pattern) {
        // Guava Domain Event in action ^^
        EventStore.getEventBus().post(new LoggingEvent("Search notes with pattern " + pattern));

        List<Note> notes = Note.find(pattern, em);
        GenericEntity<List<Note>> entity = new GenericEntity<List<Note>>(notes) {
        };
        return Response.ok(entity).build();
    }

    @GET
    @Path("/{noteId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNote(@PathParam("noteId") Integer noteId) {
        // Guava Domain Event in action ^^
        EventStore.getEventBus().post(new LoggingEvent("Get note with id " + noteId));

        if (noteId != null) {
            Note note = Note.get(noteId, em);
            return Response.ok(note).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNote(Note note) {
        if (note != null) {
            Note persisted = Note.save(note, em);
            if (persisted != null) {
                return Response.ok(persisted).build();
            }
        }
        LOGGER.error("note is null !");
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateNote(Note note) {
        if (note != null) {
            Note merged = Note.save(note, em);
            if (merged != null) {
                return Response.ok(merged).build();
            }
        }
        LOGGER.error("note is null !");
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    @DELETE
    @Path("/{noteId}")
    public Response deleteNote(@PathParam("noteId") Integer noteId) {
        if (noteId != null) {
            Note.delete(noteId, em);
            return Response.ok().build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

}

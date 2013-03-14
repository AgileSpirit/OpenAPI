package com.agile.spirit.openapi.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/*
 * 1. @XmlRootElement annotation is required because the Jersey-JSON auto-serialization is based on JAXB 
 * 2. @Entity, @Table etc. are required for Hibernate/JPA support
 * 3. @Type annotation is used for Hibernate supporting Joda DateTime type instead of basic Timestamp
 */

@XmlRootElement
// 1.
@Entity
// 2.
@Table(name = "NOTES")
@NamedQueries({ @NamedQuery(name = "list", query = "from Note"),
        @NamedQuery(name = "find", query = "from Note where title like :pattern or content like :pattern") })
public class Note {

    @Id
    @GeneratedValue
    private Integer noteId;

    @Column(name = "OWNER_ID", nullable = false)
    private Integer ownerId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = true)
    private String content;

    // 3.
    @Column(name = "CREATION_DATE", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationDate;

    // 3.
    @Column(name = "MODIFICATION_DATE", nullable = true)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationDate;

    /*
     * GETTERS / SETTERS
     */

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public DateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(DateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    /*
     * REPOSITORY
     */

    public static Note get(Integer noteId, EntityManager em) {
        Note note = em.find(Note.class, noteId);
        return note;
    }

    public static List<Note> find(String pattern, EntityManager em) {
        List<Note> notes = em.createNamedQuery("find", Note.class).setParameter("pattern", "%" + pattern + "%")
                .getResultList();
        return notes;
    }

    public static List<Note> list(EntityManager em) {
        return em.createNamedQuery("list", Note.class).getResultList();
    }

    public static Note save(Note note, EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            if (note.getNoteId() == null) {
                note.setCreationDate(new DateTime());
                em.persist(note);
            } else {
                note.setModificationDate(new DateTime());
                note = em.merge(note);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
        return note;
    }

    public static void delete(Integer noteId, EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Note note = get(noteId, em);
            if (note != null) {
                em.remove(note);
                tx.commit();
            }
        } catch (Exception e) {
            tx.rollback();
        }
    }

}

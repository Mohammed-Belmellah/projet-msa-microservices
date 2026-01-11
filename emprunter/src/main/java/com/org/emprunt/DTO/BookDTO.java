package com.org.emprunt.DTO;

public class BookDTO {
public BookDTO() {}
 private Long id;
    public Long getId() {
    return id;
}
 public void setId(Long id) {
    this.id = id;
 }
    private String titre;
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
}

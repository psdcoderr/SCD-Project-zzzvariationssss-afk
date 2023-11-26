package DTO;

public class BooksDTO {
    private String title;
    private String author;
    private String yearPassed;

    public BooksDTO(String title, String author, String yearPassed) {
        this.title = title;
        this.author = author;
        this.yearPassed = yearPassed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYearPassed() {
        return yearPassed;
    }

    public void setYearPassed(String yearPassed) {
        this.yearPassed = yearPassed;
    }
}
package dto;

public class PostDto {

    private Long id;
    private String title;
    private String body;
    private Long user_id;

    public PostDto() {}

    public PostDto(String title, String body, Long userId) {
        this.title = title;
        this.body = body;
        this.user_id = userId;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getBody() {return body;}

    public void setBody(String body) {this.body = body;}

    public Long getUser_id() {return user_id;}

    public void setUser_id(Long user_id) {this.user_id = user_id;}
}
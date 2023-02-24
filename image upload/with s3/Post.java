@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
//필드 값이 많아서 빌더 패턴 사용
//조회수, 채팅, 관심의 경우 입력받는게 아니고 특정 행위에 따라 값을 변경시켜야 하므로 setter 추가
public class Post extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private Member member;
    @Column(name="post_title")
    private String postTitle;
    @Column(name="post_content")
    private String content;
    @Column(name="writer_region")
    private String region;
    @Column(name="price")
    private int price;//int 사용시 값 설정을 하지 않으면 0으로 됨
    @OneToMany(mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch=FetchType.LAZY,
            orphanRemoval = true)
    @Builder.Default
    private List<Image> images = new ArrayList<>();
    private int chats;
    private int interests;
    private int visits;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ItemSellStatus sellStatus = ItemSellStatus.SELL;
    public void updateVisits() {
        this.visits+=1;
    }

    public void plusInterests() {
        this.interests=this.interests+1;
    }
    public void minusInterests() {
        this.interests=this.interests-1;
    }
    public static Post createEntity(PostRequest post, Member member) {//user를 저장해야함
        return Post.builder()
                .postTitle(post.getPostTitle())
                .member(member)
                .content(post.getContent())
                .region(post.getRegion())
                .price(post.getPrice()).build();
    }

    public void changeSellStatus(ItemSellStatus status) {
        this.sellStatus=status;
    }

    public void changePost(PostModifyRequest form) { //수정할 때 삭제한 이미지의 url을 전부 삭제함
        this.postTitle=form.getPostTitle();
        this.content=form.getContent();
        this.price=form.getPrice();
        List<String> urls = form.getImageUrlListForDelete();

        this.images.removeIf( //이미지를 삭제
                e -> urls.contains(e.getStoredImagePath())
        );

    }


}

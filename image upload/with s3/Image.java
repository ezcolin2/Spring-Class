@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Image {
    //요청을 하면 클라이언트에게 originName을 보여주고
    //storedImagePath에 저장된 경로에서 불러오면 됨
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;
    @Column(name="origin_name")
    private String originName;
    @Column(name="stored_image_path")
    private String storedImagePath;

}

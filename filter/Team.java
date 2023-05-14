@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Member> memberList = new ArrayList<>();
    @Builder
    public Team(String teamName) {
        Assert.hasText(teamName, "teamName은 필수");
        this.teamName=teamName;
    }
}

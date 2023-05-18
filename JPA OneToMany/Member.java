@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    @ManyToOne
    private Team team;
    @Builder
    public Member(String name, String address, Team team) {
        Assert.hasText(name, "name은 필수");
        Assert.hasText(address, "address은 필수");
        this.name=name;
        this.address=address;
        this.team=team;
        team.getMemberList().add(this);
    }
}

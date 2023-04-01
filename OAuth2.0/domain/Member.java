@Entity
@NoArgsConstructor
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //기본키
    private String name; //유저 이름
    private String password; //유저 비밀번호
    private String email; //유저 구글 이메일
    private String role; //유저 권한 (일반 유저, 관리지ㅏ)
    private String provider; //공급자 (google, facebook ...)
    private String providerId; //공급 아이디
    @Builder
    public Member(String name, String password, String email, String role, String provider, String providerId) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}

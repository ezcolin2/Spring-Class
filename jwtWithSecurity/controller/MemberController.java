@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/join")
    public String join(@RequestBody MemberDto memberDto) {
        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(Role.USER);
        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(encoder.encode(memberDto.getPassword()))
                .roles(roleList).build();
        memberRepository.save(member);
        return "회원가입 성공";
    }
}

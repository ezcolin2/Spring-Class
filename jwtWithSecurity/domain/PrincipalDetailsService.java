@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username);
        System.out.println("member = " + member);
        return new PrincipalDetails(member);
    }
}

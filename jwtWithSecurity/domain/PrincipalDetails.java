@Getter
public class PrincipalDetails implements UserDetails {
    private Member member;

    public PrincipalDetails(Member member) {
        this.member=member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //유저에게 부여된 권한들 반환
        List<Role> roles = member.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { //계정 만료 여부
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } //계정 잠김 여부

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } //credentials(password) 만료 여부

    @Override
    public boolean isEnabled() {
        return true;
    } //유저 사용 가능 여부
}


JPA 일대다 매핑

Team 엔티티와 Member 엔티티는 일대다 연관 관계.

Team은 Member를 리스트로 가지고 있고 Member는 Team을 가지고 있다.

1. @Setter 사용 자제.
    @Setter를 사용하게 되면 필드의 불변성을 보장할 수 없어서 @Setter를 사용하지 않았다.

2. Builder 패턴 사용
    Builder 패턴을 사용하면 원하는 필드 값을 세팅할 수 있어서 실수가 줄어든다.
    클래스 자체에 적용하기 보다는 생성자에 적용한다.
    원하는 필드만 세팅하도록 구성할 수 있다.
    그리고 생성자 내부에서 검증을 진행한다.

3. A Team에 속하는 Member를 생성할 때는 Member의 Team만 세팅하는게 아니라 Team의 MemberList도 세팅해준다.
    Builder로 Member를 생성할 때 인자로 Team 엔티티를 넣어준다. 그리고 Team 엔티티의 MemberList에 Member를 추가한다.

    

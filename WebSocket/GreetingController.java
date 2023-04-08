
@Controller
@RequiredArgsConstructor
public class GreetingController {
    //메시지를 구독자들에게 주기 전에 가공하는 곳
    //WebSocketCofnig에서 "/app"으로 요청이 오면 가공을 해서 구독자들에게 전달해준다는 설정을 했다.
    //즉 "/app/hello"로 요청이 오면 가공을 거쳐 "/topic/greetings"를 구독 중인 구독자들에게 메시지를 넘겨준다.
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        return new Greeting(HtmlUtils.htmlEscape(message.getName()+"님 환영합니다"));
    }
}

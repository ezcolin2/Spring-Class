@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new
                FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); //filter 등록
        filterRegistrationBean.setOrder(1); //순서. 낮을수록 우선수위 높음
        filterRegistrationBean.addUrlPatterns("/*"); //모든 요청에 대하여
        return filterRegistrationBean;
    }
}

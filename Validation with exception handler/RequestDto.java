@Getter
public class RequestDto {
    @NotNull(message="null을 허용하지 않습니다.")
    private String notNullField;
    @NotEmpty(message="null과 \"\"를 허용하지 않습니다.")
    private String notEmptyField;
    @NotBlank(message="null과 \"\"와 \" \"를 허용하지 않습니다.")
    private String notBlankField;
    @Min(1)
    @Max(10)
    private String minMaxField;
    @Size(min=1, max=100, message = "길이가 1이상 100이하만 허용합니다.")
    private String lengthField;
    @Email(message="이메일 형식만 허용합니다.")
    private String email;
}

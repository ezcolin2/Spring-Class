@Service
@RequiredArgsConstructor
public class ImageS3Service{
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName; //버킷 이름
    private String changedImageName(String ext) { //이미지 이름 중복 방지를 위해 랜덤으로 생성
        String random = UUID.randomUUID().toString();
        return random+ext;
    }

    private String uploadImageToS3(MultipartFile image) { //이미지를 S3에 업로드하고 이미지의 url을 반환
        String originName = image.getOriginalFilename(); //원본 이미지 이름
        String ext = originName.substring(originName.lastIndexOf(".")); //확장자
        String changedName = changedImageName(ext); //새로 생성된 이미지 이름
        ObjectMetadata metadata = new ObjectMetadata(); //메타데이터
        metadata.setContentType("image/"+ext.substring(1));
        try {
            PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(
                    bucketName, changedName, image.getInputStream(), metadata
            ).withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (IOException e) {
            throw new ImageUploadException(); //커스텀 예외 던짐.
        }
        return amazonS3.getUrl(bucketName, changedName).toString(); //데이터베이스에 저장할 이미지가 저장된 주소

    }


    public Image uploadImage(MultipartFile image, Post post){
        String originName = image.getOriginalFilename();
        String storedImagePath = uploadImageToS3(image);

        Image newImage = Image.builder() //이미지에 대한 정보를 담아서 반환
                .originName(originName)
                .storedImagePath(storedImagePath)
                .post(post).build();
        return newImage;
    }

    public void deleteImage(String key) {
        DeleteObjectRequest deleteRequest = new DeleteObjectRequest(bucketName, key);
        amazonS3.deleteObject(deleteRequest);
    }


}

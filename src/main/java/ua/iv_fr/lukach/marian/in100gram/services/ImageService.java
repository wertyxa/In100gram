package ua.iv_fr.lukach.marian.in100gram.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import ua.iv_fr.lukach.marian.in100gram.entity.ImageModel;
import ua.iv_fr.lukach.marian.in100gram.entity.Post;
import ua.iv_fr.lukach.marian.in100gram.entity.User;
import ua.iv_fr.lukach.marian.in100gram.exeptions.ImageNotFoundException;
import ua.iv_fr.lukach.marian.in100gram.repository.ImageRepository;
import ua.iv_fr.lukach.marian.in100gram.repository.PostRepository;
import ua.iv_fr.lukach.marian.in100gram.repository.UserRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageService {

    public static final Logger LOG = LoggerFactory.getLogger(ImageService .class);


    private ImageRepository imageRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository, UserRepository userRepository, PostRepository postRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    public ImageModel uploadImageToUser(MultipartFile file, Principal principal) throws IOException{
        User user = getUserByPrincipal(principal);
        LOG.info("Uploading image profile to User {}", user.getUsername());

        ImageModel userProfileImage = imageRepository.findByUserId(user.getId()).orElse(null);

        if (!ObjectUtils.isEmpty(userProfileImage)){
            imageRepository.delete(userProfileImage);

        }
        ImageModel imageModel = new ImageModel();
        imageModel.setUserId(user.getId());
        imageModel.setImageBytes(compressBytes(file.getBytes()));
        imageModel.setName(file.getOriginalFilename());
        return imageRepository.save(imageModel);
    }

    public ImageModel uploadImageToPost(MultipartFile file, Principal principal, Long postId) throws IOException{
        User user = getUserByPrincipal(principal);
        Post post = user.getPosts()
                .stream()
                .filter(p -> p.getId().equals(postId))
                .collect(toSinglePost());

        ImageModel imageModel = new ImageModel();
        imageModel.setPostId(post.getId());
        imageModel.setImageBytes(compressBytes(file.getBytes()));
        imageModel.setName(file.getOriginalFilename());
        LOG.info("Upload image to Post {}", post.getId());
        return imageRepository.save(imageModel);
    }

    public  ImageModel getImageToUser(Principal principal){
        User user = getUserByPrincipal(principal);

        ImageModel imageModel = imageRepository.findByUserId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(imageModel)){
            imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
        }
        return imageModel;
    }

    public ImageModel getImageToPost(Long postID){
        ImageModel imageModel = imageRepository.findByPostId(postID)
                .orElseThrow(() -> new ImageNotFoundException("Cannot find image to Post "+ postID));
        if(!ObjectUtils.isEmpty(imageModel)){
            imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
        }
        return imageModel;
    }

    private byte[] compressBytes(byte[] data){
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()){
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        }catch (IOException e){
            LOG.error("Cannot compress Bytes");
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    private static byte[] decompressBytes(byte[] data){
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while(!inflater.finished()){
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        }catch (IOException | DataFormatException e){
            LOG.error("Cannot decompress Bytes");
        }
        return outputStream.toByteArray();
    }

    private User getUserByPrincipal(Principal principal) {
        String userName = principal.getName();
        return userRepository.findUserByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + userName));
    }

    private <T> Collector<T,?,T> toSinglePost(){
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() !=1){
                        throw new IllegalArgumentException();
                    }
                    return list.get(0);
                }
        );
    }

}

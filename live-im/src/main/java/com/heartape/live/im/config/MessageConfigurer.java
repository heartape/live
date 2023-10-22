package com.heartape.live.im.config;

import com.heartape.live.im.message.type.file.FileMessage;
import com.heartape.live.im.message.type.greeting.GreetingMessage;
import com.heartape.live.im.message.type.image.ImageMessage;
import com.heartape.live.im.message.type.location.LocationMessage;
import com.heartape.live.im.message.type.sound.SoundMessage;
import com.heartape.live.im.message.type.text.TextMessage;
import com.heartape.live.im.message.type.video.VideoMessage;
import lombok.Getter;

/**
 * 添加消息组件配置加载
 * @since 0.0.1
 * @author heartape
 */
@Getter
public class MessageConfigurer {

    private TextProviderConfigurer textConfigurer;
    private GreetingProviderConfigurer greetingConfigurer;
    private ImageProviderConfigurer imageConfigurer;
    private FileProviderConfigurer fileConfigurer;
    private LocationProviderConfigurer locationConfigurer;
    private SoundProviderConfigurer soundConfigurer;
    private VideoProviderConfigurer videoConfigurer;

    public static MessageConfigurer init() {
        return new MessageConfigurer()
                .greeting(new GreetingProviderConfigurer())
                .text(new TextProviderConfigurer())
                .image(new ImageProviderConfigurer())
                .file(new FileProviderConfigurer())
                .video(new VideoProviderConfigurer())
                .location(new LocationProviderConfigurer())
                .sound(new SoundProviderConfigurer());
    }

    public TextProviderConfigurer text(){
        return this.textConfigurer;
    }

    public MessageConfigurer text(TextProviderConfigurer textConfigurer){
        this.textConfigurer = textConfigurer;
        return this;
    }

    public GreetingProviderConfigurer greeting(){
        return this.greetingConfigurer;
    }

    public MessageConfigurer greeting(GreetingProviderConfigurer greetingConfigurer){
        this.greetingConfigurer = greetingConfigurer;
        return this;
    }

    public ImageProviderConfigurer image(){
        return this.imageConfigurer;
    }

    public MessageConfigurer image(ImageProviderConfigurer imageConfigurer){
        this.imageConfigurer = imageConfigurer;
        return this;
    }

    public FileProviderConfigurer file(){
        return this.fileConfigurer;
    }

    public MessageConfigurer file(FileProviderConfigurer fileConfigurer){
        this.fileConfigurer = fileConfigurer;
        return this;
    }

    public LocationProviderConfigurer location(){
        return this.locationConfigurer;
    }

    public MessageConfigurer location(LocationProviderConfigurer locationConfigurer){
        this.locationConfigurer = locationConfigurer;
        return this;
    }

    public SoundProviderConfigurer sound(){
        return this.soundConfigurer;
    }

    public MessageConfigurer sound(SoundProviderConfigurer soundConfigurer){
        this.soundConfigurer = soundConfigurer;
        return this;
    }

    public VideoProviderConfigurer video(){
        return this.videoConfigurer;
    }

    public MessageConfigurer video(VideoProviderConfigurer videoConfigurer){
        this.videoConfigurer = videoConfigurer;
        return this;
    }

    /**
     * 添加Text消息组件配置加载
     * @see AbstractProviderConfigurer
     */
    public static class TextProviderConfigurer extends AbstractProviderConfigurer<TextMessage> {
    }

    /**
     * 添加Greeting消息组件配置加载
     * @see AbstractProviderConfigurer
     */
    public static class GreetingProviderConfigurer extends AbstractProviderConfigurer<GreetingMessage> {
    }

    /**
     * 添加Image消息组件配置加载
     * @see AbstractProviderConfigurer
     */
    public static class ImageProviderConfigurer extends AbstractProviderConfigurer<ImageMessage> {
    }

    /**
     * 添加Image消息组件配置加载
     * @see AbstractProviderConfigurer
     */
    public static class FileProviderConfigurer extends AbstractProviderConfigurer<FileMessage> {
    }

    /**
     * 添加Image消息组件配置加载
     * @see AbstractProviderConfigurer
     */
    public static class LocationProviderConfigurer extends AbstractProviderConfigurer<LocationMessage> {
    }

    /**
     * 添加Image消息组件配置加载
     * @see AbstractProviderConfigurer
     */
    public static class SoundProviderConfigurer extends AbstractProviderConfigurer<SoundMessage> {
    }

    /**
     * 添加Image消息组件配置加载
     * @see AbstractProviderConfigurer
     */
    public static class VideoProviderConfigurer extends AbstractProviderConfigurer<VideoMessage> {
    }

}

package com.heartape.live.im.config;

import com.heartape.live.im.gateway.*;
import com.heartape.live.im.gateway.proxy.InterceptorGatewayStaticProxyFactory;
import com.heartape.live.im.gateway.proxy.GatewayProxyFactory;
import com.heartape.live.im.handler.*;
import com.heartape.live.im.interceptor.message.*;
import com.heartape.live.im.interceptor.prompt.ListPromptInterceptorRegister;
import com.heartape.live.im.interceptor.prompt.PromptInterceptorRegister;
import com.heartape.live.im.manage.friend.*;
import com.heartape.live.im.manage.group.*;
import com.heartape.live.im.message.*;
import com.heartape.live.im.message.type.file.FileFilterManager;
import com.heartape.live.im.message.type.file.FileMessageConverter;
import com.heartape.live.im.message.type.file.FileMessageProvider;
import com.heartape.live.im.message.type.file.MemoryFileCenterRepository;
import com.heartape.live.im.message.type.greeting.MemoryGreetingCenterRepository;
import com.heartape.live.im.message.type.greeting.GreetingFilterManager;
import com.heartape.live.im.message.type.greeting.GreetingMessageConverter;
import com.heartape.live.im.message.type.greeting.GreetingMessageProvider;
import com.heartape.live.im.message.type.image.MemoryImageCenterRepository;
import com.heartape.live.im.message.type.image.ImageFilterManager;
import com.heartape.live.im.message.type.image.ImageMessageConverter;
import com.heartape.live.im.message.type.image.ImageMessageProvider;
import com.heartape.live.im.message.type.location.LocationFilterManager;
import com.heartape.live.im.message.type.location.LocationMessageConverter;
import com.heartape.live.im.message.type.location.LocationMessageProvider;
import com.heartape.live.im.message.type.location.MemoryLocationCenterRepository;
import com.heartape.live.im.message.type.sound.MemorySoundCenterRepository;
import com.heartape.live.im.message.type.sound.SoundFilterManager;
import com.heartape.live.im.message.type.sound.SoundMessageConverter;
import com.heartape.live.im.message.type.sound.SoundMessageProvider;
import com.heartape.live.im.message.type.text.*;
import com.heartape.live.im.message.type.video.MemoryVideoCenterRepository;
import com.heartape.live.im.message.type.video.VideoFilterManager;
import com.heartape.live.im.message.type.video.VideoMessageConverter;
import com.heartape.live.im.message.type.video.VideoMessageProvider;
import com.heartape.live.im.prompt.PromptProvider;
import com.heartape.util.id.IdentifierGenerator;
import com.heartape.util.id.snowflake.IpSnowflakeHolder;
import com.heartape.util.id.snowflake.SnowFlake;
import com.heartape.util.id.snowflake.SnowflakeHolder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.web.socket.WebSocketHandler;

import java.util.Map;
import java.util.Set;

/**
 * 配置im服务器自动配置
 * @since 0.0.1
 * @author heartape
 */
@Configuration
@EnableConfigurationProperties({LiveImProperties.class})
public class LiveImAutoConfiguration {

    public static final String GROUP_CENTER_MESSAGE_REPOSITORY_BEAN_NAME = "groupCenterMessageRepository";

    public static final String USER_CENTER_MESSAGE_REPOSITORY_BEAN_NAME = "userCenterMessageRepository";

    /**
     * 消息配置
     */
    @Configuration
    public static class MessageConfiguration {
        @Bean
        public MessageConfigurer messageConfigurer(IdentifierGenerator<Long> identifierGenerator){
            MessageConfigurer messageConfigurer = MessageConfigurer.init();

            // TEXT
            TextMessageConverter textMessageConverter = new TextMessageConverter();
            TextFilterManager textFilterManager = new TextFilterManager();
            textFilterManager.register(new TextBaseFilter());
            textFilterManager.register(new MemoryTextKeywordShieldFilter(Set.of("卧槽", "握草")));
            MemoryTextCenterRepository groupMemoryTextCenterRepository = new MemoryTextCenterRepository(identifierGenerator, groupCenterMessageRepository);
            MemoryTextCenterRepository userMemoryTextCenterRepository = new MemoryTextCenterRepository(identifierGenerator, userCenterMessageRepository);

            messageConfigurer.text()
                    .converter(textMessageConverter)
                    .filterManager(textFilterManager)
                    .group(groupMemoryTextCenterRepository)
                    .user(userMemoryTextCenterRepository);

            // GREETING
            GreetingMessageConverter greetingMessageConverter = new GreetingMessageConverter();
            GreetingFilterManager greetingFilterManager = new GreetingFilterManager();
            MemoryGreetingCenterRepository groupMemoryGreetingCenterRepository = new MemoryGreetingCenterRepository(identifierGenerator, groupCenterMessageRepository);
            MemoryGreetingCenterRepository userMemoryGreetingCenterRepository = new MemoryGreetingCenterRepository(identifierGenerator, userCenterMessageRepository);

            messageConfigurer.greeting()
                    .converter(greetingMessageConverter)
                    .filterManager(greetingFilterManager)
                    .group(groupMemoryGreetingCenterRepository)
                    .user(userMemoryGreetingCenterRepository);

            // IMAGE
            ImageMessageConverter imageMessageConverter = new ImageMessageConverter();
            ImageFilterManager imageFilterManager = new ImageFilterManager();
            MemoryImageCenterRepository groupMemoryImageCenterRepository = new MemoryImageCenterRepository(identifierGenerator, groupCenterMessageRepository);
            MemoryImageCenterRepository userMemoryImageCenterRepository = new MemoryImageCenterRepository(identifierGenerator, userCenterMessageRepository);

            messageConfigurer.image()
                    .converter(imageMessageConverter)
                    .filterManager(imageFilterManager)
                    .group(groupMemoryImageCenterRepository)
                    .user(userMemoryImageCenterRepository);

            // FILE
            FileMessageConverter fileMessageConverter = new FileMessageConverter();
            FileFilterManager fileFilterManager = new FileFilterManager();
            MemoryFileCenterRepository groupMemoryFileCenterRepository = new MemoryFileCenterRepository(identifierGenerator, groupCenterMessageRepository);
            MemoryFileCenterRepository userMemoryFileCenterRepository = new MemoryFileCenterRepository(identifierGenerator, userCenterMessageRepository);

            messageConfigurer.file()
                    .converter(fileMessageConverter)
                    .filterManager(fileFilterManager)
                    .group(groupMemoryFileCenterRepository)
                    .user(userMemoryFileCenterRepository);

            // VIDEO
            VideoMessageConverter videoMessageConverter = new VideoMessageConverter();
            VideoFilterManager videoFilterManager = new VideoFilterManager();
            MemoryVideoCenterRepository groupMemoryVideoCenterRepository = new MemoryVideoCenterRepository(identifierGenerator, groupCenterMessageRepository);
            MemoryVideoCenterRepository userMemoryVideoCenterRepository = new MemoryVideoCenterRepository(identifierGenerator, userCenterMessageRepository);

            messageConfigurer.video()
                    .converter(videoMessageConverter)
                    .filterManager(videoFilterManager)
                    .group(groupMemoryVideoCenterRepository)
                    .user(userMemoryVideoCenterRepository);


            // LOCATION
            LocationMessageConverter locationMessageConverter = new LocationMessageConverter();
            LocationFilterManager locationFilterManager = new LocationFilterManager();
            MemoryLocationCenterRepository groupMemoryLocationCenterRepository = new MemoryLocationCenterRepository(identifierGenerator, groupCenterMessageRepository);
            MemoryLocationCenterRepository userMemoryLocationCenterRepository = new MemoryLocationCenterRepository(identifierGenerator, userCenterMessageRepository);

            messageConfigurer.location()
                    .converter(locationMessageConverter)
                    .filterManager(locationFilterManager)
                    .group(groupMemoryLocationCenterRepository)
                    .user(userMemoryLocationCenterRepository);

            // SOUND
            SoundMessageConverter soundMessageConverter = new SoundMessageConverter();
            SoundFilterManager soundFilterManager = new SoundFilterManager();
            MemorySoundCenterRepository groupMemorySoundCenterRepository = new MemorySoundCenterRepository(identifierGenerator, groupCenterMessageRepository);
            MemorySoundCenterRepository userMemorySoundCenterRepository = new MemorySoundCenterRepository(identifierGenerator, userCenterMessageRepository);

            messageConfigurer.sound()
                    .converter(soundMessageConverter)
                    .filterManager(soundFilterManager)
                    .group(groupMemorySoundCenterRepository)
                    .user(userMemorySoundCenterRepository);

            return messageConfigurer;
        }
    }

    /**
     * 消息网关配置
     */
    @Configuration
    public static class MessageGatewayConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public MessageInterceptorRegister messageInterceptorRegister(FriendshipService friendshipService, GroupChatMemberRepository groupChatMemberRepository){
            ListMessageInterceptorRegister listMessageInterceptorRegister = new ListMessageInterceptorRegister();
            listMessageInterceptorRegister
                    .register(new MemoryFriendshipInterceptor(friendshipService))
                    .register(new DefaultGroupMemberInterceptor(groupChatMemberRepository));
            return listMessageInterceptorRegister;
        }

        @Bean
        @ConditionalOnMissingBean
        public PromptInterceptorRegister systemInterceptorRegister(){
            return new ListPromptInterceptorRegister();
        }

        @Bean
        @ConditionalOnMissingBean
        public GatewayProxyFactory messageGatewayProxyFactory(MessageInterceptorRegister messageInterceptorRegister,
                                                              PromptInterceptorRegister promptInterceptorRegister) {
            return new InterceptorGatewayStaticProxyFactory(messageInterceptorRegister, promptInterceptorRegister);
        }

        @Bean
        @ConditionalOnMissingBean
        public Gateway gateway(MessageConfigurer messageConfigurer){
            MessageConfigurer.TextProviderConfigurer textConfigurer = messageConfigurer.getTextConfigurer();
            MessageConfigurer.GreetingProviderConfigurer greetingConfigurer = messageConfigurer.getGreetingConfigurer();
            MessageConfigurer.ImageProviderConfigurer imageConfigurer = messageConfigurer.getImageConfigurer();
            MessageConfigurer.FileProviderConfigurer fileConfigurer = messageConfigurer.getFileConfigurer();
            MessageConfigurer.LocationProviderConfigurer locationConfigurer = messageConfigurer.getLocationConfigurer();
            MessageConfigurer.SoundProviderConfigurer soundConfigurer = messageConfigurer.getSoundConfigurer();
            MessageConfigurer.VideoProviderConfigurer videoConfigurer = messageConfigurer.getVideoConfigurer();

            TextMessageProvider textMessageProvider = new TextMessageProvider(textConfigurer.getMessageConverter(), textConfigurer.getFilterManager(), textConfigurer.getGroupRepository(), textConfigurer.getUserRepository());
            GreetingMessageProvider greetingMessageProvider = new GreetingMessageProvider(greetingConfigurer.getMessageConverter(), greetingConfigurer.getFilterManager(), greetingConfigurer.getGroupRepository(), greetingConfigurer.getUserRepository());
            ImageMessageProvider imageMessageProvider = new ImageMessageProvider(imageConfigurer.getMessageConverter(), imageConfigurer.getFilterManager(), imageConfigurer.getGroupRepository(), imageConfigurer.getUserRepository());
            FileMessageProvider fileMessageProvider = new FileMessageProvider(fileConfigurer.getMessageConverter(), fileConfigurer.getFilterManager(), fileConfigurer.getGroupRepository(), fileConfigurer.getUserRepository());
            LocationMessageProvider locationMessageProvider = new LocationMessageProvider(locationConfigurer.getMessageConverter(), locationConfigurer.getFilterManager(), locationConfigurer.getGroupRepository(), locationConfigurer.getUserRepository());
            SoundMessageProvider soundMessageProvider = new SoundMessageProvider(soundConfigurer.getMessageConverter(), soundConfigurer.getFilterManager(), soundConfigurer.getGroupRepository(), soundConfigurer.getUserRepository());
            VideoMessageProvider videoMessageProvider = new VideoMessageProvider(videoConfigurer.getMessageConverter(), videoConfigurer.getFilterManager(), videoConfigurer.getGroupRepository(), videoConfigurer.getUserRepository());

            Map<String, MessageProvider> messageProviderStrategyMap = Map.of(
                    MessageType.TEXT, textMessageProvider,
                    MessageType.GREETING, greetingMessageProvider,
                    MessageType.IMAGE, imageMessageProvider,
                    MessageType.FILE, fileMessageProvider,
                    MessageType.LOCATION, locationMessageProvider,
                    MessageType.SOUND, soundMessageProvider,
                    MessageType.VIDEO, videoMessageProvider
            );

            // 定义需要的系统消息类型
            Map<String, PromptProvider> promptProviderStrategyMap = Map.of();

            return new StrategyGateway(messageProviderStrategyMap, promptProviderStrategyMap);
        }

        @Primary
        @Bean
        @ConditionalOnBean
        public Gateway gatewayProxy(Gateway gateway, GatewayProxyFactory gatewayProxyFactory){
            return gatewayProxyFactory.create(gateway);
        }

    }

    /**
     * 基础配置
     */
    @Configuration
    public static class BaseConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public SnowflakeHolder ipSnowflakeHolder(LiveImProperties liveImProperties){
            return new IpSnowflakeHolder(liveImProperties.getNetworkInterfaceName());
        }

        @Bean
        @ConditionalOnMissingBean
        public IdentifierGenerator<Long> identifierGenerator(SnowflakeHolder snowflakeHolder){
            return new SnowFlake(snowflakeHolder);
        }

        @Bean("standaloneWebSocketSessionManager")
        public WebSocketSessionManager standaloneWebSocketSessionManager(Gateway gateway,
                                                                         GroupChatMemberRepository groupChatMemberRepository) {
            return new StandaloneWebSocketSessionManager(gateway, groupChatMemberRepository);
        }

        @Bean("clusterWebSocketSessionManager")
        // @ConditionalOnProperty(name = "live.im.cluster.enable", havingValue = "true")
        public WebSocketSessionManager clusterWebSocketSessionManager(@Qualifier("standaloneWebSocketSessionManager") WebSocketSessionManager webSocketSessionManager,
                                                                      RedisOperations<String, String> redisOperations,
                                                                      WebSocketHandler clusterWebSocketHandler,
                                                                      GroupChatMemberRepository groupChatMemberRepository,
                                                                      LiveImProperties liveImProperties,
                                                                      @Value("${server.port}") int port) {
            LiveImProperties.Cluster cluster = liveImProperties.getCluster();
            return new RedisRegisterClusterWebSocketSessionManager(redisOperations, webSocketSessionManager, clusterWebSocketHandler, groupChatMemberRepository, cluster.getServers(), port, liveImProperties.getCluster().getHost(), liveImProperties.getNetworkInterfaceName());
        }

        @Bean("imWebSocketHandler")
        public WebSocketHandler imWebSocketHandler(@Qualifier("clusterWebSocketSessionManager") WebSocketSessionManager webSocketSessionManager){
            return new ImTextWebSocketHandler(webSocketSessionManager);
        }

        @Bean("clusterWebSocketHandler")
        // @ConditionalOnProperty(name = "live.im.cluster.enable", havingValue = "true")
        public WebSocketHandler clusterWebSocketHandler(@Qualifier("standaloneWebSocketSessionManager") WebSocketSessionManager webSocketSessionManager){
            return new ClusterWebSocketHandler(webSocketSessionManager);
        }
    }

    /**
     * manage配置
     */
    @Configuration
    public static class ManageConfiguration {

        @Configuration
        public static class FriendConfiguration {
            @Bean
            @ConditionalOnMissingBean
            public FriendshipGroupRepository friendshipGroupRepository(){
                return new MemoryFriendshipGroupRepository();
            }

            @Bean
            @ConditionalOnMissingBean
            public FriendshipRepository friendshipRepository(IdentifierGenerator<Long> identifierGenerator){
                return new MemoryFriendshipRepository(identifierGenerator);
            }

            @Bean
            @ConditionalOnMissingBean
            public FriendshipService friendshipService(IdentifierGenerator<Long> identifierGenerator,
                                                       FriendshipRepository friendshipRepository,
                                                       FriendshipGroupRepository friendshipGroupRepository) {
                return new DefaultFriendshipService(identifierGenerator, friendshipRepository, friendshipGroupRepository);
            }
        }

        @Configuration
        public static class GroupConfiguration {

            @Bean
            @ConditionalOnMissingBean
            public GroupChatRepository groupChatRepository(IdentifierGenerator<Long> identifierGenerator){
                return new MemoryGroupChatRepository(identifierGenerator);
            }

            @Bean
            @ConditionalOnMissingBean
            public GroupChatMemberRepository groupChatMemberRepository(IdentifierGenerator<Long> identifierGenerator){
                return new MemoryGroupChatMemberRepository(identifierGenerator);
            }
        }
    }
}

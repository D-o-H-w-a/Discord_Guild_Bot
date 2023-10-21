package org.example;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.apache.commons.collections4.IterableMap;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {

    private static ShardManager shardManager;

    public static void main(String[] args) throws LoginException {

        String token = System.getenv("TOKEN");
        JDABuilder jda = JDABuilder.createDefault(token);
        jda.setStatus(OnlineStatus.ONLINE);
        jda.setActivity(Activity.playing("문양"));

        jda.enableIntents(GatewayIntent.MESSAGE_CONTENT,GatewayIntent.GUILD_MEMBERS).build().addEventListener(new Main()); // You need to implement this listener.
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] name = event.getMessage().getContentRaw().split(" ");
        String nick = event.getMember().getNickname();
        if (nick == null) {
            nick = event.getAuthor().getName();
        }
        String prefix = "!";
        System.out.println(event.getMessage());
        if (!event.getAuthor().isBot()) {
            if (name[0].equals(prefix + "횟수")) {
                if (name.length == 1) {
                    event.getChannel().sendMessage("!횟수 <던전 이름>").queue();
                } else {
                    boolean isCheck = false;

                    for (String str : name) {
                        switch (str) {
                            case "로드":
                            case "로드미션": {
                                event.getChannel().sendMessage("로드미션: 3회").queue();
                                isCheck = true;
                                break;
                            }
                            case "정화": {
                                event.getChannel().sendMessage("정화: 10  회").queue();
                                isCheck = true;
                                break;
                            }
                            case "테흐":
                            case "테흐두인": {
                                event.getChannel().sendMessage("테흐두인: 15회").queue();
                                isCheck = true;
                                break;
                            }
                            case "글랜":
                            case "글랜베르나": {
                                event.getChannel().sendMessage("글랜베르나: 3회").queue();
                                isCheck = true;
                                break;
                            }
                            case "크롬":
                            case "크롬바스": {
                                event.getChannel().sendMessage("크롬바스: 10회").queue();
                                isCheck = true;
                                break;
                            }
                        }
                    }
                    if (!isCheck)
                        event.getChannel().sendMessage("횟수 제한이 없거나 존재 하지 않는 던전 입니다.").queue();
                }
            }
        }
    }
}
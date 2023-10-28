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
    public final String prefix = "!"; // 디스코드 검색 시 최초 선언에 필요한 특수문자.

    public static void main(String[] args) throws LoginException {

        String token = "";
        JDABuilder jda = JDABuilder.createDefault(token);
        jda.setStatus(OnlineStatus.ONLINE);
        jda.setActivity(Activity.playing(""));

        jda.enableIntents(GatewayIntent.MESSAGE_CONTENT,GatewayIntent.GUILD_MEMBERS,GatewayIntent.GUILD_WEBHOOKS).build().addEventListener(new Main()); // You need to implement this listener.

        RedisExample.connectJedisDB();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] name = event.getMessage().getContentRaw().split(" ");
        String nick = event.getMember().getNickname();
        if (nick == null) {
            nick = event.getAuthor().getName();
        }
        if (!event.getAuthor().isBot()) {
            if (name[0].equals(prefix + "정보")) {
                saveGameInformaition(name, event);
            }
        }
    }
    public static void saveGameInformaition(String[] dataName,MessageReceivedEvent event) {
        if (dataName.length == 1)
        {
            event.getChannel().sendMessage("!정보 <검색,저장,삭제>").queue();
        }

        else {
            for (String data : dataName) {
                switch (data) {
                    case "검색":{
                        if (dataName.length < 3) {
                            event.getChannel().sendMessage("ex: " + "!정보 검색 검색명").queue();
                        }
                        else {
                            String loadata = RedisExample.loadGameInformation(dataName[2]);
                            event.getChannel().sendMessage(loadata).queue();
                        }
                        break;
                    }
                    case "저장": {
                        if (dataName.length < 4) {
                            event.getChannel().sendMessage("ex: " + "!정보 저장 제목 내용").queue();
                        } else {
                            RedisExample.saveGameInformaition(dataName[2],dataName[3]);
                            event.getChannel().sendMessage("저장 완료 되었다냥").queue();
                        }
                        break;
                    }

                    case "삭제": {
                        if (dataName.length < 3) {
                            event.getChannel().sendMessage("ex" + "!정보 삭제 제목 ").queue();
                        } else {
                           String dataRemove = RedisExample.removeGameInformation(dataName[2]);
                            event.getChannel().sendMessage(dataRemove).queue();
                        }
                        break;
                    }
                }
            }
        }
    }
}
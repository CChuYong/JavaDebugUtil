package kr.chuyong.jdu.session;

import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class SessionFactory {
    private static HashMap<CommandSender, JduSession> sessionMap = new HashMap<>();
    public static JduSession getSession(CommandSender sender){
        return sessionMap.computeIfAbsent(sender, x->new JduSession());
    }
}

package kr.chuyong.jdu.commands;

import api.cosmoage.global.commandannotation.CosmoCMD;
import kr.chuyong.jdu.session.JduSession;
import kr.chuyong.jdu.session.SessionFactory;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCinstance {
    @CosmoCMD(parent="dinstc", op=true, console = true)
    public void onMainCommand(CommandSender sender, String[] args){
        sender.sendMessage("/dinstc string [instanceKey] [stringVal..]");
        sender.sendMessage("/dinstc player [instanceKey] [playerName]");
        sender.sendMessage("/dinstc int [instanceKey] [number]");
        sender.sendMessage("/dinstc boolean [instanceKey] [true/false]");
    }
    @CosmoCMD(parent="dinstc", child = "string", op=true, console = true, minArgs = 3)
    public void onstrCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        StringBuilder sb = new StringBuilder();
        for(int i = 2; i < args.length; i++){
            sb.append(args[i]);
            if(i != args.length -1)
                sb.append(" ");
        }
        session.insertObjectData(args[1], sb.toString());
        sender.sendMessage("Object insertion completed!");
    }
    @CosmoCMD(parent="dinstc", child = "player", op=true, console = true, minArgs = 3)
    public void onplayerCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        Player player = Bukkit.getPlayer(args[2]);
        if(player != null)
        {
            session.insertObjectData(args[1], player);
            sender.sendMessage("Object insertion completed!");
        }else
        {
            sender.sendMessage("that player not exists!");
        }
    }
    @CosmoCMD(parent="dinstc", child = "int", op=true, console = true, minArgs = 3)
    public void onIntCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        try{
            Integer i = Integer.parseInt(args[2]);
            session.insertObjectData(args[1], i);
            sender.sendMessage("Object insertion completed!");
        }catch(NumberFormatException ex){
            sender.sendMessage("that is not integer!");
        }
    }
    @CosmoCMD(parent="dinstc", child = "boolean", op=true, console = true, minArgs = 3)
    public void onBoolCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        try{
            Boolean i = Boolean.valueOf(args[2]);
            session.insertObjectData(args[1], i);
            sender.sendMessage("Object insertion completed!");
        }catch(Exception ex){
            sender.sendMessage("that is not boolean!");
        }
    }
}

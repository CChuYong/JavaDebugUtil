package kr.chuyong.jdu.commands;

import api.cosmoage.global.commandannotation.CosmoCMD;
import kr.chuyong.jdu.session.JduSession;
import kr.chuyong.jdu.session.SessionFactory;
import org.bukkit.command.CommandSender;

public class CommandInstance {
    @CosmoCMD(parent="dinstance", op=true, console = true)
    public void onMainCommand(CommandSender sender, String[] args){
        sender.sendMessage("/dinstance new [instanceKey] [classKey] [inst..]");
        sender.sendMessage("/dinstance assign [instanceKey] [methodKey] - static method to instance");
        sender.sendMessage("/dinstance assignm [instanceKey] [methodKey] [instance] [instances..] - dynamic method to instance");
        sender.sendMessage("/dinstance assignf [instanceKey] [fieldKey] [instanceKey|null]");
        sender.sendMessage("/dinstance print [instanceKey]");
    }
    @CosmoCMD(parent="dinstance", child="new", op=true, console = true, minArgs = 3)
    public void onNewCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        try{

            if(args.length >= 4){
                String[] arr = new String[args.length - 3];
                int app = 0;
                for(int i = 3; i<args.length; i++) {
                    arr[app] = args[i];
                    app++;
                }
                if(session.insertObject(args[1], args[2], arr)){
                    sender.sendMessage("Successfully initiated object");
                }else{
                    sender.sendMessage("Class not found!");
                }
            }else{
                if(session.insertObject(args[1], args[2])){
                    sender.sendMessage("Successfully initiated object");
                }else{
                    sender.sendMessage("Class not found!");
                }
            }
        }catch(Exception ex){
            sender.sendMessage("Encountered Exception!");
            sender.sendMessage(ex.getMessage());
            if(session.printStack){
                for(StackTraceElement el : ex.getStackTrace()){
                    sender.sendMessage(el.toString());
                }
            }
        }
    }
    @CosmoCMD(parent="dinstance", child="assign", op=true, console = true, minArgs = 3)
    public void onAssignCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        try{
            if(session.insertObjectByMethod(args[1], args[2])){
                sender.sendMessage("Successfully initiated object");
            }else{
                sender.sendMessage("Class not found!");
            }
        }catch(Exception ex){
            sender.sendMessage("Encountered Exception!");
            sender.sendMessage(ex.getMessage());
            if(session.printStack){
                for(StackTraceElement el : ex.getStackTrace()){
                    sender.sendMessage(el.toString());
                }
            }
        }
    }
    @CosmoCMD(parent="dinstance", child="print", op=true, console = true, minArgs = 2)
    public void onPrintCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        Object obj = session.getObject(args[1]);
        if(obj == null)
            sender.sendMessage("null");
        else
            sender.sendMessage(obj.toString());
    }
    @CosmoCMD(parent="dinstance", child="assignm", op=true, console = true, minArgs = 3)
    public void onAssignmCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        try{
            if(session.insertObjectByMethodDynamic(args[1], args[2], args[3], args)){
                sender.sendMessage("Successfully initiated object");
            }else{
                sender.sendMessage("Class not found!");
            }
        }catch(Exception ex){
            sender.sendMessage("Encountered Exception!");
            sender.sendMessage(ex.getMessage());
            if(session.printStack){
                for(StackTraceElement el : ex.getStackTrace()){
                    sender.sendMessage(el.toString());
                }
            }
        }
    }
    @CosmoCMD(parent="dinstance", child="assignf", op=true, console = true, minArgs = 4)
    public void onAssignfCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        try{
            if(session.insertObject(args[1], args[2], args[3])){
                sender.sendMessage("Successfully assigned object");
            }else{
                sender.sendMessage("Class not found!");
            }
        }catch(Exception ex){
            sender.sendMessage("Encountered Exception!");
            sender.sendMessage(ex.getMessage());
            if(session.printStack){
                for(StackTraceElement el : ex.getStackTrace()){
                    sender.sendMessage(el.toString());
                }
            }
        }
    }
}
